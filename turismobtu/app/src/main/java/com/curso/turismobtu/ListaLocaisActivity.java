package com.curso.turismobtu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaLocaisActivity extends AppCompatActivity {

    public static Intent intent(Context c, String category) {
        Intent i = new Intent(c, ListaLocaisActivity.class);
        i.putExtra("category", category);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        String category = getIntent().getStringExtra("category");
        ((TextView) findViewById(R.id.tv_title)).setText(category);

        List<PontoTuristico> places = BaseDeDados.byCategory(category);

        RecyclerView rv = findViewById(R.id.rv_places);
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(new ListaLocaisAdapter(places, new ListaLocaisAdapter.OnClick() {
            @Override
            public void onViewMore(PontoTuristico p) {
                startActivity(DetalheLocalActivity.intent(ListaLocaisActivity.this, p.id));
            }

            @Override
            public void onViewOnMap(PontoTuristico p) {
                FerramentasApp.toast(ListaLocaisActivity.this, "Ver no mapa: " + p.name);
            }
        }));

        // Filtros básicos mock
        Spinner sp = findViewById(R.id.sp_order);
        findViewById(R.id.btn_apply_filters).setOnClickListener(v ->
                FerramentasApp.toast(this, "Ordenar por: " + sp.getSelectedItem())
        );
    }
}
