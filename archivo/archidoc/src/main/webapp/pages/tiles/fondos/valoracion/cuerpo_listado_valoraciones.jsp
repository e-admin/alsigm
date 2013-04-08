<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="listaValoraciones" value="${requestScope[appConstants.valoracion.LISTA_VALORACIONES_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.valoracion.enElaboracion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>
		<script>
			<bean:struts id="mappingGestionValoraciones" mapping="/gestionValoracion" />

			function eliminarValoraciones() {
				var formularioSeleccion = document.forms['<c:out value="${mappingGestionValoraciones.name}" />'];
				var nSelected=FormsToolKit.getNumSelectedChecked(formularioSeleccion,"valoracionSeleccionada");
				if(nSelected >= 1) {
					if (confirm("<fmt:message key='archigest.archivo.eliminacion.valoracion.msg.warningDelete'/>")) {   
						formularioSeleccion.method.value="eliminarValoraciones";
						formularioSeleccion.submit();
					}
				} else
						alert("<bean:message key='archigest.archivo.valoracion.eliminacionMinimoUna'/>");
			}
		</script>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<c:url var="nuevaValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="nuevaValoracion" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${nuevaValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/new.gif" altKey="archigest.archivo.iniciar" titleKey="archigest.archivo.iniciar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.iniciar"/></a>
				</td>
				<td width="10">&nbsp;</td>
				<c:if test="${!empty listaValoraciones}">
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:eliminarValoraciones()" >
					<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
				</td>
				<td width="10">&nbsp;</td>
				</c:if>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionValoracion">
	<input type="hidden" name="method">
		<div class="bloque">
		<display:table name="pageScope.listaValoraciones"
			id="valoracion" 
			pagesize="10"
			requestURI="/action/gestionValoracion"			
			sort="list"
			export="false"
			style="width:99%;margin-left:auto;margin-right:auto">

			<display:setProperty name="basic.msg.empty_list">
				<div class="separador8">&nbsp;</div>
				<bean:message key="archigest.archivo.valoraciones.ningunaValoracionEnElaboracion"/>
				<div class="separador8">&nbsp;</div>
			</display:setProperty>
			
			<display:column style="width:15px">
				<c:if test="${valoracion.abierta || valoracion.rechazada}">
					<input type="checkbox" name="valoracionSeleccionada" value="<c:out value="${valoracion.id}"/>" >
				</c:if>
			</display:column>

			<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" sortable="true" headerClass="sortable">
				<c:url var="infoValoracionURL" value="/action/gestionValoracion">
					<c:param name="method" value="verValoracion" />
					<c:param name="id" value="${valoracion.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${infoValoracionURL}" escapeXml="false"/>"><c:out value="${valoracion.titulo}" /></a>
			</display:column>
			<display:column titleKey="archigest.archivo.serie">
				<c:out value="${valoracion.serie.codReferencia}" />
				<c:out value="${valoracion.serie.titulo}" />
			</display:column>

			<display:column titleKey="archigest.archivo.estado">
				<fmt:message key="archigest.archivo.valoracion.estado${valoracion.estado}" />
			</display:column>
		</display:table>
		</div>
		<div style="display:none;"></html:form></div>
	</tiles:put>
</tiles:insert>

<script>removeCookie('tabSeleccionDatos');</script>