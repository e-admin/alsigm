<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="infoRol" value="${sessionScope[appConstants.controlAcceso.INFO_ROL]}" />

<bean:struts id="mappingGestionRoles" mapping="/gestionRoles" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.datosPerfil"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
	<script>
		function crearRol() {
			var form = document.forms['<c:out value="${mappingGestionRoles.name}" />'];
			form.method.value = 'guardarRol';
			form.submit();
		}
	</script>
		<table><tr>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:crearRol()" >
			<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
		</td>		
		<td width="10px"></td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true">
				<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
				<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
			</tiles:insert>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionRoles">		
		<div class="bloque">
			<input type="hidden" name="method">
			<c:set var="formBean" value="${requestScope[mappingGestionRoles.name]}" />
			<c:if test="${!empty formBean.idRol}">
				<html:hidden property="idRol" />
			</c:if>
			<table class="formulario" width="99%">
				<tr>
					<td class="tdTitulo" width="120px">
						<bean:message key="archigest.archivo.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos"><html:text styleClass="input60" property="nombre" maxlength="64" /></td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.modulo"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:set var="listaModulos" value="${sessionScope[appConstants.controlAcceso.LISTA_MODULOS]}" />
						<c:choose>
						<c:when test="${!empty listaModulos}">
						<select name="modulo" onchange="this.form.method.value='seleccionPermisos';this.form.submit()">
							<option value=""> -- </option>
							<c:forEach var="modulo" items="${listaModulos}">
								<option value="<c:out value="${modulo.key}"/>"
									<c:if test="${modulo.key==param.modulo}">selected="true"</c:if>>
									<fmt:message key="${modulo.value}"/>
								</option>
							</c:forEach>
						</select>
						</c:when>
						<c:otherwise>
							<fmt:message key="nombreModulo.modulo${formBean.modulo}" />
						</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td class="tdTitulo" style="vertical-align:top;">
						<bean:message key="archigest.archivo.descripcion"  />:&nbsp;
					</td>
					<td class="tdDatos"><html:textarea rows="4" property="descripcion" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)" /></td>
				</tr>
			</table>

		</div>
		<div class="separador5">&nbsp;</div>

		<c:if test="${!empty infoRol}">

			<div class="cabecero_bloque">
				<table class="w98" cellpadding=0 cellspacing=0><tr>
					<td class="etiquetaAzul12Bold">
						<bean:message key="archigest.archivo.cacceso.permisos" />
					</td>		
				</tr></table>
			</div>

			<div class="bloque" style="padding:8px">
				<c:set var="listaPermisos" value="${infoRol.permisos}" />
				<display:table name="pageScope.listaPermisos" 
						id="permiso" 
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list"
						defaultsort="1">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cacceso.msgNoPermisosRol" />
					</display:setProperty>

					<display:column titleKey="archigest.archivo.nombre">
					<c:set var="permisoKey">archigest.archivo.permiso.<c:out value="${permiso.perm}"/></c:set>
						<fmt:message key="${permisoKey}" />
					</display:column>
				</display:table>
			</div>
		</c:if>

		<div style="display:none;">
		</div>
		</html:form>
	</tiles:put>
</tiles:insert>