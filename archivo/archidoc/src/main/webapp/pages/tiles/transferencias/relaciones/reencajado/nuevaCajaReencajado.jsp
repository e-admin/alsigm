<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<bean:struts id="mapping" mapping="/asignacionCajasReencajado" />
<c:set var="formName" value="${mapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
<c:set var="unidadInstalacion" value="${sessionScope[appConstants.transferencias.CAJA_KEY]}" />
<c:set var="reencajadoView" value="${requestScope[appConstants.transferencias.REENCAJADO_VIEW_OBJECT_KEY]}" />
<c:set var="incorporando" value="${requestScope[appConstants.transferencias.INCORPORANDO_UNIDAD_DOCUMENTAL]}" />
<c:set var="udocsSinCaja" value="${reencajadoView.udocsSinAsignar}" />
<c:set var="methodAction" value="crearCaja"/>
<c:if test="${incorporando}"><c:set var="methodAction" value="guardarIncorporarACaja"/></c:if>

<script type="text/javascript">
	function aceptar(){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		document.forms['<c:out value="${mapping.name}" />'].submit();
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
		<html:form action="/asignacionCajasReencajado">
		<input type="hidden" name="method" value="<c:out value="${methodAction}" />"/>
		<html:hidden property="idRelacion" />

		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.selIncorporarUdocs"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>
				<c:url var="udocsSinCajaPaginationURI" value="/action/asignacionCajasReencajado" />
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
					id="udoc"
					requestURI='<%=udocsSinCajaPaginationURI%>'
					pagesize="50"
					export="false"
					style="width:99%;margin-left:auto;margin-right:auto">

					<display:column title="" style="width:20px">
						<c:set var="idUdoc" value="${udoc.id}" />
						<jsp:useBean id="idUdoc" type="java.lang.String" />
						<input type="checkbox" name="udocSeleccionada" value="<%=idUdoc%>" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.num" property="posUdocEnUI" sortable="true" />
					<display:column sortable="true" headerClass="sortable" titleKey="archigest.archivo.transferencias.nExp" >
						<c:out value="${udoc.numExp}" /><c:out value="${udoc.parteExp}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.asunto" sortable="true" sortProperty="asunto" headerClass="sortable" >
						<c:url var="infoUdocURL" value="/action/gestionUdocsRelacion">
							<c:param name="method" value="infoudoc" />
							<c:param name="udocID" value="${udoc.id}" />
						</c:url>
						<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${udoc.asunto}" />
						</a>
					</display:column>
				</display:table>
				<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>