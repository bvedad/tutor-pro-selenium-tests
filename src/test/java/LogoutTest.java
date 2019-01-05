import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;

@RunWith(JUnit4.class)
public class LogoutTest extends BaseTest {
    @Test
    public void testLogout() {
        this.successLogin();
        driver.findElement(By.id("logout-btn")).click();
        driver.findElement(By.id("confirm-logout-btn")).click();
        Assert.assertEquals(BASE_URL + "/#!/login?page=1", driver.getCurrentUrl());
    }
}