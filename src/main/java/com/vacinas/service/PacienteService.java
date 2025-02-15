package com.vacinas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.PacienteDAO;

import com.vacinas.model.Paciente;

public class PacienteService {
    private Connection conexao;

    public boolean atualizarPaciente(Paciente paciente) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            PacienteDAO.conexao = conexao;
            int linhasAfetadas = PacienteDAO.atualizarPaciente(paciente);
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Paciente consultarPorId(int id) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            PacienteDAO.conexao = conexao;
            return PacienteDAO.consultarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Paciente create(Paciente paciente) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            PacienteDAO.conexao = this.conexao;
            return PacienteDAO.criar(paciente);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            PacienteDAO.conexao = this.conexao;
            return PacienteDAO.deletar(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Paciente> buscarTodos() {
        try {
            this.conexao = ConexaoDAO.getConexao();
            PacienteDAO.conexao = this.conexao;
            return PacienteDAO.buscarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}