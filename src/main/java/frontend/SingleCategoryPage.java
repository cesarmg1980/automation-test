package frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class SingleCategoryPage extends BasePage{

    private static final int CALCULATE_SHIPPING_FEES_WAIT_TIMEOUT = 5;

    public SingleCategoryPage(WebDriver driver) { super(driver); }

    public NavBarPage navBar = new NavBarPage(getDriver());

    @FindBy(how = How.CLASS_NAME, using = "breadcrumb__title")
    private WebElement categoryTitle;

    @FindBy(how = How.CLASS_NAME, using = "quantity-results")
    private WebElement categoryQtyResults;

    @FindBy(how = How.CLASS_NAME, using = "andes-tooltip__title")
    private WebElement calculateShippingFeesToCurrentLocation;

    @FindBy(how = How.CLASS_NAME, using = "andes-button__content")
    private WebElement entendidoBtn;

    @FindBy(how = How.XPATH, using = "//dl[@id='id_state']/dd/a/span")
    private WebElement ubicacionCapitalFederal;

    @FindBy(how = How.XPATH, using = "//section[@id='results-section']/ol/li")
    List<WebElement> resultsFilteredByLocation;

    public void validateCategory(String nameToValidate) {
        verifyIfPageIsCalculatingShippingFees();
        Assert.assertEquals(categoryTitle.getText(),nameToValidate,
                "Test failed! Category selected doesn't match current category Page");
        System.out.println("Category validated, " + categoryTitle.getText() + " matches " + nameToValidate);
    }

    public WebElement getCategoryQtyResults() { return categoryQtyResults; }

    public String getResultsQty() {
        return getCategoryQtyResults().getText();
    }

    public void verifyIfPageIsCalculatingShippingFees() {
        if(elementExist(calculateShippingFeesToCurrentLocation, CALCULATE_SHIPPING_FEES_WAIT_TIMEOUT)) {
            click(entendidoBtn);
        }
    }

    public void selectUbicacionCapFed() {
        verifyIfPageIsCalculatingShippingFees();
        scrollToElement(ubicacionCapitalFederal);
        click(ubicacionCapitalFederal);
    }

    public SingleItemPage selectSingleRandomElementFromListOfResults() {
        int randindex = new Random().nextInt(resultsFilteredByLocation.size());
        System.out.println("Random index generated: " + randindex);
        WebElement randElement = resultsFilteredByLocation.get(randindex);
        String randElementTitle = getElemenTitle(randElement);
        String randElementPrice = getElementPrice(randElement);
        System.out.println("Element selected from list: " + randElementTitle + ", Element's Price: " + randElementPrice);
        scrollToElement(itemTitle(randElement));
        click(itemTitle(randElement));
        return new SingleItemPage(getDriver(), randElementTitle, randElementPrice);

    }

    public String getElemenTitle(WebElement element) {
        String elementTitle = element.findElement(By.className("item__info-title")).getText();
        return elementTitle;
    }

    public String getElementPrice(WebElement element) {
        return element.findElement(By.className("price__fraction")).getText();
    }

    public WebElement itemTitle(WebElement element) {
        return element.findElement(By.className("main-title"));
    }
}
