package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import com.vacinas.model.Imunizacoes;

public class ImunizacoesDAO {
    public static Connection conexao = null;

    public static int atualizarImunizacoes(Imunizacoes imunizacoes) throws SQLException {

        String sql = "UPDATE imunizacoes SET id_paciente = ?, id_dose = ?, data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ? ";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, imunizacoes.getIdPaciente());
            comando.setInt(2, imunizacoes.getIdDose());
            comando.setObject(3, imunizacoes.getDataAplicacao());
            comando.setString(4, imunizacoes.getFabricante());
            comando.setString(5, imunizacoes.getLote());
            comando.setString(6, imunizacoes.getLocalAplicacao().toString());
            comando.setString(7, imunizacoes.getProfissionalAplicador());
            comando.setInt(8, imunizacoes.getId());
            

            int linhasAlteradas = comando.executeUpdate();
            return linhasAlteradas;
        }
    }

    public static int excluirImunizacoes(int id) throws SQLException {
        String sql = "Delete from imunizacoes where id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            var resultado = comando.executeUpdate();
            return resultado;
        } catch (Exception e) {
            conexao.rollback();
            throw e;
        }
    }

    public static ArrayList<Imunizacoes> consultarTodasImunizacoes() throws SQLException {
        ArrayList<Imunizacoes> listaImunizacoes = new ArrayList<Imunizacoes>();

        String sql = "Select * from imunizacoes";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
                listaImunizacoes.add(new Imunizacoes(
                    resultado.getInt("id"),
                    resultado.getInt("id_paciente"),
                    resultado.getInt("id_dose"),
                    dataAplicacao,
                    resultado.getString("fabricante"), 
                    resultado.getString("lote"),
                    resultado.getString("local_aplicacao"),
                    resultado.getString("profissional_aplicador")));
            }
        }
        return listaImunizacoes;
    }
    
    public static Imunizacoes consultarPorIdPaciente(int idPaciente) throws SQLException {
        Imunizacoes imunizacoes = null;
        String sql = "Select * from imunizacoes where id_paciente = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
                imunizacoes = new Imunizacoes(
                    resultado.getInt("id"),
                    resultado.getInt("id_paciente"),
                    resultado.getInt("id_dose"),
                    dataAplicacao,
                    resultado.getString("fabricante"), 
                    resultado.getString("lote"),
                    resultado.getString("local_aplicacao"),
                    resultado.getString("profissional_aplicador"));
            }
        }
        return imunizacoes;
    }

    public static int contarVacinasPorPaciente(int idPaciente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM imunizacoes WHERE id_paciente = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        }
        return 0;
    }


}
