<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="lista" value="${requestScope[appConstants.salas.LISTA_USUARIOS_KEY]}" />
<bean:struts id="actionMapping" mapping="/gestionUsuarioSalasConsultaAction" />

<script>
function ver(url){

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	window.location = url;
}

function verBuscador(){
	var form = document.forms["<c:out value="${actionMapping.name}" />"];
	form.method.value="verBuscador";
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	form.submit();
}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.salas.usuarios.listado"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.salasActions.ALTA_USUARIO_ACTION}">
				<td noWrap>
					<c:url var="crearURL" value="/action/gestionUsuarioSalasConsultaAction">
						<c:param name="method" value="nuevo" />
					</c:url>
					<a class="etiquetaAzul12Bold"
						href="<c:out value="${crearURL}" escapeXml="false"/>">
						<html:img page="/pages/images/newDoc.gif"
							altKey="archigest.archivo.salas.usuario.crear"
							titleKey="archigest.archivo.salas.usuario.crear"
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.salas.usuario.crear"/>
					</a>

				</td>
				<td width="10">&nbsp;</TD>
				</security:permissions>
				<td width="10px"></td>
					<c:url var="busquedaUsuariosURL" value="/action/gestionUsuarios">
						<c:param name="method" value="verBuscador" />
					</c:url>
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:verBuscador()" >
						<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.buscar"/></a>
					</td>
				<td width="10">&nbsp;</TD>
				<td>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
				<div class="bloque">
					<html:form action="/gestionUsuarioSalasConsultaAction" styleId="formulario">
						<input type="hidden" name="method"/>

						<display:table name="pageScope.lista"
							id="usuario"
							requestURI="/action/gestionUsuarioSalasConsultaAction?method=listado"
							sort="list"
							export="true"
							pagesize="15"
							style="width:99%;margin-left:auto;margin-right:auto">

							<c:url var="verURL" value="/action/gestionUsuarioSalasConsultaAction">
								<c:param name="method" value="ver" />
								<c:param name="idUsuario" value="${usuario.id}" />
							</c:url>

							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.salas.lista.sin.usuarios"/>
							</display:setProperty>

							<display:column titleKey="archigest.archivo.doc.identificativo" media="html" style="width:250px;" headerClass="sortable" sortable="true">
								<a class="tdlink" href="javascript:ver('<c:out value="${verURL}" escapeXml="false"/>');">
									<c:out value="${usuario.numDocIdentificacion}"/>
								</a>
							</display:column>
							<display:column titleKey="archigest.archivo.doc.identificativo" media="csv pdf excel xml" sortable="true">
									<c:out value="${usuario.numDocIdentificacion}"/>
							</display:column>

							<display:column titleKey="archigest.archivo.nombre" sortable="true" headerClass="sortable">
									<c:out value="${usuario.nombre}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.apellidos" sortable="true" headerClass="sortable">
									<c:out value="${usuario.apellidos}"/>
							</display:column>

							<display:column titleKey="archigest.archivo.salas.usuario.vigente" media="html"  style="width:80px; text-align: center;" sortable="true" >
								<c:choose>
									<c:when test="${usuario.vigente == 'S'}">
										<html:img page="/pages/images/checkbox-yes.gif"
														altKey="archigest.archivo.si"
														titleKey="archigest.archivo.si"
														styleClass="imgTextMiddle" />
									</c:when>
									<c:otherwise>
										<html:img page="/pages/images/checkbox-no.gif"
												altKey="archigest.archivo.no"
												titleKey="archigest.archivo.no"
												styleClass="imgTextMiddle" />
									</c:otherwise>
								</c:choose>
							</display:column>
							<display:column titleKey="archigest.archivo.salas.usuario.vigente" media="csv pdf excel xml">
								<c:choose>
									<c:when test="${usuario.vigente == 'S'}">
										<bean:message key="archigest.archivo.si"/>
									</c:when>
									<c:otherwise>
										<bean:message key="archigest.archivo.no"/>
									</c:otherwise>
								</c:choose>
							</display:column>
						</display:table>
					</html:form>
				</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>