package frontend;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;


import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public abstract class BasePage {

    private static final int DEFAULT_ELEMENT_WAIT_TIMEOUT = 15;
    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        initElements();
    }

    private void initElements() {
        PageFactory.initElements(getDriver(), this);
    }
    public WebDriver getDriver() { return driver; }

    public FluentWait<WebDriver> wait(int timeout) {
        return new FluentWait<>(getDriver())
                .ignoring(NoSuchElementException.class)
                .ignoring(Exception.class)
                .withTimeout(Duration.ofSeconds(timeout));
    }

    public void click(WebElement element) {
        wait(DEFAULT_ELEMENT_WAIT_TIMEOUT).until(elementToBeClickable(element)).click();
    }

    public boolean elementExist(WebElement elem, int timeout) {
        try {
            wait(timeout).until(visibilityOf(elem));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
