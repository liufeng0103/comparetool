package com.ibm.spe.comparetool4.compare;

public class CompareSummary {
	private int totalRowCount;
	private int notFoundErrorRowCount = 0;
	private int notMatchErrorRowCount = 0;
	
	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}
	
	public int getTotalCorrectRowCount() {
		return totalRowCount - notFoundErrorRowCount - notMatchErrorRowCount;
	}

	public int getTotalErrorRowCount() {		
		return notFoundErrorRowCount + notMatchErrorRowCount;
	}

	public int getNotFoundErrorRowCount() {
		return notFoundErrorRowCount;
	}

	public int getNotMatchErrorRowCount() {
		return notMatchErrorRowCount;
	}
	
	public void plusNotFoundErrorRow() {
		notFoundErrorRowCount++;
	}
	
	public void plusNotMatchErrorRow() {
		notMatchErrorRowCount++;
	}
	
	public void minusNotFoundErrorRow(int minusCount) {
		notFoundErrorRowCount -= minusCount;
	}
	
	public void minusNotMatchErrorRow(int minusCount) {
		notMatchErrorRowCount -= minusCount;
	}
	
}
