# Insere arquivo no bucket landing-zone
import os
from airflow.decorators import dag, task
from minio import Minio
from pendulum import datetime

MIN_ACCESS_KEY = os.environ.get("MIN_ACCESS_KEY")
MIN_SECRET_KEY = os.environ.get("MIN_SECRET_KEY")
MIN_HOST = os.environ.get("MIN_HOST")

@dag(
    start_date=datetime(2024, 6, 16),
    schedule="@daily",
    default_args={"owner": "minio", "retries": 3}
)
def landzone():

    @task
    def insert_csv():
        client = Minio(MIN_HOST, access_key=MIN_ACCESS_KEY, secret_key=MIN_SECRET_KEY, secure=False)
        # Bucket | Nome do Arquivo | Caminho do Arquivo
        client.fput_object('landing-zone', 'carro.csv', './dags/carro.csv')

    insert_csv()

landzone()
