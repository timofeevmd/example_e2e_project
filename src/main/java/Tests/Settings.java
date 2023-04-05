package Tests;

import Objects.DropDown.DropDownUserMenu;
import Objects.ModalWindows.ChooseApplication;
import Objects.Pages.LoginPage;
import Objects.Pages.PowerInvoicePage;
import Objects.Pages.SettingsPage;
import Tests.Setting.Config.ConfigurationProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Settings extends TestBase{
    private static WebDriver driver;
    private static PowerInvoicePage powerInvoicePage;

    private String[] defaultTubs = new String[]{"All", "Main", "Rejected", "Deleted"};

    @Test
    public void existTab() throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);

        SettingsPage settingsPage = powerInvoicePage
                .chooseSettingsPage();

        for (int i = 0; i < defaultTubs.length; i++) {
            Assert.assertEquals(settingsPage.tabs.get(defaultTubs[i]).tabText, defaultTubs[i]);
        }
    }

    @Test
    public void createTab() throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);

        SettingsPage settingsPage = powerInvoicePage
                .chooseSettingsPage();

        settingsPage.createNewTab();

        SettingsPage changedSettingsPage = powerInvoicePage
                .chooseSettingsPage();

        Assert.assertNotEquals(settingsPage.tabs.size(), changedSettingsPage.tabs.size());
    }
}
