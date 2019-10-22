package frontendtest;

import frontend.MLHomePage;
import org.testng.annotations.Test;

public class MLFrontEndTest extends BaseWebTest {

    @Test
    public void basicTest() {
        MLHomePage home = new MLHomePage(driver);
        home.goToMLHomePage(properties.getProperty("ml.home.url")).clickOnCategories();
    }
}
