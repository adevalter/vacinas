package com.vacinas.dao;

import java.sql.*;
import java.util.ArrayList;
import com.vacinas.enums.Sexo;
import com.vacinas.model.ConsultaPaciente;
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

    public static ConsultaPaciente consultarPorId(int id) throws SQLException {
        String sql = """
                select p.id, p.nome, p.cpf, p.sexo, p.data_nascimento,
                (select nome from paciente where id = p.responsavel) as nome_responsavel
                from paciente p where p.id = ?
                """;
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {

                return new ConsultaPaciente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        Sexo.valueOf(resultado.getString("sexo")),
                        resultado.getDate("data_nascimento").toLocalDate(),
                        resultado.getString("nome_responsavel"));
            }
        }
        return null;
    }

    public static Paciente criar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO paciente (nome, cpf, sexo, data_nascimento, responsavel) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().toString());
            comando.setDate(4, Date.valueOf(paciente.getData_nascimento()));
            if(paciente.getResponsavel()==null){
                comando.setNull(5, java.sql.Types.INTEGER);
            } else {
                comando.setInt(5, paciente.getResponsavel());
            }

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

    public static ArrayList<ConsultaPaciente> buscarTodos() throws SQLException {
        String sql = """
                select p.id, p.nome, p.cpf, p.sexo, p.data_nascimento,
                (select nome from paciente where id = p.responsavel) as nome_responsavel
                from paciente p
                """;
        ArrayList<ConsultaPaciente> pacientes = new ArrayList<>();
        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            System.out.println("Todos pacianete ====>" + pacientes);
            while (resultado.next()) {
                ConsultaPaciente paciente = new ConsultaPaciente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        Sexo.valueOf(resultado.getString("sexo")),
                        resultado.getDate("data_nascimento").toLocalDate(),
                        resultado.getString("nome_responsavel"));
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }
}