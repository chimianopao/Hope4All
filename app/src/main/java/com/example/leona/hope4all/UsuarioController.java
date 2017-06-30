package com.example.leona.hope4all;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by leona on 20/05/2017.
 */

public class UsuarioController {
    private static UsuarioController instance;
    private static Usuario usuario;
    private Administrador administrador;

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
        TelaPrincipalActivity.ORIGEM = TelaPrincipalActivity.ORIGEM_USUARIO;
        tela.startActivity(new Intent(tela, TelaPrincipalActivity.class));
    }

    public void fazerLogin(String email, Activity tela) {
        UsuarioDB.getInstance().verificaUsuario(email, tela);
    }

    public void pedirTelefone(Activity tela) {
        ((LoginActivity)tela).chamaTelaTelefone();
    }

    public void loginAdm(Activity tela, String email, String senha) {
        Dialogs.dialogCarregando(tela);
        UsuarioDB.getInstance().loginAdm(tela, email, senha);
    }

    public void terminouLoginAdm(Activity tela, Administrador adm) {
        Dialogs.tiraDialogCarregando();
        administrador = adm;
        TelaPrincipalActivity.ORIGEM = TelaPrincipalActivity.ORIGEM_ADM;
        tela.startActivity(new Intent(tela, TelaPrincipalActivity.class));
    }
}
