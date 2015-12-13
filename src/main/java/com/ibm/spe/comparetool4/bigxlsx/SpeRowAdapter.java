package com.ibm.spe.comparetool4.bigxlsx;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 用于屏蔽不用的方法
 * @author Luis
 */
public abstract class SpeRowAdapter implements Row{

	@Override
	public Iterator<Cell> cellIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cell createCell(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cell getCell(int arg0, MissingCellPolicy arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getFirstCellNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeightInPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPhysicalNumberOfCells() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CellStyle getRowStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sheet getSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getZeroHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeCell(Cell arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeight(short arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeightInPoints(float arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowStyle(CellStyle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZeroHeight(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
}
