# Configurações Docker

# Dockerfile

- Neste arquivo foi necessário realizar a instalação do manipulador do MinIO.
- Instalação do provider do Apache Spark para realizar a conexão com o Airflow.
- Instalação da biblioteca pandas para manipulação dos dataframes.
- Instalação do Java e configuração das variáveis de ambiente para o funcionamento do Spark.

# Docker Compose Override

- Realizado a inserção da imagem do MinIO e configuração da subnet para que o Airflow consiga enxergá-lo.
- Realizado a inserção da imagem do cluster do Spark e configuração da subnet para que o Airflow consiga enxergá-lo.
- Na imagem do PostgreSQL, adicionados os volumes do banco de dados `buson_bd` para realizar a criação das tabelas e inserção automática dos dados de exemplo.
#
> Obs: Essas configurações já estão prontas na pasta do projeto