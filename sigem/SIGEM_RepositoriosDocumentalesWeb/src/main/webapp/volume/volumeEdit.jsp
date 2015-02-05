<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>

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

	function validateForm(form)
	{
		var input = form.name;
		var msg="<bean:message key="message.volumen.nuevo.error"/>";
		var correcto=true;
		input.value = stripBlanks(input.value);
		
		if (isBlank(input.value) ){
			msg += "<bean:message key="message.volumen.nuevo.error.nombrenulo"/>";
			correcto=false;
		}
		if ( !isValidText(input.value) ){
			msg+= "<bean:message key="message.volumen.nuevo.error.nombreerroneo"/>" ;
			correcto=false;
		}
		maxSize = form.maxSize.value;
		if (!isBlank(maxSize)){
			if (!isNumeric(maxSize)){
				msg += "<bean:message key="message.volumen.nuevo.error.capacidaderronea"/>";
				correcto=false;
			}
		}
		else{
			msg +="<bean:message key="message.volumen.nuevo.error.capacidadnula"/>";
			correcto=false;
		}
		var input = form.description;
		if ( !isValidText(input.value) ){
			msg+= "<bean:message key="message.volumen.nuevo.error.descripcionerronea"/>" ;
			correcto=false;
		}
		
		if (correcto == false ) {
			alert(msg);
		}
		return correcto;
	}			
	
	function cancel(){
		var chequeo = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( chequeo == "false" ) {	
			document.location.href="<html:rewrite forward='cancel'/>";
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}
	}
	
	function confirmDelete(){		
		var chequeo = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( chequeo == "false" ) {	
			return confirm('<bean:message key="message.confirm.delete.volume"/>');
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}		
	}
	
	function aceptar(){
		var chequeo = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		var formulario = document.forms[0];
		if( chequeo == "false" ) {
			if(validateForm(formulario) == true){
				formulario.submit();
			}
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}		
	}			
	
</script>

</head>
<body onload="choosebox(1,9);">
    <div id="contenedora">
    
		<div class="cuerpo" style="width:530px; height:165px;">
			<html:form action="/volume/volumeEdit" method="post" onsubmit="return validateForm(this);">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
						<h1><bean:message key="message.volumen.editar.titulo"/></h1>
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
						<div class="cuadro" style="height: 165px;">
							<div id="box1" style="width:100%">
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
									<label class="gr"><html:text property="name" style="width:350px;"/></label>
							    </div>
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.capacidadmb"/></label>
									<label class="gr"><html:text property="maxSize" style="width:350px;"/></label>
							    </div>								
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
									<html:textarea property="description" cols="65" rows="3"/>
							    </div>	
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.estado"/></label>
									<label class="gr" style="width:130px;"></label>
									<label class="gr" style="width:320px;">
								    <logic:iterate name="statesVolumeList" id="estado" property="states" scope="request">
									  <html:multibox property="states" style="width:20px;" >
									   <bean:write name="estado" property="value"/> 
									  </html:multibox> 
								      <bean:define id="estadoRepo" name="estado" property="label" toScope="request"></bean:define>
									  <bean-el:message key="${estadoRepo}" />
									</logic:iterate>		
									</label>
							    </div>	
  							
							</div>
							<div id="box2" style="overflow:auto; height:150px; width:100%">
							
								<logic:iterate name="listasCollection" id="lista" property="list" scope="request">
								    <div class="col" style="width: 400px;">
										  <label class="gr" style="width: 50px;">
										  <html:multibox property="listAsociated" style="width:20px;">
										   <bean:write name="lista" property="value"/> 
										  </html:multibox>
										  </label>
										  <label class="gr" style="width: 100px;"><bean:write name="lista" property="label"/></label>
								    </div>										
								</logic:iterate>							
							</div>
		       			</div>   				
     			</div>
      		</div>

			<input type="hidden" name="submitted" value="true"/>
			<html:hidden property="id"/>
			<!-- <html:submit value="Aceptar" styleClass="okEO"/> -->
			<input type="button" value="<bean:message key="message.comun.boton.aceptar"/>" class="okEO" onclick="aceptar();" />
			</html:form>
			
			<html-el:form action="/volume/delete" onsubmit="return confirmDelete();">
				<html-el:hidden property="id" value="${volumeForm.id}"/>
				<html-el:hidden property="type" value="4"/>
				<html-el:submit value="Eliminar" styleClass="ok" style="position:absolute; left:95px; top:210px; height:20px; width:87px;" />
				<input type="button" value="<bean:message key="message.comun.boton.cancelar"/>" class="ok" onclick="cancel();" style="position:absolute; left:185px; top:210px; height:20px; width:87px;" />
			</html-el:form>	
		
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
<%-- 
<div id="capaFondo" class="capaFondo"> </div>
--%>

<div class="titulo" > Volumen - Editar </div>

<html:form action="/volume/volumeEdit" method="post" onsubmit="return validateForm(this);">


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
      <td>Capacidad (MB):</td>
      <td><html:text property="maxSize"/></td>
    </tr>
    <tr>
      <td colspan="2"><hr width="90%" align="center"></td>
    </tr>
    <tr>
      <td colspan="2">Descripcion</td>
    </tr>
    <tr>
      <td colspan="2"><html:textarea property="description"/></td>
    </tr>
    <tr>
      <td colspan="2">Estado</td>
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

<table border="0" width="85%" style="margin-left: 20px; margin-top: 10px;">
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
<html:submit value="Aceptar" styleClass="boton1"/>
</html:form>

<html-el:form action="/volume/delete" onsubmit="return confirmDelete();">
	<html-el:hidden property="id" value="${volumeForm.id}"/>
	<html-el:hidden property="type" value="4"/>
	<html-el:submit value="Eliminar" styleClass="boton2"/>
	<input type="button" value="Cancelar" class="boton3" onclick="cancel();" />
</html-el:form>

</body>
 -->

</html>