package com.curso.turismobtu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GerenciadorEstado {
    private static final GerenciadorEstado I = new GerenciadorEstado();
    public static GerenciadorEstado get() { return I; }

    // Login
    private boolean loggedIn = false;
    public boolean isLoggedIn(){ return loggedIn; }
    public void setLoggedIn(boolean v){ loggedIn = v; }

    // ---- Favoritos ----
    private final Set<String> favorites = new HashSet<>();
    public boolean isFavorite(String placeId){ return favorites.contains(placeId); }
    public void toggleFavorite(String placeId){
        if (favorites.contains(placeId)) favorites.remove(placeId);
        else favorites.add(placeId);
    }
    public List<PontoTuristico> favoritePlaces(){
        return BaseDeDados.PLACES.stream()
                .filter(p -> favorites.contains(p.id))
                .collect(Collectors.toList());
    }
}
