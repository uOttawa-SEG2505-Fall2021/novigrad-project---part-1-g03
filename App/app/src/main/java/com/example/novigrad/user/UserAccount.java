package com.example.novigrad.user;

/**
 * Classe UserAccount
 * */

public class UserAccount
{

    private static UserAccount userInstance;

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //UserAccount Attributes
    private int accountType;
    private int id;
    private String prenom;
    private String nomDeFamille;
    private String nomDeUtilisateur;
    private String motDePasse;

    //------------------------
    // CONSTRUCTOR
    //------------------------


    public static UserAccount getUserInstance() {
        return userInstance;
    }

    public static void setUserInstance(UserAccount currentUser) {
        userInstance = currentUser;
    }

    public static void unsetUserInstance() {
        userInstance = null;
    }

    //blank constructor so that UserAccount can be written to Firebase
    public UserAccount(){}

    public UserAccount(int aAccountType, int aId, String aPrenom, String aNomDeFamille, String aNomDeUtilisateur, String aMotDePasse) {
        accountType = aAccountType;
        id = aId;
        prenom = aPrenom;
        nomDeFamille = aNomDeFamille;
        nomDeUtilisateur = aNomDeUtilisateur;
        motDePasse = aMotDePasse;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setAccountType(int aAccountType)
    {
        boolean wasSet = false;
        accountType = aAccountType;
        wasSet = true;
        return wasSet;
    }

    public boolean setId(int aId)
    {
        boolean wasSet = false;
        id = aId;
        wasSet = true;
        return wasSet;
    }

    public boolean setPrenom(String aPrenom)
    {
        boolean wasSet = false;
        prenom = aPrenom;
        wasSet = true;
        return wasSet;
    }

    public boolean setNomDeFamille(String aNomDeFamille)
    {
        boolean wasSet = false;
        nomDeFamille = aNomDeFamille;
        wasSet = true;
        return wasSet;
    }

    public boolean setNomDeUtiliseur(String aNomDeUtiliseur)
    {
        boolean wasSet = false;
        nomDeUtilisateur = aNomDeUtiliseur;
        wasSet = true;
        return wasSet;
    }

    public boolean setMotDePasse(String aMotDePasse)
    {
        boolean wasSet = false;
        motDePasse = aMotDePasse;
        wasSet = true;
        return wasSet;
    }

    /**
     * 2 pour admin, 1 pour employee, 0 pour client 
     * (devrait être implémenté dans les constructeur des sous-classes)
     */
    public int getAccountType()
    {
        return accountType;
    }

    public int getId()
    {
        return id;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public String getNomDeFamille()
    {
        return nomDeFamille;
    }

    public String getNomDeUtilisateur()
    {
        return nomDeUtilisateur;
    }

    public String getMotDePasse()
    {
        return motDePasse;
    }

    // Test to check if a first name or last name is valid
    public boolean isValid(String str){
        char[] chars = str.toCharArray();
        for(char c : chars) {
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public String toString()
    {
        return super.toString() + "["+
                "accountType" + ":" + getAccountType()+ "," +
                "id" + ":" + getId()+ "," +
                "prenom" + ":" + getPrenom()+ "," +
                "nomDeFamille" + ":" + getNomDeFamille()+ "," +
                "nomDeUtiliseur" + ":" + getNomDeUtilisateur()+ "," +
                "motDePasse" + ":" + getMotDePasse()+ "]";
    }
}
