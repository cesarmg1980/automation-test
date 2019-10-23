package frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class SingleItemPage extends BasePage {


    private String elementsTitleFromList;
    private String elementsPriceFromList;

    public SingleItemPage(WebDriver driver, String randElementTitle, String randElementPrice) {
        super(driver);
        this.elementsTitleFromList = randElementTitle;
        this.elementsPriceFromList = randElementPrice;
    }

    @FindBy(how = How.XPATH, using = "//h1[@class='ui-pdp-title']")
    WebElement selectedElementTitle;

    @FindBy(how = How.CLASS_NAME, using = "price-tag-fraction")
    WebElement selectedElementPrice;

    public void validateElementByTitleAndPrice() {
        Assert.assertEquals(selectedElementTitle.getText(), elementsTitleFromList,
                "Test Failed: Element's Title doesn't match");
        Assert.assertEquals(selectedElementPrice.getText(), elementsPriceFromList,
                "Test Failed: Element's Title doesn't match");
        System.out.println("Element's Title and Price Match...");
    }
}
