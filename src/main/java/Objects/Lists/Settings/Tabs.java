package Objects.Lists.Settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Tabs {
    private WebDriver driver;
    public String tabText;
    public WebElement edit;
    public WebElement delete;

    public Tabs(WebDriver driver, String tabText, WebElement editSelfElement, WebElement deleteSelfElement) {
        this.driver = driver;
        initialisationAction(tabText, editSelfElement, deleteSelfElement);
    }

    public Tabs(WebDriver driver, String tabText) {
        this.driver = driver;
        initialisationTab(tabText);
    }

    private void initialisationTab(String tabText) {
        this.tabText = tabText;
    }

    private void initialisationAction(String tabText, WebElement editSelfElement, WebElement deleteSelfElement) {
        this.tabText = tabText;
        this.edit = editSelfElement;
        this.delete = deleteSelfElement;
    }


}
