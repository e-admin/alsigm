<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="MOSTRAR_NAVEGADOR_CAMBIO_UBICACION" value="false"/>
<c:if test="${!empty requestScope[appConstants.deposito.MOSTRAR_NAVEGADOR_CAMBIO_DESTINO_UBICACION] and requestScope[appConstants.deposito.MOSTRAR_NAVEGADOR_CAMBIO_DESTINO_UBICACION]=='true'}" >
	<c:set var="MOSTRAR_NAVEGADOR_CAMBIO_UBICACION" value="true"/>
</c:if>

<c:set var="PERMITIR_SELECCIONAR_ELEMENTO_UBICACION" value="false"/>
<c:if test="${!empty sessionScope[appConstants.deposito.PERMITIR_SELECCIONAR_ELEMENTO_UBICACION] and sessionScope[appConstants.deposito.PERMITIR_SELECCIONAR_ELEMENTO_UBICACION]=='true'}" >
	<c:set var="PERMITIR_SELECCIONAR_ELEMENTO_UBICACION" value="true"/>
</c:if>

<script language="javascript">
function mostrarNavegador(valor){
	if(valor){
		document.getElementById("naveg").style.display="block";
		document.getElementById("imgActualizar").style.display="block";
	}
	else{
		document.getElementById("naveg").style.display="none";
		document.getElementById("imgActualizar").style.display="none";
	}
}

function actualizarUbicacion(){

	document.getElementById("method").value = "actualizarubicacion";

	var valor = window.frames["navegador"].getValorSeleccionado();
	document.getElementById("asignabledestino").value = valor;


	if(valor == null || valor == "" ){
		alert("<bean:message key='archigest.archivo.transferencias.seleccioneUbicacion'/>");
		return;
	}

	document.getElementById("formulario").submit();
}


function cambiarElementoDestino(){
	document.getElementById("method").value = "cambiarElementoDestino";
	document.getElementById("formulario").submit();
}

function cancelarCambiarElementoDestino(){
	document.getElementById("method").value = "cancelarCambiarElementoDestino";
	document.getElementById("formulario").submit();
}

</script>

<html:form action="/gestionUbicacionRelaciones" styleId="formulario">
	<input type="hidden" name="method" id="method" value="actualizarubicacion"/>
	<html:hidden property="idrelacionseleccionada" styleId="idrelacionseleccionada" />
	<html:hidden property="asignabledestino" styleId="asignabledestino"/>

</html:form>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="vRelacion" value="${sessionScope[appConstants.deposito.RELACION_KEY]}"/>
<c:set var="ubicacion" value="${sessionScope[appConstants.deposito.DEPOSITO_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.ubicarRelacion" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		<c:choose>
		<c:when test="${MOSTRAR_NAVEGADOR_CAMBIO_UBICACION == 'true'}" >

			<TD noWrap>
					<a class="etiquetaAzul12Bold" href="javascript:actualizarUbicacion()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
			</TD>
			<c:if test="${not vRelacion.signaturacionAsociadaHuecoYSignaturaSolicitable}">
				<TD width="10">&nbsp;</TD>
				<TD noWrap>
					<a class=etiquetaAzul12Bold href="javascript:cancelarCambiarElementoDestino()" >
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.cancelar"/>
					</a>
				</TD>
			</c:if>
		</c:when>
		<c:otherwise>
			<TD noWrap>
				<c:url var="urlAtras" value="/action/gestionUbicacionRelaciones">
					<c:param name="method" value="goBack"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${urlAtras}" escapeXml="false"/>'">
					<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.atras"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<c:if test="${PERMITIR_SELECCIONAR_ELEMENTO_UBICACION == 'true'}" >
				<c:if test="${not vRelacion.signaturacionAsociadaHuecoYSignaturaSolicitable}">
					<TD noWrap>
						<a class="etiquetaAzul12Bold" href="javascript:cambiarElementoDestino()" style="text-align:right" >
							<html:img page="/pages/images/treeMover.gif" altKey="archigest.archivo.transferencias.cambiar.elemento.destino" titleKey="archigest.archivo.transferencias.cambiar.elemento.destino" styleClass="imgTextBottom" />
							<bean:message key="archigest.archivo.transferencias.cambiar.elemento.destino"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>
		   	<TD noWrap>
			   	<c:url var="URLInforme" value="/action/informeUbicacion">
					<c:param name="idRelacion" value="${vRelacion.id}" />
			   	</c:url>
			   	<a class="etiquetaAzul12Bold" href="<c:out value="${URLInforme}" escapeXml="false"/>" style="text-align:right" >
					<html:img page="/pages/images/documentos/doc_pdf.gif" border="0" altKey="archigest.archivo.informeUbicacion" titleKey="archigest.archivo.informeUbicacion" styleClass="imgTextBottom"/>
					<bean:message key="archigest.archivo.informeUbicacion"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			</c:if>
			<c:choose>
				<c:when test="${vRelacion.isIngresoDirecto}">
					<TD noWrap>
						<c:url var="urlAceptar" value="/action/gestionUbicacionRelaciones">
							<c:param name="method" value="aceptarinformeubicacion"/>
						</c:url>
						<script>
							function signaturarUbicarValidarIngreso() {
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.operacion.realizandoSignaturacionUbicacionValidacionIngreso"/>';
									var message = '<bean:message key="archigest.archivo.operacion.msgRealizandoSignaturacionUbicacionValidacionIngreso"/>';
									var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
									window.top.showWorkingDiv(title, message, message2);
								}
								window.location = '<c:out value="${urlAceptar}" escapeXml="false" />';
							}
						</script>
						<a class="etiquetaAzul12Bold" href="javascript:signaturarUbicarValidarIngreso();"/>
							<html:img page="/pages/images/validar.gif" altKey="archigest.archivo.deposito.ubicarValidarIngreso" titleKey="archigest.archivo.deposito.ubicarValidarIngreso" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.deposito.ubicarValidarIngreso"/>
						</a>
					</TD>
				</c:when>
				<c:otherwise>
					<TD noWrap>
						<c:url var="urlAceptar" value="/action/gestionUbicacionRelaciones">
							<c:param name="method" value="aceptarinformeubicacion"/>
						</c:url>
						<script>
							function aceptarUbicacion() {
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
									var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
									window.top.showWorkingDiv(title, message);
								}
								window.location = '<c:out value="${urlAceptar}" escapeXml="false" />';
							}
						</script>

						<a class="etiquetaAzul12Bold" href="javascript:aceptarUbicacion()" >
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>
					</TD>
				</c:otherwise>
			</c:choose>
			<TD width="10">&nbsp;</TD>
			<TD noWrap>
				<c:url var="urlCancelar" value="/action/gestionUbicacionRelaciones">
					<c:param name="method" value="goReturnPoint"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${urlCancelar}" escapeXml="false"/>'" >
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</c:otherwise>
		</c:choose>

		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.ingresoDirecto"/></tiles:put>
				</c:otherwise>
			</c:choose>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:choose>
					<c:when test="${!vRelacion.isIngresoDirecto}">
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
					</c:when>
					<c:otherwise>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_ingreso.jsp" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.espacioNecesario"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.deposito.numUnidadesUbicar"/>:&nbsp;
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
						<bean:message key="archigest.archivo.transferencias.ubicacionActual"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vRelacion.ubicacion.nombre}"/>
					</TD>
				</TR>

			</TABLE>

		<c:if test="${MOSTRAR_NAVEGADOR_CAMBIO_UBICACION == 'true'}" >

		<div id="naveg" >

		<TABLE class="w100" cellpadding=0 cellspacing=0>
			<TR>
				<TD>
				<c:url var="urlNavegador" value="/action/navegadorReservaDepositoAction">
					<c:param name="method" value="initial"/>
					<c:param name="root" value="${sessionScope[appConstants.deposito.EDIFICIO_KEY]}"/>
					<c:param name="filterByIdformato" value="${vRelacion.idformatoui}"/>
					<c:param name="seleccionadoinicial" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_DESTINO_KEY]}"/>
					<c:param name="numHuecosNecesarios" value ="${vRelacion.numeroUnidadesInstalacion}"/>
					<c:param name="recorrerDepositos" value ="S"/>
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
		</div>

		</c:if>
		</tiles:put>
		</tiles:insert>
	<c:if test="${MOSTRAR_NAVEGADOR_CAMBIO_UBICACION == 'false'}" >
		<c:set var="huecosReservados" value="${sessionScope[appConstants.deposito.LISTA_HUECOS_RESERVADOS_KEY]}"/>
		<c:if test="${!empty huecosReservados}">
		<div class="separador5">&nbsp;</div>
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.msgOcuparHuecosReservados"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador5">&nbsp;</div>

				<display:table name="pageScope.huecosReservados"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="listaRegistros"
					export="false">

					<display:column titleKey="archigest.archivo.ubicacion" property="hueco.path" />
					<display:column titleKey="archigest.archivo.deposito.conCajaN" property="unidadInstalacion.orden" style="width:15%" />
					<c:choose>
						<c:when test="${ubicacion.tipoSignaturacion == appConstants.transferencias.tiposSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO.identificador}">
							<display:column titleKey="archigest.archivo.deposito.sigCaja" property="hueco.numeracion" style="width:15%" />
						</c:when>
						<c:otherwise>
							<display:column titleKey="archigest.archivo.deposito.sigCaja" property="unidadInstalacion.signaturaUI" style="width:15%" />
						</c:otherwise>
					</c:choose>
				</display:table>
			<div class="separador5">&nbsp;</div>
			</tiles:put>
			</tiles:insert>
		</c:if>

		<c:set var="huecosALiberar" value="${sessionScope[appConstants.deposito.LISTA_HUECOS_A_LIBERAR_KEY]}"/>
		<c:if test="${!empty huecosALiberar}">
		<div class="separador5">&nbsp;</div>
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.msgLiberarHuecos"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador5">&nbsp;</div>
				<display:table name="pageScope.huecosALiberar"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="listaRegistros"
					sort="page"
					export="false">
					<display:column titleKey="archigest.archivo.ubicacion" property="hueco.path" />
				</display:table>
			<div class="separador5">&nbsp;</div>
			</tiles:put>
			</tiles:insert>
		</c:if>

		<c:set var="huecosAOcupar" value="${sessionScope[appConstants.deposito.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY]}"/>
		<c:if test="${! empty huecosAOcupar}">
		<div class="separador5">&nbsp;</div>
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">


			<bean:message key="archigest.archivo.deposito.msgOcuparHuecosLibres"/>

			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador5">&nbsp;</div>

				<display:table name="pageScope.huecosAOcupar"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="listaRegistros"
					sort="page"
					defaultsort="2"
					export="false">
					<display:column titleKey="archigest.archivo.ubicacion" property="hueco.path" />
					<display:column titleKey="archigest.archivo.deposito.nCaja" property="unidadInstalacion.orden" style="width:15%" />
					<c:choose>
						<c:when test="${ubicacion.tipoSignaturacion == appConstants.transferencias.tiposSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO.identificador}">
							<display:column titleKey="archigest.archivo.deposito.sigCaja" property="hueco.numeracion" style="width:15%" />
						</c:when>
						<c:otherwise>
							<display:column titleKey="archigest.archivo.deposito.sigCaja" property="unidadInstalacion.signaturaUI" style="width:15%" />
						</c:otherwise>
					</c:choose>
				</display:table>
			<div class="separador5">&nbsp;</div>
			</tiles:put>
			</tiles:insert>
		</c:if>
	</c:if>




	</tiles:put>
</tiles:insert>