package com.example.novigrad;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Classe Service pour créer un service
 * */

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

    public static boolean verifyService(Service service, Context context) {

        //check for numbers in the service name
        for (char c : service.getNomService().toCharArray()) {
            if (Character.isDigit(c)) {
                Toast.makeText(context, "Le nom de service ne devrait pas contenir de chiffres", Toast.LENGTH_LONG).show();
                return false;
            }
        }

         if (!TextUtils.isEmpty(service.getNomService()) && !TextUtils.isEmpty(service.getInfosRequises()) && !TextUtils.isEmpty(service.getDocsRequis())) {
            return true;
        } else {
            Toast.makeText(context, "Il y a des champs de textes vides", Toast.LENGTH_LONG).show();
        }
         return false;
    }
}
