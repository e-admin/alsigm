<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<bean:struts id="actionMapping" mapping="/gestionMesasAction" />

<c:set var="sala" value="${sessionScope[appConstants.salas.SALA_KEY]}" />
<c:set var="listaPathsMesas" value="${sessionScope[appConstants.salas.LISTA_PATHS_MESAS_KEY]}"/>
<c:set var="modificando" value="${sessionScope[appConstants.salas.ACTION_MODIFICAR_MESAS_KEY]}" />

<script language="javascript">
function aceptar(){
	var modificando = '<c:out value="${modificando}" />';
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	if(modificando){
		if (confirm('<bean:message key="archigest.archivo.salas.modificarMesaMsg"/>')){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}
			form.method.value = 'modificarCodigos';
			form.submit();
		}
	}else{
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		form.submit();
	}
}
</script>

<html:form action="/gestionMesasAction">
	<input type="hidden" name="method" value="finalizar" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${modificando}">
				<bean:message key="archigest.archivo.salas.cambiar.codigos.mesas"/>
				<bean:message key="archigest.archivo.salas.mesas"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.crear"/>
				<bean:message key="archigest.archivo.salas.mesas"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
						<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td noWrap>
					<tiles:insert definition="button.closeButton">
						<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
						<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
					</tiles:insert>
				</td>
				<td width="10">&nbsp;</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.ver.sala"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<tiles:insert name="salas.datos.sala" flush="true"/>
			</tiles:put>
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.datos.mesas"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador1">&nbsp;</div>
				<display:table name="pageScope.listaPathsMesas"
					style="width:99%;margin-left:auto;margin-right:auto"
					requestURI='<%=request.getContextPath()+"/action/gestionMesasAction?method=listadoModulos"%>'
					id="pathMesa" export="false" excludedParams="method">

					<display:column style="width:10px">
						<c:out value="${pathMesa_rowNum}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.deposito.ubicacion" sortable="true" headerClass="sortable" style="width:500px">
						<c:out value="${pathMesa}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.salas.mesa.codigo" style="width:150px">
						<html-el:text property="mapFormValues(${pathMesa_rowNum})" styleClass="inputRO"/>
					</display:column>
				</display:table>
				<div class="separador1">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>