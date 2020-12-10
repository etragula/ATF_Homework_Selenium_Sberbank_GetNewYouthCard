package appline.sberbank.newyouthcardtest;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void doBefore() {
        System.setProperty("webdriver.chrome.driver", "webDriver/chromedriver");        // установка пути к драйверу MAC OS
        driver = new ChromeDriver();                                                    // инициализация драйвера MAC OS
        driver.manage().window().maximize();                                            // увеличение окна браузера
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);  // установка таймаута на загрузку страницы
//      driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);        // установка неявного ожидания появления элемента
                                                                                // ожидание при поиске появления элемента
        wait = new WebDriverWait(driver, 4);

        driver.get("http://www.sberbank.ru/ru/person");             // переход на сайт
/*
                 Методы driver-a
        driver.navigate().to("http://www.sberbank.ru/ru/person");   // переход на сайт
        driver.navigate().back();    // вернуться на предыдущую страницу
        driver.navigate().forward(); // вернуться на страницу вперед
        driver.navigate().refresh(); // обновление страницы

        driver.quit();               // закрытие браузера в конце теста
        driver.close();              // закрытие активной вкладки

        driver.manage().window().maximize();                        // окно во весь экран
        driver.manage().window().setSize(new Dimension(900, 500));  // окно собственного размера

        driver.getTitle();           // получение title-а страницы (название вкладки)
        driver.getCurrentUrl();      // получение URL страницы
*/
    }

    @After
    public void doAfter() {
        driver.quit();
    }
}
