<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
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

		          		<h1><bean:message key="autenticacion.seleccioneEntidad"/></h1>

					<div class="contenido_cuerpo_login"> 
						<div class="seccion_login"> 
            		<html:form styleId="tipoAccesoBean" action="obtenerTipoAcceso" method="post">
					            <p class="fila">
				            		<label for="certificadoId" class="login"><bean:message key="autenticacion.entidad"/></label>
				            		<html:select property="entidadId" styleClass="login">
				            			<html:optionsCollection name="entidades" value="identificador" label="nombre"/>
							</html:select>
						    </p>	
					            <p class="fila">&nbsp;</p>
					            <p class="fila_right">
							<input type="submit" class="botonFondo" value='<bean:message key="autenticacion.aceptar"/>'/>
						    </p>	
            		</html:form>
						</div>
					</div>
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