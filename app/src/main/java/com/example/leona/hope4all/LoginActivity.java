package com.example.leona.hope4all;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private String nome, email, aniversario, genero;
    private EditText editEmail, editSenha;
    private Button buttEntrar, buttCadastro;
    private RadioGroup radioGroup;
    private RadioButton radioCidadao;
    private TextInputLayout layoutEmail, layoutSenha;
    private RadioButton radioAdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        instanciaComponentes();
        loginButton.setReadPermissions("public_profile", "email", "user_birthday");
        accessToken = AccessToken.getCurrentAccessToken();
        setEventos();
        setTitle("Login");
    }

    private void instanciaComponentes() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        buttCadastro = (Button) findViewById(R.id.buttCadastrar);
        buttEntrar = (Button) findViewById(R.id.buttEntrar);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        radioCidadao = (RadioButton) findViewById(R.id.radioCidadao);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        layoutEmail = (TextInputLayout) findViewById(R.id.layoutEmail);
        layoutSenha = (TextInputLayout) findViewById(R.id.layoutSenha);
        radioAdm = (RadioButton) findViewById(R.id.radioAdm);
    }

    private void setEventos() {
        if(accessToken != null){
            buscaInfoFacebook(accessToken);
        }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                buscaInfoFacebook(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {

            }
        });
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){
                    //Toast.makeText(LoginActivity.this, "token null", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(LoginActivity.this, "token existe", Toast.LENGTH_SHORT).show();
                }
            }
        };
        buttCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CadastroEntidadeActivity.class));
            }
        });
        buttEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioAdm.isChecked())
                    UsuarioController.getInstance().loginAdm(LoginActivity.this, editEmail.getText().toString(), editSenha.getText().toString());
                else
                    EntidadeController.getInstance().fazerLogin(LoginActivity.this, editEmail.getText().toString(), editSenha.getText().toString());
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(radioCidadao.isChecked()){
                    layoutEmail.setVisibility(View.GONE);
                    layoutSenha.setVisibility(View.GONE);
                    buttEntrar.setVisibility(View.GONE);
                    buttCadastro.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                    if(accessToken != null)
                        startActivity(new Intent(LoginActivity.this, TelaPrincipalActivity.class));
                }
                else{
                    layoutEmail.setVisibility(View.VISIBLE);
                    layoutSenha.setVisibility(View.VISIBLE);
                    buttEntrar.setVisibility(View.VISIBLE);
                    buttCadastro.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.GONE);
                }
            }
        });
    }

    private void buscaInfoFacebook(AccessToken token){
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            nome = object.getString("name");
                            email = object.getString("email");
                            aniversario = object.getString("birthday"); // 01/31/1980 format
                            genero = object.getString("gender");
                            Dialogs.dialogCarregando(LoginActivity.this);
                            UsuarioController.getInstance().fazerLogin(email, LoginActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    public void chamaTelaTelefone() {
        Usuario user = new Usuario(nome, aniversario, genero, email);
        startActivity(new Intent(this, ComplementarLoginActivity.class).putExtra("usuario", user));
    }
}
