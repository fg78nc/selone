package selen.one.framework.utils;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class BrowserCommands {

	private BrowserCommands() {
	}

	public static void waitUntilElementIsPresentByLocating(By locator, long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
	}

	public static void waitUntilElementIsVisible(WebElement element, long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
	}

	public static void waitUntilElementIsClickable(By locator, long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
	}
	
	public static void switchToFrameByIdAndClose(String id) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		String currentWindow = driver.getWindowHandle();
		driver.switchTo().frame(id).close();
		driver.switchTo().frame(currentWindow);
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
		// new WebDriverWait(driver, 10).until( d -> {
		// return d.getTitle().toLowerCase().contains(text);
		// }
		// );
	}
	
	public static void waitUntilExecuted(Function<? super WebDriver,? super WebElement> function,
													int timeOutInMillis, int pollingMillis, 
													Collection<Class<? extends Throwable>> throwables) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofMillis(timeOutInMillis))
			.pollingEvery(Duration.ofMillis(pollingMillis))
			.ignoreAll(throwables)
			.until(function);
	}

	public static void waitUntilPageIsLoaded(long timeOutInSeconds) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		// WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		// String script =
		// "(()=>{while(true){if(window.document.readyState==='complete') return '
		// ';}})();";
		// wait.until(ExpectedConditions.refreshed(ExpectedConditions.jsReturnsValue(script)));
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.refreshed(d -> ((JavascriptExecutor) d)
				.executeScript("return document.readyState === 'complete'", new Object())));
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



}
