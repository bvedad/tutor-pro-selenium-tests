import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

public abstract class BaseTest {
    public static final String BASE_URL = "http://tutor-pro.herokuapp.com";
    public static final String FIRST_TITLE = "First title-" + UUID.randomUUID();
    public static final String NEW_FIRST_TITLE = "New First title-" + UUID.randomUUID();;
    public static final String FIRST_DESCRIPTION = "First description-" + UUID.randomUUID();;
    public static final String NEW_FIRST_DESCRIPTION = "New First description-" + UUID.randomUUID();;
    public static final String FIRST_STEP_TITLE = "First step title-" + UUID.randomUUID();;
    public static final String NEW_FIRST_STEP_TITLE = "New First step title-" + UUID.randomUUID();;
    public static final String FIRST_STEP_DESCRIPTION = "First step description-" + UUID.randomUUID();;
    public static final String NEW_FIRST_STEP_DESCRIPTION = "New First step description-" + UUID.randomUUID();;
    public static final String FIRST_STEP_LEVEL = "5";
    public static final String NEW_FIRST_STEP_LEVEL = "9";
    public static final String SECOND_STEP_TITLE = "Second step title-" + UUID.randomUUID();;
    public static final String SECOND_STEP_DESCRIPTION = "Second step description-" + UUID.randomUUID();;
    public static final String SECOND_STEP_LEVEL = "6";
    protected WebDriver driver;
    User user;

    @Before
    public void setup() {
        user = new User("john.doe@test.com", "johndoe", "password123");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @After
    public void teardown() {
        driver.quit();
        driver = null;
        user = null;
    }

    void successLogin() {
        this.successLogin(user.getEmail(), user.getPassword());
    }

    void successLogin(String email, String password) {
        waitForItemVisible("inputEmail");
        driver.findElement(By.id("inputEmail")).sendKeys(email);
        driver.findElement(By.id("inputPassword")).sendKeys(password);
        driver.findElement(By.id("login-btn")).click();
        waitForItemVisible("tutorial-list-container");
    }

    void createTutorial() {
        driver.findElement(By.id("tutorial-post-tab-btn")).click();
        waitForItemVisible("title");
        driver.findElement(By.id("title")).sendKeys(FIRST_TITLE);
        driver.findElement(By.id("description")).sendKeys(FIRST_DESCRIPTION);
        driver.findElement(By.id("stepTitle0")).sendKeys(FIRST_STEP_TITLE);
        driver.findElement(By.id("step-description-0")).sendKeys(FIRST_STEP_DESCRIPTION);
        new Select(driver.findElement(By.id("level-select-0"))).selectByValue(FIRST_STEP_LEVEL);
        driver.findElement(By.id("add-new-step-btn")).click();
        driver.findElement(By.id("stepTitle1")).sendKeys(SECOND_STEP_TITLE);
        driver.findElement(By.id("step-description-1")).sendKeys(SECOND_STEP_DESCRIPTION);
        new Select(driver.findElement(By.id("level-select-1"))).selectByValue(SECOND_STEP_LEVEL);
        driver.findElement(By.id("create-tutorial-btn")).click();
        waitForItemVisible("tutorial-details-container");
    }

    void waitForTextNotEmpty(final String id) {
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @NullableDecl
            public Boolean apply(@NullableDecl WebDriver d) {
                return d.findElement(By.id(id)).
                        getText().length() != 0;
            }
        });
    }

    void waitForItemVisible(String id) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    void waitForItemInvisible(String id) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
    }

    class User {
        private String email;
        private String username;
        private String password;

        public User(String email, String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
