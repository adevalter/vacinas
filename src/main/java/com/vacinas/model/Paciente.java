package com.vacinas.model;

import java.time.LocalDate;
import com.vacinas.enums.Sexo;

public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private Sexo sexo;
    private LocalDate data_nascimento;
    private Integer responsavel;

    public Paciente() {}

    public Paciente(int id, String nome, String cpf, Sexo sexo, LocalDate data_nascimento, Integer responsavel) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.data_nascimento = data_nascimento;
        this.responsavel = responsavel;
    }

    public Paciente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Integer responsavel) {
        this.responsavel = responsavel;
    }

    public Paciente(int id, String nome, String cpf, Sexo sexo, LocalDate data_nascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.data_nascimento = data_nascimento;
    }

    public Paciente(String nome, String cpf, Sexo sexo, LocalDate data_nascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.data_nascimento = data_nascimento;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}
