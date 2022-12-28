import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DentClinicAppTestingSuite {
    private static final String BASE_URL = "http://localhost:8085/home";
    private static final String ADMIN = "Admin";
    private static final String USER = "User";
    private WebDriver driver;

    @BeforeEach
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        assert driver != null;
        driver.get(BASE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void createAnAppointment() throws InterruptedException {
        WebElement patientButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        patientButton.click();

        Thread.sleep(4000);

        WebElement shadowHost = driver.findElement(By.xpath("/html/body/vaadin-dev-tools"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        WebElement shadowContent = shadowRoot.findElement(By.xpath("/html/body/vaadin-dev-tools//div[2]/div/div[2]"));
        //nie umie zlokalizować

        WebElement nameField = driver.findElement(By.id("input-vaadin-text-field-27"));
        nameField.sendKeys("IntegrationTestName");

        WebElement surnameField = driver.findElement(By.id("input-vaadin-text-field-28"));
        surnameField.sendKeys("IntegrationTestSurname");

        WebElement peselField = driver.findElement(By.id("input-vaadin-big-decimal-field-29"));
        peselField.sendKeys("1234");

        WebElement emailField = driver.findElement(By.id("input-vaadin-email-field-30"));
        emailField.sendKeys("Integration@test.com");

        WebElement datePicker = driver.findElement(By.id("input-vaadin-date-time-picker-date-picker-31"));
        datePicker.click();
        datePicker.sendKeys(DateTimeFormatter.ofPattern("dd/MM/uuuu").format(LocalDate.now().plusDays(1)));
        datePicker.sendKeys(Keys.ENTER);

        WebElement timePicker = driver.findElement(By.id("input-vaadin-date-time-picker-time-picker-33"));
        timePicker.click();
        timePicker.sendKeys(DateTimeFormatter.ofPattern("hh/mm").format(LocalTime.of(12, 0)));
        timePicker.sendKeys(Keys.ENTER);

        WebElement dentistCombobox = driver.findElement(By.id("input-vaadin-combo-box-35"));
        dentistCombobox.click();
        Thread.sleep(1000);
        dentistCombobox.sendKeys(Keys.DOWN);
        dentistCombobox.sendKeys(Keys.ENTER);

        WebElement serviceCombobox = driver.findElement(By.id("input-vaadin-combo-box-37"));
        serviceCombobox.click();
        Thread.sleep(1000);
        serviceCombobox.sendKeys(Keys.DOWN);
        serviceCombobox.sendKeys(Keys.ENTER);
    }

    private void logIn(String identity) {
        WebElement employeeButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        employeeButton.click();

        WebElement usernameField = driver.findElement(By.id("input-vaadin-text-field-6"));
        WebElement passwordField = driver.findElement(By.id("input-vaadin-password-field-7"));

        if(identity == "Admin") {
            usernameField.sendKeys("admin");
        } else if(identity == "User") {
            usernameField.sendKeys("user");
        }

        passwordField.sendKeys("userpass");

        WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton.click();
    }

    @Test
    void test() throws InterruptedException {
//        createAnAppointment();
        logIn(ADMIN);
    }
}
