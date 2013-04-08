<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<bean:struts id="actionMapping" mapping="/cesionTareasDigitalizacion" />
<c:set var="mappingName" value="${actionMapping.name}" />
<c:set var="mappingPath" value="${actionMapping.path}" />
<c:set var="formBean" value="${requestScope[actionMapping.name]}" />



<script>
	function goOn() 
	{
		var form = document.forms["<c:out value="${mappingName}" />"];
		form.method.value = "seleccionar";
		form.submit();
	}
	function reload()
	{
		var form = document.forms["<c:out value="${mappingName}" />"];
		form.method.value = "verBuscadorGestor";
		form.submit();
	}
	function search()
	{
		var form = document.forms["<c:out value="${mappingName}" />"];
		form.method.value = "buscar";
		form.submit();
	}
	function busquedaTareaForm(){
		<c:url var="URL" value="/action${mappingPath}">
			<c:param name="method" value="verBuscador" />
		</c:url>
		window.location = "<c:out value="${URL}" escapeXml="false"/>";
	}
	function busquedaTareaPorGestorForm(){
		<c:url var="URL" value="/action${mappingPath}">
			<c:param name="method" value="verBuscadorGestor" />
		</c:url>
		window.location = "<c:out value="${URL}" escapeXml="false"/>";
	}
</script>

<html:form action="/cesionTareasDigitalizacion">
<div style="display:none;">
<input type="hidden" name="tipoBusqueda" value="<c:out value="${formBean.tipoBusqueda}" />" />
<input type="hidden" name="method" value="buscar"/>
</div>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.documentos.digitalizacion.cederControl"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img 
					page="/pages/images/Next.gif" 
					altKey="archigest.archivo.siguiente" 
					titleKey="archigest.archivo.siguiente" 
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
				</tiles:insert>
			</td>
		</tr></table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<c:choose>
			<c:when test="${formBean.tipoBusqueda==1}">
				<c:set var="classTab1" value="tabActual" />
				<c:set var="classLink1" value="textoPestana" />
				<c:set var="classTab2" value="tabSel" />
				<c:set var="classLink2" value="textoPestanaSel" />
			</c:when>
			<c:otherwise>
				<c:set var="classTab1" value="tabSel" />
				<c:set var="classLink1" value="textoPestanaSel" />
				<c:set var="classTab2" value="tabActual" />
				<c:set var="classLink2" value="textoPestana" />
			</c:otherwise>
		</c:choose>

		<div class="cabecero_tabs">
			<table cellspacing="0" cellpadding=0>
				<tr>
				<td class="<c:out value="${classTab1}"/>">
					<a href="javascript:busquedaTareaForm()" class="<c:out value="${classLink1}"/>">
						<bean:message key="archigest.archivo.documentos.digitalizacion.buscarTarea"/>
					</a>
				</td>
				<td width="5px">&nbsp;</td>
				<td class="<c:out value="${classTab2}"/>">
					<a href="javascript:busquedaTareaPorGestorForm()" class="<c:out value="${classLink2}"/>">
						<bean:message key="archigest.archivo.documentos.digitalizacion.buscarUsuarioCaptura"/>
					</a>
				</td>
				</tr>
			</table>
		</div>
	
		<div class="bloque_tab">
			<div class="cabecero_bloque_tab">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0>
				  <TR>
					<TD width="100%" align="right">
						<a class="etiquetaAzul12Normal" 
							href="javascript:search()">
							<html:img titleKey="archigest.archivo.buscar" 
								altKey="archigest.archivo.buscar" 
								page="/pages/images/buscar.gif" 
								styleClass="imgTextMiddle"/>
							<bean:message key="archigest.archivo.buscar"/>
						</a>		
					</TD>
				  </TR>
				</TABLE>
			</div>
			<table class="w100">
				<tr>
					<td width="10px">&nbsp;</td>
					<td class="etiquetaAzul12Bold">
						<bean:message key="archigest.archivo.buscarPor"/>:&nbsp;
					</td>
				</tr>
			</table>
			<c:choose>
				<c:when test="${formBean.tipoBusqueda==1}">
					<table class="formulario">					
						<tr>
							<td class="tdTitulo" width="150px">Referencia:&nbsp;</td>
							<td class="tdDatos"><html:text property="referencia" size="60" /></td>
						</tr>
						<tr>
							<td class="tdTitulo">Título:&nbsp;</td>
							<td class="tdDatos"><html:text property="titulo" styleClass="input90" /></td>
						</tr>		
					</table>
				</c:when>
				<c:otherwise>
					<table class="formulario">		
						<tr>				
							<td width="20px">&nbsp;</td>
							<td class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.documentos.digitalizacion.buscarUsuarioCaptura"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:set var="gestores" value="${sessionScope[appConstants.documentos.USUARIOS_CAPTURADORES_KEY]}"/>
								<c:choose>
									<c:when test="${!empty gestores}">
										<select name="gestor">
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
						</tr>
					</table>
				</c:otherwise>
			</c:choose>	
		</div>

		<c:if test="${formBean.resultado}">
			<div class="separador8">&nbsp;</div>
	
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.documentos.digitalizacion.msgSelTareasCeder" /></tiles:put>
				<tiles:put name="blockContent" direct="true">	
					<jsp:useBean id="mappingPath" type="java.lang.String" />
					<c:set var="listaTareasAGestionar" value="${requestScope[appConstants.documentos.LISTA_TAREAS_CEDIBLES_KEY]}"/>		
					<display:table name="pageScope.listaTareasAGestionar"
						id="tarea" 
						pagesize="15"
						requestURI='<%="../../action" + mappingPath%>'
						style="width:99%;margin-left:auto;margin-right:auto">
				
						<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.documentos.digitalizacion.noExistenResultadosParaLaBusqueda"/>
						</display:setProperty>
				
						<display:column style="width:15px">
							<input type="checkbox" name="tareasSeleccionadas" value="<c:out value='${tarea.id}'/>"/>		
						</display:column>
							
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
					<div class="separador5">&nbsp;</div>
				</tiles:put>
			</tiles:insert>
		</c:if>
	</tiles:put>
</tiles:insert>

</html:form>

