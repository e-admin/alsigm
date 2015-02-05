<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

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

<script language="javascript">

	<bean:define id="texto" type="java.lang.String">
		<bean:message key="label.id"/>
	</bean:define>
	<bean:define id="desc" type="java.lang.String">
		<bean:message key="label.description"/>
	</bean:define>
	<bean:define id="clase" type="java.lang.String">
		<bean:message key="auth.label.class"/>
	</bean:define>
	<bean:define id="type" type="java.lang.String">
		<bean:message key="auth.label.type"/>
	</bean:define>
	
	function validateFieldsLengths(){
	    return true;
	}
	
    	function validateForm(){
    		var formulario = document.getElementById("hooksBean");
    		if (formulario.identifier.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=texto%>"/>');
                	return false;
                }
    		if (formulario.description.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=desc%>"/>');
                	return false;
                }
    		if (formulario.typeId.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=type%>"/>');
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
//<!-- PUESTOS A NO VISIBLES TODOS LOS BOTONES -->
   	//button.style.display = "";   	
   }
   
   function hideDelButton(){
   	var button = document.getElementById("delButton");
   	button.style.display = "none";
   }
   
   function showAddButton(){
   	var button = document.getElementById("addButton");
//<!-- PUESTOS A NO VISIBLES TODOS LOS BOTONES -->
   	//button.style.display="";
   }
   
   function hideAddButton(){
   	var button = document.getElementById("addButton");
   	button.style.display = "none";
   }
   
   function showUpdateButton(){
   	var button = document.getElementById("updateHookButton");
//<!-- PUESTOS A NO VISIBLES TODOS LOS BOTONES -->
   	//button.style.display="";
   }
   
   function hideUpdateButton(){
   	var button = document.getElementById("updateHookButton");
   	button.style.display="none";
   }
   
   function nuevoHook(){
   	var form = document.getElementById("hooksBean");
   	form.identifier.value = "";
   	form.description.value = "";	
   	//form.implementClass.value = "";
   	form.typeId.value = "";
   	//form.info.value = "";
   	hideUpdateButton();
   	showAddButton();
   	hideDelButton();
   	enableInput(document.getElementById("identificador"));
   	document.getElementById("identificador").focus();
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
   	disableInput(document.getElementById("identificador"));

//<!-- PUESTOS A NO VISIBLES TODOS LOS BOTONES; Y LOS CAMPOS A NO EDITABLES -->
   	disableInput(document.getElementById("descripcion"));
   	disableInput(document.getElementById("tipo"));
   }

   function loadHook(form_element){
		var form = document.getElementById("hooksBean");
		form.userAction.value = "load";
		form.submit();
	}

   function getUserAction(theAction){   
   	  var form = document.getElementById("hooksBean");
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
			if(!validateUpdateAction()){
				return;
			}
		}
	  }
	  var form = document.getElementById("hooksBean");
	  form.userAction.value=theAction;
	  form.submit();
   }

   function validateAddAction(){
      var ctrl = document.getElementById("hooksList");
      var elem, exists;
      var value = hooksBean.identifier.value;
      exists = false;
      for (i = 0; i < ctrl.options.length; i++){
         if (ctrl.options[i].value == value){
            exists = true;
            break;
	     }
	   }        
       if (exists){
	       alert('<bean:message key="auth.error1"/>');
	   }
	   return exists;
	}
	
   function validateUpdateAction(){
      var ctrl = document.getElementById("hooksList");
      var elem, exists;
      var value = hooksBean.identifier.value;
      exists = false;
      for (i = 0; i < ctrl.options.length; i++){
         if (ctrl.options[i].value == value){
            exists = true;
            break;
	     }
	   }        
       if (!exists){
	       alert('<bean:message key="auth.error1"/>');
	   }
	   return exists;
	}

    
</script>
    
    
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

	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
	<META name="GENERATOR" content="IBM Software Development Platform">
	<TITLE></TITLE>
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
						<li class="subOn">
							<h3><a><bean:message key="menu.auth.conn" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="authHook.do"><bean:message key="menu.conectores_tramites" /></a></h3>
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

					<html:form styleId="hooksBean" action="hooks" method="post">

					<div class="seccion_large"> 
						<div class="cuerpo_sec fila_sub clearfix">
							<ul>
<!-- PUESTOS A NO VISIBLES TODOS LOS BOTONES -->
							<%if(writeFlag == "1"){%>                                             	            
								<li id="newHookButton" name="newHookButton" style="display: none">
								<a href="javascript:nuevoHook();" class="col_nuevo">
									<bean:message key="button.label.new"/>
								</a>
								</li>
				 			<%}%> 
				 			<%if(writeFlag == "1"){%> 
								<li id="updateHookButton" name="updateHookButton" style="display: none">
								<a onclick="javascript:getUserAction('update')" class="col_guardar" >
									<bean:message key="button.label.save"/>
								</a>
								</li>
								<li id="addButton" name="addButton"  style="display: none">
								<a href="javascript:getUserAction('add')" class="col_nuevo">
									<bean:message key="button.label.add"/>
								</a>
								</li>
								<li id="delButton" name="delButton" style="display: none">
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

					<div class="seccion"> 
						<p class="fila">
							<label class="sml sub"><bean:message key="auth.hooks"/></label>
						        <div class="barraHz mdl">
							<html:select styleId="hooksList" property="hookId" styleClass="multiple minmdl" size="6" onchange="loadHook(this)">
								<html:optionsCollection name="hooks" label="hookDesc" value="hookId"/>
							</html:select>  
							</div>
						</p>
					</div>

					<div class="seccion"> 
						<p class="fila_sub">
							<label class="sml" /><bean:message key="label.id"/></label>
							<html:text property="identifier" styleId="identificador" maxlength="32" styleClass="xsml"/>
						</p>
						<p class="fila_sub">
							<label class="sml" ><bean:message key="label.description"/></label>
							<html:text property="description" styleId="descripcion" maxlength="256" styleClass="mdl"/>
						</p>
						<p class="fila">
							<label class="sml sub" ><bean:message key="auth.label.type"/></label>
							<div class="barraHz mdl">
							<html:select property="typeId" styleId="tipo" styleClass="single minmdl">
								<html:optionsCollection name="hookTypes" label = "typeDescription" value="typeId"/>
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
	</body>

<!-- PUESTOS LOS CAMPOS A NO EDITABLES -->
	<script language="Javascript">
	   	disableInput(document.getElementById("identificador"));
	   	disableInput(document.getElementById("descripcion"));
	   	disableInput(document.getElementById("tipo"));
	</script>

</html:html>
<logic:equal name="hooksBean" property="userAction" value="load">
	<script language="javascript">
		afterLoadHook();
	</script>
</logic:equal>