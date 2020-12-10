package appline.sberbank.newyouthcardtest;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class NewYouthCardScenarioTest extends BaseTest {

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        return Arrays.asList(new Object[][]{
                {"Петросян", "Михаил", "Юрьевич", "1_2_3@mail.ru", "(971) 111-11-11", "07.09.1998"},
                {"Иванов", "Иван", "Иванович", "qwerrty@mail.ru", "(911) 654-78-98", "28.12.1986"},
                {"фамилия", "имя", "отчество", "pochta@yandex.ru", "(123) 321-45-78", "05.05.1965"},
        });
    }

    @Parameterized.Parameter(0)
    public String lName;
    @Parameterized.Parameter(1)
    public String fName;
    @Parameterized.Parameter(2)
    public String mName;
    @Parameterized.Parameter(3)
    public String email;
    @Parameterized.Parameter(4)
    public String phone;
    @Parameterized.Parameter(5)
    public String dateOfBirth;

    @Test
    public void testCardOrderScenario() {

        // 1. Кликнуть "Меню".
        clickButton(By.xpath("//a[@aria-label='Меню  Карты']"));

        // 2. Кликнуть "Дебетовые карты".
        clickButton(By.xpath("//ul//a[contains(text(), 'Дебетовые карты')]"));

        // 3. Проверка заголовка – "Дебетовые карты".
        WebElement titleDebCard = driver.findElement(By.xpath("//h1[contains(text(),'Дебетовые карты')]"));
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Дебетовые карты", titleDebCard.getText());

        // 4. Выбирать "Молодежная карта" и кликнуть "Заказать онлайн".
        clickButton(By.xpath("//a[@data-product='Молодёжная карта']/span[contains(text(),'Заказать онлайн')]"));

        // 5. Проверка заголовка – "Молодёжная карта".
        WebElement titleYouthCard = driver.findElement(By.xpath("//h1[contains(text(),'Молодёжная карта')]"));
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Молодёжная карта", titleYouthCard.getText());

        // Заполнение полей.
        // Фамилия
        sleepForSecond();
        fillAndCheck("lastName", lName);
        // Имя
        fillAndCheck("firstName", fName);
        // Отчество
        fillAndCheck("middleName", mName);
        // E-mail
        fillAndCheck("email", email);
        // Телефон
        fillAndCheck("phone", phone);
        // Дата рождения
        fillAndCheck("birthDate", dateOfBirth);

        // 10. Кликнуть "Далее".
        clickButton(By.xpath("//span[contains(text(),'Далее')]"));

        // 11. Проверить сообщение именно "Обязательное поле" у незаполненных полей
        checkError(By.xpath("//input[@data-name='series']/following-sibling::div[@class='odcui-error__text']"));
        checkError(By.xpath("//input[@data-name='number']/following-sibling::div[@class='odcui-error__text']"));
        checkError(By.xpath("//label[contains(text(), 'Дата выдачи')]/following-sibling::div[@class='odcui-error__text']"));
    }

    private void sleepForSecond() {
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void checkError(By by) {
        WebElement errorAlert = driver.findElement(by);
        Assert.assertEquals("Проверка ошибки у незаполненного поля " + errorAlert.toString() + " не была пройдено",
                "Обязательное поле", errorAlert.getText());
    }

    private void clickButton(By by) {
        WebElement button = driver.findElement(by);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void fillAndCheck(String field, String value) {
        String xPath = "//input[@data-name='%s']";
        WebElement element = driver.findElement(By.xpath(String.format(xPath, field)));
        scrollToElement(element);
        sleepForSecond();
        element.click();
        element.sendKeys(value);
        if (field.equals("phone")) {
            Assert.assertEquals("Поле заполнено некорректно " + element.toString(),
                    element.getAttribute("value"), "+7 " + value);
        } else {
            Assert.assertEquals("Поле заполнено некорректно " + element.toString(),
                    element.getAttribute("value"), value);
        }
    }
}

