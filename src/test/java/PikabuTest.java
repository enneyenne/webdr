import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PikabuTest {

    static WebDriver driver = new ChromeDriver();
    String actualTitle, actualSite, errorMessage;

    @BeforeEach
    public void test_openBrowser() {
        // Переход по ссылке
        driver.get("https://pikabu.ru");
    }

    @Test
    public void test_pikabuAuth() throws InterruptedException {

        // Проверка сайта и его заголовка
        actualSite = driver.getCurrentUrl();
        actualTitle = driver.getTitle();
        Assertions.assertEquals(actualSite, "https://pikabu.ru/");
        Assertions.assertEquals(actualTitle, "Горячее – самые интересные и обсуждаемые посты | Пикабу");

        // Клик по кнопке "Войти"
        driver.findElement(By.xpath("//span[contains(@class, \"header-right-menu__login-button\")]")).click();
        Thread.sleep(500);

        // Проверка отображения окон и полей
        WebElement userName = driver.findElement(By.xpath("//div[@class=\"overlay\"] //input[contains(@class, input__input) " +
                "and contains(@name, \"username\") " +
                "and contains(@placeholder, \"Логин\")]"));

        WebElement userPassword = driver.findElement(By.xpath("//div[@class=\"overlay\"] //input[contains(@class, input__input) " +
                "and contains(@name, \"password\") " +
                "and contains(@placeholder, \"Пароль\")]"));

        WebElement loginButton = driver.findElement(By.xpath("//div[@class=\"overlay\"] //button[contains(@type, \"submit\") and contains(@class, \"button_success\")]"));

        userName.isDisplayed();
        userPassword.isDisplayed();
        loginButton.isDisplayed();

        // Ввод логина
        userName.click();
        userName.sendKeys("Qwerty");
        Thread.sleep(500);

        // Ввод пароля
        userPassword.click();
        userPassword.sendKeys("Qwerty");
        Thread.sleep(500);

        // Авторизация
        loginButton.click();
        loginButton.sendKeys(Keys.ENTER);
        Thread.sleep(2500);

        // Сообщение об ошибке
        errorMessage = driver.findElement(By.xpath("//div[@class=\"overlay\"] //span[contains(@class, \"auth__error auth__error_top\")]")).getAttribute("outerText");
        Assertions.assertEquals(errorMessage, "Ошибка. Вы ввели неверные данные авторизации");
    }

    @AfterEach
    public void test_closeBrowser() throws InterruptedException {
        // Завершенеие работы браузера
        Thread.sleep(5000);
        driver.quit();
    }

}
