package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vacinas.enums.Sexo;
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
    public static Paciente consultarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            
            if (resultado.next()) {

                return new Paciente(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("cpf"),
                    Sexo.valueOf(resultado.getString("sexo")), 
                    resultado.getDate("data_nascimento").toLocalDate() 
                );
            }
        }
        return null; 
    }
}
    
