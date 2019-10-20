package backendtest;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class MLBackendTest {

    private static final int REQUEST_OK_STATUS = 200;
    private static final String REQUEST_FORMAT = "application/json";

    @Test
    public  void mlProductsTest () {
        RestAssured.baseURI = "https://api.mercadolibre.com";
        given().param("q", "placas de video")
                .when()
                .get("sites/MLA/search")
                .then()
                .assertThat()
                .statusCode(REQUEST_OK_STATUS)
                .and()
                .contentType(REQUEST_FORMAT)
                .and()
                .body("paging.limit", equalTo(50));
    }
}
