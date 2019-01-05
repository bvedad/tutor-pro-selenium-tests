import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;

import java.util.UUID;

@RunWith(JUnit4.class)
public class LoginTest extends BaseTest {
    @Test
    public void testLoginWithCorrectCredentials() {
        this.successLogin();
        Assert.assertEquals(BASE_URL + "/#!/tutorials?page=1", driver.getCurrentUrl());
    }

    @Test
    public void testLoginWithIncorrectCredentials() {
        waitForItemVisible("inputEmail");
        driver.findElement(By.id("inputEmail")).sendKeys(UUID.randomUUID().toString() + "@test.com");
        driver.findElement(By.id("inputPassword")).sendKeys(UUID.randomUUID().toString());
        driver.findElement(By.id("login-btn")).click();
        waitForItemVisible("login-error-message");
        Assert.assertTrue(driver.findElement(By.id("login-error-message")).isDisplayed());
    }
}
