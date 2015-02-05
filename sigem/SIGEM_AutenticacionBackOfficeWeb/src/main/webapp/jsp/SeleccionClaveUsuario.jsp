<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<bean:define id="url" name="cambioClaveBean" property="url" type="java.lang.String"/>

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
				function cancel() {
					window.location.href = '<html:rewrite href="<%=url%>"/>'
				}
			//-->
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
		          		<h1><bean:message key="cambioClave.table_title" /></h1>
		          		<div class="submenu3"></div>
		          		<div class="cuadro" style="background: url(img/foto_mujer_teclado.jpg) no-repeat">
		            		<br/>
							<label style="position:relative; left:350px" class="error"><html:errors/></label>
		            		<br/><br/>
		            		<html:form styleId="cambioClaveBean" action="cambiarClaveUsuario.do" method="post">
		            			<html:hidden styleId="url" property="url"/>
		                		<label for="username" class="gr" style="position:relative; left:350px"><bean:message key="cambioClave.username" /></label>
		                		<html:text styleId="username" property="username" style="position:relative; left:350px"/>
		                		<br/><br/>
		                		<label for="currentPassword" class="gr" style="position:relative; left:350px"><bean:message key="cambioClave.currentPassword" /></label>
		                		<html:password styleId="currentPassword" property="currentPassword" style="position:relative; left:350px"/>
		                		<br/><br/>
		                		<label for="newPassword" class="gr" style="position:relative; left:350px"><bean:message key="cambioClave.newPassword" /></label>
		                		<html:password styleId="newPassword" property="newPassword" style="position:relative; left:350px"/>
		                		<br/><br/>
		                		<label for="newPasswordConfirm" class="gr" style="position:relative; left:350px"><bean:message key="cambioClave.newPasswordConfirm" /></label>
		                		<html:password styleId="newPasswordConfirm" property="newPasswordConfirm" style="position:relative; left:350px"/>
		                		<br/><br/>
		                		<logic:present name="entidades">
		                		<label for="idEntidad" class="gr" style="position:relative; left:350px"><bean:message key="cambioClave.entidad" /></label>
   			            		<html:select styleId="idEntidad" property="idEntidad" styleClass="gr" style="position:relative; left:350px">
			            			<html:optionsCollection name="entidades" value="identificador" label="nombre"/>
								</html:select>
		                		</logic:present>
		                		<logic:notPresent name="entidades">
   			            		<html:hidden styleId="idEntidad" property="idEntidad"/>
		                		</logic:notPresent>
		                		<br/>
		                		<input type="submit" class="ok" value='<bean:message key="cambioClave.aceptar" />' style="position:relative; left:435px;"/>
		                		<input type="button" class="ok" value='<bean:message key="cambioClave.cancelar" />' style="position:relative; left:435px;"
		                			onclick="javascript:cancel();"/>
		              			<br/><br/>
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
  		
  		<script language="Javascript">
  			document.getElementById('username').focus();
  		</script>
	</body>
</html:html>