<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.valoracion.buscar.listado"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>
		<script>
			<bean:struts id="mappingGestionValoraciones" mapping="/gestionValoracion" />

			function eliminarValoraciones() {
				var formularioSeleccion = document.forms['<c:out value="${mappingGestionValoraciones.name}" />'];
				var nSelected=FormsToolKit.getNumSelectedChecked(formularioSeleccion,"valoracionSeleccionada");
				if(nSelected >= 1) {
					if (confirm("<bean:message key='archigest.archivo.valoracion.msg.warningDelete'/>")) {   
						formularioSeleccion.method.value="eliminarValoraciones";
						formularioSeleccion.submit();
					}
				} else
						alert("<bean:message key='archigest.archivo.valoracion.eliminacionMinimoUna'/>");
			}
		</script>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.fondosActions.EDITAR_VALORACIONES_ACTION}">
				<td nowrap>
					<c:url var="nuevaValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="nuevaValoracion" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${nuevaValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.valoracion.iniValoracion" titleKey="archigest.archivo.valoracion.iniValoracion" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.iniValoracion"/></a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:eliminarValoraciones()" >
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
	<html:form action="/gestionValoracion">
	<input type="hidden" name="method">
		<c:set var="listaValoraciones" value="${requestScope[appConstants.valoracion.LISTA_VALORACIONES_KEY]}"/>
		<c:url var="listaValoracionesPaginationURI" value="/action/buscarValoracion">
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
			style="width:99%;margin-left:auto;margin-right:auto"
			defaultorder="descending"
			defaultsort="2">

			<display:setProperty name="basic.msg.empty_list">
				<div class="separador8">&nbsp;</div>
				<bean:message key="archigest.archivo.valoraciones.ningunaValoracion"/>
				<div class="separador8">&nbsp;</div>
			</display:setProperty>
			
			<security:permissions action="${appConstants.fondosActions.EDITAR_VALORACIONES_ACTION}">
			<display:column style="width:15px">
				<c:if test="${valoracion.abierta || valoracion.rechazada}">
					<input type="checkbox" name="valoracionSeleccionada" value="<c:out value="${valoracion.id}"/>" >
				</c:if>
			</display:column>
			</security:permissions>

			<display:column titleKey="archigest.archivo.codigo" sortable="true" sortProperty="codigo" headerClass="sortable">
				<c:url var="infoValoracionURL" value="/action/gestionValoracion">
					<c:param name="method" value="verValoracion" />
					<c:param name="id" value="${valoracion.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${infoValoracionURL}" escapeXml="false"/>"><c:out value="${valoracion.titulo}" /></a>
			</display:column>

			<display:column titleKey="archigest.archivo.serie" property="tituloSerie" sortable="true" headerClass="sortable"/>
			<display:column titleKey="archigest.archivo.valoracion.estadoValoracion" sortProperty="estado" sortable="true" headerClass="sortable">
				<fmt:message key="archigest.archivo.valoracion.estado${valoracion.estado}" />
			</display:column>


		</display:table>
		</div>
<div style="display:none;"></html:form></div>
	</tiles:put>
</tiles:insert>