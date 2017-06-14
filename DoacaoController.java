package com.example.leona.hope4all;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by portol on 29/05/2017.
 */

public class DoacaoController {

    private static DoacaoController instance;

    public DoacaoController(){}

    public static DoacaoController getInstance(){
        if(instance == null)
            instance = new DoacaoController();
        return instance;
    }

    public void realizarDoacao(Activity tela, Doacao doacao){
        Dialogs.dialogCarregando(tela);
        DoacaoDB.getInstance().insereDoacao(tela, doacao);
    }

    public void terminouDoacao(final Activity tela, String key) {
        Dialogs.tiraDialogCarregando();
        new AlertDialog.Builder(tela)
                .setTitle("Aviso")
                .setMessage("Doação agendada! \nIdentificador: " + key)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tela.finish();
                    }
                })
                .show();
    }
}
