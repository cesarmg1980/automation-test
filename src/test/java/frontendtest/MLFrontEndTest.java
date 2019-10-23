package frontendtest;

import frontend.MLHomePage;
import frontend.SingleCategoryPage;
import frontend.SingleItemPage;
import org.testng.annotations.Test;

public class MLFrontEndTest extends BaseWebTest {

    @Test
    public void climatizacionTest() throws InterruptedException {
        MLHomePage home = new MLHomePage(driver);
        home.goToMLHomePage(properties.getProperty("ml.home.url"));

        SingleCategoryPage climatizacion = home.navbar.goToClimatizacionPage();
        climatizacion.validateCategory("Climatización");
        System.out.println("Quantity of elements retrieved by the search: " + climatizacion.getResultsQty());
        Thread.sleep(5000);
    }

    @Test
    public void celularesSmartphonesTest() throws InterruptedException {
        MLHomePage home = new MLHomePage(driver);
        home.goToMLHomePage(properties.getProperty("ml.home.url"));

        SingleCategoryPage celularesSmartphones = home.navbar.goToCelularesSmartphones();
        celularesSmartphones.validateCategory("Celulares y Smartphones");
        System.out.println("Quantity of elements retrieved by the search: " + celularesSmartphones.getResultsQty());
        Thread.sleep(5000);
    }

    @Test
    public void industriaTextilTest() throws InterruptedException {
        MLHomePage home = new MLHomePage(driver);
        home.goToMLHomePage(properties.getProperty("ml.home.url"));

        SingleCategoryPage industriaTextil = home.navbar.goToIndustriaTextil();
        industriaTextil.validateCategory("Industria Textil");
        System.out.println("Quantity of elements retrieved by the search: " + industriaTextil.getResultsQty());
        Thread.sleep(5000);
    }

    @Test
    public void cuartoDelBebeTest() throws InterruptedException {
        MLHomePage home = new MLHomePage(driver);
        home.goToMLHomePage(properties.getProperty("ml.home.url"));

        SingleCategoryPage cuartoDelBebe = home.navbar.goToCuartoDelBebe();
        cuartoDelBebe.validateCategory("Cuarto del Bebé");
        System.out.println("Quantity of elements retrieved by the search: " + cuartoDelBebe.getResultsQty());
        Thread.sleep(5000);
    }

    @Test
    public void perfumesImportadosTest() throws InterruptedException {
        MLHomePage home = new MLHomePage(driver);
        home.goToMLHomePage(properties.getProperty("ml.home.url"));

        SingleCategoryPage perfumesImportados = home.navbar.goToPerfumesImportados();
        perfumesImportados.validateCategory("Perfumes Importados");
        System.out.println("Quantity of elements retrieved by the search: " + perfumesImportados.getResultsQty());
        Thread.sleep(5000);
    }

    @Test
    public void singleItemTest() throws InterruptedException {
        MLHomePage home = new MLHomePage(driver);
        home.goToMLHomePage(properties.getProperty("ml.home.url"));

        SingleCategoryPage celularesSmartphones = home.navbar.goToCelularesSmartphones();
        celularesSmartphones.selectUbicacionCapFed();
        SingleItemPage singleItem = celularesSmartphones.selectSingleRandomElementFromListOfResults();
        singleItem.validateElementByTitleAndPrice();
        Thread.sleep(5000);
    }
}
