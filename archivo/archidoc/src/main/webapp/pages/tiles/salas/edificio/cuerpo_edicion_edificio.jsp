<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/gestionEdificiosAction" />
<c:set var="formBean" value="${sessionScope[actionMapping.name]}"/>
<c:set var="archivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
		<c:when test="${!empty formBean.idEdificio}">
			<bean:message key="archigest.archivo.salas.edificio.editar"/>
		</c:when>
		<c:otherwise>
			<bean:message key="archigest.archivo.salas.edificio.crear"/>
		</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.salasActions.MODIFICAR_EDIFICIO_ACTION}">
				<td nowrap>
					<script>
						function save()
						{
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}

							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold"
						href="javascript:save()">
						<html:img page="/pages/images/Ok_Si.gif"
						titleKey="archigest.archivo.aceptar"
						altKey="archigest.archivo.aceptar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
			<html:form action="/gestionEdificiosAction">
				<html:hidden property="idEdificio" />
				<input type="hidden" name="method" value="guardar">

				<table class="formulario">
					<tr>
						<td width="100px" class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos">
						<c:choose>
						<c:when test="${!editando}">
							<html:text property='nombre' styleClass="input99" maxlength="64" />
						</c:when>
						<c:otherwise>
							<html:text property='nombre' styleClass="inputRO99" size="99%" maxlength="64" readonly="true" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.ubicacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property='ubicacion' styleClass="input99" maxlength="254" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.archivo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${!empty archivos[1]}">
										<html:select property="idArchivo">
											<html:options collection="archivos" property="id" labelProperty="nombre"/>
										</html:select>
								</c:when>
								<c:otherwise>
									<html-el:hidden property="idArchivo"/>
									<html:text property="nombreArchivo" styleClass="inputRO99" maxlength="254" size="50%" readonly="true" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>

				</table>
			</html:form>
		</div>
		<div class="separador5">&nbsp;</div>

	</tiles:put>
</tiles:insert>
