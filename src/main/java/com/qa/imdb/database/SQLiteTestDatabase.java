package com.qa.imdb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class SQLiteTestDatabase {

	private static Connection con;
	private static boolean hasData = false;
	public static final Logger log = Logger.getLogger(SQLiteTestDatabase.class.getName());
	
		
	public ResultSet displayDetails() throws SQLException, ClassNotFoundException{
		
		if(con == null){
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT MovieTitle, Ratings, Years FROM IMDBMovieTable");
		return res;
		
	}

	private void getConnection() throws ClassNotFoundException, SQLException {
		
		log.info("Connecting to database");
		Class.forName("org.sqlite.JDBC");
//		con = DriverManager.getConnection("jdbc:sqlite:C:/Users/sai/Downloads/Compressed/sqlitedB/company.db");
		con = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"/sqlitedB/company.db");
		log.info("Database connection is done successfully");
		initialise();
		
		
	}

	private void initialise() throws SQLException {

		if(!hasData){
			hasData = true;
			
			
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'IMDBMovieTable'");
			
			if(!res.next()){
				
				System.out.println("Building the IMDBMovieTable table with prepopulated values.");
				
				Statement state2 = con.createStatement();
				
				log.info("Checking for table availability");
				state2.execute("CREATE TABLE IF NOT EXISTS IMDBMovieTable (MovieTitle varchar(40),"+"Ratings varchar(20),"+"Years varchar(20));");				
			}
			
		}
		
	}
	
	
	public void addDetails(String movieTitle, String movieRating, String movieYear) throws ClassNotFoundException, SQLException{
		
		if(con == null){
			
			getConnection();
		}
		
		PreparedStatement prep = con.prepareStatement("INSERT INTO IMDBMovieTable values(?,?,?);");
		prep.setString(1, movieTitle);
		prep.setString(2, movieRating);
		prep.setString(3, movieYear);
//		prep.execute();
		prep.executeUpdate();
		
		
	}
	
	
	public void deleteDetails() throws ClassNotFoundException, SQLException{
		
		if(con == null){
			
			getConnection();
		}
		
		PreparedStatement prep = con.prepareStatement("delete from IMDBMovieTable;");
		prep.execute();
		
		log.info("Records deleted successfully from table");
	}
	
}
