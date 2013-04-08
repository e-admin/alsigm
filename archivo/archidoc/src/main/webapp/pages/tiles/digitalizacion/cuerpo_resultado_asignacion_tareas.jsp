<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="gestor" value="${requestScope[appConstants.documentos.CAPTURADOR_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.documentos.digitalizacion.cederControl"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
			<tiles:insert definition="button.closeButton" flush="true">
			<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
			</tiles:insert>
		    </td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<c:out value="${gestor.nombreCompleto}"/>&nbsp;
				<span class="etiquetaNegra12Normal">
					<bean:message key="archigest.archivo.documentos.digitalizacion.gestorAsignadoMsg"/>
				</span>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>
			<c:set var="tareas" value="${sessionScope[appConstants.documentos.LISTA_TAREAS_KEY]}" />
			<display:table name="pageScope.tareas"
				id="tarea" 
				style="width:99%;margin-left:auto;margin-right:auto">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.documentos.digitalizacion.noTareasACeder"/>
				</display:setProperty>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.tipo" sortable="true" headerClass="sortable">
					<fmt:message key="${tarea.nombreTipoObjeto}" />		
				</display:column>
				
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.codReferencia" property="codRefObj" sortable="true" headerClass="sortable"/>
				
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.titulo" sortable="true" headerClass="sortable">
					<c:url var="verTareaURL" value="/action/gestionTareasDigitalizacion">
						<c:param name="method" value="verTarea" />
						<c:param name="idTarea" value="${tarea.id}" />
					</c:url>
					<a class="tdlink" href='<c:out value="${verTareaURL}" escapeXml="false"/>' >
						<c:out value="${tarea.tituloObj}"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.usuarioCaptura" sortProperty="usuario"  sortable="true" headerClass="sortable">
						<c:out value="${tarea.nombreUsuarioCompleto}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado">
					<fmt:message key="${tarea.nombreEstado}" />	
				</display:column>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.fechaEstado" sortProperty="fechaEstado" sortable="true" headerClass="sortable">
					<fmt:formatDate value="${tarea.fechaEstado}" pattern="${appConstants.common.FORMATO_FECHA}"/>
				</display:column>
			</display:table>

			<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>