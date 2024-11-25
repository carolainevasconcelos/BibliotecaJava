/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao;

import controle.EmprestimoControle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaLista extends JFrame {

    private JTable tabelaEmprestimos;
    private JScrollPane scrollPane;
    private JButton btnEditar;
    private JButton btnExcluir;

    public TelaLista() {
        setTitle("Listagem de Empréstimos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Layout da tela
        setLayout(new BorderLayout());

        // Criar painel de botões
        JPanel painelBotoes = new JPanel();
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.NORTH);

        // Criar tabela
        tabelaEmprestimos = new JTable();
        scrollPane = new JScrollPane(tabelaEmprestimos);
        add(scrollPane, BorderLayout.CENTER);

        // Carregar dados na tabela
        carregarTabelaEmprestimos();

        // Ações dos botões
        btnEditar.addActionListener(e -> editarEmprestimo());
        btnExcluir.addActionListener(e -> excluirEmprestimo());
    }

    private void carregarTabelaEmprestimos() {
        try {
            EmprestimoControle emprestimoControle = new EmprestimoControle();

            // Obter lista de empréstimos
            ArrayList<String[]> listaEmprestimos = emprestimoControle.listarEmprestimos();

            // Definir colunas para a tabela
            String[] colunas = {"ID", "Livro", "Usuário", "Data Empréstimo", "Data Devolução"};
            DefaultTableModel model = new DefaultTableModel(colunas, 0);

            // Preencher o modelo da tabela
            for (String[] emprestimo : listaEmprestimos) {
                model.addRow(emprestimo);
            }

            // Associar o modelo à tabela
            tabelaEmprestimos.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados dos empréstimos: " + e.getMessage());
        }
    }

    private void editarEmprestimo() {
        int linhaSelecionada = tabelaEmprestimos.getSelectedRow();
        if (linhaSelecionada != -1) {
            // Obter dados da linha selecionada
            String idEmprestimo = tabelaEmprestimos.getValueAt(linhaSelecionada, 0).toString();
            String nomeLivro = tabelaEmprestimos.getValueAt(linhaSelecionada, 1).toString();
            String nomeUsuario = tabelaEmprestimos.getValueAt(linhaSelecionada, 2).toString();
            String dataEmprestimo = tabelaEmprestimos.getValueAt(linhaSelecionada, 3).toString();
            String dataDevolucao = tabelaEmprestimos.getValueAt(linhaSelecionada, 4).toString();

            // Abrir a tela de edição passando os dados
            TelaEdit telaEdit = new TelaEdit(idEmprestimo, nomeLivro, nomeUsuario, dataEmprestimo, dataDevolucao);
            telaEdit.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo para editar.");
        }
    }

    private void excluirEmprestimo() {
        int linhaSelecionada = tabelaEmprestimos.getSelectedRow();
        if (linhaSelecionada != -1) {
            // Obter ID do empréstimo a partir da tabela (coluna 0)
            String idEmprestimo = (String) tabelaEmprestimos.getValueAt(linhaSelecionada, 0);

            // Confirmar exclusão
            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o empréstimo ID: " + idEmprestimo + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                try {
                    EmprestimoControle emprestimoControle = new EmprestimoControle();

                    // Excluir o empréstimo no banco de dados
                    boolean sucesso = emprestimoControle.excluirEmprestimo(idEmprestimo);

                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Empréstimo excluído com sucesso.");
                        carregarTabelaEmprestimos();  // Atualiza a tabela após exclusão
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir o empréstimo.");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir o empréstimo: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo para excluir.");
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBeditar = new javax.swing.JButton();
        jBexcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jBeditar.setBackground(new java.awt.Color(102, 204, 255));
        jBeditar.setForeground(new java.awt.Color(255, 255, 255));
        jBeditar.setText("Editar");
        jBeditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBeditarActionPerformed(evt);
            }
        });

        jBexcluir.setBackground(new java.awt.Color(102, 204, 255));
        jBexcluir.setForeground(new java.awt.Color(255, 255, 255));
        jBexcluir.setText("Excluir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jBeditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBexcluir)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBeditar)
                    .addComponent(jBexcluir))
                .addContainerGap(271, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBeditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBeditarActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jBeditarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaLista().setVisible(true);  // A exceção SQLException será propagada aqui
                } catch (Exception e) {
                    e.printStackTrace();  // Aqui você pode fazer o que preferir com a exceção
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBeditar;
    private javax.swing.JButton jBexcluir;
    // End of variables declaration//GEN-END:variables
}
