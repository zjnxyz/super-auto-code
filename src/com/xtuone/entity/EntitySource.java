package com.xtuone.entity;

public class EntitySource {
	
	private ModelTemplateSource modelTemplateSource;
	
	private ModelRedisSource modelRedisSource;
	
	//redis mapper
	private MapperRedisSource mapperRedisSource;
	
	private DaoRepositorySource daoRepositorySource;
	
	//只写
	private DaoWriteRepositorySource daoWriteRepositorySource;
	
	// 只读
	private DaoReadRepositorySource daoReadRepositorySource;
	
	//dao-redis 接口
	private DaoRedisSource daoRedisSource;
	
	//dao-redis 实现
	private DaoRedisImplSource daoRedisImplSource;
	
	private ServiceDubboRemoteSource serviceDubboRemoteSource;
	
	private ServiceDubboSource serviceDubboSource;
	
	private ServiceImplSource serviceImplSource;
	
	private ServiceSource serviceSource;
	

	public ModelTemplateSource getModelTemplateSource() {
		return modelTemplateSource;
	}

	public void setModelTemplateSource(ModelTemplateSource modelTemplateSource) {
		this.modelTemplateSource = modelTemplateSource;
	}

	public ModelRedisSource getModelRedisSource() {
		return modelRedisSource;
	}

	public void setModelRedisSource(ModelRedisSource modelRedisSource) {
		this.modelRedisSource = modelRedisSource;
	}

	public MapperRedisSource getMapperRedisSource() {
		return mapperRedisSource;
	}

	public void setMapperRedisSource(MapperRedisSource mapperRedisSource) {
		this.mapperRedisSource = mapperRedisSource;
	}

	public DaoRepositorySource getDaoRepositorySource() {
		return daoRepositorySource;
	}

	public void setDaoRepositorySource(DaoRepositorySource daoRepositorySource) {
		this.daoRepositorySource = daoRepositorySource;
	}

	public DaoWriteRepositorySource getDaoWriteRepositorySource() {
		return daoWriteRepositorySource;
	}

	public void setDaoWriteRepositorySource(
			DaoWriteRepositorySource daoWriteRepositorySource) {
		this.daoWriteRepositorySource = daoWriteRepositorySource;
	}

	public DaoReadRepositorySource getDaoReadRepositorySource() {
		return daoReadRepositorySource;
	}

	public void setDaoReadRepositorySource(
			DaoReadRepositorySource daoReadRepositorySource) {
		this.daoReadRepositorySource = daoReadRepositorySource;
	}

	public DaoRedisSource getDaoRedisSource() {
		return daoRedisSource;
	}

	public void setDaoRedisSource(DaoRedisSource daoRedisSource) {
		this.daoRedisSource = daoRedisSource;
	}

	public DaoRedisImplSource getDaoRedisImplSource() {
		return daoRedisImplSource;
	}

	public void setDaoRedisImplSource(DaoRedisImplSource daoRedisImplSource) {
		this.daoRedisImplSource = daoRedisImplSource;
	}

	public ServiceDubboRemoteSource getServiceDubboRemoteSource() {
		return serviceDubboRemoteSource;
	}

	public void setServiceDubboRemoteSource(
			ServiceDubboRemoteSource serviceDubboRemoteSource) {
		this.serviceDubboRemoteSource = serviceDubboRemoteSource;
	}

	public ServiceDubboSource getServiceDubboSource() {
		return serviceDubboSource;
	}

	public void setServiceDubboSource(ServiceDubboSource serviceDubboSource) {
		this.serviceDubboSource = serviceDubboSource;
	}

	public ServiceImplSource getServiceImplSource() {
		return serviceImplSource;
	}

	public void setServiceImplSource(ServiceImplSource serviceImplSource) {
		this.serviceImplSource = serviceImplSource;
	}

	public ServiceSource getServiceSource() {
		return serviceSource;
	}

	public void setServiceSource(ServiceSource serviceSource) {
		this.serviceSource = serviceSource;
	}

	@Override
	public String toString() {
		return "EntitySource [modelTemplateSource=" + modelTemplateSource + "]";
	}
	
	

}
