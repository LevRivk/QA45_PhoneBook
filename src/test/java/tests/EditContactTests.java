package tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

public class EditContactTests extends ApplicationManager {


    SoftAssert softAssert = new SoftAssert();

    UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");
    ContactsPage contactsPage;
    @BeforeMethod
    public void login(){
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
       contactsPage = new ContactsPage(getDriver());

    }
    @Test
    public void editContactPositiveTest(){

    }
}
