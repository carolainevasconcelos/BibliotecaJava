package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {  // Método renomeado
        String url = "jdbc:mysql://localhost:3306/livraria"; // Substitua pela sua URL do banco
        String user = "root"; // Substitua pelo seu usuário do banco
        String password = "Carolaine22"; // Substitua pela sua senha

        // Retorna a conexão
        return DriverManager.getConnection(url, user, password);
    }
}
