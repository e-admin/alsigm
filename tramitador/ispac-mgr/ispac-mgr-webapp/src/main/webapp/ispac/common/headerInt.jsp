<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<tr style="width:100%;">
    <td width="100%">
	<div id="cabecera_int_left">
		<h1><bean:message key="main.company"/></h1>
	</div>
	<div id="cabecera_int_right">
		<h3><bean:message key="main.productName"/></h3>
		<p class="salir"><a 
		href="javascript:sure('exit.do', '<bean:message key="ispac.action.application.close"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>');"><bean:message key="menu.salir"/></a></p>
	</div>
    </td>
</tr>



