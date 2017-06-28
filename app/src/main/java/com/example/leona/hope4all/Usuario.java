package com.example.leona.hope4all;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

/**
 * Created by leona on 20/05/2017.
 */

public class Usuario implements Serializable{

    private String nome;
    private String aniversario;
    private String genero;
    private String email;
    private String telefone;
    private int pontos;

    public Usuario(){}

    public Usuario(DataSnapshot dataSnapshot) {
        nome = (String) dataSnapshot.child("nome").getValue();
        aniversario = (String) dataSnapshot.child("aniversario").getValue();
        genero = (String) dataSnapshot.child("genero").getValue();
        if(genero.equals("male"))
            genero = "Masculino";
        else
            genero = "Feminino";
        email = (String) dataSnapshot.child("email").getValue();
        telefone = (String) dataSnapshot.child("telefone").getValue();
    }

    public Usuario(String nome, String aniversario, String genero, String email) {
        this.nome = nome;
        this.aniversario = aniversario;
        this.genero = genero;
        this.email = email;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAniversario() {
        return aniversario;
    }

    public void setAniversario(String aniversario) {
        this.aniversario = aniversario;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
