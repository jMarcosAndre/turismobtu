package com.curso.turismobtu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ListaPromos extends RecyclerView.Adapter<ListaPromos.VH> {

    public interface OnClick { void onClick(OfertaPromocional p); }
    private final List<OfertaPromocional> data;
    private final OnClick onClick;

    public ListaPromos(List<OfertaPromocional> data, OnClick onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promo, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        OfertaPromocional p = data.get(pos);
        h.title.setText(p.title);
        h.subtitle.setText(p.subtitle);
        Glide.with(h.img.getContext())
                .load(p.imageUrl)
                .centerCrop()
                .into(h.img);
        h.itemView.setOnClickListener(v -> { if (onClick != null) onClick.onClick(p); });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img; TextView title; TextView subtitle;
        VH(View v) {
            super(v);
            img = v.findViewById(R.id.img);
            title = v.findViewById(R.id.title);
            subtitle = v.findViewById(R.id.subtitle);
        }
    }
}
