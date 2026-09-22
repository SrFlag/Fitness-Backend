# 🏋️ Fitness Backend

> Backend da plataforma de treino e nutrição personalizada, construída
> com **Java + Spring Boot + MongoDB**, preparada para integração com
> aplicativos **Android e iOS** e futura utilização de **Inteligência
> Artificial**.

------------------------------------------------------------------------

## 📌 Sobre o projeto

O **Fitness Backend** é a API responsável por centralizar usuários,
autenticação, treinos, exercícios, histórico de desempenho e,
futuramente, nutrição e inteligência artificial.

A proposta não é ser apenas mais um aplicativo de academia. O sistema
está sendo construído para **conhecer o usuário e adaptar sua
experiência** com base em objetivos, experiência, disponibilidade,
equipamentos, preferências e evolução.

### 🎯 Objetivo

Criar uma plataforma capaz de conectar:

-   👤 Perfil e objetivos do usuário
-   🏋️ Treinos personalizados
-   📈 Histórico e evolução
-   🍎 Nutrição
-   🤖 Inteligência Artificial
-   📱 Aplicativo Android e iOS

O backend será independente do aplicativo, permitindo que diferentes
clientes consumam a mesma API.

------------------------------------------------------------------------

# 🏗️ Stack tecnológica

  Tecnologia                Utilização
  ------------------------- --------------------------------
  ☕ Java                   Linguagem principal
  🌱 Spring Boot            Framework do backend
  🔐 Spring Security        Segurança e autenticação
  🔑 JWT                    Autenticação baseada em tokens
  🔒 BCrypt                 Hash seguro de senhas
  🍃 MongoDB Atlas          Banco de dados
  📦 Spring Data MongoDB    Persistência
  ✅ Jakarta Validation     Validação das requisições
  🛠️ Maven                  Gerenciamento e build
  🧩 Lombok                 Redução de código repetitivo
  📊 Spring Boot Actuator   Monitoramento
  📱 React Native + Expo    Cliente mobile planejado
  🤖 Gemini                 IA planejada

------------------------------------------------------------------------

# 🧱 Arquitetura atual

A aplicação está sendo organizada por responsabilidades:

``` text
src/main/java/br/com/fitness
│
├── auth
│   ├── controller
│   │   └── AuthController.java
│   │
│   ├── dto
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   ├── RegisterRequest.java
│   │   └── UserResponse.java
│   │
│   ├── security
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtService.java
│   │
│   └── service
│       └── AuthService.java
│
├── common
│   └── controller
│       └── HealthController.java
│
├── config
│   ├── PasswordConfig.java
│   └── SecurityConfig.java
│
└── user
    ├── model
    │   └── User.java
    │
    └── repository
        └── UserRepository.java
```

A arquitetura será expandida conforme novas funcionalidades forem
implementadas.

------------------------------------------------------------------------

# 🔐 Autenticação

A autenticação já está implementada.

## Cadastro

``` http
POST /api/auth/register
```

Exemplo:

``` json
{
  "name": "João",
  "email": "joao@fitness.com",
  "password": "123456"
}
```

O backend:

1.  Valida os dados.
2.  Verifica se o e-mail já existe.
3.  Gera um hash BCrypt da senha.
4.  Cria o usuário.
5.  Salva no MongoDB Atlas.
6.  Retorna os dados públicos do usuário.

### Exemplo de resposta

``` json
{
  "id": "xxxxxxxx",
  "name": "João",
  "email": "joao@fitness.com",
  "role": "USER"
}
```

> 🔒 O `passwordHash` não é retornado pela API.

------------------------------------------------------------------------

## Login

``` http
POST /api/auth/login
```

Exemplo:

``` json
{
  "email": "joao@fitness.com",
  "password": "123456"
}
```

Resposta:

``` json
{
  "token": "eyJ...",
  "type": "Bearer"
}
```

O token deve ser enviado nas rotas protegidas através do header:

``` http
Authorization: Bearer SEU_TOKEN
```

------------------------------------------------------------------------

# 🔑 JWT

A autenticação utiliza JWT com sessão **STATELESS**.

Fluxo:

``` text
┌──────────────┐
│    Cliente   │
└──────┬───────┘
       │ login
       ▼
┌──────────────┐
│ AuthService  │
└──────┬───────┘
       │
       │ BCrypt
       ▼
┌──────────────┐
│   MongoDB    │
└──────┬───────┘
       │
       │ credenciais válidas
       ▼
┌──────────────┐
│  JwtService  │
└──────┬───────┘
       │
       ▼
      JWT
       │
       ▼
┌──────────────┐
│    Cliente   │
└──────────────┘
```

Nas próximas requisições:

``` text
Cliente
   │
   │ Authorization: Bearer JWT
   ▼
JwtAuthenticationFilter
   │
   ▼
JwtService
   │
   ▼
Spring Security
   │
   ▼
Rota protegida
```

O token atualmente possui validade de **24 horas**.

------------------------------------------------------------------------

# 🗄️ Banco de dados

O projeto utiliza **MongoDB Atlas**.

Banco:

``` text
fitness
```

Coleção atual:

``` text
users
```

### Estrutura atual de `User`

``` text
id
name
email
passwordHash
role
createdAt
updatedAt
```

O acesso ao MongoDB é feito através da variável de ambiente:

``` text
MONGODB_URI
```

A aplicação não deve armazenar credenciais do banco diretamente no
código.

------------------------------------------------------------------------

# ⚙️ Configuração

## Requisitos

Antes de executar o projeto, tenha instalado:

-   Java
-   Maven
-   IntelliJ IDEA ou outra IDE compatível
-   MongoDB Atlas
-   Git

------------------------------------------------------------------------

## Variáveis de ambiente

A aplicação utiliza:

``` text
MONGODB_URI
JWT_SECRET
```

### `MONGODB_URI`

String de conexão fornecida pelo MongoDB Atlas.

### `JWT_SECRET`

Chave utilizada para assinar os tokens JWT.

> ⚠️ Nunca publique essas variáveis no GitHub.

------------------------------------------------------------------------

# 📝 `application.yml`

A configuração utiliza variáveis de ambiente:

``` yaml
spring:
  application:
    name: fitness-backend

  mongodb:
    uri: ${MONGODB_URI}
    database: fitness

server:
  port: 8080

management:
  endpoints:
    web:
      exposure:
        include: health,info

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000
```

------------------------------------------------------------------------

# 🌐 Endpoints atuais

## Health Check

### `GET /api/health`

Endpoint público para verificar se a API está funcionando.

Resposta:

``` text
Fitness Backend funcionando!
```

------------------------------------------------------------------------

## Autenticação

### `POST /api/auth/register`

Cria uma nova conta.

**Público:** ✅

### `POST /api/auth/login`

Autentica um usuário e retorna um JWT.

**Público:** ✅

------------------------------------------------------------------------

## Rotas protegidas

Por padrão:

``` text
/api/**
```

é protegido pelo Spring Security, exceto as rotas explicitamente
liberadas.

Regra atual:

``` text
/api/health              → público
/api/auth/register       → público
/api/auth/login          → público
outras rotas             → autenticação obrigatória
```

------------------------------------------------------------------------

# 🧪 Testes realizados

A primeira etapa do projeto já validou o fluxo completo de autenticação.

### MongoDB

-   [x] Conexão com MongoDB Atlas
-   [x] Descoberta do servidor primário
-   [x] Autenticação no Atlas
-   [x] Persistência de usuários

### Cadastro

-   [x] `POST /api/auth/register`
-   [x] Validação de dados
-   [x] Verificação de e-mail existente
-   [x] BCrypt
-   [x] Salvamento no MongoDB
-   [x] Resposta sem `passwordHash`

### Login

-   [x] `POST /api/auth/login`
-   [x] Busca do usuário por e-mail
-   [x] Verificação da senha com BCrypt
-   [x] Geração de JWT

### Segurança

-   [x] Spring Security
-   [x] JWT Filter
-   [x] Bearer Token
-   [x] Rotas protegidas
-   [x] Sessão STATELESS
-   [x] Secret via variável de ambiente

------------------------------------------------------------------------

# 🚧 Roadmap

## Parte 1 --- Backend base e autenticação

**Status: ✅ Concluída**

-   [x] Spring Boot
-   [x] MongoDB Atlas
-   [x] Estrutura inicial
-   [x] Usuários
-   [x] Cadastro
-   [x] BCrypt
-   [x] Login
-   [x] JWT
-   [x] Spring Security
-   [x] Variáveis de ambiente

------------------------------------------------------------------------

## Parte 2 --- Usuário e perfil

**Status: 🔜 Próxima etapa**

Planejado:

-   [ ] `GET /api/users/me`
-   [ ] Atualização do usuário
-   [ ] `UserProfile`
-   [ ] Objetivo
-   [ ] Experiência
-   [ ] Altura
-   [ ] Peso
-   [ ] Dias disponíveis
-   [ ] Duração do treino
-   [ ] Local de treino
-   [ ] Equipamentos disponíveis
-   [ ] Preferências
-   [ ] Limitações e observações

------------------------------------------------------------------------

## Parte 3 --- Exercícios

-   [ ] Cadastro de exercícios
-   [ ] Grupos musculares
-   [ ] Equipamentos
-   [ ] Dificuldade
-   [ ] Instruções
-   [ ] Dicas
-   [ ] Erros comuns
-   [ ] Imagens
-   [ ] Vídeos
-   [ ] Alternativas

------------------------------------------------------------------------

## Parte 4 --- Treinos

-   [ ] Criar treino
-   [ ] Editar treino
-   [ ] Excluir treino
-   [ ] Adicionar exercícios
-   [ ] Reordenar exercícios
-   [ ] Séries
-   [ ] Repetições
-   [ ] Descanso
-   [ ] Registro de cargas
-   [ ] Execução do treino
-   [ ] Histórico

------------------------------------------------------------------------

## Parte 5 --- Evolução

-   [ ] Volume de treino
-   [ ] Cargas
-   [ ] Repetições
-   [ ] Frequência
-   [ ] PRs
-   [ ] Gráficos
-   [ ] Comparação de desempenho

------------------------------------------------------------------------

## Parte 6 --- Inteligência Artificial

-   [ ] Integração com Gemini
-   [ ] Contexto baseado no perfil
-   [ ] Geração de treinos
-   [ ] Avaliação de treinos
-   [ ] Sugestão de ajustes
-   [ ] Respostas estruturadas
-   [ ] Validação das respostas da IA
-   [ ] Uso exclusivo de exercícios existentes no banco

A IA não deverá acessar o banco diretamente.

O fluxo planejado será:

``` text
Usuário
   ↓
Aplicativo
   ↓
Backend
   ↓
Seleciona dados relevantes
   ↓
Monta contexto
   ↓
Gemini
   ↓
Resposta estruturada
   ↓
Backend valida
   ↓
Aplicativo
```

------------------------------------------------------------------------

## Parte 7 --- Nutrição

-   [ ] Perfil nutricional
-   [ ] Metas calóricas
-   [ ] Macronutrientes
-   [ ] Diário alimentar
-   [ ] Refeições
-   [ ] Alimentos
-   [ ] Base nutricional
-   [ ] Progresso alimentar

------------------------------------------------------------------------

## Parte 8 --- IA nutricional

-   [ ] Análise de foto do prato
-   [ ] Identificação de alimentos
-   [ ] Estimativa de porções
-   [ ] Cálculo nutricional
-   [ ] Correção manual
-   [ ] Histórico alimentar

> As informações nutricionais geradas por IA serão tratadas como
> **estimativas**, permitindo correção pelo usuário.

------------------------------------------------------------------------

# 📱 Aplicativo

O backend será consumido inicialmente por um aplicativo desenvolvido
com:

``` text
React Native
+
Expo
+
TypeScript
```

O objetivo é oferecer suporte desde o início para:

-   🤖 Android
-   🍎 iOS

O backend permanece independente da plataforma.

------------------------------------------------------------------------

# 🔐 Boas práticas de segurança

O projeto segue algumas regras desde o início:

-   🔒 Senhas nunca são armazenadas em texto puro.
-   🔐 Senhas são protegidas com BCrypt.
-   🎫 Autenticação utiliza JWT.
-   🌐 Rotas protegidas exigem autenticação.
-   🚫 Secrets não ficam no código.
-   🗄️ Credenciais do MongoDB ficam em variáveis de ambiente.
-   📱 O cliente não envia `userId` para acessar os próprios dados; a
    identidade deverá ser obtida através do JWT.
-   🧹 Dados temporários de teste devem ser removidos antes de cada
    release.

------------------------------------------------------------------------

# 🐳 Infraestrutura planejada

A infraestrutura futura seguirá aproximadamente:

``` text
                 ┌─────────────────┐
                 │   Mobile App    │
                 │ Android / iOS    │
                 └────────┬────────┘
                          │
                          ▼
                    ┌───────────┐
                    │ Cloudflare│
                    └─────┬─────┘
                          │
                          ▼
                  ┌───────────────┐
                  │    Nginx      │
                  └───────┬───────┘
                          │
                          ▼
                 ┌─────────────────┐
                 │ Spring Boot API │
                 │    Docker       │
                 └───────┬─────────┘
                         │
              ┌──────────┼──────────┐
              ▼          ▼          ▼
         MongoDB      Gemini     Storage
          Atlas         AI       de mídia
```

A hospedagem planejada do backend será em uma **VPS da Hostinger**.

------------------------------------------------------------------------

# 📂 Organização do desenvolvimento

O projeto está sendo desenvolvido de forma incremental.

Cada etapa deve:

1.  Criar uma funcionalidade.
2.  Testar localmente.
3.  Validar integração.
4.  Limpar código temporário.
5.  Atualizar este README.
6.  Criar um commit organizado.
7.  Enviar para o GitHub.

------------------------------------------------------------------------

# 📌 Estado atual

**Versão:** `Parte 1`

**Status:** 🟢 Backend base funcional

O sistema já consegue:

``` text
Criar usuário
     ↓
Salvar no MongoDB
     ↓
Criptografar senha
     ↓
Fazer login
     ↓
Gerar JWT
     ↓
Receber Bearer Token
     ↓
Autenticar requisições
```

------------------------------------------------------------------------

# 👨‍💻 Desenvolvimento

Projeto desenvolvido como uma plataforma real de treino e nutrição
personalizada, com foco em:

-   Engenharia de software
-   Backend
-   Segurança
-   Mobile
-   Banco de dados
-   Inteligência Artificial
-   Experiência do usuário
-   Escalabilidade

------------------------------------------------------------------------

## 📜 Licença

Projeto em desenvolvimento.

A definição da licença será realizada posteriormente.
