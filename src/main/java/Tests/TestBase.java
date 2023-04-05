package Tests;

import Objects.DropDown.DropDownUserMenu;
import Objects.ModalWindows.ChooseApplication;
import Objects.Pages.LoginPage;
import Objects.Pages.PowerInvoicePage;
import Tests.Setting.Config.ConfigurationProperties;
import Tests.Setting.Driver.Drivers;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class TestBase extends Drivers{
    protected WebDriver driver;
    private static LoginPage loginPage;
    private static ChooseApplication chooseApplication;
    private static PowerInvoicePage powerInvoicePage;
    private static DropDownUserMenu dropDownUserMenu;

    @BeforeTest
    @Parameters({"browser", "mode"})
    public void setUpSuite (String browser, String mode) {
        this.driver = getDriver(browser, mode);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @BeforeMethod
    @Parameters({"userLogIn", "userPassword"})
    public void setUpTest(String userLogIn, String userPassword) throws Exception {
        driver.get("https://invoices-stage.matmgt.com/login");
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(ConfigurationProperties.getProperty(userLogIn))
                .inputPassword(ConfigurationProperties.getProperty(userPassword))
                .clickButton();

        chooseApplication = new ChooseApplication(driver);
        chooseApplication.clickButton("powerInvoice");
    }

    @AfterMethod
    public void signOutTest() throws Exception {
        powerInvoicePage = new PowerInvoicePage(driver);
        dropDownUserMenu = new DropDownUserMenu(driver);

        powerInvoicePage.userMenu().signOut();
    }

    @AfterTest
    public void signOutSuite(){
        driver.close();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
