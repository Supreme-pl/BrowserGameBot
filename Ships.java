package com.IntelliJSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ships {
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static DateTimeFormatter myFormatHour = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    WebDriver driverClass;
    String shipsAmountClass;
    String xPathClass;

    public Ships(WebDriver driver, String shipsAmount, String xmlPath) throws InterruptedException {
//        WebElement shipField = driver.findElement(By.xpath(xmlPath));
//        shipField.sendKeys(shipsAmount);
//        System.out.println(formattedDate + " Sending " + shipsAmount + " " + getClass().getSimpleName());
        driverClass = driver;
        shipsAmountClass = shipsAmount;
        xPathClass = xmlPath;

    }

    public Ships()
    {}


    public void setField()
    {
        WebElement shipField = driverClass.findElement(By.xpath(xPathClass));
        shipField.sendKeys(shipsAmountClass);
        System.out.println(formattedDate + " Sending " + shipsAmountClass + " " + getClass().getSimpleName());
    }
}
class LightFighterShip extends Ships //lm
{
    // ships input textfield
    public static final String xmlPath = "/html/body/div[5]/div[3]/div[2]/div[1]/div/div[8]/div/form/div/div[1]/ul/li[1]/input";

    public LightFighterShip(WebDriver driver, String shipsAmount) throws InterruptedException {
        super(driver, shipsAmount, xmlPath);
    }
}
class ColonyShip extends Ships //kolonek
{
    public static final String xmlPath = "/html/body/div[5]/div[3]/div[2]/div[1]/div/div[8]/div/form/div/div[2]/ul/li[3]/input";

    public ColonyShip(WebDriver driver, String shipsAmount) throws InterruptedException {
        super(driver, shipsAmount, xmlPath);
    }
}
class InterceptorShip extends Ships //pc
{
    public static final String xmlPath = "/html/body/div[5]/div[3]/div[2]/div[1]/div/div[7]/div/form/div/div[1]/ul/li[5]/input";

    public InterceptorShip(WebDriver driver, String shipsAmount, String xPath) throws InterruptedException {
        super(driver, shipsAmount, xmlPath);
    }
}
class BattleshipShip extends Ships //ow
{
    public static final String xmlPath = "/html/body/div[5]/div[3]/div[2]/div[1]/div/div[7]/div/form/div/div[1]/ul/li[4]/input";

    public BattleshipShip(WebDriver driver, String shipsAmount, String xPath) throws InterruptedException {
        super(driver, shipsAmount, xmlPath);
    }
}
class LargeTransporterShip extends Ships //dt
{
    public static final String xmlPath = "/html/body/div[5]/div[3]/div[2]/div[1]/div/div[7]/div/form/div/div[2]/ul/li[2]/input";

    public LargeTransporterShip(WebDriver driver, String shipsAmount, String xPath) throws InterruptedException {
        super(driver, shipsAmount, xmlPath);
    }
}
class ExplorerShip extends Ships //pionki
{
    public static final String xmlPath = "/html/body/div[5]/div[3]/div[2]/div[1]/div/div[7]/div/form/div/div[1]/ul/li[10]/input";

    public ExplorerShip(WebDriver driver, String shipsAmount, String xPath) throws InterruptedException {
        super(driver, shipsAmount, xmlPath);
    }
}
