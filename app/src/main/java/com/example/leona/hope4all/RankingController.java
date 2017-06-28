package com.example.leona.hope4all;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by leona on 27/06/2017.
 */

public class RankingController {
    private static RankingController instance;

    public RankingController(){}

    public static RankingController getInstance(){
        if(instance == null)
            instance = new RankingController();
        return instance;
    }

    public void carregaRanking(Activity tela, ArrayList<Usuario> listaRanking){
        Dialogs.dialogCarregando(tela);
        UsuarioDB.getInstance().getUsuarios(tela, listaRanking);
    }

    private ArrayList<Usuario> filtraLista(ArrayList<Usuario> lista){
        ArrayList<Usuario> res = new ArrayList<>();
        for(Usuario user : lista){
            if(user.getPontos() > 0)
                res.add(user);
        }
        return res;
    }

    public void terminouContagem(Activity tela, ArrayList<Usuario> listaRanking) {
        listaRanking = filtraLista(listaRanking);
        if(listaRanking.size() == 0) {
            Dialogs.tiraDialogCarregando();
            ((TelaPrincipalActivity) tela).exibeAviso();
            return;
        }
        Collections.sort(listaRanking, new Comparator<Usuario>() {
            @Override
            public int compare(Usuario usuario, Usuario t1) {
                if(usuario.getPontos() > t1.getPontos())
                    return -1;
                else if(usuario.getPontos() < t1.getPontos())
                    return 1;
                return 0;
            }
        });
        if(listaRanking.size() > 10)
            listaRanking = new ArrayList<>(listaRanking.subList(0, 10));
        Dialogs.tiraDialogCarregando();
        if(tela instanceof TelaPrincipalActivity)
            ((TelaPrincipalActivity)tela).populaListaRanking(listaRanking);
    }
}
