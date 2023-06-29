package za.co.absa.testcase;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class postToDB {

    //Not In use, logic changed.
    @DataProvider(name = "postToDB")
    public static Object[][] postScoringNumber() {
        ArrayList<String> applicationNumber = when().get(System.getenv("baseURL")).then().extract().path("applicationNumber");
        Object[][] testData = new Object[applicationNumber.size()][1];
        for (int i = 0; i < applicationNumber.size(); i++) {
            testData[i][0] = applicationNumber.get(i);
            System.out.println(testData[i][0]);
            String postURL = System.getenv("postURL");
            String postBody = "{ \"applicationNumber\":" + testData[i][0] + "}";

            String post = given().header("Content-Type", "application/json").baseUri(postURL).body(postBody).when().post().thenReturn().asString();
            System.out.println(post);

        }

        return testData;
    }
}