package com.vacinas;

import java.sql.Connection;
import java.sql.SQLException;

import com.vacinas.DAO.ConexaoDAO;

public class Main {
    public static Connection conexao = null;

    public static void main(String[] args) throws SQLException {

        conexao = ConexaoDAO.getConexao();

        System.out.println(conexao);

        System.out.println("Hello world!");
    }
}