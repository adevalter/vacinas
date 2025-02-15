package com.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.vacinas.enums.PublicoAlvo;
import com.vacinas.model.ResultadoDoseIdadeMaior;
import com.vacinas.model.ResultadoNaoAplicaveis;
import com.vacinas.model.ResultadoVacinaIdadeMaior;
import com.vacinas.model.Vacina;

public class VacinaDAO {
    public static Connection conexao = null;

    public static void inserir(Vacina vacina) throws SQLException {
        String sql = "INSERT INTO vacinas (vacina, descricao, limite_aplicacao, publico_alvo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, vacina.getVacina());
            comando.setString(2, vacina.getDescricao());
            comando.setInt(3, vacina.getLimiteAplicacao());
            comando.setString(4, vacina.getPublicoAlvo().toString());

            comando.executeUpdate();
            ResultSet idGerado = comando.getGeneratedKeys();
            if (idGerado.next()) {
                vacina.setId(idGerado.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir vacina: " + e.getMessage());
            throw e;
        }
    }

    public static ArrayList<Vacina> listarPorFaixaEtaria(String faixaEtaria) throws SQLException {
        ArrayList<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM vacinas WHERE publico_alvo = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, faixaEtaria);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                vacinas.add(new Vacina(
                    resultado.getInt("id"),
                    resultado.getString("vacina"),
                    resultado.getString("descricao"),
                    resultado.getInt("limite_aplicacao"),
                   PublicoAlvo.valueOf( resultado.getString("publico_alvo"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vacinas por faixa etária: " + e.getMessage());
            throw e;
        }
        return vacinas;
    }

    public static ArrayList<ResultadoVacinaIdadeMaior> listarPorIdadeMaior(int idadeEmMeses) throws SQLException {
        ArrayList<ResultadoVacinaIdadeMaior> vacinas = new ArrayList<>();
        String sql ="""
        select d.id, v.vacina,d.dose,d.idade_recomendada_aplicacao,v.limite_aplicacao,v.publico_alvo
        from dose d inner join vacinas v on v.id = d.id_vacina
        where idade_recomendada_aplicacao > ? order by d.idade_recomendada_aplicacao
        """ ;

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idadeEmMeses);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                vacinas.add(new ResultadoVacinaIdadeMaior(
                    resultado.getString("vacina"),
                    resultado.getInt("limite_aplicacao"),
                    PublicoAlvo.valueOf( resultado.getString("publico_alvo")),
                    new ResultadoDoseIdadeMaior(resultado.getInt("id"), resultado.getString("dose"), resultado.getInt("idade_recomendada_aplicacao"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vacinas por idade maior: " + e.getMessage());
            throw e;
        }
        return vacinas;
    }

    public static ArrayList<ResultadoNaoAplicaveis> listarNaoAplicaveis(int idadePaciente) throws SQLException {
        ArrayList<ResultadoNaoAplicaveis> vacinas = new ArrayList<>();
        String sql = """
                SELECT d.id,v.vacina,d.dose,v.limite_aplicacao FROM imunizacoes i
                inner join dose d on d.id = i.id_dose 
                inner join vacinas v on v.id = d.id_vacina
                where i.id_paciente = ?
                """; 

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idadePaciente);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                vacinas.add(new ResultadoNaoAplicaveis(
                    resultado.getInt("id"),
                    resultado.getString("vacina"),
                    resultado.getString("dose"),
                    resultado.getInt("limite_aplicacao"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vacinas não aplicáveis: " + e.getMessage());
            throw e;
        }
        return vacinas;
    }
    public static ArrayList<Vacina> listarTodas() throws SQLException {
        ArrayList<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM vacinas ";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
    
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                vacinas.add(new Vacina(
                    resultado.getInt("id"),
                    resultado.getString("vacina"),
                    resultado.getString("descricao"),
                    resultado.getInt("limite_aplicacao"),
                    PublicoAlvo.valueOf( resultado.getString("publico_alvo"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vacinas não aplicáveis: " + e.getMessage());
            throw e;
        }
        return vacinas;
    }
}
