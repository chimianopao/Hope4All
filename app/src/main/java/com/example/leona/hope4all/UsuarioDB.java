package com.example.leona.hope4all;

import android.app.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by leona on 20/05/2017.
 */

public class UsuarioDB {

    private static UsuarioDB instance;
    private DatabaseReference databaseReference;

    public static UsuarioDB getInstance(){
        if(instance == null){
            instance = new UsuarioDB();
        }
        return instance;
    }

    public UsuarioDB(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void verificaUsuario(final String email, final Activity tela){
        databaseReference
                .child("usuarios")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            Dialogs.tiraDialogCarregando();
                            UsuarioController.getInstance().pedirTelefone(tela);
                            return;
                        }
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            Usuario user = new Usuario(child);
                            UsuarioController.getInstance().terminouLogin(user, tela);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void insereUsuario(final Usuario user, final Activity tela) {
        DatabaseReference newRef = databaseReference.child("usuarios").push();
        newRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                UsuarioController.getInstance().terminouLogin(user, tela);
            }
        });
    }

    public void getUsuarios(final Activity tela, final ArrayList<Usuario> listaRanking){
        databaseReference
                .child("usuarios")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot == null){
                            Dialogs.tiraDialogCarregando();
                            return;
                        }

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Usuario user = new Usuario(child);
                            listaRanking.add(user);
                        }
                        DoacaoDB.getInstance().calculaPontos(tela, listaRanking);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void loginAdm(final Activity tela, String email, String senha) {
        databaseReference
                .child("administradores")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot == null){
                            Dialogs.dialogErro(tela, "Este e-mail não está cadastrado!");
                            return;
                        }

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Administrador adm = new Administrador(child);
                            UsuarioController.getInstance().terminouLoginAdm(tela, adm);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
