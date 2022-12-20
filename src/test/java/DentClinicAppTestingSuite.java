import com.dent.config.WebDriverConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class DentClinicAppTestingSuite {
    private static final String BASE_URL = "http://localhost:8085/home";
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



//        WebElement bookButton = driver.findElement(By.xpath(""));
//        patientButton.click();
    }

    @Test
    void test() throws InterruptedException {
        createAnAppointment();
    }
}
