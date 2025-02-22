package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vacinas.core.config.ConexaoDAO;

public class EstatisticaDAO {
    public static Connection conexao;

    static {
        try {
            conexao = ConexaoDAO.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao conectar ao banco!", e);
        }
    }

    public static int contarVacinasPorPaciente(int idPaciente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM imunizacoes WHERE id_paciente = ?";

        try (
            Connection conexao = ConexaoDAO.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        }
        return 0;
    }

    public static int contarVacinasAtrasadas(int idPaciente) throws SQLException {
        String sql = """
                SELECT COUNT(*)
                FROM dose d
                JOIN paciente p ON p.id = ?
                LEFT JOIN imunizacoes i ON d.id = i.id_dose AND i.id_paciente = ?
                WHERE i.id IS NULL
                AND d.idade_recomendada_aplicacao <= TIMESTAMPDIFF(MONTH, p.data_nascimento, CURDATE())
                """;

        try (Connection conexao = ConexaoDAO.getConnection();
                PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idPaciente);
            comando.setInt(2, idPaciente);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }
        }
        return 0;
    }

    public static int contarVacinasAcimaIdade(int idadeMeses) throws SQLException {
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
        }
        return 0;
    }

    public static int contarVacinasProximoMes(int idPaciente) throws SQLException {
        String sql = """
                    SELECT COUNT(*)
                    FROM dose d
                    JOIN vacinas v ON d.id_vacina = v.id
                    JOIN paciente p ON p.id = ?
                    LEFT JOIN imunizacoes i ON d.id = i.id_dose AND i.id_paciente = p.id
                    WHERE i.id IS NULL
                    AND d.idade_recomendada_aplicacao = TIMESTAMPDIFF(MONTH, p.data_nascimento, CURDATE()) + 1
                """;

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        }
        return 0;
    }
}
