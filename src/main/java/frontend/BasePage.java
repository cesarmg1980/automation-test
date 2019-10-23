package frontend;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;


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
        try {
            String elementText = element.getText();
            wait(DEFAULT_ELEMENT_WAIT_TIMEOUT).until(elementToBeClickable(element)).click();
            System.out.println("Element clicked " + elementText);
        } catch (Exception e) {
            Assert.fail("Test Failed! Element couldn't be found " + e.getMessage());
        }
    }

    public boolean elementExist(WebElement elem, int timeout) {
        try {
            wait(timeout).until(visibilityOf(elem));
            System.out.println("Element exist " + elem.getText());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void hoverOverElement(WebElement element) {
        Actions action = new Actions(getDriver());
        wait(DEFAULT_ELEMENT_WAIT_TIMEOUT).until(visibilityOf(element));
        action.moveToElement(element).perform();
        System.out.println("Hovered over " + element.getText());
    }

    public void scrollToElement(WebElement element) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element);
        System.out.println("Scrolled over " + element.getText());
    }
}
