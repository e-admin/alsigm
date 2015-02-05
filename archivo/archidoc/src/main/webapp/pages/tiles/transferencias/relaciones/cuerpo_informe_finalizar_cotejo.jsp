<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.informeCotejo"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<c:url var="cancelURI" value="/action/cotejoysignaturizacionAction">
						<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

	<DIV class="cabecero_bloque_sin_height"> <%-- primer bloque de datos (resumen siempre visible) --%>
		<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
	</DIV> <%-- primer bloque de datos (resumen siempre visible) --%>

	<c:set var="infoAsignacionUdocs" value="${sessionScope[appConstants.transferencias.ASIGNACION_UDOC2UI]}" />

	<c:forEach var="elementolista" items="${infoAsignacionUdocs.unidadesInstalacion}">
	<div class="separador8">&nbsp;</div>

	<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.transferencias.caja" />:&nbsp;<c:out value="${elementolista.orden}"/>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="120px"><bean:message key="archigest.archivo.transferencias.signatura" />:</td>
					<td class="tdDatos"><c:out value="${elementolista.signaturaUI}"/></td>
				</tr>
				<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.transferencias.estadoCotejo" />:</td>
					<td class="tdDatos">
						<c:choose>
							<c:when test="${elementolista.conErrores}">
								<bean:message key="archigest.archivo.transferencias.estadoCotejoConErrores" />
							</c:when>
							<c:when test="${elementolista.revisada}">
								<bean:message key="archigest.archivo.transferencias.estadoCotejoRevisada" />
							</c:when>
							<c:when test="${elementolista.pendiente}">
								<bean:message key="archigest.archivo.transferencias.estadoCotejoPendiente" />
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.transferencias.devolver" />:</td>
					<td class="tdDatos">
						<c:choose>
						<c:when test="${elementolista.devolver}">
							<bean:message key="archigest.archivo.si" />&nbsp;&nbsp;
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.no" />&nbsp;&nbsp;
						</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.transferencias.observaciones" />:</td>
					<td class="tdDatos"><c:out value="${elementolista.notasCotejo}"> -- </c:out></td>
				</tr>
				<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.contenido" /></td>
					<td class="tdDatos">
						<div class="separador8">&nbsp;</div>

						<display:table name='pageScope.elementolista.udocs' 
									style="width:98%;margin-left:auto;margin-right:auto"
									id="parteUdocumental" 
									sort="list"
									export="false">

									<c:set var="vUdoc" value="${parteUdocumental.unidadDocumental}"/>

									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.transferencias.noUDCaja" />
									</display:setProperty>

									<display:column titleKey="archigest.archivo.transferencias.unidadDoc" sortable="false" headerClass="sortable" style="width:60%">
										<c:out value="${vUdoc.numeroExpediente}"/>&nbsp;,
										<c:out value="${vUdoc.asunto}"/>
									</display:column>
									<display:column titleKey="archigest.archivo.signatura" sortable="false" headerClass="sortable" style="width:60px">
										<c:out value="${parteUdocumental.signaturaUdoc}"/>
									</display:column>

									<display:column titleKey="archigest.archivo.transferencias.estado" style="width:60px">
										<c:choose>
											<c:when test="${parteUdocumental.conErrores}">
												<bean:message key="archigest.archivo.transferencias.estadoCotejoConErrores" />
											</c:when>
											<c:when test="${parteUdocumental.revisada}">
												<bean:message key="archigest.archivo.transferencias.estadoCotejoRevisada" />
											</c:when>
											<c:when test="${parteUdocumental.pendiente}">
												<bean:message key="archigest.archivo.transferencias.estadoCotejoPendiente" />
											</c:when>
										</c:choose>
									</display:column>

									<display:column titleKey="archigest.archivo.transferencias.observaciones" >
										<c:out value="${parteUdocumental.notasCotejo}"/>
									</display:column>
						</display:table>					
					</td>
				</tr>
			</table>
		</tiles:put>
	</tiles:insert>
	</c:forEach>
	</tiles:put>
</tiles:insert>