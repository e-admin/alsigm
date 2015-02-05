<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="ieci.tecdoc.sgm.registro.utils.Defs" %>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<html:html locale="true">
	<head>
		<%String afirma = "jsp/AFirma";//(String)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_AFIRMA);%>
		<script type="text/javascript" language="javascript" src="<%= afirma %>/install_files/common-js/time.js"></script>
		<script type="text/javascript" language="javascript" src="<%= afirma %>/install_files/common-js/appletHelper.js"></script>
		<script type="text/javascript" language="javascript" src="<%= afirma %>/install_files/common-js/instalador.js"></script>
		<script type="text/javascript" language="javascript" src="<%= afirma %>/install_files/common-js/firma.js"></script>
		<script type="text/javascript" language="javascript" src="<%= afirma %>/install_files/constantes.js"></script>
		<script language="Javascript">
			function install()
			{
				//setTimeout("instalador.instalar(); comprobarInstalacion();", 1);
				instalador.instalar(); //comprobarInstalacion();
			}
			
			function comprobarInstalacion()
			{
				if(instalador.isInstalado() && instalador.isActualizado())
				{
					alert('<bean:message key="firma.instalado"/>');
				}else{
					install();
				}
			}
			
			function getBase()
			{
				var baseHREF= document.location.href;
				baseHREF= baseHREF.substring(0, baseHREF.lastIndexOf('/'));
				return baseHREF;
			}
			
			var base = getBase() + '/jsp/AFirma/install_files' ;
		</script>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos.css" type="text/css" />	
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
	</head>
	<body onLoad="cargarAppletInstalador()">

	<div id="contenedora">
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
		<div class="centered">
		<div class="contenedor_centrado">
            		<html:form  action="realizarSolicitudRegistro" method="post">

		    	<div class="cuerpo">
	      		<div class="cuerporight">
	        		<div class="cuerpomid">
	          			<h1><bean:message key="solicitudes.seleccionar"/></h1>

		          		<div class="cuadro" >
			            		<label for="selTramiteId" class="gr">
			            			<bean:message key="solicitudes.seleccion"/>
			            		</label>
		                		<html:select styleClass="gr" property="selTramiteId">
									<html:optionsCollection name="<%=Defs.TRAMITES%>" label="tramiteDescripcion" value="tramiteId"/>
						</html:select>
		          		</div>
			        </div>
			</div>
			</div>

			<div class="cuerpobt">
			<div class="cuerporightbt">
				<div class="cuerpomidbt">
					<input type="submit" class="ok" value="<bean:message key="solicitudes.aceptar"/>" />	
		          		<input class="ok" type="button" id="instalarButton" value="<bean:message key="solicitudes.instalar"/>" onClick="javascript:comprobarInstalacion()">
				</div>
			</div>
			</div>
            		</html:form>
		</div>
		</div>
	</div>
	</body>
</html:html>