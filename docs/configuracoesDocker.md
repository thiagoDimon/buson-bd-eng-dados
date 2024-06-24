# Configurações Docker

## Dockerfile
	- Neste arquivo foi necessário realizar a instalação do manipulador do minio
	- Instalação do providers do apache spark para realizar a conexão com o airflow
	- Instalação da biblioteca pandas para manipulação dos dataframes
	- Instalação do Java e configuração das variáveis de ambiente para funcionamento do SPARK


## Docker Compose Override
	- Realizado a inserção da imagem do minio e configuração da subnet para que o airflow consiga lhe enxergar
	- Realizado a inserção da imagem do cluster do spark e configuração da subnet para que o airflow consiga lhe enxergar
	- Na imagem do postgres adicionado os volumes do banco de dados buson_bd para realizar a criação das tabelas e inserção automática dos dados de exemplo