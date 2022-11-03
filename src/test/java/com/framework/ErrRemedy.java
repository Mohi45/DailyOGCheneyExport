package com.framework;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ErrRemedy {
   public static void ErrScreenshotCapture(WebDriver driver) {
      File src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

      try {
         FileUtils.copyFile(src, new File(System.getProperty("user.home")+"\\Downloads\\errorScreenshotGFS\\error.png"));
      } catch (Exception var3) {
         System.out.println("take screenshot failure");
         var3.printStackTrace();
      }

   }

   public static void ErrReportingMail() {
   }
}