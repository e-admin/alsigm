<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

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
		          		<h1><bean:message key="autenticacion.seleccioneEntidad"/></h1>
		          		<div class="submenu3"></div>
		          		<div class="cuadro" style="background: url(img/foto_carpetas.jpg) no-repeat; height: 231px">
		            		<html:form styleId="entidadAccesoBean" action="comprobarDatos.do" method="post">
			            		<br/><br/>
			            		<label for="entidadId" class="gr" style="position:relative; left:300px">
			            			<bean:message key="autenticacion.entidad"/>
			            		</label>
			            		<html:select property="entidadId" style="position:relative; left:300px; width:200px;" styleClass="gr">
			            			<html:optionsCollection name="entidades" value="identificador" label="nombre"/>
								</html:select>
		              			<br/><br/>
								<input type="submit" class="ok" value="<bean:message key="autenticacion.aceptar"/>" style="position:relative; left:525px;"/>
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
	</body>
</html:html>