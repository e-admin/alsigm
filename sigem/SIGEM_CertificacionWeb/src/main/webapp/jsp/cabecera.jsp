<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="java.util.Locale"%>
<%@page import="ieci.tecdoc.sgm.certificacion.utilsweb.Defs"%>
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
		   			<img src="<%=request.getContextPath()%>/img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
		   			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="SIgem" />
		   		</div>
				<div class="salir">
					<img src="img/<%=rutaImagenes%>exit.gif" alt='<bean:message key="salir"/>' width="26" height="20" class="icono" />
					<span class="titular">
						<a href='<html:rewrite page="/desconectar.do"/>'><bean:message key="salir"/></a>
					</span>
				</div>
				<%
					Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
					String strIdioma = locale.getLanguage() + "_" + locale.getCountry();
				%>
				<logic:present name="<%=Defs.IDIOMAS_DISPONIBLES%>">
					<bean:define id="idiomasDesplegable" type="java.util.ArrayList" name="<%=Defs.IDIOMAS_DISPONIBLES%>" />
					<div class="idioma">
						<select id="selIdioma">
						<%
						for(int indIdioma = 	0; indIdioma<idiomasDesplegable.size(); indIdioma++){
							Idioma objIdioma = (Idioma)idiomasDesplegable.get(indIdioma);
						%>
							<option value="<%=objIdioma.getCodigo()%>" <%=(objIdioma.getCodigo().equals(strIdioma) ? "selected" : "")%>><%=objIdioma.getDescripcion()%></option>
						<%}%>
						</select>
						<img src="img/<%=rutaImagenes%>ico_change.gif"  alt='<bean:message key="cambiarIdioma"/>' width="20" height="20" class="icono" onclick="javascript:cambiarIdioma();"/>
						<iframe src="blank.html" id="recargarIdioma" title='<bean:message key="cambiarIdioma"/>' style="top: 0px; left: 0px; visibility:hidden; position:absolute">
						</iframe>
					</div>
				</logic:present>
		 		</div>
		 		<div class="usuario">
		   		<div class="usuarioleft">
		     		<p><bean:message key="usuario"/> <%=(String)session.getAttribute(Defs.NOMBRE_USUARIO)%></p>
				</div>
		   		<div class="usuarioright">
		     			<div style="padding-top: 8px; padding-right: 24px;">
			     		<a href="#"><img src="img/<%=rutaImagenes%>help.gif" style="border: 0px" alt='<bean:message key="boton.ayuda"/>' width="16" height="16" /></a>
			     	</div>
		   		</div>
		 	</div>
		  	<!-- Fin Cabecera -->
			<div><br/><br/></div>