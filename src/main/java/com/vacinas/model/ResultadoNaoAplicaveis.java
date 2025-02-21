package com.vacinas.model;

public class ResultadoNaoAplicaveis {
  private int id;
  private String dose;
  private String vacina;
  private int limite_aplicacao;
  public ResultadoNaoAplicaveis(int id, String dose, String vacina, int limite_aplicacao) {
    this.id = id;
    this.dose = dose;
    this.vacina = vacina;
    this.limite_aplicacao = limite_aplicacao;
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
  public String getVacina() {
    return vacina;
  }
  public void setVacina(String vacina) {
    this.vacina = vacina;
  }
  public int getLimite_aplicacao() {
    return limite_aplicacao;
  }
  public void setLimite_aplicacao(int limite_aplicacao) {
    this.limite_aplicacao = limite_aplicacao;
  }
}
