<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<!-- Inicio Cabecera -->
<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.Idioma"%>
<%@page import="ieci.tecdoc.sgm.ct.utilities.Misc"%>
<%@page import="ieci.tecdoc.sgm.core.services.telematico.ConstantesGestionTelematicoCiudadano"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

	<div id="cabecera">
		<h1>
			<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="sigem" />
		</h1>
		<h3>&nbsp;</h3>
		<p class="salir"><a href="Desconectar.jsp"><bean:message key="salir"/></a></p>
	</div>

	<div id="usuario">
		<div id="barra_usuario">
			<h3><bean:message key="consultaExpedientesDe"/></h3>
			<p class="ayuda">
				<a href="javascript:abrirAyuda('ayuda/ConsultaExpedientes.htm');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</p>
		</div>
	</div>

<script language="javascript">
		function abrirAyuda(url){
		   var opciones="left=100,top=100,width=700,height=450,status=no,menubar=no,scrollbars=yes,status=no,resizable=yes";
			window.open(url,"hija",opciones);
		}
</script>
