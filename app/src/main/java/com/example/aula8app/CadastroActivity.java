package com.example.aula8app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CadastroActivity extends AppCompatActivity {

    TextInputEditText etCadastroUsuario, etCadastroSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etCadastroUsuario = findViewById(R.id.etCadastroUsuario);
        etCadastroSenha = findViewById(R.id.etCadastroSenha);
    }

    public void onClickSalvar(View view) {
        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
        Editor editor = settings.edit();

        editor.putString(etCadastroUsuario.getText().toString()+"-"+etCadastroSenha.getText().toString(), "1");

        editor.apply();
        editor.commit();

        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

        onBackPressed();
    }
}