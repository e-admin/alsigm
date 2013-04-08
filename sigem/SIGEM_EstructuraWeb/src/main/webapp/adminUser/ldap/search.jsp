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
	<script language='javascript'>	
	var typeTree = '<c:out value="${requestScope.typeTree}"/>';
	parent.typeTree = typeTree;
	<html:messages id="msg" message="true" bundle="general_errors">
		/*
		if( typeTree != "1") {
			parent.estirarIframePropiedades();
		} else 
		    parent.encogerIframePropiedades();	
		*/    
		parent.propiedadesText.style.visibility = "hidden";
		window.alert("<bean:write name='msg'/>");
	</html:messages>
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
	function envia()
	{
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {
		
			if( document.forms[0].tipoBusqueda.value == "1") {
				parent.tree.location.href = appBase + '/user/ldap/userTree.do';
			} else if( document.forms[0].tipoBusqueda.value == "2") {
				parent.tree.location.href = appBase + '/user/ldap/groupTree.do';
				
			}
			
			document.getElementById('campoBusqueda').value="";
			document.getElementById('submitted').value="";
			document.getElementById('searchForm').submit();
		}
		else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}
	}
	<c:if test="${requestScope.enc}" >
		var dn = '<c:out value="${requestScope.dn}"/>';
		/*
		if( typeTree != undefined ) {
			if(typeTree == "1") parent.fillBotones();
			else parent.emptyBotones();
		}*/
		//parent.titulo.innerHTML = '<bean:message key="message.comun.etiqueta.propiedades" />';
		parent.propiedades.location.href = appBase + '/user/ldap/properties.do?dn='+dn+"&typeTree="+typeTree;
		
	</c:if>
	function enviar(){
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {
			if(document.getElementById('campoBusqueda').value != "") {
				parent.propiedadesText.style.visibility = "visible";
				//parent.estirarIframePropiedades();
				document.forms[0].action = appBase + "/user/ldap/search.do?submitted=true";
				document.forms[0].submit();
			}
		}else{
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}
	}
	
	</script>	
</head>
<body topmargin="0" leftmargin="0">
	
		<table cellpadding="0" cellspacing="0" border="0">
		<tr>
			<html:form action="/user/ldap/search" method="post" styleId="searchForm">
			<td><html:text property="valor" size="15" styleId="campoBusqueda" /></td>
			<td>
			<html:select property="tipoBusqueda" onchange="envia();" >
				<html:option value="1" key="message.users"/>
				<html:option value="2" key="message.groups"/>
			</html:select>
			</td>
			<td><input type="button" value='<bean:message key="message.search.button.find" />' onclick="enviar();" class="okEO" /></td>
			<html:hidden property="submitted" styleId="submitted" value="true" />
			</html:form>
		</tr>
		</table>
	
</body>
</html>
