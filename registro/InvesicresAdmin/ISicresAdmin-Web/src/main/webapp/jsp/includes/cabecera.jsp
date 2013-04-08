<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper"%>
<script>
	function abreAyuda( url ) {

		var features = "directories=0,top=50px,left=50px,height=520px,width=930px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

		window.open(url, "ventanaAyuda", features);
	}
</script>

<div id="appHeader">
	<div id="cabecera_int_left">
		<div id="logo_app">
			<img  src="./img/interna-gallery.png" id="logo_app_img"/>
		</div>
	</div>
	<div class="cabecera_int_right">
		<div id="logo_cia">
			<img  src="./img/invesicres-logo.png" id="logo_app_img2" align="right"/>
			<div id="salir" class="salir">
				<a href='<html:rewrite page="/logout.do" />'>
					<bean:message key="ieci.tecdoc.sgm.rpadmin.logout"/>
				</a>
			</div>
		</div>
	</div>
</div>


<div id="usuario">
	<div id="barra_usuario">
		<h3><bean:message key="ieci.tecdoc.sgm.rpadmin.titulo"/></h3>
		<p class="ayuda">
			<b><logic:present name="username"><bean:write name="username"/></logic:present></b>
			<% String ayuda = "/ayuda/" + LocaleFilterHelper.getCurrentLocale(request).getLanguage() + "/index.html";%>
			<a href="#" onclick="abreAyuda('<html:rewrite page="<%=ayuda%>" />')">&nbsp;</a>
		</p>
	</div>
</div>

<br/>
<br/>