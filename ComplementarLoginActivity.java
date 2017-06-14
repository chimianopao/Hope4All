package com.example.leona.hope4all;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComplementarLoginActivity extends AppCompatActivity {

    private String telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complementar_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Usuario user = (Usuario) getIntent().getSerializableExtra("usuario");
        final EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        Button buttConfirmar = (Button) findViewById(R.id.buttonConfirmar);
        final Usuario finalUser = user;
        buttConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTelefone.getText().toString().trim().isEmpty() || txtTelefone.getText().toString().length() < 11){
                    Dialogs.dialogErro(ComplementarLoginActivity.this, "O telefone deve conter DDD e número!");
                    return;
                }
                Dialogs.dialogCarregando(ComplementarLoginActivity.this);
                telefone = txtTelefone.getText().toString();
                finalUser.setTelefone(telefone);
                UsuarioDB.getInstance().insereUsuario(finalUser, ComplementarLoginActivity.this);
            }
        });
    }

}
