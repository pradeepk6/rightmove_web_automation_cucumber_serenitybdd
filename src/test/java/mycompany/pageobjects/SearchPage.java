package mycompany.pageobjects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SearchPage extends PageObject {

    @FindBy(css = "#radius")
    public WebElement seaRadius_select;

    @FindBy(css = "#displayPropertyType")
    public WebElement displayPropertyType_select;

    @FindBy(css = "#minPrice")
    public WebElementFacade minPrice_select;

    @FindBy(css = "#maxPrice")
    public WebElement maxPrice_select;

    @FindBy(css = "#maxDaysSinceAdded")
    public WebElement maxDaysSinceAdded_select;

    @FindBy(css = "#minBedrooms")
    public WebElement minBedrooms_select;

    @FindBy(css = "#maxBedrooms")
    public WebElement maxBedrooms_select;

    @FindBy(css = "#includeSSTC")
    public WebElement includeSSTC_cb;

    @FindBy(id = "submit")
    public WebElement submit;

    public SearchPage(WebDriver driver) {
        super(driver);
    }
    public boolean isOnPage() {
        return true;
    }
    public void fillMinPrice(String minPrice) {
        minPrice_select.waitUntilClickable();
        Select select = new Select(minPrice_select);
        select.selectByVisibleText(minPrice);
    }
    public void fillMaxPrice(String maxPrice) {
        Select select = new Select(maxPrice_select);
        select.selectByVisibleText(maxPrice);
    }
    public void clickSubmit() {
        submit.click();
    }




}
