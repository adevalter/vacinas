package com.vacinas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.VacinaDAO;
import com.vacinas.model.Vacina;

public class VacinaService {

    private Connection conexao;

    public Vacina inserir(Vacina vacina) throws SQLException {
        this.conexao = ConexaoDAO.getConexao();
        VacinaDAO.conexao = conexao;
        VacinaDAO.inserir(vacina);
        return vacina;
    }

    public Vacina consultarPorId(int id) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            VacinaDAO.conexao = conexao;
            Vacina vacina = VacinaDAO.consultarPorId(id);
            return vacina;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Vacina> consultarTodasVacinas() {
        try {
            this.conexao = ConexaoDAO.getConexao();
            VacinaDAO.conexao = conexao;
            ArrayList<Vacina> vacinas = VacinaDAO.consultarTodasVacinas();
            return vacinas;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public int alterarVacina(Vacina vacina) {
        try {
            this.conexao = ConexaoDAO.getConexao();
            VacinaDAO.conexao = conexao;
            var resultado = VacinaDAO.atualizarVacina(vacina);
            return resultado;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;

    }

    public int excluir(int id) {
        try {

            this.conexao = ConexaoDAO.getConexao();
            VacinaDAO.conexao = conexao;
            return VacinaDAO.excluirVacina(id);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return 0;
    }
}
