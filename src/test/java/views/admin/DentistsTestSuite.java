package views.admin;

import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import views.patientAndDentist.AppointmentTestSuite;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DentistsTestSuite {
    private WebDriver driver;
    private final AppointmentTestSuite patientAndDentist = new AppointmentTestSuite();

    @BeforeEach
    public void initTests() throws InterruptedException {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(AppointmentTestSuite.BASE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement adminButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]"));
        adminButton.click();

        Thread.sleep(2000);

        patientAndDentist.logIn(AppointmentTestSuite.ADMIN);

        WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton.click();

        Thread.sleep(2000);

        WebElement dentistPanelButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[1]/a[2]"));
        dentistPanelButton.click();

        Thread.sleep(2000);

        dentistCreation();
    }

    private void dentistCreation() throws InterruptedException {
        WebElement dentistCreationButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[1]/vaadin-button"));
        dentistCreationButton.click();

        Thread.sleep(1000);
        driver.manage().window().fullscreen();

        WebElement nameField = driver.findElement(By.id("input-vaadin-text-field-7"));
        nameField.sendKeys("IntegrationTestName");

        WebElement surnameField = driver.findElement(By.id("input-vaadin-text-field-11"));
        surnameField.sendKeys("IntegrationTestSurname");

        WebElement dateOfEmploymentPicker = driver.findElement(By.id("input-vaadin-date-picker-15"));
        dateOfEmploymentPicker.click();

        Thread.sleep(1000);

        dateOfEmploymentPicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().minusDays(1)));
        dateOfEmploymentPicker.sendKeys(Keys.ENTER);

        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        saveButton.click();

        Thread.sleep(4000);
    }

    private void deleteDentist(String dentistName) throws InterruptedException {
        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals(dentistName))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        driver.manage().window().fullscreen();
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        deleteButton.click();
    }

    @Test
    public void createAndDeleteDentist() throws InterruptedException {
        //Check
        boolean nameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        boolean surnameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestSurname"));

        Assertions.assertTrue(nameFieldPresence && surnameFieldPresence);

        //Clean Up
        deleteDentist("IntegrationTestName");

        Thread.sleep(1000);

        boolean integrationNamePresenceAfterDelete = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        Assertions.assertFalse(integrationNamePresenceAfterDelete);

        driver.close();
    }

    @Test
    public void shouldEditDentist() throws InterruptedException {
        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals("IntegrationTestName"))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        WebElement nameField = driver.findElement(By.id("input-vaadin-text-field-7"));
        nameField.sendKeys("1");

        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        saveButton.click();

        Thread.sleep(1000);

        //Check
        boolean nameFieldAfterEdit = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName1"));

        boolean nameFieldBeforeEdit = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        Assertions.assertTrue(nameFieldAfterEdit);
        Assertions.assertFalse(nameFieldBeforeEdit);

        //Clean up
        deleteDentist("IntegrationTestName1");

        driver.close();
    }
}