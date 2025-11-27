package com.curso.turismobtu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ListaLocaisAdapter extends RecyclerView.Adapter<ListaLocaisAdapter.VH> {

    interface OnClick {
        void onViewMore(PontoTuristico p);
        void onViewOnMap(PontoTuristico p);
    }

    private final List<PontoTuristico> data;
    private final OnClick listener;

    public ListaLocaisAdapter(List<PontoTuristico> data, OnClick listener) {
        this.data = data; this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        PontoTuristico p = data.get(pos);


        h.title.setText(p.getName());


        h.sub.setText(p.getDistrict() + " • ★ " + String.format("%.1f", p.getRating()));

        Glide.with(h.image.getContext())

                .load(p.getImageUrl())
                .centerCrop()
                .into(h.image);

        h.btnMore.setOnClickListener(v -> listener.onViewMore(p));
        h.btnMap.setOnClickListener(v -> listener.onViewOnMap(p));
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView image; TextView title; TextView sub; Button btnMore; Button btnMap;
        VH(View v) {
            super(v);
            image = v.findViewById(R.id.img);
            title = v.findViewById(R.id.title);
            sub = v.findViewById(R.id.subtitle);
            btnMap = v.findViewById(R.id.btn_map);
        }
    }
}