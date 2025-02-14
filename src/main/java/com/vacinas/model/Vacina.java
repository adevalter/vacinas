package com.vacinas.model;

import com.vacinas.enums.PublicoAlvo;

public class Vacina {
    private int id;
    private String vacina;
    private String descricao;
    private String limite_aplicacao;
    private PublicoAlvo publicoAlvo;

    public Vacina(int id, String vacina, String descricao, String limite_aplicacao, PublicoAlvo publicoAlvo) {
        this.id = id;
        this.vacina = vacina;
        this.descricao = descricao;
        this.limite_aplicacao = limite_aplicacao;
        this.publicoAlvo = publicoAlvo;
    }

    public Vacina(String vacina, String descricao, String limite_aplicacao, PublicoAlvo publicoAlvo) {
        this.vacina = vacina;
        this.descricao = descricao;
        this.limite_aplicacao = limite_aplicacao;
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

    public String getLimite_aplicacao() {
        return limite_aplicacao;
    }

    public void setLimite_aplicacao(String limite_aplicacao) {
        this.limite_aplicacao = limite_aplicacao;
    }

    public PublicoAlvo getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(PublicoAlvo publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
}
