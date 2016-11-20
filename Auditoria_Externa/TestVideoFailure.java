package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestVideoFailure {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		File file = new File("C:\\Users\\fm010\\Desktop\\geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testVideoFailure() throws Exception {
		driver.get(baseUrl + "/app/#/home");
		driver.findElement(By.id("selectVideoInput")).clear();
		driver.findElement(By.id("selectVideoInput")).sendKeys(
				"C:\\Users\\fm010\\Desktop\\ACS_SegmentTemp-master\\Implementación\\Server_side_java_components\\Dissolve1-15.mp4");
		Thread.sleep(10000);
		driver.findElement(By.cssSelector("button.confirm")).click();
		driver.findElement(By.xpath("//div[@id='start']/i")).click();
		Thread.sleep(10000);
		driver.findElement(By.cssSelector("button.confirm")).click();
		Thread.sleep(15000);
		driver.findElement(By.cssSelector("button.confirm")).click();
		assertEquals("Conexión cerrada", driver.findElement(By.cssSelector("h2")).getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
