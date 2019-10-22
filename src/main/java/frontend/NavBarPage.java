package frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NavBarPage extends BasePage {

    public NavBarPage(WebDriver driver) { super(driver); }

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Categorías')]")
    private WebElement categoriesNavBar;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Hogar y Electrodomésticos')]")
    private WebElement hogaryElectrodomesticos;

    @FindBy(how = How.XPATH, using = "//a[contains(.,'Climatización')]")
    private WebElement climatizacion;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Tecnología')]")
    private WebElement tecnologia;

    @FindBy(how = How.XPATH, using = "//a[contains(.,'Celulares y Smartphones')]")
    private WebElement celularesSmartphone;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Belleza y Cuidado Personal')]")
    private WebElement bellezaCuidadoPersonal;

    @FindBy(how = How.XPATH, using = "//a[contains(.,'Perfumes Importados')]")
    private WebElement perfumesImportados;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Herramientas e Industrias')]")
    private WebElement herramientasIndustrias;

    @FindBy(how = How.XPATH, using = "//a[contains(.,'Industria Textil')]")
    private WebElement industriaTextil;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Juguetes y Bebés')]")
    private WebElement juguetesBebes;

    @FindBy(how = How.XPATH, using = "//a[contains(.,'Cuarto del Bebé')]")
    private WebElement cuartoDelBebe;

    public void clickOnCategoriesNavBarElement() {
        click(categoriesNavBar);
    }

    public SingleCategoryPage  goToClimatizacionPage() {
        hoverOverElement(categoriesNavBar);
        hoverOverElement(hogaryElectrodomesticos);
        click(climatizacion);
        return new SingleCategoryPage(getDriver());
    }

    public SingleCategoryPage goToCelularesSmartphones() {
        hoverOverElement(categoriesNavBar);
        hoverOverElement(tecnologia);
        click(celularesSmartphone);
        return new SingleCategoryPage(getDriver());
    }

    public SingleCategoryPage goToPerfumesImportados() {
        hoverOverElement(categoriesNavBar);
        hoverOverElement(bellezaCuidadoPersonal);
        click(perfumesImportados);
        return new SingleCategoryPage(getDriver());
    }

    public SingleCategoryPage goToIndustriaTextil() {
        hoverOverElement(categoriesNavBar);
        hoverOverElement(herramientasIndustrias);
        click(industriaTextil);
        return new SingleCategoryPage(getDriver());
    }

    public SingleCategoryPage goToCuartoDelBebe() {
        hoverOverElement(categoriesNavBar);
        hoverOverElement(juguetesBebes);
        click(cuartoDelBebe);
        return new SingleCategoryPage(getDriver());
    }
}
