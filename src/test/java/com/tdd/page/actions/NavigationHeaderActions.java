package com.tdd.page.actions;

import com.automation.accelerators.ActionEngine;
import com.automation.report.CReporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.tdd.page.object.repository.MenuPageOR;
import com.tdd.page.object.repository.NavigationHeaderOR;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

/**
 * Created by E003690 on 1/22/2018.
 */
public class NavigationHeaderActions extends ActionEngine implements NavigationHeaderOR {
    ExtentTest logger;

    public NavigationHeaderActions(EventFiringWebDriver driver, CReporter cignitiReports, ExtentTest extentTestReports) {
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
    public void verifyNavigationHeader() throws Throwable {
        Assert.assertTrue(waitForElementPresent(userSettingsIcon, 3), "User Settings Icon Not Present");
    }

    public void switchOutFromFrame() {
        Driver.switchTo().defaultContent();
    }

    public void logout() throws Throwable {
        switchOutFromFrame();
        click(byLocator(userSettingsIcon), "userSettingsIcon");
        click(byLocator(logoutLink), "logoutLink");
        Assert.assertTrue(waitForElementNotPresent(userSettingsIcon, 3), "User Settings icon should not be present after logout");
    }
}



