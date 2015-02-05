
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/asignacionCajas" />
<c:set var="relacionEntrega" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />

<SCRIPT>
function aceptar(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "guardarEditarSignatura";
	form.submit();
}
</SCRIPT>

<c:set var="unidadInstalacion" value="${sessionScope[appConstants.transferencias.CAJA_KEY]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="delimiter" value="${appConstants.common.DELIMITER_IDS}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.organizacionCaja"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar();" >
					<html:img page="/pages/images/save.gif" border="0" altKey="archigest.archivo.guardar" titleKey="archigest.archivo.guardar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.guardar"/>
				</a>
			</TD>
			<TD width="15">&nbsp;</TD>
			<TD>
				<tiles:insert definition="button.closeButton" flush="true"/>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">	
		<html:form action="/asignacionCajas">
		<input type="hidden" name="method" value="guardarEditarSignatura" />
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relaciones.relacionEntrega"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>	
		<div class="separador8"></div>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.cotejoCaja.caption"/> <c:out value="${unidadInstalacion.orden}"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario" cellpadding=0 cellspacing=0>
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.solicitudes.signaturaCaja"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:text property="signaturaCaja" maxlength="16"/>
					</TD>
				</TR>
			</TABLE>
			</tiles:put>
		</tiles:insert>
		<div class="separador8"></div>
		
		</html:form>		
	</tiles:put>
</tiles:insert>