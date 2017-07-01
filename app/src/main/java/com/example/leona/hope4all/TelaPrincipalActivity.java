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
import android.widget.CheckBox;
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
    private ArrayList<CheckBox> listaCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ORIGEM == ORIGEM_USUARIO) {
            setContentView(R.layout.activity_tela_principal);
            inicializaTabHostUsuario();
            inicializaListasUsuario();
        }
        else if(ORIGEM == ORIGEM_ADM) {
            setContentView(R.layout.layout_principal_adm);
            inicializaComponentes();
        }
        else {
            setContentView(R.layout.activity_tela_principal);
            inicializaTabHostEntidade();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void inicializaComponentes() {
        findViewById(R.id.buttAnalise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TelaPrincipalActivity.this, TelaListaEntidadesAnalise.class));
            }
        });

        findViewById(R.id.buttCadastroPonto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TelaPrincipalActivity.this, CadastroPontoColeta.class));
            }
        });
    }

    private void inicializaTabHostEntidade() {

    }

    private void inicializaListasUsuario() {
        listaEntidades = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntidadeController.getInstance().setEntidade(listaEntidades.get(position));
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

        listViewRanking = (ListView) findViewById(R.id.listView2);
        listaDoadores = new ArrayList<>();

        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaDoadores = new ArrayList<>();
                RankingController.getInstance().carregaRanking(TelaPrincipalActivity.this, listaDoadores);
            }
        });
    }

    private void inicializaTabHostUsuario() {
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec spec3 = host.newTabSpec("Ranking");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Ranking");
        host.addTab(spec3);

        TabHost.TabSpec spec = host.newTabSpec( "Entidades");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Entidades");
        host.addTab(spec);

        TabHost.TabSpec spec2 = host.newTabSpec("Mapa");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Mapa");
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
