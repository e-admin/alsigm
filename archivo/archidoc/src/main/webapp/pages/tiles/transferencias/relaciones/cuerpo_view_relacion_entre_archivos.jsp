<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<bean:struts id="actionMapping" mapping="/gestionRelaciones" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="infoAsignacion" value="${sessionScope[appConstants.transferencias.ASIGNACION_UDOC2UI]}" />
<c:set var="udocsSinDocsFisicos" value="${infoAsignacion.udocsSinDocumentosFisicos}" />
<c:set var="cajasRelacion" value="${infoAsignacion.unidadesInstalacion}" />

<c:choose>
<c:when test="${vRelacion.conReencajadoValidada}">
	<c:set var="udocsRelacion" value="${sessionScope[appConstants.transferencias.LISTA_UDOCS_REENCAJADO_KEY]}" />
</c:when>
<c:otherwise>
	<c:set var="udocsRelacion" value="${infoAsignacion.udocsRelacion}" />
</c:otherwise>
</c:choose>






<script>
	<c:set var="codigoRelacion"><c:out value="${vRelacion.codigoTransferencia}"/></c:set>

	function enviarRelacion() {
		var codigoRelacion = '<c:out value="${codigoRelacion}" />';
		if (confirm("<bean:message key='archigest.archivo.transferencias.relaciones.enviada1'/>"+codigoRelacion+"<bean:message key='archigest.archivo.transferencias.previsiones.enviada2'/>")) {
			<c:url var="envioRelacionURL" value="/action/recepcionRelaciones">
				<c:param name="method" value ="enviarrelacion" />
				<c:param name="codigo" value ="${vRelacion.id}" />
			</c:url>
			window.location = '<c:out value="${envioRelacionURL}" escapeXml="false"/>';
		}
	}

	function eliminarUnidadesInstalacion() {
		var selectionForm = document.getElementById("formulario");
		if (selectionForm && selectionForm.elementosseleccionados
				&& FormsToolKit.getNumSelectedChecked(selectionForm,"elementosseleccionados") > 0) {

			if (confirm("<bean:message key='archigest.archivo.transferencias.msgWarningDeleteUnidades'/>")){
			document.getElementById("method").value="eliminarUInstReea";
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}
				selectionForm.submit();
			}
		}else{
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
		}
	}

	function eliminarUDocsElectronicas() {
		if (confirm("<bean:message key='archigest.archivo.transferencias.msgWarningDeleteUnidades'/>"))
		{
			document.getElementById("method").value="eliminarUDocsElectronicasReea";
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}
			document.getElementById("formulario").submit();
		}
	}


	function grabar(){
		document.getElementById("method").value="grabarRelacionEntreArchivos";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		document.getElementById("formulario").submit();
	}

	function marcarRevisada(){
		document.getElementById("method").value="marcarCajaRevisadaEntreArchivos";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		document.getElementById("formulario").submit();
	}

	function showDetails (cajasRelacion)
	{
		var elementos = document.all;
		for(i=0; i< elementos.length;i++)
		{

			if(elementos[i] != null){
				var id = String(elementos[i].id);

				if(id.indexOf("divCaja") != -1){
					var layerId = id.substring(3);
					switchDivVisibility(layerId);
				}
			}
		}
	}

	function hideDetails (cajasRelacion)
	{
		var elementos = document.all;
		for(i=0; i< elementos.length;i++)
		{

			if(elementos[i] != null){
				var id = String(elementos[i].id);

				if(id.indexOf("divCaja") != -1){
					var layerId = id.substring(3);
					switchDivVisibility(layerId);
				}
			}
		}
	}

	function addUinst(url){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		window.location = url;
	}

	function addUdocElectronica(url){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		window.location = url;
	}

 	<c:if test="${requestScope[appConstants.transferencias.CON_REENCAJADO_KEY]}">
	<c:if test="${vRelacion.permitidoAccionesReencajado}">
		<security:permissions action="${appConstants.transferenciaActions.CANCELAR_REENCAJADO}">

	function cancelarReencajado(url){
		if (confirm("<bean:message key='archigest.archivo.transferencias.msgWarningCancelarReencajado'/>"))
		{
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}
			window.location = url;
		}
	}

		</security:permissions>
	</c:if>
</c:if>

</script>
<html:form action="/gestionRelaciones" styleId="formulario">
<input type="hidden" name="method" id="method">
<html:hidden property="idrelacionseleccionada" styleId="idrelacionseleccionada" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.datosRelacion"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
		 	<%--ACTIVAR REENCAJADO --%>
		 	<c:if test="${requestScope[appConstants.transferencias.CON_REENCAJADO_KEY]}">
				<c:if test="${vRelacion.permitidoActivarReencajado}">
					<security:permissions action="${appConstants.transferenciaActions.ACTIVAR_REENCAJADO}">
					<TD>
						<c:url var="urlActivarReencajado" value="/action/gestionRelaciones">
							<c:param name="method" value="activarReencajado"/>
							<c:param name="idrelacionseleccionada" value="${vRelacion.id}"/>
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${urlActivarReencajado}" escapeXml="false"/>" >
							<html:img titleKey="archigest.archivo.transferencias.activar.reencajado" altKey="archigest.archivo.transferencias.activar.reencajado" page="/pages/images/caja_quitar.gif" styleClass="imgTextMiddle" />
							 &nbsp;<bean:message key="archigest.archivo.transferencias.activar.reencajado"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
					</security:permissions>
				</c:if>
			</c:if>
			<%--CANCELAR REENCAJADO --%>
		 	<c:if test="${requestScope[appConstants.transferencias.CON_REENCAJADO_KEY]}">
				<c:if test="${vRelacion.permitidoAccionesReencajado}">
					<security:permissions action="${appConstants.transferenciaActions.CANCELAR_REENCAJADO}">
					<TD>
						<c:url var="urlCancelarReencajado" value="/action/gestionRelaciones">
							<c:param name="method" value="cancelarReencajado"/>
							<c:param name="idrelacionseleccionada" value="${vRelacion.id}"/>
						</c:url>
						<a class="etiquetaAzul12Bold" href="javascript:cancelarReencajado('<c:out value="${urlCancelarReencajado}" escapeXml="false"/>');" >
							<html:img titleKey="archigest.archivo.transferencias.cancelar.reencajado" altKey="archigest.archivo.transferencias.cancelar.reencajado" page="/pages/images/cajaDevuelta.gif" styleClass="imgTextMiddle" />
							 &nbsp;<bean:message key="archigest.archivo.transferencias.cancelar.reencajado"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
					</security:permissions>
				</c:if>
			</c:if>
			<%--ELIMINAR --%>
			<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
			<%--ENVIAR --%>
			<c:if test="${vRelacion.puedeSerEnviada}">
				<TD>
					<a class="etiquetaAzul12Bold" href="javascript:enviarRelacion()">
						 <html:img titleKey="archigest.archivo.enviar" altKey="archigest.archivo.enviar" page="/pages/images/enviar.gif" styleClass="imgTextMiddle"/>
						 &nbsp;<bean:message key="archigest.archivo.enviar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
			</c:if>
			</security:permissions>
			<%--RECIBIR --%>
			<c:if test="${vRelacion.puedeSerRecibida}">
				<security:permissions action="${appConstants.transferenciaActions.RECIBIR_RELACION}">
				<TD>
					<c:url var="urlRecibir" value="/action/recepcionRelaciones">
						<c:param name="method" value="recibirrelacion"/>
						<c:param name="codigo" value="${vRelacion.id}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href='<c:out value="${urlRecibir}" escapeXml="false"/>' >
						 <html:img titleKey="archigest.archivo.recibir" altKey="archigest.archivo.recibir" page="/pages/images/recibir.gif" styleClass="imgTextMiddle"/>
						 &nbsp;<bean:message key="archigest.archivo.recibir"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
			<%--RECHAZAR --%>
				<TD>
					<c:url var="urlRechazar" value="/action/recepcionRelaciones">
						<c:param name="method" value="initRechazarRelacion"/>
						<c:param name="codigo" value="${vRelacion.id}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href='<c:out value="${urlRechazar}" escapeXml="false"/>' >
						 <html:img titleKey="archigest.archivo.rechazar" altKey="archigest.archivo.rechazar" page="/pages/images/rechazar.gif" styleClass="imgTextMiddle"/>
						 &nbsp;<bean:message key="archigest.archivo.rechazar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>
			<%--COTEJAR Y SIGNATURAR --%>
			<c:if test="${vRelacion.puedeSerCotejada}">
				<security:permissions action="${appConstants.transferenciaActions.COTEJAR_RELACION}">
				<TD>

						<c:choose>
							<c:when test="${vRelacion.relacionConReencajado}">
								<c:url var="urlCotejar" value="/action/cotejoysignaturizacionAction">
									<c:param name="method" value="verCajasReencajado"/>
									<c:param name="idRelacion" value="${vRelacion.id}"/>
								</c:url>
							</c:when>
							<c:otherwise>
								<c:url var="urlCotejar" value="/action/cotejoysignaturizacionAction">
									<c:param name="method" value="verCajas"/>
									<c:param name="codigoseleccionada" value="${vRelacion.id}"/>
								</c:url>
							</c:otherwise>
						</c:choose>

					<a class="etiquetaAzul12Bold" href='<c:out value="${urlCotejar}" escapeXml="false"/>' >
						<html:img page="/pages/images/cotejar.gif"
							titleKey="archigest.archivo.transferencias.cotejarSignaturar"
							altKey="archigest.archivo.transferencias.cotejarSignaturar"
							styleClass="imgTextMiddle"/>

						<c:choose>
							<c:when test="${vRelacion.puedeSerSignaturada && not vRelacion.signaturacionAsociadaAHueco}">
								<bean:message key="archigest.archivo.transferencias.cotejarSignaturar"/>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.transferencias.cotejar"/>
							</c:otherwise>
						</c:choose>
					</a>
				</TD>
				<TD width="15">&nbsp;</TD>
				</security:permissions>
			</c:if>

			<%--FINALIZAR CORRECCION DE ERRORES --%>
			<c:if test="${vRelacion.permitidaFinalizacionCorreccion and  vRelacion.permitidoCorregirErrores}">
				<security:permissions action="${appConstants.transferenciaActions.FINALIZAR_CORRECCION}">
				<c:url var="finalizarCorreccionURL" value="/action/gestionRelaciones">
					<c:param name="method" value="finalizarCorreccion"/>
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}"/>
				</c:url>
				<TD>
					<a class="etiquetaAzul12Bold" href="<c:out value="${finalizarCorreccionURL}" escapeXml="false"/>">
						<html:img titleKey="archigest.archivo.transferencias.finalizarCorreccion" altKey="archigest.archivo.transferencias.finalizarCorreccion" page="/pages/images/finCorrecion.gif" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.transferencias.finalizarCorreccion"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>

			<%--GENERAR CARTELAS EN ARCHIVO RECEPTOR --%>
			<c:if test="${vRelacion.permitidaImpresionCartelasDefinitivas}">
				<security:permissions action="${appConstants.transferenciaActions.IMPRESION_CARTELAS_DEFINITIVAS}">
				<TD>
					<c:url var="urlImprimir" value="/action/gestionRelaciones">
						<c:param name="method" value="selCartelas"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
						<html:img titleKey="archigest.archivo.transferencias.generarCartelas" altKey="archigest.archivo.transferencias.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
						 &nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>

			<%--VALIDAR --%>
			<c:if test="${vRelacion.puedeSerValidada}">
				<security:permissions action="${appConstants.transferenciaActions.VALIDAR_RELACION}">
				<c:url var="verEstadoValidacionURL" value="/action/gestionRelaciones">
					<c:param name="method" value="verEstadoValidacion" />
					<c:param name="idRelacion" value="${vRelacion.id}" />
				</c:url>
					<TD>
						<a class="etiquetaAzul12Bold" href="<c:out value="${verEstadoValidacionURL}" escapeXml="false"/>" taget="_top">
							<html:img titleKey="archigest.archivo.transferencias.validar" altKey="archigest.archivo.transferencias.validar" page="/pages/images/validar.gif" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.transferencias.validar"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>

			<%--GESTIONAR RESERVA --%>
			<c:if test="${vRelacion.reservadeposito == appConstants.transferencias.reservaPrevision.PENDIENTE.identificador}">
				<security:permissions action="${appConstants.transferenciaActions.GESTION_SOLICITUD_RESERVA}">
				<c:url var="gestionSolicitudReservaURL" value="/action/gestionReservaRelaciones">
					<c:param name="method" value="vernavegador" />
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
				</c:url>
					<TD>
						<a class="etiquetaAzul12Bold" href="<c:out value="${gestionSolicitudReservaURL}" escapeXml="false"/>">
							<html:img titleKey="archigest.archivo.transferencias.gestionarReserva" altKey="archigest.archivo.transferencias.gestionarReserva" page="/pages/images/reservar.gif" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.transferencias.gestionarReserva"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>
			<%--UBICAR --%>
			<c:if test="${vRelacion.puedeSerUbicada}">
				<security:permissions action="${appConstants.transferenciaActions.UBICAR_RELACION}">
				<c:url var="ubicarRelacionURL" value="/action/gestionUbicacionRelaciones">
					<c:param name="method" value="comprobarreserva" />
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
				</c:url>
					<TD>
						<script>
							function ubicarRelacion() {
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
									var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
									window.top.showWorkingDiv(title, message);
								}
								window.location = '<c:out value="${ubicarRelacionURL}" escapeXml="false" />';
							}
						</script>
							<a class="etiquetaAzul12Bold" href="javascript:ubicarRelacion();" />
							<html:img titleKey="archigest.archivo.deposito.ubicarRelacion" altKey="archigest.archivo.deposito.ubicarRelacion" page="/pages/images/ubicar.gif" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.deposito.ubicarRelacion"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>

			<%--MODIFICAR UBICACION --%>
			<c:if test="${vRelacion.permitidaModificacionUbicacion}">
				<security:permissions action="${appConstants.transferenciaActions.MODIFICAR_UBICACION}">
				<c:url var="modificarUbicacionURL" value="/action/gestionRelaciones">
				<c:param name="method" value="modificarubicacion" />
				</c:url>
				<TD>
					<a class="etiquetaAzul12Bold" href="<c:out value="${modificarUbicacionURL}" escapeXml="false"/>">
						<html:img titleKey="archigest.archivo.transferencias.cambiarUbicacion" altKey="archigest.archivo.transferencias.cambiarUbicacion" page="/pages/images/cambiarUbicacion.gif" styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.transferencias.cambiarUbicacion"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>

			<%--INFORME DE RELACIÓN --%>
			<c:if test="${vRelacion.permitidoVerInformeRelacion}">
				<td>
					<c:url var="URL" value="/action/informeRelacion">
						<c:param name="idRelacion" value="${vRelacion.id}" />
			   		</c:url>
					<a class="etiquetaAzul12Bold"
					   href="<c:out value="${URL}" escapeXml="false"/>"
					><html:img page="/pages/images/documentos/doc_pdf.gif"
					        border="0"
					        altKey="archigest.archivo.informe"
					        titleKey="archigest.archivo.informe"
					        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.relacion"/></a>
				</td>
				<TD width="10">&nbsp;</TD>
			</c:if>
			<%--INFORME DE Entrega (Detalles Relacion)--%>
			<c:if test="${vRelacion.permitidoVerInformeDetallesRelacion }">
				<td>
					<c:url var="URL" value="/action/informeDetallesRelacion">
						<c:param name="idRelacion" value="${vRelacion.id}" />
			   		</c:url>
					<a class="etiquetaAzul12Bold"
					   href="<c:out value="${URL}" escapeXml="false"/>"
					><html:img page="/pages/images/documentos/doc_pdf.gif"
					        border="0"
					        altKey="archigest.archivo.informe"
					        titleKey="archigest.archivo.informe"
					        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.detalles"/>&nbsp;<bean:message key="archigest.archivo.relacion"/></a>
				</td>
				<TD width="10">&nbsp;</TD>
			</c:if>
			<%--INFORME DE COTEJO--%>
		   	<c:if test="${vRelacion.permitidoVerInformeCotejo}">
		   	<td>
				<c:url var="URL" value="/action/informeCotejo">
					<c:param name="idRelacion" value="${vRelacion.id}" />
		   		</c:url>
				<a class="etiquetaAzul12Bold"
				   href="<c:out value="${URL}" escapeXml="false"/>"
				><html:img page="/pages/images/documentos/doc_pdf.gif"
				        border="0"
				        altKey="archigest.archivo.informe"
				        titleKey="archigest.archivo.informe"
				        styleClass="imgTextMiddle"
				        />&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.cotejo"/></a>
			</td>
			<TD width="10">&nbsp;</TD>
			</c:if>

			<%--CERRAR --%>
			<TD>
				<c:url var="volverURL" value="/action/gestionRelaciones">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.relaciones.relacionEntrega"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
			</tiles:put>
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

		<tiles:insert page="/pages/tiles/PADataBlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
		<%--DOCUMENTOS ELECTRONICOS --%>
		<bean:define id="udocsSinDocsFisicos" name="udocsSinDocsFisicos" toScope="request"/>
		<bean:define id="infoAsignacion" name="infoAsignacion" toScope="request"/>
		<bean:define id="vRelacion" name="vRelacion" toScope="request"/>
		<bean:define id="FORMATO_FECHA" name="FORMATO_FECHA" toScope="request"/>
		<bean:define id="udocsRelacion" name="udocsRelacion" toScope="request"/>
		<bean:define id="cajasRelacion" name="cajasRelacion" toScope="request"/>

		<tiles:insert page="/pages/tiles/transferencias/relaciones/cuerpo_view_reea_docs_electronicos.jsp" />
		<%--FIN DOCUMENTOS ELECTRONICOS --%>

			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
<script>removeCookie('tabsInfoUdoc');</script>