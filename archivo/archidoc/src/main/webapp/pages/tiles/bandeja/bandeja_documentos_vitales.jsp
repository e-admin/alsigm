<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBandeja.documentosVitalesAGestionar > 0}">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">documentosVitales</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.documentosVitales"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
		
			<table class="form_bandeja" align="center" cellspacing="10px" cellpadding="4px"><tr>
				<c:if test="${beanBandeja.documentosVitalesAGestionar > 0}">
				<td class="td_bandeja">
				<TABLE class="form_centrado"> 
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.documentosVitales"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>				
					<c:if test="${beanBandeja.documentosVitalesAGestionar > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="documentosVitalesAGestionarURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="gestionDocumentosVitales" />
								<c:param name="menuName" value="MenuGestionDocumentosVitales" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${documentosVitalesAGestionarURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.documentosVitalesAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionar"/>
							</a>
						</TD>
					</TR>
					</c:if>
				</TABLE>
				</td>
				</c:if>
			</tr></table>
		</tiles:put>
		</tiles:insert>
		<div class="separador5">&nbsp;</div>
	</c:if>