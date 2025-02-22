package com.vacinas.core.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class ConexaoDAO {
    static Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USERNAME");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");
    private static Connection conexao;

    public static Connection getConnection() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexão com o banco estabelecida!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("❌ Erro ao carregar o driver do MySQL!", e);
            }
        }
        return conexao;
    }
}
