<%@ page errorPage="/jsp/error_1.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Documentos" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Documento" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Conector" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.TipoConector" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Conectores" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites" %>
<%@ page import="ieci.tecdoc.sgm.core.services.LocalizadorServicios" %>
<%@page import="ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper"%>

<html:html>
	<bean:define id="parentMenuNumber" value="1" type="java.lang.String"/>
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
		
		    var selectedIndex;
		    
			<bean:define id="ide" type="java.lang.String">
				<bean:message key="docs.id"/>
			</bean:define>
			<bean:define id="desc" type="java.lang.String">
				<bean:message key="docs.desc.js"/>
			</bean:define>
			<bean:define id="ext" type="java.lang.String">
				<bean:message key="docs.ext.js"/>
			</bean:define>
		   
			function validateFieldsLengths(){
		        return true;
			}
		
			function validateForm(){
				var formulario = document.getElementById("Frm_1");
				if (formulario.id.value == ""){
					alert('<bean:message key="errors.required" arg0="<%=ide%>"/>');
		            return false;
		        }
		        if (formulario.description.value == ""){
		           alert('<bean:message key="errors.required" arg0="<%=desc%>"/>');
		           return false;
		        }
		        if (formulario.extension.value == ""){
		           alert('<bean:message key="errors.required" arg0="<%=ext%>"/>');
		           return false;
		        }else{
		        	formulario.extension.value = eliminarEspacios(formulario.extension.value);
					if (!extensionesValidas(formulario.extension.value)) {
			        	alert('<bean:message key="errors.invalidExtension" arg0="<%=ext%>"/>');
			      		return false;
		           	}
		        }
		        return validateFieldsLengths();
			}
		    	
		    function extensionesValidas(cadena){
		    	for(var i=0;i<cadena.length;i++){
		    		if (!isLetterOrDigit(cadena.charAt(i))){
		    			if (cadena.charAt(i) != ',')
		    				return false;
		    		}
		    	}
		    	return true;
		    }
		    
		    function eliminarEspacios(cadena) {
		    	var aux = '';
		    	for(var i=0;i<cadena.length;i++){
		    		if (cadena.charAt(i) != ' '){
						aux = aux + cadena.charAt(i);
		    		}
		    	}
		    	return aux;
		    }
		    
		    function isLetterOrDigit(letra) {
		    	var s = /^[a-zA-Z0-9]+$/;
		    	return s.test(letra);
		    }
		    
		    function load() {
		       var formulario=document.getElementById("Frm_1");
		       formulario.id.focus();
		    }
		        
		   function disableInput(inputobject){
		   	  inputobject.disabled = true;
		   }
			   
		   function enableInput(inputobject){
		   	  inputobject.disabled = false;
		   }
		        
			function showDelButton(){
		   		var button = document.getElementById("delete");
		   		button.style.display = "";   	
		   	}
		   
			function hideDelButton(){
			   	var button = document.getElementById("delete");
			   	button.style.display = "none";
			}

		        function showAddButton(){
		        	var formulario = document.getElementById("Frm_1");
		        	document.getElementById('add').style.display = "";
		        }
		   
/*
		   	function showAddButton(){
		   		var button = document.getElementById("add");
		   		button.style.display="";
		   	}
*/		   
		   	function hideAddButton(){
		   		var button = document.getElementById("add");
		   		button.style.display = "none";
		   	}
		
		    function showUpdateDocumentButton(){
		    	var formulario = document.getElementById("Frm_1");
		    	document.getElementById("updateDocument").style.display = "";
		    }
		    
		    function hideUpdateDocumentButton(){
		    	var formulario = document.getElementById("Frm_1");
		    	document.getElementById("updateDocument").style.display = "none";
		    }
		        
		
		    function addDocument() {
		        var formulario = document.getElementById("Frm_1");
		        var ctrl = document.getElementById("documents");
		        var elem, exists;
		        var value = formulario.id.value;
		        
		        if (!validateForm())
		            return;
		        
		        exists = false;
		        for (i = 0; i < ctrl.options.length; i++) {
		           if (ctrl.options[i].value == value){
		              exists = true;
		              break;
		           }
		        }
		        
		        if (exists) {
		            alert('<bean:message key="docs.error1"/>');
		        } else {
		           formulario.action = '<html:rewrite page="/newdocument.do"/>';
		           formulario.submit();
		        }
		    }
		        
		
		    function updateDocumento()  {
		
		        var formulario = document.getElementById("Frm_1");
		        var ctrl = document.getElementById("documents");
		        var elem, exists;
		        var value = formulario.id.value;
		
		        if (!validateForm())
		            return;
		        
		        exists = false;
		        for (i = 0; i < ctrl.options.length; i++) {
		           if (ctrl.options[i].value == value) {
		              exists = true;
		              break;
		           }
		        }
		        
		        if (!exists) {
		            alert('<bean:message key="docs.error2"/>');
		        } else {
		           formulario.action = '<html:rewrite page="/updatedocument.do"/>';
		           formulario.submit();
		        }
		    }
		
		
		    function doneUpdate() {
		
		        var ctrl = document.getElementById("documents");
		        var elem;
		
		    	//ctrl.removeChild(ctrl.options[ctrl.selectedIndex]);
		    
		        elem = document.createElement("OPTION");
		        //elem.text     = Frm_1.description.value;
		        //elem.value    = Frm_1.id.value;
		        //ctrl.add(elem);
		        ctrl.options[ctrl.selectedIndex] = new Option(Frm_1.description.value, Frm_1.id.value);
		        elem.selected = true;
		        parent.modificaDocumentos("modificar", Frm_1.id.value, Frm_1.description.value);
		    }
		
		
		    function doneAdd(){
		
		        var ctrl = document.getElementById("documents");
		        var formulario = document.getElementById("Frm_1");
		        
		        /*var elem;
				elem = document.createElement("OPTION");
		        elem.text     = formulario.description.value;
		        elem.value    = formulario.id.value;
		        ctrl.add(elem);
		        elem.selected = true;*/
		        ctrl.options[ctrl.length] = new Option(formulario.description.value, formulario.id.value);      
				ctrl.selectedIndex = ctrl.length;
				sortlist(formulario.id.value);
		        formulario.id.disabled = true;
		        showUpdateDocumentButton();
		        hideAddButton();
		        parent.modificaDocumentos("nuevo", formulario.id.value, formulario.description.value);
		    }
		        
		    function sortlist(sel) {
				var lb = document.getElementById("documents");
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
					if (arrValues[i] == sel)
						lb.selectedIndex = i;
				}
			}
	        
	        function getValue(ctrl, texto){
				var i=0;
				for(i=0; i<ctrl.options.length; i++){
					if(texto == ctrl.options[i].text)
						return ctrl.options[i].value;
				}
			}
		        
		    function doneLoad(id, description, extension, signatureHook, contentHook) {
		        
		   		var formulario = document.getElementById("Frm_1");
		        formulario.id.value = id;
		        formulario.description.value = description;
		        formulario.extension.value = extension;

//<!-- COMENTADA INFORMACION DE FIRMA Y CONTENIDO YA QUE NO ESTA DESARROLLADA LA FUNCIONALIDAD-->
		        //formulario.signature.value = signatureHook;
		        //formulario.content.value = contentHook;
		        
			 <%
		         if(writeFlag == "1"){
		     %>                                             	                        
		        showUpdateDocumentButton();
		        formulario.id.disabled = true;
		        hideAddButton();
			 <%
				}
		     %>                                             	                        
		    }
		    
		    function nuevoDocument(){
		    	var formulario = document.getElementById("Frm_1");
		        formulario.id.value = "";
		        formulario.description.value = "";
		        formulario.extension.value = "";
//<!-- COMENTADA INFORMACION DE FIRMA Y CONTENIDO YA QUE NO ESTA DESARROLLADA LA FUNCIONALIDAD-->
		        //formulario.signature.value = "";
		        //formulario.content.value = "";
		        
		        var ctrl = document.getElementById("documents");
		        ctrl.selectedIndex = -1;
		        formulario.id.disabled = false;
		        
		        hideUpdateDocumentButton();
		        showAddButton();
		        
		    }
		        
		    function loadDocument() {
		
		    	var formulario = document.getElementById("Frm_1");
		        var id = formulario.documents.options[formulario.documents.selectedIndex].value;
		        
		        formulario.action = "<html:rewrite page="/loaddocument.do"/>?id=" + id;
		        formulario.submit();
		    }
		        
		
		    function doneDelete() {
		
		       var formulario = document.getElementById("Frm_1");
		       var ctrl = document.getElementById("documents");
		       var codigo = ctrl.options[ctrl.selectedIndex].value;
		       ctrl.removeChild(ctrl.options[selectedIndex]);
		
		       formulario.id.value = "";
		       formulario.description.value = "";
		       formulario.extension.value = "";
//<!-- COMENTADA INFORMACION DE FIRMA Y CONTENIDO YA QUE NO ESTA DESARROLLADA LA FUNCIONALIDAD-->
		       //formulario.signature.value = "";
		       //formulario.content.value = "";
		       
		       hideUpdateDocumentButton();
		       formulario.id.disabled = false;
		       
		       parent.modificaDocumentos("eliminar", codigo, "");
		    }
		        
		    function deleteDocument() {
		
		    	var formulario = document.getElementById("Frm_1");
		        var ctrl = document.getElementById("documents");
		        selectedIndex = ctrl.selectedIndex;
		        
		        if (selectedIndex == -1)
	                return;
		        
		        var id = ctrl.options[selectedIndex].value;
		        
		        formulario.action = "<html:rewrite page="/deldocument.do"/>?id=" + id;
		        formulario.submit();
		    }
		
		</script>
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
   </head>
    <body onload="load()">
    
    
<!-- NUEVO -->

		<div class="cuerpo">
		<div class="cuerporight">
       			<div class="cuerpomid">
       				<h1><bean:message key="menu.configurador" /></h1>

				<div class="tabs_submenus">
					<ul>
						<li class="subOff">
							<h3><a href="procedure.do"><bean:message key="procedures.prococedures" /></a></h3>
						</li>
						<li class="subOn">
							<h3><a><bean:message key="menu.tiposDocs" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="hooks.do"><bean:message key="menu.auth.conn" /></a></h3>
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
        				<form name="Frm_1" id="Frm_1" action='<html:rewrite page="/newdocument.do"/>' method="post" target="work">

					<div class="seccion_large"> 
						<div class="cuerpo_sec fila_sub clearfix">
							<ul>
							<%if(writeFlag == "1"){%>                                             	            
								<li id="newDocument" name="newDocument">
								 	<a href="javascript:nuevoDocument();" class="col_nuevo">
									<bean:message key="button.label.new"/>
									</a>
								</li>
				 			<%}%> 
							
							<%if(writeFlag == "1"){%> 
								<li id="updateDocument" name="updateDocument" style="display: none">
									<a href="javascript:updateDocumento();" class="col_guardar">
									<bean:message key="button.label.save"/>
									</a>
								</li>

								<li id="add" name="add" style="display: none">
									<a href="javascript:addDocument();" class="col_nuevo">
									<bean:message key="button.label.add"/>
									</a>
								</li>

								<li id="delete" name="delete">
									<a href="javascript:deleteDocument();" class="col_eliminar">
									<bean:message key="button.label.delete"/>
									</a>
								</li>
							<%}%>	

							</ul>
						</div>
					</div> <!-- fin seccion -->

					<div class="clear">
					</div> <!-- fin clear -->

					<div class="seccion"> 
						<p class="fila">
					            <label class="sml sub"><bean:message key="docs.subtitle"/></label>
						    <div class="barraHz mdl">
					            <select class="multiple minmdl" id="documents" name="documents" size="6" onchange="loadDocument()">
						                <%
								ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
				        		        Documentos documents = oServicio.getDocuments(SesionAdminHelper.obtenerEntidad(request));
				                		Documento document;
						                for (i=0; i < documents.count(); i++)  {
						                    document = documents.get(i);
						                    out.write("<option value=\"" + document.getId() + "\">" + document.getDescription() + "</option>\n");
				        		        }
				                		%>
					            </select>
					            </div>
						
						</p>
					</div>

					<div class="seccion"> 
						<p class="fila_sub">
							<label class="sml" ><bean:message key="docs.id"/></label>
						        <input type="text" class="xsml" name="id" id="id" value="" maxlength="32" />
						</p>
						<p class="fila_sub">
						        <label class="sml"><bean:message key="docs.desc"/></label>
						        <input type="text" class="mdl" name="description" id="description" value="" maxlength="256"/>
						</p>
						<p class="fila_sub">
						        <label class="sml" ><bean:message key="docs.ext"/></label>
						        <input type="text" class="xsml" name="extension" id="extension" value="" maxlength="128"/>
						</p>
<!-- COMENTADA INFORMACION DE FIRMA Y CONTENIDO YA QUE NO ESTA DESARROLLADA LA FUNCIONALIDAD CORRESPONDIENTE-->
<!--						
						<p class="fila">
						        <label class="sml sub"><bean:message key="docs.sign"/></label>
							<%
							Conectores hooks;
							Conector hook;
							%>
							<div class="barraHz mdl">
							<select class="single minmdl" type="text" name="signature" id="signature" >
							<option value=""></option>
							<%
					                	hooks = oServicio.getHooksByType(TipoConector.SIGNATURE_VERIFY, SesionAdminHelper.obtenerEntidad(request));
					                	for (i=0; i < hooks.count(); i++) {
					                    	hook = hooks.get(i);
						                    out.write("<option value=\"" + hook.getId() + "\">" + hook.getDescription() + "</option>\n");
						                }
						        %>
						        </select>
						        </div>
						</p>
						<p class="fila">
						        <label class="sml sub" ><bean:message key="docs.content"/></label>
						        <div class="barraHz mdl">
							<select class="single minmdl" type="text" name="content" id="content">
							<option value=""></option>
					                <%
					                	hooks = oServicio.getHooksByType(TipoConector.DOCUMENT_VERIFY, SesionAdminHelper.obtenerEntidad(request));
						                for (i=0; i < hooks.count(); i++) {
						                    hook = hooks.get(i);
						                    out.write("<option value=\"" + hook.getId() + "\">" + hook.getDescription() + "</option>\n");
						                }
						        %>
						        </select>
						        </div>
						</p>
-->
					</div>


					</form>		
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

<!-- FIN NUEVO -->
	
	<iframe name="work" id="work" frameborder="0" tabindex="-1" src="jsp/blank.html"
	style="position: absolute; margin-top:0px; margin-left: 50%; left: -350px; width:727px; height:470px; visibility:visible"></iframe>
	    	
    </body>
</html:html>