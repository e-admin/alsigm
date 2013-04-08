<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="archivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />

<bean:struts id="mappingGestionUbicacion" mapping="/gestionDepositoAction" />
<c:set var="gestionUbicacionFormName" value="${mappingGestionUbicacion.name}" />
<c:set var="gestionUbicacionForm" value="${requestScope[gestionUbicacionFormName]}" />

<c:choose>
	<c:when test="${empty gestionUbicacionForm.idUbicacion}">
		<c:set var="editando" value="false"/>
	</c:when>
	<c:otherwise>
		<c:set var="editando" value="true"/>
	</c:otherwise>
</c:choose>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${!editando}">
				<bean:message key="archigest.archivo.crear"/> <bean:message key="archigest.archivo.deposito.ubicacion"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.deposito.edicionUbicacion"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<script>
			function guardarUbicacion() {
				var form = document.forms['<c:out value="${mappingGestionUbicacion.name}" />'];
				form.submit();
			}
		</script>
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:guardarUbicacion()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD noWrap>
				<tiles:insert definition="button.closeButton">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
				</tiles:insert>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.datos.ubicacion"/>:&nbsp;
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<html:form action="/gestionDepositoAction">
				<html:hidden property="idUbicacion" />
				<input type="hidden" name="method" value="guardarUbicacion">
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
							<html:text property='ubicacion' styleClass="input99" maxlength="255" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.archivo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${!empty archivos}">
									<html:select property="idArchivo">
										<html:options collection="archivos" property="id" labelProperty="nombre" />
									</html:select>
								</c:when>
								<c:otherwise>
									<html-el:hidden property="idArchivo"/>
									<html:text property="nombreArchivo" styleClass="inputRO99" maxlength="255" size="50%" readonly="true" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>

				</table>
				</html:form>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>