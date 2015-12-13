package com.ibm.spe.comparetool4;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.spe.comparetool4.compare.CompareSummary;
import com.ibm.spe.comparetool4.util.ConfigFileHelper;

/**
 * 操作比较结果文件
 * @author Luis
 */
public class ResultFile {
	private static final String RESULT_FILE_NAME = ConfigFileHelper.getConfig().getResultFileName();
	
	private Workbook wb = null;	
	private Sheet sheet = null;
	private String sheetName = null;
	private int rowNum = 0;
	private int increase = 0;
	private int errorRowCount = 0;
	
	public ResultFile() {
		wb = new XSSFWorkbook();		
	}
	
	public void createSheet(String sheetName) {		
		this.sheetName = sheetName;
		sheet = wb.createSheet(sheetName);
		rowNum = 0;
		increase = 0;
		errorRowCount = 0;
		info("Key Value", "Compare Results", "Sheet Row", "Error Type");
	} 
	
	public void info(String value1, String value2, String value3) {
//		if(rowNum == 60000) {
//			createSheet(sheetName + increase++);			
//		}
		Row row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue(value1);
		row.createCell(1).setCellValue(value2);
		row.createCell(2).setCellValue(value3);
		rowNum++;
	}
	
	public void info(String value1, String value2, String value3, String value4) {

		Row row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue(value1);
		row.createCell(1).setCellValue(value2);
		row.createCell(2).setCellValue(value3);
		row.createCell(3).setCellValue(value4);
		rowNum++;
	}
	
	public void info(String value) {
		info("", value, "");
	}
	
	public void write() {		
		try {
			FileOutputStream fileOut = new FileOutputStream(RESULT_FILE_NAME);
			wb.write(fileOut);		
			fileOut.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}    
	}
	
	public void setSheetCompareResult(int totalRowsVerified) {
        Row row0 = sheet.getRow(0);
        row0.createCell(4).setCellValue("Summary");
        Row row1 = sheet.getRow(1);
        if(row1 == null) {
            row1 = sheet.createRow(1);
        }
        row1.createCell(4).setCellValue("Total Rows Verified: " + totalRowsVerified);
        Row row2 = sheet.getRow(2);
        if(row2 == null) {
            row2 = sheet.createRow(2);
        }
        row2.createCell(4).setCellValue("Total Rows Correct: " + (totalRowsVerified - errorRowCount));
        Row row3 = sheet.getRow(3);
        if(row3 == null) {
            row3 = sheet.createRow(3);
        }
        row3.createCell(4).setCellValue("Total Rows with Error: " + errorRowCount);
		// Total Rows Verified
		// Total Rows Correct
		// Total Rows with Error
	}
	
	public void setSheetCompareResult(CompareSummary summary) {
        Row row0 = sheet.getRow(0);
        row0.createCell(4).setCellValue("Summary");
        Row row1 = sheet.getRow(1);
        if(row1 == null) {
            row1 = sheet.createRow(1);
        }
        row1.createCell(4).setCellValue("Total Rows Verified: " + summary.getTotalRowCount());
        Row row2 = sheet.getRow(2);
        if(row2 == null) {
            row2 = sheet.createRow(2);
        }
        row2.createCell(4).setCellValue("Total Rows Correct: " + summary.getTotalCorrectRowCount());
        Row row3 = sheet.getRow(3);
        if(row3 == null) {
            row3 = sheet.createRow(3);
        }
        row3.createCell(4).setCellValue("Total Rows with Error: " + summary.getTotalErrorRowCount());
        Row row4 = sheet.getRow(4);
        if(row4 == null) {
            row4 = sheet.createRow(4);
        }
//        row4.createCell(4).setCellValue("Total Rows with Not Found: " + summary.getNotFoundErrorRowCount());
//        Row row5 = sheet.getRow(5);
//        if(row5 == null) {
//            row5 = sheet.createRow(5);
//        }
//        row5.createCell(4).setCellValue("Total Rows with Not Match: " + summary.getNotMatchErrorRowCount());
		// Total Rows Verified
		// Total Rows Correct
		// Total Rows with Error
	}

	public void errorRow() {
		errorRowCount++;
	}
}
