package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.TestException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PageObject {
    private WebDriver driver;
    private WebDriverWait wait;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected void Clear(String elementForClean) throws Exception {
        TryClear(elementForClean, 5);
    }

    protected void Click(WebElement elementForClick) throws Exception {
        TryClick(elementForClick, 5);
    }

    protected void Click(String elementForClick) throws Exception {
        TryClick(elementForClick, 5);
    }

    protected void SendKey(WebElement elementForSend, String message){
        if(elementForSend.isEnabled()){
            Send(elementForSend, message);
        }
    }

    protected void SendKey(String elementForSend, String message){
        WebElement element = driver.findElement(By.xpath(elementForSend));
        if(element.isEnabled()){
            Send(element, message);
        }
    }

    private void Send(WebElement element, String message) {
        try{
            Click(element);
            element.clear();
            element.sendKeys(message);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    protected void MoveCursorToElement(WebElement elementForMoveСursor){
        Actions actions = new Actions(driver);
        actions.moveToElement(elementForMoveСursor).build().perform();
    }

    protected void MoveCursorToElement(String elementForMoveСursor){
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(elementForMoveСursor));
        actions.moveToElement(element).build().perform();
    }

    protected List<WebElement> waitWebElements(WebElement parentElement, String tag) {
        try {
            if (parentElement == null) {
                Wait(tag, 5);
                return driver.findElements(By.xpath(tag));
            } else {
                waitInElement(parentElement, tag);
                return parentElement.findElements(By.xpath(tag));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected WebElement waitInElement(WebElement parentElement, String tag) {
        try {
            Wait(tag, 5);
            return parentElement.findElement(By.xpath(tag));
        } catch (Exception ex) {
            throw new TestException(ex);
        }
    }

    private void TryClick(WebElement elementForClick, int numberOfAttempts) throws Exception {
        if(elementForClick == null)
            throw new Exception("Element for click is not detected");
        for(int count = 1; count <= numberOfAttempts; count++){
            try{
                MoveCursorToElement(elementForClick);
                elementForClick.click();
                return;
            } catch (Exception e){
                if (count == numberOfAttempts) continue;
                var waitTime = count * count * 100;
                WaitingInMilliseconds(waitTime);
            }
        }
    }

    private void TryClick(String elementForClick, int numberOfAttempts) throws Exception {
        WebElement element = driver.findElement(By.xpath(elementForClick));
        if(element == null)
            throw new Exception("Element for click is not detected");
        for(int count = 1; count <= numberOfAttempts; count++){
            try{
                MoveCursorToElement(element);
                element.click();
                return;
            } catch (Exception e){
                if (count == numberOfAttempts) continue;
                var waitTime = count * count * 100;
                WaitingInMilliseconds(waitTime);
            }
        }
    }


    private void TryClear(String elementForClear, int numberOfAttempts) throws Exception {
        WebElement element = driver.findElement(By.xpath(elementForClear));
        if(element == null)
            throw new Exception("Element for click is not detected");
        for (int i = 1; i < numberOfAttempts; i++) {
            try {
                MoveCursorToElement(element);
                element.clear();
                return;
            } catch (Exception e){
                if (i ==numberOfAttempts) continue;
                var waitTime = i * i * 100;
                WaitingInMilliseconds(waitTime);
            }
        }
    }

    protected void waitInvisibilityOfElementLocated(String tag){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(tag)));
    }

    public List<WebElement> Wait (String tag, int numberForAttempts) throws InterruptedException, TestException {
        for(int count = 0; count < numberForAttempts; count++){
            List<WebElement> elementInThePage = driver.findElements(By.xpath(tag));
            if(elementInThePage.get(0).isDisplayed()){
                return elementInThePage;
            }
            if (count == numberForAttempts) continue;
            long waitTime = count * count * 100;
            WaitingInMilliseconds(waitTime);
        }
        throw new TestException("Element " + tag + " is not detected");
    }

    protected boolean waitUntilModuleDisappears(String tag) throws InterruptedException {
        int timeInMilliseconds = 1000;
        int stepForWaitingInMilliseconds = 100;
        boolean result;
        while (timeInMilliseconds >= 0) {
            try{
                if (!(driver.findElement(By.xpath(tag)).isEnabled()))
                    return result = true;
            } catch (Exception ex){
                return result = false;
            }
            WaitingInMilliseconds(stepForWaitingInMilliseconds);
            timeInMilliseconds -= stepForWaitingInMilliseconds;
        }
        return false;
    }

    public WebElement elementInThePage(String tag, int numberForAttempts) throws InterruptedException {
        for(int count = 0; count < numberForAttempts; count++){
            WebElement elementInThePage = driver.findElement(By.xpath(tag));
            if(elementInThePage.isEnabled()){
                return elementInThePage;
            }
            if (count == numberForAttempts) continue;
            long waitTime = count * count * 100;
            WaitingInMilliseconds(waitTime);
        }
        throw new TestException("Element " + tag + " is not detected");
    }

    protected void WaitingInMilliseconds(long waitTime) throws InterruptedException {
        Thread.sleep(waitTime);
    }

    protected List<WebElement> WaitWebElements(String tag){
        try{
            Wait(tag, 5);
            return driver.findElements(By.xpath(tag));
        } catch (InterruptedException e) {
            e.printStackTrace();
            e.getMessage();
            return new ArrayList<WebElement>();
        }
    }

    protected WebElement WaitWebElement(String tag){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tag)));
        return driver.findElement(By.xpath(tag));
    }

}
