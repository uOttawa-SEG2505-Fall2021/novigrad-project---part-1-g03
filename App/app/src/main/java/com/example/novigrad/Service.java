package com.example.novigrad;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

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

    public static boolean verifyService(Service service, String[] errors) {

        //check for numbers in the service name
        for (char c : service.getNomService().toCharArray()) {
            if (Character.isDigit(c)) {
                errors[0] = "Le nom de service ne devrait pas contenir de chiffres";
                return false;
            }
        }

         if (!service.getNomService().isEmpty() && !service.getInfosRequises().isEmpty() && !service.getDocsRequis().isEmpty()) {
            return true;
        } else {
            errors[0] = "Il y a des champs de textes vides";
        }
         return false;
    }
}
