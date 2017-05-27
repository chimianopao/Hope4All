package com.example.leona.hope4all;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroEntidadeActivity extends AppCompatActivity {

    private EditText editNome, editEndereco, editLatitude, editLongitude, editEmail, editSenha;
    private Button buttConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_entidade);
        instanciaComponentes();
    }

    private void instanciaComponentes() {
        editNome = (EditText) findViewById(R.id.editNome);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editLatitude = (EditText) findViewById(R.id.editLatitude);
        editLongitude = (EditText) findViewById(R.id.editLongitude);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        buttConfirmar = (Button) findViewById(R.id.buttConfirmar);
        buttConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editSenha.getText().length() < 6){
                    Dialogs.dialogErro(CadastroEntidadeActivity.this, "Sua senha deve ter pelo menos 6 caracteres!");
                    return;
                }
                String nome, endereco, email, senha;
                nome = editNome.getText().toString();
                endereco = editEndereco.getText().toString();
                email = editEmail.getText().toString();
                senha = editSenha.getText().toString();
                float latitude, longitude;
                latitude = Float.parseFloat(editLatitude.getText().toString());
                longitude = Float.parseFloat(editLongitude.getText().toString());
                Entidade entidade = new Entidade(nome, endereco, email, senha, latitude, longitude);
                EntidadeDB.getInstance().insereEntidade(entidade, CadastroEntidadeActivity.this);
            }
        });
    }
}
