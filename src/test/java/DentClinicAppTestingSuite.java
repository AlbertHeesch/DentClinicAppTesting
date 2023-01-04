import com.dent.config.WebDriverConfig;
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
        Thread.sleep(1000);
        datePicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().plusDays(1)));
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

        WebElement bookButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-horizontal-layout/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        bookButton.click();
    }

    private void logIn(String identity) {
        WebElement usernameField = driver.findElement(By.name("//*[@id=\"input-vaadin-text-field-52\"]"));
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
    void createAppointmentAsPatient() throws InterruptedException {
        createAnAppointment();
        Thread.sleep(10000);

        WebElement patientButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]"));
        patientButton.click();

        logIn(ADMIN);
    }
}
