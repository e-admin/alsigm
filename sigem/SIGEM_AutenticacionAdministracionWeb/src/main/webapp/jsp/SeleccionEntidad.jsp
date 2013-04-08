<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="ieci.tecdoc.sgm.administracion.utils.Defs"%>

<html:html>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/estilos.css" type="text/css" />	
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

					            <p class="fila">
				            		<logic:present name="<%=Defs.MENSAJE_ERROR%>">
				            			<label class="error"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_ERROR)%>"/></label>
							</logic:present>
						    </p>	

			            		<html:form styleId="entidadAccesoBean" action="/seleccionEntidad.do" method="post">

					            <p class="fila">&nbsp;</p>
					            <p class="fila">
				            		<label for="entidadId" class="login"><bean:message key="autenticacion.entidad"/></label>
				            		<html:select property="entidadId" styleClass="login">
				            			<html:optionsCollection name="entidades" value="identificador" label="nombreCorto"/>
							</html:select>
						    </p>	

					            <p class="fila">&nbsp;</p>
					            <p class="fila_right">
							<input type="submit" class="botonFondo" value="<bean:message key="autenticacion.aceptar"/>" />
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