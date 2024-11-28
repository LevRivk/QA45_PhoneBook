package tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.util.Random;

public class RegistrationTests extends ApplicationManager {
    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000);
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeRegistrationForm("frodo_baggins"+i+"@gmail.com","My_password123!");






    }
}
