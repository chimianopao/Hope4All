package com.example.leona.hope4all;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TelaDoacaoActivity extends AppCompatActivity {

    private Spinner spinnerPontos;
    private EditText txtDescricao, txtHorario;
    private RadioButton radioColeta, radioInstituicao;
    private ArrayList<PontoEntrega> pontosEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_doacao);

        TextView texto = (TextView) findViewById(R.id.txtNome);
        texto.setText(EntidadeController.getInstance().getEntidade().getNome());
        texto = (TextView) findViewById(R.id.txtEndereco);
        texto.setText(EntidadeController.getInstance().getEntidade().getEndereco());
        texto = (TextView) findViewById(R.id.txtTelefone);
        texto.setText(Long.toString(EntidadeController.getInstance().getEntidade().getTelefone()));
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        radioColeta = (RadioButton) findViewById(R.id.radioColeta);
        radioInstituicao = (RadioButton) findViewById(R.id.radioInstituicao);
        txtHorario = (EditText) findViewById(R.id.txtHorario);
        TextView txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        txtCategoria.setText(EntidadeController.getInstance().getCategoria());
        Button buttConfirmar = (Button) findViewById(R.id.buttConfirmar);
        buttConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria, descricao, emailUsuario, emailInstituicao;
                TipoDoacao tipo;
                categoria = EntidadeController.getInstance().getCategoria();
                descricao = txtDescricao.getText().toString();
                tipo = radioColeta.isChecked() ? TipoDoacao.COLETA : TipoDoacao.ENTREGA;
                emailUsuario = UsuarioController.getInstance().getUsuario().getEmail();
                emailInstituicao = EntidadeController.getInstance().getEntidade().getEmail();
                String status = radioInstituicao.isChecked() ? "Entrega Pendente" : "Coleta Pendente";
                Doacao doacao = new Doacao(categoria, descricao, emailUsuario, emailInstituicao, tipo, status);
                if(radioInstituicao.isChecked()){
                    doacao.setHorarioEntrega(txtHorario.getText().toString());
                    doacao.setEnderecoEntrega(pontosEntrega.get(spinnerPontos.getSelectedItemPosition()).getEndereco());
                }
                DoacaoController.getInstance().realizarDoacao(TelaDoacaoActivity.this, doacao);
            }
        });
        spinnerPontos = (Spinner) findViewById(R.id.spinnerPontosEntrega);
        pontosEntrega = new ArrayList<>();
        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(radioInstituicao.isChecked()){
                    txtHorario.setHint("Digite o horário que irá entregar");
                    findViewById(R.id.card_horario).setVisibility(View.VISIBLE);
                    findViewById(R.id.card_spinner).setVisibility(View.VISIBLE);
                    if(pontosEntrega.isEmpty()){
                        pontosEntrega.add(EntidadeController.getInstance().getEntidade());
                        PontoController.getInstance().getPontos(TelaDoacaoActivity.this, pontosEntrega);
                    }
                }
                else{
                    txtHorario.setHint("Digite a partir de que horário pode receber");
                    findViewById(R.id.card_horario).setVisibility(View.VISIBLE);
                    findViewById(R.id.card_spinner).setVisibility(View.GONE);
                }
            }
        });
    }

    public void terminouBusca() {
        spinnerPontos.setAdapter(new PontosAdapter(this, pontosEntrega));
    }

    private class PontosAdapter extends ArrayAdapter<PontoEntrega> {
        private ArrayList<PontoEntrega> lista;
        private Context contexto;

        public PontosAdapter(Context contexto, ArrayList<PontoEntrega> pontos){
            super(contexto, android.R.layout.simple_spinner_dropdown_item, pontos);
            lista = pontos;
            this.contexto = contexto;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            PontoEntrega ponto = lista.get(position);

            if(convertView == null){
                convertView = LayoutInflater.from(contexto).inflate(R.layout.item_lista, parent, false);
            }

            TextView txtNome = (TextView) convertView.findViewById(R.id.txtNome);
            TextView txtEndereco = (TextView) convertView.findViewById(R.id.txtEndereco);
            txtNome.setText(ponto.getNome());
            txtEndereco.setText(ponto.getEndereco());

            return convertView;
        }
    }
}
