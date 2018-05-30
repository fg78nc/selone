package selen.one.framework.utils;

import java.io.File;

public final class GlobalVars {

	private GlobalVars() {
	}
	
	public static final String BASE_URL = "https://imgur.com";
	public static final String BROWSER = "chrome";
	public static final String PLATFORM = "mac";
	public static final String ENVIRONMENT = "local";
	
	public static String defaultEnv = null;
	public static String defaultBrowser = null;
	public static String defaultPlatform = null;

	public static String suiteName = null;
	public static String propertiesFile = "src/main/resources/selenium.properties";
	
	public static final String PROPERTIES_PATH = new File(propertiesFile).getAbsolutePath();
	
	public static final long TIMEOUT_MINUTE = 60;
	public static final long TIMEOUT_HALF_MINUTE = 30;
	public static final long TIMEOUT_TEN_SECONDS = 10;
	
	public static enum ElementType { HEADING, SPAN, PARAGRAPH, DIV, HREF };
	
	
}
