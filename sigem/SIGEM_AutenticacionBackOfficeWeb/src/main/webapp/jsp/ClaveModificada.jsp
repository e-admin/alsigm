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
		
		<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
  		<br />
    	<br />
		<div align="center">
			<div class="cuerpo" style="width:707px;text-align:left">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="cambioClave.mensaje.title"/></h1>
		          		<div class="submenu3"></div>
		          		<div class="cuadro" style="height: 231px">
		            		<br/>
							<label style="position:relative; left:350px" class="error"><html:errors/></label>
		            		<br/><br/>
	                		<label style="position:relative; left:100px"><bean:message key="cambioClave.mensaje.resultado"/></label>
	                		<br/><br/>
	                		<input type="button" class="ok" value='<bean:message key="cambioClave.aceptar" />' style="position:relative; left:100px;" onclick="javascript:go()"/>
	              			<br/><br/>
		            		<br/><br/>
		            		<label style="position:relative; color: #006699; left:250px;float:left;"></label>
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