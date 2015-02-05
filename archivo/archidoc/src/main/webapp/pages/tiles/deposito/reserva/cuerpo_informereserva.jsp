<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.deposito.RELACION_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.reservas" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<TD noWrap>
					<c:url var="urlAtras" value="/action/gestionReservaRelaciones">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class=etiquetaAzul12Bold href="<c:out value="${urlAtras}" escapeXml="false"/>">
						<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.atras"/>
			   		</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<c:url var="urlAceptar" value="/action/gestionReservaRelaciones">
						<c:param name="method" value="aceptarinformereserva"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${urlAceptar}" escapeXml="false"/>" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<tiles:insert definition="button.closeButton">
						<tiles:put name="action" value="goReturnPoint" />
						<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
						<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
					</tiles:insert>

				</TD>
			</TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<TABLE class="w98m1" cellpadding=0 cellspacing=2>
					<TR>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
							<span class="etiquetaNegra11Normal">
								<c:out value="${vRelacion.codigoTransferencia}"/>
							</span>
						</TD>
						<TD width="25%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
							<span class="etiquetaNegra11Normal">
								<c:out value="${vRelacion.nombreestado}"/>
							</span>
						</TD>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
							<span class="etiquetaNegra11Normal">
								<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
							</span>
						</TD>
						<TD width="35%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
							<c:set var="gestorEnOrganoRemitente" value="${vRelacion.gestorEnOrganoRemitente}" />
							<span class="etiquetaNegra11Normal">
								<c:out value="${gestorEnOrganoRemitente.nombreCompleto}"/>
							</span>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.espacioReservar"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
				<TR>
					<TD class="tdTitulo" width="300px">
						Número de huecos:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vRelacion.numUIs}"/>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vRelacion.formatoDestino.nombre}"/>
					</TD>
				</TR>
			</TABLE>
		</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.msgReservarHuecos" /></tiles:put>

		<c:set var="ubicacion" value="${sessionScope[appConstants.deposito.DEPOSITO_KEY]}"/>

		<tiles:put name="blockContent" direct="true">
			<div class="separador5">&nbsp;</div>

			<c:set var="LISTA_NAME" value="sessionScope.${appConstants.deposito.LISTA_HUECOS_KEY}"/>
			<jsp:useBean id="LISTA_NAME" type="java.lang.String" />

			<display:table name="<%=LISTA_NAME%>"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="listaRegistros"
				decorator="transferencias.decorators.ViewPrevisionDecorator"
				sort="page"
				defaultsort="3"
				export="false">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.deposito.noHuecosReservar" />
				</display:setProperty>

				<display:column title="&nbsp;"><%=listaRegistros_rowNum%></display:column>
				<display:column titleKey="archigest.archivo.transferencias.ubicacionReservada" property="path" />
				<c:if test="${ubicacion.tipoSignaturacion == appConstants.transferencias.tiposSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO.identificador}">
					<display:column titleKey="archigest.archivo.deposito.sigCaja" property="numeracion" style="width:15%" />
				</c:if>
			</display:table>
			<div class="separador5">&nbsp;</div>

		</tiles:put>
		</tiles:insert>

	</tiles:put>
</tiles:insert>