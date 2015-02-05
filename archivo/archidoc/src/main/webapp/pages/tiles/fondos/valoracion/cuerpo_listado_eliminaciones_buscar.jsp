<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.eliminacion.buscar.listado"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>
		<script>
			<bean:struts id="mappingGestionEliminaciones" mapping="/gestionEliminacion" />

			function eliminarEliminaciones() {
				var formularioSeleccion = document.forms['<c:out value="${mappingGestionEliminaciones.name}" />'];
				var nSelected=FormsToolKit.getNumSelectedChecked(formularioSeleccion,"eliminacionSeleccionada");
				if(nSelected >= 1) {
					if (confirm("<bean:message key='archigest.archivo.eliminacion.msg.warningDelete'/>")) {   
						formularioSeleccion.method.value="eliminarEliminaciones";
						formularioSeleccion.submit();
					}
				} else
						alert("<bean:message key='archigest.archivo.eliminacion.minimoUna'/>");
			}
		</script>
		<table>
			<tr>
				<security:permissions action="${appConstants.fondosActions.EDITAR_SELECCIONES_ACTION}">
				<td nowrap>
					<c:url var="nuevaEliminacionURL" value="/action/gestionEliminacion">
						<c:param name="method" value="nuevaEliminacion" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${nuevaEliminacionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.crear"/></a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:eliminarEliminaciones()" >
					<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionEliminacion">
	<input type="hidden" name="method">
		<c:set var="listaEliminaciones" value="${requestScope[appConstants.valoracion.LISTA_ELIMINACIONES_KEY]}"/>
		<c:url var="listaEliminacionesPaginationURI" value="/action/buscarEliminacion">
			<c:param name="method" value="${param.method}" />
		</c:url>
		<jsp:useBean id="listaEliminacionesPaginationURI" type="java.lang.String" />

		<div class="bloque">
		<display:table name="pageScope.listaEliminaciones"
			id="eliminacion" 
			pagesize="15"
			requestURI="<%=listaEliminacionesPaginationURI%>"			
			sort="list"
			export="false"
			style="width:99%;margin-left:auto;margin-right:auto"
			defaultorder="descending"
			defaultsort="2">

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.valoraciones.ningunaEliminacion"/>
			</display:setProperty>
			
			<security:permissions action="${appConstants.fondosActions.EDITAR_SELECCIONES_ACTION}">
			<display:column style="width:15px">
				<c:if test="${eliminacion.abierta || eliminacion.rechazada}">
					<input type="checkbox" name="eliminacionSeleccionada" value="<c:out value="${eliminacion.id}"/>" >
				</c:if>
			</display:column>
			</security:permissions>

			<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" sortable="true" headerClass="sortable">
				<c:url var="infoValoracionURL" value="/action/gestionEliminacion">
					<c:param name="method" value="verEliminacion" />
					<c:param name="id" value="${eliminacion.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${infoValoracionURL}" escapeXml="false"/>"><c:out value="${eliminacion.titulo}" /></a>
			</display:column>
			<display:column titleKey="archigest.archivo.serie" sortProperty="tituloSerie" sortable="true" headerClass="sortable">
				<c:out value="${eliminacion.tituloSerie}" />
			</display:column>
			<display:column titleKey="archigest.archivo.valoracion" sortProperty="codigoValoracion" sortable="true" headerClass="sortable">
				<c:out value="${eliminacion.tituloValoracion}" />
			</display:column>
			<display:column titleKey="archigest.archivo.estado" sortProperty="estado" sortable="true" headerClass="sortable">
				<fmt:message key="archigest.archivo.eliminacion.estado${eliminacion.estado}" />
			</display:column>
		</display:table>
		</div>
	</html:form>
	</tiles:put>
</tiles:insert>