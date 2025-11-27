package com.curso.turismobtu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocaisFavoritos extends Fragment {

    private RecyclerView rv;
    private View empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);
        rv = v.findViewById(R.id.rv_favs);
        empty = v.findViewById(R.id.empty);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        render();
    }

    private void render() {
        List<PontoTuristico> favs = GerenciadorEstado.get().favoritePlaces();
        boolean isEmpty = favs.isEmpty();

        empty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rv.setVisibility(isEmpty ? View.GONE : View.VISIBLE);

        if (!isEmpty) {
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(new ListaLocaisAdapter(favs, new ListaLocaisAdapter.OnClick() {
                @Override
                public void onViewMore(PontoTuristico p) {
                    // CORRIGIDO: Usa o método público p.getId() em vez de p.id
                    startActivity(DetalheLocalActivity.intent(requireContext(), p.getId()));
                }

                @Override
                public void onViewOnMap(PontoTuristico p) {
                    FerramentasApp.toast(getContext(), "Ver no mapa: " + p.getName());
                }
            }));
        }
    }
}