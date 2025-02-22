package com.vacinas.model;

import java.time.LocalDate;

public class ResultadoImunizacaoPorIdPaciente {
    private int id;
    private String nome;
    private String dose;
    private LocalDate dataAplicacao;
    private String fabricante;
    private String lote;
    private String localAplicacao;
    private String profissionalAplicador;

    public ResultadoImunizacaoPorIdPaciente(int id, String nome, String dose, LocalDate dataAplicacao,
                                            String fabricante, String lote, String localAplicacao, String profissionalAplicador) {
        this.id = id;
        this.nome = nome;
        this.dose = dose;
        this.dataAplicacao = dataAplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.localAplicacao = localAplicacao;
        this.profissionalAplicador = profissionalAplicador;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDose() {
        return dose;
    }
    public void setDose(String dose) {
        this.dose = dose;
    }
    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }
    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }
    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    public String getLote() {
        return lote;
    }
    public void setLote(String lote) {
        this.lote = lote;
    }
    public String getLocalAplicacao() {
        return localAplicacao;
    }
    public void setLocalAplicacao(String localAplicacao) {
        this.localAplicacao = localAplicacao;
    }
    public String getProfissionalAplicador() {
        return profissionalAplicador;
    }
    public void setProfissionalAplicador(String profissionalAplicador) {
        this.profissionalAplicador = profissionalAplicador;
    }
}
