package com.tdd.page.object.repository;

public interface BookingsPageOR {
    String bookingsTable = "css= table#ctl00_cphMain_grdRecentBookings";
    String bookingDetailsEachIDCell = "xpath= //table[@id='ctl00_cphMain_grdRecentBookings']//tr//td//a[contains(text(),'%s')][contains(@id,'ctl00_cphMain_grdRecentBookings')]";
    String saveCloseBtn = "css= input#ctl00_cphMain_ucRibbon_btnSaveAndClose";
    String bookingRefFields = "css= input#ctl00_cphMain_txtBookingRef";

    //New Bookings Page
    String addMediaPlusIconByMediaName = "xpath= //tr//td[2][contains(text(),'%s')]//preceding::td[1]//input[contains(@title,'Select')]";
    String bookingSelectionDropdown = "css= select#ctl00_cphMain_ddlReqSection_DropDownList1";
    String bookingAdSizeDropdown = "css= select#ctl00_cphMain_ddlAdSize_DropDownList1";
    String selectTabByText = "css= input[type='submit'][value='%s']";
    String currentSelectedTab = "xpath= //input[@type='submit'][@class='tabButtonSelected'][@value='%s']";
    String adDetailsTab = "Ad details";
    String insertionsTab = "Insertions";
    String materialTab = "Material";
    String financeTab = "Finance";
    String paymentTab = "Payment";
    String issueSelectionCheckBox = "css= input#ctl00_cphMain_ffIssueSelection_ffIssuePicklist_grdIssues_ctl00_ctl04_chkIsSelected";
    String nextBtn = "css= input#ctl00_cphMain_ucRibbon_btnNext";
}
