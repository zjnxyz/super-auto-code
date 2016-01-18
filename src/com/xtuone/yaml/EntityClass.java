package com.xtuone.yaml;

import java.util.Arrays;

public class EntityClass {
	
	private String curPackageName;
	
	private String className;
	
	private String tableName;
	
	private Field[] field;

	public String getCurPackageName() {
		return curPackageName;
	}

	public void setCurPackageName(String curPackageName) {
		this.curPackageName = curPackageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Field[] getField() {
		return field;
	}

	public void setField(Field[] field) {
		this.field = field;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "EntityClass [curPackageName=" + curPackageName + ", className="
				+ className + ", tableName=" + tableName + ", field="
				+ Arrays.toString(field) + "]";
	}
	
	

}
