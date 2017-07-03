package com.example.leona.hope4all;

/**
 * Created by leona on 02/07/2017.
 */

public class PontoEntrega {

    private String nome,endereco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String toString(){
        return nome + "\n" + endereco;
    }
}
