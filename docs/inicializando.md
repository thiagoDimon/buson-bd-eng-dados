# Inicializando

## Como subir o ambiente com Airflow, Spark e Minio

### Ferramentas necessárias para a criação do ambiente:

- ### Docker

      > Instale o [Docker](https://www.docker.com/products/docker-desktop/)

- ### Git

      > Instale o [Git](https://git-scm.com/downloads)

- ### Astro CLI

      - Abra o Windows PowerShell em modo Administrador e execute o seguinte comando:

      ```
         winget install -e --id Astronomer.Astro
      ```

      > Documentação [Astro CLI](https://www.astronomer.io/docs/astro/cli/install-cli)

## Clonando o Projeto

- Crie uma pasta no seu Desktop, e navegue até essa pasta através do CMD.
- Após navegar até a pasta, execute o seguinte comando no Terminal para clonar o repositório:

      ```
      git clone https://github.com/thiagoDimon/buson-bd-eng-dados.git
      ```

## Iniciando as Imagens do Container

- Abra o Docker Desktop, e certifique-se de estar logado em uma conta.
- Pelo CMD, navegue até a pasta astro, e execute o seguinte comando no Terminal:

      ```
      astro dev start
      ```

- Agora, basta aguardar (pode demorar um pouco).
