package com.vacinas.service;

import java.sql.SQLException;

import com.vacinas.dao.PacienteDAO;
import com.vacinas.model.Paciente;

public class PacienteService {
    private PacienteDAO pacienteDAO;

    public PacienteService() {
        this.pacienteDAO = new PacienteDAO();
    }

    public boolean atualizarPaciente(Paciente paciente) {
        try {
            int linhasAfetadas = pacienteDAO.atualizarPaciente(paciente);
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
