package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vacinas.enums.Sexo;
import com.vacinas.model.Paciente;

public class PacienteDAO {
    public static Connection conexao = null;

    public static int atualizarPaciente(Paciente paciente) throws SQLException {
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, sexo = ?, data_nascimento = ? WHERE id = ?";
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
        String sql = "SELECT * FROM paciente WHERE id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {

                return new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        Sexo.valueOf(resultado.getString("sexo")),
                        resultado.getDate("data_nascimento").toLocalDate());
            }
        }
        return null;
    }

    public Paciente criar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO PACIENTE (nome, cpf, sexo, data_nascimento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().toString());
            comando.setDate(4, Date.valueOf(paciente.getData_nascimento()));
    
            ResultSet resultado = comando.executeUpdate();
            if (resultado.next()) {

                return new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        Sexo.valueOf(resultado.getString("sexo")),
                        resultado.getDate("data_nascimento").toLocalDate());
            }
            return null;
        }
        return null
    }
    
    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM PACIENTE WHERE id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
    
            int resultado = comando.executeUpdate();
            return resultado > 0;
        }
        return null
    
    }

    public static List<Paciente> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM PACIENTE";
        List<Paciente> pacientes = new ArrayList<>();

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Paciente paciente = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        Sexo.valueOf(resultado.getString("sexo")),
                        resultado.getDate("data_nascimento").toLocalDate());
                pacientes.add(paciente);
            }
            return pacientes
        } 
        return null;
    }
}
