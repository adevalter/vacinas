package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.vacinas.model.Paciente;

public class PacienteDAO {
    public static Connection conexao = null;

    public int atualizarPaciente(Paciente paciente) throws SQLException {
        String sql = "UPDATE pacientes SET nome = ?, cpf = ?, sexo = ?, data_nascimento = ? WHERE id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().toString());
            comando.setObject(4, paciente.getData_nascimento());
            comando.setInt(5, paciente.getId());

            return comando.executeUpdate();
        }

    }

}
