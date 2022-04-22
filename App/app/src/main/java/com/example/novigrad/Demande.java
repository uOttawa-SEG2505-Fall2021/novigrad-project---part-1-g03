package com.example.novigrad;

/**
 * Classe Demande qui créer une demande lorsqu'un utilisateur soumet une demande de service
 * */
public class Demande {

    //magic numbers for demande: 0 1 2
    // 0 = pending
    // 1 = approved
    // 2 = rejected
    private int status = 0;

    // instance variables
    private String firstName;
    private String lastName;
    private String nomDeUtilisateur;
    private String nomDuServiceDemande;
    private String nomSuccursaleDemande;

    // constructeur 1
    public Demande(){}

    // constructeur 2
    public Demande(String firstName, String lastName, String nomDeUtilisateur, String nomDuServiceDemande, String nomSuccursaleDemande, int status){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nomDeUtilisateur = nomDeUtilisateur;
        this.nomDuServiceDemande = nomDuServiceDemande;
        this.nomSuccursaleDemande = nomSuccursaleDemande;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o) {
            return true;
        }
        // null check
        if (o == null) {
            return false;
        }
        // type check and cast
        if (getClass() != o.getClass()) {
            return false;
        }
        Demande demande = (Demande) o;
        return demande.firstName.equals(this.firstName) &&
                demande.lastName.equals(this.lastName) &&
                demande.nomDeUtilisateur.equals(this.nomDeUtilisateur) &&
                demande.nomDuServiceDemande.equals(this.nomDuServiceDemande) &&
                demande.nomSuccursaleDemande.equals(this.nomSuccursaleDemande);
    }

    @Override
    public String toString() {
        return "Demande faite par " + firstName + " " + lastName + " à la succursale " + getNomSuccursaleDemande() + " pour le service \"" + nomDuServiceDemande +"\"";
    }
}
