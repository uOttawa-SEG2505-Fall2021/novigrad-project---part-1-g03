package com.example.novigrad;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.novigrad.user.UserAccount;
import com.example.novigrad.user.AdminAccount;
import com.example.novigrad.user.ClientAccount;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest  {

    @Test
    //  create client account
    public void testClienAccount (){

        ClientAccount user = new  ClientAccount( 555, "Leonel","Messi","Messi06","seg2505") ;
        assertEquals(user.getId(),555);
        assertEquals(user.getPrenom(),"Leonel");
        assertEquals(user.getNomDeFamille(),"Messi");
        assertEquals(user.getNomDeUtiliseur(),"Messi06");
        assertEquals(user.getMotDePasse(),"seg2505");

    }

    @Test
    //  test user acount
    public  void  testUserAccount (){

        UserAccount user = new UserAccount(1 , 123, "Benoi","Bolt","Benoi05","matlab") ;
        assertEquals(user.getAccountType(),1);
        assertEquals(user.getId(),123);
        assertEquals(user.getPrenom(),"Benoi");
        assertEquals(user.getNomDeFamille(),"Bolt");
        assertEquals(user.getNomDeUtiliseur(),"Benoi05");
        assertEquals(user.getMotDePasse(),"matlab");

    }

    @Test
    // tes admin Account
    public  void  testAdminAccount (){
        //  create a user
        AdminAccount user = new  AdminAccount(2 , "Jasque", "Trudeau","Jasque47","password") ;
        assertEquals(user.getId(),2);
        assertEquals(user.getPrenom(),"Jasque");
        assertEquals(user.getNomDeFamille(),"Trudeau");
        assertEquals(user.getNomDeUtiliseur(),"Jasque47");
        assertEquals(user.getMotDePasse(),"password");

    }




}