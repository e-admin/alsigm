<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="documentosVitales" value="${requestScope[appConstants.documentosVitales.DOCUMENTOS_VITALES_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.docvitales.gestion.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.docvitales.gestion.pendientes.caption"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>
				<display:table name="pageScope.documentosVitales"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="documentoVital">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.docvitales.busqueda.listado.empty"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.docvitales.docVital.nombre" property="identidad"/>
					<display:column titleKey="archigest.archivo.docvitales.docVital.identificacion" property="numIdentidad"/>
					<display:column titleKey="archigest.archivo.docvitales.docVital.tipoDocumento">
						<c:url var="docURL" value="/action/gestionDocumentosVitales">
							<c:param name="method" value="verDocVital"/>
							<c:param name="id" value="${documentoVital.id}"/>
						</c:url>
						<a class="tdlink" href="<c:out value="${docURL}" escapeXml="false"/>">
							<c:out value="${documentoVital.nombreTipoDocVit}"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.docvitales.docVital.estado">
						<fmt:message key="archigest.archivo.docvitales.estado.${documentoVital.estadoDocVit}" />
					</display:column>
					<display:column titleKey="archigest.archivo.docvitales.docVital.fechaCaducidad">
						<fmt:formatDate value="${documentoVital.fechaCad}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
					<display:column titleKey="archigest.archivo.docvitales.docVital.numAccesos" property="numAccesos"/>
					<display:column titleKey="archigest.archivo.docvitales.docVital.fechaUltimoAcceso">
						<fmt:formatDate value="${documentoVital.fechaUltAcceso}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
					<display:column titleKey="archigest.archivo.docvitales.docVital.observaciones" property="observaciones" maxLength="100"/>
				</display:table>
				<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty documentosVitales[0]}">
		<div class="pie_bloque_right">
			<c:url var="masDocumentosURL" value="/action/gestionDocumentosVitales">
				<c:param name="method" value="documentosPendientes" />
			</c:url>
			<a class="etiq_pie_bloque" 
				href="<c:out value="${masDocumentosURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" 
					styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		<div class="separador20"></div>
	</tiles:put>
</tiles:insert>
