package com.ibm.spe.comparetool4.bigxlsx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

/**
 * Sheet
 * @author Luis
 */
public class SpeSheet extends SpeSheetAdapter {
	private Map<Integer, Row> rows = new HashMap<Integer, Row>();	
	private int lastRowNum = -1;

	@Override
	public Row createRow(int index) {	
		Row row = new SpeRow();
		rows.put(index, row);
		row.setRowNum(index);
		if(index > lastRowNum) {
			lastRowNum = index;
		}
		return row;
	}

	@Override
	public int getLastRowNum() {		
		return lastRowNum;
	}

	@Override
	public Row getRow(int index) {		
		return rows.get(index);
	}

	@Override
	public Iterator<Row> iterator() {		
		return new Iterator<Row>(){
			Iterator<Map.Entry<Integer, Row>> iter = rows.entrySet().iterator();		
			
			@Override
			public boolean hasNext() {	
				return iter.hasNext();
			}
			
			@Override
			public Row next() {			
				Map.Entry<Integer, Row> entry = iter.next();
				return entry.getValue();
			}
			@Override
			public void remove() {}			
		};
	}

}
