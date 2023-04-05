package Objects.DropDown;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AssignModalWindow extends PageObject {
    private WebDriver driver;
    private String dropDownModalWindowTag = "//span[@id='assigned-to-dropdown']";
    private String inputTag = "//input";
    private String searchButtonTag = "//button[contains(@class,'search-btn')]";
    private String clearButtonTag = "//button[contains(@class,'clear-btn')]";
    private String dropDownOptionTag = "//div[@role='list']//span[@role='option']";

    public AssignModalWindow(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public AssignModalWindow searchByUser(String userName) throws Exception {
        WebElement elementForSend = driver.findElement(By.xpath(dropDownModalWindowTag + inputTag));
        SendKey(elementForSend, userName);
        Click(dropDownOptionTag);
        return new AssignModalWindow(driver);
    }

    public PowerInvoicePage searchButton() throws Exception {
        Click(dropDownModalWindowTag + searchButtonTag);
        return new PowerInvoicePage(driver);
    }

    public PowerInvoicePage clearButton() throws Exception {
        Click(dropDownModalWindowTag + clearButtonTag);
        return new PowerInvoicePage(driver);
    }
}
