<%@page import="java.util.Map"%>
<%@page import="com.dpabpm.activiti.ActivityUtils"%>
<%@page import="org.activiti.engine.ProcessEngine"%>
<%@page import="org.activiti.engine.TaskService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.dpabpm.constants.DemoPortletKeys"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="org.activiti.engine.task.Task"%>
<%@ include file="init.jsp" %>

<%
	ProcessEngine processEngine = ActivityUtils.getProcessEngine();
	
	User usr = themeDisplay.getUser();
	
	long userId = usr.getUserId();
	
	long[] roleIds = usr.getRoleIds();
	
	boolean isEmployee = ArrayUtils.contains(roleIds, DemoPortletKeys.EMPLOYEE_ROLE_ID);
	
	boolean isManager = ArrayUtils.contains(roleIds, DemoPortletKeys.MANAGER_ROLE_ID);

	TaskService taskService = processEngine.getTaskService();
	
	List<Task> taskList = new ArrayList<Task>();
	
	for (long roleId : roleIds) {
		List<Task> roleTaskList = taskService
				.createTaskQuery()
				.processDefinitionKey(DemoPortletKeys.PROCESS_DEFINITION_KEY)
				.taskCandidateGroup(String.valueOf(roleId))
				.includeProcessVariables()
				.list();
		
		taskList.addAll(roleTaskList);
	}
	
	List<Task> userTaskList = taskService
			.createTaskQuery()
			.processDefinitionKey(DemoPortletKeys.PROCESS_DEFINITION_KEY)
			.taskAssignee(String.valueOf(userId))
			.includeProcessVariables()
			.list();
	
	taskList.addAll(userTaskList);
%>

<table class="table table-hover table-bordered table-stripped">
	<thead>
		<tr>
			<th>Name</th>
			<th>Content</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (Task task : taskList) {
				Map<String, Object> variables = task.getProcessVariables();
				String name = (String) variables.get("name");
				String content = (String) variables.get("content");
		%>
		
		<portlet:actionURL var="submitURL" name="completeTask">
			<portlet:param name="taskId" value="<%=String.valueOf(task.getId()) %>"/>
		</portlet:actionURL>
		
		<portlet:actionURL var="approveURL" name="completeTask">
			<portlet:param name="taskId" value="<%=String.valueOf(task.getId()) %>"/>
			<portlet:param name="isApproved" value="true"/>
		</portlet:actionURL>
		
		<portlet:actionURL var="rejectURL" name="completeTask">
			<portlet:param name="taskId" value="<%=String.valueOf(task.getId()) %>"/>
			<portlet:param name="isApproved" value="false"/>
		</portlet:actionURL>
		
		<tr>
			<td><%=HtmlUtil.escape(name)  %></td>
			<td><%=HtmlUtil.escape(content) %></td>
			<td>
				<%
					if (isEmployee) {
				%>
				<a class="btn btn-primary btn-sm" href="<%=submitURL %>">Submit</a>
				<%
					}
					else if (isManager) {
				%>
				<a class="btn btn-primary btn-sm" href="<%=approveURL %>">Approve</a>
				<a class="btn btn-danger btn-sm" href="<%=rejectURL %>">Reject</a>
				<%
					}
				%>
			</td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>

