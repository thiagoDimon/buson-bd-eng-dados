DROP TABLE IF EXISTS public.associacaos;
CREATE TABLE public.associacaos (
    id serial PRIMARY KEY,
    cnpj int8,
    nome varchar(255) NOT NULL,
    endereco varchar(255) NOT NULL,
    situacao varchar(255) NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL
);

DROP TABLE IF EXISTS public.instituicaos;
CREATE TABLE public.instituicaos (
    id serial PRIMARY KEY,
    nome varchar(255) NOT NULL,
    endereco varchar(255) NOT NULL,
    situacao varchar(255) NOT NULL,
    associacao_id int4 NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT instituicaos_associacao_id_fkey FOREIGN KEY (associacao_id) REFERENCES public.associacaos(id)
);

DROP TABLE IF EXISTS public.cursos;
CREATE TABLE public.cursos (
    id serial PRIMARY KEY,
    nome varchar(255) NOT NULL,
    situacao varchar(255) NOT NULL,
    instituicao_id int4 NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT cursos_instituicao_id_fkey FOREIGN KEY (instituicao_id) REFERENCES public.instituicaos(id) ON UPDATE CASCADE
);

DROP TABLE IF EXISTS public.usuarios;
CREATE TABLE public.usuarios (
    id serial PRIMARY KEY,
    nome varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    telefone varchar(255) NOT NULL,
    endereco varchar(255),
    matricula varchar(255),
    curso_id int4,
    associacao_id int4,
    tipo_acesso varchar(255) NOT NULL,
    senha varchar(255) NOT NULL,
    situacao varchar(255) NOT NULL,
	dias_uso_transporte varchar(255) NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT usuarios_email_key UNIQUE (email),
    CONSTRAINT usuarios_associacao_id_fkey FOREIGN KEY (associacao_id) REFERENCES public.associacaos(id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT usuarios_curso_id_fkey FOREIGN KEY (curso_id) REFERENCES public.cursos(id) ON DELETE SET NULL ON UPDATE CASCADE
);

DROP TABLE IF EXISTS public.parametros;
CREATE TABLE public.parametros (
    id serial PRIMARY KEY,
    associacao_id int4 NOT NULL,
    valor1 int4 NOT NULL,
    valor2 int4 NOT NULL,
    valor3 int4 NOT NULL,
    valor4 int4 NOT NULL,
    valor5 int4 NOT NULL,
    valor6 int4 NOT NULL,
    valor_multa int4 NOT NULL,
    dia_vencimento int4 NOT NULL,
    dia_abertura_pagamentos int4 NOT NULL,
    dias_tolerancia_multa int4 NOT NULL,
    libera_alteracao_dados_pessoais varchar(255) NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT parametros_associacao_id_fkey FOREIGN KEY (associacao_id) REFERENCES public.associacaos(id)
);

DROP TABLE IF EXISTS public.pagamentos;
CREATE TABLE public.pagamentos (
    id serial PRIMARY KEY,
    usuario_id int4 NOT NULL,
    tipo varchar(255) NOT NULL,
    valor int4 NOT NULL,
    multa int4,
    data_vencimento timestamptz,
    data_pagamento timestamptz,
    situacao varchar(255) NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT pagamentos_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id)
);