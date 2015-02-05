<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ page import="ieci.tecdoc.mvc.util.Constantes" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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

<ieci:baseInvesDoc/>

<link rel="Stylesheet" rev="Stylesheet" href="include/css/tabs.css" />
<link rel="Stylesheet" rev="Stylesheet" href="include/css/estilos.css" />
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
<script src="include/js/docobj.js" type="text/javascript"></script>
<script src="include/js/tabs.js" type="text/javascript"></script>
<script src="include/js/edition.js" type="text/javascript"></script>
<script src="include/js/validations.js" type="text/javascript"></script>

<script language="javascript">

	function activaPestanhaGeneral()
	{
		document.getElementById("img1").src='include/images/subme3_on.gif';
		document.getElementById("img2").src='include/images/subme3_on_of.gif';
		document.getElementById("img3").src='include/images/subme3_of_of.gif';	
		document.getElementById("img4").src='include/images/subme3_of_of.gif';
		document.getElementById("img5").src='include/images/subme3_of_0.gif';
		document.getElementById("tab1").className='submen1on';
		document.getElementById("tab2").className='submen1off';
		document.getElementById("tab3").className='submen1off';
		document.getElementById("tab4").className='submen1off';	
	}
	
	function activaPestanhaDatosPersonales()
	{
		document.getElementById("img1").src='include/images/subme3_off.gif';
		document.getElementById("img2").src='include/images/subme3_of_on.gif';
		document.getElementById("img3").src='include/images/subme3_on_of.gif';
		document.getElementById("img4").src='include/images/subme3_of_of.gif';
		document.getElementById("img5").src='include/images/subme3_of_0.gif';
		document.getElementById("tab1").className='submen1off';
		document.getElementById("tab2").className='submen1on';
		document.getElementById("tab3").className='submen1off';
		document.getElementById("tab4").className='submen1off';	
	}	
	
	function activaPestanhaPerfil()
	{
		document.getElementById("img1").src='include/images/subme3_off.gif';
		document.getElementById("img2").src='include/images/subme3_of_of.gif';
		document.getElementById("img3").src='include/images/subme3_of_on.gif';	
		document.getElementById("img4").src='include/images/subme3_on_of.gif';
		document.getElementById("img5").src='include/images/subme3_of_0.gif';
		document.getElementById("tab1").className='submen1off';
		document.getElementById("tab2").className='submen1off';
		document.getElementById("tab3").className='submen1on';	
		document.getElementById("tab4").className='submen1off';	
	}	
	
	function activaPestanhaGrupos()
	{
		document.getElementById("img1").src='include/images/subme3_off.gif';
		document.getElementById("img2").src='include/images/subme3_of_of.gif';
		document.getElementById("img3").src='include/images/subme3_of_of.gif';		
		document.getElementById("img4").src='include/images/subme3_of_on.gif';
		document.getElementById("img5").src='include/images/subme3_on_0.gif';
		document.getElementById("tab1").className='submen1off';
		document.getElementById("tab2").className='submen1off';
		document.getElementById("tab3").className='submen1off';	
		document.getElementById("tab4").className='submen1on';	
	}


	function validateForm()
	{
		var pwd = eval(userGeneral[1]).value;
		var re = /^\*\*\*\*$/;
		var ar = re.exec(pwd);
		var msg="<bean:message key='message.usuario.nuevo.error'/>";
		var correcto=true;
		
		var input = eval(userGeneral[0]);
		input.value = stripBlanks(input.value);
		
		if (isBlank(input.value) ){
			msg += "<bean:message key="message.usuario.nuevo.error.nombrenulo"/>";
			correcto=false;
		}
		if ( !isValidText(input.value) ){
			msg+= "<bean:message key="message.usuario.nuevo.error.nombreerroneo"/>" ;
			correcto=false;
		}
		var input = eval(userGeneral[3]);
		input.value = stripBlanks(input.value);
		
		if ( !isValidText(input.value) ){
			msg+= "<bean:message key="message.usuario.nuevo.error.descripcionerronea"/>" ;
			correcto=false;
		}
		if (ar == null ) {
			var repwd = eval(userGeneral[2]).value;
			if (!isValidPwd(pwd)){
				msg +="<bean:message key='message.usuario.nuevo.error.contrasena'/>";
				correcto=false;
			}
			else {
				if (pwd != repwd ){
					msg +="<bean:message key='message.usuario.nuevo.error.contrasenaconfirmacion'/>";
					correcto=false;
				}
			}
			
		}
		if (correcto == false ) {
			alert(msg);
		}
	
		return correcto;
	}


function enableGroups()
{
	var groups = document.getElementsByName('gruposAsignados');
	for (i = 0; i < groups.length; i ++ )
		groups[i].disabled = false;

}
function cancel()
{
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {	
			document.location.href="<html:rewrite forward='cancel'/>";
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}	
}

function confirmDelete(){	
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {	
		return confirm('<bean:message key="message.confirm.delete.user"/>');
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}	
}

function aceptar(){
	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
	if( check == "false" ) {
		if(validateForm()){
			document.forms[0].submit();
		}
	} else {
		window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
	}	
}
		
</script>

<logic:present name="<%=Constantes.TOKEN_USER_CONNECTED%>" >

<%-- 
UserConnected: <bean:write name="user" property="profiles"/> <br>
UserDTO: <bean:write name="userDTO" property="profiles"/>
--%>

<logic:greaterEqual name="user" property="profile(2)" value="1"> <%-- Al menos es IUSER_STANDARD y puede ver la pagina --%>
<!--  Puede leer esto, Consultar Administrador de usuarios<br> -->
</logic:greaterEqual>

<%-- 
<c:out value="userProfile: ${sessionScope.user.userProfile}"></c:out> <br>
<c:out value="userDTOProfile: ${requestScope.userDTO.userProfile}"></c:out> <br>

<c:out value="userID: ${sessionScope.user.id}"></c:out> <br>
<c:out value="userDTOiD: ${sessionScope.userDTO.id}"></c:out> <br>
<c:out value="ManagerId: ${sessionScope.managerId}"/>
--%>

<!-- && <c:out value="mgrDeptId: ${param.mgrDeptId}"/> -->
<c:choose>
	<c:when test="${not empty param.mgrDeptId}">
		<c:set target="request" var="mgrDeptId">
			<c:out value="${param.mgrDeptId}"/>
		</c:set>
	</c:when>
	<c:otherwise> <!-- Vacio -->
		<c:set target="request" var="mgrDeptId">
			<c:out value="${sessionScope.managerId}"/>
		</c:set>
	</c:otherwise>
</c:choose>
<!-- <c:out value="mgrDeptId#${mgrDeptId}#"/> -->


<c:if test="${mgrDeptId eq sessionScope.user.id}">
	 <!-- Es manager Del Dtpo al que pertenece el usuario -->
</c:if>


<c:import url="../common/limitUserPrivilege.jsp">
	<c:param name="method" value="bd"/>
	<c:param name="mgrDeptId" value="${mgrDeptId}"/>
</c:import>





</logic:present>

<logic:notPresent name="<%=Constantes.TOKEN_USER_CONNECTED%>">

</logic:notPresent>
</head>


<body onload="choosebox(1,9);" style="background-color: #ffffff">
	<div id="contenedora">
    	<html:form action="/user/bd/userEdit" method="post" onsubmit="return validateForm();">
			<div class="cuerpo" style="width:530px; height:180px;">
	   			<div class="cuerporight">
	    			<div class="cuerpomid">
						<h1><bean:message key="message.usuario.editar.titulo"/></h1>
						<div class="submenu3">
							<ul>
	        					<li class="submen1on" id="tab1" onclick="choosebox(1,9);activaPestanhaGeneral();">
	        						<img id="img1" src="include/images/subme3_on.gif" />
	        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
	        					</li>
	        					<li class="submen1off" id="tab2" onclick="choosebox(2,9);activaPestanhaDatosPersonales();">
									<img id="img2" src="include/images/subme3_on_of.gif" />
									<label id="tabmiddle2"><bean:message key="message.comun.pestana.datospersonales"/></label>									
								</li>
								<li class="submen1off" id="tab3" onclick="choosebox(3,9);activaPestanhaPerfil();">
									<img id="img3" src="include/images/subme3_of_of.gif" />
									<label id="tabmiddle3"><bean:message key="message.comun.pestana.perfiles"/></label>
								</li>	        					
								<li class="submen1off" id="tab4" onclick="choosebox(4,9);activaPestanhaGrupos();">
									<img id="img4" src="include/images/subme3_of_of.gif" />
									<label id="tabmiddle4"><bean:message key="message.comun.pestana.grupos"/></label>
									<img id="img5" src="include/images/subme3_of_0.gif" />
								</li>
							</ul>							
						</div>	
						<div class="cuadro" style="height: 180px;">
							<div id="box1" style="width:100%">
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
									<label class="gr"><html:text property="nombre" styleId="user.name" style="width:350px;"/></label>
							    </div>                
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.usuario.etiqueta.contrasena"/></label>
									<label class="gr"><html:password property="pwd" styleId="user.pwd" style="width:350px;" /></label>
							    </div>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.usuario.etiqueta.confirmacion"/></label>
									<label class="gr"><html:password property="repwd" styleId="user.repwd" style="width:350px;" /></label>
							    </div>  							    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:255px;"><bean:message key="message.usuario.etiqueta.iniciarcontrasena"/></label>
									<label class="gr" style="width:65px;"><html:checkbox property="pwdmbc" styleId="user.pwdmbc" style="width:20px" /></label>
							    </div>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:255px;"><bean:message key="message.usuario.etiqueta.comprobarcontrasena"/></label>
									<label class="gr" style="width:65px;"><html:checkbox property="pwdvpcheck" styleId="user.pwdvpcheck" style="width:20px" /></label>
							    </div> 							    				    					                    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
									<html:text property="descripcion" styleId="user.description" style="width:350px; height: 30px;"/>								
								</div>
							</div>
	
							<div id="box2" style="height:150px; width:100%">
								
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:140px;" for="user.nombrePersonal">
										<bean:message key="message.usuario.etiqueta.nombre"/>
									</label>
									<html:text property="nombrePersonal" styleId="user.nombrePersonal" maxlength="256" style="width:320px;"/>									
							    </div> 
							    
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:140px;" for="user.apellidos">
										<bean:message key="message.usuario.etiqueta.apellidos"/>
									</label>
									<html:text property="apellidos" styleId="user.apellidos" maxlength="256" style="width:320px;"/>									
							    </div> 
							    
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:140px;" for="user.cargo">
										<bean:message key="message.usuario.etiqueta.cargo"/>
									</label>
									<html:text property="cargo" styleId="user.cargo" maxlength="256" style="width:320px;"/>									
							    </div>    
							    
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:140px;" for="user.email">
										<bean:message key="message.usuario.etiqueta.email"/>
									</label>									
									<html:text property="email" styleId="user.email" maxlength="256" style="width:320px;"/>									
							    </div>
							    
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:140px;" for="user.telefonoMovil">
										<bean:message key="message.usuario.etiqueta.telefonoMovil"/>
									</label>
									<html:text property="tfnoMovil" styleId="user.telefonoMovil" maxlength="16" style="width:320px;"/>									
							    </div>
							    
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:180px;" for="user.IdCertDigital">
										<bean:message key="message.usuario.etiqueta.idCertificadoDigital"/>
									</label>								
									<html:text property="idCert" styleId="user.IdCertDigital" maxlength="256" style="width:280px;"/>									
							    </div>
							
							</div>	
							
							<div id="box3" style="overflow:auto; height:150px; width:100%">
								<table border class="tableBase" width="100%">
									<tr>
										<th width="30%"><bean:message key="message.usuario.etiqueta.aplicacion"/></th>
										<th><bean:message key="message.usuario.etiqueta.perfil"/></th>
									</tr>
									
									 <logic:iterate name="userFormBd" id="aplicacion" property="listaAplicaciones" indexId="i">
									 <tr>
									 	<td><bean:write name="aplicacion" property="descripcion" /><br></td>
									 	<td>
									 		<table>
									 			<tr>
													<td>
														<c:choose>
															<c:when test="${userFormBd.listaAplicaciones[i].editableSinPermisos}">
												 				<html-el:radio property="listaAplicaciones[${i}].perfil" value="0" style="width: 20px">Sin permisos</html-el:radio>
															</c:when>
															<c:otherwise>
												 				<html-el:radio property="listaAplicaciones[${i}].perfil" value="0" disabled="true" style="width: 20px">Sin permisos</html-el:radio>
															</c:otherwise>
														</c:choose>
									 				</td>
									 				
									 				<td>
									 					<c:choose>
									 						<c:when test="${userFormBd.listaAplicaciones[i].editableUsuarioEstandar}">
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="1" style="width: 20px">Estandar</html-el:radio>
									 						</c:when>
									 						<c:otherwise>
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="1" style="width: 20px" disabled="true">Estandar</html-el:radio>
									 						</c:otherwise>
									 					</c:choose>
													</td>
													<td>
									 					<c:choose>
									 						<c:when test="${userFormBd.listaAplicaciones[i].editableUsuarioAdministrador}">
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="3" style="width: 20px">Administrador</html-el:radio>
									 						</c:when>
									 						<c:otherwise>
									 							<html-el:radio property="listaAplicaciones[${i}].perfil" value="3" style="width: 20px" disabled="true">Administrador</html-el:radio>
									 						</c:otherwise>
									 					</c:choose>														
													</td>
									 			</tr>
									 		</table>
									 	</td>
									 </tr>
									 </logic:iterate>
								 </table>					
							</div>	
							
							<div id="box4" style="overflow:auto; height:150px; width:100%">
								<table border="0" width="95%">
								<logic:iterate name="gruposListDTO" id="grupo" property="grupos" scope="request">
									<tr>
									<td>
									<c:choose>
										<c:when test="${grupo.isDisabled}">
										  <html:multibox property="gruposAsignados" disabled="true" styleId="gruposAsignados" style="width:20px"  >
											   <bean:write name="grupo" property="id"/> 
										  </html:multibox> 			
										</c:when>
										<c:otherwise>
											<html:multibox property="gruposAsignados" styleId="gruposAsignados" style="width:20px" >
											   <bean:write name="grupo" property="id"/> 
										  </html:multibox> 
										</c:otherwise>
									</c:choose>
								    <bean:write name="grupo" property="nombre"/> 
								      </td>
								    </tr>
								</logic:iterate>
								</table>							
							</div>
						</div>
					</div>							
		       	</div>   				
      		</div>
			<html:hidden property="submitted" value="true"/>
			<html:hidden property="id" />
			<!-- <html:submit value="Aceptar" styleClass="okEO"/> -->
			<input type="button" value="<bean:message key='message.comun.boton.aceptar'/>" class="okEO" onclick="aceptar();" /> 
			<%-- <input type="button" class="boton3" value="checkPwd" onclick="validateForm();"/> --%>
			
			<input type="button" value="<bean:message key='message.comun.boton.cancelar'/>" class="ok" onclick="cancel();" />
			
		</html:form>
			
		<html:form action="/user/bd/userDelete" method="post" onsubmit="return confirmDelete();" >
			<html:hidden property="id" />
			<%-- Validaci�n ya hecha en el Action --%>
			<input type="submit" value="<bean:message key='message.comun.boton.elimimar'/>" class="ok" style="position:absolute; left:185px; top:225px; height:20px; width:87px;" /> 
			<!--<html:submit value="Eliminar" styleClass="ok"  style="position:absolute; left:185px; top:225px; height:20px; width:87px;" /> -->
		</html:form>	
					
</body>





<!-- 
<body onload="choosebox(1,2); carga(); limitUserPrivilege();">


<div class="titulo" > Usuario - Editar </div>
<html:form action="/user/bd/userEdit" method="post" onsubmit="return validateForm();">

<div id="capaSistema" style="visibility: hidden;" class="contexto">

<table align="center" width="100%">
	<tbody><tr>
		<td><html:checkbox property="systemSuperuser" onchange="System(this)" styleId="system.superuser"/></td>
		<td>Superusuario</td>
	</tr>
</tbody></table>
</div>

<div id="capaAdminUsuarios" style="visibility: hidden;" class="contexto">

<table align="center" width="100%">
	<tbody><tr>
	 <td><html:radio property="user" value="superuser" styleId="user.superuser"/></td>
	 <td>Superusuario</td>
	</tr>
	
	<tr>
	 <td><html:radio property="user" value="manager" styleId="user.manager"/></td>
	 <td>Administrador</td>
	</tr>	
	<tr>
	  <td><html:radio property="user" value="standard" styleId="user.standard"/>
	  </td><td>Est�ndard</td>
	</tr>  
        
	<tr>
		<td><html:radio property="user" value="none" styleId="user.none" /></td>
		<td>Sin derechos</td>
	</tr>
</tbody></table>
</div>

<div id="capaInvesdoc" style="visibility: hidden;" class="contexto"> 

<table align="center" width="100%">
	<tbody><tr>
	 <td><html:radio property="idoc" value="superuser" onchange="adminIdoc(this);" styleId="idoc.superuser"/> </td>
	 <td>Superusuario</td>
	</tr>
	<tr>
	 <td><html:radio property="idoc" value="manager" onchange="adminIdoc(this);" styleId="idoc.manager"/></td>
	 <td>Administrador</td>
	</tr>
	<tr>
	 <td><html:radio property="idoc" value="standard" onchange="adminIdoc(this);" styleId="idoc.standard"/></td>
	 <td>Est�ndar</td>
	</tr>
	<tr>
	 <td><html:radio property="idoc" value="none" onchange="adminIdoc(this);" styleId="idoc.none"/></td>
	 <td>Sin derechos </td>
	</tr>
</tbody></table>
</div>


<div id="capaAdminVolumen" style="visibility: hidden;" class="contexto">

<table align="center" width="100%">
<tbody><tr>
	<td><html:checkbox property="volumeSuperuser" styleId="volume.superuser"/></td>
	<td>Superusuario</td>
</tr>
</tbody></table>
</div>




<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,2)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="tableft" height="17" width="7">
			<img src="include/images/dot.gif" alt="" border="0" height="17" width="7">
		</td>
		<td id="tabmiddle1">General</td>
		<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	</tr>
</tbody>
</table>
</div>

<div id="box1">
<table>
<tr> <td> Nombre: </td> <td> <html:text property="nombre" styleId="user.name" /></td>
<tr> <td> Contrase�a: </td><td><html:password property="pwd" styleId="user.pwd" /></tr>
<tr> <td> Confirmacion: </td><td><html:password property="repwd" styleId="user.repwd" /></tr>
<tr> <td colspan="2"><html:checkbox property="pwdmbc" styleId="user.pwdmbc" /> Inicializaci�n obligatoria de la contrase�a </td></tr>
<tr> <td colspan="2"><html:checkbox property="pwdvpcheck" styleId="user.pwdvpcheck" /> Comprobar caducidad de la contrase�a </td></tr>

<c:if test="${applicationScope.useCertificate}">
	<tr> <td>Id.Certificado</td> <td><html:text property="idCert" styleId="user.idCert" /></td></tr>
</c:if>
<tr> <td valign="top"> Descripcion </td><td><html:text property="descripcion" styleId="user.description"/></td></tr>
<%-- 
<tr> <td valign="top"> Descripcion </td><td><html:textarea property="descripcion" styleId="user.description" cols="15" rows="2"/></td></tr>
--%>
</table>
</div>

<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,2)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="tableft" height="17" width="7">
			<img src="include/images/dot.gif" alt="" border="0" height="17" width="7">
		</td>
		<td class="tabmiddle2" id="tabmiddle2">Perfiles</td>
		<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	</tr>
</tbody></table>
</div>

<div style="z-index: 0;" id="box2">
 <table align="center" border="0" width="95%">
    <tbody><tr>
      <td><p>Contexto:</p>      </td>
    </tr>
    <tr>
      <td><select style="visibility: hidden;" id="selectContexto" onchange="mostrarCapas(this)">
      		<option value="system">Sistema</option>
      		<option value="user">Administracion de Usuarios</option>
      		<option value="invesdoc" selected="selected">w@rdA</option>
      		<option value="volume">Administrador de vol�menes</option></select></td>
    </tr>
  </tbody></table>
</div>

<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)" onclick="choosebox(3,2)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tbody><tr>
	<td class="tableft" height="17" width="7">
		<img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td class="tabmiddle3" id="tabmiddle3">Permisos</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</tbody></table>
</div>

<div style="z-index: 0;" id="box3">
			<table border="0" width="95%">
				<tr>
					<td><html:checkbox property="idocConsulta" onclick="valida(this);" styleId="idocConsulta" /></td>
					<td>Consulta</td>
				</tr>
				<tr>
					<td><html:checkbox property="idocModificacion" onclick="valida(this);" styleId="idocModificacion" /></td>
					<td>Modificaci�n</td>
				</tr>				
				<tr>
					<td><html:checkbox property="idocCreacion" onclick="valida(this);" styleId="idocCreacion"/></td>
					<td>Creaci�n</td>
				</tr>				
				<tr>
					<td><html:checkbox property="idocBorrado" onclick="valida(this);" styleId="idocBorrado"/></td>
					<td>Borrado</td>
				</tr>				
				<tr>
					<td><html:checkbox property="idocImpresion" onclick="valida(this);" styleId="idocImpresion"/></td>
					<td>Impresion</td>
				</tr>					
				</table>
			<br><br>
</div>


<div id="tab4" onmouseover="tabover(4)" onmouseout="tabout(4)" onclick="choosebox(4,2)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tbody><tr>
	<td class="tableft" height="17" width="7">
		<img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td class="tabmiddle4" id="tabmiddle4">Grupos</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</tbody></table>
</div>

<div style="z-index: 0;" id="box4">

<table border="0" width="95%">
<logic:iterate name="gruposListDTO" id="grupo" property="grupos" scope="request">
	<tr>
	<td>
	<c:choose>
		<c:when test="${grupo.isDisabled}">
		  <html:multibox property="gruposAsignados" disabled="true" styleId="gruposAsignados">
			   <bean:write name="grupo" property="id"/> 
		  </html:multibox> 			
		</c:when>
		<c:otherwise>
			<html:multibox property="gruposAsignados" styleId="gruposAsignados">
			   <bean:write name="grupo" property="id"/> 
		  </html:multibox> 
		</c:otherwise>
	</c:choose>
    <bean:write name="grupo" property="nombre"/> 
      </td>
    </tr>
</logic:iterate>
</table>
</div>


<html:hidden property="submitted" value="true"/>
<html:hidden property="id" />
<html:submit value="Aceptar" styleClass="boton1"/>
<%-- <input type="button" class="boton3" value="checkPwd" onclick="validateForm();"/> --%>

<input type="button" value="Cancelar" class="boton3" onclick="cancel();" />

</html:form>

<html:form action="/user/bd/userDelete" method="post" onsubmit="return confirmDelete();" >
	<html:hidden property="id" />
	<%-- Validaci�n ya hecha en el Action --%>
	<html:submit value="Eliminar" styleClass="boton2"/>
	<%--
	<c:choose>  OJO SI SI ENTRA AQUI POR GRUPOS PUEDES BORRAR A QUIEN QUIERAS SI ERES EL MANAGER DEL GRUPO 
		<c:when test="${sessionScope.user.id == mgrDeptId  || sessionScope.user.userProfileSuperuser}">
		Puede eliminar si es manager del dpto o si es IUSER_SUPERUSER 
			<html:submit value="Eliminar" styleClass="boton2"/>
		</c:when>
		<c:otherwise>
			<html:submit value="Eliminar" disabled="true" styleClass="boton2"/>
		</c:otherwise>
	</c:choose>
	--%>
		<html:hidden property="id"/>
</html:form>	

</body>
 -->



</html:html>