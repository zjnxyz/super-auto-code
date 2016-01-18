package com.xtuone.test;

import java.io.File;
import java.io.FileNotFoundException;

import org.ho.yaml.Yaml;

import com.xtuone.yaml.Config;
import com.xtuone.yaml.EntityClass;
import com.xtuone.yaml.Field;
import com.xtuone.yaml.Person;

public class Main {
	
//	public static void main(String[] args) {
//		 	Person michael = new Person();  
//	        Person floveria = new Person();  
//	        Person[] children = new Person[2];  
//	        children[0] = new Person();  
//	        children[1] = new Person();  
//	        
//	        michael.setName("Michael Corleone");  
//	        michael.setAge(24);  
//	        floveria.setName("Floveria Edie");  
//	        floveria.setAge(24);  
//	        children[0].setName("boy");  
//	        children[0].setAge(3);  
//	        children[1].setName("girl");  
//	        children[1].setAge(1);  
//	        
//	        michael.setSpouse(floveria);  
//	        floveria.setSpouse(michael);  
//	          
//	        michael.setChildren(children);  
//	        floveria.setChildren(children);  
//	          
//	        /* Export data to a YAML file. */  
//	        File dumpFile = new File("testYaml.yaml");  
//	        try {  
//	            Yaml.dump(michael, dumpFile);  
//	        } catch (FileNotFoundException e) {  
//	            e.printStackTrace();  
//	        }  
//	}
	
//	public static void main(String[] args) {
//		try {  
//			 File dumpFile = new File("testYaml.yaml");  
//			 File dumpFile2 = new File("testYaml2.yaml");  
//		    Person corleone = (Person) Yaml.loadType(dumpFile, Person.class);  
//		    Person corleone2 = (Person) Yaml.loadType(dumpFile2, Person.class);  
//		    output(corleone);  
//		    System.out.println();  
//		    output(corleone2);  
//		} catch (FileNotFoundException e) {  
//		    e.printStackTrace();  
//		} 
//	}
//
//	private static void output(Person person) {  
//	    System.out.println("Name: " + person.getName());  
//	    System.out.println("Age: " + person.getAge());  
//	    System.out.println("Spouse: " + person.getSpouse().getName());  
//	    System.out.println("Children: " + person.getChildren()[0].getName() +   
//	        ", " + person.getChildren()[1].getName());  
//	} 
	
	public static void main(String[] args) {
			try {  
				 File dumpFile = new File("config.yaml");  
				 File dumpFile2 = new File("config2.yaml");  
			    Config corleone = (Config) Yaml.loadType(dumpFile, Config.class);  
			    Config corleone2 = (Config) Yaml.loadType(dumpFile2, Config.class);  
			    System.out.println("corleone:"+corleone);
//			    output(corleone);  
			    System.out.println("-------------------------------");  
			    System.out.println("corleone2:"+corleone2);
//			    output(corleone2);  
			} catch (FileNotFoundException e) {  
			    e.printStackTrace();  
			} 
	}
	
//	public static void main(String[] args) {
//		
//		Field field = new Field();
//		field.setPropertyName("key");
//		field.setTableColumnName("k_e_y");
//		field.setTypeClass("com.x.t");
//		
//		Field field2 = new Field();
//		field2.setPropertyName("key2");
//		field2.setTableColumnName("k_e_y2");
//		field2.setTypeClass("com.x.t2");
//		
//		Field field3 = new Field();
//		field3.setPropertyName("key3");
//		field3.setTableColumnName("k_e_y3");
//		field3.setTypeClass("com.x.t3");
//		
//		Field field4 = new Field();
//		field4.setPropertyName("key4");
//		field4.setTableColumnName("k_e_y4");
//		field4.setTypeClass("com.x.t4");
//		
//		
//		EntityClass entityClass1 = new EntityClass();
//		entityClass1.setClassName("hello");
//		entityClass1.setCurPackageName("thello");
//		
//		Field [] f1 = new Field [2];
//		f1[0] = field;
//		
//		f1[1] = field3;
//		entityClass1.setField(f1);
//		
//	
//		EntityClass entityClass2 = new EntityClass();
//		entityClass2.setClassName("word");
//		entityClass2.setCurPackageName("tword");
//		
//		Field [] f2 = new Field [3];
//		f2[0] = field2;
//		
//		f2[1] = field4;
//		
//		f2[2] = field;
//		entityClass2.setField(f2);
//		
//		
//		Config config = new Config();
//		config.setPrePackage("cn.xsuper.treehole.service");
//		config.setSupportRedis(true);
//		config.setWriteReadSep(true);
//		
//		EntityClass [] entityClasses = new EntityClass [2];
//		entityClasses[0] = entityClass1;
//		entityClasses[1] = entityClass2;
//		config.setEntityClasses(entityClasses);
//		
//		 File dumpFile = new File("config.yaml");  
//	        try {  
//	            Yaml.dump(config, dumpFile);  
//	        } catch (FileNotFoundException e) {  
//	            e.printStackTrace();  
//	        }  
//		
//	}
}
