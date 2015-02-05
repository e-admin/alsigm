<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionGrupos" mapping="/gestionGrupos" />
<script>
	function eliminarGrupos() {
		var form = document.forms['<c:out value="${mappingGestionGrupos.name}" />'];
		if (form.gruposSeleccionados && elementSelected(form.gruposSeleccionados)) {
			if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmGruposEliminar'/>")) {
				form.submit();
			}
		} else
			alert("<bean:message key='archigest.archivo.cacceso.msgNoGruposEliminar'/>");
	}
</script>
<html:form action="/gestionGrupos">
<input type="hidden" name="method" value="eliminarGrupos">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.listaGrupos" />
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
		<tr>
		<security:permissions action="${appConstants.controlAccesoActions.ALTA_GRUPO}">
			<td nowrap>
				<c:url var="createURL" value="/action/gestionGrupos">
					<c:param name="method" value="altaGrupo" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/newDoc.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.crear"/></a>
			</td>
			<td width="10px"></td>
		</security:permissions>
		<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_GRUPO}">
			<td nowrap>
				<a class="etiquetaAzul12Bold" href="javascript:eliminarGrupos()" >
				<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.eliminar"/></a>
			</td>
			<td width="10px"></td>
		</security:permissions>
		
			
			<c:url var="busquedaURL" value="/action/gestionGrupos">
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
				<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/controlAcceso/GestionGrupos.htm');">
				<html:img page="/pages/images/help.gif" 
					        altKey="archigest.archivo.ayuda" 
					        titleKey="archigest.archivo.ayuda" 
					        styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
			</td>
			<td width="10">&nbsp;</td>
		</c:if>
		<td nowrap>
			<tiles:insert definition="button.closeButton" />	
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
		<c:set var="listaGrupos" value="${requestScope[appConstants.controlAcceso.LISTA_GRUPOS]}" />
		<display:table name="pageScope.listaGrupos" 
				id="grupo" 
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
				requestURI="/action/gestionGrupos"
				pagesize="10">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.cacceso.msgNoGruposSistema" />
			</display:setProperty>
			<security:permissions action="${appConstants.controlAccesoActions.ELIMINAR_GRUPO}">
				<display:column style="width:10px" title="">
						<input type="checkbox" name="gruposSeleccionados" value="<c:out value="${grupo.id}" />" >
				</display:column>
			</security:permissions>			
			<display:column titleKey="archigest.archivo.grupo">
				<c:url var="verURL" value="/action/gestionGrupos">
					<c:param name="method" value="verGrupo" />
					<c:param name="idGrupo" value="${grupo.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
				<c:out value="${grupo.nombre}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.descripcion">
				<c:out value="${grupo.descripcion}" />
			</display:column>
		</display:table>
		</div>

	</tiles:put>
</tiles:insert>
</html:form>

<script>removeCookie('tabsRol');</script>