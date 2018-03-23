package com.automation.accelerators;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppiumActionEngine extends TestEngine{
	private static final Logger LOG = Logger.getLogger(AppiumActionEngine.class);
	
	private final String msgClickSuccess = "Successfully Clicked On ";
	private final String msgClickFailure = "Unable To Click On ";
	private final String msgTypeSuccess = "Successfully Typed On ";
	private final String msgTypeFailure = "Unable To Type On ";
	private final String msgIsElementFoundSuccess = "Successfully Found Element ";
	private final String msgIsElementFoundFailure = "Unable To Found Element ";
	
	/**
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Throwable
	 */
	public boolean click(By locator, String locatorName) throws Throwable
	{
		
		boolean status = false;
		try
		{
		this.appiumDriver.findElement(locator).click();
		this.reporter.SuccessReport("Click" , this.msgClickSuccess + locatorName);
		status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			this.reporter.failureReport("Click", this.msgClickFailure + locatorName, this.appiumDriver);
			
		}
		return status;
		
	}
	
	public void sleep(int time) throws InterruptedException{
		Thread.sleep(time);
	}
	public boolean VerifyElementPresent(By by, String locatorName,boolean expected) throws Throwable
	{
		boolean status = false;
		try
		{
			if(this.appiumDriver.findElement(by).isDisplayed()){
				this.reporter.SuccessReport("VerifyElementPresent "+locatorName , this.msgIsElementFoundSuccess + locatorName);
				status = true;
			}
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			/*if(expected == status)
			{
				this.reporter.SuccessReport("isElementPresent", "isElementPresent");
			}
			else
			{
				this.reporter.failureReport("isElementPresent", this.msgIsElementFoundFailure + locatorName, this.Driver);
			}*/
		}
		return status;
	}
	public boolean selectByIndex(By locator, int index,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(appiumDriver.findElement(locator));
			s.selectByIndex(index);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				this.reporter.failureReport("Select Value from the Dropdown"+locatorName, "Option at index " + index
						+ " is Not Select from the DropDown" + locatorName);


			} else if (flag) {
				this.reporter.SuccessReport("Select Value from the Dropdown"+locatorName, "Option at index " + index
						+ "is Selected from the DropDown" + locatorName);

			}
		}
	}
	public boolean isElementPresent(By by, String locatorName,boolean expected) throws Throwable
	{
		boolean status = false;
		try
		{
			this.appiumDriver.findElement(by);
			this.reporter.SuccessReport("isElementPresent" , this.msgIsElementFoundSuccess + locatorName);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			if(expected == status)
			{
				this.reporter.SuccessReport("isElementPresent", "isElementPresent");
			}
			else
			{
				this.reporter.failureReport("isElementPresent", this.msgIsElementFoundFailure + locatorName, this.appiumDriver);
			}
		}
		return status;
	}
	
	public boolean type(By locator, String testdata, String locatorName) throws Throwable
	{
		boolean status = false;
		try
		{
			this.appiumDriver.findElement(locator).clear();
			this.appiumDriver.findElement(locator).sendKeys(testdata);
			this.reporter.SuccessReport("type" , this.msgTypeSuccess + locatorName);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			this.reporter.failureReport("type", this.msgTypeFailure + locatorName, this.appiumDriver);
		}
		
		return status;
	}
	
	public boolean waitForElementPresent(By by, String locator, int secs)
			throws Throwable {
		boolean status = false;
		
		try {
			
			WebDriverWait wait = new WebDriverWait(this.appiumDriver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			for (int i = 0; i < secs/2; i++)
			{
				List<WebElement> elements = this.appiumDriver.findElements(by);
				if (elements.size()>0)
				{
					status = true;
					return status;

				} 
				else
				{
					this.appiumDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				}
			}
	       
		
		} 
		catch (Exception e) {
			
			return status;
		} 
	
		return status;
		
	}
}
