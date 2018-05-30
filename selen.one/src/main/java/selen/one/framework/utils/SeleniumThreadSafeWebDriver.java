package selen.one.framework.utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class SeleniumThreadSafeWebDriver {

	private static final SeleniumThreadSafeWebDriver INSTANCE = new SeleniumThreadSafeWebDriver();
	private static final int IMPLICIT_TIMEOUT = 0;
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
	private ThreadLocal<String> sessionId = new ThreadLocal<>();
	private ThreadLocal<String> browser = new ThreadLocal<>();
	private ThreadLocal<String> platform = new ThreadLocal<>();
	private ThreadLocal<String> version = new ThreadLocal<>();
	
	private Properties props = new Properties();
	private String setEnvironment = null;
	
	private SeleniumThreadSafeWebDriver() {
	}

	public static SeleniumThreadSafeWebDriver getInstance() {
		return INSTANCE;
	}
	
	@SafeVarargs
	public final void setDriver(String browser, String platform, String env, Map<String, Object> ... prefs) throws Exception {
		DesiredCapabilities extraCaps = null;
		props.load(new FileInputStream(GlobalVars.PROPERTIES_PATH));
		
		switch (browser) {
		case "chrome": 
			extraCaps = DesiredCapabilities.chrome();
			
			ChromeOptions chromeOpt = new ChromeOptions();
			Map<String, Object> chromePrefs = new HashMap<>();
			
			chromePrefs.put("credentials_enable_service", false);
			chromePrefs.put("profile.password_manager_enabled", false);
			chromeOpt.addArguments("--disable-popup-blocking");
			
			extraCaps.setCapability(ChromeOptions.CAPABILITY, chromeOpt);
			extraCaps.setCapability("applicationCacheEnabled", false);
			
			if(env.equalsIgnoreCase("local")) {
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
				webDriver.set(new ChromeDriver(chromeOpt.merge(extraCaps)));
			}
			break;
		case "firefox":
			extraCaps = DesiredCapabilities.firefox();
			FirefoxOptions firefoxOpt = new FirefoxOptions();
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			
			extraCaps.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
			extraCaps.setCapability("marionette", true);
			
			if(env.equalsIgnoreCase("local")) {
				System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
				webDriver.set(new FirefoxDriver(firefoxOpt.merge(extraCaps)));
			}
			break;
		default :
			throw new IllegalArgumentException("Driver is not supported");
		}
		
		this.setEnvironment = env;
		this.sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
		this.browser.set(extraCaps.getBrowserName());
		this.version.set(extraCaps.getVersion());
		this.platform.set(platform);
		
		System.out.println("\n -=[ TEST ENV: "
		+ "| " + this.getBrowser() 
		+ "| " + this.getSessionId()
		+ "| " + this.getPlatform()
		+ "| " + this.setEnvironment
		+ "| Selenium rev:" + props.getProperty("selenium.version")
		);
		
		this.getDriver().manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		this.getDriver().manage().window().maximize();
	}
	
	public WebDriver getDriver() {
		return this.webDriver.get();
	}
	
	public void closeDriver() {
		try {
			this.getDriver().close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String getSessionId() {
		return sessionId.get();
	}

	public String getBrowser() {
		return browser.get();
	}

	public String getPlatform() {
		return platform.get();
	}

	public String getVersion() {
		return version.get();
	}
	
}
