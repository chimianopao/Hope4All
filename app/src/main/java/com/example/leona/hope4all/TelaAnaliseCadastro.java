package com.example.leona.hope4all;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TelaAnaliseCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_analise_cadastro);
        final Entidade entidade = EntidadeController.getInstance().getEntidade();
        ((TextView)findViewById(R.id.txtNome)).setText(entidade.getNome());
        ((TextView)findViewById(R.id.txtArea)).setText(entidade.getArea());
        ((TextView)findViewById(R.id.txtEndereco)).setText(entidade.getEndereco());
        ((TextView)findViewById(R.id.txtTelefone)).setText(String.valueOf(entidade.getTelefone()));
        findViewById(R.id.buttAprovar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntidadeController.getInstance().aprovarEntidade(TelaAnaliseCadastro.this, entidade);
            }
        });
        findViewById(R.id.buttReprovar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaAnaliseCadastro.this.finish();
            }
        });
    }
}
