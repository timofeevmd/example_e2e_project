package Objects.ModalWindows;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceDetail extends PageObject {
    private WebDriver driver;
    private final String invoiceDetailModalWindowTag= "//div[@class='modal-content']";
    private final String closeButtonTag= "//button[@class='close']";

    //General tab
    private final String generalTabTag= "//div/button[contains(@class,'btn')][1]";
    private final String invoiceNoTag = "//input[@name='InvoiceNumber']";
    private final String poNoTag = "//input[@name='PoNumber']";
    private final String invoiceInformationTag = "//div[contains(@class,'col-sm-auto text-align-end')]";
    private final String actualSectionTag = "//div//div[2]";
    private final String invoiceDateTag = "//input[@aria-label='Date']";
    private final String notesTag = "//textarea[@name='Note']";

    //Items tab
    private final String invoiceTableRowTag = "//table[@id='po-table']//tbody[1]/tr";
    private final String poTableRowTag = "//table[@id='po-table']//tbody[@class='po-body']/tr";

    public Map<String, String> generalInvoiceInformation = new HashMap<>();
    public List<WebElement> invoiceTableRows;
    public List<WebElement> poTableRows;


    public InvoiceDetail(WebDriver driver) throws Exception {
        super(driver);
        this.driver = driver;
        openGeneralSection();
        initializationInvoiceDetailModalWindow();
    }

    private void initializationInvoiceDetailModalWindow() {
      if(driver.findElement(By.xpath(invoiceDetailModalWindowTag)).isDisplayed()){
          generalInvoiceInformation.put("Invoice No.", driver.findElement(By.xpath(invoiceDetailModalWindowTag + invoiceNoTag)).getAttribute("value"));
          generalInvoiceInformation.put("PO No.", driver.findElement(By.xpath(invoiceDetailModalWindowTag + poNoTag)).getAttribute("value"));
          generalInvoiceInformation.put("Invoice Date", driver.findElements(By.xpath(invoiceDetailModalWindowTag + invoiceDateTag)).get(0).getAttribute("value"));
          generalInvoiceInformation.put("Notes", driver.findElement(By.xpath(invoiceDetailModalWindowTag + notesTag)).getText());
      }
      invoiceTableRows = driver.findElements(By.xpath(invoiceDetailModalWindowTag + invoiceTableRowTag));
      poTableRows = driver.findElements(By.xpath(invoiceDetailModalWindowTag + poTableRowTag));
    }

    public PowerInvoicePage closeDetailModalWindow() throws Exception {
        Click(invoiceDetailModalWindowTag + closeButtonTag);
        return new PowerInvoicePage(driver);
    }

    public void openGeneralSection() throws Exception {
        Click(invoiceDetailModalWindowTag + generalTabTag);
        WaitingInMilliseconds(1500);
    }


}
