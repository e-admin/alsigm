<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.previsiones"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
	<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

	<security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ORGANO_REMITENTE}">

		<c:set var="listaPrevisiones" value="${requestScope[appConstants.transferencias.LISTA_PREVISIONES_EN_ELABORACION_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.previsionesElaboracion"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaPrevisiones"
				id="prevision" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noPrev"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.transferencias.prevision">
					<c:url var="verPrevisionURL" value="/action/gestionPrevisiones">
						<c:param name="method" value="verprevision" />
						<c:param name="idprevision" value="${prevision.id}" />
					</c:url>
					<a class="tdlink" href='<c:out value="${verPrevisionURL}" escapeXml="false"/>' >
						<c:out value="${prevision.codigoTransferencia}"/>
					</a>
				</display:column>
				
				<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />		
				</display:column>
				
				<display:column titleKey="archigest.archivo.transferencias.estado" >
					<fmt:message key="archigest.archivo.transferencias.estadoPrevision.${prevision.estado}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.fEstado">
					<fmt:formatDate value="${prevision.fechaestado}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.FIT">
					<fmt:formatDate value="${prevision.fechainitrans}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.FFT">
					<fmt:formatDate value="${prevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaPrevisiones[0]}">
		<div class="pie_bloque_right">
			<c:url var="previsionesEnElaboracionURL" value="/action/gestionPrevisiones">
				<c:param name="method" value="listadooficina" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${previsionesEnElaboracionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		<div class="separador20"></div>
	</security:permissions>
	<security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ARCHIVO_RECEPTOR}">
		<c:set var="listaPrevisiones" value="${requestScope[appConstants.transferencias.LISTA_PREVISIONES_A_GESTIONAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.previsionesGestionar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.listaPrevisiones"
				id="prevision" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noPrev"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.transferencias.prevision">
					<c:url var="verPrevisionURL" value="/action/gestionPrevisiones">
						<c:param name="method" value="verprevision" />
						<c:param name="idprevision" value="${prevision.id}" />
					</c:url>
					<a class="tdlink" href='<c:out value="${verPrevisionURL}" escapeXml="false"/>' >
						<c:out value="${prevision.codigoTransferencia}"/>
					</a>
				</display:column>
				
				<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />		
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.estado" >
					<fmt:message key="archigest.archivo.transferencias.estadoPrevision.${prevision.estado}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.fEstado">
					<fmt:formatDate value="${prevision.fechaestado}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.FIT">
					<fmt:formatDate value="${prevision.fechainitrans}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.FFT">
					<fmt:formatDate value="${prevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
				</display:column>
			</display:table>

			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty listaPrevisiones[0]}">
		<div class="pie_bloque_right">
			<c:url var="previsionesAGestionarURL" value="/action/gestionPrevisiones">
				<c:param name="method" value="listadoarchivo" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${previsionesAGestionarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
	</security:permissions>

	</tiles:put>
</tiles:insert>