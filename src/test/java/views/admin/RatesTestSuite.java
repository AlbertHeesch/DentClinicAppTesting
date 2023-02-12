package views.admin;

import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class RatesTestSuite {
    private WebDriver driver;
    private final views.patientAndDentist.AppointmentTestSuite patientAndDentist = new views.patientAndDentist.AppointmentTestSuite();

    @BeforeEach
    public void initTests() throws InterruptedException {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(views.patientAndDentist.AppointmentTestSuite.BASE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement adminButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]"));
        adminButton.click();

        Thread.sleep(2000);

        patientAndDentist.logIn(views.patientAndDentist.AppointmentTestSuite.ADMIN);

        WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton.click();

        Thread.sleep(2000);

        WebElement ratesPanelButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[1]/a[4]"));
        ratesPanelButton.click();

        Thread.sleep(2000);

        rateCreation();
    }

    private void rateCreation() throws InterruptedException {
        WebElement rateCreationButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[1]/vaadin-button"));
        rateCreationButton.click();

        Thread.sleep(1000);
        driver.manage().window().fullscreen();

        WebElement nameField = driver.findElement(By.id("input-vaadin-text-field-7"));
        nameField.sendKeys("IntegrationTestName");

        WebElement valueField = driver.findElement(By.id("input-vaadin-big-decimal-field-11"));
        valueField.sendKeys("2137");

        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        saveButton.click();

        Thread.sleep(4000);
    }

    private void deleteRate(String rateName) throws InterruptedException {
        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals(rateName))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        driver.manage().window().fullscreen();
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        deleteButton.click();
    }

    @Test
    public void createAndDeleteRate() throws InterruptedException {
        //Check
        boolean nameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        boolean valueFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("2137.0"));

        Assertions.assertTrue(nameFieldPresence && valueFieldPresence);

        //Clean Up
        deleteRate("IntegrationTestName");

        Thread.sleep(1000);

        boolean integrationNamePresenceAfterDelete = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        Assertions.assertFalse(integrationNamePresenceAfterDelete);

        driver.close();
    }

    @Test
    public void shouldEditRate() throws InterruptedException {
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
        deleteRate("IntegrationTestName1");

        driver.close();
    }
}