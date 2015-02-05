<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>

<script language="JavaScript1.2" type="text/JavaScript">

<c:set var="gestoresDeSeries" value="${requestScope[appConstants.fondos.LISTA_GESTORES_SERIE_KEY]}" />

function enviar()
{

	var form = document.forms['SerieForm'];

	<c:if test="${!empty gestoresDeSeries}">
	if(form.idGestor.value=="")
	{
		alert("<bean:message key='archigest.archivo.series.gestorArchivoNoSeleccionado'/>");
		return;
	}
	</c:if>

	enableChecks();
	form.submit();
}
</script>
<c:set var="solicitud" value="${sessionScope[appConstants.fondos.DETALLE_SOLICITUD_KEY]}"/>
<c:set var="infoUDocsSerie" value="${requestScope[appConstants.fondos.NIVELES_FICHA_UDOC_REP_ECM]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${param.method == 'autorizarSolicitud'}">
				<bean:message key="archigest.archivo.cf.autorizarSolicitud"/>
			</c:when>
			<c:when test="${param.method == 'rechazarSolicitud'}">
				<bean:message key="archigest.archivo.cf.rechazarSolicitud"/>
			</c:when>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:enviar()">
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="cancelarURL" value="/action/gestionSeries">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${cancelarURL}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

	<jsp:include page="info_solicitud.jsp" flush="true" />

	<c:url var="ejecucionSolicitudURL" value="/action/gestionSeries" />

	<html:form action="/gestionSeries">
	<input type="hidden" name="idSolicitud" value="<c:out value="${solicitud.id}" />">

	<div class="cabecero_bloque">
		<table class="w98m1" cellpadding=0 cellspacing=0 height="20">
		  <tr>
			<TD class="etiquetaAzul12Bold">
				<bean:message key="archigest.archivo.resolucion"/>
				<c:choose>
					<c:when test="${param.method == 'autorizarSolicitud'}">
						<bean:message key="archigest.archivo.cf.autorizarSolicitud"/>
					</c:when>
					<c:when test="${param.method == 'rechazarSolicitud'}">
						<bean:message key="archigest.archivo.cf.rechazarSolicitud"/>
					</c:when>
				</c:choose>
			</TD>
		  </tr>
		</table>
	</div>

	<div class="bloque"> <%-- primer bloque de datos --%>
		<c:choose>
		<c:when test="${param.method == 'autorizarSolicitud' || (param.method == 'ejecutarSolicitudAltaAprobada' && !empty infoUDocsSerie)}">

			<input type="hidden" name="method" value="ejecutarSolicitudAltaAprobada">

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tdTitulo">
						<c:set var="gestoresDeSeries" value="${requestScope[appConstants.fondos.LISTA_GESTORES_SERIE_KEY]}" />
						<c:if test="${!empty gestoresDeSeries}">
							<bean:message key="archigest.archivo.cf.GestorSerie"/>:
							<html:img page="/pages/images/pixel.gif" styleClass="imgTextTop" width="150px" height="1"/>
							<html:select property="idGestor">
								<html:option value=""></html:option>
								<html:options collection="gestoresDeSeries" property="id" labelProperty="nombreCompleto" />
							</html:select>
						</c:if>
					</td>
				</tr>
			</TABLE>

			<div class="separador8">&nbsp;</div>

			<c:set var="listaFichasCLDocumentales" value="${requestScope[appConstants.fondos.LISTA_FICHAS_CL_DOCUMENTALES_KEY]}" />
			<c:set var="listaNivelesDocumentales" value="${requestScope[appConstants.fondos.LISTA_NIVELES_DOCUMENTALES_KEY]}" />
			<c:set var="fichasUdocs" value="${requestScope[appConstants.fondos.LISTA_FICHAS_PARA_UDOCS_KEY]}" />

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<TR>
					<td class="tdTitulo" width="99%">
						<bean:message key="archigest.archivo.cf.serie"/>
					</td>
				</TR>
				<TR>
					<TD>
						<TABLE class="formulario" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="20px">&nbsp;</TD>
							<td class="tdTitulo" width="250px">
								<bean:message key="archigest.archivo.cf.fichaDescAsociada"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:set var="fichasSerie" value="${requestScope[appConstants.fondos.LISTA_FICHAS_PARA_SERIES_KEY]}" />
								<c:if test="${!empty fichasSerie}">
									<html:select property="idFichaDescriptivaSerie">
										<html:options collection="fichasSerie" property="id" labelProperty="nombre" />
									</html:select>
								</c:if>
							</td>
						</tr>
						<tr>
							<TD>&nbsp;</TD>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<bean:define id="nombreCampo" value="idRepEcmSerie" toScope="request"/>
								<tiles:insert name="lista.repositorios.ecm" flush="true"/>
							</td>
						</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<div class="separador5">&nbsp;</div>
		</c:when>
		<c:when test="${param.method == 'rechazarSolicitud'}">
		<input type="hidden" name="method" value="ejecutarSolicitudDenegada">
		<table class="formulario">
			<tr>
				<td width="170px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.motivoRechazo"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<textarea name="motivoRechazo" ></textarea>
				</td>
			</tr>
		</table>
		</c:when>
		</c:choose>

		<div class="separador5">&nbsp;</div>

	</div>
	</html:form>

	</tiles:put>
</tiles:insert>
