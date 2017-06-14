package com.example.leona.hope4all;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.leona.hope4all.R.id.buttConfirmar;

public class TelaDoacaoActivity extends AppCompatActivity {

    private Spinner spinnerCategoria;
    private EditText txtDescricao;
    private RadioButton radioColeta, radioInstituicao;

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
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        radioColeta = (RadioButton) findViewById(R.id.radioColeta);
        radioInstituicao = (RadioButton) findViewById(R.id.radioInstituicao);
        Button buttConfirmar = (Button) findViewById(R.id.buttConfirmar);
        buttConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria, descricao, emailUsuario, emailInstituicao;
                TipoDoacao tipo;
                categoria = (String) spinnerCategoria.getSelectedItem();
                descricao = txtDescricao.getText().toString();
                tipo = radioColeta.isChecked() ? TipoDoacao.COLETA : TipoDoacao.INSTITUICAO;
                emailUsuario = UsuarioController.getInstance().getUsuario().getEmail();
                emailInstituicao = EntidadeController.getInstance().getEntidade().getEmail();
                Doacao doacao = new Doacao(categoria, descricao, emailUsuario, emailInstituicao, tipo);
                DoacaoController.getInstance().realizarDoacao(TelaDoacaoActivity.this, doacao);
            }
        });
    }
}
