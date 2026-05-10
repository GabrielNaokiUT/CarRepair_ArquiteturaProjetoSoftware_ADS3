
# Projeto Arquitetura de Software — ADS 3

```text
 █████╗ ██████╗  ██████╗██╗  ██╗██╗████████╗███████╗████████╗██╗   ██╗██████╗  █████╗ 
██╔══██╗██╔══██╗██╔════╝██║  ██║██║╚══██╔══╝██╔════╝╚══██╔══╝██║   ██║██╔══██╗██╔══██╗
███████║██████╔╝██║     ███████║██║   ██║   █████╗     ██║   ██║   ██║██████╔╝███████║
██╔══██║██╔══██╗██║     ██╔══██║██║   ██║   ██╔══╝     ██║   ██║   ██║██╔══██╗██╔══██║
██║  ██║██║  ██║╚██████╗██║  ██║██║   ██║   ███████╗   ██║   ╚██████╔╝██║  ██║██║  ██║
╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝   ╚═╝   ╚══════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝

> Implementação de Sistemas Robustos com Separação de Responsabilidades.
```

## 🏗️ Arquitetura do Sistema

O projeto utiliza o padrão de **Arquitetura em Camadas**, garantindo que a lógica de negócio seja independente da interface e do banco de dados.

```text
┌──────────────────────────────────────────────────────────────────┐
│                           CONTROLLER                             │
│           (Entrada de dados / Endpoints REST / View)             │
└─────────────────────────┬────────────────────────────────────────┘
                          │ Invoca
                          ▼
┌──────────────────────────────────────────────────────────────────┐
│                            SERVICE                               │
│           (Lógica de Negócio / Regras / Validações)              │
└─────────────────────────┬────────────────────────────────────────┘
                          │ Invoca
                          ▼
┌─────────────────────────┴────────────────────────────────────────┐
│                          REPOSITORY                              │
│          (Acesso a Dados / Abstração de Persistência)            │
└─────────────────────────┬────────────────────────────────────────┘
                          │ Persiste
                          ▼
┌──────────────────────────────────────────────────────────────────┐
│                            MODEL                                 │
│           (Entidades de Domínio / Objetos de Valor)              │
└──────────────────────────────────────────────────────────────────┘
```

## 🗺️ Roadmap de Desenvolvimento

```text
╔════════════════════════════════════════════════════════════════════════════╗
║                       PROGRESSO DO PROJETO — ADS 3                         ║
╠═══════════════════════════╦══════════════════════╦═════════════════════════╣
║  FASE 1 — Estrutura       ║  FASE 2 — Lógica     ║  FASE 3 — Refinamento   ║
╠═══════════════════════════╬══════════════════════╬═════════════════════════╣
║ ✅ Setup Maven / Java 17 ║ ✅ CRUD de Contatos  ║  ⬜ Testes Unitários    ║
║ ✅ Estrutura de Pastas   ║ ✅ Regras de Negócio ║  ⬜ Dockerização        ║
║ ✅ Definição do Model    ║ 🔄 Validações        ║  ⬜ Documentação API    ║
║ ✅ Interface Repository  ║ ⬜ Exceções Custom   ║  ⬜ Deploy CI/CD        ║
╚═══════════════════════════╩══════════════════════╩═════════════════════════╝
  ✅ Concluído   🔄 Em andamento   ⬜ Pendente
```

## 📁 Estrutura de Pastas

```text
projeto-arquitetura/
│
├── src/
│   ├── main/
│   │   ├── java/com/equipe/projeto/
│   │   │   ├── controller/      ← Portas de entrada (API/UI)
│   │   │   ├── service/         ← Regras de negócio e validações
│   │   │   ├── repository/      ← Persistência de dados
│   │   │   ├── model/           ← Entidades e POJOs
│   │   │   └── exception/       ← Erros personalizados
│   │   │
│   │   └── resources/           ← Configurações e SQL
│   │
│   └── test/                    ← Testes de integração/unidade
│
├── .gitignore                   ← Exclusão de arquivos temporários
├── pom.xml                      ← Gerenciamento de dependências
└── README.md                    ← Documentação do projeto
```

## 🛠️ Stack Tecnológica

| Camada | Tecnologia | Descrição |
| :--- | :--- | :--- |
| **Linguagem** | Java | Versão 17 (LTS) |
| **Build Tool** | Maven | Gerenciamento de dependências |
| **Framework** | Spring Boot | Facilitação de infraestrutura |
| **Banco de Dados** | H2 / MySQL | Persistência relacional |

## 🚀 Como Rodar Localmente

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/Caio4breu/projetoArquitetura.git](https://github.com/Caio4breu/projetoArquitetura.git)
   ```
2. **Compile o projeto:**
   ```bash
   mvn clean install
   ```
3. **Inicie a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

## 👥 Equipe
* **Caio Abreu**
* **Cassiano Abreu**
* **Gabriel Naoki**
* **Isaque Duarte**
* **Wyllian Mariano**

---
*Este projeto foi desenvolvido como parte dos requisitos acadêmicos da **FATESG SENAI**.*
```
