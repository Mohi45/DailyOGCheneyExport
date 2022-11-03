package com.framework;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RandomAction {
   public static WebDriver driver;

   public static void main(String[] args) {
      System.out.println("main");
   }

   public static String getDate() {
      DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
      Calendar calobj = Calendar.getInstance();
      System.out.println(df.format(calobj.getTime()));
      String CurrentDate = df.format(calobj.getTime());
      return CurrentDate;
   }

   public static boolean isFramePresent(WebDriver driver) throws InterruptedException {
      Thread.sleep(3000L);
      List<WebElement> ele = driver.findElements(By.tagName("frame"));
      System.out.println("Number of frames in a page :" + ele.size());
      if (ele.size() == 0) {
         System.out.println("No frames on this page");
         return false;
      } else {
         System.out.println("Frames present on this page, Below are the details -");
         Iterator var2 = ele.iterator();

         while(var2.hasNext()) {
            WebElement el = (WebElement)var2.next();
            System.out.println("Frame Id :" + el.getAttribute("id"));
            System.out.println("Frame name :" + el.getAttribute("name"));
         }

         return true;
      }
   }

   public static String setdownloadDir() {
      File files = new File(System.getProperty("user.home") + "\\Downloads\\" + getDate());
      if (!files.exists()) {
         if (files.mkdir()) {
            System.out.println("Multiple directory are created!");
         } else {
            System.out.println("Failed to create multiple directory!.. The directory might exist");
         }
      }

      String filepath = files.getPath();
      return filepath;
   }

   public static WebDriver setDownloadFilePath() {
      String downloadFilepath = setdownloadDir();
      HashMap<String, Object> chromePrefs = new HashMap();
      chromePrefs.put("profile.default_content_settings.popups", 0);
      chromePrefs.put("download.default_directory", downloadFilepath);
      ChromeOptions options = new ChromeOptions();
      options.setExperimentalOption("prefs", chromePrefs);
      DesiredCapabilities cap = DesiredCapabilities.chrome();
      cap.setCapability("acceptSslCerts", true);
      cap.setCapability("chromeOptions", options);
      WebDriver driver = new ChromeDriver(cap);
      return driver;
   }

   public static File getLatestFilefromDirxlsx(String dirPath) {
      File getLatestFilefromDir = null;
      File dir = new File(dirPath);
      FileFilter fileFilter = new WildcardFileFilter("*.xlsx");
      File[] files = dir.listFiles(fileFilter);
      if (files.length > 0) {
         Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         getLatestFilefromDir = files[0];
      }

      return getLatestFilefromDir;
   }

   public static File getLatestFilefromDirxls(String dirPath) {
      File getLatestFilefromDir = null;
      File dir = new File(dirPath);
      FileFilter fileFilter = new WildcardFileFilter("*.xls");
      File[] files = dir.listFiles(fileFilter);
      if (files.length > 0) {
         Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         getLatestFilefromDir = files[0];
      }

      return getLatestFilefromDir;
   }

   public static File getLatestFilefromDirPDF(String dirPath) {
      File getLatestFilefromDir = null;
      File dir = new File(dirPath);
      FileFilter fileFilter = new WildcardFileFilter("*.pdf");
      File[] files = dir.listFiles(fileFilter);
      if (files.length > 0) {
         Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         getLatestFilefromDir = files[0];
      }

      return getLatestFilefromDir;
   }

   public static File getLatestFilefromDirCsv(String dirPath) {
      File getLatestFilefromDir = null;
      File dir = new File(dirPath);
      FileFilter fileFilter = new WildcardFileFilter("*.csv");
      File[] files = dir.listFiles(fileFilter);
      if (files.length > 0) {
         Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         getLatestFilefromDir = files[0];
      }

      return getLatestFilefromDir;
   }

   public static void DownloadOG(Robot robot, WebDriver driver) {
      try {
         robot = new Robot();
         Thread.sleep(2000L);
         robot.keyPress(40);
         Thread.sleep(2000L);
         robot.keyPress(9);
         Thread.sleep(2000L);
         robot.keyPress(9);
         Thread.sleep(2000L);
         robot.keyPress(9);
         Thread.sleep(2000L);
         robot.keyPress(10);
         System.out.println("File is downloaded");
      } catch (AWTException var3) {
         ErrRemedy.ErrScreenshotCapture(driver);
         var3.printStackTrace();
      } catch (InterruptedException var4) {
         ErrRemedy.ErrReportingMail();
         var4.printStackTrace();
      }

   }
   public static void deleteFiles(String path, String extension) {
      File dir = new File(path);
      // FileUtils.cleanDirectory(dir);
      for (File file : dir.listFiles())
         if (!file.isDirectory() && file.getName().contains(extension))
            file.delete();
      System.out.println("All files deleted from folder :-" + path);

   }
   public static void deleteFiles(String path) {
      File dir = new File(path);
      File[] var2 = dir.listFiles();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File file = var2[var4];
         if (!file.isDirectory()) {
            file.delete();
         }
      }

      System.out.println("All files deleted from folder :-" + path);
   }

   public static WebDriver openBrowser(String browser, String path) {
      if (browser.equalsIgnoreCase("ie")) {
         System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Downloads\\chromedriver_win32\\ie.exe");
         driver = new InternetExplorerDriver();
      } else if (browser.equalsIgnoreCase("ff")) {
         driver = new FirefoxDriver();
      } else if (browser.equalsIgnoreCase("gecko")) {
         System.setProperty("webdriver.gecko.driver", path);
         driver = new FirefoxDriver();
         driver.manage().window().maximize();
      } else {
         ChromeOptions options = new ChromeOptions();
         options.addArguments("start-maximized");
         System.setProperty("webdriver.chrome.driver", path);
         driver = new ChromeDriver(options);
      }

      return driver;
   }
   public static WebDriver launchBrowser() {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless");
      options.addArguments( "--window-size=1920,1200");
      options.addArguments("disable-infobars"); // disabling infobars
      options.addArguments("--disable-extensions"); // disabling extensions
      options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
      options.addArguments("--no-sandbox"); // Bypass OS security model
      WebDriverManager.chromedriver().driverVersion("106.0.5249.119").setup();
      driver = new ChromeDriver(options);
      driver.manage().window().maximize();
      return driver;
   }

   public static boolean isAlertPresent() {
      try {
         driver.switchTo().alert();
         return true;
      } catch (NoAlertPresentException var1) {
         return false;
      }
   }

   public static void acceptAlert() {
      try {
         driver.switchTo().alert().accept();
      } catch (NoAlertPresentException var1) {
         var1.printStackTrace();
      }

   }

   public static void dismissAlert() {
      try {
         driver.switchTo().alert().dismiss();
      } catch (NoAlertPresentException var1) {
         var1.printStackTrace();
      }

   }

   public static void errorScreenshot(WebDriver driver, String orderID) {
      try {
         File src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
         FileUtils.copyFile(src, new File("C:\\Users\\Edge\\Desktop\\Reports\\Screenshots\\" + orderID + ".png"));
      } catch (Exception var3) {
         System.out.println("Screenshot failed");
         var3.printStackTrace();
      }

   }

   public static boolean isIframePresent(WebDriver driver) throws InterruptedException {
      Thread.sleep(3000L);
      List<WebElement> ele = driver.findElements(By.tagName("iframe"));
      System.out.println("Number of frames in a page :" + ele.size());
      if (ele.size() == 0) {
         System.out.println("No frames on this page");
         return false;
      } else {
         System.out.println("Frames present on this page, Below are the details -");
         Iterator var2 = ele.iterator();

         while(var2.hasNext()) {
            WebElement el = (WebElement)var2.next();
            System.out.println("Frame Id :" + el.getAttribute("id"));
            System.out.println("Frame name :" + el.getAttribute("name"));
         }

         return true;
      }
   }
}