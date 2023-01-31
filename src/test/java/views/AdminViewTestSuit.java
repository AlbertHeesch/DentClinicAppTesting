package views;

import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class AdminViewTestSuit {
    private WebDriver driver;
    private PatientAndDentistViewsTestSuit patientAndDentist = new PatientAndDentistViewsTestSuit();

    @BeforeEach
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(PatientAndDentistViewsTestSuit.BASE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void createAnAppointmentAsAdmin() throws InterruptedException {
        WebElement adminButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]"));
        adminButton.click();

        Thread.sleep(2000);

        patientAndDentist.logIn(PatientAndDentistViewsTestSuit.ADMIN);

        WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton.click();

        Thread.sleep(2000);

        WebElement appointmentCreationButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[1]/vaadin-button"));
        appointmentCreationButton.click();

        Thread.sleep(1000);
        driver.manage().window().fullscreen();

        WebElement nameField = driver.findElement(By.id("input-vaadin-text-field-7"));
        nameField.sendKeys("IntegrationTestName");

        WebElement surnameField = driver.findElement(By.id("input-vaadin-text-field-11"));
        surnameField.sendKeys("IntegrationTestSurname");

        WebElement peselField = driver.findElement(By.id("input-vaadin-big-decimal-field-15"));
        peselField.sendKeys("1234");

        WebElement emailField = driver.findElement(By.id("input-vaadin-email-field-19"));
        emailField.sendKeys("Integration@test.com");

        WebElement datePicker = driver.findElement(By.id("input-vaadin-date-time-picker-date-picker-29"));
        datePicker.click();
        Thread.sleep(1000);
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY) {
            datePicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().plusDays(3)));
        } else if (LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY) {
            datePicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().plusDays(2)));
        } else {
            datePicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().plusDays(1)));
        }
        datePicker.sendKeys(Keys.ENTER);

        WebElement timePicker = driver.findElement(By.id("input-vaadin-date-time-picker-time-picker-31"));
        timePicker.click();
        timePicker.sendKeys(DateTimeFormatter.ofPattern("hh/mm").format(LocalTime.of(12, 0)));
        timePicker.sendKeys(Keys.ENTER);

        WebElement dentistCombobox = driver.findElement(By.id("input-vaadin-combo-box-36"));
        dentistCombobox.click();
        Thread.sleep(1000);
        dentistCombobox.sendKeys(Keys.DOWN);
        dentistCombobox.sendKeys(Keys.ENTER);

        WebElement serviceCombobox = driver.findElement(By.id("input-vaadin-combo-box-41"));
        serviceCombobox.click();
        Thread.sleep(1000);
        serviceCombobox.sendKeys(Keys.DOWN);
        serviceCombobox.sendKeys(Keys.ENTER);

        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        saveButton.click();

        Thread.sleep(4000);

        //Check
        boolean nameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        boolean surnameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestSurname"));

        boolean peselFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("1234"));

        boolean emailFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("Integration@test.com"));

        Assertions.assertTrue(nameFieldPresence && surnameFieldPresence && peselFieldPresence && emailFieldPresence);

        //Clean Up
        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals("IntegrationTestName"))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        driver.manage().window().fullscreen();
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        deleteButton.click();

        driver.close();
    }
}
