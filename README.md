# Criação de Diagrama Entidade-Relacionamento e Modelo Lógico do Banco de Dados

Este documento descreve a criação do Diagrama Entidade-Relacionamento (ER) e do Modelo Lógico do Banco de Dados, com base na regra de negócio proposta e no diagrama conceitual elaborado previamente para o sistema de gerenciamento de usuários e tarefas.

## 1. Diagrama Entidade-Relacionamento (ER)

O Diagrama ER é utilizado para representar de forma visual as entidades do sistema, seus atributos e os relacionamentos entre elas. O diagrama a seguir foi criado com base na regra de negócio definida para o sistema.

### 1.1 Entidades

- **Usuário**
  - `id` (PK) - Identificador único do usuário.
  - `nome` - Nome do usuário.
  - `email` - E-mail do usuário.

- **Tarefa**
  - `id` (PK) - Identificador único da tarefa.
  - `usuario_id` (FK) - Identificador do usuário responsável pela tarefa.
  - `descricao` - Descrição detalhada da tarefa.
  - `setor` - Setor relacionado à tarefa.
  - `prioridade` - Prioridade da tarefa (Baixa, Média, Alta).
  - `status` - Status atual da tarefa (Pendente, Em Progresso, Concluída).
  - `data_criacao` - Data de criação da tarefa.

### 1.2 Relacionamentos

- **Usuário - Tarefa**
  - Um usuário pode criar várias tarefas, mas cada tarefa é associada a um único usuário.
  - O relacionamento entre as entidades é de **1 para N** (um usuário pode ter várias tarefas).
  - A chave estrangeira `usuario_id` na tabela `tarefas` referencia a chave primária `id` na tabela `usuarios`.

---

## 2. Modelo Lógico do Banco de Dados

O Modelo Lógico é um diagrama mais detalhado, que descreve as tabelas e seus relacionamentos no banco de dados, utilizando um modelo relacional. O modelo lógico do banco de dados para o sistema é o seguinte:

### 2.1 Tabelas

- **Tabela `usuarios`**
  - `id` (INT, AUTO_INCREMENT) [PK] - Identificador único do usuário.
  - `nome` (VARCHAR(100)) - Nome do usuário.
  - `email` (VARCHAR(100), UNIQUE) - E-mail do usuário.

- **Tabela `tarefas`**
  - `id` (INT, AUTO_INCREMENT) [PK] - Identificador único da tarefa.
  - `usuario_id` (BIGINT) [FK] - Identificador do usuário responsável pela tarefa.
  - `descricao` (TEXT) - Descrição da tarefa.
  - `setor` (VARCHAR(100)) - Setor ou área relacionada à tarefa.
  - `prioridade` (VARCHAR(50)) - Prioridade da tarefa.
  - `status` (VARCHAR(50)) - Status da tarefa.
  - `data_criacao` (DATE) - Data de criação da tarefa.

### 2.2 Relacionamentos

- **Usuário - Tarefa**
  - Relacionamento de **1 para N** entre `usuarios` e `tarefas`, onde um usuário pode ter várias tarefas.
  - A chave estrangeira `usuario_id` na tabela `tarefas` referencia a tabela `usuarios`.

---

## 3. Diagrama Entidade-Relacionamento em Mermaid

Abaixo está o código para gerar o diagrama ER usando **Mermaid**:

```mermaid
erDiagram
    USUARIOS {
        INT id PK
        VARCHAR nome
        VARCHAR email
    }
    TAREFAS {
        INT id PK
        LONG usuario_id FK
        TEXT descricao
        VARCHAR setor
        VARCHAR prioridade
        VARCHAR status
        DATE data_criacao
    }

    USUARIOS ||--o{ TAREFAS : "cria"
 ```