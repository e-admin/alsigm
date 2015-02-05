<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuarios" />
<script>
	function anadirGrupos() {
		var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
		form.method.value = 'anadirGrupoAUsuario';
		form.submit();
	}
</script>
<html:form action="/gestionUsuarios">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.listaGrupos" />
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
	
		<table><tr>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:anadirGrupos()" >
			<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.aceptar"/></a>
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
		<input type="hidden" name="method" value="anadirRoleAUsuario">

		<tiles:insert page="infoUsuario.jsp"/>
		
		<div class="bloque">
		
		<c:url var="listaGruposPaginationURI" value="/action/gestionUsuarios">
			<c:param name="method" value="${param.method}" />
		</c:url>
		<jsp:useBean id="listaGruposPaginationURI" type="java.lang.String" />
		<c:set var="listaGrupos" value="${requestScope[appConstants.controlAcceso.LISTA_GRUPOS]}" />
		<display:table name="pageScope.listaGrupos" 
				id="grupo" 
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
	            requestURI="<%=listaGruposPaginationURI%>"
				pagesize="10">

			<display:column style="width:10px" title="">
					<input type="checkbox" name="gruposSeleccionados" value="<c:out value="${grupo.id}" />" >
			</display:column>
			<display:column titleKey="archigest.archivo.grupo">
				<c:url var="verURL" value="/action/gestionGrupos">
					<c:param name="method" value="verGrupo" />
					<c:param name="idGrupo" value="${grupo.id}" />
				</c:url>
				<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
				<c:out value="${grupo.nombre}" />
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.descripcion" property="descripcion" maxLength="150"/>
		</display:table>
		</div>

	</tiles:put>
</tiles:insert>
</html:form>
