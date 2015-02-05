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
			function init() {
				
			}
			function cancel() {
				window.location.href = '<html:rewrite href="<%=url%>"/>'
			}
			function mostrarEntidades() {
				var check = document.getElementById('interno');
				var formulario = document.getElementById('cambioClaveBean');
				var sel = document.getElementById('tr_entidades');
				var ents = document.getElementById('idEntidadInterno');
				if (check.checked == true) {
					if (ents.options.length > 1) {
						sel.style.visibility = 'visible';
					} else {
						sel.style.visibility = 'hidden';
					}
				} else {
					sel.style.visibility = 'hidden';
				} 
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
		          		<h1><bean:message key="cambioClave.table_title" /></h1>

					<div class="contenido_cuerpo_login"> 
						<div class="seccion_new_login"> 

					              <p class="fila">
				            		<logic:present name="org.apache.struts.action.ERROR">
		    			            		<label class="error" ><html:errors/></label>
			            			</logic:present>
					              </p>

			            		<html:form styleId="cambioClaveBean" action="cambiarClaveUsuario.do" method="post">
		            			<html:hidden styleId="url" property="url"/>

					              <p class="fila_sub">
			                		<label for="username" class="new_login"><bean:message key="cambioClave.username" /></label>
	    			            		<html:text styleId="username" property="username" styleClass="login"/>
					              </p>

					              <p class="fila_sub">
			                		<label for="currentPassword" class="new_login"><bean:message key="cambioClave.currentPassword" /></label>
	    			            		<html:password styleId="currentPassword" property="currentPassword" styleClass="login"/>
					              </p>

					              <p class="fila_sub">
			                		<label for="newPassword" class="new_login"><bean:message key="cambioClave.newPassword" /></label>
	    			            		<html:password styleId="newPassword" property="newPassword" styleClass="login"/>
					              </p>

					              <p class="fila_sub">
			                		<label for="newPasswordConfirm" class="new_login"><bean:message key="cambioClave.newPasswordConfirm" /></label>
	    			            		<html:password styleId="newPasswordConfirm" property="newPasswordConfirm" styleClass="login"/>
					              </p>

					              <p class="fila">
				                		<label for="interno" class="new_login" ><bean:message key="autenticacion.interno" /></label>
					                	<html:checkbox styleId="interno" styleClass="checkbox" property="interno" onclick="javascript:mostrarEntidades();"/>
					              </p>


					              <p class="fila" id="tr_entidades" >
				                		<label for="idEntidadInterno" class="new_login" ><bean:message key="autenticacion.entidad" /></label>
		    			            		<html:select property="idEntidadInterno" styleId="idEntidadInterno"  styleClass="login">
					            			<html:optionsCollection name="entidades" value="identificador" label="nombreLargo"/>
								</html:select>
					              </p>

					              <p class="fila">&nbsp;</p>
					              <p class="fila_right">
				                		<input type="submit" class="botonFondo" value='<bean:message key="cambioClave.aceptar" />' />
				                		<input type="button" class="botonFondo" value='<bean:message key="cambioClave.cancelar" />' onclick="javascript:cancel();"/>
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
  		
	<script language="Javascript">
		document.getElementById('username').focus();
		mostrarEntidades();
	</script>

	</body>
</html:html>