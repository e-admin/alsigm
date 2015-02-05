<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<bean:struts id="mapping" mapping="/gestionRegistroConsultaAction" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}" />
<c:set var="usuariosRegistrados" value="${sessionScope[appConstants.salas.LISTA_USUARIOS_REGISTRADOS_KEY]}" />

<html:form action="/gestionRegistroConsultaAction" >
	<input type="hidden" name="method">

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.salas.listado.usuarios.registrados"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<script>
				function buscarUsuarios() {
					var form = document.forms['<c:out value="${mapping.name}" />'];
					form.method.value = "busquedaUsuarios";
					form.submit();
				}

				function registrarSalida() {
					var form = document.forms['<c:out value="${mapping.name}" />'];
					if(form.registrosSeleccionados && numElementsSelected(form.registrosSeleccionados) > 0){
						if(numElementsSelected(form.registrosSeleccionados) > 1){
							alert("<bean:message key='archigest.archivo.salas.msgUnUsuarioSeleccionado'/>");
						}else{
							if (confirm('<bean:message key="archigest.archivo.salas.registrarSalidaMsg"/>')){
								form.method.value = "registrarSalida";
								form.submit();
							}
						}
					}else{
						alert("<bean:message key='archigest.archivo.salas.msgNoUsuarioSeleccionado'/>");
					}
				}
			</script>
			<table cellpadding=0 cellspacing=0>
			<tr>
				<c:if test="${not empty usuariosRegistrados}">
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:registrarSalida();">
						<html:img page="/pages/images/quitar.gif" altKey="archigest.archivo.salas.registrar.salida" titleKey="archigest.archivo.salas.registrar.salida" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.salas.registrar.salida"/>
					</a>
				</td>
				<td width="10"></td>
				</c:if>
				<td>
					<a class="etiquetaAzul12Bold" href="javascript:buscarUsuarios();">
						<html:img page="/pages/images/buscar_go.gif" altKey="archigest.archivo.salas.buscar.usuarios" titleKey="archigest.archivo.salas.buscar.usuarios" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.salas.buscar.usuarios"/>
					</a>
				</td>
				<td width="10"></td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
			</table>
		</tiles:put>

		<c:url var="paginationURL" value="/action/gestionRegistroConsultaAction" >
			<c:param name="method" value="listadoUsuariosRegistrados"/>
		</c:url>
		<jsp:useBean id="paginationURL" type="java.lang.String" />
		<tiles:put name="boxContent" direct="true">
			<div class="bloque">
				<display:table name="pageScope.usuariosRegistrados"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="usuario"
					pagesize="15"
					sort="list"
					requestURI='<%=paginationURL%>'
					excludedParams="*"
					export="true">

					<display:column style="width:10px" title="" media="html">
						<input type="checkbox" name="registrosSeleccionados" value="<c:out value="${usuario.id}" />" >
					</display:column>
					<display:column titleKey="archigest.archivo.doc.identificativo" sortProperty="numDocIdentificacion" sortable="true" headerClass="sortable" media="html">
						<c:url var="verURL" value="/action/gestionUsuarioSalasConsultaAction">
							<c:param name="method" value="ver" />
							<c:param name="idUsuario" value="${usuario.idUsrCSala}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
							<c:out value="${usuario.numDocIdentificacion}" />
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.doc.identificativo" media="csv excel xml pdf">
						<c:out value="${usuario.numDocIdentificacion}" />
					</display:column>
					<display:column titleKey="archigest.archivo.salas.usuario.nombreApellidos" sortable="true" headerClass="sortable" property="nombreApellidos" media="html csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.archivo" sortable="true" headerClass="sortable" property="nombreArchivo" media="html csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.salas.mesa.asignada" sortable="true" headerClass="sortable" property="mesaAsignada" media="html csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.salas.registro.fechaRegistro" sortable="true" headerClass="sortable" media="html csv excel xml pdf" style="text-align:center;">
						<fmt:formatDate value="${usuario.fechaEntrada}" pattern="${FORMATO_FECHA}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.salas.registro.horasRegistro" sortable="true" headerClass="sortable" media="html csv excel xml pdf" style="text-align:center;">
						<c:out value="${usuario.horaEntrada}"/>
						<c:if test="${usuario.registroCerrado}"><c:out value="- ${usuario.horaSalida}"/></c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.salas.registro.cerrado" sortable="true" headerClass="sortable" media="html" style="text-align:center;">
						<c:choose>
							<c:when test="${usuario.registroCerrado}">
								<html:img page="/pages/images/checkbox-yes.gif" styleClass="imgTextMiddle"/>
							</c:when>
							<c:otherwise>
								<html:img page="/pages/images/checkbox-no.gif" styleClass="imgTextMiddle"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.salas.registro.cerrado" sortable="true" headerClass="sortable" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.salas.datos.registro" sortable="false" media="html" style="text-align:center;">
						<c:url var="verUdocURL" value="/action/gestionRegistroConsultaAction">
							<c:param name="method" value="ver" />
							<c:param name="idRegistroConsulta" value="${usuario.id}" />
						</c:url>
						<a class="etiquetaAzul12Normal" href="<c:out value="${verUdocURL}" escapeXml="false"/>">
							 <html:img titleKey="archigest.archivo.salas.datos.registro" altKey="archigest.archivo.salas.datos.registro" page="/pages/images/verDoc.gif" styleClass="imgTextMiddle" />&nbsp;
						</a>
					</display:column>
				</display:table>
			</div>
		</tiles:put>
	</tiles:insert>
</html:form>