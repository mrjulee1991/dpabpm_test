
package com.dpabpm.portlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ArrayUtils;
import org.osgi.service.component.annotations.Component;

import com.dpabpm.constants.DemoPortletKeys;
import com.dpabpm.singleton.SingletonUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.util.ContentUtil;

/**
 * @author Admin
 */
@Component(immediate = true, property = {
	"com.liferay.portlet.display-category=category.sample",
	"com.liferay.portlet.instanceable=true",
	"javax.portlet.display-name=demo Portlet",
	"javax.portlet.init-param.template-path=/",
	"javax.portlet.init-param.view-template=/view.jsp",
	"javax.portlet.name=" + DemoPortletKeys.Demo,
	"javax.portlet.resource-bundle=content.Language",
	"javax.portlet.security-role-ref=power-user,user"
}, service = Portlet.class)
public class DemoPortlet extends MVCPortlet {
	
	public void deployProcess(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ProcessEngine processEngine = SingletonUtils.INSTANCE.getProcEngineSingleton();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		String bpmXmlString = ContentUtil.get("/com/dpabpm/resources/bmpnxml/demo.bpmn20.xml");
		
		repositoryService.createDeployment().addString("demo.bpmn20.xml", bpmXmlString).deploy();
	}
	
	public void handleDossier(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String name = ParamUtil.getString(actionRequest, "name");

		String content = ParamUtil.getString(actionRequest, "content");
		
		Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("name", name);
        variables.put("content", content);
		
		ProcessEngine processEngine = SingletonUtils.INSTANCE.getProcEngineSingleton();
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		Map<String, Object> submitedEmployee = new HashMap<String, Object>();
		submitedEmployee.put("submitedEmployee", null);
		
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(DemoPortletKeys.PROCESS_DEFINITION_KEY, submitedEmployee);
		
		TaskService taskService = processEngine.getTaskService();
		
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
		
		for (Task task : taskList) {
			taskService.complete(task.getId(), variables);
		}
	}
	
	public void completeTask(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		long taskId = ParamUtil.getLong(actionRequest, "taskId");
		
		boolean isApproved = ParamUtil.getBoolean(actionRequest, "isApproved");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		User usr = themeDisplay.getUser();
		
		long[] roleIds = usr.getRoleIds();
		
		boolean isEmployee = ArrayUtils.contains(roleIds, DemoPortletKeys.EMPLOYEE_ROLE_ID);
		
		boolean isManager = ArrayUtils.contains(roleIds, DemoPortletKeys.MANAGER_ROLE_ID);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		if (isEmployee) {
			variables.put("submitedEmployee", usr.getUserId());
		}
		else if (isManager) {
			variables.put("isApproved", isApproved);
		}
		
		ProcessEngine processEngine = SingletonUtils.INSTANCE.getProcEngineSingleton();
		
		TaskService taskService = processEngine.getTaskService();
	
		taskService.complete(String.valueOf(taskId), variables); 
	}
	
}
