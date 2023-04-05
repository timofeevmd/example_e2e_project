package Objects.Tables.Invoices.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

public class InputCell {
    private String tableTag = "//table[@id='po-table']";
    private String rowsTag = "//tbody/tr[@role='row']";
    private String cellsTag = "//td[@role='cell']";
    private int indexRow;
    private WebDriver driver;
    public WebElement selfElement;
    public String tagText;
    public String text;

    public InputCell(WebDriver driver, WebElement selfElement, int indexRow, String tag, boolean needTagText) {
        this.driver = driver;
        this.selfElement = selfElement;
        this.indexRow = indexRow;
        getText(indexRow, tag);
        if (needTagText)
            getTagText(indexRow, tag);
    }

    private String getText(int indexRow, String tag) {
        return text = driver.findElement(By.xpath(tableTag + rowsTag + "[" + indexRow + "]" + tag)).getText();
    }

    @Nullable
    public String getTagText(int indexRow, String tag) {
        List<WebElement> elements = driver.findElements(By.xpath(tableTag + rowsTag + "[" + indexRow + "]" + cellsTag + tag));
        tagText = elements.get(6).getText();
        return tagText;
    }
}
