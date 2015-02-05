<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<script>
	<c:set var="codigoRelacion"><c:out value="${vRelacion.codigoTransferencia}" /></c:set>

	function rechazarRelacion() {
		var codigoRelacion = '<c:out value="${codigoRelacion}" />';
		if (confirm("<bean:message key='archigest.archivo.transferencias.relaciones.enviada1'/>"+codigoRelacion+"<bean:message key='archigest.archivo.transferencias.previsiones.enviada2'/>")) {
			<c:url var="envioRelacionURL" value="/action/recepcionRelaciones">
				<c:param name="method" value ="enviarrelacion" />
				<c:param name="codigo" value ="${vRelacion.id}" />
			</c:url>
			window.location = '<c:out value="${envioRelacionURL}" escapeXml="false" />';
		}
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${!vRelacion.isIngresoDirecto}">
				<bean:message key="archigest.archivo.transferencias.datosRelacion"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.fondos.datos.ingreso.directo"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<%--Insertamos la tile de la barra de botones que se ha extraido fuera --%>
		<bean:define id="vRelacion" name="vRelacion" toScope="request"/>
		<TABLE cellpadding="0" cellspacing="0">
		  <TR>
			<%--boton aceptar --%>
			<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
				<script>function enviar() {	document.forms[0].submit();		}</script>
			<TD>
				<a class="etiquetaAzul12Normal" href="javascript:enviar();">
					<html:img titleKey="archigest.archivo.aceptar" altKey="archigest.archivo.aceptar" page="/pages/images/aceptar.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="volverURL" value="/action/recepcionRelaciones">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${volverURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>
			</TD>
			</security:permissions>
		 </TR>
		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.ingresoDirecto"/></tiles:put>
				</c:otherwise>
			</c:choose>

			<tiles:put name="blockContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<TD class="tdTitulo" style="width:20%">
							<bean:message key="archigest.archivo.solicitudes.motivorechazo"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:form action="/recepcionRelaciones">
								<html:hidden property="method" value="rechazarRelacion"/>
								<html:textarea property="motivoRechazo" rows="2" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)" />
							</html:form>
						</TD>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>