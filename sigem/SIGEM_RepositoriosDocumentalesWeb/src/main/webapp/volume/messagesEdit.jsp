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
		var idList='<c:out value="${requestScope.id}"/>';
		parent.propiedades.location.href = appBase + '/volume/volumeList.do?id='+idList+'&tipo=2';
		parent.listTree.location.href = appBase + '/volume/listTree.do';
	}
	else if (tipo == 1) // Se ha creado o borrado un repositorio
	{
		var idRep='<c:out value="${requestScope.id}"/>';
		parent.propiedades.location.href = appBase + '/volume/volumeList.do?id='+idRep+'&tipo=1';
		parent.repTree.location.href = appBase + '/volume/repositoriesTree.do';
	}
	else if (tipo == 4 ) // Se ha borrado o creado un volumen
	{
		var idVol='<c:out value="${requestScope.id}"/>';
		var tipoHis='<c:out value="${requestScope.tipoHis}"/>';
		parent.propiedades.location.href = appBase + '/volume/volumeList.do?id='+idVol+'&tipo='+tipoHis;
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
				
					<c:choose> 
					  <c:when test="${tipo eq '2'}" > 
					  	<bean:message key="message.lista.edit"/>
					  </c:when> 
					  <c:when test="${tipo eq '1'}" > 
						<bean:message key="message.repositorio.edit"/>
					  </c:when> 
					  <c:otherwise>
					  	<bean:message key="message.volumen.edit"/>
					  </c:otherwise> 
					</c:choose> 
				</div>
			</td>
		</tr>
	</table>
	</div>
</body>

</html>