# Spring Boot API Security Inventory

API de inventário construída com Spring Boot, JPA/Hibernate e autenticação baseada em JWT. Inclui gerenciamento de usuários e produtos, segurança stateless com tokens, e integração com MySQL via Docker Compose.

## Visão Geral

- API REST para cadastrar e consultar produtos
- Fluxo de autenticação com geração e validação de JWT
- Perfis de usuário com `ROLE_USER` e `ROLE_ADMIN`
- Segurança stateless com filtro de autenticação

## Tecnologias

- Java `21`
- Spring Boot `3.5.7`
- Spring Security
- JPA/Hibernate
- MySQL
- Docker e Docker Compose
- Lombok

## Serviços Utilizados

- GitHub
- Docker

## Pré-requisitos

- `Java 21` instalado
- `Docker` e `Docker Compose` instalados
- Porta `3306` disponível para MySQL

## Configuração de Ambiente

- Variável de ambiente para o segredo do JWT:
  - `JWT_SECRET` (opcional; padrão `my-secret-key`)
- Configurações de banco em `src/main/resources/application.properties`:
  - URL: `jdbc:mysql://localhost:3306/inventory`
  - Usuário: `admin`
  - Senha: `123`

## Subir Banco de Dados (Docker)

- Inicie o MySQL com Docker Compose:

```bash
docker-compose up -d mysql
```

## Executar a Aplicação

- Via Maven Wrapper (Windows):

```bash
./mvnw.cmd spring-boot:run
```

- Via Maven Wrapper (Linux/Mac):

```bash
./mvnw spring-boot:run
```

## Endpoints Principais

- Autenticação
  - `POST /auth/login` — recebe `{ "username": "...", "password": "..." }` e retorna `{ "accessToken": "..." }`
  - `POST /auth/register` — cria um novo usuário
- Usuários
  - `GET /users` — requer `ROLE_ADMIN`
- Produtos
  - `GET /products` — lista produtos
  - `GET /products/{id}` — detalha produto
  - `POST /products` — requer `ROLE_ADMIN`

## Exemplo de Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123"}'
```

- Resposta esperada:

```json
{ "accessToken": "<jwt-token>" }
```

- Utilize o token nas requisições protegidas:

```bash
curl http://localhost:8080/products -H "Authorization: Bearer <jwt-token>"
```

## Semeadura de Usuário Admin

- Um usuário `admin` pode ser criado via endpoint `POST /auth/register`.
- Senhas são armazenadas com `BCrypt`.

## Observações de Segurança

- Configure `JWT_SECRET` em produção com um valor forte e mantido em segredo
- Mantenha o banco protegido e com credenciais seguras

## Versionamento

- `1.0.0`

## Autores

- **Equipe Inventory**

Obrigado por visitar e bons códigos!