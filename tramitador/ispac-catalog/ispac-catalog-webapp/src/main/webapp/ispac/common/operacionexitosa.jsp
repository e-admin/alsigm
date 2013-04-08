<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<c:set var="refresh">
   <c:out value="${requestScope['refresh']}" default="false"/>
</c:set>
<jsp:useBean id="refresh" type="java.lang.String"/>


<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<div class="acciones_ficha">
			<input type="button" value='<bean:message key="forms.button.accept"/>'  id="btnOk" class="btnOk" onclick='javascript:salir();'/>
		</div>
	</div>
</div>

<br/><br/>
<div class="popUp">
<bean:message key="message.operacionRealizadaConExito"/>
</div>
</div>
<script>

function salir(){

parent.hideFrame('workframemsg','<ispac:rewrite page="wait.jsp"/>')

}
$(document).ready(function() {
	$("#move").draggable();
});
</script>