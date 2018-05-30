package selen.one.framework.po;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selen.one.framework.utils.BaseEventListener;
import selen.one.framework.utils.BrowserCommands;
import selen.one.framework.utils.GlobalVars;
import selen.one.framework.utils.GlobalVars.ElementType;
import selen.one.framework.utils.SeleniumThreadSafeWebDriver;

public abstract class BasePageObject<T extends WebElement> {

	protected String pageURL = "";
	
	protected abstract String getURL();
	protected abstract void setURL(String url);
	
	protected BasePageObject() {
		PageFactory.initElements(SeleniumThreadSafeWebDriver.getInstance().getDriver(), this);
	}
	
	public void navigateTo(String url) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		driver.navigate().to(url);
		BrowserCommands.waitUntilURLContains(url, GlobalVars.TIMEOUT_TEN_SECONDS);
	}
	
	protected void switchTo(String page) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		By locator = By.xpath("a[contains(text(),'" + page + "')]");
		BrowserCommands.waitUntilElementIsClickable(locator, GlobalVars.TIMEOUT_MINUTE);
		driver.findElement(locator).click();
		BrowserCommands.waitUntilTitleContains(driver.getTitle(), GlobalVars.TIMEOUT_TEN_SECONDS);
	}
	
	public void matchPageTitle(String title) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		assertEquals(driver.getTitle(), title, "Matching Page title");
	}
	
	protected void matchPageURL(String url) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		assertEquals(driver.getCurrentUrl(), url, "Matching Page URL");
	}
	
	protected void verifyIfPageSourceContains(String snippet) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		assertTrue(driver.getPageSource().contains(snippet));
	}
	
	protected void matchElementsInnerText(String pattern, String text, ElementType elementType) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		By locator = null;
		locator = getLocatorOf(elementType, pattern);
		String innerText = driver.findElement(locator).getText();
		assertEquals(innerText, text, "Matching Element's innerText");
	}
	
	protected void registerEventListener() {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
	    eventDriver.register(new BaseEventListener());
	}
	
	protected void supressAlert (long timeOutInSeconds) {
		try {
			WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.alertIsPresent())).dismiss();
		} catch (NoAlertPresentException e) {
			// ignore exception
		}
	}

	protected boolean checkIfElementIsPresent(By locator) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, GlobalVars.TIMEOUT_TEN_SECONDS);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
			driver.findElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected void takeScreenshot() throws Exception {
		File scrFile = ((TakesScreenshot) SeleniumThreadSafeWebDriver.getInstance().getDriver())
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("target/imgs/" + LocalDateTime.now().toString() + ".png"));
	}
	
	private By getLocatorOf(ElementType elementType, String pattern) {
		By locator = null;
		switch (elementType) {
			case DIV: 
				locator = By.xpath("//div[contains(text(),'" + pattern + "')]"); 
				break;
			case HEADING : 
				locator = By.xpath("//h1[contains(text(),'" + pattern + "')]"); 
				break;
			case PARAGRAPH : 
				locator = By.xpath("//p[contains(text(),'" + pattern + "')]"); 
				break;
			case SPAN : 
				locator = By.xpath("//span[contains(text(),'" + pattern + "')]"); 
				break;
			case HREF:
				locator = By.xpath("//a[contains(text(),'" + pattern + "')]");
		}
		return locator;
	}
	
	public void refreshPage() {
		SeleniumThreadSafeWebDriver.getInstance().getDriver().navigate().refresh();
	}
	
	public void clearCookies() {
		SeleniumThreadSafeWebDriver.getInstance().getDriver().manage().deleteAllCookies();
	}
	public static void hoverOverElement(WebElement element) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
		WebDriverWait wait = new WebDriverWait(driver, GlobalVars.TIMEOUT_TEN_SECONDS);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
	}
	
}
