package za.co.absa.utils;

import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverCommandExecutor;
import org.apache.http.client.fluent.Request;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ReadProperties {

    public static void readProperties() throws IOException {
        FileReader configFileReader = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\configfiles\\config.properties");
        FileReader locatorsFileReader = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\configfiles\\locators.properties");
        Properties properties = new Properties();
        Properties locators = new Properties();
        properties.load(configFileReader);
        locators.load(locatorsFileReader);
       // System.out.println(properties.getProperty("application"));
        //properties.getProperty("application");
    }




    protected static void updateImplicitWaitMs(WiniumDriver driver, int waitMs) throws IOException {
        if (driver == null) { return; }
        WiniumDriverCommandExecutor commandExecutor = (WiniumDriverCommandExecutor) driver.getCommandExecutor();
        URL driverUrl = commandExecutor.getAddressOfRemoteServer();
        String timeoutUrl = driverUrl.toString() + "/session/AwesomeSession/timeouts/implicit_wait";
        String postBody = "{ \"SESSIONID\":\"AwesomeSession\", \"ms\":" + waitMs + " }";
        Request.Post(timeoutUrl).bodyByteArray(postBody.getBytes()).execute().returnContent();
    }

}
