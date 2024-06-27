# Todos os comandos seguidos
# Rodar esse aqui na apresentação 

# Insere arquivo no bucket landing-zone

import os
from pyspark.sql.functions import current_timestamp, lit
import pyspark
from pyspark.sql import SparkSession
import pandas as pd

MIN_ACCESS_KEY = "uqDOLBCtACeVkz5FB6Fo"
MIN_SECRET_KEY = "HjzXolPPIhmLEUeuXJ5EYiXHZ74bN7iKFs1DPf7l"
MIN_HOST = "host.docker.internal:9000"

conf = (
    pyspark.SparkConf()
        .setAppName('app_name')
        .set('spark.jars.packages', 'org.apache.hadoop:hadoop-aws:3.2.2,io.delta:delta-core_2.12:2.3.0,org.postgresql:postgresql:42.5.0')
        .set('spark.sql.catalog.spark_catalog', 'org.apache.spark.sql.delta.catalog.DeltaCatalog')
        .set("spark.hadoop.fs.s3a.access.key", MIN_ACCESS_KEY)
        .set("spark.hadoop.fs.s3a.secret.key", MIN_SECRET_KEY)
        .set("spark.hadoop.fs.s3a.endpoint", MIN_HOST)
        .set("spark.hadoop.fs.s3a.connection.ssl.enabled", "false")
        .set("spark.hadoop.fs.s3a.path.style.access", "true")
        .set('spark.hadoop.fs.s3a.aws.credentials.provider', 'org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider')
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
df_pagamentos_clone = df_pagamentos
df_usuarios_clone = df_usuarios

# Selecionando as colunas necessárias e renomeando as colunas para evitar ambiguidades
df_associacaos_final = df_associacaos_clone.select("ID", "NOME", "SITUACAO") \
    .withColumnRenamed("ID", "codigo_associacao") \
    .withColumnRenamed("NOME", "nome_associacao") \
    .withColumnRenamed("SITUACAO", "situacao_associacao")

df_pagamentos_final = df_pagamentos_clone.select("ID", "DATA_VENCIMENTO", "VALOR", "MULTA", "SITUACAO", "USUARIO_ID") \
    .withColumnRenamed("ID", "codigo_pagamento") \
    .withColumnRenamed("DATA_VENCIMENTO", "data_vencimento") \
    .withColumnRenamed("VALOR", "valor") \
    .withColumnRenamed("MULTA", "multa") \
    .withColumnRenamed("SITUACAO", "situacao") \
    .withColumnRenamed("USUARIO_ID", "codigo_usuario")

df_usuarios_final = df_usuarios_clone.select("ID", "NOME", "SITUACAO", "DIAS_USO_TRANSPORTE", "ASSOCIACAO_ID") \
    .withColumnRenamed("ID", "codigo_usuario") \
    .withColumnRenamed("NOME", "nome_usuario") \
    .withColumnRenamed("SITUACAO", "situacao_usuario") \
    .withColumnRenamed("DIAS_USO_TRANSPORTE", "dias_uso_transporte") \
    .withColumnRenamed("ASSOCIACAO_ID", "codigo_associacao")

# Realizando a junção dos dataframes
df_merged = df_pagamentos_final \
    .join(df_usuarios_final, df_pagamentos_final["codigo_usuario"] == df_usuarios_final["codigo_usuario"], "inner") \
    .join(df_associacaos_final, df_usuarios_final["codigo_associacao"] == df_associacaos_final["codigo_associacao"], "inner") \
    .select(df_pagamentos_final["codigo_pagamento"],
            df_pagamentos_final["data_vencimento"],
            df_pagamentos_final["valor"],
            df_pagamentos_final["multa"],
            df_pagamentos_final["situacao"],
            df_usuarios_final["codigo_usuario"],
            df_usuarios_final["nome_usuario"],
            df_usuarios_final["situacao_usuario"],
            df_usuarios_final["dias_uso_transporte"],
            df_associacaos_final["codigo_associacao"],
            df_associacaos_final["nome_associacao"],
            df_associacaos_final["situacao_associacao"])

# Salvando o modelo_eng_dados na camada gold
df_merged.write.format("delta").save(f"s3a://gold/modelo_eng_dados/")

# Parametros de conexão com o banco
jdbc_url = "jdbc:postgresql://host.docker.internal:5432/postgres"
connection_properties = {
    "user": "postgres",
    "password": "postgres",
    "driver": "org.postgresql.Driver"
}

# Escrever o DataFrame no PostgreSQL criando uma nova tabela
df_merged.write.jdbc(url=jdbc_url, table="public.modelo_eng_dados", mode="overwrite", properties=connection_properties)