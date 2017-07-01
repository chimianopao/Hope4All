package com.example.leona.hope4all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;

public class TelaListaEntidadesAnalise extends AppCompatActivity {

    private ArrayList<Entidade> listaEntidades;
    private ListView listView;
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista_entidades);

        listaEntidades = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewAnalise);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntidadeController.getInstance().setEntidade(listaEntidades.get(position));
                startActivity(new Intent(TelaListaEntidadesAnalise.this, TelaAnaliseCadastro.class));
            }
        });
        EntidadeController.getInstance().buscaEntidades(this, listaEntidades);

        searchBar = (SearchView) findViewById(R.id.searchBarAdm);
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
                listView.setAdapter(new EntidadeAdapter(TelaListaEntidadesAnalise.this,res));
                return true;
            }
        });
    }

    public void populaLista(){
        listView.setAdapter(new EntidadeAdapter(this, listaEntidades));
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
