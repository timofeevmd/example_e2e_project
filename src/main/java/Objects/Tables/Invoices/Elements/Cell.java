package Objects.Tables.Invoices.Elements;

import Objects.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Cell extends PageObject {
    private String tableTag = "//table[@id='po-table']";
    private String rowsTag = "//tbody/tr[@role='row']";
    private String cellsTag = "//td[@role='cell']";
    private String headerAssignedToFilterTag = "//div[@id='selectAssignedToDropdown']//button[contains(@class,'btn-secondary')]";
    private String headerSelectFilterTag = "//div[@id='selectStatusDropdown']//button[contains(@class,'btn-secondary')]";
    private String headerTagFilterTag = "//div[@id='selectTagDropdown']//button[contains(@class,'btn-secondary')]";
    private String cellTextByAssignedToTag = "//div[@id='assignedTo0']//span";
    private String statusTag = "//div//span[2]";
    private String tagNameTag = "//span";
    private WebDriver driver;
    public WebElement selfElement;
    public WebElement cellValue;
    public String cellText;
    public String headerCellText;
    public WebElement headerButton;

    public Cell(WebDriver driver, WebElement selfElement, int count) {
        super(driver);
        this.driver = driver;
        this.selfElement = selfElement;
        initializationInputCell(count);
    }

    private void initializationInputCell(int count) {
        List<WebElement> headerCellsText = driver.findElements(By.xpath("//thead//th[@role='columnheader']"));
        switch (count){
            case 0 : // input fields
            case 1 : // input fields
                headerCellText = headerCellsText.get(count).getText();
                headerButton = selfElement.findElement(By.xpath("//span//span"));
                cellText = selfElement.getAttribute("value");
                cellValue = selfElement;
                break;
            case 11 : // input without ordering
                headerCellText = headerCellsText.get(count).getText();
                cellText = selfElement.getAttribute("value");
                cellValue = selfElement;
                break;
            case 16 : // filtering fields
                headerCellText = headerCellsText.get(count).getText();
                headerButton = headerCellsText.get(count).findElement(By.xpath(headerAssignedToFilterTag));
                cellText = selfElement.findElement(By.xpath(cellTextByAssignedToTag)).getText();
                cellValue = selfElement;
                break;
            case 17 : // filtering fields "Status"
                headerCellText = headerCellsText.get(count).getText();
                headerButton = headerCellsText.get(count).findElement(By.xpath(headerSelectFilterTag));
                cellText = driver.findElement(By.xpath(cellsTag + statusTag)).getText();
                cellValue = selfElement;
                break;
            case 18 : // filtering fields "Tag"
                headerCellText = headerCellsText.get(count).getText();
                headerButton = headerCellsText.get(count).findElement(By.xpath(headerTagFilterTag));
                cellText = driver.findElements(By.xpath(cellsTag + tagNameTag)).get(6).getText();
                cellValue = selfElement;
                break;
            case 8 :  // simple text without ordering
            case 26 : // simple text without ordering
            case 27 : // simple text without ordering
                headerCellText = headerCellsText.get(count).getText();
                cellText = selfElement.getText();
                cellValue = selfElement;
                break;
            default: // simple text with ordering
                headerCellText = headerCellsText.get(count).getText(); // simple text with ordering
                headerButton = selfElement.findElement(By.xpath("//span//span"));
                cellText = selfElement.getText();
                cellValue = selfElement;
                break;
        }
    }
}
