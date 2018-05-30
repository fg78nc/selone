package selen.one.framework.po;import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import selen.one.framework.utils.BrowserCommands;
import selen.one.framework.utils.GlobalVars;

public class ImgGurIndexPO<T extends WebElement> extends BasePageObject<T> {

	private String PAGE_TITLE = "IMGGUR_INDEX";
	public static final String PAGE_URL = "https://imgur.com/";
	
//	@FindBy(css = "#front-page-beta > div.BetaSignUp-link > a")
//	protected T betaSignUpPopUP;
	
	@FindBy(css = "#secondary-nav > ul > li.signin-link > a")
	protected T logInButton;

	@FindBy(css = "#username")
	protected T usernameField;
	
	@FindBy(css = "#password")
	protected T passwordField;
	
	@FindBy(css = "")
	protected T userAccountButton;
	
	@FindBy(css = "#secondary-nav > ul > li.account > div.user-dropdown-container > ul > li:nth-child(1) > a")
	protected T userImagesButton;
	
	@FindBy(css = "#signin-form > div.signin-button > button")
	protected T signInButton;
	
	@FindBy(css = "#topbar > div > span.upload-button-container > a > span.upload-btn-text")
	protected T newPostButton;
	
	@FindBy(css = "#upload-modal > div.primary-actions > div.drag-drop-box > div")
	protected T uploadImageDropBox;
	
	public ImgGurIndexPO() {
		super();
		this.setURL(PAGE_URL);
	}

	@Override
	protected String getURL() {
		return this.pageURL;
	}

	@Override
	protected void setURL(String url) {
		this.pageURL = url;
		
	}

	public void signIn(String username, String password) {
//		if (betaSignUpPopUP != null && betaSignUpPopUP.isDisplayed()) {
//			betaSignUpPopUP.click();
//		}
		BrowserCommands.waitUntilElementIsClickable(this.logInButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.logInButton.click();
		BrowserCommands.waitUntilElementIsClickable(this.usernameField, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.usernameField.sendKeys(username);
		BrowserCommands.waitUntilElementIsClickable(this.passwordField, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.passwordField.sendKeys(password);
		BrowserCommands.waitUntilElementIsClickable(this.signInButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		signInButton.click();
	}
	
	public void navigateToUserImages() {
		BrowserCommands.waitUntilElementIsClickable(this.userImagesButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.userImagesButton.click();
	}
	
	public void postNewImage() {
		BrowserCommands.waitUntilElementIsClickable(this.newPostButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.newPostButton.click();
		
		BrowserCommands.waitUntilElementIsClickable(this.uploadImageDropBox, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.uploadImageDropBox.sendKeys("/Users/fg/eclipse-workspace/selen.one/src/main/resources/vang.jpg");
		
		
		
	}
}
