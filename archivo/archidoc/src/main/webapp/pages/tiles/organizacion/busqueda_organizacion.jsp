<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="actionMapping" mapping="/gestionOrganizacionAction" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<c:set var="organizaciones" value="${requestScope[appConstants.organizacion.LISTA_ORGANIZACIONES_KEY]}" />
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/clean_campos_busqueda.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/busquedasUtils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function cleanForm()
	{
		var form = document.forms['<c:out value="${actionMappingName}" />'];

		cleanCodigoOrganizacion(form);
		cleanNombreOrganizacion(form);
		cleanTipoOrganizacion(form);
		cleanEstadoOrganizacion(form);
		cleanUsuarioOrganizacion(form);
	}

	function buscar() {
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		form.submit();
	}

	function buscarUsuario() {
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		form.method.value='verBuscadorUsuario';
		form.submit();
	}
</script>

<html:form action="/gestionOrganizacionAction">
<input type="hidden" name="method" value="buscarOrganizacion">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="organizacion.org.buscar"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table cellspacing=0 cellpadding=0><tr>
		<td>
			<a class="etiquetaAzul12Bold" href="javascript:cleanForm()">
				<html:img page="/pages/images/clear0.gif" altKey="organizacion.limpiar"
			        titleKey="organizacion.limpiar" styleClass="imgTextMiddle"/>&nbsp;
			        <bean:message key="organizacion.limpiar"/>
			</a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
			<html:img page="/pages/images/buscar.gif" altKey="organizacion.buscar" titleKey="organizacion.buscar" styleClass="imgTextMiddle" />
			<bean:message key="organizacion.buscar"/></a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="false"/>
		</td>
	</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="120px">
							<bean:message key="organizacion.org.codigo"/>:
						</td>
						<td class="tdDatos">
							<html:text property="codigo" styleId="codigo" styleClass="input60"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="organizacion.org.nombre"/>:
						</td>
						<td class="tdDatos">
							<html:text property="nombre" styleId="nombre" styleClass="input60"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="organizacion.org.tipo"/>:
						</td>
						<td class="tdDatos">
							<html:select property="tipo" styleId="tipo">
								<html:option value="">--</html:option>
								<html:option key="organizacion.org.tipo.institucion" value="1"/>
								<html:option key="organizacion.org.tipo.organo" value="2"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="organizacion.org.estado"/>:
						</td>
						<td class="tdDatos">
							<html:select property="estado" styleId="estado">
								<html:option value="">--</html:option>
								<html:option key="organizacion.org.estado.borrador" value="1"/>
								<html:option key="organizacion.org.estado.vigente" value="2"/>
								<html:option key="organizacion.org.estado.historico" value="3"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="organizacion.user.usuario"/>:
						</td>
						<td class="tdDatos">
							<div>
							<html:text property="searchTokenUsuario" styleId="nombreUsuario" styleClass="input60"/>
							<a class="etiquetaAzul12Bold" href="javascript:buscarUsuario()">
								<html:img styleId="imgExpand" page="/pages/images/expand.gif" styleClass="imgTextMiddle" />
							</a>
							</div>
						</td>
					</tr>
				</table>
		</div>

		<div class="separador8">&nbsp;</div>

		<c:if test="${organizaciones != null}">

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
			<tiles:put name="blockTitle" direct="true"><bean:message key="organizacion.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

			<display:table name="pageScope.organizaciones"
					id="organizacion"
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI="../../action/gestionOrganizacionAction"
					pagesize="10">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="organizacion.org.busquedasinresultado"/>
				</display:setProperty>
				<display:column titleKey="organizacion.org.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" style="width:150">
					<c:out value="${organizacion.codigo}"/>
				</display:column>
				<display:column titleKey="organizacion.org.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
					<c:url var="verURL" value="/action/manageVistaOrganizacion">
						<c:param name="actionToPerform" value="goHome" />
						<c:param name="origen" value="busqueda" />
						<c:param name="idOrganizacion" value="${organizacion.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" target="_self" >
						<c:out value="${organizacion.nombre}"/>
					</a>
				</display:column>
				<display:column titleKey="organizacion.org.tipo" sortProperty="tipo" sortable="true" headerClass="sortable">
					<c:choose>
						<c:when test="${organizacion.tipo == 1}">
							<c:set var="tipo" value="institucion" />
						</c:when>
						<c:otherwise>
							<c:set var="tipo" value="organo" />
						</c:otherwise>
					</c:choose>
					<fmt:message key="organizacion.org.tipo.${tipo}"/>
				</display:column>
				<display:column titleKey="organizacion.org.estado" sortProperty="estado" sortable="true" headerClass="sortable">
					<c:set var="estado" value="${organizacion.estado}" />
					<fmt:message key="organizacion.org.estado${estado}"/>
				</display:column>
			</display:table>
			</tiles:put>
		</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>