# Insere arquivo no bucket landing-zone
import os
from airflow.decorators import dag, task
from minio import Minio
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
def bronze():

    @task
    def operacoes():
        conf = (
            pyspark.SparkConf()
                .setAppName('app_name')
                .set('spark.jars.packages', 'org.apache.hadoop:hadoop-aws:3.2.2,io.delta:delta-core_2.12:2.3.0')
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

        # Lendo o arquivo carro.csv do bucket landing-zone
        df = spark.read.option("inferSchema", "true").option("header", "true").csv(f"s3a://landing-zone/carro.csv") 

        # Adicionando metadados de data e hora de processamento e nome do arquivo de origem
        df_carro = df.withColumn("data_hora_bronze", current_timestamp()).withColumn("nome_arquivo", lit("carro.csv"))

        # Salvando arquivo em formato delta no bucket bronze
        df_carro.write.format("delta").save(f"s3a://bronze/carro")

    operacoes()

bronze()
