package com.example.leona.hope4all;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by leona on 29/06/2017.
 */

public class Administrador {

    private String email, nome;

    public Administrador() {
    }

    public Administrador(DataSnapshot dataSnapshot) {
        nome = (String) dataSnapshot.child("nome").getValue();
        email = (String) dataSnapshot.child("email").getValue();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
