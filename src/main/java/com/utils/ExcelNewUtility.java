package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.*;

public class ExcelNewUtility 
{
	public FileInputStream inputStream;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public String path;
	
	public ExcelNewUtility(String path) {
		this.path = path;
	}
	
	public int getRowCount(String sheetName) throws IOException {
		inputStream=new FileInputStream(path);
		workbook=new XSSFWorkbook(inputStream);
		sheet=workbook.getSheet(sheetName);
		int rowCount=sheet.getLastRowNum();
		workbook.close();
		inputStream.close();
		return rowCount;
	}
	
	public int getCellCount(String sheetName, int rowNum) throws IOException{
		inputStream=new FileInputStream(path);
		workbook=new XSSFWorkbook(inputStream);
		sheet=workbook.getSheet(sheetName);
		int colCount=sheet.getRow(rowNum).getLastCellNum();
		workbook.close();
		inputStream.close();
		return colCount;
	}
	
	public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
		inputStream=new FileInputStream(path);
		workbook=new XSSFWorkbook(inputStream);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rowNum);
		cell=row.getCell(colNum);
		DataFormatter formatter=new DataFormatter();
		String data;
		try {
			data=formatter.formatCellValue(cell);
		}
		catch(Exception e) {
			data="";
		}
		return data;
	}
	
	public String[][] getData(String sheetName, String path) {
		ExcelNewUtility excel=new ExcelNewUtility(path);
		try {
		int totalRows=excel.getRowCount(sheetName);
		int totalCells=excel.getCellCount(sheetName, 1);
		String loginData[][]=new String[totalRows][totalCells];
		for(int i=1;i<=totalRows;i++) {
			for(int j=0;j<totalCells;j++) {
				loginData[i-1][j]=excel.getCellData(sheetName, i, j);
			}
		}
		return loginData;
		}
		catch(IOException e) {
			return null;
		}
	}
}