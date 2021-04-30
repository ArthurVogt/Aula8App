package com.example.aula8app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etUsuario, etSenha;
    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);

    }

    public void onClickLogin(View view) {
        String usuario = etUsuario.getText().toString();
        String senha = etSenha.getText().toString();

        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);

        boolean existe = settings.contains(usuario+"-"+senha);

        if (existe) {
            count++;
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
//            it.putExtra("count", count);

            startActivity(it);
        } else {
            startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
        }
    }
}