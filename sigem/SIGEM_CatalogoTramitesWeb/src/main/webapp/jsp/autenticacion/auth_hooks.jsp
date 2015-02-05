<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.ConectoresAutenticacion" %>
<%@page import="ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites"%>
<%@page import="ieci.tecdoc.sgm.core.services.LocalizadorServicios"%>
<%@page import="ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper"%>

<html:html>
	<bean:define id="parentMenuNumber" value="2" type="java.lang.String"/>
	<%
	
		String readWriteToken = (String)session.getAttribute("readWriteToken");
		
		String writeFlag = "0";
		if(readWriteToken != null){
			if(readWriteToken.charAt(Integer.valueOf(parentMenuNumber).intValue()) == '1'){
				writeFlag = "1";
			}else{
				writeFlag = "0";
			}
		}else{
			writeFlag = "1";
		}
	%>

    <%
    
    int i, len;

    %>

    <head>
	    <%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />

		<link rel="stylesheet" href="css/estilos.css" type="text/css" />

		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
		<![endif]-->	
	
		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
		<![endif]-->	
	
		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
		<![endif]-->	


	    <script language="javascript">
	
			<bean:define id="idtramite" type="java.lang.String">
				<bean:message key="authhook.idtramite"/>
			</bean:define>
			<bean:define id="idconector" type="java.lang.String">
				<bean:message key="authhook.idconector"/>
			</bean:define>
		
			function validateFieldsLengths(){
		    	return true;
			}
		
	    	function validateForm(){
	    		var formulario = document.getElementById("authHooksBean");
	    		if (formulario.selProcedureId.value == ""){
	    			alert('<bean:message key="errors.required" arg0="<%=idtramite%>"/>');
	                	return false;
	            }
	    		if (formulario.selHookId.value == ""){
	    			alert('<bean:message key="errors.required" arg0="<%=idconector%>"/>');
	               	return false;
	            }
	            return validateFieldsLengths();
	    	}
	        
	        
	        function nuevoAuthHook(){
			   	var form = document.getElementById("authHooksBean");
			   	form.selProcedureId.value = "";
			   	form.selHookId.value = "";	
			   	hideUpdateButton();
			   	showAddButton();
			   	hideDelButton();
			   	enableInput(document.getElementById("selProcedureId"));
			   	enableInput(document.getElementById("selHookId"));
			}
			
	   		function disableInput(inputobject){
	   			inputobject.disabled = true;
	   		}
	   
	   		function enableInput(inputobject){
	   			inputobject.disabled = false;
	   		}
	   
	   		function showDelButton(){
	   			var button = document.getElementById("delButton");
	   			button.style.display = "";   	
	   		}
	   
	   		function hideDelButton(){
	   			var button = document.getElementById("delButton");
	   			button.style.display = "none";
	   		}
	   
	   		function showAddButton(){
	   			var button = document.getElementById("addButton");
	   			button.style.display="";
	   		}
	   
	   		function hideAddButton(){
	   			var button = document.getElementById("addButton");
	   			button.style.display = "none";
	   		}
	   
	   		function showUpdateButton(){
	   			var button = document.getElementById("updateHookButton");
	   			button.style.display="";
	   		}
	   
	   		function hideUpdateButton(){
	   			var button = document.getElementById("updateHookButton");
	   			button.style.display="none";
	   		}
	   		
			function getUserAction(theAction){   
	   	  		var form = document.getElementById("authHooksBean");
		  		if ((theAction == 'add')||(theAction == 'update')) {
					if(!validateForm()){
						return;
					}
	      			if(theAction == 'add'){
						if (validateAddAction()) {
							return;
						}
					}
					if(theAction == 'update'){
						if(validateUpdateAction()){
							return;
						}
					}
		  		}
		  		enableInput(document.getElementById("selProcedureId"));
		  		var form = document.getElementById("authHooksBean");
		  		form.userAction.value=theAction;
		  		form.submit();
	   		}
	   		
	   		function validateAddAction(){
	      		var ctrl = document.getElementById("authhooks");
	      		var proc = document.getElementById("selProcedureId");
	      		var hook = document.getElementById("selHookId");
	      		var elem, exists;
	      		var value = proc.options[proc.selectedIndex].value + "," + hook.options[hook.selectedIndex].value;
	      		exists = false;
	      		for (i = 0; i < ctrl.options.length; i++){
	        		if (ctrl.options[i].value == value){
	            		exists = true;
	            		break;
		     		}
		   		}        
	       		if (exists){
		       		alert('<bean:message key="auth.error5"/>');
		   		}
		   		return exists;
			}
		
			function loadAuthHook(form_element){
				var form = document.getElementById("authHooksBean");
				form.userAction.value = "load";
				var ctrl = document.getElementById("authhooks");
				var value = ctrl.options[ctrl.selectedIndex].value;
				var idtramite = value.substring(0, value.indexOf(","));
				var idconector = value.substring(value.indexOf(",")+1, value.length);
				enableInput(document.getElementById("selProcedureId"));
				form.selHookId.value = idconector;
				form.selProcedureId.value = idtramite;
				form.oldHookId.value = idconector;
				form.submit();
			}
			
	   		function validateUpdateAction(){
	      		var ctrl = document.getElementById("authhooks");
	      		var proc = document.getElementById("selProcedureId");
	      		var hook = document.getElementById("selHookId");
	      		var elem, exists;
	      		var value = proc.options[proc.selectedIndex].value + "," + hook.options[hook.selectedIndex].value;
	      		exists = false;
	      		for (i = 0; i < ctrl.options.length; i++){
	         		if (ctrl.options[i].value == value){
	            		exists = true;
	            		break;
		     		}
		   		}        
	       		if (exists){
		       		alert('<bean:message key="auth.error5"/>');
		   		}
		   		return exists;
			}
			
			function afterLoadHook(){
				<%
	            if(writeFlag == "1"){
	            %>                                             	                           
					showUpdateButton();
				   	hideAddButton();
				   	showDelButton();
				<%
	            }
	            %>                                             	                           	
			   	disableInput(document.getElementById("selProcedureId"));
	   		}
	   		
	   		function changeDescriptions(){
	   			var ctrl = document.getElementById("authhooks");
	      		var proc = document.getElementById("selProcedureId");
	      		var hook = document.getElementById("selHookId");
	      		var i=0, j=0, index=-1;
	      		var found = false;
	      		var value = "", idtramite = "", idconector = "";
	      		for(i=0; i<ctrl.options.length; i++){
		      		value = ctrl.options[i].value;
					idtramite = value.substring(0, value.indexOf(","));
					idconector = value.substring(value.indexOf(",")+1, value.length);
					for(j=0; j<proc.options.length; j++){
						if (idtramite == proc.options[j].value){
							found = true;
							index = j;
							break;
						}
					}
					if(found){
						ctrl.options[i].text = proc.options[index].text;
					}else ctrl.options[i].text = idtramite;
					found = false;
					index = -1;
					
					for(j=0; j<hook.options.length; j++){
						if (idconector == hook.options[j].value){
							found = true;
							index = j;
							break;
						}
					}
					if(found){
						ctrl.options[i].text += " -> " + hook.options[index].text;
					}else ctrl.options[i].text += " -> " + idconector;
					found = false;
					index = -1;
				}
				sortlist();
	    	}     		
	      	
	      	function sortlist() {
				var lb = document.getElementById("authhooks");
				arrTexts = new Array();
				for(i=0; i<lb.length; i++)  {
					arrTexts[i] = lb.options[i].text;
				}
				arrTexts.sort();
				arrValues = new Array();
				for(i=0; i<lb.length; i++)  {
					arrValues[i] = getValue(lb, arrTexts[i]);
				}
				for(i=0; i<lb.length; i++)  {
					lb.options[i].text = arrTexts[i];
					lb.options[i].value = arrValues[i];
				}
			}
			
			function getValue(ctrl, texto){
				var i=0;
				for(i=0; i<ctrl.options.length; i++){
					if(texto == ctrl.options[i].text)
						return ctrl.options[i].value;
				}
			}
			
			function selectOne(){
				var proc = document.getElementById("selProcedureId");
	      		var hook = document.getElementById("selHookId");
	      		var value = proc.options[proc.selectedIndex].value + "," + hook.options[hook.selectedIndex].value;
	      		var ctrl = document.getElementById("authhooks");
	      		var i=0;
	      		for(i=0; i<ctrl.options.length; i++){
	      			if(ctrl.options[i].value == value){
	      				ctrl.selectedIndex = i
	      				break;
	      			}
	      		}
			}
	    </script>
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
    </head>
    
    <body>
    

			<div class="cuerpo">
      			<div class="cuerporight">
        			<div class="cuerpomid">
        				<h1><bean:message key="menu.configurador" /></h1>

<!-- NUEVO -->
				<div class="tabs_submenus">
					<ul>
						<li class="subOff">
							<h3><a href="procedure.do"><bean:message key="procedures.prococedures" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="documents.do"><bean:message key="menu.tiposDocs" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="hooks.do"><bean:message key="menu.auth.conn" /></a></h3>
						</li>
						<li class="subOn">
							<h3><a><bean:message key="menu.conectores_tramites" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="addresseeList.do"><bean:message key="menu.organos" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="calendar.do"><bean:message key="menu.calendario" /></a></h3>
						</li>
					</ul>
				</div>
				<div class="cuerpo_subs">

					<div class="cuerpo_sub visible clearfix">

					<html:form styleId="authHooksBean" action="authHook" method="post">    

					<div class="seccion_large"> 
						<div class="cuerpo_sec fila_sub clearfix">
							<ul>
							<%if(writeFlag == "1"){%>                                             	            
								<li id="newHookButton" name="newHookButton">
								<a href="javascript:nuevoAuthHook();" class="col_nuevo">
									<bean:message key="button.label.new"/>
								</a>
								</li>
							<%}%> 
							<%if(writeFlag == "1"){%> 
								<li id="updateHookButton" name="updateHookButton" style="display: none">
								<a href="javascript:getUserAction('update')" class="col_guardar">
									<bean:message key="button.label.save"/>
								</a>
								</li>

								<li id="addButton" name="addButton" style="display: none">
								<a href="javascript:getUserAction('add')" class="col_nuevo">
									<bean:message key="button.label.add"/>
								</a>
								</li>

								<li id="delButton" name="delButton">
								<a href="javascript:getUserAction('delete')" class="col_eliminar">
									<bean:message key="button.label.delete"/>
								</a>
								</li>
							<%}%>	

							</ul>
						</div>
					</div> 

					<div class="clear">
					</div> 

					<html:hidden property="userAction"/>
					<html:hidden property="oldHookId"/>

					<div class="seccion"> 
						<p class="fila">
						                <label class="sml sub"><bean:message key="auth.hooks"/></label>
								<div class="barraHz mdl">
						                <select id="authhooks" name="authhooks" class="multiple minmdl" size="6" onchange="loadAuthHook(this)">
						                    <%
						                    ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
						                    ConectoresAutenticacion authHooks = oServicio.getAuthHooks("", SesionAdminHelper.obtenerEntidad(request));
						                    ConectorAutenticacion authHook;
					    	                for (i=0; i < authHooks.count(); i++) {
					        	                authHook = authHooks.get(i);
					            	            out.write("<option value=\"" + authHook.getTramiteId()+","+authHook.getConectorId() + "\">" + authHook.getTramiteId()+","+authHook.getConectorId() + "</option>\n");
					                	    }
					                    	%>
						                </select>
						                </div>
						</p>
					</div>

					<div class="seccion"> 
						<p class="fila">
							<label class="sml sub"><bean:message key="procedures.prococedures"/></label>
							<div class="barraHz mdl">
							<html:select styleClass="single minmdl" property="selProcedureId" styleId="selProcedureId">
								<html:optionsCollection name="procedures" label = "procedureDescription" value="procedureId"/>
							</html:select>
							</div>
						</p>
						<p class="fila">
							<label class="sml sub"><bean:message key="auth.hooks"/></label>
							<div class="barraHz mdl">
							<html:select styleClass="single minmdl" property="selHookId" styleId="selHookId">
								<html:optionsCollection name="hooks" label = "hookDescription" value="hookId"/>
							</html:select>
							</div>
						</p>
					</div>

					</html:form>

					</div> <!-- fin cuerpo_sub visible -->
				</div> <!-- fin cuerpo_subs -->

			</div>
      			</div>
    		</div>
    		<div class="cuerpobt">
      			<div class="cuerporightbt">
        			<div class="cuerpomidbt">&nbsp;</div>
      			</div>
		</div>		
    

        <script language="Javascript">
			changeDescriptions();
			var form = document.getElementById("authHooksBean");
	  		var acc = form.userAction;
			if (acc != null && acc != undefined){
				if (acc.value == "load"){
					afterLoadHook();
					selectOne();
				}
			}
	</script>
    </body>
</html:html>