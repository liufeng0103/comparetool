package com.ibm.spe.comparetool4.compare;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.ibm.spe.comparetool4.config.ColumnMapping;

/**
 * Created by luis on 2015/2/5.
 */
public interface CompareFile {

    Map<String, Row> getSheetRows(String sheetName) throws CompareException;

    int getColumnIndex(String sheetName, String columnName) throws CompareException;

    String getCellValue(Cell cell);

    List<ColumnMapping> getAllColumnForSheet(String sheetName);
    
    int getSheetRowCount(String sheetName);
}
