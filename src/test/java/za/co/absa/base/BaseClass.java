package za.co.absa.base;

import org.apache.http.client.fluent.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverCommandExecutor;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import za.co.absa.utils.ReadProperties;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class BaseClass extends ReadProperties{
    public static WiniumDriver driver = null;
    public static WiniumDriverService service = null;
    public static DesktopOptions options = null;
    public static FileReader configFileReader;
    public static FileReader locatorsFileReader;
    public static Properties prop = new Properties();
    public static Properties locators = new Properties();
    public static String element;



    @BeforeTest
    public void setUp() throws IOException {


        configFileReader = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\configfiles\\config.properties");
        locatorsFileReader = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\configfiles\\locators.properties");
        prop.load(configFileReader);
        locators.load(locatorsFileReader);
        options = new DesktopOptions();
        options.setApplicationPath(prop.getProperty("application"));
        File driverPath;
        driverPath = new File(System.getProperty("user.dir")+prop.getProperty("winiumdriverPath"));
        WiniumDriverService.Builder builder = new WiniumDriverService.Builder();
        builder.usingDriverExecutable(driverPath);
        builder.usingPort(Integer.parseInt(prop.getProperty("winiumdriverport")));
        builder.withVerbose(true);
        builder.withSilent(false);
        service = builder.buildDesktopService();
        try {
            service.start();
        } catch (IOException e) {
            System.out.println("Exception while starting WINIUM service");
            e.printStackTrace();
        }
        driver = new WiniumDriver(service, options);

    }

//    @AfterTest
//    public void tearDown() throws InterruptedException, RuntimeException {
//
//        driver.findElement(By.id(locators.getProperty("openSearch"))).click();
//
//        driver.findElement(By.id(locators.getProperty("btpSearch"))).sendKeys(prop.getProperty("logout"));
//        Thread.sleep(3000);
//        driver.findElement(By.name(locators.getProperty("btpSearchResult"))).click();
//        service.stop();
//
//    }

    protected static void updateImplicitWaitMs(WiniumDriver driver, int waitMs) throws IOException {
        if(driver==null){return;}
        WiniumDriverCommandExecutor commandExecutor = (WiniumDriverCommandExecutor) driver.getCommandExecutor();
        URL driverUrl = commandExecutor.getAddressOfRemoteServer();
        String timeoutUrl = driverUrl.toString() + "/session/AwesomeSession/timeouts/implicit_wait";
        String postBody = "{ \"SESSIONID\": \"AwesomeSession\", \"ms\": "+ Integer.toString(waitMs) +"}" ;
        Request.Post(timeoutUrl).bodyByteArray(postBody.getBytes()).execute().returnContent();
        System.out.println(postBody);
    }

    protected static void saveScreenshot(BufferedImage screenshot, String filename) throws IOException {

        File output = new File("S:\\AB016PN\\btp\\btpAutomation\\src\\test\\resources\\screenshots\\" + filename);
        ImageIO.write(screenshot, "png", output);
    }


}
