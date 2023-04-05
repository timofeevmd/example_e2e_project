package Objects.Tables.Invoices;

import Objects.DropDown.StatusDropDownMenu;
import Objects.PageObject;
import Objects.Tables.Invoices.Elements.HeaderCell;
import Objects.Tables.Invoices.Rows.InvoicesTableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class InvoicesTable extends PageObject {
    private WebDriver driver;

    private String tableLoaderTag = "//div[contains(@class,'d-flex table-loader')]";
    private String rowsTag = "//tbody/tr[@role='row']";
    private String headerOrderingCell = "//thead//th[@role='columnheader'][@title='Toggle SortBy']";
    private String dropDownStatusTag = "//button[@aria-haspopup='true']";
    public Map<String, WebElement> invoicesTableOrderingHeaderCells = new HashMap<>();
    public Map<Integer, InvoicesTableRow> rows = new HashMap<>();

    public InvoicesTable(WebDriver driver, boolean tag) throws Exception {
        super(driver);
        this.driver = driver;
        waitInvisibilityOfElementLocated(tableLoaderTag);
        initializationHeaderCell();
        InitializationMapRows(tag);
    }

    private Object InitializationMapRows (boolean tag) throws Exception {
        List<WebElement> rows = driver.findElements(By.xpath(rowsTag));
        if(rows.size() == 0){
            return new Exception("nothing to do");
        }else {
            for (int i = 0; i < rows.size(); i++) {
                int rowCount = i + 1;
                this.rows.put(rowCount, new InvoicesTableRow(driver, rowCount, tag));
            }
        }
        return this;
    }

    private Object initializationHeaderCell(){
        List<WebElement> headerValueCells = driver.findElements(By.xpath(headerOrderingCell));
        if (headerValueCells.size() == 0)
            return new Exception("nothing to do");
        else {
            for (int i = 0; i < headerValueCells.size(); i++) {
                HeaderCell element = new HeaderCell(driver, headerValueCells.get(i));
                invoicesTableOrderingHeaderCells.put(element.text, element.selfElement);
            }
        }
        return this;
    }

    public StatusDropDownMenu dropDownStatuses() throws Exception {
        Click(driver.findElement(By.xpath(headerOrderingCell + dropDownStatusTag)));
        return new StatusDropDownMenu(driver);
    }

    public void ordering(String key) throws Exception {
        WebElement element = invoicesTableOrderingHeaderCells.get(key);
        Click(element);
    }

    public void markRows(int countRows) throws Exception {
        for (int i = 1; i <= countRows; i++) {
            WebElement rowForMark = rows.get(i).cells.get("Supplier Number").selfElement;
            Click(rowForMark);
        }
    }

    public void markRow(int countRows) throws Exception {
       WebElement rowForMark = rows.get(countRows).cells.get("Supplier Number").selfElement;
       Click(rowForMark);
    }

    public void markRowsBySuite(int start, int end) throws Exception {
        for (int i = start; i <= end; i++) {
            WebElement rowForMark = rows.get(i).cells.get("Supplier Number").selfElement;
            Click(rowForMark);
        }
    }
}
