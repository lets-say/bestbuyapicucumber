package com.bestbuy.testbase.cucumber.steps;

import com.bestbuy.bestbuyinfo.ProductsSteps;
import com.bestbuy.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ProductStepdefs {

    public static String name="Samsung TV";
    public static int productID;

    @Steps
    ProductsSteps productsSteps;

    @When("^User creates new product record with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void userCreatesNewProductRecordWith(String name, String type, double price, String upc, int shipping, String description, String manufacturer, String model, String url, String image)  {
        productsSteps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image).log().all().statusCode(201);

    }

    @And("^User can search new record using product \"([^\"]*)\"$")
    public void userCanSearchNewRecordUsingProduct(String name)  {
        HashMap<String,Object> product = productsSteps.verifyProductInformation(name);

        productID = (int) product.get("id");

        Assert.assertThat(product,hasValue(name));
    }

    @And("^User can update new record using productID, name, \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void userCanUpdateNewRecordUsingProductIDName(String type, double price, String upc, int shipping, String description, String manufacturer, String model, String url, String image)  {
        name= name+ TestUtils.getRandomValue();

        productsSteps.changeProduct(productID,name, type, price, upc, shipping, description, manufacturer, model, url, image).log().all();
    }

    @And("^User can delete new record of productID$")
    public void userCanDeleteNewRecordOfProductID() {
        productsSteps.deleteProduct(productID).log().all().statusCode(200);
    }

    @Then("^User verify the product is deleted successfully$")
    public void userVerifyTheProductIsDeletedSuccessfully() {
        productsSteps.verifyProductDeleted(productID).log().all().statusCode(404);
    }
}
