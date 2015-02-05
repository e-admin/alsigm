<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.backoffice.temporal.Aplicacion"%>
<%@page import="ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice"%>
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
				if (idApp == "<%=ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO%>") {
					document.getElementById('desplegable').style.visibility = 'visible';
					document.getElementById('desplegable').style.position = 'relative';
					var tipo = document.getElementById('tipoUsuario');
					if (tipo.options[tipo.selectedIndex].value != "") {
						var almacenar = document.getElementById('almacenarDatosArchivo');
						document.location.href = 'almacenarDatosArchivo.do?tipo=' + tipo.options[tipo.selectedIndex].value;
					}
				} else {
					document.location.href = url;
				}
			}
		</script>
	</head>
	<body>

		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
  		<br />
    	<br />
		<div align="center">
			<div class="cuerpo" style="width:707px;text-align:left">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="autenticacion.seleccioneAplicacion"/></h1>
		          		<div class="submenu3"></div>
		          		<div class="cuadro" style="background: url(img/foto_carpetas.jpg) no-repeat; height: 231px">
		            		<html:form styleId="aplicacionAccesoBean" action="login.do" method="post">
			            		<br/><br/>
			            		<label class="gr" style="position:relative; left:300px">
			            			<bean:message key="autenticacion.aplicacion"/>
			            		</label>
			            		<br/><br/>
			            		<table style="margin-left: 350px; width: 200px;">
			            		<%
			            		ArrayList  aplicaciones = (ArrayList)request.getAttribute("aplicaciones");
			            		if (aplicaciones != null) {
			            			for (int ind=0; ind<aplicaciones.size(); ind++){
			            		%>
			            			<tr>
			            				<td width="100%" colspan="2">
				            				<label class="gr" style="width:100%">
				            					<a class="aplicacion_link" onclick="abrirAplicacion('<%=((Aplicacion)aplicaciones.get(ind)).getIdentificador()%>', '<%=((Aplicacion)aplicaciones.get(ind)).getUrl()%>')"><%=((Aplicacion)aplicaciones.get(ind)).getNombre()%></a>
				            				</label>
				            	<%
				            				if (((Aplicacion)aplicaciones.get(ind)).getIdentificador().equalsIgnoreCase(ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO)){
								%>
												</td></tr>
												<tr id="desplegable" style="position: relative"><td>
												<select name="tipoUsuario" id="tipoUsuario" class="aplicacion_select">
													<option value=""></option>
													<option value="1">Interno</option>
													<option value="2">Asociado</option>
													<%-- <option value="3">Externo</option> --%>
												</select>
												</td>
												<td>
													<input type="button" class="ok" value="<bean:message key="autenticacion.aceptar" />" onclick="abrirAplicacion('<%=((Aplicacion)aplicaciones.get(ind)).getIdentificador()%>', '<%=((Aplicacion)aplicaciones.get(ind)).getUrl()%>')"/>
								<%			            					
				            				}
				            	%>
				            			</td>
				            		</tr>
			            		<%			        
			            			}
			            		}
			            		%>
			            		</table>
		            		</html:form>
		          		</div>
		        	</div>
		      	</div>
		    </div>
		    <div class="cuerpobt" style="width:707px;">
	      		<div class="cuerporightbt">
	        		<div class="cuerpomidbt"></div>
	      		</div>
	    	</div>
  		</div>
  	<%-- 	<iframe id="almacenarDatosArchivo" src="" style="width: 0px; height: 0px; visibility: hidden"></iframe> --%>
	</body>
</html:html>