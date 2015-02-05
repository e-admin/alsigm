<%@ page contentType="text/html;charset=ISO-8859-1" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>

<ieci:baseInvesDoc />
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



<body>
	
    <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:160px;">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
					<h1><bean:message key="message.usuario.propiedades.titulo"/></h1>
					<div class="submenu3">
           				<ul>
        					<li class="submen1on"><img src="include/images/subme3_on.gif" /><bean:message key="message.comun.pestana.general"/></li>
        					<img src="include/images/subme3_on_0.gif">
						</ul>
					</div>
					
					<div class="cuadro" style="height: 182px;" >
					     <div id="box1" style="height: 150px">               
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
								<label class="gr" style="width:190px;"><bean:write name="basicForm" property="nombre" /></label>
						    </div>                
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.id"/></label>
								<label class="gr" style="width:190px;"><bean:write name="basicForm" property="id" /></label>
						    </div>  
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.departamento"/></label>
								<label class="gr" style="width:190px;"><bean:write name="basicForm" property="belongToDept" /></label>
						    </div>  					    					                    
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
								<html:textarea property="descripcion" name="basicForm" readonly="true" rows="2" cols="65" />
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
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.estado"/>&nbsp;<html:checkbox property="bloqueado" name="basicForm" disabled="true" style="width:20px"/></label>
								<label class="gr" style="width:190px;"><bean:message key="message.comun.etiqueta.bloqueado"/></label>						
						    </div>
						</div>
	       			</div>     				
     			</div>
      		</div>
      		<input type="button" value='<bean:message key="message.comun.boton.aceptar"/>' class="okEO" onclick="cancel();" align="center" />
	   	</div> 	
		
		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
	</div>


<%-- 
<div id="capaFondo" class="capaFondo"></div>
--%>

<!-- 
<div class="titulo">Usuario - Propiedades</div>

<html:form action="/user/bd/userProperties" method="post">

	<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)"
		onclick="choosebox(1,2)">
	<table summary="" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="tableft"><img src="include/images/dot.gif" alt=""
				border="0" height="17" width="7"></td>
			<td id="tabmiddle1">General</td>
			<td class="tabright"><img src="include/images/dot.gif" alt=""
				border="0" height="17" width="7"></td>
		</tr>
	</table>
	</div>


	<div id="box1">
	<table width="95%" align="center" border="0">
		<tr>
			<td>&nbsp;</td>
			<td colspan="2">Nombre:</td>
			<td><bean:write name="basicForm" property="nombre" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2">Id:</td>
			<td><bean:write name="basicForm" property="id" /></td>
		</tr>
		<%-- 
		<tr>
			<td colspan="4">
			<hr>
			</td>
		</tr>
		--%>
		<tr>
			<td colspan="3">Perteneciente al departamento</td>
			<td><bean:write name="basicForm" property="belongToDept" /></td>
		</tr>

		<tr>
			<td colspan="2">Descripcion</td>
			<td colspan="2">
				<html:textarea property="descripcion" readonly="true"/>
			</td>
		</tr>
		
		<tr>
		<%-- 
			<td>
				<html:textarea property="descripcion" readonly="true"/>
			</td>
		
			<td colspan="4"><bean:write name="basicForm" property="descripcion" /></td>
			
		</tr>
		<tr style="height: 2px;">
			<td colspan="4" align="center">
			<hr>
			</td>
		</tr>
		--%>
		<tr>
			<td>Creado:</td>
			<td><input type="text" class="fecha"
				value='<bean:write name="basicForm" property="creationDate" />'
				size="10" disabled /></td>
			<td>por</td>
			<td><bean:write name="basicForm" property="creatorName" /></td>
		</tr>
		<tr>
			<td>Modificado:</td>
			<td><input type="text" class="fecha"
				value='<bean:write name="basicForm" property="updateDate" />'
				size="10" disabled /></td>
			<td>por</td>
			<td><bean:write name="basicForm" property="updaterName" /></td>
		</tr>
		<tr>
			<td colspan="3">Estado 
				<html:checkbox property="bloqueado" disabled="true" />
				bloqueado
			</td>
			<%-- 
			<td><html:checkbox property="bloqueado" disabled="true" /></td>
			--%>
		</tr>


	</table>
	</div>
	<input type="button" value="Aceptar" class="boton1" onclick="cancel();" />
</html:form>
-->

</body>
</html:html>