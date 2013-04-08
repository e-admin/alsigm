<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="estadoConErrores"><c:out value="${appConstants.transferencias.estadoREntrega.CON_ERRORES_COTEJO.identificador}"/></c:set>
<c:set var="relacionEntrega" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.cajasRel"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<TD>
					<c:url var="creacionCajaURL" value="/action/asignacionCajas">
						<c:param name="method" value="nuevaCaja" />
						<c:param name="idRelacion" value="${relacionEntrega.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${creacionCajaURL}" escapeXml="false"/>">
						<html:img page="/pages/images/caja_new.gif"
							altKey="archigest.archivo.crear"
							titleKey="archigest.archivo.crear"
							styleClass="imgTextBottom"/>
						&nbsp;<bean:message key="archigest.archivo.crear"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</TD>
			</TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<c:set var="cajasRelacion" value="${sessionScope[appConstants.transferencias.LISTA_CAJAS_KEY]}" />

		<c:if test="${empty cajasRelacion}">
			<div class="bloque">
				<bean:message key="archigest.archivo.transferencias.noCajas"/>
			</div>
		</c:if>

		<c:forEach items="${cajasRelacion}" var="unidadInstalacion" varStatus="nCajas" >
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true"><c:out value="caja${nCajas.count}" /></tiles:put>
			<tiles:put name="blockTitle" direct="true"><c:out value="Caja ${nCajas.count}" /></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<c:if test="${relacionEntrega.puedeSerModificada}">
						<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
						<c:url var="editarURL" value="/action/asignacionCajas">
							<c:param name="method" value="editarCaja" />
							<c:param name="idUnidadInstalacion" value="${unidadInstalacion.id}" />
						</c:url>
						<TD>
							<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarURL}" escapeXml="false"/>'">
								<html:img titleKey="archigest.archivo.editar" altKey="archigest.archivo.editar" page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.editar"/>
							</a>
						</TD>
						<TD width="10">&nbsp;</TD>
						</security:permissions>
					</c:if>
				</TR>
				</TABLE>
			</tiles:put>

			<tiles:put name="dockableContent" direct="true">
				<div class="separador8">&nbsp;</div>

				<c:set var="udocs" value="${unidadInstalacion.contenido}" />

				<display:table name="pageScope.udocs" id="parteUnidadDocumental" style="width:99%;margin-left:auto;margin-right:auto">

					<display:setProperty name="basic.show.header" value="false" />

					<display:setProperty name="basic.msg.empty_list">
						<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
							&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
						</div>
					</display:setProperty>

					<c:if test="${vRelacion.formato.multidoc}">
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
						<c:out value="${parteUnidadDocumental.expediente}" />
						<c:set var="nPartesUdoc" value="${parteUnidadDocumental.totalPartes}" />
						<c:if test="${nPartesUdoc > 1}">
							&nbsp;(<c:out value="${parteUnidadDocumental.numParteUdoc}" />/<c:out value="${nPartesUdoc}" />)
						</c:if>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.asunto">
						<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${parteUnidadDocumental.asunto}" />
						</a>

						<c:if test="${!empty parteUnidadDocumental.descContenido}">
							<br><span class="etiquetaNegra11Normal"><br><b><i><bean:message key="archigest.archivo.transferencias.descContenido"/>:</i></b> <c:out value="${parteUnidadDocumental.descContenido}" /></span>
						</c:if>

						<c:if test="${vRelacion.estado == estadoConErrores}">
							<br><span class="etiquetaNegra11Normal"><br><b><i><bean:message key="archigest.archivo.transferencias.notasCotejo"/>:</i></b> <c:out value="${parteUnidadDocumental.notasCotejo}" /></span>
						</c:if>
					</display:column>

					<c:if test="${unidadInstalacion.revisada || vRelacion.conSignatura}">
						<display:column titleKey="archigest.archivo.transferencias.signatura" style="width:13%">
							<span class="etiquetaAzul11Normal"><b><bean:message key="archigest.archivo.transferencias.signatura" />:</b>&nbsp;
							<c:out value="${parteUnidadDocumental.signaturaUdoc}" /></span>
						</display:column>
					</c:if>

				</display:table>
				<div class="separador8">&nbsp;</div>

			</tiles:put>
		</tiles:insert>
		</c:forEach>
	</tiles:put>
</tiles:insert>