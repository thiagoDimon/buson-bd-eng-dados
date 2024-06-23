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
        
        file_names = ['./dags/csvs/associacaos.csv', './dags/csvs/instituicaos.csv', './dags/csvs/cursos.csv', './dags/csvs/usuarios.csv', './dags/csvs/parametros.csv', './dags/csvs/pagamentos.csv']

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
        export_to_csv('associacaos', './dags/csvs/associacaos.csv')
        export_to_csv('instituicaos', './dags/csvs/instituicaos.csv')
        export_to_csv('cursos', './dags/csvs/cursos.csv')
        export_to_csv('usuarios', './dags/csvs/usuarios.csv')
        export_to_csv('parametros', './dags/csvs/parametros.csv')
        export_to_csv('pagamentos', './dags/csvs/pagamentos.csv')

        # Fechando o cursor e a conexão
        cur.close()
        conn.close()
        
        # Bucket | Nome do Arquivo | Caminho do Arquivo
        client.fput_object('landing-zone', 'associacaos.csv', './dags/csvs/associacaos.csv')
        client.fput_object('landing-zone', 'instituicaos.csv', './dags/csvs/instituicaos.csv')
        client.fput_object('landing-zone', 'cursos.csv', './dags/csvs/cursos.csv')
        client.fput_object('landing-zone', 'usuarios.csv', './dags/csvs/usuarios.csv')
        client.fput_object('landing-zone', 'parametros.csv', './dags/csvs/parametros.csv')
        client.fput_object('landing-zone', 'pagamentos.csv', './dags/csvs/pagamentos.csv')

    insert_csv()

landzone()
