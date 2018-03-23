package com.tdd.page.object.repository;

public interface CompanyPageOR {
    String companySearchTextField = "css= input#Search_FieldSelect0_txtValue1";
    String searchGoButton = "css= input#Search_GoButton";
    String searchResultsTable = "css= table#G_SearchxresultsGrid";
    String tableDataAtGivenRowAndColumn = "css= td#SearchxresultsGrid_rc_%d_%d nobr";
    String leftMenuOptionAtGivenRowAndColumn = "css= div#iglbarMenu_%d_Item_%d";
    String contactsRowImgIcon = "css= td.gridRowImg.gridRowContact";
    String contactsIconByFirstName = "xpath= //tr[contains(@id,'FFCompanyContact_grdCompanyContacts')]//td[6][contains(text(),'%s')]//ancestor::tr[contains(@id,'FFCompanyContact_grdCompanyContacts')]//td[contains(@class,'gridRowContact')]";
    String newBookingBtn = "css= input#FFHistory_ibtnAddBooking";
    String selectCompanyByName = "xpath= //tr[contains(@id,'SearchxresultsGrid_r')]//td[4]//nobr[contains(text(),'%s')]//ancestor::tr[contains(@id,'SearchxresultsGrid_r')]//td//input";
    String companyNameInInfoPage = "css= input#ucCompanyDetail_txtCompanyName";

}