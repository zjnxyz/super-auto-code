package com.xtuone.template;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateModel;
import freemarker.template.Version;

public class Environment {
	
	private Configuration cfg;
	private String templateDirectory;
	private Map<String, TemplateModel> variableMap = null;
	
	public Environment(Configuration cfg){
		this.cfg = cfg;
	}
	
	public Environment(Version version){
		this.cfg = new Configuration(version);
	}
	
	public Environment(Configuration cfg, String templateDirectory){
		this.cfg = cfg;
		this.templateDirectory = templateDirectory;
	}
	
	public Environment(Version version, String templateDirectory){
		this.cfg = new Configuration(version);
		this.templateDirectory = templateDirectory;
	}
	
	protected void initEnvironment() throws Exception{
		try {  
            // 指定模板文件从何处加载的数据源，这里设置成一个文件目录。
            loadSharedVariables();
            String res = checkDirectory();
            if(res != null){
            	throw new Exception(res);
            }
            cfg.setDirectoryForTemplateLoading(new File(templateDirectory));
        } catch (Exception e) {  
        	throw new Exception(e);
        } 
	}
	
	private String checkDirectory(){
		String res = null;
		if(StringUtils.isBlank(templateDirectory)){
			res = "模板目录为空";
        	return res;
        }
        File file = new File(templateDirectory);
        if(!file.exists()){
        	res = "找不到目录："+templateDirectory;
        	return res;
        }
        if(!file.isDirectory()){
        	res = "该路径不是一个目录："+templateDirectory;
        	return res;
        }
        return res;
	}
	
	private void loadSharedVariables(){
		if(variableMap != null && variableMap.size() >= 0){
			for (Entry<String, TemplateModel> entry : variableMap.entrySet()) {
				cfg.setSharedVariable(entry.getKey(), entry.getValue());
			}
		}
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public Configuration getCfg() {
		return cfg;
	}

	public Map<String, TemplateModel> getVariableMap() {
		return variableMap;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}

	public void setVariableMap(Map<String, TemplateModel> variableMap) {
		this.variableMap = variableMap;
	}

}
