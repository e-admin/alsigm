<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
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

	function activaPestanhaDetalle()
	{
		document.getElementById("img1").src='include/images/subme3_off.gif';
		document.getElementById("img2").src='include/images/subme3_of_on.gif';
		document.getElementById("img3").src='include/images/subme3_on_0.gif';
		document.getElementById("tab1").className='submen1off';
		document.getElementById("tab2").className='submen1on';
	}

	function validateForm()
	{
		var msg="<bean:message key="message.repositorio.nuevo.error"/>";
		var correcto=true;
		var nombre=document.forms[0].name.value;
			
		if (isEmptyText(document.forms[0].server.value)){
			msg +='<bean:message key="message.repositorio.nuevo.error.nombrenulo"/>';
			correcto=false;
		}
		else{
			var typeServer = document.forms[0].typeServer.options[document.forms[0].typeServer.selectedIndex].value;
			if (typeServer == 1 && !isIpCheck(document.forms[0].server.value ) )  {// Es una dirección ip
				msg +="<bean:message key="message.repositorio.nuevo.error.ipincorrecta"/>";
				correcto=false;
			}
		}		
		if (isBlank(nombre) ){
			msg += "<bean:message key="message.repositorio.nuevo.error.nombrenulo"/>";
			correcto=false;
		}
		if ( !isValidText(nombre) ){
			msg += "<bean:message key="message.repositorio.nuevo.error.nombreerroneo"/>" ;
			correcto=false;
		}
		var des=document.forms[0].description.value;
		if ( !isValidText(des) ){
			msg+= "<bean:message key="message.repositorio.nuevo.error.descripcionerronea"/>" ;	
			correcto=false;
		}
		if (correcto == false ) {
			alert(msg);
		}
		return correcto;
	}			

	function load()
	{
		choosebox(1,9);
		choosebox(2,9);
		choosebox(1,9);	
	}
	function cancel()
	{	
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) { 		
			document.location.href="<html:rewrite forward='cancel'/>";
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}			
	}
	function confirmDelete()
	{	
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) { 		
			return confirm('<bean:message key="message.confirm.delete.rep"/>');
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}			
	}	
	
	function actualiza(){
		var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
		parent.repTree.location.href = appBase + '/volume/repositoriesTree.do';
	}	
	
	function aceptar(){
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
    	var formulario = document.forms[0];
		if( check == "false" ) {
			if(validateForm()){
				document.forms[0].submit();
			}
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}			
	}
	
 	function met(){
 		document.getElementById("isTest").value="si";
 		document.forms[0].submit();
 	}
	
</script>


</head>

<body onload="load();">


    <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:180px;">
			<html:form action="/volume/repositoryEdit" method="post" onsubmit="return validateForm(this);">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
						<h1><bean:message key="message.repositorio.editar.titulo"/></h1>
						<div class="submenu3">
							<ul>
	        					<li class="submen1on" id="tab1" onclick="choosebox(1,9);activaPestanhaGeneral();"><img id="img1" src="include/images/subme3_on.gif" />
	        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
	        					</li>	        					
								<li class="submen1off" id="tab2" onclick="choosebox(2,9);activaPestanhaDetalle();"><img id="img2" src="include/images/subme3_on_of.gif" />
									<label id="tabmiddle2"><bean:message key="message.comun.pestana.deatalle"/></label>
									<img id="img3" src="include/images/subme3_of_0.gif" />
								</li>
							</ul>
						</div>	
						<div class="cuadro" style="height: 180px;">
							<div id="box1">
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
									<label class="gr" ><html:text property="name" style="width:190px;"/></label>
							    </div>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.repositorio"/></label>
									<label class="gr" style="width:350px;"><bean:write name="repositoryForm" property="type" /></label>
							    </div>
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
									<html:textarea property="description" rows="7" cols="65"></html:textarea>
							    </div>								
							</div>
							
							<div id="box2">
								<%-- Mientras sólo se puedan crear repositorios de tipo FTP
									 no tiene sentido mostrar la plataforma
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.plataforma"/></label>
									<label class="gr" style="width:350px;"><bean:write name="repositoryForm" property="os"/></label>
							    </div>
							    --%>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.servidor"/></label>
									<label class="gr" style="width:350px;"></label>
									<label class="gr" style="width:120px;"><html:select property="typeServer">
										<html:options collection="typeServerCollection" property="value" labelProperty="label" />	
									</html:select></label>
									<label class="gr"><html:text property="server" style="width:350px;"></html:text></label>									
							    </div>	
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.puerto"/></label>
									<label claxss="gr"><html:text property="port" style="width:350px;"/></label>									
							    </div>							    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.usuario"/></label>
									<label class="gr"><html:text property="user" style="width:350px;"/></label>									
							    </div>	
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.contrasena"/></label>
									<label class="gr"><html:password property="pwd" style="width:350px;"/></label>									
							    </div>								    						    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.ruta"/></label>
									<%--
									<label class="gr"><html:text property="path" style="width:350px;"/></label>
									--%>
									<html:hidden property="path"/>
									<label class="gr"><bean:write name="repositoryForm" property="path" /></label>
							    </div>								    								
							</div>							
		       			</div>   				
     			</div>
      		</div>

			<html:hidden property="id"/>
			<input type="hidden" name="submitted" value="true" />
			<!--<html:submit value="Aceptar" styleClass="okEO"/>-->
			<input type="button" id="botonAceptar" value="<bean:message key="message.comun.boton.aceptar"/>" class="okEO" onclick="aceptar();" disabled="true" />
	
			<input type="hidden" id="isTest" name="test" value="no" />
			
			<c:choose> 
			  <c:when test="${testOk eq 'success'}" > 
			    <input type="button" value="<bean:message key="message.comun.boton.testok"/>" class="okVerde" style="position:absolute; left:275px; top:225px; height:20px; width:87px;" onclick="met();">
			    <script>
			    	document.getElementById("botonAceptar").disabled=false;
			    </script>
			  </c:when> 
			  <c:when test="${testOk eq 'error'}" > 
				<input type="button" value="<bean:message key="message.comun.boton.testerror"/>" class="okRojo" style="position:absolute; left:275px; top:225px; height:20px; width:87px;" onclick="met();">
			  </c:when> 
			  <c:otherwise> 
				<input type="button" value="<bean:message key="message.comun.boton.test"/>" class="ok" style="position:absolute; left:275px; top:225px; height:20px; width:87px;" onclick="met();">
			  </c:otherwise> 
			</c:choose> 	
				 
			</html:form>
			
			<html-el:form action="/volume/delete" onsubmit="return confirmDelete();" >
				<html-el:hidden property="id" value="${repositoryForm.id}"/>
				<html-el:hidden property="type" value="1"/>
				<html-el:submit value="Eliminar" styleClass="ok" style="position:absolute; left:95px; top:225px; height:20px; width:87px;"/>
				<!-- message.comun.boton.elimimar -->
				<input type="button" value="<bean:message key="message.comun.boton.cancelar"/>" class="ok" onclick="cancel();" style="position:absolute; left:185px; top:225px; height:20px; width:87px;" />
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
<body onload="load();">
<%-- 
<div id="capaFondo" class="capaFondo"> </div>
--%>

<div class="titulo" > Repositorio - Edit </div>

<html:form action="/volume/repositoryEdit" method="post" onsubmit="return validateForm(this);">


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
<table width="90%" style="margin-left: 20px; margin-top: 15px;">
    <tr>
      <td>Nombre:</td>
      <td><html:text property="name"/></td>
    </tr>

    <tr>
      <td>Repositorio:</td>
      <td><bean:write name="repositoryForm" property="type" /></td>
    </tr>
    <tr>
      <td colspan="2"><hr width="90%" align="center"></td>
    </tr>
    <tr>
      <td>Descripcion</td>
	  <td>
      <html:textarea property="description" rows="3"/></td>
    </tr>
  </table> 
</div>

<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,9)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="tableft" ><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td id="tabmiddle2">Detalle</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</table>
</div>



<div id="box2">

<table summary="" border="0" cellpadding="0" cellspacing="0" width="95;" style="margin-left: 10px; margin-top: 5px;">

<c:choose>
<c:when test="${requestScope.type == 1}"> <%-- Si es FTP --%>
<tr>
	<td>Plataforma:</td><td><bean:write name="repositoryForm" property="os"/></td>
</tr>
<tr>
	<td>Servidor</td><td>&nbsp;</td>
</tr>
<tr>
	<td><html:select property="typeServer">
		<html:options collection="typeServerCollection" property="value" labelProperty="label" /> </html:select> 
	</td>	
	<td><html:text property="server"></html:text> </td>
</tr>

<tr>
	<td>Puerto</td><td><html:text property="port"/></td>
</tr>

<tr>
	<td>Usuario</td><td><html:text property="user"/></td>
</tr>
<tr>
	<td>Contraseña</td><td><html:password property="pwd"/></td>
</tr>

<tr>
	<td>Ruta acceso:</td> <td><bean:write name="repositoryForm" property="path"/></td>
</tr>
</c:when>

<c:when test="${requestScope.type == 3}"> <%-- Si es PFS --%>
<tr>
	<td>Ruta acceso:</td> <td><html:text property="path"/></td>
</tr>

</c:when>

</c:choose>

<tr>
	<td colspan=2>Estado</td>
</tr>
<tr>
	<td colspan=2>
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
<html:hidden property="id"/>
<input type="hidden" name="submitted" value="true" />
<html:submit value="Aceptar" styleClass="boton1"/>
</html:form>

<html-el:form action="/volume/delete" onsubmit="return confirmDelete();">
	<html-el:hidden property="id" value="${repositoryForm.id}"/>
	<html-el:hidden property="type" value="1"/>
	<html-el:submit value="Eliminar" styleClass="boton2"/>

	<input type="button" value="Cancelar" class="boton3" onclick="cancel();" />
</html-el:form>



</body>
 -->
</html>