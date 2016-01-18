package com.xtuone.entity;

import java.util.List;

public class BaseSource {
	
	private String packageName;
	
	private  List<String> importList;
	
	private String className;
	
	private String superName;
	
	private String interfaceName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> getImportList() {
		return importList;
	}

	public void setImportList(List<String> importList) {
		this.importList = importList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSuperName() {
		return superName;
	}

	public void setSuperName(String superName) {
		this.superName = superName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Override
	public String toString() {
		return "BaseSource [packageName=" + packageName + ", importList="
				+ importList + ", className=" + className + ", superName="
				+ superName + ", interfaceName=" + interfaceName + "]";
	}
	
	

}
