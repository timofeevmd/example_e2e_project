package Objects.DropDown;

import Objects.PageObject;
import Objects.Pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class DropDownUserMenu extends PageObject {
    private WebDriver driver;
    private String logOut = "//button[@id='logoutButton']";
    private String sessionExpired = "//div[@class='modal-content']";
    private String goToLogInPageButton = "//button[contains(@class,'header-buttons')]";

    public DropDownUserMenu(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public LoginPage signOut() throws Exception {
        Click(logOut);
        try{
            if (driver.findElement(By.xpath(sessionExpired)).isDisplayed()){
                Click(goToLogInPageButton);
            }
        } catch (NoSuchElementException exception){
            exception.getMessage();
        }
        return new LoginPage(driver);
    }
}
