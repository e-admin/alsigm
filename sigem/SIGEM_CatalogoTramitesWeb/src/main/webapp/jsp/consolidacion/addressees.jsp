<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<html:html>

	<bean:define id="parentMenuNumber" value="3" type="java.lang.String"/>
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

<script language="javascript">

	<bean:define id="code" type="java.lang.String">
		<bean:message key="addr.code.js"/>
	</bean:define>
	<bean:define id="desc" type="java.lang.String">
		<bean:message key="addr.desc.js"/>
	</bean:define>
	function validateFieldsLengths(){
        return true;
	}

    	function validateForm(){
    		var formulario = document.getElementById("addresseeForm");
    		if (formulario.newCode.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=code%>"/>');
                	return false;
                }
    		if (formulario.newDescription.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=desc%>"/>');
                	return false;
                }
                
            return validateFieldsLengths();
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
   	var button = document.getElementById("updateAddresseeButton");
   	button.style.display="";
   }
   
   function hideUpdateButton(){
   	var button = document.getElementById("updateAddresseeButton");
   	button.style.display="none";
   }
   
   function nuevoAddr(){
   	var form = document.getElementById("addresseeForm");
   	form.newCode.value = "";
   	form.newDescription.value = "";	
   	hideUpdateButton();
   	showAddButton();
   	hideDelButton();
   	enableInput(document.getElementById("newCode"));
   	document.getElementById("newCode").focus();
   }

   function loadAddr(){
   	var form = document.getElementById("addresseeForm");
   	var codigo = form.code.options[form.code.selectedIndex].value;
   	var desc = form.code.options[form.code.selectedIndex].text;
   	form.newCode.value = codigo;
   	form.newDescription.value = desc;
		 <%
                      if(writeFlag == "1"){
                 %>                                             	
   	
   	showUpdateButton();
   	hideAddButton();
   	showDelButton();
   	disableInput(document.getElementById("newCode"));
   		 <%
			}
                 %>                                             	

   }

	function validateCode(){
		var combo = document.getElementById("code");
		var valor = document.getElementById("newCode").value;
		var i=0;
		for(i=0; i<combo.length; i++){
			if (combo.options[i].value == valor){
				alert("<bean:message key='addr.error'/>");
               	return false;
			}
		}
		return true;
	}

   function getUserAction(theAction){   
      var form = document.getElementById("addresseeForm"); 
	  if ((theAction == 'add')||(theAction == 'update')){
		if(!validateForm()){
			return;
		}
		if (theAction == 'add'){
			if(!validateCode()){
				return;
			}
		}
	  }
	  form.userAction.value=theAction;
	  form.submit();
   }
   
   
	function afterLoadAddr(){
		<% if(writeFlag == "1"){ %>                                             	                           
			showUpdateButton();
		   	hideAddButton();
		   	showDelButton();
		<% } %>                                             	                           	
   		disableInput(document.getElementById("newCode"));
 	}
 		
 	function selectOne(){
		var cod = document.getElementById("newCode");
   		var desc = document.getElementById("newDescription");
   		var ctrl = document.getElementById("code");
   		var index = searchAddr(cod.value);
   		cod.value = ctrl.options[index].value;
   		desc.value = ctrl.options[index].text;
	}
	
	function searchAddr(code){
		var ctrl = document.getElementById("code");
		var i=0;
		for (i=0; i<ctrl.length; i++){
			if (code == ctrl.options[i].value)
				return i;
		}
		return ctrl.selectedIndex;
	}
</script>
    
	<HEAD>
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


		<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
		<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<META name="GENERATOR" content="IBM Software Development Platform">
		<TITLE></TITLE>
	</HEAD>

	<body>
		

		<div class="cuerpo">
		<div class="cuerporight">
       			<div class="cuerpomid">
       				<h1><bean:message key="menu.configurador" /></h1>

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
						<li class="subOff">
							<h3><a href="authHook.do"><bean:message key="menu.conectores_tramites" /></a></h3>
						</li>
						<li class="subOn">
							<h3><a><bean:message key="menu.organos" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="calendar.do"><bean:message key="menu.calendario" /></a></h3>
						</li>
					</ul>
				</div>
				<div class="cuerpo_subs">

					<div class="cuerpo_sub visible clearfix">

					<html:form styleId="addresseeForm" action="addresseeList" method="post">	


					<div class="seccion_large"> 
						<div class="cuerpo_sec fila_sub clearfix">
							<ul>
							<li id="importButton" name="importButton">
							<a href="importAddressee.do?userAction=selectOrg" class="col_nuevo" >
								<bean:message key="button.label.import"/>
							</a>
							</li>
							<%if(writeFlag == "1"){%>                                             	            
							<li id="newAddresseeButton" name="newAddresseeButton">
							<a href="javascript:nuevoAddr();" class="col_nuevo">
								<bean:message key="button.label.new"/>
							</a>
							</li>
							<%}%> 
							<%if(writeFlag == "1"){%> 
							<li id="updateAddresseeButton" name="updateAddresseeButton" style="display: none"> 
							<a href="javascript:getUserAction('update')" class="col_guardar" >
								<bean:message key="button.label.save"/>
							</a>
							</li>
							<li id="addButton" name="addButton" style="display: none">
							<a href="javascript:getUserAction('add')" class="col_nuevo" >
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
					</div> <!-- fin seccion -->

					<div class="clear">
					</div> <!-- fin clear -->

					<html:hidden property="userAction"/>

					<div class="seccion"> 
						<p class="fila">
							<label class="sml sub"><bean:message key="addr.addrorgs"/></label>
							<div class="barraHz mdl">
							<html:select styleClass="multiple minmdl" multiple="true" property="code" styleId="code" size="15"  onchange="loadAddr()"  >
							   <html:optionsCollection name="addressees" label="code" value="description"/> 
							</html:select>
							</div>
						</p>
					</div>

					<div class="seccion"> 
						<p class="fila_sub">
							<label class="sml"><bean:message key="addr.code"/></label>
							<html:text styleId="newCode" styleClass="xsml" property="newCode" value="" maxlength="16"/>
						</p>
						<p class="fila_sub">
							<label class="sml"><bean:message key="addr.desc"/></label>
						 	<html:text property="newDescription" styleClass="mdl" styleId="newDescription" value="" maxlength="256"/>
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
			var form = document.getElementById("addresseeForm");
	  		var acc = form.userAction;
			if (acc != null && acc != undefined){
				if (acc.value == "load"){
					afterLoadAddr();
					selectOne();
				}
			}
		</script>

	</body>
</html:html>
