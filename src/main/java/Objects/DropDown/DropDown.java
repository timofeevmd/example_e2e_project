package Objects.DropDown;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class DropDown extends PageObject {
    private WebDriver driver;
    private String buttonsTag = "//div[@id='home-table-options']//div[@class='ml-3 btn-group show']//div//button";
    private String branchesNameTag = "//div";
    private String currentApptag = "//span";
    public List<WebElement> menuButton = new ArrayList<>();


    public DropDown(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initializationDropDonwMenu();
    }

    private void initializationDropDonwMenu() {
        menuButton = driver.findElements(By.xpath(buttonsTag));
    }

    public PowerInvoicePage chooseBranch(String branch) throws Exception {
        for (int i = 0; i < menuButton.size(); i++) {
            String[] currentBranch = driver.findElements(By.xpath(buttonsTag + branchesNameTag)).get(i).getText().split(" -");
            if(currentBranch[0].equals(branch)){
                Click(driver.findElements(By.xpath(buttonsTag + branchesNameTag)).get(i));
                break;
            }
        }
        return new PowerInvoicePage(driver);
    }

}
