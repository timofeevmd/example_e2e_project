package Objects.Tables.Invoices.Elements;

import Objects.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderCell extends PageObject {
    private WebDriver driver;

    public WebElement selfElement;
    public String text;
    public WebElement headerValueCell;

    public HeaderCell(WebDriver driver, WebElement selfElement) {
        super(driver);
        this.driver = driver;
        this.selfElement = selfElement;
        initializationHeaderCell();
    }

    private void initializationHeaderCell(){
        text = selfElement.getText();
        headerValueCell = selfElement;
    }
}
