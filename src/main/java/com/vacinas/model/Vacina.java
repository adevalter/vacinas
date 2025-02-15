package com.vacinas.model;

import com.vacinas.enums.PublicoAlvo;

public class Vacina {
    private int id;
    private String vacina;
    private String descricao;
    private int  limiteAplicacao;
    private PublicoAlvo publicoAlvo;

    public Vacina(int id, String vacina, String descricao, int  limiteAplicacao, PublicoAlvo publicoAlvo) {
        this.id = id;
        this.vacina = vacina;
        this.descricao = descricao;
        this.limiteAplicacao = limiteAplicacao;
        this.publicoAlvo = publicoAlvo;
    }

    public Vacina(String vacina, String descricao, int  limiteAplicacao, PublicoAlvo publicoAlvo) {
        this.vacina = vacina;
        this.descricao = descricao;
        this.limiteAplicacao = limiteAplicacao;
        this.publicoAlvo = publicoAlvo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVacina() {
        return vacina;
    }

    public void setVacina(String vacina) {
        this.vacina = vacina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
}
