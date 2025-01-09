package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import static utils.PropertiesReader.*; //для нашего проперти

public class HomePage extends BasePage {
    public HomePage(WebDriver driver){
        setDriver(driver);
     //  driver.get("https://telranedu.web.app/home");
        driver.get(getProperty("login.properties","urlStart"));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,10),this);

    }
    @FindBy(xpath = "//a[text()='LOGIN']")// =
    WebElement btnLogin; // = WebElement btnLogin = driver.finsElement(By.xpath(//a[text()='LOGIN']"));
    public void clickBtnLoginHeader(){
        btnLogin.click();
        //pause(3);
    }

}
