package Objects.Lists.Settings;

import Objects.Pages.SettingsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Branches {
    private WebDriver driver;
    public String tabText;
    public WebElement ap;
    public WebElement pi;

    public Branches(WebDriver driver, String tabText, WebElement apSelfElement, WebElement piSelfElement) {
        this.driver = driver;
        initialisationAction(tabText, apSelfElement, piSelfElement);
    }

    private void initialisationAction(String tabText, WebElement apSelfElement, WebElement piSelfElement) {
        this.tabText = tabText;
        this.ap = apSelfElement;
        this.pi = piSelfElement;
    }
}
