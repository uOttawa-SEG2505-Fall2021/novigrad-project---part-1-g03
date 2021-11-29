package com.example.novigrad;

/**
 * Classe Demande qui créer une demande lorsqu'un utilisateur soumet une demande de service
 * */
public class Demande {

    // instance variables
    private String firstName;
    private String lastName;
    private String nomDeUtilisateur;
    private String nomDuServiceDemandé;
    private String nomSuccursaleDemandé;

    // constructeur 1
    public Demande(){}

    // constructeur 1
    public Demande(String firstName, String lastName, String nomDeUtilisateur, String nomDuServiceDemandé, String nomSuccursaleDemandé){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nomDeUtilisateur = nomDeUtilisateur;
        this.nomDuServiceDemandé = nomDuServiceDemandé;
        this.nomSuccursaleDemandé = nomSuccursaleDemandé;
    }

    // getters
    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNomDeUtilisateur() {
        return nomDeUtilisateur;
    }

    public String getNomDuServiceDemandé() {
        return nomDuServiceDemandé;
    }

    public String getNomSuccursaleDemandé() {
        return nomSuccursaleDemandé;
    }

    @Override
    public String toString() {
        return "Demande faite par " + firstName + " " + lastName + " pour le service \"" + nomDuServiceDemandé +"\"";
    }
}
