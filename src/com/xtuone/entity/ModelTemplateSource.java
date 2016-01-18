package com.xtuone.entity;

import java.util.List;

/**
 * @author BravoZu
 *
 */
public class ModelTemplateSource extends BaseSource {
	
	private String tableName;
	
	private List<FieldSource> fieldSources;

	public List<FieldSource> getFieldSources() {
		return fieldSources;
	}

	public void setFieldSources(List<FieldSource> fieldSources) {
		this.fieldSources = fieldSources;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "ModelTemplateSource [tableName=" + tableName
				+ ", fieldSources=" + fieldSources + "]";
	}
	
	

}
