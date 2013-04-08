<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<bean:parameter id="url" name="url" />

<html:html>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/estilos.css" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
		<script type="text/javascript" language="javascript">
			//<!--
				function go() {
					window.location = '<html:rewrite href="<%=url%>"/>'
				}
			//-->
		</script>
	</head>
	<body>



	<div id="contenedora">

		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>

		<div class="centered">
		<div class="contenedor_centrado">
			<div class="cuerpo">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="cambioClave.mensaje.title" /></h1>

					<div class="contenido_cuerpo_login"> 
						<div class="seccion_login"> 

					              <p class="fila">
							<label class="error"><html:errors/></label>
					              </p>

					              <p class="fila">
			                		<label><bean:message key="cambioClave.mensaje.resultado"/></label>
					              </p>


					              <p class="fila">&nbsp;</p>
					              <p class="fila_right">
	                				<input type="button" class="botonFondo" value='<bean:message key="cambioClave.aceptar" />' onclick="javascript:go()"/>
					              </p>
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