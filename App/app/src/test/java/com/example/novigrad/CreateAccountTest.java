package com.example.novigrad;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.novigrad.user.AdminAccount;
import com.example.novigrad.user.ClientAccount;
import com.example.novigrad.user.EmployeeAccount;

/**
 * Tests unitaires pour la création de compte admin, employé et client
 */

public class CreateAccountTest {

    @Test
    //  test create client account
    public void testClienAccount (){

        ClientAccount user = new  ClientAccount( 555, "Leonel","Messi","Messi06","seg2505") ;
        assertEquals(user.getId(),555);
        assertEquals(user.getPrenom(),"Leonel");
        assertEquals(user.getNomDeFamille(),"Messi");
        assertEquals(user.getNomDeUtiliseur(),"Messi06");
        assertEquals(user.getMotDePasse(),"seg2505");

    }

    @Test
    // test create admin Account
    public  void  testAdminAccount (){
        //  create a user
        AdminAccount user = new  AdminAccount(2 , "Jasque", "Trudeau","Jasque47","password") ;
        assertEquals(user.getId(),2);
        assertEquals(user.getPrenom(),"Jasque");
        assertEquals(user.getNomDeFamille(),"Trudeau");
        assertEquals(user.getNomDeUtiliseur(),"Jasque47");
        assertEquals(user.getMotDePasse(),"password");

    }

    @Test
    //  test create Employee Account
    public  void  testEmployeeAccount (){

        EmployeeAccount user = new  EmployeeAccount(7869 , "Karine", "Lahaie","Karine1515","Karine35") ;
        assertEquals(user.getId(),7869);
        assertEquals(user.getPrenom(),"Karine");
        assertEquals(user.getNomDeFamille(),"Lahaie");
        assertEquals(user.getNomDeUtiliseur(),"Karine1515");
        assertEquals(user.getMotDePasse(),"Karine35");

    }

}