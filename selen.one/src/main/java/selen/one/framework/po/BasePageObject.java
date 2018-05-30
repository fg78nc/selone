package selen.one.framework.po;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

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
	
	public void switchTo(String page) {
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
	
	public void matchPageURL(String url) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		assertEquals(driver.getCurrentUrl(), url, "Matching Page URL");
	}
	
	public void verifyIfPageSourceContains(String snippet) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		assertTrue(driver.getPageSource().contains(snippet));
	}
	
	public void matchElementsInnerText(String pattern, String text, ElementType elementType) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		By locator = null;
		locator = getLocatorOf(elementType, pattern);
		String innerText = driver.findElement(locator).getText();
		assertEquals(innerText, text, "Matching Element's innerText");
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
	
}
