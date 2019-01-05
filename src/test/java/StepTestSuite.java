import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

@RunWith(JUnit4.class)
public class StepTestSuite extends BaseTest {
    @Test
    public void addNewStep() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("add-new-step-btn");
        driver.findElement(By.id("add-new-step-btn")).click();
        driver.findElement(By.id("stepTitle")).sendKeys(FIRST_STEP_TITLE);
        driver.findElement(By.id("stepDescription")).sendKeys(FIRST_STEP_DESCRIPTION);
        new Select(driver.findElement(By.id("stepLevel"))).selectByValue(FIRST_STEP_LEVEL);
        driver.findElement(By.id("create-new-step-btn")).click();
        waitForItemVisible("step-title-2");
        Assert.assertEquals(FIRST_STEP_TITLE, driver.findElement(By.id("step-title-2")).getText());
        Assert.assertEquals(FIRST_STEP_DESCRIPTION, driver.findElement(By.id("step-description-2")).getText());
        Assert.assertEquals(FIRST_STEP_LEVEL, driver.findElement(By.id("step-level-2")).getText());
    }

    @Test
    public void testCancelEditStep() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("edit-step-0");
        driver.findElement(By.id("edit-step-0")).click();
        driver.findElement(By.id("stepTitle0")).clear();
        driver.findElement(By.id("stepTitle0")).sendKeys(NEW_FIRST_STEP_TITLE);
        driver.findElement(By.id("stepDescription0")).clear();
        driver.findElement(By.id("stepDescription0")).sendKeys(NEW_FIRST_STEP_DESCRIPTION);
        new Select(driver.findElement(By.id("stepLevel0"))).selectByVisibleText(NEW_FIRST_STEP_LEVEL);
        driver.findElement(By.id("reset-step-0")).click();
        waitForItemVisible("step-title-0");
        Assert.assertEquals(FIRST_STEP_TITLE, driver.findElement(By.id("step-title-0")).getText());
        Assert.assertEquals(FIRST_STEP_DESCRIPTION, driver.findElement(By.id("step-description-0")).getText());
        Assert.assertEquals(FIRST_STEP_LEVEL, driver.findElement(By.id("step-level-0")).getText());
    }

    @Test
    public void testSubmitEditStep() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("edit-step-0");
        driver.findElement(By.id("edit-step-0")).click();
        waitForItemVisible("stepTitle0");
        driver.findElement(By.id("stepTitle0")).clear();
        driver.findElement(By.id("stepTitle0")).sendKeys(NEW_FIRST_STEP_TITLE);
        driver.findElement(By.id("stepDescription0")).clear();
        driver.findElement(By.id("stepDescription0")).sendKeys(NEW_FIRST_STEP_DESCRIPTION);
        new Select(driver.findElement(By.id("stepLevel0"))).selectByVisibleText(NEW_FIRST_STEP_LEVEL);
        driver.findElement(By.id("submit-step-0")).click();
        waitForItemVisible("step-title-0");
        Assert.assertEquals(NEW_FIRST_STEP_TITLE, driver.findElement(By.id("step-title-0")).getText());
        Assert.assertEquals(NEW_FIRST_STEP_DESCRIPTION, driver.findElement(By.id("step-description-0")).getText());
        Assert.assertEquals(NEW_FIRST_STEP_LEVEL, driver.findElement(By.id("step-level-0")).getText());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteStep() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("edit-step-0");
        driver.findElement(By.id("edit-step-0")).click();
        waitForItemVisible("stepTitle0");
        driver.findElement(By.id("remove-step-0")).click();
        waitForItemInvisible("stepTitle0");
        driver.findElement(By.id("stepTitle0"));
    }
}
