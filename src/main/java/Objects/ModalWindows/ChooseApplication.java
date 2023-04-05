package Objects.ModalWindows;

import Objects.PageObject;
import Objects.Pages.PowerInvoicePage;
import org.openqa.selenium.WebDriver;

public class ChooseApplication extends PageObject {
    private WebDriver driver;

    private String loader = "//div[@class='page-loader-container d-flex justify-content-center']";
    private String aPInvoiceImport = "//div[contains(text(),'AP Invoice Import')]";
    private String powerInvoice = "//div[contains(text(),'Power Invoice')]";


    public ChooseApplication(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public PowerInvoicePage clickButton(String application) throws Exception {
        switch(application){
            case "powerInvoice":
                Click(powerInvoice);
                break;
            case "aPInvoiceImport":
                Click(aPInvoiceImport);
        }
        return new PowerInvoicePage(driver);
    }
}
