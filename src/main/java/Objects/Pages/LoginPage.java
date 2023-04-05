package Objects.Pages;

import Objects.ModalWindows.ChooseApplication;
import Objects.PageObject;
import org.openqa.selenium.WebDriver;

public class LoginPage extends PageObject {
    private WebDriver driver;

    private String loginInput = "//input[@type='text']";
    private String passwordInput = "//input[@type='password']";
    private String logInButton = "//button[@id='login-btn']";

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public ChooseApplication clickButton() throws Exception {
        Click(logInButton);
        return new ChooseApplication(driver);
    }

    public LoginPage inputLogin(String login){
        SendKey(loginInput, login);
        return new LoginPage(driver);
    }

    public LoginPage inputPassword(String password){
        SendKey(passwordInput, password);
        return new LoginPage(driver);
    }

}
