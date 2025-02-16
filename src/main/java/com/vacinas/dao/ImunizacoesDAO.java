package com.vacinas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.vacinas.model.Imunizacoes;

public class ImunizacoesDAO {
    public static Connection conexao = null;

    public static int inserirImunizacao(Imunizacoes imunizacoes) throws SQLException {
        String sql = "INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, imunizacoes.getIdPaciente());
            comando.setInt(2, imunizacoes.getIdDose());
            comando.setObject(3, imunizacoes.getDataAplicacao());
            comando.setString(4, imunizacoes.getFabricante());
            comando.setString(5, imunizacoes.getLote());
            comando.setString(6, imunizacoes.getLocalAplicacao());
            comando.setString(7, imunizacoes.getProfissionalAplicador());
            
            return comando.executeUpdate();
        }
    }

    public static int atualizarImunizacoes(Imunizacoes imunizacoes) throws SQLException {

        String sql = "UPDATE imunizacoes SET id_paciente = ?, id_dose = ?, data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ? ";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, imunizacoes.getIdPaciente());
            comando.setInt(2, imunizacoes.getIdDose());
            comando.setObject(3, imunizacoes.getDataAplicacao());
            comando.setString(4, imunizacoes.getFabricante());
            comando.setString(5, imunizacoes.getLote());
            comando.setString(6, imunizacoes.getLocalAplicacao().toString());
            comando.setString(7, imunizacoes.getProfissionalAplicador());
            comando.setInt(8, imunizacoes.getId());
            

            int linhasAlteradas = comando.executeUpdate();
            return linhasAlteradas;
        }
    }

    public static int excluirImunizacoes(int id) throws SQLException {
        String sql = "Delete from imunizacoes where id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            var resultado = comando.executeUpdate();
            return resultado;
        } catch (Exception e) {
            conexao.rollback();
            throw e;
        }
    }

    public static ArrayList<Imunizacoes> consultarTodasImunizacoes() throws SQLException {
        ArrayList<Imunizacoes> listaImunizacoes = new ArrayList<Imunizacoes>();

        String sql = "Select * from imunizacoes";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
                listaImunizacoes.add(new Imunizacoes(
                    resultado.getInt("id"),
                    resultado.getInt("id_paciente"),
                    resultado.getInt("id_dose"),
                    dataAplicacao,
                    resultado.getString("fabricante"), 
                    resultado.getString("lote"),
                    resultado.getString("local_aplicacao"),
                    resultado.getString("profissional_aplicador")));
            }
        }
        return listaImunizacoes;
    }
    
    public static Imunizacoes consultarPorIdPaciente(int idPaciente) throws SQLException {
        Imunizacoes imunizacoes = null;
        String sql = "Select * from imunizacoes where id_paciente = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
                imunizacoes = new Imunizacoes(
                    resultado.getInt("id"),
                    resultado.getInt("id_paciente"),
                    resultado.getInt("id_dose"),
                    dataAplicacao,
                    resultado.getString("fabricante"), 
                    resultado.getString("lote"),
                    resultado.getString("local_aplicacao"),
                    resultado.getString("profissional_aplicador"));
            }
        }
        return imunizacoes;
    }
    public static int contarVacinasPorPaciente(int idPaciente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM imunizacoes WHERE id_paciente = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        }
        return 0;
    }

    public static Imunizacoes consultarImunizacaoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM imunizacoes WHERE id = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
                return new Imunizacoes(
                        resultado.getInt("id"),
                        resultado.getInt("id_paciente"),
                        resultado.getInt("id_dose"),
                        dataAplicacao,
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("local_aplicacao"),
                        resultado.getString("profissional_aplicador")
                );
            }
        }
        return null;
    }

    public int contarVacinasAtrasadas(int idPaciente) throws SQLException {
        String sql = """
        SELECT COUNT(*) 
        FROM dose d
        JOIN paciente p ON p.id = ?
        LEFT JOIN imunizacoes i ON d.id = i.id_dose AND i.id_paciente = p.id
        WHERE i.id IS NULL 
        AND d.idade_recomendada_aplicacao <= TIMESTAMPDIFF(MONTH, p.data_nascimento, CURDATE())
        """;

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao contar vacinas atrasadas para o paciente.", e);
        }
        return 0;
    }

    public int contarVacinasAcimaIdade(int idadeMeses) throws SQLException {
        String sql = """
        SELECT COUNT(*) 
        FROM dose 
        WHERE idade_recomendada_aplicacao > ?
        """;

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idadeMeses);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao contar vacinas com recomendação acima da idade informada.", e);
        }
        return 0;
    }

    public static int contarVacinasProximoMes(int idPaciente) throws SQLException {
        String sql = """
        SELECT COUNT(*)
        FROM dose d
        JOIN vacina v ON d.id_vacina = v.id
        JOIN paciente p ON p.id = ?
        LEFT JOIN imunizacoes i ON d.id = i.id_dose AND i.id_paciente = p.id
        WHERE i.id IS NULL
        AND d.idade_recomendada_aplicacao = TIMESTAMPDIFF(MONTH, p.data_nascimento, CURDATE()) + 1
    """;

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            comando.setInt(2, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        }
        return 0;
    }

    public static ArrayList<Imunizacoes> consultarImunizacoesPorPacienteEIntervalo(int idPaciente, LocalDate dtInicio, LocalDate dtFim) throws SQLException {
        String sql = """
        SELECT * FROM imunizacoes 
        WHERE id_paciente = ? 
        AND data_aplicacao BETWEEN ? AND ?
    """;

        ArrayList<Imunizacoes> listaImunizacoes = new ArrayList<>();

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            comando.setDate(2, Date.valueOf(dtInicio));
            comando.setDate(3, Date.valueOf(dtFim));

            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
                listaImunizacoes.add(new Imunizacoes(
                        resultado.getInt("id"),
                        resultado.getInt("id_paciente"),
                        resultado.getInt("id_dose"),
                        dataAplicacao,
                        resultado.getString("fabricante"),
                        resultado.getString("lote"),
                        resultado.getString("local_aplicacao"),
                        resultado.getString("profissional_aplicador")
                ));
            }
        }
        return listaImunizacoes;
    }

    public static boolean deletarPorPaciente(int idPaciente) throws SQLException {
        String sql = "DELETE from imunizacoes WHERE id_paciente = ?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            return comando.executeUpdate() > 0;
        }
    }
}

