package backendtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class MLBackendTest {

    private static final int REQUEST_OK_STATUS = 200;

    @Test
    public  void mlProductsTest () {
        RestAssured.baseURI = "https://api.mercadolibre.com";
        Response response = given().param("q", "placas de video")
                .when()
                .get("sites/MLA/search")
                .then()
                .assertThat()
                .statusCode(REQUEST_OK_STATUS)
                .extract().response();
        System.out.println(response.asString());
    }
}
