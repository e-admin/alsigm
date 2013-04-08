<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="elementoNoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_NO_ASIGNABLE_KEY]}"/>
<c:set var="cartelas" value="${sessionScope[appConstants.deposito.LISTA_CARTELAS_KEY]}"/>

<bean:struts id="actionMapping" mapping="/gestionTipoNoAsignableAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.generacionCartelas"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
				<c:if test="${!empty cartelas}">
				<td>
					<a class="etiquetaAzul12Bold" href="<c:url value="/action/cartelasDeposito"/>">
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
		<tiles:insert page="/pages/tiles/deposito/tiponoasignable/datos_tipoNoAsignable.jsp" />
		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.listadoCartelas"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div id="divTbl" style="width:98%;margin-left:5px;padding-left:5px;margin-bottom:5px;">
					<table cellpadding="6" cellspacing="6">
						<tr>
							<c:forEach var="cartela" items="${cartelas}" varStatus="numCartela">
							<td width="70px" style="border:1px solid black">
								<c:out value="${cartela.signaturaUI}">
									<c:out value="${cartela.numorden}" />
								</c:out>
							</td>
							<c:if test="${numCartela.count % 8 == 0}">
							</tr><tr>
							</c:if>
							</c:forEach>
						</tr>
					</table>
				</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>