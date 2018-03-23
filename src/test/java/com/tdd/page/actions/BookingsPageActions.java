package com.tdd.page.actions;

import com.automation.accelerators.ActionEngine;
import com.automation.report.CReporter;
import com.google.gson.JsonObject;
import com.relevantcodes.extentreports.ExtentTest;
import com.tdd.page.object.repository.BookingsPageOR;
import com.tdd.page.object.repository.MenuPageOR;
import com.tdd.page.object.repository.NavigationHeaderOR;
import org.json.simple.JSONObject;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by E003690 on 1/22/2018.
 */
public class BookingsPageActions extends ActionEngine implements BookingsPageOR, NavigationHeaderOR {
    ExtentTest logger;

    public BookingsPageActions(EventFiringWebDriver driver, CReporter cignitiReports, ExtentTest extentTestReports) {
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
    public void verifyBookingsPage() throws Throwable {
        Assert.assertTrue(waitForElementPresent(bookingsTable, 5), "Bookings Table in Bookings Page not present");
    }

    public void verifyEditBookingsPage() throws Throwable {
        Assert.assertTrue(waitForElementPresent(saveCloseBtn, 5), "saveCloseBtn in Edit Bookings Page not present");
    }

    public void updateBookingDetailsByID(JSONObject editBookingDetails) throws Throwable {
        String bookingID = editBookingDetails.get("bookingID").toString();
        String locator = String.format(bookingDetailsEachIDCell, bookingID);
        click(byLocator(locator), "Edit Booking By ID :" + bookingID);
        verifyEditBookingsPage();
        updateBookingDetails(editBookingDetails, "");
    }

    public void updateBookingDetails(JSONObject editBookingDetails, String winHandleBefore) throws Throwable {
        String newBookingReferenceToBeEnter = editBookingDetails.get("newBookingReference").toString() + new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        type(byLocator(bookingRefFields), newBookingReferenceToBeEnter, "bookingRefFields");
        try {
            click(byLocator(saveCloseBtn), "saveCloseBtn");
        } catch (NoSuchWindowException ncw) {
            Driver.switchTo().window(winHandleBefore);
        }
    }

    public void selectBookingDetailsTabByText(String givenText) throws Throwable {
        String locator = String.format(selectTabByText, givenText);
        waitAndClick(byLocator(locator), "New Bookings selection tab : " + givenText);
        String finalLocator = String.format(currentSelectedTab, givenText);
        Assert.assertTrue(waitForElementPresent(finalLocator, 10), "Current Tab Selection Element");
    }

    public void createNewBookingDetails(JSONObject bookingDetails, String winHandleBefore) throws Throwable {
        String mediaPlusIconByMediaName = String.format(addMediaPlusIconByMediaName, bookingDetails.get("media").toString());
        waitAndClick(byLocator(mediaPlusIconByMediaName), "mediaPlusIconByMediaName");
        selectDropDown(byLocator(bookingSelectionDropdown), bookingDetails.get("section").toString(), "Booking Selection type");
        Thread.sleep(1000);
        selectDropDown(byLocator(bookingAdSizeDropdown), bookingDetails.get("adSize").toString(), "Booking Ad Size type");
        selectBookingDetailsTabByText(insertionsTab);
        waitAndClick(byLocator(issueSelectionCheckBox), "Select Insertions Issues");
        selectBookingDetailsTabByText(materialTab);
        selectBookingDetailsTabByText(financeTab);
        selectBookingDetailsTabByText(paymentTab);
        waitAndClick(byLocator(nextBtn), "Next Btn in create bookings Page");
        updateBookingDetails(bookingDetails, winHandleBefore);
    }


}



