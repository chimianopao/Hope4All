package com.example.leona.hope4all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;

public class TelaPrincipalActivity extends AppCompatActivity {

    public static final int ORIGEM_USUARIO = 1;
    public static final int ORIGEM_ADM = 2;
    public static final int ORIGEM_ENTIDADE = 3;
    public static int ORIGEM;
    private ArrayList<Entidade> listaEntidades;
    private ArrayList<Usuario> listaDoadores;
    private SearchView searchBar;
    private ListView listView, listViewRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicializaTabHost();
        inicializaListas();

        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaDoadores = new ArrayList<>();
                RankingController.getInstance().carregaRanking(TelaPrincipalActivity.this, listaDoadores);
            }
        });
    }

    private void inicializaListas() {
        listaEntidades = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntidadeController.getInstance().setEntidade(listaEntidades.get(position));
                if(ORIGEM == ORIGEM_ADM)
                    startActivity(new Intent(TelaPrincipalActivity.this, TelaAnaliseCadastro.class));
                else
                    startActivity(new Intent(TelaPrincipalActivity.this, TelaDoacaoActivity.class));
            }
        });
        EntidadeController.getInstance().buscaEntidades(this, listaEntidades);

        searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Entidade> res = new ArrayList<>();
                for(Entidade ent : listaEntidades){
                    String nomeSemAcento = Normalizer.normalize(ent.getNome().toUpperCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                    String enderecoSemAcento = Normalizer.normalize(ent.getEndereco().toUpperCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                    if(nomeSemAcento.contains(newText.toUpperCase()) || enderecoSemAcento.contains(newText.toUpperCase())){
                        res.add(ent);
                    }
                }
                listView.setAdapter(new EntidadeAdapter(TelaPrincipalActivity.this,res));
                return true;
            }
        });

        if(ORIGEM == ORIGEM_USUARIO) {
            listViewRanking = (ListView) findViewById(R.id.listView2);
            listaDoadores = new ArrayList<>();
        }
        else
            findViewById(R.id.floating).setVisibility(View.GONE);
    }

    private void inicializaTabHost() {
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        String tab1 = null;
        String tab2 = null;
        String tab3 = null;
        int id1 = 0;
        int id2 = 0;
        int id3 = 0;

        if(ORIGEM == ORIGEM_USUARIO){
            tab1 = "Ranking";
            tab2 = "Entidades";
            tab3 = "Mapa";
            id1 = R.id.tab3;
            id2 = R.id.tab1;
            id3 = R.id.tab2;
        }
        else if(ORIGEM == ORIGEM_ADM){
            tab1 = "Análise de Cadastros";
            tab2 = "Pontos de Coleta";
            tab3 = "Campanhas de Doação";
            id1 = R.id.tab1;
            id2 = R.id.tab2;
            id3 = R.id.tab3;
        }
        else{
            tab1 = "Ranking";
            tab2 = "Entidades";
            tab3 = "Mapa";
            id1 = R.id.tab3;
            id2 = R.id.tab1;
            id3 = R.id.tab2;
        }

        TabHost.TabSpec spec3 = host.newTabSpec(tab1);
        spec3.setContent(id1);
        spec3.setIndicator(tab1);
        host.addTab(spec3);

        TabHost.TabSpec spec = host.newTabSpec(tab2);
        spec.setContent(id2);
        spec.setIndicator(tab2);
        host.addTab(spec);

        TabHost.TabSpec spec2 = host.newTabSpec(tab3);
        spec2.setContent(id3);
        spec2.setIndicator(tab3);
        host.addTab(spec2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void populaLista(){
        listView.setAdapter(new EntidadeAdapter(this, listaEntidades));
        if(ORIGEM == ORIGEM_USUARIO)
            RankingController.getInstance().carregaRanking(this, listaDoadores);
    }

    public void populaListaRanking(ArrayList<Usuario> listaRanking) {
        findViewById(R.id.card_aviso).setVisibility(View.GONE);
        listViewRanking.setAdapter(new RankingAdapter(this, listaRanking));
    }

    public void exibeAviso() {
        findViewById(R.id.card_aviso).setVisibility(View.VISIBLE);
    }

    private class RankingAdapter extends ArrayAdapter<Usuario> {
        public RankingAdapter(Context contexto, ArrayList<Usuario> usuarios){
            super(contexto, 0, usuarios);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Usuario user = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            TextView txtNome = (TextView) convertView.findViewById(android.R.id.text1);
            TextView txtPontos = (TextView) convertView.findViewById(android.R.id.text2);
            txtNome.setText(user.getNome());
            txtPontos.setText("Pontos: " + user.getPontos());

            return convertView;
        }
    }

    private class EntidadeAdapter extends ArrayAdapter<Entidade> {
        public EntidadeAdapter(Context contexto, ArrayList<Entidade> entidades){
            super(contexto, 0, entidades);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Entidade entidade = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, parent, false);
            }

            TextView txtNome = (TextView) convertView.findViewById(R.id.txtNome);
            TextView txtEndereco = (TextView) convertView.findViewById(R.id.txtEndereco);
            txtNome.setText(entidade.getNome());
            txtEndereco.setText(entidade.getEndereco());

            return convertView;
        }
    }
}
