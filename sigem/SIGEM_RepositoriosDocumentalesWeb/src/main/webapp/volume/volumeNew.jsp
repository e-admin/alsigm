<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
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

<script language="javascript">

	function activaPestanhaGeneral()
	{
		document.getElementById("img1").src='include/images/subme3_on.gif';
		document.getElementById("img2").src='include/images/subme3_on_of.gif';
		document.getElementById("img3").src='include/images/subme3_of_0.gif';
		document.getElementById("tab1").className='submen1on';
		document.getElementById("tab2").className='submen1off';
	}

	function activaPestanhaListas()
	{
		document.getElementById("img1").src='include/images/subme3_off.gif';
		document.getElementById("img2").src='include/images/subme3_of_on.gif';
		document.getElementById("img3").src='include/images/subme3_on_0.gif';
		document.getElementById("tab1").className='submen1off';
		document.getElementById("tab2").className='submen1on';
	}

function fillPath(obj)
{
var name = document.getElementById('form').name.value;
if ( name != "" && name != null && ( obj.value == "" || obj.value == null ) )
	obj.value = name;
}

function checkForm(form)
{
	var input = form.name;
	var inputRuta = form.volPath;
	var msg="<bean:message key="message.volumen.nuevo.error"/>";
	var correcto=true;
	input.value = stripBlanks(input.value);
	inputRuta.value = stripBlanks(inputRuta.value);
	
	if (isBlank(input.value) ){
		msg += "<bean:message key="message.volumen.nuevo.error.nombrenulo"/>";
		correcto = false;
	}
	if (isBlank(inputRuta.value) ){
		msg += "<bean:message key="message.volumen.nuevo.error.rutanula"/>";
		correcto = false;
	}
	if ( !isValidText(input.value) ){
		msg += "<bean:message key="message.volumen.nuevo.error.nombreerroneor"/>" ;
		correcto = false;
	}		
	
	var input = form.description;
	if ( !isValidText(input.value) ){
		msg+= "<bean:message key="message.volumen.nuevo.error.descripcionerronea"/>" ;
		correcto = false;
	}
	if (!isBlank(form.maxSize.value)){
			if (!isNumeric(form.maxSize.value)){
				msg += "<bean:message key="message.volumen.nuevo.error.capacidaderronea"/>";
				correcto = false;
			}
		}
	else{
		msg +='<bean:message key="message.volumen.nuevo.error.capacidadnula"/>';
		correcto=false;
	}
	if (correcto == false) {
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
	
	function check(form){
		var nombre = form.name.value;
		var ruta = form.volPath.value;
		var msg="";
		
		if (isBlank(nombre) ){
			msg += " No se ha especificado nombre \n";
		}
		if ( !isValidText(nombre) ){
			msg += "El nombre del volumen no puede contener comillas simples o dobles \n" ;
		}
		
		if (isBlank(ruta) ){
			msg += " No se ha especificado la ruta del volumen \n";
		}
		if ( !isValidText(ruta) ){
			msg += "El nombre de la ruta no puede contener comillas simples o dobles \n" ;
		}
		
		if (msg.length > 0 ) {
			alert(msg);
			return false;
		}
		return true;
	}	
	
function aceptar(){
	var chequeo = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	var formulario = document.forms[0];
	if( chequeo == "false" ) {
		if(checkForm(formulario) == true){
			document.forms[0].submit();
		}
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
	}		
}

</script>
</head>

<body onload="choosebox(1,9);">
    <div id="contenedora">
		
		<div class="cuerpo" style="width:530px; height:185px;">
			<html:form action="/volume/volumeNew" method="post" styleId="form" onsubmit="return check(this);" >
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
						<h1><bean:message key="message.volumen.nuevo.titulo"/></h1>
						<div class="submenu3">
							<ul>
	        					<li class="submen1on" id="tab1" onclick="choosebox(1,9);activaPestanhaGeneral();"><img id="img1" src="include/images/subme3_on.gif" />
	        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
	        					</li>	        					
								<li class="submen1off" id="tab2" onclick="choosebox(2,9);activaPestanhaListas();"><img id="img2" src="include/images/subme3_on_of.gif" />
									<label id="tabmiddle2"><bean:message key="message.comun.pestana.listas"/></label>
									<img id="img3" src="include/images/subme3_of_0.gif" />
								</li>
							</ul>
						</div>	
						<div class="cuadro" style="height: 185px;">
							<div id="box1" style="width:100%">
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
									<label class="gr" ><html:text property="name" style="width:350px;"/></label>
							    </div>
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.ruta"/></label>
									<label class="gr"><html:text readonly="true" property="path" style="border: 0; width:350px;"/></label>
									<label class="gr" style="width:120px;"></label>
									<label class="gr"><html:text property="volPath" style="width:350px;" onfocus="fillPath(this);" /></label>									
							    </div>
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.capacidadmb"/></label>
									<label class="gr"><html:text property="maxSize" style="width:350px;" value="500"/></label>
							    </div>								
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
									<html:textarea property="description" rows="2" cols="65" />
							    </div>	
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.estado"/></label>
									<label class="gr" style="width:130px;"></label>
									<label class="gr" style="width:320px;">
								    <logic:iterate name="statesVolumeList" id="estado" property="states" scope="request">
									  <html:multibox style="width:20px;" property="states">
									   <bean:write name="estado" property="value"/> 
									  </html:multibox> 
								      <bean:define id="estadoRepo" name="estado" property="label" toScope="request"></bean:define>
									  <bean-el:message key="${estadoRepo}" />
									</logic:iterate>		
									</label>
							    </div>	
						
							</div>
							
							</div>
							<div id="box2" style="overflow:auto; height:150px; width:90%">
							
								<logic:iterate name="listasCollection" id="lista" property="list" scope="request">
								    <div class="col" style="width: 320px;">
										  <label class="gr" style="width: 50px;">
										  <html:multibox property="listAsociated">
										   <bean:write name="lista" property="value"/> 
										  </html:multibox>
										  </label>
										  <label class="gr" style="width: 100px;"><bean:write name="lista" property="label"/></label>
								    </div>										
								</logic:iterate>												
							</div>
		       			</div>   				

	     		<input type="hidden" name="submitted" value="true"/>
				<html:hidden property="id"/>
				<html:hidden property="path"/>
				<!-- <html:submit value="Aceptar" styleClass="okEO"/> -->
				<input type="button" value="<bean:message key="message.comun.boton.aceptar"/>" class="okEO" onclick="aceptar()" />
				<input type="button" value="<bean:message key="message.comun.boton.cancelar"/>" class="ok" onclick="cancel();" style="position:absolute; left:95px; top:230px; height:20px; width:87px;" />
				</html:form>
	
	   	</div> 	
		
		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
	</div>								

</body>


<!-- 
<body onload="choosebox(1,9);">

<div class="titulo" > Volumen - Nuevo </div>

<html:form action="/volume/volumeNew" method="post" styleId="form" onsubmit="return checkForm(this);" >


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
<table>
    <tr>
      <td>Nombre:</td>
      <td><html:text property="name" /></td>
    </tr>
	<tr>
      <td colspan="2">Ruta Acceso:</td>
    </tr>
    <tr>
      <td><%-- <bean:write name="volumeForm" property="path" /> --%>
      	<html:text readonly="true" property="path" style="border: 0" />
      </td>
      <td><html:text property="volPath" onfocus="fillPath(this);" /></td>
    </tr>
    <tr>
      <td>Capacidad (Mbytes):</td>
      <td><html:text property="maxSize"/></td>
    </tr>

    <tr>
      <td>Descripcion</td>
      <td><html:textarea property="description" rows="2" /></td>
    </tr>
	
    <tr>
    <td colspan="2">Estado </td>
    </tr>
    <tr> 
    	<td colspan="2">
	    <logic:iterate name="statesVolumeList" id="estado" property="states" scope="request">
		  <html:multibox property="states">
		   <bean:write name="estado" property="value"/> 
		  </html:multibox> 
	      <bean:write name="estado" property="label"/> 
		</logic:iterate>		
		</td>
    </tr>
  </table> 
</div>


<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,9)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tbody><tr>
	<td class="tableft" height="17" width="7">
		<img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td class="tabmiddle2" id="tabmiddle2">Listas</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</tbody></table>
</div>

<div style="z-index: 0;" id="box2">

<table border="0" width="85%" style="margin-top: 20px; margin-left: 15px;">
<logic:iterate name="listasCollection" id="lista" property="list" scope="request">
	<tr>
	<td>
  <html:multibox property="listAsociated">
   <bean:write name="lista" property="value"/> 
  </html:multibox> 
      <bean:write name="lista" property="label"/> 
      </td>
    </tr>
</logic:iterate>
</table>
</div>





<input type="hidden" name="submitted" value="true"/>
<html:hidden property="id"/>
<html:hidden property="path"/>
<html:submit value="Aceptar" styleClass="boton1"/>
	<input type="button" value="Cancelar" class="boton2" onclick="cancel();" />
</html:form>

</body>
 -->

</html>