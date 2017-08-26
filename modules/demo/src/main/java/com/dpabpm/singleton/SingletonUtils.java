package com.dpabpm.singleton;

import org.activiti.engine.ProcessEngine;

import com.dpabpm.activiti.ActivityUtils;

public enum SingletonUtils {
	INSTANCE;
	
	public ProcessEngine getProcEngineSingleton() throws Exception {
		return  ActivityUtils.getProcessEngine();
	}
}
