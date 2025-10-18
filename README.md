### **Visão Geral**

O **Datarium API** é o componente de back-end do projeto Datarium, uma plataforma de assessoramento virtual de investimentos, desenvolvida para o desafio da **XP Inc.** e FIAP. Esta aplicação é uma API RESTful desenvolvida em Java com Spring Boot, responsável pela lógica de negócio, persistência de dados (CRUD) e autenticação segura de usuários usando JWT, comunicando-se com o banco de dados Oracle da FIAP. O projeto também prioriza a conformidade com a LGPD.

### **Participantes do Grupo**

* **Anna Yagyu** - RM 550360
* **Breno Silva** - RM 99275
* **Danilo Urze** - RM 99465
* **Gabriel Pacheco** - RM 550191

### **Tecnologias Utilizadas**

* **Java 17**
* **Spring Boot** (Spring Data JPA, Spring Web, Spring Security)
* **JWT (JSON Web Tokens)** para autenticação stateless
* **BCrypt** para hashing de senhas
* **Maven**
* **Oracle Database**

### **Diagramas**

* **Diagrama de Arquitetura em Camadas**
    <img width="922" height="357" alt="image" src="https://github.com/user-attachments/assets/f341329b-0578-496f-b0b5-c4f9b70bac70" />

* **Diagrama de Entidades (ER ou UML)**
    <img width="1258" height="415" alt="image" src="https://github.com/user-attachments/assets/d1387427-0232-4f80-bb4d-b2b093177e14" />

### **Funcionalidades de Segurança Implementadas**

Para garantir a segurança da API e dos dados dos usuários, foram implementadas as seguintes funcionalidades usando Spring Security:

1.  **Autenticação Stateless com JWT:** A API utiliza JSON Web Tokens (JWT) para autenticação. Após o login bem-sucedido, o cliente recebe um token que deve ser enviado no cabeçalho `Authorization` (com prefixo `Bearer `) de todas as requisições subsequentes para endpoints protegidos. Isso torna a API *stateless*, ou seja, ela não precisa armazenar informações de sessão no servidor.
2.  **Criptografia de Senhas (BCrypt):** As senhas dos usuários nunca são armazenadas em texto plano. Ao cadastrar ou atualizar um cliente, a senha fornecida é criptografada usando o algoritmo BCrypt antes de ser salva no banco de dados. O mesmo algoritmo é usado para verificar a senha durante o processo de login.
3.  **Proteção de Endpoints:** A configuração de segurança (`SecurityConfig.java`) define quais endpoints são públicos (cadastro e login) e quais exigem autenticação. O `TokenFilter.java` intercepta as requisições para validar o token JWT antes de permitir o acesso aos endpoints protegidos.

### **Instruções de Configuração e Execução**

1.  **Pré-requisitos**:
    * **Java 17** ou superior instalado e configurado.
    * **Maven** instalado.
    * Acesso ao banco de dados Oracle da FIAP.
    * Cliente HTTP para testes (como Postman ou Insomnia).

2.  **Configuração do Banco de Dados**:
    * Abra o arquivo `src/main/resources/application.properties`.
    * Verifique e, se necessário, altere as credenciais (`spring.datasource.username` e `spring.datasource.password`) para as suas credenciais do Oracle da FIAP.
    * Certifique-se de que a `jwt.secret` está definida no mesmo arquivo.

3.  **Execução da API**:
    * No terminal, na pasta raiz do projeto, execute o comando:
        ```bash
        mvn spring-boot:run
        ```
    * A API será executada na porta `8080` e estará pronta para aceitar requisições.

### **Endpoints da API e Testes com Postman**

A base URL para todas as requisições é `http://localhost:8080`.

**Fluxo de Autenticação:**

1.  **Faça o Login:** Envie uma requisição `POST` para `/clientes/login` com o email e senha.
2.  **Copie o Token:** Se o login for bem-sucedido, você receberá um token JWT na resposta. Copie este token.
3.  **Use o Token:** Para todas as requisições subsequentes a endpoints protegidos, vá até a aba **"Authorization"** no Postman, selecione o tipo **"Bearer Token"** e cole o token copiado no campo correspondente.

---

#### **Endpoints de Clientes**

* **POST** `/clientes`
    * **Descrição**: Cadastra um novo cliente no banco de dados. **(Público)**
    * **Como testar com Postman:**
        * Método: `POST`
        * URL: `http://localhost:8080/clientes`
        * Body -> raw (JSON):
            ```json
            {
              "nome": "João da Silva",
              "email": "joao.silva@email.com",
              "senha": "senha123",
              "perfilInvestidor": "MODERADO",
              "objetivo": "LONGO_PRAZO"
            }
            ```
        * Headers: `Content-Type: application/json`

* **POST** `/clientes/login`
    * **Descrição**: Autentica um cliente e retorna um token JWT. **(Público)**
    * **Como testar com Postman:**
        * Método: `POST`
        * URL: `http://localhost:8080/clientes/login`
        * Body -> raw (JSON):
            ```json
            {
              "email": "joao.silva@email.com",
              "senha": "senha123"
            }
            ```
        * Headers: `Content-Type: application/json`
        * *Copie o valor do campo "token" da resposta para usar nos próximos testes.*

* **GET** `/clientes/{id}`
    * **Descrição**: Busca um cliente por ID. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `GET`
        * URL: `http://localhost:8080/clientes/1` (substitua `1` pelo ID desejado)
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"

* **PUT** `/clientes/{id}`
    * **Descrição**: Atualiza os dados de um cliente existente. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `PUT`
        * URL: `http://localhost:8080/clientes/1` (substitua `1` pelo ID a ser atualizado)
        * Body -> raw (JSON): *(Inclua **todos** os campos do cliente, mesmo os que não mudaram, mas o ID não é necessário no corpo)*
            ```json
            {
              "nome": "João da Silva Atualizado",
              "email": "joao.silva@email.com",
              "senha": "nova_senha456",
              "perfilInvestidor": "AGRESSIVO",
              "objetivo": "CURTO_PRAZO"
            }
            ```
        * Headers: `Content-Type: application/json`
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"

* **DELETE** `/clientes/{id}`
    * **Descrição**: Deleta um cliente por ID. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `DELETE`
        * URL: `http://localhost:8080/clientes/1` (substitua `1` pelo ID a ser deletado)
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"

---

#### **Endpoints de Ativos**

*Lembre-se: Todos os endpoints de ativos requerem o token de autenticação.*

* **POST** `/ativos`
    * **Descrição**: Adiciona um novo ativo ao portfólio de um cliente. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `POST`
        * URL: `http://localhost:8080/ativos`
        * Body -> raw (JSON): *(Certifique-se que o `id` do cliente existe)*
            ```json
            {
              "nome": "Tesouro Direto IPCA+ 2035",
              "tipo": "RENDA_FIXA",
              "valor": 1500.00,
              "descricao": "Título público atrelado à inflação.",
              "cliente": {
                "id": 1 
              }
            }
            ```
        * Headers: `Content-Type: application/json`
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"

* **GET** `/ativos/cliente/{clienteId}`
    * **Descrição**: Retorna todos os ativos de um cliente específico. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `GET`
        * URL: `http://localhost:8080/ativos/cliente/1` (substitua `1` pelo ID do cliente)
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"

* **GET** `/ativos/{id}`
    * **Descrição**: Busca um ativo por ID. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `GET`
        * URL: `http://localhost:8080/ativos/5` (substitua `5` pelo ID do ativo)
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"

* **PUT** `/ativos/{id}`
    * **Descrição**: Atualiza os dados de um ativo existente. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `PUT`
        * URL: `http://localhost:8080/ativos/5` (substitua `5` pelo ID do ativo)
        * Body -> raw (JSON): *(Você só precisa enviar os campos que quer atualizar, mas inclua também o cliente ID)*
            ```json
            {
              "nome": "Fundo XP Ações",
              "tipo": "RENDA_VARIAVEL",
              "valor": 3000.00,
              "descricao": "Fundo de ações XP.",
              "cliente": {
                 "id": 1 
              }
            }
            ```
        * Headers: `Content-Type: application/json`
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"

* **DELETE** `/ativos/{id}`
    * **Descrição**: Deleta um ativo por ID. **(Requer Token)**
    * **Como testar com Postman:**
        * Método: `DELETE`
        * URL: `http://localhost:8080/ativos/5` (substitua `5` pelo ID do ativo)
        * Authorization: Type="Bearer Token", Token="SEU_TOKEN_COPIADO"
