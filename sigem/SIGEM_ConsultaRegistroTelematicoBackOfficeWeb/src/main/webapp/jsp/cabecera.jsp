<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<div id="cabecera">
	<h1>
		<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
		<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="sigem" />
	</h1>
	<h3>&nbsp;</h3>
	<p class="salir"><a href="<html:rewrite page="/desconectar.do"/>"><bean:message key="mensaje.salir"/></a></p>
</div>

<div id="usuario">
	<div id="barra_usuario">
		<h3><bean:message key="mensaje.usuario"/></h3>
		<p class="ayuda">
			<a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
		</p>
	</div>
</div>
