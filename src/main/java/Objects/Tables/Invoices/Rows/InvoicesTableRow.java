package Objects.Tables.Invoices.Rows;

import Objects.DropDown.DropDownAction;
import Objects.PageObject;
import Objects.Tables.Invoices.Elements.Cell;
import Objects.Tables.Invoices.Elements.HeaderCell;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoicesTableRow extends PageObject {
    private WebDriver driver;
    private String headerOrderingCell = "//thead//th[@role='columnheader'][@title='Toggle SortBy']";
    private String rowsTag = "//tbody/tr[@role='row']";
    private String cellsTag = "//td[@role='cell']";
    private String cellsValueInvoiceNoTag = "//input[@name='SUPPLIER_INVOICE_NUMBER']";
    private String cellsValuePoNoTag = "//input[@name='PO_NUMBER']";
    private String cellsValueNoteTag = "//input[@name='Note']";
    private String actionButtonTag = "//button[contains(@class, 'actions')]";
    private String dropDownTag = "//div[@name='action-menu-invoices'][contains(@class,'show')]";
    private String cellsTagTag = "//span";
    private String cellsAssignedToTag = "//span";
    public Map<String, WebElement> invoicesTableOrderingHeaderCells = new HashMap<>();
    public Map<String, Cell> cells = new HashMap<>();
    private int cellsStartWithColumns = 28;

    public InvoicesTableRow(WebDriver driver, int rowCount, boolean tag) throws Exception {
        super(driver);
        this.driver = driver;
        initializationHeaderCell();
        initializationTableCell(rowCount, tag);
    }

    private Map<String, WebElement> initializationHeaderCell(){
        List<WebElement> headerValueCells = driver.findElements(By.xpath(headerOrderingCell));
        for (int i = 0; i < headerValueCells.size(); i++) {
            HeaderCell element =  new HeaderCell(driver, headerValueCells.get(i));
            invoicesTableOrderingHeaderCells.put(element.text, element.selfElement);
        }
        return invoicesTableOrderingHeaderCells;
    }

    private Map<String, Cell> initializationTableCell(int rowCount, boolean tag){
        for (int i = 0; i < cellsStartWithColumns; i++) {
            switch (i){
                case 0 :
                    WebElement cellInvoiceNoElement = driver.findElement(By.xpath(rowsTag + "[" + rowCount + "]" + cellsValueInvoiceNoTag));
                    Cell invoiceNoCell = new Cell(driver, cellInvoiceNoElement, i);
                    cells.put(invoiceNoCell.headerCellText, invoiceNoCell);
                    break;
                case 1 :
                    WebElement cellPoNoElement = driver.findElement(By.xpath(rowsTag + "[" + rowCount + "]" + cellsValuePoNoTag));
                    Cell poNoCell = new Cell(driver, cellPoNoElement, i);
                    cells.put(poNoCell.headerCellText, poNoCell);
                    break;
                case 11 :
                    WebElement noteElement = driver.findElement(By.xpath(rowsTag + "[" + rowCount + "]" + cellsValueNoteTag));
                    Cell noteCell = new Cell(driver, noteElement, i);
                    cells.put(noteCell.headerCellText, noteCell);
                    break;
                case 16 :
                    WebElement assignedToElement = driver.findElement(By.xpath(rowsTag + "[" + rowCount + "]" + cellsAssignedToTag));
                    Cell assignedTo = new Cell(driver, assignedToElement, i);
                    cells.put(assignedTo.headerCellText, assignedTo);
                    break;
                case 18 :
                    if (tag == true){
                        WebElement tagElement = driver.findElement(By.xpath(rowsTag + "[" + rowCount + "]" + cellsTagTag));
                        Cell tagCell = new Cell(driver, tagElement, i);
                        cells.put(tagCell.headerCellText, tagCell);
                    }
                    break;
                case 27 :
                    WebElement currentActionButton = driver.findElement(By.xpath(rowsTag + "[" + rowCount + "]" + actionButtonTag));
                    Cell actionCell = new Cell(driver, currentActionButton, i);
                    cells.put(actionCell.headerCellText, actionCell);
                    break;
                default:
                    int cellCount = i + 1;
                    WebElement currentCell = driver.findElement(By.xpath(rowsTag + "[" + rowCount + "]" + cellsTag + "[" + cellCount + "]"));
                    Cell cell = new Cell(driver, currentCell, i);
                    cells.put(cell.headerCellText, cell);
                    break;
            }
        }
        return cells;
    }

    public void scrollTableToElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public DropDownAction getRowAction() throws Exception {
        Click(cells.get("Actions").selfElement);
        return new DropDownAction(driver);
    }
}
