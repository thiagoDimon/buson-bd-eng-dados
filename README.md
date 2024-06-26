# buson-bd-eng-dados

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

O projeto BusON se baseia em um banco de dados dimensional criado na matéria de IA, sendo usado como base para criar uma grande massa de dados e fazer uma ingestão de dados usando o modelo medalhão para no final visualizar os dados em um BI.

## Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

Consulte **[Documentação MkDocs](https://thiagodimon.github.io/buson-bd-eng-dados/)** para saber como implantar o projeto.

## Desenho de Arquitetura

![image](https://github.com/jlsilva01/projeto-ed-satc/assets/484662/541de6ab-03fa-49b3-a29f-dec8857360c1)

## Pré-requisitos

* **Docker: ** Necessário ter [Docker](https://www.docker.com/products/docker-desktop/) instalado na máquina.
* **Git:** Necessário ter [Git](https://git-scm.com) instalado na máquina.

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

## Implantação

Adicione notas adicionais sobre como implantar isso em um sistema ativo

## Ferramentas utilizadas

* [Apache Airflow](https://airflow.apache.org) - Orquestração de Fluxos de Trabalho
* [Apache Spark](https://spark.apache.org) - Processamento Distribuído de Dados
* [Docker](https://www.docker.com/products/docker-desktop/) - Contêinerização de Aplicações
* [MinIO](https://min.io) - Armazenamento de Objetos Escalável
* [MkDocs](https://www.mkdocs.org) - Documentação
* [Postgres](https://www.postgresql.org) - Banco de Dados Relacional
* [Power BI](https://www.microsoft.com/pt-br/power-platform/products/power-bi/landing/free-account?ef_id=_k_Cj0KCQjwj9-zBhDyARIsAERjds3j5ypF-nLzY9DWq1WYBWyktjocg-5a4B2SoYNIfRIwYLMGAQI5GDQaAgZWEALw_wcB_k_&OCID=AIDcmmk4cy2ahx_SEM__k_Cj0KCQjwj9-zBhDyARIsAERjds3j5ypF-nLzY9DWq1WYBWyktjocg-5a4B2SoYNIfRIwYLMGAQI5GDQaAgZWEALw_wcB_k_&gad_source=1&gclid=Cj0KCQjwj9-zBhDyARIsAERjds3j5ypF-nLzY9DWq1WYBWyktjocg-5a4B2SoYNIfRIwYLMGAQI5GDQaAgZWEALw_wcB) - Visualização e Análise dos Dados Processados

## Colaboração

Por favor, leia o [COLABORACAO](https://gist.github.com/usuario/colaboracao.md) para obter detalhes sobre o nosso código de conduta e o processo para nos enviar pedidos de solicitação.

Se desejar publicar suas modificações em um repositório remoto no GitHub, siga estes passos:

1. Crie um novo repositório vazio no GitHub.
2. No terminal, navegue até o diretório raiz do projeto.
3. Execute os seguintes comandos:

```bash
git remote set-url origin https://github.com/seu-usuario/nome-do-novo-repositorio.git
git add .
git commit -m "Adicionar minhas modificações"
git push -u origin master
```

Isso configurará o repositório remoto e enviará suas modificações para lá.

## Versão

Fale sobre a versão e o controle de versões para o projeto. Para as versões disponíveis, observe as [tags neste repositório](https://github.com/suas/tags/do/projeto). 

## Autores

* **Bruno Dimon** - *Documentação* - [Bruno Dimon](https://github.com/BrunoDimon)
* **Douglas Kuerten** - *Documentação* - [Douglas Kuerten](https://github.com/DouglasKuerten)
* **Gustavo Taufembach Bett** - *Documentação* - [Gustavo Taufembach Bett](https://github.com/GustavoTBett)
* **Lucas Zanoni** - *Documentação* - [Lucas Zanoni](https://github.com/Castrozan)
* **Miguel Cimolin** - *Documentação* - [Miguel Cimolin](https://github.com/miguelcimolin)
* **Pedro Guedes** - *Documentação* - [Pedro Guedes](https://github.com/Pedroguedez)

Você também pode ver a lista de todos os [colaboradores](https://github.com/usuario/projeto/colaboradores) que participaram deste projeto.

## Licença

Este projeto está sob a licença (sua licença) - veja o arquivo [LICENSE](https://github.com/jlsilva01/projeto-ed-satc/blob/main/LICENSE) para detalhes.

## Referências

[Dataway BR](https://www.youtube.com/watch?v=eOrWEsZIfKU)
[Dremio](https://www.youtube.com/watch?v=X3wfVaSQS_c)
[Chat-GPT](https://openai.com/chatgpt/)