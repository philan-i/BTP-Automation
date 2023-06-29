import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class ApiTest {


    @Test
    public void apiHealthCheck(){
      baseURI = System.getenv("baseURL");
      RestAssured.useRelaxedHTTPSValidation();
      Response response = given().when().get(baseURI);
      assertEquals(response.getStatusCode(), 200);
       // assertTrue(response.getBody().asStri);
        System.out.println(response);
        System.out.println("Password1".toCharArray());
    }
}
