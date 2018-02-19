package mycompany.pageobjects;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

public class SearchResultsPage extends PageObject {

    @FindBy(css = "#sortType")
    public WebElement sortType_select;

    @FindBy(css = ".l-searchResult")
    public List<WebElement> properties;

    @FindBy(css = "span.propertyCard-branchSummary-addedOrReduced")
    public List<WebElement> addedOrReduced_dates;

    @FindBy(css = "button.pagination-direction--next")
    public WebElement next_bt;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }
    public boolean isOnPage() {
        return true;
    }

    public void changeSortType() {
        Select select = new Select(sortType_select);
        select.selectByVisibleText("Newest Listed");
    }

    public boolean isSortedByListedDate() {
        LocalDate date = LocalDate.now();
        int distinctDates = 0;
        int count = 0;
        for (WebElement we : properties) {
            //ignore edge cases and dont bother going to next page
            if (count > 24) //if reached end of page
                throw new IllegalArgumentException("Bad test data setup;Listed-dates must be distinct");
            if (distinctDates > 1) return true;
            LocalDate currPropDate = null;
            if(!isPropertyFeatured(we)) {
                currPropDate = getAddedReducedDate(we);
                //System.out.println("currPropDate = " + currPropDate);
                if (currPropDate.isAfter(date)) return false;
                else if (currPropDate.isBefore(date)){
                    distinctDates++;
                    date = currPropDate;
                }
            }
        }
        return true;
    }

    public void clickFirstNonFeaturedProperty() {
        for (WebElement we : properties) {
            if (!isPropertyFeatured(we)) {
                getDriver().get(getPropertyLink(we));
                break;
            }
        }
    }
    public String getAddrOfFirstNonFeaturedProperty() {
        String addr = "";
        for (WebElement we : properties) {
            if (!isPropertyFeatured(we)) {
                return getPropertyAddress(we);
            }
        }
        return  addr;
    }

    private String getPropertyLink(WebElement property) {
        return property.findElement(By.cssSelector(".propertyCard-link")).
                getAttribute("href");
    }

    private boolean isPropertyFeatured(WebElement property) {
        //System.out.println("property data-test attribute = " + property.getAttribute("data-test"));
        WebElement we = null;
        try {
            we = property.findElement(By.cssSelector(".propertyCard--featured"));
        }catch(Exception e) {
            //e.printStackTrace();
        }
        return we != null;
    }
    private LocalDate getAddedReducedDate(WebElement property) {
        LocalDate date = null;
        String addedOrReduced_text = property.findElement(
                By.cssSelector("span.propertyCard-branchSummary-addedOrReduced")).getText();
        //System.out.println("addedOrReduced_text = " + addedOrReduced_text);
        if (addedOrReduced_text.isEmpty())
            throw new IllegalStateException(
                    "unable to find added/reduced date for the property :" + property.toString());
        if (addedOrReduced_text.contains("Today")) date = LocalDate.now();
        else if (addedOrReduced_text.contains("yesterday")) date = LocalDate.now().minus(Period.ofDays(1));
        else {
            StringTokenizer st = new StringTokenizer(addedOrReduced_text);
            while (st.hasMoreTokens()) {

                if (st.nextToken().equalsIgnoreCase("on")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    date =  LocalDate.parse(st.nextToken(), formatter);
                }
            }
        }
        return  date;
    }
    private String getPropertyAddress(WebElement property) {
        WebElement addrSpan = property.findElement(By.cssSelector("span[data-bind='text: displayAddress']"));
        return addrSpan.getText();
    }

}
