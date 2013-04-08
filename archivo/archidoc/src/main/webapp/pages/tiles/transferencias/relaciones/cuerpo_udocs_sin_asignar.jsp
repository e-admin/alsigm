<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="estadoConErrores"><c:out value="${appConstants.transferencias.estadoREntrega.CON_ERRORES_COTEJO.identificador}"/></c:set>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="codigoRelacion"><c:out value="${vRelacion.codigoTransferencia}" /></c:set>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		Cajas Relación
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:guardarCambios(document.forms[0]);" >
					<html:img page="/pages/images/save.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
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
		<html:form action="/asignacionCajas">
		<input type="hidden" name="method" value="incorporarACaja" />

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingAsignacionCajas.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<tiles:insert definition="button.closeButton">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
				</tiles:insert>
			</TD>
		</TR>
		</TABLE>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador8">&nbsp;</div>

				<c:set var="udocs" value="${unidadInstalacion.contenido}" />

				<display:table name="pageScope.udocs" id="parteUnidadDocumental" style="width:99%;margin-left:auto;margin-right:auto">

					<display:setProperty name="basic.show.header" value="false" />

					<display:setProperty name="basic.msg.empty_list">
						<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
							&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
						</div>
					</display:setProperty>
			<display:column>
				<input type="checkbox" name="udocSeleccionada"
				value='<c:out value="${parteUnidadDocumental.posUdocEnUI}"/>'>
			</display:column>

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
							<br><span class="etiquetaNegra11Normal"><br><b><i>Descripcion de contenido:</i></b> <c:out value="${parteUnidadDocumental.descContenido}" /></span>
						</c:if>

						<c:if test="${vRelacion.estado == estadoConErrores}">
							<br><span class="etiquetaNegra11Normal"><br><b><i>Notas de cotejo:</i></b> <c:out value="${parteUnidadDocumental.notasCotejo}" /></span>
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
			</html:form>
	</tiles:put>
</tiles:insert>