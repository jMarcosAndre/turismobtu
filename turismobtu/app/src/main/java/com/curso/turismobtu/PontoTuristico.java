package com.curso.turismobtu;

public class PontoTuristico {
    public final String id, name, category, district, imageUrl;
    public final double rating, lat, lng;

    public PontoTuristico(String id, String name, String category, String district,
                          double rating, String imageUrl, double lat, double lng) {
        this.id = id; this.name = name; this.category = category; this.district = district;
        this.rating = rating; this.imageUrl = imageUrl; this.lat = lat; this.lng = lng;
    }
}
