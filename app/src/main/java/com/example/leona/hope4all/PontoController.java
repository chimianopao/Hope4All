package com.example.leona.hope4all;

import android.app.Activity;

/**
 * Created by leona on 30/06/2017.
 */

public class PontoController {

    private static PontoController instance;

    public static PontoController getInstance(){
        if(instance == null)
            instance = new PontoController();
        return instance;
    }

    public void inserePonto(PontoColeta ponto, Activity tela){
        Dialogs.dialogCarregando(tela);
        PontoDB.getInstance().inserePonto(tela, ponto);
    }

    public void terminouInserir(Activity tela, PontoColeta ponto) {
        Dialogs.tiraDialogCarregando();
        tela.finish();
    }
}
