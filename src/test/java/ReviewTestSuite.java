import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.UUID;

@RunWith(JUnit4.class)
public class ReviewTestSuite extends BaseTest{
    static final String REVIEW_BODY = "Review body" + UUID.randomUUID();
    static final String NEW_REVIEW_BODY = "New Review body" + UUID.randomUUID();

    private void createReview() {
        this.successLogin();
        this.createTutorial();
        driver.findElement(By.id("review-body-input")).sendKeys(REVIEW_BODY);
        driver.findElement(By.id("post-review-btn")).click();
        waitForItemVisible("review-body-txt-0");
    }

    @Test
    public void testPostReview() {
        createReview();
        Assert.assertEquals(REVIEW_BODY, driver.findElement(By.id("review-body-txt-0")).getText());
        Assert.assertTrue(driver.findElement(By.id("review-posted-by-txt-0")).getText().contains("Posted by " + user.getUsername() + " on "));
    }

    @Test
    public void testCancelEditMode() {
        createReview();
        driver.findElement(By.id("edit-review-btn-0")).click();
        driver.findElement(By.id("review-body-input-0")).clear();
        driver.findElement(By.id("review-body-input-0")).sendKeys(NEW_REVIEW_BODY);
        driver.findElement(By.id("reset-review-btn-0")).click();
        Assert.assertEquals(REVIEW_BODY, driver.findElement(By.id("review-body-txt-0")).getText());
    }

    @Test
    public void testSubmitEditMode() {
        createReview();
        driver.findElement(By.id("edit-review-btn-0")).click();
        driver.findElement(By.id("review-body-input-0")).clear();
        driver.findElement(By.id("review-body-input-0")).sendKeys(NEW_REVIEW_BODY);
        driver.findElement(By.id("submit-review-btn-0")).click();
        waitForItemVisible("review-body-txt-0");
        Assert.assertEquals(NEW_REVIEW_BODY, driver.findElement(By.id("review-body-txt-0")).getText());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteReview() {
        this.createReview();
        waitForItemVisible("edit-review-btn-0");
        driver.findElement(By.id("edit-review-btn-0")).click();
        driver.findElement(By.id("remove-review-btn-0")).click();
        waitForItemInvisible("review-body-txt-0");
        driver.findElement(By.id("review-body-txt-0"));
    }

    @Test
    public void testLike() {
        this.createReview();
        driver.findElement(By.id("like-review-0")).click();
        waitForItemInvisible("like-review-spinner-0");
        Assert.assertTrue(driver.findElement(By.id("favorited-review-0")).isDisplayed());
    }

    @Test
    public void testUnlike() {
        this.createReview();
        driver.findElement(By.id("like-review-0")).click();
        waitForItemInvisible("like-review-spinner-0");
        driver.findElement(By.id("like-review-0")).click();
        waitForItemInvisible("like-review-spinner-0");
        Assert.assertTrue(driver.findElement(By.id("unfavorited-review-0")).isDisplayed());
    }

    @Test
    public void testLikeCountIfTutorialLiked() {
        this.createReview();
        int favoritesCount = Integer.valueOf(driver.findElement(By.id("review-like-count-0")).getText());
        driver.findElement(By.id("like-review-0")).click();
        waitForItemInvisible("like-review-spinner-0");
        Assert.assertEquals(Integer.valueOf(favoritesCount + 1), Integer.valueOf(driver.findElement(By.
                id("review-like-count-0")).getText()));
    }

    @Test
    public void testLikeCountIfTutorialUnliked() {
        this.createReview();
        int favoritesCount = Integer.valueOf(driver.findElement(By.id("review-like-count-0")).getText());
        driver.findElement(By.id("like-review-0")).click();
        waitForItemInvisible("like-review-spinner-0");
        driver.findElement(By.id("like-review-0")).click();
        waitForItemInvisible("like-review-spinner-0");
        Assert.assertEquals(Integer.valueOf(favoritesCount), Integer.valueOf(driver.findElement(By.
                id("review-like-count-0")).getText()));
    }
}
