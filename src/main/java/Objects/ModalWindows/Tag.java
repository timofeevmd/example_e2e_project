package Objects.ModalWindows;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Tag extends PageObject {
    private WebDriver driver;
    private String tagTag = "//div[contains(@class,'custom-radio')]";
    private String lableRadioButtonTag = "//label[@for='tag1']";
    private String okayButtonTag = "//div[@class='modal-footer']/button[@class='btn modal-primary-btn']";

    public Tag(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public Tag markTag(int tagIndex) throws Exception {
        Click("//label[@for='tag" + tagIndex + "']");
        return new Tag(driver);
    }

    public PowerInvoicePage okayButton() throws Exception {
        Click(okayButtonTag);
        return new PowerInvoicePage(driver);
    }
}
