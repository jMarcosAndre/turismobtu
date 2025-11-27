package com.curso.turismobtu;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListaCategorias extends RecyclerView.Adapter<ListaCategorias.VH> {

    public interface OnClick { void onClick(TipoCategoria c); }

    private final List<TipoCategoria> data;
    private final OnClick listener;

    public ListaCategorias(List<TipoCategoria> data, OnClick listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        TipoCategoria c = data.get(pos);
        h.label.setText(c.label);
        h.icon.setImageResource(c.iconRes);

        // “chip”
        h.icon.setColorFilter(c.color, PorterDuff.Mode.SRC_IN);
        h.chip.getBackground().setTint(0x1F000000 | (c.color & 0x00FFFFFF)); // leve fundo
        h.chip.getBackground().setTintMode(PorterDuff.Mode.SRC_ATOP);

        h.itemView.setOnClickListener(v -> listener.onClick(c));
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final TextView label;
        final ImageView icon;
        final FrameLayout chip;
        VH(View v) {
            super(v);
            label = v.findViewById(R.id.label);
            icon  = v.findViewById(R.id.icon);
            chip  = v.findViewById(R.id.chip);
        }
    }
}
