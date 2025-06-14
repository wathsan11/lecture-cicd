package org.example.util;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

public class Screenshot {
    static public void takeScreenshot(WebDriver driver, String fileName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            var filePath = "./screenshots/" + fileName;
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved at " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
