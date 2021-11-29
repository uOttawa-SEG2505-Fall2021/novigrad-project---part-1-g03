package com.example.novigrad;

/**
 * Classe Demande qui créer une demande lorsqu'un utilisateur soumet une demande de service
 * */
public class Demande {

    // instance variables
    private String firstName;
    private String lastName;
    private String nomDeUtilisateur;
    private String nomDuServiceDemande;
    private String nomSuccursaleDemande;

    // constructeur 1
    public Demande(){}

    // constructeur 1
    public Demande(String firstName, String lastName, String nomDeUtilisateur, String nomDuServiceDemande, String nomSuccursaleDemande){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nomDeUtilisateur = nomDeUtilisateur;
        this.nomDuServiceDemande = nomDuServiceDemande;
        this.nomSuccursaleDemande = nomSuccursaleDemande;
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

    public String getNomDuServiceDemande() {
        return nomDuServiceDemande;
    }

    public String getNomSuccursaleDemande() {
        return nomSuccursaleDemande;
    }

    @Override
    public String toString() {
        return "Demande faite par " + firstName + " " + lastName + " pour le service \"" + nomDuServiceDemande +"\"";
    }
}
