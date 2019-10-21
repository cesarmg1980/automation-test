package backendtest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class MLBackendTest {

    private static final int REQUEST_STATUS_OK = 200;

    @Test
    public  void mlProductsTest () {
        //initial api test
        RestAssured.baseURI = "https://api.mercadolibre.com";
        Response respProdSearch = given()
            .param("q", "placas de video")
            .when()
            .get("sites/MLA/search")
            .then()
            .assertThat()
            .statusCode(REQUEST_STATUS_OK)
            .extract().response();
        String respProdSearchStr = respProdSearch.asString();
        System.out.println(respProdSearchStr);

        JsonPath json = new JsonPath(respProdSearchStr);
        List<Object> resultsObjList = json.getList("results");
        System.out.println("Size of results list: " + resultsObjList.size());
        String firstItemId = json.get("results[0].id");

        System.out.println("First Item ID: " + firstItemId);

        Response respItemSearch = given()
                .pathParam("id_producto", firstItemId)
                .when()
                .get("items/{id_producto}")
                .then()
                .assertThat()
                .statusCode(REQUEST_STATUS_OK)
                .extract().response();

        String respItemSearchStr = respItemSearch.asString();
        System.out.println(respItemSearchStr);
    }

}
