package za.co.absa.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import za.co.absa.base.BaseClass;

public class fetchCSIDAPI extends BaseClass {


    @DataProvider(name = "scoringNumberTestData")
    public static Object[][] getScoringNumber() {
//        ArrayList<String> scoringNumber = when().get(System.getenv("baseURL")).then().extract().path("scoringNumber", "applicationNumber");
//        ArrayList<String> applicationNumber = when().get(System.getenv("baseURL")).then().extract().path("applicationNumber");

////
//        Object[][] scoringNumberTestData = new Object[scoringNumber.size()][1];
//        Object[][] applicationNumberTestData = new Object[applicationNumber.size()][1];
//        for (int i = 0; i < scoringNumber.size(); i++) {
//            scoringNumberTestData[i][0] = scoringNumber.get(i);
//            System.out.println(scoringNumberTestData[i][0]);
//
//        }
//        for(int j = 0; j < applicationNumber.size(); j++){
//            applicationNumberTestData[j][0] = applicationNumber.get(j);
//            System.out.println(applicationNumberTestData[j][0]);
//        }
//        return scoringNumberTestData;


        Response response = RestAssured.get(System.getenv("baseURL"));
        String[] scoringNumber = response.jsonPath().getList("scoringNumber").toArray(new String[0]);
        String[] applicationNumber = response.jsonPath().getList("applicationNumber").toArray(new String[0]);

        Object[][] data = new Object[scoringNumber.length][2];
        for (int i = 0; i < scoringNumber.length; i++) {
            data[i][0] = scoringNumber[i];
            data[i][1] = applicationNumber[i];
            // System.out.println(data[i][1]);
        }

        return data;
    }


}



