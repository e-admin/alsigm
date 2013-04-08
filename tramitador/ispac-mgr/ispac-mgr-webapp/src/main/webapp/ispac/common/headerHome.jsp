<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script language="JavaScript"><!--

	function gotoExpedient() {
	
	    var numexp = document.getElementById('_numexp').value;
	    if (numexp == null || numexp == ''){
	    	jAlert('<bean:message key="header.gotoExpedient.javascript.empty.numexp"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
	    	return;
	    }
		location.href= <%="'"+request.getContextPath()+"'"%>+'/selectAnActivity.do?numexp='+numexp;
	}

--></script>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
 <jsp:include page="headerInt.jsp" />
  <tr>
    <td colspan="2" width="100%">
	<div id="usuario">
		<div id="barra_usuario">
			<p class="miga">
				<html:link href="javascript:gotoExpedient()" styleClass="gotoExp"><bean:message key="header.gotoExpedient"/>:</html:link>
				<input type="text" id="_numexp" name="numexp" onkeypress="handleEnter(event, 'gotoExpedient()');"/>
			</p>
			<p class="usuario">
		            <bean:write name="User"/>
			</p>
		</div>
	</div>
    </td>
  </tr>
</table>
