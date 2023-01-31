package views;

import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class PatientAndDentistViewsTestSuit {
    public static final String BASE_URL = "http://localhost:8085/home";
    public static final String ADMIN = "Admin";
    private static final String USER = "User";
    private final String testName = "IntegrationTestName";
    private final String testSurname = "IntegrationTestSurname";
    private WebDriver driver;

    @BeforeEach
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(BASE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void createAnAppointment() throws InterruptedException {
        WebElement patientButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]"));
        patientButton.click();

        Thread.sleep(4000);

        WebElement nameField = driver.findElement(By.id("input-vaadin-text-field-27"));
        nameField.sendKeys(testName);

        WebElement surnameField = driver.findElement(By.id("input-vaadin-text-field-28"));
        surnameField.sendKeys(testSurname);

        WebElement peselField = driver.findElement(By.id("input-vaadin-big-decimal-field-29"));
        peselField.sendKeys("1234");

        WebElement emailField = driver.findElement(By.id("input-vaadin-email-field-30"));
        emailField.sendKeys("Integration@test.com");

        WebElement datePicker = driver.findElement(By.id("input-vaadin-date-time-picker-date-picker-31"));
        datePicker.click();
        Thread.sleep(1000);
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY) {
            datePicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().plusDays(3)));
        } else if (LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY) {
            datePicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().plusDays(3)));
        } else {
            datePicker.sendKeys(DateTimeFormatter.ofPattern("dd.MM.uuuu").format(LocalDate.now().plusDays(1)));
        }
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

    public void logIn(String identity) {

        if (identity.equals("Admin")) {
            try {
                Robot robot = new Robot();
                robot.setAutoDelay(250);
                robot.keyPress(KeyEvent.VK_A);
                robot.keyPress(KeyEvent.VK_D);
                robot.keyPress(KeyEvent.VK_M);
                robot.keyPress(KeyEvent.VK_I);
                robot.keyPress(KeyEvent.VK_N);

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
        } else if (identity.equals("User")) {
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
    }

    @Test
    void createAppointmentAsPatient() throws InterruptedException {
        createAnAppointment();
        Thread.sleep(10000);

        WebElement adminButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]"));
        adminButton.click();

        //Check
        logIn(ADMIN);

        try {
            WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
            logInButton.click();
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex)
        {
            WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
            logInButton.click();
        }

        boolean nameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals(testName));

        boolean surnameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals(testSurname));

        Assertions.assertTrue(nameFieldPresence && surnameFieldPresence);

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

    @Test
    public void dentistsShouldFindCreatedAppointment() throws InterruptedException {
        createAnAppointment();
        Thread.sleep(10000);

        WebElement dentistButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        dentistButton.click();

        //Check
        logIn(USER);

        WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

        logInButton.click();

        boolean nameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        boolean surnameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestSurname"));

        boolean peselFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("1234"));

        boolean emailFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("Integration@test.com"));

        Assertions.assertTrue(nameFieldPresence && surnameFieldPresence && peselFieldPresence && emailFieldPresence);

        //Clean Up
        WebElement logOutButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        logOutButton.click();

        WebElement adminButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]"));
        adminButton.click();

        Thread.sleep(1000);

        logIn(ADMIN);

        WebElement logInButton1 = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton1.click();

        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals(testName))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        driver.manage().window().fullscreen();
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        deleteButton.click();

        driver.close();
    }

    @Test
    public void specificDentistShouldFindCreatedAppointment() throws InterruptedException {
        createAnAppointment();
        Thread.sleep(10000);

        WebElement dentistButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        dentistButton.click();

        //Check
        logIn(USER);

        WebElement logInButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton.click();

        WebElement specificDentistButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[1]/a[2]"));
        specificDentistButton.click();

        Thread.sleep(1000);

        boolean nameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestName"));

        boolean surnameFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("IntegrationTestSurname"));

        boolean peselFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("1234"));

        boolean emailFieldPresence = driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .anyMatch(appointment -> appointment.getText().equals("Integration@test.com"));

        Assertions.assertTrue(nameFieldPresence && surnameFieldPresence && peselFieldPresence && emailFieldPresence);

        //Clean Up
        WebElement logOutButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        logOutButton.click();

        WebElement adminButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]"));
        adminButton.click();

        Thread.sleep(1000);

        logIn(ADMIN);


        WebElement logInButton1 = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-vertical-layout/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));
        logInButton1.click();

        driver.findElements(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-grid/vaadin-grid-cell-content")).stream()
                .filter(element -> element.getText().equals(testName))
                .forEach(WebElement::click);

        Thread.sleep(1000);

        driver.manage().window().fullscreen();
        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout[2]/vaadin-form-layout/vaadin-horizontal-layout/vaadin-button[2]"));
        deleteButton.click();

        driver.close();
    }
}
