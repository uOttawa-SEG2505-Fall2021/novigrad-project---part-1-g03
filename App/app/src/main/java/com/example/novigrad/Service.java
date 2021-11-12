package com.example.novigrad;

public class Service {

    // instance variables
    private String nomService;
    private String infosRequises;
    private String docsRequis;

    // Constructor 1
    public Service() {

    }

    // Constructor 2
    public Service(String nom, String infos, String docs){
        this.nomService = nom;
        this.infosRequises = infos;
        this.docsRequis = docs;
    }

    // Getters
    public String getNomService() {
        return nomService;
    }

    public String getInfosRequises() {
        return infosRequises;
    }

    public String getDocsRequis() {
        return docsRequis;
    }
}
