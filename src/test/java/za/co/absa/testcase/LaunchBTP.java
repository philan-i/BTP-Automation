package za.co.absa.testcase;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import za.co.absa.base.BaseClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LaunchBTP extends BaseClass {

    @Test
    public static void launchBTP() throws InterruptedException, IOException {
        updateImplicitWaitMs(driver, 20000);

        driver.findElement(By.id("1003")).click();
        driver.findElement(By.name(locators.getProperty("popUserField"))).click();
        driver.findElement(By.id(locators.getProperty("popUpOK"))).click();
        driver.findElement(By.id(locators.getProperty("pwdField"))).click();
        driver.findElement(By.id(locators.getProperty("userField"))).click();
        driver.findElement(By.name(locators.getProperty("popUserField"))).click();
        File screenshot =((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage pageScreenshot = ImageIO.read(screenshot);
        saveScreenshot(pageScreenshot, "test.png");
        FileUtils.copyFile(screenshot, new File("S:\\AB016PN\\btp\\btpAutomation\\src\\test\\resources\\screenshot.png"));
        driver.findElement(By.id(locators.getProperty("popUpOK"))).click();
        driver.findElement(By.id(locators.getProperty("pwdField"))).sendKeys(System.getenv("password"));
        driver.findElement(By.id(locators.getProperty("btpLogin"))).click();
        //Assert.assertEquals("Verify login", "Processing Xml information", String.valueOf(driver.findElement(By.id(locators.getProperty("lblMessage")))));
        System.out.println("BTP Launched Successfully");


    }

    public static void main(String[]args) throws IOException, InterruptedException {
        launchBTP();
    }
}
