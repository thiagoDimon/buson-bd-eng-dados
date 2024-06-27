# Pré-Requisitos para o Projeto

Para garantir o funcionamento adequado do projeto, certifique-se de que todos os seguintes pré-requisitos estão atendidos e instalados:

## Ferramentas Necessárias

1. **SPARK 3.3.1**
    - Utilizado para o processamento distribuído de dados.

2. **DELTA 2.3.0**
    - Formato de armazenamento que permite a leitura e escrita de dados de forma eficiente com o Apache Spark.

3. **HADOOP 3.2.2**
    - Framework necessário para o funcionamento do Spark e Delta.

4. **MINIO OBJECT STORAGE**
    - Armazenamento de objetos onde os arquivos serão salvos.

5. **ASTRO CLI**
    - Interface de linha de comando para orquestração de dados.

6. **AIRFLOW**
    - Ferramenta de orquestração de workflows para agendar e monitorar os pipelines de dados.

7. **DOCKER**
    - Ferramenta para criar e gerenciar containers, utilizada para subir o Airflow, MinIO e o cluster do Spark.

8. **POWER BI**
    - Ferramenta utilizada para a criação e visualização dos dados de forma visual.

9. **ODBC**
    - Driver PostgreSQL ANSI(x64) utilizado para a conexão com o banco de dados Postgres.



## Ferramentas Adicionais (Opcional)

As seguintes ferramentas foram utilizadas durante o desenvolvimento do projeto, mas não são necessárias para a execução:

- **GIT**
    - Sistema de controle de versão utilizado para clonar o repositório do projeto.

- **Java**
    - Utilizado para algumas dependências do Spark.

- **Faker**
    - Biblioteca para geração de dados falsos para testes e desenvolvimento.

Certifique-se de que todas as ferramentas necessárias estão instaladas e configuradas corretamente antes de iniciar o projeto.
