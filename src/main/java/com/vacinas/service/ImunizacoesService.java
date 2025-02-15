package com.vacinas.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.ImunizacoesDAO;
import com.vacinas.model.Imunizacoes;

public class ImunizacoesService {

    private Connection conexao;

    public ArrayList<Imunizacoes> consultarTodasImunizacoes() {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            ArrayList<Imunizacoes> imunizacoes = ImunizacoesDAO.consultarTodasImunizacoes();
            return imunizacoes;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Imunizacoes consultarPorIdPaciente(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            Imunizacoes imunizacoes = ImunizacoesDAO.consultarPorIdPaciente(idPaciente);
            return imunizacoes;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public int alterarImunizacoes(Imunizacoes imunizacoes) throws SQLException {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            var resultado = ImunizacoesDAO.atualizarImunizacoes(imunizacoes);
            return resultado;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw e;

        }
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

    public int contarVacinasPorPaciente(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.contarVacinasPorPaciente(idPaciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Imunizacoes consultarImunizacaoPorId(int id) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.consultarImunizacaoPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int contarVacinasAtrasadas(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.contarVacinasAtrasadas(idPaciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarVacinasAcimaIdade(int idadeMeses) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.contarVacinasAcimaIdade(idadeMeses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
