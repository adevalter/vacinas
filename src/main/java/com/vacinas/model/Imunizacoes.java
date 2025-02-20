package com.vacinas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;


public class Imunizacoes {

    private int id;

    @JsonProperty("id_paciente")
    private int idPaciente;

    @JsonProperty("id_dose")
    private int idDose;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataAplicacao;

    private String fabricante;
    private String lote;

    @JsonProperty("local_aplicacao")
    private String localAplicacao;

    @JsonProperty("profissional_aplicador")
    private String profissionalAplicador;

    public Imunizacoes() {
        // Construtor vazio necessário para desserialização do JSON
    }

    public Imunizacoes(int idPaciente, int idDose, LocalDate dataAplicacao, String fabricante, String lote,
                       String localAplicacao, String profissionalAplicador) {
        this.idPaciente = idPaciente;
        this.idDose = idDose;
        this.dataAplicacao = dataAplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.localAplicacao = localAplicacao;
        this.profissionalAplicador = profissionalAplicador;
    }

    public Imunizacoes(int id, int idPaciente, int idDose, LocalDate dataAplicacao, String fabricante, String lote,
                       String localAplicacao, String profissionalAplicador) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idDose = idDose;
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

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDose() {
        return idDose;
    }

    public void setIdDose(int idDose) {
        this.idDose = idDose;
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
