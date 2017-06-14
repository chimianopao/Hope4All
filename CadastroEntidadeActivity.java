package com.example.leona.hope4all;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import static com.example.leona.hope4all.R.id.buttConfirmar;

public class CadastroEntidadeActivity extends AppCompatActivity {

    private EditText editNome, editEndereco, editLatitude, editLongitude, editEmail, editSenha, editArea;
    private EditText editTelefone;

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
        editTelefone = (EditText) findViewById(R.id.txtTelefone);
        editArea = (EditText) findViewById(R.id.editArea);
        Button buttConfirmar = (Button) findViewById(R.id.buttConfirmar);
        buttConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editSenha.getText().length() < 6){
                    Dialogs.dialogErro(CadastroEntidadeActivity.this, "Sua senha deve ter pelo menos 6 caracteres!");
                    return;
                }
                String nome, endereco, email, senha, area;
                long telefone;
                nome = editNome.getText().toString();
                endereco = editEndereco.getText().toString();
                email = editEmail.getText().toString();
                senha = editSenha.getText().toString();
                area = editArea.getText().toString();
                telefone = Long.parseLong(editTelefone.getText().toString());
                float latitude, longitude;
                latitude = Float.parseFloat(editLatitude.getText().toString());
                longitude = Float.parseFloat(editLongitude.getText().toString());
                Entidade entidade = new Entidade(nome, endereco, email, senha, telefone, area, latitude, longitude);
                EntidadeDB.getInstance().insereEntidade(entidade, CadastroEntidadeActivity.this);
            }
        });
    }
}
