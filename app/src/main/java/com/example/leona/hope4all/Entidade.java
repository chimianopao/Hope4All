package com.example.leona.hope4all;

/**
 * Created by leona on 27/05/2017.
 */

public class Entidade {

    private String nome, endereco, email, senha;
    private float longitude, latitude;

    public Entidade(String nome, String endereco, String email, String senha, float latitude, float longitude) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Entidade(){}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
