package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String email;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método para salvar no banco de dados
    public void salvar() throws SQLException {
        String sql = "INSERT INTO usuario (nome, cpf, email) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
            throw e; // Lançando o erro novamente para propagar a exceção
        }
    }
}
