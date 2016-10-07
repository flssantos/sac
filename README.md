# sac
Projeto simulando SAC para a OLX

Prezados, segue minha implementação para o Desafio SAC I descrito em https://drive.google.com/drive/folders/0BzshhR3001TnOHZEaDVJYzZMblU

Algumas observações:
- Projeto criado com JSF 2.3, Hibernate 3.6.2 e DBUnit 2.4.8. Executado em apache-tomcat 7.0.70 através de plugin no Eclipse Neon. Testado no Chrome 52.0.2743.116 m.
- Criados dois projetos: SAC contendo o projeto principal e SACtest contendo os testes automatizados
- Criadas duas bases no PostgreSQL, na porta 5433: SAC_BD (base principal) e SAC_TESTE (base de testes automatizados). Em SAC_BD, o user é "sac" e em SAC_TESTE é "sacteste". ambas as bases tem o mesmo password: sacsenha. Configurações no projeto SAC se encontram em SAC/src/hibernate.cfg.xml e no projeto SACtest em SACtest/src/hibernate-test.cfg.xml
- Os scripts de criação de base (DDL) e carga (DML) de ambos os projetos se apresentam na pasta BD do projeto SAC
- Bibliotecas em SACtest\lib devem ser adicionadas como bibliotecas externas no projeto SACtest
- Projeto não foi focado no design, nem em CRUD de objetos (não solicitado no desafio).
- Entidade estado criada não é o foco da solução, ela apenas serve de apoio para a entidade principal do sistema: Atendimento. Portanto não há funcionalidades para esta entidade no sistema. Somente uma carga inicial em banco.
