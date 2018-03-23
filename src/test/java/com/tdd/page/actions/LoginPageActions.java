package com.tdd.page.actions;

import com.automation.accelerators.ActionEngine;
import com.automation.report.CReporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tdd.page.object.repository.LoginPageOR;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

/**
 * Created by E003690 on 1/22/2018.
 */
public class LoginPageActions extends ActionEngine implements LoginPageOR {
    ExtentTest logger;

    public LoginPageActions(EventFiringWebDriver driver, CReporter cignitiReports, ExtentTest extentTestReports) {
        super(driver, cignitiReports, extentTestReports);
        this.reporter = cignitiReports;
        this.Driver = driver;
        this.logger = extentTestReports;
    }

    /**
     * Method to login with the given credentials
     *
     * @param givenUsername
     * @param givenPassword
     * @throws Throwable
     */
    public void login(String givenUsername, String givenPassword) throws Throwable {
        waitForElementPresent(signIn, 5);
        type(byLocator(username), givenUsername, "Username");
        type(byLocator(password), givenPassword, "Password");
        click(byLocator(signIn), "Sign In");
    }

    /**
     * Method to verify Login page
     *
     * @throws Throwable
     */
    public void verifyLoginPage() throws Throwable {
        Assert.assertTrue(waitForElementPresent(imgLogo, 3), "Img logo text not present in login page");
    }
}



