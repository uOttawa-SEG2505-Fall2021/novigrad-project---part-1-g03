package com.example.novigrad.user;

/**
 * Classe EmployeeAccount qui est une sous-classe de UserAccount afin de créer un compte employé
 * */

public class EmployeeAccount extends UserAccount{
    public EmployeeAccount(int aId, String aPrenom, String aNomDeFamille, String aNomDeUtiliseur, String aMotDePasse) {
        super(1, aId, aPrenom, aNomDeFamille, aNomDeUtiliseur, aMotDePasse);
    }
}
