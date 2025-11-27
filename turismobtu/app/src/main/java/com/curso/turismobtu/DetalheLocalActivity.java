package com.curso.turismobtu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.DialogInterface;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

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

        PontoTuristico p = GerenciadorEstado.get().getPontoById(id);

        if (p == null) {
            FerramentasApp.toast(this, "Erro: Ponto turístico não encontrado ou não carregado.");
            finish();
            return;
        }

        ImageView img = findViewById(R.id.img);
        Glide.with(this).load(p.getImageUrl()).centerCrop().into(img);

        ((TextView) findViewById(R.id.title)).setText(p.getName());
        ((TextView) findViewById(R.id.sub)).setText("★ " + String.format("%.1f", p.getRating()) + " • " + p.getDistrict());


        ((TextView) findViewById(R.id.tv_about))
                .setText(p.getDescription());

        ((TextView) findViewById(R.id.tv_address))
                .setText("Endereço: " + p.getAddress());

        ((TextView) findViewById(R.id.tv_schedule))
                .setText("Horário: " + p.getSchedule());


        findViewById(R.id.btn_map).setOnClickListener(v -> {

            final CharSequence[] options = {"Ver no Mapa do App", "Navegar com WAZE/Maps"};

            new AlertDialog.Builder(this)
                    .setTitle("Opções de Navegação")
                    .setItems(options, (dialog, item) -> {
                        if (item == 0) {
                            Intent mapIntent = new Intent(this, MainActivity.class);
                            mapIntent.putExtra("NAVIGATE_TO_MAP", true);
                            mapIntent.putExtra("TARGET_LAT", p.getLat());
                            mapIntent.putExtra("TARGET_LNG", p.getLng());
                            mapIntent.putExtra("TARGET_NAME", p.getName());
                            startActivity(mapIntent);
                        } else if (item == 1) {
                            FerramentasApp.openWaze(this, p.getLat(), p.getLng());
                        }
                    })
                    .show();
        });

        MaterialButton btnFav = findViewById(R.id.btn_fav);

        boolean isFav = GerenciadorEstado.get().isFavorite(p.getId());
        updateFavButton(btnFav, isFav);
        btnFav.setOnClickListener(v -> {

            GerenciadorEstado.get().toggleFavorite(p.getId());
            boolean nowFav = GerenciadorEstado.get().isFavorite(p.getId());
            updateFavButton(btnFav, nowFav);
            FerramentasApp.toast(this, nowFav ? "Adicionado aos favoritos" : "Removido dos favoritos");
        });
    }

    private void updateFavButton(MaterialButton b, boolean isFav) {
        b.setText(isFav ? "Remover dos favoritos" : "Salvar nos favoritos");
    }
}