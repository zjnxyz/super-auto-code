package com.xtuone.yaml;

import java.util.Arrays;

public class Config {
	
	private String prePackage;
	
	private boolean writeReadSep;
	
	private boolean supportRedis;
	
	//是否为 服务提供方
	private boolean provider;
	
	private EntityClass [] entityClasses;

	public String getPrePackage() {
		return prePackage;
	}

	public void setPrePackage(String prePackage) {
		this.prePackage = prePackage;
	}

	public boolean isWriteReadSep() {
		return writeReadSep;
	}

	public void setWriteReadSep(boolean writeReadSep) {
		this.writeReadSep = writeReadSep;
	}

	public boolean isSupportRedis() {
		return supportRedis;
	}

	public void setSupportRedis(boolean supportRedis) {
		this.supportRedis = supportRedis;
	}

	public EntityClass[] getEntityClasses() {
		return entityClasses;
	}

	public void setEntityClasses(EntityClass[] entityClasses) {
		this.entityClasses = entityClasses;
	}

	public boolean isProvider() {
		return provider;
	}

	public void setProvider(boolean provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "Config [prePackage=" + prePackage + ", writeReadSep="
				+ writeReadSep + ", supportRedis=" + supportRedis
				+ ", entityClasses=" + Arrays.toString(entityClasses) + "]";
	}
	
	
	

}
