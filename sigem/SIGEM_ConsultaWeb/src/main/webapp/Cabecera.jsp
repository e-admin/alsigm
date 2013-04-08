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

String entidadId = (String)request.getParameter("ENTIDAD_ID");
if (entidadId == null || entidadId.equals(""))
	entidadId = (String)request.getSession().getAttribute("ENTIDAD_ID");
session.setAttribute(ConstantesGestionTelematicoCiudadano.PARAMETRO_ID_ENTIDAD, entidadId);
%>

	<div id="cabecera">
		<h1>
			<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="SIgem 2" />
		</h1>
		<h3>&nbsp;</h3>
		<p class="salir"><a href="Desconectar.jsp"><bean:message key="salir"/></a></p>
	</div>

	<div id="usuario">
		<div id="barra_usuario">
			<h3><bean:message key="consultaExpedientesDe"/> <%=session.getAttribute(Misc.NOMBRE_USUARIO)%></h3>
			<p class="ayuda">

				<%
					Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
					String strIdioma = locale.getLanguage() + "_" + locale.getCountry();
				%>

				<logic:present name="<%=Misc.IDIOMAS_DISPONIBLES%>">

					<bean:define id="idiomasDesplegable" type="java.util.ArrayList" name="<%=Misc.IDIOMAS_DISPONIBLES%>" />
					<span class="idioma">
						<select id="selIdioma">
						<%
						for(int indIdioma = 	0; indIdioma<idiomasDesplegable.size(); indIdioma++){
							Idioma objIdioma = (Idioma)idiomasDesplegable.get(indIdioma);
						%>
							<option value="<%=objIdioma.getCodigo()%>" <%=(objIdioma.getCodigo().equals(strIdioma) ? "selected" : "")%>><%=objIdioma.getDescripcion()%></option>
						<%}%>
						</select>
						<a href="javascript:cambiarIdioma();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
						<iframe src="blank.html" id="recargarIdioma" title='<bean:message key="cambiarIdioma"/>' style="top: 0px; left: 0px; visibility:hidden; position:absolute">
						</iframe>
					</span>
				</logic:present>

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
