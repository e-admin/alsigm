<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBandeja.divisionesFSEnElaboracion > 0 || beanBandeja.divisionesFSFinalizadas > 0}">
	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">divisionesFS</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.divisionesfs"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
		<table class="form_bandeja" align="center" cellspacing="10px" cellpadding="4px"><tr>
				<td class="td_bandeja">
				<TABLE class="form_centrado"> 
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.divisionesfs"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>
					<c:if test="${beanBandeja.divisionesFSEnElaboracion > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="divisionesFSEnElaboracion" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="divisionesFSEnElaboracion" />
								<c:param name="menuName" value="MenuDivisionesFSEnElaboracion" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${divisionesFSEnElaboracion}" escapeXml="false"/>">
								<c:out value="${beanBandeja.divisionesFSEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.divisionesFSFinalizadas > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="divisionesFSFinalizadasURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="divisionesFSFinalizadas" />
								<c:param name="menuName" value="MenuDivisionesFSFinalizadas" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${divisionesFSFinalizadasURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.divisionesFSFinalizadas}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.finalizadas"/>
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