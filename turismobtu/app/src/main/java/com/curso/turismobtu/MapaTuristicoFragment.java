package com.curso.turismobtu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

public class MapaTuristicoFragment extends Fragment {

    private MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private String filterCategory = null;
    private final List<String> categorias = Arrays.asList(
            "Todos",
            "Museus",
            "Esportes",
            "Cachoeiras",
            "Restaurantes",
            "Parques",
            "Eventos",
            "Hospedagem",
            "Compras"
    );

    private final Map<String, Integer> colorByCat = new HashMap<String, Integer>() {{
        put("Museus", 0xFF3B82F6);       // azul
        put("Esportes", 0xFF22C55E);     // verde
        put("Cachoeiras", 0xFF06B6D4);   // ciano
        put("Restaurantes", 0xFFFF2E8B); // magenta
        put("Parques", 0xFF84CC16);      // “lime”
        put("Eventos", 0xFFF97316);      // laranja
        put("Hospedagem", 0xFF7C3AED);   // roxo
        put("Compras", 0xFFF59E0B);      // amarelo
    }};

    private final ActivityResultLauncher<String> reqLocation =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) enableMyLocation();
            });

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);


        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());

        map = v.findViewById(R.id.osm_map);
        map.setMultiTouchControls(true);
        map.getController().setZoom(12.0);
        map.getController().setCenter(new GeoPoint(-22.90, -47.06)); // centro mock

        ChipGroup chips = v.findViewById(R.id.chips);
        for (String c : categorias) {
            Chip chip = new Chip(requireContext());
            chip.setText(c);
            chip.setCheckable(true);


            chip.setChipBackgroundColorResource(android.R.color.white);
            chip.setRippleColorResource(android.R.color.darker_gray);

            if ("Todos".equals(c)) chip.setChecked(true);

            chip.setOnClickListener(view -> {
                filterCategory = "Todos".equals(c) ? null : c;
                renderMarkers();
            });

            chips.addView(chip);
        }


        // Permissão de localização
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            reqLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        renderMarkers();
        return v;
    }

    private void enableMyLocation() {
        if (myLocationOverlay != null) return;
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), map);
        myLocationOverlay.enableMyLocation();
        map.getOverlays().add(myLocationOverlay);
    }

    private void renderMarkers() {
        map.getOverlays().removeIf(ov -> ov instanceof Marker);
        for (PontoTuristico p : BaseDeDados.PLACES) {
            if (filterCategory != null && !filterCategory.equals(p.category)) continue;

            GeoPoint pt = new GeoPoint(p.lat, p.lng);
            Marker m = new Marker(map);
            m.setPosition(pt);
            m.setTitle(p.name);
            m.setSubDescription(p.district + " • ★ " + String.format("%.1f", p.rating));

            // Ícone tintado por categoria
            m.setIcon(tintedPin(colorByCat.getOrDefault(p.category, 0xFF3B63FF)));


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
