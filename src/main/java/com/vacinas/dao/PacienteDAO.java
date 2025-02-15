package com.vacinas.dao;

import java.sql.*;
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

    public static Paciente criar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO paciente (nome, cpf, sexo, data_nascimento) VALUES (?, ?, ?, ?)";

        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().toString());
            comando.setDate(4, Date.valueOf(paciente.getData_nascimento()));

            int linhasAfetadas = comando.executeUpdate();

            try (ResultSet rs = comando.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    paciente.setId(idGerado);

                }
            }
            return paciente;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir paciente: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM paciente WHERE id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            return comando.executeUpdate() > 0;
        }
    }

    public static List<Paciente> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM paciente";
        List<Paciente> pacientes = new ArrayList<>();
        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            System.out.println("Todos pacianete ====>" + pacientes);
            while (resultado.next()) {
                Paciente paciente = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        Sexo.valueOf(resultado.getString("sexo")),
                        resultado.getDate("data_nascimento").toLocalDate());
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }
}