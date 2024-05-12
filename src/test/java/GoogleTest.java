import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.*;

public class GoogleTest {

    static WebDriver driver = new ChromeDriver();
    static WebDriverWait wait;

    @BeforeEach
    public void test_openBrowser() {

        // Implicit
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        //Explicit
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Переход по ссылке
        driver.get("https://google.com.ru");
    }

    @Test
    public void test_googleSearchPobeda() throws InterruptedException {

        // Поиск селектора
        WebElement googleSearch = driver.findElement(By.cssSelector("[aria-label=\"Найти\"]"));
        Assertions.assertTrue(googleSearch.isDisplayed());

        // Поисковый запрос
        googleSearch.click();
        googleSearch.sendKeys("Сайт компании Победа");
        googleSearch.sendKeys(Keys.ENTER);

        // Поиск селектора и переход по ссылке
        WebElement pbd = driver.findElement(By.cssSelector("h3"));
        pbd.click();

        // Смена фокуса на новое открытое окно
        String oldTab = driver.getWindowHandle();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        newTab.remove(oldTab);
        driver.switchTo().window(newTab.get(0));

        // Поиск картинки с Калининградом
        WebElement kenImg = driver.findElement(By.xpath("//img[contains(@src, \"Kalinigrad\")]"));

        with()
                .pollDelay(100, TimeUnit.MILLISECONDS)
                .await()
                .atMost(90, TimeUnit.SECONDS)
                .until(kenImg::isDisplayed);

        String kenText = driver.findElement(By.xpath("//img[contains(@src, \"Kalinigrad\")] /following::div[contains(text(), \"Полетели в Калининград!\")]")).getAttribute("outerText");
        Assertions.assertEquals(kenText, "Полетели в Калининград!");

        // Переключение языка и последующая проверка отображения текстов
        Thread.sleep(1000);
        WebElement languageSelectButton = driver.findElement(By.xpath("//button[contains(@type, \"button\")" +
                " and contains(@class, \"dp-1yj5213-root-root\")]"));
        languageSelectButton.click();

        Thread.sleep(1000);
        WebElement engButton = driver.findElement(By.xpath("//button[@class=\"dp-14vias7-root\"] //div[contains(@class, \"dp-5l8iqa-root\")]"));
        engButton.click();

        WebElement ticketSearch = driver.findElement(By.xpath("//div[contains(text(), \"Ticket search\")" +
                " and contains(@class, \"dp-YpbSQV-textVisible-ref dp-1glhebn-root-textVisible\")]"));
        WebElement onlineCheckIn = driver.findElement(By.xpath("//div[contains(text(), \"Online check-in\")" +
                " and contains(@class, \"dp-YpbSQV-textVisible-ref dp-1glhebn-root-textVisible\")]"));
        WebElement manageMyBooking = driver.findElement(By.xpath("//div[contains(text(), \"Manage my booking\")" +
                " and contains(@class, \"dp-YpbSQV-textVisible-ref dp-1glhebn-root-textVisible\")]"));

        wait.until(ExpectedConditions.textToBePresentInElement(ticketSearch, "Ticket search"));
        wait.until(ExpectedConditions.textToBePresentInElement(onlineCheckIn, "Online check-in"));
        wait.until(ExpectedConditions.textToBePresentInElement(manageMyBooking, "Manage my booking"));

        Assertions.assertTrue(ticketSearch.isDisplayed());
        Assertions.assertTrue(onlineCheckIn.isDisplayed());
        Assertions.assertTrue(manageMyBooking.isDisplayed());

    }

    @AfterEach
    public void test_closeBrowser() throws InterruptedException {

        // Завершенеие работы браузера
        Thread.sleep(1000);
        driver.quit();
    }

}