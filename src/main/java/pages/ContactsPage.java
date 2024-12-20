package pages;

import dto.ContactDtoLombok;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ContactsPage extends BasePage {

    public ContactsPage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,10),this);

    }
    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;
    @FindBy(xpath = "//a[text()='ADD']")
    WebElement btnAdd;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div/div[last()]")
    WebElement lastElementContactList;

    public boolean validatelastElementContactList(ContactDtoLombok contact){
        System.out.println(lastElementContactList.getText());
        return (lastElementContactList.getText().contains(contact.getName()));

    }

    public void clickBtnSignOut (){
        pause(3);
        btnSignOut.click();

    }

    public boolean isSigbOutPresent(){
        pause(3);
       return btnSignOut.isDisplayed();
    }

    public void clickBtnAdd(){
        btnAdd.click();
    }


}
