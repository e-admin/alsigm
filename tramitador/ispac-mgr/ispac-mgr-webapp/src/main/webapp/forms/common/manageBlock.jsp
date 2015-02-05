<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!-- Para resituarse en la pestaña en la que nos encontrabamos -->
<logic:present parameter="block"> 
	<bean:parameter id="_block" name="block"/>
	<script language='JavaScript' type='text/javascript'><!--
	
		showTab(<bean:write name="_block"/>);
	
	//--></script>
</logic:present>
<%--
<logic:notPresent parameter="block">
	<script language='JavaScript' type='text/javascript'><!--
	
		var activeBlock = document.defaultForm.block.value;
		if (activeBlock != '') {
			showTab(activeBlock);
		}
		else {
			showTab(block);
		}
		
	//--></script>
</logic:notPresent>
--%>

<!-- En el caso de que la entidad tenga documentos asociados,
     para resituarse en la pestaña de documentos al interactuar con la lista (ordenacion, paginacion) -->
<logic:present parameter="blockdocs"> 
	<bean:parameter id="_blockdocs" name="blockdocs"/>
	<script language='JavaScript' type='text/javascript'><!--
	
		showTab(<bean:write name="_blockdocs"/>);
	
	//--></script>
</logic:present>
