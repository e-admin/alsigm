<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="es.archigest.framework.web.filter.security.common.SecurityGlobals" %>
<%@ page import="gcontrol.ControlAccesoConstants"%>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice"%>

<%
	String urlLogout = (String)request.getAttribute(ControlAccesoConstants.MULTIENTITY_LOGOUT_URL);
%>

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
						<table cellpadding="7" cellspacing="2" border="0"  align="center" valign="top">
							<tr><td width="55%">
					        </td></tr>
							<c:if test="${requestScope[appConstants.controlAcceso.EXPULSAR_KEY]}">
								<html:form styleId="formLogin" action="/loginAction" method="post">

							  	  	<input type="hidden" name="method" value="doLogin">
					                <tr>
						                <td class="etiquetaLoginSmall">
						                  	<bean:message key="archigest.archivo.login.expulsar"/>:
						                </td>
					                    <td width="10px">&nbsp;</td>
						                <td align="center">
						                  	<input type="checkbox" class="checkbox" name="expulsar" value="true" <c:if test="${param['expulsar']=='on'}">CHECKED</c:if>/>
						                </td>
										<td align="right">
											<input type="submit" class="btLisoLogin" value="<bean:message key='archigest.archivo.validar'/>"
											  onmouseover="this.className='btLisoLogin'"
											  onmouseout="this.className='btLisoLogin'"
											  />
										</td>
					                </tr>
					            </html:form>
						    </c:if>
							<c:set var="urlKey" value="${appConstants.controlAcceso.MULTIENTITY_LOGOUT_URL}"/>
							<c:if test="${requestScope[urlKey]!=null}">
								<tr>
									<c:choose>
										<c:when test="${requestScope[appConstants.controlAcceso.EXPULSAR_KEY]}">
											<td colspan="4" align="right">
										</c:when>
										<c:otherwise>
											<td colspan="4" align="left">
										</c:otherwise>
									</c:choose>
												<form action="../../<html:rewrite href="<%=urlLogout%>"/>" method="POST">
													<input type="submit" class="btLisoLogin" value="<bean:message key="label.exit"/>"/>
												</form>
											</td>
								</tr>
							</c:if>
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