package com.example.leona.hope4all;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton button;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private String nome, email, aniversario, genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        button = (LoginButton) findViewById(R.id.login_button);
        button.setReadPermissions("public_profile", "email", "user_birthday");
        accessToken = AccessToken.getCurrentAccessToken();
        setEventos();
    }

    private void setEventos() {
        if(accessToken != null){
            buscaInfoFacebook(accessToken);
        }
        button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                    //ainda nao logou
                    Toast.makeText(LoginActivity.this, "token null", Toast.LENGTH_SHORT).show();
                }
                else{
                    // ja logou
                    Toast.makeText(LoginActivity.this, "token existe", Toast.LENGTH_SHORT).show();
                }
            }
        };
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
