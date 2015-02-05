<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMATO_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>

<c:set var="vRelacion" value="${sessionScope[appConstants.deposito.RELACION_KEY]}"/>


<div id="contenedor_ficha">

<bean:struts id="mappingGestionReserva" mapping="/gestionReservaRelaciones" />

<html:form action="/gestionReservaRelaciones" >
<input type="hidden" name="method" value="informereserva">
<html:hidden property="asignabledestino" styleId="asignabledestino"/>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.reservas"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD noWrap>
				<c:url var="urlRechazar" value="/action/gestionReservaRelaciones">
					<c:param name="method" value="rechazar"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${urlRechazar}" escapeXml="false"/>">
					<html:img page="/pages/images/rechazar.gif" altKey="archigest.archivo.rechazar" titleKey="archigest.archivo.rechazar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.rechazar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD noWrap>
				<script>
					var form = document.forms['<c:out value="${mappingGestionReserva.name}" />'];

					function recogerAsignableSeleccionado() {
						var valorSeleccionado = window.frames['navegador'].getValorSeleccionado();

						if(valorSeleccionado == null || valorSeleccionado == "" ){
							alert("<bean:message key='errors.deposito.reserva.EsNecesarioSeleccionarUnaUbicacionParaLaReserva'/>");
							return;
						}
						document.getElementById("asignabledestino").value = valorSeleccionado;

						form.submit();

					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:recogerAsignableSeleccionado()" >
					<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD noWrap>
				<c:url var="urlCancelar" value="/action/gestionUbicacionRelaciones">
					<c:param name="method" value="goReturnPoint"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${urlCancelar}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
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
					<TD class="tdTitulo" width="170px">
						<bean:message key="archigest.archivo.deposito.numHuecos"/>:&nbsp;
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
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.reservaActual"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${requestScope[appConstants.deposito.DEPOSITO_KEY].pathName}"/>
					</TD>
				</TR>

			</TABLE>
		</tiles:put>
		</tiles:insert>



		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.depositosUbicacion"/> <c:out value="${vRelacion.ubicacion.nombre}"/></tiles:put>

		<tiles:put name="blockContent" direct="true">



		<TABLE class="w100" cellpadding=0 cellspacing=0>
			<TR>
				<TD>
				<c:url var="urlNavegador" value="/action/navegadorReservaDepositoAction">
					<c:param name="method" value="initial"/>

					<c:if test="${!empty requestScope[appConstants.deposito.DEPOSITO_KEY] || requestScope[appConstants.deposito.DEPOSITO_KEY]!= null}" >
						<c:param name="seleccionadoinicial" value="${requestScope[appConstants.deposito.DEPOSITO_KEY].id}:${requestScope[appConstants.deposito.DEPOSITO_KEY].idTipoElemento}"/>
						<c:param name="seleccionado" value="${requestScope[appConstants.deposito.DEPOSITO_KEY].id}:${requestScope[appConstants.deposito.DEPOSITO_KEY].idTipoElemento}"/>
					</c:if>

					<c:param name="root" value="${requestScope[appConstants.deposito.EDIFICIO_KEY].id}:${requestScope[appConstants.deposito.EDIFICIO_KEY].idTipoElemento}"/>
					<c:param name="filterByIdformato" value="${vRelacion.formatoDestino.id}"/>
					<c:param name="idTipoLastLevel" value="${requestScope[appConstants.deposito.DEPOSITO_KEY].idTipoElemento}" />
					<c:param name="allowSelectAnyElement" value="true" />
					<c:param name="numHuecosNecesarios" value ="${vRelacion.numUIs}"/>
					<c:param name="recorrerDepositos" value="S" />
				</c:url>

						<script>
							function resizeNavegador() {
								var frameNavegador = document.getElementById("navegador");
								if (frameNavegador.contentDocument && frameNavegador.contentDocument.body.offsetHeight) //ns6 syntax
									frameNavegador.height = frameNavegador.contentDocument.body.offsetHeight;
								else if (frameNavegador.Document && frameNavegador.Document.body.scrollHeight) //ie5+ syntax
									frameNavegador.height = frameNavegador.Document.body.scrollHeight;
							}
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}
						</script>

				<iframe id="navegador" name="navegador" src='<c:out value="${urlNavegador}" escapeXml="false"/>' frameborder="0" height="100%" width="100%">
				</iframe>

					<script>
						var frameNavegador = document.getElementById("navegador");
						if (frameNavegador.addEventListener)
							frameNavegador.addEventListener("load", resizeNavegador, false);
						else if (frameNavegador.attachEvent) {
							frameNavegador.detachEvent("onload", resizeNavegador); // Bug fix line
							frameNavegador.attachEvent("onload", resizeNavegador);
						}
					</script>

				</TD>
			</TR>
		</TABLE>


		</tiles:put>
		</tiles:insert>

	</tiles:put>
</tiles:insert>

</html:form>