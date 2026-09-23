# Fitness Backend

Backend da plataforma de fitness personalizada, desenvolvido com Java, Spring Boot, MongoDB e Google Gemini.

## Status do projeto

| Parte | Módulo | Status |
|---|---|---|
| 1 | Backend + autenticação | Implementado |
| 2 | Usuário + perfil | Implementado |
| 3 | Exercícios | Implementado |
| 4 | Workout | Implementado |
| 5 | WorkoutSession + SetLog | Implementado |
| 6 | Performance / histórico | Implementado |
| 7 | Inteligência Artificial | Parcialmente implementado |
| 8 | Nutrição | Implementado |
| 9 | IA nutricional | Futuro |

## Stack

- Java 26
- Spring Boot 4.1.1
- Maven
- Spring Web
- Spring Data MongoDB
- Spring Security
- Bean Validation
- Lombok
- Actuator
- JJWT 0.13.0
- Springdoc OpenAPI
- Google GenAI SDK
- MongoDB Atlas

## Estrutura

```text
br.com.fitness
├── ai
├── auth
├── common
├── config
├── exercise
├── nutrition
├── performance
├── profile
├── user
└── workout
```

## Variáveis de ambiente

```text
MONGODB_URI
JWT_SECRET
GEMINI_API_KEY
```

Nunca versionar chaves ou credenciais.

## Endpoints

### Health

```http
GET /api/health
```

### Autenticação

```http
POST /api/auth/register
POST /api/auth/login
```

### Usuário

```http
GET /api/users/me
```

### Perfil de treino

```http
POST /api/profile
GET  /api/profile
PUT  /api/profile
```

### Exercícios

```http
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

```http
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

```http
GET /api/performance/history
GET /api/performance/summary
GET /api/performance/exercise/{exerciseId}
```

### Inteligência Artificial

```http
POST /api/ai/test
GET  /api/ai/analyze-profile
POST /api/ai/generate-workout
```

### Nutrição

#### Alimentos

```http
POST   /api/foods
GET    /api/foods
GET    /api/foods/{id}
GET    /api/foods/category/{category}
GET    /api/foods/search?name=arroz
PUT    /api/foods/{id}
DELETE /api/foods/{id}
```

#### Perfil nutricional

```http
POST   /api/nutrition-profile
GET    /api/nutrition-profile
PUT    /api/nutrition-profile
DELETE /api/nutrition-profile
```

#### Refeições

```http
POST   /api/meals
GET    /api/meals
GET    /api/meals/date/{date}
GET    /api/meals/{id}
PUT    /api/meals/{id}
DELETE /api/meals/{id}
```

#### Itens das refeições

```http
POST   /api/meals/{mealId}/items
GET    /api/meals/{mealId}/items
GET    /api/meals/{mealId}/items/summary
GET    /api/meals/{mealId}/items/{itemId}
PUT    /api/meals/{mealId}/items/{itemId}
DELETE /api/meals/{mealId}/items/{itemId}
```

#### Resumo diário e histórico

```http
GET /api/nutrition/daily?date=2026-09-22
GET /api/nutrition/history?startDate=2026-09-18&endDate=2026-09-22
```

## Autenticação

A API utiliza BCrypt para senhas e JWT para autenticação stateless.

O usuário autenticado é identificado pelo email presente no JWT. O cliente não deve enviar `userId` para determinar a identidade da requisição.

## Arquitetura de treino

```text
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

A camada de performance trabalha com carga, repetições, volume, PR, sessões concluídas e histórico.

## Parte 7 — Inteligência Artificial

A Parte 7 está parcialmente implementada.

### Concluído

- Integração com Gemini
- Contexto baseado no perfil
- Geração de treinos
- Respostas estruturadas
- Validação das respostas
- Uso exclusivo de exercícios existentes no banco
- Uso do histórico de desempenho
- `targetWeight`
- Validação de progressão de carga
- Salvamento do treino gerado

### Pendente

- Avaliação de treinos
- Sugestão de ajustes

### Fluxo

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

A IA não acessa o banco diretamente.

### Limitação atual do Gemini

Durante os testes foi atingida a cota diária do Free Tier:

```text
429 RESOURCE_EXHAUSTED
GenerateRequestsPerDayPerModel-FreeTier
```

A requisição chegou ao Gemini e foi recusada pela cota disponível.

## Parte 8 — Nutrição

A Parte 8 foi implementada sem IA. A IA nutricional fica para a Parte 9.

### Funcionalidades implementadas

- CRUD de alimentos
- Busca de alimentos por nome
- Filtro de alimentos por categoria
- Perfil nutricional do usuário
- Metas calóricas
- Metas de proteínas, carboidratos, gorduras e fibras
- Preferências alimentares
- Restrições alimentares
- CRUD de refeições
- Itens de refeições
- Cálculo proporcional dos nutrientes conforme a quantidade consumida
- Resumo nutricional por refeição
- Resumo nutricional diário
- Cálculo do saldo restante das metas
- Histórico nutricional por período
- Isolamento dos dados por usuário autenticado

### Estrutura

```text
nutrition
├── controller
│   ├── FoodController
│   ├── NutritionProfileController
│   ├── MealController
│   ├── MealItemController
│   ├── DailyNutritionController
│   └── NutritionHistoryController
│
├── dto
├── model
│   ├── Food
│   ├── NutritionProfile
│   ├── Meal
│   └── MealItem
│
├── repository
│   ├── FoodRepository
│   ├── NutritionProfileRepository
│   ├── MealRepository
│   └── MealItemRepository
│
└── service
    ├── FoodService
    ├── NutritionProfileService
    ├── MealService
    ├── MealItemService
    ├── DailyNutritionService
    └── NutritionHistoryService
```

### Fluxo nutricional

```text
Food
  ↓
Meal
  ↓
MealItem
  ↓
Resumo da refeição
  ↓
Resumo diário
  ↓
Histórico nutricional
```

O `MealItem` utiliza os dados nutricionais cadastrados no `Food` e calcula os valores proporcionalmente à quantidade informada.

Os valores de alimentos utilizados nos testes são dados de exemplo para validação técnica do sistema; não constituem uma base nutricional oficial.

### Observação de desempenho

A implementação atual prioriza simplicidade e clareza para o MVP. O histórico diário reutiliza o serviço de resumo diário, o que pode gerar múltiplas consultas ao MongoDB em períodos maiores. Uma futura otimização pode utilizar aggregation do MongoDB para consolidar os dados.

## Parte 9 — IA nutricional

Fica para o futuro.

Planejamento:

- análise de foto do prato
- identificação de alimentos
- estimativa de porções
- cálculo nutricional
- correção manual
- histórico alimentar

A IA nutricional não faz parte do escopo atual.

## Segurança

Implementado:

- BCrypt
- JWT
- autenticação stateless
- secrets por variáveis de ambiente
- identidade baseada no JWT
- MongoDB URI fora do código
- chave Gemini fora do código
- validação de propriedade dos recursos pelo usuário autenticado

## Execução local

### Requisitos

- Java 26
- Maven
- MongoDB Atlas
- IDE Java

### Executar

```bash
mvn spring-boot:run
```

Servidor:

```text
http://localhost:8080
```

Health:

```text
http://localhost:8080/api/health
```

## Próximos passos

1. Voltar à Parte 7.
2. Implementar avaliação de treinos pela IA.
3. Implementar sugestões de ajustes nos treinos.
4. Testar e validar a Parte 7 completa.
5. Manter a Parte 9 — IA nutricional — como etapa futura.

## Git

Commit recomendado para registrar o estado atual:

```bash
git status
git add .
git commit -m "feat: conclui parte 8 de nutricao e atualiza documentacao"
git push origin main
```
