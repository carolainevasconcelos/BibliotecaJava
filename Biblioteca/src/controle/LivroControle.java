package controle;

import modelo.Conexao;
import modelo.Livro;

import java.sql.*;
import java.util.ArrayList;

public class LivroControle {
    private Connection conexao;

    public LivroControle() throws SQLException {
        this.conexao = Conexao.getConnection();
    }

    public void adicionarLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livro (titulo, autor, genero, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getQuantidade());
            stmt.executeUpdate();
        }
    }

    public ArrayList<Livro> listarLivros() throws SQLException {
        ArrayList<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livro";
        try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setGenero(rs.getString("genero"));
                livro.setQuantidade(rs.getInt("quantidade"));
                livros.add(livro);
            }
        }
        return livros;
    }
}
