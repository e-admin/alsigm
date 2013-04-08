<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/common.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<script src="include/js/validations.js" type="text/javascript"></script>
	<c:if test="${requestScope.enc}" >
		<script language='javascript'>	
			var id = '<c:out value="${requestScope.id}" />';
			var tipo = '<c:out value="${requestScope.tipo}" />';
			var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
			if (tipo != 1 ){
					parent.propiedades.location.href = appBase + '/user/bd/usersList.do?id='+id+'&tipo='+tipo;
					parent.edicion.location.href = appBase + '/blank.do';
			}
			else{
					parent.propiedades.location.href = appBase + '/user/bd/userMenu.do?id='+id;					
					parent.edicion.location.href = appBase + '/blank.do';		
			}
		</script>	
	</c:if>
	
<script type="text/javascript">
	function enviar(){
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {
			document.forms[0].submit();
		}else{
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}
	}
</script>	
	
</head>

<body>
	<div id="contenedora">
		<html:messages id="msg" message="true" bundle="api_errors">
			<script>
				window.alert("<bean:write name='msg'/>");
			</script>
		</html:messages>
		<html:form action="/user/bd/search" method="post" styleId="searchForm">
			<table border="0">
				<tr>
					<td><html:text property="valor" size="15"/>
					</td>
					<td>
						<html:select property="tipoBusqueda" style="width: 100px;" >
							<html:option value="1" key="message.users"/>
							<html:option value="2" key="message.groups"/>
							<html:option value="32" key="message.departaments"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<!-- td colspan="2" align="left"> <html:submit value="Buscar" styleClass="genericButton" /></td -->
					<td colspan="2" align="left"> <!-- <input type="submit" value="Buscar" class="ok" /> -->
						<input type="button" onclick="enviar();" value='<bean:message key="message.comun.boton.buscar"/>' class="ok" />
					</td>
				</tr>
				<html:hidden property="submitted" value="true" />
			</table>
		</html:form>
	</div>
</body>

</html>
