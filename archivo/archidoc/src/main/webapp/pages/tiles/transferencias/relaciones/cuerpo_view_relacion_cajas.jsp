<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>

		<script type="text/javascript">
			function crearCaja(url){
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
					var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
					window.top.showWorkingDiv(title, message);
				}
				goTo(url);
			}
		</script>

		<div class="cabecero_bloque_tab"> <%--cabecero segundo bloque de datos --%>
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				  <TBODY>
				  <TR>
				    <TD width="100%" align="right">
					<TABLE cellpadding="0" cellspacing="0">
					  <TR>
						<%--GENERAR CARTELAS EN ORGANO REMITENTE --%>
						<c:if test="${vRelacion.permitidaImpresionCartelasProvisionales}">
							<security:permissions action="${appConstants.transferenciaActions.IMPRESION_CARTELAS_PROVISIONALES}">
							<TD>
								<c:url var="urlImprimir" value="/action/gestionRelaciones">
									<c:param name="method" value="selCartelas"/>
								</c:url>
								<a class="etiquetaAzul12Normal" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
									<html:img titleKey="archigest.archivo.transferencias.generarCartelas" altKey="archigest.archivo.transferencias.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
									 &nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/>
								</a>
							</TD>
							<TD width="10">&nbsp;</TD>
							</security:permissions>
						</c:if>
						<%--ORGANIZAR ASIGNACION DE UNIDADES DOCUMENTALES A UNIDADES DE INSTALACION --%>
						<c:if test="${vRelacion.permitidaModificacionAsignacionCajas}">
						<c:choose>
							<c:when test="${vRelacion.isIngresoDirecto}">
								<security:permissions action="${appConstants.transferenciaActions.ELABORACION_INGRESOS_DIRECTOS}">
									<c:url var="organizarUdocsURL" value="/action/asignacionCajas">
										<c:param name="method" value="cajasRelacion" />
										<c:param name="idRelacion" value="${vRelacion.id}" />
									</c:url>
									<c:if test="${!empty infoAsignacion.partesSinAsignar}">
									<TD>
										<c:url var="asignacionCajaURL" value="/action/asignacionCajas">
											<c:param name="method" value="nuevaCaja" />
											<c:param name="idRelacion" value="${vRelacion.id}" />
											<c:param name="asignacion" value="true" />
										</c:url>
										<a class="etiquetaAzul12Normal" href="javascript:showWorkingDiv('wip');goTo('<c:out value="${asignacionCajaURL}" escapeXml="false"/>');">
											<html:img page="/pages/images/caja_new.gif"
												altKey="archigest.archivo.transferencias.asociarCaja"
												titleKey="archigest.archivo.transferencias.asociarCaja"
												styleClass="imgTextBottom"/>
											&nbsp;<bean:message key="archigest.archivo.transferencias.asociarCaja"/>
										</a>
									</TD>
									<TD width="10">&nbsp;</TD>
									<TD>
										<c:url var="creacionCajaURL" value="/action/asignacionCajas">
											<c:param name="method" value="nuevaCaja" />
											<c:param name="idRelacion" value="${vRelacion.id}" />
										</c:url>
										<a class="etiquetaAzul12Normal" href="javascript:crearCaja('<c:out value="${creacionCajaURL}" escapeXml="false"/>');">
											<html:img page="/pages/images/caja_new.gif"
												altKey="archigest.archivo.transferencias.crearCaja"
												titleKey="archigest.archivo.transferencias.crearCaja"
												styleClass="imgTextBottom"/>
											&nbsp;<bean:message key="archigest.archivo.transferencias.crearCaja"/>
										</a>
									</TD>
									</c:if>
								</security:permissions>

							</c:when>
							<c:otherwise>
								<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
								<c:url var="organizarUdocsURL" value="/action/asignacionCajas">
									<c:param name="method" value="cajasRelacion" />
									<c:param name="idRelacion" value="${vRelacion.id}" />
								</c:url>
								<TD>
									<c:if test="${!empty infoAsignacion.partesSinAsignar && not vRelacion.sinDocsFisicos}">
											<c:url var="creacionCajaURL" value="/action/asignacionCajas">
												<c:param name="method" value="nuevaCaja" />
												<c:param name="idRelacion" value="${vRelacion.id}" />
											</c:url>
											<a class="etiquetaAzul12Normal" href="javascript:crearCaja('<c:out value="${creacionCajaURL}" escapeXml="false"/>');">
												<html:img page="/pages/images/caja_new.gif"
													altKey="archigest.archivo.transferencias.crearCaja"
													titleKey="archigest.archivo.transferencias.crearCaja"
													styleClass="imgTextBottom"/>
												&nbsp;<bean:message key="archigest.archivo.transferencias.crearCaja"/>
											</a>
									</c:if>
								</TD>
							</security:permissions>
							</c:otherwise>
						</c:choose>

						</c:if>
					</TR>
					</TABLE>
					</TD>
			</TR></TBODY></TABLE>
			</div> <%--cabecero bloque tab --%>

			<%--Expedientes sin asignar --%>
			<c:if test="${!empty infoAsignacion.partesSinAsignar}">
			<c:set var="udocsSinCaja" value="${infoAsignacion.partesSinAsignar}" />

			<div class="titulo_gris_bloque">

				<table class="tablaAncho99">
					<tr>
						<td class="etiquetaAzul11Bold">
							<html:img page="/pages/images/up.gif" styleId="imgListaUdocsSinAsignar" onclick="switchDivVisibility('ListaUdocsSinAsignar')" />
							<c:choose>
							<c:when test="${vRelacion.formato.multidoc && vRelacion.nivelDocumental.subtipo != appConstants.fondos.subtiposNivel.CAJA.identificador}">
								&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.UDsinCaja" /> (<c:out value="${infoAsignacion.numUdocsSinAsignar}" />)
							</c:when>
							<c:otherwise>
								&nbsp;&nbsp;<bean:message key="archigest.archivo.unidadesDocumentales" /> (<c:out value="${infoAsignacion.numUdocsSinAsignar}" />)
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</div>

			<div isOpen="true" id="divListaUdocsSinAsignar">
				<display:table name="pageScope.udocsSinCaja" id="parteUnidadDocumental"
					sort="list"
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.show.header" value="false" />
					<c:set var="unidadDocumental" value="${parteUnidadDocumental.unidadDocumental}" />
					<display:column title="" style="text-align:right" >
						<c:out value="${parteUnidadDocumental_rowNum}" />:
					</display:column>
					<c:if test="${parteUnidadDocumental.signaturaUdoc}">
						<display:column titleKey="archigest.archivo.transferencias.signatura">
							<c:out value="${parteUnidadDocumental.signaturaUdoc}" />
						</display:column>
					</c:if>

					<display:column titleKey="archigest.archivo.transferencias.nExp">
						<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
							<c:param name="method">
							<c:choose><c:when test="${relacionEnArchivo}">infoUnidadDocumentalAGestionar</c:when><c:otherwise>infoUnidadDocumental</c:otherwise></c:choose>
							</c:param>
							<c:param name="udocID" value="${unidadDocumental.id}" />
							<c:param name="numOrden" value="${unidadDocumental.orden}"/>
						</c:url>
						<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${unidadDocumental.numeroExpediente}" />
							<c:set var="nPartesUdoc" value="${unidadDocumental.numeroPartes}" />
							<c:if test="${nPartesUdoc > 1}">
								&nbsp;(<c:out value="${parteUnidadDocumental.numParteUdoc}" />/<c:out value="${nPartesUdoc}" />)
							</c:if>
						</a>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.asunto">
						<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
							<c:param name="method">
							<c:choose><c:when test="${relacionEnArchivo}">infoUnidadDocumentalAGestionar</c:when><c:otherwise>infoUnidadDocumental</c:otherwise></c:choose>
							</c:param>
							<c:param name="udocID" value="${unidadDocumental.id}" />
							<c:param name="numOrden" value="${unidadDocumental.orden}"/>
						</c:url>
						<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${unidadDocumental.asunto}" />
						</a>
					</display:column>

				</display:table>

			</div> <%--divListaUdocsSinAsignar --%>

			<span class="separador5">&nbsp;</span>
			</c:if>

			<%--Expedientes sin documentos físicos --%>
			<c:if test="${!empty infoAsignacion.udocsSinDocumentosFisicos}">
			<c:set var="udocsSinDocsFisicos" value="${infoAsignacion.udocsSinDocumentosFisicos}" />


			<div class="titulo_gris_bloque">
				<table class="tablaAncho99">
					<tr>
						<td class="etiquetaAzul11Bold">
							&nbsp;<html:img page="/pages/images/docelectronico.gif" styleId="img1"/>
							<html:img page="/pages/images/up.gif" styleId="imgListaUdocsSinDocsFisicos" onclick="switchDivVisibility('ListaUdocsSinDocsFisicos')" />
							&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.unidades.doc.solo.documentos.electronicos" /> (<c:out value="${infoAsignacion.numUdocsSinDocumentosFisicos}" />)
							<c:if test="${infoAsignacion.docsElectronicasConErrores}">
								<html:img page="/pages/images/cajaError.gif" altKey="archigest.archivo.transferencias.cotejoConErrores" titleKey="archigest.archivo.transferencias.cotejoConErrores"/>
							</c:if>
						</td>
					</tr>
				</table>
			</div>

			<div isOpen="true" id="divListaUdocsSinDocsFisicos">
				<display:table name="pageScope.udocsSinDocsFisicos" id="udocSinDocsFisicos"
					sort="list"
					style="width:99%;margin-left:auto;margin-right:auto">

					<display:setProperty name="basic.show.header" value="false" />

					<display:column title="" style="text-align:right; width:5%;">
						<c:out value="${udocSinDocsFisicos_rowNum}" />:
					</display:column>
						<c:if test="${!empty udocSinDocsFisicos.estadoCotejo}">

							<display:column titleKey="archigest.archivo.transferencias.cotejoUDoc" style="width:2%">
								<c:choose>
									<c:when test="${udocSinDocsFisicos.estadoCotejo == appConstants.transferencias.estadoCotejo.PENDIENTE.identificador}">
										<html:img page="/pages/images/cajaPendiente.gif" altKey="archigest.archivo.transferencias.pendienteCotejo" titleKey="archigest.archivo.transferencias.pendienteCotejo"/>
									</c:when>
									<c:when test="${udocSinDocsFisicos.estadoCotejo == appConstants.transferencias.estadoCotejo.REVISADA.identificador}">
										<html:img page="/pages/images/cajaRevisada.gif" altKey="archigest.archivo.transferencias.cotejoCorrecto" titleKey="archigest.archivo.transferencias.cotejoCorrecto"/>
									</c:when>
									<c:when test="${udocSinDocsFisicos.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador}">
										<html:img page="/pages/images/cajaError.gif" altKey="archigest.archivo.transferencias.cotejoConErrores" titleKey="archigest.archivo.transferencias.cotejoConErrores"/>
									</c:when>
								</c:choose>
							</display:column>

						</c:if>

					<display:column titleKey="archigest.archivo.transferencias.nExp" style="width:30%;">
						<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
							<c:param name="method" value="infoUnidadDocumental" />
							<c:param name="udocID" value="${udocSinDocsFisicos.id}" />
							<c:param name="numOrden" value="${udocSinDocsFisicos.posicion}"/>
						</c:url>
						<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${udocSinDocsFisicos.expediente}" />
						</a>
						<c:if test="${udocSinDocsFisicos.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador && !empty udocSinDocsFisicos.notasCotejo }">
							<br/><span class="etiquetaNegra11Normal"><br/><b><i><bean:message key="archigest.archivo.transferencias.notasCotejo" />:</i></b> <c:out value="${udocSinDocsFisicos.notasCotejo}" /></span>
						</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.asunto" style="width:30%;">
						<c:out value="${udocSinDocsFisicos.asunto}" />
					</display:column>

					<c:if test="${vRelacion.conErroresCotejo}">
						<display:column style="width:50px">
							<c:if test="${udocSinDocsFisicos.estadoCotejo  != appConstants.transferencias.estadoCotejo.REVISADA.identificador}">
								<c:url var="eliminarURL" value="/action/asignacionCajas">
									<c:param name="method" value="eliminarUDocElectronica" />
									<c:param name="idUnidadDocumental" value="${udocSinDocsFisicos.id}" />
								</c:url>
								<a class="etiquetaAzul12Bold" href="javascript:eliminarRegistro('<c:out value="${udocSinDocsFisicos.expediente}" />','<c:out value="${eliminarURL}" escapeXml="false"/>');">
									<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delete_doc_electronico.gif" styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.eliminar"/>
								</a>
							</c:if>

						</display:column>
					</c:if>

				</display:table>

			</div> <%--divListaUdocsSinDocsFisicos --%>

			<span class="separador5">&nbsp;</span>
			</c:if>

			<c:set var="cajasRelacion" value="${infoAsignacion.unidadesInstalacion}" />

			<c:if test="${empty infoAsignacion.partesSinAsignar && empty infoAsignacion.unidadesInstalacion && empty udocSinDocsFisicos}">
				<c:choose>
					<c:when test="${vRelacion.isIngresoDirecto}">
						<c:choose>
							<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
								<bean:message key="archigest.archivo.fondos.ingresoDirectoSinFraccionesSerie" />
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.fondos.ingresoDirectoSinUnidadesDocumentales" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
								<bean:message key="archigest.archivo.transferencias.relacionSinFraccionesSerie" />
							</c:when>
							<c:otherwise>
								<c:if test="${not vRelacion.sinDocsFisicos}">
									<bean:message key="archigest.archivo.transferencias.relacionSinUnidadesDocumentales" />
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:if>

			<%--Expedientes asignados: Cajas  --%>

<script>
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



</script>
			<c:if test="${(!empty vRelacion.numeroUnidadesInstalacion && vRelacion.numeroUnidadesInstalacion > 0) ||
						  (!empty vRelacion.numeroUnidadesInstalacionAsignadas && vRelacion.numeroUnidadesInstalacionAsignadas > 0)}">
				<div class="titulo_gris_bloque">
				<table class="tablaAncho99">
					<tr>
						<td width="50%" class="etiquetaAzul11Bold">
							<html:img page="/pages/images/caja_closed.gif" styleId="imgListaUdocsSinAsignar"/>
							<html:img page="/pages/images/pixel.gif" width="15" styleId="imgListaUdocsSinAsignar"/>
							&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.numUnidInstalacionNuevas"/>:&nbsp;
							<c:out value="${vRelacion.numeroUnidadesInstalacion}"/>
						</td>
						<c:if test="${vRelacion.isIngresoDirecto && vRelacion.nivelDocumental.subtipo != appConstants.fondos.subtiposNivel.CAJA.identificador && vRelacion.formato.multidoc}">
							<td width="50%" class="etiquetaAzul11Bold">
								<html:img page="/pages/images/pixel.gif" width="15" styleId="imgListaUdocsSinAsignar"/>
								&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.numUnidInstalacionAsignadas"/>:&nbsp;
								<c:out value="${vRelacion.numeroUnidadesInstalacionAsignadas}"/>
							</td>
						</c:if>
					</tr>
				</table>
				</div>
			</c:if>

			<c:set var="gestionRelacionOrganoRemitente" value="false"/>
			<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
				<c:set var="gestionRelacionOrganoRemitente" value="true"/>
			</security:permissions>

			<c:set var="mostrarTexto" value="true"/>
			<c:forEach items="${cajasRelacion}" var="unidadInstalacion" varStatus="nCajas" >
				<c:set var="cajaPanelName" value="Caja${nCajas.count}" />
				<jsp:useBean id="cajaPanelName" type="java.lang.String" />

				<c:if test="${(vRelacion.formato.multidoc && vRelacion.nivelDocumental.subtipo != appConstants.fondos.subtiposNivel.CAJA.identificador) || (!vRelacion.formato.multidoc && unidadInstalacion.conErrores)}">

					<c:if test="${mostrarTexto && !vRelacion.formato.multidoc && (vRelacion.conErroresCotejo || vRelacion.corregidaErrores)}">
						<div class="titulo_gris_bloque">
						<table class="tablaAncho99">
							<tr>
								<td width="50%" class="etiquetaAzul11Bold">
								<html:img page="/pages/images/cajaError.gif" styleId="imgListaUdocsSinAsignar"/>
								&nbsp;<bean:message key="archigest.archivo.transferencias.estadoRelacion.5"/>
								</td>
							</tr>
						</table>
						</div>
						<c:set var="mostrarTexto" value="false"/>

					</c:if>

				<div class="titulo_gris_bloque">
					<c:set var="panelControlImg" value="img${cajaPanelName}" />
					<jsp:useBean id="panelControlImg" type="java.lang.String" />
					<c:set var="panelVisibilityCommand" value="switchDivVisibility('${cajaPanelName}')" />
					<jsp:useBean id="panelVisibilityCommand" type="java.lang.String" />
					<table class="w98" cellpadding="0" cellspacing="0"><tr>
					<td width="40%" class="etiquetaAzul12Normal">
						<html:img page="/pages/images/down.gif" styleId="<%=panelControlImg%>" onclick="<%=panelVisibilityCommand%>" styleClass="imgTextMiddle"/>
						&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.caja" />&nbsp;<c:out value="${nCajas.count}" />

						<c:choose>
							<c:when test="${unidadInstalacion.conErrores}">
								&nbsp;&nbsp;<html:img page="/pages/images/cajaError.gif" border="0" altKey="archigest.archivo.transferencias.cajaConErrores" titleKey="archigest.archivo.transferencias.cajaConErrores" styleClass="imgTextMiddle"/>
							</c:when>
							<c:otherwise>
								&nbsp;&nbsp;<html:img page="/pages/images/pixel.gif" width="15" border="0" altKey="archigest.archivo.transferencias.cajaConErrores" titleKey="archigest.archivo.transferencias.cajaConErrores" styleClass="imgTextMiddle"/>
							</c:otherwise>
						</c:choose>
						<c:choose>
						<c:when test="${unidadInstalacion.devolver}">
							&nbsp;&nbsp<html:img page="/pages/images/cajaDevuelta.gif" border="0" altKey="archigest.archivo.transferencias.devolver" titleKey="archigest.archivo.transferencias.devolver" styleClass="imgTextMiddle"/>
						</c:when>
							<c:otherwise>
								&nbsp;&nbsp;<html:img page="/pages/images/pixel.gif" width="26" border="0" altKey="archigest.archivo.transferencias.cajaConErrores" titleKey="archigest.archivo.transferencias.cajaConErrores" styleClass="imgTextMiddle"/>
							</c:otherwise>
						</c:choose>
						&nbsp;&nbsp;
							<bean-el:message key="archigest.archivo.caja.contiene.unidades.documentales" arg0="${unidadInstalacion.udocCount}"/>
					</td>
					<td width="15%" class="etiquetaAzul11Normal">
						<c:if test="${!empty unidadInstalacion.signaturaUI}">
							<b><bean:message key="archigest.archivo.transferencias.signatura" />:</b>
							&nbsp;<c:out value="${unidadInstalacion.signaturaUI}" />
						</c:if>
						&nbsp;
					</td>
					<c:set var="cajaOrganizable" value="false"/>
					<c:if test="${!unidadInstalacion.revisada}">
						<c:set var="cajaOrganizable" value="true"/>
					</c:if>
					<c:choose>
					<c:when test="${vRelacion.permitidaModificacionAsignacionCajas && cajaOrganizable}">
							<td width="40%" style="text-align:right;" class="etiquetaAzul11Normal">

							<c:choose>
								<c:when test="${vRelacion.isIngresoDirecto}">
									<security:permissions action="${appConstants.transferenciaActions.ELABORACION_INGRESOS_DIRECTOS}">
										<table cellpadding="0" cellspacing="0"><tr>

										<td>
											<c:if test="${vRelacion.signaturaSolictableEnUI}">
											<c:url var="editarSignaturaURL" value="/action/asignacionCajas">
												<c:param name="method" value="editarSignaturaCaja" />
												<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
											</c:url>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarSignaturaURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.transferencias.editarSignatura"
												altKey="archigest.archivo.transferencias.editarSignatura" page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"/>
												<bean:message key="archigest.archivo.transferencias.editarSignatura"/>
											</a>
											</c:if>
											&nbsp;
										</td>

										<TD width="10">&nbsp;</TD>
										<td>
											<c:url var="editarURL" value="/action/asignacionCajas">
												<c:param name="method" value="editarCaja" />
												<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
												<c:param name="ordenCaja" value="${nCajas.count}"/>
											</c:url>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.transferencias.organizarCajas" altKey="archigest.archivo.transferencias.organizarCajas" page="/pages/images/caja_open.gif" styleClass="imgTextMiddle"/>
												<bean:message key="archigest.archivo.transferencias.organizarCajas"/>
											</a>
										</td>

											<TD width="10">&nbsp;</TD>

												<TD>
												<c:if test="${unidadInstalacion.borrable}">
													<c:url var="eliminarURL" value="/action/asignacionCajas">
														<c:param name="method" value="eliminarCaja" />
														<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
													</c:url>
													<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${eliminarURL}" escapeXml="false"/>'">
														<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/caja_delete.gif" styleClass="imgTextMiddle"/>
														&nbsp;<bean:message key="archigest.archivo.eliminar"/>
													</a>
												</c:if>	&nbsp;
												</TD>

										</tr></table>
									</security:permissions>
								</c:when>
								<c:otherwise>
									<c:if test="${gestionRelacionOrganoRemitente=='true'}">
										<table cellpadding="0" cellspacing="0"><tr>

										<td>
											<c:if test="${vRelacion.signaturaSolictableEnUI}">
											<c:url var="editarSignaturaURL" value="/action/asignacionCajas">
												<c:param name="method" value="editarSignaturaCaja" />
												<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
											</c:url>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarSignaturaURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.transferencias.editarSignatura"
												altKey="archigest.archivo.transferencias.editarSignatura" page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"/>
												<bean:message key="archigest.archivo.transferencias.editarSignatura"/>
											</a>
											</c:if>
											&nbsp;
										</td>

										<TD width="10">&nbsp;</TD>
										<td>
											<c:url var="editarURL" value="/action/asignacionCajas">
												<c:param name="method" value="editarCaja" />
												<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
												<c:param name="ordenCaja" value="${nCajas.count}"/>
												<c:param name="idRelacion" value="${vRelacion.id}"/>
											</c:url>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.transferencias.organizarCajas" altKey="archigest.archivo.transferencias.organizarCajas" page="/pages/images/caja_open.gif" styleClass="imgTextMiddle"/>
												<bean:message key="archigest.archivo.transferencias.organizarCajas"/>
											</a>
										</td>

											<TD width="10">&nbsp;</TD>

												<TD>
												<c:if test="${unidadInstalacion.borrable}">
													<c:url var="eliminarURL" value="/action/asignacionCajas">
														<c:param name="method" value="eliminarCaja" />
														<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
													</c:url>
													<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${eliminarURL}" escapeXml="false"/>'">
														<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/caja_delete.gif" styleClass="imgTextMiddle"/>
														&nbsp;<bean:message key="archigest.archivo.eliminar"/>
													</a>
												</c:if>	&nbsp;
												</TD>

										</tr></table>
									</c:if>
								</c:otherwise>
							</c:choose>
						</td>
					</c:when>
					<c:otherwise>
						<c:if test="${!unidadInstalacion.conErrores}">
							<td colspan="5">&nbsp;</td>
						</c:if>
						<c:if test="${unidadInstalacion.conErrores}">
							<c:choose>
								<c:when test="${vRelacion.conErroresCotejo && vRelacion.formato.multidoc}">
									<td width="40%" style="text-align:right;" class="etiquetaAzul11Normal">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<c:url var="URL" value="/action/gestionUdocsRelacion">
														<c:param name="method" value="editarDescripcionContenidoCaja"/>
														<c:param name="idUInstalacion" value="${unidadInstalacion.id}"/>
													</c:url>
													<a class="etiquetaAzul11Normal" href='<c:out value="${URL}" escapeXml="false"/>' >
														 <html:img page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"
															altKey="archigest.archivo.descripcion" titleKey="archigest.archivo.descripcion"/>
														<bean:message key="archigest.archivo.descripcion"/>
													</a>
												</td>
											</tr>
										</table>
									</td>
								</c:when>
								<c:otherwise>
									<td colspan="5">&nbsp;</td>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:otherwise>
					</c:choose>
				</tr></table>
				</div>

				<c:if test="${!empty unidadInstalacion.notasCotejo}">
					<div class="titulo_gris_bloque">
						<html:img page="/pages/images/pixel.gif" width="20"/>
							<b><bean:message key="archigest.archivo.transferencias.observaciones" />
						</b>:&nbsp;<c:out value="${unidadInstalacion.notasCotejo}" />
					</div>
				</c:if>

			</c:if><%--is multidoc --%>

				<div isOpen="false" id="div<%=cajaPanelName%>" style="display:none;">
					<c:set var="udocs" value="${unidadInstalacion.udocs}" />

					<display:table name="pageScope.udocs" id="parteUnidadDocumental"
						style="width:99%;margin-left:auto;margin-right:auto">

						<c:set var="unidadDocumental" value="${parteUnidadDocumental.unidadDocumental}" />

						<display:setProperty name="basic.show.header" value="false" />

						<display:setProperty name="basic.msg.empty_list">
							<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
								&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
							</div>
						</display:setProperty>

						<c:if test="${vRelacion.formato.multidoc && vRelacion.nivelDocumental.subtipo != appConstants.fondos.subtiposNivel.CAJA.identificador}">
							<display:column title="" style="width:30px;text-align:right;">
								<c:out value="${parteUnidadDocumental_rowNum}" />:
							</display:column>
						</c:if>

						<c:if test="${!empty parteUnidadDocumental.estadoCotejo}">

							<display:column titleKey="archigest.archivo.transferencias.cotejoUDoc" style="width:2%">
								<c:choose>
									<c:when test="${parteUnidadDocumental.estadoCotejo == appConstants.transferencias.estadoCotejo.PENDIENTE.identificador}">
										<html:img page="/pages/images/cajaPendiente.gif" altKey="archigest.archivo.transferencias.pendienteCotejo" titleKey="archigest.archivo.transferencias.pendienteCotejo"/>
									</c:when>
									<c:when test="${parteUnidadDocumental.estadoCotejo == appConstants.transferencias.estadoCotejo.REVISADA.identificador}">
										<html:img page="/pages/images/cajaRevisada.gif" altKey="archigest.archivo.transferencias.cotejoCorrecto" titleKey="archigest.archivo.transferencias.cotejoCorrecto"/>
									</c:when>
									<c:when test="${parteUnidadDocumental.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador}">
										<html:img page="/pages/images/cajaError.gif" altKey="archigest.archivo.transferencias.cotejoConErrores" titleKey="archigest.archivo.transferencias.cotejoConErrores"/>
									</c:when>
								</c:choose>
							</display:column>

						</c:if>
						<display:column titleKey="archigest.archivo.transferencias.nExp" style="width:13%">
							<c:out value="${unidadDocumental.numeroExpediente}" />
							<c:set var="nPartesUdoc" value="${unidadDocumental.numeroPartes}" />
							<c:if test="${nPartesUdoc > 1}">
								&nbsp;(<c:out value="${parteUnidadDocumental.numParteUdoc}" />/<c:out value="${nPartesUdoc}" />)
							</c:if>
						</display:column>

						<display:column titleKey="archigest.archivo.transferencias.asunto">
							<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
								<c:param name="method">
								<c:choose><c:when test="${relacionEnArchivo}">infoUnidadDocumentalAGestionar</c:when><c:otherwise>infoUnidadDocumental</c:otherwise></c:choose>
								</c:param>
								<c:param name="udocID" value="${unidadDocumental.id}" />
								<c:param name="numOrden" value="${unidadDocumental.orden}"/>
							</c:url>
							<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
								<c:out value="${unidadDocumental.asunto}" />
							</a>

							<c:if test="${!empty parteUnidadDocumental.descContenido}">
								<br><span class="etiquetaNegra11Normal"><br><b><i><bean:message key="archigest.archivo.transferencias.descContenido"/>:</i></b> <c:out value="${parteUnidadDocumental.descContenido}" /></span>
							</c:if>

							<c:if test="${parteUnidadDocumental.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador && !empty parteUnidadDocumental.notasCotejo}">
								<br/><span class="etiquetaNegra11Normal"><br/><b><i><bean:message key="archigest.archivo.transferencias.notasCotejo"/>:</i></b> <c:out value="${parteUnidadDocumental.notasCotejo}" /></span>
							</c:if>
						</display:column>
						<c:if test="${unidadInstalacion.revisada || (vRelacion.conSignatura
							&& (!vRelacion.formato.multidoc || vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador))}">
							<display:column titleKey="archigest.archivo.transferencias.signatura" style="width:15%">
								<span class="etiquetaAzul11Normal"><b><bean:message key="archigest.archivo.transferencias.signatura" />:</b>&nbsp;
								<c:out value="${parteUnidadDocumental.signaturaUdoc}" /></span>
							</display:column>
						</c:if>

					</display:table>

				</div> <%--divcajaPanelNameN--%>
				<span class="separador1"></span>
			</c:forEach>
			<span class="separador5">&nbsp;</span>