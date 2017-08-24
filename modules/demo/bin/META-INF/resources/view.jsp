<%@ include file="init.jsp" %>

<portlet:actionURL var="deployProcessURL" name="deployProcess"/>

<portlet:renderURL var="handleDossierURL">
	<portlet:param name="mvcPath" value="/handle_dossier.jsp"/>
</portlet:renderURL>

<portlet:renderURL var="reviewDossierURL">
	<portlet:param name="mvcPath" value="/review_dossier.jsp"/>
</portlet:renderURL>

<a class="btn btn-primary" href="<%=deployProcessURL %>">
	Deploy process
</a>

<ul class="list-group">
	<li class="list-group-item">
		<a href="<%=handleDossierURL %>">
			Handle dossier
		</a>
	</li>
	<li class="list-group-item">
		<a href="<%=reviewDossierURL %>">
			Review dossier
		</a>
	</li>
</ul>