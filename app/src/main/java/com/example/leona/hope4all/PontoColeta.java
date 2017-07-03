package com.example.leona.hope4all;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by leona on 30/06/2017.
 */

public class PontoColeta extends PontoEntrega{

    private String nome, endereco, horarioDe, horarioAte;
    private ArrayList<String> itensAceitos;

    public PontoColeta() {
    }

    public PontoColeta(String nome, String endereco, String horarioDe, String horarioAte, ArrayList<String> itensAceitos) {
        this.nome = nome;
        this.endereco = endereco;
        this.horarioDe = horarioDe;
        this.horarioAte = horarioAte;
        this.itensAceitos = itensAceitos;
    }

    public PontoColeta(DataSnapshot dataSnapshot){
        nome = (String) dataSnapshot.child("nome").getValue();
        endereco = (String) dataSnapshot.child("endereco").getValue();
        itensAceitos = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child("itensAceitos").getChildren()){
            itensAceitos.add((String)child.getValue());
        }
        horarioAte = (String) dataSnapshot.child("horarioAte").getValue();
        horarioDe = (String) dataSnapshot.child("horarioDe").getValue();
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

    public String getHorarioDe() {
        return horarioDe;
    }

    public void setHorarioDe(String horarioDe) {
        this.horarioDe = horarioDe;
    }

    public String getHorarioAte() {
        return horarioAte;
    }

    public void setHorarioAte(String horarioAte) {
        this.horarioAte = horarioAte;
    }

    public ArrayList<String> getItensAceitos() {
        return itensAceitos;
    }

    public void setItensAceitos(ArrayList<String> itensAceitos) {
        this.itensAceitos = itensAceitos;
    }

    public String toString(){
        return super.toString();
    }
}
