package com.vacinas.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.EstatisticaDAO;

public class EstatisticaService {
    private Connection conexao;

    public int contarVacinasPorPaciente(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            EstatisticaDAO.conexao = conexao;
            return EstatisticaDAO.contarVacinasPorPaciente(idPaciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarVacinasAtrasadas(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            EstatisticaDAO.conexao = conexao;
            return EstatisticaDAO.contarVacinasAtrasadas(idPaciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarVacinasAcimaIdade(int idadeMeses) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            EstatisticaDAO.conexao = conexao;
            return EstatisticaDAO.contarVacinasAcimaIdade(idadeMeses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarVacinasProximoMes(int idPaciente) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            EstatisticaDAO.conexao = conexao;
            return EstatisticaDAO.contarVacinasProximoMes(idPaciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
