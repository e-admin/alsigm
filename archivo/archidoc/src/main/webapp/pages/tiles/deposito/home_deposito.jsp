<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.ubicacionesDisponibles"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<security:permissions action="${appConstants.depositoActions.ALTA_ELEMENTO_ACTION}">
			<TD noWrap>
				<c:url var="urlCreacionDeposito" value="/action/gestionDepositoAction">
					<c:param name="method" value="altaUbicacion" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${urlCreacionDeposito}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.crear"/>
				</a>
			</TD>
			<td width="10">&nbsp;</td>
			<c:if test="${appConstants.configConstants.mostrarAyuda}">

				<td>
					<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
					<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
					<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/deposito/depositoHome.html');">
					<html:img page="/pages/images/help.gif"
					        altKey="archigest.archivo.ayuda"
					        titleKey="archigest.archivo.ayuda"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
				</td>
			</c:if>
			<TD width="10">&nbsp;</TD>
			</security:permissions>
			<TD>
				<tiles:insert definition="button.closeButton" />
			</TD>
		</TR>
		</TABLE>
	</tiles:put>


	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.ubicaciones"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
					<c:url var="buscarUISAnioYSerieURL" value="/action/busquedaUISAnioYSerieAction">
						<c:param name="method" value="initBusqueda" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${buscarUISAnioYSerieURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/buscarCaja.gif" altKey="archigest.archivo.deposito.consultaUISAnioYSerie" titleKey="archigest.archivo.deposito.consultaUISAnioYSerie" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.deposito.consultaUISAnioYSerie" />
					</a>
					&nbsp;&nbsp;
					<c:url var="buscarUIEliminadaURL" value="/action/busquedaUISEliminadasAction">
						<c:param name="method" value="nuevaBusquedaUI" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${buscarUIEliminadaURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/buscarCaja.gif" altKey="archigest.archivo.deposito.busqueda.ui.eliminadas.caption" titleKey="archigest.archivo.deposito.busqueda.ui.eliminadas.caption" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.deposito.busqueda.ui.eliminadas.caption" />
					</a>
					&nbsp;&nbsp;
					<c:url var="buscarUIURL" value="/action/gestionDepositoAction">
						<c:param name="method" value="nuevaBusquedaUI" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${buscarUIURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/buscarCaja.gif" altKey="archigest.archivo.deposito.buscarUI" titleKey="archigest.archivo.deposito.buscarUI" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.deposito.buscarUI" />
					</a>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>

				<c:set var="listaUbicaciones" value="${requestScope[appConstants.deposito.LISTA_UBICACIONES_KEY]}" />
				<display:table name="pageScope.listaUbicaciones"
					id="ubicacion"
					style="width:99%;margin-left:auto;margin-right:auto">

					<display:setProperty name="basic.msg.empty_list">
						<c:choose>
							<c:when test="${appConstants.configConstants.mostrarTodasUbicaciones}">
								<bean:message key="archigest.archivo.deposito.noUbicacion"/>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.deposito.noUbicacion.accesible"/>
							</c:otherwise>
						</c:choose>
					</display:setProperty>

					<display:column titleKey="archigest.archivo.ubicacion">
						<c:url var="verUbicacionURL" value="/action/manageVistaDeposito">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${ubicacion.itemPath}" />
							<c:param name="refreshView" value="true" />
						</c:url>
						<a class="tdlink" href='<c:out value="${verUbicacionURL}" escapeXml="false"/>' >
							<c:out value="${ubicacion.nombre}"/>
						</a>
					</display:column>

				</display:table>

				<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>

		<div class="separador8"></div>

		<tiles:insert definition="deposito.datosOcupacion">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.resumenOcupacion"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<TD>
					<c:url var="verOcupacionURL" value="/action/manageVistaDeposito">
						<c:param name="actionToPerform" value="resumenDeposito" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${verOcupacionURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/ocupacion.gif" altKey="archigest.archivo.deposito.informeOcupacion" titleKey="archigest.archivo.deposito.informeOcupacion" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.deposito.informeOcupacion" />
					</a>
					</TD>
				</TR>
				</TABLE>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>