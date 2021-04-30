package com.example.aula8app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private EditText etModelo, etAno, etValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etModelo = findViewById(R.id.etModelo);
        etAno = findViewById(R.id.etAno);
        etValor = findViewById(R.id.etValor);

        helper = new DatabaseHelper(this);
    }

    public void onClickSalvar(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("modelo", etModelo.getText().toString());
        values.put("ano", Integer.parseInt(etAno.getText().toString()));
        values.put("valor", Double.parseDouble(etValor.getText().toString()));

        long resultado = db.insert("carro", null, values);
        if (resultado != -1) {
            Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Erro ao salvar registro!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}