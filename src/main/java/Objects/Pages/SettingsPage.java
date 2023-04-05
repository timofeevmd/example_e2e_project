package Objects.Pages;

import Objects.PageObject;
import Objects.Lists.Settings.Branches;
import Objects.Lists.Settings.Tabs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsPage extends PageObject {
    private WebDriver driver;

    // Manege tab
    protected final String manegeTag = "//div[@class='home-body']/div[2]";
    protected final String createTabButtonTag = "//div//button[contains(@class,'header-buttons')]";
    protected final String existTabsTag = "//ul/div/span";
    protected final String editActionTabsTag = "//ul/div/div/span[1]";
    protected final String deleteActionTabsTag = "//ul/div/div/span[2]";

    //Manage branches
    protected final String menageBranchesTag = "//div[@class='home-body']/div[4]";
    protected final String existBranchesNameTag = "//ul/div/span";
    protected final String apRadioButtonsTag = "//ul/div//div[contains(@class,'pr-3')]//label";
    protected final String piRadioButtonsTag = "//ul/div//div[2]//label";

    public Map<String, Tabs> tabs = new HashMap<>();
    public Map<String, Branches> branches = new HashMap<>();

    public SettingsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initialisationManegeTab();
        initialisationManegeBranch();
    }

    private void initialisationManegeBranch() {
        List<WebElement> nameTabs = driver.findElements(By.xpath(menageBranchesTag + existBranchesNameTag));
        List<WebElement> editTabs = driver.findElements(By.xpath(menageBranchesTag + apRadioButtonsTag));
        List<WebElement> deleteTabs = driver.findElements(By.xpath(menageBranchesTag + piRadioButtonsTag));

        for (int i = 0; i < nameTabs.size(); i++) {
            branches.put(nameTabs.get(i).getText(), new Branches(driver, nameTabs.get(i).getText(), editTabs.get(i), deleteTabs.get(i)));
        }
    }

    private void initialisationManegeTab() {
        List<WebElement> nameTabs = driver.findElements(By.xpath(manegeTag + existTabsTag));
        List<WebElement> editTabs = driver.findElements(By.xpath(manegeTag + editActionTabsTag));
        List<WebElement> deleteTabs = driver.findElements(By.xpath(manegeTag + deleteActionTabsTag));

        for (int i = 0; i < 4; i++) {
            tabs.put(nameTabs.get(i).getText(), new Tabs(driver, nameTabs.get(i).getText()));
        }
        for (int i = 4, j = 0; i < nameTabs.size(); i++, j++) {
            tabs.put(nameTabs.get(i).getText(), new Tabs(driver, nameTabs.get(i).getText(), editTabs.get(j), deleteTabs.get(j)));
        }
    }

    public SettingsPage createNewTab() throws Exception {
        WebElement createButton = driver.findElement(By.xpath(createTabButtonTag));
        Click(createButton);
        return new SettingsPage(driver);
    }


}
