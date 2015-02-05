<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

	<bean:struts id="actionMapping" mapping="/gestionRelaciones" />

	<input type="hidden" id="udocBloqueadas" name="udocBloqueadas"/>

				<div class="separador8">&nbsp;</div>

				<c:url var="vistaRelacionPaginationURI" value="/action/gestionRelaciones">
					<c:param name="method" value="${param.method}" />
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
				</c:url>
				<jsp:useBean id="vistaRelacionPaginationURI" type="java.lang.String" />
				<script>
					var tabPanel = new TabPanel("tabsUdocsRelacion");
					<c:if test="${!vRelacion.conReencajadoValidada}">
						tabPanel.addTab(new Tab("tabCajasRelacion", "tabCajasRelacion"));
					</c:if>
					tabPanel.addTab(new Tab("tabUdocsRelacion", "tabUdocsRelacion"));
					tabPanel.addTab(new Tab("tabReencajado", "tabReencajado"));

					var SEPARATOR = "@@";
					function marcarBloqueo(selectionFormName, elemento, marcaBloqueo){
						var selectionForm = document.forms[selectionFormName];
						if(selectionForm) {
							if(elemento.checked){
								if(marcaBloqueo > 0){
									if(selectionForm.udocBloqueadas.value == ''){
										selectionForm.udocBloqueadas.value = elemento.value;
									}else{
										selectionForm.udocBloqueadas.value = selectionForm.udocBloqueadas.value + SEPARATOR + elemento.value;
									}
								}
							}else{
								if(marcaBloqueo > 0){
									var bloqueadas = selectionForm.udocBloqueadas.value.split(SEPARATOR);
									if(bloqueadas.length > 0){
										for(var i=0; i<bloqueadas.length;i++){
											if(bloqueadas[i] == elemento.value){
												bloqueadas[i] = '';
											}
										}
										selectionForm.udocBloqueadas.value = '';
										for(var i=0; i<bloqueadas.length;i++){
											if(bloqueadas[i] != '')
												selectionForm.udocBloqueadas.value = selectionForm.udocBloqueadas.value + bloqueadas[i] + SEPARATOR;
										}
									}
								}
							}
						}
					}

					function lockSelectedItem(selectionFormName) {
						var selectionForm = document.forms[selectionFormName];
						if (selectionForm && selectionForm.elementosUdocSeleccionados) {
							var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"elementosUdocSeleccionados");
							if(nSelected >= 1){
								if(selectionForm.udocBloqueadas.value != ''){
									var bloqueadas = selectionForm.udocBloqueadas.value.split(SEPARATOR);
									if(bloqueadas.length > 0)
										alert("<bean:message key='errors.relaciones.noPermitirBloquearUdocBloqueadas'/>");
								}else{
								selectionForm.method.value='lockUdocs';
								selectionForm.submit();
								}
							} else {
								alert("<bean:message key='archigest.archivo.transferencias.selUDocLock'/>");
							}
						}else
							alert("<bean:message key='archigest.archivo.transferencias.selUDocLock'/>");
						}

					function unlockSelectedItem(selectionFormName) {
						var selectionForm = document.forms[selectionFormName];
						if (selectionForm && selectionForm.elementosUdocSeleccionados) {
							var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"elementosUdocSeleccionados");
							if(nSelected >= 1){
								if((selectionForm.udocBloqueadas.value == '') ||
										(selectionForm.udocBloqueadas.value != '' && selectionForm.udocBloqueadas.value.split(SEPARATOR).length != nSelected)){
									alert("<bean:message key='errors.relaciones.noPermitirDesbloquearUdocsDesbloqueadas'/>");
								}else{
								selectionForm.method.value='unlockUdocs';
								selectionForm.submit();
								}
							} else {
								alert("<bean:message key='archigest.archivo.transferencias.selUDocUnlock'/>");
							}
						}else
							alert("<bean:message key='archigest.archivo.transferencias.selUDocUnlock'/>");
						}
				</script>
				<div class="cabecero_tabs">
					<table cellspacing="0" cellpadding=0>
						<tr>
							<c:if test="${!vRelacion.conReencajadoValidada}">
						    	<td class="tabActual" id="ptabCajasRelacion" onclick="javascript:tabPanel.showTab('tabCajasRelacion');">
									<a href="javascript:tabPanel.showTab('tabCajasRelacion');" id="ttabCajasRelacion" class="textoPestana">
										<c:choose>
											<c:when test="${vRelacion.sinDocsFisicos}">
												<bean:message key="archigest.archivo.detalle"/>
											</c:when>
											<c:otherwise>
												<bean:message key="archigest.archivo.transferencias.unidInstalacion"/>
											</c:otherwise>
										</c:choose>
	
	
									</a>
							    </td>
								<td width="5px">&nbsp;</td>
							</c:if>
					    	<td class="tabSel" id="ptabUdocsRelacion" onclick="javascript:tabPanel.showTab('tabUdocsRelacion');">
								<a href="javascript:tabPanel.showTab('tabUdocsRelacion');" id="ttabUdocsRelacion" class="textoPestana">
									<bean:message key="archigest.archivo.transferencias.contenido"/>
								</a>
						    </td>
						    <c:if test="${vRelacion.relacionConReencajado}">
						    <td width="5px">&nbsp;</td>
					    	<td class="tabSel" id="ptabReencajado" onclick="javascript:tabPanel.showTab('tabReencajado');">
								<a href="javascript:tabPanel.showTab('tabReencajado');" id="ttabReencajado" class="textoPestana">
									<bean:message key="archigest.archivo.transferencias.tab.reencajado"/>
								</a>
						    </td>
						    </c:if>
						</tr>
					</table>
				</div>
				<%--Tab de Unidades de Instalacion (Cajas) --%>
				<c:if test="${!vRelacion.conReencajadoValidada}">
				<div id="tabCajasRelacion" class="bloque_tab" style="display:block">

					<div class="cabecero_bloque_tab"> <%--cabecero segundo bloque de datos --%>
						<table class="w98m1" cellpadding=0 cellspacing=0>
						  <tr>
						    <td width="100%" align="right">

							</td>
						</tr>
					  </table>
					</div><%--cabecero segundo bloque de datos --%>



		<c:if test="${not empty udocsSinDocsFisicos || vRelacion.permitidaAdicionDocsElectronicos}">
		<div class="separador8">&nbsp;</div>
		<div style="margin:5px">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
				<html:img page="/pages/images/docelectronico.gif" altKey="archigest.archivo.transferencias.unidadesElectronicas" titleKey="archigest.archivo.transferencias.unidadesElectronicas" styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.transferencias.unidadesElectronicas" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<c:if test="${vRelacion.permitidaAdicionDocsElectronicos}">
				<c:url var="addUInstURL" value="/action/gestionUInstRelacion" >
					<c:param name="method" value="selUInst"/>
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
					<c:param name="tipo" value="E"/>
				</c:url>

				<a class="etiquetaAzul12Normal" href="javascript:addUdocElectronica('<c:out value="${addUInstURL}" escapeXml="false" />');">
					<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/add_doc_electronico.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.anadir"/>
				</a>
				&nbsp;
			</c:if>
			<c:if test="${vRelacion.permitidaSustraccionDocsElectronicos or vRelacion.permitidoCorregirErrores}">
				<a class="etiquetaAzul12Normal" href="javascript:eliminarUDocsElectronicas()">
					<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delete_doc_electronico.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.eliminar"/>
				</a>
				&nbsp;
			</c:if>
			<c:if test="${!vRelacion.relacionConReencajado and vRelacion.permitidoCorregirErrores}">
			   	<a class="etiquetaAzul12Normal"
				   href="javascript:marcarRevisada()">
				   <html:img page="/pages/images/doc_electronico_ok.gif"
							altKey="archigest.archivo.transferencias.marcarRevisada"
							titleKey="archigest.archivo.transferencias.marcarRevisada"
							styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.transferencias.marcarRevisada"/></a>
			</c:if>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
				<c:if test="${not empty udocsSinDocsFisicos and (vRelacion.permitidaSustraccionDocsElectronicos or  vRelacion.permitidoCorregirErrores)}">

					<table class="formulario">
						<tr>
							<td align="right">
						<a class="etiquetaAzul12Bold"
							href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosElectronicosSel);"
			 			><html:img page="/pages/images/checked.gif"
							    altKey="archigest.archivo.selTodos"
							    titleKey="archigest.archivo.selTodos"
							    styleClass="imgTextBottom" />
					    &nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
						&nbsp;
						<a class="etiquetaAzul12Bold"
							href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosElectronicosSel);"
			 			><html:img page="/pages/images/check.gif"
							    altKey="archigest.archivo.quitarSel"
							    titleKey="archigest.archivo.quitarSel"
							    styleClass="imgTextBottom" />
					    &nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
						&nbsp;&nbsp;
					</td>
					</tr>
					</table>
				</c:if>

				<display:table name="requestScope.udocsSinDocsFisicos" id="udocSinDocsFisicos"
					style="width:99%;margin-left:auto;margin-right:auto">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.no.encontradas.unidades.electronicas.entre.archivos"/>
				</display:setProperty>
					<display:setProperty name="basic.show.header" value="false" />
					<display:column style="width:10px">
							<c:if test="${(vRelacion.permitidaSustraccionDocsElectronicos or  vRelacion.permitidoCorregirErrores)}">
								<input type="checkbox" name="elementosElectronicosSel" value="<c:out value="${udocSinDocsFisicos.id}" />" >
							</c:if>
					</display:column>
						<c:if test="${!empty udocSinDocsFisicos.estadoCotejo}">
							<display:column titleKey="archigest.archivo.transferencias.cotejoUDoc" style="width:20px">
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

					<display:column titleKey="archigest.archivo.transferencias.asunto">
					<c:choose>
					<c:when test="${vRelacion.entreArchivos}">
						<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
							<c:param name="method" value="verEnFondos" />
							<c:param name="unidadDocumental" value="${udocSinDocsFisicos.id}" />
						</c:url>
					</c:when>
					<c:otherwise>
						<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
							<c:param name="method" value="infoUnidadDocumental"/>
							<c:param name="udocID" value="${udocElectronica.id}"/>
						</c:url>
					</c:otherwise>
					</c:choose>

							<a href="<c:out value="${infoUdocURL}" escapeXml="false" />" class="tdlink">
								<c:out value="${udocSinDocsFisicos.asunto}" />
							</a>
						<c:if test="${udocSinDocsFisicos.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador && !empty udocSinDocsFisicos.notasCotejo }">
							<br/><span class="etiquetaNegra11Normal"><br/><b><i><bean:message key="archigest.archivo.transferencias.notasCotejo" />:</i></b> <c:out value="${udocSinDocsFisicos.notasCotejo}" /></span>
						</c:if>
					</display:column>

					<c:if test="${vRelacion.permitidoMostrarBloqueoDesbloqueoExpedientes}">
						<display:column style="text-align: center;"  media="html">
							<c:choose>
								<c:when test="${udocSinDocsFisicos.marcasBloqueo>0}" >
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
								<c:when test="${udocSinDocsFisicos.marcasBloqueo>0}" >
									<bean:message key="archigest.archivo.si" />
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.no" />
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:if>
				</display:table>
				<div class="separador8">&nbsp;</div>
		</tiles:put>
		</tiles:insert>
		</div>
		</c:if>
		<%--FIN DOCUMENTOS ELECTRONICOS --%>

		<%--UNIDADES DE INSTALACION --%>

		<c:if test="${not empty cajasRelacion || vRelacion.permitidaAdicionExpedientes}">
				<div class="separador8">&nbsp;</div>

		<div style="margin:5px">
				<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
				<html:img page="/pages/images/caja_closed.gif" altKey="archigest.archivo.transferencias.UndInstal" titleKey="archigest.archivo.transferencias.UndInstal" styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.transferencias.UndInstal" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<c:if test="${vRelacion.permitidaImpresionCartelasProvisionales and !vRelacion.relacionConReencajado}">
				<security:permissions action="${appConstants.transferenciaActions.IMPRESION_CARTELAS_PROVISIONALES}">
					<c:url var="urlImprimir" value="/action/gestionRelaciones">
						<c:param name="method" value="selCartelas"/>
					</c:url>
					<a class="etiquetaAzul12Normal" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
						<html:img titleKey="archigest.archivo.transferencias.generarCartelas" altKey="archigest.archivo.transferencias.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
							 &nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/>&nbsp;&nbsp;
					</a>
				</security:permissions>
			</c:if>
			<c:if test="${vRelacion.permitidaAdicionExpedientes}">
				<c:url var="addUInstURL" value="/action/gestionUInstRelacion" >
					<c:param name="method" value="selUInst"/>
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
					<c:param name="tipo" value="F"/>
				</c:url>
				<a class="etiquetaAzul12Normal" href="javascript:addUinst('<c:out value="${addUInstURL}" escapeXml="false" />');">
					<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/caja_new.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.anadir"/>&nbsp;
				</a>
			</c:if>
			<c:if test="${vRelacion.permitidaSustraccionExpedientes or vRelacion.permitidoCorregirErrores}">
				<a class="etiquetaAzul12Normal" href="javascript:eliminarUnidadesInstalacion()">
					<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/caja_delete.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.eliminar"/>
				</a>
			</c:if>
			<c:if test="${!vRelacion.relacionConReencajado and vRelacion.permitidoCorregirErrores}">
			   	<a class="etiquetaAzul12Normal"
				   href="javascript:marcarRevisada()">
				   <html:img page="/pages/images/caja_ok.gif"
							altKey="archigest.archivo.transferencias.marcarRevisada"
							titleKey="archigest.archivo.transferencias.marcarRevisada"
							styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.transferencias.marcarRevisada"/></a>
			</c:if>
			&nbsp;
		</tiles:put>
		<tiles:put name="blockContent" direct="true">


			<c:if test="${not empty cajasRelacion and vRelacion.sinDocsFisicos  and (vRelacion.permitidaSustraccionExpedientes or  vRelacion.permitidoCorregirErrores)}">
				<table class="formulario">
					<tr>
						<td align="right">
							<a class="etiquetaAzul12Bold"
								href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosseleccionados);">
					 			<html:img page="/pages/images/checked.gif"
									    altKey="archigest.archivo.selTodas"
									    titleKey="archigest.archivo.selTodas"
									    styleClass="imgTextBottom" />
							    &nbsp;<bean:message key="archigest.archivo.selTodas"/>
							</a>&nbsp;
							<a class="etiquetaAzul12Bold"
									href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${actionMapping.name}" />'].elementosseleccionados);" >
									<html:img page="/pages/images/check.gif"
									    altKey="archigest.archivo.quitarSel"
									    titleKey="archigest.archivo.quitarSel"
									    styleClass="imgTextBottom" />
							    &nbsp;<bean:message key="archigest.archivo.quitarSel"/>
							 </a>&nbsp;&nbsp;
						  </td>
					</tr>
				</table>
			</c:if>

			<div>

				<c:if test="${empty cajasRelacion}">
					<bean:message key="archigest.archivo.relacion.sin.unidades.instalacion"/>
				</c:if>

				<c:forEach items="${cajasRelacion}" var="unidadInstalacion" varStatus="nCajas" >
				<c:set var="cajaPanelName" value="Caja${nCajas.count}" />
				<jsp:useBean id="cajaPanelName" type="java.lang.String" />

				<div class="titulo_gris_bloque">
					<c:set var="panelControlImg" value="img${cajaPanelName}" />
					<jsp:useBean id="panelControlImg" type="java.lang.String" />
					<c:set var="panelVisibilityCommand" value="switchDivVisibility('${cajaPanelName}')" />
					<jsp:useBean id="panelVisibilityCommand" type="java.lang.String" />
					<table class="w98" cellpadding="0" cellspacing="0"><tr>
						<td width="50%" class="etiquetaAzul12Normal">
						<c:choose>
							<c:when test="${vRelacion.permitidaSustraccionExpedientes or vRelacion.permitidoCorregirErrores}" >
								 <input type="checkbox" name="elementosseleccionados" value="<c:out value="${unidadInstalacion.id}" />" >
							</c:when>
							<c:otherwise>
								<html:img page="/pages/images/pixel.gif" width="20" styleClass="imgTextMiddle"/>
							</c:otherwise>
						</c:choose>

							<html:img page="/pages/images/down.gif" styleId="<%=panelControlImg%>" onclick="<%=panelVisibilityCommand%>" styleClass="imgTextMiddle"/>
								&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.caja" />:&nbsp;<c:out value="${unidadInstalacion.orden}" />
								<c:if test="${vRelacion.permitidoMostrarBloqueoDesbloqueoExpedientes}">
									<c:choose>
										<c:when test="${unidadInstalacion.lockedByUdoc}" >
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
								</c:if>
								<c:if test="${unidadInstalacion.conErrores}">
									&nbsp;&nbsp;<html:img page="/pages/images/cajaError.gif" border="0" altKey="archigest.archivo.transferencias.cajaConErrores" titleKey="archigest.archivo.transferencias.cajaConErrores" styleClass="imgTextMiddle"/>
								</c:if>
								<c:if test="${unidadInstalacion.devolver}">
									&nbsp;&nbsp<html:img page="/pages/images/cajaDevuelta.gif" border="0" altKey="archigest.archivo.transferencias.devolver" titleKey="archigest.archivo.transferencias.devolver" styleClass="imgTextMiddle"/>
								</c:if>
								&nbsp;&nbsp;
								<bean-el:message key="archigest.archivo.caja.contiene.unidades.documentales" arg0="${unidadInstalacion.udocCount}"/>
						</td>
					<c:if test="${!empty unidadInstalacion.signaturaUI}">
						<td width="15%" class="etiquetaAzul11Normal">
							<b><bean:message key="archigest.archivo.transferencias.signatura" />:</b>
							&nbsp;<c:out value="${unidadInstalacion.signaturaUI}" />
						</td>
					</c:if>
					<c:set var="cajaOrganizable" value="true"/>
					<c:if test="${(unidadInstalacion.revisada && vRelacion.conErroresCotejo) || (!unidadInstalacion.devolver && vRelacion.conErroresCotejo)}">
						<c:set var="cajaOrganizable" value="false"/>
					</c:if>
					<c:if test="${vRelacion.permitidaModificacionAsignacionCajas && cajaOrganizable}">
						<td width="40%" style="text-align:right;" class="etiquetaAzul11Normal">
						<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
							<table cellpadding="0" cellspacing="0"><tr>
							<c:if test="${vRelacion.signaturaSolictableEnUI}">
								<td>
									<c:url var="editarSignaturaURL" value="/action/asignacionCajas">
										<c:param name="method" value="editarSignaturaCaja" />
										<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
									</c:url>
									<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarSignaturaURL}" escapeXml="false"/>'">
										<html:img titleKey="archigest.archivo.transferencias.editarSignatura"
											altKey="archigest.archivo.transferencias.editarSignatura" page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"/>
										<bean:message key="archigest.archivo.transferencias.editarSignatura"/>
									</a>
								</td>
							</c:if>
							<td width="10">&nbsp;</td></tr>
							</table>
						</security:permissions>
						</td>
					</c:if>
						</tr></table>
				</div>

				<c:if test="${!empty unidadInstalacion.notasCotejo}">
					<div class="titulo_gris_bloque">
						&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.observaciones" />:&nbsp;<c:out value="${unidadInstalacion.notasCotejo}" />
					</div>
				</c:if>
				<div isOpen="false" id="div<%=cajaPanelName%>" style="display:none;">
					<c:set var="udocs" value="${unidadInstalacion.udocs}" />

						<display:table name="pageScope.udocs" id="parteUnidadDocumental"
								style="width:99%;margin-left:auto;margin-right:auto">

							<c:set var="unidadDocumental" value="${parteUnidadDocumental}" />

							<display:setProperty name="basic.show.header" value="false" />

							<display:setProperty name="basic.msg.empty_list">
								<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
									&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
								</div>
							</display:setProperty>
							<display:column title="" style="width:30px;text-align:right;">
								<c:out value="${parteUnidadDocumental_rowNum}" />:
							</display:column>
							<display:column titleKey="archigest.archivo.transferencias.asunto">
								<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
									<c:param name="method" value="verEnFondos" />
									<c:param name="unidadDocumental" value="${unidadDocumental.id}" />
								</c:url>
								<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
									<c:out value="${unidadDocumental.asunto}" />
								</a>
								</display:column>

								<c:if test="${vRelacion.permitidoMostrarBloqueoDesbloqueoExpedientes}">
									<display:column style="text-align: center;"  >
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
								</c:if>

							</display:table>

							</div> <%--divcajaPanelNameN--%>
							<span class="separador1"></span>
						</c:forEach>
					</div>
				</tiles:put>
				</tiles:insert>
				<div id="separador8">&nbsp;</div>
				</div>
			</c:if>
		<%--FIN UNIDADES DE INSTALACION --%>

				</div>
				</c:if>



				<%--Tab de Unidades Documentales (Expedientes) --%>
				<div id="tabUdocsRelacion" class="bloque_tab" style="display:none">



					<div class="cabecero_bloque_tab"> <%--cabecero segundo bloque de datos --%>
						&nbsp;
					</div><%--cabecero segundo bloque de datos --%>

		<%--UNIDADES ELECTRONICAS --%>
				<c:if test="${empty udocsSinDocsFisicos and empty udocsRelacion}">
					<bean:message key="archigest.archivo.sin.unidades.documentales"/>
				</c:if>


					<c:if test="${not empty udocsSinDocsFisicos}">

					<div class="separador8">&nbsp;</div>
					<div style="margin:5px">
					<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
					<tiles:put name="blockTitle" direct="true">
							<html:img page="/pages/images/docelectronico.gif" altKey="archigest.archivo.transferencias.unidadesElectronicas" titleKey="archigest.archivo.transferencias.unidadesElectronicas" styleClass="imgTextBottom" />
							<bean:message key="archigest.archivo.transferencias.unidadesElectronicas" />
					</tiles:put>
					<tiles:put name="buttonBar" direct="true">
						<c:if test="${vRelacion.permitidoBloqueoDesbloqueoExpedientes}">
							<TABLE class="w98m1" cellpadding=0 cellspacing=0>
								<TBODY>
									<TR>
										<TD width="100%" align="right">
										<TABLE cellpadding="0" cellspacing="0">
											<TR>

												<TD><a class="etiquetaAzul12Normal"
													href="javascript:lockSelectedItem('<c:out value="${actionMapping.name}" />')">
												<html:img titleKey="archigest.archivo.bloquear"
													altKey="archigest.archivo.bloquear"
													page="/pages/images/udocBloquear.gif" styleClass="imgTextMiddle" />
												&nbsp;<bean:message key="archigest.archivo.bloquear" /> </a></TD>
												<TD width="10">&nbsp;</TD>
												<TD><a class="etiquetaAzul12Normal"
													href="javascript:unlockSelectedItem('<c:out value="${actionMapping.name}" />')">
												<html:img titleKey="archigest.archivo.desbloquear"
													altKey="archigest.archivo.desbloquear"
													page="/pages/images/udocDesbloquear.gif"
													styleClass="imgTextMiddle" /> &nbsp;<bean:message
													key="archigest.archivo.desbloquear" /> </a></TD>
											</TR>
										</TABLE>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</c:if>

					</tiles:put>
					<tiles:put name="blockContent" direct="true">
					<div class="separador8">&nbsp;</div>
					<display:table name="requestScope.udocsSinDocsFisicos" id="udocSinDocsFisicos"
					style="width:99%;margin-left:auto;margin-right:auto"
					requestURI="<%=vistaRelacionPaginationURI%>"
					>

					<c:if test="${vRelacion.permitidoBloqueoDesbloqueoExpedientes}">
						<display:column style="width:20px" headerClass="minusDocIcon" media="html">
							<html-el:multibox property="elementosUdocSeleccionados" value="${udocSinDocsFisicos.id}"
								onclick="javascript:marcarBloqueo('${actionMapping.name}',this,'${udocSinDocsFisicos.marcasBloqueo}');"/>
						</display:column>
					</c:if>

					<display:column titleKey="archigest.archivo.transferencias.num" sortable="true" sortProperty="orden" media="html">
						<c:out value="${udocSinDocsFisicos_rowNum}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.asunto" media="html">
							<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
								<c:param name="method" value="verEnFondos" />
								<c:param name="unidadDocumental" value="${udocSinDocsFisicos.id}" />
							</c:url>

							<a href="<c:out value="${infoUdocURL}" escapeXml="false" />" class="tdlink">
								<c:out value="${udocSinDocsFisicos.asunto}" />
							</a>
						<c:if test="${udocSinDocsFisicos.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador && !empty udocSinDocsFisicos.notasCotejo }">
							<br/><span class="etiquetaNegra11Normal"><br/><b><i><bean:message key="archigest.archivo.transferencias.notasCotejo" />:</i></b> <c:out value="${udocSinDocsFisicos.notasCotejo}" /></span>
						</c:if>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.asunto" media="csv xml excel pdf">
						<c:out value="${udocSinDocsFisicos.asunto}" />
						<c:if test="${udocSinDocsFisicos.estadoCotejo == appConstants.transferencias.estadoCotejo.ERRORES.identificador && !empty udocSinDocsFisicos.notasCotejo }">
							<bean:message key="archigest.archivo.transferencias.notasCotejo" />:<c:out value="${udocSinDocsFisicos.notasCotejo}" />
						</c:if>
					</display:column>

					<c:if test="${vRelacion.permitidoMostrarBloqueoDesbloqueoExpedientes}">
						<display:column style="text-align: center;"  media="html">
							<c:choose>
								<c:when test="${udocSinDocsFisicos.marcasBloqueo>0}" >
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
								<c:when test="${udocSinDocsFisicos.marcasBloqueo>0}" >
									<bean:message key="archigest.archivo.si" />
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.no" />
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:if>

				</display:table>
				<div class="separador8">&nbsp;</div>
				</tiles:put>
				</tiles:insert>
				</div>
				</c:if>

		<%--FIN DE UNIDADES ELECTRONICAS --%>

					<c:if test="${not empty udocsRelacion}">
	<div class="separador8">&nbsp;</div>
	<div style="margin: 5px"><tiles:insert
		page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<html:img page="/pages/images/docsElectronicos.gif"
				altKey="archigest.archivo.unidadesDocumentales"
				titleKey="archigest.archivo.unidadesDocumentales"
				styleClass="imgTextBottom" />
			<bean:message key="archigest.archivo.unidadesDocumentales" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<c:if test="${vRelacion.permitidoBloqueoDesbloqueoExpedientes}">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
					<TBODY>
						<TR>
							<TD width="100%" align="right">
							<TABLE cellpadding="0" cellspacing="0">
								<TR>

									<TD><a class="etiquetaAzul12Normal"
										href="javascript:lockSelectedItem('<c:out value="${actionMapping.name}" />')">
									<html:img titleKey="archigest.archivo.bloquear"
										altKey="archigest.archivo.bloquear"
										page="/pages/images/udocBloquear.gif" styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.bloquear" /> </a></TD>
									<TD width="10">&nbsp;</TD>
									<TD><a class="etiquetaAzul12Normal"
										href="javascript:unlockSelectedItem('<c:out value="${actionMapping.name}" />')">
									<html:img titleKey="archigest.archivo.desbloquear"
										altKey="archigest.archivo.desbloquear"
										page="/pages/images/udocDesbloquear.gif"
										styleClass="imgTextMiddle" /> &nbsp;<bean:message
										key="archigest.archivo.desbloquear" /> </a></TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</c:if>
		</tiles:put>
					<tiles:put name="blockContent" direct="true">
					<div class="separador8">&nbsp;</div>
					
					<c:choose>
						<c:when test="${vRelacion.conReencajadoValidada}">
							<display:table name="requestScope.udocsRelacion" id="unidadDocumental"
								style="width:99%;margin-left:auto;margin-right:auto"
								sort="list"
								pagesize="0"
								requestURI="<%=vistaRelacionPaginationURI%>" export="true">
		
								<display:setProperty name="basic.msg.empty_list">
									<bean:message key="archigest.archivo.transferencias.relacionSinUnidadesDocumentales" />
								</display:setProperty>
		
								<display:column titleKey="archigest.archivo.transferencias.num">
									<c:out value="${unidadDocumental_rowNum}"/>
								</display:column>
		
								<display:column titleKey="archigest.archivo.transferencias.asunto" media="html">
									<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
										<c:param name="method" value="verEnFondos" />
										<c:param name="unidadDocumental" value="${unidadDocumental.idUnidadDoc}" />
									</c:url>
		
									<a href="<c:out value="${infoUdocURL}" escapeXml="false" />" class="tdlink">
										<c:out value="${unidadDocumental.asunto}" />
									</a>
								</display:column>
		
								<display:column titleKey="archigest.archivo.transferencias.asunto" media="csv xml excel pdf">
									<c:out value="${unidadDocumental.asunto}" />
								</display:column>
							</display:table>
						</c:when>
						<c:otherwise>
							<display:table name="requestScope.udocsRelacion" id="unidadDocumental"
								style="width:99%;margin-left:auto;margin-right:auto"
								sort="list"
								pagesize="0"
								requestURI="<%=vistaRelacionPaginationURI%>" export="true">
		
								<display:setProperty name="basic.msg.empty_list">
									<bean:message key="archigest.archivo.transferencias.relacionSinUnidadesDocumentales" />
								</display:setProperty>
		
							<c:if test="${vRelacion.permitidoBloqueoDesbloqueoExpedientes}">
								<display:column style="width:20px" headerClass="minusDocIcon" media="html">
									<html-el:multibox property="elementosUdocSeleccionados" value="${unidadDocumental.id}"
										onclick="javascript:marcarBloqueo('${actionMapping.name}',this,'${unidadDocumental.marcasBloqueo}');"/>
								</display:column>
							</c:if>
		
								<display:column titleKey="archigest.archivo.transferencias.num">
									<c:out value="${unidadDocumental_rowNum}"/>
								</display:column>
		
								<display:column titleKey="archigest.archivo.transferencias.asunto" media="html">
									<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
										<c:param name="method" value="verEnFondos" />
										<c:param name="unidadDocumental" value="${unidadDocumental.id}" />
									</c:url>
		
									<a href="<c:out value="${infoUdocURL}" escapeXml="false" />" class="tdlink">
										<c:out value="${unidadDocumental.asunto}" />
									</a>
								</display:column>
		
								<display:column titleKey="archigest.archivo.transferencias.asunto" media="csv xml excel pdf">
									<c:out value="${unidadDocumental.asunto}" />
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
						</c:otherwise>
					</c:choose>
					<div class="separador8">&nbsp;</div>
					</tiles:put>
					</tiles:insert>
					</div>
					</c:if>


				</div><%--Tab de Unidades Documentales (Expedientes) --%>

				<%--Tab de Reencajado --%>
				<c:if test="${vRelacion.relacionConReencajado}">
					<div id="tabReencajado" class="bloque_tab" style="display:none">
						<c:set var="infoUdocReeacr" value="${sessionScope[appConstants.transferencias.INFO_UDOCREEACR]}" />
						<bean:define id="vRelacion" name="vRelacion" toScope="request"/>
						<bean:define id="infoUdocReeacr" name="infoUdocReeacr" toScope="request"/>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/cuerpo_relacion_reencajado.jsp" flush="true"/>
					</div><%--Tab de Reencajado --%>
				</c:if>
		<script>
			<c:choose>
				<c:when test="${!vRelacion.conReencajadoValidada}">
					if (typeof tabPanel != 'undefined') {
						tabPanel.showTab(getCookie("tabsUdocsRelacion"));
					}
				</c:when>
				<c:otherwise>
					if (typeof tabPanel != 'undefined') {
						tabPanel.showTab("tabReencajado");
					}
				</c:otherwise>
			</c:choose>			
		</script>