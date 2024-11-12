package com.example;

import com.example.controller.BancoDeDados;
import com.example.Model.Tarefa;
import com.example.Model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static BancoDeDados bancoDeDados = new BancoDeDados();
    private static JFrame frame;

    public static void main(String[] args) {
        // Inicia a aplicação com a interface gráfica
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                criarMenuPrincipal(); // Chama a função para criar o menu principal
            }
        });
    }

    // Função para criar o menu principal da aplicação
    private static void criarMenuPrincipal() {
        // Cria a janela principal
        frame = new JFrame("Gerenciador de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Organiza os botões na vertical

        // Cria os botões para cadastro de usuário, cadastro de tarefa e gerenciamento de tarefas
        JButton cadastroUsuarioBtn = new JButton("Cadastrar Usuário");
        JButton cadastroTarefaBtn = new JButton("Cadastrar Tarefa");
        JButton gerenciamentoTarefaBtn = new JButton("Gerenciar Tarefas");

        // Adiciona os eventos para cada botão
        cadastroUsuarioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarTelaCadastroUsuario(); // Abre a tela de cadastro de usuário
            }
        });

        cadastroTarefaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarTelaCadastroTarefa(); // Abre a tela de cadastro de tarefa
            }
        });

        gerenciamentoTarefaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarTelaGerenciamentoTarefas(); // Abre a tela de gerenciamento de tarefas
            }
        });

        // Adiciona os botões no painel principal
        panel.add(cadastroUsuarioBtn);
        panel.add(cadastroTarefaBtn);
        panel.add(gerenciamentoTarefaBtn);

        frame.add(panel); // Adiciona o painel na janela
        frame.setVisible(true); // Torna a janela visível
    }

    // Função para criar a tela de cadastro de usuário
    private static void criarTelaCadastroUsuario() {
        JFrame frame = new JFrame("Cadastro de Usuário");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2)); // Organiza os componentes em uma grade

        // Criação dos campos de entrada para o cadastro de usuário
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        // Botão para cadastrar o usuário
        JButton cadastrarBtn = new JButton("Cadastrar");

        // Ação do botão de cadastro de usuário
        cadastrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String email = emailField.getText();

                // Valida os dados e adiciona o usuário ao banco de dados
                if (!nome.isEmpty() && !email.isEmpty() && email.contains("@")) {
                    Usuario usuario = new Usuario(null, nome, email);
                    bancoDeDados.adicionarUsuario(usuario);
                    JOptionPane.showMessageDialog(frame, "Cadastro concluído com sucesso");
                    frame.dispose(); // Fecha a janela de cadastro após sucesso
                } else {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos corretamente.");
                }
            }
        });

        // Adiciona os componentes no painel
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(cadastrarBtn);

        frame.add(panel); // Adiciona o painel na janela
        frame.setVisible(true); // Torna a janela visível
    }

    // Função para criar a tela de cadastro de tarefa
    private static void criarTelaCadastroTarefa() {
        JFrame frame = new JFrame("Cadastro de Tarefa");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2)); // Organiza os componentes em uma grade

        // Criação dos campos de entrada para o cadastro de tarefa
        JLabel descricaoLabel = new JLabel("Descrição:");
        JTextField descricaoField = new JTextField();
        JLabel setorLabel = new JLabel("Setor:");
        JTextField setorField = new JTextField();
        JLabel prioridadeLabel = new JLabel("Prioridade:");
        JComboBox<String> prioridadeComboBox = new JComboBox<>(new String[] { "Baixa", "Média", "Alta" });
        JLabel usuarioLabel = new JLabel("Usuário:");
        JComboBox<Usuario> usuarioComboBox = new JComboBox<>(new DefaultComboBoxModel<>(bancoDeDados.getUsuarios().toArray(new Usuario[0]))); // Lista os usuários cadastrados

        // Botão para cadastrar a tarefa
        JButton cadastrarBtn = new JButton("Cadastrar");

        // Ação do botão de cadastro de tarefa
        cadastrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descricao = descricaoField.getText();
                String setor = setorField.getText();
                String prioridade = (String) prioridadeComboBox.getSelectedItem();
                Usuario usuarioSelecionado = (Usuario) usuarioComboBox.getSelectedItem();

                // Valida os dados e adiciona a tarefa ao banco de dados
                if (!descricao.isEmpty() && !setor.isEmpty() && usuarioSelecionado != null) {
                    Tarefa tarefa = new Tarefa(null, usuarioSelecionado.getId(), descricao, setor, prioridade, java.time.LocalDate.now(), "A Fazer");
                    bancoDeDados.adicionarTarefa(tarefa);
                    JOptionPane.showMessageDialog(frame, "Cadastro concluído com sucesso");
                    frame.dispose(); // Fecha a janela de cadastro após sucesso
                } else {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos corretamente.");
                }
            }
        });

        // Adiciona os componentes no painel
        panel.add(descricaoLabel);
        panel.add(descricaoField);
        panel.add(setorLabel);
        panel.add(setorField);
        panel.add(prioridadeLabel);
        panel.add(prioridadeComboBox);
        panel.add(usuarioLabel);
        panel.add(usuarioComboBox);
        panel.add(cadastrarBtn);

        frame.add(panel); // Adiciona o painel na janela
        frame.setVisible(true); // Torna a janela visível
    }

    // Função para criar a tela de gerenciamento de tarefas
    private static void criarTelaGerenciamentoTarefas() {
        frame.dispose(); // Fecha a janela principal
        frame = new JFrame("Gerenciamento de Tarefas");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));

        JPanel tarefasPanel = new JPanel();
        tarefasPanel.setLayout(new BoxLayout(tarefasPanel, BoxLayout.Y_AXIS));
        tarefasPanel.setBorder(BorderFactory.createTitledBorder("Tarefas"));

        // Adiciona as tarefas na lista
        for (Tarefa tarefa : bancoDeDados.listarTarefas()) {
            JPanel tarefaPanel = new JPanel();
            tarefaPanel.setLayout(new BoxLayout(tarefaPanel, BoxLayout.Y_AXIS));
            tarefaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Exibe os detalhes da tarefa
            tarefaPanel.add(new JLabel("Descrição: " + tarefa.getDescricao()));
            tarefaPanel.add(new JLabel("Setor: " + tarefa.getSetor()));
            tarefaPanel.add(new JLabel("Prioridade: " + tarefa.getPrioridade()));
            tarefaPanel.add(new JLabel("Status: " + tarefa.getStatus()));

            // Criação dos botões para alterar o status da tarefa
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new FlowLayout());

            JButton aFazerBtn = new JButton("A Fazer");
            JButton fazendoBtn = new JButton("Fazendo");
            JButton prontoBtn = new JButton("Pronto");
            JButton editarBtn = new JButton("Editar");
            JButton excluirBtn = new JButton("Excluir");

            // Ação para alterar o status da tarefa para "A Fazer"
            aFazerBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tarefa.setStatus("A Fazer");
                    bancoDeDados.atualizarTarefa(tarefa);
                    JOptionPane.showMessageDialog(frame, "Status alterado para 'A Fazer'");
                    criarTelaGerenciamentoTarefas(); // Atualiza a tela
                }
            });

            // Ação para alterar o status da tarefa para "Fazendo"
            fazendoBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tarefa.setStatus("Fazendo");
                    bancoDeDados.atualizarTarefa(tarefa);
                    JOptionPane.showMessageDialog(frame, "Status alterado para 'Fazendo'");
                    criarTelaGerenciamentoTarefas(); // Atualiza a tela
                }
            });

            // Ação para alterar o status da tarefa para "Pronto"
            prontoBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tarefa.setStatus("Pronto");
                    bancoDeDados.atualizarTarefa(tarefa);
                    JOptionPane.showMessageDialog(frame, "Status alterado para 'Pronto'");
                    criarTelaGerenciamentoTarefas(); // Atualiza a tela
                }
            });

            // Ação para editar a tarefa
            editarBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Abre a tela de edição de tarefa com os dados preenchidos
                    criarTelaCadastroTarefa(tarefa); // Passa a tarefa para a tela de edição
                }
            });

            // Ação para excluir a tarefa
            excluirBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bancoDeDados.removerTarefa(tarefa); // Remove a tarefa do banco
                    JOptionPane.showMessageDialog(frame, "Tarefa excluída com sucesso");
                    criarTelaGerenciamentoTarefas(); // Atualiza a tela
                }
            });

            // Adiciona os botões na tela de tarefa
            buttonsPanel.add(aFazerBtn);
            buttonsPanel.add(fazendoBtn);
            buttonsPanel.add(prontoBtn);
            buttonsPanel.add(editarBtn);
            buttonsPanel.add(excluirBtn);

            tarefaPanel.add(buttonsPanel); // Adiciona os botões no painel da tarefa
            tarefasPanel.add(tarefaPanel); // Adiciona o painel da tarefa na lista

        }

        JScrollPane scrollPane = new JScrollPane(tarefasPanel);
        panel.add(scrollPane); // Adiciona o painel das tarefas ao painel principal

        frame.add(panel); // Adiciona o painel principal na janela
        frame.setVisible(true); // Torna a janela visível
    }

    // Função para criar a tela de cadastro de tarefa para edição
    private static void criarTelaCadastroTarefa(Tarefa tarefa) {
        JFrame frame = new JFrame("Editar Tarefa");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2)); // Organiza os componentes em uma grade

        // Criação dos campos de entrada para o cadastro de tarefa
        JLabel descricaoLabel = new JLabel("Descrição:");
        JTextField descricaoField = new JTextField(tarefa.getDescricao());
        JLabel setorLabel = new JLabel("Setor:");
        JTextField setorField = new JTextField(tarefa.getSetor());
        JLabel prioridadeLabel = new JLabel("Prioridade:");
        JComboBox<String> prioridadeComboBox = new JComboBox<>(new String[] { "Baixa", "Média", "Alta" });
        prioridadeComboBox.setSelectedItem(tarefa.getPrioridade());
        JLabel usuarioLabel = new JLabel("Usuário:");
        JComboBox<Usuario> usuarioComboBox = new JComboBox<>(new DefaultComboBoxModel<>(bancoDeDados.getUsuarios().toArray(new Usuario[0]))); // Lista os usuários cadastrados
        usuarioComboBox.setSelectedItem(new Usuario(tarefa.getUsuarioId(), null, null)); // Setando o usuário da tarefa para edição

        // Botão para atualizar a tarefa
        JButton atualizarBtn = new JButton("Atualizar");

        // Ação do botão de atualização de tarefa
        atualizarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descricao = descricaoField.getText();
                String setor = setorField.getText();
                String prioridade = (String) prioridadeComboBox.getSelectedItem();
                Usuario usuarioSelecionado = (Usuario) usuarioComboBox.getSelectedItem();

                // Valida os dados e atualiza a tarefa no banco de dados
                if (!descricao.isEmpty() && !setor.isEmpty() && usuarioSelecionado != null) {
                    tarefa.setDescricao(descricao);
                    tarefa.setSetor(setor);
                    tarefa.setPrioridade(prioridade);
                    tarefa.setUsuarioId(usuarioSelecionado.getId());

                    bancoDeDados.atualizarTarefa(tarefa);
                    JOptionPane.showMessageDialog(frame, "Tarefa atualizada com sucesso");
                    frame.dispose(); // Fecha a janela de edição após sucesso
                    criarTelaGerenciamentoTarefas(); // Atualiza a tela principal de gerenciamento
                } else {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos corretamente.");
                }
            }
        });

        // Adiciona os componentes no painel
        panel.add(descricaoLabel);
        panel.add(descricaoField);
        panel.add(setorLabel);
        panel.add(setorField);
        panel.add(prioridadeLabel);
        panel.add(prioridadeComboBox);
        panel.add(usuarioLabel);
        panel.add(usuarioComboBox);
        panel.add(atualizarBtn);

        frame.add(panel); // Adiciona o painel na janela
        frame.setVisible(true); // Torna a janela visível
    }
}
