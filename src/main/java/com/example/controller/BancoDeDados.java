package com.example.controller;

import com.example.Model.Tarefa;
import com.example.Model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {

    // Parâmetros de conexão com o banco de dados PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/to_do_list"; // URL do banco de dados
    private static final String USER = "postgres"; // Usuário do banco de dados
    private static final String PASSWORD = "postgres"; // Senha do banco de dados

    private static Long idUsuarioCounter = 1L; // Contador para o ID do usuário (caso precise gerar manualmente)
    private static Long idTarefaCounter = 1L; // Contador para o ID da tarefa (caso precise gerar manualmente)

    // Método para obter a conexão com o banco de dados
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD); // Cria a conexão com o banco
    }

    // Método para adicionar um novo usuário no banco de dados
    public void adicionarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)"; // Comando SQL para inserir um novo usuário

        usuario.setId(idUsuarioCounter++); // Define o ID do usuário antes de inseri-lo no banco

        try (Connection conn = getConnection(); // Obtém a conexão com o banco
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara o comando SQL

            pstmt.setString(1, usuario.getNome()); // Define o nome do usuário
            pstmt.setString(2, usuario.getEmail()); // Define o email do usuário

            pstmt.executeUpdate(); // Executa o comando de inserção no banco
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
    }

    // Método para buscar um usuário por seu ID no banco de dados
    public Usuario buscarUsuarioPorId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?"; // Comando SQL para buscar um usuário pelo ID
        Usuario usuario = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara o comando SQL
            pstmt.setLong(1, id); // Define o parâmetro do ID
            ResultSet rs = pstmt.executeQuery(); // Executa a consulta

            if (rs.next()) { // Se houver um resultado
                usuario = new Usuario(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
        return usuario; // Retorna o usuário encontrado
    }

    // Método para listar todos os usuários no banco de dados
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>(); // Lista para armazenar os usuários
        String sql = "SELECT * FROM usuarios"; // Comando SQL para selecionar todos os usuários

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta

            while (rs.next()) { // Para cada usuário retornado
                Usuario usuario = new Usuario(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("email"));
                usuarios.add(usuario); // Adiciona o usuário à lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
        return usuarios; // Retorna a lista de usuários
    }

    // Método para adicionar uma nova tarefa no banco de dados
    public void adicionarTarefa(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (descricao, setor, prioridade, status, usuario_id, data_criacao) VALUES (?, ?, ?, ?, ?, ?)"; // Comando SQL para inserir uma nova tarefa

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Prepara o comando SQL

            pstmt.setString(1, tarefa.getDescricao()); // Define a descrição da tarefa
            pstmt.setString(2, tarefa.getSetor()); // Define o setor da tarefa
            pstmt.setString(3, tarefa.getPrioridade()); // Define a prioridade da tarefa
            pstmt.setString(4, tarefa.getStatus()); // Define o status da tarefa
            pstmt.setLong(5, tarefa.getUsuarioId()); // Define o ID do usuário relacionado à tarefa
            pstmt.setDate(6, Date.valueOf(tarefa.getDataCriacao())); // Define a data de criação da tarefa

            pstmt.executeUpdate(); // Executa a inserção no banco

            ResultSet rs = pstmt.getGeneratedKeys(); // Recupera o ID gerado automaticamente pelo banco
            if (rs.next()) {
                tarefa.setId(rs.getLong(1)); // Define o ID gerado para a tarefa
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
    }

    // Método para listar todas as tarefas no banco de dados
    public List<Tarefa> listarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>(); // Lista para armazenar as tarefas
        String sql = "SELECT * FROM tarefas"; // Comando SQL para selecionar todas as tarefas

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta

            while (rs.next()) { // Para cada tarefa retornada
                Tarefa tarefa = new Tarefa(
                        rs.getLong("id"),
                        rs.getLong("usuario_id"), // ID do usuário relacionado à tarefa
                        rs.getString("descricao"),
                        rs.getString("setor"),
                        rs.getString("prioridade"),
                        rs.getDate("data_criacao").toLocalDate(),
                        rs.getString("status"));
                tarefas.add(tarefa); // Adiciona a tarefa à lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
        return tarefas; // Retorna a lista de tarefas
    }

    // Método para excluir uma tarefa do banco de dados
    public void excluirTarefa(Long id) {
        String sql = "DELETE FROM tarefas WHERE id = ?"; // Comando SQL para excluir uma tarefa pelo ID

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara o comando SQL
            pstmt.setLong(1, id); // Define o parâmetro do ID
            pstmt.executeUpdate(); // Executa a exclusão
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
    }

    // Método para atualizar os dados de uma tarefa no banco de dados
    public void atualizarTarefa(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET descricao = ?, setor = ?, prioridade = ?, status = ?, data_criacao = ? WHERE id = ?"; // Comando SQL para atualizar os dados de uma tarefa

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara o comando SQL
            pstmt.setString(1, tarefa.getDescricao()); // Define a descrição da tarefa
            pstmt.setString(2, tarefa.getSetor()); // Define o setor da tarefa
            pstmt.setString(3, tarefa.getPrioridade()); // Define a prioridade da tarefa
            pstmt.setString(4, tarefa.getStatus()); // Define o status da tarefa
            pstmt.setDate(5, java.sql.Date.valueOf(tarefa.getDataCriacao())); // Define a data de criação da tarefa
            pstmt.setLong(6, tarefa.getId()); // Define o ID da tarefa

            pstmt.executeUpdate(); // Executa a atualização no banco
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
    }

    // Método para gerar um novo ID para uma tarefa (caso necessário)
    public Long gerarNovoIdTarefa() {
        return idTarefaCounter++; // Retorna e incrementa o contador de IDs de tarefas
    }

    // Método para remover uma tarefa do banco de dados
    public void removerTarefa(Tarefa tarefa) {
        String sql = "DELETE FROM tarefas WHERE id = ?"; // Comando SQL para excluir uma tarefa pelo ID

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara o comando SQL
            pstmt.setLong(1, tarefa.getId()); // Define o parâmetro do ID
            pstmt.executeUpdate(); // Executa a exclusão
        } catch (SQLException e) {
            e.printStackTrace(); // Caso ocorra erro, imprime o erro no console
        }
    }
    
}
