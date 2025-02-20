package com.vacinas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {
    private static final String URL = "jdbc:mysql://185.111.156.141:3806/vacinacao";
    private static final String USUARIO = "vacina_user";
    private static final String SENHA = "h95k4T5kbflavJh";
    private static Connection conexao;

    public static Connection getConnection() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("✅ Conexão com o banco estabelecida!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("❌ Erro ao carregar o driver do MySQL!", e);
            }
        }
        return conexao;
    }
}
