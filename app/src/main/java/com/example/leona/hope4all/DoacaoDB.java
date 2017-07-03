package com.example.leona.hope4all;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;

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

    public void insereDoacao(final Activity tela, final Doacao doacao){
        final DatabaseReference newRef = databaseReference.child("doacoes").push();
        newRef.setValue(doacao, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                DoacaoController.getInstance().terminouDoacao(tela, newRef.getKey());
            }
        });
    }

    public void calculaPontos(final Activity tela, final ArrayList<Usuario> listaRanking) {
        final boolean controle[] = new boolean[listaRanking.size()];
        for(final Usuario user : listaRanking) {
            databaseReference.child("doacoes")
                    .orderByChild("emailUsuario")
                    .equalTo(user.getEmail())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot == null) {
                                Dialogs.tiraDialogCarregando();
                                return;
                            }

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String dataString = (String) child.child("data").getValue();
                                String status = (String) child.child("status").getValue();
                                DateTime data = DateTime.parse(dataString, DateTimeFormat.forPattern("dd-MM-YYYY HH:mm"));
                                if(data.getMonthOfYear() == DateTime.now().getMonthOfYear() && status.equals("Completada com sucesso")){
                                    user.setPontos(user.getPontos() + 1);
                                }
                            }

                            for (int i = 0; i < controle.length; i++) {
                                if(!controle[i]) controle[i] = true;
                            }
                            boolean aux = true;
                            for (int i = 0; i < controle.length; i++) {
                                aux = aux && controle[i];
                            }
                            if(aux){
                                RankingController.getInstance().terminouContagem(tela, listaRanking);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    public void confirmaDoacao(final Activity tela, final String identificador) {
        databaseReference
                .child("doacoes")
                .child(identificador)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            Dialogs.tiraDialogCarregando();
                            Dialogs.dialogErro(tela, "Este identificador não existe!");
                            return;
                        }

                        databaseReference
                                .child("doacoes")
                                .child(identificador)
                                .child("status")
                                .setValue("Completada com sucesso")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        DoacaoController.getInstance().terminouConfirmacao(tela);
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
