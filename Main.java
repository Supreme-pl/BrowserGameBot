package com.IntelliJSelenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.lang.System.out;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TODO: add logged-in/logged-out status
//TODO: more ship types
//TODO: add more runtime delays [to seem more 'human'] (eg. before collecting debris if CR appeared; loggin in -> send fleet when overview is blank; send fleet after expo event is delay [rand 15-120s])
//TODO: implement galaxy view check and debris collection
//TODO: more xpath conditions so selenium won't crash (if(getElements(...).size() > 0) -> true)
//TODO: condition/method checking current planet every time fleet is going to be sent
//TODO: implement encryption/decryption login data from file

public class Main {
    private final static String EMAIL = "";
    private final static String PASSWORD = "";

    static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static boolean checkIfLoggedIn(WebDriver driver)
    {
        if(existsElement(driver, "fleetStatus")) return true;
        else return false;
    }
    private static void login(WebDriver driver)
    {
        /* wait for page */
        WebElement loadedWebsiteCondition = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("registerForm")));

        WebElement tabListLogin = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/ul/li[1]/span"));
        tabListLogin.click();
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys(EMAIL); //fill credentials
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(PASSWORD);
        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/p/button[1]/span")).click(); //login

    }
    public static void enterTheUni(WebDriver driver)
    {
        /* wait for page */
        WebElement LoggedCondition = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("joinGame")));
        /* click 'Play game', click 1st 'play' button on page */
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/div[2]/div/a/button/span")).click(); //enter account list
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/section[1]/div[2]/div/div/div[1]/div[2]/div[1]/div/div[11]/button/span")).click(); //enter uni
        /* switch handle to the 2nd tab (actual game tab) */
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));

    }
    public static void checkEventBox(WebDriver driver)
    {
        LocalDateTime myDateObj = LocalDateTime.now();
        String formattedDate = myDateObj.format(myFormatObj);
        /* check if web is loaded (finds nickname class in upper-left corner)*/
        WebElement loadedGameCondition = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("textBeefy")));

        //Boolean present = driver.findElements(By.className("event_list")).size() > 0;
        if(existsElement(driver, "event_list"))
        {
            /* unfold/open eventBox */
            driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[4]/div/div[1]/div[1]")).click();
        } else { //the account might be logged-out or there's no fleet movement
            /* throw noFleetMovement message */
            System.out.println(formattedDate + " " + driver.findElement(By.xpath("//*[@id=\"eventboxBlank\"]")).getText());

        }
    }
    public static void sendExpo(WebDriver driver) throws InterruptedException, ParseException {
        ArrayList<Ships> ships = new ArrayList<>();
        ships.add(new LightFighterShip(driver, "1"));
        ships.add(new ColonyShip(driver, "1"));

        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        LocalDateTime myDateObj = LocalDateTime.now();

        String formattedDate = myDateObj.format(myFormatObj);
        String formattedHour = myDateObj.format(Ships.myFormatHour);
        System.out.println(formattedDate + " Sending fleet..." + "\n");
        WebElement fleetTabPresence = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"menuTable\"]/li[9]/a/span")));
        driver.findElement(By.xpath("//*[@id=\"menuTable\"]/li[9]/a/span")).click(); //click "Fleet" tab

        if(Main.checkIfLoggedIn(driver)) {
            WebElement fleetTabPresence2 = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"menuTable\"]/li[9]/a/span")));
            if(driver.getCurrentUrl().contains("component=overview")) driver.findElement(By.xpath("//*[@id=\"menuTable\"]/li[9]/a/span")).click(); //click "Flota" tab
            WebElement fleetdispatchComponent = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.className("fleetStatus")));
            /* expoSlots calculation */
            String expo1 = "";
            String expo2 = "";
            String exSlots = driver.findElement(By.xpath("/html/body/div[5]/div[3]/div[2]/div[1]/div/div[3]/div/div[2]/span")).getText();
            for(int i = 12; i < 13; i++)
                expo1 += exSlots.charAt(i);
            for(int i = 14; i < 15; i++)
                expo2 += exSlots.charAt(i);
            int expoSlotsUsed = Integer.parseInt(expo1);
            int allExpoSlots = Integer.parseInt(expo2);
            int expoSlots = allExpoSlots - expoSlotsUsed;
            if(expoSlots != expoSlotsUsed) {
                for (int i = 0; i < expoSlots; i++) {
                    /* 1st Fleetpage */
                    WebElement fleetdispatchComponent2 = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.className("fleetStatus")));

                    System.out.println("-------- " + (i+1) + ". fala --------");
                    /* what ships to send */
                    //new InterceptorShip(driver, "1990000");
                    //new ExplorerShip(driver, "1");
                    //new LargeTransporterShip(driver, "1");
                    //new LightFighterShip(driver, "1", LightFighterShip.xmlPath);
                    //Thread.sleep((long) (Math.random() * 2000) + 1000);
                    //new ColonyShip(driver, "1", ColonyShip.xmlPath);
                    for(Ships ship : ships)
                        ship.setField();
                    System.out.println("------------------------");

                    JavascriptExecutor jse = (JavascriptExecutor) driver;
                    jse.executeScript("window.scrollBy(0,250)");

                    /* 2nd Fleetpage */
                    driver.findElement(By.xpath("//*[@id=\"continueToFleet2\"]/span")).click();
                    new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.className("target")));
                    WebElement position = driver.findElement(By.xpath("//*[@id=\"position\"]"));
                    position.sendKeys("16"); //set pos at 16

                    /* 3rd Fleetpage */
                    driver.findElement(By.xpath("//*[@id=\"continueToFleet3\"]/span")).click();
                    //jse.executeScript("window.scrollBy(0,250)");
                    WebDriverWait wait = new WebDriverWait(driver, 5);
                    WebElement clickMetalBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"metal\"]")));
                    clickMetalBox.click();
                    //new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.id("resources")));
                    driver.findElement(By.xpath("//*[@id=\"missionButton15\"]")).click();
                    clickMetalBox.sendKeys(Keys.ENTER);

                    Thread.sleep((long) (Math.random() * 5000) + 1000);
                }
            }

            driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[4]/div/div[1]/div[1]/p")).click();
            WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement checkArrivalTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("arrivalTime")));
            List<WebElement> ArrivalTimesElem = new ArrayList();
            ArrivalTimesElem = driver.findElements(By.className("arrivalTime"));
            ArrayList<String> ArrTimeToStr = new ArrayList<>();
            ArrayList<String> FinalStrTimes = new ArrayList<>();
            ArrayList<Time> ArrivalTimes = new ArrayList<>();

            for(WebElement e : ArrivalTimesElem)
            {
                //System.out.println(e.getText());
                ArrTimeToStr.add(e.getText());
            }
            for(int i = 0; i < allExpoSlots; i++)
            {
                String time = "";
                for(int y = 0; y < 8; y++)
                     time +=  ArrTimeToStr.get(i).charAt(y);
                FinalStrTimes.add(time);
            }
            for(int i = 0; i < FinalStrTimes.size(); i++)
                ArrivalTimes.add(new Time(formatter.parse(FinalStrTimes.get(i)).getTime()));

            for(Time s : ArrivalTimes)
                out.println(s);

            long timeToWait = ArrivalTimes.get(ArrivalTimes.size()-1).getTime() - ((Date) Ships.myFormatHour.parse(formattedHour)).getTime();

            out.println(timeToWait);

        } else {
            Main.enterTheUni(driver);
            sendExpo(driver);
        }

    }

    public static void main(String[] args) throws InterruptedException, ParseException {
        System.setProperty("webdriver.chrome.driver", "C:\\SeleniumChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("localhost");
        login(driver);
        enterTheUni(driver);
        checkEventBox(driver);
        sendExpo(driver);
        //if <p class="event_list"> is present then click the eventbox and get the necessary data, else get to the fleetdispatch
    }

    private static boolean existsElement(WebDriver driver, String className) {
        try {
            driver.findElement(By.className(className));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    static class selectedComponent {
        String urlLink;
        boolean isSelected;

        public selectedComponent(String url, boolean selected)
        {
            this.urlLink = url;
            this.isSelected = selected;
        }

        public void interact(WebDriver driver)
        {
            driver.findElement(new By.ByPartialLinkText(urlLink));

        }
    }
}