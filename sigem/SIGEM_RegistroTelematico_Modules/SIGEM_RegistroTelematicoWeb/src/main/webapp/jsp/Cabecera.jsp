<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.Idioma"%>
<%@page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>
<%@page import="ieci.tecdoc.sgm.core.services.telematico.ConstantesGestionTelematicoCiudadano"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.ConstantesIdioma"%>
<%@page import="ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper"%>

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



<script>
		location.https_port="<%=PortsConfig.getHttpsPort()%>";
		location.http_port="<%=PortsConfig.getHttpPort()%>";
		location.cert_port="<%=PortsConfig.getCertPort()%>";

		location.https_frontend_port="<%=PortsConfig.getHttpsFrontendPort()%>";
		location.http_frontend_port="<%=PortsConfig.getHttpFrontendPort()%>";
		location.cert_frontend_port="<%=PortsConfig.getHttpsFrontendAuthclientPort()%>";
		//location.server_name="<%=request.getServerName()%>";
	</script>


	<div id="cabecera">
		<h1>
			<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="SIgem" />
		</h1>
		<h3>&nbsp;</h3>
		<p class="salir"><a href="<html:rewrite page="/desconectar.do"/>"><bean:message key="salir"/></a></p>
	</div>

	<div id="usuario">
		<div id="barra_usuario">
			<h3><bean:message key="aplicacion"/></h3>
			<p class="ayuda">
				<%
					Locale locale = LocaleFilterHelper.getCurrentLocale(request);
					String strIdioma = locale.getLanguage() + "_" + locale.getCountry();
				%>

				<logic:present  scope="session" name="<%=ConstantesIdioma.IDIOMAS_DISPONIBLES%>">
				<bean:define id="idiomasDesplegable" type="java.util.ArrayList" name="<%=ConstantesIdioma.IDIOMAS_DISPONIBLES%>" />
					<span class="idioma">
						<select id="selIdioma">
						<%
						for(int indIdioma = 	0; indIdioma<idiomasDesplegable.size(); indIdioma++){
							Idioma objIdioma = (Idioma)idiomasDesplegable.get(indIdioma);
						%>
							<option value="<%=objIdioma.getCodigo()%>" <%=(objIdioma.getCodigo().equals(strIdioma) ? "selected" : "")%>><%=objIdioma.getDescripcion()%></option>
						<%}%>
						</select>
						<a href="javascript:cambiarIdiomaSinRefresco();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
						<iframe src="blank.html" id="recargarIdioma" title='<bean:message key="cambiarIdioma"/>' style="top: 0px; left: 0px; visibility:hidden; position:absolute">
						</iframe>
					</span>
				</logic:present>

				<a href="jsp/ayuda/Solicitud.htm" target="_blank">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</p>
		</div>
	</div>
