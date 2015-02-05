<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres"%>

<spring:url value="direccion/fisica/delete.action?tercero=${tercero}" var="deleteURI" />
<spring:url value="direccion/fisica/retrieve.action" var="retrieveURI" />

<div class="twocolrightb">
	<div class="column first"><h4><spring:message code="label.form.direccion.fisica.title"/></h4></div>
	<div class="column last textright">
		<p class="botonera">
			<a href="direccion/fisica/new.action?content.tercero.id=${tercero}" id="add" class="add">
				<span><spring:message code="label.form.button.aniadir"/></span>
			</a>
		</p>
	</div>
</div>

<div class="onecol">
	<div id="formularioDireccion" style="width: auto; overflow: auto; position: relative;">
		<display:table name="list" id="direccionFisicaList" >
			<display:column href="${retrieveURI}" paramId="content.id" paramProperty="id" property="direccion" titleKey="label.list.direccion.fisica.direccion" />
			<display:column property="codigoPostal" titleKey="label.list.direccion.fisica.codigoPostal"> </display:column>
			<display:column property="ciudad.nombre" titleKey="label.list.direccion.fisica.ciudad"> </display:column>
			<display:column property="provincia.nombre" titleKey="label.list.direccion.fisica.provincia"> </display:column>
			<display:column property="pais.nombre" titleKey="label.list.direccion.fisica.pais"> </display:column>
			<display:column>
				<c:choose>
					<c:when test="${direccionFisicaList.principal}">
						<span class="principal">
							<spring:message code="label.list.direccion.principal" />
						</span>
					</c:when>
					<c:otherwise>
						<spring:url value="direccion/fisica/changePrincipal.action" var="selectPrincipalURI" >
							<spring:param name="direccion" value="${direccionFisicaList.id}"></spring:param>
							<spring:param name="tercero" value="${direccionFisicaList.tercero.id}"></spring:param>
						</spring:url>
						<a href="${selectPrincipalURI}" class="noprincipal">
							<spring:message code="label.list.direccion.principal" />
						</a>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column href="${deleteURI}" paramId="content.id" paramProperty="id" class="delete">
				<spring:message code="label.link.eliminar" />
			</display:column>
			<display:setProperty name="basic.msg.empty_list">
				<spring:message code="label.list.direccion.fisica.empty" />
			</display:setProperty>
		</display:table>
	</div>
</div>

<script type="text/javascript">
<!--
	jQuery(document).ready(function($) {

		// La respuesta ante el clic de un enlace se muestra en el tab con id ui-tabs-1
		contentLinksInsideTab('#ui-tabs-1');
	});
//-->
</script>