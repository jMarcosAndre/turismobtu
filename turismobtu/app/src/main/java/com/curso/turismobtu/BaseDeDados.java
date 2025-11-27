package com.curso.turismobtu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseDeDados {
    public static final List<PontoTuristico> PLACES = Arrays.asList(
            new PontoTuristico("p1","Museu da Cidade","Museus","Centro",4.7,
                    "https://images.unsplash.com/photo-1529429612779-c8e40ef2f36e?q=80&w=1200&auto=format&fit=crop",-22.90,-47.06),
            new PontoTuristico("p2","Trilha da Cachoeira Azul","Cachoeiras","Serra",4.8,
                    "https://images.unsplash.com/photo-1508615039623-a25605d2b022?q=80&w=1200&auto=format&fit=crop",-22.92,-47.09),
            new PontoTuristico("p3","Parque Central","Parques","Zona Norte",4.5,
                    "https://images.unsplash.com/photo-1469474968028-56623f02e42e?q=80&w=1200&auto=format&fit=crop",-22.91,-47.04),
            new PontoTuristico("p4","Estádio Municipal","Esportes","Oeste",4.3,
                    "https://images.unsplash.com/photo-1518602164577-0babd100d8ac?q=80&w=1200&auto=format&fit=crop",-22.905,-47.08),
            new PontoTuristico("p5","Cantina do Centro","Restaurantes","Centro",4.6,
                    "https://images.unsplash.com/photo-1559339352-11d035aa65de?q=80&w=1200&auto=format&fit=crop",-22.895,-47.07),
            new PontoTuristico("p6","Feira de Artes","Eventos","Praça Velha",4.4,
                    "https://images.unsplash.com/photo-1525874684015-58379d421a52?q=80&w=1200&auto=format&fit=crop",-22.898,-47.055),
            new PontoTuristico("p7","Hotel Mirante","Hospedagem","Vista Alta",4.2,
                    "https://images.unsplash.com/photo-1566073771259-6a8506099945?q=80&w=1200&auto=format&fit=crop",-22.903,-47.03),
            new PontoTuristico("p8","Shopping Boulevard","Compras","Leste",4.1,
                    "https://images.unsplash.com/photo-1509512693960-30f39d81d6a5?q=80&w=1200&auto=format&fit=crop",-22.887,-47.065)
    );

    public static List<PontoTuristico> byCategory(String category) {
        if (category == null) return new ArrayList<>(PLACES);
        return PLACES.stream().filter(p -> category.equals(p.category)).collect(Collectors.toList());
    }
}
