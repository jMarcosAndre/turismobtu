package com.curso.turismobtu;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
// Firebase Auth Imports removidos

public class InicializacaoActivity extends AppCompatActivity {
    // FirebaseAuth mAuth removido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Lógica de verificação de login removida

        // Botões da tela (Login Fixo para Testes)
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            // CORRIGIDO: Usa o método setLoggedIn(true)
            GerenciadorEstado.get().setLoggedIn(true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        findViewById(R.id.btn_guest).setOnClickListener(v -> {
            // CORRIGIDO: Usa o método setLoggedIn(false)
            GerenciadorEstado.get().setLoggedIn(false);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}