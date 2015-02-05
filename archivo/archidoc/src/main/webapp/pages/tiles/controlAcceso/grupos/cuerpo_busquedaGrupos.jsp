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

<c:set var="listaGrupos" value="${requestScope[appConstants.controlAcceso.LISTA_GRUPOS]}" />

<html:form action="/gestionGrupos">
<input type="hidden" name="method" value="buscar">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.busquedaGrupos"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table cellspacing=0 cellpadding=0><tr>
		<td nowrap>
			<script>
				function buscar() {
					var form = document.forms['<c:out value="${mappingGestionGrupos.name}" />'];
					form.submit();
				}
			</script>
			<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
			<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.buscar"/></a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true"/>
		</td>
	</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
				<table class="formulario">
					<tr>
					<td class="tdTituloFichaSinBorde" width="10%"><bean:message key="archigest.archivo.nombre"/>:</td>
					<td class="tdDatosFichaSinBorde">
						<html:text property="nombre" styleClass="input99" maxlength="254" />
					</td>
					</tr>
				</table>
		</div>

		<div class="separador8">&nbsp;</div>

		<c:if test="${listaGrupos != null}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<c:url var="paginationURL" value="/action/gestionGrupos" />
				<jsp:useBean id="paginationURL" type="java.lang.String" />

				<display:table name="pageScope.listaGrupos" 
						id="grupo" 
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="list"
						requestURI="/action/gestionGrupos"
						pagesize="10">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cacceso.msgNoGruposSistema" />
					</display:setProperty>			
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
			</tiles:put>
		</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>