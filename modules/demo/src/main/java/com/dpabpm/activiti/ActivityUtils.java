package com.dpabpm.activiti;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class ActivityUtils {
	
	public static ProcessEngine getProcessEngine()
			throws Exception {
		
		StandaloneProcessEngineConfiguration scfg = new StandaloneProcessEngineConfiguration();
		
		// BasicDataSource dataSource = DataSourceUtils.getDataSource();
		
		DataSource dataSource = DataSourceUtils.getLiferayDataSource();
		
		scfg.setDataSource(dataSource);
		
		ProcessEngine processEngine = scfg.buildProcessEngine();
		
		return processEngine;
	}
}