<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>


<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:set var="cartelas" value="${requestScope[appConstants.transferencias.LISTA_CARTELAS]}"/>
<c:set var="selCajas" value="${param['selCajas']}"/>
<c:set var="cajas" value="${param['cajas']}"/>

<bean:struts id="actionMapping" mapping="/gestionRelaciones" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.generarCartelas"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
				<c:if test="${!empty cartelas}">
				<td>
					<c:choose>
						<c:when test="${vRelacion.signaturizada || vRelacion.recibida || vRelacion.validada}">
							<c:set var="tipoCartela" value="2" />
						</c:when>
						<c:otherwise>
							<c:set var="tipoCartela" value="1" />
						</c:otherwise>
					</c:choose>
					<c:url var="URL" value="/action/cartelas">
						<c:param name="idRelacion" value="${vRelacion.id}" />
						<c:param name="cajas" value="${cajas}" />
						<c:param name="tipo" value="${tipoCartela}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${URL}" escapeXml="false"/>">
					   <html:img page="/pages/images/documentos/doc_pdf.gif"
							border="0" altKey="archigest.archivo.abrir" titleKey="archigest.archivo.abrir" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.abrir"/></a>
				</td>
				<td width="10px">&nbsp;</td>
				</c:if>
				<td nowrap="nowrap">
					<tiles:insert definition="button.closeButton" flush="true" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.fondos.datos.ingreso.directo"/></tiles:put>
				</c:otherwise>
			</c:choose>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:choose>
					<c:when test="${!vRelacion.isIngresoDirecto}">
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
					</c:when>
					<c:otherwise>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_ingreso.jsp" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.listadoCartelas"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<div id="divTbl" style="width:98%;margin-left:5px;padding-left:5px;margin-bottom:5px;">
				<table cellpadding="6" cellspacing="6"><tr>
				<c:forEach var="cartela" items="${cartelas}" varStatus="numCartela">
					<td width="70px" style="border:1px solid black"><c:out value="${cartela.signaturaUI}"><c:out value="${cartela.orden}" /></c:out></td>
					<c:if test="${numCartela.count % 8 == 0}"></tr><tr></c:if>
				</c:forEach>
				</tr></table>
			</div>
		</tiles:put>
		</tiles:insert>


	</tiles:put>
</tiles:insert>