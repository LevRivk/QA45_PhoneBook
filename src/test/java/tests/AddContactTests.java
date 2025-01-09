package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TakeScreenShot;
import utils.TestNGListener;
import static utils.PropertiesReader.*;
import static utils.TakeScreenShot.*;

@Listeners(TestNGListener.class)

public class AddContactTests extends ApplicationManager {

    SoftAssert softAssert = new SoftAssert();

   // UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");

    UserDto user = new UserDto(getProperty("login.properties", "email"),
    getProperty("login.properties","password"));
    AddPage addPage;

    @BeforeMethod
    public void login(){
new HomePage(getDriver()).clickBtnLoginHeader();
new LoginPage(getDriver()).typeLoginForm(user);
new ContactsPage(getDriver()).clickBtnAdd();
addPage = new AddPage(getDriver());
    }
    @Test(invocationCount = 1)

    public void addNewContactPositiveTest(){
        // для ЛОггера
        logger.info("addNewContactPositiveTest with data ----> " + ".description" +
                ".address" +
                " .email" +
                ".phone" +
                " .lastName" +
                " .name");
        // для ЛОггера
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .description("description")
                .address("adress st.1")
                .email("lastnae@gmail.com")
                .phone("1234567890")
                .lastName("MyLast name")
                .name("MyName")
                .build();
        addPage.typeContactForm(contact);
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(new ContactsPage(getDriver()).validatelastElementContactList(contact));

    }
    @Test
    public void addNewContactNegativeTest_emptyName(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .description("description")
                .address("adress st.1")
                .email("lastnae@gmail.com")
                .phone("1234567890")
                .lastName("MyLast name")
                .name("")
                .build();
        addPage.typeContactForm(contact);
        Assert.assertFalse(addPage.validateUrlContacts());
    }
    @Test
    public void addNewContactNegativeTest_wrongPhone(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .description("description")
                .address("adress st.1")
                .email("lastnae@gmail.com")
                .phone("1qlkjkj")
                .lastName("MyLast name")
                .name("name 123")
                .build();
        addPage.typeContactForm(contact);

        String message=addPage.closeAlertAndReturnText();
        System.out.println(message);
       softAssert.assertTrue(message.contains("Phone number must contain only digits! And length min 10, max 15!"));
        System.out.println("code after assert");

       softAssert.assertFalse(addPage.validateUrlContacts());

        softAssert.assertAll();
    }
    @Test
    public void addNewContactNegativeTest_wrongEmail(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .description("description")
                .address("adress st.1")
                .email("lastname")
                .phone("1234567890")
                .lastName("MyLast name")
                .name("name 123")
                .build();
        addPage.typeContactForm(contact);

        String message=addPage.closeAlertAndReturnText();
        System.out.println(message);
        softAssert.assertTrue(message.contains("Email not valid"));
        System.out.println("code after assert");

        softAssert.assertFalse(addPage.validateUrlContacts());

        softAssert.assertAll();
    }
}
