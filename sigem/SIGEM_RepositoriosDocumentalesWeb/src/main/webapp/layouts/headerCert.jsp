<%@ page import="java.util.HashMap"%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

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
		<table class="tableBase" border="0" cellpadding="0" cellpadding="0">
			<tr>
				<td valign="top" colspan="2" align="left" class="user"><img hspace="10" src="include/images/logo_idoc.gif"/></td>
			</tr>
			<tr>

				<td align="left" class="blue">&nbsp;
				<logic:present name="user" >
					<bean:write name="user" property="userName"/>
				</logic:present>
				</td>
				<td align="right" class="blue">
					<table class="header" cellspacing="1px" cellpadding="0" border="0">
						<tr>
							<td class="header" id="td14" onmouseover="changeColor(this,true);" onmouseout="changeColor(this,false);">
								<html:link href="javascript:welcome();" styleClass="header">Inicio</html:link></td>
							<td class="header" id="td13" onmouseover="changeColor(this,true);" onmouseout="changeColor(this,false);">
								<html:link href="javascript:logout();" styleClass="header">Salir</html:link></td>
							<td class="header" id="td14" onmouseover="changeColor(this,true);" onmouseout="changeColor(this,false);">
								<html:link href="javascript:showHelp()" styleClass="header">Ayuda</html:link></td>
							<td class="header" id="td14" onmouseover="changeColor(this,true);" onmouseout="changeColor(this,false);">
								<html:link href="javascript:addCertificate()" styleClass="header">Alta Certificado</html:link></td>	
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top" colspan="2" align="left" id="status" class="status">Inicio</td>
			</tr>
		</table>
	</body>
</html:html>