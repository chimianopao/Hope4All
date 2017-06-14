package com.example.leona.hope4all;

import android.app.Activity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by portol on 29/05/2017.
 */

public class DoacaoDB {

    private DatabaseReference databaseReference;
    private static DoacaoDB instance;

    public static DoacaoDB getInstance(){
        if(instance == null){
            instance = new DoacaoDB();
        }
        return instance;
    }

    public DoacaoDB(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void insereDoacao(final Activity tela, Doacao doacao){
        final DatabaseReference newRef = databaseReference.child("doacoes").push();
        newRef.setValue(doacao, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                DoacaoController.getInstance().terminouDoacao(tela, newRef.getKey());
            }
        });
    }

}
