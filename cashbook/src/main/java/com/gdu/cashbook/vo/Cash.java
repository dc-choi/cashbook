package com.gdu.cashbook.vo;

public class Cash {
	private int cashNo;
	private String categoryName;
	private String memberId;
	private String cashKind;
	private int cashPrice;
	private String cashPlace;
	private String cashMemo;
	private String cashDate;
	
	public int getCashNo() {
		return cashNo;
	}
	public void setCashNo(int cashNo) {
		this.cashNo = cashNo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCashKind() {
		return cashKind;
	}
	public void setCashKind(String cashKind) {
		this.cashKind = cashKind;
	}
	public int getCashPrice() {
		return cashPrice;
	}
	public void setCashPrice(int cashPrice) {
		this.cashPrice = cashPrice;
	}
	public String getCashPlace() {
		return cashPlace;
	}
	public void setCashPlace(String cashPlace) {
		this.cashPlace = cashPlace;
	}
	public String getCashMemo() {
		return cashMemo;
	}
	public void setCashMemo(String cashMemmo) {
		this.cashMemo = cashMemmo;
	}
	public String getCashDate() {
		return cashDate;
	}
	public void setCashDate(String cashDate) {
		this.cashDate = cashDate;
	}
	
	@Override
	public String toString() {
		return "Cash [cashNo=" + cashNo + ", categoryName=" + categoryName + ", memberId=" + memberId + ", cashKind="
				+ cashKind + ", cashPrice=" + cashPrice + ", cashPlace=" + cashPlace + ", cashMemo=" + cashMemo
				+ ", cashDate=" + cashDate + "]";
	}
}
