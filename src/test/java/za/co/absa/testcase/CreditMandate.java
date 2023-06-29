package za.co.absa.testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import za.co.absa.base.BaseClass;
import za.co.absa.utils.fetchCSIDAPI;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreditMandate extends BaseClass {
//  @Test(dataProvider = "scoringNumberTestData")
    public void creditMandate(String scoringNumber, String applicationNumber) throws InterruptedException {
//      driver.findElement(By.id(locators.getProperty("openSearch"))).click();
//
//      driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("actionPerformed"));
//
//      driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();
//
//      driver.findElement(By.id(locators.getProperty("openSearch"))).click();
//      Thread.sleep(5000);
//
//      driver.findElement(By.id(locators.getProperty("acssNumberField"))).sendKeys(scoringNumber);
//
//      driver.findElement(By.id(locators.getProperty("next"))).click();
//Thread.sleep(4000);
        System.out.println("Credit refer CSID Automation");
        driver.findElement(By.id(locators.getProperty("closeCSID"))).click();


        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
        driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("siteSwap"));
        Thread.sleep(3000);
        driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();
        Thread.sleep(3000);
        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).sendKeys(prop.getProperty("siteCode"));
        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).click();
        Actions action = new Actions(driver);
        action.moveByOffset(-3, 56).click().build().perform();
        driver.findElement(By.id(locators.getProperty("siteCodePasswordField"))).sendKeys(System.getenv("password"));
        action.moveByOffset(-3, 56).click().build().perform();
        driver.findElement(By.id(locators.getProperty("siteCodeOKbutton"))).click();
        driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("actionPerformed"));

        driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();

        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
        Thread.sleep(5000);

        driver.findElement(By.id(locators.getProperty("acssNumberField"))).sendKeys(scoringNumber);
        driver.findElement(By.id(locators.getProperty("next"))).click();
        Thread.sleep(5000);
        driver.findElement(By.id(locators.getProperty("next"))).click();
        Thread.sleep(5000);
        driver.findElement(By.id(locators.getProperty("next"))).click();
        Thread.sleep(5000);
        driver.findElement(By.id(locators.getProperty("mandateOffer"))).sendKeys(prop.getProperty("acceptMandateOffer"));
        Thread.sleep(2000);
        driver.findElement(By.id(locators.getProperty("next"))).click();
        Thread.sleep(5000);
        driver.findElement(By.id(locators.getProperty("mandateNumber_ID"))).sendKeys(prop.getProperty("mandateNumber"));
        driver.findElement(By.id(locators.getProperty("manualDecision_ID"))).sendKeys(prop.getProperty("manualDecision"));
        Thread.sleep(2000);
        driver.findElement(By.id(locators.getProperty("next"))).click();
        Thread.sleep(5000);

        try {
            updateImplicitWaitMs(driver, 3000);
            System.out.println("Searching for overrideUI element");
            WebElement findElement1 = driver.findElement(By.id(locators.getProperty("overridePWD")));
            WebElement findElement2 = driver.findElement(By.id(locators.getProperty("overrideOK")));
            Thread.sleep(2000);
            findElement1.sendKeys(System.getenv("password"));
            findElement2.click();

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("False");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(2000);
        element = driver.findElement(By.id(locators.getProperty("csid_assert"))).getText();

        if (!element.equalsIgnoreCase(prop.getProperty("csid_assert_text"))) {
            System.out.println("Not CSID'd");
        } else {
            System.out.println("CSID'ed");
            System.out.println(applicationNumber);
            String postURL = System.getenv("postURL");
            String postBody = "{ \"applicationNumber\":" + applicationNumber + "}";
            String post = given().header("Content-Type", "application/json").baseUri(postURL).body(postBody).when().post().thenReturn().asString();
            System.out.println(post);

//        }
        }


        //Switching back to 3840 to allow next app
        Thread.sleep(5000);
        driver.findElement(By.id(locators.getProperty("closeCSID"))).click();

        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
        driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("siteSwap"));
        Thread.sleep(3000);
        driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();
        Thread.sleep(3000);
        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).sendKeys(prop.getProperty("siteCode2"));
        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).click();
        action.moveByOffset(-3, 56).click().build().perform();
        driver.findElement(By.id(locators.getProperty("siteCodePasswordField"))).sendKeys(System.getenv("password"));
        action.moveByOffset(-3, 56).click().build().perform();
        Thread.sleep(2000);
        driver.findElement(By.id(locators.getProperty("siteCodeOKbutton"))).click();
      driver.findElement(By.id(locators.getProperty("openSearch"))).click();

    }

    @DataProvider(name = "scoringNumberTestData", parallel = false)
    public static Object[][] scoringDataProvFunc() {
        return new Object[][]{
                {"42001866972", "42001867261"}};//, {"42001807776","42001807776"}, {"42001807807","42001807807"},
//        {"42001807823","42001807823"},{"42001808081","42001808081"},{"42001808277","42001808277"}};
        // {"42001808308"},{"42001807360"}};
    }

}
