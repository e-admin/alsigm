<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="edificio" value="${sessionScope[appConstants.salas.EDIFICIO_KEY]}" />
<c:set var="lista" value="${sessionScope[appConstants.salas.LISTA_SALAS_KEY]}" />
<bean:struts id="actionMapping" mapping="/gestionEdificiosAction" />
<security:permissions action="${appConstants.salasActions.MODIFICAR_EDIFICIO_ACTION}">
<script language="javascript">
	function editar()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value="editar";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
</script>
</security:permissions>

<security:permissions action="${appConstants.salasActions.ELIMINAR_EDIFICIO_ACTION}">
<script language="javascript">
function eliminar(){
	if(window.confirm('<fmt:message key="archigest.archivo.salas.eliminar.edificio.msg"/>')){
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		form.method.value="eliminar";
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
}
</script>
</security:permissions>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.salas.edificio.ver"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>

				<security:permissions action="${appConstants.salasActions.MODIFICAR_EDIFICIO_ACTION}">
				<td nowrap="nowrap">
					<a class="etiquetaAzul12Bold" href="javascript:editar()" >
						<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.editar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>

				<security:permissions action="${appConstants.salasActions.ELIMINAR_EDIFICIO_ACTION}">
				<td nowrap="nowrap">
						<a class="etiquetaAzul12Bold"
						href="javascript:eliminar()">
						<html:img page="/pages/images/delete.gif"
							altKey="archigest.archivo.eliminar"
							titleKey="archigest.archivo.eliminar"
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/gestionEdificiosAction">
				<input type="hidden" name="method" id="method" value=""/>
				<tiles:insert name="salas.datos.edificio" flush="true"/>
				<input type="hidden" name="idSala" id="idSala" value=""/>

			</html:form>
		</div>
		<div class="separador5">&nbsp;</div>
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.salas.listado.salas"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<security:permissions action="${appConstants.salasActions.ALTA_SALA_ACTION}">
				<c:url var="crearSalaURL" value="/action/gestionSalasAction">
					<c:param name="method" value="nuevo" />
					<c:param name="idEdificio" value="${edificio.id}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${crearSalaURL}" escapeXml="false"/>">
					<html:img page="/pages/images/new.gif"
						altKey="archigest.archivo.salas.crear.sala"
						titleKey="archigest.archivo.salas.crear.sala"
						styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.salas.crear.sala"/>
				</a>
				</security:permissions>

				<script language="javascript">
					function verSala(verSalaURL){
						if (window.top.showWorkingDiv) {
							var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
							var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
							window.top.showWorkingDiv(title, message);
						}
						window.location = verSalaURL;
					}
				</script>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
					<tiles:put name="blockContent" direct="true">
						<div class="bloque">
							<div class="separador10">&nbsp;</div>
							<display:table name="pageScope.lista"
								id="sala"
								requestURI="/action/gestionEdificiosAction?method=verEdificio"
								sort="list"
								export="true"
								style="width:99%;margin-left:auto;margin-right:auto">
								<display:setProperty name="basic.msg.empty_list">
									<bean:message key="archigest.archivo.salas.lista.sin.salas"/>
								</display:setProperty>
								<display:column titleKey="archigest.archivo.salas.edificio.nombre.label" media="html" sortable="true" sortProperty="nombre">
									<c:url var="verSalaURL" value="/action/gestionVistaSalasConsulta">
										<c:param name="actionToPerform" value="verNodo" />
										<c:param name="node" value="${sala.itemPath}" />
										<c:param name="refreshView" value="true" />
									</c:url>
									<a class="tdlink" href="javascript:verSala('<c:out value="${verSalaURL}" escapeXml="false"/>');">
										<c:out value="${sala.nombre}"/>
									</a>
								</display:column>
								<display:column titleKey="archigest.archivo.salas.edificio.nombre.label" media="csv pdf excel xml">
									<c:out value="${sala.nombre}"/>
								</display:column>
								<display:column titleKey="archigest.archivo.descripcion" sortable="true" sortProperty="descripcion">
									<c:out value="${sala.descripcion}"/>
								</display:column>
							</display:table>
						</div>
						<div class="separador1">&nbsp;</div>
					</tiles:put>
				</tiles:insert>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
