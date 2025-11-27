package com.curso.turismobtu;

import java.io.Serializable;

public class PontoTuristico implements Serializable {

    // 1. CAMPOS AGORA PRIVADOS
    private String id;
    private String name;
    private String category;
    private String district;
    private String imageUrl;
    private double rating;
    private double lat;
    private double lng;

    // 2. CONSTRUTOR VAZIO (Obrigatório para o Firebase)
    public PontoTuristico() {
        // Inicialização de segurança
        this.id = "";
        this.name = "";
        this.category = "";
        this.district = "";
        this.rating = 0.0;
        this.imageUrl = "";
        this.lat = 0.0;
        this.lng = 0.0;
    }

    // 3. CONSTRUTOR COMPLETO (Opcional, mas mantido para consistência)
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

    // 4. GETTERS E SETTERS (MANDATÓRIOS PARA O FIREBASE)

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }
}