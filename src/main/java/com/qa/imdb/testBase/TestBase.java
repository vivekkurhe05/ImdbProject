package com.qa.imdb.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestBase {
	
	
	public static WebDriver driver;
	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	Properties prop;
		
	
	
	public void init() throws Exception{
		
		PropertyConfigurator.configure("Log4j.properties");
		loadPropertiesFile();
		selectBrowser(prop.getProperty("browser"));
		getURL();
		
	}

	
	
	
	public void loadPropertiesFile() throws Exception{
		
		prop = new Properties();
		File fileLocation = new File(System.getProperty("user.dir")+"/src/main/java/com/qa/imdb/propertiesFile/config.properties");
		FileInputStream fis = new FileInputStream(fileLocation);
		prop.load(fis);
		
	}

	
	
	
	public void selectBrowser(String browser){
		
		
		if(System.getProperty("os.name").contains("Windows")){
			
			log.info("Tests running on "+System.getProperty("os.name")+" operating system");
			
			if(browser.equals("firefox")){
				
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				log.info("Launching Firefox on Windows...");
				driver = new FirefoxDriver();
				log.info("Firefox opened");
				
				
			}else if(browser.equals("chrome")){
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				log.info("Launching Chrome on Windows...");
				driver = new ChromeDriver();
				log.info("Chrome opened");
				
			}
			
		}else if(System.getProperty("os.name").contains("Linux") || System.getProperty("os.name").contains("Ubuntu")){
			
			log.info("Tests running on "+System.getProperty("os.name")+" operating system");
			
			if(browser.equals("firefox")){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				log.info("Launching Firefox on Ubuntu...");
				driver = new FirefoxDriver();
				log.info("Firefox opened");
				
				
			}else if(browser.equals("chrome")){
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				log.info("Launching Chrome on Ubuntu...");
				driver = new ChromeDriver();
				log.info("Chrome opened");
				
				
			}
			
		}
		
	}
	
	
	public void getURL(){
		
		driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(prop.getProperty("pageLoadTimeOut")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("implicitWait")), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		log.info("URL launched "+prop.getProperty("url"));
		
	}

	

}
