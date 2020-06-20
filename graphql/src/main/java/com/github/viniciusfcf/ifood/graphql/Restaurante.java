package com.github.viniciusfcf.ifood.graphql;

public class Restaurante {

    private String nome;
    private String dono;

    public String getDono() {
        return dono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }
}
