<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="tareas" value="${sessionScope[appConstants.documentos.LISTA_TAREAS_KEY]}" />
<c:set var="gestores" value="${sessionScope[appConstants.documentos.USUARIOS_CAPTURADORES_KEY]}" />

<bean:struts id="actionMapping" mapping="/cesionTareasDigitalizacion" />

<script>
	function select() 
	{
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
</script>

<div style="display:none;">
<html:form action="/cesionTareasDigitalizacion">
<html:hidden property="tipoBusqueda"/>
<input type="hidden" name="method" value="asignarGestor"/>
</div>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.documentos.digitalizacion.cederControl"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td width="10">&nbsp;</td>
	        <td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
				</tiles:insert>
		    </td>
			<c:if test="${!empty tareas}">
			<td width="10">&nbsp;</td>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:select()" >
				<html:img page="/pages/images/Ok_Si.gif" 
					altKey="archigest.archivo.aceptar" 
					titleKey="archigest.archivo.aceptar" 
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			</c:if>
			<td width="10">&nbsp;</td>
	        <td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
				</tiles:insert>
		    </td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.documentos.digitalizacion.modificables"/>:
			</tiles:put>
			
			<tiles:put name="blockContent" direct="true">
			<span class="separador5"></span>

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

			<span class="separador5"></span>

			<input type="hidden" name="method" value="asignarGestor">
			<table class="w98">
			<tr>
				<td class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.documentos.digitalizacion.nuevoGestor"/>:&nbsp;&nbsp;
					<c:set var="gestores" value="${sessionScope[appConstants.documentos.USUARIOS_CAPTURADORES_KEY]}"/>
					<c:choose>
						<c:when test="${!empty gestores}">
							<select name="nuevoGestor">
								<option value="">&nbsp;</option>
								<c:forEach var="gestor" items="${gestores}">
								<option value="<c:out value="${gestor.id}" />"
									<c:if test="${beanForm.gestor == gestor.id}">
									selected="true"
									</c:if>
									>
									<c:out value="${gestor.nombreCompleto}" />
								</option>
								</c:forEach>
							</select>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.documentos.digitalizacion.capturadores.empty"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr></table>
			<span class="separador5"></span>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>