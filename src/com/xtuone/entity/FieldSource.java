package com.xtuone.entity;

/**
 * 实体类中对应的属性
 * @author BravoZu
 *
 */
public class FieldSource {

	private String columnName;//字段在数据表中的名字
	
	private String fieldName;//字段名字
	
	private String setterMethodName;//字段的getter和setter对应的名字
	
	private String getterMethodName;//字段的getter和setter对应的名字
	
	//返回值或者参数的类型
	private String typeClass;
	
	private String fieldNote;//字段的注释

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getSetterMethodName() {
		return setterMethodName;
	}

	public void setSetterMethodName(String setterMethodName) {
		this.setterMethodName = setterMethodName;
	}

	public String getGetterMethodName() {
		return getterMethodName;
	}

	public void setGetterMethodName(String getterMethodName) {
		this.getterMethodName = getterMethodName;
	}

	public String getFieldNote() {
		return fieldNote;
	}

	public void setFieldNote(String fieldNote) {
		this.fieldNote = fieldNote;
	}

	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	@Override
	public String toString() {
		return "FieldSource [columnName=" + columnName + ", fieldName="
				+ fieldName + ", setterMethodName=" + setterMethodName
				+ ", getterMethodName=" + getterMethodName + ", typeClass="
				+ typeClass + ", fieldNote=" + fieldNote + "]";
	}
	
	
	
}
