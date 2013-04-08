<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBandeja.tareasAGestionar > 0 || beanBandeja.tareasPendientes > 0}">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">tareasDigitalizacion</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.tareasDigitalizacion"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
		
			<table class="form_bandeja" align="center" cellspacing="10px" cellpadding="4px"><tr>
				<td class="td_bandeja">
				<TABLE class="form_centrado"> 
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.tareasDigitalizacion"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>				
					<c:if test="${beanBandeja.tareasPendientes > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="tareasPendientesURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="tareasPendientes" />
								<c:param name="menuName" value="MenuGestionTareas" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${tareasPendientesURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.tareasPendientes}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.pendientes"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.tareasAGestionar > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="tareasAGestionarURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="tareasAGestionar" />
								<c:param name="menuName" value="MenuGestionTareas" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${tareasAGestionarURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.tareasAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionar"/>
							</a>
						</TD>
					</TR>
					</c:if>
				</TABLE>
				</td>

			</tr></table>
		</tiles:put>
		</tiles:insert>
		<div class="separador5">&nbsp;</div>
	</c:if>