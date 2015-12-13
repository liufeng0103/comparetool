package com.ibm.spe.comparetool4.bigxlsx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Row
 * @author Luis
 */
public class SpeRow extends SpeRowAdapter {
	private int rowNum;
	private Map<Integer, Cell> cells = new HashMap<Integer, Cell>();
	private short lastCellNum = -1;
	
	@Override
	public Cell getCell(int index) {		
		return cells.get(index);
	}

	@Override
	public short getLastCellNum() {		
		return lastCellNum;
	}

	@Override
	public int getRowNum() {		
		return rowNum;
	}

	@Override
	public void setRowNum(int rowNum) {	
		this.rowNum = rowNum;
	}

	@Override
	public Iterator<Cell> iterator() {		
		return new Iterator<Cell>(){
			Iterator<Map.Entry<Integer, Cell>> iter = cells.entrySet().iterator();
					
			@Override
			public boolean hasNext() {				
				return iter.hasNext();
			}
			@Override
			public Cell next() {				
				Map.Entry<Integer, Cell> entry = iter.next();
				return entry.getValue();
			}
			@Override
			public void remove() {}			
		};
	}

	@Override
	public Cell createCell(int columnIndex) {
		Cell cell = new SpeCell(columnIndex);
		cells.put(columnIndex, cell);		
		/* last cell从1开始, cell index 从0开始 */
		if((columnIndex + 1) > lastCellNum) {
			lastCellNum = (short)(columnIndex + 1);
		}
		return cell;
	}
}
