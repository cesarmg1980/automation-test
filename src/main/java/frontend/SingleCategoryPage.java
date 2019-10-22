package frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class SingleCategoryPage extends BasePage{

    private static final int CALCULATE_SHIPPING_FEES_WAIT_TIMEOUT = 5;

    public SingleCategoryPage(WebDriver driver) { super(driver); }

    public NavBarPage navBar = new NavBarPage(getDriver());

    @FindBy(how = How.CLASS_NAME, using = "breadcrumb__title")
    private WebElement categoryTitle;

    @FindBy(how = How.CLASS_NAME, using = "quantity-results")
    private WebElement categoryQtyResults;

    @FindBy(how = How.CLASS_NAME, using = "andes-tooltip__title")
    WebElement calculateShippingFeesToCurrentLocation;

    @FindBy(how = How.CLASS_NAME, using = "andes-button__content")
    WebElement entendidoBtn;

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
            System.out.println("Element exists: " + calculateShippingFeesToCurrentLocation.getText());
            click(entendidoBtn);
            System.out.println("Button 'Entendido' clicked");
        }
    }
}
