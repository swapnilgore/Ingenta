package com.tdd.page.actions;

import com.automation.accelerators.ActionEngine;
import com.automation.report.CReporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.tdd.page.object.repository.InventoryPageOR;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E003690 on 3/15/2018.
 */
public class InventoryPageActions extends ActionEngine implements InventoryPageOR {

    public Actions action = new Actions(Driver);

    public InventoryPageActions(EventFiringWebDriver driver, CReporter cignitiReports, ExtentTest extentTestReports) {
        super(driver, cignitiReports, extentTestReports);
        this.reporter = cignitiReports;
        this.Driver = driver;
        this.logger = extentTestReports;
    }


    /**
     * Verify inventory page
     *
     * @throws Throwable
     */
    public void verifyInventoryPage() throws Throwable {
        Assert.assertTrue(waitForElementPresent(inventoryPage, 5), "Inventory page is not loaded");
    }


    /**
     * selects multi value in media box drop down
     *
     * @param values
     * @throws Throwable
     */
    public void selectMutipleMediaList(List<String> values) throws Throwable {

        action.keyDown(Keys.CONTROL);
        {
            for (int i = 0; i < values.size(); i++)
                selectdropdownvaluebyID(Mediabox, values.get(i));
        }
        action.keyUp(Keys.CONTROL);
    }

    /**
     * Cllicks on the new search
     *
     * @throws Throwable
     */
    public void clicknewsearch() throws Throwable {
        click(byLocator(newSearch), "new search");
    }

    /**
     * Creates inventory with the given parameters
     *
     * @param inventorydetails
     * @param adtype
     * @param mediagroup
     * @param medialist
     * @param sectionlist
     * @param placement
     * @param periodtime
     * @param viewtype
     * @param adsize
     * @throws Throwable
     */
    public void getInventory(JSONObject inventorydetails, String adtype, String mediagroup, List<String> medialist, String sectionlist, String placement, String periodtime, String viewtype, String adsize) throws Throwable {
        waitForElementPresent(newSearch);
        selectdropdownvaluebyID(aD_type, inventorydetails.get(adtype).toString());
        selectdropdownvaluebyID(mediaGroupField, inventorydetails.get(mediagroup).toString());
        Thread.sleep(4000);
        selectMutipleMediaList(medialist);
        Thread.sleep(4000);
        selectdropdownvaluebyID(selection, inventorydetails.get(sectionlist).toString());
        Thread.sleep(4000);
        selectdropdownvaluebyID(inventory_placement, inventorydetails.get(placement).toString());
        selectdropdownvaluebyID(period, inventorydetails.get(periodtime).toString());
        if (viewtype.equals("pages")) {
            click(byLocator(pages), "pages");
        } else if (viewtype.equals("matrix")) {
            click(byLocator(matrix), "Matrix");
        } else if (viewtype.equals("list")) {
            click(byLocator(list), "List");
        }
        Thread.sleep(4000);
        selectdropdownvaluebyID(Adsize, inventorydetails.get(adsize).toString());
        if (inventorydetails.get(periodtime).toString().equals("Six Months")) {
            type(byLocator(torange), inventorydetails.get("torange").toString(), "");
        }
        getInventory();
    }

    /**
     * Selects the dropdown value by the given optoin
     *
     * @param locator
     * @param option
     * @throws Throwable
     */
    public void selectdropdownvaluebyID(String locator, String option) throws Throwable {
        List<WebElement> elements = getAllElements(locator);
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                selectDropDown(element, option);
                break;
            }
        }
    }

    /**
     * Selects the plus icon for date of invetory search
     *
     * @param placementName
     * @throws Throwable
     */
    public void selectDateByInventoryPLacement(String placementName) throws Throwable {
        String placement = String.format(selectplusiconforbydate, placementName);
        waitForElementPresent(placement);
        click(byLocator(placement), placementName);
    }

    public void selectStartAndEnddateInMatrixViewinhousingmedia(int startDate, int endDate) throws Throwable {
        Assert.assertTrue(waitForElementPresent(housingGarden), "Matrix is not yet loaded completely");
        String start = String.format(startAndEnddate, startDate);
        String end = String.format(startAndEnddate, endDate);
        click(byLocator(start), start);
        action.keyDown(Keys.LEFT_SHIFT).click(getElement(end));
    }

    public void selectStartAndEnddateInMatrixViewinbridgegmedia(int startDate, int endDate) throws Throwable {
        String start = String.format(startAndEnddateofbridge, startDate);
        String end = String.format(startAndEnddateofbridge, endDate);
        click(byLocator(start), start);
        action.keyDown(Keys.LEFT_SHIFT).click(getElement(end));
    }

    public void addToCart(int enddate) throws Throwable {
        String end = String.format(startAndEnddate, enddate);
        action.contextClick(getElement(end)).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click(getElement(addtoCart)).build().perform();
        //      click(byLocator(addtoCart), "Add to cart");
        waitForElementPresent(verifyIteminCart);
    }

    public void verifyCardItem() throws Throwable {
        Assert.assertTrue(waitForElementPresent(verifyIteminCart, 20), "Items not found in Cart");
    }

    /**
     * Clicks on add to cart
     *
     * @throws Throwable
     */
    public void clickonCart() throws Throwable {
        waitForElementPresent(verifyIteminCart, 10);
        click(byLocator(verifyIteminCart), "Add to Cart");
    }

    public void getInventory() throws Throwable {
        List<WebElement> elements = getAllElements(get_inventory);
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                click(element, "Get Inventory Btn");
            }
        }
    }

    public void clickOnCustomer() throws Throwable {
        waitForElementPresent(customer);
        click(byLocator(customer), "customer");
    }

    public void selectCompanyNameAndContactName() throws Throwable {
        clickOnCustomer();
        waitForElementPresent(companyName);
        click(byLocator(companyName), "CompanyName");
        waitForElementPresent(companyNameField);
        type(byLocator(companyNameField), "Duis", "ComnayName");
        click(byLocator(searchButton), "SearchButton");
        waitForElementPresent(selectCompany);
        click(byLocator(selectCompany), "Select Company Name");
        waitForElementPresent(contactName);
        click(byLocator(contactName), "Contact Name");
        waitForElementPresent(selectContact);
        click(byLocator(contactName), "ContactName");
    }
}
