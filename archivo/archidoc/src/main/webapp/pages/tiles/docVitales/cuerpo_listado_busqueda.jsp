<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="documentosVitales" value="${requestScope[appConstants.documentosVitales.DOCUMENTOS_VITALES_KEY]}"/>

<bean:struts id="actionMapping" mapping="/buscarDocumentosVit" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.docvitales.busqueda.listado.caption"/>
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
		<div class="bloque">		
			<display:table name="pageScope.documentosVitales"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="documentoVital" 
				pagesize="15"
				requestURI="/action/buscarDocumentosVit"
				export="true">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.docvitales.busqueda.listado.empty"/>
				</display:setProperty>
				<display:column titleKey="archigest.archivo.docvitales.docVital.nombre" property="identidad" sortable="true" headerClass="sortable"/>
				<display:column titleKey="archigest.archivo.docvitales.docVital.identificacion" property="numIdentidad" sortable="true" headerClass="sortable"/>
				<display:column titleKey="archigest.archivo.docvitales.docVital.tipoDocumento" sortProperty="nombreTipoDocVit" sortable="true" headerClass="sortable" media="html">
					<c:url var="docURL" value="/action/gestionDocumentosVitales">
						<c:param name="method" value="verDocVital"/>
						<c:param name="id" value="${documentoVital.id}"/>
					</c:url>
					<a class="tdlink" href="<c:out value="${docURL}" escapeXml="false"/>">
						<c:out value="${documentoVital.nombreTipoDocVit}"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.docvitales.docVital.tipoDocumento" property="nombreTipoDocVit" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.docvitales.docVital.estado" sortProperty="estado" sortable="true" headerClass="sortable">
					<fmt:message key="archigest.archivo.docvitales.estado.${documentoVital.estadoDocVit}" />
				</display:column>
				<display:column titleKey="archigest.archivo.docvitales.docVital.fechaCaducidad" sortProperty="fechaCaducidad" sortable="true" headerClass="sortable">
					<fmt:formatDate value="${documentoVital.fechaCad}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.docvitales.docVital.numAccesos" property="numAccesos" sortable="true" headerClass="sortable"/>
				<display:column titleKey="archigest.archivo.docvitales.docVital.fechaUltimoAcceso" sortProperty="fechaUltimoAcceso" sortable="true" headerClass="sortable">
					<fmt:formatDate value="${documentoVital.fechaUltAcceso}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.docvitales.docVital.observaciones" property="observaciones" maxLength="100"/>
			</display:table>  
		</div>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
</tiles:insert>
