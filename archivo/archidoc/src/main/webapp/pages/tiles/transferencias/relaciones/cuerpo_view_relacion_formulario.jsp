<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<html:form action="/gestionUdocsRelacion" >
	<input type="hidden" name="method" value="eliminarUdocs">
	<bean:struts id="mappingGestionUdocs" mapping="/gestionUdocsRelacion" />
		<div class="cabecero_bloque_tab"> <%-- cabecero segundo bloque de datos --%>
			<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				<TBODY>
				  <TR>
				    <TD width="100%" align="right">
					<TABLE cellpadding="0" cellspacing="0">
					  <TR>
					  	<c:choose>
						  	<c:when test="${vRelacion.isIngresoDirecto}">
						  		<security:permissions action="${appConstants.transferenciaActions.ELABORACION_INGRESOS_DIRECTOS}">
						  			<c:if test="${vRelacion.permitidoBloqueoDesbloqueoExpedientes}">
									<TD>
										<a class="etiquetaAzul12Normal" href="javascript:lockSelectedItem('<c:out value="${mappingGestionUdocs.name}" />')">
											<html:img titleKey="archigest.archivo.bloquear" altKey="archigest.archivo.bloquear" page="/pages/images/udocBloquear.gif" styleClass="imgTextMiddle"/>
											&nbsp;<bean:message key="archigest.archivo.bloquear"/>
										</a>
									</TD>
								   <TD width="10">&nbsp;</TD>
									<TD>
										<a class="etiquetaAzul12Normal" href="javascript:unlockSelectedItem('<c:out value="${mappingGestionUdocs.name}" />')">
											<html:img titleKey="archigest.archivo.desbloquear" altKey="archigest.archivo.desbloquear" page="/pages/images/udocDesbloquear.gif" styleClass="imgTextMiddle"/>
											&nbsp;<bean:message key="archigest.archivo.desbloquear"/>
										</a>
									</TD>
									</c:if>
								   <TD width="10">&nbsp;</TD>
									<c:if test="${vRelacion.permitidaImportacionExpedientes}">
										<c:url var="importUdocsURL" value="/action/gestionUdocsRelacion?method=formImportUdocs" />
										<TD>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${importUdocsURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.importar" altKey="archigest.archivo.importar" page="/pages/images/importar.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.importar"/>
											</a>
										</TD>
									   <TD width="10">&nbsp;</TD>
									</c:if>
							  		<c:if test="${vRelacion.permitidaAdicionExpedientes}">
										<c:url var="addUdocURL" value="/action/gestionUdocsRelacion?method=addUdocs" />
										<TD>
											<a class="etiquetaAzul12Normal" href="javascript:addUdocSelectedItem('<c:out value="${mappingGestionUdocs.name}" />')">
												<html:img titleKey="archigest.archivo.transferencias.anadirExps" altKey="archigest.archivo.transferencias.anadirExps" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.anadir"/>
											</a>
										</TD>
									   <TD width="10">&nbsp;</TD>
									</c:if>
									<c:if test="${vRelacion.permitidaSustraccionExpedientes}">
									   <TD>
											<a class="etiquetaAzul12Normal" href="javascript:eliminarSelectedItems('<c:out value="${mappingGestionUdocs.name}" />')">
												<html:img titleKey="archigest.archivo.transferencias.delExps" altKey="archigest.archivo.transferencias.delExps" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.eliminar"/>
											</a>
									   </TD>
									</c:if>
						  		</security:permissions>
						   	</c:when>
						  	<c:when test="${vRelacion.sinDocsFisicos}">
								<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
							  		<c:if test="${vRelacion.permitidaAdicionExpedientes}">
										<c:url var="addUdocURL" value="/action/gestionUdocsRelacion?method=addUdocs" />
										<TD>
											<a class="etiquetaAzul12Normal" href="javascript:addUdocSelectedItem('<c:out value="${mappingGestionUdocs.name}" />')">
												<html:img titleKey="archigest.archivo.transferencias.anadirExps" altKey="archigest.archivo.transferencias.anadirExps" page="/pages/images/add_doc_electronico.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.anadir"/>
											</a>
										</TD>
									   <TD width="10">&nbsp;</TD>
									</c:if>
								</security:permissions>


						  	</c:when>
						  	<c:otherwise>
								<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
									<c:if test="${vRelacion.permitidaImportacionExpedientes}">
										<c:url var="importUdocsURL" value="/action/gestionUdocsRelacion?method=formImportUdocs" />
										<TD>
											<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${importUdocsURL}" escapeXml="false"/>'">
												<html:img titleKey="archigest.archivo.importar" altKey="archigest.archivo.importar" page="/pages/images/importar.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.importar"/>
											</a>
										</TD>
									   <TD width="10">&nbsp;</TD>
									</c:if>
							  		<c:if test="${vRelacion.permitidaAdicionExpedientes}">
										<c:url var="addUdocURL" value="/action/gestionUdocsRelacion?method=addUdocs" />
										<TD>
											<a class="etiquetaAzul12Normal" href="javascript:addUdocSelectedItem('<c:out value="${mappingGestionUdocs.name}" />')">
												<html:img titleKey="archigest.archivo.transferencias.anadirExps" altKey="archigest.archivo.transferencias.anadirExps" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.anadir"/>
											</a>
										</TD>
									   <TD width="10">&nbsp;</TD>
									</c:if>
									<c:if test="${vRelacion.permitidaSustraccionExpedientes}">
									   <TD>
											<a class="etiquetaAzul12Normal" href="javascript:eliminarSelectedItems('<c:out value="${mappingGestionUdocs.name}" />')">
												<html:img titleKey="archigest.archivo.transferencias.delExps" altKey="archigest.archivo.transferencias.delExps" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.eliminar"/>
											</a>
									   </TD>
									</c:if>
								</security:permissions>
							</c:otherwise>
						</c:choose>
				     </TR>
					</TABLE>
					</TD>
			</TR></TBODY></TABLE>
		</div> <%-- cabecero bloque tab --%>

		<c:if test="${!empty EXPEDIENTES_DUPLICADOS}">
		<div class="titulo_left_bloque">
			<bean:message key="archigest.archivo.expedientes.ya.insertados.en.relacion"/>
		</div>
		<table class="its" style="width:90%;margin-left:auto;margin-right:auto">
			<c:forEach var="expedienteDuplicado" items="${EXPEDIENTES_DUPLICADOS}">
				<tr class="odd">
					<td width="50px"><c:out value="${expedienteDuplicado.codigo}" /></td>
					<td><c:out value="${expedienteDuplicado.asunto}" /></td>
				</tr>
			</c:forEach>
		</table>
		</c:if>

		<c:set var="udocsRelacion" value="${infoAsignacion.udocsRelacion}" />
		<c:set var="nofUdocsRelacion" value="${infoAsignacion.nofUdocsRelacion}" />
		<c:set var="bRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
		<c:set var="estadoAbierta"><c:out value="${appConstants.transferencias.estadoREntrega.ABIERTA.identificador}"/></c:set>


		<c:if test="${!empty udocsRelacion && bRelacion.puedeSerModificada}">

					<TABLE cellpadding=0 cellspacing=0 class="w100">
					  <TR>
					   <TD align="right">
						   <div class="separador8">&nbsp;</div>
							<a class="etiquetaAzul12Normal" href="javascript:selectedAllElements('<c:out value="${mappingGestionUdocs.name}"/>', 'selectAll');seleccionarCheckboxSet(document.forms[0].selectedUdoc);" >
								<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
								<bean:message key="archigest.archivo.selTodos"/>&nbsp;
							</a>&nbsp;
							<a class="etiquetaAzul12Normal" href="javascript:selectedAllElements('<c:out value="${mappingGestionUdocs.name}"/>', 'unselectAll');deseleccionarCheckboxSet(document.forms[0].selectedUdoc);" >
								<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
								&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
							</a>&nbsp;&nbsp;
						   <div class="separador8">&nbsp;</div>
					   </TD>
					 </TR>
					</TABLE>

			</c:if>

		<c:if test="${!empty udocsRelacion && !empty nofUdocsRelacion}">
				<table class="tablaAncho99">
					<tr class="filaAlineadaDerecha">
						<td class="etiquetaAzul11Bold">
							<c:out value="${nofUdocsRelacion}"/>&nbsp;
							<c:choose>
							<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
								<bean:message key="archigest.archivo.fraccionesSerie"/>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.unidadesDocumentales"/>
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
		</c:if>

		<c:url var="vistaRelacionPaginationURI" value="/action/gestionRelaciones">
			<c:param name="method" value="${param.method}" />
			<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
		</c:url>
		<jsp:useBean id="vistaRelacionPaginationURI" type="java.lang.String" />

		<input type="hidden" id="udocBloqueadas" name="udocBloqueadas"/>
		<input type="hidden" id="allUdocsBloqueadas" name="allUdocsBloqueadas"/>
		<display:table name="pageScope.udocsRelacion" id="unidadDocumental"
			style="width:99%;margin-left:auto;margin-right:auto"
			sort="list"
			pagesize="0"
			requestURI="<%=vistaRelacionPaginationURI%>" export="true">

			<display:setProperty name="basic.msg.empty_list">
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
								<bean:message key="archigest.archivo.transferencias.relacionSinUnidadesDocumentales" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</display:setProperty>

			<c:if test="${vRelacion.permitidaSustraccionExpedientes}">
				<display:column style="width:20px" headerClass="minusDocIcon" media="html">
					<c:if test="${unidadDocumental.estadoCotejo != appConstants.transferencias.estadoCotejo.REVISADA.identificador}">
						<html-el:multibox property="selectedUdoc" value="${unidadDocumental.id}"
						onclick="javascript:marcarBloqueo('${mappingGestionUdocs.name}',this,'${unidadDocumental.marcasBloqueo}');"/>
					</c:if>
				</display:column>
			</c:if>

			<display:column titleKey="archigest.archivo.transferencias.num" sortProperty="orden" headerClass="sortable" sortable="true" >
				<c:out value="${unidadDocumental_rowNum}"/>
			</display:column>

			<display:column titleKey="archigest.archivo.transferencias.nExp" sortProperty="numeroExpediente" sortable="true" headerClass="sortable" media="html">
				<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
					<c:param name="method" value="infoUnidadDocumental" />
					<c:param name="udocID" value="${unidadDocumental.id}" />
					<c:param name="numOrden" value="${unidadDocumental.orden}"/>
				</c:url>

				<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${unidadDocumental.numeroExpediente}" />
				</a>
				<c:choose>
					<c:when test="${unidadDocumental.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador}">
						&nbsp;&nbsp;<html:img page="/pages/images/cajaError.gif" altKey="archigest.archivo.transferencias.cotejoConErrores" titleKey="archigest.archivo.transferencias.cotejoConErrores"/>
					</c:when>
				</c:choose>

			</display:column>

			<display:column titleKey="archigest.archivo.transferencias.nExp" sortProperty="numeroExpediente" sortable="true" headerClass="sortable" media="csv excel xml pdf">
				<c:out value="${unidadDocumental.numeroExpediente}" />
			</display:column>

			<display:column titleKey="archigest.archivo.transferencias.asunto" media="html">
				<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
					<c:param name="method" value="infoUnidadDocumental" />
					<c:param name="udocID" value="${unidadDocumental.id}" />
					<c:param name="numOrden" value="${unidadDocumental.orden}"/>
				</c:url>
				<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
					<c:out value="${unidadDocumental.asunto}" />
				</a>
			</display:column>

			<display:column titleKey="archigest.archivo.transferencias.asunto" headerClass="sortable" media="csv excel xml pdf">
				<c:out value="${unidadDocumental.asunto}" />
			</display:column>

			<display:column titleKey="archigest.archivo.fInicio">
				<fmt:formatDate value="${unidadDocumental.fechaInicio}" pattern="${FORMATO_FECHA}" />
			</display:column>
			<display:column titleKey="archigest.archivo.fFin">
				<fmt:formatDate value="${unidadDocumental.fechaFin}" pattern="${FORMATO_FECHA}" />
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.interesadoPrincipal" media="html">
				<c:set var="interesadoPrincipal" value="${unidadDocumental.interesadoPrincipal}" />
				<c:if test="${!empty interesadoPrincipal}">
				<c:choose>
					<c:when test="${interesadoPrincipal.validado}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
					<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
				</c:choose>
				<c:out value="${interesadoPrincipal.nombre}" />
				</c:if>
			</display:column>

			<display:column titleKey="archigest.archivo.transferencias.interesadoPrincipal" media="csv excel xml pdf">
				<c:set var="interesadoPrincipal" value="${unidadDocumental.interesadoPrincipal}" />
				<c:if test="${!empty interesadoPrincipal}">
					<c:out value="${interesadoPrincipal.nombre}" /><c:choose><c:when test="${interesadoPrincipal.validado}"> (<bean:message key="archivo.estado.validacion.validado"/>)</c:when><c:otherwise> (<bean:message key="archivo.estado.validacion.no_validado"/>)</c:otherwise></c:choose>
				</c:if>
			</display:column>

			<c:if test="${not vRelacion.sinDocsFisicos}">
			<display:column titleKey="archigest.archivo.transferencias.caja">
				<c:choose>
					<c:when test="${!empty unidadDocumental.uiOcupadas}" >
						<c:out value="${unidadDocumental.uiOcupadas}" />
					</c:when>
					<c:otherwise>
						<c:out value="-" />
					</c:otherwise>
				</c:choose>
			</display:column>
			</c:if>
			<display:column titleKey="archigest.archivo.transferencias.docs" style="text-align: center;"  media="html">
				<c:choose>
					<c:when test="${unidadDocumental.tieneDocumentosFisicosYElectronicos}" >
						<html:img
							page="/pages/images/docmixto.gif"
							titleKey="archigest.archivo.transferencias.documentos.mixtos"
							altKey="archigest.archivo.transferencias.documentos.mixtos"
							styleClass="imgTextBottom" />
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${unidadDocumental.tieneDocumentosFisicos}" >
								<html:img
									page="/pages/images/docfisico.gif"
									titleKey="archigest.archivo.transferencias.documentos.fisicos"
									altKey="archigest.archivo.transferencias.documentos.fisicos"
									styleClass="imgTextBottom" />
							</c:when>
							<c:otherwise>
								<html:img
									page="/pages/images/docelectronico.gif"
									titleKey="archigest.archivo.transferencias.documentos.electronicos"
									altKey="archigest.archivo.transferencias.documentos.electronicos"
									styleClass="imgTextBottom" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

			</display:column>
			<c:if test="${vRelacion.permitidoMostrarBloqueoDesbloqueoExpedientes}">
			<display:column style="text-align: center;"  media="html">
				<c:choose>
					<c:when test="${unidadDocumental.marcasBloqueo>0}" >
						<html:img
							page="/pages/images/udocBloqueada.gif"
							titleKey="archigest.archivo.bloqueada"
							altKey="archigest.archivo.bloqueada"
							styleClass="imgTextBottom" />
					</c:when>
					<c:otherwise>
						<html:img
							page="/pages/images/udocDesbloqueada.gif"
							titleKey="archigest.archivo.desbloqueada"
							altKey="archigest.archivo.desbloqueada"
							styleClass="imgTextBottom" />
					</c:otherwise>
				</c:choose>
			</display:column>

			<display:column titleKey="archigest.archivo.bloqueada" media="csv excel xml pdf">
				<c:choose>
					<c:when test="${unidadDocumental.marcasBloqueo>0}" >
						<bean:message key="archigest.archivo.si" />
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.no" />
					</c:otherwise>
				</c:choose>
			</display:column>
			</c:if>
		</display:table>

		<script>
			if (typeof tabPanel != 'undefined') {
				tabPanel.showTab(getCookie("tabsUdocsRelacion"));
			}

			<c:forEach var="udoc" items="${udocsRelacion}" varStatus="index">
				<c:if test="${udoc.marcasBloqueo > 0}">
					var elementos = document.getElementById('allUdocsBloqueadas').value;
					if(elementos == ''){
						document.getElementById('allUdocsBloqueadas').value = '<c:out value="${udoc.id}"/>';
					}
					if(elementos != ''){
						document.getElementById('allUdocsBloqueadas').value = document.getElementById('allUdocsBloqueadas').value + SEPARATOR + '<c:out value="${udoc.id}"/>';
					}
				</c:if>
			</c:forEach>
		</script>

	</html:form>