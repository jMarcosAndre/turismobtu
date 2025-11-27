package com.curso.turismobtu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GerenciadorEstado {
    private static final GerenciadorEstado I = new GerenciadorEstado();
    public static GerenciadorEstado get() { return I; }

    private Map<String, PontoTuristico> cachedPlaces = new HashMap<>();

    public void setCachedPlaces(List<PontoTuristico> points) {
        cachedPlaces = points.stream()
                .collect(Collectors.toMap(p -> p.id, p -> p, (p1, p2) -> p1));
    }

    public PontoTuristico getPontoById(String id) {
        return cachedPlaces.get(id);
    }

    private boolean loggedIn = false;
    public boolean isLoggedIn(){ return loggedIn; }
    public void setLoggedIn(boolean v){ loggedIn = v; }

    private final Set<String> favorites = new HashSet<>();
    public boolean isFavorite(String placeId){ return favorites.contains(placeId); }
    public void toggleFavorite(String placeId){
        if (favorites.contains(placeId)) favorites.remove(placeId);
        else favorites.add(placeId);
    }
    public List<PontoTuristico> favoritePlaces(){
        return cachedPlaces.values().stream()
                .filter(p -> favorites.contains(p.id))
                .collect(Collectors.toList());
    }
}