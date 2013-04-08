<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="lista" value="${requestScope[appConstants.salas.LISTA_EDIFICIOS_KEY]}" />
<bean:struts id="actionMapping" mapping="/gestionEdificiosAction" />

<script>
function verEdificio(verEdificioURL){

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	window.location = verEdificioURL;
}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.salas.edificios.listado"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.salasActions.ALTA_EDIFICIO_ACTION}">
				<td noWrap>
					<c:url var="crearEdificioURL" value="/action/gestionEdificiosAction">
						<c:param name="method" value="nuevo" />
					</c:url>
					<a class="etiquetaAzul12Bold"
						href="<c:out value="${crearEdificioURL}" escapeXml="false"/>">
						<html:img page="/pages/images/new.gif"
							altKey="archigest.archivo.salas.edificio.crear"
							titleKey="archigest.archivo.salas.edificio.crear"
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.salas.edificio.crear"/>
					</a>

				</td>
				<td width="10">&nbsp;</TD>
				</security:permissions>
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
					<div class="separador1">&nbsp;</div>

					<html:form action="/gestionEdificiosAction" styleId="formulario">
						<input type="hidden" name="method"/>
						<input type="hidden" name="idEdificio"/>
						<display:table name="pageScope.lista"
							id="edificio"
							requestURI="/action/gestionEdificiosAction?method=listado"
							sort="list"
							export="true"
							style="width:99%;margin-left:auto;margin-right:auto">
							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.salas.lista.sin.edificios"/>
							</display:setProperty>
							<display:column titleKey="archigest.archivo.salas.edificio.nombre.label" media="html" sortable="true" sortProperty="nombre">
								<c:url var="verEdificioURL" value="/action/gestionEdificiosAction">
									<c:param name="method" value="verEdificio" />
									<c:param name="idEdificio" value="${edificio.id}" />
								</c:url>
								<a class="tdlink" href="javascript:verEdificio('<c:out value="${verEdificioURL}" escapeXml="false"/>');">
									<c:out value="${edificio.nombre}"/>
								</a>
							</display:column>
							<display:column titleKey="archigest.archivo.salas.edificio.nombre.label" media="csv pdf excel xml">
									<c:out value="${edificio.nombre}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.salas.edificio.ubicacion.label" sortable="true" sortProperty="ubicacion">
								<c:out value="${edificio.ubicacion}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.salas.edificio.archivo.label" sortable="true" sortProperty="nombreArchivo">
								<c:out value="${edificio.nombreArchivo}"/>
							</display:column>
						</display:table>
					</html:form>
					<div class="separador1">&nbsp;</div>
				</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>