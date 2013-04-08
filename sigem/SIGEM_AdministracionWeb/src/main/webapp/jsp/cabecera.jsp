<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%
String ayuda = request.getParameter("ayuda");
%>
<title><bean:message key="entidades.titulo"/></title>
<div id="cabecera">
	<div id="logo">
		<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
		<img src="img/logo.gif" alt="AL SIGM" />
	</div>
	<div class="salir">
		<a href="logout.do"><img src="img/exit.gif" alt="<bean:message key="salir"/>" width="26" height="20" class="icono" border="0" /></a>
		<span class="titular">
			<a title="<bean:message key="salir"/>" href="<html:rewrite href="logout.do" />"><bean:message key="salir"/></a>
		</span>
	</div>
</div>

<script language="javascript">
	function abrirAyuda(url){
	   var opciones="left=100,top=100,width=700,height=450,status=no,menubar=no,scrollbars=yes,status=no,resizable=yes";
		window.open(url,"hija",opciones);
	}
	function estoyEnFrame() { return; }
</script>

<div class="usuario">
	<div class="usuarioleft">
		<p><bean:message key="usuario"/>&nbsp;<bean:write name="<%=Defs.PARAMETRO_NOMBRE_ADMINISTRADOR %>"/></p>
	</div>
	<div class="usuarioright">
		<p style="padding-right: 26px; padding-top: 5 px">
			<a title="<bean:message key="boton.ayuda"/>" style="border-style: none" href="javascript:abrirAyuda('<%=ayuda%>');">
				<img style="border-style: none" src="img/help.gif" />
			</a>
		</p>
	</div>
</div>
<br />