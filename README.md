# Pós-Tech-FIAP/ALURA-Hackaton

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

# Descrição do projeto
Desenvolver um sistema eficiente para o processamento de pagamentos de operadoras de cartão de crédito. Nosso objetivo principal é receber os dados das transações com cartão de crédito e validar se o cartão do cliente possui limite disponível para a realização da compra. Este sistema garantirá a verificação precisa e em tempo real do limite de crédito dos clientes,proporcionando uma experiência de pagamento segura e confiável..

## Requisitos:
1. Microsserviço de usuários: microsserviço responsável por autenticar um usuário e fornecer um token de acesso, para realizar a comunicação com demais microsserviços. O token deverá ter validade de 2 minutos. 
2. Microsserviço de clientes: microsserviço que permitirá o cadastro do cliente.
3. Microsserviço de cartões de crédito: microsserviço que permitirá o cadastro de cartão de crédito.
4. Microsserviço de registro e consulta de pagamentos: microsserviço que permitirá o cadastro de um pagamento com cartão de crédito, bem como a consulta de pagamentos realizados.

## Entregáveis:
1. Link do Github com o código fonte dos serviços desenvolvidos.
2. Documentação técnica.
3. Um relatório técnico descrevendo as tecnologias e ferramentas utilizadas, os desafios encontrados durante o desenvolvimento e as soluções implementadas para resolvê-las.

# Tecnologias utilizadas
1. Java 17
2. Gradle 7.6
3. Spring Boot 3.3.2
4. Spring Web MVC (compatível com o Spring Boot) 
5. Spring Data JPA (compatível com o Spring Boot)  
6. Spring Bean Validation (compatível com o Spring Boot) 
7. Spring Doc Open API 2.3.0
8. Spring Open Feign 4.1.1
9. Lombok 
10. Postgres 16.3
11. Flyway 
12. JUnit 5
13. Mockito
14. TestContainers
15. Docker
16. WireMock 3.3.1

# Setup do Projeto

Para realizar o setup do projeto é necessário possuir o Java 17, Gradle 7.6, docker 24 e docker-compose 1.29 instalado em sua máquina.
Faca o download do projeto (https://github.com/EvolutionTeamFiapAluraPostech/fiapAluraTechChallengeFase05-microservicos) e atualize suas dependências com o gradle.
Antes de iniciar o projeto é necessário criar o banco de dados. O banco de dados está programado para ser criado em um container. 
Para criar o container, execute o docker-compose (Acesse a pasta raiz do projeto, no mesmo local onde encontra-se o arquivo compose.yaml). Para executá-lo, execute o comando docker-compose up -d (para rodar detached e não prender o terminal). O docker compose irá criar os bancos de dados, buildar a imagem de cada um dos microsserviços, iniciar a aplicação dentro do container correspondente (em sua porta específica). Desta maneira, o conjunto todo da solução estará disponível para ser consumido.
Após a inicialização dos microsserviços, será necessário se autenticar, pois o Spring Security está habilitado. Para tanto, utilize o Postman (ou outra aplicação de sua preferência), crie um endpoint para realizar a autenticação, com a seguinte url **localhost:8080/authenticate**. No body, inclua um json contendo o atributo “email” com o valor “thomas.anderson@itcompany.com” e outro atributo “password” com o valor “@XptoZyB1138”. Realize a requisição para este endpoint para se obter o token JWT que deverá ser utilizado para consumir os demais endpoints do projeto.
Segue abaixo instruções do endpoint para se autenticar na aplicação.

POST /authenticate HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 76

{
"email": "thomas.anderson@itcompany.com",
"password": "@XptoZyB1138”
}

Atenção: Está sendo utilizada uma nova feature do Spring Security 6.3, disponível na versão do Spring Boot a partir da versão 3.3.0. O Spring Security fornece a interface CompromisedPasswordChecker, que por sua vez é implementada através da classe HaveIBeenPwnedRestApiPasswordChecker que é integrada a API https://haveibeenpwned.com/API/v3#PwnedPasswords. Esta API possui uma extensa base de dados de senhas comprometidas e que devem ser evitadas.

# Collection do Postman
* Marcelo-RM350802-Fiap-Alura-Tech Challenge-Fase05.postman_collection.json
* Esta collection está salva na raiz do projeto.

# Environments do Postman
* Marcelo-RM350802-Fiap-Alura-Tech Challenge-Fase05.postman_dev_environment.json
* Estas environments estão salvas na raiz do projeto.

# Documentação da API
* Local dev
  * Microsserviço de Gerenciamento de usuários - http://localhost:8080/swagger-ui/index.html
  * Microsserviço de Gerenciamento de empresas - http://localhost:8081/swagger-ui/index.html
  * Microsserviço de Gerenciamento de produtos - http://localhost:8082/swagger-ui/index.html
  * Microsserviço de Gerenciamento de pedidos - http://localhost:8083/swagger-ui/index.html
  * Microsserviço de Gerenciamento de pagamentos - http://localhost:8084/swagger-ui/index.html

# Documentação do PROJETO
O projeto está dividido em 5 containers de microsserviços backend Java Spring Boot e outros 5 containers de banco de dados Postgresql. Cada um dos microsserviços possui seu respectivo banco de dados.

O backend foi implementado seguindo as recomendações da Clean Architecture, com Clean Code, SOLID e testes automatizados (de unidade e integração), seguindo os princípios do FIRST e Clean Tests. Observação: a Clean Architecuture não foi completamente implementada, visto que os microsserviços são fortemente acomplados com o Spring, entretanto, a aplicação está bem segmentada em pacotes, classes e responsabilidades, seguindo os princípios do DDD.

# Comunicação entre os microsserviços

- O microsserviço de usuários (Users) se comunica com todos os demais microsserviços, através da interface da aplicação, pois este gerencia os usuários e a autenticação através de um token JWT.
- o microsserviço de empresas (Companies) se comunica com a interface da aplicação.
- O microsserviço de produtos/itens (Products) se comunica com a interface da aplicação.
- O microsserviço de pedidos (Orders) se comunica com a interface da aplicação e com os microsserviços de empresas e produtos.
- O microsserviço de pagamentos (Payments) se comunica com a interface da aplicação e com os microsserviços de empresas, pedidos e pagamentos.
- Os containers com banco de dados estão acessíveis apenas por seu respectivo microsserviço.

![alt text](./img/image-24.png)

## Observação
Este projeto de tech challenge está sendo feito por um único desenvolvedor, o tempo de entrega é apertado, assim esta arquitetura foi criada para atender os requisitos mínimos do tech challenge da fase 05, pois esta arquitetura não possui um API Gateway para centralizar as requisições para os microsserviços, não tem um balanceador de carga, um Service Registry, Config Server, nem um Authorization Server para garantir maior segurança com OAuth2/OpenID Connect. Contudo, o microsserviço de pagamento foi implementado com endpoints e banco de dados real. Todos os microsserviços estão com Spring Security fechando o acesso caso não seja utilizado um token JWT na requisição e com autorização para consumir os recursos por role USER/ADMIN. Todos os microsserviços possuem testes de unidade, integração e de design arquitetural (validação de pacotes com o ArchUnit), bem como foram realizados testes manuais com o Postman. Os microsserviços possuem vasta documentação de API com SpringDoc OpenApi, diagramas de camadas da aplicação, bem como diagrama de entidade e relacionamento de cada um dos bancos de dados. Para subir todos os microsserviços foi criado um único docker compose que baixa as imagens necessarias para criar os containers e também constrói (build) cada um dos projetos para facilitar o teste dos nossos avaliadores.

# Microsserviço de gerenciamento de usuários e autenticação na aplicação
O objetivo deste microsserviço é gerenciar os usuários do sistema, ou seja, os consumidores de produtos que realizarão pedidos de compra de algum produto no e-commerce.
  * Microsserviço de gerenciamento de usuários
    * API: 
        * http://localhost:8080/users 
            * Verbo POST - para realizar o cadastro.
                * Escopo: público, não requer autenticação.
                * Regras de negócio:
                    * Atributos name, email, tipo do documento, número do documento e password obrigatórios;
                    * Atributos name e email com o tamanho máximo de 500 caracteres;
                    * Atributo tipo do documento deverá ser CPF ou CNPJ;
                      * Para esta regra, foi criada uma anotação genérica @ValueOfEnum(enumClass = DocNumberType.class), que avalia se o valor do atributo do DTO de entrada é correspondente as opções disponíveis no enum.
                    * Atributo número do documento deverá ser um número válido para CPF ou CNPJ;
                      * Para esta regra, foi criada uma anotação @CPFouCNPJ que realiza a regra de validação do número de CPF ou número de CNPJ conforme o tipo do documento.
                    * Atributo senha com o tamanho mínimo de 8 e máximo de 20 caracteres;
                    * Validação de força da senha, exigindo no mínimo 1 caracter maiúsculo, 1 caracter minúsculo, 1 caracter especial ( @#$%^&+= ) e 1 número;
                    * **Está sendo utilizada uma nova feature do Spring Security 6.3, disponível na versão do Spring Boot a partir da versão 3.3.0. O Spring Security fornece a interface CompromisedPasswordChecker, que por sua vez é implementada através da classe HaveIBeenPwnedRestApiPasswordChecker que é integrada a API https://haveibeenpwned.com/API/v3#PwnedPasswords. Esta API possui uma extensa base de dados de senhas comprometidas e que devem ser evitadas.**
                    * Validação do e-mail único na base de dados;
                    * O atributo password será criptografado antes de ser armazenado no banco de dados.
                * Http response status do endpoint:
                    * Status 201 - Created - cadastro realizado com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 409 - Conflict - se o e-mail ou cpf do usuário já está cadastrado na base de dados.

        * http://localhost:8080/authenticate
            * Verbo POST - para realizar o login na aplicação com um usuário já cadastrado.
                * Escopo: público, não requer autenticação.
                * Regras de negócio:
                    * Atributos email e password obrigatórios;
                    * Atributo senha com o tamanho mínimo de 8 e máximo de 20 caracteres;
                    * Validação de força da senha, exigindo no mínimo 1 caracter maiúsculo, 1 caracter minúsculo, 1 caracter especial ( @#$%^&+= ) e 1 número;
                    * Validação do e-mail já cadastrado na base de dados;
                * Http response status do endpoint:
                    * Status 200 - Ok - usuário autenticado na aplicação;
                    * Status 401 - Unauthorized - se o usuário não foi autenticado;

        * http://localhost:8080/users/{id} 
            * Verbo GET - para realizar a pesquisa de um usuário pelo seu ID.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O usuário será pesquisado por um UUID válido.
                    * O usuário já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - se o usuário foi encontrado por seu ID;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o usuário não foi encontrado por seu ID;

            * Verbo PUT - para realizar a atualização de dados de um usuário pelo seu ID. Necessário informar request body.
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * O usuário será pesquisado por um UUID válido;
                    * O usuário já deve ter sido cadastrado anteriormente;
                    * O email/cpf não pode ser atualizado no usuário se já estiverem sendo utilizados por outro usuário;
                    * Demais regras equivalentes para realizar o cadastro.
                * Http response status do endpoint:
                    * Status 202 - Accepted - atualização realizada com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o usuário não foi encontrado por seu ID para ser atualizado;
                    * Status 409 - Conflict - se o e-mail ou cpf do usuário já está cadastrado na base de dados.

            * Verbo DELETE - para realizar a exclusão (soft delete) de um usuário pelo seu ID.
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * O usuário será pesquisado por um UUID válido.
                    * O usuário já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 204 - No content - exclusão realizada com sucesso;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o usuário não foi encontrado por seu ID para ser excluído;

        * http://localhost:8080/users/name-email
            * Verbo GET - para realizar a pesquisa paginada de um usuário pelo seu nome ou email.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O usuário será pesquisado por um e-mail com formato válido.
                    * O usuário já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - usuário encontrado com sucesso;
                    * Status 200 - Ok - usuário não encontrado, mas com response body com propriedades de paginação, porém com o content vazio;
                    * Status 400 - Bad request - se alguma regra foi violada;


    * Documentação da API: http://localhost:8080/swagger-ui/index.html
    * Banco de dados: http://localhost:5432/users-db

    ![alt text](./img/image-12.png)

    200 testes de integração e unidade, executados em 6 segudos, com 94% de classes e 89% de linhas de código cobertas.
    Para realizar os testes de integração em endpoints autenticados, foi utilizado a anotação @WithMockUser("thomas.anderson@itcompany.com", authorities = {"ADMIN"}), na anotação customizada IntegrationTest.

    ![alt text](./img/image-19.png)

# Microsserviço de gerenciamento de empresas
O objetivo deste microsserviço é gerenciar as empresas fornecedoras de um produto ou serviço para atender seus clientes. A empresa cadastrada será o ponto inicial da rota de entrega do bem para o cliente.
  * Microsserviço de Gerenciamento de empresas 
    * API: 
        * http://localhost:8081/companies 
            * Verbo POST - para realizar o cadastro.
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * Atributos name, email, número do documento, tipo de documento, rua, número, bairro, cidade, Estado, país, e CEP obrigatórios;
                    * Atributos name e email com o tamanho máximo de 500 caracteres;
                    * Atributo docNumber com o tamanho máximo de 14 caracteres; 
                    * Atributo rua com o tamanho máximo de 255 caracteres;
                    * Atributo número, bairro, cidade e país com o tamanho máximo de 100 caracteres;
                    * Atributo Estado com o tamanho máximo de 2 caracteres;
                    * Atributo CEP com o tamanho máximo de 8 caracteres;
                    * Validação do formato do e-mail;
                    * Validação do e-mail único na base de dados;
                    * Validação do número do documento, se é CPF ou CNPJ;
                    * Validação de número de documento único na base de dados.
                * Http response status do endpoint:
                    * Status 201 - Created - cadastro realizado com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 409 - Conflict - se o e-mail ou cpf da empresa já está cadastrado na base de dados.

        * http://localhost:8081/companies/{id} 
            * Verbo GET - para realizar a pesquisa de uma empresa pelo seu ID.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * A empresa será pesquisada por um UUID válido.
                    * A empresa já deve ter sido cadastrada anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - se a empresa foi encontrada por seu ID;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se a empresa não foi encontrada por seu ID;

            * Verbo PUT - para realizar a atualização de dados de uma empresa pelo seu ID. Necessário informar request body.
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * A empresa será pesquisada por um UUID válido.
                    * A empresa já deve ter sido cadastrada anteriormente;
                    * O email/cpf não pode ser atualizado na empresa se já estiverem sendo utilizados por outra empresa;
                    * Demais regras equivalentes para realizar o cadastro.
                * Http response status do endpoint:
                    * Status 202 - Accepted - atualização realizada com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se a empresa não foi encontrada por seu ID para ser atualizada;
                    * Status 409 - Conflict - se o e-mail ou cpf da empresa já está cadastrado na base de dados.

            * Verbo DELETE - para realizar a exclusão (soft delete) de uma empresa pelo seu ID.
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * A empresa será pesquisada por um UUID válido.
                    * A empresa já deve ter sido cadastrada anteriormente;
                * Http response status do endpoint:
                    * Status 204 - No content - exclusão realizada com sucesso;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se a empresa não foi encontrada por seu ID para ser excluído;

        * http://localhost:8081/companies/name-email
            * Verbo GET - para realizar a pesquisa paginada de uma empresa pelo seu nome ou email.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * A empresa será pesquisada por um e-mail com formato válido.
                    * A empresa já deve ter sido cadastrada anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - empresa encontrada com sucesso;
                    * Status 200 - Ok - empresa não encontrada, mas com response body com propriedades de paginação, porém com o content vazio;
                    * Status 400 - Bad request - se alguma regra foi violada;

    * Documentação da API: http://localhost:8081/swagger-ui/index.html
    * Banco de dados: http://localhost:5432/company-db

    ![alt text](./img/image-14.png)

    326 testes de integração e unidade, executados em 7 segudos, com 97% de classes e 83% de linhas de código cobertas.
    Para realizar os testes de integração em endpoints autenticados, foi utilizado a anotação @WithMockUser("thomas.anderson@itcompany.com", authorities = {"ADMIN"}), na anotação customizada IntegrationTest.

    ![alt text](./img/image-20.png)

# Microsserviço de gerenciamento de produtos (itens)
O objetivo deste microsserviço é gerenciar os produtos/serviços cadastrados pelo fornecedor, que por sua vez, serão incluídos em pedidos de venda.
  * Microsserviço de Gerenciamento de produtos 
    * API: 
        * http://localhost:8082/products
            * Verbo GET - para realizar a pesquisa de todos os produtos, retornando uma lista paginada.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * Este endpoint retornará uma coleção paginada de produtos se existirem registros no banco de dados.
                * Http response status do endpoint:
                    * Status 200 - Ok - se existirem produtos cadastrados;

            * Verbo POST - para realizar o cadastro.
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * Atributos sku, descrição, unidade de medida, quantidade em estoque, preço obrigatórios e url da imagem.
                    * Atributos sku e unidade de medida com o tamanho máximo de 20 caracteres;
                    * Atributo descrição mínimo de 3 e máximo de 500 caracteres;
                    * Atributos quantidade em estoque e preço unitário com o valor maior que zero;
                    * Validação do sku único na base de dados.
                * Http response status do endpoint:
                    * Status 201 - Created - cadastro realizado com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 409 - Conflict - se o sku informado já está cadastrado na base de dados.

        * http://localhost:8082/products/{id}
            * Verbo GET - para realizar a pesquisa de um produto pelo seu ID.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O produto será pesquisada por um UUID válido.
                    * O produto já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - se o produto foi encontrado por seu ID;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o produto não foi encontrado por seu ID;

            * Verbo PUT - para realizar a atualização de dados de um produto pelo seu ID. Necessário informar request body.
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * O produto será pesquisado por um UUID válido.
                    * O produto já deve ter sido cadastrado anteriormente;
                    * O sku não pode ser atualizado no produto se já estiverem sendo utilizados por outro produto;
                    * Demais regras equivalentes para realizar o cadastro.
                * Http response status do endpoint:
                    * Status 202 - Accepted - atualização realizada com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o produto não foi encontrado por seu ID para ser atualizado;
                    * Status 409 - Conflict - se o sku do produto já está cadastrado na base de dados.

            * Verbo DELETE - para realizar a exclusão (soft delete) de um produto pelo seu ID.   
                * Escopo: privado, requer autenticação com role ADMIN.
                * Regras de negócio:
                    * O produto será pesquisado por um UUID válido.
                    * O produto já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 204 - No content - exclusão realizada com sucesso;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o produto não foi encontrado por seu ID para ser excluído;            

        * http://localhost:8082/products/sku-description
            * Verbo GET - para realizar a pesquisa paginada de um produto pelo seu sku ou descrição.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O produto já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - O produto encontrado com sucesso;
                    * Status 200 - Ok - O produto não encontrado, mas com response body com propriedades de paginação, porém com o content vazio;
                    * Status 400 - Bad request - se alguma regra foi violada;

    * Documentação: http://localhost:8082/swagger-ui/index.html
    * Banco de dados: http://localhost:5434/product-db

    ![alt text](./img/image-2.png)

    88 testes de integração e unidade, executados em 3 segudos, com 97% de classes e 77% de linhas de código cobertas.
    Para realizar os testes de integração em endpoints autenticados, foi utilizado a anotação @WithMockUser("thomas.anderson@itcompany.com", authorities = {"ADMIN"}), na anotação customizada IntegrationTest.

    ![alt text](./img/image-16.png)

# Microsserviço de gerenciamento de pedidos
O objetivo deste microsserviço é gerenciar os pedidos cadastrados pelos clientes, que irão consumir um produto/serviço.
  * Microsserviço de Gerenciamento de pedidos 
    * API: 
        * http://localhost:8083/orders
            * Verbo POST - para realizar o cadastro.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * Atributos ID da empresa/usuário, lista dos produtos com ID do produto, sku do produto, unidade de medida do produto, quantidade do produto e preço unitário obrigatórios;
                    * Atributos sku e unidade de medida com o tamanho máximo de 20 caracteres;
                    * Atributos quantidade em estoque e preço unitário com o valor maior que zero;
                    * Validar se o atributo companyId é de uma empresa existente no microsserviço de gerenciamento de empresas;
                    * Validar se o atributo customerId é de um usuário existente no microsserviço de gerenciamento de usuários;
                    * Validar se o atributo productId é de um produto existente no microsserviço de gerenciamento de produtos;
                * Http response status do endpoint:
                    * Status 201 - Created - cadastro realizado com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o pedido, empresa, cliente ou produto não foi encontrado por seu ID;

        * http://localhost:8083/orders/{id}
            * Verbo GET - para realizar a pesquisa de um pedido pelo seu ID.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O pedido será pesquisada por um UUID válido.
                    * O pedido já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - se o pedido foi encontrado por seu ID;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o pedido não foi encontrado por seu ID;

            * Verbo PUT - para realizar a atualização de dados de um pedido pelo seu ID. Necessário informar request body.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * Atributos ID da empresa/usuário, lista dos produtos com ID do produto, sku do produto, unidade de medida do produto, quantidade do produto e preço unitário obrigatórios;
                    * Atributos sku e unidade de medida com o tamanho máximo de 20 caracteres;
                    * Atributos quantidade em estoque e preço unitário com o valor maior que zero;
                    * Validar se o atributo companyId é de uma empresa existente no microsserviço de gerenciamento de empresas;
                    * Validar se o atributo customerId é de um usuário existente no microsserviço de gerenciamento de usuários;
                    * Validar se o atributo productId é de um produto existente no microsserviço de gerenciamento de produtos;
                * Http response status do endpoint:
                    * Status 202 - Accepted - atualização realizada com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o pedido, empresa, cliente ou produto não foi encontrado por seu ID para ser atualizado;

            * Verbo DELETE - para realizar a exclusão (soft delete) de um pedido pelo seu ID.   
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O pedido será pesquisado por um UUID válido.
                    * O pedido já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 204 - No content - exclusão realizada com sucesso;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o pedido não foi encontrado por seu ID para ser excluído;

        * http://localhost:8083/orders/company-customer
            * Verbo GET - para realizar a pesquisa paginada de um pedido pela empresa ou cliente.        
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O pedido já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - O pedido encontrado com sucesso;
                    * Status 200 - Ok - O pedido não encontrado, mas com response body com propriedades de paginação, porém com o content vazio;
                    * Status 400 - Bad request - se alguma regra foi violada;

        * http://localhost:8083/orders/{id}/payment
            * Verbo PATCH - para realizar a atualização status do pedido para PAGO.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * Validar se o atributo ID é de um pedido existente;
                    * Validar se o atributo STATUS é de AGUARDANDO_PAGAMENTO;
                * Http response status do endpoint:
                    * Status 202 - Accepted - atualização realizada com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o não foi encontrado por seu ID para ser atualizado;

    * Documentação: http://localhost:8083/swagger-ui/index.html
    * Banco de dados: http://localhost:5435/order-db

![alt text](./img/image-3.png)

103 testes de integração e unidade, executados em 4 segudos, com 91% de classes e 75% de linhas de código cobertas.
Para os testes dos endpoints que realizam comunicação a um microsserviço externo, seu respectivo endpoint foi mockado com o Wiremock, para tornar o teste independente e não falhar pela dependência do microsserviço externo.

![alt text](./img/image-17.png)

# Microsserviço de gerenciamento de pagamentos
O objetivo deste microsserviço é gerenciar os pagamentos cadastrados pelos clientes, que realizaram a compra de um pedido.
  * Microsserviço de Gerenciamento de pagamentos 
    * API: 
        * http://localhost:8084/payments
            * Verbo POST - para realizar o cadastro do pagamento.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * Atributos ID pedido e tipo de pagamento obrigatórios.
                    * Validar se o atributo orderId é de um pedido existente no microsserviço de gerenciamento de pedidos;
                    * Validar se o atributo customerId é correspondente ao usuário autenticado na aplicação, pois ele está realizando o pagamento;
                    * Recuperar os dados do pedido no microsserviço de pedidos e validar se o status do pedido é AGUARDANDO_PAGAMENTO. Se for algum valor difererente, lançar exceção;
                    * Os status do pagamento poderão ser PENDENTE ou REALIZADO.
                    * O pagamento será cadastrado com o status REALIZADO. 
                    * O pagamento atualizará o status do pedido como PAGO.
                * Http response status do endpoint:
                    * Status 201 - Created - cadastro realizado com input de dados válidos;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o pedido não foi encontrado por seu ID no microsserviço de pedidos.

        * http://localhost:8084/payments/order/{id}
            * Verbo GET - para realizar a pesquisa de um pagamento de um pedido pelo ID do pedido.
                * Escopo: privado, requer autenticação com role USER/ADMIN.
                * Regras de negócio:
                    * O pagamento será pesquisada por um UUID de pedido válido.
                    * O pagamento já deve ter sido cadastrado anteriormente;
                * Http response status do endpoint:
                    * Status 200 - Ok - se o pagamento foi encontrado por seu ID;
                    * Status 400 - Bad request - se alguma regra foi violada;
                    * Status 404 - Not found - se o pagamento do pedido não foi encontrado pelo ID do pedido;

    * Documentação: http://localhost:8084/swagger-ui/index.html
    * Banco de dados: http://localhost:5436/payment-db

![alt text](./img/image-18.png)

56 testes de integração e unidade, executados em 3 segudos, com 97% de classes e 78% de linhas de código cobertas.
Para os testes dos endpoints que realizam comunicação a um microsserviço externo, seu respectivo endpoint foi mockado com o Wiremock, para tornar o teste independente e não falhar pela dependência do microsserviço externo.

![alt text](./img/image-21.png)

# Qualidade de software
Para garantir a qualidade de software, implementamos testes de unidade e de integração na grande maioria do código e teste de design arquitetural do projeto com o ArchUnit. Para identificar o que foi testado, utilizamos a cobertura de testes de código do próprio IntelliJ IDEA e o ArchUnit. O ArchUnit foi utilizado para identificar através de um teste a existência de testes correspondentes para as classes de serviço, use case, validators, identifica se as classes foram criadas respeitando a arquitetura/design do projeto (cada classe deverá ser criada em sua respectiva pasta, conforme seu objetivo, não é permitido injetar repositories em classes indevidas, métodos de use case que executam operações de escrita em banco de dados devem ser anotadas com @Transactional).
Os testes de unidade foram implementados nas classes de domínio e application testando a menor unidade de código. Os testes de integração foram implementados nas classes de presentation, realizando a requisição HTTP aos endpoints em diversos cenários, testando o código por completo, da entrada dos dados, processamento e saída. O objetivo desta segregação foi considerar a eficiência dos testes versus o tempo de entrega do projeto. Aplicando este método, foi apurado pela cobertuda de testes do IntelliJ IDEA, em mais de 90% de classes testadas na maioria dos microserviços. A decisão de utilizar o próprio IntelliJ foi motivada pela manutenção de menor número de dependências a serem adicionadas no projeto, com o objetivo de reduzir possibilidades de libs externas abrirem uma fragilidade na segurança da aplicação (lembrando do caso do Log4J) e que no cenário em que o projeto foi desenvolvido não foi necessária a adição do Jacoco.  Para realizar o teste de cobertura, clique com o botão direito do mouse sobre o nome do projeto, navegue até a opção More Run/Debug, em seguida selecione a opção Run tests in <nome do projeto> with Coverage.
As comunicações dentre microsserviços foram mockadas nos testes de integração, para que os testes se mantenham independentes, porém contemplando os cenários pertinentes a esta comunicação.

# Desafios encontrados no projeto
Um grande desafio encontrado no projeto foi a quantidade de código implementado através de cinco microsserviços criados, bem como seus bancos de dados individuais, sua documentação e testes. Nos testes de integração, que realiza testes completos desde a entrada da requisição no controller, passar pelos use cases, services, repositories, vai até o banco de dados e retorna uma resposta com status e response body, houve um complicador. O complicador foi que existem endpoints que realizam a comunicação entre microsserviços para validar a existência de um recurso, apenas retornando um http status OK ou retornando um response body. Assim, os testes de integração falhavam por conta desta comunicação externa ao próprio microsserviço. Desta maneira, para solucionar o problema, precisei mockar (com Wiremock) estas comunicações externas a fim de tornar o teste de integração independente. Outro desafio foi recuperar os dados dos microsserviços externos devido a minha inexperiência no assunto. Para resolver a questão, utilizei o Spring Open Fegin, com interfaces que retornam ResponseEntity. Desta maneira, consegui recuperar os dados, bem como o HttpStatus. Se ocorresse erro, criei um handler para capturar e tratar o erro para mandar para o consumidor do endpoint principal. Outro desafio foi o tempo de entrega do trabalho, pois como desenvolvedor solo, a mão de obra foi grande e não implementei outras opções de arquitetura de microsserviços, como utilizar um API Gateway, Service discovery, Config Server e outro framework de segurança como o OAuth2 com OpenID Connect.
