package com.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonCheney {
   public static WebDriver driver;
   public static WebDriverWait wait;
   private static final Logger logger = Logger.getLogger(CommonCheney.class);
   public static int retry = 0;
   public static int maxretry = 3;

   public boolean LoginCheney(WebDriver driver, String usernameCBI, String passwordCBI) throws InterruptedException {
      driver.get("http://www.procurement.itradenetwork.com/Platform/Membership/Login");
      Thread.sleep(2000L);
      wait = new WebDriverWait(driver, 30L);
      WebElement chb_Username = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'username')]"))));
      chb_Username.sendKeys(usernameCBI);
      WebElement chb_Password = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'password')]"))));
      chb_Password.sendKeys(passwordCBI);
      driver.findElement(By.xpath("//input[contains(@id,'rememberMe')]")).click();
      WebElement btn_Login = (WebElement)wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[contains(@value,'Login')]"))));
      btn_Login.click();
      System.out.println("Login Successful");
      Thread.sleep(5000L);

      try {
         driver.get("http://www.procurement.itradenetwork.com/Platform/Products/BrowseProducts/Browse");
         Thread.sleep(5000L);
      } catch (Exception var15) {
         var15.printStackTrace();
         System.out.println("using URL for Order Guide");
      }

      try {
         Thread.sleep(3000L);
         driver.findElement(By.xpath("//a[contains(@id,'ExportGridButton')]/span/*")).click();
         System.out.println("Clicked - Export Grid");
      } catch (NoSuchElementException var13) {
         var13.printStackTrace();
      } catch (WebDriverException var14) {
         var14.printStackTrace();
      }

      Thread.sleep(2000L);
      WebElement lnk_ExportTyp = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(@id,'ExportType')]/span/*"))));
      lnk_ExportTyp.click();
      WebElement ddl_Excel = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(.,'Excel')]"))));
      ddl_Excel.click();
      System.out.println("format choosen as Excel");

      while(retry < maxretry && !this.removeColumns()) {
         ++retry;
      }

      List<WebElement> OG_Col = driver.findElements(By.xpath("//ul[@id='Sortable']/*"));
      System.out.println(OG_Col.size());
      Assert.assertEquals((long)OG_Col.size(), 7L);
      WebElement lnk_Download = (WebElement)wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(@id,'DownloadButton')]/*/*/*/*/img[@class='rtbIcon']"))));
      lnk_Download.click();
      Thread.sleep(1000);
      try {
         WebElement lnk_Logout = (WebElement)wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(.,'Logout')]"))));
         lnk_Logout.click();
      } catch (Exception var12) {
         var12.printStackTrace();
      }

      Thread.sleep(3000L);
      return true;
   }

   public boolean LoginCheney(WebDriver driver, String OGName, String usernameCBI, String passwordCBI) throws InterruptedException {
      driver.get("http://www.procurement.itradenetwork.com/Platform/Membership/Login");
      Thread.sleep(2000L);
      wait = new WebDriverWait(driver, 30L);
      WebElement chb_Username = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'username')]"))));
      chb_Username.sendKeys(new CharSequence[]{usernameCBI});
      WebElement chb_Password = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'password')]"))));
      chb_Password.sendKeys(new CharSequence[]{passwordCBI});
      driver.findElement(By.xpath("//input[contains(@id,'rememberMe')]")).click();
      WebElement btn_Login = (WebElement)wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[contains(@value,'Login')]"))));
      btn_Login.click();
      System.out.println("Login Successful");
      Thread.sleep(2000L);
      WebElement lnk_Ordering = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(.,'Ordering')]"))));
      lnk_Ordering.click();
      Thread.sleep(2000L);
      List<WebElement> allElements = driver.findElements(By.xpath("//a[contains(.,'Ordering')]/following-sibling::div/ul/li/*/*/div/a"));
      System.out.println(allElements.size());
      Thread.sleep(1000L);
      Iterator var10 = allElements.iterator();

      WebElement ddl_Excel;
      while(var10.hasNext()) {
         ddl_Excel = (WebElement)var10.next();
         if (ddl_Excel.getText().equalsIgnoreCase("Custom Order Guides")) {
            String OG_text = ddl_Excel.getText();
            ddl_Excel.click();
            System.out.println("Clicked on link - " + OG_text);
            break;
         }
      }

      Thread.sleep(5000L);
      driver.findElement(By.xpath("//td[2]/a[contains(.,'" + OGName + "')]")).click();
      System.out.println("custom guide selected");

      try {
         driver.findElement(By.xpath("//a[contains(@id,'ExportGridButton')]/span/*")).click();
         System.out.println("Clicked - Export Grid");
      } catch (NoSuchElementException var15) {
         var15.printStackTrace();
         Thread.sleep(3000L);
         driver.findElement(By.xpath("//a[contains(@id,'ExportGridButton')]/span/*")).click();
         System.out.println("Clicked - Export Grid");
      } catch (WebDriverException var16) {
         var16.printStackTrace();
         Thread.sleep(3000L);
         driver.findElement(By.xpath("//a[contains(@id,'ExportGridButton')]/span/*")).click();
         System.out.println("Clicked - Export Grid");
      }

      Thread.sleep(1000L);
      WebElement lnk_ExportTyp = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(@id,'ExportType')]/span/*"))));
      lnk_ExportTyp.click();
      ddl_Excel = (WebElement)wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(.,'Excel')]"))));
      ddl_Excel.click();
      System.out.println("format choosen as Excel");
      Thread.sleep(2000L);

      while(retry < maxretry && !this.removeColumns()) {
         ++retry;
      }

      List<WebElement> OG_Col = driver.findElements(By.xpath("//ul[@id='Sortable']/*"));
      System.out.println(OG_Col.size());
      Assert.assertEquals((long)OG_Col.size(), 7L);
      WebElement lnk_Download = (WebElement)wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(@id,'DownloadButton')]"))));
      lnk_Download.click();
      WebElement lnk_Logout = (WebElement)wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(.,'Logout')]"))));
      lnk_Logout.click();
      Thread.sleep(5000L);
      return true;
   }

   public boolean removeColumns() {
      try {
         Thread.sleep(3000L);
         ArrayList<String> removeColumns = new ArrayList();
         List<WebElement> OG_Col = driver.findElements(By.xpath("//ul[@id='Sortable']/*"));
         System.out.println(OG_Col.size());
         Iterator iterator = OG_Col.iterator();

         while(true) {
            while(iterator.hasNext()) {
               WebElement element = (WebElement)iterator.next();
               Thread.sleep(1000L);
               String Col_id = element.getAttribute("id");
               if (!Col_id.equalsIgnoreCase("Sku") && !Col_id.equalsIgnoreCase("DistributorNumber") && !Col_id.equalsIgnoreCase("CaseUom") && !Col_id.equalsIgnoreCase("UnitOfMeasure") && !Col_id.equalsIgnoreCase("CasePack") && !Col_id.equalsIgnoreCase("PackDescription") && !Col_id.equalsIgnoreCase("Description") && !Col_id.equalsIgnoreCase("ProductDescription") && !Col_id.equalsIgnoreCase("BrandName") && !Col_id.equalsIgnoreCase("Brand") && !Col_id.equals("CatalogProductStatus") && !Col_id.equalsIgnoreCase("ProductStatus") && !Col_id.equalsIgnoreCase("CasePrice")) {
                  iterator.remove();
                  removeColumns.add(Col_id);
               } else {
                  System.out.println("selected column :- " + Col_id);
               }
            }

            System.out.println("removeColumns - " + removeColumns.size() + " and OG_Col -" + OG_Col.size());
            if (OG_Col.size() < 7) {
               System.out.println("OG_Col.size - " + OG_Col.size());
               this.addHiddenColumns();
            } else {
               if (OG_Col.size() > 7) {
                  System.out.println("OG_Col.size - " + OG_Col.size());
                  return false;
               }

               System.out.println("OG_Col.size - " + OG_Col.size());
            }

            Iterator var9 = removeColumns.iterator();

            while(var9.hasNext()) {
               String column = (String)var9.next();
               wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[contains(@id,'" + column + "')]"))));
               driver.findElement(By.xpath("//li[contains(@id,'" + column + "')]/table/tbody/tr/td[2]/img")).click();
               Thread.sleep(3000L);
               System.out.println("removed column :- " + column);
            }

            return true;
         }
      } catch (StaleElementReferenceException var7) {
         var7.printStackTrace();
         return false;
      } catch (Exception var8) {
         System.out.println(var8.getMessage());
         var8.printStackTrace();
         return false;
      }
   }

   private void addHiddenColumns() {
      try {
         Thread.sleep(3000L);
         ArrayList<String> addColumns = new ArrayList();
         List<WebElement> OG_Col = driver.findElements(By.xpath("//table[@id='HiddenColumnsTable']/..//img"));
         System.out.println(OG_Col.size());
         Iterator iterator = OG_Col.iterator();

         while(true) {
            while(iterator.hasNext()) {
               WebElement element = (WebElement)iterator.next();
               Thread.sleep(1000L);
               String Col_id = element.getAttribute("id");
               if (!Col_id.equalsIgnoreCase("Sku") && !Col_id.equalsIgnoreCase("DistributorNumber") && !Col_id.equalsIgnoreCase("CaseUom") && !Col_id.equalsIgnoreCase("UnitOfMeasure") && !Col_id.equalsIgnoreCase("CasePack") && !Col_id.equalsIgnoreCase("PackDescription") && !Col_id.equalsIgnoreCase("Description") && !Col_id.equalsIgnoreCase("ProductDescription") && !Col_id.equalsIgnoreCase("BrandName") && !Col_id.equalsIgnoreCase("Brand") && !Col_id.equals("CatalogProductStatus") && !Col_id.equalsIgnoreCase("ProductStatus") && !Col_id.equalsIgnoreCase("CasePrice")) {
                  System.out.println("Column not matched - " + Col_id);
               } else {
                  System.out.println("Added Column from Hidden column :- " + Col_id);
                  addColumns.add(Col_id);
                  iterator.remove();
               }
            }

            System.out.println("removeColumns - " + addColumns.size() + " and OG_Col -" + OG_Col.size());
            Iterator var10 = addColumns.iterator();

            while(var10.hasNext()) {
               String column = (String)var10.next();
               WebElement lnk_column = (WebElement)wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//table[@id='HiddenColumnsTable']/..//img[@id='" + column + "']"))));
               lnk_column.click();
               Thread.sleep(3000L);
               System.out.println("Added column :- " + column);
            }
            break;
         }
      } catch (StaleElementReferenceException var8) {
         var8.printStackTrace();
      } catch (Exception var9) {
         System.out.println(var9.getMessage());
         var9.printStackTrace();
      }

   }
   
}