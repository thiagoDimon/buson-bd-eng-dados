# Geração de Dados

Foi criado um banco de dados com o modelo incial do buson, criado um backend em java para fazer a conexcao com o banco apos isso utilizando as entidades em java e utilizando a dependencia do faker para criar dados ficticios e popular as tabelas.
Apos isso foi pego scripts sql que foram gerados e inseriddos em um arquivo sql com a cricao das tabelas e insercao dos dados. 
Na criacao dos ambientes do Airflow e minio o banco iniciado foi o postgres junto com o script de inicializacao para ja ter todos os dados inseridos, foi usado apenas o volume inicial para se obter o script.