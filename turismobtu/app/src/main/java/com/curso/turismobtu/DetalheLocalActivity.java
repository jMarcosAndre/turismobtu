package com.curso.turismobtu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class DetalheLocalActivity extends AppCompatActivity {

    public static Intent intent(Context c, String placeId) {
        Intent i = new Intent(c, DetalheLocalActivity.class);
        i.putExtra("placeId", placeId);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        String id = getIntent().getStringExtra("placeId");
        PontoTuristico p = BaseDeDados.PLACES.stream().filter(x -> x.id.equals(id)).findFirst().orElse(null);
        if (p == null) { finish(); return; }

        ImageView img = findViewById(R.id.img);
        Glide.with(this).load(p.imageUrl).centerCrop().into(img);

        ((TextView) findViewById(R.id.title)).setText(p.name);
        ((TextView) findViewById(R.id.sub)).setText("★ " + String.format("%.1f", p.rating) + " • " + p.district);


        ((TextView) findViewById(R.id.tv_about))
                .setText("Um local imperdível para quem visita a cidade. Estrutura completa e experiências para todas as idades.");
        ((TextView) findViewById(R.id.tv_address))
                .setText("Bairro " + p.district + " – Próximo a pontos de ônibus e estacionamentos.");
        ((TextView) findViewById(R.id.tv_schedule))
                .setText("Seg–Sex: 09:00–18:00 • Sáb/Dom: 10:00–17:00");

        //  Botões
        findViewById(R.id.btn_map).setOnClickListener(v ->
                FerramentasApp.toast(this, "Abrir rotas (simulado) para: " + p.name)
        );

        MaterialButton btnAr = findViewById(R.id.btn_ar);
        btnAr.setOnClickListener(v ->
                startActivity(ExperienciaARA.intent(this, p.name))
        );

        // Favoritar / Remover
        MaterialButton btnFav = findViewById(R.id.btn_fav);
        boolean isFav = GerenciadorEstado.get().isFavorite(p.id);
        updateFavButton(btnFav, isFav);
        btnFav.setOnClickListener(v -> {
            GerenciadorEstado.get().toggleFavorite(p.id);
            boolean nowFav = GerenciadorEstado.get().isFavorite(p.id);
            updateFavButton(btnFav, nowFav);
            FerramentasApp.toast(this, nowFav ? "Adicionado aos favoritos" : "Removido dos favoritos");
        });
    }

    private void updateFavButton(MaterialButton b, boolean isFav) {
        b.setText(isFav ? "Remover dos favoritos" : "Salvar nos favoritos");
    }
}
