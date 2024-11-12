package com.example.view;

import com.example.Model.Tarefa;
import com.example.Model.Usuario;
import com.example.controller.BancoDeDados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciamentoTarefas {

    // Instância do banco de dados que será usada para listar e manipular tarefas
    private static BancoDeDados bancoDeDados = new BancoDeDados();

    // Função principal para iniciar a interface de gerenciamento de tarefas
    public static void main(String[] args) {
        // Iniciar a interface gráfica de gerenciamento de tarefas em uma thread separada
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                criarTelaGerenciamentoTarefas(); // Chama a função para criar a tela de gerenciamento de tarefas
            }
        });
    }

    // Função para criar a tela de gerenciamento de tarefas
    private static void criarTelaGerenciamentoTarefas() {
        JFrame frame = new JFrame("Gerenciamento de Tarefas"); // Cria a janela principal
        frame.setSize(700, 500); // Define o tamanho da janela
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela

        // Criar o painel principal com layout vertical (BoxLayout)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Adicionar todas as tarefas à tela
        for (Tarefa tarefa : bancoDeDados.listarTarefas()) {
            // Para cada tarefa, cria um painel individual e adiciona ao painel principal
            JPanel tarefaPanel = criarPainelDeTarefa(tarefa);
            mainPanel.add(tarefaPanel);
        }

        // Adiciona o painel principal em um JScrollPane para permitir rolagem se necessário
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        frame.add(scrollPane); // Adiciona o JScrollPane ao frame
        frame.setVisible(true); // Torna a janela visível
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a ação ao fechar a janela
    }

    // Função para criar um painel individual para cada tarefa
    private static JPanel criarPainelDeTarefa(Tarefa tarefa) {
        JPanel tarefaPanel = new JPanel(); // Painel que representará uma tarefa
        tarefaPanel.setLayout(new BoxLayout(tarefaPanel, BoxLayout.Y_AXIS)); // Layout vertical
        tarefaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Define uma borda preta
        tarefaPanel.setPreferredSize(new Dimension(600, 150)); // Define o tamanho preferido do painel

        // Exibe as informações da tarefa (descrição, setor, prioridade, usuário)
        tarefaPanel.add(new JLabel("Descrição: " + tarefa.getDescricao()));
        tarefaPanel.add(new JLabel("Setor: " + tarefa.getSetor()));
        tarefaPanel.add(new JLabel("Prioridade: " + tarefa.getPrioridade()));
        tarefaPanel.add(new JLabel("Usuário: " + buscarUsuarioNome(tarefa.getUsuarioId())));

        // Painel para os botões de ação (Editar, Excluir e Alterar Status)
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout()); // Layout horizontal para os botões

        // Botão para editar a tarefa
        JButton editarBtn = new JButton("Editar");
        editarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarTarefa(tarefa); // Chama a função de edição da tarefa
            }
        });
        buttonsPanel.add(editarBtn);

        // Botão para excluir a tarefa
        JButton excluirBtn = new JButton("Excluir");
        excluirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirTarefa(tarefa); // Chama a função de exclusão da tarefa
            }
        });
        buttonsPanel.add(excluirBtn);

        // Botões para alterar o status da tarefa (A Fazer, Fazendo, Pronto)
        JButton aFazerBtn = new JButton("A Fazer");
        JButton fazendoBtn = new JButton("Fazendo");
        JButton prontoBtn = new JButton("Pronto");

        // Adiciona as ações de alterar status para cada botão
        aFazerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarStatusTarefa(tarefa, "A Fazer"); // Altera o status da tarefa para "A Fazer"
            }
        });

        fazendoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarStatusTarefa(tarefa, "Fazendo"); // Altera o status da tarefa para "Fazendo"
            }
        });

        prontoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarStatusTarefa(tarefa, "Pronto"); // Altera o status da tarefa para "Pronto"
            }
        });

        // Adiciona os botões de status no painel de botões
        buttonsPanel.add(aFazerBtn);
        buttonsPanel.add(fazendoBtn);
        buttonsPanel.add(prontoBtn);

        // Adiciona o painel de botões ao painel da tarefa
        tarefaPanel.add(buttonsPanel);

        return tarefaPanel; // Retorna o painel completo da tarefa
    }

    // Função para buscar o nome do usuário com base no ID do usuário associado à tarefa
    private static String buscarUsuarioNome(Long usuarioId) {
        // Itera sobre todos os usuários e retorna o nome do usuário com o ID correspondente
        for (Usuario usuario : bancoDeDados.getUsuarios()) {
            if (usuario.getId().equals(usuarioId)) {
                return usuario.getNome();
            }
        }
        return "Desconhecido"; // Retorna "Desconhecido" caso o usuário não seja encontrado
    }

    // Função para editar uma tarefa
    private static void editarTarefa(Tarefa tarefa) {
        // Cria um novo frame para edição da tarefa
        JFrame editarFrame = new JFrame("Editar Tarefa");
        editarFrame.setSize(400, 300);
        editarFrame.setLocationRelativeTo(null); // Centraliza a janela

        // Painel para os campos de edição
        JPanel panel = new JPanel(new GridLayout(5, 2));

        // Campos de texto para editar a descrição, setor e prioridade
        JTextField descricaoField = new JTextField(tarefa.getDescricao());
        JTextField setorField = new JTextField(tarefa.getSetor());
        JTextField prioridadeField = new JTextField(tarefa.getPrioridade());

        // Campo de seleção para o usuário
        JComboBox<Usuario> usuarioComboBox = new JComboBox<>();
        for (Usuario usuario : bancoDeDados.getUsuarios()) {
            usuarioComboBox.addItem(usuario);
        }

        // Seleciona o usuário atual da tarefa no combobox
        for (int i = 0; i < usuarioComboBox.getItemCount(); i++) {
            if (usuarioComboBox.getItemAt(i).getId().equals(tarefa.getUsuarioId())) {
                usuarioComboBox.setSelectedIndex(i);
                break;
            }
        }

        // Adiciona os componentes ao painel
        panel.add(new JLabel("Descrição:"));
        panel.add(descricaoField);
        panel.add(new JLabel("Setor:"));
        panel.add(setorField);
        panel.add(new JLabel("Prioridade:"));
        panel.add(prioridadeField);
        panel.add(new JLabel("Usuário:"));
        panel.add(usuarioComboBox);

        // Botão para salvar as alterações
        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Atualiza os valores da tarefa com os dados dos campos
                tarefa.setDescricao(descricaoField.getText());
                tarefa.setSetor(setorField.getText());
                tarefa.setPrioridade(prioridadeField.getText());
                Usuario usuarioSelecionado = (Usuario) usuarioComboBox.getSelectedItem();
                tarefa.setUsuarioId(usuarioSelecionado.getId());

                // Atualiza a tarefa no banco de dados
                bancoDeDados.atualizarTarefa(tarefa);
                JOptionPane.showMessageDialog(editarFrame, "Tarefa atualizada com sucesso!");

                // Fecha a janela de edição e atualiza a tela principal
                editarFrame.dispose();
                criarTelaGerenciamentoTarefas();
            }
        });

        // Botão para cancelar a edição
        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarFrame.dispose();
            }
        });

        // Adiciona os botões ao painel
        panel.add(salvarBtn);
        panel.add(cancelarBtn);

        editarFrame.add(panel);
        editarFrame.setVisible(true);
    }

    // Função para excluir uma tarefa
    private static void excluirTarefa(Tarefa tarefa) {
        // Exibe uma caixa de diálogo pedindo confirmação para excluir a tarefa
        int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta tarefa?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            bancoDeDados.excluirTarefa(tarefa.getId()); // Remove a tarefa do banco de dados
            JOptionPane.showMessageDialog(null, "Tarefa excluída com sucesso!"); // Exibe uma mensagem de sucesso
            criarTelaGerenciamentoTarefas(); // Atualiza a tela de gerenciamento de tarefas
        }
    }

    // Função para alterar o status de uma tarefa
    private static void alterarStatusTarefa(Tarefa tarefa, String novoStatus) {
        tarefa.setStatus(novoStatus); // Altera o status da tarefa
        bancoDeDados.atualizarTarefa(tarefa); // Atualiza a tarefa no banco de dados
        JOptionPane.showMessageDialog(null, "Status alterado para: " + novoStatus); // Exibe a nova situação
        criarTelaGerenciamentoTarefas(); // Atualiza a tela de gerenciamento de tarefas
    }
}
