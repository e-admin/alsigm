<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<c:set var="solicitud" value="${sessionScope[appConstants.fondos.DETALLE_SOLICITUD_KEY]}"/>
<c:set var="serie" value="${solicitud.serieSolicitada}" />
<bean:struts id="mappingGestionSeries" mapping="/gestionSeries" />
<div id="contenedor_ficha">
<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			&nbsp;<bean:message key="archigest.archivo.cf.solicitudCambio"/>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
			<c:choose>
			<c:when test= "${not empty serie}">
	 		<security:permissions action="${appConstants.fondosActions.GESTION_SOLICITUDES_SERIE_ACTION}">
 				<c:if test="${!(solicitud.tipoSolicitudAlta && (solicitud.denegada||solicitud.solicitada))}">
				<TD>
					<c:url var="showCFURL" value="/action/gestionSeries">
						<%--Se sustituye verEnFondos por verDesdeFondos --%>
						<c:param name="method" value="verDesdeFondos" />
						<c:param name="id" value="${solicitud.idSerie}" />
					</c:url>
					<a class=etiquetaAzul12Bold href="<c:out value="${showCFURL}" escapeXml="false"/>">
						<html:img page="/pages/images/tree.gif" altKey="archigest.archivo.cf.verEnFondos" titleKey="archigest.archivo.cf.verEnFondos" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cf.verEnFondos"/>
					</a>
				</TD>
				</c:if>
				<c:if test="${solicitud.solicitada}">
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="autorizacionAltaURL" value="/action/gestionSeries">
						<c:param name="method" value="autorizarSolicitud" />
						<c:param name="idSolicitud" value="${solicitud.id}" />
					</c:url>
					<script>
						function autorizarSolicitud() {
							var urlAutorizacion = '<c:out value="${autorizacionAltaURL}" escapeXml="false"/>';
							if (confirm("<bean:message key='archigest.archivo.cf.msgAutorizarSolicitud'/>"))
								window.location = urlAutorizacion;
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:autorizarSolicitud()">
						<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.cf.autorizarSolicitud" titleKey="archigest.archivo.cf.autorizarSolicitud" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cf.autorizarSolicitud"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="rechazarSolicitudURL" value="/action/gestionSeries">
						<c:param name="method" value="rechazarSolicitud" />
						<c:param name="idSolicitud" value="${solicitud.id}" />
					</c:url>
					<script>
						function rechazarSolicitud() {
							var urlRechazoSolicitud = '<c:out value="${rechazarSolicitudURL}" escapeXml="false"/>';
							if (confirm("<bean:message key='archigest.archivo.cf.msgRechazarSolicitud'/>"))
								window.location = urlRechazoSolicitud;
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:rechazarSolicitud()">
						<html:img page="/pages/images/rechazar.gif" altKey="archigest.archivo.cf.rechazarSolicitud" titleKey="archigest.archivo.cf.rechazarSolicitud" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cf.rechazarSolicitud"/>
					</a>
				</TD>
				</c:if>
	 		</security:permissions>
	 		</c:when>

	 		<c:otherwise>
				<td nowrap>

					<c:url var="eliminarSolicitudURL" value="/action/gestionSeries">
						<%--Se sustituye verEnFondos por verDesdeFondos --%>
						<c:param name="method" value="eliminarSolicitud" />
						<c:param name="idSolicitud" value="${solicitud.id}" />
					</c:url>

				<script>
					function eliminarSolicitudes() {
						var form = document.forms['<c:out value="${mappingGestionSeries.name}" />'];
						if (confirm("<bean:message key='archigest.archivo.solicitudes.eliminar.msg'/>")) {
							window.location = '<c:out value="${eliminarSolicitudURL}" escapeXml="false"/>';
						}
					}
				</script>

					<a class="etiquetaAzul12Bold" href="javascript:eliminarSolicitudes()" >
					<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.eliminar"/></a>
				</td>
					 		</c:otherwise>

			</c:choose>
				<TD width="10">&nbsp;</TD>
				<TD>
					<tiles:insert definition="button.closeButton" />
			   </TD>
			 </TR>
			</TABLE>

		</TD>
	</TR>
	</TABLE>
	</div>
</span></h1>

	<div class="cuerpo_drcha">
	<div class="cuerpo_izda">

		<DIV id="barra_errores">
			<archivo:errors />
		</DIV>

		<jsp:include page="info_solicitud.jsp" flush="true" />


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

