package com.vacinas.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import com.vacinas.model.Paciente;


import com.vacinas.model.Dose;
import com.vacinas.model.Imunizacoes;
import com.vacinas.model.ResultadoImunizacaoPorIdPaciente;

public class ImunizacoesDAO {
    public static Connection conexao;

    static {
        try {
            conexao = ConexaoDAO.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao conectar ao banco!", e);
        }
    }

    public static boolean existeImunizacao(int idPaciente, int idDose) throws SQLException {
        String sql = "SELECT COUNT(*) FROM imunizacoes WHERE id_paciente = ? AND id_dose = ?";

        try (Connection conexao = ConexaoDAO.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idDose);

            try (ResultSet resultado = stmt.executeQuery()) {
                return resultado.next() && resultado.getInt(1) > 0;
            }
        }
    }
    public static int inserirImunizacao(Imunizacoes imunizacao) throws SQLException {
        String sql = "INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDAO.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, imunizacao.getIdPaciente());
            stmt.setInt(2, imunizacao.getIdDose());

            if (imunizacao.getDataAplicacao() != null) {
                stmt.setDate(3, Date.valueOf(imunizacao.getDataAplicacao()));
            } else {
                throw new IllegalArgumentException("Data de aplicação não pode ser nula.");
            }

            stmt.setString(4, imunizacao.getFabricante());
            stmt.setString(5, imunizacao.getLote());
            stmt.setString(6, imunizacao.getLocalAplicacao());
            stmt.setString(7, imunizacao.getProfissionalAplicador());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return 0;
    }

    public static int atualizarImunizacoes(Imunizacoes imunizacao) throws SQLException {
        String sql = "UPDATE imunizacoes SET id_paciente = ?, id_dose = ?, data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ?";

        try (Connection conexao = ConexaoDAO.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, imunizacao.getIdPaciente());
            stmt.setInt(2, imunizacao.getIdDose());
            stmt.setDate(3, Date.valueOf(imunizacao.getDataAplicacao()));
            stmt.setString(4, imunizacao.getFabricante());
            stmt.setString(5, imunizacao.getLote());
            stmt.setString(6, imunizacao.getLocalAplicacao());
            stmt.setString(7, imunizacao.getProfissionalAplicador());
            stmt.setInt(8, imunizacao.getId());

            return stmt.executeUpdate();
        }
    }

    public static int excluirImunizacoes(int id) throws SQLException {
        String sql = "DELETE FROM imunizacoes WHERE id = ?";

        try (Connection conexao = ConexaoDAO.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }

<<<<<<< HEAD
    public static ArrayList<Imunizacoes> consultarTodasImunizacoes() throws SQLException {
        ArrayList<Imunizacoes> listaImunizacoes = new ArrayList<>();
        String sql = "SELECT * FROM imunizacoes";

        try (Connection conexao = ConexaoDAO.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
=======
    public static ArrayList<ResultadoImunizacaoPorIdPaciente> consultarTodasImunizacoes() throws SQLException {
        ArrayList<ResultadoImunizacaoPorIdPaciente> listaImunizacoes = new ArrayList<ResultadoImunizacaoPorIdPaciente>();

        String sql = """
                SELECT i.id, p.nome, d.dose, i.data_aplicacao, i.fabricante, i.lote, i.local_aplicacao, i.profissional_aplicador
                FROM imunizacoes i inner join paciente p on p.id = i.id_paciente
                inner join dose d on d.id = i.id_dose
                """;
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
>>>>>>> f69027de2f4859806942ca557943d67fb12329a0

            while (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
<<<<<<< HEAD
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
=======
                listaImunizacoes.add(new ResultadoImunizacaoPorIdPaciente(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("dose"),
                    dataAplicacao,
                    resultado.getString("fabricante"), 
                    resultado.getString("lote"),
                    resultado.getString("local_aplicacao"),
                    resultado.getString("profissional_aplicador")));
>>>>>>> f69027de2f4859806942ca557943d67fb12329a0
            }
        }
        return listaImunizacoes;
    }
<<<<<<< HEAD

    public static Imunizacoes consultarPorIdPaciente(int idPaciente) throws SQLException {
        Imunizacoes imunizacoes = null;
        String sql = "Select * from imunizacoes where id_paciente = ?";
=======
    
    public static ArrayList<ResultadoImunizacaoPorIdPaciente> consultarPorIdPaciente(int idPaciente) throws SQLException {
        ArrayList<ResultadoImunizacaoPorIdPaciente> listaImunizacoes = new ArrayList<ResultadoImunizacaoPorIdPaciente>();

        String sql = """
                SELECT i.id, p.nome, d.dose, i.data_aplicacao, i.fabricante, i.lote, i.local_aplicacao, i.profissional_aplicador
                FROM imunizacoes i inner join paciente p on p.id = i.id_paciente
                inner join dose d on d.id = i.id_dose 
                where i.id_paciente = ?
                """;
>>>>>>> f69027de2f4859806942ca557943d67fb12329a0
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                LocalDate dataAplicacao = resultado.getDate("data_aplicacao").toLocalDate();
                listaImunizacoes.add(new ResultadoImunizacaoPorIdPaciente(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("dose"),
                    dataAplicacao,
                    resultado.getString("fabricante"),
                    resultado.getString("lote"),
                    resultado.getString("local_aplicacao"),
                    resultado.getString("profissional_aplicador")));
            }
        }
        return listaImunizacoes;
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

    public static ArrayList<Paciente> listarTodosPacientes() throws SQLException {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT id, nome FROM pacientes";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Paciente paciente = new Paciente(rs.getInt("id"), rs.getString("nome"));
            pacientes.add(paciente);
        }
        return pacientes;
    }

    public static ArrayList<Dose> listarTodasDoses() throws SQLException {
        ArrayList<Dose> doses = new ArrayList<>();
        String sql = "SELECT id, id_vacina, dose, idade_recomendada_aplicacao FROM dose";

        System.out.println("Executando SQL: " + sql);

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

        System.out.println("Total de doses encontradas: " + doses.size());
        return doses;
    }

}