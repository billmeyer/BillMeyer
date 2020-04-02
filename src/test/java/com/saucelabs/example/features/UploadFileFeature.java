package com.saucelabs.example.features;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class UploadFileFeature extends BaseFeature
{
    private static String username = System.getenv("SAUCE_USERNAME");
    private static String accessKey = System.getenv("SAUCE_ACCESS_KEY");

    @Test
    public void uploadFileViaWebBrowser()
    throws MalformedURLException
    {
        URL url = new URL("https://ondemand.saucelabs.com:443/wd/hub");

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "79.0");

        caps.setCapability("username", username);
        caps.setCapability("accessKey", accessKey);
        caps.setCapability("name", "File Upload Example");
        caps.setCapability("tunnelIdentifier", "qa-vdc-1");

        RemoteWebDriver driver = new RemoteWebDriver(url, caps);
        driver.setFileDetector(new LocalFileDetector());

        // Navigate to our web page with the upload feature...
        String webUrl = "http://imac1.billmeyer.corp:8080";
        driver.navigate().to(webUrl);

        // Find the text field on the web page that will hold the path to the file the user selects for upload...
        WebElement fileToUploadElem = driver.findElement(By.id("file-to-upload"));

        // Find the upload/submit button...
        WebElement submitElem = driver.findElement(By.id("submit"));

        // Specify the path to the file we want to upload.  Relative to where the test script runs from
        // or use an absolute path.
        fileToUploadElem.sendKeys("./shakespeare.txt");

        // Submit the upload.
        submitElem.click();

        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("sauce:job-result=true");

        driver.quit();
    }
}
