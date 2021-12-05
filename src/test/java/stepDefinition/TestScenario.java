package stepDefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestScenario {

    WebDriver driver;


    @Given("^Open chrome$")
    public void Open_chrome() throws Throwable {
        System.setProperty("webdriver.chrome.driver", "target/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Given("^Navigate to anz$")
    public void NavigateToAnz() throws Throwable {
        driver.get("https://www.anz.com.au/personal/home-loans/calculators-tools/much-borrow/");
    }

    @Given("^Navigate to melia$")
    public void NavigateToMelia() throws Throwable {
        driver.get("https://www.melia.com");
    }

    @Given("^Navigate to melia specific browse$")
    public void Navigate_to_melia_specific_browse() throws Throwable {
        driver.get("https://www.melia.com/es/hoteles/espana/madrid/melia-castilla/habitaciones.htm?fechaEntrada=1621015200&fechaSalida=1621188000");
    }


    @Given("^Close melia cookies$")
    public void Close_melia_cookies() throws Throwable {
        driver.findElement(By.xpath("//button[contains(@id,'didomi-notice-agree-button')]")).click();
    }

    @When("^User enters valid details$")
    public void User_enters_valid_details() throws Throwable {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("application_type_single")).click();
        driver.findElement(By.xpath("//label[contains(@for,'home')]")).click();//input[contains(@aria-labelledby,'q2q2')]
        driver.findElement(By.xpath("//input[@aria-labelledby='q2q1']")).sendKeys("80000");
        driver.findElement(By.xpath("//input[@aria-labelledby='q2q2']")).sendKeys("10000");
        driver.findElement(By.xpath("//input[contains(@id,'expenses')]")).sendKeys("500");
        driver.findElement(By.xpath("//input[contains(@id,'otherloans')]")).sendKeys("100");
        driver.findElement(By.xpath("//input[@id='credit']")).sendKeys("10000");
        driver.findElement(By.xpath("//button[contains(@class,'calculate')]")).click();
    }

    @Then("^borrowing estimate is displayed correctly$")
    public void borrowing_estimate_is_displayed_correctly() throws Throwable {
        Thread.sleep(1000);
        String actualamount = "$483,000";
        String item = driver.findElement(By.xpath("//span[@class='borrow__result__text__amount']")).getText();
        Assert.assertTrue(item.contains(actualamount));
    }

    @When("^User clicks start over button$")
    public void User_clicks_start_over_button() throws Throwable {
        driver.findElement(By.xpath("(//span[contains(@class,'icon icon_restart')])[1]")).click();
    }

    @Then("^Form is cleared$")
    public void Form_is_cleared() throws Throwable {
        String resetvalue = "0";
        String item1 = driver.findElement(By.xpath("//input[@aria-labelledby='q2q1']")).getAttribute("value");
        String item2 = driver.findElement(By.xpath("//input[@aria-labelledby='q2q2']")).getAttribute("value");
        String item3 = driver.findElement(By.xpath("//input[contains(@id,'expenses')]")).getAttribute("value");
        String item4 = driver.findElement(By.xpath("//input[@id='homeloans']")).getAttribute("value");
        String item5 = driver.findElement(By.xpath("//input[@id='otherloans']")).getAttribute("value");
        String item6 = driver.findElement(By.xpath("//input[contains(@aria-labelledby,'q3q4')]")).getAttribute("value");
        String item7 = driver.findElement(By.xpath("//input[@id='credit']")).getAttribute("value");

        Assert.assertTrue(item1.contains(resetvalue));
        Assert.assertTrue(item2.contains(resetvalue));
        Assert.assertTrue(item3.contains(resetvalue));
        Assert.assertTrue(item4.contains(resetvalue));
        Assert.assertTrue(item5.contains(resetvalue));
        Assert.assertTrue(item6.contains(resetvalue));
        Assert.assertTrue(item7.contains(resetvalue));
    }

    @When("^User does not enter all details$")
    public void User_does_not_enter_all_details() throws Throwable {
        driver.findElement(By.xpath("//input[@id='expenses']")).sendKeys("1");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[contains(@class,'calculate')]")).click();
    }

    @Then("^Correct error is displayed$")
    public void Correct_error_is_displayed() throws Throwable {
        String errorMessage = "Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. For questions, call us on 1800 035 500.";
        String item = driver.findElement(By.xpath("//span[contains(@class,'borrow__error__text')]")).getText();
        Assert.assertTrue(item.contains(errorMessage));
    }

    @Then("^Check link ios app$")
    public void Check_link_app() throws Throwable {
        String linkApp = "https://app.melia.com";
        String item = driver.findElement(By.xpath("//div[contains(@class,'u-download-apps')]//a[1]")).getAttribute("href");
        Assert.assertTrue(item.contains(linkApp));
    }

    @Then("^Click link ios app$")
    public void Click_link_ios_app() throws Throwable {
        driver.findElement(By.xpath("//div[contains(@class,'u-download-apps')]//a[1]")).click();
        //Ver visualmente el resultado
        Thread.sleep(4000);
    }


    @Then("^Application should be closed$")
    public void Application_should_be_closed() throws Throwable {
        driver.quit();
    }

    @When("^the user inserts the mandatory values in the Hotel browser$")
    public void User_inserts_the_mandatory_values_in_the_hotel_browser(DataTable dt) {
        List<Map<String, String>> userValues = dt.asMaps(String.class, String.class);
        setMandatoryHotelValues(
                userValues.get(0).get("Destination"),
                userValues.get(0).get("CheckIn"),
                userValues.get(0).get("CheckOut")
        );
    }

    public void setMandatoryHotelValues(String destinationName, String checkIn, String checkOut) {
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        final By destinationHotel = By.id("mbe-destination-input");
        final WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(destinationHotel));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(destinationName);
        final By destinationHotelList = By.xpath("//ul[contains(@class, 'mbe-results-destination')]/li/b");
        boolean elementFound = false;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(destinationHotelList));
        List<WebElement> elementsOnList = driver.findElements(destinationHotelList);
        for (WebElement element : elementsOnList) {
            if ((element.getText().equalsIgnoreCase(destinationName))) {
                element.click();
                elementFound = true;
                break;
            }
        }
        if (!elementFound) {
            System.out.println("The item to select does not exist");
        }
        final String dateHotelList = "//div[contains(@class, 'mbe-calendar')]//li[@d= \"%s\"]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(dateHotelList, checkIn)))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(dateHotelList, checkOut)))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='mbe-rooms']//button[contains(text(),'Aceptar')]"))).click();
    }

    @And("^the user clicks on 'Buscar' button$")
    public void the_user_clicks_on_buscar_button() {
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("mbe-search-button"))).click();
    }

    @Then("^the number of rooms displayed is '(\\d+)'$")
    public void the_number_of_rooms_displayed_is(int expectedRoomsDisplayed) {
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        final By roomsList = By.xpath("//div[contains(@class, 'c-hotel-sheet-room')]//a[contains(@class, 'btn')]");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(roomsList));
        List<WebElement> elementsOnList = driver.findElements(roomsList);
        Assert.assertEquals(expectedRoomsDisplayed, elementsOnList.size());
    }
}