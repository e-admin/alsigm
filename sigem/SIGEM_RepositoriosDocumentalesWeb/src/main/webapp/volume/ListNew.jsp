<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" rev="stylesheet" href="include/css/tabs.css">
<link rel="stylesheet" rev="stylesheet" href="include/css/estilos.css">
<script src="include/js/docobj.js" type="text/javascript"></script>
<script src="include/js/tabs.js" type="text/javascript"></script>
<script src="include/js/validations.js" type="text/javascript"></script>

<script type="text/javascript">

	function validateForm(form)
	{
		var input = form.name;
		var correcto=true;
		var msg="<bean:message key="message.lista.nuevo.error"/>";
		input.value = stripBlanks(input.value);
		
		if (isBlank(input.value) ) {
			msg += "<bean:message key="message.lista.nuevo.error.nombrenulo"/>";
			correcto=false;
		}
		if ( !isValidText(input.value) ){
			msg+= "<bean:message key="message.lista.nuevo.error.nombreerroneo"/>" ;
			correcto=false;
		}
		
		var input = form.description;
		input.value = stripBlanks(input.value);
		if ( !isValidText(input.value) ){
			msg+= "<bean:message key="message.lista.nuevo.error.descripcionerronea"/>" ;
			correcto=false;
		}
		if (correcto == false ) {
			alert(msg);
		}
		return correcto;
	}			

function cancel(){
    var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) { 	
		document.location.href="<html:rewrite forward='cancel'/>";
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
	}	
}		

function aceptar(){
    var chequeo = chequearSession('<html:rewrite page="/chequearSession.do"/>');
    var formulario = document.forms[0];
	if( chequeo == "false" ) {
		if(validateForm(formulario)){
			document.forms[0].submit();
		}
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
	}	
}


</script>

</head>

<body>
	<html:form action="/volume/listNew" method="post" onsubmit="return validateForm(this);">
    <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:150px;">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
					<h1><bean:message key="message.lista.nuevo.titulo"/></h1>
					<div class="submenu3">
           				<ul>
        					<li class="submen1on" id="tab1"><img src="include/images/subme3_on.gif" />
        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
        					</li>
        					<img src="include/images/subme3_on_0.gif">
						</ul>
					</div>	
					<div class="cuadro" style="height: 150px;">
						<div id="box1" style="height: 90%">
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
								<label class="gr"><html:text property="name" style="width:350px;" /></label>
						    </div>                
						    <div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
								<html:textarea property="description" rows="6" cols="65" />
						    </div>
						  </div>  	
	       			    </div>			
     			</div>
      		</div>
			<!-- <html:submit value="Aceptar" styleClass="okEO"/> -->
			<input type="button" value="<bean:message key="message.comun.boton.aceptar"/>" class="okEO" onclick="aceptar();" />
			<input type="button" value="<bean:message key="message.comun.boton.cancelar"/>" class="ok" onclick="cancel();" />
			
			<html:hidden property="id"/>
			<input type="hidden" name="submitted" value="true" />
	   	</div> 	
		
		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
	</div>
	</html:form>
</body>
<!-- 
<body onload="choosebox(1,9);">



<div class="titulo" > Lista - Nueva </div>

<html:form action="/volume/listNew" method="post" onsubmit="return validateForm(this);">


<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,9)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="tableft" ><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td id="tabmiddle1">General</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</table>
</div>


<div id="box1">
<table width="80%" align="center" style="margin-top: 20px; margin-left: 10px;">
    <tr>
      <td>Nombre:</td>
      <td><html:text property="name"/></td>
    </tr>

    <tr>
      <td>Descripcion</td>
      <td><html:textarea property="description" rows="3" /></td>
    </tr>

  </table> 
</div>
<html:submit value="Aceptar" styleClass="boton1"/>
<input type="button" value="Cancelar" class="boton2" onclick="cancel();" />

<html:hidden property="id"/>
<input type="hidden" name="submitted" value="true" />

</html:form>





</body>
 -->

</html>