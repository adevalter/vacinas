package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vacinas.model.Vacina;

public class VacinaDAO {
    public static Connection conexao = null;

    public static void inserir(Vacina vacina) throws SQLException {
        String sql = "INSERT INTO vacina ( vacina, descricao, limite_aplicacao, publico_alvo) VALUES( ?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            comando.setString(1, vacina.getVacina());
            comando.setString(2, vacina.getDescricao());
            comando.setString(3, vacina.getLimite_aplicacao());
            comando.setString(4, vacina.getPublicoAlvo().toString());

            comando.executeUpdate();
            ResultSet idGerado = comando.getGeneratedKeys();
            if (idGerado.next())
                vacina.setId(idGerado.getInt(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
