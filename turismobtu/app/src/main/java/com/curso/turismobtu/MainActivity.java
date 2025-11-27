package com.curso.turismobtu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Fragmentos da bottom bar
    private final Fragment home = new DescobrirFragment();
    private final Fragment map = new MapaTuristicoFragment();
    private final Fragment fav = new LocaisFavoritos();
    private final Fragment profile = new MinhaConta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, home)
                    .commit();
        }


        BottomNavigationView bar = findViewById(R.id.bottom_bar);
        bar.setOnItemSelectedListener(item -> {
            Fragment target;
            int id = item.getItemId();
            if (id == R.id.nav_home)       target = home;
            else if (id == R.id.nav_map)   target = map;
            else if (id == R.id.nav_fav)   target = fav;
            else                            target = profile;

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, target)
                    .commit();
            return true;
        });
    }
}
