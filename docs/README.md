# 📁 docs — Documentação do CarRepair

Documentação arquitetural do projeto **CarRepair**, desenvolvido na disciplina de Arquitetura e Projeto de Software — SENAI FATESG, ADS 3º Semestre.

**Equipe:** Caio Abreu · Cassiano Abreu · Gabriel Naoki · Wyllian Mariano  
**Repositório:** [github.com/Caio4breu/projetoArquitetura](https://github.com/Caio4breu/projetoArquitetura)

---

## 📄 Documentos

### DAS — Documento de Arquitetura de Software (V4.0)

Visão geral e completa da arquitetura do sistema. Cobre a stack tecnológica (Java 21 + Spring Boot + PostgreSQL + Angular 21), o padrão obrigatório de camadas `Controller → Service → Validation → Repository → Model`, o pacote `core` com classes genéricas que eliminam duplicação de CRUD, a estrutura do frontend Angular, o modelo de banco de dados com relacionamentos e ENUMs, e o histórico de evolução do projeto da v1.0 à V4.0. É o ponto de entrada para entender o projeto como um todo.

---

### ADR-001 — Definição da Stack e Arquitetura Base

**Status:** Em vigor · **Aula:** 01

Registra a decisão de adotar Java 21 com Spring Boot no backend, Angular 21 com TypeScript no frontend e PostgreSQL como banco de dados. Define o monolito modular como padrão arquitetural — descartando microsserviços pelo escopo acadêmico — e estabelece o fluxo obrigatório de camadas que todo domínio deve seguir sem exceção.

---

### ADR-002 — BaseModel e Spring Data JPA

**Status:** Em vigor · **Aula:** 02

Registra a decisão de criar a classe abstrata `BaseModel` com `@MappedSuperclass`, centralizando os campos comuns a todas as entidades: `id` (UUID gerado automaticamente), `ativo` (boolean para soft delete) e `dataHoraCriacao` (auditoria). Define o uso de `JpaRepository` do Spring Data como base para todos os repositórios, eliminando SQL manual. Explica a escolha de UUID sobre IDs sequenciais e o mecanismo de soft delete — registros nunca são removidos fisicamente.

---

### ADR-003 — Generics, Mappers e Tratamento Global de Exceções

**Status:** Em vigor · **Aula:** 03

Registra a decisão de introduzir classes genéricas no pacote `core` usando Java Generics, eliminando a duplicação de CRUD entre os domínios. Define a adoção obrigatória de DTOs em todas as respostas da API — nunca expor entidades JPA diretamente. Registra a criação do `GlobalExceptionHandler` com `@RestControllerAdvice`, garantindo que todos os erros retornem no mesmo formato `ErrorResponse`. No frontend, define o `ApiBaseService`, o `HttpErrorInterceptor` global e o `MensagemService`.

---

### ADR-004 — Domínios Mecânico e Usuário + Integração Angular Completa

**Status:** Em vigor · **Autor:** Cassiano Abreu

Registra duas fases de implementação:

**v4.0** — Criação dos domínios Mecânico e Usuário no backend (11 arquivos Java cada, seguindo o padrão genérico do ADR-003) e implementação do módulo de Usuários no frontend Angular do zero. Com esses endpoints disponíveis, a integração com a tela de Mecânicos (já existente) passou a funcionar, e a Atividade 4 de alinhamento da OrdemServico foi desbloqueada.

**V4.0** — Integração do design HTML estático aprovado nos componentes Angular (CSS variables globais, navbar, hero, tabelas estilizadas, formulários colapsáveis com animação `fadeDown`). Todos os 6 domínios passaram a consumir a API real na porta 9081 sem dados simulados. Implementação dos botões **Editar** (PUT) e **Excluir** (DELETE) em todas as páginas. Substituição do sistema de notificações por toast flutuante com animação. Correção da tela de Ordens de Serviço (nomes resolvidos via `forkJoin`, tabela responsiva). Duas branches com PRs abertos: `feat/frontend-carrepair-ui-v2` e `feat/frontend-edit-delete-toast`.

---

## 🗂 Estrutura dos Arquivos

```
docs/
├── README.md                        ← este arquivo
├── DAS_CarRepair_v5.docx            ← Documento de Arquitetura de Software
├── ADR-001_CarRepair.docx           ← Stack e arquitetura base
├── ADR-002_CarRepair.docx           ← BaseModel e Spring Data JPA
├── ADR-003_CarRepair.docx           ← Generics, mappers e exceções
└── ADR-004_CarRepair.docx           ← Domínios Mecânico e Usuário + Angular V4.0
```

---

## 📐 Padrão dos ADRs

Todos os ADRs seguem o formato padrão da disciplina:

| Seção | Conteúdo |
|---|---|
| **Contexto** | Situação que motivou a decisão |
| **Decisão** | O que foi decidido e como foi implementado |
| **Justificativa** | Por que essa abordagem foi escolhida |
| **Alternativas Consideradas** | O que foi descartado e por quê |
| **Consequências** | Impactos positivos e negativos da decisão |
