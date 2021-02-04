import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExampleScenarioTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);



        wait = new WebDriverWait(driver, 10, 1000);
        String baseUrl = "https://www.rgs.ru";
        driver.get(baseUrl);
    }

    @Test
    public void exampleScenario() throws InterruptedException {

        // Нажимаем кнопку "Меню", открывается ниспадающее меню.
        String insuranceButtonXPath = "//a[@class='hidden-xs']";
        List<WebElement> insuranceButtonList = driver.findElements(By.xpath(insuranceButtonXPath));
        if (!insuranceButtonList.isEmpty()) {
            insuranceButtonList.get(0).click();
        }

        // Перешли в раздел "Компаниям"
        String companyButtonXPath = "//a[@href='https://www.rgs.ru/products/juristic_person/index.wbp']";
        WebElement companyButton = driver.findElement(By.xpath(companyButtonXPath));
        companyButton.click();

        //Перешли в раздел "Здоровья"
        String healthButtonXPath = "//a[@href='/products/juristic_person/health/index.wbp' and @class='list-group-item adv-analytics-navigation-line4-link']";
        WebElement healthButton = driver.findElement(By.xpath(healthButtonXPath));
        healthButton.click();

        //Перешли в раздел "Добровольное медицинское страхование"
        String dmsButtonXPath = "//a[contains(text(), 'Добровольное медицинское страхование')]";
        WebElement dmsButton = driver.findElement(By.xpath(dmsButtonXPath));
        dmsButton.click();



        //Проверим наличия заголовка "Добровольное медицинское страхование"
        String pageTitleXPath = "//h1";
        waitUtilElementToBeVisible(By.xpath(pageTitleXPath));
        WebElement pageTitle = driver.findElement(By.xpath(pageTitleXPath));
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Добровольное медицинское страхование", pageTitle.getText());


        //Нажимаем на кнопку "Отправить заявку"
        String requestButtonXPath=
                "//a[@class='btn btn-default text-uppercase hidden-xs adv-analytics-navigation-desktop-floating-menu-button' " +
                        "and contains(text(), 'Отправить заявку')]";
        WebElement requestButton = driver.findElement(By.xpath(requestButtonXPath));
        requestButton.click();


        //Проверим наличия заголовка "Заявка на добровольное медицинское страхование"
        String pageTitleXPath1 = "//h4";
        waitUtilElementToBeVisible(By.xpath(pageTitleXPath1));
        WebElement pageTitle1 = driver.findElement(By.xpath(pageTitleXPath1));
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Заявка на добровольное медицинское страхование", pageTitle1.getText());

        //Заполняем поле "Фамилия"
        String fieldLastNameXPath = "//*[@name='LastName']";
        WebElement fieldLastName = driver.findElement(By.xpath(fieldLastNameXPath));
        fieldLastName.sendKeys("Иванов");

        //Заполняем поле "Имя"
        String fieldFirstNameXPath = "//*[@name='FirstName']";
        WebElement fieldFirstName = driver.findElement(By.xpath(fieldFirstNameXPath));
        fieldFirstName.sendKeys("Иван");

        //Заполняем поле "Отчество"
        String fieldMiddleNameXPath = "//*[@name='MiddleName']";
        WebElement fieldMiddleName = driver.findElement(By.xpath(fieldMiddleNameXPath));
        fieldMiddleName.sendKeys("Иванович");

        //Заполняем поле "Регион"
        String fieldRegionXPath = "//*[@name='Region']";
        WebElement fieldRegion = driver.findElement(By.xpath(fieldRegionXPath));
        //fieldRegion.click();
        fieldRegion.sendKeys("Москва");

        //Thread.sleep(1000);

        //Заполняем поле "Телефон"
        String fieldPhoneXPath = "//input[contains(@data-bind, '+7 (999) 999-99-99')]";
        WebElement fieldPhone = driver.findElement(By.xpath(fieldPhoneXPath));
        fieldPhone.click();
        fieldPhone.sendKeys("9993215577");

        //Заполняем поле "Email"
        String fieldEmailXPath = "//*[@name='Email']";
        WebElement fieldEmail = driver.findElement(By.xpath(fieldEmailXPath));
        fieldEmail.sendKeys("qwertyqwertyqwertqwertyqwertyqwert");

        //Заполняем поле "Предпочитаемая дата контакта*" - заполняется с глюком
        String fieldDateXPath = "//*[@name='ContactDate']";
        WebElement fieldDate = driver.findElement(By.xpath(fieldDateXPath));
        fieldDate.click();
        fieldDate.sendKeys("15.02.2021\n");

        //Заполняем поле "Комментарии"
        String fieldCommentXPath ="//*[@name='Comment']";
        WebElement fieldComment = driver.findElement(By.xpath(fieldCommentXPath));
        fieldComment.sendKeys("Здесь должен быть комментарий!");

        //Ставим галочку согласия
        String fieldCheckXPath = "//*[@class='checkbox']";
        WebElement fieldCheck = driver.findElement(By.xpath(fieldCheckXPath));
        fieldCheck.click();

        Thread.sleep(5000);

        //Проверяем наличие ошибки при вводе email
        String errorEmailXPath = "//span[contains(text(), 'Введите адрес электронной почты')]";
        WebElement errorEmail = driver.findElement(By.xpath(errorEmailXPath));
        Assert.assertEquals("Проверка ошибки у поля не была пройдена",
                "Введите адрес электронной почты", errorEmail.getText());


    }

    @After
    public void after() {
        driver.quit();
    }

    private void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
