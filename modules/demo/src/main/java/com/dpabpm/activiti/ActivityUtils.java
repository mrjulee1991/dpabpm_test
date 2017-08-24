package com.dpabpm.activiti;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.xml.sax.SAXException;

public class ActivityUtils {
	
	public static ProcessEngine getProcessEngine()
			throws ParserConfigurationException, SAXException, IOException {
		
		StandaloneProcessEngineConfiguration scfg = new StandaloneProcessEngineConfiguration();
		
		BasicDataSource dataSource = DataSourceUtils.getDataSource();
		
		scfg.setDataSource(dataSource);
		
		ProcessEngine processEngine = scfg.buildProcessEngine();
		
		return processEngine;
	}
}
