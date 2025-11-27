package com.curso.turismobtu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.Arrays;
import java.util.List;

public class MinhaConta extends Fragment {

    private TextView tvName, tvSub;
    private MaterialButton btnLoginLogout;
    private ChipGroup chipsPrefs;

    private final List<String> categories = Arrays.asList(
            "Museus","Esportes","Cachoeiras","Restaurantes",
            "Parques","Eventos","Hospedagem","Compras"
    );

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = v.findViewById(R.id.tv_name);
        tvSub  = v.findViewById(R.id.tv_sub);
        btnLoginLogout = v.findViewById(R.id.btn_login_logout);
        chipsPrefs = v.findViewById(R.id.chips_prefs);

        // Render inicial
        renderUser();

        // Alternar login/logout (mock igual ao Flutter)
        btnLoginLogout.setOnClickListener(view -> {
            boolean now = !GerenciadorEstado.get().isLoggedIn();
            GerenciadorEstado.get().setLoggedIn(now);
            renderUser();
        });

        // Monta chips de preferências
        buildPreferenceChips();

        return v;
    }

    private void renderUser() {
        boolean logged = GerenciadorEstado.get().isLoggedIn();
        if (logged) {
            tvName.setText("Raldnei Usuária");
            tvSub.setText("PT-BR • Preferências de categorias");
            btnLoginLogout.setText("Sair");
        } else {
            tvName.setText("Convidado");
            tvSub.setText("Entrar / Criar conta");
            btnLoginLogout.setText("Entrar");
        }
    }

    private void buildPreferenceChips() {
        chipsPrefs.removeAllViews();

        for (String c : categories) {
            Chip chip = new Chip(requireContext(), null, com.google.android.material.R.style.Widget_Material3_Chip_Filter);
            chip.setText(c);
            chip.setCheckable(true);


            if ("Restaurantes".equals(c) || "Parques".equals(c)) {
                chip.setChecked(true);
            }

            chip.setOnClickListener(v -> {

            });

            chipsPrefs.addView(chip);
        }
    }
}
