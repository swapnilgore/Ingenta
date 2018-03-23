
package com.automation.accelerators;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.automation.report.CReporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ActionEngine extends TestEngineWeb {
    private static final Logger LOG = Logger.getLogger(ActionEngine.class);

    private final String msgClickSuccess = "Successfully Clicked On ";
    private final String msgClickFailure = "Unable To Click On ";
    private final String msgTypeSuccess = "Successfully Typed On ";
    private final String msgTypeFailure = "Unable To Type On ";
    private final String msgIsElementFoundSuccess = "Successfully Found Element ";
    private final String msgIsElementFoundFailure = "Unable To Found Element ";
    protected static Integer pauseS = 15;
    public EventFiringWebDriver Driver = null;
    public CReporter reporter = null;
    public ExtentTest logger;

    public ActionEngine(EventFiringWebDriver eventFiringWebDriver, CReporter cReporter, ExtentTest extentTestReports) {
        this.Driver = eventFiringWebDriver;
        this.reporter = cReporter;
        this.logger = extentTestReports;
    }

    /**
     * @param locator
     * @param locatorName
     * @return
     * @throws Throwable
     */
    public boolean selectByIndex(By locator, int index,
                                 String locatorName) throws Throwable {
        boolean flag = false;
        try {
            Select s = new Select(Driver.findElement(locator));
            s.selectByIndex(index);
            flag = true;
            return true;
        } catch (Exception e) {

            return false;
        } finally {
            if (!flag) {
                this.reporter.failureReport("Select Value from the Dropdown" + locatorName, "Option at index " + index
                        + " is Not Select from the DropDown" + locatorName);


            } else if (flag) {
                this.reporter.SuccessReport("Select Value from the Dropdown" + locatorName, "Option at index " + index
                        + "is Selected from the DropDown" + locatorName);

            }
        }
    }

    public boolean click(By locator, String locatorName) throws Throwable {
        boolean status = false;
        try {
            this.Driver.findElement(locator).click();
            this.reporter.SuccessReport("Click on " + locatorName, this.msgClickSuccess + locatorName, this.Driver);
            logger.log(LogStatus.PASS, "Click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickPass")));
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            this.reporter.failureReport("Click on " + locatorName, this.msgClickFailure + locatorName, this.Driver);
            logger.log(LogStatus.FAIL, "Failed to click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickFail")));
            throw e;
        }
        return status;
    }

    public boolean waitAndClick(By locator, String locatorName) throws Throwable {
        boolean status = false;
        try {
            WebDriverWait wait = new WebDriverWait(Driver, pauseS);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            Driver.findElement(locator).click();
            this.reporter.SuccessReport("Click on " + locatorName, this.msgClickSuccess + locatorName, this.Driver);
            logger.log(LogStatus.PASS, "Click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickPass")));
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            this.reporter.failureReport("Click on " + locatorName, this.msgClickFailure + locatorName, this.Driver);
            logger.log(LogStatus.FAIL, "Failed to click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickFail")));
            throw e;
        }
        return status;
    }

    public boolean click(WebElement webElement, String locatorName) throws Throwable {
        boolean status = false;
        try {
            webElement.click();
            this.reporter.SuccessReport("Click on " + locatorName, this.msgClickSuccess + locatorName, this.Driver);
            logger.log(LogStatus.PASS, "Click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickPass")));
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            this.reporter.failureReport("Click on " + locatorName, this.msgClickFailure + locatorName, this.Driver);
            logger.log(LogStatus.FAIL, "Failed to click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickFail")));
            throw e;
        }
        return status;
    }

    /**
     * If more than one element present
     *
     * @param locator
     * @param index
     * @return
     * @throws Throwable
     */
    public boolean click(By locator, int index, String locatorName) throws Throwable {
        boolean status = false;
        try {
            this.Driver.findElements(locator).get(index).click();
            this.reporter.SuccessReport("Click on " + locatorName, this.msgClickSuccess + locatorName, this.Driver);
            logger.log(LogStatus.PASS, "Click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickPass")));
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            this.reporter.failureReport("Click on " + locatorName, this.msgClickFailure + locatorName, this.Driver);
            logger.log(LogStatus.FAIL, "Failed to click on " + locatorName, logger.addScreenCapture(capture(Driver, "ClickFail")));
        }
        return status;
    }

    public void clickusingJavaScript(String locator) throws Throwable {
        Thread.sleep(2000);
        WebElement element = this.Driver.findElement(byLocator(locator));
        JavascriptExecutor executor = (JavascriptExecutor) Driver;
        executor.executeScript("arguments[0].click();", element);
    }


    public boolean type(By locator, String testdata, String locatorName) throws Throwable {
        boolean status = false;
        try {
			/*this.Driver.findElement(locator).clear();
			this.Driver.findElement(locator).sendKeys(testdata);*/
            Driver.findElement(locator).clear();
            Driver.findElement(locator).sendKeys(testdata);
            this.reporter.SuccessReport("Enter text in " + locatorName, this.msgTypeSuccess + locatorName, this.Driver);
            logger.log(LogStatus.PASS, "Enter text in " + locatorName, logger.addScreenCapture(capture(Driver, "typePass")));
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            this.reporter.failureReport("Enter text in " + locatorName, this.msgTypeFailure + locatorName, this.Driver);
            logger.log(LogStatus.FAIL, "Enter text in " + locatorName, logger.addScreenCapture(capture(Driver, "typeFailed")));
        }

        return status;
    }

    /**
     * Moves the mouse to the middle of the element. The element is scrolled
     * into view and its location is calculated using getBoundingClientRect.
     *
     * @param locator     : Action to be performed on element (Get it from Object
     *                    repository)
     * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
     */
    public boolean mouseover(By locator, String locatorName)
            throws Throwable {
        boolean flag = false;
        try {
            WebElement mo = this.Driver.findElement(locator);
            new Actions(this.Driver).moveToElement(mo).build().perform();
            flag = true;
            return true;
        } catch (Exception e) {

            return false;
        } finally {
            if (flag == false) {
                this.reporter.failureReport("MouseOver on" + locatorName,
                        "MouseOver action is not perform on" + locatorName, this.Driver);

            } else if (flag == true) {

                this.reporter.SuccessReport("MouseOver on" + locatorName,
                        "MouserOver Action is Done on" + locatorName);
            }
        }
    }

    public boolean JSClick(By locator, String locatorName)
            throws Throwable {
        boolean flag = false;
        try {
            WebElement element = this.Driver.findElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) this.Driver;
            executor.executeScript("arguments[0].click();", element);
            // driver.executeAsyncScript("arguments[0].click();", element);

            flag = true;

        } catch (Exception e) {


        } finally {
            if (flag == false) {
                this.reporter.failureReport("MouseOver",
                        "MouseOver action is not perform on" + locatorName);
                return flag;
            } else if (flag == true) {
                this.reporter.SuccessReport("MouseOver",
                        "MouserOver Action is Done on" + locatorName);
                return flag;
            }
        }
        return flag;
    }

    public void sleep(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    /**
     * Binding to get Xpath, CSS, Link, Partial link element
     *
     * @param locator locator of element in xpath=locator; css=locator etc
     * @return found WebElement
     */
    protected WebElement getElement(final String locator) {
        return getElement(locator, true);
    }


    /**
     * Get "By" object to locate element
     *
     * @param locator locator of element in xpath=locator; css=locator etc
     * @return by object
     */
    protected By byLocator(final String locator) {
        String prefix = locator.substring(0, locator.indexOf('='));
        String suffix = locator.substring(locator.indexOf('=') + 1);

        switch (prefix) {
            case "xpath":
                return By.xpath(suffix);
            case "css":
                return By.cssSelector(suffix);
            case "link":
                return By.linkText(suffix);
            case "partLink":
                return By.partialLinkText(suffix);
            case "id":
                return By.id(suffix);
            case "name":
                return By.name(suffix);
            case "tag":
                return By.tagName(suffix);
            case "className":
                return By.className(suffix);
            default:
                return null;
        }
    }

    /**
     * @param locator          locator of element in xpath=locator; css=locator etc
     * @param screenShotOnFail make screenshot on failed attempt
     * @return found WebElement
     */
    protected WebElement getElement(final String locator, boolean screenShotOnFail) {
        try {
            return Driver.findElement(byLocator(locator));
        } catch (Exception e) {
            if (screenShotOnFail) ;
            throw e;
        }
    }

    public boolean waitForElementPresent(String locator, int secs) throws Throwable {
        boolean status = false;
        try {
            WebDriverWait wait = new WebDriverWait(Driver, secs);
            wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator(locator)));
            status = true;
//            logger.log(LogStatus.PASS, "Successfully checked element present " + locator, logger.addScreenCapture(capture(Driver, "waitForElementPresent_pass")));
//            this.reporter.SuccessReport("Checked element present", "Successfully checked element present " + locator, Driver);
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Failed to checked element present " + locator, logger.addScreenCapture(capture(Driver, "waitForElementPresent_failed")));
            this.reporter.failureReport("Checked element present", "Failed - " + locator + "not present", Driver);
            e.printStackTrace();
            return status;
        }

        return status;
    }

    public boolean waitForElementNotPresent(String locator, int secs) throws Throwable {
        boolean status = false;
        try {
            WebDriverWait wait = new WebDriverWait(Driver, secs);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator(locator)));
            status = true;
//            logger.log(LogStatus.PASS, "Successfully checked element absence " + locator, logger.addScreenCapture(capture(Driver, "waitForElementNotPresent_pass")));
//            this.reporter.SuccessReport("Checked element absence", "Successfully checked element absence " + locator, Driver);
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Failed to checked element absence " + locator, logger.addScreenCapture(capture(Driver, "waitForElementNotPresent_failed")));
            this.reporter.failureReport("Checked element absence", "Failed - " + locator + "present", Driver);
            e.printStackTrace();
            return status;
        }

        return status;
    }


    /**
     * Soft wait for visibility of element with default timeout
     *
     * @param locator locator of element to wait for
     * @return true if element is present and visible / false otherwise
     */
    protected boolean waitForElementPresent(final String locator) throws Throwable {
        return waitForElementPresent(locator, pauseS);
    }

    /**
     * Wait until element is invisible/not present on the page with default timeout
     *
     * @param locator locator to element
     */
    protected void waitForElementNotPresent(final String locator) throws Throwable {
        waitForElementNotPresent(locator, pauseS);
    }

    /**
     * @param locatorName: webelement having multiple elements
     * @return list of webelements
     * @throws Throwable
     */
    public List<WebElement> getAllElements(String locatorName) throws Throwable {

        List<WebElement> elements = new ArrayList<WebElement>();
        try {
            elements = Driver.findElements(byLocator(locatorName));
            return elements;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return elements;
    }

    /**
     * Binding to check Checkbox
     *
     * @param we webElement of checkbox to check
     */
    protected void check(final WebElement we) {
        if (!we.isSelected()) {
            we.click();
        }
    }

    /**
     * Binding to check checkbox
     *
     * @param locator locator of checkbox to check
     */
    protected void check(final String locator) {
        check(getElement(locator));
    }

    /**
     * Binding to clear text field and enter text
     *
     * @param we       webElement to type to
     * @param value    value to type into the field
     * @param clear    true to clear the field first; false to enter text without clearing field
     * @param keyClear true to clean using keyboard shortcut; false without clean;
     * @return webElement with edited text
     */
    protected WebElement typeKeys(final WebElement we, final String value, final boolean clear, final boolean keyClear) {
        if (clear) we.clear();
        if (keyClear) {
            we.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            we.sendKeys(Keys.chord(Keys.DELETE));
        }
        we.sendKeys(value);
        return we;
    }

    protected void selectDropDown(By locator, String option, String locatorName) throws Throwable {

        try {
            WebDriverWait wait = new WebDriverWait(Driver, pauseS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            new Select(Driver.findElement(locator)).selectByVisibleText(option);
            Thread.sleep(500);
            this.reporter.SuccessReport("Drop Down Selection " + locatorName, "Successfully Selected " + option + " in drop down " + locatorName, this.Driver);
            logger.log(LogStatus.PASS, "Successfully Selected " + option + " in drop down " + locatorName, logger.addScreenCapture(capture(Driver, "DropdownPass")));
        } catch (Exception e) {
            this.reporter.failureReport("Drop Down Selection " + locatorName, "Failed to select value " + option + " in drop down " + locatorName, this.Driver);
            logger.log(LogStatus.FAIL, "Failed to select value " + option + " in drop down " + locatorName, logger.addScreenCapture(capture(Driver, "DropdownFail")));
            throw e;
        }
    }

    protected void selectDropDown(final WebElement we, final String option) {

        try {
            new Select(we).selectByVisibleText(option);
        } catch (Exception e) {
            throw e;
        }
    }


    public void scrollElementIntoView(String locator) {
        ((JavascriptExecutor) Driver).executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    public void scrollElementIntoView(WebElement elementToScroll) {
        ((JavascriptExecutor) Driver).executeScript("arguments[0].scrollIntoView(true);", elementToScroll);
    }

    public void typeUsingJavaScript(String locator, String data) {

        WebElement element = this.Driver.findElement(byLocator(locator));
        JavascriptExecutor executor = (JavascriptExecutor) Driver;
        executor.executeScript("arguments[0].value='" + data + "';", element);
    }

    public void assertTrue(boolean condition, String message, String strStepName, String failedMessage, String passMessage) throws Throwable {
        if (!condition) {
            this.reporter.failureReport(strStepName, failedMessage, Driver);
            logger.log(LogStatus.FAIL, failedMessage, logger.addScreenCapture(capture(Driver, "assertTrue_Fail")));
            Assert.assertTrue(condition, message);
        } else {
            this.reporter.SuccessReport(strStepName, passMessage, Driver);
            logger.log(LogStatus.PASS, passMessage, logger.addScreenCapture(capture(Driver, "assertTrue_pass")));
        }
    }

    public void assertFalse(boolean condition, String message, String strStepName, String failedMessage, String passMessage) throws Throwable {
        if (condition) {
            this.reporter.failureReport(strStepName, failedMessage, Driver);
            logger.log(LogStatus.FAIL, failedMessage, logger.addScreenCapture(capture(Driver, "assertFalse_Fail")));
            Assert.assertFalse(condition, message);
        } else {
            this.reporter.SuccessReport(strStepName, passMessage, Driver);
            logger.log(LogStatus.PASS, passMessage, logger.addScreenCapture(capture(Driver, "assertFalse_pass")));
        }
    }

    public void assertEqual(String actual, String expected, String message, String strStepName, String failedMessage, String passMessage) throws Throwable {
        if (!actual.equals(expected)) {
            this.reporter.failureReport(strStepName, failedMessage, Driver);
            logger.log(LogStatus.FAIL, failedMessage, logger.addScreenCapture(capture(Driver, "assertEqual_Fail")));
            Assert.assertEquals(actual, expected, message);
        } else {
            this.reporter.SuccessReport(strStepName, passMessage, Driver);
            logger.log(LogStatus.PASS, passMessage, logger.addScreenCapture(capture(Driver, "assertEqual_pass")));
        }
    }

    public void assertEqual(int actual, int expected, String message, String strStepName, String failedMessage, String passMessage) throws Throwable {
        if (actual == expected) {
            this.reporter.failureReport(strStepName, failedMessage, Driver);
            logger.log(LogStatus.FAIL, failedMessage, logger.addScreenCapture(capture(Driver, "assertEqual_Fail")));
            Assert.assertEquals(actual, expected, message);
        } else {
            this.reporter.SuccessReport(strStepName, passMessage, Driver);
            logger.log(LogStatus.PASS, passMessage, logger.addScreenCapture(capture(Driver, "assertEqual_pass")));
        }
    }

    public void highlight(String locator) {
        WebElement elem = getElement(locator);
        ((JavascriptExecutor) Driver).executeScript("arguments[0].style.border='6px solid yellow'", elem);
        ((JavascriptExecutor) Driver).executeScript("arguments[0].style.border='6px solid red'", elem);
        ((JavascriptExecutor) Driver).executeScript("arguments[0].style.border='6px solid blue'", elem);
        ((JavascriptExecutor) Driver).executeScript("arguments[0].style.border='0px solid green'", elem);
    }

    public void waitForGivenNumberOfWindowsToOpen(int expectedWindowsCount, int timeUnitsInSec) throws InterruptedException {
        int actualWindowsCount = Driver.getWindowHandles().size();
        for (int i = 0; i < timeUnitsInSec; i++) {
            Thread.sleep(1000);
            if (expectedWindowsCount == actualWindowsCount)
                break;
        }
    }
}
