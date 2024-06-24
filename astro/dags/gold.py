import os
from airflow.decorators import dag, task
from pendulum import datetime
from pyspark.sql.functions import current_timestamp, lit
import pyspark
from pyspark.sql import SparkSession
import pandas as pd

MIN_ACCESS_KEY = os.environ.get("MIN_ACCESS_KEY")
MIN_SECRET_KEY = os.environ.get("MIN_SECRET_KEY")
MIN_HOST = os.environ.get("MIN_HOST")

@dag(
    start_date=datetime(2024, 6, 16),
    schedule="@daily",
    default_args={"owner": "minio", "retries": 3}
)
def gold():

    @task
    def operacoes():
        conf = (
            pyspark.SparkConf()
                .setAppName("app_name")
                .set("spark.jars.packages", "org.apache.hadoop:hadoop-aws:3.2.2,io.delta:delta-core_2.12:2.3.0")
                .set("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
                .set("spark.hadoop.fs.s3a.access.key", MIN_ACCESS_KEY)
                .set("spark.hadoop.fs.s3a.secret.key", MIN_SECRET_KEY)
                .set("spark.hadoop.fs.s3a.endpoint", MIN_HOST)
                .set("spark.hadoop.fs.s3a.connection.ssl.enabled", "false")
                .set("spark.hadoop.fs.s3a.path.style.access", "true")
                .set("spark.hadoop.fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider")
                .set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
        )

        spark = SparkSession.builder.config(conf=conf).getOrCreate()

        # Lendo os arquivos da camada silver
        df_associacaos = spark.read.format("delta").load(f"s3a://silver/associacaos/")
        df_cursos = spark.read.format("delta").load(f"s3a://silver/cursos/")
        df_instituicaos = spark.read.format("delta").load(f"s3a://silver/instituicaos/")
        df_pagamentos = spark.read.format("delta").load(f"s3a://silver/pagamentos/")
        df_parametros = spark.read.format("delta").load(f"s3a://silver/parametros/")
        df_usuarios = spark.read.format("delta").load(f"s3a://silver/usuarios/")

        # Clonar para não alterar os dados de origem
        df_associacaos_clone = df_associacaos
        df_cursos_clone = df_cursos
        df_instituicaos_clone = df_instituicaos
        df_pagamentos_clone = df_pagamentos
        df_parametros_clone = df_parametros
        df_usuarios_clone = df_usuarios

        # Selecionando as colunas necessárias
        df_associacaos_final = df_associacaos_clone.select("ID", "NOME", "SITUACAO")
        df_pagamentos_final = df_pagamentos_clone.select("ID", "DATA_VENCIMENTO", "VALOR", "MULTA", "SITUACAO", "USUARIO_ID")
        df_usuarios_final = df_usuarios_clone.select("ID", "NOME", "SITUACAO", "DIAS_USO_TRANSPORTE", "ASSOCIACAO_ID")

        # Renomeando colunas para evitar ambiguidades após a junção
        df_associacaos_final = df_associacaos_final.withColumnRenamed("ID", "ASSOCIACAO_ID").withColumnRenamed("NOME", "NOME_ASSOCIACAO").withColumnRenamed("SITUACAO", "SITUACAO_ASSOCIACAO")
        df_usuarios_final = df_usuarios_final.withColumnRenamed("ID", "USUARIO_ID").withColumnRenamed("NOME", "NOME_USUARIO").withColumnRenamed("SITUACAO", "SITUACAO_USUARIO")

        # Realizando as junções
        df_merged = df_pagamentos_final.join(df_usuarios_final, on="USUARIO_ID", how="left")
        df_final = df_merged.join(df_associacaos_final, on="ASSOCIACAO_ID", how="left")

        # Salvando o modelo_eng_dados na camada gold
        df_final.write.format("delta").save(f"s3a://gold/modelo_eng_dados/")

        # Parametros de conexão com o banco
        jdbc_url = "jdbc:postgresql://host.docker.internal:5432/postgres"
        connection_properties = {
            "user": "postgres",
            "password": "postgres",
            "driver": "org.postgresql.Driver"
        }

        # Escrever o DataFrame no PostgreSQL criando uma nova tabela
        df_final.write.jdbc(url=jdbc_url, table="public.modelo_eng_dados", mode="overwrite", properties=connection_properties)

    operacoes()

gold()
