<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBandeja.ingresosEnElaboracion > 0 || beanBandeja.ingresosFinalizados > 0}">
	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">ingresos</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.ingresos.directos"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
		<table class="form_bandeja" align="center" cellspacing="10px" cellpadding="4px"><tr>
			<c:if test="${beanBandeja.ingresosEnElaboracion > 0 || beanBandeja.ingresosFinalizados > 0 }">
				<td class="td_bandeja">
				<TABLE class="form_centrado"> 
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.ingresos.directos"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>
					<c:if test="${beanBandeja.ingresosEnElaboracion > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="ingresosEnElaboracionURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="ingresosEnElaboracion" />
								<c:param name="menuName" value="MenuIngresosEnElaboracion" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${ingresosEnElaboracionURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.ingresosEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.ingresosFinalizados > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="ingresosFinalizadosURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="ingresosFinalizados" />
								<c:param name="menuName" value="MenuIngresosFinalizados" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${ingresosFinalizadosURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.ingresosFinalizados}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.finalizadas"/>
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