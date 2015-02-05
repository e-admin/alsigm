<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.documentos.digitalizacion.busqueda.listado.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">			
		<c:set var="listaTareasAGestionar" value="${requestScope[appConstants.documentos.TAREAS_KEY]}"/>		
		<display:table name="pageScope.listaTareasAGestionar"
			id="tarea" 
			pagesize="15"
			requestURI="/action/busquedaTareasDigitalizacion"
			style="width:99%;margin-left:auto;margin-right:auto">

			<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.documentos.digitalizacion.noExistenResultadosParaLaBusqueda"/>
			</display:setProperty>
			
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.tipo" sortable="true" sortProperty="tipo" headerClass="sortable">
				<fmt:message key="${tarea.nombreTipoObjeto}" />		
			</display:column>
			
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.codReferencia" property="codRefObj" sortProperty="codRefObj" sortable="true" headerClass="sortable"/>
			
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.titulo" sortable="true" sortProperty="titulo" headerClass="sortable">
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
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado" sortable="true" sortProperty="estado" headerClass="sortable">
				<fmt:message key="${tarea.nombreEstado}" />	
			</display:column>
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.fechaEstado" sortProperty="fechaEstado" sortable="true" headerClass="sortable">
				<fmt:formatDate value="${tarea.fechaEstado}" pattern="${appConstants.common.FORMATO_FECHA}"/>
			</display:column>

		</display:table>
		</div>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
</tiles:insert>
