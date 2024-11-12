package com.example.view;

import com.example.Model.Tarefa;
import com.example.Model.Usuario;
import com.example.controller.BancoDeDados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class CadastroTarefa extends JFrame {

    // Componentes da interface gráfica
    private JTextField descricaoField, setorField; // Campos de texto para descrição e setor
    private JComboBox<String> prioridadeComboBox; // ComboBox para selecionar a prioridade
    private JTextField idUsuarioField; // Campo de texto para ID do usuário
    private JButton cadastrarButton; // Botão para cadastrar a tarefa

    private BancoDeDados bancoDeDados; // Instância da classe BancoDeDados para interação com o banco

    // Construtor da classe, inicializa os componentes da interface gráfica
    public CadastroTarefa(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados; // Recebe a instância do banco de dados

        // Definindo o título da janela
        setTitle("Cadastro de Tarefa");

        // Definindo o layout da janela (FlowLayout distribui os componentes em linha)
        setLayout(new FlowLayout());

        // Definindo as dimensões da janela
        setSize(400, 250);

        // Criando os componentes da interface
        JLabel idUsuarioLabel = new JLabel("ID do Usuário:"); // Rótulo para o campo ID do Usuário
        idUsuarioField = new JTextField(20); // Campo de texto para inserir o ID do usuário

        JLabel descricaoLabel = new JLabel("Descrição da Tarefa:"); // Rótulo para a descrição da tarefa
        descricaoField = new JTextField(20); // Campo de texto para inserir a descrição

        JLabel setorLabel = new JLabel("Setor:"); // Rótulo para o setor
        setorField = new JTextField(20); // Campo de texto para inserir o setor

        JLabel prioridadeLabel = new JLabel("Prioridade:"); // Rótulo para a prioridade
        prioridadeComboBox = new JComboBox<>(new String[] { "Baixa", "Média", "Alta" }); // ComboBox para selecionar a prioridade

        cadastrarButton = new JButton("Cadastrar Tarefa"); // Botão para cadastrar a tarefa

        // Adicionando os componentes na janela
        add(idUsuarioLabel);
        add(idUsuarioField);
        add(descricaoLabel);
        add(descricaoField);
        add(setorLabel);
        add(setorField);
        add(prioridadeLabel);
        add(prioridadeComboBox);
        add(cadastrarButton);

        // Definindo a ação do botão de cadastrar
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarTarefa(); // Chama o método para cadastrar a tarefa
            }
        });

        // Tornando a janela visível
        setVisible(true);

        // Configuração para fechar a aplicação ao fechar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Método para cadastrar a tarefa
    private void cadastrarTarefa() {
        try {
            // Obtém os dados inseridos pelo usuário nos campos da interface
            Long idUsuario = Long.parseLong(idUsuarioField.getText()); // Obtém o ID do usuário
            String descricao = descricaoField.getText(); // Obtém a descrição da tarefa
            String setor = setorField.getText(); // Obtém o setor
            String prioridade = (String) prioridadeComboBox.getSelectedItem(); // Obtém a prioridade selecionada

            // Verificando se o usuário existe no banco de dados
            Usuario usuario = bancoDeDados.buscarUsuarioPorId(idUsuario); 
            if (usuario == null) {
                // Caso o usuário não exista, exibe uma mensagem de erro
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criando uma nova tarefa com os dados inseridos
            Long idTarefa = bancoDeDados.gerarNovoIdTarefa(); // Gerar um ID único para a tarefa
            Tarefa tarefa = new Tarefa(idTarefa, idUsuario, descricao, setor, prioridade, LocalDate.now(), "a fazer");

            // Adicionando a tarefa no banco de dados
            bancoDeDados.adicionarTarefa(tarefa);
            // Exibe uma mensagem de sucesso após o cadastro
            JOptionPane.showMessageDialog(this, "Tarefa cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            // Caso o ID do usuário não seja válido, exibe uma mensagem de erro
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID válido para o usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método principal para rodar a interface gráfica
    public static void main(String[] args) {
        BancoDeDados bancoDeDados = new BancoDeDados(); // Criação da instância do banco de dados
        new CadastroTarefa(bancoDeDados); // Inicia a janela de cadastro de tarefas
    }
}
