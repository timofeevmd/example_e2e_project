package Objects.DropDown;

import Objects.ModalWindows.Attachments;
import Objects.ModalWindows.InvoiceDetail;
import Objects.PageObject;
import Objects.Pages.EnrichmentPage;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropDownAction extends PageObject {
    private WebDriver driver;
    private String actionMenuTag = "//div[@name='action-menu-invoices'][contains(@class,'show')]";
    private String actionsTag = "//button[@class='dropdown-item']";
    Map<String, WebElement> actionButtons = new HashMap<>();

    public DropDownAction(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initializationDropDownActionMenu();
    }

    private void initializationDropDownActionMenu() {
        List<WebElement> actions = driver.findElements(By.xpath(actionMenuTag + actionsTag));
        for (WebElement action: actions) {
         //TODO: реализовать EnrichmentPage
         actionButtons.put(action.getText(), action);
        }
    }

    public Object actionBy(String action) throws Exception {
        Click(actionButtons.get(action));
        switch (action){
            case "Process Invoice" :
                return new EnrichmentPage(driver);
            case "Reject Invoice" :
            case "Delete Invoice" :
            case "Create Order file":
                return new PowerInvoicePage(driver);
            case "View Invoice Details" :
                return new InvoiceDetail(driver);
            case "View Attachments" :
                return new Attachments(driver);
        }
        return this;
    }
}
