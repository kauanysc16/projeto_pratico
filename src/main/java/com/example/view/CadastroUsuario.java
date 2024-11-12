package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.controller.BancoDeDados;
import com.example.Model.Usuario;

public class CadastroUsuario extends JFrame {

    // Componentes da interface gráfica
    private JTextField nomeField, emailField; // Campos de texto para o nome e e-mail
    private JButton cadastrarButton; // Botão para cadastrar o usuário
    private BancoDeDados bancoDeDados; // Instância da classe BancoDeDados para interação com o banco

    // Construtor da classe, inicializa a interface gráfica
    public CadastroUsuario(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados; // Recebe a instância do banco de dados

        setTitle("Cadastro de Usuário"); // Define o título da janela
        setLayout(new FlowLayout()); // Layout simples com fluxo
        setSize(400, 200); // Define o tamanho da janela

        // Inicializando os componentes da interface
        JLabel nomeLabel = new JLabel("Nome do Usuário:"); // Rótulo para o campo nome
        nomeField = new JTextField(20); // Campo de texto para inserir o nome

        JLabel emailLabel = new JLabel("E-mail:"); // Rótulo para o campo e-mail
        emailField = new JTextField(20); // Campo de texto para inserir o e-mail

        cadastrarButton = new JButton("Cadastrar Usuário"); // Botão para cadastrar o usuário

        // Adicionando os componentes na tela
        add(nomeLabel);
        add(nomeField);
        add(emailLabel);
        add(emailField);
        add(cadastrarButton);

        // Definindo a ação do botão de cadastro
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario(); // Chama o método para cadastrar o usuário
            }
        });

        // Configurações da janela
        setVisible(true); // Torna a janela visível
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define que ao fechar a janela a aplicação será encerrada
    }

    // Método para cadastrar o usuário
    private void cadastrarUsuario() {
        String nome = nomeField.getText(); // Obtém o nome inserido
        String email = emailField.getText(); // Obtém o e-mail inserido

        // Verifica se os campos estão preenchidos
        if (nome.isEmpty() || email.isEmpty()) {
            // Exibe uma mensagem de erro caso algum campo esteja vazio
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Valida o e-mail
        if (!validarEmail(email)) {
            // Exibe uma mensagem de erro caso o e-mail seja inválido
            JOptionPane.showMessageDialog(this, "E-mail inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cria o objeto Usuário com os dados inseridos
        Usuario usuario = new Usuario(null, nome, email);
        // Adiciona o usuário no banco de dados
        bancoDeDados.adicionarUsuario(usuario);
        // Exibe uma mensagem de sucesso
        JOptionPane.showMessageDialog(this, "Cadastro concluído com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o cadastro bem-sucedido
        nomeField.setText("");
        emailField.setText("");
        dispose(); // Fecha a janela após o cadastro
    }

    // Método para validar o e-mail utilizando uma expressão regular (regex)
    private boolean validarEmail(String email) {
        // Regex para validar o e-mail
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex); // Compila o padrão da regex
        Matcher matcher = pattern.matcher(email); // Aplica o padrão ao e-mail
        return matcher.matches(); // Retorna true se o e-mail for válido
    }

    // Método principal para rodar a interface gráfica
    public static void main(String[] args) {
        BancoDeDados bancoDeDados = new BancoDeDados(); // Cria uma instância do banco de dados
        new CadastroUsuario(bancoDeDados); // Cria e exibe a janela de cadastro de usuário
    }
}
