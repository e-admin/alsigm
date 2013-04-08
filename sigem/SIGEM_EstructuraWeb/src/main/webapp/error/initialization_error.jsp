<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci" %>
<%@ page import="ieci.tecdoc.mvc.util.MvcDefs" session="false"%>
<html:html>
<head>
	<title><bean:message key="message.common.title"/></title>
	<ieci:baseInvesDoc/>
	<link rel="stylesheet" type="text/css" href="include/css/error.css"/>
</head>
<body>
	<table class="tableBase" border="0">
		<tr>
			<td width="100%">
				<table class="tableBase">
					<tr>
						<td class="cabecera" align="left"><bean:message key="message.init.info"/>
						</td>
					</tr>
					<tr>
						<td class="cabecera" align="left">&nbsp;
						</td>
					</tr>
					<tr>
						<td class="cabecera" align="left"><bean:message key="message.init.check"/>
						</td>
					</tr>
					<tr>
						<td class="cabecera" align="left">&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="tableBase" align="center" border="0">
					<logic:iterate name="<%=MvcDefs.TOKEN_SYSTEM_PROPERLY_STARTED%>" id="item">  
					<tr>
						<td width="50%" class="index" align="left">
							<li><c:out value="${item.message}"/></li>
					  </td>
				  	</tr>
					</logic:iterate>
				</table>
			</td>
		</tr>
		<tr>
			<td class="cabecera" align="center">&nbsp;
			</td>
		</tr>
		<tr>
			<td class="cabecera" align="center"><bean:message key="message.init.contact"/>
			</td>
		</tr>
	</table>							
</body>
</html:html>