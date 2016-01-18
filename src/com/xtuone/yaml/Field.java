package com.xtuone.yaml;

public class Field {
	
	//类参数名
	private String propertyName;
	
	//对应数据库名称
	private String tableColumnName;
	
	//类型
	private String typeClass;
	
	private String fieldNote;//字段的注释

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getTableColumnName() {
		return tableColumnName;
	}

	public void setTableColumnName(String tableColumnName) {
		this.tableColumnName = tableColumnName;
	}

	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	public String getFieldNote() {
		return fieldNote;
	}

	public void setFieldNote(String fieldNote) {
		this.fieldNote = fieldNote;
	}

	@Override
	public String toString() {
		return "Field [propertyName=" + propertyName + ", tableColumnName="
				+ tableColumnName + ", typeClass=" + typeClass + ", fieldNote="
				+ fieldNote + "]";
	}
	
	

}
