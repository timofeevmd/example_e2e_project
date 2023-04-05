package Objects.ModalWindows;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Attachments extends PageObject {
    private WebDriver driver;
    private String modalWindowTag = "//div[@class='modal-content']";
    private String titleTag = "//h5";
    private String closeButtonTag = "//button[@class='close']";
    private WebElement modalWindow;
    private WebElement closeButton;
    public WebElement title;

    public Attachments(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initializationModalWindow();
    }

    private void initializationModalWindow() {
        modalWindow = driver.findElement(By.xpath(modalWindowTag));
        closeButton = modalWindow.findElement(By.xpath(closeButtonTag));
        title = modalWindow.findElement(By.xpath(titleTag));
    }

    public PowerInvoicePage closeButton() throws Exception {
        Click(closeButton);
        return new PowerInvoicePage(driver);
    }
}
