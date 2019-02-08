## Executando o projeto
A unica dependência para executar o projeto e o mongodb, utilizei no docker. Abaixo o comando para docker:
```
docker run -d -p 27017:27017 mongo
```
Apos subir o banco, iniciar a app
```
mvn spring-boot:run
```
# Técnicas
1- TDD (Test Driven Development)
1- DDD (Domain Driven Development)

# Ferramentas
1- Java
2- Spring
3- MongoDB

# Fluxo da aplicação
http://localhost:8080/swagger-ui.html

1- Cadastrar um novo eleitor
POST / voter
*nome *cpf

2- Cadastrar nova pauta
POST /pauta

3- Iniciar sessão de votação
POST /schedule/{idSchedule}/session-voting

4- Registrar o voto
POST /schedule/{idSchedule}

5- Contabilizar votos
GET /schedule/{idSchedule}

Apenas as informações especificas da sessão de votos:
GET /schedule/{idSchedule}/session-voting

6- Listagem paginada de Pautas
GET /schedule

Obs? 10 registros por pag
