import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DentClinicAppTestingSuite {
//    private static final String BASE_URL = "http://localhost:8080/home";
    private static final String BASE_URL = "http://localhost:8085/home";
    private static final String ADMIN = "Admin";
    private static final String USER = "User";
    private WebDriver driver;

//    private WebElement adminButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]"));

    @BeforeEach
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
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

        if(identity.equals("Admin")) {
            try {
                Robot robot = new Robot();
                robot.setAutoDelay(250);
                robot.keyPress(KeyEvent.VK_A);
                robot.keyPress(KeyEvent.VK_D);
                robot.keyPress(KeyEvent.VK_M);
                robot.keyPress(KeyEvent.VK_I);

                robot.keyPress(KeyEvent.VK_TAB);

                robot.keyPress(KeyEvent.VK_U);
                robot.keyPress(KeyEvent.VK_S);
                robot.keyPress(KeyEvent.VK_E);
                robot.keyPress(KeyEvent.VK_R);
                robot.keyPress(KeyEvent.VK_P);
                robot.keyPress(KeyEvent.VK_A);
                robot.keyPress(KeyEvent.VK_S);
                robot.keyPress(KeyEvent.VK_S);

            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        } else if(identity.equals("User")) {
            try {
                Robot robot = new Robot();
                robot.setAutoDelay(250);
                robot.keyPress(KeyEvent.VK_U);
                robot.keyPress(KeyEvent.VK_S);
                robot.keyPress(KeyEvent.VK_E);
                robot.keyPress(KeyEvent.VK_R);

                robot.keyPress(KeyEvent.VK_TAB);

                robot.keyPress(KeyEvent.VK_U);
                robot.keyPress(KeyEvent.VK_S);
                robot.keyPress(KeyEvent.VK_E);
                robot.keyPress(KeyEvent.VK_R);
                robot.keyPress(KeyEvent.VK_P);
                robot.keyPress(KeyEvent.VK_A);
                robot.keyPress(KeyEvent.VK_S);
                robot.keyPress(KeyEvent.VK_S);

            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        }

        WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton.click();
    }

    @Test
    void createAppointmentAsPatient() throws InterruptedException {
        createAnAppointment();
        Thread.sleep(10000);

        WebElement dentistButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        dentistButton.click();

        logIn(USER);

        boolean nameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        boolean surnameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestSurname"));

        Assertions.assertTrue(nameFieldPresence);
        Assertions.assertTrue(surnameFieldPresence);
    }
}
