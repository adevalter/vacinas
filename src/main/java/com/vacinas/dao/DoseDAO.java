package com.vacinas.dao;

import com.vacinas.model.Dose;
import com.vacinas.core.config.ConexaoDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoseDAO {
    private static Connection conexao;

    static {
        try {
            conexao = ConexaoDAO.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao conectar ao banco!", e);
        }
    }

    public static List<Dose> listarTodasDoses() throws SQLException {
        List<Dose> doses = new ArrayList<>();
        String sql = "SELECT id, id_vacina, dose, idade_recomendada_aplicacao FROM dose";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Dose dose = new Dose();
                dose.setId(rs.getInt("id"));
                dose.setIdVacina(rs.getInt("id_vacina"));
                dose.setDose(rs.getString("dose"));
                dose.setIdadeRecomendadaAplicacao(rs.getInt("idade_recomendada_aplicacao"));
                doses.add(dose);
            }
        }
        return doses;
    }
}
