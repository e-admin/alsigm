<%@ page contentType="text/html;charset=ISO-8859-1" language="java"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<html:html>
<head>
<ieci:baseInvesDoc />
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<script src="include/js/validations.js" type="text/javascript"></script>

<script language="javascript" type="text/javascript">
	function newList(){
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) { 		
			document.getElementById('form').submit();
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}	 		
	}
</script>
</head>
<body>



<c:choose>
	<c:when test="${empty param.submitted}">
		<form action="<c:out value="${request.requestURL}"/>" id="form">
		
		    <div id="contenedora">
				<div class="cuerpo" style="width:390px; height:100px;">
			   		<div class="cuerporight">
			    		<div class="cuerpomid">
			    			<h1><bean:message key="message.comun.etiqueta.listas"/></h1><br/>		
							<div class="cuadro" style="height: 100px;">		
								<a class="menu0" href="javascript:newList();"><bean:message key="message.comun.etiqueta.nueva"/></a>
								<html:hidden property="submitted" value="true" />
							</div>
						</div>
					</div>
				</div>
				<div class="cuerpobt" style="width:390px">
			    	<div class="cuerporightbt">
			      		<div class="cuerpomidbt"></div>
			    	</div>
				</div>
			</div>			
		</form>		
	</c:when>
	<c:otherwise>
		<script language='javascript'>
			var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
			parent.edicion.location.href = appBase + '/volume/listNew.do';
		</script>
	</c:otherwise>
</c:choose>

</body>


</html:html>

