<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html:html>


<head>
  <ieci:baseInvesDoc/>    
	<title><bean:message key="message.common.title"/></title>
	<link rel="stylesheet" type="text/css" href="include/css/logout.css"/>
</head>

<body>

<% response.sendRedirect("../" + AutenticacionAdministracion.obtenerUrlLogout(request)); %>

</body>


<!-- 
<body>
	<table class="tableBase">
		<tr>
			<td width="100%">
				<table class="tableBase">
					<tr>
						<td class="cabecera" valign="top" align="left"><img hspace="10" src="include/images/logo_idocWhite.gif"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="tableBase" height="350px">
					<tr>
						<td width="50%" align="right">
							<table>
								<tr>
									<td><img src="include/images/desk.gif" border="0" width="392px" height="351px"/>
									</td>
								</tr>
							</table>
						</td>
						<td width="50%" align="left"  class="index"> 
							<c:choose>
								<c:when test="${param.logout eq null}">
									<%-- <bean-el:message key="message.logout.timeout" arg0="${session_life_time}" /><br> --%>
									<bean:message key="message.logout.apologise"/><br>
								</c:when>
								
								<c:when test="${param.logout eq 'error'}">
									<bean:message key="message.logout.notprivilege"/><br>
								</c:when>
								
								<c:when test="${param.logout ne null}">
									<bean:message key="message.logout.executed"/><br>
								</c:when>
								
							</c:choose>
							<bean:message key="message.logout.connect"/>
						  <html:link page="/acs/login.jsp" styleClass="relogin" ><bean:message key="message.logout.connect.link"/></html:link>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>							
</body>
 -->

<script type="text/javascript">

	function getParent(obj) {	
		return obj.top;
	}
	
	if(parent.frames.length)
		top.location=document.location;

	<%-- 
	if (window.opener){
	
		var obj = window.opener; 
		var principal = getParent(obj); // Da un pete en explorer
		
		
		principal.location.replace(document.location);
		window.close();
		
	}--%>
	
</script>

</html:html>