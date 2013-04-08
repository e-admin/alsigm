<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.autenticacion.utils.Defs" %>
<%@ page import="java.util.ArrayList" %>
<%@page import="ieci.tecdoc.sgm.autenticacion.form.TipoAccesoForm"%>
<%@page import="java.io.File"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";

if ("".equals(rutaEstilos) || "".equals(rutaImagenes)) {
	String entidadId = (String)session.getAttribute(Defs.ENTIDAD_ID);
	if (entidadId != null && !entidadId.equals("")) {
		String ruta = request.getRealPath("");
		String ruta_entera = ruta + "/img/" + entidadId;
		File f = new File(ruta_entera);
		if (f.exists())	 {
			session.setAttribute("PARAMETRO_RUTA_IMAGENES", entidadId + "/");
			rutaImagenes = entidadId + "/";
		} else {
			session.setAttribute("PARAMETRO_RUTA_IMAGENES", "");
			rutaImagenes = "";
		}
		
		ruta_entera = ruta + "/css/" + entidadId;
		f = new File(ruta_entera);
		if (f.exists())	 {
			session.setAttribute("PARAMETRO_RUTA_ESTILOS", entidadId + "/");
			rutaEstilos = entidadId + "/";
		} else {
			session.setAttribute("PARAMETRO_RUTA_ESTILOS", "");
			rutaEstilos = "";
		}
	}
}
%>

<html:html locale="true">
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />	
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
	</head>
	<body>
	<div id="contenedora">
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
		<div class="centered">
		<div class="contenedor_centrado">
			<div class="cuerpo">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="autenticacion.tipo_acceso"/></h1>


					<div class="contenido_cuerpo_acceso"> 
						<div class="seccion_acceso"> 
			            		<html:form styleId="tipoAccesoBean" action="seleccionarTipoAcceso" method="post">
					              <p class="fila_sub">
				            		<label for="certificadoId" class="login">
				            			<bean:message key="autenticacion.acceso"/>
				            		</label>
				            		<%
				            		ArrayList tipos = (ArrayList)request.getAttribute(Defs.TIPO_ACCESO);
				            		%>
				            		<select name="selTipoAcceso" class="login">
				            			<%for(int i=0; i<tipos.size(); i++){ 
				            				TipoAccesoForm form = (TipoAccesoForm)tipos.get(i);
				            			%>
					            			<option value="<%=form.getAccesoId()%>"><bean:message key="<%=form.getAccesoDescription()%>"/></option>
								<%}%>
							</select>
					              </p>
					              <p class="fila">&nbsp;</p>
					              <p class="fila_right">
							<input type="submit" class="botonFondo" value='<bean:message key="autenticacion.aceptar"/>' />
					              </p>
					              <p class="fila">&nbsp;</p>
	
			            		</html:form>
						</div> <!-- fin seccion -->
					</div> <!-- contenido_cuerpo -->
	          		</div>
		        </div>
		      	</div>
			<div class="cuerpobt">
	      		<div class="cuerporightbt">
	        		<div class="cuerpomidbt"></div>
	      		</div>
			</div>
		</div>
		</div>
	</div>
	</body>
</html:html>