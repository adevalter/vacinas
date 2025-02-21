package com.vacinas.model;

public class Dose {
    private int id;
    private int id_vacina;
    private String dose;
    private int idadeRecomendadaAplicacao;

    public Dose() {}

    public Dose(int id_vacina, String dose, int idadeRecomendadaAplicacao) {
        this.id_vacina = id_vacina;
        this.dose = dose;
        this.idadeRecomendadaAplicacao = idadeRecomendadaAplicacao;
    }

    public Dose(int id, int id_vacina, String dose, int idadeRecomendadaAplicacao) {
        this.id = id;
        this.id_vacina = id_vacina;
        this.dose = dose;
        this.idadeRecomendadaAplicacao = idadeRecomendadaAplicacao;
    }

    public Dose(int id, String dose) {
        this.id = id;
        this.dose = dose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(int id_vacina) {
        this.id_vacina = id_vacina;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public int getIdadeRecomendadaAplicacao() {
        return idadeRecomendadaAplicacao;
    }

    public void setIdadeRecomendadaAplicacao(int idadeRecomendadaAplicacao) {
        this.idadeRecomendadaAplicacao = idadeRecomendadaAplicacao;
    }

    public int getIdVacina() {
        return id_vacina;
    }

    public void setIdVacina(int id_vacina) {
        this.id_vacina = id_vacina;
    }

}
