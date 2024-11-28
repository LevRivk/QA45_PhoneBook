package pages;

import dto.UserDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,10),this);

    }
    @FindBy(xpath = "//input[@name='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@name='password']")
    WebElement inputPassword;
    @FindBy(xpath = "//button[text()='Login']")
    WebElement btnLogin;
    @FindBy(xpath = "//button[text()='Registration']")
    WebElement btnRegistration;







    public void typeLoginForm(String email,String password){
        inputEmail.sendKeys(email);

        inputPassword.clear();
        inputPassword.sendKeys(password);

        btnLogin.click();

    }

    public void typeLoginForm(UserDto user){
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());
        btnLogin.click();

    }

    public void typeRegistrationForm (String email,String password){
        inputEmail.sendKeys(email);

        inputPassword.clear();
        inputPassword.sendKeys(password);
        btnRegistration.click();







    }

}
