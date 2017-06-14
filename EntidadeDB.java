package com.example.leona.hope4all;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by leona on 27/05/2017.
 */

public class EntidadeDB {

    private static EntidadeDB instance;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public static EntidadeDB getInstance(){
        if(instance == null){
            instance = new EntidadeDB();
        }
        return instance;
    }

    public EntidadeDB(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void insereEntidade(final Entidade entidade, final Activity tela){
        Dialogs.dialogCarregando(tela);
        mAuth.createUserWithEmailAndPassword(entidade.getEmail(), entidade.getSenha())
                .addOnCompleteListener(tela, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference newRef = databaseReference.child("entidades").push();
                            newRef.setValue(entidade, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    EntidadeController.getInstance().terminouCadastro(tela);
                                }
                            });
                        }
                        else{
                            Dialogs.tiraDialogCarregando();
                            Dialogs.dialogErro(tela, "Erro ao cadastrar entidade, tente novamente.");
                        }
                    }
                });
    }

    public void getEntidades(final Activity tela, final ArrayList<Entidade> listaEntidades) {
        databaseReference
                .child("entidades")
                .orderByChild("nome")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot == null){
                            Dialogs.tiraDialogCarregando();
                            return;
                        }

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            listaEntidades.add(new Entidade(child));
                        }
                        EntidadeController.getInstance().terminouBusca(tela);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
