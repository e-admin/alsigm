<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<c:set var="detallePrevision" value="${__INFO_DETALLE}" />

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="estadoAbierta" value="${appConstants.transferencias.estadoPrevision.ABIERTA.identificador}" />

<c:set var="listaUInst" value="${sessionScope[appConstants.transferencias.LISTA_UINST_ENTRE_ARCHIVOS_KEY]}"/>
<c:set var="listaUDocsElectronicas" value="${sessionScope[appConstants.transferencias.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY]}"/>

<c:choose>
<c:when test="${param.method == 'nuevoDetalle'}">
	<c:set var="__ACTION_TO_PERFORM" scope="session" value="crearDetalle" />
</c:when>
<c:when test="${param.method == 'editarDetalle'}">
	<c:set var="__ACTION_TO_PERFORM" scope="session" value="guardarDetalle" />
</c:when>
</c:choose>

<script>

	function mostrarBuscadorSeries(formName, id) {
		var buscadorSeries = xGetElementById('buscadorSerie'+id);
		if (!buscadorSeries || buscadorSeries.style.display == 'none') {
			var form = document.forms[formName];
			form.elements['method'].value="verBuscadorSeries"+id;
			form.submit();
		} else
			xDisplay(buscadorSeries, 'none');
	}

	function mostrarInformacionUinst(formName) {
		var form = document.forms[formName];
		form.elements['method'].value="mostrarInfoUnidadesInstalacion";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		form.submit();
	}

</script>
<bean:struts id="mappingGestionDetallesPrevision" mapping="/gestionDetallesPrevision" />
<c:set var="detallePrevisionFormName" value="${mappingGestionDetallesPrevision.name}" />
<c:set var="detallePrevisionForm" value="${requestScope[detallePrevisionFormName]}" />

<div id="contenedor_ficha">

	<html:form action="/gestionDetallesPrevision">

		<input type="hidden" name="method" value="<c:out value="${__ACTION_TO_PERFORM}" />">
		<html:hidden property="serieOrigen" styleId="serieOrigen"/>
		<html:hidden property="serieDestino" styleId="serieDestino"/>

		<div class="ficha">

			<h1><span>
				<div class="w100">
					<TABLE class="w98" cellpadding=0 cellspacing=0>
						<TR>
						    <TD class="etiquetaAzul12Bold" height="25px">
								<bean:message key="archigest.archivo.transferencias.edicion"/><bean:message key="archigest.archivo.transferencias.ele"/>
								<bean:message key="archigest.archivo.transferencias.contenido"/>
							</TD>
						    <TD align="right">
								<TABLE cellpadding=0 cellspacing=0>
									<TR>
										<TD>
											<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${detallePrevisionFormName}" />'].submit()" >
												<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
												&nbsp;<bean:message key="archigest.archivo.aceptar"/>
											</a>
										</TD>
										<TD width="10">&nbsp;</TD>
										<TD>
											<c:url var="cancelURI" value="/action/gestionDetallesPrevision">
												<c:param name="method" value="goBack"  />
											</c:url>
											<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
												<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
												&nbsp;<bean:message key="archigest.archivo.cancelar"/>
											</a>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
				</div>
			</span></h1>

		<div class="cuerpo_drcha">
			<div class="cuerpo_izda">

			<div id="barra_errores">
					<archivo:errors />
			</div>

			<div class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
						<jsp:include page="cabeceracte_prevision.jsp" flush="true" />
			</div> <%--primer bloque de datos (resumen siempre visible) --%>

			<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
			</div>

			<div class="bloque"> <%--primer bloque de datos --%>

				<table class="formulario">
					<c:if test="${bPrevision.entreArchivos}">
						<TR>
							<TD class="tdTitulo" width="170px">
								<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
							</TD>
							<TD class="tdDatosSinMargenIzq">
								<c:out value="${bPrevision.nombrearchivoremitente}"/>
							</TD>
						</TR>
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.archivoReceptor"/>:&nbsp;
							</TD>
							<TD class="tdDatosSinMargenIzq">
								<c:out value="${bPrevision.nombrearchivoreceptor}"/>
							</TD>
						</TR>
					</c:if>

					<c:set var="bListaCodigosFondoKey" value="${appConstants.transferencias.LISTA_CODIGOSFONDO_KEY}"/>
					<jsp:useBean id="bListaCodigosFondoKey" type="java.lang.String"/>
					<c:set var="listaFondos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSFONDO_KEY]}" />
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.cf.fondoOrigen"/>:&nbsp;
						</TD>
						<TD class="tdDatosSinMargenIzq">
							<script>
							function cambioListaFondoOrigen(){
								document.getElementById("nombreSerieOrigen").value = "";
							}
							</script>

							<c:if test="${!empty listaFondos}">
								<html:select property='idFondoOrigen' size="1" styleClass="input" onchange="cambioListaFondoOrigen()">
									<html:option value=""></html:option>
									<html:optionsCollection name="<%=bListaCodigosFondoKey%>" label="label" value="id"/>
								</html:select>
							</c:if>
						</TD>
					</TR>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.serieOrigen"/>:&nbsp;
						</td>
						<td class="tdDatosSinMargenIzq">
							<c:set var="canSelectSerie" value="${bPrevision.entreArchivos && bPrevision.estado == estadoAbierta}" />
							<c:set var="inputClass" value="input95" />
							<c:if test="${!canSelectSerie}"><c:set var="inputClass" value="inputRO99" /></c:if>
							<input name="nombreSerieOrigen" id="nombreSerieOrigen" type="text" class="<c:out value="${inputClass}" />"
							value="<c:out value="${detallePrevisionForm.nombreSerieOrigen}" />" <c:if test="${!canSelectSerie}">readOnly=1</c:if>>
							<c:if test="${canSelectSerie}">
								<a href="javascript:mostrarBuscadorSeries('<c:out value="${mappingGestionDetallesPrevision.name}" />','Origen')">
									<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.series" altKey="archigest.archivo.series" styleClass="imgTextMiddleHand" />
								</a>
								<bean:define id="idBuscadorSerie" value="Origen" toScope="request"/>
								<bean:define id="formName" name="mappingGestionDetallesPrevision" property="name" toScope="request"/>
								<c:if test="${param.method == 'verBuscadorSeriesOrigen' || param.method == 'buscarSerieOrigen'}">
									<jsp:include page="buscador_serie_generico.jsp" flush="true" />
								</c:if>
							</c:if>
						</td>
					</tr>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.cf.fondoDestino"/>:&nbsp;
						</TD>
						<TD class="tdDatosSinMargenIzq">
							<script>
							function cambioListaFondoDestino(){
								document.getElementById("nombreSerieDestino").value = "";
							}
							</script>

							<c:if test="${!empty listaFondos}">
								<html:select property='idFondoDestino' size="1" styleClass="input" onchange="cambioListaFondoDestino()" >
									<html:option value=""></html:option>
									<html:optionsCollection name="<%=bListaCodigosFondoKey%>" label="label" value="id"/>
								</html:select>
							</c:if>
						</TD>
					</TR>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.serieDestino"/>:&nbsp;
						</td>
						<td class="tdDatosSinMargenIzq">
							<c:set var="canSelectSerie" value="${!bPrevision.ordinaria && bPrevision.estado == estadoAbierta}" />
							<c:set var="inputClass" value="input95" />
							<c:if test="${!canSelectSerie}"><c:set var="inputClass" value="inputRO99" /></c:if>
							<input name="nombreSerieDestino" id="nombreSerieDestino" type="text" class="<c:out value="${inputClass}" />"
							value="<c:out value="${detallePrevisionForm.nombreSerieDestino}" />" <c:if test="${!canSelectSerie}">readOnly=1</c:if>>
							<c:if test="${canSelectSerie}">
								<a href="javascript:mostrarBuscadorSeries('<c:out value="${mappingGestionDetallesPrevision.name}" />','Destino')">
									<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.series" altKey="archigest.archivo.series" styleClass="imgTextMiddleHand" />
								</a>
								<bean:define id="idBuscadorSerie" value="Destino" toScope="request"/>
								<bean:define id="formName" name="mappingGestionDetallesPrevision" property="name" toScope="request"/>
								<c:if test="${param.method == 'verBuscadorSeriesDestino' || param.method == 'buscarSerieDestino'}">
									<jsp:include page="buscador_serie_generico.jsp" flush="true" />
								</c:if>
							</c:if>
						</td>
					</tr>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
						<tr>
						<td class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.transferencias.observaciones"/>:
						</td>
						<td class="tdDatosSinMargenIzq">
							<html:textarea property="observaciones" rows="3" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
						</td>
					</tr>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>

					<TR>
						<TD class="tdTitulo" colspan="2">
							<bean:message key="archigest.archivo.transferencias.rangoFechas"/>:&nbsp;
						</TD>
					</TR>

					<TR>
						<TD class="tdTitulo">&nbsp;</TD>
						<TD class="tdDatosSinMargenIzq">
							<TABLE cellpadding="0" cellspacing="0">
								<TR>
									<TD width="100px" class="td2Titulo">
										<bean:message key="archigest.archivo.transferencias.fechaInicioExps"/>:&nbsp;
									</TD>
									<TD width="50px">
										<html:text property="fechaInicio" size="5" maxlength="4"/>
									</TD>
									<TD width="20px">
									</TD>
									<TD width="100px" class="td2Titulo">
										<bean:message key="archigest.archivo.transferencias.fechaFinExps"/>:&nbsp;
									</TD>
									<TD width="50px">
										<html:text property="fechaFin" size="5" maxlength="4"/>
									</TD>
									<TD width="20px">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</tr>
					</table>
			</div> <%--bloque --%>


		<div class="separador8">&nbsp;</div>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.transferencia.entre.archivos.detalle.unidades" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<a class=etiquetaAzul12Bold href="javascript:mostrarInformacionUinst('<c:out value="${mappingGestionDetallesPrevision.name}" />')">
				<html:img page="/pages/images/actualizar.gif" altKey="archigest.archivo.actualizar" titleKey="archigest.archivo.actualizar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.actualizar"/>
			</a>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<c:if test="${empty listaUDocsElectronicas && empty listaUInst}">
					<bean:message key="archigest.archivo.transferencias.no.ui.entre.archivos"/>
			</c:if>
			<c:if test="${not empty listaUDocsElectronicas}">
				<div class="separador8">&nbsp;</div>
				<div style="margin:5px">
				<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
						<html:img page="/pages/images/docelectronico.gif" altKey="archigest.archivo.transferencias.unidadesElectronicas" titleKey="archigest.archivo.transferencias.unidadesElectronicas" styleClass="imgTextBottom" />
						&nbsp;
						<bean:message key="archigest.archivo.transferencias.unidadesElectronicas" />
				</tiles:put>
				<tiles:put name="buttonBar" direct="true">
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<display:table name="pageScope.listaUDocsElectronicas"
						style="width:99%;"
						id="listaRegistros"
						export="false">
						<display:column titleKey="archigest.archivo.transferencias.asunto" property="titulo"/>
					</display:table>
				</tiles:put>
				</tiles:insert>
				</div>
				<div class="separador8">&nbsp;</div>
			</c:if>

			<c:if test="${not empty listaUInst}">
				<div style="margin:5px">
				<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
						<html:img page="/pages/images/caja_closed.gif" altKey="archigest.archivo.transferencias.informacionUInst" titleKey="archigest.archivo.transferencias.informacionUInst" styleClass="imgTextBottom" />&nbsp;
						<bean:message key="archigest.archivo.transferencias.informacionUInst"/>
				</tiles:put>
				<tiles:put name="buttonBar" direct="true">
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<display:table name="pageScope.listaUInst"
						style="width:99%;"
						id="listaRegistros"
						export="false">

						<display:column titleKey="archigest.archivo.signatura" property="signaturaui"/>
						<display:column titleKey="archigest.archivo.transferencias.formato" property="nombreFormato"/>
						<display:column titleKey="archigest.archivo.transferencias.unidades.validas" property="unidadesvalidas"/>
						<display:column titleKey="archigest.archivo.transferencias.unidades.totales" property="unidadestotales"/>

					</display:table>
				</tiles:put>
				</tiles:insert>
				</div>
				<div class="separador8">&nbsp;</div>
			</c:if>



		</tiles:put>
		</tiles:insert>




			</div> <%--cuerpo_drcha --%>
		</div> <%--cuerpo_izda --%>

		<h2><span>&nbsp;
		</span></h2>

		</div> <%--ficha --%>



	</html:form>




</div> <%--contenedor_ficha --%>



