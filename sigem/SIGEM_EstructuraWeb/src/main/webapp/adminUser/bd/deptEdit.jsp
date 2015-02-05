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

<c:if test="${sessionScope.managerId eq sessionScope.user.id}">
	<!-- Es manager Del Dtpo -->
</c:if>


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
	<%-- Te permite modificar la pestaña general de un dpto, si eres IUSER_SUPERUSER O IUSER_MANAGER del dpto --%>
	<c:if test="${sessionScope.user.id != usersListForm.managerId && !sessionScope.user.userProfileSuperuser }">
		disableDeptGeneral();
	</c:if>
	}
	
	function validar(form){
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
	
	function cancel(){
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {	
			document.location.href="<html:rewrite forward='cancel'/>";
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}
	}	

	function confirmDelete()
	{
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {	
			return confirm('<bean:message key="message.confirm.delete.dept"/>');
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}		
	}
	
	function refresca(){
		var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
		parent.deptos.location.href = appBase + '/user/bd/deptTree.do';
	}
	
	function aceptar(){
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) {
			if(validar(document.forms[0])){	
				document.forms[0].submit();
			}
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA) %>');	
		}			
	}
	
</script> 
</head>

<body onload="choosebox(1,9);">

    <div id="contenedora">
    
		<div class="cuerpo" style="width:530px; height:170px;">
		
			<html:form action="/user/bd/deptEdit" method="post" >
		
	   			<div class="cuerporight">
	   		
	    			<div class="cuerpomid">
	    			
						<h1><bean:message key="message.departamento.editar.titulo"/></h1>
						
						<div class="submenu3">
							<ul>
	        					<li class="submen1on" id="tab1" onclick="choosebox(1,9); activaPestanhaGeneral();"><img id="img1" src="include/images/subme3_on.gif" />
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
											<html:optionsCollection name="adminUsersDTO" property="list" value="value" label="label" style="width:350px;"/> 
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
				<html:hidden property="id" />
				<!--<html:submit value="Aceptar" styleClass="okEO"/>-->
				<input type="button" class="okEO" value ='<bean:message key="message.comun.boton.aceptar"/>' onclick="aceptar();" />
				<input type="button" value='<bean:message key="message.comun.boton.cancelar"/>' class="ok" onclick="cancel();" />
			
			</html:form>
			
			<html:form action="/user/bd/deptDelete" onsubmit="return confirmDelete();" method="post" >

				<input type="submit" value="<bean:message key='message.comun.boton.elimimar'/>" class="ok" style="position:absolute; left:185px; top:215px; height:20px; width:87px;" />
				<html:hidden property="id" />
				
			</html:form>
			
	   	</div> 	
		
		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
				
	</div>

</body>

</html:html>