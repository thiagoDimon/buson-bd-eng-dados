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
def minio_buckets():
    @task
    def create_bucket():
        client = Minio(MIN_HOST, access_key=MIN_ACCESS_KEY, secret_key=MIN_SECRET_KEY, secure=False)
        minio_buckets = ["landing-zone", "bronze", "silver", "gold"]
        for bucket in minio_buckets:
            client.make_bucket(bucket)

    create_bucket()

minio_buckets()
