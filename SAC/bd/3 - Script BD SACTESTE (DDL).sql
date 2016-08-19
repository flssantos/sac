-- Sequence: teste."ATENDIMENTO_id_seq"

-- DROP SEQUENCE teste."ATENDIMENTO_id_seq";

CREATE SEQUENCE teste."ATENDIMENTO_id_seq"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE teste."ATENDIMENTO_id_seq"
  OWNER TO sacteste;

-- Table: teste."ESTADO"

-- DROP TABLE teste."ESTADO";

CREATE TABLE teste."ESTADO"
(
  sigla character varying(2) NOT NULL, -- Sigla dos estados
  nome character varying(50), -- Nomes dos estados
  CONSTRAINT pk_sigla PRIMARY KEY (sigla)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE teste."ESTADO"
  OWNER TO sacteste;
COMMENT ON COLUMN teste."ESTADO".sigla IS 'Sigla dos estados';
COMMENT ON COLUMN teste."ESTADO".nome IS 'Nomes dos estados';





-- Table: teste."ATENDIMENTO"

-- DROP TABLE teste."ATENDIMENTO";

-- Table: teste."ATENDIMENTO"

-- DROP TABLE teste."ATENDIMENTO";

CREATE TABLE teste."ATENDIMENTO"
(
  id integer NOT NULL DEFAULT nextval('"ATENDIMENTO_id_seq"'::regclass), -- Identificador único dos atendimentos registrados
  tipo character(1) NOT NULL DEFAULT 'T'::bpchar, -- Tipo do atendimento. Se foi telefone (T), email (E) ou chat (C)
  estado character varying(2), -- sigla do estado de onde se originou o chamado
  motivo character(1) NOT NULL DEFAULT 'D'::bpchar, -- Motivo do chamado. Se dúivda (D), elogio (E) ou sugestão (S)
  detalhes character varying(500), -- Campo livre (opcional) para o atendente registrar detalhes do atendimento.
  data date, -- Data do atendimento
  CONSTRAINT pk_id PRIMARY KEY (id),
  CONSTRAINT fk_estado FOREIGN KEY (estado)
      REFERENCES teste."ESTADO" (sigla) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ck_motivo CHECK (motivo = ANY (ARRAY['D'::bpchar, 'E'::bpchar, 'S'::bpchar])),
  CONSTRAINT ck_tipo CHECK (tipo = ANY (ARRAY['T'::bpchar, 'E'::bpchar, 'C'::bpchar]))
)
WITH (
  OIDS=FALSE
);
ALTER TABLE teste."ATENDIMENTO"
  OWNER TO sacteste;
COMMENT ON TABLE teste."ATENDIMENTO"
  IS 'Tabela para registrar os atendimentos do sistema';
COMMENT ON COLUMN teste."ATENDIMENTO".id IS 'Identificador único dos atendimentos registrados';
COMMENT ON COLUMN teste."ATENDIMENTO".tipo IS 'Tipo do atendimento. Se foi telefone (T), email (E) ou chat (C)';
COMMENT ON COLUMN teste."ATENDIMENTO".estado IS 'sigla do estado de onde se originou o chamado';
COMMENT ON COLUMN teste."ATENDIMENTO".motivo IS 'Motivo do chamado. Se dúivda (D), elogio (E) ou sugestão (S)';
COMMENT ON COLUMN teste."ATENDIMENTO".detalhes IS 'Campo livre (opcional) para o atendente registrar detalhes do atendimento.';
COMMENT ON COLUMN teste."ATENDIMENTO".data IS 'Data do atendimento';

