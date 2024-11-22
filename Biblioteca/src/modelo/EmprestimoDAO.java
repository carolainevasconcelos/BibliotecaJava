package modelo;

import modelo.Conexao;  // Importe a classe de conexão
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmprestimoDAO {

    public ArrayList<String[]> listarEmprestimos() throws SQLException {
        ArrayList<String[]> emprestimos = new ArrayList<>();

        // Usando a classe Conexao para obter a conexão
        try (Connection conn = Conexao.getConexao();  // Utilizando Conexao.java
             Statement stmt = conn.createStatement();
             // Alteração: Agora estamos fazendo uma junção entre as tabelas
             ResultSet rs = stmt.executeQuery(
                 "SELECT " +
                 "Usuario.nome AS usuario, " +
                 "Livro.titulo AS livro, " +
                 "Emprestimo.dataEmprestimo, " +
                 "Emprestimo.dataDevolucao " +
                 "FROM Emprestimo " +
                 "INNER JOIN Usuario ON Emprestimo.idUsuario = Usuario.id " +
                 "INNER JOIN Livro ON Emprestimo.idLivro = Livro.id"
             )) {

            // Processando os resultados
            while (rs.next()) {
                emprestimos.add(new String[]{
                    rs.getString("usuario"),  // Nome do usuário
                    rs.getString("livro"),    // Título do livro
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
}
