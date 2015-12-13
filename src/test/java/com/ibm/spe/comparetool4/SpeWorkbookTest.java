package com.ibm.spe.comparetool4;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.ibm.spe.comparetool4.bigxlsx.SpeWorkbook;

public class SpeWorkbookTest {
//	@Test
	public void test() throws Exception {
		Workbook book = new SpeWorkbook("Financial Data Export - 6948-17H.xlsx");
		Sheet sheet = book.getSheet("FINANCIAL_FACTORS");
		for (Row row : sheet) {
			for (Cell cell : row) {
				 String value = "";
				if(cell != null) {
		            switch (cell.getCellType()) {
		                case Cell.CELL_TYPE_STRING:
		                    value = cell.getStringCellValue();
		                    break;
		                case Cell.CELL_TYPE_NUMERIC:
		                    double tmp = cell.getNumericCellValue();
		                    value = new DecimalFormat("0.000000").format(tmp);
		                    break;
		                case Cell.CELL_TYPE_BLANK:
		                    value = "";
		                    break;
		                default:
		                   throw new RuntimeException();
		            }
		        }
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}
	

}
