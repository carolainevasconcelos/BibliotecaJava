package controle;

import modelo.Conexao;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioControle {
    private Connection conexao;

    public UsuarioControle() throws SQLException {
        this.conexao = Conexao.getConnection();
    }

    // Método para adicionar um usuário
    public void adicionarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, cpf, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os usuários
    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    // Método para buscar um usuário por ID
    public Usuario buscarUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setEmail(rs.getString("email"));
                    return usuario;
                }
            }
        }
        return null;
    }

    // Método para atualizar um usuário
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, cpf = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar um usuário por ID
    public void deletarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
