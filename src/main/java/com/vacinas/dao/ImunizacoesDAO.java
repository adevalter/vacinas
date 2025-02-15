package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;

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


}
