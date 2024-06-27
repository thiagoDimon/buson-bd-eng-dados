# Configurações Airflow e Minio

- Acessar a seguinte url para realizar a execução das DAGS do Airflow: http://localhost:8080/
- Usuário e senha de acesso ao airflow: admin e admin
- Configurar a conexão com o cluster do spark na guia admin e posteriormente em conections


# Váriaveis de Ambiente Minio
- Acessar o minio na porta 9001 usuario: minioadmin senha: minioadmin
- Ir em AccessKeys e criar uma nova, copiar o accessKey e o accessSecretKey e colocar no dotenv do projeto.
Apos isso reiniciar o ambiente com o comando 
```
astro dev restart
```