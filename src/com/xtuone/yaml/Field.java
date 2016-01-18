package com.xtuone.yaml;

public class Field {
	
	//�������
	private String propertyName;
	
	//��Ӧ���ݿ�����
	private String tableColumnName;
	
	//����
	private String typeClass;
	
	private String fieldNote;//�ֶε�ע��

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
