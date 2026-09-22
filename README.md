# 🏋️ Fitness Backend

> Backend da plataforma de treino e nutrição personalizada, construída com **Java + Spring Boot + MongoDB**, preparada para integração com aplicativos **Android e iOS** e futura utilização de **Inteligência Artificial**.

---

## 📌 Sobre o projeto

O **Fitness Backend** é a API responsável por centralizar usuários, autenticação, perfis, exercícios, treinos, histórico de desempenho e, futuramente, nutrição e inteligência artificial.

A proposta não é ser apenas mais um aplicativo de academia. O sistema está sendo construído para **conhecer o usuário e adaptar sua experiência** com base em objetivos, experiência, disponibilidade, equipamentos, preferências e evolução.

### 🎯 Objetivo

Criar uma plataforma capaz de conectar:

- 👤 Perfil e objetivos do usuário
- 🏋️ Treinos personalizados
- 📈 Histórico e evolução
- 🍎 Nutrição
- 🤖 Inteligência Artificial
- 📱 Aplicativo Android e iOS

O backend será independente do aplicativo, permitindo que diferentes clientes consumam a mesma API.

---

# 🏗️ Stack tecnológica

| Tecnologia | Utilização |
|---|---|
| ☕ Java | Linguagem principal |
| 🌱 Spring Boot | Framework do backend |
| 🔐 Spring Security | Segurança e autenticação |
| 🔑 JWT | Autenticação baseada em tokens |
| 🔒 BCrypt | Hash seguro de senhas |
| 🍃 MongoDB Atlas | Banco de dados |
| 📦 Spring Data MongoDB | Persistência |
| ✅ Jakarta Validation | Validação das requisições |
| 🛠️ Maven | Gerenciamento e build |
| 🧩 Lombok | Redução de código repetitivo |
| 📊 Spring Boot Actuator | Monitoramento |
| 📱 React Native + Expo | Cliente mobile planejado |
| 🤖 Gemini | IA planejada |

---

# 🧱 Arquitetura atual

A aplicação está sendo organizada por responsabilidades:

```text
src/main/java/br/com/fitness
│
├── auth
│   ├── controller
│   │   └── AuthController.java
│   ├── dto
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   ├── RegisterRequest.java
│   │   └── UserResponse.java
│   ├── security
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtService.java
│   └── service
│       └── AuthService.java
│
├── common
│   ├── controller
│   │   └── HealthController.java
│   ├── dto
│   │   └── ApiErrorResponse.java
│   └── exception
│       └── GlobalExceptionHandler.java
│
├── config
│   ├── PasswordConfig.java
│   └── SecurityConfig.java
│
├── exercise
│   ├── controller
│   │   └── ExerciseController.java
│   ├── dto
│   │   ├── CreateExerciseRequest.java
│   │   ├── ExerciseResponse.java
│   │   └── UpdateExerciseRequest.java
│   ├── model
│   │   └── Exercise.java
│   ├── repository
│   │   └── ExerciseRepository.java
│   └── service
│       └── ExerciseService.java
│
├── profile
│   ├── controller
│   │   └── UserProfileController.java
│   ├── dto
│   │   ├── CreateUserProfileRequest.java
│   │   ├── UpdateUserProfileRequest.java
│   │   └── UserProfileResponse.java
│   ├── model
│   │   └── UserProfile.java
│   ├── repository
│   │   └── UserProfileRepository.java
│   └── service
│       └── UserProfileService.java
│
└── user
    ├── controller
    │   └── UserController.java
    ├── model
    │   └── User.java
    └── repository
        └── UserRepository.java
```

A arquitetura será expandida conforme novas funcionalidades forem implementadas.

---

# 🔐 Autenticação

A autenticação já está implementada.

## Cadastro

```http
POST /api/auth/register
```

Exemplo:

```json
{
  "name": "João",
  "email": "joao@fitness.com",
  "password": "123456"
}
```

O backend:

1. Valida os dados.
2. Verifica se o e-mail já existe.
3. Gera um hash BCrypt da senha.
4. Cria o usuário.
5. Salva no MongoDB Atlas.
6. Retorna os dados públicos do usuário.

### Exemplo de resposta

```json
{
  "id": "xxxxxxxx",
  "name": "João",
  "email": "joao@fitness.com",
  "role": "USER"
}
```

> 🔒 O `passwordHash` não é retornado pela API.

---

## Login

```http
POST /api/auth/login
```

Exemplo:

```json
{
  "email": "joao@fitness.com",
  "password": "123456"
}
```

Resposta:

```json
{
  "token": "eyJ...",
  "type": "Bearer"
}
```

O token deve ser enviado nas rotas protegidas através do header:

```http
Authorization: Bearer SEU_TOKEN
```

---

# 🔑 JWT

A autenticação utiliza JWT com sessão **STATELESS**.

Fluxo:

```text
┌──────────────┐
│    Cliente   │
└──────┬───────┘
       │ login
       ▼
┌──────────────┐
│ AuthService  │
└──────┬───────┘
       │ BCrypt
       ▼
┌──────────────┐
│   MongoDB    │
└──────┬───────┘
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

```text
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

---

# 👤 Usuário e perfil

O usuário autenticado pode acessar seus próprios dados através da identidade extraída do JWT.

### Usuário

```http
GET /api/users/me
```

Retorna os dados públicos do usuário autenticado.

### Perfil

```http
POST /api/profile
GET /api/profile
PUT /api/profile
```

O perfil contém informações como:

- Data de nascimento
- Altura
- Peso
- Gênero opcional
- Objetivo
- Nível de experiência
- Dias disponíveis para treino
- Duração do treino
- Local de treino
- Equipamentos disponíveis
- Exercícios preferidos
- Restrições e observações

A criação duplicada de perfil é bloqueada e os dados recebem validação antes de serem persistidos.

---

# 🏋️ Banco de exercícios

O módulo de exercícios foi concluído na **Parte 3** do desenvolvimento.

Cada exercício possui:

```text
id
name
description
primaryMuscleGroup
secondaryMuscleGroups
equipment
difficulty
instructions
tips
commonMistakes
alternatives
createdAt
updatedAt
```

O backend possui CRUD completo:

- Criar exercício
- Listar exercícios
- Buscar por ID
- Atualizar exercício
- Excluir exercício

Também existem filtros por:

- Grupo muscular principal
- Equipamento
- Dificuldade
- Grupo muscular + equipamento

### Normalização

Os valores categóricos são normalizados pelo backend antes de serem armazenados e consultados.

Por exemplo:

```text
peito
Peito
 PEITO 
```

são tratados como:

```text
PEITO
```

O mesmo princípio é aplicado a equipamentos, dificuldades e filtros combinados.

Os textos descritivos, como nome, descrição e instruções, continuam preservando sua escrita normal, apenas removendo espaços desnecessários nas extremidades.

---

# 🗄️ Banco de dados

O projeto utiliza **MongoDB Atlas**.

Banco:

```text
fitness
```

Coleções atuais:

```text
users
user_profiles
exercises
```

### `User`

```text
id
name
email
passwordHash
role
createdAt
updatedAt
```

### `UserProfile`

```text
id
userId
birthDate
height
weight
gender
goal
experienceLevel
trainingDays
trainingDuration
trainingLocation
availableEquipment
preferredExercises
restrictions
createdAt
updatedAt
```

### `Exercise`

```text
id
name
description
primaryMuscleGroup
secondaryMuscleGroups
equipment
difficulty
instructions
tips
commonMistakes
alternatives
createdAt
updatedAt
```

O acesso ao MongoDB é feito através da variável de ambiente:

```text
MONGODB_URI
```

A aplicação não deve armazenar credenciais do banco diretamente no código.

---

# ⚙️ Configuração

## Requisitos

Antes de executar o projeto, tenha instalado:

- Java
- Maven
- IntelliJ IDEA ou outra IDE compatível
- MongoDB Atlas
- Git

---

## Variáveis de ambiente

A aplicação utiliza:

```text
MONGODB_URI
JWT_SECRET
```

### `MONGODB_URI`

String de conexão fornecida pelo MongoDB Atlas.

### `JWT_SECRET`

Chave utilizada para assinar os tokens JWT.

> ⚠️ Nunca publique essas variáveis no GitHub.

---

# 📝 `application.yml`

A configuração utiliza variáveis de ambiente:

```yaml
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

---

# 🌐 Endpoints atuais

## Health Check

### `GET /api/health`

Endpoint público para verificar se a API está funcionando.

**Público:** ✅

Resposta:

```text
Fitness Backend funcionando!
```

---

## Autenticação

### `POST /api/auth/register`

Cria uma nova conta.

**Público:** ✅

### `POST /api/auth/login`

Autentica um usuário e retorna um JWT.

**Público:** ✅

---

## Usuário

### `GET /api/users/me`

Retorna os dados do usuário autenticado.

**Autenticação:** 🔒 Bearer JWT

---

## Perfil

### `POST /api/profile`

Cria o perfil do usuário autenticado.

**Autenticação:** 🔒 Bearer JWT

### `GET /api/profile`

Retorna o perfil do usuário autenticado.

**Autenticação:** 🔒 Bearer JWT

### `PUT /api/profile`

Atualiza o perfil do usuário autenticado.

**Autenticação:** 🔒 Bearer JWT

---

## Exercícios

### `POST /api/exercises`

Cadastra um exercício.

**Autenticação:** 🔒 Bearer JWT

### `GET /api/exercises`

Lista todos os exercícios em ordem alfabética.

**Autenticação:** 🔒 Bearer JWT

### `GET /api/exercises/{id}`

Busca um exercício pelo ID.

**Autenticação:** 🔒 Bearer JWT

### `GET /api/exercises/muscle/{primaryMuscleGroup}`

Busca exercícios pelo grupo muscular principal.

**Autenticação:** 🔒 Bearer JWT

Exemplo:

```http
GET /api/exercises/muscle/peito
```

### `GET /api/exercises/equipment/{equipment}`

Busca exercícios pelo equipamento.

**Autenticação:** 🔒 Bearer JWT

Exemplo:

```http
GET /api/exercises/equipment/halter
```

### `GET /api/exercises/difficulty/{difficulty}`

Busca exercícios pela dificuldade.

**Autenticação:** 🔒 Bearer JWT

Exemplo:

```http
GET /api/exercises/difficulty/iniciante
```

### `GET /api/exercises/filter?muscle={muscle}&equipment={equipment}`

Filtra simultaneamente por grupo muscular principal e equipamento.

**Autenticação:** 🔒 Bearer JWT

Exemplo:

```http
GET /api/exercises/filter?muscle=peito&equipment=barra
```

### `PUT /api/exercises/{id}`

Atualiza um exercício.

**Autenticação:** 🔒 Bearer JWT

### `DELETE /api/exercises/{id}`

Exclui um exercício.

**Autenticação:** 🔒 Bearer JWT

Resposta de sucesso:

```http
204 No Content
```

---

# ⚠️ Tratamento de erros

As validações e erros de regras de negócio são centralizados pelo `GlobalExceptionHandler`.

A resposta segue o formato:

```json
{
  "status": 400,
  "message": "Exercício não encontrado",
  "timestamp": "2026-09-22T16:00:00"
}
```

Para erros de validação, as mensagens dos campos inválidos são agrupadas em uma resposta única.

> Observação: atualmente `RuntimeException` é tratado como `400 Bad Request`. A diferenciação futura entre `400`, `404`, `401`, `403` e `500` será feita através de exceções específicas.

---

# 🧪 Testes realizados

## MongoDB

- [x] Conexão com MongoDB Atlas
- [x] Autenticação no Atlas
- [x] Persistência de usuários
- [x] Persistência de perfis
- [x] Persistência de exercícios

## Cadastro e autenticação

- [x] `POST /api/auth/register`
- [x] Validação de dados
- [x] Verificação de e-mail existente
- [x] BCrypt
- [x] Salvamento no MongoDB
- [x] Resposta sem `passwordHash`
- [x] `POST /api/auth/login`
- [x] Verificação da senha
- [x] Geração de JWT
- [x] Bearer Token
- [x] Rotas protegidas
- [x] Sessão STATELESS

## Usuário e perfil

- [x] `GET /api/users/me`
- [x] `POST /api/profile`
- [x] `GET /api/profile`
- [x] `PUT /api/profile`
- [x] Validações de perfil
- [x] Bloqueio de criação de perfil duplicado
- [x] Identidade baseada no JWT

## Exercícios

- [x] Cadastro de exercícios
- [x] Listagem
- [x] Busca por ID
- [x] Busca por grupo muscular
- [x] Busca por equipamento
- [x] Busca por dificuldade
- [x] Filtro combinado
- [x] Atualização
- [x] Exclusão
- [x] DTOs de entrada e saída
- [x] Validação dos requests
- [x] Normalização de valores categóricos
- [x] Tratamento global de erros

## Segurança

- [x] Spring Security
- [x] JWT Filter
- [x] Bearer Token
- [x] Rotas protegidas
- [x] Sessão STATELESS
- [x] Secret via variável de ambiente
- [x] Senhas com BCrypt

---

# 🚧 Roadmap

## Parte 1 — Backend base e autenticação

**Status: ✅ Concluída**

- [x] Spring Boot
- [x] MongoDB Atlas
- [x] Estrutura inicial
- [x] Usuários
- [x] Cadastro
- [x] BCrypt
- [x] Login
- [x] JWT
- [x] Spring Security
- [x] Variáveis de ambiente

---

## Parte 2 — Usuário e perfil

**Status: ✅ Concluída**

- [x] `GET /api/users/me`
- [x] `UserProfile`
- [x] Criação de perfil
- [x] Consulta de perfil
- [x] Atualização de perfil
- [x] Objetivo
- [x] Experiência
- [x] Altura
- [x] Peso
- [x] Dias disponíveis
- [x] Duração do treino
- [x] Local de treino
- [x] Equipamentos disponíveis
- [x] Preferências
- [x] Limitações e observações
- [x] Validações
- [x] Proteção por JWT

---

## Parte 3 — Exercícios

**Status: ✅ Concluída**

- [x] Cadastro de exercícios
- [x] Grupos musculares
- [x] Equipamentos
- [x] Dificuldade
- [x] Instruções
- [x] Dicas
- [x] Erros comuns
- [x] Alternativas
- [x] CRUD completo
- [x] DTOs
- [x] Validação
- [x] Filtros
- [x] Normalização dos valores categóricos
- [x] Tratamento global de erros

### Próximas evoluções do módulo

- [ ] Imagens
- [ ] Vídeos
- [ ] Mídia em storage externo
- [ ] Alternativas vinculadas por ID
- [ ] Exceções específicas para `404`

---

## Parte 4 — Treinos

**Status: 🔜 Próxima etapa**

A estrutura será separada entre planejamento e execução:

```text
Workout
   ↓
Treino planejado

WorkoutSession
   ↓
Execução de um treino

SetLog
   ↓
Registro de cada série executada
```

Planejado:

- [ ] Criar treino
- [ ] Editar treino
- [ ] Excluir treino
- [ ] Adicionar exercícios
- [ ] Reordenar exercícios
- [ ] Séries
- [ ] Repetições
- [ ] Descanso
- [ ] Registro de cargas
- [ ] Execução do treino
- [ ] Histórico

---

## Parte 5 — Evolução

- [ ] Volume de treino
- [ ] Cargas
- [ ] Repetições
- [ ] Frequência
- [ ] PRs
- [ ] Gráficos
- [ ] Comparação de desempenho

---

## Parte 6 — Inteligência Artificial

- [ ] Integração com Gemini
- [ ] Contexto baseado no perfil
- [ ] Geração de treinos
- [ ] Avaliação de treinos
- [ ] Sugestão de ajustes
- [ ] Respostas estruturadas
- [ ] Validação das respostas da IA
- [ ] Uso exclusivo de exercícios existentes no banco

A IA não deverá acessar o banco diretamente.

O fluxo planejado será:

```text
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

---

## Parte 7 — Nutrição

- [ ] Perfil nutricional
- [ ] Metas calóricas
- [ ] Macronutrientes
- [ ] Diário alimentar
- [ ] Refeições
- [ ] Alimentos
- [ ] Base nutricional
- [ ] Progresso alimentar

---

## Parte 8 — IA nutricional

- [ ] Análise de foto do prato
- [ ] Identificação de alimentos
- [ ] Estimativa de porções
- [ ] Cálculo nutricional
- [ ] Correção manual
- [ ] Histórico alimentar

> As informações nutricionais geradas por IA serão tratadas como **estimativas**, permitindo correção pelo usuário.

---

# 📱 Aplicativo

O backend será consumido inicialmente por um aplicativo desenvolvido com:

```text
React Native
+
Expo
+
TypeScript
```

O objetivo é oferecer suporte desde o início para:

- 🤖 Android
- 🍎 iOS

O backend permanece independente da plataforma.

---

# 🔐 Boas práticas de segurança

O projeto segue algumas regras desde o início:

- 🔒 Senhas nunca são armazenadas em texto puro.
- 🔐 Senhas são protegidas com BCrypt.
- 🎫 Autenticação utiliza JWT.
- 🌐 Rotas protegidas exigem autenticação.
- 🚫 Secrets não ficam no código.
- 🗄️ Credenciais do MongoDB ficam em variáveis de ambiente.
- 📱 O cliente não envia `userId` para acessar os próprios dados; a identidade é obtida através do JWT.
- 🧹 Dados temporários de teste devem ser removidos antes de cada release.

---

# 🐳 Infraestrutura planejada

A infraestrutura futura seguirá aproximadamente:

```text
                 ┌─────────────────┐
                 │   Mobile App    │
                 │ Android / iOS   │
                 └────────┬────────┘
                          │
                          ▼
                    ┌───────────┐
                    │ Cloudflare│
                    └─────┬─────┘
                          │
                          ▼
                   ┌───────────────┐
                   │     Nginx     │
                   └───────┬───────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ Spring Boot API │
                  │     Docker      │
                  └───────┬─────────┘
                          │
                  ┌───────┼──────────┐
                  ▼       ▼          ▼
             MongoDB   Gemini     Storage
              Atlas      AI       de mídia
```

A hospedagem planejada do backend será em uma **VPS da Hostinger**.

---

# 📂 Organização do desenvolvimento

O projeto está sendo desenvolvido de forma incremental.

Cada etapa deve:

1. Criar uma funcionalidade.
2. Testar localmente.
3. Validar integração.
4. Limpar código temporário.
5. Atualizar este README.
6. Criar um commit organizado.
7. Enviar para o GitHub.

---

# 📌 Estado atual

**Versão:** `Parte 3`

**Status:** 🟢 Backend core funcional + módulos de usuário, perfil e exercícios concluídos

O sistema atualmente consegue:

```text
Criar usuário
     ↓
Salvar no MongoDB
     ↓
Proteger senha com BCrypt
     ↓
Fazer login
     ↓
Gerar JWT
     ↓
Autenticar requisições
     ↓
Criar e gerenciar perfil
     ↓
Criar e gerenciar exercícios
     ↓
Filtrar exercícios
     ↓
Validar entradas
     ↓
Padronizar categorias
     ↓
Retornar erros estruturados
```

### Próximo módulo

A próxima etapa será a **Parte 4 — Treinos (`Workout`)**.

A arquitetura será separada entre:

```text
Workout
   ↓
Treino planejado

WorkoutSession
   ↓
Execução de um treino

SetLog
   ↓
Registro de cada série executada
```

Essa separação permitirá futuramente trabalhar com histórico, evolução, cargas, repetições, volume, PRs e IA sem misturar planejamento com execução.

---

# 👨‍💻 Desenvolvimento

Projeto desenvolvido como uma plataforma real de treino e nutrição personalizada, com foco em:

- Engenharia de software
- Backend
- Segurança
- Mobile
- Banco de dados
- Inteligência Artificial
- Experiência do usuário
- Escalabilidade

---

## 📜 Licença

Projeto em desenvolvimento.

A definição da licença será realizada posteriormente.
