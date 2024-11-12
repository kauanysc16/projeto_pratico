package com.example.Model;

public class Usuario {
    // Atributos da classe Usuario
    private Long id; // ID único do usuário
    private String nome; // Nome do usuário
    private String email; // E-mail do usuário

    // Construtor com todos os parâmetros (para inicializar os atributos)
    public Usuario(Long id, String nome, String email) {
        this.id = id; // Define o ID do usuário
        this.nome = nome; // Define o nome do usuário
        this.email = email; // Define o e-mail do usuário
    }

    // Getters e Setters: métodos para acessar e modificar os valores dos atributos

    public Long getId() {
        return id; // Retorna o ID do usuário
    }

    public void setId(Long id) {
        this.id = id; // Define o ID do usuário
    }

    public String getNome() {
        return nome; // Retorna o nome do usuário
    }

    public void setNome(String nome) {
        this.nome = nome; // Define o nome do usuário
    }

    public String getEmail() {
        return email; // Retorna o e-mail do usuário
    }

    public void setEmail(String email) {
        this.email = email; // Define o e-mail do usuário
    }
}
