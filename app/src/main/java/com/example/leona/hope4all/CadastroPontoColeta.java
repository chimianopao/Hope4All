package com.example.leona.hope4all;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CadastroPontoColeta extends AppCompatActivity {

    private ArrayList<CheckBox> listaCheck;
    private EditText txtNome, txtEndereco, txtHorarioDe, txtHorarioAte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ponto_coleta);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.layoutChecks);
        listaCheck = new ArrayList<>();
        CheckBox check = new CheckBox(this);
        check.setText("Alimentos");
        layout.addView(check);
        listaCheck.add(check);
        check = new CheckBox(this);
        check.setText("Agasalhos");
        layout.addView(check);
        listaCheck.add(check);
        check = new CheckBox(this);
        check.setText("Livros");
        layout.addView(check);
        listaCheck.add(check);
        check = new CheckBox(this);
        check.setText("Brinquedos");
        layout.addView(check);
        listaCheck.add(check);
        check = new CheckBox(this);
        check.setText("Remédios");
        layout.addView(check);
        listaCheck.add(check);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtHorarioAte = (EditText) findViewById(R.id.txtHorarioAte);
        txtHorarioDe = (EditText) findViewById(R.id.txtHorarioDe);

        findViewById(R.id.buttCadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String endereco, nome, horariode, horarioate;
                endereco = txtEndereco.getText().toString();
                nome = txtNome.getText().toString();
                horariode = txtHorarioDe.getText().toString();
                horarioate = txtHorarioAte.getText().toString();
                if(horariode.indexOf(':') < 0 || horarioate.indexOf(':') < 0){
                    Dialogs.dialogErro(CadastroPontoColeta.this, "Preencha com horários válidos!");
                    return;
                }
                ArrayList<String> itens = new ArrayList<>();
                for(int i = 0; i < layout.getChildCount(); i++){
                    CheckBox check = (CheckBox) layout.getChildAt(i);
                    if(check.isChecked())
                        itens.add(check.getText().toString());
                }
                PontoColeta ponto = new PontoColeta(nome, endereco, horariode, horarioate, itens);
                PontoController.getInstance().inserePonto(ponto, CadastroPontoColeta.this);
            }
        });
    }
}
