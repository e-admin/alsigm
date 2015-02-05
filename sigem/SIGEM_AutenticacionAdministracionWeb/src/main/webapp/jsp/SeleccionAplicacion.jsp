<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="ieci.tecdoc.sgm.core.services.administracion.Aplicacion"%>
<%@page import="ieci.tecdoc.sgm.administracion.utils.Defs"%>
<%@page import="ieci.tecdoc.sgm.administracion.utils.Utilidades"%>
<%@page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion"%>

<%
	String key = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
	String keyEntidad = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD);
	
	String nombre = "entidad.administrar.nombre.";
	String desc_corta = "entidad.administrar.descripcion_corta.";
	String desc_larga = "entidad.administrar.descripcion_larga.";
%>

<html:html>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/estilos.css" type="text/css" />	
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
		
		<script language="Javascript">
			function abrirAplicacion(idApp, url) {
				document.forms[0].action = url;
				document.forms[0].submit();
			}
		</script>
	</head>
	<body>

	<div id="contenedora">

		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>

		<div class="centered">
		<div class="contenedor_centrado_ancho">
			<div class="cuerpo">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="autenticacion.seleccioneAplicacion"/></h1>


	            			<html:form styleId="aplicacionAccesoBean" action="login.do" method="post">
						<input type="hidden" name="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM%>" id="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM%>" value="<%=key%>" />
						<input type="hidden" name="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD%>" id="<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD%>" value="<%=keyEntidad%>" />
	
						<div class="contenido_cuerpo"> 
				            		<logic:present name="<%=Defs.MENSAJE_ERROR%>">
								<label class="error"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_ERROR)%>"/></label>
							</logic:present>
							<div class="clear"></div> <!-- fin clear -->

							<% 
							Aplicacion[] aplicaciones = (Aplicacion[])request.getAttribute(Defs.PARAMETRO_APLICACIONES);
							int ajuste = 0;
							for(int i=0; i<aplicaciones.length; i++) { 
								Aplicacion aplicacion = (Aplicacion)aplicaciones[i];
							%>
							<div class="seccion"> 
								<div class="encabezado_sec"><bean:message key="<%=nombre + aplicacion.getIdentificador()%>"/></div>
								<div class="cuerpo_sec"> 
									<ul>
										<li>
										<%
										String url = Utilidades.obtenerUrlAplicacion(request, aplicacion);
										if (!ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION.equals(aplicacion.getIdentificador())) {
										%>
										<a href="<%=url%>?<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD%>=<%=keyEntidad%>">
											<bean:message key="<%=desc_corta+aplicacion.getIdentificador()%>"/>
										</a>
										<%} else { %>
										<a href="<%=url%>?<%=ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM%>=<%=key%>">
											<bean:message key="<%=desc_corta+aplicacion.getIdentificador()%>"/>
										</a>
										<%} %>

										</li>
									</ul>
									<p><bean:message key="<%=desc_larga+aplicacion.getIdentificador()%>"/></p>
								</div>
							</div> <!-- fin seccion -->
							<%
							if ((i%2)==1) {
							%>
							<div class="clear"></div> <!-- fin clear -->
							<% 	}
							}
							%>
						</div>
	            			</html:form>
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