<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionSalasAction" />

<c:set var="edificio" value="${sessionScope[appConstants.salas.EDIFICIO_KEY]}" />
<c:set var="permitidoModificar" value="${requestScope[appConstants.salas.PERMITIR_MODIFICAR_MESA_KEY]}" />
<c:choose>
	<c:when test="${empty salaForm.idSala}">
		<c:set var="editando" value="false"/>
	</c:when>
	<c:otherwise>
		<c:set var="editando" value="true"/>
	</c:otherwise>
</c:choose>

<script language="javascript">
function save() {
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.submit();
}
</script>

<html:form action="/gestionSalasAction">
	<input type="hidden" name="method" value="guardar" />
	<c:if test="${editando}">
	<html:hidden property="idSala"/>
	<html:hidden property="idEdificio"/>
	<html:hidden property="numeroMesas"/>
	</c:if>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${editando}">
				<bean:message key="archigest.archivo.salas.editar.sala"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.salas.crear.sala"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
		<tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:save()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<td noWrap>
				<tiles:insert definition="button.closeButton">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
				</tiles:insert>
			</td>
		</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<c:if test="${!editando}">
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.salas.edificio.ver"/>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<tiles:insert name="salas.datos.edificio" flush="true"/>
				</tiles:put>
			</tiles:insert>
		</c:if>
		<div class="separador8">&nbsp;</div>
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.salas.ver.sala"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td width="180px" class="tdTitulo">
							<bean:message key="archigest.archivo.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${!editando or permitidoModificar}">
									<html:text property="nombre" styleClass="input60" maxlength="64"/>
								</c:when>
								<c:otherwise>
									<html:text property="nombre" styleClass="inputRO" style="width:60%" maxlength="64" readonly="true"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property="descripcion" styleClass="input60" maxlength="254"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.salas.equipo.informatico"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:checkbox property="equipoInformatico" styleClass="checkbox" styleId="equipoInformatico"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.salas.numero.mesas"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${!editando}">
									<html:text property="numeroMesas" size="5" maxlength="5" />
								</c:when>
								<c:otherwise>
									<html:text property="numeroMesas" styleClass="inputRO" size="5" maxlength="5" readonly="true" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>