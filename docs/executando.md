# Executando o projeto

- Executar as seguintes DAGS na ordem aqui fornecida: 

## buckets.py
 O MinIO Buckets realiza a criação dos Buckets "landing-zone", "bronze", "silver" e "gold" no Object Storage.
Dessa forma, os dados vão ser persistidos após manipulados.
 ```
 def create_bucket():
        client = Minio(MIN_HOST, access_key=MIN_ACCESS_KEY, secret_key=MIN_SECRET_KEY, secure=False)
        minio_buckets = ["landing-zone", "bronze", "silver", "gold"]
        for bucket in minio_buckets:
            client.make_bucket(bucket)

    create_bucket()

 ```
## landing-zone.py
 Cria conexão com o Postgres e extrai os dados em formato CSV. 
 ```
 file_names = ['./dags/csv/associacaos.csv', './dags/csv/instituicaos.csv', './dags/csv/cursos.csv', './dags/csv/usuarios.csv', './dags/csv/parametros.csv', './dags/csv/pagamentos.csv']

        for file_name in file_names:
            with open(file_name, 'w') as f:
                pass

        conn = psycopg2.connect(
            dbname="postgres",
            user="postgres",
            password="postgres",
            host="host.docker.internal",
            port="5432"
        )

        cur = conn.cursor()

        # Função para exportar dados para CSV
        def export_to_csv(table_name, file_name):
            with open(file_name, 'w') as f:
                cur.copy_expert(f'COPY {table_name} TO STDOUT WITH CSV HEADER', f)

        # Exportando as tabelas para arquivos CSV
        export_to_csv('associacaos', './dags/csv/associacaos.csv')
        export_to_csv('instituicaos', './dags/csv/instituicaos.csv')
        export_to_csv('cursos', './dags/csv/cursos.csv')
        export_to_csv('usuarios', './dags/csv/usuarios.csv')
        export_to_csv('parametros', './dags/csv/parametros.csv')
        export_to_csv('pagamentos', './dags/csv/pagamentos.csv')

        # Fechando o cursor e a conexão
        cur.close()
        conn.close()
 ```
 Por ser a Landing-Zone, esses dados serão persistidos em seu formato bruto, para serem manipulados nas camadas seguintes.
 ```
 # Bucket | Nome do Arquivo | Caminho do Arquivo
        client.fput_object('landing-zone', 'associacaos.csv', './dags/csv/associacaos.csv')
        client.fput_object('landing-zone', 'instituicaos.csv', './dags/csv/instituicaos.csv')
        client.fput_object('landing-zone', 'cursos.csv', './dags/csv/cursos.csv')
        client.fput_object('landing-zone', 'usuarios.csv', './dags/csv/usuarios.csv')
        client.fput_object('landing-zone', 'parametros.csv', './dags/csv/parametros.csv')
        client.fput_object('landing-zone', 'pagamentos.csv', './dags/csv/pagamentos.csv')
 ```
## bronze.py 
 A camada Bronze utiliza Spark para ler e manipular os dados da landing-zone.
 ```
 # Lendo o arquivo do bucket landing-zone
    df_associacaos = spark.read.option("inferSchema", "true").option("header", "true").csv(f"s3a://landing-zone/associacaos.csv") 
    df_cursos = spark.read.option("inferSchema", "true").option("header", "true").csv(f"s3a://landing-zone/cursos.csv") 
    df_instituicaos = spark.read.option("inferSchema", "true").option("header", "true").csv(f"s3a://landing-zone/instituicaos.csv") 
    df_pagamentos = spark.read.option("inferSchema", "true").option("header", "true").csv(f"s3a://landing-zone/pagamentos.csv") 
    df_parametros = spark.read.option("inferSchema", "true").option("header", "true").csv(f"s3a://landing-zone/parametros.csv") 
    df_usuarios = spark.read.option("inferSchema", "true").option("header", "true").csv(f"s3a://landing-zone/usuarios.csv") 

 ```
 Para cada arquivo CSV, serão adicionadas novas colunas de metadados, marcando o momento do processamento, 
 e o nome do arquivo de origem.
 ```
 # Adicionando metadados de data e hora de processamento e nome do arquivo de origem
    df_associacaos = df_associacaos.withColumn("data_hora_bronze", current_timestamp()).withColumn("nome_arquivo", lit("associacaos.csv"))
    df_cursos = df_cursos.withColumn("data_hora_bronze", current_timestamp()).withColumn("nome_arquivo", lit("cursos.csv"))
    df_instituicaos = df_instituicaos.withColumn("data_hora_bronze", current_timestamp()).withColumn("nome_arquivo", lit("instituicaos.csv"))
    df_pagamentos = df_pagamentos.withColumn("data_hora_bronze", current_timestamp()).withColumn("nome_arquivo", lit("pagamentos.csv"))
    df_parametros = df_parametros.withColumn("data_hora_bronze", current_timestamp()).withColumn("nome_arquivo", lit("parametros.csv"))
    df_usuarios = df_usuarios.withColumn("data_hora_bronze", current_timestamp()).withColumn("nome_arquivo", lit("usuarios.csv"))
 ```
 Após esse processo, os dados são persistidos na camada Bronze, já em formato Delta.
 ```
 # Salvando arquivo em formato delta no bucket bronze
    df_associacaos.write.format("delta").save(f"s3a://bronze/associacaos")
    df_cursos.write.format("delta").save(f"s3a://bronze/cursos")
    df_instituicaos.write.format("delta").save(f"s3a://bronze/instituicaos")
    df_pagamentos.write.format("delta").save(f"s3a://bronze/pagamentos")
    df_parametros.write.format("delta").save(f"s3a://bronze/parametros")
    df_usuarios.write.format("delta").save(f"s3a://bronze/usuarios")
 ```
## silver.py
 A camada Silver utiliza Spark para ler e manipular os dados da camada Bronze.
 ```
 spark = SparkSession.builder.config(conf=conf).getOrCreate()

 # Lendo os arquivos da camada bronze
    df_associacaos = spark.read.format("delta").load(f"s3a://bronze/associacaos/")
    df_cursos = spark.read.format("delta").load(f"s3a://bronze/cursos/")
    df_instituicaos = spark.read.format("delta").load(f"s3a://bronze/instituicaos/")
    df_pagamentos = spark.read.format("delta").load(f"s3a://bronze/pagamentos/")
    df_parametros = spark.read.format("delta").load(f"s3a://bronze/parametros/")
    df_usuarios = spark.read.format("delta").load(f"s3a://bronze/usuarios/")
 ```
 Criação dos metadados (colunas) referentes a data de processamento dos dados, e nome do arquivo de origem.
 ```
 # Adicionando metadados de data e hora de processamento e nome do arquivo de origem
    df_associacaos = df_associacaos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("associacaos"))
    df_cursos = df_cursos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("cursos"))
    df_instituicaos = df_instituicaos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("instituicaos"))
    df_pagamentos = df_pagamentos.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("pagamentos"))
    df_parametros = df_parametros.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("parametros"))
    df_usuarios = df_usuarios.withColumn("data_hora_silver", current_timestamp()).withColumn("nome_arquivo", lit("usuarios"))
 ```
 Após a criação dos metadados, é feita padronização dos nomes das tabelas, e exclusão de tabelas desnecessárias.
 ```
 df_cursos = (
    df_cursos
    .withColumnRenamed("id"                     , "ID")
    .withColumnRenamed("nome"                   , "NOME")
    .withColumnRenamed("situacao"               , "SITUACAO")
    .withColumnRenamed("instituicao_id"         , "INSTITUICAO_ID")
    .drop("created_at")
    .drop("updated_at")
    .drop("data_hora_bronze")
    .withColumnRenamed("nome_arquivo"           , "NOME_ARQUIVO")
    .withColumnRenamed("data_hora_silver"       , "DATA_HORA_SILVER")
    )
 ```
 As novas modificações serão persistidas na camada Silver, já no formato Delta.
 ```
 # Salvando os arquivos na camada silver
    df_associacaos.write.format("delta").save(f"s3a://silver/associacaos/")
    df_cursos.write.format("delta").save(f"s3a://silver/cursos/")
    df_instituicaos.write.format("delta").save(f"s3a://silver/instituicaos/")
    df_pagamentos.write.format("delta").save(f"s3a://silver/pagamentos/")
    df_parametros.write.format("delta").save(f"s3a://silver/parametros/")
    df_usuarios.write.format("delta").save(f"s3a://silver/usuarios/")
 ```

## gold.py
 A camada Gold utiliza Spark para ler os dados da camada Silver.
 ```
 spark = SparkSession.builder.config(conf=conf).getOrCreate()

    # Lendo os arquivos da camada silver
    df_associacaos = spark.read.format("delta").load(f"s3a://silver/associacaos/")
    df_cursos = spark.read.format("delta").load(f"s3a://silver/cursos/")
    df_instituicaos = spark.read.format("delta").load(f"s3a://silver/instituicaos/")
    df_pagamentos = spark.read.format("delta").load(f"s3a://silver/pagamentos/")
    df_parametros = spark.read.format("delta").load(f"s3a://silver/parametros/")
    df_usuarios = spark.read.format("delta").load(f"s3a://silver/usuarios/") 
 ```
 Serão feitas modificações em colunas específicas dos dataframes, para que não haja ambiguidade no resultado.
 ```
 # Selecionando as colunas necessárias e renomeando as colunas para evitar ambiguidades
    df_associacaos_final = df_associacaos_clone.select("ID", "NOME", "SITUACAO") \
        .withColumnRenamed("ID", "codigo_associacao") \
        .withColumnRenamed("NOME", "nome_associacao") \
        .withColumnRenamed("SITUACAO", "situacao_associacao")

    df_pagamentos_final = df_pagamentos_clone.select("ID", "DATA_VENCIMENTO", "VALOR", "MULTA", "SITUACAO", "USUARIO_ID") \
        .withColumnRenamed("ID", "codigo_pagamento") \
        .withColumnRenamed("DATA_VENCIMENTO", "data_vencimento") \
        .withColumnRenamed("VALOR", "valor") \
        .withColumnRenamed("MULTA", "multa") \
        .withColumnRenamed("SITUACAO", "situacao") \
        .withColumnRenamed("USUARIO_ID", "codigo_usuario")
 ```
 Após manipular as colunas, os dataframes serão agregados em um único dataframe.
 ```
 # Realizando a junção dos dataframes
    df_merged = df_pagamentos_final \
        .join(df_usuarios_final, df_pagamentos_final["codigo_usuario"] == df_usuarios_final["codigo_usuario"], "inner") \
        .join(df_associacaos_final, df_usuarios_final["codigo_associacao"] == df_associacaos_final["codigo_associacao"], "inner") \
        .select(df_pagamentos_final["codigo_pagamento"],
                df_pagamentos_final["data_vencimento"],
                df_pagamentos_final["valor"],
                df_pagamentos_final["multa"],
                df_pagamentos_final["situacao"],
                df_usuarios_final["codigo_usuario"],
                df_usuarios_final["nome_usuario"],
                df_usuarios_final["situacao_usuario"],
                df_usuarios_final["dias_uso_transporte"],
                df_associacaos_final["codigo_associacao"],
                df_associacaos_final["nome_associacao"],
                df_associacaos_final["situacao_associacao"])
 ```
 O resultado da junção dos dataframes será persistido na camada Gold
 ```
 df_merged.write.format("delta").save(f"s3a://gold/modelo_eng_dados/")
 ```

#
#

Nessa parte descrita acima, acredito que pode ser colocado junto os comandos disponiveis nos arquivos, buckets.py, landzone.py, bronze.py, 
silver.py e gold.py, os quais refltem todo o processo descrito acima como exemplificação que estão no caminho astro/dags