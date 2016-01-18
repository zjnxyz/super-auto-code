package com.xtuone.entity;

import java.util.List;

/**
 * model_redis 生成实体类
 * @author BravoZu
 *
 */
public class ModelRedisSource extends BaseSource{

	private List<FieldSource> fieldSources;

	public List<FieldSource> getFieldSources() {
		return fieldSources;
	}

	public void setFieldSources(List<FieldSource> fieldSources) {
		this.fieldSources = fieldSources;
	}

	@Override
	public String toString() {
		return "ModelRedisSource [fieldSources=" + fieldSources + "]";
	}
	
	
}
