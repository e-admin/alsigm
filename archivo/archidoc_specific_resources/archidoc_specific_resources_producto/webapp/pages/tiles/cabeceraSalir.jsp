<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<div id="cabecera_int_left">
	<h1><bean:message key="archigest.archivo.cab.titulo.izquierda"/></h1>
</div>
<div id="cabecera_int_right">
	<h3><bean:message key="archigest.archivo.cab.titulo.derecha"/></h3>
	<p class="salir">
		<a href="#" onclick="javascript:logout(event)" class="etiquetaExit">
			 <bean:message key="label.exit"/>
		 </a>
	</p>
</div>

<script>
	function logout(event) {
		xStopPropagation(event);
		xPreventDefault(event);
		if (window.opener && !window.opener.closed) {
			window.opener.location = "<c:url value="/action/logoutAction" />";
			window.opener.focus();
			window.close();
		} else {
			window.location = "<c:url value="/action/logoutAction" />";
		}
	}
</script>