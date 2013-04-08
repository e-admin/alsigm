<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBandeja.prestamosEnElaboracion > 0 || beanBandeja.prestamosAGestionar > 0 || beanBandeja.prestamosAEntregar > 0
					|| beanBandeja.consultasAGestionar > 0 || beanBandeja.consultasAGestionarReserva > 0
					|| beanBandeja.consultasEnElaboracion > 0 || beanBandeja.consultasAEntregar > 0 || beanBandeja.prestamosAGestionarReserva > 0
					|| beanBandeja.registrosAbiertos > 0}">
	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">servicios</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.servicios"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
		<table class="form_bandeja" align="center" cellspacing="10px" cellpadding="4px"><tr>
			<c:if test="${beanBandeja.prestamosEnElaboracion > 0 || beanBandeja.prestamosAGestionar > 0 || beanBandeja.prestamosAEntregar > 0 || beanBandeja.prestamosAGestionarReserva > 0}">
				<td class="td_bandeja">
				<TABLE class="form_centrado">
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.prestamos"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>
					<c:if test="${beanBandeja.prestamosEnElaboracion > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="prestamosEnElaboracionURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="listaPrestamosEnElaboracion" />
								<c:param name="menuName" value="MenuPrestamos" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${prestamosEnElaboracionURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.prestamosEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.prestamosAGestionar > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="prestamosAGestionarURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="gestionPrestamos" />
								<c:param name="menuName" value="MenuPrestamos" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${prestamosAGestionarURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.prestamosAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionar"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.prestamosAGestionarReserva > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="prestamosAGestionarReservaURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="gestionPrestamosReserva" />
								<c:param name="menuName" value="MenuPrestamos" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${prestamosAGestionarReservaURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.prestamosAGestionarReserva}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionarReserva"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.prestamosAEntregar > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="prestamosAEntregarURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="entregaPrestamos" />
								<c:param name="menuName" value="MenuPrestamos" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${prestamosAEntregarURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.prestamosAEntregar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aEntregar"/>
							</a>
						</TD>
					</TR>
					</c:if>
				</TABLE>
				</td>
			</c:if>
			<c:if test="${beanBandeja.consultasEnElaboracion > 0 || beanBandeja.consultasAGestionar > 0
						|| beanBandeja.consultasAEntregar > 0 || beanBandeja.consultasAGestionarReserva > 0}">
			<td class="td_bandeja">
			<TABLE class="form_centrado">
				<TR>
					<TD class="tdTitulo" colspan="2">
						<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
						<bean:message key="archigest.archivo.bandeja.consultas"/>
					</TD>
				</TR>
				<TR><TD width="60px"></TD><TD></TD></TR>
				<c:if test="${beanBandeja.consultasEnElaboracion > 0}">
				<TR>
					<TD></TD>
					<TD class="tdDatos">
						<c:url var="consultasEnElaboracionURL" value="/action/navigateAction">
							<c:param name="method" value="menuOption" />
							<c:param name="menuOption" value="listaConsultasEnElaboracion" />
							<c:param name="menuName" value="MenuConsultas" />
						</c:url>
						<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
						<a class="tdlink" href="<c:out value="${consultasEnElaboracionURL}" escapeXml="false"/>">
							<c:out value="${beanBandeja.consultasEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
						</a>
					</TD>
				</TR>
				</c:if>
				<c:if test="${beanBandeja.consultasAGestionar > 0}">
				<TR>
					<TD>
					</TD>
					<TD class="tdDatos">
						<c:url var="consultasAGestionarURL" value="/action/navigateAction">
							<c:param name="method" value="menuOption" />
							<c:param name="menuOption" value="gestionConsultas" />
							<c:param name="menuName" value="MenuConsultas" />
						</c:url>
						<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
						<a class="tdlink" href="<c:out value="${consultasAGestionarURL}" escapeXml="false"/>">
							<c:out value="${beanBandeja.consultasAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionar"/>
						</a>
					</TD>
				</TR>
				</c:if>
				<c:if test="${beanBandeja.consultasAGestionarReserva > 0}">
				<TR>
					<TD>
					</TD>
					<TD class="tdDatos">
						<c:url var="consultasAGestionarReservaURL" value="/action/navigateAction">
							<c:param name="method" value="menuOption" />
							<c:param name="menuOption" value="gestionConsultasReserva" />
							<c:param name="menuName" value="MenuConsultas" />
						</c:url>
						<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
						<a class="tdlink" href="<c:out value="${consultasAGestionarReservaURL}" escapeXml="false"/>">
							<c:out value="${beanBandeja.consultasAGestionarReserva}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionarReserva"/>
						</a>
					</TD>
				</TR>
				</c:if>
				<c:if test="${beanBandeja.consultasAEntregar > 0}">
				<TR>
					<TD>
					</TD>
					<TD class="tdDatos">
						<c:url var="consultasAEntregarURL" value="/action/navigateAction">
							<c:param name="method" value="menuOption" />
							<c:param name="menuOption" value="entregaConsultas" />
							<c:param name="menuName" value="MenuConsultas" />
						</c:url>
						<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
						<a class="tdlink" href="<c:out value="${consultasAEntregarURL}" escapeXml="false"/>">
							<c:out value="${beanBandeja.consultasAEntregar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aEntregar"/>
						</a>
					</TD>
				</TR>
				</c:if>
			</TABLE>
			</td>
			</c:if>
			<c:if test="${beanBandeja.registrosAbiertos > 0}">
			<td class="td_bandeja">
				<TABLE class="form_centrado">
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.salas"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>
					<c:if test="${beanBandeja.registrosAbiertos > 0}">
					<TR>
						<TD>
						</TD>
						<TD class="tdDatos">
							<c:url var="registrosAbiertosURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="gestionRegistroConsultas" />
								<c:param name="menuName" value="MenuSalas" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${registrosAbiertosURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.registrosAbiertos}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.registros.abiertos"/>
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