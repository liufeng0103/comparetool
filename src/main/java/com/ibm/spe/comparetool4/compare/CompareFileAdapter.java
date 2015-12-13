package com.ibm.spe.comparetool4.compare;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

import com.ibm.spe.comparetool4.SpeProperties;
import com.ibm.spe.comparetool4.gui.MessageDisplay;

/**
 * Created by luis on 2015/2/6.
 */
public abstract class CompareFileAdapter implements CompareFile {
    private static Log logger = LogFactory.getLog(CompareFileAdapter.class);

    private static final int FILE_BIG_SIZE = Integer.parseInt(SpeProperties.getBigFileSize());    

    @Override
    public String getCellValue(Cell cell) {
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
                    String errorMessage = String.format("Cell Type not correct :%s", cell.getCellType());
                    logger.error(errorMessage);
                    MessageDisplay.show(errorMessage);
            }
        }
        return value;
    }

    boolean isBigFile(File file) {
        String fileName = file.getName();
        long size = file.length();
        if(".xlsx".equalsIgnoreCase(fileName.substring(fileName.length()-5)) && size > FILE_BIG_SIZE) {
            return true;
        }
        return false;
    }
}
