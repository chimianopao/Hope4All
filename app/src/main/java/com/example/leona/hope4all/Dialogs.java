package com.example.leona.hope4all;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * Created by leona on 20/05/2017.
 */

public class Dialogs {
    private static ProgressDialog loadingDialog;

    public static void dialogCarregando(Activity tela){
        loadingDialog = ProgressDialog.show(tela, "Aguarde", "Carregando");
    }

    public static void tiraDialogCarregando(){
        loadingDialog.dismiss();
    }

    public static void dialogErro(Activity tela, String texto) {
        new AlertDialog.Builder(tela)
        .setTitle("Erro")
        .setMessage(texto)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
        .show();
    }
}
