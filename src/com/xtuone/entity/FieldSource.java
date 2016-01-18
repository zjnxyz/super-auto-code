package com.xtuone.entity;

/**
 * ʵ�����ж�Ӧ������
 * @author BravoZu
 *
 */
public class FieldSource {

	private String columnName;//�ֶ������ݱ��е�����
	
	private String fieldName;//�ֶ�����
	
	private String setterMethodName;//�ֶε�getter��setter��Ӧ������
	
	private String getterMethodName;//�ֶε�getter��setter��Ӧ������
	
	//����ֵ���߲���������
	private String typeClass;
	
	private String fieldNote;//�ֶε�ע��

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
