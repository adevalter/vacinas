package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.vacinas.model.Imunizacoes;

public class ImunizacoesDAO {
    public static Connection conexao = null;

    public static int atualizarImunizacoes(Imunizacoes imunizacoes) throws SQLException {

        String sql = "UPDATE imunizacoes SET data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ? ";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setObject(1, imunizacoes.getDataAplicacao());
            comando.setString(2, imunizacoes.getFabricante());
            comando.setString(3, imunizacoes.getLote());
            comando.setString(4, imunizacoes.getLocalAplicacao().toString());
            comando.setString(5, imunizacoes.getProfissionalAplicador());
            comando.setInt(6, imunizacoes.getId());
            

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


}
