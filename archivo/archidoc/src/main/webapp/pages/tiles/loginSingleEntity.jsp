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


/*if(window.opener && !window.opener.closed){
	window.opener.focus();
	window.opener.location = window.location;
}
else
*/
if(window.top &&  window.top.basefrm){
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
	<table border="0" cellpadding="0" cellspacing="0" width="100%" >
		<tr>
			<td width="100%">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" >
					<TR>
						<TD><html:img page="/pages/images/pixel.gif" width="1" height="30"/></TD>
					</TR>
					<TR>
						<TD align="center">
							<div style="text-align:left; width:70%;">
						  		<html:img page="/pages/images/pixel.gif" width="1" />
								<archivo:errors/>
						    </div>

							<logic:present scope="session" name="LOGIN EXCEPTION">
							<bean:define id="exception" scope="session" name="LOGIN EXCEPTION"/>
							<div style="text-align:left; width:70%;">
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
						</TD>
					</TR>
					<TR>
						<TD><html:img page="/pages/images/pixel.gif" width="1" height="30"/></TD>
					</TR>
					<tr>
					  <td width="100%">
					    <table style = "background: url(../images/logo/logo_archivo.jpg) no-repeat" cellpadding="7" cellspacing="2" border="0"  align="center" valign="top">

					      <tr>
	 				        <td width="55%">
	 				        </td>
					        <td>
							  <form name="formLogin" action="<archigest:getLoginActionURL action="/action/loginAction" />" method="post">
						  	  <input type="hidden" name="method" value="doLogin">
				              <table border="0" class="w98" cellpadding="0" cellspacing="0">
								<tr><td><html:img page="/pages/images/pixel.gif" style="height:20px" styleClass="imgTextMiddle"/></td></tr>
				                <tr>
				                  <td class="etiquetaLoginBig" style="text-align:center">
				                  	<bean:message key="archigest.archivo.login.datosAcceso"/>
				                  </td>
				                </tr>
								<tr>
									<td><hr class="hrLogin"/></td>
								</tr>
								<tr>
							        <td><html:img page="/pages/images/pixel.gif" style="height:10px" styleClass="imgTextMiddle"/></td>
								</tr>
				              </table>
				              <table border="0" cellpadding="0" cellspacing="8">
				                <c:choose>
				                  <c:when test="${!empty actor and actor.value=='citizen'}">
					                <tr>
					                  <td class="etiquetaLoginSmall">
					                  	<bean:message key="archigest.archivo.login.nif_nie"/>:
					                  </td>
					                  <td width="10px">&nbsp;</td>
					                  <td>
										<input type="text" size="12" name="login" class="input"
					                  		value="<c:out value="${param['login']}"/>"/>
					                  	<input type="hidden" name="userType" value="3" />
					                  </td>
					                </tr>
				                  </c:when>
				                  <c:otherwise>
					                <tr>
					                  <td class="etiquetaLoginSmall">
					                  	<bean:message key="archigest.archivo.login.tipoUsuario"/>:
					                  </td>
					                  <td width="10px">&nbsp;</td>
					                  <td style="text-align:right;"><select name="userType" class="input">
					                  		<c:forEach items="${tiposUsuarios}" var="tipoUsuario">
					                  			<option value="<c:out value="${tipoUsuario.tipo}"/>"
					                  			<c:if test="${param.userType==tipoUsuario.tipo}">selected="true"</c:if>
					                  			><bean-el:message key="archigest.archivo.cacceso.tipoUsuario.${tipoUsuario.tipo}"/></option>
					                  		</c:forEach>
					                      </select>
					                      </td>
					                </tr>
					                <tr>
					                  <td class="etiquetaLoginSmall">
					                  	<bean:message key="archigest.archivo.login.usuario"/>:
					                  </td>
					                  <td width="10px">&nbsp;</td>
										<td><input type="text" size="12" name="login" class="input"
					                  		value="<c:out value="${param['login']}"/>" /></td>
					                </tr>
				                  </c:otherwise>
				                </c:choose>
				                <tr>
				                  <td class="etiquetaLoginSmall">
				                  	<bean:message key="archigest.archivo.login.password"/>:
				                  </td>
				                  <td width="10px">&nbsp;</td>
									<td><input type="password" size="12" name="password" class="input"
				                  		value="<c:out value="${param['password']}"/>"/></td>
				                </tr>
				                <%-- Superusuario, visible o no vía configuración --%>
				                <c:if test="${appConstants.configConstants.mostrarCheckSuperusuario}">
					                <tr>
						                <td class="etiquetaLoginSmall">
						                  	<bean:message key="archigest.archivo.login.superusuario"/>:
						                </td>
					                    <td width="10px">&nbsp;</td>
						                <td>
						                   	<input type="checkbox" class="checkbox" name="superuser" onclick="javascript:toggleUserType();" <c:if test="${param['superuser']=='on'}">CHECKED</c:if> />
						                </td>
					                </tr>
					            </c:if>
				                <c:if test="${requestScope[appConstants.controlAcceso.EXPULSAR_KEY]}">
				                <tr>
					                <td class="etiquetaLoginSmall">
					                  	<bean:message key="archigest.archivo.login.expulsar"/>:
					                </td>
				                    <td width="10px">&nbsp;</td>
					                <td>
					                  	<input type="checkbox" class="checkbox" name="expulsar" value="true" <c:if test="${param['expulsar']=='on'}">CHECKED</c:if>/>
					                </td>
				                </tr>
				                </c:if>
				                  <td colspan="2" align="center">
				                  </td>
				                <tr>
				                  <td colspan="3" align="right">
										<input type="submit" class="btLisoLogin" value="<bean:message key='archigest.archivo.validar'/>"
										  onmouseover="this.className='btLisoLogin'"
										  onmouseout="this.className='btLisoLogin'"
										  />

				                  </td>
				                </tr>
								<tr>
									<td><html:img page="/pages/images/pixel.gif" width="1" height="30"/></td>
								</tr>
				              </table>
					          </form>

				              <script type="text/javascript">
				              	document.forms["formLogin"].login.focus();
				              </script>
					        </td>
							<TD><html:img page="/pages/images/pixel.gif" width="40" height="1"/></TD>
					      </tr>
					    </table>
					  </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

  </tiles:put>
</tiles:insert>

<%
	// Eliminar la excepción de sesión
	session.removeAttribute(SecurityGlobals.LOGIN_EXCEPTION);
%>