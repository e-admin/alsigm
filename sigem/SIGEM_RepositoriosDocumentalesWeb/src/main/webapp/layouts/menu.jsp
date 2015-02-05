<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-menu.tld" prefix="menu"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html:html>
<head>
	<title><bean:message key="message.common.title"/></title>
	
    <ieci:baseInvesDoc/>
	<link rel="stylesheet" type="text/css" href="include/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
	<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<script type="text/javascript" >
var appBase = '<c:out value="${pageContext.request.contextPath}"/>';



function cargaVolumenes()
{
	parent.data.location.replace(appBase + "/volume/adminVolume.jsp");
		//parent.header.document.getElementById('status').innerHTML="<bean:message key="message.comun.miga.inicio.volumenes"/>";
}


</script>
</head>

<body onload="cargaVolumenes();">
	<div class=contenedora align="center">
    	<div class="cuerpo" style="width:889px">
      		<div class="cuerporight">
        		<div class="cuerpomid">
        			<div class="submenu3">
           				<ul>
        					<li id="lista2" class="submen1on" onclick="cargaVolumenes();"><img id="img1" src="include/images/subme3_on.gif" />
        						<bean:message key="message.comun.pestana.volumenes"/>
        						<img id="img2" src="include/images/subme3_on_0.gif" />
        					</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- >table width="100%">
<c:if test="${sessionScope.user.hasAccessUser}">
<tr>
	<td class="menuItem" onclick="cargaUsuarios();">
		Usuarios
	</td>
</tr>
</c:if>
<c:if test="${sessionScope.user.hasAccessVol}">
<tr>
	<td class="menuItem" onclick="cargaVolumenes();">Volumenes</td>
</tr>
</c:if>

<c:if test="${sessionScope.user.hasAccessSys}">
<tr>
	<td class="menuItem" onclick="cargaArchivadores();">Archivadores</td>
</tr>
</c:if>
</table -->


</body>


</html:html>