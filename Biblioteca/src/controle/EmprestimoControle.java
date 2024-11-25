package controle;

import modelo.Conexao;
import java.sql.Connection;
import modelo.Emprestimo;
import java.sql.*;
import java.util.ArrayList;


public class EmprestimoControle {

    private Connection conexao;

    public EmprestimoControle() throws SQLException {
        this.conexao = Conexao.getConnection();  // Usando o método renomeado
    }

    public ArrayList<String[]> listarEmprestimos() throws SQLException {
        ArrayList<String[]> emprestimos = new ArrayList<>();
        String sql = """
        SELECT 
            Emprestimo.id,
            Livro.titulo AS livro,
            Usuario.nome AS usuario,
            Emprestimo.dataEmprestimo,
            Emprestimo.dataDevolucao
        FROM Emprestimo
        INNER JOIN Livro ON Emprestimo.idLivro = Livro.id
        INNER JOIN Usuario ON Emprestimo.idUsuario = Usuario.id
    """;
        try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] dados = {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("livro"),
                    rs.getString("usuario"),
                    rs.getDate("dataEmprestimo").toString(),
                    rs.getDate("dataDevolucao") != null ? rs.getDate("dataDevolucao").toString() : "Não devolvido"
                };
                emprestimos.add(dados);
            }
        }
        return emprestimos;
    }

    // Método para buscar um empréstimo pelo ID
    public Emprestimo buscarEmprestimoPorId(int id) throws SQLException {
    Emprestimo emprestimo = null;
    String sql = """
        SELECT 
            Livro.titulo AS livro,
            Usuario.nome AS usuario,
            Emprestimo.dataEmprestimo,
            Emprestimo.dataDevolucao
        FROM Emprestimo
        INNER JOIN Livro ON Emprestimo.idLivro = Livro.id
        INNER JOIN Usuario ON Emprestimo.idUsuario = Usuario.id
        WHERE Emprestimo.id = ?
    """;

    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                emprestimo = new Emprestimo(
                        id,  // Passando o ID
                        rs.getString("livro"),
                        rs.getString("usuario"),
                        rs.getDate("dataEmprestimo").toLocalDate(),
                        rs.getDate("dataDevolucao") != null ? rs.getDate("dataDevolucao").toLocalDate() : null
                );
            }
        }
    }
    return emprestimo;
}


    // Método para atualizar os dados do empréstimo
    public void atualizarEmprestimo(Emprestimo emprestimo) throws SQLException {
        String sql = """
            UPDATE Emprestimo
            SET idLivro = (SELECT id FROM Livro WHERE titulo = ?),
                idUsuario = (SELECT id FROM Usuario WHERE nome = ?),
                dataEmprestimo = ?,
                dataDevolucao = ?
            WHERE id = ?
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, emprestimo.getLivro());  // Define o título do livro
            stmt.setString(2, emprestimo.getUsuario());  // Define o nome do usuário
            stmt.setDate(3, java.sql.Date.valueOf(emprestimo.getDataEmprestimo()));  // Converte a data para SQL
            if (emprestimo.getDataDevolucao() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(emprestimo.getDataDevolucao()));  // Converte a data de devolução para SQL
            } else {
                stmt.setNull(4, Types.DATE);  // Se não houver data de devolução, define como NULL
            }
            stmt.setInt(5, emprestimo.getId());  // Define o ID do empréstimo a ser atualizado

            stmt.executeUpdate();
        }
    }
    
    public boolean excluirEmprestimo(String idEmprestimo) throws SQLException {
    String sql = "DELETE FROM emprestimo WHERE id = ?"; // Supondo que a tabela seja 'emprestimos'
    try (Connection conn = Conexao.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, idEmprestimo);
        
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // Se alguma linha for afetada, a exclusão foi bem-sucedida
    }

}

}