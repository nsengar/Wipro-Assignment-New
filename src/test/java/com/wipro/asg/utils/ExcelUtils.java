package com.wipro.asg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtils {


	
	
	public static void setExcelFile(String path, String name, TreeMap<Integer, Object[]> data) throws IOException, EncryptedDocumentException, InvalidFormatException
	{
		File file = null;
		OutputStream fos = null;
		XSSFWorkbook workbook = null;
		System.out.println("Insideeeeeeeeeeeee set Excel file");
			file = new File(path);
			XSSFSheet excelsheet = null;
			if (file.exists()) {
				workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));
				System.out.println("File already eeeeeeeeeeeeeeexist");
			} else {
				workbook = new XSSFWorkbook();
				System.out.println("Create new wrokbooooooooooooook");
			}
			
		// excelsheet  = workbook.createSheet(name);
			
		 if (workbook.getNumberOfSheets() != 0) {
			 ArrayList<String> sheetNames = new ArrayList<String>();
		        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
		           sheetNames.add(workbook.getSheetName(i));
		        }
		        
		        if (sheetNames.contains(name)) {
		        	   System.out.println("Inside inner iffffffffffffffffff");
		        	   excelsheet = workbook.getSheet(name);
		            } else {
		            	excelsheet = workbook.createSheet(name);
		            	System.out.println("Inside inner else create workboooooook");
		            }
		        
		    }
		    else {
		        // Create new sheet to the workbook if empty
		    	excelsheet = workbook.createSheet(name);
		    	System.out.println("Inside else create workboooooook");
		    }
		 
		 
		 System.out.println("Sheeeeeeeeeeeeet is"+name);
		Set<Integer> keyset = data.keySet(); 
		System.out.println("Keyset -->"+keyset);
        int rownum = 0; 
        for (Integer key : keyset) { 
            // this creates a new row in the sheet 
        //    Row row = excelsheet.createRow(rownum++); 
        	Row row = excelsheet.createRow(key);
            Object[] objArr = data.get(key); 
            int cellnum = 0; 
            for (Object obj : objArr) { 
                // this line creates a cell in the next column of that row 
                Cell cell = row.createCell(cellnum++); 
                if (obj instanceof String) 
                    cell.setCellValue((String)obj); 
                else if (obj instanceof Integer) 
                    cell.setCellValue((Integer)obj); 
            } 
        } 
        try {  
        	
           // FileOutputStream out = new FileOutputStream(path); 
           // workbook.write(out); 
           // out.close(); 
        	fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.flush();
			fos.close();
            System.out.println(path + " sheet "+ name+" written successfully on disk."); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
			}
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
			}
		}
		
	}
	
	

	@SuppressWarnings("deprecation")
	public static TreeMap<Integer,  ArrayList<Object>> readExcelFile(String path, String name) throws IOException
	{
		TreeMap<Integer,ArrayList<Object>> data = new TreeMap<Integer, ArrayList<Object>>();
	        
		try { 
            FileInputStream file = new FileInputStream(path); 
            XSSFWorkbook workbook = new XSSFWorkbook(file); 
            XSSFSheet sheet = workbook.getSheet(name); 
               Iterator<Row> rowIterator = sheet.iterator(); 
               Row row = rowIterator.next(); 
            while (rowIterator.hasNext()) { 
                 row = rowIterator.next(); 
                // For each row, iterate through all the columns 
                Iterator<Cell> cellIterator = row.cellIterator(); 
                int rowNum=(int) row.getCell(0).getNumericCellValue();
                System.out.println("Row NUm is "+rowNum);
                Cell celldata = cellIterator.next();
               ArrayList<Object> temp = new ArrayList<Object>();
                while (cellIterator.hasNext()) { 
            
                     celldata=cellIterator.next();
					
					  switch (celldata.getCellType())
					  { 
					  case Cell.CELL_TYPE_NUMERIC:
						  temp.add((int)celldata.getNumericCellValue()); break; 
					  case Cell.CELL_TYPE_STRING:
						  temp.add(celldata.getStringCellValue()); break; 
					  }
					 
                } 
                data.put(rowNum, temp);
            } 
            file.close(); 
        
		}
		 catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
			System.out.println("Data is --> "+data);
		return data;
	}
	
	
	@SuppressWarnings("deprecation")
	public static HashMap<String,  Object> readExcelFileKeyValue(String path, String name) throws IOException
	{
		HashMap<String,  Object> data = new HashMap<String,  Object>();
	        
		try { 
            FileInputStream file = new FileInputStream(path); 
            XSSFWorkbook workbook = new XSSFWorkbook(file); 
            XSSFSheet sheet = workbook.getSheet(name); 
               Iterator<Row> rowIterator = sheet.iterator(); 
               Row row = rowIterator.next(); 
            while (rowIterator.hasNext()) { 
                 	row = rowIterator.next();     
                 	 Iterator<Cell> cellIterator = row.cellIterator(); 
                    //  cellIterator.next();
                 	cellIterator.next();
                	//Object key = row.getCell(1);
                     Object key = cellIterator.next();
                	Object value = null;
                	Cell celldata = cellIterator.next();
                	switch (celldata.getCellType())
					  { 
					  case Cell.CELL_TYPE_NUMERIC:
						  value=((int)celldata.getNumericCellValue()); break; 
					  case Cell.CELL_TYPE_STRING:
						  value=celldata.getStringCellValue(); break; 
					  }
                	 data.put(key.toString(), value);
                
            } 
            file.close(); 
        
		}
		 catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
			System.out.println("Data is --> "+data);
		return data;
	}


}
