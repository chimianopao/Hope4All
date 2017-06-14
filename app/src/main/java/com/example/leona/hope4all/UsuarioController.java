package com.example.leona.hope4all;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by leona on 20/05/2017.
 */

public class UsuarioController {
    private static UsuarioController instance;
    private static Usuario usuario;

    public static UsuarioController getInstance(){
        if(instance == null)
            instance = new UsuarioController();
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void terminouLogin(Usuario user, Activity tela){
        usuario = user;
        Dialogs.tiraDialogCarregando();
        if(tela instanceof ComplementarLoginActivity)
            tela.finish();
        tela.startActivity(new Intent(tela, TelaPrincipalActivity.class));
    }

    public void fazerLogin(String email, Activity tela) {
        UsuarioDB.getInstance().verificaUsuario(email, tela);
    }

    public void pedirTelefone(Activity tela) {
        ((LoginActivity)tela).chamaTelaTelefone();
    }
}
