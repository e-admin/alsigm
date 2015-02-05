<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.pe.struts.Constantes"%>
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

<div id="cabecera">
    <div id="logo">
    	<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
    	<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="SIGem" />
    </div>
    <div class="salir">
    	<img src="img/<%=rutaImagenes%>exit.gif" alt="salir" width="26" height="20" class="icono" />
    	<span class="titular">
    		<a href='<html:rewrite page="/desconectar.do" />'>
    			<bean:message key="ieci.tecdoc.sgm.pe.struts.common.exit"/>
    		</a>
    	</span>
    </div>
    <%
		Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
		String strIdioma = locale.getLanguage() + "_" + locale.getCountry();
	%>
	<logic:present name="<%=Constantes.IDIOMAS_DISPONIBLES%>">
	<bean:define id="idiomasDesplegable" type="java.util.ArrayList" name="<%=Constantes.IDIOMAS_DISPONIBLES%>" />
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
<div class="usuario">
	<div class="usuarioleft">
    	<p>
    		<bean:message key="ieci.tecdoc.sgm.pe.struts.common.logedas"/>:  <bean:write name="ieci.tecdoc.sgm.pe.struts.userData" property="m_nombre"/>
    	</p>
	</div>
	<div class="usuarioright">
    	<div style="padding-right: 26px; padding-top: 4px">
   			<a href="<html:rewrite page="/ayuda/Pago.html" />" target="_blank">
   				<img src="img/<%=rutaImagenes%>help.gif" border="0" alt='<bean:message key="ieci.tecdoc.sgm.pe.struts.common.help"/>' style="padding-top: 6px;"/>
   			</a>
   		</div>
	</div>
</div>