package com.gdu.cashbook.vo;

public class Category {
	private String categoryName;
	private String categoryDesc;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	
	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", categoryDesc=" + categoryDesc + "]";
	}
}
