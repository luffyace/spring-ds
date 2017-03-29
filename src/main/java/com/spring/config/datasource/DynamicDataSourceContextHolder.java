package com.spring.config.datasource;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DynamicDataSourceContextHolder {
	private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

	public static List<String> dataSourceIds = new ArrayList<String>();

	public static void setDataSource(String dataSourceType) {
		dataSources.set(dataSourceType);
	}
	
	public static void setDataSource(String dataSourceType,JdbcTemplate template,DataSource ds) {
		setDataSource(dataSourceType);
		template.setDataSource(ds);
	}

	public static String getDataSource() {
		return dataSources.get();
	}

	public static void clearDataSource() {
		dataSources.remove();
	}

	public static boolean containsDataSource(String dataSourceId) {
		return dataSourceIds.contains(dataSourceId);
	}
}
