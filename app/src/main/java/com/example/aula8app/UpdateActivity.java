package com.example.aula8app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private String id;
    private DatabaseHelper helper;
    private EditText etModelo, etAno, etValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Bundle extras = getIntent().getExtras();
        id = String.valueOf(extras.getInt("id"));

        etModelo = findViewById(R.id.etModelo2);
        etAno = findViewById(R.id.etAno2);
        etValor = findViewById(R.id.etValor2);

        etModelo.setText(extras.getString("modelo"));
        etAno.setText(String.valueOf(extras.getInt("ano")));
        etValor.setText(String.valueOf(extras.getDouble("raw_valor")));

        helper = new DatabaseHelper(this);

    }

    public void onClickAtualizar(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("modelo", etModelo.getText().toString());
        values.put("ano", Integer.parseInt(etAno.getText().toString()));
        values.put("valor", (etValor.getText().toString()));

        String[] where = {id};
        long resultado = db.update("carro", values, "id = ?", where);
        if (resultado != -1) {
            Toast.makeText(this, "Registro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Erro ao atualizar registro!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickExcluir(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] where = {id};

        long resultado = db.delete("carro", "id = ?", where);
        if (resultado != -1) {
            Toast.makeText(this, "Registro excluído com sucesso!", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Erro ao excluir registro!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}