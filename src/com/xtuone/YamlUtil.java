package com.xtuone;

import java.io.File;
import java.io.FileNotFoundException;

import org.ho.yaml.Yaml;

import com.xtuone.yaml.Config;

public class YamlUtil {
	
	public static Config readConfigFile(){
		Config config = null;
		 File dumpFile = new File("config2.yaml");  
		 try {
			config = (Config) Yaml.loadType(dumpFile, Config.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		 return config;
	}

}
