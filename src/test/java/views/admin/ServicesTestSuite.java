package views.admin;

import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class ServicesTestSuite {
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

        WebElement servicesPanelButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[1]/a[3]"));
        servicesPanelButton.click();

        Thread.sleep(2000);

        serviceCreation();
    }

    private void serviceCreation() throws InterruptedException {
        WebElement serviceCreationButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[1]/vaadin-button"));
        serviceCreationButton.click();

        Thread.sleep(1000);
        driver.manage().window().fullscreen();

        WebElement descriptionField = driver.findElement(By.id("input-vaadin-text-field-7"));
        descriptionField.sendKeys("IntegrationTestDescription");

        WebElement costField = driver.findElement(By.id("input-vaadin-big-decimal-field-11"));
        costField.sendKeys("2137");

        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        saveButton.click();

        Thread.sleep(4000);
    }

    private void deleteService(String serviceDescription) throws InterruptedException {
        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals(serviceDescription))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        driver.manage().window().fullscreen();
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        deleteButton.click();
    }

    @Test
    public void createAndDeleteService() throws InterruptedException {
        //Check
        boolean descriptionFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestDescription"));

        boolean costFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("2137.0"));

        Assertions.assertTrue(descriptionFieldPresence && costFieldPresence);

        //Clean Up
        deleteService("IntegrationTestDescription");

        Thread.sleep(1000);

        boolean integrationDescriptionPresenceAfterDelete = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestDescription"));

        Assertions.assertFalse(integrationDescriptionPresenceAfterDelete);

        driver.close();
    }

    @Test
    public void shouldEditService() throws InterruptedException {
        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals("IntegrationTestDescription"))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        WebElement descriptionField = driver.findElement(By.id("input-vaadin-text-field-7"));
        descriptionField.sendKeys("1");

        WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        saveButton.click();

        Thread.sleep(1000);

        //Check
        boolean descriptionFieldAfterEdit = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestDescription1"));

        boolean descriptionFieldBeforeEdit = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestDescription"));

        Assertions.assertTrue(descriptionFieldAfterEdit);
        Assertions.assertFalse(descriptionFieldBeforeEdit);

        //Clean up
        deleteService("IntegrationTestDescription1");

        driver.close();
    }
}