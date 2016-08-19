
INSERT INTO public."ESTADO"(
            sigla, nome)
    VALUES ('RJ', 'Rio de Janeiro');

INSERT INTO public."ESTADO"(
            sigla, nome)
    VALUES ('SP', 'São Paulo');

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
    VALUES ( 'MG', 'Não informado', to_date('12/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'C', 'SP', 'S', 'Problemas ao preencher formulário', to_date('12/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'E', 'RJ', 'E', 'Agradecendo a venda', to_date('12/08/2016', 'DD/MM/YYYY'));

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'E', 'MG', 'S', 'Problemas na venda', current_date);

INSERT INTO public."ATENDIMENTO"(
            tipo, estado, motivo, detalhes, data)
    VALUES ( 'C', 'RJ', 'S', 'Problemas na venda. Cliente afirma que usou o site, mas em determinado momento a página demorou a responder com isso, ao usar o botão back do navegador, as informações do formulário foram perdidas', current_date);