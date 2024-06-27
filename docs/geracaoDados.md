# Geração de Dados

##1. Modelagem do Banco de Dados
-   Foi criado um modelo inicial de banco de dados baseado no conceito do projeto Buson. 
-   O modelo incluiu definições de tabelas, relações e campos necessários para suportar as funcionalidades do aplicativo.

##2. Desenvolvimento do Backend em Java 
-   Implementação de um backend em Java para estabelecr a conexão com o banco de dados PostgresSQL.
-   Foram desenvolvidas entidades em Java que correspondem diretamente às tabelas definidas no modelo do banco de dados.

##3. Criação de Dados Fictícios
-   Utilização da dependência do Faker para gerar dados fictícios de maneira realista.
-   Os dados fictícios foram utilizados para popular as tabelas do banco de dados durante o desenvolvimento e os testes iniciais.

##4. Geração e Utilização de Scripts SQL
-   Foram gerados scripts SQL a partir das entidades Java desenvolvidas.
-   Os scripts SQL incluem comandos para criar as tabelas necessárias e inserir os dados fictícios gerados anteriormente.
-   Esses scripts foram consolidados em um arquivo SQL único para facilitar a inicialização e a configuração do banco de dados em diferentes ambientes.

##5. Configuração dos Ambientes com Docker
-   Na configuração dos ambientes de desenvolvimento, teste e produção, foi utilizado Docker para garantir a consistência e a portabilidade do ambiente.
-   PostgreSQL foi escolhido como o banco de dados principal para integração com os serviços de Airflow e Minio.
-   Um script de inicialização foi preparado para automatizar a criação das tabelas e a inserção dos dados no PostgreSQL, garantindo que o banco de dados esteja pronto para uso imediato nos ambientes Docker.
-   Utilizou-se um volume inicial para disponibilizar o script de inicialização, simplificando o processo de configuração e garantindo a replicabilidade do ambiente.