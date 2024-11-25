package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EmprestimoDAO {

    public ArrayList<String[]> listarEmprestimos() throws SQLException {
        ArrayList<String[]> emprestimos = new ArrayList<>();

        // Usando a classe Conexao para obter a conexão
        try (Connection conn = Conexao.getConnection(); // Alterado para getConnection()
                 Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(
                "SELECT "
                + "Usuario.nome AS usuario, "
                + "Livro.titulo AS livro, "
                + "Emprestimo.dataEmprestimo, "
                + "Emprestimo.dataDevolucao "
                + "FROM Emprestimo "
                + "INNER JOIN Usuario ON Emprestimo.idUsuario = Usuario.id "
                + "INNER JOIN Livro ON Emprestimo.idLivro = Livro.id"
        )) {

            // Processando os resultados
            while (rs.next()) {
                emprestimos.add(new String[]{
                    rs.getString("usuario"), // Nome do usuário
                    rs.getString("livro"), // Título do livro
                    rs.getString("dataEmprestimo"),
                    rs.getString("dataDevolucao")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Relança a exceção
        }

        return emprestimos;
    }

    public int obterIdLivroPorNome(String nomeLivro) throws SQLException {
    String query = "SELECT id FROM Livro WHERE titulo = ?";
    try (Connection conn = Conexao.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, nomeLivro); // Definindo o nome do livro
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id"); // Retorna o ID do livro encontrado
            }
        }
    }
    return -1; // Retorna -1 caso não encontre o livro
}

public int obterIdUsuarioPorNome(String nomeUsuario) throws SQLException {
    String query = "SELECT id FROM Usuario WHERE nome = ?";
    try (Connection conn = Conexao.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, nomeUsuario); // Definindo o nome do usuário
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id"); // Retorna o ID do usuário encontrado
            }
        }
    }
    return -1; // Retorna -1 caso não encontre o usuário
}


    public void atualizarEmprestimo(int id, int idLivro, int idUsuario, String dataEmprestimo, String dataDevolucao) throws SQLException {
        String query = "UPDATE emprestimo SET idLivro = ?, idUsuario = ?, dataEmprestimo = ?, dataDevolucao = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection(); // Alterado para getConnection()
                 PreparedStatement pst = conn.prepareStatement(query)) {  // Preparamos o statement com a conexão

            // Passando os IDs do livro e do usuário, além das datas
            pst.setInt(1, idLivro);  // ID do livro
            pst.setInt(2, idUsuario);  // ID do usuário
            pst.setString(3, dataEmprestimo);
            pst.setString(4, dataDevolucao);
            pst.setInt(5, id);  // ID do empréstimo para identificar o registro correto

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Exibe a mensagem com o ID do empréstimo
                JOptionPane.showMessageDialog(null, "Alterações salvas para o ID do empréstimo: " + id);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar empréstimo.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao executar o UPDATE: " + e.getMessage());
        }
    }

    public void salvarEmprestimo(int idLivro, int idUsuario, String dataEmprestimo, String dataDevolucao) throws SQLException {
        String query = "INSERT INTO Emprestimo (idLivro, idUsuario, dataEmprestimo, dataDevolucao) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection(); // Criando a conexão
                 PreparedStatement pst = conn.prepareStatement(query)) {

            // Verifique se os valores são válidos
            if (idLivro == -1 || idUsuario == -1 || dataEmprestimo.isEmpty() || dataDevolucao.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Dados inválidos. Verifique se os campos estão preenchidos corretamente.");
                return;  // Retorna se algum dado estiver inválido
            }

            pst.setInt(1, idLivro);  // Definindo o ID do livro
            pst.setInt(2, idUsuario);  // Definindo o ID do usuário
            pst.setString(3, dataEmprestimo);  // Definindo a data do empréstimo
            pst.setString(4, dataDevolucao);  // Definindo a data de devolução

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Empréstimo registrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao salvar empréstimo.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao executar o INSERT: " + e.getMessage());
        }

    }

}
