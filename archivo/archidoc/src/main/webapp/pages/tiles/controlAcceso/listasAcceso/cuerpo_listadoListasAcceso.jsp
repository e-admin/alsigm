<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionListasAcceso" mapping="/gestionListasAcceso" />
<c:set var="tiposLista" value="${appConstants.tiposListasAcceso}" />

<script>
	function eliminarListasAcceso() {
		var form = document.forms['<c:out value="${mappingGestionListasAcceso.name}" />'];
		if (form.listasAccesoSeleccionadas && elementSelected(form.listasAccesoSeleccionadas)) {
			if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmListasEliminar'/>")) {
				form.submit();
			}
		} else
			alert("<bean:message key='archigest.archivo.cacceso.msgNoListasAccesoEliminar'/>");
	}
</script>

<html:form action="/gestionListasAcceso">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.listasAcceso"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table><tr>
		<security:permissions action="${appConstants.controlAccesoActions.ALTA_LISTA_ACCESO}">
			<td nowrap>
				<c:url var="createURL" value="/action/gestionListasAcceso">
					<c:param name="method" value="altaListaAcceso" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/newDoc.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.crear"/></a>
			</td>
			<td width="10px"></td>
		</security:permissions>

		<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_LISTA_ACCESO}">	
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:eliminarListasAcceso()" >
				<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.eliminar"/></a>
			</td>
			<td width="10px"></td>
		</security:permissions>
		
			<c:url var="busquedaURL" value="/action/gestionListasAcceso">
				<c:param name="method" value="verBuscador" />
			</c:url>
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="<c:out value="${busquedaURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.buscar"/></a>
			</td>
			<td width="10px"></td>
			
		<c:if test="${appConstants.configConstants.mostrarAyuda}">
			<td>
				<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
				<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
				<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/controlAcceso/GestionListaAcceso.htm');">
				<html:img page="/pages/images/help.gif" 
					        altKey="archigest.archivo.ayuda" 
					        titleKey="archigest.archivo.ayuda" 
					        styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
			</td>
			<td width="10">&nbsp;</td>
		</c:if>
		<td nowrap>
			<c:url var="cancelURL" value="/action/gestionListasAcceso">
				<c:param name="method" value="goBack" />
			</c:url>

			<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
			<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<input type="hidden" name="method" value="eliminarListasAcceso">
		<div class="bloque">
		<c:set var="listaListasAcceso" value="${requestScope[appConstants.controlAcceso.LISTA_LISTAS_ACCESO]}" />
		<display:table name="pageScope.listaListasAcceso" 
				id="listaAcceso" 
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
				requestURI="/action/gestionListasAcceso"
				pagesize="10">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.cacceso.msgNoListasAcceso"/>
			</display:setProperty>

			<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_LISTA_ACCESO}">	
				<display:column style="width:10px" title="">
						<input type="checkbox" name="listasAccesoSeleccionadas" value="<c:out value="${listaAcceso.id}" />" >
				</display:column>
			</security:permissions>	
			<display:column titleKey="archigest.archivo.nombre">
				<c:url var="verURL" value="/action/gestionListasAcceso">
					<c:param name="method" value="verListaAcceso" />
					<c:param name="idListaAcceso" value="${listaAcceso.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
				<c:out value="${listaAcceso.nombre}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.tipo">
				<c:choose>
					<c:when test="${listaAcceso.tipo==tiposLista['ELEMENTO_CUADRO_CLASIFICACION']}">
						<bean:message key="archigest.archivo.elementoDelCuadro"/>
					</c:when> 
					<c:when test="${listaAcceso.tipo==tiposLista['DESCRIPTOR']}">
						<bean:message key="archigest.archivo.descriptor"/>
					</c:when> 
					<c:when test="${listaAcceso.tipo==tiposLista['FORMATO_FICHA']}">
						<bean:message key="archigest.archivo.formatoFicha"/>
					</c:when> 
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.descripcion">
				<c:out value="${listaAcceso.descripcion}" />
			</display:column>
		</display:table>
		</div>

	</tiles:put>
</tiles:insert>
</html:form>