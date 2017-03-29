package com.spring.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
	private ConversionService conversionService = new DefaultConversionService();
	private PropertyValues dataSourcePropertyValues;

	private DataSource defaultDataSource;

	private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();

	public void setEnvironment(Environment environment) {
		initDefaultDataSource(environment);
		initCustomDataSources(environment);
	}

	private void initDefaultDataSource(Environment env) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		Map<String, Object> dsMap = new HashMap<String, Object>();
		dsMap.put("type", propertyResolver.getProperty("type"));
		dsMap.put("driverClassName", propertyResolver.getProperty("driverClassName"));
		dsMap.put("url", propertyResolver.getProperty("url"));
		dsMap.put("username", propertyResolver.getProperty("username"));
		dsMap.put("password", propertyResolver.getProperty("password"));

		defaultDataSource = buildDataSource(dsMap);
		dataBinder(defaultDataSource, env);
	}

	private void initCustomDataSources(Environment env) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
		String dsPrefixs = propertyResolver.getProperty("names");
		for (String dsPrefix : dsPrefixs.split(",")) {
			Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
			DataSource ds = buildDataSource(dsMap);
			customDataSources.put(dsPrefix, ds);
			dataBinder(ds, env);
		}
	}

	@SuppressWarnings("unchecked")
	public DataSource buildDataSource(Map<String, Object> dsMap) {
		Object type = dsMap.get("type");
		if (type == null)
			type = DATASOURCE_TYPE_DEFAULT;
		Class<? extends DataSource> dataSourceType;

		try {
			dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
			String driverClassName = dsMap.get("driverClassName").toString();
			String url = dsMap.get("url").toString();
			String username = dsMap.get("username").toString();
			String password = dsMap.get("password").toString();
			DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
			return factory.build();
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	private void dataBinder(DataSource dataSource, Environment env) {
		RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
		dataBinder.setConversionService(conversionService);
		dataBinder.setIgnoreNestedProperties(false);
		dataBinder.setIgnoreInvalidFields(false);
		dataBinder.setIgnoreUnknownFields(true);
		if (dataSourcePropertyValues == null) {
			Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
			Map<String, Object> values = new HashMap<String, Object>(rpr);
			values.remove("type");
			values.remove("driverClassName");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}
		dataBinder.bind(dataSourcePropertyValues);

	}

	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put("dataSource", defaultDataSource);
		DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
		targetDataSources.putAll(customDataSources);
		for (String key : customDataSources.keySet())
			DynamicDataSourceContextHolder.dataSourceIds.add(key);

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicDataSource.class);

		beanDefinition.setSynthetic(false);
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
		mpv.addPropertyValue("targetDataSources", targetDataSources);
		registry.registerBeanDefinition("dataSource", beanDefinition);
		logger.info("Dynamic DataSource Registry");
	}
}