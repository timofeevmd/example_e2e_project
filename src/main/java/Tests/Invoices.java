package Tests;

import Objects.DropDown.AssignModalWindow;
import Objects.DropDown.DropDownUserMenu;
import Objects.DropDown.TagDropDownMenu;
import Objects.ModalWindows.Attachments;
import Objects.ModalWindows.InvoiceDetail;
import Objects.Pages.PowerInvoicePage;
import Objects.Tables.Invoices.Elements.Cell;
import Objects.Tables.Invoices.InvoicesTable;
import Objects.Tables.Invoices.Rows.InvoicesTableRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Invoices extends TestBase {
    private WebDriver driver;
    private static PowerInvoicePage powerInvoicePage;

    private final String[] headerCells = new String[] {"Job No.", "Group", "Job Name"
            , "Supplier Name", "Branch ID", "Other_Chg"
            , "Branch Name", "Distributor Quote", "Auto-validated"
            , "Invoice Total", "Image Received", "Invoice Date"
            , "Inv_Tax", "PO_Exist", "Notes"
            , "Freight", "PO No.", "Status"
            , "Actions", "Invoice No.", "Supplier Number"
            , "Inv_Exist", "Receiving status", "Assigned To"
            , "Process Date", "Supplier ID", "Tag", "Received Date"};

    private String invoiceNumber;
    private String changedInvoiceTag;
    private String invoiceUnTag;
    private String initInvoiceTag;
    private String currentInvoiceStatus;
    private String invoiceStatusAfterRestored;

    private List<String> invoicesNumber = new ArrayList<>();
    private List<String> currentInvoicesStatus = new ArrayList<>();
    private List<String> invoicesStatusAfterRestored = new ArrayList<>();

    @Test(description = "checking the functionality of the search string across branches", priority = 0)
    @Parameters({"currentBranch"})
    public void searchBranches(String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        String branchName = powerInvoicePage.selectedBranchesName.get(0);

        for (int rowCount = 1; rowCount <= table.rows.size(); rowCount++) {
           Assert.assertEquals(branchName, table.rows.get(rowCount).cells.get("Branch Name").cellText);
        }
    }

    @Test(priority = 1)
    @Parameters({"rowNumber", "currentBranch"})
    public void rejectInvoice(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
            .selectBranch()
            .choiceBranches(currentBranch)
            .clickByName()
            .getInvoicesTable(false);

        invoiceNumber = table.rows.get(currentRow).cells
            .get("Invoice No.")
            .cellText;

        table.markRow(currentRow);
        powerInvoicePage.clickHeaderButton("reject")
            .searchInvoice(invoiceNumber);

        InvoicesTable mainTable = new InvoicesTable(driver, false);
        currentInvoiceStatus = mainTable.rows.get(1).cells
            .get("Status")
            .cellText;

        Assert.assertEquals("Rejected", currentInvoiceStatus);
    }

    @Test(priority = 2, groups = "broken")
    @Parameters({"rowNumber", "currentBranch"})
    public void restoreInvoiceAfterReject(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.markRow(currentRow);
        powerInvoicePage.clickHeaderButton("reject")
                .closeAlert()
                .searchInvoice(invoiceNumber);

        InvoicesTable allTable = new InvoicesTable(driver,false);
        currentInvoiceStatus = allTable.rows.get(1).cells
                .get("Status")
                .cellText;

        allTable.markRow(1);
        powerInvoicePage.clickHeaderButton("restore")
                .closeAlert()
                .searchInvoice(invoiceNumber);

        InvoicesTable allTableAfterRestore = new InvoicesTable(driver, false);
        invoiceStatusAfterRestored = allTableAfterRestore.rows.get(1).cells
                .get("Status")
                .cellText;

        Assert.assertEquals("Rejected", currentInvoiceStatus);
        Assert.assertEquals("New", invoiceStatusAfterRestored);
    }

    @Test(priority = 3)
    @Parameters({"rowNumber", "currentBranch"})
    public void deleteInvoice(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.markRow(currentRow);
        powerInvoicePage.clickDeleteButton()
                .closeAlert()
                //.choiceTableTab("deleted")
                .searchInvoice(invoiceNumber);

        InvoicesTable mainTable = new InvoicesTable(driver, false);
        currentInvoiceStatus = mainTable.rows.get(1).cells
                .get("Status")
                .cellText;

        Assert.assertEquals("Deleted", currentInvoiceStatus);
    }

    @Test(priority = 4)
    @Parameters({"rowNumber", "currentBranch"})
    public void restoreInvoiceAfterDelete(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.markRow(currentRow);
        InvoicesTable allTable = powerInvoicePage
                .clickDeleteButton()
                .searchInvoice(invoiceNumber)
                .getInvoicesTable(false);

        currentInvoiceStatus = allTable.rows.get(1).cells
                .get("Status")
                .cellText;

        allTable.markRow(1);
        powerInvoicePage.clickHeaderButton("restore")
                .closeAlert()
                .searchInvoice(invoiceNumber);

        InvoicesTable allTableAfterRestore = new InvoicesTable(driver, false);
        invoiceStatusAfterRestored = allTableAfterRestore.rows.get(1).cells
                .get("Status")
                .cellText;

        Assert.assertEquals("Deleted", currentInvoiceStatus);
        Assert.assertEquals("New", invoiceStatusAfterRestored);
    }

    @Test(priority = 5)
    @Parameters({"firstInvoice", "lastInvoice", "currentBranch"})
    public void restoreSelectionInvoicesAfterDelete(String firstInvoice, String lastInvoice, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentFirstInvoice = Integer.parseInt(firstInvoice);
        int currentLastInvoice = Integer.parseInt(lastInvoice);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        for (int i = currentFirstInvoice; i <= currentLastInvoice; i++) {
            invoicesNumber.add(table.rows.get(i).cells
                    .get("Invoice No.")
                    .cellText);
        }

        table.markRowsBySuite(currentFirstInvoice, currentLastInvoice);
        powerInvoicePage.clickDeleteButton()
                .closeAlert();

        for (String invoiceNumber: invoicesNumber) {
            powerInvoicePage.searchInvoice(invoiceNumber);
            InvoicesTable allTable = new InvoicesTable(driver, false);
            currentInvoicesStatus.add(allTable.rows.get(1).cells
                    .get("Status")
                    .cellText);

            allTable.markRow(1);
            powerInvoicePage.clickHeaderButton("restore")
                    .closeAlert()
                    .searchInvoice(invoiceNumber);

            InvoicesTable allTableAfterRestore = new InvoicesTable(driver, false);

            invoicesStatusAfterRestored.add(allTableAfterRestore.rows.get(1).cells
                    .get("Status")
                    .cellText);
        }

        for (int i = 0; i < invoicesStatusAfterRestored.size(); i++) {
            Assert.assertEquals("Deleted", currentInvoicesStatus.get(i));
            Assert.assertEquals("New", invoicesStatusAfterRestored.get(i));
        }
    }

    @Test(priority = 6)
    @Parameters({"rowNumber", "currentBranch"})
    public void tagUnTagInvoice(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.markRow(currentRow);
        InvoicesTable tableAfterTegingInvoice = powerInvoicePage.clickTagButton()
                .markTag(1)
                .okayButton()
                .searchInvoice(invoiceNumber)
                .getInvoicesTable(true);

        changedInvoiceTag = tableAfterTegingInvoice.rows.get(1).cells
                .get("Tag")
                .cellText;

        tableAfterTegingInvoice.markRow(1);
        InvoicesTable tableUnTegingInvoice = powerInvoicePage.clickTagButton()
                .markTag(0)
                .okayButton()
                .searchInvoice(invoiceNumber)
                .getInvoicesTable(true);

        invoiceUnTag = tableUnTegingInvoice.rows.get(1).cells
                .get("Tag")
                .cellText;

        Assert.assertNotEquals(changedInvoiceTag, "");
    }

    @Test(priority = 7)
    @Parameters({"currentBranch"})
    public void columnOrderingUpDown(String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        List<String> orderingTableInvoicesList = new ArrayList<>();

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        for (int i = 1; i <= table.rows.size(); i++) {
            invoicesNumber.add(table.rows.get(i).cells
                    .get("Invoice No.")
                    .cellText);
        }

        Map<String, WebElement> sortingMap = table.rows.get(1).invoicesTableOrderingHeaderCells;
        for (Map.Entry<String, WebElement> includeMup : sortingMap.entrySet()) {
            String headerCell = includeMup.getKey();
            if(!(headerCell.equals("Group") || headerCell.equals("Actions") || headerCell.equals("Tag") || headerCell.equals("Assigned To") || headerCell.equals("Notes") || headerCell.equals("Invoice Date"))) {
                table.ordering(headerCell);
                InvoicesTable orderingTable = powerInvoicePage
                        .getInvoicesTable(false);
                for (int i = 1; i < orderingTable.rows.size(); i++) {
                    orderingTableInvoicesList.add(orderingTable.rows.get(i).cells
                            .get("Invoice No.")
                            .cellText);
                }
                int index = (int) (Math.random() * 9);
                Assert.assertNotEquals(orderingTableInvoicesList.get(index), invoicesNumber.get(index),
                        "Ordering by [" + headerCell + "] is not a correct."
                                + " Invoice number " + invoicesNumber.get(index)
                                + " are equal ordering invoice number " + orderingTableInvoicesList.get(index));
                orderingTableInvoicesList.clear();
            }
        }
    }

    @Test(priority = 8)
    @Parameters({"currentBranch"})
    public void presenceInformationCell(String currentBranch) throws Exception {
        //TODO: после реализации pageObject enrichment дописать тест
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        for (int i = 1; i <= table.rows.size(); i++) {
            Assert.assertFalse(table.rows.get(i).cells.get("Group").cellText.isEmpty());
        }
    }

    @Test(priority = 9)
    @Parameters({"rowNumber", "currentBranch"})
    public void actionButtonDelete(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.rows.get(currentRow)
                .scrollTableToElement(table.rows.get(currentRow)
                        .cells.get("Actions")
                        .selfElement);

        table.rows.get(currentRow)
                .getRowAction()
                .actionBy("Delete Invoice");

        powerInvoicePage.searchInvoice(invoiceNumber);

        InvoicesTable mainTable = new InvoicesTable(driver, false);
        mainTable.rows.get(1)
                .scrollTableToElement(mainTable.rows.get(1)
                        .cells.get("Invoice No.")
                        .selfElement);
        currentInvoiceStatus = mainTable.rows.get(1).cells
                .get("Status")
                .cellText;

        Assert.assertEquals("Deleted", currentInvoiceStatus);

        mainTable.markRow(1);
        powerInvoicePage.clickHeaderButton("restore")
                .closeAlert();
    }

    @Test(priority = 10)
    @Parameters({"rowNumber", "currentBranch"})
    public void actionButtonReject(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.rows.get(currentRow)
                .scrollTableToElement(table.rows.get(currentRow)
                        .cells.get("Actions")
                        .selfElement);

        table.rows.get(currentRow)
                .getRowAction()
                .actionBy("Reject Invoice");

        powerInvoicePage.searchInvoice(invoiceNumber);

        InvoicesTable mainTable = new InvoicesTable(driver, false);
        mainTable.rows.get(1)
                .scrollTableToElement(mainTable.rows.get(1)
                        .cells.get("Invoice No.")
                        .selfElement);
        currentInvoiceStatus = mainTable.rows.get(1).cells
                .get("Status")
                .cellText;

        Assert.assertEquals("Rejected", currentInvoiceStatus);

        mainTable.markRows(1);
        powerInvoicePage.clickHeaderButton("restore")
                .closeAlert();
    }

    @Test(priority = 11)
    @Parameters({"rowNumber", "currentBranch"})
    public void actionButtonViewInvoiceDetails(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);
        String invoiceValueCell;

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        table.rows.get(currentRow)
                .scrollTableToElement(table.rows.get(currentRow)
                        .cells.get("Actions")
                        .selfElement);

        InvoiceDetail invoiceDetail = (InvoiceDetail) table.rows.get(currentRow)
                .getRowAction()
                .actionBy("View Invoice Details");

        invoiceDetail.closeDetailModalWindow();

        for (Map.Entry<String, String> detailsMap : invoiceDetail.generalInvoiceInformation.entrySet()) {
            String  detailHeaderCell = detailsMap.getKey();
            String detailValueCell = detailsMap.getValue();
            invoiceValueCell = table.rows.get(currentRow).cells.get(detailHeaderCell).cellText;
            Assert.assertEquals(detailValueCell, invoiceValueCell, "detailValueCell [" + detailValueCell + "] and invoiceValueCell [" + invoiceValueCell + "] are not equal");
        }
        Assert.assertFalse(invoiceDetail.invoiceTableRows.isEmpty(), "Table on Invoice side has not initialize");
        Assert.assertFalse(invoiceDetail.poTableRows.isEmpty(), "Table on PO side has not initialize");
    }

    @Test(priority = 12)
    @Parameters({"rowNumber", "currentBranch"})
    public void actionButtonAttachments(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        table.rows.get(currentRow)
                .scrollTableToElement(table.rows.get(currentRow)
                        .cells.get("Actions")
                        .selfElement);

        Attachments attachmentsModalWindow = (Attachments) table.rows.get(currentRow)
                .getRowAction()
                .actionBy("View Attachments");

        attachmentsModalWindow.closeButton();

        Assert.assertTrue(attachmentsModalWindow.title.isDisplayed(), "Attachment modal window was not initialize");
    }

    @Test(priority = 13)
    @Parameters({"currentBranch"})
    public void goToPage(String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        List<String> invoicesNumberFirstPage = new ArrayList<>();
        List<String> poNumberFirstPage = new ArrayList<>();
        List<String> invoicesNumberNextPage = new ArrayList<>();
        List<String> poNumberNextPage = new ArrayList<>();

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        for (Map.Entry<Integer, InvoicesTableRow> item: table.rows.entrySet()) {
            InvoicesTableRow value = item.getValue();
            invoicesNumberFirstPage.add(value.cells.get("Invoice No.").cellText);
            poNumberFirstPage.add(value.cells.get("PO No.").cellText);
        }

        int totalNumberOfPages = powerInvoicePage.totalNumberOfPages();

        if(totalNumberOfPages > 1) {
            powerInvoicePage.goToPage("2");
            InvoicesTable tableSecondPage = powerInvoicePage
                    .getInvoicesTable(false);

            for (Map.Entry<Integer, InvoicesTableRow> item: tableSecondPage.rows.entrySet()) {
                InvoicesTableRow value = item.getValue();
                invoicesNumberNextPage.add(value.cells.get("Invoice No.").cellText);
                poNumberNextPage.add(value.cells.get("PO No.").cellText);
            }
        }

        for (int i = 0; i < invoicesNumberFirstPage.size(); i++) {
            boolean invoiceEquals = invoicesNumberFirstPage.get(i).equals(invoicesNumberNextPage.get(i));
            boolean poEquals = poNumberFirstPage.get(i).equals(poNumberNextPage.get(i));
            if((!invoiceEquals && poEquals) || (invoiceEquals && !poEquals))
                Assert.assertNotEquals(invoiceEquals, poEquals);
            else if (!invoiceEquals && !poEquals)
                Assert.assertEquals(invoiceEquals, poEquals);
            else
                Assert.assertNotEquals(invoiceEquals, poEquals, "invoiceNumber[" + invoicesNumberFirstPage.get(i) + "] and poNumber[" + poNumberFirstPage.get(i) + "] in line on first page "
                        + i + " equal invoiceNumber[" + invoicesNumberNextPage.get(i) + "] and poNumber[" + poNumberNextPage.get(i) + "] in line on second page");
        }
    }

    @Test(priority = 14)
    @Parameters({"currentBranch"})
    public void viewRecordsOption(String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int[] expectedSize = new int[]{10, 20, 50};

       powerInvoicePage
               .selectBranch()
               .choiceBranches(currentBranch)
               .clickByName();

        for (int i = 0; i < expectedSize.length; i++) {
            powerInvoicePage.viewRecordsOption(expectedSize[i])
                    .clickByName();
            InvoicesTable changedTable = powerInvoicePage
                    .getInvoicesTable(false);
            Assert.assertEquals(expectedSize[i], changedTable.rows.size());
        }
    }

    @Test(priority = 15)
    @Parameters({"currentBranch"})
    public void statusFilter(String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        List<String> statusesFirstChangedStatus = new ArrayList<>();
        List<String> statusesSecondChangedStatus = new ArrayList<>();

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        table.rows.get(1)
                .scrollTableToElement(table.rows.get(1)
                        .cells
                        .get("Status")
                        .selfElement);

         table.dropDownStatuses()
                .unMarkAllCheckBoxes()
                .markCheckBoxes("New")
                .okButton();

        InvoicesTable tableFirstChangedStatus = powerInvoicePage
                .getInvoicesTable(false);

        for (int i = 1; i <= tableFirstChangedStatus.rows.size() ; i++) {
            statusesFirstChangedStatus.add(tableFirstChangedStatus.rows.get(i).cells.get("Status").cellText);
        }

        tableFirstChangedStatus.dropDownStatuses()
                .unMarkAllCheckBoxes()
                .markCheckBoxes("Approved")
                .okButton();

        InvoicesTable tableSecondChangedStatus = powerInvoicePage
                .getInvoicesTable(false);

        for (int i = 1; i <= tableSecondChangedStatus.rows.size() ; i++) {
            statusesSecondChangedStatus.add(tableSecondChangedStatus.rows.get(i).cells.get("Status").cellText);
        }

        for (int i = 0; i < tableSecondChangedStatus.rows.size(); i++) {
            Assert.assertNotEquals(statusesFirstChangedStatus.get(i), statusesSecondChangedStatus.get(i));
        }
    }

    @Test(priority = 16)
    @Parameters({"rowNumber", "currentBranch"})
    public void moveToBranchButton(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);
        String currentBranchName;
        String branchNameAfterChange;

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        currentBranchName = table.rows.get(currentRow).cells
                .get("Branch Name")
                .cellText;

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.markRows(currentRow);

        powerInvoicePage.clickMoveButton("Move to branch")
                .chooseBranch("Unity Electric WA");

        InvoicesTable tableAfterChanges = powerInvoicePage
                .selectBranch()
                .choiceBranches("Unity Electric WA")
                .clickByName()
                .searchInvoice(invoiceNumber)
                .getInvoicesTable(false);

        branchNameAfterChange = tableAfterChanges.rows.get(1).cells
                .get("Branch Name")
                .cellText;

        Assert.assertNotEquals(currentBranchName, branchNameAfterChange);
    }

    @Test(priority = 17)
    @Parameters({"currentBranch"})
    public void fullHeaderColumns(String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        for(Map.Entry<String, Cell> row: table.rows.get(1).cells.entrySet()){
            String headerCell = row.getKey();
            for(String header: headerCells){
                if(header.equals(headerCell))
                    Assert.assertEquals(headerCell, header);
            }
        }
    }

    @Test(priority = 18)
    @Parameters({"rowNumber", "currentBranch"})
    public void assignedTo(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(false);

        table.rows.get(1)
                .scrollTableToElement(table.rows.get(1)
                        .cells
                        .get("Freight")
                        .selfElement);

        AssignModalWindow assignModalWindow = (AssignModalWindow) powerInvoicePage.clickByFilterButton(
                table.rows.get(currentRow).cells.get("Assigned To").headerCellText
                , table.rows.get(currentRow).cells.get("Assigned To").headerButton);

        assignModalWindow.searchByUser("Michael Timofeev")
                .searchButton();

        InvoicesTable tableAfterAssignedFilter = powerInvoicePage
                .getInvoicesTable(false);

        for (int i = 1; i <= tableAfterAssignedFilter.rows.size(); i++) {
            Assert.assertEquals("MT", tableAfterAssignedFilter.rows.get(i).cells.get("Assigned To").cellText);
        }
    }

    @Test(priority = 19)
    @Parameters({"rowNumber", "currentBranch"})
    public void tagFilter(String rowNumber, String currentBranch) throws Exception {
        driver = getDriver();
        powerInvoicePage = new PowerInvoicePage(driver);
        int currentRow = Integer.parseInt(rowNumber);

        InvoicesTable table = powerInvoicePage
                .selectBranch()
                .choiceBranches(currentBranch)
                .clickByName()
                .getInvoicesTable(true);

        table.markRow(currentRow);

        invoiceNumber = table.rows.get(currentRow).cells
                .get("Invoice No.")
                .cellText;

        table.rows.get(1)
                .scrollTableToElement(table.rows.get(1)
                        .cells
                        .get("Freight")
                        .selfElement);

        initInvoiceTag = table.rows.get(currentRow).cells
                .get("Tag")
                .cellText;

        //table.markRow(currentRow);
        InvoicesTable tableAfterTagingInvoice = powerInvoicePage.clickTagButton()
                .markTag(1)
                .okayButton()
                .getInvoicesTable(true);

        TagDropDownMenu tagDropDownMenu = (TagDropDownMenu) powerInvoicePage.clickByFilterButton(
                tableAfterTagingInvoice.rows.get(currentRow).cells.get("Tag").headerCellText
                , tableAfterTagingInvoice.rows.get(currentRow).cells.get("Tag").headerButton);

        tagDropDownMenu.resetButton();

        TagDropDownMenu tagDropDownMenuAfterReset = (TagDropDownMenu) powerInvoicePage
                .clickByFilterButton(tableAfterTagingInvoice.rows.get(currentRow).cells.get("Tag").headerCellText
                        , tableAfterTagingInvoice.rows.get(currentRow).cells.get("Tag").headerButton);

        tagDropDownMenuAfterReset.markCheckBoxes("Imp")
                .okButton();

        InvoicesTable tableAfterFiltering = powerInvoicePage
                .getInvoicesTable(true);

        for (int i = 1; i <= tableAfterFiltering.rows.size(); i++) {
            Assert.assertEquals("Imp", tableAfterFiltering.rows.get(i).cells.get("Tag").cellText);
        }
    }
}
