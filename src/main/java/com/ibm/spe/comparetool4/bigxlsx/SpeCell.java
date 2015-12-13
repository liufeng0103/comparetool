package com.ibm.spe.comparetool4.bigxlsx;

/**
 * 单元格，只实现了部分功能，主要用于excel文件的读取
 * @author Luis
 */
public class SpeCell extends SpeCellAdapter {

	private int cellType;
	private int columnIndex;
	private String stringCellValue;	
	private double numericCellValue;

	public SpeCell(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	@Override
	public int getCellType() {		
		return cellType;
	}
	
	@Override
	public void setCellType(int cellType) {	
		this.cellType = cellType;
	}

	@Override
	public String getStringCellValue() {		
		return stringCellValue;
	}

	@Override
	public void setCellValue(String cellValue) {
		this.stringCellValue = cellValue;
	}	

	@Override
	public double getNumericCellValue() {		
		return numericCellValue;
	}

	@Override
	public void setCellValue(double numericCellValue) {
		this.numericCellValue = numericCellValue;		
	}
	
	@Override
	public int getColumnIndex() {		
		return columnIndex;
	}
	
}
