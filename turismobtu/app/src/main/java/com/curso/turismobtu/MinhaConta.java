package com.curso.turismobtu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull; // Adicionado para a anotação do onCreateView
import androidx.fragment.app.Fragment;

// IMPORT FALTANDO ADICIONADO AQUI
import android.os.Bundle;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = v.findViewById(R.id.tv_name);
        tvSub  = v.findViewById(R.id.tv_sub);
        btnLoginLogout = v.findViewById(R.id.btn_login_logout);
        chipsPrefs = v.findViewById(R.id.chips_prefs);

        renderUser();

        // Lógica de Sair/Entrar
        btnLoginLogout.setOnClickListener(view -> {
            boolean logged = GerenciadorEstado.get().isLoggedIn();
            if (logged) {
                // LÓGICA DE LOGOUT CORRIGIDA: Seta o estado de deslogado
                GerenciadorEstado.get().setLoggedIn(false);

                requireActivity().startActivity(new android.content.Intent(requireActivity(), InicializacaoActivity.class));
                requireActivity().finish();
            } else {
                // Redireciona para a tela de Login (InicializacaoActivity)
                requireActivity().startActivity(new android.content.Intent(requireActivity(), InicializacaoActivity.class));
                requireActivity().finish();
            }
        });

        buildPreferenceChips();

        return v;
    }

    private void renderUser() {
        boolean logged = GerenciadorEstado.get().isLoggedIn();
        if (logged) {
            tvName.setText("Usuário Teste");
            tvSub.setText("Logado para testes (ID: " + GerenciadorEstado.get().getCurrentUserId() + ")");
            btnLoginLogout.setText("Sair");
        } else {
            tvName.setText("Convidado");
            tvSub.setText("Fazer Login");
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