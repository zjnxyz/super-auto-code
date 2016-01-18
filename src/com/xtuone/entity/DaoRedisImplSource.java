package com.xtuone.entity;

/**
 * dao-redis source
 * @author BravoZu
 *
 */
public class DaoRedisImplSource extends BaseSource{

	private String mapperClassName;
	
	//@Component 注解
	private String annoName;

	public String getMapperClassName() {
		return mapperClassName;
	}

	public void setMapperClassName(String mapperClassName) {
		this.mapperClassName = mapperClassName;
	}
	
	public String getAnnoName() {
		return annoName;
	}

	public void setAnnoName(String annoName) {
		this.annoName = annoName;
	}
	
	
	
}
