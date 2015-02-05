<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="identificacionSerie" value="${sessionScope[appConstants.fondos.IDENTIFICACION_SERIE_KEY]}" />
<c:set var="productoresADarDeAlta" value="${sessionScope[appConstants.fondos.PRODUCTORES_A_DAR_DE_ALTA_KEY]}" />

<script language="JavaScript1.2">
	function desplegarOrgano(posicion) {
		switchVisibility("organoNoDesplegado"+posicion);
		switchVisibility("organoDesplegado"+posicion);
	}
</script>

<bean:struts id="mappingIdentificacionSerie" mapping="/gestionIdentificacionAction" />
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.identificacionSerie"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		<TD>
			<script>
				function guardar() {
					var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
					form.method.value = "guardarNuevosOrganos";
					form.submit();
				}
			</script>

			<a class="etiquetaAzul12Bold" href="javascript:guardar()" >
				<html:img page="/pages/images/save.gif" altKey="archigest.archivo.guardar" titleKey="archigest.archivo.guardar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.guardar"/>
			</a>
		</TD>
		<TD width="10"></TD>
		<TD>
			<c:url var="cerrarURL" value="/action/gestionIdentificacionAction">
				<c:param name="method" value="goBack" />
			</c:url>
			<SCRIPT>
				<c:set var="dataForm" value="${sessionScope[mappingIdentificacionSerie.name]}" />
				var dataHasChanged = <c:out value="${dataForm.changed}" />
				function warning() {
					if (dataHasChanged) {
						if (confirm("<bean:message key='archigest.warning.formularioModificado'/>"))
							window.location = '<c:out value="${cerrarURL}" escapeXml="false"/>';
					} else
						window.location = '<c:out value="${cerrarURL}" escapeXml="false"/>';
				}
			</SCRIPT>
				<tiles:insert definition="button.returnButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cerrar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/close.gif</tiles:put>
					<tiles:put name="action" direct="true">edicionIdentificacion</tiles:put>
				</tiles:insert>

		</TD>

		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.serie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
		<table class="formulario">
			<tr>
				<td class="tdTitulo" style="vertical-align:top;" width="180px">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> <c:out value="${serie.denominacion}" />
				</td>
			</tr>
		</table>
		</tiles:put>
		</tiles:insert>

		<html:form action="/gestionIdentificacionAction">
		<input type="hidden" name="method">


		<c:set var="vProcedimiento" value="${identificacionSerie.procedimiento}" />

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.procedimientosDeLaSerie"/></tiles:put>
		<c:choose>
		<c:when test="${identificacionSerie.permitidoVincularProcedimiento}">
			<c:if test="${identificacionSerie.permitidoEditarProcedimientoAsociado}">
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
				<TD>
					<c:url var="listadoProcedimientoURL" value="/action/gestionIdentificacionAction">
						<c:param name="method" value="verBuscadorProcedimiento" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${listadoProcedimientoURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.cf.asociarProc" titleKey="archigest.archivo.cf.asociarProc" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.asociarProc"/>
					</a>
				</TD>
				<c:if test="${!empty vProcedimiento}">
				<TD width="10"></TD>
				<TD>
					<c:url var="devincularDeProcedimientoURL" value="/action/gestionIdentificacionAction">
						<c:param name="method" value="eliminarprocedimiento" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${devincularDeProcedimientoURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.cf.desvincularProc" titleKey="archigest.archivo.cf.desvincularProc" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.desvincularProc"/>
					</a>
				</TD>
				</c:if>
				</TR>
				</TABLE>
			</tiles:put>
			</c:if>

			<tiles:put name="blockContent" direct="true">
				<div class="separador5">&nbsp;</div>
				<c:choose>
					<c:when test="${!empty vProcedimiento}">
						<table class="w98"><tr>
							<td class="etiquetaNegra11Normal">
								<c:out value="${vProcedimiento.codigo}" />&nbsp;
								<c:out value="${vProcedimiento.nombre}" />
							</td>
						</tr></table>
					</c:when>
					<c:otherwise>
						&nbsp;<bean:message key="archigest.archivo.cf.noProcedimientos"/>
					</c:otherwise>
				</c:choose>
				<div class="separador5">&nbsp;</div>
			</tiles:put>
		</c:when>
		<c:otherwise>
			<tiles:put name="blockContent" direct="true">
				<div class="aviso">
					<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
					<bean:message key="archigest.archivo.cf.msgSerieNoIdentificadaProc"/>
				</div>
			</tiles:put>
		</c:otherwise>
		</c:choose>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<%-- ORGANOS PRODUCTORES --%>

		<c:if test="${!empty productoresADarDeAlta}">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresADarDeAlta"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

				<div class="separador5">&nbsp;</div>
				<%-- Productores a dar de alta --%>

					<c:set var="archivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />
					<input type="hidden" name="isOrganoDesplegado"/>
					<display:table name="pageScope.productoresADarDeAlta" id="productorADarDeAlta" style="width:99%">
						<display:column titleKey="archigest.archivo.organo">

							<c:set var="organoNoDesplegadoName" value="organoNoDesplegado" />
							<c:set var="organoDesplegadoName" value="organoDesplegado" />
							<c:set var="position" value="${productorADarDeAlta_rowNum - 1}" />
							<c:set var="organoNoDesplegado" value="${organoNoDesplegadoName}${position}" />
							<c:set var="organoDesplegado" value="${organoDesplegadoName}${position}" />

							<div id="<c:out value="${organoNoDesplegado}"/>">
								<a href="javascript:desplegarOrgano(<c:out value="${position}"/>)">
									<html:img page="/pages/images/plus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${productorADarDeAlta.nombreCorto}" />
							</div>

							<div id="<c:out value="${organoDesplegado}"/>" style="display:none">
								<a href="javascript:desplegarOrgano(<c:out value="${position}"/>)">
									<html:img page="/pages/images/minus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${productorADarDeAlta.nombre}" />
							</div>
						</display:column>
						<display:column titleKey="archigest.archivo.nombre">
							<c:set var="position" value="${productorADarDeAlta_rowNum - 1}" />
							<html-el:text property="nombreProductor[${productorADarDeAlta_rowNum - 1}]" styleClass="input" />
						</display:column>

						<display:column titleKey="archigest.archivo.descripcion">
							<html-el:text property="descripcionProductor[${productorADarDeAlta_rowNum - 1}]" styleClass="input" />
						</display:column>

						<display:column titleKey="archigest.archivo.archivo">
							<c:choose>
							<c:when test="${!empty archivos[1]}">
								<html:select property="archivoPorProductor">
									<html:options collection="archivos" property="id" labelProperty="nombre" />
								</html:select>
							</c:when>
							<c:otherwise>
								<html-el:hidden property="archivoPorProductor" value="${archivos[0].id}"/>
								<c:set var="archivoPorProductor" value="${archivos[0]}" />
								<c:out value="${archivoPorProductor.nombre}" />
							</c:otherwise>
							</c:choose>
						</display:column>

					</display:table>
					<div class="separador5">&nbsp;</div>

			</tiles:put>
		</tiles:insert>
		</c:if>
		</html:form>
	</tiles:put>
</tiles:insert>