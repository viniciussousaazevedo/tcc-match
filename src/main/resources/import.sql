insert into area_estudo (ID,NOME) values (1,'Engenharia de Software');

insert into area_estudo (ID,NOME) values (2,'Inteligência Artificial');

insert into area_estudo (ID,NOME) values (3,'Sistemas de Informação Geográfica');

insert into area_estudo (ID,NOME) values (4,'Bancos de dados');

insert into area_estudo (ID,NOME) values (5,'Percepcao Computacional');

insert into usuario (DTYPE, ID, EMAIL, IS_DISPONIVEL, LABORATORIOS, NOME, SENHA) values ('Professor', 1,'campelo@dsc.ufcg.edu.br',TRUE,'LACINA','Claudio Campelo', '123');

insert into usuario (DTYPE, ID, EMAIL, IS_DISPONIVEL, LABORATORIOS, NOME, SENHA) values ('Professor', 2,'eanes@dsc.ufcg.edu.br',TRUE,'LPC','Eanes Torres', '123');

insert into usuario (DTYPE, ID, EMAIL, IS_DISPONIVEL, LABORATORIOS, NOME, SENHA) values ('Professor', 3,'everton@dsc.ufcg.edu.br',TRUE,'','Everton Leandro', '123');

insert into professor_area (PROFESSOR_ID,AREA_ID) values (1,3);

insert into professor_area (PROFESSOR_ID,AREA_ID) values (1,4);

insert into professor_area (PROFESSOR_ID,AREA_ID) values (2,2);

insert into professor_area (PROFESSOR_ID,AREA_ID) values (2,5);

insert into professor_area (PROFESSOR_ID,AREA_ID) values (3,1);

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 4,'joao@ccc.ufcg.edu.br',111111111,'Joao Zezin','2099.3', '123');

insert into usuario(DTYPE, ID, NOME, EMAIL, SENHA) values ('Coordenador', 5, 'Francisco Brasileiro', 'fubica@computacao.ufcg.edu.br', '123');

insert into sessao (ID, STATUS, USUARIO_ID) values (1, 'NAO_LOGADO', null);

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 6,'pedro@ccc.ufcg.edu.br',123456789,'Pedro Alves','2021.2', '123');

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 7,'lucas@ccc.ufcg.edu.br',132456789,'Lucas Henrique','2021.1', '123');

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 8,'antonio@ccc.ufcg.edu.br',143256789,'Antonio Pablo','2021.2', '123');

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 9,'maria@ccc.ufcg.edu.br',154326789,'Maria Brandão','2021.2', '123');

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 10,'luiza@ccc.ufcg.edu.br',162345789,'Luíza Melo','2022.1', '123');

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 11,'chiquinha@ccc.ufcg.edu.br',176234589,'Chiquinha Renata','2021.2', '123');

insert into usuario (DTYPE, ID,EMAIL,MATRICULA,NOME,PERIODO_PARA_CONCLUSAO, SENHA) values ('Aluno', 12,'luiz@ccc.ufcg.edu.br',18234569,'Luiz Pablo','2022.1', '123');

insert into aluno_area (ALUNO_ID,AREA_ID) values (6,1);

insert into tema (ID, TITULO, DESCRICAO, SEMESTRE, STATUS, AUTOR, ORIENTADOR_ID, ORIENTANDO_ID) values (1, 'Aspectos de segurança em modelos relacionais', 'um tema bem legal', '2021.1','ACEITO', 'COORDENADOR', 1, 4);

insert into tema (ID, TITULO, DESCRICAO, SEMESTRE, STATUS, AUTOR ,ORIENTADOR_ID, ORIENTANDO_ID) values (2, 'Modelo de IA baseado em redes neurais' , 'um tema bem legal','2020.2','FINALIZADO', 'ALUNO', 2, 7);

insert into tema (ID, TITULO, DESCRICAO, SEMESTRE, STATUS, AUTOR ,ORIENTADOR_ID, ORIENTANDO_ID) values (3, 'Aplicacao de Visao Computacional para análise textual' , 'um tema bem legal','2020.2','FINALIZADO', 'PROFESSOR', 2, 6);

insert into tema (ID, TITULO, DESCRICAO, SEMESTRE, STATUS, AUTOR, ORIENTADOR_ID, ORIENTANDO_ID) values (4, 'Estudo de caso - Funcionamento do GPS', 'um tema bem legal', '2021.1','PENDENTE', 'PROFESSOR', 1, null);

insert into tema (ID, TITULO, DESCRICAO, SEMESTRE, STATUS, AUTOR, ORIENTADOR_ID, ORIENTANDO_ID) values (5, 'Bubble Sort e suas aplicações', 'um tema sem graça', '2021.1','PENDENTE', 'ALUNO', null, 9);

insert into tema (ID, TITULO, DESCRICAO, SEMESTRE, STATUS, AUTOR, ORIENTADOR_ID, ORIENTANDO_ID) values (6, 'Importância da análise de qualidade em aplicações', 'um tema bem legal', '2021.1','PENDENTE', 'ALUNO', null, 10);

insert into tema (ID, TITULO, DESCRICAO, SEMESTRE, STATUS, AUTOR, ORIENTADOR_ID, ORIENTANDO_ID) values (7, 'Uma abordagem de desenvolvimento baseada em TDD', 'um tema bem legal', '2021.1','PENDENTE', 'ALUNO', null, 11);

insert into tema_area (TEMA_ID,AREA_ID) values (1,4);

insert into tema_area (TEMA_ID,AREA_ID) values (2,2);

insert into tema_area (TEMA_ID,AREA_ID) values (2,5);

insert into tema_area (TEMA_ID,AREA_ID) values (3,5);

insert into tema_area (TEMA_ID,AREA_ID) values (4,3);

insert into tema_area (TEMA_ID,AREA_ID) values (6,1);

insert into tema_area (TEMA_ID,AREA_ID) values (7,1);

insert into problema_orientacao (ID,AUTOR,DESCRICAO_PROBLEMA) values (1, 'PROFESSOR' , 'Estou tendo problemas quanto a disponibilidade do aluno para conversar sobre o TCC, e falta de interesse do mesmo');

insert into problema_orientacao (ID,AUTOR,DESCRICAO_PROBLEMA) values (2, 'PROFESSOR' , 'O aluno demora tempo demais para responder meus emails, ficando difícil acompanhar a situação atual do trabalho');

insert into problema_orientacao (ID,AUTOR,DESCRICAO_PROBLEMA) values (3, 'ALUNO' , 'Meu orientador é uma pessoa de difícil acesso, e por isso nos reunimos poucas vezes para que eu tire dúvidas');

insert into problema_orientacao (ID,AUTOR,DESCRICAO_PROBLEMA) values (4, 'ALUNO' , 'Meu orientador me manda excesso de artigos para ler , em vez de ajudar a direcionar minha pesquisa para resolver o problema atual que estou tendo');

insert into solicitacao_orientacao(ID, RESPOSTA_SOLICITACAO, STATUS, ORIENTADOR_ID,	SOLICITANTE_ID,	TEMA_ID) values (1, null, 'PENDENTE', 3, 11, 7);
