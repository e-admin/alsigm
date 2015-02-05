<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
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
<ieci:baseInvesDoc/>
<link rel="Stylesheet" rev="Stylesheet" href="include/css/tabs.css" />
<link rel="Stylesheet" rev="Stylesheet" href="include/css/estilos.css" />

<script src="include/js/docobj.js" type="text/javascript"></script>
<script src="include/js/tabs.js" type="text/javascript"></script>
<script src="include/js/edition.js" type="text/javascript"></script>
<script src="include/js/validations.js" type="text/javascript"></script>

<script language='javascript'>

	function activaPestanhaGeneral()
	{
		document.getElementById("img1").src='include/images/subme3_on.gif';
		document.getElementById("img2").src='include/images/subme3_on_of.gif';
		document.getElementById("img3").src='include/images/subme3_of_0.gif';
		document.getElementById("tab1").className='submen1on';
		document.getElementById("tab2").className='submen1off';
	}

	function restringePrivilegios()
	{
	<%-- Te permite modificar la pestaña general de un dpto, si eres IUSER_SUPERUSER  --%>
	<c:if test="${!sessionScope.user.idocProfileSuperuser }">
		disablePermission();
	</c:if>
	}
	
	function validateForm()
	{
		var input = eval(deptGeneral[0]);
		var msg="";
		
		input.value = stripBlanks(input.value);
		
		if (isBlank(input.value) )
			msg += " No se ha especificado nombre \n";
		if ( !isValidText(input.value) )
			msg+= "El nombre del departamento no puede contener comillas simples o dobles \n" ;
		
		var input = eval ( deptGeneral[2] );
		input.value = stripBlanks(input.value);
		if ( !isValidText(input.value) )
			msg+= "La descripción del departamento no puede contener comillas simples o dobles \n" ;
			
			
		if (msg.length > 0 ) {
			alert(msg);
			return false;
		}	
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
	
	function check(form){
		var correcto=true;
		var msg="<bean:message key='message.departamento.nuevo.error'/>";
		var nombre = form.nombre.value;
		
		if (isBlank(nombre) ){
			msg += "<bean:message key="message.departamento.nuevo.error.nombrenulo"/>";
			correcto=false;
		}		
		if ( !isValidText(nombre) ){
			msg+= "<bean:message key="message.departamento.nuevo.error.nombreerroneo"/>" ;
			correcto=false;
		}
		
		var desc = form.descripcion.value;
		if ( !isValidText(desc) ){
			msg+= "<bean:message key="message.departamento.nuevo.error.descripcionerronea"/>" ;
			correcto=false;
		}
		
		if (correcto == false ) {
			alert(msg);
		}

		return correcto;
	}
	
	function aceptar(){
		var chequeo = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( chequeo == "false" ) {
			if(check(document.forms[0])){
				document.forms[0].submit();
			}
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}			
	}
		
</script> 
</head>

<body onload="choosebox(1,9);">

<html:form action="/user/bd/deptNew" onsubmit="return check(this); " method="post" >

    <div id="contenedora">
    
		<div class="cuerpo" style="width:530px; height:170px;">
		
	   		<div class="cuerporight">
	   		
	    		<div class="cuerpomid">
	    		
					<h1><bean:message key="message.departamento.nuevo.titulo"/></h1>
					
					<div class="submenu3">
						<ul>
        					<li  class="submen1on" id="tab1" onclick="choosebox(1,9); activaPestanhaGeneral();"><img id="img1" src="include/images/subme3_on.gif" />
        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
        					</li>
        					<img src="include/images/subme3_on_0.gif">
						</ul>
					</div>
						
					<div class="cuadro" style="height: 170px;">
					
						<div id="box1">
						
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
								<label class="gr" style="width:190px;"><html:text property="nombre" style="width:350px;"/></label>
						    </div>
							<div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.administrador"/></label>
								<label class="gr" style="width:190px;">
									<html:select property="managerId" size="1" styleId="dept.manager" style="width:350px;">
										<html:optionsCollection name="adminUsersDTO" property="list" value="value" label="label"/> 
									</html:select> 
								</label>
						    </div>	
						    <div class="col" style="width: 490px;">
								<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
								<label class="gr" style="width:190px;"><html:textarea property="descripcion" cols="65" rows="6" styleId="dept.description"/></label>
						    </div>
						    
						</div>
	       	
	       			</div>   				
     				
     			</div>
      	
      		</div>
      		
			<html:hidden property="submitted" value="true"/>
			<html:hidden property="id"/>
			<!--<html:submit value="Aceptar" styleClass="okEO"/>-->
			<input type="button" class="okEO" value ="<bean:message key='message.comun.boton.aceptar'/>" on onclick="aceptar();" />
			
			<input type="button" class="ok" value ="<bean:message key='message.comun.boton.cancelar'/>" onclick="cancel();" />
			<input type="hidden" name="idPadre" value='<c:out value="${param.idPadre}"/>' />
			
	   	</div> 	
		
		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
		
	</div>

</html:form>

</body>

</html:html>