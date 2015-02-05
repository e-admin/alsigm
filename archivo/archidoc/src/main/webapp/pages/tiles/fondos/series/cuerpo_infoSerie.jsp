<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="identificacionSerie" value="${sessionScope[appConstants.fondos.IDENTIFICACION_SERIE_KEY]}" />
<c:set var="valoracion" value="${sessionScope[appConstants.valoracion.VALORACION_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.serie"/></tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>

			<c:set var="treeView" value="${sessionScope[appConstants.fondos.CUADRO_CLF_VIEW_NAME]}" />
			<c:set var="parentNode" value="${treeView.selectedNode.parent}" />
			<c:if test="${!empty parentNode}">
				<td>
					<c:url var="viewElementURL" value="/action/manageVistaCuadroClasificacion">
						<c:param name="actionToPerform" value="verNodo" />
						<c:param name="node" value="${parentNode.nodePath}" />
						<c:param name="refreshView" value="true" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${viewElementURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.verPadre"/>
					</a>
				</td>
				<TD width="10">&nbsp;</TD>
			</c:if>

			<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
				<td>
					<c:url var="moverSerieURL" value="/action/gestionSeries">
						<c:param name="method" value="moveSerie" />
						<c:param name="idSerie" value="${serie.id}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${moverSerieURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/treeMover.gif" altKey="archigest.archivo.cf.mover" titleKey="archigest.archivo.cf.mover" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.mover"/>
					</a>
				</td>
				<TD width="10">&nbsp;</TD>
			</security:permissions>
			<c:choose>
			<c:when test="${serie.estadoserie == appConstants.estadosSerie.NO_VIGENTE}">
				<security:permissions action="${appConstants.fondosActions.SOLICITUD_CAMBIOS_ESTADO_O_MODIF_ACTION}">

				<TD>
					<c:url var="pasoAVigenteURL" value="/action/gestionSeries">
						<c:param name="method" value="pantallaSolicitudPasoAVigente" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${pasoAVigenteURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/vigente.gif" altKey="archigest.archivo.cf.solicitarPasoVigente" titleKey="archigest.archivo.cf.solicitarPasoVigente" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.solicitarPasoVigente"/>
					</a>
				</TD>
				</security:permissions>
			</c:when>
			<c:when test="${serie.estadoserie == appConstants.estadosSerie.VIGENTE}">
				<security:permissions permission="${appConstants.permissions.GESTION_SOLICITUDES_SERIE}">
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="serieEnEstudioURL" value="/action/gestionSeries">
						<c:param name="method" value="serieEnEstudio" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${serieEnEstudioURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/go.gif" altKey="archigest.archivo.cf.pasarAEnEstudio" titleKey="archigest.archivo.cf.pasarAEnEstudio" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.pasarAEnEstudio"/>
					</a>
				</TD>
				</security:permissions>
				<security:permissions permission="${appConstants.permissions.GESTOR_SERIE}">
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="solicitarPasoAHistoricaURL" value="/action/gestionSeries">
						<c:param name="method" value="pantallaSolicitudPasoAHistorica" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${solicitarPasoAHistoricaURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/go.gif" altKey="archigest.archivo.cf.solicitarPasoAHistorica" titleKey="archigest.archivo.cf.solicitarPasoAHistorica" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.solicitarPasoAHistorica"/>
					</a>
				</TD>
				</security:permissions>
			</c:when>
			<c:when test="${serie.estadoserie == appConstants.estadosSerie.EN_ESTUDIO}">
				<security:permissions action="${appConstants.fondosActions.SOLICITUD_CAMBIOS_IDENTIFICACION_ACTION}">
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="autorizacionCambiosURL" value="/action/gestionSeries">
						<c:param name="method" value="solicitarAutorizacionCambios" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${autorizacionCambiosURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/go.gif" altKey="archigest.archivo.cf.solicitarAutorizacion" titleKey="archigest.archivo.cf.solicitarAutorizacion" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.solicitarAutorizacion"/>
					</a>
				</TD>
				</security:permissions>
			</c:when>
			</c:choose>
			<security:permissions action="${appConstants.fondosActions.ELIMINACION_ELEMENTO_ACTION}">
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="eliminarURI" value="/action/gestionSeries">
						<c:param name="method" value="eliminar" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
					<script>
						function eliminarSerie() {
							if (confirm("<bean:message key='archigest.archivo.cf.msgConfirmSerieEliminar'/>"))
								window.location = '<c:out value="${eliminarURI}" escapeXml="false"/>';
						}
					</script>

					<a class="etiquetaAzul12Bold" href="javascript:eliminarSerie();">
						<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</TD>
				</security:permissions>
			<TD width="10">&nbsp;</TD>
			<TD>
				<tiles:insert definition="button.closeButton" />
			</TD>
		</TR>
		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<c:if test="${!identificacionSerie.withoutValues}">
				<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
				<security:access object="${serie}" permission="${appConstants.permissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION}">
			   	<td nowrap="nowrap">
					<c:url var="verDescipcionSerieURL" value="/action/isadg">
						<c:param name="method" value="retrieve" />
						<c:param name="id" value="${serie.id}" />
					</c:url>
			   		<a class="etiquetaAzul12Bold"
					   href="<c:out value="${verDescipcionSerieURL}" escapeXml="false"/>" >
					   <html:img page="/pages/images/searchDoc.gif"
					        altKey="archigest.archivo.descripcion.descriptor.button.verDescripcion"
					        titleKey="archigest.archivo.descripcion.descriptor.button.verDescripcion"
					        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.descripcion.descriptor.button.verDescripcion"/></a>
				</td>
				</security:access>
				</security:permissions>
				</c:if>

					<security:permissions action="${appConstants.documentosActions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
						<security:access object="${serie}" permission="${appConstants.permissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION}">
							<td width="10">&nbsp;</td>
						   	<td nowrap="nowrap">
								<c:url var="verDocumentosURL" value="/action/documentos">
									<c:param name="actionToPerform" value="homeUDoc" />
									<c:param name="id" value="${serie.id}" />
									<c:param name="tipo" value="1" />
								</c:url>
						   		<a class="etiquetaAzul12Bold"
						   		   target="_self"
								   href="<c:out value="${verDocumentosURL}" escapeXml="false"/>" >
								   <html:img page="/pages/images/docsElectronicos.gif"
								        altKey="archigest.archivo.descripcion.descriptor.button.verDocumentos"
								        titleKey="archigest.archivo.descripcion.descriptor.button.verDocumentos"
								        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.descripcion.descriptor.button.verDocumentos"/></a>
							</td>
						</security:access>
					</security:permissions>
				<td width="10">&nbsp;</td>

				<c:choose>
				<c:when test="${serie.contieneUnidadesDocumentales}">

				<td nowrap="nowrap" class="etiquetaAzul12Bold">
					<c:url var="busquedaContenidoSerieURL" value="/action/buscarElementos">
						<c:param name="method" value="formBusquedaUDocsSerie" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
			   		<a class="etiquetaAzul12Bold"
					   href="<c:out value="${busquedaContenidoSerieURL}" escapeXml="false"/>" >
					   <html:img page="/pages/images/buscar_go.gif"
							altKey="NavigationTitle_CUADRO_FILTRO_CONTENIDO_SERIE"
					        titleKey="NavigationTitle_CUADRO_FILTRO_CONTENIDO_SERIE"
							styleClass="imgTextMiddle"/>&nbsp;<bean:message key="NavigationTitle_CUADRO_FILTRO_CONTENIDO_SERIE"/></a>
				</td>
				<td width="10">&nbsp;</td>
			   	<td nowrap="nowrap" class="etiquetaAzul12Bold">
					<c:url var="verContenidoSerieURL" value="/action/gestionUdocsCF">
						<c:param name="method" value="verUdocsSerie" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
			   		<a class="etiquetaAzul12Bold"
					   href="<c:out value="${verContenidoSerieURL}" escapeXml="false"/>" >
					   <html:img page="/pages/images/udocsLink.gif"
							altKey="archigest.archivo.unidadesDocumentales"
					        titleKey="archigest.archivo.unidadesDocumentales"
							styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.unidadesDocumentales"/></a>
				</td>
				</c:when>
				</c:choose>

			</TR>
			</TABLE>

		</tiles:put>
		<tiles:put name="blockContent" direct="true">

		<table class="formulario">
			<tr>
				<td width="180px" class="tdTitulo" style="vertical-align:top;">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> - <c:out value="${serie.titulo}" />
				</td>
			</tr>
		</table>
		<div class="separador5">&nbsp;</div>

		<table class="formulario">
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.gestor"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${serie.gestor.nombreCompleto}" />
				</td>
			</tr>

		</table>
	</tiles:put>
	</tiles:insert>


	<security:permissions action="${appConstants.fondosActions.GESTION_SOLICITUDES_SERIE_ACTION}">
	<div class="separador8">&nbsp;</div>

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockName" direct="true">datosGestionSerie</tiles:put>
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.datosGestionVisualizacionAlmacenamiento"/></tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<TABLE><TR>
			<TD>
				<c:url var="edicionSerieURL" value="/action/gestionSeries">
					<c:param name="method" value="editarInfoSerie" />
					<c:param name="idSerie" value="${serie.id}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${edicionSerieURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.editar"/>
				</a>
			</TD>
			</TR></TABLE>
		</tiles:put>

		<tiles:put name="dockableContent" direct="true">
			<table class="formulario">
				<tr>

					<td class="tdTitulo" width="280px">
						<bean:message key="archigest.archivo.identificador"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${serie.id}"/>
					</td>
				</tr>

				<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${serie.ordPos}" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="tdTitulo" width="250px">
						<bean:message key="archigest.archivo.cf.fichaDescAsociada"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${serie.opcionesDescripcion.fichaDescripcionSerie.nombre}"> -- </c:out>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${serie.opcionesDescripcion.volumenSerie.nombre}" > -- </c:out>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="250px">
						<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:choose>
							<c:when test="${serie.nivelAcceso == '1'}">
								<bean:message key="archivo.nivel_acceso.publico"/>
							</c:when>
							<c:when test="${serie.nivelAcceso == '2'}">
								<bean:message key="archivo.nivel_acceso.archivo"/>
							</c:when>
							<c:when test="${serie.nivelAcceso == '3'}">
								<bean:message key="archivo.nivel_acceso.restringido"/>
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose>
					</td>
				</tr>

				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.listaControlAcceso"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:url var="editarListaAccesoURL" value="/action/gestionListasAcceso">
							<c:param name="method" value="verListaAcceso" />
							<c:param name="idListaAcceso" value="${serie.listaControlAcceso.id}" />
						</c:url>
						<c:if test="${!empty serie.listaControlAcceso}">
						<a href="<c:out value="${editarListaAccesoURL}" escapeXml="false"/>"> </c:if>
							<c:out value="${serie.listaControlAcceso.nombre}"> -- </c:out>
						<c:if test="${!empty serie.listaControlAcceso}">
						</a></c:if>
					</td>
				</tr>
			</table>
			<div class="separador5">&nbsp;</div>
			<c:set var="infoFichaUDocRepEcmUDocsSerie" value="${serie.opcionesDescripcion.infoFichaUDocRepEcmUDocsSerie}" />
			<c:if test="${empty infoFichaUDocRepEcmUDocsSerie}">
				<c:set var="infoFichaUDocRepEcmUDocsSerie" value="${sessionScope[appConstants.fondos.NIVELES_FICHA_UDOC_REP_ECM]}" />
			</c:if>
			<c:if test="${!empty infoFichaUDocRepEcmUDocsSerie}">
				<bean:define id="infoUDocsSerie" name="infoFichaUDocRepEcmUDocsSerie" toScope="request"/>
			</c:if>
			<tiles:insert page="view_infoFichaUDocRepEcm.jsp"/>
		</tiles:put>
	</tiles:insert>

	</security:permissions>

	<div class="separador8">&nbsp;</div>

	<c:choose>
		<c:when test="${param.method == 'veridentificacionserie' }">
			<c:set var="classTabUdocsSerie" value="tabSel" />
			<c:set var="classTabIdentificacionSerie" value="tabActual" />
			<c:set var="classTabValoracionSerie" value="tabSel" />
		</c:when>
		<c:when test="${param.method == 'verValoracionSerieDictaminada' }">
			<c:set var="classTabUdocsSerie" value="tabSel" />
			<c:set var="classTabIdentificacionSerie" value="tabSel" />
			<c:set var="classTabValoracionSerie" value="tabActual" />
		</c:when>
		<c:otherwise>
			<c:set var="classTabUdocsSerie" value="tabActual" />
			<c:set var="classTabIdentificacionSerie" value="tabSel" />
			<c:set var="classTabValoracionSerie" value="tabSel" />
		</c:otherwise>
	</c:choose>

	<div class="cabecero_tabs">
			<table cellspacing="0" cellpadding=0>
				<tr>
			    	<td class="<c:out value="${classTabIdentificacionSerie}" />">
						<c:url var="verDescripcionSerieURL" value="/action/gestionIdentificacionAction">
							<c:param name="method" value="veridentificacionserie" />
							<c:param name="idserie" value="${serie.id}" />
						</c:url>
						<a href="<c:out value="${verDescripcionSerieURL}" escapeXml="false"/>"  class="textoPestana">
							<bean:message key="archigest.archivo.cf.identificacion"/>
						</a>
				    </td>
					<td width="5px">&nbsp;</td>
			    	<td class="<c:out value="${classTabValoracionSerie}" />">
						<c:url var="verValoracionSerieURL" value="/action/gestionValoracion">
							<c:param name="method" value="verValoracionSerieDictaminada" />
							<c:param name="idserie" value="${serie.id}" />
						</c:url>
						<a href="<c:out value="${verValoracionSerieURL}" escapeXml="false"/>"  class="textoPestana">
							<bean:message key="archigest.archivo.valoracion"/>
						</a>
				    </td>
				    <c:if test="${!empty serie.valoracionDictaminada && serie.valoracionDictaminada.valorDictamen != 1}">
					<td width="5px">&nbsp;</td>
			    	<td class="<c:out value="${classTabUdocsSerie}" />"">
						<c:url var="verUdocsAEliminarURL" value="/action/gestionEliminacion">
							<c:param name="method" value="udocsSeleccionables" />
							<c:param name="idSerie" value="${serie.id}" />
						</c:url>
						<a href="<c:out value="${verUdocsAEliminarURL}" escapeXml="false"/>" class="textoPestana">
							<bean:message key="archigest.archivo.seleccion"/>
						</a>
				    </td>
				    </c:if>
				</tr>
			</table>
	</div>
	<c:choose>
	<c:when test="${param.method == 'verValoracionSerieDictaminada'}">

		<c:set var="valoracionDictaminada" value="${sessionScope[appConstants.valoracion.VALORACION_KEY]}" />
		<c:set var="valoracionActual" value="${requestScope[appConstants.valoracion.VALORACION_ACTUAL_KEY]}" />

		<tiles:insert page="/pages/tiles/PATabBlockLayout.jsp">
			<tiles:put name="visible" direct="true">true</tiles:put>

			<tiles:put name="buttonBar" direct="true">
				<table cellpadding="0" cellspacing="0" style="right:4px"><tr>
					<c:if test="${valoracion.puedeImprimirse}">
						<security:permissions action="${appConstants.fondosActions.IMPRIMIR_VALORACION_ACTION}">
						<td width="10">&nbsp;</td>
						<td nowrap>
							<c:url var="URL" value="/action/informeValoracion">
								<c:param name="id" value="${valoracion.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold"
								href="<c:out value="${URL}" escapeXml="false"/>"
							><html:img page="/pages/images/documentos/doc_pdf.gif"
								border="0"
								altKey="archigest.archivo.informe"
								titleKey="archigest.archivo.informe"
								styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
						</td>
						<TD width="10">&nbsp;</TD>
						</security:permissions>
					</c:if>


					<c:if test="${requestScope[appConstants.valoracion.VER_BOTON_INICIAR_VALORACION]}">
					<security:permissions permission="${appConstants.permissions.GESTOR_SERIE}">
					<TD>
						<c:url var="iniciarValoracionSerieURL" value="/action/gestionValoracion">
							<c:param name="method" value="iniciarValoracionSerie" />
							<c:param name="idSerie" value="${serie.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${iniciarValoracionSerieURL}" escapeXml="false"/>" target="_self">
							<html:img page="/pages/images/new.gif" styleClass="imgTextMiddle"
								altKey="archigest.archivo.valoracion.iniValoracion" titleKey="archigest.archivo.valoracion.iniValoracion"  />
							<bean:message key="archigest.archivo.valoracion.iniValoracion"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
					</security:permissions>
					</c:if>
					<c:if test="${!empty valoracionActual}">
						<TD>
							<c:url var="verValoracionSerieURL" value="/action/gestionValoracion">
								<c:param name="method" value="verValoracion" />
								<c:param name="id" value="${valoracionActual.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${verValoracionSerieURL}" escapeXml="false"/>" target="_self">
								<html:img page="/pages/images/searchFolder.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.valoracion.verValoracion" titleKey="archigest.archivo.valoracion.verValoracion"  />
								<bean:message key="archigest.archivo.valoracion.verValoracion"/>
							</a>
						</TD>
					</c:if>
				</tr></table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<c:choose>
					<c:when test="${!empty valoracionDictaminada}">

						<div style="width:100%;padding-left:6px;padding-right:6px">
							<table class="formulario" >
								<tr>
									<td class="tdTitulo" width="300px">
										<bean:message key="archigest.archivo.valoracion.estadoValoracion"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<fmt:message key="archigest.archivo.valoracion.estado${valoracionDictaminada.estado}" />
									</td>
								</tr>
								<TR><TD>&nbsp;</TD></TR>
								<tr>
									<td class="tdTitulo" width="300px">
										<bean:message key="archigest.archivo.valoracion.fechaEvaluacion"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<fmt:formatDate value="${valoracionDictaminada.fechaEvaluacion}" pattern="${FORMATO_FECHA}"/>
									</td>
								</tr>
								<TR><TD>&nbsp;</TD></TR>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.valoracion.fechaDictamen"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<fmt:formatDate value="${valoracionDictaminada.fechaDictamen}" pattern="${FORMATO_FECHA}"/>
									</td>
								</tr>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.valoracion.fechaResolucionDictamen"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<fmt:formatDate value="${valoracionDictaminada.fechaResolucionDictamen}" pattern="${FORMATO_FECHA}"/>
									</td>
								</tr>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.valoracion.boletinDictamen"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<c:out value="${valoracionDictaminada.boletinDictamen}" />
									</td>
								</tr>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.valoracion.fechaBoletinDictamen"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<fmt:formatDate value="${valoracionDictaminada.fechaBoletinDictamen}" pattern="${FORMATO_FECHA}"/>
									</td>
								</tr>
							</table>
						</div>
						<tiles:insert template="/pages/tiles/PADockableBlockLayout.jsp">
							<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.datosValoracion"/></tiles:put>
							<tiles:put name="blockName" direct="true">datosValoracion</tiles:put>
							<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
							<tiles:put name="dockableContent" value="valoracion.datosValoracion" type="definition" />
						</tiles:insert>


						<tiles:insert template="/pages/tiles/PADockableBlockLayout.jsp">
							<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.seriesPrecedentes"/></tiles:put>
							<tiles:put name="blockName" direct="true">seriesPrecedentes</tiles:put>
							<tiles:put name="dockableContent" direct="true">
								<c:set var="seriesPrecedentes" value="${valoracionDictaminada.listaSeriesPrecedentes}" />
								<div style="padding-top:4px;padding-bottom:4px">
								<display:table name="pageScope.seriesPrecedentes"
									id="seriePrecedente"
									style="width:99%;margin-left:auto;margin-right:auto">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.valoracion.msgNoSeriesPrecedentes"/>
									</display:setProperty>
									<display:column titleKey="archigest.archivo.cf.codigo">
										<c:url var="vistaSerieURL" value="/action/gestionSeries">
											<%-- Se sustituye verserie por verDesdeFondos --%>
											<c:param name="method" value="verDesdeFondos" />
											<c:param name="id" value="${seriePrecedente.id}" />
										</c:url>
										<a href="<c:out value="${vistaSerieURL}" escapeXml="false"/>"><c:out value="${seriePrecedente.codigo}" /></a>
									</display:column>
									<display:column titleKey="archigest.archivo.cf.titulo">
										<c:out value="${seriePrecedente.titulo}" />
									</display:column>
								</display:table>
								</div>
							</tiles:put>
						</tiles:insert>
						<tiles:insert template="/pages/tiles/PADockableBlockLayout.jsp">
							<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.seriesRelacionadas"/></tiles:put>
							<tiles:put name="blockName" direct="true">seriesRelacionadas</tiles:put>
							<tiles:put name="dockableContent" direct="true">
								<c:set var="seriesRelacionadas" value="${valoracionDictaminada.listaSeriesRelacionadas}" />
								<div style="padding-top:4px;padding-bottom:4px">
								<display:table name="pageScope.seriesRelacionadas"
									id="serieRelacionada"
									style="width:99%;margin-left:auto;margin-right:auto">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.valoracion.msgNoSeriesRelacionadas"/>
									</display:setProperty>
									<display:column titleKey="archigest.archivo.cf.codigo">
										<c:url var="vistaSerieURL" value="/action/gestionSeries">
											<c:param name="method" value="verDesdeFondos" />
											<c:param name="id" value="${serieRelacionada.id}" />
										</c:url>
										<a href="<c:out value="${vistaSerieURL}" escapeXml="false"/>"><c:out value="${serieRelacionada.codigo}" /></a>
									</display:column>
									<display:column titleKey="archigest.archivo.cf.titulo">
										<c:out value="${serieRelacionada.titulo}" />
									</display:column>
								</display:table>
								</div>
							</tiles:put>
						</tiles:insert>

					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.valoracion.msgNoValoracionDicSerie"/>
					</c:otherwise>
				</c:choose>

			</tiles:put>
		</tiles:insert>

	</c:when>

	<c:when test="${param.method == 'veridentificacionserie'}">

		<c:set var="identificacionSerie" value="${sessionScope[appConstants.fondos.IDENTIFICACION_SERIE_KEY]}" />

		<tiles:insert page="/pages/tiles/PATabBlockLayout.jsp">
			<tiles:put name="visible" direct="true">true</tiles:put>
			<tiles:put name="buttonBar" direct="true">
			<table style="right:4px"><tr>
						<td width="10">&nbsp;</td>
						<td nowrap>
							<c:url var="URL" value="/action/informeIdentificacion">
								<c:param name="id" value="${serie.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold"
								href="<c:out value="${URL}" escapeXml="false"/>"
							><html:img page="/pages/images/documentos/doc_pdf.gif"
								border="0"
								altKey="archigest.archivo.informe"
								titleKey="archigest.archivo.informe"
								styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
						</td>

				<security:permissions action="${appConstants.fondosActions.SOLICITUD_CAMBIOS_IDENTIFICACION_ACTION}">
				<c:if test="${identificacionSerie.puedeSerModificada}">
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="identificarSerieURL" value="/action/gestionIdentificacionAction">
						<c:param name="method" value="inicioidentificarserie" />
						<c:param name="idserie" value="${serie.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${identificarSerieURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.cf.identificarSerie" titleKey="archigest.archivo.cf.identificarSerie" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.identificarSerie"/>
					</a>
				</TD>
				</c:if>
				</security:permissions>
				<security:permissions action="${appConstants.fondosActions.GESTION_SOLICITUDES_SERIE_ACTION}">
				<c:if test="${identificacionSerie.puedeSerRefrescada}">
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="refrescarIdentificacionURL" value="/action/gestionIdentificacionAction">
						<c:param name="method" value="refrescarIdentificacion" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
					<script>
						function actualizarIdentificacion() {
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.operacion.actualizandoIdentificacion"/>';
								var message = '<bean:message key="archigest.archivo.operacion.msgActualizandoIdentificacion"/>';
								var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
								window.top.showWorkingDiv(title, message, message2);
							}
							window.location = '<c:out value="${refrescarIdentificacionURL}" escapeXml="false"/>';
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:actualizarIdentificacion()" >
						<html:img page="/pages/images/actualizar.gif" altKey="archigest.archivo.actualizar" titleKey="archigest.archivo.actualizar" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.actualizar"/>
					</a>
				</TD>
				</c:if>
				</security:permissions>
			</tr></table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<html:form action="/gestionSeries" style="margin: 0; padding:0; ">

				<bean:define id="fechaEditable" value="true" toScope="request"/>
				<tiles:insert definition="fondos.serie.identificacion" attribute="fechaEditable" >
					<tiles:put name="fechaEditable" value="true"/>
				</tiles:insert>

			</html:form>
			</tiles:put>
		</tiles:insert>
	</c:when>
	<c:otherwise>
		<tiles:insert page="/pages/tiles/PATabBlockLayout.jsp">
			<tiles:put name="visible" direct="true">true</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table style="right:4px">
					<tr>
						<c:if test="${empty serie.seleccion}">
						<security:permissions permission="${appConstants.permissions.GESTION_ELIMINACION_ESTANDAR}|${appConstants.permissions.GESTION_ELIMINACION_TOTAL}">
						<TD width="10">&nbsp;</TD>
						<TD>
							<c:url var="iniciarSeleccionSerieURL" value="/action/gestionEliminacion">
								<c:param name="method" value="iniciarEliminacion" />
								<c:param name="idSerie" value="${serie.id}|${serie.valoracionDictaminada.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${iniciarSeleccionSerieURL}" escapeXml="false"/>" >
								<html:img page="/pages/images/new.gif"
									styleClass="imgTextMiddle"
									altKey="archigest.archivo.valoracion.iniEliminacion"
									titleKey="archigest.archivo.valoracion.iniEliminacion"  />
								<bean:message key="archigest.archivo.valoracion.iniEliminacion"/>
							</a>
						</TD>
						</security:permissions>
						</c:if>
						<c:if test="${!empty serie.seleccion}">
						<TD width="10">&nbsp;</TD>
						<TD>
							<c:url var="seleccionURL" value="/action/gestionEliminacion">
								<c:param name="method" value="verEliminacion" />
								<c:param name="id" value="${serie.seleccion.id}" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${seleccionURL}" escapeXml="false"/>" >
								<html:img page="/pages/images/searchFolder.gif"
									altKey="archigest.archivo.valoracion.verEliminacion"
									titleKey="archigest.archivo.valoracion.verEliminacion"
									styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.valoracion.verEliminacion"/>
							</a>
						</TD>
						</c:if>
					</tr>
				</table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
		<div class="separador5">&nbsp;</div>
					<c:set var="udocs" value="${sessionScope[appConstants.valoracion.LISTA_UDOCS_KEY]}"/>
		<display:table
			name="pageScope.udocs"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="udoc"
			pagesize="15"
			sort="list"
			requestURI="../../action/gestionEliminacion"
			export="false">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.cf.noUDocsSeleccion"/>
			</display:setProperty>

			<display:column titleKey="archigest.archivo.cf.codigo" sortProperty="codigo" sortable="true">

				<c:url var="verUdocURL" value="/action/gestionUdocsCF">
					<c:param name="method" value="verUnidadDocumentalWithPermissions" />
					<c:param name="id" value="${udoc.idudoc}" />
				</c:url>

				<a href="<c:out value="${verUdocURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${udoc.codigo}" />
				</a>
			</display:column>

			<display:column titleKey="archigest.archivo.cf.denominacion" >
				<c:out value="${udoc.titulo}" />
			</display:column>

		</display:table>

		<div class="separador5">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</c:otherwise>
	</c:choose>
	</tiles:put>
</tiles:insert>
<script>
	removeCookie("tabSeleccionDatos");
</script>