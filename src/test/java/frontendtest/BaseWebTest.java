package frontendtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class BaseWebTest {
    public WebDriver driver;
    Properties properties = new Properties();

    @BeforeClass
    public void setupTest() throws IOException {
        // We load the properties file
        String cwd = System.getProperty("user.dir");
        FileInputStream propFile = new FileInputStream(cwd + "\\src\\main\\resources\\config.properties");
        this.properties.load(propFile);

        // We create a new property key=webdriver.chrome.driver, value=path_to_driver
        System.setProperty("webdriver.chrome.driver", this.properties.getProperty("chrome.driver.path"));

        // We instantiate a new chromedriver
        this.driver = new ChromeDriver(new ChromeOptions());
        driver.manage().window().maximize();
    }

    @AfterClass
    public void teardownTest() {
        driver.quit();
    }
}
