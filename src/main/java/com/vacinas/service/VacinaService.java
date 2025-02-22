package com.vacinas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vacinas.core.config.ConexaoDAO;
import com.vacinas.dao.VacinaDAO;
import com.vacinas.model.ResultadoNaoAplicaveis;
import com.vacinas.model.ResultadoVacinaIdadeMaior;
import com.vacinas.model.Vacina;

public class VacinaService {

    private Connection conexao;

    public Vacina inserir(Vacina vacina) throws SQLException {
        this.conexao = ConexaoDAO.getConnection();
        VacinaDAO.conexao = conexao;
        VacinaDAO.inserir(vacina);
        return vacina;
    }

    public ArrayList<Vacina> listarPorFaixaEtaria(String faixa) throws SQLException {
        try 
        {
            
            this.conexao = ConexaoDAO.getConnection();
            VacinaDAO.conexao = conexao;
            ArrayList<Vacina> listaporFaixaEtaria = VacinaDAO.listarPorFaixaEtaria(faixa);
            return listaporFaixaEtaria;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new  ArrayList<Vacina>();
    }
    public ArrayList<ResultadoVacinaIdadeMaior> listarPorIdadeMaior(int idadeEmMeses) throws SQLException {
        try 
        {
            
            this.conexao = ConexaoDAO.getConnection();
            VacinaDAO.conexao = conexao;
            ArrayList<ResultadoVacinaIdadeMaior> listarTodas = VacinaDAO.listarPorIdadeMaior(idadeEmMeses);
            return listarTodas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new  ArrayList<ResultadoVacinaIdadeMaior>();
    }
    public ArrayList<ResultadoNaoAplicaveis> listarNaoAplicaveis(int idadePaciente) throws SQLException {
        try 
        {
            
            this.conexao = ConexaoDAO.getConnection();
            VacinaDAO.conexao = conexao;
            ArrayList<ResultadoNaoAplicaveis> listarTodas = VacinaDAO.listarNaoAplicaveis(idadePaciente);
            return listarTodas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new  ArrayList<ResultadoNaoAplicaveis>();
    }
    public ArrayList<Vacina> listarTodas() throws SQLException {
        try {
            this.conexao = ConexaoDAO.getConnection();
            VacinaDAO.conexao = conexao;
            return VacinaDAO.listarTodas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    }
    



