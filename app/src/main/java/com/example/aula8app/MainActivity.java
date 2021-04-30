package com.example.aula8app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private EditText etBuscarAno, etBuscarModelo;
    private TextView tvCount;
    private ListView lvCarros;
    List<Map<String, Object>> carros;
    String[] de = {"id", "modelo", "ano", "valor"};
    int[] para = {R.id.tvId, R.id.tvModelo, R.id.tvAno, R.id.tvValor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBuscarAno = findViewById(R.id.etBuscarAno);
        etBuscarModelo = findViewById(R.id.etBuscarModelo);
        lvCarros = findViewById(R.id.lvCarros);
        tvCount = findViewById(R.id.tvCount);

//        Intent it = getIntent();
//        int count = it.getIntExtra("count", 1);

        tvCount.setText(String.valueOf(LoginActivity.count));

        lvCarros.setClickable(true);
        lvCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> carro = carros.get(position);

                Intent it = new Intent(MainActivity.this, UpdateActivity.class);
                it.putExtra("id", (Integer) carro.get("id"));
                it.putExtra("modelo", (String) carro.get("modelo"));
                it.putExtra("ano", (Integer) carro.get("ano"));
                it.putExtra("valor", (String) carro.get("valor"));
                it.putExtra("raw_valor", (Double) carro.get("raw_valor"));
                startActivity(it);
            }
        });

        helper = new DatabaseHelper(this);
    }

    public void onClickBuscar(View view) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("carro");

        String ano = etBuscarAno.getText().toString();
        String modelo = etBuscarModelo.getText().toString();
//        String query = "SELECT * FROM carro";

        String where = null;
        if (!ano.isEmpty()) {

            where = "ano = " + ano;
//            query = "SELECT * FROM carro WHERE ano = " + busca;
        }
        if (!modelo.isEmpty()){

            if(where == null)
                where = "";
            else
                where += " AND ";

            where += "modelo LIKE '%" + modelo +  "%'";
        }

        String query = queryBuilder.buildQuery(null, where, null, null, null, null);
        carros = listarCarros(query);
        SimpleAdapter adapter = new SimpleAdapter(this, carros, R.layout.item_list, de, para);
        lvCarros.setAdapter(adapter);
    }

    private List<Map<String, Object>> listarCarros(String query) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        carros = new ArrayList<>();
        if(cursor.getCount() > 0){
            do {
                Map<String, Object> item = new HashMap<>();
                item.put("id", cursor.getInt(0));
                item.put("modelo", cursor.getString(1));
                item.put("ano", cursor.getInt(2));
                item.put("valor", String.format("R$ %.2f", cursor.getDouble(3)));
                item.put("raw_valor", cursor.getDouble(3));

                carros.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return carros;
    }

    public void onClickAdicionar(View view) {
        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }
}