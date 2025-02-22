package com.vacinas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.PacienteDAO;
import com.vacinas.model.ConsultaPaciente;
import com.vacinas.model.Paciente;

public class PacienteService {
    private Connection conexao;

    public boolean atualizarPaciente(Paciente paciente) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            PacienteDAO.conexao = conexao;
            int linhasAfetadas = PacienteDAO.atualizarPaciente(paciente);
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ConsultaPaciente consultarPorId(int id) {
        try {
            this.conexao = ConexaoDAO.getConnection();
            PacienteDAO.conexao = conexao;
            return PacienteDAO.consultarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Paciente create(Paciente paciente) throws SQLException {
        try {
            this.conexao = ConexaoDAO.getConnection();
            PacienteDAO.conexao = this.conexao;
            return PacienteDAO.criar(paciente);
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean delete(int id) throws SQLException {
        try {
            this.conexao = ConexaoDAO.getConnection();
            PacienteDAO.conexao = this.conexao;
            return PacienteDAO.deletar(id);
        } catch (SQLException e) {
           throw e;
        }
    }

    public ArrayList<ConsultaPaciente> buscarTodos() {
        try {
            this.conexao = ConexaoDAO.getConnection();
            PacienteDAO.conexao = this.conexao;
            return PacienteDAO.buscarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<ConsultaPaciente>();
        }
    }
}