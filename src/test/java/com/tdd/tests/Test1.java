package com.tdd.tests;

import com.automation.accelerators.TestEngineWeb;
import com.automation.support.TestProperties;
import com.tdd.page.actions.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by E002465 on 06-06-2017.
 */
public class Test1 extends TestEngineWeb {
    LoginPageActions loginPage;
    MenuPageActions menuPage;
    NavigationHeaderActions navigationHeader;
    BookingsPageActions bookingsPage;
    CompanyPageActions companyPage;

    @BeforeMethod
    public void getPageObjects() {
        loginPage = new LoginPageActions(getDriver(), getReporter(), getLogger());
        menuPage = new MenuPageActions(getDriver(), getReporter(), getLogger());
        navigationHeader = new NavigationHeaderActions(getDriver(), getReporter(), getLogger());
        bookingsPage = new BookingsPageActions(getDriver(), getReporter(), getLogger());
        companyPage = new CompanyPageActions(getDriver(), getReporter(), getLogger());
    }


    @TestProperties(testID = "TC_001")
    @Test(description = "Test to login and update booking ref details of given booking id", enabled = true)
    public void TC_001_loginInAndUpdateBookings() throws Throwable {
        loginPage.verifyLoginPage();
        loginPage.login(userDataFile.getString("username"), userDataFile.getString("password"));
        navigationHeader.verifyNavigationHeader();
        menuPage.verifyMenuPage();
        menuPage.selectMyBookingsMenuOptions();
        bookingsPage.verifyBookingsPage();
        bookingsPage.updateBookingDetailsByID(userDataFile.getInnerJson("editBookingDetails"));
        bookingsPage.verifyBookingsPage();
        navigationHeader.logout();
        loginPage.verifyLoginPage();
    }
}