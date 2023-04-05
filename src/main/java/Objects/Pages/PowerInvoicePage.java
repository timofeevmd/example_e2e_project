package Objects.Pages;

import Objects.DropDown.AssignModalWindow;
import Objects.DropDown.DropDown;
import Objects.DropDown.DropDownUserMenu;
import Objects.DropDown.TagDropDownMenu;
import Objects.ModalWindows.Tag;
import Objects.PageObject;
import Objects.Tables.Invoices.InvoicesTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class PowerInvoicePage extends PageObject {
    private WebDriver driver;

    private final String settings = "//a[@id='settingsLink']";
    private final String selectBranch = "//div[@aria-label='Dropdown select'][contains(@class,'branch-options-dropdown')]";
    private final String unSelectedBranch = "//span[@role='option'][@aria-selected='false']";
    private final String userAvatar = "//span[contains(@class,'toggle-dropdown')]";
    private final String namePage = "//h5[@class='mb-0']";
    private final String rejectHeaderButtonTag = "//button[contains(@class,'reject-invoices-btn')]";
    private final String restoreHeaderButtonTag = "//button[@id='restoreInvoicesButton']";
    private final String deleteButtonTag = "//button[@id='deleteInvoicesButton']";
    private final String tagButtonTag = "//button[@title='tag invoices']";
    private final String tableSearchInput = "//input[@id='tableSearchInput']";
    private final String buttonSearch = "//button[@type='submit']";
    private final String dropDownButtonTag = "//div[@id='home-table-options']//div[@class='ml-3 btn-group']//button[contains(@class,'header-buttons')]";
    private final String footerTag = "//div[@id='home-table-pagination']";
    private final String pagesTag = "//span//strong";
    private final String selectorInvoiceOnPageTag = "//select";
    private final String goToPageTag = "//span//input";

    private final String alertTag = "//div[contains(@class,'react-toast-notifications')]";
    private final String alertButtonTag = "//div[@role='button']";

    public static List<String> selectedBranchesName = new ArrayList<>();;

    public PowerInvoicePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public SettingsPage chooseSettingsPage() throws Exception {
        Click(settings);
        return new SettingsPage(driver);
    }

    public PowerInvoicePage closeAlert() throws Exception {
        WaitWebElement(alertTag);
        Click(alertTag + alertButtonTag);
        return new PowerInvoicePage(driver);
    }

    public Object clickByFilterButton(String cellText, WebElement elementForClick) throws Exception {
        switch (cellText){
            case "Assigned To" :
                Click(elementForClick);
                return new AssignModalWindow(driver);
            case "Tag" :
                Click(elementForClick);
                return new TagDropDownMenu(driver);
        }
        return this;
    }

    public PowerInvoicePage viewRecordsOption(int option) throws Exception {
        Click(footerTag + selectorInvoiceOnPageTag);
        Select viewRecordsOption = new Select (driver.findElement(By.xpath(footerTag + selectorInvoiceOnPageTag)));
        switch(option) {
            case (10):
                viewRecordsOption.selectByValue("10");
                break;
            case (20):
                viewRecordsOption.selectByValue("20");
                break;
            case (50):
                viewRecordsOption.selectByValue("50");
                break;
        }
        return new PowerInvoicePage(driver);
    }

    public PowerInvoicePage goToPage(String numberToGo){
        WebElement elementForSend = driver.findElement(By.xpath(footerTag + goToPageTag));
        SendKey(elementForSend, numberToGo);
        return new PowerInvoicePage(driver);
    }

    public Integer totalNumberOfPages(){
        String[] countPage = driver.findElement(By.xpath(footerTag + pagesTag)).getText().split(" of ");
        return Integer.parseInt(countPage[1]);
    }

    public DropDown clickMoveButton(String button) throws Exception {
        switch (button){
            case "Move to Group":
                Click(driver.findElements(By.xpath(dropDownButtonTag)).get(0));
                break;
            case "Move to branch":
                Click(driver.findElements(By.xpath(dropDownButtonTag)).get(1));
                break;
        }
        return new DropDown(driver);
    }

    public PowerInvoicePage clickHeaderButton(String button) throws Exception {
        switch (button){
            case "reject":
                WaitingInMilliseconds(200);
                Click(rejectHeaderButtonTag);
                break;
            case "restore":
                WaitingInMilliseconds(200);
                Click(restoreHeaderButtonTag);
                break;
        }
        return new PowerInvoicePage(driver);
    }

    public PowerInvoicePage clickDeleteButton() throws Exception {
        Click(deleteButtonTag);
        return new PowerInvoicePage(driver);
    }

    public Tag clickTagButton() throws Exception {
        Click(tagButtonTag);
        return new Tag(driver);
    }

    public PowerInvoicePage clickByName() throws Exception {
        Click(namePage);
        return new PowerInvoicePage(driver);
    }

    public InvoicesTable getInvoicesTable(boolean tag) throws Exception {
        if (tag)
            return new InvoicesTable(driver, true);
        else
            return new InvoicesTable(driver, false);
    }

    public DropDownUserMenu userMenu() throws Exception {
        Click(userAvatar);
        return new DropDownUserMenu(driver);
    }

    public PowerInvoicePage selectBranch() throws Exception {
        Click(selectBranch);
        return new PowerInvoicePage(driver);
    }

    public PowerInvoicePage searchInvoice(String invoiceNumber) throws Exception {
        Clear(tableSearchInput);
        SendKey(tableSearchInput, invoiceNumber);
        Click(buttonSearch);
        return new PowerInvoicePage(driver);
    }

    public PowerInvoicePage choiceBranches(String branchName) throws Exception {
        List<WebElement> unSelectedBranches = driver.findElements(By.xpath(unSelectedBranch));
        for (WebElement element: unSelectedBranches) {
            if(element.getAttribute("aria-label").equals(branchName)){
                Click(element);
                selectedBranchesName.add(element.getText());
            }
        }
        return new PowerInvoicePage(driver);
    }

}
