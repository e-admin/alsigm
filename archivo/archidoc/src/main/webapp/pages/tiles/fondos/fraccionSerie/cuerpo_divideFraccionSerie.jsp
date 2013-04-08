<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="udocsFraccionSerie" value="${sessionScope[appConstants.fondos.UDOCS_FRACCION_SERIE]}" />
<c:set var="divisionFraccionSerie" value="${sessionScope[appConstants.fondos.DIVISION_FRACCION_SERIE]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<bean:struts id="mappingFS" mapping="/gestionFraccionSerie" />
<bean:struts id="mappingUDocsEnFS" mapping="/gestionUDocsEnFS" />
<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.datosDivision"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				
				<c:if test="${divisionFraccionSerie.permitidoModificarDivisionFS && !empty udocsFraccionSerie}">
					<td>
						<c:url var="saveInfoUDocsURL" value="/action/gestionFraccionSerie?method=saveInfoUDocsEnFraccionSerie" />
						<script language="javascript">
							var enviado = false;
							function finalizarDivision(){
								if (!enviado){
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}
									enviado = true;
									window.location = "<c:out value="${saveInfoUDocsURL}" escapeXml="false"/>";
								}
							}
						</script>
						
						<a class="etiquetaAzul12Bold" href="javascript:finalizarDivision();">							
							<html:img page="/pages/images/dividirfs_finalizar.gif" border="0" altKey="archigest.archivo.fondos.finalizarDivisionFS" titleKey="archigest.archivo.fondos.finalizarDivisionFS" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.fondos.finalizarDivisionFS"/>
						</a>
					</td>
				</c:if>
			   <td width="8px">&nbsp;</td>
		 	
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cerrar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/close.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>
	
	
	<tiles:put name="boxContent" direct="true">	
			<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
				<tiles:put name="blockName" direct="true">divisionFSContexto</tiles:put>
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.fondos.divisionFS"/></tiles:put>
				<tiles:put name="visibleContent" direct="true">
					<tiles:insert page="/pages/tiles/fondos/fraccionSerie/cabeceracte_divisionFS.jsp"/>
				</tiles:put>
				<tiles:put name="dockableContent" direct="true">
					<tiles:insert page="/pages/tiles/fondos/fraccionSerie/datos_divisionFS.jsp" />
				</tiles:put>
			</tiles:insert>

			<html:form action="/gestionUDocsEnFS" >
				<input type="hidden" name="method" value="eliminarUdocs">
				<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
					<tiles:put name="blockTitle" direct="true">
						<bean:message key="archigest.archivo.unidadesDocumentales"/>
					</tiles:put>	
					<tiles:put name="buttonBar" direct="true">
					<c:if test="${divisionFraccionSerie.permitidoModificarDivisionFS}">
						<script>
										function eliminarSelectedItems(selectionFormName) {
											var selectionForm = document.forms[selectionFormName];
											if (selectionForm && selectionForm.selectedUdoc) {
												var nSelected = FormsToolKit.getNumSelectedChecked(selectionForm,"selectedUdoc");
												if(nSelected >= 1) {
													if (confirm("<bean:message key='archigest.archivo.transferencias.delUDocSel'/>")) {
														if (selectionForm)
															selectionForm.submit();
													}
												} else 
													alert("<bean:message key='archigest.archivo.transferencias.selUDocEliminar'/>");
											}
										}
						</script>
						<TABLE class="w98m1" cellpadding=0 cellspacing=0>
							<TBODY>
								<TR>
								    <TD width="100%" align="right">
										<TABLE cellpadding="0" cellspacing="0">
										  <TR>
											<c:url var="addUdocURL" value="/action/gestionUDocsEnFS?method=addUdocs" />
												<TD>
													<a class="etiquetaAzul12Normal" href="<c:out value="${addUdocURL}" escapeXml="false"/>">
														<html:img titleKey="archigest.archivo.transferencias.anadirExps" altKey="archigest.archivo.transferencias.anadirExps" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
														&nbsp;<bean:message key="archigest.archivo.anadir"/>
													</a>		
												</TD>
												<TD width="10">&nbsp;</TD>
											<TD>
												<a class="etiquetaAzul12Normal" href="javascript:eliminarSelectedItems('<c:out value="${mappingUDocsEnFS.name}" />')">
													<html:img titleKey="archigest.archivo.transferencias.delExps" altKey="archigest.archivo.transferencias.delExps" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
													&nbsp;<bean:message key="archigest.archivo.eliminar"/>
												</a>
											</TD>
										 </TR>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>	
					</c:if>					
					</tiles:put>				
					<tiles:put name="blockContent" direct="true">

						<c:url var="saveInfoUdocsFSURI" value="/action/gestionFraccionSerie">
							<c:param name="method" value="saveInfoUDocsEnFraccionSerie" />
						</c:url>

						<jsp:useBean id="saveInfoUdocsFSURI" type="java.lang.String" />

						<div class="separador8">&nbsp;</div>	
						<display:table name="pageScope.udocsFraccionSerie" id="unidadDocumental" 
										style="width:99%;margin-left:auto;margin-right:auto"
										sort="list"
										pagesize="0"
										requestURI = "<%=saveInfoUdocsFSURI%>">
									
							<c:url var="infoUdocURL" value="/action/gestionUDocsEnFS">
								<c:param name="method" value="infoUnidadDocumental" />
								<c:param name="udocID" value="${unidadDocumental.idUDoc}" />
							</c:url>
							<c:if test="${divisionFraccionSerie.permitidoEliminarUDocs}">
								<display:column style="width:20px" headerClass="minusDocIcon" media="html">
									<html-el:multibox property="selectedUdoc" value="${unidadDocumental.idUDoc}" ></html-el:multibox>
								</display:column>
							</c:if>
							<display:column titleKey="archigest.archivo.transferencias.num" sortProperty="orden" headerClass="sortable" sortable="true" >
								<c:out value="${unidadDocumental_rowNum}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.transferencias.nExp">
								<c:set var="isNivelCaja" value="${sessionScope[appConstants.fondos.BANDERA_MOSTRAR_RANGOS]}" />
								<c:choose>
								<c:when test="${isNivelCaja}">
									<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
										<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />		
										<c:out value="${unidadDocumental.nombreRangos}" />									
									</a>
								</c:when>
								<c:otherwise>
									<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
										<c:out value="${unidadDocumental.numExp}" />
									</a>
								</c:otherwise>
								</c:choose>
							</display:column>
							<display:column titleKey="archigest.archivo.titulo">
								<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
									<c:out value="${unidadDocumental.asunto}"/>
								</a>
							</display:column>
							<display:column titleKey="archigest.archivo.transferencias.fIni">		
								<fmt:formatDate value="${unidadDocumental.fechaExtIni}" pattern="${FORMATO_FECHA}" />																				
							</display:column>
							<display:column titleKey="archigest.archivo.transferencias.fFin">	
								<fmt:formatDate value="${unidadDocumental.fechaExtFin}" pattern="${FORMATO_FECHA}" />																				
							</display:column>
						</display:table>	
					<div class="separador8">&nbsp;</div>	
				</tiles:put>
			</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>