<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
<bean:define id="classTdTituloCampoSinBorde" value="tdTituloFichaSinBorde" toScope="request"/>
<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
<bean:define id="classTdCampoSinBorde" value="" toScope="request"/>
<bean:define id="sizeCampo" value="200" toScope="request"/>
<bean:struts id="actionMapping" mapping="/consultaUnidadesDocumentalesAction" />

<c:set var="tiposUsuarioPrestamosCollection" value="${sessionScope[appConstants.consultaUnidadesDocumentales.LISTADO_TIPOS_USUARIO_PRESTAMOS]}" />
<c:set var="tiposUsuarioConsultasCollection" value="${sessionScope[appConstants.consultaUnidadesDocumentales.LISTADO_TIPOS_USUARIO_CONSULTAS]}" />
<c:set var="listaUdocs" value="${sessionScope[appConstants.consultaUnidadesDocumentales.LISTADO_BUSQUEDA_UDOCS]}" />
<c:set var="tiposServicio" value="${sessionScope[appConstants.consultaUnidadesDocumentales.LISTADO_TIPOS_SERVICIO]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="mostrarResultados" value="${sessionScope[appConstants.consultaUnidadesDocumentales.MOSTRAR_RESULTADOS]}" />
<script>

	function changeCheckBox(){

		if(document.getElementById("tipoServicioPrestamos").checked==true)
		{
			document.getElementById("tipoUsuarioPrestamos").disabled=false;
		}

		else
		{
			document.getElementById("tipoUsuarioPrestamos").disabled=true;
		}

		if(document.getElementById("tipoServicioConsultas").checked==true)
		{
			document.getElementById("tipoUsuarioConsultas").disabled=false;
		}

		else
		{
			document.getElementById("tipoUsuarioConsultas").disabled=true;
		}

	}


</script>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.prestamos.consultarUnidadesDocumentales"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table cellspacing=0 cellpadding=0><tr>
		<td nowrap>
			<script>
				function buscar() {
					if (window.top.showWorkingDiv) {
						var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
						var message = '<bean:message key="archigest.archivo.buscando.msgRegistroEntregas"/>';
						var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
						window.top.showWorkingDiv(title, message, message2);
					}
					document.getElementById("linkPressed").value="buscar";
					document.forms["<c:out value="${actionMapping.name}" />"].submit();
				}
			</script>
			<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
			<html:img page="/pages/images/buscar.gif" border="0" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.buscar"/></a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true"/>
		</td>
	</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<html:form action="/consultaUnidadesDocumentalesAction">
		<input type="hidden" name="method" id="method" value="buscar">
		<input type="hidden" id="linkPressed" name="linkPressed"/>
		<div id="barra_errores"><archivo:errors/></div>
		<div class="bloque">
				<table class="formulario">
					<tiles:insert page="../fondos/busquedas/campo_busqueda_condiciones_ambito.jsp" flush="true"/>

					<tiles:insert page="prestamos/busquedas/campo_busqueda_numero_expediente.jsp" flush="true"/>

					<bean:define id="mostrarCalificadorSignatura" value="S" toScope="request"/>
					<tiles:insert page="prestamos/busquedas/campo_busqueda_signatura.jsp" flush="true"/>

					<%--Filtro por solicitante de servicio --%>
					<tr>
						<td class="tdTituloFicha" width="150">
							<bean:message key="archigest.archivo.consultas.busqueda.solicitante"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="solicitante" styleClass="input60"/>
						</td>
					</tr>

					<tiles:insert page="campo_busqueda_fecha_entrega.jsp" flush="true"/>

					<tiles:insert page="campo_busqueda_fecha_devolucion.jsp" flush="true"/>

					<%-- Filtro por tipo de servicio--%>
					<tr><td class="tdTituloFichaSinBorde">
							<bean:message key="archigest.archivo.busqueda.form.tipoServicio"/>:&nbsp;
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150">&nbsp;</td>
						<td class="tdDatos">

							<table cellpadding="0" cellspacing="0">

							<c:forEach var="tipoServicio" items="${tiposServicio}" varStatus="row">
									<tr>


										<c:if test="${tipoServicio.value==appConstants.prestamos.TIPO_SOLICITUD_PRESTAMO}">
											<td class="tdDatos2">
												<html:multibox style="border:0" property="tiposServicio" styleId="tipoServicioPrestamos" onclick="javascript:changeCheckBox()">
													<bean:write name="tipoServicio" property="value"/>
												</html:multibox>
											</td>
										</c:if>
										<c:if test="${tipoServicio.value==appConstants.consultas.TIPO_SOLICITUD_CONSULTA}">
											<td class="tdDatos2">
												<html:multibox style="border:0" property="tiposServicio" styleId="tipoServicioConsultas" onclick="javascript:changeCheckBox()">
													<bean:write name="tipoServicio" property="value"/>
												</html:multibox>
											</td>
										</c:if>
										<td class="tdDatos2">
											<bean:write name="tipoServicio" property="label"/>
										</td>
										<td width="50">&nbsp;</td>

										<td class="tdDatos2">
											<bean:message key="archigest.archivo.tipoUsuario"/>:
										</td>

										<td width="10">&nbsp;</td>


										<c:if test="${tipoServicio.value==appConstants.prestamos.TIPO_SOLICITUD_PRESTAMO}">
											<td class="tdDatos2">
												<html:select property="tipoUsuarioPrestamos" styleId="tipoUsuarioPrestamos">
													<html:option value="">&nbsp;</html:option>
													<html:optionsCollection name="tiposUsuarioPrestamosCollection" value="value" label="label"/>
												</html:select>
											</td>
										</c:if>
										<c:if test="${tipoServicio.value==appConstants.consultas.TIPO_SOLICITUD_CONSULTA}">
											<td class="tdDatos2">
												<html:select property="tipoUsuarioConsultas" styleId="tipoUsuarioConsultas">
													<html:option value="">&nbsp;</html:option>
													<html:optionsCollection name="tiposUsuarioConsultasCollection" value="value" label="label"/>
												</html:select>
											</td>
										</c:if>

									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>

				</table>

				<div class="separador8">&nbsp;</div>


		<c:if test="${mostrarResultados}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

				<display:table name="pageScope.listaUdocs"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="udocs"
					pagesize="0"
					sort="external"
					defaultsort="0"
					requestURI="../../action/consultaUnidadesDocumentalesAction?method=buscar"
					export="true"
					excludedParams="linkPressed">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.descripcion.busqueda.vacia"/>
					</display:setProperty>

					<display:column titleKey="archigest.archivo.cf.codReferencia"  sortable="true" headerClass="sortable" sortProperty="codReferencia" media="html">
						<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
							<c:param name="method" value="verEnFondos" />
							<c:param name="unidadDocumental" value="${udocs.idudoc}" />
						</c:url>

						<a href="<c:out value="${infoUdocURL}" escapeXml="false" />" class="tdlink">
							<span title='<c:out value="${udocs.codReferencia}"/>'><html:img page="/pages/images/abreviado.gif" styleClass="imgTextMiddle"/></span>
						</a>
						<c:set var="codigoReferencia" value="${udocs.codReferenciaAbreviado}"/>
						<c:out value="${codigoReferencia}"/>
					</display:column>

					<display:column titleKey="archigest.archivo.cf.codReferencia" property="codReferencia" media="csv excel xml pdf"/>

					<display:column titleKey="archigest.archivo.solicitudes.titulo"  sortable="true" headerClass="sortable" property="titulo" media="html csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc"  sortable="true" headerClass="sortable" property="signaturaudoc" media="html csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.solicitudes.expedienteudoc" sortable="true" headerClass="sortable" sortProperty="expedienteudoc" media="html">
						<c:choose>
							<c:when test="${udocs.subtipoCaja}">
								<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
								<c:if test="${!empty udocs.nombreRangos}">
									<c:out value="${udocs.nombreRangos}"/><br/>
								</c:if>
								<c:out value="${udocs.expedienteudoc}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${udocs.expedienteudoc}" />
							</c:otherwise>
						</c:choose>
					</display:column>

					<display:column titleKey="archigest.archivo.solicitudes.expedienteudoc" sortable="true" headerClass="sortable" sortProperty="expedienteudoc" media="csv excel xml pdf">
						<c:choose>
							<c:when test="${udocs.subtipoCaja}">
								<c:choose>
									<c:when test="${!empty udocs.expedienteudoc}">
										<c:out value="${udocs.expedienteudoc}"/>
									</c:when>
									<c:otherwise>
										<c:out value="${udocs.nombreRangos}"/>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:out value="${udocs.expedienteudoc}" />
							</c:otherwise>
						</c:choose>
					</display:column>

					<display:column titleKey="archigest.archivo.serie" sortable="true" headerClass="sortable" property="nombreSerie" media="html csv excel xml pdf" />

					<display:column titleKey="archigest.archivo.tipo" media="html">

						<c:if test="${appConstants.prestamos.TIPO_SOLICITUD_PRESTAMO == udocs.tiposolicitud}">
							<c:url var="URL" value="/action/gestionPrestamos">
								<c:param name="method" value="verprestamo" />
								<c:param name="id" value="${udocs.idsolicitud}" />
							</c:url>
						</c:if>

						<c:if test="${appConstants.consultas.TIPO_SOLICITUD_CONSULTA == udocs.tiposolicitud}">
							<c:url var="URL" value="/action/gestionConsultas">
								<c:param name="method" value="verconsulta" />
								<c:param name="id" value="${udocs.idsolicitud}" />
							</c:url>
						</c:if>


						<a class="tdlink" href="<c:out value="${URL}" escapeXml="false"/>" >
							<c:if test="${appConstants.prestamos.TIPO_SOLICITUD_PRESTAMO == udocs.tiposolicitud}">
								<html:img page="/pages/images/prestamo.gif" altKey="archigest.archivo.prestamos.prestamo" titleKey="archigest.archivo.prestamos.prestamo" styleClass="imgTextBottom" />
							</c:if>
							<c:if test="${appConstants.consultas.TIPO_SOLICITUD_CONSULTA == udocs.tiposolicitud}">
								<html:img page="/pages/images/consulta.gif" altKey="archigest.archivo.consultas.consulta" titleKey="archigest.archivo.consultas.consulta" styleClass="imgTextBottom" />
							</c:if>
						</a>
					</display:column>

					<display:column titleKey="archigest.archivo.tipo" media="csv excel xml pdf">
						<c:if test="${appConstants.prestamos.TIPO_SOLICITUD_PRESTAMO == udocs.tiposolicitud}">
							<bean:message key="archigest.archivo.prestamos.prestamo"/>
						</c:if>
						<c:if test="${appConstants.consultas.TIPO_SOLICITUD_CONSULTA == udocs.tiposolicitud}">
							<bean:message key="archigest.archivo.consultas.consulta"/>
						</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.fechaEntrega" sortable="true" headerClass="sortable" sortProperty="fentrega" media="html csv excel xml pdf">
						<fmt:formatDate value="${udocs.fentrega}" pattern="${FORMATO_FECHA}" />
					</display:column>

					<display:column titleKey="archigest.archivo.estado" sortable="true" headerClass="sortable" sortProperty="estado" media="html csv excel xml pdf" >
						<bean-el:message key="archigest.archivo.solicitudes.detalle.estado.${udocs.estado}"/>
					</display:column>

					<display:column titleKey="archigest.archivo.fEstado" sortable="true" headerClass="sortable" sortProperty="festado" media="html csv excel xml pdf">
						<fmt:formatDate value="${udocs.festado}" pattern="${FORMATO_FECHA}" />
					</display:column>

					<display:column titleKey="archigest.archivo.solicitudes.numsolicitud" property="codigoTransferencia" media="html csv excel xml pdf"/>

					<display:column titleKey="archigest.archivo.prestamos.solicitante" sortable="true" headerClass="sortable" property="solicitante" media="html csv excel xml pdf"/>

				</display:table>

			</tiles:put>
		</tiles:insert>
		</c:if>

		<script>
			changeCheckBox();
		</script>
		</div>

		</html:form>
	</tiles:put>
</tiles:insert>
