<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="udocsElectronicas" value="${requestScope[appConstants.transferencias.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY]}"/>
<c:set var="udocsRelacion" value="${requestScope[appConstants.transferencias.LISTADO_UNIDADES_DOCUMENTALES_KEY]}" />
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.labelValidacionRelacion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
					<c:if test="${vRelacion.puedeSerValidada}">
						<security:permissions action="${appConstants.transferenciaActions.VALIDAR_RELACION}">
						<td>
						<c:url var="validarRelacionURL" value="/action/gestionRelaciones">
							<c:param name="method" value="validar" />
							<c:param name="idRelacion" value="${vRelacion.id}" />
						</c:url>
						<script>
							function validarRelacion() {
								if (window.top.showWorkingDiv) {
									var title = '<bean:message key="archigest.archivo.operacion.realizandoValidacion"/>';
									var message = '<bean:message key="archigest.archivo.operacion.msgRealizandoValidacion"/>';
									var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
									window.top.showWorkingDiv(title, message, message2);
								}
								window.location = '<c:out value="${validarRelacionURL}" escapeXml="false"/>';
							}
						</script>
						<a class="etiquetaAzul12Bold" href="javascript:validarRelacion()">
						<c:choose>
							<c:when test="${vRelacion.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
								<html:img page="/pages/images/validar.gif" styleClass="imgTextMiddle" altKey="archigest.archivo.transferencias.validarFraccionesSerie" titleKey="archigest.archivo.transferencias.validarFraccionesSerie"/> 
								<bean:message key="archigest.archivo.transferencias.validarFraccionesSerie"/>
							</c:when>
							<c:otherwise>
								<html:img page="/pages/images/validar.gif" styleClass="imgTextMiddle" altKey="archigest.archivo.transferencias.validarToda" titleKey="archigest.archivo.transferencias.validarToda"/> 
								<bean:message key="archigest.archivo.transferencias.validarToda"/>
							</c:otherwise>
						</c:choose>
						</a>
						</td>
						<td width="10px">&nbsp;</td>
						</security:permissions>
					</c:if>				
				
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
		
		<div class="separador8">&nbsp; </div><%--8 pixels de separacion --%>
			<c:url var="vistaRelacionPaginationURI" value="/action/gestionRelaciones">
				<c:param name="method" value="${param.method}" />
				<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
			</c:url>
			<jsp:useBean id="vistaRelacionPaginationURI" type="java.lang.String" />
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.contenido"/></tiles:put>
			<tiles:put name="buttonBar" direct="true"></tiles:put>
			<tiles:put name="blockContent" direct="true">
	<%--DOCUMENTOS ELECTRONICOS --%>
				
		<c:if test="${not empty udocsElectronicas}">
		<div class="separador8">&nbsp;</div>
		<div style="margin:5px">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
				<html:img page="/pages/images/docelectronico.gif" altKey="archigest.archivo.transferencias.unidadesElectronicas" titleKey="archigest.archivo.transferencias.unidadesElectronicas" styleClass="imgTextBottom" />
				<bean:message key="archigest.archivo.transferencias.unidadesElectronicas" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
					<display:table name="pageScope.udocsElectronicas" id="unidadDocumental" 
					style="width:99%;margin-left:auto;margin-right:auto"
					sort="list"
					pagesize="10"
					requestURI="<%=vistaRelacionPaginationURI%>" >

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.noUDCajas" />
					</display:setProperty>
				
					<display:column titleKey="archigest.archivo.transferencias.validada" style="text-align:center;width:10%">
						<c:choose>
							<c:when test="${unidadDocumental.validada}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
							<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.nExp" sortable="true" headerClass="sortable" style="width:20%">
						<c:out value="${unidadDocumental.expediente}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.asunto" style="width:65%">
						<c:choose>
							<c:when test="${vRelacion.entreArchivos}">
								<c:url var="validarUdocURL" value="/action/gestionUdocsCF">
									<c:param name="method" value="verEnFondos" />
									<c:param name="unidadDocumental" value="${unidadDocumental.id}" />
								</c:url>
							</c:when>
							<c:otherwise>
								<c:url var="validarUdocURL" value="/action/gestionUdocsRelacion">
									<c:param name="method" value="infoUnidadDocumental" />
									<c:param name="udocID" value="${unidadDocumental.id}" />
								</c:url>
							</c:otherwise>
						</c:choose>
						<a href="<c:out value="${validarUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${unidadDocumental.asunto}" />
						</a>
					</display:column>

					<c:if test="${vRelacion.permitidoMostrarBloqueoDesbloqueoExpedientes}">
						<display:column style="text-align: center; width:5%;">
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
				<div class="separador8">&nbsp;</div>						
		</tiles:put>
		</tiles:insert>
		</div>
		</c:if>			
		<%--FIN DOCUMENTOS ELECTRONICOS --%>	

	<%--DOCUMENTOS FISICOS --%>
				
		<c:if test="${not empty udocsRelacion}">
		<div class="separador8">&nbsp;</div>
		<div style="margin:5px">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
				<html:img page="/pages/images/docsElectronicos.gif" altKey="archigest.archivo.unidadesDocumentales" titleKey="archigest.archivo.unidadesDocumentales" styleClass="imgTextBottom" />&nbsp;
				<bean:message key="archigest.archivo.unidadesDocumentales" />
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
				<display:table name="pageScope.udocsRelacion" id="unidadDocumental" 
					style="width:99%;margin-left:auto;margin-right:auto"
					sort="list"
					pagesize="10"
					requestURI="<%=vistaRelacionPaginationURI%>" >

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.noUDCajas" />
					</display:setProperty>
				
					<display:column titleKey="archigest.archivo.transferencias.validada" style="text-align:center;width:10%">
						<c:choose>
							<c:when test="${unidadDocumental.validada}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
							<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.nExp" sortable="true" headerClass="sortable"  style="width:20%">
						<c:out value="${unidadDocumental.numeroExpediente}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.asunto" style="width:65%">
						<c:choose>
							<c:when test="${vRelacion.entreArchivos}">
								<c:url var="validarUdocURL" value="/action/gestionUdocsCF">
									<c:param name="method" value="verEnFondos" />
									<c:param name="unidadDocumental" value="${unidadDocumental.id}" />
								</c:url>
							</c:when>
							<c:otherwise>
								<c:url var="validarUdocURL" value="/action/gestionUdocsRelacion">
									<c:param name="method" value="infoUnidadDocumental" />
									<c:param name="udocID" value="${unidadDocumental.id}" />
								</c:url>
							</c:otherwise>
						</c:choose>
						<a href="<c:out value="${validarUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${unidadDocumental.asunto}" />
						</a>
					</display:column>

					<c:if test="${vRelacion.permitidoMostrarBloqueoDesbloqueoExpedientes}">
						<display:column style="text-align: center; width:5%;">
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
				<div class="separador8">&nbsp;</div>						
		</tiles:put>
		</tiles:insert>
		</div>
		</c:if>			
		<%--FIN DOCUMENTOS FISICOS --%>	

		</tiles:put>
		</tiles:insert>	

				
	</tiles:put>
</tiles:insert>