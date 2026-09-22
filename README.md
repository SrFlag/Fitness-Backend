# Fitness Backend

Backend da plataforma de fitness personalizada, desenvolvido com Java,
Spring Boot, MongoDB e Google Gemini.

## Status do projeto

  Parte   Módulo                    Status
  ------- ------------------------- ---------------------------
  1       Backend + autenticação    Implementado
  2       Usuário + perfil          Implementado
  3       Exercícios                Implementado
  4       Workout                   Implementado
  5       WorkoutSession + SetLog   Implementado
  6       Performance / histórico   Implementado
  7       Inteligência Artificial   Parcialmente implementado
  8       Nutrição                  Em desenvolvimento
  9       IA nutricional            Futuro

## Stack

-   Java 26
-   Spring Boot 4.1.1
-   Maven
-   Spring Web
-   Spring Data MongoDB
-   Spring Security
-   Bean Validation
-   Lombok
-   Actuator
-   JJWT 0.13.0
-   Springdoc OpenAPI
-   Google GenAI SDK
-   MongoDB Atlas

## Estrutura

``` text
br.com.fitness
├── ai
├── auth
├── common
├── config
├── exercise
├── performance
├── profile
├── user
└── workout
```

## Variáveis de ambiente

``` text
MONGODB_URI
JWT_SECRET
GEMINI_API_KEY
```

Nunca versionar chaves ou credenciais.

## Endpoints

### Health

``` http
GET /api/health
```

### Autenticação

``` http
POST /api/auth/register
POST /api/auth/login
```

### Usuário

``` http
GET /api/users/me
```

### Perfil

``` http
POST /api/profile
GET /api/profile
PUT /api/profile
```

### Exercícios

``` http
POST   /api/exercises
GET    /api/exercises
GET    /api/exercises/{id}
GET    /api/exercises/muscle/{primaryMuscleGroup}
GET    /api/exercises/equipment/{equipment}
GET    /api/exercises/difficulty/{difficulty}
GET    /api/exercises/filter?muscle=PEITO&equipment=BARRA
PUT    /api/exercises/{id}
DELETE /api/exercises/{id}
```

### Workout e execução

``` http
POST /api/workouts/{workoutId}/exercises
GET  /api/workouts/{workoutId}/exercises
PUT  /api/workouts/{workoutId}/exercises/{workoutExerciseId}

POST /api/workout-sessions
PUT  /api/workout-sessions/{sessionId}/finish
GET  /api/workout-sessions
GET  /api/workout-sessions/{sessionId}

POST /api/workout-sessions/{sessionId}/sets/{workoutExerciseId}
GET  /api/workout-sessions/{sessionId}/sets
```

### Performance

``` http
GET /api/performance/history
GET /api/performance/summary
GET /api/performance/exercise/{exerciseId}
```

## Autenticação

A API utiliza BCrypt para senhas e JWT para autenticação stateless.

O usuário autenticado é identificado pelo email presente no JWT. O
cliente não deve enviar `userId` para determinar a identidade da
requisição.

## Arquitetura de treino

``` text
Workout
   ↓
WorkoutExercise
   ↓
Exercise

Workout
   ↓
WorkoutSession
   ↓
SetLog
```

`WorkoutExercise.repetitions` é `String` no modelo atual.

Uma sessão finalizada não deve aceitar novas séries.

## Performance

A camada de performance trabalha com carga, repetições, volume, PR,
sessões concluídas e histórico.

## Parte 7 --- Inteligência Artificial

A Parte 7 está parcialmente implementada.

### Concluído

-   Integração com Gemini
-   Contexto baseado no perfil
-   Geração de treinos
-   Respostas estruturadas
-   Validação das respostas
-   Uso exclusivo de exercícios existentes no banco
-   Uso do histórico de desempenho
-   `targetWeight`
-   Validação de progressão de carga
-   Salvamento do treino gerado

### Pendente

-   Avaliação de treinos
-   Sugestão de ajustes

### Endpoints

``` http
POST /api/ai/test
GET  /api/ai/analyze-profile
POST /api/ai/generate-workout
```

### Fluxo

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

A IA não acessa o banco diretamente.

### Limitação atual do Gemini

Durante os testes foi atingida a cota diária do Free Tier:

``` text
429 RESOURCE_EXHAUSTED
GenerateRequestsPerDayPerModel-FreeTier
```

A requisição chegou ao Gemini e foi recusada pela cota disponível.

## Parte 8 --- Nutrição

A próxima etapa é Nutrição, inicialmente sem IA.

Escopo:

-   [ ] Perfil nutricional
-   [ ] Metas calóricas
-   [ ] Macronutrientes
-   [ ] Diário alimentar
-   [ ] Refeições
-   [ ] Alimentos
-   [ ] Base nutricional
-   [ ] Progresso alimentar

Estrutura planejada:

``` text
nutrition
├── controller
├── dto
├── model
├── repository
└── service
```

### Primeiro passo

Criar a base nutricional/alimentos.

Depois:

1.  CRUD de alimentos
2.  Perfil nutricional
3.  Metas calóricas
4.  Macronutrientes
5.  Refeições
6.  Diário alimentar
7.  Progresso alimentar

## Parte 9 --- IA nutricional

Fica para o futuro.

Planejamento:

-   análise de foto do prato
-   identificação de alimentos
-   estimativa de porções
-   cálculo nutricional
-   correção manual
-   histórico alimentar

## Segurança

Implementado:

-   BCrypt
-   JWT
-   autenticação stateless
-   secrets por variáveis de ambiente
-   identidade baseada no JWT
-   MongoDB URI fora do código
-   chave Gemini fora do código

## Execução local

Requisitos:

-   Java 26
-   Maven
-   MongoDB Atlas
-   IDE Java

Executar:

``` bash
mvn spring-boot:run
```

Servidor:

``` text
http://localhost:8080
```

Health:

``` text
http://localhost:8080/api/health
```

## Próximos passos

1.  Implementar Parte 8 --- Nutrição.
2.  Começar pela base nutricional/alimentos.
3.  Depois voltar à Parte 7 para implementar avaliação de treinos e
    sugestão de ajustes.
4.  Parte 9 --- IA nutricional permanece futura.

## Git

Para registrar o estado atual:

``` bash
git status
git add .
git commit -m "feat: finaliza base das partes 1 a 7 e inicia parte 8 de nutricao"
git push origin main
```

O commit registra o estado atual: a integração principal da IA foi
construída, a Parte 7 ainda possui avaliação de treinos e sugestão de
ajustes pendentes, e a Parte 8 passa a ser a etapa atual de
desenvolvimento.
