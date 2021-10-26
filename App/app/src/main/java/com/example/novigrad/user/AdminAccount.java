package com.example.novigrad.user;

public class AdminAccount extends UserAccount{
    public AdminAccount(int aId, String aPrenom, String aNomDeFamille, String aNomDeUtiliseur, String aMotDePasse) {
        super(2, aId, aPrenom, aNomDeFamille, aNomDeUtiliseur, aMotDePasse);
    }
}
