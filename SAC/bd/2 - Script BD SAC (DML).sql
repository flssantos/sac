
INSERT INTO public."ESTADO"(
            sigla, nome)
    VALUES ('RJ', 'Rio de Janeiro');

INSERT INTO public."ESTADO"(
            sigla, nome)
    VALUES ('SP', 'S�o Paulo');

INSERT INTO public."ESTADO"(
            sigla, nome)
    VALUES ('MG', 'Minas Gerais');

INSERT INTO public."ATENDIMENTO"(
            estado, detalhes, data)
    VALUES ( 'RJ', 'N/D', to_date('08/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            estado, detalhes, data)
    VALUES ( 'SP', '', to_date('12/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            estado, detalhes, data)
    VALUES ( 'MG', 'N�o informado', to_date('12/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'C', 'SP', 'S', 'Problemas ao preencher formul�rio', to_date('12/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'E', 'RJ', 'E', 'Agradecendo a venda', to_date('12/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'E', 'MG', 'S', 'Problemas na venda', current_date);

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'C', 'RJ', 'S', 'Problemas na venda. Cliente afirma que usou o site, mas em determinado momento a p�gina demorou a responder com isso, ao usar o bot�o back do navegador, as informa��es do formul�rio foram perdidas', current_date);