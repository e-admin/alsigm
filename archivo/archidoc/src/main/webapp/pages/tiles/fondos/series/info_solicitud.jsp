<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="solicitud" value="${sessionScope[appConstants.fondos.DETALLE_SOLICITUD_KEY]}"/>
<c:set var="serieSolicitada" value="${solicitud.serieSolicitada}" />
<c:set var="ancestros" value="${sessionScope[appConstants.fondos.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION]}"/>

<div class="bloque"> <%-- primer bloque de datos --%>
	<table class="formulario" cellpadding=0 cellspacing=0>
		<tr>
			<td class="tdTitulo" width="170px" style="vertical-align:top;">
				<bean:message key="archigest.archivo.cf.serie"/>:&nbsp;
			</td>
		<c:choose>
		<c:when test="${solicitud.tipoSolicitudAlta && solicitud.denegada}">
			<td class="tdDatosBold">
				<c:out value="${solicitud.etiquetaSerie}" />
			</td>
		</c:when>
		<c:otherwise>
			<td class="tdDatosBold">
					<c:choose>
					<c:when test="${serieSolicitada.temporal}">
						<c:out value="${solicitud.etiquetaSerie}" />
					</c:when>
					<c:otherwise>
						<c:url var="viewSerieURL" value="/action/gestionIdentificacionAction">
							<c:param name="method" value="veridentificacionserie" />
							<c:param name="idserie" value="${solicitud.idSerie}" />
						</c:url>
						<a class="tdlinkBold" href="<c:out value="${viewSerieURL}" escapeXml="false"/>"><c:out value="${solicitud.etiquetaSerie}" /></a>
					</c:otherwise>
					</c:choose>
			</td>
		</c:otherwise>
		</c:choose>
		</tr>
		<tr>
		<c:if test="${!(solicitud.tipoSolicitudAlta && solicitud.denegada)}">
			<td class="tdTitulo" style="vertical-align:top;">
				<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
			</td>

			<td class="tdDatos" >
				<TABLE cellpadding=0 cellspacing=0>
					<c:forEach var="aAncestor" items="${ancestros}" varStatus="nivel">
						<TR>
							 <TD style="vertical-align:top;">
								<span style="padding-left:<c:out value="${(nivel.count-1)*10}px"/>;" class="user_data">
		  						    <c:choose>
										<c:when test="${nivel.first}">
											<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="6px"/>
											<html:img page="/pages/images/padre.gif" styleClass="imgTextMiddle"/>
									    </c:when>
										<c:when test="${nivel.last}">
											<html:img page="/pages/images/descendiente_last.gif" styleClass="imgTextMiddle"/>
									    </c:when>
									  	<c:otherwise>
											<html:img page="/pages/images/descendiente.gif" styleClass="imgTextMiddle"/>
										</c:otherwise>
									</c:choose>
									<c:out value="${aAncestor.codigo}" />&nbsp;<c:out value="${aAncestor.titulo}" />
								</span>
							</TD>
						 </TR>
					</c:forEach>
				</TABLE>
			</td>
		</c:if>
		</tr>
	</table>
</div>

<div class="separador8">&nbsp;</div>


<div class="cabecero_bloque">
	<table class="w98m1" cellpadding=0 cellspacing=0 height="20">
	  <tr>
		<TD class="etiquetaAzul12Bold">
			<bean:message key="archigest.archivo.cf.datosSolicitud"/>
		</TD>
	  </tr>
	</table>
</div>

<div class="bloque"> <%-- primer bloque de datos --%>

	<table class="formulario" cellpadding=0 cellspacing=0>
		<tr>
			<td class="tdTitulo" width="170px">
				<bean:message key="archigest.archivo.cf.tipoSolicitud"/>:&nbsp;
			</td>
			<td class="tdDatosBold">
				<fmt:message key="${appConstants.i18nPrefixes.PETICION_CAMBIO_SERIE}${solicitud.tipo}" />
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.fechaSolicitud"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<fmt:formatDate value="${solicitud.fecha}" pattern="${FORMATO_FECHA}"/>
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.estado"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<fmt:message key="archigest.archivo.cf.estadoSolicitudAlta.${solicitud.estado}" />
			</td>
		</tr>
	</table>
	<div class="separador5">&nbsp;</div>
	<table class="formulario" cellpadding=0 cellspacing=0>
		<tr>
			<td class="tdTitulo" width="170px">
				<bean:message key="archigest.archivo.cf.solicitante"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${solicitud.usuarioSolicitante.nombreCompleto}" />
			</td>
		</tr>
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.motivoSolicitud"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${solicitud.motivoSolicitud}"> -- </c:out>
			</td>
		</tr>
		<c:if test="${solicitud.denegada}">
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.cf.motivoRechazo"/>:&nbsp;
			</td>
			<td class="tdDatos">
				<c:out value="${solicitud.motivoRechazo}"> -- </c:out>
			</td>
		</tr>
		</c:if>

	</table>
</div>