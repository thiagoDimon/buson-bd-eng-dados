# Buson Delta Lake

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Sobre
 - O projeto Buson Delta Lake se baseia em um banco de dados relacional criado na matéria de IA do Buson App, sendo usado como base para criar uma grande massa de dados e fazer uma ingestão de dados usando o modelo medalhão para no final visualizar os dados em um BI.

> Buson App é um aplicativo para facilitar o gerenciamento dos pagamentos mensais dos acadêmicos que utilizam o serviço de transporte para deslocamento até as universidades. Além disso, o aplicativo fornece informações detalhadas sobre a associação e, situação dos acadêmicos e seus pagamentos, por meio de um chat com inteligência artificial.

## Documentação

A documentação permitirá que você obtenha todas as informações sobre o projeto e as instruções para a operação na sua máquina local para fins de desenvolvimento e teste.

Consulte **[Documentação MkDocs](https://thiagodimon.github.io/buson-bd-eng-dados/)** para saber como executar o projeto.

## Desenho de Arquitetura

![image](https://github.com/thiagoDimon/buson-bd-eng-dados/assets/69534716/e9c197ca-1acc-4dde-8e3c-57d17957c252)

## Visão Geral

* O Apache Airflow é nossa plataforma de gerenciamento de fluxo de trabalho, responsável por orquestrar todas as tarefas na nossa pipeline de dados e transformações necessárias, até a disponibilização em uma interface de análise de dados.
* A massa de dados será gerada usando a biblioteca Faker em Java.
* Postgres foi utilizado para armazenar esses dados, utilizando um script Python.
* O Spark será usado junto com o Airflow para ler e manipular os dados do Postgres, que serão extraídos no formato de arquivo CSV.
* O MinIO é usado para criar Buckets, permitindo a persistência dos dados em Object Storage.
* O Bucket do MinIO criará as 4 camadas da pipeline: Landing-Zone, Bronze, Silver e Gold.
* Na Landing-Zone, os dados serão extraídos do banco e persistidos em seu formato bruto, em CSV.
* Na Bronze, os dados serão extraídos da Landing-Zone e colunas de metadados serão criadas. As alterações serão persistidas na camada Bronze, em CSV.
* Na Silver, os dados serão extraídos da Bronze e colunas de metadados serão criadas. Os nomes das colunas serão padronizados, e colunas desnecessárias serão descartadas. As alterações serão persistidas na camada Silver, em CSV.
* Na Gold, os dados serão extraídos da Silver e colunas de metadados serão criadas. O nome de algumas colunas será alterado para evitar ambiguidades nos resultados. Após isso, os dataframes serão unificados em apenas um. Este dataframe final será persistido na camada Gold, em CSV.
* O Power BI será responsável pela apresentação dos dados em Dashboards.

## Ferramentas utilizadas

* [Apache Airflow](https://airflow.apache.org) - Orquestração de Fluxos de Trabalho
* [Apache Spark](https://spark.apache.org) - Processamento Distribuído de Dados
* [Docker](https://www.docker.com/products/docker-desktop/) - Contêinerização de Aplicações
* [MinIO](https://min.io) - Armazenamento de Objetos Escalável
* [MkDocs](https://www.mkdocs.org) - Documentação
* [Postgres](https://www.postgresql.org) - Banco de Dados Relacional
* [Power BI](https://www.microsoft.com/pt-br/power-platform/products/power-bi/landing/free-account?ef_id=_k_Cj0KCQjwj9-zBhDyARIsAERjds3j5ypF-nLzY9DWq1WYBWyktjocg-5a4B2SoYNIfRIwYLMGAQI5GDQaAgZWEALw_wcB_k_&OCID=AIDcmmk4cy2ahx_SEM__k_Cj0KCQjwj9-zBhDyARIsAERjds3j5ypF-nLzY9DWq1WYBWyktjocg-5a4B2SoYNIfRIwYLMGAQI5GDQaAgZWEALw_wcB_k_&gad_source=1&gclid=Cj0KCQjwj9-zBhDyARIsAERjds3j5ypF-nLzY9DWq1WYBWyktjocg-5a4B2SoYNIfRIwYLMGAQI5GDQaAgZWEALw_wcB) - Visualização e Análise dos Dados Processados

## Autores

* *Power BI/Documentação/Métricas* - [Bruno Dimon](https://github.com/BrunoDimon)
* *Configuração Mkdocs/Documentação/Métricas* - [Douglas Kuerten](https://github.com/DouglasKuerten)
* *Ingestão de Dados* - [Gustavo Taufembach Bett](https://github.com/GustavoTBett)
* *Pipeline de Dados* - [Lucas Zanoni](https://github.com/Castrozan)
* *Documentação* - [Miguel Cimolin](https://github.com/miguelcimolin)
* *Documentação* - [Pedro Guedes](https://github.com/Pedroguedez)
* *Pipeline de Dados* - [Thiago Dimon](https://github.com/thiagodimon)

Todos os Colaboradores do Projeto -> [CLIQUE AQUI](https://github.com/thiagoDimon/buson-bd-eng-dados/graphs/contributors).

## Licença

Este projeto está sob a licença (sua licença) - veja o arquivo [LICENSE](https://github.com/thiagoDimon/buson-bd-eng-dados/blob/main/LICENSE) para detalhes.

## Referências

[Dataway BR](https://www.youtube.com/watch?v=eOrWEsZIfKU)

[Dremio](https://www.youtube.com/watch?v=X3wfVaSQS_c)

[Chat-GPT](https://openai.com/chatgpt/)
