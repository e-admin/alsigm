<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="es.archigest.framework.web.filter.security.common.SecurityGlobals" %>
<%@ page import="gcontrol.ControlAccesoConstants"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	// ======== ERROR EN EL FRAMEWORK SI USUARIO NO TIENE ROL VÁLIDO =========
	session.removeAttribute(SecurityGlobals.SUBJECT);
	// ========================================================================

%>

<script>
function toggleUserType(selected)
{
	var userTypeSelect = document.getElementById("userType");
	var superuserCheck = document.getElementById("superuser");
	if (superuserCheck != null && superuserCheck != 'null')
	{
		if (superuserCheck.checked)
			userTypeSelect.disabled = true;
		else 
			userTypeSelect.disabled = false;
	}
}


if(window.opener && !window.opener.closed){
	window.opener.focus();
	window.opener.location = window.location;
}
else if(window.top &&  window.top.basefrm){
	window.top.location = window.location;
}


</script>

<c:set var="tiposUsuarios" value="${appConstants.configuracion.configuracionControlAcceso.usuarios}"/>
<c:set var="actor" value="${cookie['actor']}"/>

<tiles:insert template="/pages/tiles/SimpleLayout.jsp">
  <tiles:put name="head" value="head.jsp" />	
  <tiles:put name="cabecera" value="cabecera.jsp" />
  <tiles:put name="pie_pagina" value="pie_pagina.jsp" />

  <tiles:put name="cuerpo" direct="true">
  	<div id="contenido_centrado">
		<div>
			<archivo:errors/>
		</div>
	        
		<logic:present scope="session" name="LOGIN EXCEPTION">
			<bean:define id="exception" scope="session" name="LOGIN EXCEPTION"/>
			<div>
				<html:img page="/pages/images/pixel.gif" width="1" />
				<logic:equal scope="session" name="LOGIN EXCEPTION" property="class.name" value="es.archigest.framework.facilities.security.exceptions.InsufficientDataException">
					<li class="errores_li"><bean:message key="login.InsufficientDataException" arg0="<%=((es.archigest.framework.facilities.security.exceptions.InsufficientDataException)exception).getDataDescription()%>"/></li>
				</logic:equal>
				<logic:equal scope="session" name="LOGIN EXCEPTION" property="class.name" value="es.archigest.framework.facilities.security.exceptions.InsufficientPrivilegesException">
					<li class="errores_li"><bean:write name="exception" property="localizedMessage"/></li>
				</logic:equal>
				<logic:equal scope="session" name="LOGIN EXCEPTION" property="class.name" value="es.archigest.framework.facilities.security.exceptions.UnknownUserException">
					<li class="errores_li"><bean:message key="login.UnknownUserException" arg0="<%=((es.archigest.framework.facilities.security.exceptions.UnknownUserException)exception).getId()%>"/></li>
				</logic:equal>
				<logic:equal scope="session" name="LOGIN EXCEPTION" property="class.name" value="es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException">
					<li class="errores_li"><bean:message key="login.UnmanagedLoginException" arg0="<%=((es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException)exception).getLocalizedMessage()%>"/></li>
				</logic:equal>
				<logic:equal scope="session" name="LOGIN EXCEPTION" property="class.name" value="es.archigest.framework.facilities.security.exceptions.WrongPasswordException">
					<li class="errores_li"><bean:message key="login.WrongPasswordException" arg0="<%=((es.archigest.framework.facilities.security.exceptions.WrongPasswordException)exception).getUsername()%>"/></li>
				</logic:equal>
				<logic:equal scope="session" name="LOGIN EXCEPTION" property="class.name" value="javax.security.auth.login.LoginException">
					<li class="errores_li"><%=((javax.security.auth.login.LoginException)exception).getMessage()%></li>
				</logic:equal>
			</div>
		</logic:present>
		
		<div class="ficha_fixed">
			<div class="encabezado"><div class="left_encabezado"><h3>&nbsp;</h3></div></div> <!-- fin encabezado -->			
			<div class="cuerpo">
				<div class="contenido_cuerpo_login"> 
					<div class="seccion_login"> 
				    	<form name="formLogin" action="<archigest:getLoginActionURL action="/action/loginAction" />" method="post">
				    		<input type="hidden" name="method" value="doLogin">
				    		<p class="etiquetaLoginBig">
				            	<bean:message key="archigest.archivo.login.datosAcceso"/>
				            </p>				            
				            <hr class="hrLogin"/>
				            <c:choose>
				            	<c:when test="${!empty actor and actor.value=='citizen'}">
				            		<p class="fila_sub">
				            			<label class="login"><bean:message key="archigest.archivo.login.nif_nie"/>:</label>
				            			<input type="text" size="12" name="login" class="input"	value="<c:out value="${param['login']}"/>"/>
					                  	<input type="hidden" name="userType" value="3" />
				            		</p>
								</c:when>
				              	<c:otherwise>
				              		<p class="fila_sub">
				                		<label class="login"><bean:message key="archigest.archivo.login.tipoUsuario"/>:</label>
				                		<select name="userType" class="input">
					                  		<c:forEach items="${tiposUsuarios}" var="tipoUsuario">
					                  			<option value="<c:out value="${tipoUsuario.tipo}"/>"
					                  				<c:if test="${param.userType==tipoUsuario.tipo}">selected="true"</c:if>
					                  				><bean-el:message key="archigest.archivo.cacceso.tipoUsuario.${tipoUsuario.tipo}"/>
					                  			</option>
					                  		</c:forEach>
					                    </select>
				                	</p>
				                	<p class="fila_sub">
				                		<label class="login"><bean:message key="archigest.archivo.login.usuario"/>:</label>
				                		<input type="text" size="12" name="login" class="login" value="<c:out value="${param['login']}"/>" />
			                		</p>			                					            		
				                </c:otherwise>
				            </c:choose>
				            
		            		<p class="fila_sub">
		                		<label class="login"><bean:message key="archigest.archivo.login.password"/>:</label>
		              			<input type="password" size="12" name="password" class="login" value="<c:out value="${param['password']}"/>"/>
		            		</p>	
				            <%-- Superusuario, visible o no vía configuración --%>
		              		<c:if test="${appConstants.configConstants.mostrarCheckSuperusuario}">
		              			<p class="fila">
		                			<label class="superuser"><bean:message key="archigest.archivo.login.superusuario"/>:</label>
		                			<input type="checkbox" class="checkbox" name="superuser" onclick="javascript:toggleUserType();" <c:if test="${param['superuser']=='on'}">CHECKED</c:if> />
		                		</p>
		                    </c:if>
		                    <c:if test="${requestScope[appConstants.controlAcceso.EXPULSAR_KEY]}">
		                    	<p class="fila">
		                			<label class="superuser"><bean:message key="archigest.archivo.login.expulsar"/>:</label>
				          			<input type="checkbox" class="checkbox" name="expulsar" value="true" <c:if test="${param['expulsar']=='on'}">CHECKED</c:if>/>
				          		</p>
				          	</c:if>
				            <p class="fila_right">
								<input type="submit" class="botonFondo" value="<bean:message key='archigest.archivo.validar'/>"
									onmouseover="this.className='botonFondo'"
								  	onmouseout="this.className='botonFondo'"
								/>
				            </p>				              
				    	</form>
					</div> <!-- fin seccion -->
				</div> <!-- contenido_cuerpo -->
			</div> <!-- fin cuerpo -->
		</div> <!-- fin ficha_fixed -->
		<script type="text/javascript">
			document.forms["formLogin"].login.focus();
		</script>
	</div> <!-- fin contenido_centrado -->
  </tiles:put>
</tiles:insert>

<%
	// Eliminar la excepción de sesión
	session.removeAttribute(SecurityGlobals.LOGIN_EXCEPTION);
%>