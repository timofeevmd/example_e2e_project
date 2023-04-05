package Objects.DropDown;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusDropDownMenu extends PageObject {
    private WebDriver driver;
    private String statusItemTag = "//div[@id='selectStatusDropdown']//button[@class='dropdown-item']";
    private String statusItemLabelTag = "//label";
    private String okButtonTag = "//div[@id='selectStatusDropdown']//h6//button";
    private Map<String, WebElement> checkBoxesMap = new HashMap<>();

    public StatusDropDownMenu(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initialization();
    }

    private void initialization() {
        int count;
        List<WebElement> checkBoxes = driver.findElements(By.xpath(statusItemTag));
        for (int i = 0; i < checkBoxes.size(); i++) {
            count = i + 1;
            WebElement element = driver.findElement(By.xpath(statusItemTag + "[" + count + "]" + statusItemLabelTag));
            checkBoxesMap.put(element.getText(), element);
        }
    }

    public StatusDropDownMenu unMarkAllCheckBoxes() throws Exception {
        for (Map.Entry<String, WebElement> elementEntry: checkBoxesMap.entrySet()) {
            String key = elementEntry.getKey();
            WebElement value = elementEntry.getValue();
            if (key.equals("ALL")) {
                Click(value);
                Click(value);
                break;
            }
        }
        return new StatusDropDownMenu(driver);
    }

    public StatusDropDownMenu markCheckBoxes(String status) throws Exception {
        Click(checkBoxesMap.get(status));
        return new StatusDropDownMenu(driver);
    }

    public PowerInvoicePage okButton() throws Exception {
        Click(okButtonTag);
        return new PowerInvoicePage(driver);
    }

}
