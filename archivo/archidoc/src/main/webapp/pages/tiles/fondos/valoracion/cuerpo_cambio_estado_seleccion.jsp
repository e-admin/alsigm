<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security" %>

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<c:set var="eliminacion" value="${sessionScope[appConstants.valoracion.ELIMINACION_KEY]}" />
<c:set var="serie" value="${sessionScope[appConstants.valoracion.SERIE_KEY]}" />

<bean:struts id="mappingGestionEliminacion" mapping="/gestionEliminacion" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.eliminacion.editarSeleccion"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionEliminacion.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>

				</td>
				<TD width="10">&nbsp;</TD>
				<td nowrap>
					<c:url var="cerrarURL" value="/action/gestionEliminacion">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cerrarURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	
	<tiles:put name="boxContent" direct="true">

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.eliminacion.serie.caption"/>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td  width="180px" class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:url var="vistaSerieURL" value="/action/gestionSeries">
								<%-- Se sustituye verEnFondos por verDesdeFondos --%>
								<c:param name="method" value="verDesdeFondos" />
								<c:param name="id" value="${serie.id}" />
							</c:url>
							<a class="tdlink" href="<c:out value="${vistaSerieURL}" escapeXml="false"/>"><c:out value="${serie.codigo}" /></a>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${serie.denominacion}" />
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

	<html:form action="/gestionEliminacion">
	<input type="hidden" name="id" value="<c:out value="${eliminacion.id}" />">
	<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.informacion"/>
		</tiles:put>

		<tiles:put name="blockContent" direct="true" >
			<tiles:insert definition="valoracion.datosEliminacion" />

			<table class="formulario">
			<c:choose>
				<c:when test="${param.method == 'recogerFechaDestruccion' || param.method == 'destruirFisicamente'}">
					<input type="hidden" name="method" value="destruirFisicamente">
					<TR>
						<TD class="tdTitulo" width="180px">
							<bean:message key="archigest.archivo.eliminacion.fechaDestruccion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:text  size="14" maxlength="10" property="fechaDestruccion" />
								<archigest:calendar 
									image="../images/calendar.gif"
									formId="eliminacionForm" 
									componentId="fechaDestruccion" 
									format="dd/mm/yyyy" 
									enablePast="false" />
						</TD>
					</TR>
				</c:when>
				<c:when test="${param.method == 'solicitarAprobacion' || param.method == 'pantallaSolicitudAprobacion'}">
					<input type="hidden" name="method" value="solicitarAprobacion">
					<c:choose>
					<c:when test="${empty eliminacion.fechaEjecucion}">
					<TR>
						<TD class="tdTitulo" width="180px">
							<bean:message key="archigest.archivo.eliminacion.fechaEjecucion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:text size="14" property="fechaEjecucion" maxlength="10"/>
								<archigest:calendar 
									image="../images/calendar.gif"
									formId="eliminacionForm" 
									componentId="fechaEjecucion" 
									format="dd/mm/yyyy" 
									enablePast="false" />
						</TD>
					</TR>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="fechaEjecucion" value="<fmt:formatDate value="${eliminacion.fechaEjecucion}" pattern="${appConstants.common.FORMATO_FECHA}" />">
					</c:otherwise>
					</c:choose>
					<security:permissions action="${appConstants.fondosActions.APROBAR_ELIMINACION_ACTION}">
					<TR>
						<TD class="tdTitulo" colspan="2">
							<bean:message key="archigest.archivo.cf.autorizarAutomaticamente"/>:&nbsp;
							<input type="checkbox" name="autorizarAutomaticamente" value="S" class="radio">
						</TD>
					</TR>
					</security:permissions>
				</c:when>
				<c:when test="${param.method == 'pantallaRechazoSeleccion' || param.method == 'rechazarEliminacion'}">
					<input type="hidden" name="method" value="rechazarEliminacion">
					<TR>
						<TD class="tdTitulo" width="180px">
							<bean:message key="archigest.archivo.valoracion.motivoRechazo"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:textarea property="motivoRechazo" styleClass="textarea99" />
						</TD>
					</TR>
				</c:when>

			</c:choose>
			</table>
		</tiles:put>
	</tiles:insert>

	</html:form>
	</tiles:put>
</tiles:insert>