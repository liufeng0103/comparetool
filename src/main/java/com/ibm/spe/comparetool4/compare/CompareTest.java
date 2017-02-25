package com.ibm.spe.comparetool4.compare;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;

import com.ibm.spe.comparetool4.ResultFile;
import com.ibm.spe.comparetool4.config.ColumnMapping;
import com.ibm.spe.comparetool4.config.CompareConfig;
import com.ibm.spe.comparetool4.config.SheetMapping;
import com.ibm.spe.comparetool4.gui.ConsoleTextarea;
import com.ibm.spe.comparetool4.gui.MessageDisplay;
import com.ibm.spe.comparetool4.util.ConfigFileHelper;

/**
 * Created by luis on 2015/2/6.
 */
public class CompareTest {

    private static Log logger = LogFactory.getLog(CompareTest.class);

    public static final String FM = "Financial - Mrt";
    public static final String MM = "Mrt - Mrt";
    public static final String FF = "Financial - Financial";
    public static final String[] compareTypes = {FM, MM, FF};

    private CompareConfig config;

    public CompareTest() {
        config = ConfigFileHelper.getConfig();
    }

    public void executeCompare(String fromFile, String toFile, String compareType) {
        ConsoleTextarea.info(String.format("Compare type : %s", compareType));
        ResultFile resultFile = new ResultFile();
        try {
            CompareFile file1 = null;
            CompareFile file2 = null;
            List<SheetMapping> mappings = null;
            if(FM.equals(compareType)) {
                file1 = new FinancialFile(fromFile);
                file2 = new MrtFile(toFile, FM);
                mappings = config.getFmSheetMappings();
            } else if(MM.equals(compareType)) {
                file1 = new MrtFile(fromFile, MM);
                file2 = new MrtFile(toFile, MM);
                mappings = config.getMmSheetMappings();

            } else if(FF.equals(compareType)) {
                file1 = new FinancialFile(fromFile);
                file2 = new FinancialFile(toFile);
                mappings = config.getFfSheetMappings();
            }
            for(SheetMapping mapping : mappings) {
                if(mapping.isSheetNeedCompare()) {
                	CompareSummary summary = new CompareSummary();
                    resultFile.createSheet(mapping.getSheet1Name());
                    ConsoleTextarea.info(String.format("Compare sheet[%s] with sheet[%s]", mapping.getSheet1Name(), mapping.getSheet2Name()));
                    Map<String, Row> rows1 = file1.getSheetRows(mapping.getSheet1Name());
                    Map<String, Row> rows2 = file2.getSheetRows(mapping.getSheet2Name());
                    ConsoleTextarea.info(String.format("%s rows VS %s rows", rows1.size(), rows2.size()));
                    summary.setTotalRowCount(file1.getSheetRowCount(mapping.getSheet1Name()));
                    List<ColumnMapping> comparedColumns;
                    if(FF.equals(compareType) || MM.equals(compareType)) {
                        comparedColumns = file1.getAllColumnForSheet(mapping.getSheet1Name());
                    } else {
                        comparedColumns = config.getNeedComparedColumnForSheet(mapping.getSheet1Name(), compareType);
                    }
                    logger.info(comparedColumns.toString());
                    int duplicateNotFoundErrorCount = 0;
                    int duplicateNotMatchErrorCount = 0;
                    Iterator<Map.Entry<String, Row>> rows1Iter = rows1.entrySet().iterator();
                    while (rows1Iter.hasNext()) {
                        Map.Entry<String, Row> entry = rows1Iter.next();
                        String row1Key = entry.getKey();
                        row1Key = FinancialFile.convertKey(row1Key);
                        Row row1 = entry.getValue();
                        Row row2 = rows2.get(row1Key);
                        if(row2 == null) {
                            // not found
                            resultFile.info(row1Key, " not found in file " + new File(toFile).getName(), String.valueOf(row1.getRowNum() + 1), "Not Found");
                            summary.plusNotFoundErrorRow();
                            if (MM.equals(compareType) && MrtFile.isSeperatedKey(mapping.getSheet1Name(), row1Key)) {
                            	duplicateNotFoundErrorCount++;
                            }
                            logger.info(row1Key + " not found in file " + new File(toFile).getName() + " at row " + String.valueOf(row1.getRowNum() + 1));
                        } else {
                            int errorColumnCount = 0;
                            for(ColumnMapping column : comparedColumns) {
                                if(column.isColumnNeedCompare()) {
                                    int column1index = file1.getColumnIndex(column.getSheet1Name(), column.getSheet1ColumnName());
                                    int column2index = file2.getColumnIndex(column.getSheet2Name(), column.getSheet2ColumnName());
                                    String cellValue1 = file1.getCellValue(row1.getCell(column1index));
                                    String cellValue2 = file2.getCellValue(row2.getCell(column2index));
                                    if(!cellValue1.equals(cellValue2)) {
                                        /**
                                         * Special case
                                         * 1. USD VS USD-U for Currency
                                         * 2. "" VS 0.000000
                                         * 3. Feature Long Description
                                         * 4. Unit Cost, SERVICE_MONTHLY_COST, ONE_TIME_CHARGE
                                         */
                                        if("Currency".equals(column.getSheet1ColumnName()) && cellValue2.equals(cellValue1 + "-U")) {
                                            continue;
                                        }
                                        if(("0.000000".equals(cellValue1) || "".equals(cellValue1)) && ("0.000000".equals(cellValue2) || "".equals(cellValue2))) {
                                            continue;
                                        }
                                        if(FM.equals(compareType) && "Feature Long Description".equals(column.getSheet1ColumnName()) && cellValue2.contains(cellValue1)) {
                                            continue;
                                        }
                                        if(FM.equals(compareType) && "Unit Cost".equals(column.getSheet1ColumnName())) {
                                            String featureCodeType = file1.getCellValue(row1.getCell(file1.getColumnIndex(column.getSheet1Name(), "Feature Code Type")));
                                            if("Monthly Recurring Charge".equals(featureCodeType)) {
                                                if(!"SERVICE_MONTHLY_COST".equals(column.getSheet2ColumnName())) {
                                                    continue;
                                                }
                                            } else if(!"ONE_TIME_CHARGE".equals(column.getSheet2ColumnName())) {
                                                continue;
                                            }
                                        }
                                        errorColumnCount++;
                                        resultFile.info(row1Key, String.format("Column[%s] value[%s] was updated to column[%s] value[%s]", column.getSheet1ColumnName(), cellValue1, column.getSheet2ColumnName(), cellValue2), String.valueOf(row1.getRowNum() + 1), "Not Match");
                                        logger.info(String.format("Column[%s] value[%s] was updated to column[%s] value[%s]", column.getSheet1ColumnName(), cellValue1, column.getSheet2ColumnName(), cellValue2));
                                    }
                                }
                            }
                            if(errorColumnCount > 0) {
                            	summary.plusNotMatchErrorRow();
                            	if (MM.equals(compareType) && MrtFile.isSeperatedKey(mapping.getSheet1Name(), row1Key)) {
                                	duplicateNotMatchErrorCount++;
                                }
                            }
                        }
                    }
//                    summary.minusNotFoundErrorRow(duplicateNotFoundErrorCount/2);
                    summary.minusNotMatchErrorRow(duplicateNotMatchErrorCount/2);
                    resultFile.setSheetCompareResult(summary);
                }
            }
        } catch (Exception e) {
            MessageDisplay.show(e.getMessage());
            logger.error(e);
            e.printStackTrace();
        } finally {
            resultFile.write();
        }
    }
}
