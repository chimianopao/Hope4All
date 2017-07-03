package com.example.leona.hope4all;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ConfirmarDoacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_doacao);
        final EditText txtId = (EditText) findViewById(R.id.txtId);
        findViewById(R.id.buttConfirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String identificador = txtId.getText().toString();
                DoacaoController.getInstance().confirmarDoacao(ConfirmarDoacaoActivity.this, identificador);
            }
        });
    }
}
