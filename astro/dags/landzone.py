# Insere arquivo no bucket landing-zone
import os
from airflow.decorators import dag, task
from minio import Minio
from pendulum import datetime
import psycopg2

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
        
        file_names = ['./dags/csv/associacaos.csv', './dags/csv/instituicaos.csv', './dags/csv/cursos.csv', './dags/csv/usuarios.csv', './dags/csv/parametros.csv', './dags/csv/pagamentos.csv']

        for file_name in file_names:
            with open(file_name, 'w') as f:
                pass

        conn = psycopg2.connect(
            dbname="postgres",
            user="postgres",
            password="postgres",
            host="host.docker.internal",
            port="5432"
        )

        cur = conn.cursor()

        # Função para exportar dados para CSV
        def export_to_csv(table_name, file_name):
            with open(file_name, 'w') as f:
                cur.copy_expert(f'COPY {table_name} TO STDOUT WITH CSV HEADER', f)

        # Exportando as tabelas para arquivos CSV
        export_to_csv('associacaos', './dags/csv/associacaos.csv')
        export_to_csv('instituicaos', './dags/csv/instituicaos.csv')
        export_to_csv('cursos', './dags/csv/cursos.csv')
        export_to_csv('usuarios', './dags/csv/usuarios.csv')
        export_to_csv('parametros', './dags/csv/parametros.csv')
        export_to_csv('pagamentos', './dags/csv/pagamentos.csv')

        # Fechando o cursor e a conexão
        cur.close()
        conn.close()
        
        # Bucket | Nome do Arquivo | Caminho do Arquivo
        client.fput_object('landing-zone', 'associacaos.csv', './dags/csv/associacaos.csv')
        client.fput_object('landing-zone', 'instituicaos.csv', './dags/csv/instituicaos.csv')
        client.fput_object('landing-zone', 'cursos.csv', './dags/csv/cursos.csv')
        client.fput_object('landing-zone', 'usuarios.csv', './dags/csv/usuarios.csv')
        client.fput_object('landing-zone', 'parametros.csv', './dags/csv/parametros.csv')
        client.fput_object('landing-zone', 'pagamentos.csv', './dags/csv/pagamentos.csv')

    insert_csv()

landzone()
