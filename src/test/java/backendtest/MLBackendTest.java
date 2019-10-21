package backendtest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class MLBackendTest {

    private static final int REQUEST_STATUS_OK = 200;
    Properties properties = new Properties();

    @BeforeTest
    public void getProperties() throws IOException {
        String cwd = System.getProperty("user.dir");
        FileInputStream propFile = new FileInputStream(cwd + "\\src\\main\\resources\\config.properties");
        this.properties.load(propFile);
    }

    @Test
    public  void mlProductsTest () {
        //initial api test
        RestAssured.baseURI = properties.getProperty("ml.host");
        Response respProdSearch = given()
            .param(this.properties.getProperty("ml.items.search.param"),
                    this.properties.getProperty("ml.item.to.search"))
            .when()
            .get(this.properties.getProperty("ml.resources.search"))
            .then()
            .assertThat()
            .statusCode(REQUEST_STATUS_OK)
            .extract().response();

        // We convert the response into raw String
        String respProdSearchStr = respProdSearch.asString();
        System.out.println(respProdSearchStr);

        // We get the total items retrieved by the endpoint
        // along with the paging limit, the List of Items
        // and the amount of elements in the retrieved list
        // finally we assert for equality among these two
        JsonPath json = new JsonPath(respProdSearchStr);
        int totalItemsRetrieved = json.get("paging.total");
        int pagingItemLimit = json.get("paging.limit");
        List<Object> listOfItemsRetrieved = json.getList("results");
        int totalItemsInList = listOfItemsRetrieved.size();
        Assert.assertEquals(pagingItemLimit, totalItemsInList,
                "Test Failed! List size doesn't match paging limit");
        System.out.println("Total items retrieved: " + totalItemsRetrieved);
        System.out.println("Paging Limit: " + pagingItemLimit + ", List size: " + totalItemsInList);

        // We get a random item from the list
        int randItemIndex = getRandomIndexFromList(json.getList("results"));
        // We get the necessary properties from the item
        String itemID = json.get("results[" + randItemIndex + "].id");
        String itemTitle = json.get("results[" + randItemIndex + "].title");
        float itemPrice = json.getFloat("results[" + randItemIndex + "].price");
        boolean itemAcceptsMP = json.getBoolean("results[" + randItemIndex + "].accepts_mercadopago");
        String itemCurrency = json.get("results[" + randItemIndex + "].currency_id");
        boolean itemFreeShipping = json.getBoolean("results[" + randItemIndex + "].shipping.free_shipping");

        System.out.println("Random index= " + randItemIndex +
                "\nRandom item ID: " + itemID +
                "\nRandom item Title: " + itemTitle +
                "\nRandom item Price: " + itemPrice +
                "\nRandom item Accepts MP: " + itemAcceptsMP +
                "\nRandom item Currency: " + itemCurrency +
                "\nRandom item Free Shipping: " + itemFreeShipping);

        Response respItemSearch = given()
                .pathParam("id_producto", itemID)
                .when()
                .get(this.properties.getProperty("ml.resources.items") + "{id_producto}")
                .then()
                .assertThat()
                .statusCode(REQUEST_STATUS_OK)
                .extract().response();

        // We get Item response into raw string
        String respItemSearchStr = respItemSearch.asString();
        System.out.println(respItemSearchStr);

        // We convert the raw string into Json
        JsonPath jsonItem = new JsonPath(respProdSearchStr);
    }

    public int getRandomIndexFromList(List<Object> list) {
        return new Random().nextInt(list.size());
    }

}

