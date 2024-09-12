package amazon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.*;
public class ProductPage {

    	    private WebDriver driver;

    	    @BeforeClass
    	    public void setup() {
    	        WebDriverManager.chromedriver().setup();
    	        driver = new ChromeDriver();
    	        driver.manage().window().maximize();
    	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    	    }

    	    @Test
    	    public void testProductPage() {
    	        System.out.println("Opening Amazon India website...");
    	        driver.get("https://www.amazon.in");
    	        System.out.println("Searching for 'LG Soundbar'...");
    	        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
    	        searchBox.sendKeys("lg soundbar");
    	        searchBox.submit();
    	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    	        System.out.println("Fetching product names and prices from the first search results page...");

    	        List<WebElement> products = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
    	        Map<String, Integer> productPriceMap = new HashMap<>();
    	        List<String> noPriceProducts = new ArrayList<>();

    	        for (WebElement product : products) {
    	            try {
    	                String name = product.findElement(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal")).getText();
    	                String priceString = product.findElement(By.cssSelector("span.a-price-whole")).getText().replace(",", "");
    	                int price = Integer.parseInt(priceString);
    	                productPriceMap.put(name, price);
    	            } catch (Exception e) {
    	                // Track products without prices
    	                String name = product.findElement(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal")).getText();
    	                noPriceProducts.add(name);
    	            }
    	        }

    	        System.out.println("Sorting products by price...");
    	        List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(productPriceMap.entrySet());
    	        sortedProducts.sort(Map.Entry.comparingByValue());

    	        System.out.println("Displaying products sorted by price:");
    	        for (Map.Entry<String, Integer> entry : sortedProducts) {
    	            System.out.println("Price: ₹" + entry.getValue() + " | Product: " + entry.getKey());
    	        }

    	        System.out.println("Products with no price information:");
    	        for (String productName : noPriceProducts) {
    	            System.out.println("Product: " + productName + " | No price information available.");
    	        }
    	    }

    	    @AfterClass
    	    public void teardown() {
    	        driver.quit();
    	    }
    	}