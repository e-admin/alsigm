<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../../include/css/adminApp.css"/>
<link rel="stylesheet" type="text/css" href="../../include/css/estilos.css"/>
<script src="../../include/js/validations.js" type="text/javascript"></script>

<script language="javascript">
var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
var id = '<bean:write name='usersListForm' property='id'/>';
var tipo ='<bean:write name='usersListForm' property='tipo'/>';

function userProperties(id)
{
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {	
		parent.parent.edicion.location.href = appBase + '/user/bd/userProperties.do?id='+id;
	} else {
		window.parent.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}	
}
function userEdit(id, mgrDeptId){
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {	
		if (mgrDeptId == '' )
			parent.parent.edicion.location.href = appBase + '/user/bd/userEdit.do?id='+id;
		else
			parent.parent.edicion.location.href = appBase + '/user/bd/userEdit.do?id='+id + '&mgrDeptId=' + mgrDeptId;
	} else {
		window.parent.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}	
}
function properties()
{
	if (tipo == 32 )
		parent.parent.edicion.location.href = appBase + '/user/bd/deptProperties.do?id='+id;
	else
		parent.parent.edicion.location.href = appBase + '/user/bd/groupProperties.do?id='+id;
	
}
function edit ()
{
	if (tipo == 32 )
		parent.parent.edicion.location.href = appBase + '/user/bd/deptEdit.do?id='+id;
	else
		parent.parent.edicion.location.href = appBase + '/user/bd/groupEdit.do?id='+id;
}
function newItem(id,tipo)
{
	parent.parent.edicion.location.href = appBase + '/user/bd/deptNew.do?idPadre='+id;
}

function newUser(id)
{
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {
		parent.parent.edicion.location.href = appBase + '/user/bd/userNew.do?idPadre='+id;
	} else {
		window.parent.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}	
}

function changeStatus(idUser)
{
	parent.document.location.href = appBase + '/user/bd/usersList.do?id='+id+'&tipo='+tipo+'&idUser='+idUser;
}

</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

		<table border class="tableBase" width="100%">
			<tr>
				<th width="60%"><bean:message key="message.comun.etiqueta.nombre"/></th>
				<th colspan="3">&nbsp;</th>
			</tr>
			
			<logic:iterate name="usersListForm" property="usersList" id="user" >
			<tr>
				<td><b><bean:write name="user" property="nombre" /></b></td>
				<td><a href="javascript:userProperties('<bean:write name='user' property='id'/>');"><bean:message key="message.comun.etiqueta.propiedades"/></a></td>
				<td><a href="javascript:userEdit('<bean:write name='user' property='id'/>', '<bean:write name='user' property='mgrDeptId'/>');"><bean:message key="message.comun.etiqueta.editar"/></a></td>
			</tr>
			</logic:iterate>			

						
			<c:if test="${usersListForm.tipo == 32}"> <%-- Muestra la opcion de Nuevo usuario, solo si es departamento --%>
			<tr>
				<td colspan="4">
					<a class="menu0" href="javascript:newUser('<bean:write name='usersListForm' property='id'/>');"><bean:message key="message.usuario.etiqueta.nuevousuario"/></a>		
				</td>
			</tr>
			</c:if>	       		
		</table>
</body>
</html>