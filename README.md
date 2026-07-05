# 🔧 CarRepair — Sistema de Gestão de Oficina Mecânica

> Projeto acadêmico desenvolvido na disciplina **Arquitetura e Projeto de Software**  
> SENAI FATESG · Análise e Desenvolvimento de Sistemas · 3º Semestre

---

## 👥 Equipe

| Integrante | GitHub | 
|---|---|
| Caio Abreu | [@Caio4breu](https://github.com/Caio4breu) | 
| Cassiano Abreu | [@Nomscodes](https://github.com/Nomscodes) | 
| Gabriel Naoki | [@GabrielNaokiUT](https://github.com/GabrielNaokiUT) |

---

## 📋 Sobre o Sistema

O **CarRepair** gerencia o ciclo completo de atendimento de uma oficina mecânica:

- Cadastro de **clientes** e seus **veículos**
- Cadastro de **mecânicos** responsáveis pela execução técnica
- Cadastro de **usuários** operadores do sistema
- Abertura e acompanhamento de **Ordens de Serviço**
- Registro de **serviços executados** e **peças aplicadas**

---

## 🛠️ Stack Tecnológica

| Camada | Tecnologia | Versão |
|---|---|---|
| Backend | Java + Spring Boot | **Java 21 (LTS)** |
| Banco de Dados | PostgreSQL | UUID, ENUM, pgcrypto |
| Frontend | Angular + TypeScript | Angular 21 |
| Build Backend | Maven | — |
| Build Frontend | Angular CLI + npm | CLI 21 |
| Redução de código | Lombok | @Data, @Getter, @Setter |
| Testes Frontend | Vitest + JSDOM | — |

---

## 🏗️ Arquitetura

### Padrão de Camadas — Backend

Todo domínio segue **obrigatoriamente** o mesmo fluxo:

```
Controller → Service → Validation → Repository → Model
Response  ← Controller ← Mapper (Model → DTO) ← Service
```

Cada camada possui:
- Uma **interface** (prefixo `I`) que define o contrato
- Uma **classe concreta** que implementa a lógica

### Pacote `core` — Classes Genéricas

| Classe / Interface | Função |
|---|---|
| `BaseModel` | Campos comuns: `id` (UUID), `ativo`, `dataHoraCriacao` |
| `BaseDTO` | Campos `id` e `active` para todas as respostas da API |
| `GenericController` | CRUD completo reutilizável por herança |
| `GenericService` | Orquestração com hook `beforeInsert` customizável |
| `GenericValidation` | Validações reutilizáveis por domínio |
| `GenericMapper` | Conversão bidirecional `Model ↔ DTO` |
| `IGenericRepository` | Estende `JpaRepository` do Spring Data |
| `GlobalExceptionHandler` | Tratamento centralizado de erros — resposta padronizada |

---

## 📁 Estrutura de Pastas

```
projetoArquitetura/
│
├── car-repair/                          # Backend Spring Boot
│   ├── src/main/java/.../car_repair/
│   │   ├── business/                    # Domínios de negócio
│   │   │   ├── clientes/
│   │   │   │   ├── ClienteModel.java
│   │   │   │   ├── ClienteDTO.java
│   │   │   │   ├── IClienteRepository.java
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── IClienteService.java
│   │   │   │   ├── ClienteService.java
│   │   │   │   ├── IClienteValidation.java
│   │   │   │   ├── ClienteValidation.java
│   │   │   │   ├── IClienteMapper.java
│   │   │   │   ├── ClienteMapper.java
│   │   │   │   └── ClienteController.java
│   │   │   ├── veiculos/                # mesma estrutura (11 arquivos)
│   │   │   ├── servicos/                # mesma estrutura (11 arquivos)
│   │   │   ├── ordens_servico/          # mesma estrutura (11 arquivos)
│   │   │   ├── mecanicos/               # mesma estrutura (11 arquivos)
│   │   │   └── usuarios/                # mesma estrutura (11 arquivos)
│   │   └── core/                        # Classes genéricas reutilizáveis
│   │       ├── BaseModel.java
│   │       ├── BaseDTO.java
│   │       ├── GenericController.java
│   │       ├── IGenericController.java
│   │       ├── GenericService.java
│   │       ├── IGenericService.java
│   │       ├── GenericValidation.java
│   │       ├── IGenericValidation.java
│   │       ├── GenericMapper.java
│   │       ├── IGenericMapper.java
│   │       ├── IGenericRepository.java
│   │       ├── GlobalExceptionHandler.java
│   │       ├── ErrorResponse.java
│   │       └── CorsConfig.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── .env                             # Variáveis de ambiente (não versionar)
│   └── pom.xml
│
└── oficina-angular/                     # Frontend Angular
    └── src/app/
        ├── core/
        │   ├── http/                    # ApiBaseService + HttpErrorInterceptor
        │   └── validacoes/              # Validações reutilizáveis
        ├── modelos/                     # Interfaces TypeScript por domínio
        │   ├── cliente.ts
        │   ├── veiculo.ts
        │   ├── mecanico.ts
        │   ├── usuario.ts
        │   └── ordem-servico.ts
        ├── paginas/                     # Componentes de tela (standalone)
        │   ├── clientes/
        │   ├── veiculos/
        │   ├── mecanicos/
        │   ├── ordens-servico/
        │   ├── usuarios/
        │   └── dashboard/
        ├── services/dominios/           # Services REST por domínio
        ├── shared/mensagens/            # MensagemService + MensagensComponent
        └── app.routes.ts
```

---

## 🌐 Domínios Implementados

| Domínio | Endpoint | Backend | Frontend |
|---|---|---|---|
| Cliente | `/clientes` | ✅ | ✅ |
| Veículo | `/veiculos` | ✅ | ✅ |
| Serviço | `/servicos` | ✅ | ✅ |
| Ordem de Serviço | `/ordens-servico` | ✅ | ✅ |
| Mecânico | `/mecanicos` | ✅ | ✅ |
| Usuário | `/usuarios` | ✅ | ✅ |

---

## 🗄️ Banco de Dados

### Tecnologia
- PostgreSQL com extensão `pgcrypto`
- Chave primária **UUID** (`gen_random_uuid()`)
- **ENUM** para status da Ordem de Serviço
- **TIMESTAMPTZ** para campos de auditoria temporal

### Tabelas

| Tabela | Descrição |
|---|---|
| `usuarios` | Operadores do sistema (login, perfil, senha_hash) |
| `clientes` | Proprietários dos veículos |
| `veiculos` | Automóveis vinculados a clientes |
| `mecanicos` | Profissionais técnicos da oficina |
| `ordens_servico` | Tabela central — vincula cliente, veículo, usuário e mecânico |
| `ordem_servico_servicos` | Itens de mão de obra executados na OS |
| `ordem_servico_pecas_aplicadas` | Itens de peças utilizadas na OS |

### ENUM de Status da OS

```sql
CREATE TYPE status_ordem_servico AS ENUM (
  'aberta',
  'em_execucao',
  'finalizada',
  'cancelada'
);
```

### Relacionamentos

```
clientes       1:N → veiculos
clientes       1:N → ordens_servico
usuarios       1:N → ordens_servico
mecanicos      1:N → ordens_servico
veiculos       1:N → ordens_servico
ordens_servico 1:N → ordem_servico_servicos
ordens_servico 1:N → ordem_servico_pecas_aplicadas
```

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos

- Java 21 (LTS)
- Maven
- Node.js + Angular CLI 21
- PostgreSQL 17

---

### 1. Clonar o repositório

```bash
git clone https://github.com/Caio4breu/projetoArquitetura
cd projetoArquitetura
```

---

### 2. Configurar o banco de dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE car_repair;
```

---

### 3. Configurar variáveis de ambiente (Backend)

Na pasta `car-repair/`, crie um arquivo `.env` com base no exemplo abaixo:

```env
# .env.example — copie para .env e preencha com seus dados

DB_NAME=car_repair
DB_USERNAME=postgres
DB_PASSWORD=sua_senha_aqui
```

> ⚠️ O arquivo `.env` **não deve ser versionado**. Adicione ao `.gitignore`.

---

### 4. Rodar o Backend

```bash
cd car-repair
mvn clean install
mvn spring-boot:run
```

API disponível em: `http://localhost:9081`

---

### 5. Rodar o Frontend

```bash
cd oficina-angular
npm install
npm start
```

Frontend disponível em: `http://localhost:4200`

> **Observação:** Quando o backend não está rodando, os erros capturados pelo `HttpErrorInterceptor` exibem mensagens amigáveis no frontend — comportamento esperado.

---

## 🗂️ Documentação Arquitetural

| Documento | Status | Conteúdo |
|---|---|---|
| [DAS v4.0](./docs/DAS_CarRepair_v4_0.docx) | Em vigor | Visão geral, stack, camadas, domínios, banco |
| [ADR-001](./docs/ADR-001_CarRepair.docx) | Em vigor | Definição da stack e arquitetura base |
| [ADR-002](./docs/ADR-002_CarRepair.docx) | Em vigor | BaseModel e Spring Data JPA |
| [ADR-003](./docs/ADR-003_CarRepair.docx) | Em vigor | Generics, mappers e tratamento global de exceções |
| [ADR-004](./docs/ADR-004_CarRepair.docx) | Em vigor | Implementação dos domínios Mecânico e Usuário |

---

## 📌 Tratamento de Erros — Frontend

| Status HTTP | Mensagem exibida |
|---|---|
| `0` | Falha de rede — verifique sua conexão |
| `400` | Dados inválidos — usa mensagem do backend |
| `401` | Sessão expirada |
| `403` | Acesso negado |
| `404` | Recurso não encontrado |
| `5xx` | Erro interno do servidor |

---

## 📄 Licença

Projeto acadêmico — SENAI FATESG · Goiânia, GO · 2025
