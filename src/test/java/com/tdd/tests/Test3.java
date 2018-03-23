package com.tdd.tests;

import com.automation.accelerators.TestEngineWeb;
import com.automation.support.TestProperties;
import com.tdd.page.actions.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by E003690 on 3/20/2018.
 */
public class Test3 extends TestEngineWeb {

    LoginPageActions loginPage;
    MenuPageActions menuPage;
    NavigationHeaderActions navigationHeader;
    InventoryPageActions inventory;
    ArrayList<String> mediatypes = new ArrayList<String>();
    JSONObject json;

    @BeforeMethod
    public void getPageObjects() {
        loginPage = new LoginPageActions(getDriver(), getReporter(), getLogger());
        menuPage = new MenuPageActions(getDriver(), getReporter(), getLogger());
        navigationHeader = new NavigationHeaderActions(getDriver(), getReporter(), getLogger());
        inventory = new InventoryPageActions(getDriver(), getReporter(), getLogger());
    }

    @TestProperties(testID = "TC_003")
    @Test(description = "Test to create inventory ang verify booking summary for it", enabled = true)
    public void TC_003_createInventory() throws Throwable {
        loginPage.verifyLoginPage();
        loginPage.login(userDataFile.getString("username"), userDataFile.getString("password"));
        navigationHeader.verifyNavigationHeader();
        menuPage.verifyMenuPage();
        menuPage.selectInventoryMenuOptions();

        // 1st search for inventory
        json = userDataFile.getInnerJson("inventorydetails");
        mediatypes.add(json.get("medialist1").toString());
        mediatypes.add(json.get("medialist2").toString());

        inventory.getInventory(json, "adtype", "mediaGroup",
                mediatypes, "Sectionlist", "placement", "period", "pages", "ADsize");
        inventory.clicknewsearch();
        mediatypes.clear();

        //2nd Search for inventory
        mediatypes.add(json.get("mediahome").toString());
        inventory.getInventory(json, "eventadtype", "eventmediagroup",
                mediatypes, "homesection", "homeplacement", "homeperiod", "list", "homeADsize");

//        inventory.selectDateByInventoryPLacement(json.get("inventorydate").toString());

        inventory.clicknewsearch();
        mediatypes.clear();

        //3rd Search for inventory
        mediatypes.add(json.get("househome").toString());
        mediatypes.add(json.get("bridgehome").toString());

        inventory.getInventory(json, "webadtype", "webmediagroup",
                mediatypes, "runsection", "runplacement", "webperiod", "matrix", "webADsize");

        inventory.selectStartAndEnddateInMatrixViewinhousingmedia(24, 26);
        inventory.addToCart(20);
        inventory.selectStartAndEnddateInMatrixViewinbridgegmedia(24, 26);
        inventory.addToCart(20);
        inventory.verifyCardItem();

        inventory.clickonCart();
        inventory.clickOnCustomer();

    }
}
