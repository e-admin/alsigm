<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionListaAcceso" mapping="/gestionListasAcceso" />
<c:url var="urlActionListaAcceso" value="/action/gestionListasAcceso" />
<c:set var="actionListaAcceso" value="/action/gestionListasAcceso" />
<c:set var="formBean" value="${sessionScope[mappingGestionListaAcceso.name]}" />
<c:set var="tipoElemento" value="${formBean.tipoElementoSeleccionado}" />
<c:set var="vListaAcceso" value="${sessionScope[appConstants.controlAcceso.INFO_LISTA_ACCESO]}" />
<c:set var="modoVista" value="${param.modoVista}">false</c:set>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.msgSelPermisosListaAcceso"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<script>
			function guardar() {
				var form = document.forms['<c:out value="${mappingGestionListaAcceso.name}" />'];
				form.method.value = 'agregarElementoALista';
				form.submit();
			}
		</script>
		<table><tr>
		<c:if test="${!param.disableBackButton}">
			<td nowrap>
				<c:url var="goBackURL" value="/action/cesionPrestamos">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${goBackURL}" escapeXml="false"/>">
				<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.atras"/>
				</a>
			</td>
			<td width="10px"></td>
		</c:if>

		<c:if test="${!modoVista}">
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:guardar()" >
			<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
		</td>		
		<td width="10px"></td>
		</c:if>
		<td nowrap>
			<c:url var="cancelURL" value="${actionListaAcceso}">
				<c:param name="method" value="goReturnPoint" />
			</c:url>
			<c:choose>
			<c:when test="${!modoVista}">
				<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
			</c:when>
			<c:when test="${modoVista}">
				<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
			</c:when>
			</c:choose>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">


	<c:set var="destinatario" value="${sessionScope[appConstants.controlAcceso.DESTINATARIO_LISTA]}" />
	<html:form action="/gestionListasAcceso">
	<input type="hidden" name="method" value="agregarElementoALista">
	<div>
		<div class="separador8">&nbsp;</div>

		<div class="cabecero_bloque" style="height:auto">
			<table width="98%" cellpadding="5" cellspacing="2">
				<tr height="20">
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.cacceso.listaAcceso"/>:
				</td>
				<td class="etiquetaNegra12Normal">
					<c:out value="${vListaAcceso.nombre}" />
				</td>
				</tr>
				<tr height="20">
				<td class="etiquetaAzul12Bold">
					<c:choose>
						<c:when test="${formBean.tipoElementoUsuario}">
							<bean:message key="archigest.archivo.usuario"/>:
							<c:set var="nombreDestinatario" value="${destinatario.nombreCompleto}" />
						</c:when>
						<c:when test="${formBean.tipoElementoGrupo}">
							<bean:message key="archigest.archivo.grupo"/>:
							<c:set var="nombreDestinatario" value="${destinatario.nombre}" />
						</c:when>
						<c:when test="${formBean.tipoElementoOrgano}">
							<bean:message key="archigest.archivo.organo"/>:
							<c:set var="nombreDestinatario" value="${destinatario.nombreLargo}" />
						</c:when>
					</c:choose>
				</td>
				<td class="etiquetaNegra12Normal">
					<c:out value="${nombreDestinatario}" />
				</td>
			</tr></table>
		</div>

		<div class="separador8">&nbsp;</div>

		<div class="cabecero_bloque">
			<table class="w98" cellpadding=0 cellspacing=0><tr>
			<td class="etiquetaAzul12Bold">
				<bean:message key="archigest.archivo.cacceso.permisos"/>
			</td>		
			</tr></table>
		</div>
		<div class="bloque">
		<div class="separador8">&nbsp;</div>

		<c:set var="listaPermisos" value="${formBean.listaPermisos}" />
		<display:table name="pageScope.listaPermisos" 
					id="permiso" 
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.cacceso.msgNoPermisosListaAcceso"/>
				</display:setProperty>
				<c:choose>
					<c:when test="${!modoVista}">
						<display:column style="width:15px">
							<c:choose>
								<c:when test="${formBean.valoresPermisos[permiso]}"> 
									<input type="checkbox" name="permisosSeleccionados" value="<c:out value="${permiso}" />" checked>
								</c:when>
								<c:otherwise> 
									<input type="checkbox" name="permisosSeleccionados" value="<c:out value="${permiso}" />">
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column style="width:15px">
							<c:choose>
								<c:when test="${formBean.valoresPermisos[permiso]}"> 
									<html:img page="/pages/images/checkbox-yes.gif" styleClass="imgTextMiddle" />
								</c:when>
								<c:otherwise> 
									<html:img page="/pages/images/checkbox-no.gif" styleClass="imgTextMiddle" />
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:otherwise>
				</c:choose>
				
				<display:column titleKey="archigest.archivo.nombre">
					<c:set var="permisoKey">archigest.archivo.permiso.<c:out value="${permiso}"/></c:set>
						<fmt:message key="${permisoKey}" />
				</display:column>
		</display:table>
		<div class="separador8">&nbsp;</div>
		</div>
	</div>
<div style="display:none;"></html:form></div>
</tiles:put>
</tiles:insert>
