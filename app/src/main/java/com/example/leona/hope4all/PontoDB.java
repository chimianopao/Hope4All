package com.example.leona.hope4all;

import android.app.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    public void getPontos(final Activity tela, final ArrayList<PontoEntrega> lista) {
        databaseReference
                .child("pontoscoleta")
                .orderByChild("nome")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot == null){
                            Dialogs.tiraDialogCarregando();
                            return;
                        }

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            lista.add(new PontoColeta(child));
                        }
                        PontoController.getInstance().terminouBusca(tela);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
