-- public.assistants definition

-- Drop table

-- DROP TABLE public.assistants;

CREATE TABLE public.assistants (
	id serial DEFAULT nextval('assistants_id_seq'::regclass) NOT NULL,
	api_id varchar(255) NOT NULL,
	created_at timestamptz NOT NULL,
	"name" varchar(255) NOT NULL,
	description varchar(255),
	model varchar(255) NOT NULL,
	instructions varchar(255) NOT NULL,
	situacao enum_assistants_situacao NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT assistants_pkey PRIMARY KEY (id)
);

-- public.threads definition

-- Drop table

-- DROP TABLE public.threads;

CREATE TABLE public.threads (
	id serial DEFAULT nextval('threads_id_seq'::regclass) NOT NULL,
	api_id varchar(255) NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT threads_pkey PRIMARY KEY (id)
);

-- public.pix_apis definition

-- Drop table

-- DROP TABLE public.pix_apis;

CREATE TABLE public.pix_apis (
	id serial DEFAULT nextval('pix_apis_id_seq'::regclass) NOT NULL,
	dev_api_key int4 NOT NULL,
	client_id int4 NOT NULL,
	client_secret int4 NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT pix_apis_pkey PRIMARY KEY (id)
);

-- public.associacaos definition

-- Drop table

-- DROP TABLE public.associacaos;

CREATE TABLE public.associacaos (
	id serial DEFAULT nextval('associacaos_id_seq'::regclass) NOT NULL,
	cnpj int8,
	nome varchar(255) NOT NULL,
	endereco varchar(255) NOT NULL,
	situacao enum_associacaos_situacao NOT NULL,
	pix_api_id int4,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT associacaos_pkey PRIMARY KEY (id),
	CONSTRAINT associacaos_pix_api_id_fkey FOREIGN KEY (pix_api_id) REFERENCES public.pix_apis(id)
);

-- public.instituicaos definition

-- Drop table

-- DROP TABLE public.instituicaos;

CREATE TABLE public.instituicaos (
	id serial DEFAULT nextval('instituicaos_id_seq'::regclass) NOT NULL,
	nome varchar(255) NOT NULL,
	endereco varchar(255) NOT NULL,
	situacao enum_instituicaos_situacao NOT NULL,
	associacao_id int4 NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT instituicaos_pkey PRIMARY KEY (id),
	CONSTRAINT instituicaos_associacao_id_fkey FOREIGN KEY (associacao_id) REFERENCES public.associacaos(id)
);

-- public.cursos definition

-- Drop table

-- DROP TABLE public.cursos;

CREATE TABLE public.cursos (
	id serial DEFAULT nextval('cursos_id_seq'::regclass) NOT NULL,
	nome varchar(255) NOT NULL,
	situacao enum_cursos_situacao NOT NULL,
	instituicao_id int4 NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT cursos_pkey PRIMARY KEY (id),
	CONSTRAINT cursos_instituicao_id_fkey FOREIGN KEY (instituicao_id) REFERENCES public.instituicaos(id) ON UPDATE CASCADE
);

-- public.usuarios definition

-- Drop table

-- DROP TABLE public.usuarios;

CREATE TABLE public.usuarios (
	id serial DEFAULT nextval('usuarios_id_seq'::regclass) NOT NULL,
	nome varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	telefone varchar(255) NOT NULL,
	endereco varchar(255),
	matricula varchar(255),
	curso_id int4,
	associacao_id int4,
	tipo_acesso enum_usuarios_tipo_acesso NOT NULL,
	senha varchar(255) NOT NULL,
	situacao enum_usuarios_situacao NOT NULL,
	dias_uso_transporte _enum_usuarios_dias_uso_transporte NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT usuarios_pkey PRIMARY KEY (id),
	CONSTRAINT usuarios_associacao_id_fkey FOREIGN KEY (associacao_id) REFERENCES public.associacaos(id) ON DELETE SET NULL ON UPDATE CASCADE,
	CONSTRAINT usuarios_curso_id_fkey FOREIGN KEY (curso_id) REFERENCES public.cursos(id) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE UNIQUE INDEX usuarios_email_key ON public.usuarios (email);

-- public.parametros definition

-- Drop table

-- DROP TABLE public.parametros;

CREATE TABLE public.parametros (
	id serial DEFAULT nextval('parametros_id_seq'::regclass) NOT NULL,
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
	libera_alteracao_dados_pessoais enum_parametros_libera_alteracao_dados_pessoais NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT parametros_pkey PRIMARY KEY (id),
	CONSTRAINT parametros_associacao_id_fkey FOREIGN KEY (associacao_id) REFERENCES public.associacaos(id)
);
CREATE UNIQUE INDEX parametros_associacao_id_key ON public.parametros (associacao_id);

-- public.token_autenticacaos definition

-- Drop table

-- DROP TABLE public.token_autenticacaos;

CREATE TABLE public.token_autenticacaos (
	id serial DEFAULT nextval('token_autenticacaos_id_seq'::regclass) NOT NULL,
	usuario_id int4 NOT NULL,
	"token" varchar(255) NOT NULL,
	data_validade timestamptz NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT token_autenticacaos_pkey PRIMARY KEY (id)
);

-- public.pagamentos definition

-- Drop table

-- DROP TABLE public.pagamentos;

CREATE TABLE public.pagamentos (
	id serial DEFAULT nextval('pagamentos_id_seq'::regclass) NOT NULL,
	tx_id varchar(255),
	pix_copia_cola varchar(255),
	usuario_id int4 NOT NULL,
	tipo enum_pagamentos_tipo NOT NULL,
	valor int4 NOT NULL,
	multa int4,
	data_vencimento timestamptz,
	data_pagamento timestamptz,
	situacao enum_pagamentos_situacao NOT NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT pagamentos_pkey PRIMARY KEY (id),
	CONSTRAINT pagamentos_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id)
);

-- public.imagems definition

-- Drop table

-- DROP TABLE public.imagems;

CREATE TABLE public.imagems (
	id serial DEFAULT nextval('imagems_id_seq'::regclass) NOT NULL,
	imagem bytea,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT imagems_pkey PRIMARY KEY (id)
);