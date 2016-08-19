-- DROP SEQUENCE public."ATENDIMENTO_id_seq";

CREATE SEQUENCE public."ATENDIMENTO_id_seq"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 7
  CACHE 1;
ALTER TABLE public."ATENDIMENTO_id_seq"
  OWNER TO "sac";


-- DROP SEQUENCE public."ATENDIMENTO_id_seq";

CREATE SEQUENCE public."ATENDIMENTO_id_seq"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 7
  CACHE 1;
ALTER TABLE public."ATENDIMENTO_id_seq"
  OWNER TO "sac";


-- Table: public."ESTADO"

-- DROP TABLE public."ESTADO";

CREATE TABLE public."ESTADO"
(
  sigla character varying(2) NOT NULL, -- Sigla dos estados
  nome character varying(50), -- Nomes dos estados
  CONSTRAINT pk_sigla PRIMARY KEY (sigla)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."ESTADO"
  OWNER TO "sac";
COMMENT ON TABLE public."ESTADO"
  IS 'Tabela que traz os estados da federação';
COMMENT ON COLUMN public."ESTADO".sigla IS 'Sigla dos estados';
COMMENT ON COLUMN public."ESTADO".nome IS 'Nomes dos estados';

-- Table: public."ATENDIMENTO"

-- DROP TABLE public."ATENDIMENTO";

CREATE TABLE public."ATENDIMENTO"
(
  id integer NOT NULL DEFAULT nextval('"ATENDIMENTO_id_seq"'::regclass), -- Identificador único dos atendimentos registrados
  tipo character(1) NOT NULL DEFAULT 'T'::bpchar, -- Tipo do atendimento. Se foi telefone (T), email (E) ou chat (C)
  estado character varying(2), -- sigla do estado de onde se originou o chamado
  motivo character(1) NOT NULL DEFAULT 'D'::bpchar, -- Motivo do chamado. Se dúivda (D), elogio (E) ou sugestão (S)
  detalhes character varying(500), -- Campo livre (opcional) para o atendente registrar detalhes do atendimento.
  data date, -- Data do atendimento
  CONSTRAINT pk_id PRIMARY KEY (id),
  CONSTRAINT fk_estado FOREIGN KEY (estado)
      REFERENCES public."ESTADO" (sigla) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ck_motivo CHECK (motivo = ANY (ARRAY['D'::bpchar, 'E'::bpchar, 'S'::bpchar])),
  CONSTRAINT ck_tipo CHECK (tipo = ANY (ARRAY['T'::bpchar, 'E'::bpchar, 'C'::bpchar]))
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."ATENDIMENTO"
  OWNER TO "sac";
COMMENT ON TABLE public."ATENDIMENTO"
  IS 'Tabela para registrar os atendimentos do sistema';
COMMENT ON COLUMN public."ATENDIMENTO".id IS 'Identificador único dos atendimentos registrados';
COMMENT ON COLUMN public."ATENDIMENTO".tipo IS 'Tipo do atendimento. Se foi telefone (T), email (E) ou chat (C)';
COMMENT ON COLUMN public."ATENDIMENTO".estado IS 'sigla do estado de onde se originou o chamado';
COMMENT ON COLUMN public."ATENDIMENTO".motivo IS 'Motivo do chamado. Se dúivda (D), elogio (E) ou sugestão (S)';
COMMENT ON COLUMN public."ATENDIMENTO".detalhes IS 'Campo livre (opcional) para o atendente registrar detalhes do atendimento. ';
COMMENT ON COLUMN public."ATENDIMENTO".data IS 'Data do atendimento';

