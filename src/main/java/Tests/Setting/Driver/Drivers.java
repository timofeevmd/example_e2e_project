package Tests.Setting.Driver;

import Tests.Setting.Config.ConfigurationProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;

public class Drivers {
    public WebDriver driver;

    public WebDriver getDriver(String browser, String mode){
        switch(mode){
            case ("standard") :
                switch (browser){
                    case ("chrome") :
                        getChromeDriver();
                        return driver;
                    case ("firefox") :
                        getGeckoDriver();
                        break;
                    case ("edge") :
                        getEdgeDriver();
                        break;
                }
            break;
            case ("headless") :
                switch (browser) {
                    case ("chrome") :
                        getChromeDriverHeadLessMode();
                        break;
                    case ("firefox") :
                        getGeckoDriverHeadLessMode();
                        break;
                    case ("edge") :
                        getEdgeDriverHeadLessMode();
                        break;
                }
            break;
        }
        return driver;
    }

    private void getChromeDriver(){
        System.setProperty(ConfigurationProperties.getProperty("nameChromeDriver")
                , ConfigurationProperties.getProperty("pathChromeDriver"));
        this.driver = new ChromeDriver();
        PageFactory.initElements(driver, this);
    }

    private void getChromeDriverHeadLessMode(){
        System.setProperty(ConfigurationProperties.getProperty("nameChromeDriver")
                , ConfigurationProperties.getProperty("pathChromeDriver"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        this.driver = new ChromeDriver(options);
        PageFactory.initElements(driver, this);
    }

    private void getGeckoDriver(){
        System.setProperty(ConfigurationProperties.getProperty("nameGeckoDriver")
                , ConfigurationProperties.getProperty("pathGeckoDriver"));
        this.driver = new FirefoxDriver();
        PageFactory.initElements(driver, this);
    }

    private void getGeckoDriverHeadLessMode(){
        System.setProperty(ConfigurationProperties.getProperty("nameGeckoDriver")
                , ConfigurationProperties.getProperty("pathGeckoDriver"));
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        this.driver = new FirefoxDriver(options);
        PageFactory.initElements(driver, this);
    }

    private void getEdgeDriver(){
        System.setProperty(ConfigurationProperties.getProperty("nameEdgeDriver")
                , ConfigurationProperties.getProperty("pathEdgeDriver"));
        this.driver = new EdgeDriver();
        PageFactory.initElements(driver, this);
    }

    private void getEdgeDriverHeadLessMode(){
        System.setProperty(ConfigurationProperties.getProperty("nameChromeDriver")
                , ConfigurationProperties.getProperty("pathChromeDriver"));
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");
        this.driver = new EdgeDriver(options);
        PageFactory.initElements(driver, this);
    }
}
