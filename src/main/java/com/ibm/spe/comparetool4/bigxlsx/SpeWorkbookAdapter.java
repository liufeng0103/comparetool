package com.ibm.spe.comparetool4.bigxlsx;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 用于屏蔽不用的方法
 * @author Luis
 */
public abstract class SpeWorkbookAdapter implements Workbook {

	@Override
	public int addPicture(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addToolPack(UDFFinder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sheet cloneSheet(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CellStyle createCellStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataFormat createDataFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font createFont() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Name createName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sheet createSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font findFont(short arg0, short arg1, short arg2, String arg3,
			boolean arg4, boolean arg5, short arg6, byte arg7) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getActiveSheetIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<? extends PictureData> getAllPictures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CellStyle getCellStyleAt(short arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreationHelper getCreationHelper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFirstVisibleTab() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Font getFontAt(short arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getForceFormulaRecalculation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MissingCellPolicy getMissingCellPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Name getName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Name getNameAt(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNameIndex(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getNumCellStyles() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getNumberOfFonts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfNames() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfSheets() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPrintArea(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sheet getSheetAt(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSheetIndex(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSheetIndex(Sheet arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSheetName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSheetHidden(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSheetVeryHidden(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeName(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeName(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePrintArea(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSheetAt(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActiveSheet(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFirstVisibleTab(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setForceFormulaRecalculation(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMissingCellPolicy(MissingCellPolicy arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPrintArea(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPrintArea(int arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRepeatingRowsAndColumns(int arg0, int arg1, int arg2,
			int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedTab(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSheetHidden(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSheetHidden(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSheetName(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSheetOrder(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(OutputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
