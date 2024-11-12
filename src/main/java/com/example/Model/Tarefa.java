package com.example.Model;

import java.time.LocalDate;

public class Tarefa {
    // Atributos da classe Tarefa
    private Long id; // ID único da tarefa (gerado automaticamente no banco de dados)
    private Long usuarioId; // ID do usuário associado a esta tarefa
    private String descricao; // Descrição detalhada da tarefa
    private String setor; // Setor ou área relacionada à tarefa (ex: TI, Financeiro, etc.)
    private String prioridade; // Prioridade da tarefa (ex: Alta, Média, Baixa)
    private LocalDate dataCriacao; // Data de criação da tarefa
    private String status; // Status atual da tarefa (ex: Em andamento, Concluída)

    // Construtor com todos os parâmetros (usado quando se conhece todos os dados da tarefa)
    public Tarefa(Long id, Long usuarioId, String descricao, String setor, String prioridade, LocalDate dataCriacao,
            String status) {
        this.id = id; // Define o ID da tarefa
        this.usuarioId = usuarioId; // Define o ID do usuário associado
        this.descricao = descricao; // Define a descrição da tarefa
        this.setor = setor; // Define o setor da tarefa
        this.prioridade = prioridade; // Define a prioridade da tarefa
        this.dataCriacao = dataCriacao; // Define a data de criação da tarefa
        this.status = status; // Define o status da tarefa
    }

    // Construtor simplificado (sem o ID, pois ele será gerado automaticamente no banco)
    public Tarefa(String descricao, String setor, String prioridade, String status, Long usuarioId,
            LocalDate dataCriacao) {
        this.descricao = descricao; // Define a descrição da tarefa
        this.setor = setor; // Define o setor da tarefa
        this.prioridade = prioridade; // Define a prioridade da tarefa
        this.status = status; // Define o status da tarefa
        this.usuarioId = usuarioId; // Define o ID do usuário associado
        this.dataCriacao = dataCriacao; // Define a data de criação da tarefa
        this.id = null; // O ID será gerado automaticamente quando a tarefa for inserida no banco
    }

    // Getters e Setters: métodos para acessar e modificar os valores dos atributos

    public Long getId() {
        return id; // Retorna o ID da tarefa
    }

    public void setId(Long id) {
        this.id = id; // Define o ID da tarefa
    }

    public Long getUsuarioId() {
        return usuarioId; // Retorna o ID do usuário associado
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId; // Define o ID do usuário associado
    }

    public String getDescricao() {
        return descricao; // Retorna a descrição da tarefa
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao; // Define a descrição da tarefa
    }

    public String getSetor() {
        return setor; // Retorna o setor da tarefa
    }

    public void setSetor(String setor) {
        this.setor = setor; // Define o setor da tarefa
    }

    public String getPrioridade() {
        return prioridade; // Retorna a prioridade da tarefa
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade; // Define a prioridade da tarefa
    }

    public LocalDate getDataCriacao() {
        return dataCriacao; // Retorna a data de criação da tarefa
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao; // Define a data de criação da tarefa
    }

    public String getStatus() {
        return status; // Retorna o status da tarefa
    }

    public void setStatus(String status) {
        this.status = status; // Define o status da tarefa
    }
}
