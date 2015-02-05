<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

	<div id="cabecera">
		<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
		<p class="logoSIGM"><img alt="sigem" src="img/logo.gif"></p>
		<h3>&nbsp;</h3>
		<p class="salir"><a href='<html:rewrite page="/desconectar.do"/>'><bean:message key="salir"/></a></p>
	</div>
	<div id="usuario">
		<div id="barra_usuario">
			<h3><bean:message key="procedures.title"/></h3>
			<p class="ayuda">
				<a href="#" target="_blank">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</p>
		</div>
	</div>

