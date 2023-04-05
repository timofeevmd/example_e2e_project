package Objects.DropDown;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagDropDownMenu extends PageObject {
    private WebDriver driver;
    private String dropDownMenuTag = "//div[@role='menu'][@class='dropdown-menu show']";
    private String tagsTag = "//label[@class='custom-control-label']";
    private String okButtonTag = "//h6//button[1]";
    private String resetButtonTag = "//h6//button[2]";
    private Map<String, WebElement> tagsMap = new HashMap<>();

    public TagDropDownMenu(WebDriver driver) {
        super(driver);
        this.driver = driver;
        initialization();
    }

    private void initialization() {
        List<WebElement> labelTagList = driver.findElements(By.xpath(dropDownMenuTag + tagsTag));
        for (int i = 0; i < labelTagList.size(); i++) {
            tagsMap.put(labelTagList.get(i).getText(), labelTagList.get(i));
        }
    }

    public TagDropDownMenu markCheckBoxes(String tag) throws Exception {
        Click(tagsMap.get(tag));
        return new TagDropDownMenu(driver);
    }

    public PowerInvoicePage okButton() throws Exception {
        Click(dropDownMenuTag + okButtonTag);
        return new PowerInvoicePage(driver);
    }

    public PowerInvoicePage resetButton() throws Exception {
        Click(dropDownMenuTag + resetButtonTag);
        return new PowerInvoicePage(driver);
    }
}
