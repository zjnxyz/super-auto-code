package com.xtuone;

import java.util.List;

import com.xtuone.entity.EntitySource;
import com.xtuone.yaml.Config;

public class Generator {
	
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Config config = YamlUtil.readConfigFile();
		
		if(config == null){
			throw new Exception("config.yaml");
		}
		
		GeneratorControl generatorControl = new GeneratorControl(config);
		
		List<EntitySource> entitySources = generatorControl.createSource();
		
		generatorControl.processMore(entitySources);
		
		
	}

}
