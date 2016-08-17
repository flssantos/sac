# sac
Projeto simulando SAC para a OLX

Prezados, segue minha implementação para o Desafio SAC I descrito em https://drive.google.com/drive/folders/0BzshhR3001TnOHZEaDVJYzZMblU

Algumas observações:
- Projeto criado com JSF, Hibernate e DBUnit. Executado em apache-tomcat 7.0.70. Testado no Chrome.
- Criadas duas bases no PostgreSQL, na porta 5433: SAC_BD (base principal) e SAC_TESTE (base de testes automatizados). Em SAC_BD, o user é "sac" e em SAC_TESTE é "sacteste". ambas as bases tem o mesmo password: sacsenha. Configurações no projeto SAC se encontram em SAC/src/hibernate.cfg.xml
- Os scripts de criação de base (script 1) e carga (script 2) se apresentam na pasta BD do projeto SAC
- O script 1 (de DDL) deve ser executado nas duas bases, mas o script 2 (de DML) deve ser executado somente na base principal (SAC_BD)
- Projeto não foi focado no design, nem em CRUD de objetos (não solicitado no desafio).
- Entidade estado criada não é o foco da solução, ela apenas serve de apoio para a entidade principal do sistema: Atendimento. Portanto não há funcionalidades para esta entidade no sistema. Somente uma carga inicial em banco.
