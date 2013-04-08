	<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
	
	<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
	<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>
	
	<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
	%>
	
	
	
	<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
	<html>
	<head>
	<ieci:baseInvesDoc/>
	<link rel="stylesheet" rev="stylesheet" href="include/css/tabs.css">
	<link rel="stylesheet" rev="stylesheet" href="include/css/estilos.css">
	<script src="include/js/docobj.js" type="text/javascript"></script>
	<script src="include/js/tabs.js" type="text/javascript"></script>
	<script src="include/js/validations.js" type="text/javascript"></script>
	
	<script language="javascript">
	
	function testVerify(){
		alert(alert);	
	}
	
	function checkForm(form)
	{
		var error = "";
	
		var message = "<bean:message key="message.repositorio.nuevo.error"/>";
	
		<c:choose>
		<c:when test="${repositoryForm.type == 1}"> <%-- Constante VolumeDefs.REP_TYPE_FTP --%>
			if (isEmptyText(form.server.value)){
				error +='<bean:message key="message.repositorio.nuevo.error.nombrenulo"/>';
			}
			else{
				var typeServer = form.typeServer.options[form.typeServer.selectedIndex].value;
				if (typeServer == 1 && !isIpCheck(form.server.value ) ) // Es una dirección ip
					error +="<bean:message key="message.repositorio.nuevo.error.ipincorrecta"/>";
			}	
			if (!isBlank(form.port.value)){
				if (!isNumeric(form.port.value)){
					error += '<bean:message key="message.repositorio.nuevo.error.puertoincorrecto"/>';
				}
			}
			else
				error +='<bean:message key="message.repositorio.nuevo.error.portIsEmpty"/>';
			
			if (isBlank(form.user.value)){
				error +='<bean:message key="message.repositorio.nuevo.error.userIsEmpty"/>'; 
			}
			if (isBlank(form.pwd.value)){
				error +='<bean:message key="message.repositorio.nuevo.error.passwordIsEmpty"/>'; 
			}
		</c:when>
		</c:choose>
	
		var ruta = form.path.value;
		
		if (isEmptyText(ruta)){
			error +='<bean:message key="message.repositorio.nuevo.error.rutanula"/>'; 
		}
		var barra = '\\';
		if (ruta.indexOf(barra) != -1){
			error +='<bean:message key="message.repositorio.nuevo.error.barraruta"/>'; 			
		}
					
		if (error.length > 0){
				message = message.concat(error);
				alert(message);
				return false;
		}
		return true;
	}
	function cancel()
	{
	    var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) { 
			document.location.href="<html:rewrite forward='cancel'/>";
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}		
	}	
	
	function aceptar(){
	    var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	    var formulario = document.forms[0];
		if( check == "false" ) { 
			if(checkForm(formulario)){
				document.forms[0].submit();
			}
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}	
	}		
	
	</script>
	</head>
	
	<body>
	
	
	    <div id="contenedora">
			<div class="cuerpo" style="width:530px; height:195px;">
				<html:form action="/volume/repositoryNew" method="post" styleId="form" onsubmit="return checkForm(this);">
		   		<div class="cuerporight">
		    		<div class="cuerpomid">
						<h1><bean:message key="message.repositorio.nuevo.paso2"/></h1>
						<div class="submenu3">
	           				<ul>
	        					<li class="submen1on" id="tab1"><img src="include/images/subme3_on.gif" /><bean:message key="message.comun.pestana.deatalle"/></li>
	        					<label id="tabmiddle1"><img src="include/images/subme3_on_0.gif"></label>
							</ul>
						</div>					
						<div class="cuadro" style="height: 165px;">
							
							<div id="box1">
								<html:hidden property="os" value="3"/>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.servidor"/></label>
									<label class="gr" style="width:350px;"></label>
									<label class="gr" style="width:120px;">
										<html:select property="typeServer">
											<html:options collection="typeServerCollection" property="value" labelProperty="label" />
										</html:select>
									</label>
									<label class="gr"><html:text property="server" style="width:350px;"></html:text></label>									
								</div>		
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.puerto"/></label>
									<label class="gr"><html:text property="port" style="width:350px;"/></label>									
								</div>							    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.usuario"/></label>
									<label class="gr"><html:text property="user" style="width:350px;"/></label>									
								   </div>	
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.contrasena"/></label>
									<label class="gr" style="width:190px;"><html:password property="pwd" style="width:350px;"/></label>									
								</div>								    						    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.ruta"/></label>
									<label class="gr"><html:text property="path" style="width:350px;"/></label>									
								</div>					    					
							</div>						
		       			</div>  
						<html:hidden property="id"/>
						<html:hidden property="name"/>
						<html:hidden property="type"/>
						<html:hidden property="description"/>
						<input type="hidden" name="step" value="2" />
						<!-- <html:submit value="Crear" styleClass="okEO"/> -->
						<input type="button" value="<bean:message key="message.comun.boton.crear"/>" class="okEO" onclick="aceptar();" />
						<input type="button" value="<bean:message key="message.comun.boton.cancelar"/>" class="ok" onclick="cancel();" />
						</html:form>				
	
		   	</div> 	
			
			<div class="cuerpobt" style="width:530px">
		    	<div class="cuerporightbt">
		      		<div class="cuerpomidbt"></div>
		    	</div>
			</div>
		</div>
	
	</body>
	
	<!-- 
	<body onload="choosebox(1,9);">
	
	<%-- 
	<div id="capaFondo" class="capaFondo"> </div>
	--%>
	<div class="titulo" > Nuevo Repositorio - Paso 2 de 2 </div>
	
	<html:form action="/volume/repositoryNew" method="post" styleId="form" onsubmit="return checkForm(this);">
	
	
	<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,9)">
	<table summary="" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="tableft" ><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
		<td id="tabmiddle1">Detalle</td>
		<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	</tr>
	</table>
	</div>
	
	
	<div id="box1">
	<c:choose>
	<c:when test="${repositoryForm.type == 1}"> <%-- Constante VolumeDefs.REP_TYPE_FTP --%>
	<table style="margin-left: 5px;">
		<tr>
			<td>Plataforma</td>
			<td>
				<html:select property="os">
		      		<html:options collection="plataformCollection" property="value" labelProperty="label" />
		     	</html:select>
		     </td>
		</tr>     
		<tr>
			<td>Servidor</td><td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<html:select property="typeServer">
					<html:options collection="typeServerCollection" property="value" labelProperty="label" />
				</html:select>
			</td>	
			<td><html:text property="server"></html:text> </td>
		</tr>
		<tr>
			<td>Puerto</td><td><html:text property="port"/></td>
		</tr>
		<tr>
			<td>Usuario</td><td><html:text property="user"/></td>
		</tr>
		<tr>
			<td>Contraseña</td><td><html:password property="pwd"/></td>
		</tr>
		<tr>
			<td>Ruta acceso:</td> <td><html:text property="path"/></td>
		</tr>
	</table>
	</c:when>
	<c:when test="${repositoryForm.type== 3}"> <%-- Constante VolumeDefs.REP_TYPE_PFS --%>
	<table style="margin-top: 10px; margin-left: 20px;">
		<tr>
			<td>Ruta acceso:</td> <td><html:text property="path"/></td>
		</tr>
	</table>
	</c:when> 
	</c:choose>
	</div>
	<html:hidden property="id"/>
	<html:hidden property="name"/>
	<html:hidden property="type"/>
	<html:hidden property="description"/>
	<input type="hidden" name="step" value="2" />
	<html:submit value="Crear" styleClass="boton1"/>
	<input type="button" value="Cancelar" class="boton2" onclick="cancel();" />
	</html:form>
	</body>
	 -->
	
	
	
	</html>