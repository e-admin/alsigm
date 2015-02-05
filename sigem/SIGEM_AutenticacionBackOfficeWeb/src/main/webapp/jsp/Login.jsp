<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="utils-admin" uri="http://www.ieci.es/tecdoc/smg/utilidadesadministracion" %>

<%@ page import="ieci.tecdoc.idoc.admin.api.EstructuraOrganizativaLdapManager" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice" %>
<%@ page import=" ieci.tecdoc.sgm.backoffice.utils.Defs" %>
<%
	boolean canChangePassword = false;
	String idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);

	boolean sso = false;
	String singleSignOn = (String) session.getServletContext().getAttribute(Defs.PLUGIN_SINGLE_SIGN_ON);

	if ((singleSignOn != null) && (singleSignOn.equalsIgnoreCase("true"))) {
		sso = true;
	} else {
		if ((idEntidad != null) && (idEntidad.trim().length() > 0)){
			canChangePassword = !EstructuraOrganizativaLdapManager.esEntidadLdap(idEntidad);
		}
	}
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
	</head>
	<body>

		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>

  		<br />
    	<br />
		<div align="center">
			<div class="cuerpo" style="width:707px;text-align:left">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="autenticacion.table_title" /></h1>
		          		<div class="submenu3"></div>
		          		<div class="cuadro" style="background: url(img/foto_mujer_teclado.jpg) no-repeat">
		            		<logic:equal name="invalid_user" value="true">
		            			<label style="position:relative; left:340px" class="error"><bean:message key="autenticacion.error"/></label>
							</logic:equal>
							<logic:present name="<%=Defs.MENSAJE_LOGIN%>">
								<br/>
				            	<label style="position:relative; left:340px" class="error"><bean:message key="<%=(String)request.getAttribute(Defs.MENSAJE_LOGIN)%>"/></label>
			            	</logic:present>
		            		<br/><br/><br/>
		            		<html:form styleId="loginAccesoBean" action="login.do" method="post">
		                		<label for="username" class="gr" style="position:relative; left:340px"><bean:message key="autenticacion.username" /></label>
		                		<% if (sso) { %>
		                			<input type="text" name="username" id="username" style="position:relative; left:340px;background-color:#DDDDDD;" disabled="disabled"/>
	                			<% } else { %>
		                			<input type="text" name="username" id="username" style="position:relative; left:340px"/>
		                		<% } %>
		                		<br/><br/>
		                		<label for="password" class="gr" style="position:relative; left:340px"><bean:message key="autenticacion.password" /></label>
		                		<% if (sso) { %>
		                			<input type="password" name="password" id="password" style="position:relative; left:340px;background-color:#DDDDDD;" disabled="disabled"/>
		                		<% } else { %>
		                			<input type="password" name="password" id="password" style="position:relative; left:340px"/>
		                		<% } %>
		                		<br/><br/>
		                		<% if (sso) { %>
		                			<input type="submit" class="ok" value="<bean:message key="autenticacion.aceptar" />" style="position:relative; left:525px;" disabled="disabled"/>
		                		<% } else { %>
		                			<input type="submit" class="ok" value="<bean:message key="autenticacion.aceptar" />" style="position:relative; left:525px;"/>
		                		<% } %>
		              			<br/><br/>
		            		</html:form>
		            		<div style="position:relative; color: #006699; left:340px;float:left">
		            			<% if (canChangePassword) { %>
		            			<html:link action="seleccionClaveUsuario.do" style="margin-right:55px">
									<bean:message key="autenticacion.changepass" />
		            			</html:link>
		            			<% } %>
		            			<% if (!sso) { %>
		            			<html:link page="/jsp/RedireccionCertificados.jsp" ><bean:message key="autenticacion.acceso.certificado" /></html:link>
								<% } %>
		            		</div>

		            		<br/><br/>
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

  		<script language="Javascript">
  			<% if (!sso) { %>
				document.getElementById('username').focus();
			<% } %>
  		</script>
	</body>
</html:html>