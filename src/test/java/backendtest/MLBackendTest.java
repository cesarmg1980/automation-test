package backendtest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static io.restassured.RestAssured.given;

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
        System.out.println("----------------------------------------------------");
        System.out.println("Total items retrieved: " + totalItemsRetrieved);
        System.out.println("Paging Limit: " + pagingItemLimit + ", List size: " + totalItemsInList);

        // We get a random int to use it as an index for the items list
        int randItemIndex = new Random().nextInt(totalItemsInList);
        // We get the necessary properties from the item
        String itemID = json.get("results[" + randItemIndex + "].id");
        String itemTitle = json.get("results[" + randItemIndex + "].title");
        float itemPrice = json.getFloat("results[" + randItemIndex + "].price");
        boolean itemAcceptsMP = json.getBoolean("results[" + randItemIndex + "].accepts_mercadopago");
        String itemCurrency = json.get("results[" + randItemIndex + "].currency_id");
        boolean itemFreeShipping = json.getBoolean("results[" + randItemIndex + "].shipping.free_shipping");

        System.out.println("Random index= " + randItemIndex + ", Random item ID: " + itemID);
        System.out.println("----------------------------------------------------");
        System.out.println("Random item Title: " + itemTitle +
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
        System.out.println("----------------------------------------------------");
        System.out.println(respItemSearchStr);
        System.out.println("----------------------------------------------------");

        // We convert the raw string into Json
        JsonPath jsonItem = new JsonPath(respItemSearchStr);
        String selectedItemName = jsonItem.getString("title");
        float selectedItemPrice = jsonItem.getFloat("price");
        boolean selectedItemAcceptsMP = jsonItem.getBoolean("accepts_mercadopago");
        String selectedItemCurrency = jsonItem.getString("currency_id");
        boolean selectedItemFreeShipping = jsonItem.getBoolean("shipping.free_shipping");

        System.out.println("Selected item Title: " + selectedItemName +
                "\nSelected item Price: " + selectedItemPrice +
                "\nSelected item Accepts MP: " + selectedItemAcceptsMP +
                "\nSelected item Currency: " + selectedItemCurrency +
                "\nSelected item Free Shipping: " + selectedItemFreeShipping);
        System.out.println("----------------------------------------------------");

        if(!(itemTitle.equals(selectedItemName) && itemPrice == selectedItemPrice && itemAcceptsMP == selectedItemAcceptsMP
                && itemCurrency.equals(selectedItemCurrency) && itemFreeShipping == selectedItemFreeShipping)) {
            Assert.fail("Test failed! Properties mismatch!" +
                    "\nRandom Item Title: " + itemTitle + ", Selected Item Title: " + selectedItemName +
                    "\nRandom Item Price: " + itemPrice + ", Selected Item Price: " + selectedItemPrice +
                    "\nRandom Item Accepts MP: " + itemAcceptsMP + ", Selected Item Accepts MP: " + selectedItemAcceptsMP +
                    "\nRandom Item Currency: " + itemCurrency + ", Selected Item Currency: " + selectedItemCurrency +
                    "\nRandom Item Free Shipping: " + itemFreeShipping + ", Selected Item Free Shipping: " + selectedItemFreeShipping);
        } else {
            System.out.println("Test Result Successfull, all items match!");
        }
    }
}


