<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBandeja.seriesEnIdentificacion > 0 || beanBandeja.solicitudesPendientes > 0 || beanBandeja.solicitudesAGestionar > 0 || beanBandeja.valoracionesEnElaboracion > 0 || beanBandeja.valoracionesAGestionar > 0 || beanBandeja.seleccionesEnElaboracion > 0}">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">series</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.series"/></tiles:put>
		<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
		<tiles:put name="dockableContent" direct="true">
		
			<table class="form_bandeja" align="center" cellspacing="10px" cellpadding="4px"><tr>
				<c:if test="${beanBandeja.seriesEnIdentificacion > 0 || beanBandeja.solicitudesPendientes > 0 || beanBandeja.solicitudesAGestionar > 0}">
				<td class="td_bandeja">
				<TABLE class="form_centrado"> 
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.identificacion"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>				
					<c:if test="${beanBandeja.seriesEnIdentificacion > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="seriesEnIdentificacionURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="identificacionSeries" />
								<c:param name="menuName" value="MenuIdentificar" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${seriesEnIdentificacionURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.seriesEnIdentificacion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.seriesIdentificacion"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.solicitudesPendientes > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="solicitudesPendientesURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="solicitudesAprobacion" />
								<c:param name="menuName" value="MenuIdentificar" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${solicitudesPendientesURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.solicitudesPendientes}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.solicitudesRealizadas"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${ beanBandeja.solicitudesAGestionar > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="solicitudesAGestionarURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="gestionSolicitudesAprobacion" />
								<c:param name="menuName" value="MenuIdentificar" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${solicitudesAGestionarURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.solicitudesAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.solicitudesAutorizar"/>
							</a>
						</TD>
					</TR>
					</c:if>
				</TABLE>
				</td>
				</c:if>
				<c:if test="${beanBandeja.valoracionesEnElaboracion > 0 || beanBandeja.valoracionesAGestionar > 0}">
				<td class="td_bandeja">
				<TABLE class="form_centrado"> 
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.valoracion"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>				
					<c:if test="${beanBandeja.valoracionesEnElaboracion > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="valoracionesEnElaboracionURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="valoracionesEnElaboracion" />
								<c:param name="menuName" value="MenuValorar" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${valoracionesEnElaboracionURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.valoracionesEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
							</a>
						</TD>
					</TR>
					</c:if>
					<c:if test="${beanBandeja.valoracionesAGestionar > 0}">
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="valoracionesAGestionarURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="valoracionesAGestionar" />
								<c:param name="menuName" value="MenuValorar" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${valoracionesAGestionarURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.valoracionesAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionar"/>
							</a>
						</TD>
					</TR>
					</c:if>
				</TABLE>
				</td>
				</c:if>
				<c:if test="${beanBandeja.seleccionesEnElaboracion > 0}">
				<td class="td_bandeja">
				<TABLE class="form_centrado">
					<TR>
						<TD class="tdTitulo" colspan="2">
							<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.bandeja.seleccion"/>
						</TD>
					</TR>
					<TR><TD width="60px"></TD><TD></TD></TR>				
					<TR>
						<TD></TD>
						<TD class="tdDatos">
							<c:url var="seleccionesEnElaboracionURL" value="/action/navigateAction">
								<c:param name="method" value="menuOption" />
								<c:param name="menuOption" value="seleccionesEnElaboracion" />
								<c:param name="menuName" value="MenuSeleccionar" />
							</c:url>
							<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
							<a class="tdlink" href="<c:out value="${seleccionesEnElaboracionURL}" escapeXml="false"/>">
								<c:out value="${beanBandeja.seleccionesEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
							</a>
						</TD>
					</TR>
				</TABLE>
				</td>
				</c:if>
			</tr></table>
		</tiles:put>
		</tiles:insert>
		<div class="separador5">&nbsp;</div>
</c:if>