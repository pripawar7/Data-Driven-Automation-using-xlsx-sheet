package CS522_Week10_toStudents;

//import org.testng.annotations.AfterClass;
import org.openqa.selenium.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
//import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;

public class TestLogOnJUnit {
	XSSFSheet sheet;
	static int jk=1;
	XSSFWorkbook workbook;
	public WebDriver driver;
	Actions actions;
	String[] userName = new String[3];
	String[] password= new String[3];
	
	Logger logger = Logger.getLogger("CS522(L)_17094_TestNPULoginJunit");

	//	@Ignore
     
	  @Test(dataProvider="studentdat")
	  public void NPUStudentLoginTestNG(String email, String password) {
		   	
		  try{  
		   		FileInputStream file = new FileInputStream(new File("C:\\Users\\priyanka\\workspace1\\HW10\\StudentLogin.xlsx")); 
		   				//HSSFWorkbook workbook = new HSSFWorkbook(file);
		   				 workbook = new XSSFWorkbook(file);

		   				//HSSFSheet sheet = workbook.getSheetAt(0);
		   				 sheet = workbook.getSheetAt(0); 
		   				workbook.close();		  
		   				file.close();
		   		}catch(FileNotFoundException fnfe) {
		   		    fnfe.printStackTrace();
		   		   } catch (IOException ioe) {
		   		    ioe.printStackTrace();
		   		   }
		
		 //1. Initiate Firefox browser - setUp();
		
//		3. If apply online button not found
		openApplyOnline();
		logger.info("Step 3. If Apply Online button not found, try - Passed");
		captureScreenshot(driver,"Step "+ jk);
		
		//4. Check Student Login page title is "Log On"
		checkOpenStudentLogin(driver, "Log ON");
		logger.info("Step 4. Verify NPU LOG ON page is loaded - Passed");
		
	
//		5. Input Email, Password
		inputEmailPassword(email,password);
		logger.info("Step 5. Input Email, Password - Passed");
		
		
//		6. Click Save button
		clickLogOnButton();
		logger.info("Step 6. Click LOG ON button - Passed");
		//captureScreenshot(driver,"Step " + "6");
		captureScreenshot(driver,"Step "+jk);
//		7. Go back to NPU home page
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		sleep(3);		
		logger.info("Step 7. Go back to NPU home page - Passed");
		
		captureScreenshot(driver,"Step "+jk);

	}
	  

	  @DataProvider(name="studentdat")
	  public Object[][] passData()throws IOException, InvalidFormatException{
		  //TestNPULogonDataDrivenTestNG t;
		  ExcelDataRead1  config = new ExcelDataRead1("C:\\Users\\priyanka\\workspace1\\HW10\\StudentLogin.xlsx");
	   int rows = config.getRowCount(0);
	  
	   Object[][] data = new Object[rows][2];
	   
	   for (int i = 0; i < rows; i++){
	    data[i][0] = config.getData(0, i, 0);
	    data[i][1] = config.getData(0, i, 1);
	    //writeDataToExcelSheet(i); // Write data to xls sheet
	  //  userName[i]=   data[i][0].toString();
	 //  password[i]= data[i][1].toString();
	  
	   
	   }
	   return data;
	  }
	@BeforeTest
	public static void beforeclass(){
		System.out.println("    **** BEGINNING ****    ");
	}
		@AfterTest
	public static void afterclass(){
			System.out.println("    **** ENDING ****    ");
		}

	
	
	@BeforeMethod
	public void setUp(){
		
		//1. Initiate Firefox browser
		driver = new FirefoxDriver();		
		logger.info("Step 1. Initiate Firefox Browser - Passed");
		captureScreenshot(driver,"Step "+jk);
		
		//2. Google search and click to open the search result
		openNPUFromGoogleSearch(driver, actions, "NPU", "Northwestern Polytechnic University | Welcome to ... - Fremont", "http://npu.edu");
		logger.info("Step 2. Google search and click to open search result - Passed");
		captureScreenshot(driver,"Step "+jk);
	

	
	}

	@AfterMethod
	public void tearDown(){
		driver.close();
		logger.info("Step 8: Close Browser and Exit - passed");
		
		
	}
	
	public void openNPUFromGoogleSearch(WebDriver driver, Actions action, String searchtext, String searchresultlink, String WebURL){
		driver.get("http://www.google.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.id("lst-ib")).sendKeys("NPU");
		Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.ENTER).build().perform();
	    sleep(4);
	    if(driver.getPageSource().contains(searchresultlink)){
	    	driver.findElement(By.linkText(searchresultlink)).click();	    	
	    }
	    else{
	    	driver.navigate().to(WebURL);	    	
	    }
	 
	    sleep(4);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
	    if(driver.findElement(By.linkText("Northwestern Polytechnic University | Welcome to ... - Fremont")).isDisplayed() ){
		    driver.findElement(By.linkText("Northwestern Polytechnic University | Welcome to ... - Fremont")).click(); // Click Search Result Link Text to open NPU homepage
		   
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);// wait maximum up to 30s until page loaded completely
		    sleep(6);   
	    } else{
	    	driver.get("http://www.npu.edu"); // if not found the proper search result link text, just open npu.edu web site
	    	 
	    }
	    captureScreenshot(driver,"Step "+jk);
	    sleep(4);
	}
	
	public void checkOpenStudentLogin(WebDriver driver, String title){
		try{
			AssertJUnit.assertTrue(driver.getTitle().contains(title));
			captureScreenshot(driver,"Step " + jk);
		}catch(Throwable t){
			t.getMessage();
		}
	}
	
	public void openApplyOnline(){
		driver.findElement(By.xpath(".//*[@id='block-tb-megamenu-menu-header-top-menu']/div/div/div/ul/li[2]/a")).click();
	
		driver.findElement(By.xpath(".//*[@id='tb-megamenu-column-6']/div/ul/li[2]/a")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
		sleep(3);
	}
	
	
	
	public void clickLogOnButton(){
		driver.findElement(By.xpath(".//*[@id='main']/fieldset/fieldset/fieldset[1]/form/p/input")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		sleep(1);
	}
	


	public void inputEmailPassword(String Usname, String pssword){
		if(Usname !=null){
			driver.findElement(By.id("UserName")).clear();
			driver.findElement(By.id("UserName")).sendKeys(Usname);
		}
		
		if(pssword !=null){
			driver.findElement(By.id("Password")).clear();
			driver.findElement(By.id("Password")).sendKeys(pssword);
		}
		
		captureScreenshot(driver,"Step "+jk);
	}

	
	private void sleep(int i) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static String captureScreenshot(WebDriver driver, String screenshotname) {
		try {
			
				TakesScreenshot ts = (TakesScreenshot)driver;
		
				File source = ts.getScreenshotAs(OutputType.FILE);
				
				
				String	 dest = "./screenshot/" + screenshotname + ".png";
				
				File destination = new File(dest);
		
				//FileUtils.copyFile(source, new File("./screenshot/" + screenshotname + ".png"));
				
				FileUtils.copyFile(source, destination);
			jk++;
				System.out.println("screnshot taken");
				
				return dest;
				
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot." + e.getMessage());
			return e.getMessage();
		}
	}
        	
}