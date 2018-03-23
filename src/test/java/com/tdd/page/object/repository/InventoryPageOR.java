package com.tdd.page.object.repository;

/**
 * Created by E003690 on 3/15/2018.
 */
public interface InventoryPageOR {

    String inventoryPage = "xpath= //div[@class='NavMenuTitle']//span[text()='Inventory']";
    String aD_type = "xpath= //select[contains(@id,'AdType_DropDownList1')]";
    String mediaGroupField = "xpath= //select[contains(@id,'MediumGroup_DropDownList1')]";
    String Mediabox = "xpath= //select[contains(@id,'Media_ListBox1')]";
    String selection = "xpath= //select[contains(@id, 'Section_ListBox1')]";
    String inventory_placement = "xpath= //select[contains(@id, 'Inventory_ListBox1')]";
    String period = "xpath= //select[contains(@id,'BkingPeriod_ddlPeriod')]";
    String pages = "xpath= //input[contains(@id,'blInventoryView_1')]";
    String matrix = "xpath= //input[@id='ctl00_cphMain_srch03_rblInventoryView_0']";
    String list = "css= #ctl00_cphMain_srch02_rblInventoryView_2";
    String Adsize = "xpath= //select[contains(@id,'AdSize_DropDownList1')]";
    String get_inventory = "xpath= //input[contains(@id,'btnGetInventory')]";
    String mediatype = "xpath= //select[@id='ctl00_cphMain_srch01_lbMedia_ListBox1']//option[@value='GAZE']";
    String newSearch = "css= input[value='New Search']";
    String screenlock = "css= .inv_wait";
    String selectplusiconforbydate = "xpath= //td[@class='%s']/../..//input[@class='InvResultCol Select linkButton new']";
    String housingGarden = "css= tr [title = 'www.houseandgarden.com']";
    String startAndEnddate = "xpath= //tr[@title = 'www.houseandgarden.com']/..//tr[@title='www.houseandgarden.com - Run of Site']/td[%d]";
    String startAndEnddateofbridge = "xpath= //tr[@title = 'www.bridesmagazine.com']/..//tr[@title='www.bridesmagazine.com - Run of Site']/td[%d]";
    String verifyIteminCart = "css= #tabs-li-cart a#acart";
    String addtoCart = "xpath= //li//a[contains(text(), 'Add to cart')]";
    String torange = "css= #ctl00_cphMain_srch02_ucBkingPeriod_dteRangeTo_iwdcDate_input";
    String customer = "css= #ctl00_cphMain_ucCart_btnCustomer";
    String companyName = "css= #ctl00_cphMain_ucCustomer_ctlModalBuyerCompany_btnModal";
    String contactName = "css= #ctl00_cphMain_ucCustomer_ctlModalBuyerContact_btnModal";
    String companyNameField = "css= #Search_FieldSelect0_txtValue1";
    String searchButton = "css= #Search_GoButton";
    String selectCompany = "css= .ig_5aac757b_rcb1112.SelectionSearch.btnOpenSite";
    String selectContact = "css= .ig_5aac757b_rcb1112.SelectionSearch.btnOpenContact";


}
