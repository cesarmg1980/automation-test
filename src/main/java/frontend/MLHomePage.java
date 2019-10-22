package frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MLHomePage extends BasePage {

    public MLHomePage(WebDriver driver) {
        super(driver);
    }

    public NavBarPage navbar = new NavBarPage(getDriver());

    public MLHomePage goToMLHomePage(String url) {
        getDriver().get(url);
        return this;
    }
}
