package com.qa.imdb.runTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.imdb.database.SQLiteTestDatabase;
import com.qa.imdb.testBase.TestBase;


public class RunTest extends TestBase {
	
	
	public static final Logger log = Logger.getLogger(RunTest.class.getName());
	
	ArrayList<String> movieTitleList = new ArrayList<String>();
    ArrayList<String> movieRatingsList = new ArrayList<String>();
    ArrayList<String> movieYearsList = new ArrayList<String>();

    
	@BeforeMethod
	public void setUp() throws Exception{
		init();	
	}
	
	
	
	@Test(priority=1)
	public void storeMovieTitlesInArrayList(){
		
		log.info("Storing titles in an ArrayList");
		List<WebElement> movieTitles = driver.findElements(By.xpath("//table[@data-caller-name='chart-top250movie']/tbody/tr/td[2]/a"));
		log.info("Collecting titles from website");
		for(WebElement t : movieTitles){
			
			String titles = t.getText();
			System.out.println(titles);
			movieTitleList.add(titles);
			
		}
		
		log.info("Movie titles stored successfully");
		
	}
	
	
	@Test(priority=2)
	public void storeMovieRatingsInArrayList(){
		
		log.info("Storing Ratings in an ArrayList");
		
		List<WebElement> movieRatings = driver.findElements(By.xpath("//table[@data-caller-name='chart-top250movie']/tbody/tr/td[3]/strong"));
		log.info("Collecting ratings from website");
		for(WebElement r : movieRatings){
			
			String ratings = r.getText();
			System.out.println(ratings);
			movieRatingsList.add(ratings);
		}
		
		log.info("Ratings stored successfully");

		
	}
	
	
	@Test(priority=3)
	public void storeMovieYearsInArrayList(){
		
		log.info("Storing movie release years in an ArrayList");
		
		List<WebElement> movieYears = driver.findElements(By.xpath("//table[@data-caller-name='chart-top250movie']/tbody/tr/td[2]/span"));
		log.info("Collecting years from website");
		for(WebElement y : movieYears){
			
			String years = y.getText();
			System.out.println(years);
			movieYearsList.add(years);
		}		
		
		log.info("Years stored successfully");
		
	}
	
	
	@Test(priority=4)
	public void fetchImdbDataFromTable() throws ClassNotFoundException, SQLException{
		
		SQLiteTestDatabase test = new SQLiteTestDatabase();
		ResultSet rs;
		
		log.info("Deleting previous records from table..");
		test.deleteDetails();
		log.info("Records deleted successfully");
		
		log.info("Display IMDB top 250 movies list\n");
		for(int i=0;i<movieTitleList.size();i++){
			
			test.addDetails(movieTitleList.get(i), movieRatingsList.get(i), movieYearsList.get(i));
			
			rs = test.displayDetails();
			
			while(rs.next()){
//				System.out.println(rs.getString("MovieTitle")+" "+rs.getString("Ratings")+" "+rs.getString("Years"));
				log.info(rs.getString("MovieTitle")+" "+rs.getString("Ratings")+" "+rs.getString("Years"));
			}

		}
		
		log.info("Records added successfully to table");
	}
	
	
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
		log.info("Browser closed");
	}
	

}
