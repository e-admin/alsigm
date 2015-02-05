<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="beanBandeja" value="${requestScope[appConstants.common.BANDEJA_KEY]}"/>

<c:if test="${beanBandeja.previsionesEnElaboracion > 0 || beanBandeja.previsionesAGestionar > 0
				|| beanBandeja.relacionesEnElaboracion > 0 || beanBandeja.relacionesAGestionar > 0 
				|| beanBandeja.reservasPendientes > 0 || beanBandeja.relacionesAUbicar > 0
				|| beanBandeja.relacionesRechazadas > 0 || beanBandeja.previsionesAceptadasORechazadas > 0}">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">transferencias</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.bandeja.transferencias"/></tiles:put>
			<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<table class="form_bandeja" align="center" cellspacing="10px" cellpadding="4px"><tr>
					<c:if test="${beanBandeja.previsionesEnElaboracion > 0 || beanBandeja.previsionesAGestionar > 0 || beanBandeja.previsionesAceptadasORechazadas > 0}">
						<td class="td_bandeja">
						<TABLE class="form_centrado"> 
							<TR>
								<TD class="tdTitulo" colspan="2">
									<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
									<bean:message key="archigest.archivo.bandeja.previsiones"/>
								</TD>
							</TR>
							<TR><TD width="60px"></TD><TD></TD></TR>
							<c:if test="${beanBandeja.previsionesEnElaboracion > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="previsionesEnElaboracionURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="previsionesEnElaboracion" />
										<c:param name="menuName" value="MenuPrevisionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${previsionesEnElaboracionURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.previsionesEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
									</a>
								</TD>
							</TR>
							</c:if>
							<c:if test="${beanBandeja.previsionesAGestionar > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="previsionesAGestionarURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="gestionPrevisiones" />
										<c:param name="menuName" value="MenuPrevisionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${previsionesAGestionarURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.previsionesAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionar"/>
									</a>
								</TD>
							</TR>
							</c:if>
							<c:if test="${beanBandeja.previsionesAceptadasORechazadas > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="previsionesAceptadasORechazadasURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="previsionesAceptadasORechazadas" />
										<c:param name="menuName" value="MenuPrevisionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${previsionesAceptadasORechazadasURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.previsionesAceptadasORechazadas}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aceptadasORechazadas"/>
									</a>
								</TD>
							</TR>
							</c:if>							
						</TABLE>
						</td>
					</c:if>
					<c:if test="${beanBandeja.relacionesEnElaboracion > 0 || beanBandeja.relacionesAGestionar > 0 
									|| beanBandeja.reservasPendientes > 0 || beanBandeja.relacionesAUbicar > 0 
									|| beanBandeja.relacionesRechazadas > 0 || beanBandeja.relacionesFinalizadas > 0}">
						<td class="td_bandeja">
						<TABLE class="form_centrado"> 
							<TR>
								<TD class="tdTitulo" colspan="2">
									<html:img page="/pages/images/arrow_down.gif" styleClass="imgTextMiddle"/>
									<bean:message key="archigest.archivo.bandeja.relaciones"/>
								</TD>
							</TR>
							<TR><TD width="60px"></TD><TD></TD></TR>
							<c:if test="${beanBandeja.relacionesEnElaboracion > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="relacionesEnElaboracionURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="relacionesEnElaboracion" />
										<c:param name="menuName" value="MenuRelacionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${relacionesEnElaboracionURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.relacionesEnElaboracion}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.enElaboracion"/>
									</a>
								</TD>
							</TR>
							</c:if>
							<c:if test="${beanBandeja.relacionesAGestionar > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="relacionesAGestionarURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="gestionRelaciones" />
										<c:param name="menuName" value="MenuRelacionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${relacionesAGestionarURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.relacionesAGestionar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionar"/>
									</a>
								</TD>
							</TR>
							</c:if>
						
							<c:if test="${beanBandeja.reservasPendientes > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="reservasAGestionarURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="gestionReservas" />
										<c:param name="menuName" value="MenuRelacionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${reservasAGestionarURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.reservasPendientes}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aGestionarReserva"/>
									</a>
								</TD>
							</TR>
							</c:if>
							<c:if test="${beanBandeja.relacionesAUbicar > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="relacionesAUbicarURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="ubicacionRelaciones" />
										<c:param name="menuName" value="MenuRelacionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${relacionesAUbicarURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.relacionesAUbicar}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aUbicar"/>
									</a>
								</TD>
							</TR>
							</c:if>
							<c:if test="${beanBandeja.relacionesRechazadas > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="relacionesRechazadasURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="relacionesRechazadas" />
										<c:param name="menuName" value="MenuRelacionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${relacionesRechazadasURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.relacionesRechazadas}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.rechazadas"/>	
									</a>
								</TD>
							</TR>
							</c:if>
							<c:if test="${beanBandeja.relacionesFinalizadas > 0}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<c:url var="relacionesFinalizadasURL" value="/action/navigateAction">
										<c:param name="method" value="menuOption" />
										<c:param name="menuOption" value="relacionesFinalizadas" />
										<c:param name="menuName" value="MenuRelacionesEnGestion" />
									</c:url>
									<html:img page="/pages/images/arrow_right.gif" styleClass="imgTextMiddle"/>
									<a class="tdlink" href="<c:out value="${relacionesFinalizadasURL}" escapeXml="false"/>">
										<c:out value="${beanBandeja.relacionesFinalizadas}"/>&nbsp;<bean:message key="archigest.archivo.bandeja.finalizadas"/>
										
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