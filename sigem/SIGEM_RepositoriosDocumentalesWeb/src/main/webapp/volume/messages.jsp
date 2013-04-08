<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>

<script language="javascript" >

function actualiza()
{
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
	var tipo = '<c:out value="${requestScope.tipo}"/>'; 
	
	if (tipo == 2) // Se ha creado o borrado una lista 
	{
		parent.propiedades.location.href = appBase + '/blank.do';
		parent.listTree.location.href = appBase + '/volume/listTree.do';
	}
	else if (tipo == 1) // Se ha creado o borrado un repositorio
	{
		parent.propiedades.location.href = appBase + '/blank.do';
		parent.repTree.location.href = appBase + '/volume/repositoriesTree.do';
	}
	else if (tipo == 4 ) // Se ha borrado o creado un volumen
	{
		parent.propiedades.location.reload();
	}
}
</script>


</head>
<body onload="actualiza();">
	<div class="contenedora">
	<table height="60%">
		<tr>
			<td>
				<div class="encabezado1">
					<html:messages id="msg" message="true">
						<bean:write name="msg"/>
					</html:messages>
				</div>
			</td>
		</tr>
	</table>
	</div>
</body>

</html>