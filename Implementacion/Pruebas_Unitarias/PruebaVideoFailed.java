package pruebas;

import java.util.regex.Pattern;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PruebaVideoFailed {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
		File file = new File("C:\\Users\\jjime\\Desktop\\geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:3000/";
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void testVideoFailed() throws Exception {
    driver.get(baseUrl + "");
    Thread.sleep(3000);
    driver.findElement(By.cssSelector("input[type=\"file\"]")).clear();
    driver.findElement(By.cssSelector("input[type=\"file\"]")).sendKeys("D:\\USUARIOS\\Yordan\\DOCUMENTOS\\Documents\\comu.txt");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    assertEquals("Dirección del video es incorrecta.", closeAlertAndGetItsText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
    String serverPath = "C:\\Users\\jjime\\Documents\\GitHub\\Proyecto_ACS_Analisis_no_supervisado\\Implementacion\\Código\\Server\\downloads\\output.avi";
    File f = new File(serverPath);
    if (f.exists())
    	f.delete();
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
