<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.valoracion.aGestionar"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<bean:struts id="mappingGestionValoraciones" mapping="/gestionValoracion" />

		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionValoracion">
	<input type="hidden" name="method">
		<c:set var="listaValoraciones" value="${requestScope[appConstants.valoracion.LISTA_VALORACIONES_KEY]}"/>
		<c:url var="listaValoracionesPaginationURI" value="/action/gestionValoracion">
			<c:param name="method" value="${param.method}" />
		</c:url>
		<jsp:useBean id="listaValoracionesPaginationURI" type="java.lang.String" />

		<div class="bloque">
		<display:table name="pageScope.listaValoraciones"
			id="valoracion" 
			pagesize="15"
			requestURI="<%=listaValoracionesPaginationURI%>"
			sort="list"
			export="false"
			style="width:99%;margin-left:auto;margin-right:auto">

			<display:setProperty name="basic.msg.empty_list">
				<div class="separador8">&nbsp;</div>
				<bean:message key="archigest.archivo.valoraciones.ningunaValoracionAGestionar"/>
				<div class="separador8">&nbsp;</div>
			</display:setProperty>
			

			<display:column titleKey="archigest.archivo.codigo" sortProperty="titulo" sortable="true" headerClass="sortable">
				<c:url var="infoValoracionURL" value="/action/gestionValoracion">
					<c:param name="method" value="verValoracion" />
					<c:param name="id" value="${valoracion.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${infoValoracionURL}" escapeXml="false"/>"><c:out value="${valoracion.titulo}" /></a>
			</display:column>
			<display:column titleKey="archigest.archivo.serie" property="tituloSerie" sortable="true" headerClass="sortable" />
			<display:column titleKey="archigest.archivo.estado" sortProperty="estado" sortable="true" headerClass="sortable">
				<fmt:message key="archigest.archivo.valoracion.estado${valoracion.estado}" />
			</display:column>
			<display:column titleKey="archigest.archivo.valoracion.fEstado" sortProperty="fechaEstado" sortable="true" headerClass="sortable">
				<fmt:formatDate value="${valoracion.fechaEstado}" pattern="${FORMATO_FECHA}"/>
			</display:column>
		</display:table>
		</div>
<div style="display:none;"></html:form></div>
	</tiles:put>
</tiles:insert>

<script>removeCookie('tabSeleccionDatos');</script>