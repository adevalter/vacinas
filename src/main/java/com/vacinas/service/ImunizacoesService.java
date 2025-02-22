package com.vacinas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.ImunizacoesDAO;
import com.vacinas.model.Imunizacoes;
import com.vacinas.model.ResultadoImunizacaoPorIdPaciente;

public class ImunizacoesService {

    private Connection conexao;

    public static int inserirImunizacao(Imunizacoes imunizacoes) {
        try {
            Connection conexao = ConexaoDAO.getConnection();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.inserirImunizacao(imunizacoes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<ResultadoImunizacaoPorIdPaciente> consultarTodasImunizacoes() {
        try {
            this.conexao = ConexaoDAO.getConnection();
            ImunizacoesDAO.conexao = conexao;
            ArrayList<ResultadoImunizacaoPorIdPaciente> imunizacoes = ImunizacoesDAO.consultarTodasImunizacoes();
            return imunizacoes;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ResultadoImunizacaoPorIdPaciente> consultarPorIdPaciente(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            ImunizacoesDAO.conexao = conexao;
            ArrayList<ResultadoImunizacaoPorIdPaciente> imunizacoes = ImunizacoesDAO.consultarPorIdPaciente(idPaciente);
            return imunizacoes;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public int alterarImunizacoes(Imunizacoes imunizacoes) throws SQLException {
        try {
            this.conexao = ConexaoDAO.getConnection();
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
            this.conexao = ConexaoDAO.getConnection();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.excluirImunizacoes(id);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return 0;
    }

    public Imunizacoes consultarImunizacaoPorId(int id) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.consultarImunizacaoPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Imunizacoes> consultarImunizacoesPorPacienteEIntervalo(int idPaciente, LocalDate dtInicio,
            LocalDate dtFim) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.consultarImunizacoesPorPacienteEIntervalo(idPaciente, dtInicio, dtFim);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean deletarPorPaciente(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            ImunizacoesDAO.conexao = conexao;
            return ImunizacoesDAO.deletarPorPaciente(idPaciente);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

}