package mycompany.steps;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mycompany.pageobjects.HomePage;
import mycompany.pageobjects.PropertyPage;
import mycompany.pageobjects.SearchPage;
import mycompany.pageobjects.SearchResultsPage;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;

public class Change_sorttype_of_searchresults_Steps {

    @Managed
    WebDriver driver;

    Properties properties;

    HomePage homePage;
    SearchPage searchPage;
    SearchResultsPage searchResultsPage;

    String firstNonFeaturedPropAddr;

    @Before
    public void setupTestData() throws IOException  {
        String testDataFile = "src/test/resources/testdata/change_sorttype.properties";
        properties = new Properties();
        properties.load( Files.newInputStream(Paths.get(testDataFile)));
    }

    @Given("^user has successfully searched properties for sale with different list dates$")
    public void user_has_search_results_of_properties_for_sale_with_different_list_dates() {
        homePage = new HomePage(driver);
        homePage.open();
        homePage.fillSearchLocation(properties.getProperty("sale.location"));
        homePage.clickForSale();
        searchPage = new SearchPage(driver);
        searchPage.fillMinPrice(properties.getProperty("sale.minPrice"));
        searchPage.fillMaxPrice(properties.getProperty("sale.maxPrice"));
        searchPage.clickSubmit();
    }

    @Given("^user has successfully searched properties for rent with different list dates$")
    public void user_has_successfully_searched_properties_for_rent_with_different_list_dates() {
        homePage = new HomePage(driver);
        homePage.open();
        homePage.fillSearchLocation(properties.getProperty("rent.location"));
        homePage.clickForRent();
        searchPage = new SearchPage(driver);
        searchPage.fillMinPrice(properties.getProperty("rent.minPrice"));
        searchPage.fillMaxPrice(properties.getProperty("rent.maxPrice"));
        searchPage.clickSubmit();
    }



    @When("^user changes the sort type of search results to \"([^\"]*)\"$")
    public void user_changes_the_sort_type_of_search_results_to(String arg1) {
        searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.isOnPage();
        searchResultsPage.changeSortType();
    }

    @Then("^search results sort order should change accordingly$")
    public void search_results_sort_order_should_change_accordingly() {
        assertTrue(searchResultsPage.isSortedByListedDate());
        firstNonFeaturedPropAddr = searchResultsPage.getAddrOfFirstNonFeaturedProperty();
        //System.out.println("firstNonFeaturedPropAddr = " + firstNonFeaturedPropAddr);
    }

    @Then("^check that the first non-featured search result is linked OK$")
    public void check_that_the_first_non_featured_search_result_is_linked_OK() {
        searchResultsPage.clickFirstNonFeaturedProperty();
        PropertyPage propertyPage = new PropertyPage(driver);
        assertTrue(propertyPage.getTitle().contains(firstNonFeaturedPropAddr));
    }
}
