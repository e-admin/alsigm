<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="utils-admin" uri="http://www.ieci.es/tecdoc/smg/utilidadesadministracion" %>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.Idioma"%>
<%@ page import="ieci.tecdoc.sgm.autenticacion.utils.Defs" %>
<%@page import="ieci.tecdoc.sgm.core.services.telematico.ConstantesGestionTelematicoCiudadano"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";

String entidadId = (String)request.getParameter("ENTIDAD_ID");
if (entidadId == null || entidadId.equals(""))
	entidadId = (String)request.getSession().getAttribute("ENTIDAD_ID");
if (entidadId == null || entidadId.equals(""))
	entidadId = " ";
session.setAttribute(ConstantesGestionTelematicoCiudadano.PARAMETRO_ID_ENTIDAD, entidadId);
%>

	<div id="cabecera">
		<utils-admin:eco-logo/>
		<p class="logoSIGM">
			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="SIgem 2" />
		</p>
		<h3>&nbsp;</h3>
		<p class="salir"><a href="jsp/RedireccionPortal.jsp"><bean:message key="salir"/></a></p>
	</div>

	<div id="usuario">
		<div id="barra_usuario">
			<h3><bean:message key="aplicacion"/></h3>
			<p class="ayuda">
				<%
					Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
					String strIdioma = locale.getLanguage() + "_" + locale.getCountry();
				%>
				<logic:present name="<%=Defs.IDIOMAS_DISPONIBLES%>">
				<bean:define id="idiomasDesplegable" type="java.util.ArrayList" name="<%=Defs.IDIOMAS_DISPONIBLES%>" />
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
						<iframe src="blank.html" id="recargarIdioma" title='<bean:message key="cambiarIdioma"/>' style="top: 0px; left: 0px; visibility:hidden; position:absolute" >
						</iframe>
					</span>
				</logic:present>

				<a href="jsp/ayuda/AccesoSistemaTramitacion.htm" target="_blank">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</p>
		</div>
	</div>
