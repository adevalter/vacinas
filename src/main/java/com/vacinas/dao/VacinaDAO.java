package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.vacinas.enums.PublicoAlvo;
import com.vacinas.model.Vacina;

public class VacinaDAO {
    public static Connection conexao = null;

    public static void inserir(Vacina vacina) throws SQLException {
        String sql = "INSERT INTO vacinas ( vacina, descricao, limite_aplicacao, publico_alvo) VALUES( ?, ?, ?, ?)";
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

    public static Vacina consultarPorId(int id) throws SQLException {
        Vacina vacina = null;
        String sql = "Select * from vacinas where id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                vacina = new Vacina(resultado.getInt("id"),
                        resultado.getString("vacina"),
                        resultado.getString("descricao"),
                        resultado.getString("limite_aplicacao"),
                        PublicoAlvo.valueOf(resultado.getString("publico_alvo")));
            }
        }
        return vacina;
    }

    public static ArrayList<Vacina> consultarTodasVacinas() throws SQLException {
        ArrayList<Vacina> listaVacina = new ArrayList<Vacina>();

        String sql = "Select * from vacinas";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                listaVacina.add(new Vacina(resultado.getInt("id"),
                        resultado.getString("vacina"),
                        resultado.getString("descricao"),
                        resultado.getString("limite_aplicacao"),
                        PublicoAlvo.valueOf(resultado.getString("publico_alvo"))));
            }
        }
        return listaVacina;
    }

    public static int atualizarVacina(Vacina vacina) throws SQLException {

        String sql = "UPDATE vacinas SET vacina = ?, descricao = ?, limite_aplicacao = ?, publico_alvo = ? WHERE id = ? ";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, vacina.getVacina());
            comando.setString(2, vacina.getDescricao());
            comando.setString(3, vacina.getLimite_aplicacao());
            comando.setString(4, vacina.getPublicoAlvo().toString());
            comando.setInt(5, vacina.getId());

            int linhasAlteradas = comando.executeUpdate();
            return linhasAlteradas;
        }
    }

    public static int excluirVacina(int id) throws SQLException {
        String sql = "Delete from vacinas where id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            var resultado = comando.executeUpdate();
            return resultado;
        } catch (Exception e) {
            conexao.rollback();
            throw e;
        }
    }

}
