<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ page import="ieci.tecdoc.mvc.util.Constantes" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>

<ieci:baseInvesDoc/>

<link rel="Stylesheet" rev="Stylesheet" href="include/css/tabs.css" />
<link rel="Stylesheet" rev="Stylesheet" href="include/css/estilos.css" />
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<script src="include/js/docobj.js" type="text/javascript"></script>
<script src="include/js/tabs.js" type="text/javascript"></script>
<script src="include/js/edition.js" type="text/javascript"></script>
<script src="include/js/validations.js" type="text/javascript"></script>

<script language="javascript">


	function validateForm()
	{		
		return true;
	}


	function confirmDelete(){	
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {	
			return confirm('<bean:message key="message.confirm.darDeBaja.user"/>');
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}	
	}
	
	function aceptar(){
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {
			if(validateForm()){
				document.forms[0].submit();
			}
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}	
	}
		
</script>

<logic:present name="<%=Constantes.TOKEN_USER_CONNECTED%>" >

<%-- 
UserConnected: <bean:write name="user" property="profiles"/> <br>
UserDTO: <bean:write name="userDTO" property="profiles"/>
--%>

<logic:greaterEqual name="user" property="profile(2)" value="1"> <%-- Al menos es IUSER_STANDARD y puede ver la pagina --%>
<!--  Puede leer esto, Consultar Administrador de usuarios<br> -->
</logic:greaterEqual>

<%-- 
<c:out value="userProfile: ${sessionScope.user.userProfile}"></c:out> <br>
<c:out value="userDTOProfile: ${requestScope.userDTO.userProfile}"></c:out> <br>

<c:out value="userID: ${sessionScope.user.id}"></c:out> <br>
<c:out value="userDTOiD: ${sessionScope.userDTO.id}"></c:out> <br>
<c:out value="ManagerId: ${sessionScope.managerId}"/>
--%>

<!-- && <c:out value="mgrDeptId: ${param.mgrDeptId}"/> -->
<c:choose>
	<c:when test="${not empty param.mgrDeptId}">
		<c:set target="request" var="mgrDeptId">
			<c:out value="${param.mgrDeptId}"/>
		</c:set>
	</c:when>
	<c:otherwise> <!-- Vacio -->
		<c:set target="request" var="mgrDeptId">
			<c:out value="${sessionScope.managerId}"/>
		</c:set>
	</c:otherwise>
</c:choose>
<!-- <c:out value="mgrDeptId#${mgrDeptId}#"/> -->


<c:if test="${mgrDeptId eq sessionScope.user.id}">
	 <!-- Es manager Del Dtpo al que pertenece el usuario -->
</c:if>


<c:import url="../common/limitUserPrivilege.jsp">
	<c:param name="method" value="bd"/>
	<c:param name="mgrDeptId" value="${mgrDeptId}"/>
</c:import>





</logic:present>

<logic:notPresent name="<%=Constantes.TOKEN_USER_CONNECTED%>">

</logic:notPresent>
</head>


<body class="cuadro">
	
	<html:form action="/user/ldap/userEdit" method="post" onsubmit="return validateForm();">
	

								<h1><bean:message key="message.usuario.editar.titulo"/> <bean:message key="message.comun.pestana.perfiles"/></h1>

								<table class="tableBase" style="width:99%">
									<tr>
										<th width="30%"><bean:message key="message.usuario.etiqueta.aplicacion"/></th>
										<th><bean:message key="message.usuario.etiqueta.perfil"/></th>
									</tr>
									
									 <logic:iterate name="userFormLdap" id="aplicacion" property="listaAplicaciones" indexId="i">
									 <tr>
									 	<td><bean:write name="aplicacion" property="descripcion" /><br></td>
									 	<td>
									 		<table>
									 			<tr>
													<td>
														<c:choose>
															<c:when test="${userFormLdap.listaAplicaciones[i].editableSinPermisos}">
												 				<html-el:radio property="listaAplicaciones[${i}].perfil" value="0" style="width: 20px">Sin permisos</html-el:radio>
															</c:when>
															<c:otherwise>
												 				<html-el:radio property="listaAplicaciones[${i}].perfil" value="0" disabled="true" style="width: 20px">Sin permisos</html-el:radio>
															</c:otherwise>
														</c:choose>
									 				</td>
									 				
									 				<td>
									 					<c:choose>
									 						<c:when test="${userFormLdap.listaAplicaciones[i].editableUsuarioEstandar}">
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="1" style="width: 20px">Estandar</html-el:radio>
									 						</c:when>
									 						<c:otherwise>
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="1" style="width: 20px" disabled="true">Estandar</html-el:radio>
									 						</c:otherwise>
									 					</c:choose>
													</td>
													<td>
									 					<c:choose>
									 						<c:when test="${userFormLdap.listaAplicaciones[i].editableUsuarioAdministrador}">
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="3" style="width: 20px">Administrador</html-el:radio>
									 						</c:when>
									 						<c:otherwise>
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="3" style="width: 20px" disabled="true">Administrador</html-el:radio>
									 						</c:otherwise>
									 					</c:choose>														
													</td>
									 			</tr>
									 		</table>
									 	</td>
									 </tr>
									 </logic:iterate>
								 </table>					
		
								<table style="width:95%">
									<tr>
										<td width="30%">
			<html:hidden property="submitted" value="true"/>
			<html:hidden property="guid" />
			<!-- <html:submit value="Aceptar" styleClass="okEO"/> -->
			<input type="submit" value="<bean:message key='message.comun.boton.aceptar'/>" class="ok" /> 
			
	</html:form>
										</td>
	<html:form action="/user/ldap/userDelete" method="post" onsubmit="return confirmDelete();" >
										<td>
			<html:hidden property="guid" />
			<%-- Validación ya hecha en el Action --%>
			<input type="submit" value="<bean:message key='message.comun.boton.darDeBaja'/>" class="ok" /> 
			<!--<html:submit value="Eliminar" styleClass="ok"  style="position:absolute; left:185px; top:225px; height:20px; width:87px;" /> -->
	</html:form>	
										</td>
									</tr>
								 </table>					
							
					
</body>

</html:html>