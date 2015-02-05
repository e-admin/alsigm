<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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
function check(form)
{
	var input = form.name;
	var msg="<bean:message key="message.repositorio.nuevo.error"/>";
	var correcto=true;
	
	input.value = stripBlanks(input.value);
	
	if (isBlank(input.value) ){
		msg += "<bean:message key="message.repositorio.nuevo.error.nombrenulo"/>";
		correcto=false;
	}
	if ( !isValidText(input.value) ){
		msg += "<bean:message key="message.repositorio.nuevo.error.nombreerroneo"/>";
		correcto=false;		
	}
	var input = form.description;
	if ( !isValidText(input.value) ){
		msg += "<bean:message key="message.repositorio.nuevo.error.descripcionerronea"/>";
		correcto=false;				
	}
	
	if (correcto == false ) {
		alert(msg);
	}
	return correcto;
}

function aceptar(){
    var chequeo = chequearSession('<html:rewrite page="/chequearSession.do"/>');
    var formulario = document.forms[0];
	if( chequeo == "false" ) { 
	    if( check(formulario)) {
		    document.forms[0].submit();
		}
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
	}	
}	

function cancel(){
    var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) { 
		document.location.href="<html:rewrite forward='cancel'/>";
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
	}	
}		



</script>


</head>

<body>
	
    <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:170px;">
			<html:form action="/volume/repositoryNew" method="post" >
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
					<h1><bean:message key="message.repositorio.nuevo.paso1"/></h1>
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
									<label class="gr"><html:text name="repositoryForm" property="name" style="width:350px;"/></label>
							    </div>
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
									<html:textarea name="repositoryForm" property="description" rows="3" cols="65"></html:textarea>
							    </div>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.tipo"/></label>
									<html:select name="repositoryForm" property="type">
      									<html:options collection="typeCollection" property="value" labelProperty="label" />
      								</html:select>
							    </div>								    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:300px;"><bean:message key="message.comun.etiqueta.estado"/></label>
									<label class="gr" style="width:300px;">
										 <logic:iterate name="statesRepositoryList" id="estado" property="states" scope="request">
											  <html:multibox name="repositoryForm" property="states" style="width:20px;">
											  	<bean:write name="estado" property="value"/> 
											  </html:multibox>
											  <bean:define id="estadoRepo" name="estado" property="label" toScope="request"></bean:define>
										      <bean-el:message key="${estadoRepo}" />						      										      
										</logic:iterate>	
									</label>
								</div>							    					
						</div>						
	       			</div>  			
     			</div>
      		</div>
      		
      		<input type="hidden" name="step" value="1" />

			<input type="button" value="<bean:message key="message.comun.boton.siguiente"/>" class="okEO" onclick="javascript:aceptar();" />
			<input type="button" value="<bean:message key="message.comun.boton.cancelar"/>" class="ok" onclick="cancel();" />
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
<%-- 
<div id="capaFondo" class="capaFondo"> </div>
--%>

<div class="titulo" > Nuevo Repositorio - Paso 1 de 2 </div>

<html:form action="/volume/repositoryNew" method="post" onsubmit="return check(this);">


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
<table width="80%" align="center" style="margin-left: 8px; margin-top: 15px;">
    <tr>
      <td>Nombre:</td>
      <td><html:text property="name"/></td>
    </tr>

    <tr>
      <td>Tipo:</td>
      	<td>
      		<html:select property="type">
      			<html:options collection="typeCollection" property="value" labelProperty="label" />
      		</html:select>
      	</td>	
    </tr>
    <tr>
      <td>Descripcion</td>
      <td><html:textarea property="description"/></td>
    </tr>
    
	<tr>
		<td colspan="2">Estado: </td>
	</tr>
	<tr>
		<td colspan="2">
		 <logic:iterate name="statesRepositoryList" id="estado" property="states" scope="request">
		  <html:multibox property="states">
		   <bean:write name="estado" property="value"/> 
		  </html:multibox> 
	      <bean:write name="estado" property="label"/> 
		</logic:iterate>		
		</td>
	</tr>
  </table> 
</div>
<input type="hidden" name="step" value="1" />
<html:submit value="Siguiente" styleClass="boton1"/>
<input type="button" value="Cancelar" class="boton2" onclick="cancel();" />
</html:form>





</body>
 -->

</html>