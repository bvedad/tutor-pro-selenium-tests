import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;


@RunWith(JUnit4.class)
public class TutorialTestSuite extends BaseTest {

    @Test
    public void testPostTutorial() {
        this.successLogin();
        this.createTutorial();
        waitForTextNotEmpty("tutorial-title");
        Assert.assertEquals(FIRST_TITLE, driver.findElement(By.id("tutorial-title")).getText());
        Assert.assertEquals(FIRST_DESCRIPTION, driver.findElement(By.id("tutorial-description")).getText());
        Assert.assertEquals(FIRST_STEP_TITLE, driver.findElement(By.id("step-title-0")).getText());
        Assert.assertEquals(FIRST_STEP_DESCRIPTION, driver.findElement(By.id("step-description-0")).getText());
        Assert.assertEquals(FIRST_STEP_LEVEL, driver.findElement(By.id("step-level-0")).getText());
        Assert.assertEquals(SECOND_STEP_TITLE, driver.findElement(By.id("step-title-1")).getText());
        Assert.assertEquals(SECOND_STEP_DESCRIPTION, driver.findElement(By.id("step-description-1")).getText());
        Assert.assertEquals(SECOND_STEP_LEVEL, driver.findElement(By.id("step-level-1")).getText());
    }

    @Test
    public void testTutorialListAfterPostingTutorial() {
        this.successLogin();
        this.createTutorial();
        driver.findElement(By.id("tutorial-list-tab-btn")).click();
        waitForTextNotEmpty("tutorial-list-container");
        Assert.assertEquals(BASE_URL + "/#!/tutorials?page=1", driver.getCurrentUrl());
        waitForTextNotEmpty("tutorial-title-0");
        Assert.assertEquals(FIRST_TITLE, driver.findElement(By.id("tutorial-title-0")).getText());
        Assert.assertEquals(FIRST_DESCRIPTION, driver.findElement(By.id("tutorial-description-0")).getText());
        Assert.assertTrue(driver.findElement(By.id("tutorial-created-by-txt-0")).getText().contains("Created by " + user.getUsername() + " at "));
    }

    @Test
    public void testDeleteTutorial() {
        this.successLogin();
        this.createTutorial();
        driver.findElement(By.id("tutorial-list-tab-btn")).click();
        waitForItemVisible("tutorial-title-0");
        final String firstTitle = driver.findElement(By.id("tutorial-title-0")).getText();
        driver.findElement(By.id("more-details-0")).click();
        waitForItemVisible("delete-tutorial-btn");
        driver.findElement(By.id("delete-tutorial-btn")).click();
        waitForItemVisible("tutorial-title-0");
        Assert.assertNotEquals(firstTitle, driver.findElement(By.id("tutorial-title-0")).getText());
    }

    @Test
    public void testCancelTitleEdit() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("edit-title");
        driver.findElement(By.id("edit-title")).click();
        waitForItemVisible("title");
        driver.findElement(By.id("title")).clear();
        driver.findElement(By.id("title")).sendKeys(NEW_FIRST_TITLE);
        driver.findElement(By.id("reset-title")).click();
        waitForItemVisible("tutorial-title");
        Assert.assertEquals(FIRST_TITLE, driver.findElement(By.id("tutorial-title")).getText());
    }

    @Test
    public void testSubmitTitleEdit() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("edit-title");
        driver.findElement(By.id("edit-title")).click();
        driver.findElement(By.id("title")).clear();
        driver.findElement(By.id("title")).sendKeys(NEW_FIRST_TITLE);
        driver.findElement(By.id("submit-title")).click();
        Assert.assertEquals(NEW_FIRST_TITLE, driver.findElement(By.id("tutorial-title")).getText());
    }

    @Test
    public void testSubmitDescriptionEdit() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("tutorial-description");
        waitForItemVisible("edit-description");
        driver.findElement(By.id("edit-description")).click();
        waitForItemVisible("description");
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys(NEW_FIRST_DESCRIPTION);
        driver.findElement(By.id("reset-description")).click();
        waitForItemVisible("tutorial-description");
        Assert.assertEquals(FIRST_DESCRIPTION, driver.findElement(By.id("tutorial-description")).getText());
    }

    @Test
    public void testResetDescriptionEdit() {
        this.successLogin();
        this.createTutorial();
        waitForItemVisible("edit-description");
        driver.findElement(By.id("edit-description")).click();
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys(NEW_FIRST_DESCRIPTION);
        driver.findElement(By.id("submit-description")).click();
        waitForItemVisible("tutorial-description");
        Assert.assertEquals(NEW_FIRST_DESCRIPTION, driver.findElement(By.id("tutorial-description")).getText());
    }

    @Test
    public void testLike() {
        this.successLogin();
        this.createTutorial();
        driver.findElement(By.id("mark-favorite-btn")).click();
        waitForItemInvisible("favorite-loader");
        Assert.assertTrue(driver.findElement(By.id("tutorial-favorited")).isDisplayed());
    }

    @Test
    public void testUnlike() {
        this.successLogin();
        this.createTutorial();
        driver.findElement(By.id("mark-favorite-btn")).click();
        waitForItemInvisible("favorite-loader");
        driver.findElement(By.id("mark-favorite-btn")).click();
        waitForItemInvisible("favorite-loader");
        Assert.assertTrue(driver.findElement(By.id("tutorial-unfavorited")).isDisplayed());
    }

    @Test
    public void testLikeCountIfTutorialLiked() {
        this.successLogin();
        this.createTutorial();
        waitForTextNotEmpty("favorites-count-txt");
        int favoritesCount = Integer.valueOf(driver.findElement(By.id("favorites-count-txt")).getText());
        driver.findElement(By.id("mark-favorite-btn")).click();
        waitForItemInvisible("favorite-loader");
        Assert.assertEquals(Integer.valueOf(favoritesCount + 1), Integer.valueOf(driver.findElement(By.
                id("favorites-count-txt")).getText()));
    }

    @Test
    public void testLikeCountIfTutorialUnliked() {
        this.successLogin();
        this.createTutorial();
        waitForTextNotEmpty("favorites-count-txt");
        int favoritesCount = Integer.valueOf(driver.findElement(By.id("favorites-count-txt")).getText());
        driver.findElement(By.id("mark-favorite-btn")).click();
        waitForItemInvisible("favorite-loader");
        driver.findElement(By.id("mark-favorite-btn")).click();
        waitForItemInvisible("favorite-loader");
        Assert.assertEquals(Integer.valueOf(favoritesCount), Integer.valueOf(driver.findElement(By.
                id("favorites-count-txt")).getText()));
    }
}