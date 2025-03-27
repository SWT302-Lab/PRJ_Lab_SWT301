/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.Duration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import static org.junit.Assert.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author meocon
 */
public class AddDishTestSelenium {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\yhv\\Downloads\\Edgedriver\\msedgedriver.exe");
        driver = new EdgeDriver();
        baseUrl = "http://localhost:8080/prjProject/";
    }
    
    @Test
    public void login() throws InterruptedException {
        // Điều hướng đến trang đăng nhập
        driver.get(baseUrl + "index.html");

        // Chuyển sang form Admin
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("signUp")));
        button.click();
        Thread.sleep(1000);

        // Đợi form Admin hiện ra
        WebElement usernameField = driver.findElement(By.xpath("//div[contains(@class, 'sign-up-container')]//input[@name='username']"));
        WebElement passwordField = driver.findElement(By.xpath("//div[contains(@class, 'sign-up-container')]//input[@name='password']"));
        WebElement loginButton = driver.findElement(By.xpath("//div[contains(@class, 'sign-up-container')]//button[text()='Sign In']"));

        // Nhập thông tin đăng nhập Admin
        usernameField.sendKeys("khanhnd");
        passwordField.sendKeys("HE186956");
        loginButton.click();
        wait.until(ExpectedConditions.urlContains("admin.jsp"));
        assertTrue("Login should redirect to admin.jsp",
                driver.getCurrentUrl().contains("admin.jsp"));
    }
    
    @Test
    public void testAddDish1() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));

        nameField.clear();
        nameField.sendKeys("Chả Cá Lã Vọng");

        priceField.clear();
        priceField.sendKeys("600000");

        statusField.selectByValue("yes");

        imageField.clear();
        imageField.sendKeys("https://cdn.eva.vn/upload/1-2024/images/2024-01-19/cach-lam-cha-ca-la-vong-thom-nuc-mui-an-suong-mieng-cho-dip-tat-nien-420016090_3319731778317987_4285851838373962216_n-1705637382-942-width780height681.jpg");
        addButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("getDish"));
        assertTrue("Add should redirect to dish list",
                driver.getCurrentUrl().contains("getDish"));
        driver.quit();
    }
    
    @Test
    public void testAddDish2() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));

        nameField.clear();
        nameField.sendKeys("Cơm");
        
        priceField.clear();
        priceField.sendKeys("1000");
        
        statusField.selectByValue("yes");
        
        imageField.clear();
        imageField.sendKeys("https://cdn.tgdd.vn/2021/09/CookDish/cach-lam-ca-phao-muoi-xoi-an-lien-chua-cay-gion-ngon-don-gian-avt-1200x676cdn.tgdd.vn/2021/09/CookDish/cach-lam-ca-phao-muoi-xoi-an-lien-chua-cay-gion-ngon-don-gian-avtcdn.tgdd.vn/2021/09/CookDish/cach-lam-ca-phao9.jpg");
        addButton.click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("getDish"));
        assertTrue("Add should redirect to dish list",
                driver.getCurrentUrl().contains("getDish"));
        driver.quit();
    }
    
    @Test
    public void testAddDish3() throws InterruptedException {
        // Login first
        login();
        
        // Navigate to create dish page
        driver.get(baseUrl + "createDish.jsp");
        
        // Find form elements
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Clear and enter dish details
        nameField.clear();
        nameField.sendKeys("Cơm rang hải sản đặc biệt Khánh Hòa kết hợp với rau thơm và gia vị từ vùng cao nguyên xa xôi Đắk Lắk");
        
        priceField.clear();
        priceField.sendKeys("999999999");
        
        statusField.selectByValue("no");
        
        imageField.clear();
        imageField.sendKeys("https://.net.png");
        
        // Click add button
        addButton.click();
        
        // Wait and verify redirection
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("getDish"));
        assertTrue("Add should redirect to dish list",
                driver.getCurrentUrl().contains("getDish"));
        // Close the browser
        driver.quit();
    }

    @Test    
    public void testAddDish4() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        nameField.clear();
        nameField.sendKeys("A");
        
        priceField.clear();
        priceField.sendKeys("898");
        
        statusField.selectByValue("no");
        
        imageField.clear();
        imageField.sendKeys("https://example.com/products/vietnamese-restaurant/menu/special-dishes/seafood/grilled-fish/traditional-recipes/north-region/hanoi-style/with-special-herbs/dill-turmeric-galangal/served-with-rice-noodles/fresh-vegetables/peanuts/spring-onions/premium-quality/family-secret-recipe/best-seller-2025/img12345.jpg");
        
        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }
    
    @Test    
    public void testAddDish5() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        nameField.clear();
        nameField.sendKeys("Cá hồi Na Uy nướng lá chuối kèm sốt chanh dây Đà Lạt và khoai tây nghiền hương vị Pháp, trang trí với lá húng quế Ý, rau thơm Hạ Long và hoa tím Sapa phục vụ trên đĩa sứ nghệ thuật Bát Tràng cao cấp");
        
        priceField.clear();
        priceField.sendKeys("9000000000009");
        
        statusField.selectByValue("no");
        
        imageField.clear();
        imageField.sendKeys("https://.jpg");
        
        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }
    
    @Test    
    public void testAddDish6() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        nameField.clear();
        nameField.sendKeys("12389abcdg@%$");
        
        priceField.clear();
        priceField.sendKeys("0");
        
        statusField.selectByValue("no");
        
        imageField.clear();
        imageField.sendKeys("abcdg@htps");
        
        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }
    
    @Test    
    public void testAddDish7() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Clear and enter new dish details
        nameField.clear();
        nameField.sendKeys("Bánh Mì");

        priceField.clear();
        priceField.sendKeys("abc12389money");

        statusField.selectByValue("no");

        imageField.clear();
        imageField.sendKeys("https://media-cdn-v2.laodong.vn/storage/newsportal/2024/5/31/1346983/Banh-Mi-8.jpg");

        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        //String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        //String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        //boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        //boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        //System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        //System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        //Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        //Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }
       
    @Test    
    public void testAddDish8() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Clear and enter new dish details
        nameField.clear();
        nameField.sendKeys("Bánh Mì Que");

        priceField.clear();
        priceField.sendKeys("");
        // Note: Price field left empty as per your specification

        statusField.selectByValue("no");

        imageField.clear();
        imageField.sendKeys("https://cdn.tgdd.vn/Files/2020/10/22/1301077/tuyet-chieu-lam-banh-mi-que-thit-cay-gion-rum-ngon-tai-te-202203142255016223.jpg");

        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        //String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        //String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        //boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        //boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        //System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        //System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        //Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        //Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }
       
    @Test    
    public void testAddDish9() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Clear and enter new dish details
        nameField.clear();
        nameField.sendKeys("GG");

        priceField.clear();
        priceField.sendKeys("999");

        statusField.selectByValue("no");

        imageField.clear();
        imageField.sendKeys("https://example.com/a-very-long-url-that-contains-precisely-256-characters-including-the-domain-path-and-query-parameters?param1=value1&param2=value2&longquerystring=abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789X123456789123456789123456789");

        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }
    
    @Test    
    public void testAddDish10() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Clear and enter new dish details
        nameField.clear();
        nameField.sendKeys("Lẩu cá tầm Sapa nấu với nấm rừng, rau thơm núi đá, dùng kèm bún gạo tươi Hà Nội, chao ớt cay nồng đậm");

        priceField.clear();
        priceField.sendKeys("1000000000");

        statusField.selectByValue("no");

        imageField.clear();
        imageField.sendKeys("https://az/abcd");
        
        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }

    @Test    
    public void testAddDish11() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Clear and enter new dish details
        nameField.sendKeys("");

        priceField.clear();
        priceField.sendKeys("");

        statusField.selectByValue("yes");

        imageField.clear();
        imageField.sendKeys("");

        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        String expectedNameError = "Dish name must be between 3 and 100 characters";
        String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        Assert.assertTrue("Dish name error message not found!", isNameErrorPresent);
        Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }
    
    @Test    
    public void testAddDish12() throws InterruptedException {
        login();
        driver.get(baseUrl + "createDish.jsp");
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        Select statusField = new Select(driver.findElement(By.name("status")));
        WebElement imageField = driver.findElement(By.name("image"));
        WebElement addButton = driver.findElement(By.xpath("//input[@type='submit']"));
        
        // Clear and enter new dish details
        nameField.clear();
        nameField.sendKeys("Bún Bò Huế");

        priceField.clear();
        priceField.sendKeys("50000");

        statusField.selectByValue("yes");

        imageField.clear();
        imageField.sendKeys("https://bizweb.dktcdn.net/100/442/328/files/bun-bo-hue-dac-san-dan-da-lam-say-long-biet-bao-thuc-khach-4.jpg?v=1638937219225");

        // Click add button
        addButton.click();        


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Tìm phần tử chứa lỗi (thẻ <h3>)
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));

// Lấy nội dung lỗi thực tế
        String actualErrorMessage = errorElement.getText();

// In ra để kiểm tra
        System.out.println("Actual Error Message: " + actualErrorMessage);

// Thông báo lỗi mong đợi
        String expectedNameError = "Dish name already exists. Please choose a different name.";
        //String expectedPriceError = "Price must be a valid number between 1000 and 999,999,999";
        //String expectedImageUrlError = "Image URL must be between 16 and 255 characters and start with http:// or https://";

// Kiểm tra từng lỗi có xuất hiện không
        boolean isNameErrorPresent = actualErrorMessage.contains(expectedNameError);
        //boolean isPriceErrorPresent = actualErrorMessage.contains(expectedPriceError);
        //boolean isImageUrlErrorPresent = actualErrorMessage.contains(expectedImageUrlError);

// In kết quả kiểm tra ra console
        System.out.println("Dish Name Error Present: " + isNameErrorPresent);
        //System.out.println("Dish Price Error Present: " + isPriceErrorPresent);
        //System.out.println("Image URL Error Present: " + isImageUrlErrorPresent);

// Kiểm tra nếu thiếu bất kỳ lỗi nào thì test fail
        Assert.assertTrue("Dish name dupplicate message not found!", isNameErrorPresent);
        //Assert.assertTrue("Dish price error message not found!", isPriceErrorPresent);
        //Assert.assertTrue("Image URL error message not found!", isImageUrlErrorPresent);
        driver.quit();
    }

}


