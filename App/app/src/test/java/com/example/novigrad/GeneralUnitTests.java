package com.example.novigrad;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.novigrad.user.UserAccount;
import com.example.novigrad.user.AdminAccount;
import com.example.novigrad.user.ClientAccount;
import com.example.novigrad.user.EmployeeAccount;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GeneralUnitTests  {

    @Test
    //  create client account
    public void testClientAccount (){

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

    @Test
    //  create  test EmployeeAccount
    public  void  testEmployeeAccount (){

        EmployeeAccount user = new  EmployeeAccount(7869 , "Karine", "Lahaie","Karine1515","Karine35") ;
        assertEquals(user.getId(),7869);
        assertEquals(user.getPrenom(),"Karine");
        assertEquals(user.getNomDeFamille(),"Lahaie");
        assertEquals(user.getNomDeUtiliseur(),"Karine1515");
        assertEquals(user.getMotDePasse(),"Karine35");

    }

    @Test
    // create service test
    public void testServiceCreation() {
        Service service = new Service("Driver's Test", "Measures your driving aptitude", "Driver's License");
        assertEquals(service.getNomService(), "Driver's Test");
        assertEquals(service.getDocsRequis(), "Driver's License");
        assertEquals(service.getInfosRequises(), "Measures your driving aptitude");
    }

    @Test
    // test name verification for services
    public void testServiceVerification() {
        Service valid = new Service("Driver's Test",
                "Measures your driving aptitude", "Driver's License");
        Service invalid = new Service("Dr1ver's T3st",
                "Measures your driving aptitude", "Driver's License");
        assertEquals(Service.verifyService(valid, new String[1]), true);
        assertEquals(Service.verifyService(invalid, new String[1]), false);
    }

}