package tests;

import config.TestConfig;
import data.ExcelDataProvider;
import pages.LoginPage;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class LoginDDTTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        if (TestConfig.runMode().equals("browserstack")) {
            driver = createBrowserStackDriver();
        } else {
            driver = createLocalDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
    public void loginDDT(String testCaseId, String username, String password, String expected) {
        LoginPage page = new LoginPage(driver);
        page.open(TestConfig.BASE_URL);

        page.login(username, password);
        String msg = page.messageText();

        boolean isSuccess = msg.toLowerCase().contains("you logged into a secure area");
        boolean isError = msg.toLowerCase().contains("your username is invalid")
                || msg.toLowerCase().contains("your password is invalid");

        boolean passed;
        if ("SUCCESS".equalsIgnoreCase(expected)) {
            passed = isSuccess;
        } else if ("ERROR".equalsIgnoreCase(expected)) {
            passed = isError;
        } else {
            throw new IllegalArgumentException("Expected must be SUCCESS or ERROR, but was: " + expected);
        }

        // ✅ log per dataset
        System.out.println("[" + testCaseId + "] expected=" + expected + " => " + (passed ? "PASSED" : "FAILED")
                + " | msg=" + msg.replace("\n", " "));

        Assert.assertTrue(passed, "[" + testCaseId + "] Assertion failed. msg=" + msg);
    }

    private WebDriver createLocalDriver() {
        return switch (TestConfig.browser()) {
            case "firefox" -> new FirefoxDriver();
            default -> new ChromeDriver();
        };
    }

    private WebDriver createBrowserStackDriver() throws Exception {
        String user = TestConfig.bsUser();
        String key = TestConfig.bsKey();
        if (user.isBlank() || key.isBlank()) {
            throw new IllegalStateException("Set BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY");
        }

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserName", TestConfig.browser().equals("firefox") ? "Firefox" : "Chrome");
        caps.setCapability("browserVersion", "latest");

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("sessionName", "Assignment6-DDT-" + TestConfig.browser());
        bstackOptions.put("buildName", "Assignment6-Java-DDT");
        caps.setCapability("bstack:options", bstackOptions);

        URL hub = new URL("https://" + user + ":" + key + "@hub-cloud.browserstack.com/wd/hub");
        return new RemoteWebDriver(hub, caps);
    }
}
