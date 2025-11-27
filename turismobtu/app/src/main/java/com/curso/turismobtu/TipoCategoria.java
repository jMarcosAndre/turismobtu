package com.curso.turismobtu;

public class TipoCategoria {
    public final String label;
    public final int iconRes;
    public final int color; // ARGB

    public TipoCategoria(String label, int iconRes, int color) {
        this.label = label;
        this.iconRes = iconRes;
        this.color = color;
    }
}
