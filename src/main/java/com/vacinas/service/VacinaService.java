package com.vacinas.service;

import java.sql.Connection;
import java.sql.SQLException;

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

}
