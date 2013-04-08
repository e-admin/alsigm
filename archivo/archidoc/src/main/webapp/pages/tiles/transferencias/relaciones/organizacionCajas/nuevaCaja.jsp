<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="relacionEntrega" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
<c:set var="methodAction" ><c:out value="${requestScope[appConstants.common.METHOD_KEY]}">crearCaja</c:out></c:set>
<c:set var="unidadInstalacion" value="${sessionScope[appConstants.transferencias.CAJA_KEY]}" />
<c:set var="udocsSinCaja" value="${requestScope[appConstants.transferencias.EXPEDIENTES_SIN_ASIGNAR]}" />
<c:set var="incorporando" value="${requestScope[appConstants.transferencias.INCORPORANDO_UNIDAD_DOCUMENTAL]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<bean:struts id="mappingAsignacionCajas" mapping="/asignacionCajas" />
<c:set var="formName" value="${mappingAsignacionCajas.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<script type="text/javascript">
	function aceptar(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		document.forms['<c:out value="${mappingAsignacionCajas.name}" />'].submit();
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.incorporacionUdocs"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding="0" cellspacing="0">
		<TR>
			<TD>
				<c:if test="${!empty udocsSinCaja}">
					<a class="etiquetaAzul12Bold" href="javascript:aceptar();" >
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</c:if>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<tiles:insert definition="button.closeButton">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
				</tiles:insert>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<html:form action="/asignacionCajas">
		<input type="hidden" name="method" value="<c:out value="${methodAction}" />"/>
		<html:hidden property="idRelacion" />
		<html:hidden property="ordenCaja"/>
		<html:hidden property="asignando"/>

		<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.fondos.datos.ingreso.directo"/></tiles:put>
				</c:otherwise>
			</c:choose>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:choose>
					<c:when test="${!vRelacion.isIngresoDirecto}">
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
					</c:when>
					<c:otherwise>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_ingreso.jsp" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<c:if test="${(relacionEntrega.signaturaSolictableEnUI || form.asignando) && (!incorporando) }">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.cotejoCaja.caption"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.transferencias.signaturaUI"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
					<c:if test="${form.asignando}">
						<c:choose>
							<c:when test="${!empty unidadInstalacion.signaturaUI}">
								<c:out value="${unidadInstalacion.signaturaUI}"/>
							</c:when>
							<c:otherwise>
								<html:text property="signaturaCaja" maxlength="16"/>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${!form.asignando}">
						<html:text property="signaturaCaja" maxlength="16"/>
					</c:if>
					</TD>
				</TR>
			</TABLE>
			</tiles:put>
		</tiles:insert>
		<div class="separador8"></div>
		</c:if>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.selIncorporarUdocs"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<c:url var="udocsSinCajaPaginationURI" value="/action/asignacionCajas" />
			<jsp:useBean id="udocsSinCajaPaginationURI" type="java.lang.String" />

			<c:if test="${!empty udocsSinCaja}">

				<TABLE cellpadding=0 cellspacing=0 class="w100">
				  <TR>
				   <TD align="right">
						<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].udocSeleccionada);" >
							<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
							<bean:message key="archigest.archivo.selTodos"/>&nbsp;
						</a>&nbsp;
						<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].udocSeleccionada);" >
							<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
						</a>&nbsp;&nbsp;
				   </TD>
				 </TR>
				</TABLE>
			</c:if>
			<div class="separador8"></div>

			<display:table name="pageScope.udocsSinCaja"
				id="unidadDocumental"
				requestURI='<%=udocsSinCajaPaginationURI%>'
				pagesize="50"
				sort="list"
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:column title="" style="width:20px">
					<c:set var="idUnidadDocumental" value="${unidadDocumental.id}" />
					<jsp:useBean id="idUnidadDocumental" type="java.lang.String" />
					<input type="checkbox" name="udocSeleccionada" value="<%=idUnidadDocumental%>" />
				</display:column>

				<display:column titleKey="archigest.archivo.transferencias.num" property="orden" sortable="true" />
				<display:column sortable="true" property="numeroExpediente" headerClass="sortable" titleKey="archigest.archivo.transferencias.nExp" />

				<display:column titleKey="archigest.archivo.transferencias.asunto" sortable="true" sortProperty="asunto" headerClass="sortable" >
					<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
						<c:param name="method" value="infoUnidadDocumental" />
						<c:param name="udocID" value="${unidadDocumental.id}" />
					</c:url>
					<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
						<c:out value="${unidadDocumental.asunto}" />
					</a>
				</display:column>

			</display:table>

			<div class="separador8"></div>
		</tiles:put>
		</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>