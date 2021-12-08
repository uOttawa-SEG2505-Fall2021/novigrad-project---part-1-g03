package com.example.novigrad.user;

/**
 * Classe ClientAccount qui est une sous-classe de UserAccount afin de créer un compte client
 * */

public class ClientAccount extends UserAccount{

    public ClientAccount(int aId, String aPrenom, String aNomDeFamille, String aNomDeUtiliseur, String aMotDePasse) {
        super(0, aId, aPrenom, aNomDeFamille, aNomDeUtiliseur, aMotDePasse);
    }

}
