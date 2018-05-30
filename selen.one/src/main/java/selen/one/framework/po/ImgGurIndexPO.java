package selen.one.framework.po;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import selen.one.framework.utils.BrowserCommands;
import selen.one.framework.utils.GlobalVars;

public class ImgGurIndexPO<T extends WebElement> extends BasePageObject<T> {

	private String PAGE_TITLE = "IMGGUR_INDEX";
	public static final String PAGE_URL = "https://imgur.com/";

	// @FindBy(css = "#front-page-beta > div.BetaSignUp-link > a")
	// protected T betaSignUpPopUP;

	@FindBy(css = "#secondary-nav > ul > li.signin-link > a")
	protected T logInButton;

	@FindBy(css = "#username")
	protected T usernameField;

	@FindBy(css = "#password")
	protected T passwordField;

	@FindBy(css = "#secondary-nav > ul > li.account > a")
	protected T userAccountButton;

	@FindBy(css = "#secondary-nav > ul > li.account > div.user-dropdown-container > ul > li:nth-child(1) > a")
	protected T userImagesButton;

	@FindBy(css = "#signin-form > div.signin-button > button")
	protected T signInButton;

	@FindBy(css = "#topbar > div > span.upload-button-container > a > span.upload-btn-text")
	protected T newPostButton;

	@FindBy(css = "#paste-url-input")
	protected T uploadImageDropBox;

	@FindBy(css = "#content > div.left > div > div:nth-child(3) > h2 > span.total-images")
	protected T imgCount;

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
		// if (betaSignUpPopUP != null && betaSignUpPopUP.isDisplayed()) {
		// betaSignUpPopUP.click();
		// }
		BrowserCommands.waitUntilElementIsClickable(this.logInButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.logInButton.click();
		BrowserCommands.waitUntilElementIsClickable(this.usernameField, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.usernameField.sendKeys(username);
		BrowserCommands.waitUntilElementIsClickable(this.passwordField, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.passwordField.sendKeys(password);
		BrowserCommands.waitUntilElementIsClickable(this.signInButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		signInButton.click();
	}

	public void navigateToImages() {
		BrowserCommands.waitUntilElementIsClickable(this.userAccountButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		BrowserCommands.hoverOverElement(this.userAccountButton);
		BrowserCommands.waitUntilElementIsClickable(this.userImagesButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.userImagesButton.click();
	}

	public void postNewImage() {
		By imageAddedAlert = By.cssSelector("#FlipInfo-Container > div > span");
		BrowserCommands.waitUntilElementIsClickable(this.imgCount, GlobalVars.TIMEOUT_TEN_SECONDS);
		int prevImgCount = Integer.parseInt(this.imgCount.getText());

		BrowserCommands.waitUntilElementIsClickable(this.newPostButton, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.newPostButton.click();

		BrowserCommands.waitUntilElementIsClickable(this.uploadImageDropBox, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.uploadImageDropBox.sendKeys("https://i.imgur.com/7k5Icld.jpg");
		
		BrowserCommands.waitUntilElementIsPresent(imageAddedAlert, GlobalVars.TIMEOUT_TEN_SECONDS);
		this.navigateToImages();
		
		int currentImgCount = Integer.parseInt(this.imgCount.getText());
		assertTrue(currentImgCount - prevImgCount == 1);
	}

	
}
