package mycompany.pageobjects;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObject {

    @FindBy(css = "#searchLocation")
    public WebElement searchLocation_txt;

    @FindBy(css = "#buy")
    public WebElement buy_bt;

    @FindBy(css = "#rent")
    public WebElement rent_bt;

    public HomePage(WebDriver driver) {
        super(driver);
    }
    public boolean isOnPage() {
        return true;
    }
    public void fillSearchLocation(String location) {
        this.searchLocation_txt.sendKeys(location);
    }
    public void clickForSale() {
        buy_bt.click();
    }
    public void clickForRent() {
        rent_bt.click();
    }






}
