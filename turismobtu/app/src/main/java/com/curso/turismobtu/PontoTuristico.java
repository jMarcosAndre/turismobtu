package com.curso.turismobtu;

import java.io.Serializable;


public class PontoTuristico implements Serializable {


    public String id;
    public String name;
    public String category;
    public String district;
    public String imageUrl;
    public double rating;
    public double lat;
    public double lng;

    // >> CONSTRUTOR VAZIO (Obrigatório para o Firebase) <<
    public PontoTuristico() {
        this.id = "";
        this.name = "";
        this.category = "";
        this.district = "";
        this.rating = 0.0;
        this.imageUrl = "";
        this.lat = 0.0;
        this.lng = 0.0;
    }

    public PontoTuristico(String id, String name, String category, String district,
                          double rating, String imageUrl, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.district = district;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
    }
}