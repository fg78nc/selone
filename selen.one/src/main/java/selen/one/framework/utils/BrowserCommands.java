package selen.one.framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class BrowserCommands {

	private BrowserCommands() {
	}
	
	public static void waitUntilElementIsClickable(By locator, long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
	}
	
	public static void waitUntilElementIsClickable(WebElement element, long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}
	
	public static void waitUntilURLContains(String text, long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(text)));
	}
	
	public static void waitUntilTitleContains(String text, long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(text)));
	}
	
	public static void waitUntilPageIsLoaded(long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		String script = "(()=>{while(true){if(document.readyState==='complete') return '';}})();";
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.jsReturnsValue(script)));
	}
	
	public static void click(By locator) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebElement element = driver.findElement(locator);
		element.click();
	}
	
	public static void executeJSScript(String script, By locator, boolean isAsync) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebElement element = driver.findElement(locator);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		if (isAsync) {
			jsExecutor.executeAsyncScript("var cb = arguments[arguments.length - 1];" + script, element);
		} else {
			jsExecutor.executeScript(script, element);
		}
	}

	public static void hoverOverElement(WebElement userAccountButton) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		Actions builder = new Actions(driver);
		builder.moveToElement(userAccountButton).build().perform();
		WebDriverWait wait = new WebDriverWait(driver, GlobalVars.TIMEOUT_TEN_SECONDS);
	}
	
}
