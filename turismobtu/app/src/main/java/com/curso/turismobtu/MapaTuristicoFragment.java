package com.curso.turismobtu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.res.ColorStateList;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapaTuristicoFragment extends Fragment implements DataLoadListener {

    private MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private String filterCategory = null;

    private FirebaseDataManager dataManager;
    private List<PontoTuristico> currentPoints;

    // LISTA CORRIGIDA
    private final List<String> categorias = Arrays.asList(
            "Todos", "Museus", "Esportes", "Cachoeiras", "Restaurantes",
            "Parques", "Eventos", "Mirantes", "Religioso"
    );

    // MAPA DE CORES CORRIGIDO
    private final Map<String, Integer> colorByCat = new HashMap<String, Integer>() {{
        put("Museus", 0xFF3B82F6);
        put("Esportes", 0xFF22C55E);
        put("Cachoeiras", 0xFF06B6D4);
        put("Restaurantes", 0xFFFF2E8B);
        put("Parques", 0xFF84CC16);
        put("Eventos", 0xFFF97316);
        put("Mirantes", 0xFF7C3AED);     // Cor de Hospedagem -> Mirantes
        put("Religioso", 0xFFF59E0B);    // Cor de Compras -> Religioso
    }};

    private final ActivityResultLauncher<String> reqLocation =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) enableMyLocation();
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new FirebaseDataManager();
        dataManager.fetchAllPontos(this);
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());

        map = v.findViewById(R.id.osm_map);
        map.setMultiTouchControls(true);
        map.getController().setZoom(12.0);

        map.getController().setCenter(new GeoPoint(-22.88, -48.44));

        ChipGroup chips = v.findViewById(R.id.chips);
        for (String c : categorias) {
            Chip chip = new Chip(requireContext());
            chip.setText(c);
            chip.setCheckable(true);

            // LÓGICA DE CORES DINÂMICAS PARA O CHIP
            int color;
            if ("Todos".equals(c)) {
                color = 0xFF666666;
            } else {
                color = colorByCat.getOrDefault(c, 0xFF3B63FF);
            }

            int[][] states = new int[][] {
                    new int[] { android.R.attr.state_checked },
                    new int[] {}
            };
            int[] colors = new int[] {
                    color,
                    0xFFEEEEEE
            };
            ColorStateList colorStateList = new ColorStateList(states, colors);

            chip.setChipBackgroundColor(colorStateList);

            int[] textColors = new int[] {
                    0xFFFFFFFF,
                    color
            };
            chip.setTextColor(new ColorStateList(states, textColors));

            if ("Todos".equals(c)) chip.setChecked(true);

            chip.setOnClickListener(view -> {
                filterCategory = "Todos".equals(c) ? null : c;
                renderMarkers();
            });

            chips.addView(chip);
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            reqLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        renderMarkers();
        return v;
    }

    @Override
    public void onDataLoaded(List<PontoTuristico> pontos) {
        currentPoints = pontos;
        renderMarkers();
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(getContext(), "Erro ao carregar mapa: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    private void enableMyLocation() {
        if (myLocationOverlay != null) return;
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), map);
        myLocationOverlay.enableMyLocation();
        map.getOverlays().add(myLocationOverlay);
    }

    private void renderMarkers() {
        if (map == null || currentPoints == null) return;

        map.getOverlays().removeIf(ov -> ov instanceof Marker);

        for (PontoTuristico p : currentPoints) {
            if (filterCategory != null && !p.getCategory().contains(filterCategory)) continue;

            GeoPoint pt = new GeoPoint(p.getLat(), p.getLng());
            Marker m = new Marker(map);
            m.setPosition(pt);
            m.setTitle(p.getName());
            m.setSubDescription(p.getDistrict() + " • ★ " + String.format("%.1f", p.getRating()));

            m.setIcon(tintedPin(colorByCat.getOrDefault(p.getCategory(), 0xFF3B63FF)));

            m.setOnMarkerClickListener((marker, mapView) -> {
                marker.showInfoWindow();
                return true;
            });

            map.getOverlays().add(m);
        }
        map.invalidate();
    }

    private Drawable tintedPin(int argb) {
        Drawable d = ContextCompat.getDrawable(requireContext(), R.drawable.ic_pin);
        if (d == null) return null;
        Drawable wr = DrawableCompat.wrap(d.mutate());
        DrawableCompat.setTint(wr, argb);
        return wr;
    }

    @Override public void onResume() {
        super.onResume();
        if (map != null) map.onResume();
    }

    @Override public void onPause() {
        if (map != null) map.onPause();
        super.onPause();
    }
}