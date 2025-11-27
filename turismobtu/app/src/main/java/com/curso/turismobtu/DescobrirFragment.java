package com.curso.turismobtu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DescobrirFragment extends Fragment implements DataLoadListener {

    private ViewPager2 pager;
    private RecyclerView rvCategories;
    private FirebaseDataManager dataManager;

    private final Handler auto = new Handler(Looper.getMainLooper());
    private int page = 0;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        EditText et = v.findViewById(R.id.et_search);
        v.findViewById(R.id.btn_filters).setOnClickListener(x -> showFilters());


        pager = v.findViewById(R.id.pager);
        rvCategories = v.findViewById(R.id.rv_categories);


        dataManager = new FirebaseDataManager();
        dataManager.fetchAllPontos(this);


        et.setOnEditorActionListener((tv, actionId, event) -> {
            Toast.makeText(getContext(), "Buscar: " + tv.getText(), Toast.LENGTH_SHORT).show();
            return true;
        });


        initializePromoCarrossel();

        return v;
    }


    private void initializePromoCarrossel() {
        List<OfertaPromocional> promos = Arrays.asList(
                new OfertaPromocional("Festival de Gastronomia", "Sabores locais • Este mês",
                        "https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=1200&auto=format&fit=crop"),
                new OfertaPromocional("Parques e Trilhas", "Descubra rotas verdes",
                        "https://images.unsplash.com/photo-1520974396685-5f59d8d35a08?q=80&w=1200&auto=format&fit=crop"),
                new OfertaPromocional("Agenda Cultural", "Shows e eventos do fim de semana",
                        "https://images.unsplash.com/photo-1506157786151-b8491531f063?q=80&w=1200&auto=format&fit=crop")
        );
        pager.setAdapter(new ListaPromos(promos, p ->
                Toast.makeText(getContext(), "Abrir: " + p.title, Toast.LENGTH_SHORT).show()
        ));
        startAutoSlide(promos.size());
    }


    @Override
    public void onDataLoaded(List<PontoTuristico> allPontos) {
        if (rvCategories == null || allPontos.isEmpty()) {
            FerramentasApp.toast(getContext(), "Nenhum ponto turístico carregado.");
            return;
        }

        // LINHA CRÍTICA: Salva a lista na cache global para DetalheLocalActivity
        GerenciadorEstado.get().setCachedPlaces(allPontos);


        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 1));


        List<TipoCategoria> cats = Arrays.asList(
                new TipoCategoria("Museus", R.drawable.ic_home, 0xFF3B82F6),
                new TipoCategoria("Esportes", R.drawable.ic_map, 0xFF22C55E),
                new TipoCategoria("Cachoeiras", R.drawable.ic_map, 0xFF06B6D4),
                new TipoCategoria("Restaurantes", R.drawable.ic_favorite, 0xFFFF2E8B),
                new TipoCategoria("Parques", R.drawable.ic_map, 0xFF84CC16),
                new TipoCategoria("Eventos", R.drawable.ic_map, 0xFFF97316),
                new TipoCategoria("Mirantes", R.drawable.ic_person, 0xFF7C3AED),
                new TipoCategoria("Religioso", R.drawable.ic_map, 0xFFF59E0B)
        );


        rvCategories.setAdapter(new ListaCategorias(cats, c ->
                startActivity(ListaLocaisActivity.intent(requireContext(), c.label, new ArrayList<>(allPontos)))
        ));
    }

    @Override
    public void onFailure(String errorMessage) {
        FerramentasApp.toast(getContext(), "Falha ao carregar pontos: " + errorMessage);
    }


    private void showFilters() {
        BottomSheetDialog b = new BottomSheetDialog(requireContext());
        View content = LayoutInflater.from(getContext()).inflate(R.layout.bottom_filters, null, false);
        content.findViewById(R.id.btn_apply).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtros aplicados", Toast.LENGTH_SHORT).show();
            b.dismiss();
        });
        b.setContentView(content);
        b.show();
    }

    private void startAutoSlide(int count) {
        auto.postDelayed(new Runnable() {
            @Override public void run() {
                if (pager == null || count == 0) return;
                page = (page + 1) % count;
                pager.setCurrentItem(page, true);
                auto.postDelayed(this, 4000);
            }
        }, 4000);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        auto.removeCallbacksAndMessages(null);
        pager = null;
    }
}