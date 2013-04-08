<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="ieci.tecdoc.sgm.admsistema.util.Utilidades"%>

<%
	String url = (String)request.getAttribute(Defs.PARAMETRO_URL_REDIRECCION);
%>

<html>
	<%	if (!Utilidades.esNuloOVacio(url)) { %>
	<head>
		<script language="Javascript">
			function comprobarRedireccion() {
				try {
					parent.estoyEnFrame();
					parent.document.location.reload();
				} catch(err) {
					window.location.href = '<%=url%>';
				}
			}
		</script>
	</head>
	<body onload="javascript:comprobarRedireccion();">
		<p>
			<bean:message key="cargando"/>
		</p>
	</body>
	<% } %>
</html>