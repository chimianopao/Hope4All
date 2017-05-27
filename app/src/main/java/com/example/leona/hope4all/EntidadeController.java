package com.example.leona.hope4all;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by leona on 27/05/2017.
 */

public class EntidadeController {

    private static EntidadeController instance;
    private static Entidade entidade;
    private FirebaseAuth firebaseAuth;

    public EntidadeController(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static EntidadeController getInstance(){
        if(instance == null)
            instance = new EntidadeController();
        return instance;
    }

    public void terminouCadastro(Activity tela){
        Dialogs.tiraDialogCarregando();
        if(tela instanceof CadastroEntidadeActivity)
            tela.finish();
    }

    public void fazerLogin(final Activity tela, String email, String senha) {
        Dialogs.dialogCarregando(tela);
        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(tela, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Dialogs.tiraDialogCarregando();
                    Dialogs.dialogErro(tela, "E-mail ou senha incorretos!");
                    return;
                }
                Dialogs.tiraDialogCarregando();
                tela.startActivity(new Intent(tela, TelaPrincipalActivity.class));
            }
        });
    }
}
