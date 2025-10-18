### **Visão Geral**

O **Datarium API** é o componente de back-end do projeto Datarium, uma plataforma de assessoramento virtual de investimentos, desenvolvida para o desafio da **XP Inc.** e FIAP. Esta aplicação é uma API RESTful desenvolvida em Java com Spring Boot, responsável pela lógica de negócio, persistência de dados (CRUD) e autenticação de usuários, comunicando-se com o banco de dados Oracle da FIAP. O projeto também prioriza a cibersegurança e a conformidade com a LGPD.

### **Participantes do Grupo**

* **Anna Yagyu** - RM 550360
* **Breno Silva** - RM 99275
* **Danilo Urze** - RM 99465
* **Gabriel Pacheco** - RM 550191

### **Tecnologias Utilizadas**

* **Java 17**
* **Spring Boot** (Spring Data JPA, Spring Web)
* **Maven**
* **Oracle Database**
* **GitHub Actions** (para testes de segurança)

### **Diagramas**

* **Diagrama de Arquitetura em Camadas**
    <img width="922" height="357" alt="image" src="https://github.com/user-attachments/assets/f341329b-0578-496f-b0b5-c4f9b70bac70" />

* **Diagrama de Entidades (ER ou UML)**
    <img width="1258" height="415" alt="image" src="https://github.com/user-attachments/assets/d1387427-0232-4f80-bb4d-b2b093177e14" />

### **Sprint de Cibersegurança**

Durante a sprint de Cibersegurança, implementamos testes de segurança automatizados para garantir a integridade e a robustez do nosso código. A solução utiliza o **GitHub Actions** com **CodeQL** para realizar uma análise estática do código-fonte em busca de vulnerabilidades, como injeção de SQL e XSS. Além disso, o fluxo de trabalho de segurança inclui uma análise dinâmica com **OWASP ZAP**. Esses testes são executados a cada `push` ou `pull request`.

Você pode encontrar o fluxo de trabalho de segurança no seguinte caminho: `.github/workflows/codeql.yml`.

### **Instruções de Configuração e Execução**

1.  **Pré-requisitos**:
    * **Java 17** ou superior instalado e configurado.
    * **Maven** instalado.
    * Acesso ao banco de dados Oracle da FIAP.
    * Cliente HTTP para testes (como Postman ou Insomnia).

2.  **Configuração do Banco de Dados**:
    * Abra o arquivo `src/main/resources/application.properties`.
    * O projeto está configurado para rodar na minha conta pessoal do banco de dados Oracle. Caso queira executá-lo em outra conta, por favor, mude as credenciais (username e password) para as suas.
    ```properties
    spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
    spring.datasource.username=RM99275
    spring.datasource.password=180105
    ```

3.  **Execução da API**:
    * No terminal, na pasta raiz do projeto, execute o comando:
        ```bash
        mvn spring-boot:run
        ```
    * A API será executada na porta `8080` e estará pronta para aceitar requisições do aplicativo mobile.

### **Endpoints da API**

Os seguintes endpoints foram implementados para gerenciar clientes e ativos:

#### **Endpoints de Clientes**

* **POST** `/clientes`
    * **Descrição**: Cadastra um novo cliente no banco de dados.
    * **Exemplo de Requisição**:
        ```json
        {
          "nome": "João da Silva",
          "email": "joao.silva@email.com",
          "senha": "senha123",
          "perfilInvestidor": "MODERADO",
          "objetivo": "LONGO_PRAZO"
        }
        ```
* **POST** `/clientes/login`
    * **Descrição**: Autentica um cliente com e-mail e senha.
    * **Exemplo de Requisição**:
        ```json
        {
          "email": "joao.silva@email.com",
          "senha": "senha123"
        }
        ```
* **GET** `/clientes/{id}`
    * **Descrição**: Busca um cliente por ID.
    * **Exemplo de Requisição**: `http://localhost:8080/clientes/1`
* **PUT** `/clientes/{id}`
    * **Descrição**: Atualiza os dados de um cliente existente.
    * **Exemplo de Requisição**:
        ```json
        {
          "id": "5", 
          "nome": "João da Silva ATUALIZADO",
          "email": "joao.silva@email.com",
          "senha": "nova_senha123",
          "perfilInvestidor": "AGRESSIVO",
          "objetivo": "CURTO_PRAZO"
        }
        ```
* **DELETE** `/clientes/{id}`
    * **Descrição**: Deleta um cliente por ID.
    * **Exemplo de Requisição**: `http://localhost:8080/clientes/1`

#### **Endpoints de Ativos**

* **POST** `/ativos`
    * **Descrição**: Adiciona um novo ativo ao portfólio de um cliente.
    * **Exemplo de Requisição**:
        ```json
        {
          "nome": "Tesouro Direto",
          "tipo": "RENDA_FIXA",
          "valor": 1000.00,
          "descricao": "Ativo de renda fixa.",
          "cliente": {
            "id": 1
          }
        }
        ```
* **GET** `/ativos/cliente/{clienteId}`
    * **Descrição**: Retorna todos os ativos de um cliente específico.
    * **Exemplo de Requisição**: `http://localhost:8080/ativos/cliente/1`
* **GET** `/ativos/{id}`
    * **Descrição**: Busca um ativo por ID.
    * **Exemplo de Requisição**: `http://localhost:8080/ativos/1`
* **PUT** `/ativos/{id}`
    * **Descrição**: Atualiza os dados de um ativo existente.
    * **Exemplo de Requisição**:
        ```json
        {
          "id": "5",
          "nome": "Fundo de Ações",
          "tipo": "RENDA_VARIAVEL",
          "valor": 2500.00,
          "descricao": "Fundo de investimentos de ações."
        }
        ```
* **DELETE** `/ativos/{id}`
    * **Descrição**: Deleta um ativo por ID.
    * **Exemplo de Requisição**: `http://localhost:8080/ativos/1`
