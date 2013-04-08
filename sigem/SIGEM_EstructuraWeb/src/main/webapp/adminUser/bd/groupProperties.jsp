<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean" %>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html><head>

<ieci:baseInvesDoc/>
<link rel="stylesheet" rev="stylesheet" href="include/css/tabs.css">
<link rel="stylesheet" rev="stylesheet" href="include/css/estilos.css">
<script src="include/js/docobj.js" type="text/javascript"></script>
<script src="include/js/tabs.js" type="text/javascript"></script>
<script src="include/js/validations.js" type="text/javascript"></script>
<script type="text/javascript">
	function cancel(){		
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {	
			document.location.href="<html:rewrite forward='cancel'/>";
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}			
	}		
</script>

</head>

<body onload="choosebox(1,9);">

    <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:170px;">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
					<h1><bean:message key="message.grupo.propiedades.titulo"/></h1>
					<div class="submenu3">
           				<ul>
        					<li class="submen1on" id="tab1"><img src="include/images/subme3_on.gif" />
        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
        					</li>
        					<img src="include/images/subme3_on_0.gif">
						</ul>
					</div>					
					<div class="cuadro" style="height: 170px;">
						<div id="box1">
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
								<label class="gr" style="width:190px;"><bean:write name="basicForm" property="nombre" /></label>
						    </div>                
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.id"/></label>
								<label class="gr" style="width:190px;"><bean:write name="basicForm" property="id" /></label>
						    </div>
						    <div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.administrador"/></label>
								<label class="gr" style="width:190px;"><bean:write name="basicForm" property="managerName" /></label>
						    </div>
						    <div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>	
								<label class="gr" style="width:190px;"><html:textarea property="descripcion" readonly="true" name="basicForm" rows="3" cols="65"/></label>
						    </div>
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.creado"/></label>
								<label class="gr" style="width:110px;"><bean:write name="basicForm" property="creationDate" /></label>
								<label class="gr" style="width:30px;"><bean:message key="message.comun.etiqueta.por"/></label>
								<label class="gr" style="width:120px;"><bean:write name="basicForm" property="creatorName" /></label>					
						    </div>
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.modificado"/></label>
								<label class="gr" style="width:110px;"><bean:write name="basicForm" property="updateDate" /></label>
								<label class="gr" style="width:30px;"><bean:message key="message.comun.etiqueta.por"/></label>
								<label class="gr" style="width:120px;"><bean:write name="basicForm" property="updaterName" /></label>							
						    </div>
						</div>
	       			</div>     				
     			</div>
      		</div>
      		<input type="button" value='<bean:message key="message.comun.boton.aceptar"/>' class="okEO" onclick="cancel();" />
	   	</div> 	
		
		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
	</div>

</body></html>
