package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.base.TestBase;

public class DatabaseConnectivity extends TestBase{
	public String[][] provideDataFromDatabase(String query) throws SQLException {
			Connection connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
			Statement statement=connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet results=statement.executeQuery(query);
			results.last();
			int rowCount=results.getRow();
			ResultSetMetaData metaData = results.getMetaData();
			int columnCount=metaData.getColumnCount();
			String data[][]=new String[rowCount][columnCount];
			int index=0;
			results.beforeFirst();
			while(results.next()) {
				for(int j=0;j<columnCount;j++) {
					data[index][j]=results.getString(j+1);
				}
				index++;
			}
			return data;
	}
	
	public int insertingDataIntoDB(String tableName,String reg,String pword) throws SQLException {
		Connection connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
		//PreparedStatement statement = connect.prepareStatement("insert into hr.testingtable(username,password) values(?,?)");
		//PreparedStatement statement = connect.prepareStatement("insert into hr.testingtable(username,password) values(?,?)");
		String query="insert into "+tableName+" values(?,?)";
		PreparedStatement statement = connect.prepareStatement(query);
		statement.setString(1, reg);
		statement.setString(2, pword);
		int count=validatingExistenceOfData(tableName,reg, pword);
		int resultCount=0;
		try {
			if(count>0) {
				resultCount=statement.executeUpdate();	
			}
		}
		catch (Exception e) {
			System.out.println("Data is already present in the database");
		}
		return resultCount;
	}
	
	public int validatingExistenceOfData(String tableName,String currentEmail,String currentPassword) throws SQLException {
		Connection connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");		
		Statement statement=connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		String query="select * from "+tableName;
		ResultSet result=statement.executeQuery(query);
		result.last();
		int rowCount=result.getRow();
		ResultSetMetaData metaData = result.getMetaData();
		int colCount=metaData.getColumnCount();
		String[][] data=new String [rowCount][colCount];
		result.beforeFirst();
		String uname = null;
		String pword = null;
		int count=0;
		int index=0;
		while(result.next()) {
			for(int j=0;j<colCount;j++) {
				 data[index][j]=result.getString(j+1);
			}
			index++;
		}
		for(int i=0;i<rowCount;i++) {
			for(int j=0;j<colCount;j++) {
				uname=data[i][0];
				pword=data[i][1];
				//System.out.print(uname+ pword);
				if(!((uname.equals(currentEmail))&&(pword.equals(currentPassword)))) {
					count++;
				}
			}
		}
		if(count>0) {
			return count;
		}
		else
			return 0;
	}
	
	public void selectingData(String tableName, String ...columnName) throws SQLException{
		Connection connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
		Statement statement=connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		String query="select * from "+tableName;
		ResultSet result=statement.executeQuery(query);
		result.last();
		int rowCount=result.getRow();
		ResultSetMetaData metaData = result.getMetaData();
		int colCount=metaData.getColumnCount();
		result.beforeFirst();
//		String uname = null;
//		String pword = null;
		while(result.next()) {
			String uname = null;
			String pword = null;
			for(int j=0;j<colCount;j++) {
				 uname=result.getString(columnName[0]);
				 pword=result.getString(columnName[1]);		
			}
			System.out.println("|"+uname+"| "+"|"+pword+"|");
		}
	}

}
