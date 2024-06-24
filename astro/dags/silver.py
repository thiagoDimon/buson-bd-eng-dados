import os
from airflow.decorators import dag, task
from pendulum import datetime
from pyspark.sql.functions import current_timestamp, lit
import pyspark
from pyspark.sql import SparkSession

MIN_ACCESS_KEY = os.environ.get("MIN_ACCESS_KEY")
MIN_SECRET_KEY = os.environ.get("MIN_SECRET_KEY")
MIN_HOST = os.environ.get("MIN_HOST")

@dag(
    start_date=datetime(2024, 6, 16),
    schedule="@daily",
    default_args={"owner": "minio", "retries": 3}
)
def silver():

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

        # Lendo os arquivos da camada bronze
        df_associacaos = spark.read.format("delta").load(f"s3a://bronze/associacaos/")
        df_cursos = spark.read.format("delta").load(f"s3a://bronze/cursos/")
        df_instituicaos = spark.read.format("delta").load(f"s3a://bronze/instituicaos/")
        df_pagamentos = spark.read.format("delta").load(f"s3a://bronze/pagamentos/")
        df_parametros = spark.read.format("delta").load(f"s3a://bronze/parametros/")
        df_usuarios = spark.read.format("delta").load(f"s3a://bronze/usuarios/")

        # Adicionando metadados de data e hora de processamento e nome do arquivo de origem
        df_associacaos = df_associacaos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("associacaos"))
        df_cursos = df_cursos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("cursos"))
        df_instituicaos = df_instituicaos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("instituicaos"))
        df_pagamentos = df_pagamentos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("pagamentos"))
        df_parametros = df_parametros.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("parametros"))
        df_usuarios = df_usuarios.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("usuarios"))

        df_associacaos = (
            df_associacaos
            .withColumnRenamed("id"              , "ID")
            .withColumnRenamed("cnpj"            , "CNPJ")
            .withColumnRenamed("nome"            , "NOME")
            .withColumnRenamed("endereco"        , "ENDERECO")
            .withColumnRenamed("situacao"        , "SITUACAO")
            .drop("created_at")
            .drop("updated_at")
            .drop("data_hora_bronze")
            .withColumnRenamed("nome_arquivo"    , "NOME_ARQUIVO")
            .withColumnRenamed("data_hora_silver"   , "DATA_HORA_SILVER")
        )

        df_cursos = (
            df_cursos
            .withColumnRenamed("id"              , "ID")
            .withColumnRenamed("nome"            , "NOME")
            .withColumnRenamed("situacao"            , "SITUACAO")
            .withColumnRenamed("instituicao_id"        , "INSTITUICAO_ID")
            .drop("created_at")
            .drop("updated_at")
            .drop("data_hora_bronze")
            .withColumnRenamed("nome_arquivo"    , "NOME_ARQUIVO")
            .withColumnRenamed("data_hora_silver"   , "DATA_HORA_SILVER")
        )

        df_instituicaos = (
            df_instituicaos
            .withColumnRenamed("id"              , "ID")
            .withColumnRenamed("nome"            , "NOME")
            .withColumnRenamed("endereco"            , "ENDERECO")
            .withColumnRenamed("situacao"            , "SITUACAO")
            .withColumnRenamed("associacao_id"        , "ASSOCIACAO_ID")
            .drop("created_at")
            .drop("updated_at")
            .drop("data_hora_bronze")
            .withColumnRenamed("nome_arquivo"    , "NOME_ARQUIVO")
            .withColumnRenamed("data_hora_silver"   , "DATA_HORA_SILVER")
        )

        df_pagamentos = (
            df_pagamentos
            .withColumnRenamed("id"              , "ID")
            .withColumnRenamed("usuario_id"            , "USUARIO_ID")
            .withColumnRenamed("tipo"            , "TIPO")
            .withColumnRenamed("valor"            , "VALOR")
            .withColumnRenamed("multa"        , "MULTA")
            .withColumnRenamed("data_vencimento"        , "DATA_VENCIMENTO")
            .withColumnRenamed("data_pagamento"        , "DATA_PAGAMENTO")
            .withColumnRenamed("situacao"        , "SITUACAO")
            .drop("created_at")
            .drop("updated_at")
            .drop("data_hora_bronze")
            .withColumnRenamed("nome_arquivo"    , "NOME_ARQUIVO")
            .withColumnRenamed("data_hora_silver"   , "DATA_HORA_SILVER")
        )

        df_parametros = (
            df_parametros
            .withColumnRenamed("id"              , "ID")
            .withColumnRenamed("associacao_id"            , "ASSOCIACAO_ID")
            .withColumnRenamed("valor1"            , "VALOR1")
            .withColumnRenamed("valor2"            , "VALOR2")
            .withColumnRenamed("valor3"            , "VALOR3")
            .withColumnRenamed("valor4"        , "VALOR4")
            .withColumnRenamed("valor5"        , "VALOR5")
            .withColumnRenamed("valor6"        , "VALOR6")
            .withColumnRenamed("valor_multa"        , "VALOR_MULTA")
            .withColumnRenamed("dia_vencimento"        , "DIA_VENCIMENTO")
            .withColumnRenamed("dia_abertura_pagamento"        , "DIA_ABERTURA_PAGAMENTOS")
            .withColumnRenamed("dias_tolerancia_multa"        , "DIAS_TOLERANCIA_MULTA")
            .withColumnRenamed("libera_alteracao_dados_pessoais"        , "LIBERA_ALTERACAO_DADOS_PESSOAIS")
            .drop("created_at")
            .drop("updated_at")
            .drop("data_hora_bronze")
            .withColumnRenamed("nome_arquivo"    , "NOME_ARQUIVO")
            .withColumnRenamed("data_hora_silver"   , "DATA_HORA_SILVER")
        )

        df_usuarios = (
            df_usuarios
            .withColumnRenamed("id"              , "ID")
            .withColumnRenamed("nome"            , "NOME")
            .withColumnRenamed("email"            , "EMAIL")
            .withColumnRenamed("telefone"            , "TELEFONE")
            .withColumnRenamed("endereco"            , "ENDERECO")
            .withColumnRenamed("matricula"            , "MATRICULA")
            .withColumnRenamed("curso_id"        , "CURSO_ID")
            .withColumnRenamed("associacao_id"        , "ASSOCIACAO_ID")
            .withColumnRenamed("tipo_acesso"        , "TIPO_ACESSO")
            .withColumnRenamed("senha"        , "SENHA")
            .withColumnRenamed("situacao"        , "SITUACAO")
            .withColumnRenamed("dias_uso_transporte"        , "DIAS_USO_TRANSPORTE")
            .drop("created_at")
            .drop("updated_at")
            .drop("data_hora_bronze")
            .withColumnRenamed("nome_arquivo"    , "NOME_ARQUIVO")
            .withColumnRenamed("data_hora_silver"   , "DATA_HORA_SILVER")
        )

        # Salvando os arquivos na camada silver
        df_associacaos.write.format("delta").save(f"s3a://silver/associacaos/")
        df_cursos.write.format("delta").save(f"s3a://silver/cursos/")
        df_instituicaos.write.format("delta").save(f"s3a://silver/instituicaos/")
        df_pagamentos.write.format("delta").save(f"s3a://silver/pagamentos/")
        df_parametros.write.format("delta").save(f"s3a://silver/parametros/")
        df_usuarios.write.format("delta").save(f"s3a://silver/usuarios/")

    operacoes()

silver()
