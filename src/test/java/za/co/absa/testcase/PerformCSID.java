package za.co.absa.testcase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import za.co.absa.base.BaseClass;
import za.co.absa.utils.fetchCSIDAPI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static za.co.absa.testcase.CreditMandate.*;

public class PerformCSID extends BaseClass {


    @Test(dataProvider = "scoringNumberTestData", dataProviderClass = fetchCSIDAPI.class)
    public static void performCSID(String scoringNumber, String applicationNumber) throws InterruptedException, IOException {

        updateImplicitWaitMs(driver, 200000);


        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
//        driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("siteSwap"));
//        Thread.sleep(3000);
//        driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();
//        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).sendKeys(prop.getProperty("siteCode"));
//        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).click();
//        Actions action = new Actions(driver);
//        action.moveByOffset(-3, 56).click().build().perform();
//        driver.findElement(By.id(locators.getProperty("siteCodePasswordField"))).sendKeys(System.getenv("password"));
//        action.moveByOffset(-3, 56).click().build().perform();
//        driver.findElement(By.id(locators.getProperty("siteCodeOKbutton"))).click();
        driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("actionPerformed"));

        driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();

        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
        Thread.sleep(5000);

        driver.findElement(By.id(locators.getProperty("acssNumberField"))).sendKeys(scoringNumber);

        driver.findElement(By.id(locators.getProperty("next"))).click();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        element = driver.findElement(By.id(locators.getProperty("credit_refer_assert_id"))).getText();

        if (element.equalsIgnoreCase(prop.getProperty("isCreditRefer"))) {
            CreditMandate creditMandate = new CreditMandate();
            creditMandate.creditMandate(scoringNumber, applicationNumber);
        } else {


            driver.findElement(By.id(locators.getProperty("next"))).click();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.findElement(By.id(locators.getProperty("next"))).click();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.findElement(By.id(locators.getProperty("firstOffer"))).sendKeys(prop.getProperty("acceptOffer"));
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.findElement(By.id(locators.getProperty("next"))).click();
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                Response response = RestAssured.given().header("Content-Type", "application/json").baseUri(postURL).body(postBody).when().post();
                String post = given().header("Content-Type", "application/json").baseUri(postURL).body(postBody).when().post().thenReturn().asString();
                System.out.println(post);
                int statusCode = response.getStatusCode();
                if (statusCode!=200) {
                    String url = "jdbc:postgresql://zaurnbmweb0283:5432/scenario2";
                    String username = "postgres";
                    String password = "admin";

                    try {
                        Class.forName("org.postgresql.Driver");
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();
                        String query = "update csid_tests set status = ? where scoring_number = ?";
                        String status = "Complete";
                        String scoring_number = scoringNumber;
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, status);
                        preparedStatement.setString(2, scoring_number);
//                        ResultSet resultSet = statement.executeQuery(query);
//                        resultSet.close();
//                        statement.close();
                        int rowsUpdated = preparedStatement.executeUpdate();
                        System.out.println("Rows Affected: " + rowsUpdated);
                        System.out.println("Completed Manually");
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Posted to val automatically");
                }
//
//        }
            }


            //Switching back to 3840 to allow next app
            Thread.sleep(5000);
            driver.findElement(By.id(locators.getProperty("closeCSID"))).click();

//        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
//        driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("siteSwap"));
//        Thread.sleep(1500);
//        driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();
//        Thread.sleep(5000);
//        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).sendKeys(prop.getProperty("siteCode2"));
//        Thread.sleep(1500);
//        driver.findElement(By.id(locators.getProperty("siteCodeSearch"))).click();
//        Thread.sleep(1500);
//        action.moveByOffset(-3, 56).click().build().perform();
//        Thread.sleep(1500);
//        driver.findElement(By.id(locators.getProperty("siteCodePasswordField"))).sendKeys(System.getenv("password"));
//        Thread.sleep(1500);
//        action.moveByOffset(-3, 56).click().build().perform();
//        Thread.sleep(3000);
//        driver.findElement(By.id(locators.getProperty("siteCodeOKbutton"))).click();
//        Thread.sleep(1500);
//        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
//        Thread.sleep(3000);


        }
    }

    //
    @DataProvider(name = "scoringNumberTestData", parallel = false)
    public static Object[][] scoringDataProvFunc() {
        return new Object[][]{
                {"42001877183", "42001877183"}, {"42001877191","42001877191"}, {"42001877206","42001877206"}};
//        {"42001807823","42001807823"},{"42001808081","42001808081"},{"42001808277","42001808277"}};
        // {"42001808308"},{"42001807360"}}; 42001866760, 42001867261
    }


    @DataProvider(name = "postToDB", parallel = false)
    public static Object[][] applicationDataProvFunc() {
        return new Object[][]{
                {"42001807310"}, {"42001807776"}, {"42001807807"}};
//                {"42001807823"},{"42001808081"},{"42001808277"},
//                {"42001808308"},{"42001807360"}};

    }

}
//
