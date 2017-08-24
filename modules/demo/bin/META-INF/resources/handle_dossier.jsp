<%@ include file="init.jsp" %>

<portlet:actionURL var="handleDossierURL" name="handleDossier" />

<form class="form-horizontal" method="POST" action="<%=handleDossierURL %>">
	<div class="form-group">
    	<label for="<portlet:namespace/>name" 
    		class="col-sm-2 control-label">Name</label>
    		
		<div class="col-sm-10">
      		<input type="text" class="form-control" 
      			name="<portlet:namespace/>name"/>
    	</div>
	</div>
	
	<div class="form-group">
    	<label for="<portlet:namespace/>content" 
    		class="col-sm-2 control-label">Content</label>
    		
		<div class="col-sm-10">
      		<textarea class="form-control" 
      			name="<portlet:namespace/>content"></textarea>
    	</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
      		<button class="btn btn-primary" type="submit">
      			Handle dossier
      		</button>
    	</div>
	</div>
</form>