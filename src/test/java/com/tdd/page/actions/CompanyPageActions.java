package com.tdd.page.actions;

import com.automation.accelerators.ActionEngine;
import com.automation.report.CReporter;
import com.google.gson.JsonObject;
import com.relevantcodes.extentreports.ExtentTest;
import com.tdd.page.object.repository.CompanyPageOR;
import com.tdd.page.object.repository.NavigationHeaderOR;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

/**
 * Created by E003690 on 1/22/2018.
 */
public class CompanyPageActions extends ActionEngine implements CompanyPageOR, NavigationHeaderOR {
    ExtentTest logger;

    public CompanyPageActions(EventFiringWebDriver driver, CReporter cignitiReports, ExtentTest extentTestReports) {
        super(driver, cignitiReports, extentTestReports);
        this.reporter = cignitiReports;
        this.Driver = driver;
        this.logger = extentTestReports;
    }


    /**
     * Method to verify Bookings page
     *
     * @throws Throwable
     */
    public void verifyCompanySearchPage() throws Throwable {
        Driver.switchTo().defaultContent();
        Driver.switchTo().frame(0);
        Thread.sleep(1000);
        Driver.switchTo().frame(0);
        Assert.assertTrue(waitForElementPresent(companySearchTextField, 10), "companySearchTextField not present in Company Search Page");
    }

    public void searchForCompany(String companyNameKeyword, boolean isResultFound) throws Throwable {
        type(byLocator(companySearchTextField), companyNameKeyword, "companySearchTextField");
        click(byLocator(searchGoButton), "searchGoButton");
        if (isResultFound) {
            Assert.assertTrue(waitForElementPresent(searchResultsTable, 5), "searchResultsTable not present on Company Search Page");
            assertTrue(getFirstResultCompanyName().getText().contains(companyNameKeyword),
                    "Search keyword " + companyNameKeyword + "not matching with result company name" + getFirstResultCompanyName(),
                    "Verifying Search Result",
                    "Search keyword " + companyNameKeyword + "not matching with result company name" + getFirstResultCompanyName(),
                    "Successfully verified search results");
        } else
            Assert.assertTrue(waitForElementNotPresent(searchResultsTable, 5), "searchResultsTable present on Company Search Page we we expect no results");
    }

    public String getCompanySearchResultTableDataAtGivenRowAndColumn(int rowIndex, int columnIndex) {
        return String.format(tableDataAtGivenRowAndColumn, rowIndex, columnIndex);
    }

    public By getFirstResultCompanyEditBtn() {
        return byLocator(getCompanySearchResultTableDataAtGivenRowAndColumn(0, 0));
    }

    public WebElement getFirstResultCompanyName() throws Throwable {
        return Driver.findElements(By.cssSelector("td>nobr")).get(3);
    }

    public String getLeftNavigationOptionAtGivenRowColumn(int rowIndex, int columnIndex) {
        return String.format(leftMenuOptionAtGivenRowAndColumn, rowIndex, columnIndex);
    }

    public String getContactsMenu() {
        Driver.switchTo().defaultContent();
        Driver.switchTo().frame(0);
        Driver.switchTo().frame(1);
        return getLeftNavigationOptionAtGivenRowColumn(0, 3);
    }

    public void openContactsHistoryByFirstName(String contactDetails) throws Throwable {
        String locator = String.format(contactsIconByFirstName, contactDetails);
        Driver.switchTo().defaultContent();
        Driver.switchTo().frame(0);
        Driver.switchTo().frame(1);
        Driver.switchTo().frame(0);
        waitAndClick(byLocator(locator), "Contacts Img Icon");
    }

    public String goToNewBookingsFromCompanySearchPage(String companyName, String contactDetails) throws Throwable {
        click(byLocator(String.format(selectCompanyByName, companyName)), "Edit Company Btn of : " + companyName);
        Thread.sleep(5000);
        Driver.switchTo().defaultContent();
        Driver.switchTo().frame(0);
        Driver.switchTo().frame(1);
        Assert.assertTrue(waitForElementPresent(companyNameInInfoPage, 10), "companyNameInInfoPage not present in Company info Page");
        waitAndClick(byLocator(getContactsMenu()), "Contacts Link in Company Information Page");
        Assert.assertTrue(waitForElementNotPresent(companyNameInInfoPage, 10), "companyNameInInfoPage present in Company info Page after contacts page");
        openContactsHistoryByFirstName(contactDetails);
        Driver.switchTo().defaultContent();
        Driver.switchTo().frame(0);
        Driver.switchTo().frame(1);
        Thread.sleep(1000);
        Driver.switchTo().frame(0);
        waitAndClick(byLocator(newBookingBtn), "New Booking");
        waitForGivenNumberOfWindowsToOpen(2, 5);
        String winHandleBefore = Driver.getWindowHandle();
        for (String winHandle : Driver.getWindowHandles()) {
            Driver.switchTo().window(winHandle);
        }
        return winHandleBefore;
    }
}




