package com.tdd.page.actions;

import com.automation.accelerators.ActionEngine;
import com.automation.report.CReporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tdd.page.object.repository.LoginPageOR;
import com.tdd.page.object.repository.MenuPageOR;
import com.tdd.page.object.repository.NavigationHeaderOR;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

/**
 * Created by E003690 on 1/22/2018.
 */
public class MenuPageActions extends ActionEngine implements MenuPageOR, NavigationHeaderOR {
    ExtentTest logger;

    public MenuPageActions(EventFiringWebDriver driver, CReporter cignitiReports, ExtentTest extentTestReports) {
        super(driver, cignitiReports, extentTestReports);
        this.reporter = cignitiReports;
        this.Driver = driver;
        this.logger = extentTestReports;
    }


    /**
     * Method to verify Menu page
     *
     * @throws Throwable
     */
    public void verifyMenuPage() throws Throwable {
        Driver.switchTo().defaultContent();
        Driver.switchTo().frame(0);
        Assert.assertTrue(waitForElementPresent(loggedInUserImg, 5), "Logged in User Img not present in Menu page");
    }


    public void selectMenuBoxLocatorByID(String menuBlockid) throws Throwable {
        String locator = String.format(selectMenuBoxByID, menuBlockid);
        waitAndClick(byLocator(locator), "Menu Item :" + menuBlockid);
        Assert.assertTrue(waitForElementNotPresent(locator, 5));
    }

    public void selectMyBookingsMenuOptions() throws Throwable {
        selectMenuBoxLocatorByID(myBookingsID);
    }

    public void selectCompaniesMenuOptions() throws Throwable {
        selectMenuBoxLocatorByID(companiesID);
    }

    public void selectInventoryMenuOptions() throws Throwable {
        selectMenuBoxLocatorByID(inventoryID);
    }
}



