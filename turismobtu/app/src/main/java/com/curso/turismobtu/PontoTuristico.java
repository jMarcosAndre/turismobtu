package com.curso.turismobtu;

import java.io.Serializable;

public class PontoTuristico implements Serializable {


    private String id;
    private String name;
    private String category;
    private String district;
    private String imageUrl;
    private double rating;
    private double lat;
    private double lng;


    private String description;
    private String address;
    private String schedule;


    public PontoTuristico() {
        this.id = "";
        this.name = "";
        this.category = "";
        this.district = "";
        this.rating = 0.0;
        this.imageUrl = "";
        this.lat = 0.0;
        this.lng = 0.0;
        this.description = "Detalhe não fornecido."; // Inicialização
        this.address = "Endereço não fornecido.";      // Inicialização
        this.schedule = "Horário não fornecido.";     // Inicialização
    }

    public PontoTuristico(String id, String name, String category, String district,
                          double rating, String imageUrl, double lat, double lng,
                          String description, String address, String schedule) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.district = district;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
        this.address = address;
        this.schedule = schedule;
    }

    // GETTERS E SETTERS (MANDATÓRIOS PARA O FIREBASE)

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

    // GETTERS E SETTERS DOS NOVOS CAMPOS
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
}