<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres"%>

<spring:url value="direccion/telematica/delete.action?tercero=${tercero}" var="deleteURI" />
<spring:url value="direccion/telematica/retrieve.action" var="retrieveURI" />

<div class="twocolrightb">
	<div class="column first"><h4><spring:message code="label.form.direccion.telematica.title"/></h4></div>
	<div class="column last textright">
		<p class="botonera">
			<a href="direccion/telematica/new.action?content.tercero.id=${tercero}" id="add" class="add">
				<span><spring:message code="label.form.button.aniadir"/></span>
			</a>
		</p>
	</div>
</div>

<div class="onecol">
	<div id="formularioDireccionTelema" style="width: auto; overflow: auto; position: relative;">
		<display:table name="list" id="direccionTelematicaList" >
			<display:column href="${retrieveURI}" paramId="content.id" paramProperty="id" property="tipoDireccionTelematica.descripcion" titleKey="label.list.direccion.telematica.tipo" />
			<display:column property="direccion" titleKey="label.list.direccion.telematica.direccion"> </display:column>
			<display:column>
				<c:choose>
					<c:when test="${direccionTelematicaList.principal}">
						<span class="principal">
							<spring:message code="label.list.direccion.principal" />
						</span>
					</c:when>
					<c:otherwise>
						<spring:url value="direccion/telematica/changePrincipal.action" var="selectPrincipalURI">
							<spring:param name="direccion" value="${direccionTelematicaList.id}"></spring:param>
							<spring:param name="tercero" value="${direccionTelematicaList.tercero.id}"></spring:param>
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
				<spring:message code="label.list.direccion.telematica.empty" />
			</display:setProperty>
		</display:table>
	</div>
</div>

<script type="text/javascript">
<!--
	jQuery(document).ready(function($) {

		// La respuesta ante el clic de un enlace se muestra en el tab con id ui-tabs-2
		contentLinksInsideTab('#ui-tabs-2');
	});
//-->
</script>