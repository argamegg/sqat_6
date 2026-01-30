package config;

public class TestConfig {
    public static final String BASE_URL = "https://the-internet.herokuapp.com/login";

    // local | browserstack
    public static String runMode() {
        return System.getProperty("runMode", "local").toLowerCase();
    }

    // chrome | firefox
    public static String browser() {
        return System.getProperty("browser", "chrome").toLowerCase();
    }

    public static String bsUser() {
        String v = System.getenv("BROWSERSTACK_USERNAME");
        return (v != null) ? v : System.getProperty("bsUser", "");
    }

    public static String bsKey() {
        String v = System.getenv("BROWSERSTACK_ACCESS_KEY");
        return (v != null) ? v : System.getProperty("bsKey", "");
    }
}
