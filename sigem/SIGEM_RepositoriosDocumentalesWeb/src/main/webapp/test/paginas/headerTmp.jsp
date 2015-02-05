<%@ page import="java.util.HashMap"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>

<%
	HashMap params   = new HashMap();
	params.put("logout", "true");
	pageContext.setAttribute("params", params);
%>
<html:html>
	<head>
	    <ieci:baseInvesDoc/>
	    <%-- 
		<c:out value='<base href=\"http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/\">' escapeXml="false"/>	
		--%>
		
		<link rel="stylesheet" type="text/css" href="include/css/header.css"/>
		<link rel="stylesheet" type="text/css" href="include/cssTemp/estilos.css"/>
		<script src="include/js/modalWindow.js" type="text/javascript"></script>

				
		<script>
			var popupWindow;
			var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
			function logout() {
				if(confirm("<bean:message key="message.header.confirm.logout"/>"))
					window.parent.document.location.href = '<html:rewrite page="/logout.do" name="params"/>';
			}
			function welcome() {
				// resetTree();
				window.parent.document.location.href = '<html:rewrite forward="Welcome"/>';
			}
			function openWindow(hr, target, width, height)
			{
				var PosX = (screen.availWidth - width)/2;
				var PosY = (screen.availHeight - height)/2;
				var caracteristicas = 'height='+height+',width='+width+',scrollbars=no,top='+PosY+',left='+PosX;
				popupWindow = window.open(hr, target, caracteristicas);
				return false;
			}
			function changeColor(cell, cond)
			{
				if(cond)
				{
					cell.style.backgroundColor = "#e6cc38";
					cell.style.border = "1px #ffffff solid";
				} 
				else
				{
					cell.style.backgroundColor = "#639ACE";
					cell.style.border = "1px solid #639ACE";
				}
			}
			function showHelp()
			{
				var width = 780;
				var height = 580;
				path = appBase + '/help/index.htm';
				ShowWindow(path,width,height);
			}
			function addCertificate(){
				var width = 300;
				var height = 270;
				path = appBase + '/acs/addCertificate.jsp';
				ShowWindow(path,width,height);
			}
		</script>
	</head>
<body>

    <div id="contenedora" >
		<div id="cabecera">
	   		<div id="logo">
	   			<img src="/SIGEM_EstructuraOrganizativaWeb/img/logo.gif" alt="sigem" />
	   		</div>
	   		<logic:present name="user" >
				<bean:write name="user" property="userName"/>
			</logic:present>
			<div class="salir">
				<img src="/SIGEM_EstructuraOrganizativaWeb/img/exit.gif" alt="salir" width="26" height="20" class="icono" />
				<span class="titular">
					<a href="javascript:logout();">Salir</a>
				</span>
			</div>
	 	</div>
	 	<p>asdfa</p>
	 	<div class="usuario">
		   	<div class="usuarioleft">
		    	<p>Estructura Organizativa</p>
			</div>
	    	<div class="usuarioright">
	      		<div style="padding-top: 8px; padding-right: 24px;">
			   		<a href="javascript:addCertificate()"><img src="/SIGEM_EstructuraOrganizativaWeb/img/help.gif" style="border: 0px" alt="ayuda" width="16" height="16" /></a>
			   	</div>
	    	</div>
		 </div>
		 <br />

	</div>

</body>
</html:html>