import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class testProzorroSearchAndVerify {

    WebDriver webDriver;

    @BeforeMethod
    public void initDriver() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().window().maximize();
    }

    @AfterMethod
    public void quitDriver() {
        webDriver.quit();
    }

    @Test
    public void testProzorroSearchAndVerifyDetails() {

        String searchValue = "тумба";
        webDriver.get("https://prozorro.gov.ua/uk");

        WebElement searchInput = webDriver.findElement(By.className("search-text__input"));
        searchInput.clear();
        searchInput.sendKeys(searchValue);
        searchInput.sendKeys(Keys.ENTER);

        List<WebElement> itemsHeaders = webDriver.findElements(By.className("item-title__title"));

        WebElement firstResult = itemsHeaders.get(0);

        String title = firstResult.findElement(By.cssSelector("a.item-title__title")).getText();
        String price = firstResult.findElement(By.cssSelector("p.text-color--green.app-price__amount")).getText();
        String status = firstResult.findElement(By.cssSelector("p.getter__setter.__v_isRef.__v_isReadonly.effect__cacheable")).getText();
//        String organization = firstResult.findElement(By.cssSelector("div.search-result-card__description")).getText();

        itemsHeaders.get(0).click();

        String detailTitle = webDriver.findElement(By.cssSelector("div.tender--head--title.col-sm-9")).getText();
        String detailPrice = webDriver.findElement(By.cssSelector("div.green.tender--description--cost--number")).getText();
        String detailStatus = webDriver.findElement(By.cssSelector("span.marked")).getText();
//       String detailOrganization = webDriver.findElement(By.cssSelector("div.tender--organization")).getText();

        Assert.assertEquals(detailTitle, title, "Title does not match!");
        Assert.assertEquals(detailPrice, price, "Price does not match!");
        Assert.assertEquals(detailStatus, status, "Status does not match!");
//        Assert.assertEquals(detailOrganization, organization, "Organization does not match!");


    }
}
