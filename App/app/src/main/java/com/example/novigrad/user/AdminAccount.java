package com.example.novigrad.user;

/**
 * Classe AdminAccount qui est une sous-classe de UserAccount afin de créer un compte administrateur
 * */

public class AdminAccount extends UserAccount{
    public AdminAccount(int aId, String aPrenom, String aNomDeFamille, String aNomDeUtiliseur, String aMotDePasse) {
        super(2, aId, aPrenom, aNomDeFamille, aNomDeUtiliseur, aMotDePasse);
    }
}
