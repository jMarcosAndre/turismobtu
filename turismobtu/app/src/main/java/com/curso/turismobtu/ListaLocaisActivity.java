package com.curso.turismobtu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;




public class ListaLocaisActivity extends AppCompatActivity {


    public static Intent intent(Context c, String category, ArrayList<PontoTuristico> allPoints) {
        Intent i = new Intent(c, ListaLocaisActivity.class);
        i.putExtra("category", category);
        i.putExtra("allPoints", allPoints);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        String category = getIntent().getStringExtra("category");
        ((TextView) findViewById(R.id.tv_title)).setText(category);


        ArrayList<PontoTuristico> allPoints = (ArrayList<PontoTuristico>) getIntent().getSerializableExtra("allPoints");

        if (allPoints == null) {
            allPoints = new ArrayList<>();
            Toast.makeText(this, "Erro: Não foi possível carregar pontos turísticos.", Toast.LENGTH_LONG).show();
        }

        FirebaseDataManager dataManager = new FirebaseDataManager();
        List<PontoTuristico> places = dataManager.filterByCategory(allPoints, category);

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


        // Filtros
        Spinner sp = findViewById(R.id.sp_order);
        findViewById(R.id.btn_apply_filters).setOnClickListener(v ->
                FerramentasApp.toast(this, "Ordenar por: " + sp.getSelectedItem())
        );
    }
}