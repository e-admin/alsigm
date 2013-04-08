<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript"> 
//<!--

	//	Confirma la salida de la aplicación
	function exit() {
		jConfirm('<bean:message key="catalog.exit.msg"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>',function(resultado) {
			if(resultado){
				window.location.href = '<c:url value="/exit.do"/>';
			}
		});
	}
	        
//-->
</script>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr style="width:100%;">
	    <td width="100%">
		<div id="cabecera_int_left">
			<h1><bean:message key="main.company"/></h1>
		</div>
		<div id="cabecera_int_right">
			<h3><bean:message key="main.productName"/></h3>		
			<p class="salir"><a onclick="javascript:exit();return false;" href='#'><bean:message key="menu.exit"/></a></p>
		</div>
	    </td>
	</tr>
</table>
