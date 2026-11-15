package config;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    @Override
    public WebDriver get() {
        WebDriver driver = createDriver();
        driver.get(config.getBaseUrl());
        return driver;
    }

    private WebDriver createDriver() {
        System.out.println(config.isRemote());
        if (config.isRemote()) {
            return createRemoteDriver();
        } else return createLocalDriver();
    }

    private WebDriver createRemoteDriver() {
        MutableCapabilities options;
        switch (config.getBrowser()) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("browserVersion", config.getBrowserVersion());
                options = chromeOptions;
                break;
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("browserVersion", config.getBrowserVersion());
                options = firefoxOptions;
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + config.getBrowser());
        }
        return new RemoteWebDriver(config.getRemoteURL(), options);
    }

    private WebDriver createLocalDriver() {
        switch (config.getBrowser()) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            default:
                throw new RuntimeException("No such local browser");
        }
    }
}
