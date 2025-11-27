package com.curso.turismobtu;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class InicializacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        findViewById(R.id.btn_login).setOnClickListener(v -> {

            GerenciadorEstado.get().setLoggedIn(true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        findViewById(R.id.btn_guest).setOnClickListener(v -> {

            GerenciadorEstado.get().setLoggedIn(false);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}