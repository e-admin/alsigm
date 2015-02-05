<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.nt.struts.util.Misc"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.Idioma"%>
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

	<!-- Inicio Cabecera -->
	<div id="cabecera">
		<div id="logo">
			<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="SIgem" />
		</div>
		<div class="salir">
			<img src="./img/<%=rutaImagenes%>exit.gif" alt="salir" width="26" height="20" class="icono" />
			<span class="titular">
				<a href="desconectar.do">
					<bean:message key="ieci.tecdoc.sgm.nt.cabecera.salir"/>
				</a>
			</span>
		</div>
		<%
		Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
		String strIdioma = locale.getLanguage() + "_" + locale.getCountry();
		%>
		<logic:present name="<%=Misc.IDIOMAS_DISPONIBLES%>">
		<bean:define id="idiomasDesplegable" type="java.util.ArrayList" name="<%=Misc.IDIOMAS_DISPONIBLES%>" />
		<div class="idioma">
			<select id="selIdioma">
			<%
			for(int indIdioma = 	0; indIdioma<idiomasDesplegable.size(); indIdioma++){
				Idioma objIdioma = (Idioma)idiomasDesplegable.get(indIdioma);
			%>
				<option value="<%=objIdioma.getCodigo()%>" <%=(objIdioma.getCodigo().equals(strIdioma) ? "selected" : "")%>><%=objIdioma.getDescripcion()%></option>
			<%}%>
			</select>
			<img src="img/<%=rutaImagenes%>ico_change.gif"  alt="<bean:message key="cambiarIdioma"/>" width="20" height="20" class="icono" onclick="javascript:cambiarIdioma();"/>
			<iframe src="blank.html" id="recargarIdioma" title='<bean:message key="cambiarIdioma"/>' style="top: 0px; left: 0px; visibility:hidden; position:absolute">
			</iframe>
		</div>
		</logic:present>
	</div>

	<!-- Fin Cabecera -->
	<script language="javascript">
		function abrirAyuda(url){
		   var opciones="left=100,top=100,width=700,height=450,status=no,menubar=no,scrollbars=yes,status=no,resizable=yes";
			window.open(url,"hija",opciones);
		}
	</script>

	<div class="usuario">
		<div class="usuarioleft">
			<p>
				<bean:message key="ieci.tecdoc.sgm.nt.cabecera.consultaNotificaciones"/>
			</p>
		</div>
		<div class="usuarioright">
			<div style="padding-right: 26px; padding-top: 4px">
				<img src="img/<%=rutaImagenes%>help.gif" alt='<bean:message key="ieci.tecdoc.sgm.nt.cabecera.ayuda"/>' style="padding-top: 6px;" onclick="javascript:abrirAyuda('ayuda/Notificacion.htm')"/>
			</div>
		</div>
	</div>
	<br />