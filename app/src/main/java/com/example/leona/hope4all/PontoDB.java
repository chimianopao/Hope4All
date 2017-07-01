package com.example.leona.hope4all;

import android.app.Activity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by leona on 30/06/2017.
 */

public class PontoDB {

    private static PontoDB instance;
    private DatabaseReference databaseReference;

    public static PontoDB getInstance(){
        if(instance == null)
            instance = new PontoDB();
        return instance;
    }

    public PontoDB(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void inserePonto(final Activity tela, final PontoColeta ponto){
        DatabaseReference newRef = databaseReference.child("pontoscoleta").push();
        newRef.setValue(ponto, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                PontoController.getInstance().terminouInserir(tela, ponto);
            }
        });
    }
}
