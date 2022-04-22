package com.example.novigrad;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.novigrad.user.ClientAccount;
import com.example.novigrad.user.EmployeeAccount;
import com.example.novigrad.user.UserAccount;
import com.example.novigrad.Demande;

import java.util.ArrayList;
import java.util.List;

/**
 * Test unitaires pour les strings format utilisé dans notre application
 * */

public class StringFormatTests {


    // test du Welcome Message
    @Test
    public void testWelcomeMessage() {
        UserAccount user = new ClientAccount(0, "User", "Client", "userclient", "123456");
        String welcomeMessaqge = String.format("Bonjour %1$s!\\n Bienvenue à Service Novigrad! \\n Vous êtes connecté en tant que client.",
                user.getPrenom());
        assertEquals("Bonjour User!\\n Bienvenue à Service Novigrad! \\n Vous êtes connecté en tant que client.", welcomeMessaqge);
    }

    // test de l'info du compte lorsqu'on veut le supprimer
    @Test
    public void testAccountInfo() {
        UserAccount client = new ClientAccount(0, "User", "Client", "userclient", "123456");
        UserAccount employee = new EmployeeAccount(0, "User", "EMployee", "useremployee", "654321");
        String clientType = String.format("Nom d\\'utilisateur: %1$s \\n Type de compte: %2$s", client.getNomDeUtilisateur(),(client.getAccountType() == 0 ? "Client" : "Employé"));
        String employeeType = String.format("Nom d\\'utilisateur: %1$s \\n Type de compte: %2$s", employee.getNomDeUtilisateur(),(employee.getAccountType() == 0 ? "Client" : "Employé"));
        assertEquals("Nom d\\'utilisateur: userclient \\n Type de compte: Client", clientType);
        assertEquals("Nom d\\'utilisateur: useremployee \\n Type de compte: Employé", employeeType);
    }

    // test de l'info de la demande soumise
    @Test
    public void testDemandeSoumise() {
        Demande demandeEnAttente = new Demande("User", "Client", "userclient", "Carte Santé","Novigrad Centrale",0);
        Demande demandeApprouvée = new Demande("Test", "Account", "testaccount", "Covid-19","Novigrad Centrale",1);
        Demande demandeRejetée = new Demande("Client", "Test", "clienttest", "Permis de conduire","Novigrad Centrale",2);
        String pending = String.format("Prénom/Nom: %1$s %2$s\\nNom d\\'utilisateur: %3$s \\nService demandé: %4$s \\nSuccursale concernée: %5$s \\nStatut: %6$s", demandeEnAttente.getFirstName(), demandeEnAttente.getLastName(),
                demandeEnAttente.getNomDeUtilisateur(), demandeEnAttente.getNomDuServiceDemande(),demandeEnAttente.getNomSuccursaleDemande(), (demandeEnAttente.getStatus() == 0 ? "En attente" : demandeEnAttente.getStatus() == 1 ? "Approuvée" : "Rejetée"));
        String approved = String.format("Prénom/Nom: %1$s %2$s\\nNom d\\'utilisateur: %3$s \\nService demandé: %4$s \\nSuccursale concernée: %5$s \\nStatut: %6$s", demandeApprouvée.getFirstName(), demandeApprouvée.getLastName(),
                demandeApprouvée.getNomDeUtilisateur(), demandeApprouvée.getNomDuServiceDemande(), demandeApprouvée.getNomSuccursaleDemande(), (demandeApprouvée.getStatus() == 0 ? "En attente" : demandeApprouvée.getStatus() == 1 ? "Approuvée" : "Rejetée"));
        String rejected = String.format("Prénom/Nom: %1$s %2$s\\nNom d\\'utilisateur: %3$s \\nService demandé: %4$s \\nSuccursale concernée: %5$s \\nStatut: %6$s", demandeRejetée.getFirstName(), demandeRejetée.getLastName(),
                demandeRejetée.getNomDeUtilisateur(), demandeRejetée.getNomDuServiceDemande(),demandeRejetée.getNomSuccursaleDemande(), (demandeRejetée.getStatus() == 0 ? "En attente" : demandeRejetée.getStatus() == 1 ? "Approuvée" : "Rejetée"));
        assertEquals("Prénom/Nom: User Client\\nNom d\\'utilisateur: userclient \\nService demandé: Carte Santé \\nSuccursale concernée: Novigrad Centrale \\nStatut: En attente", pending);
        assertEquals("Prénom/Nom: Test Account\\nNom d\\'utilisateur: testaccount \\nService demandé: Covid-19 \\nSuccursale concernée: Novigrad Centrale \\nStatut: Approuvée", approved);
        assertEquals("Prénom/Nom: Client Test\\nNom d\\'utilisateur: clienttest \\nService demandé: Permis de conduire \\nSuccursale concernée: Novigrad Centrale \\nStatut: Rejetée", rejected);
    }

    // test affichage de la moyenne de la note d'une succursale
    @Test
    public void testRatingAverageString() {
        List<Rating> ratings = new ArrayList<Rating>();
        ratings.add(new Rating(5, "Novigrad Centrale"));
        ratings.add(new Rating(3, "Novigrad Centrale"));
        ratings.add(new Rating(4, "Novigrad Centrale"));
        ratings.add(new Rating(5, "Novigrad Centrale"));
        ratings.add(new Rating(3, "Novigrad Centrale"));
        ratings.add(new Rating(1, "Novigrad Centrale"));
        String averageString = Rating.getAverageRatingsAsString(ratings);
        assertEquals("3.5", averageString);
    }
}