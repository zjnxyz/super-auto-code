package com.xtuone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.xtuone.entity.BaseSource;
import com.xtuone.entity.DaoReadRepositorySource;
import com.xtuone.entity.DaoRedisImplSource;
import com.xtuone.entity.DaoRedisSource;
import com.xtuone.entity.DaoRepositorySource;
import com.xtuone.entity.DaoWriteRepositorySource;
import com.xtuone.entity.EntitySource;
import com.xtuone.entity.FieldSource;
import com.xtuone.entity.MapperRedisSource;
import com.xtuone.entity.ModelRedisSource;
import com.xtuone.entity.ModelTemplateSource;
import com.xtuone.entity.ServiceDubboRemoteSource;
import com.xtuone.entity.ServiceDubboSource;
import com.xtuone.entity.ServiceImplSource;
import com.xtuone.entity.ServiceSource;
import com.xtuone.util.Constant;
import com.xtuone.yaml.Config;
import com.xtuone.yaml.EntityClass;
import com.xtuone.yaml.Field;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**װ
 * @author BravoZu
 *
 */
public class GeneratorControl {
	
	private Config config;
	
	//模块的根路径
	private String MODULES_PACKAGE_PATH = "";
	
	//全局变量的根路径
	private String GLOBAL_PACKAGE_PATH = "";
	
	private String REMOTE_PACKAGE_PATH = "";
	
	public GeneratorControl(Config config){
		this.config = config;
		MODULES_PACKAGE_PATH = config.getPrePackage()+"."+Constant.MODULES_PACKAGE_NAME;
		GLOBAL_PACKAGE_PATH = config.getPrePackage()+"."+Constant.GLOBAL_PACKAGE_NAME;
		REMOTE_PACKAGE_PATH = config.getPrePackage()+"."+Constant.REMOTE_PACKAGE_NAME;
	}
	
	/**
	 * @return
	 */
	public List<EntitySource> createSource(){
		
		List<EntitySource> entitySources = new ArrayList<EntitySource>();
		 
		StringBuffer packageBuffer = new StringBuffer();
		packageBuffer.append(config.getPrePackage()).append(".").append(Constant.MODULES_PACKAGE_NAME);
		EntityClass[] entityClasses = config.getEntityClasses();
		for( EntityClass entityClass:entityClasses){
			entitySources.add(resolveEntityClass(entityClass, config.isWriteReadSep(), config.isSupportRedis(), config.isProvider() ));
		}
		return entitySources;
	}
	
	
	private EntitySource resolveEntityClass(EntityClass entityClass,
			boolean writeReadSep, boolean supportRedis, boolean provider){
		
		
		EntitySource entitySource = new EntitySource();
		String cp = entityClass.getCurPackageName();
		System.out.println("---cp---"+cp);
		if(StringUtils.isEmpty( cp )){
			cp = entityClass.getClassName().toLowerCase();
		}

		String modulePackage = MODULES_PACKAGE_PATH+"."+cp; 
		
		//model-mysql
		entitySource.setModelTemplateSource( readMysqlModelDataSource( entityClass.getClassName(), modulePackage,
				entityClass.getTableName(), entityClass.getField() ) );
		
		if(supportRedis){
			//model-redis
			entitySource.setModelRedisSource(readRedisModelDataSource(entityClass.getClassName(), modulePackage, entityClass.getField()));
			//mapper-redis
			entitySource.setMapperRedisSource(readRedisMapperModelDataSource(entityClass.getClassName(), modulePackage));
			entitySource.setDaoRedisSource( readDaoRedisDataSource(entityClass.getClassName(), modulePackage) );
			entitySource.setDaoRedisImplSource(readDaoRedisImplDataSource(entityClass.getClassName(), modulePackage));
		}
		
		if(writeReadSep){
			//读写分离
			entitySource.setDaoReadRepositorySource(readOlnyReadRepositoryDataSource(entityClass.getClassName(), modulePackage));
			entitySource.setDaoWriteRepositorySource(readOlnyWriteRepositoryDataSource(entityClass.getClassName(), modulePackage));
		}else{
			//不分离
			entitySource.setDaoRepositorySource(readRepositoryDataSource(entityClass.getClassName(), modulePackage));
		}
		if(provider){
			//dubbo
			entitySource.setServiceDubboRemoteSource(readDubboServiceRemoteSource(entityClass.getClassName()));
			entitySource.setServiceDubboSource(readDubboServiceSource(entityClass.getClassName(), modulePackage));
			entitySource.setServiceImplSource(readServiceImplSource(entityClass.getClassName(), modulePackage, 2));
		}else{
			entitySource.setServiceSource( readServiceSource(entityClass.getClassName(), modulePackage) );
			entitySource.setServiceImplSource(readServiceImplSource(entityClass.getClassName(), modulePackage, 1));
		}
		
		return entitySource;
	}
	
	
	private ServiceImplSource readServiceImplSource(String className, String modulePackage, int type){
		ServiceImplSource serviceImplSource = new ServiceImplSource();
		
		List<String> importList = new ArrayList<String>();
		
		String interfaceName = getDubboServiceClassName(className);
		serviceImplSource.setInterfaceName(interfaceName);
		importList.add(getDubboServicePackagePath(modulePackage, className));
		
		String cn = getServiceImplClassName(className);
		serviceImplSource.setClassName(cn);
		serviceImplSource.setPackageName( getServiceImplPackageName(modulePackage) );
		
		//设置annon
		if(type == 1){
			//普通项目
			serviceImplSource.setAnnoName(getAnnoName(className));
		}else if( type == 2){
			//dubbo项目
			serviceImplSource.setAnnoName(getDubboAnnoName(className));
		}
		serviceImplSource.setImportList(importList);
		return serviceImplSource;
	}
	
	private String getDubboAnnoName(String className){
		return lowerIndexChar(className+"DubboService");
	}
	
	private String getAnnoName(String className){
		return lowerIndexChar(className+"Service");
	}
	
	private String getServiceImplClassName(String className){
		return className+"ServiceImpl";
	}
	
	private String getServiceImplPackageName(String modulePackage){
		return modulePackage+".service.impl";
	}
	
	private ServiceSource readServiceSource(String className, String modulePackage){
		ServiceSource serviceSource = new ServiceSource();
		serviceSource.setPackageName(getDubboServicePackageName(modulePackage));
		serviceSource.setClassName(getDubboServiceClassName(className));
		return serviceSource;
	}
	
	private ServiceDubboSource readDubboServiceSource(String className, String modulePackage){
		ServiceDubboSource serviceDubboSource = new ServiceDubboSource();
		List<String> importList = new ArrayList<String>();
		String superName = getRemoteClassName(className);
		serviceDubboSource.setSuperName(superName);
		importList.add(getRemotePackagePath(className));
		
		String packageName = getDubboServicePackageName(modulePackage);
		serviceDubboSource.setPackageName(packageName);
		className = getDubboServiceClassName(className);
		serviceDubboSource.setClassName(className);
		
		serviceDubboSource.setImportList(importList);
		return serviceDubboSource;
	}
	
	private String getDubboServiceClassName(String className){
		return "I"+className+"Service";
	}
	
	private String getDubboServicePackageName(String modulePackage){
		return modulePackage+".service";
	}
	
	private String getDubboServicePackagePath( String modulePackage, String className){
		return getDubboServicePackageName(modulePackage)+"."+getDubboServiceClassName(className);
	}
	
	
	private ServiceDubboRemoteSource readDubboServiceRemoteSource(String className){
		
		ServiceDubboRemoteSource serviceDubboRemoteSource = new ServiceDubboRemoteSource();
		String packageName = getServiceRemotePackageName();
		serviceDubboRemoteSource.setPackageName(packageName);
		
		className = getRemoteClassName(className);
		serviceDubboRemoteSource.setClassName(className);
		
		return serviceDubboRemoteSource;
	}
	
	private String getRemotePackagePath(String className){
		return getServiceRemotePackageName()+"."+getRemoteClassName(className);
	}
	
	private String getRemoteClassName(String className){
		return "I"+className+"DubboService";
	}
	private String getServiceRemotePackageName(){
		return REMOTE_PACKAGE_PATH+".service";
	}
	
	/**
	 * 只写 Repository数据源
	 * @param className
	 * @param modulePackage
	 * @return
	 */
	private DaoWriteRepositorySource readOlnyWriteRepositoryDataSource(String className,String modulePackage){
		DaoRepositorySource daoRepositorySource = readBaseRepositoryDataSource(className, modulePackage, 2);
		DaoWriteRepositorySource repositorySource = new DaoWriteRepositorySource();
		repositorySource.setClassName(daoRepositorySource.getClassName());
		repositorySource.setSuperName(daoRepositorySource.getSuperName());
		repositorySource.setImportList(daoRepositorySource.getImportList());
		repositorySource.setPackageName(daoRepositorySource.getPackageName());
		
		return repositorySource;
	}
	
	/**
	 * 只读 Repository数据源
	 * @param className
	 * @param modulePackage
	 * @return
	 */
	private DaoReadRepositorySource readOlnyReadRepositoryDataSource(String className,String modulePackage){
		DaoRepositorySource daoRepositorySource = readBaseRepositoryDataSource(className, modulePackage, 1);
		DaoReadRepositorySource repositorySource = new DaoReadRepositorySource();
		repositorySource.setClassName(daoRepositorySource.getClassName());
		repositorySource.setSuperName(daoRepositorySource.getSuperName());
		repositorySource.setImportList(daoRepositorySource.getImportList());
		repositorySource.setPackageName(daoRepositorySource.getPackageName());
		
		return repositorySource;
	}
	
	
	
	/**
	 * 组装读写部分的Repository数据源
	 * @param className
	 * @param modulePackage
	 * @return
	 */
	private DaoRepositorySource readRepositoryDataSource(String className,String modulePackage){
		return readBaseRepositoryDataSource(className, modulePackage, 0);
	}
	
	private DaoRepositorySource readBaseRepositoryDataSource(String className,String modulePackage, int type){
		DaoRepositorySource daoRepositorySource = new DaoRepositorySource();
		List<String> importList = new ArrayList<String>();

		importList.add(getBaseRepositoryImport(type));
		//对应实体类
		String entityPackage = getModelMysqlPath(modulePackage, className);
		importList.add(entityPackage);
		daoRepositorySource.setImportList(importList);
		//设置superName
		daoRepositorySource.setSuperName(className);
		
		//设置Repository中的class
		daoRepositorySource.setClassName(getRepositoryClassName(type, className));
		
		//这只package
		daoRepositorySource.setPackageName(getRepositoryPackage(type, modulePackage));
		
		return daoRepositorySource;
	}
	
	/**
	 * 读取 dao-redis 中的数据源
	 * @param className
	 * @param modulePackage
	 * @return
	 */
	private DaoRedisSource readDaoRedisDataSource(String className,String modulePackage){
		DaoRedisSource daoRedisSource = new DaoRedisSource();
		List<String> importList = new ArrayList<String>();
		
		String superName = getModelRedis(className);
		daoRedisSource.setSuperName(superName);
		String superNamePackage = getModelRedisPath(modulePackage, className);
		importList.add(superNamePackage);
		
		daoRedisSource.setClassName(getDaoRedisClassName(className));
		daoRedisSource.setPackageName(getDaoRedisPackageName(modulePackage));
		
		daoRedisSource.setImportList(importList);
		return daoRedisSource;
	}
	
	/**
	 * 读取 dao-redis-impl 中的数据源
	 * @param className
	 * @param modulePackage
	 * @return
	 */
	private DaoRedisImplSource readDaoRedisImplDataSource(String className,String modulePackage){
		DaoRedisImplSource daoRedisImplSource = new DaoRedisImplSource();
		List<String> importList = new ArrayList<String>();
		String superName = getModelRedis(className);
		daoRedisImplSource.setSuperName(superName);
		
		String superNamePackage = getModelRedisPath(modulePackage, className);
		importList.add(superNamePackage);
		
		String interfaceName = getDaoRedisClassName(className);
		daoRedisImplSource.setInterfaceName(interfaceName);
		
		String interfaceNamePackage = getDaoRedisPackagePath(modulePackage, className);
		importList.add(interfaceNamePackage);
		
		//这是mapper数据
		String mapperClassName = getMapperClassName(className);
		daoRedisImplSource.setMapperClassName(mapperClassName);
		
		//设置packagePath
		importList.add(getMapperPackagePath(modulePackage, className));
		
		//设置annon
		daoRedisImplSource.setAnnoName(lowerIndexChar( trimIndex(interfaceName) ));
		
		//设置包名
		daoRedisImplSource.setPackageName(getDaoRedisImplPackageName(modulePackage));
		//设置类名
		daoRedisImplSource.setClassName(getDaoRedisImplClassName(className));
		
		daoRedisImplSource.setImportList(importList);
		return daoRedisImplSource;
	}
	
	private String getDaoRedisImplPackageName(String modulePackage){
		return getDaoRedisPackageName(modulePackage)+".impl";
	}
	
	private String getDaoRedisImplClassName( String className ){
		return className+"RedisDaoImpl";
	}
	
	private String getDaoRedisImplPackagePath(String modulePackage, String className){
		return getDaoRedisImplPackageName(modulePackage)+"."+getDaoRedisImplClassName(className);
	}
	
	private String trimIndex(String str){
		return str.substring(1);
	}
	
	
	private String getDaoRedisClassName(String className){
		return "I"+getModelRedis(className)+"Dao";
	}
	
	/**
	 * 获取对应类所在的classpath
	 * @param modulePackage
	 * @param className
	 * @return
	 */
	private String getDaoRedisPackagePath(String modulePackage, String className){
		return getDaoRedisPackageName(modulePackage)+"."+getDaoRedisClassName(className);
	}
	
	/**
	 * 获取类所在的包名
	 * @param modulePackage
	 * @return
	 */
	private String getDaoRedisPackageName(String modulePackage){
		return modulePackage+".dao.redis";
	}
	
	
	/**
	 * 获取Repository导入的base类
	 * @param type
	 * @return
	 */
	private String getBaseRepositoryImport(int type){
		String importClass = "";
		switch (type) {
			case 0:
				//读写不分离
				importClass = GLOBAL_PACKAGE_PATH+"."+Constant.REPOSITORY_PATH+".BaseRepository";
				break;
			case 1:
				//只读
				importClass = GLOBAL_PACKAGE_PATH+"."+Constant.REPOSITORY_PATH+".BaseOnlyReadRepository";
				break;
			case 2:
				//只写
				importClass = GLOBAL_PACKAGE_PATH+"."+Constant.REPOSITORY_PATH+".BaseOnlyWriteRepository";
				break;
			default:
				break;
		}
		return importClass;
	}
	
	/**
	 * 获取Repository的className
	 * @param type
	 * @param className
	 * @return
	 */
	private String getRepositoryClassName(int type, String className){

		switch (type) {
			case 0:
				//读写不分离
				className = className+"Repository";
				break;
			case 1:
				//只读
				className = className+"ReadRepository";
				break;
			case 2:
				//只写
				className = className+"WriteRepository";
				break;
			default:
				break;
		}
		
		return className;
	}
	

	/**
	 * 获取Repository的package
	 * @param type
	 * @param className
	 * @return
	 */
	private String getRepositoryPackage(int type, String modulePackage){
		modulePackage  = modulePackage+".dao.repository";
		
		switch (type) {
			case 0:
				//读写不分离
				break;
			case 1:
				//只读
				modulePackage  = modulePackage+".read";
				break;
			case 2:
				//只写
				modulePackage  = modulePackage+".write";
				break;
			default:
				break;
		}
		return modulePackage;
	}
	
	
	private MapperRedisSource readRedisMapperModelDataSource(String className,
			String modulePackage){
		
		MapperRedisSource mapperRedisSource = new MapperRedisSource();
		mapperRedisSource.setSuperName(getModelRedis(className));
		
		//引用
		List<String> importList = new ArrayList<String>();
		importList.add(getModelRedisPath(modulePackage, className));
		mapperRedisSource.setImportList(importList);
		
		//设置annon
		className = getMapperClassName(className);
		mapperRedisSource.setClassName(className );
		mapperRedisSource.setAnnoName(lowerIndexChar(className));
		//包包包
		mapperRedisSource.setPackageName( getMapperPackageName(modulePackage) );
		
		return mapperRedisSource;
	}
	
	private String getMapperClassName(String className){
		return getModelRedis(className)+Constant.MAPPER_SUFFIX;
	}
	
	private String getMapperPackageName(String modulePackage){
		return modulePackage+".mapper";
	}
	
	private String getMapperPackagePath(String modulePackage, String className){
		return getMapperPackageName(modulePackage)+"."+getMapperClassName(className);
	}
	
	
	/**
	 * 获取model redis 的classpath
	 * @param modulePackage
	 * @param className
	 * @return
	 */
	private String getModelRedisPath(String modulePackage, String className){
		return modulePackage+".model.redis."+className+Constant.REDIS_SUFFIX;
	}
	
	/**
	 * 获取对应实体类Mysql中 classpath
	 * @param modulePackage
	 * @param className
	 * @return
	 */
	private String getModelMysqlPath(String modulePackage, String className){
		return modulePackage+".model.mysql."+className;
	}
	
	
	/**
	 * 获取对应的redis model name
	 * @param className
	 * @return
	 */
	private String getModelRedis(String className){
		return  className+"Redis";
	}
	
	
	
	/**
	 * 组装redis
	 * 实体类数据
	 * @param className
	 * @param prePackage
	 * @param tableName
	 * @param fieldes
	 * @return
	 */
	private ModelRedisSource readRedisModelDataSource(String className,
			String modulePackage, Field[] fieldes){
		
		className = getModelRedis(className);
		
		ModelRedisSource modelRedisSource = new ModelRedisSource();
		 List<String> importList = new ArrayList<String>();
		 List<FieldSource> fieldSources = new ArrayList<FieldSource>();
		 FieldSource fieldSource;
		 modulePackage= modulePackage+".model.redis";
		 modelRedisSource.setPackageName(modulePackage);
		 modelRedisSource.setClassName(className);
		 
		 for(Field field:fieldes){
				
				fieldSource = new FieldSource();
				
				if(field.getTypeClass().contains(".")){
					String oldTypeClass = field.getTypeClass();
					if( !importList.contains(oldTypeClass)){
						importList.add(oldTypeClass);
					}
					String typeClass = oldTypeClass.substring(oldTypeClass.lastIndexOf(".")+1, oldTypeClass.length() );
					fieldSource.setTypeClass(typeClass);
				}else{
					fieldSource.setTypeClass(field.getTypeClass());
				}
				
				fieldSource.setFieldName(field.getPropertyName());
				fieldSource.setFieldNote(field.getFieldNote());
				fieldSource.setGetterMethodName( getter(field.getPropertyName(), field.getTypeClass()) );
				fieldSource.setSetterMethodName( setter( field.getPropertyName(), field.getTypeClass() ));
				fieldSources.add(fieldSource);
			}
		 modelRedisSource.setImportList(importList);
		 modelRedisSource.setFieldSources(fieldSources);
		 
		 return modelRedisSource;
	}
	 
	/**
	 * mysql实体类组装
	 * @param className
	 * @param prePackage
	 * @param tableName
	 * @param fieldes
	 * @return
	 */
	private ModelTemplateSource readMysqlModelDataSource(String className,
			String modulePackage,String tableName, Field[] fieldes){
		
		 List<String> importList = new ArrayList<String>();
		 List<FieldSource> fieldSources = new ArrayList<FieldSource>();
		 FieldSource fieldSource;
		 
		ModelTemplateSource modelTemplateSource = new ModelTemplateSource();
		
		modelTemplateSource.setClassName(className);
		
		modulePackage= modulePackage+".model.mysql";
		modelTemplateSource.setPackageName(modulePackage);
		
		if(StringUtils.isEmpty(tableName)){
			tableName = className.toLowerCase();
		}
		modelTemplateSource.setTableName(tableName);
		
		for(Field field:fieldes){
			
			fieldSource = new FieldSource();
			
			if(field.getTypeClass().contains(".")){
				String oldTypeClass = field.getTypeClass();
				if( !importList.contains(oldTypeClass)){
					importList.add(oldTypeClass);
				}
				String typeClass = oldTypeClass.substring(oldTypeClass.lastIndexOf(".")+1, oldTypeClass.length() );
				
				fieldSource.setTypeClass(typeClass);
			}else{
				fieldSource.setTypeClass(field.getTypeClass());
			}
			
			fieldSource.setColumnName(field.getTableColumnName());
			fieldSource.setFieldName(field.getPropertyName());
			fieldSource.setFieldNote(field.getFieldNote());
			fieldSource.setGetterMethodName( getter(field.getPropertyName(), field.getTypeClass()) );
			fieldSource.setSetterMethodName( setter( field.getPropertyName(), field.getTypeClass() ));
			fieldSources.add(fieldSource);
		}
		modelTemplateSource.setImportList(importList);
		modelTemplateSource.setFieldSources(fieldSources);
		
		return modelTemplateSource;
	}

	
	/**
	 * getter
	 * @param name
	 * @param type
	 * @return
	 */
	private static String getter(String name, String type) {
		
		if("boolean".equals(type) || "java.lang.Boolean".equals(type)){
			if(name.startsWith("is")){
				return name;
			}
		}
		
		return "get" + transName(name);
	}
	
	/**
	 * setter
	 * @param name
	 * @param type
	 * @return
	 */
	private static String setter(String name,String type) {
		if("boolean".equals(type) || "java.lang.Boolean".equals(type)){
			if(name.startsWith("is")){
				name = name.substring(2);
			}
		}
		return setter(name);
	}
	
	private static String setter(String name) {
		return "set" + transName(name);
	}

	private static String transName(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	
	
	/**
	 * 根据包名获取对应的
	 * 目录路径
	 * @param packageName
	 * @return
	 */
	public static String getDestPath(String packageName){
		return packageName.replace(".", "/");
		
	}
	
	public boolean processMore(List<EntitySource> entitySources){
		//删除对应目录下的所有文件
		File file = new File("code");
		try {
			FileUtils.deleteDirectory(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for( EntitySource entitySource:entitySources){
			
			if( entitySource.getModelTemplateSource() != null){
				invokeTemplate("model_mysql", entitySource.getModelTemplateSource());
			}
			
			//生成 model_redis
			if( entitySource.getModelRedisSource() != null){
				invokeTemplate("model_redis", entitySource.getModelRedisSource());
			}
			
			if( entitySource.getMapperRedisSource() != null){
				invokeTemplate("mapper_redis", entitySource.getMapperRedisSource());
			}
			
			if(entitySource.getDaoRepositorySource() != null){
				invokeTemplate("dao_repository", entitySource.getDaoRepositorySource());
			}
			
			if(entitySource.getDaoWriteRepositorySource() != null){
				invokeTemplate("dao_repository_write", entitySource.getDaoWriteRepositorySource());
			}
			
			if(entitySource.getDaoReadRepositorySource() != null){
				invokeTemplate("dao_repository_read", entitySource.getDaoReadRepositorySource());
			}
			
			if(entitySource.getDaoRedisImplSource() != null){
				invokeTemplate("dao_redis_impl", entitySource.getDaoRedisImplSource());
			}
			
			if(entitySource.getDaoRedisSource() != null){
				invokeTemplate("dao_redis", entitySource.getDaoRedisSource());
			}
			
			if(entitySource.getServiceDubboRemoteSource() != null){
				invokeTemplate("service_dubbo_remote", entitySource.getServiceDubboRemoteSource());
			}
			
			if(entitySource.getServiceDubboSource() != null){
				invokeTemplate("service_dubbo", entitySource.getServiceDubboSource());
			}
			
			if(entitySource.getServiceImplSource() != null){
				invokeTemplate("service_impl", entitySource.getServiceImplSource());
			}
			
			if(entitySource.getServiceSource() != null){
				invokeTemplate("service", entitySource.getServiceSource());
			}
			
		}
		
		return true;
	}
	
	private boolean invokeTemplate(String templateName, BaseSource baseSource){
		//使用的模板
		String srcFileName = getSourceTemplatePath(templateName);
		//生成文件的目录
		String destPath = getDestPath(baseSource.getPackageName());
		//模板模板
		String destFileName = getTargetFileName(baseSource.getClassName());
		//模板所在的路径
		String templatePath = copyFile(srcFileName, destPath, destFileName);
		
		process(templatePath, destFileName, baseSource);
		return true;
	}
	
	
	
	/**
	 * 拷贝模板文件
	 * @param srcFileName
	 * @param destPath
	 * @param destFileName
	 * @return
	 */
	public static String copyFile(String srcFileName, String destPath, String destFileName ){
		File srcFile = new File(srcFileName);
		String destFilePath = "code/"+destPath+"/"+destFileName;
		File  destFile =  new File(destFilePath);
		
		try {
			FileUtils.copyFile(srcFile, destFile);
			return "code/"+destPath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取源模板路径
	 * @param templateName
	 * @return
	 */
	private String getSourceTemplatePath(String templateName ){
		return "template/"+templateName;
	}
	
	/**
	 * 获取目标文件名
	 * @param destFileName
	 * @return
	 */
	private String getTargetFileName( String destFileName ){
		return destFileName+".java";
	}
	
	/**
	 * 真正生成对应的java文件
	 * @param destPath
	 * @param templateName
	 * @param baseSource
	 * @return
	 */
	private boolean process(String templatePath, String templateName, BaseSource baseSource){
		 Configuration freeMarkerCfg = new Configuration(Configuration.VERSION_2_3_19);  
		 OutputStreamWriter writer = null;
		 try {
			freeMarkerCfg.setDirectoryForTemplateLoading(new File(templatePath));
			Template template = freeMarkerCfg.getTemplate(templateName); 
			writer = new OutputStreamWriter(new FileOutputStream(templatePath+"/"+templateName), "UTF-8"); 
			Map<String, Object> parameters = new HashMap<String, Object>(); 
			parameters.put("entity",  baseSource);  
			template.process(parameters, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 将字符串第一个字母变成小写
	 * @param str
	 * @return
	 */
	private String lowerIndexChar(String str){
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	
	public static void main(String[] args) throws Exception {
		
		String str = "Cla";
		String st = str.substring(0, 1).toLowerCase() + str.substring(1);
		System.out.println(st);
	}
}
