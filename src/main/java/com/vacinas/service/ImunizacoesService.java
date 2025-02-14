package com.vacinas.service;

import java.sql.SQLException;
import java.sql.Connection;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.ImunizacoesDAO;
import com.vacinas.model.Imunizacoes;

public class ImunizacoesService {
    
    private Connection conexao;

    public int alterarImunizacoes(Imunizacoes imunizacoes) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            var resultado = ImunizacoesDAO.atualizarImunizacoes(imunizacoes);
            return resultado;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;

    }

    public int excluir(int id) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.excluirImunizacoes(id);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return 0;
    }
}
