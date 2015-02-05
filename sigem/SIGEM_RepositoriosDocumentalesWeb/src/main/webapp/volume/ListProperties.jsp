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
	<html:form action="/volume/listProperties" method="post">
   <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:150px;">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
					<h1><bean:message key="message.lista.propiedades.titulo"/></h1>
					<div class="submenu3">
           				<ul>
        					<li class="submen1on" id="tab1"><img src="include/images/subme3_on.gif" />
        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
        					</li>
        					<img src="include/images/subme3_on_0.gif">
						</ul>
					</div>					
					<div class="cuadro" style="height: 150px;">
						<div id="box1"  style="height: 90%">
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
								<label class="gr" style="width:190px;"><bean:write name="listForm" property="name" /></label>
						    </div>                
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.id"/></label>
								<label class="gr" style="width:350px;"><bean:write name="listForm" property="id" /></label>
						    </div>
						    <div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
								<html:textarea property="description" readonly="true" name="listForm" rows="2" cols="65"/>
						    </div>
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.creado"/></label>
								<label class="gr" style="width:110px;"><bean:write name="listForm" property="creationDate" /></label>
								<label class="gr" style="width:30px;"><bean:message key="message.comun.etiqueta.por"/></label>
								<label class="gr" style="width:120px;"><bean:write name="listForm" property="creatorName" /></label>					
						    </div>
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.modificado"/></label>
								<label class="gr" style="width:110px;"><bean:write property="updateDate" name="listForm"/></label>
								<label class="gr" style="width:30px;"><bean:message key="message.comun.etiqueta.por"/></label>
								<label class="gr" style="width:120px;"><bean:write name="listForm" property="updaterName" /></label>							
						    </div>
					    </div>
	       			</div>     				
     			</div>
      		</div>
			<html:hidden property="id"/>		
			<input type="button" value="<bean:message key="message.comun.boton.aceptar"/>" class="okEO" onclick="cancel();" />
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
<%-- 
<div id="capaFondo" class="capaFondo"> </div>
--%>
<div class="titulo" > Lista - Propiedades </div>

<html:form action="/volume/listProperties" method="post">


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
      <td>&nbsp;</td>
      <td colspan="2">Nombre:</td>
      <td><bean:write name="listForm" property="name" /></td>
    </tr>

    <tr>   
      <td>&nbsp;</td>
      <td colspan="2">Id:</td>
      <td><bean:write name="listForm" property="id" /></td>
    </tr>
    <tr>
      <td colspan="4"><hr width="90%" align="center"></td>
    </tr>
    <tr>
	  <td>&nbsp;</td>
      <td colspan="3">Descripcion</td>
    </tr>
    <tr>
	  <td>&nbsp;</td>    	
      <td colspan="3">
      	<html:textarea property="description" readonly="true"/>
      </td>
    </tr>
	<tr>
      <td colspan="4" align="center"><hr></td>
    </tr>
    
    <tr>
      <td>Creado:</td>
      <td>
	    <html:text property="creationDate" readonly="true" size="10" style="border: 0;"/>
      </td>
	  <td>por</td>
      <td><bean:write name="listForm" property="creatorName" /></td>
    </tr>
    <tr>
      <td>Modificado:</td>
      <td>
      <html:text property="creationDate" readonly="true" size="10" style="border: 0;"/>
      </td>
  	  <td>por</td>
      <td><bean:write name="listForm" property="updaterName" /></td>
    </tr>
  </table> 
</div>

<html:hidden property="id"/>

<input type="button" value="Aceptar" class="boton1" onclick="cancel();" />
</html:form>





</body>
 -->

</html>