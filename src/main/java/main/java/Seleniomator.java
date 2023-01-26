package main.java;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Seleniomator {
    private static final WebDriver driver = new ChromeDriver();

    public static void openBrowser(String url) {
        driver.get(url);

        driver.close();
    }
}
