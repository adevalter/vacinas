package com.vacinas.model;

import com.vacinas.enums.PublicoAlvo;

public class ResultadoVacinaIdadeMaior { 
    private String vacina;
    private int  limiteAplicacao;
    private PublicoAlvo publicoAlvo;
    private ResultadoDoseIdadeMaior dose;
    public ResultadoVacinaIdadeMaior() {
    }
    public ResultadoVacinaIdadeMaior(String vacina, int limiteAplicacao, PublicoAlvo publicoAlvo, ResultadoDoseIdadeMaior dose) {
      this.vacina = vacina;
      this.limiteAplicacao = limiteAplicacao;
      this.publicoAlvo = publicoAlvo;
      this.dose = dose;
    }
    public String getVacina() {
      return vacina;
    }
    public void setVacina(String vacina) {
      this.vacina = vacina;
    }
    public int getLimiteAplicacao() {
      return limiteAplicacao;
    }
    public void setLimiteAplicacao(int limiteAplicacao) {
      this.limiteAplicacao = limiteAplicacao;
    }
    public PublicoAlvo getPublicoAlvo() {
      return publicoAlvo;
    }
    public void setPublicoAlvo(PublicoAlvo publicoAlvo) {
      this.publicoAlvo = publicoAlvo;
    }
    public ResultadoDoseIdadeMaior getDose() {
      return dose;
    }
    public void setDose(ResultadoDoseIdadeMaior dose) {
      this.dose = dose;
    }
  
}
