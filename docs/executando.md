# Executando o projeto

- Executar as seguintes DAGS na ordem aqui fornecida: 


## minio_buckets:
realiza a criação dos buckets landing-zone, bronze, silver e gold no nosso object storage 
para persistir os nossos dados que serão utilizados para manipulação

## landing-zone:
extrai as informações do banco de dados criado dentro do nosso container em formato csv para manipulação com o spark 
e persiste nesta mesma camada este arquivos

## bronze 
realizar a leitura dos arquivos da camada landing-zone e manipulação de dados inserindo metadados como hora de processamento e arquivo de origem

## silver 
realiza a leitura dos arquivos persistidos em formato delta na bronze e realiza manipulações nos arquivos 
padronizando nomes de tabelas e deletando dados desnecessários

## gold 
realiza a leitura dos arquivos persistidos em formato delta na silver e realiza manipulações dos dados a fim de 
disponibilizar os dados tratados no modelo One Big Table, encaminhando e persistindo novamente no banco de dados para consumo do Power BI

#
#

Nessa parte descrita acima, acredito que pode ser colocado junto os comandos disponiveis nos arquivos, buckets.py, landzone.py, bronze.py, 
silver.py e gold.py, os quais refltem todo o processo descrito acima como exemplificação que estão no caminho astro/dags