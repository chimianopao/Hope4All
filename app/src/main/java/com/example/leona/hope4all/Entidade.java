package com.example.leona.hope4all;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by leona on 27/05/2017.
 */

public class Entidade {

    private String nome; private String endereco; private String email; private String senha; private String area; private long telefone;
    private double longitude, latitude;
    private boolean aprovada = false;

    public Entidade(String nome, String endereco, String email, String senha, long telefone, String area, float latitude, float longitude) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.latitude = latitude;
        this.longitude = longitude;
        this.area = area;
        this.telefone = telefone;
    }

    public Entidade(DataSnapshot snapshot){
        nome = (String) snapshot.child("nome").getValue();
        endereco = (String) snapshot.child("endereco").getValue();
        email = (String) snapshot.child("email").getValue();
        String latString = String.valueOf(snapshot.child("latitude").getValue());
        if(!latString.contains(".")){
            latString += ".0";
        }
        latitude = Double.parseDouble(latString);
        String longString = String.valueOf(snapshot.child("longitude").getValue());
        if(!longString.contains(".")){
            longString += ".0";
        }
        longitude = Double.parseDouble(longString);
        area = (String) snapshot.child("area").getValue();
        telefone = (long) snapshot.child("telefone").getValue();
    }

    public Entidade(){}

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public boolean isAprovada() {
        return aprovada;
    }

    public void setAprovada(boolean aprovada) {
        this.aprovada = aprovada;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
