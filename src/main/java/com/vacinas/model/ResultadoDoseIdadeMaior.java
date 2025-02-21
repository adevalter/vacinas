package com.vacinas.model;

public class ResultadoDoseIdadeMaior {
  private int id;
  private String dose;
  private int idadeRecomendadaAplicacao;
  public ResultadoDoseIdadeMaior(int id, String dose, int idadeRecomendadaAplicacao) {
    this.id = id;
    this.dose = dose;
    this.idadeRecomendadaAplicacao = idadeRecomendadaAplicacao;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
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

}
