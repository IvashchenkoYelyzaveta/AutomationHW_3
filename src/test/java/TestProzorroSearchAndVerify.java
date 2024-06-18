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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestProzorroSearchAndVerify {

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

        List<WebElement> itemsHeaders = webDriver.findElements(By.className("search-result-card__wrap"));

        WebElement firstResult = itemsHeaders.get(0);

        String title = firstResult.findElement(By.cssSelector("a.item-title__title")).getText();
        String price = firstResult.findElement(By.cssSelector("p.text-color--green.app-price__amount")).getText();
        String status = firstResult.findElement(By.cssSelector("span.getter")).getText();
        String organization = firstResult.findElement(By.cssSelector("div.search-result-card__description")).getText();

        firstResult.findElement(By.cssSelector("a.item-title__title")).click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String detailTitle = webDriver.findElement(By.cssSelector("div.tender--head--title")).getText();
        String detailPrice = webDriver.findElement(By.cssSelector("div.green.tender--description--cost--number")).getText();
        String detailStatus = webDriver.findElement(By.cssSelector("span.marked")).getText();
        String detailOrganization = webDriver.findElement(By.xpath("//table[@class='tender--customer margin-bottom']//tr[1]/td[2]")).getText();

        // Сравниваем только числа в цене
        int priceNumber = extractNumber(price);
        int detailPriceNumber = extractNumber(detailPrice);

        Assert.assertEquals(detailTitle, title, "Title does not match!");
        Assert.assertEquals(priceNumber, detailPriceNumber, "Price does not match!");
        Assert.assertEquals(detailStatus, status, "Status does not match!");
        Assert.assertEquals(extractBeforeSymbol(detailOrganization, '•'), extractBeforeSymbol(organization, '•'), "Organization does not match!");
//        Assert.assertEquals(detailOrganization, organization, "Organization does not match!");

    }

    // Метод для извлечения числа из строки
    private int extractNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }

    // Метод для извлечения части строки до указанного символа
    private String extractBeforeSymbol(String text, char symbol) {
        int index = text.indexOf(symbol);
        if (index != -1) {
            return text.substring(0, index).trim();
        } else {
            return text.trim();
        }
    }

//table[@class='tender--customer margin-bottom']//tr[1]/td[2] поиск нашего элемента в таблице без привязки к тексту

}
