<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>

<c:set var="vPadreElementoCF" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>

<bean:struts id="mappingGestionSeries" mapping="/gestionSeries" />


<script language="JavaScript1.2" type="text/JavaScript">

function enviar()
{
	var form = document.forms['<c:out value="${mappingGestionSeries.name}" />'];
	if (form.autorizacionAutomatica != null)
	{
		//if(form.autorizacionAutomatica.checked==true && form.idGestor.value=="")
		if(form.autorizacionAutomatica.checked==true)
		{
			if (form.idGestor == null || form.idGestor.value=="")
			{
				alert("<bean:message key='archigest.archivo.series.gestorArchivoNoSeleccionado'/>");
				return;
			}
		}
	}
	enableChecks();

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	form.submit();
}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.solicitarAltaSerie"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0 id="botoneraGeneral">
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:enviar()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="cancelURI" value="/action/gestionSeries">
					<c:param name="method" value="goBack"  />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<html:form action="/gestionSeries">
		<input type="hidden" name="method" value="procesarSolicitudAlta">
		<html:hidden property="idPadre" />
		<html:hidden property="idNivelPadre" />

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockContent" direct="true">

		<TABLE class="formulario" cellpadding="0" cellspacing="0">
			<tr>
				<td width="200px" class="tdTitulo" >
					<bean:message key="archigest.archivo.cf.codReferenciaPadre"/>:
				</td>
				<td class="tdDatosBold" >
					<c:out value="${vPadreElementoCF.codReferencia}"/>
				</td>
			</tr>
			<TR>
				<td class="tdTitulo" style="vertical-align:top;">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
						<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</TR>

		</TABLE>
		</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.datosSerie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">


		<TABLE class="formulario" cellpadding="0" cellspacing="0">
			<c:set var="nivelesTipoSerie" value="${requestScope[appConstants.fondos.LISTA_NIVELES_TIPO_SERIE_KEY]}" />
			<c:choose>
				<c:when test="${empty nivelesTipoSerie[1]}">
					<input type="hidden" name="idNivelSerie" value="<c:out value="${nivelesTipoSerie[0].id}" />" >
				</c:when>
				<c:otherwise>
					<tr>
						<td width="200px" class="tdTitulo">
							<bean:message key="archigest.archivo.cf.tipoDeSerie"/>
						</td>
						<td class="tdDatos">
							<c:forEach var="nivelSerie" items="${nivelesTipoSerie}">
							<html:radio property="idNivelSerie" value="id" idName="nivelSerie" /> <c:out value="${nivelSerie.nombre}" />
							</c:forEach>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<td width="200px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<html:text property="codigo" size="14" maxlength="128"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<html:text property="denominacion" styleClass="input99" maxlength="1024"/>
				</td>
			</tr>
		</TABLE>

		<div class="separador8">&nbsp;</div>

		<TABLE class="formulario" cellpadding="0" cellspacing="0">
			<tr>
				<td class="tdTitulo" width="200px" style="vertical-align:top">
					<bean:message key="archigest.archivo.cf.observaciones"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<html:textarea property="observaciones" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" style="vertical-align:top">
					<bean:message key="archigest.archivo.cf.motivoSolicitud"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<html:textarea property="motivo" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
				</td>
			</tr>
			<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<html:text property="codOrdenacion" maxlength="32"/>
					</td>
				</tr>
			</c:if>
		</TABLE>
		<security:permissions action="${appConstants.fondosActions.ALTA_DIRECTA_SERIE}">

			<div class="separador8">&nbsp;</div>

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.autorizarAutomaticamente"/>:&nbsp;
						<html:checkbox property="autorizacionAutomatica" styleClass="checkbox" value="true"
							onclick="xDisplay('datosAltaAutomatica', this.checked?'block':'none')" />
					</td>
				</tr>
			</TABLE>
		</security:permissions>
		</tiles:put>
		</tiles:insert>

		<security:permissions action="${appConstants.fondosActions.ALTA_DIRECTA_SERIE}">

		<div class="separador8">&nbsp;</div>

		<div id="datosAltaAutomatica" <c:if test="${! param.autorizacionAutomatica}">style="display:none"</c:if>>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.datosAltaSerie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tdTitulo">
						<c:set var="gestoresDeSeries" value="${requestScope[appConstants.fondos.LISTA_GESTORES_SERIE_KEY]}" />
						<c:if test="${!empty gestoresDeSeries}">
							<bean:message key="archigest.archivo.cf.GestorSerie"/>:
							<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="150px" height="1"/>
							<html:select property="idGestor">
								<html:option value="">&nbsp;</html:option>
								<html:options collection="gestoresDeSeries" property="id" labelProperty="nombreCompleto" />
							</html:select>
						</c:if>
					</td>
				</tr>
			</TABLE>

			<div class="separador8">&nbsp;</div>

			<c:set var="listaFichasCLDocumentales" value="${requestScope[appConstants.fondos.LISTA_FICHAS_CL_DOCUMENTALES_KEY]}" />
			<c:set var="listaNivelesDocumentales" value="${sessionScope[appConstants.fondos.LISTA_NIVELES_DOCUMENTALES_KEY]}" />
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

				<bean:define id="listaNivelesDocumentales" name="listaNivelesDocumentales" toScope="request"/>
				<bean:define id="listaFichasCLDocumentales" name="listaFichasCLDocumentales" toScope="request"/>
				<bean:define id="fichasUdocs" name="fichasUdocs" toScope="request"/>
	 			<c:set var="infoUDocsSerie" value="${sessionScope[appConstants.fondos.NIVELES_FICHA_UDOC_REP_ECM]}"/>
				<c:if test="${!empty infoUDocsSerie}">
					<bean:define id="infoUDocsSerie" name="infoUDocsSerie" toScope="request"/>
				</c:if>

				<tiles:insert page="../series/info_ficha_udoc_rep_ecm.jsp" flush="true"/>
			</TABLE>
			<div class="separador5">&nbsp;</div>
		</tiles:put>
		</tiles:insert>
		</div>
		</security:permissions>
	</html:form>

	</tiles:put>
</tiles:insert>
