<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

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
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.divisionUdocs"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar();" >
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
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
		<input type="hidden" name="method" value="dividirUDocEnUIExistente"/>
		<html:hidden property="idRelacion" />
		<html:hidden property="ordenCaja"/>

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

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.cotejoCaja.caption"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario" cellpadding=0 cellspacing=0>
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.transferencias.signaturaUI"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:text property="signaturaCaja"/>
					</TD>
				</TR>
			</TABLE>
			</tiles:put>
		</tiles:insert>

		</html:form>
	</tiles:put>
</tiles:insert>
