package com.example.leona.hope4all;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

}
