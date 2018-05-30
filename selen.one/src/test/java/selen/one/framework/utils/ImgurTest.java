package selen.one.framework.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/* 
 * Test 1: Upload an image to the Imgur website
* 
* Step 1 - Go to https://imgur.com/
* Step 2 - Verify Imgur Page title
* Step 3 - Perform sign in
* Step 4 - Navigate to user->images and retrieve actual number of uploaded files
* Step 5 - Click on 'New post' button
* Step 6 - Upload new file
* Step 7 - Navigate to user->images and retrieve actual number of uploaded files
* Assert - Number of uploaded files has been incremented by 1
 */

import selen.one.framework.po.ImgGurIndexPO;

public class ImgurTest {
	private ImgGurIndexPO<WebElement> indexPO = null;
	private final String DATA_FILE = "src/test/resources/imgur_test.json";
	
	@BeforeSuite(alwaysRun = true, enabled = true)
	@Parameters({"environment"})
	protected void suiteSetup(@Optional(GlobalVars.ENVIRONMENT) String env, ITestContext context) {
		GlobalVars.defaultEnv = System.getProperty("environment", env);
		GlobalVars.suiteName = context.getSuite().getXmlSuite().getName();
	}
	
	@AfterSuite(alwaysRun= false, enabled=false)
	protected void suiteTeardown() {
	}
	
	@BeforeTest(alwaysRun = true, enabled = true)
	@Parameters({"browser", "platform", "includePattern", "excludePattern"})
	protected void setupTest(@Optional(GlobalVars.BROWSER) String browser,
								@Optional(GlobalVars.PLATFORM) String platform,
								@Optional String includePattern,
								@Optional String excludePattern,
								ITestContext ctx) throws Exception {
		if (includePattern != null ) {
			System.setProperty("includePattern", includePattern);
		}
		if (excludePattern != null ) {
			System.setProperty("excludePattern", excludePattern);
		}
		
		GlobalVars.defaultBrowser = System.getProperty("browser", browser);
		GlobalVars.defaultPlatform = System.getProperty("platform", platform);
		
		SeleniumThreadSafeWebDriver.getInstance().setDriver(
				GlobalVars.defaultBrowser,
				GlobalVars.defaultPlatform,
				GlobalVars.defaultEnv);
	}
	
	@BeforeClass(alwaysRun = true, enabled = true)
	protected void testClassSetup(ITestContext ctx) {
		indexPO = new ImgGurIndexPO<>();
		JSONDataProvider.dataFile = this.DATA_FILE;
		indexPO.navigateTo(ImgGurIndexPO.PAGE_URL);
	}
	
	@BeforeMethod(alwaysRun = true, enabled = true)
	protected void testMethodSetup(ITestResult result) throws Exception {
    }
	
	@AfterTest(alwaysRun = true, enabled = true)
	protected void testTeardown() {
		SeleniumThreadSafeWebDriver.getInstance().closeDriver();
	}
	
	@AfterMethod(alwaysRun = true, enabled = true)
	protected void testMethodTearDown(ITestResult result) {
		WebDriver driver = SeleniumThreadSafeWebDriver.getInstance().getDriver();
		if (!driver.getCurrentUrl().contains(ImgGurIndexPO.PAGE_URL)){
			indexPO.navigateTo(ImgGurIndexPO.PAGE_URL);
		}
	}
	
	@AfterClass(alwaysRun = false, enabled = false)
	protected void testClassTeardown(ITestContext ctx) {
		
	}
	
	@Test(dataProvider="dataFromJSON" , dataProviderClass=JSONDataProvider.class, enabled=true, priority=1, groups="IMGUR_TEST")
	public void tc001_imgur_test(String rowID, String description, JSONObject testData) {
		
		indexPO.navigateTo(GlobalVars.BASE_URL);
		indexPO.matchPageTitle("Imgur: The magic of the Internet");
		indexPO.signIn(testData.get("username").toString(), testData.get("password").toString());
		indexPO.navigateToImages();
		indexPO.postNewImage();
	}
}
