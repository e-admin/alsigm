<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="listaUsuarios" value="${requestScope[appConstants.controlAcceso.LISTA_USUARIOS]}" />
<bean:struts id="mappingGestionGrupos" mapping="/gestionGrupos" />
<c:set var="infoGrupo" value="${sessionScope[appConstants.controlAcceso.INFO_GRUPO]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.usuariosGrupo"/>					
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<script>
			function seleccionarUsuarios() {
				var form = document.forms['<c:out value="${mappingGestionGrupos.name}" />'];
				form.method.value = 'agregarUsuariosAGrupo';
				form.submit();
			}
		</script>
		<table><tr>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:seleccionarUsuarios()" >
			<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
		</td>		
		<td width="10px"></td>
		<td nowrap>
			<c:url var="cancelURL" value="/action/gestionGrupos">
				<c:param name="method" value="goBack" />
			</c:url>

			<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
			<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.cacceso.verGrupo"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<table class="formulario" width="99%">
				<tr>
					<td class="tdTitulo" width="140px"><bean:message key="archigest.archivo.nombre"/>:</td>
					<td class="tdDatos"><c:out value="${infoGrupo.nombre}" /></td>
				</tr>
				<tr>
					<td class="tdTitulo"><bean:message key="archigest.archivo.descripcion"/>:</td>
					<td class="tdDatos"><c:out value="${infoGrupo.descripcion}"> -- </c:out></td>
				</tr>
			</table>
			</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>


	<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.msgSelUsuariosGrupo"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
					<TD>
					<a class="etiquetaAzul12Normal" href="javascript:document.forms['busquedaUsuario'].submit()">
						<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
						<bean:message key="archigest.archivo.buscar"/>
					</a>		
					</TD>
			 </TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">	
	<table class="formulario">
	
	<form name="busquedaUsuario" action="<c:url value="/action/gestionGrupos"/>">
		<input type="hidden" name="method" value="busquedaUsuario">
		<tr>
			<td class="tdTitulo" width=180px" nowrap="nowrap">
				<bean:message key="archigest.archivo.nombre"/>&nbsp;/
				<bean:message key="archigest.archivo.apellidos"/>:
			</td>
			<td class="tdDatos"><input type="text" class="input99" name="nombreUsuario" value="<c:out value="${param.nombreUsuario}" />"></td>
		</tr>
	</form>
	</table>
<html:form action="/gestionGrupos">
<input type="hidden" name="method">
	<div id="resultados">

		<c:set var="listaUsuarios" value="${requestScope[appConstants.controlAcceso.LISTA_USUARIOS]}" />
		<c:choose>
		<c:when test="${listaUsuarios != null}">
			<display:table name="pageScope.listaUsuarios" 
					id="usuario" 
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					defaultsort="2">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.cacceso.msgNoUserAsignar"/>
				</display:setProperty>
				<display:column style="width:15px">
					<input type="checkbox" name="usuarioSeleccionado" value="<c:out value="${usuario.id}" />">
				</display:column>

				<display:column titleKey="archigest.archivo.usuario">
					<c:out value="${usuario.nombre}" /> <c:out value="${usuario.apellidos}" />
				</display:column>
			</display:table>
			<span class="separador5">&nbsp;</span>
		</c:when>
		</c:choose>
	</div>
</html:form>	
	</tiles:put>
	</tiles:insert>
</tiles:put>
</tiles:insert>