package com.tdd.tests;

import com.automation.accelerators.TestEngineWeb;
import com.automation.support.TestProperties;
import com.tdd.page.actions.*;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E002465 on 06-06-2017.
 */
public class Test2 extends TestEngineWeb {
    LoginPageActions loginPage;
    MenuPageActions menuPage;
    NavigationHeaderActions navigationHeader;
    BookingsPageActions bookingsPage;
    CompanyPageActions companyPage;
    InventoryPageActions inventory;

    @BeforeMethod
    public void getPageObjects() {
        loginPage = new LoginPageActions(getDriver(), getReporter(), getLogger());
        menuPage = new MenuPageActions(getDriver(), getReporter(), getLogger());
        navigationHeader = new NavigationHeaderActions(getDriver(), getReporter(), getLogger());
        bookingsPage = new BookingsPageActions(getDriver(), getReporter(), getLogger());
        companyPage = new CompanyPageActions(getDriver(), getReporter(), getLogger());
        inventory = new InventoryPageActions(getDriver(), getReporter(), getLogger());
    }

    @TestProperties(testID = "TC_002")
    @Test(description = "Login and Create New Bookings from Company contacts Search", enabled = true)
    public void TC_002_createNewBookings() throws Throwable {
        loginPage.verifyLoginPage();
        loginPage.login(userDataFile.getString("username"), userDataFile.getString("password"));
        navigationHeader.verifyNavigationHeader();
        menuPage.verifyMenuPage();
        menuPage.selectCompaniesMenuOptions();
        companyPage.verifyCompanySearchPage();
        companyPage.searchForCompany(userDataFile.getString("companySearchKeyword"), true);
        String parentWindow = companyPage.goToNewBookingsFromCompanySearchPage(userDataFile.getString("companySearchKeyword"), userDataFile.getString("contactFirstName"));
        bookingsPage.createNewBookingDetails(userDataFile.getInnerJson("newBookingDetails"), parentWindow);
        navigationHeader.logout();
        loginPage.verifyLoginPage();
    }
}