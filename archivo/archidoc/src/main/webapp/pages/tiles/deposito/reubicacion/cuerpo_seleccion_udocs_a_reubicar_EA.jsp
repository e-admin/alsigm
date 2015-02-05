<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="mapping" mapping="/reubicacionUdocsAction" />

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vListaUdocs" value="${sessionScope[appConstants.deposito.LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY]}"/>
<c:set var="vHueco" value="${sessionScope[appConstants.deposito.HUECO_ORIGEN_COMPACTAR_KEY]}"/>


<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>


<c:set var="caja" value="${sessionScope[appConstants.deposito.CAJA_COMPACTAR_EA]}"/>


<script language="JavaScript1.2" type="text/JavaScript">
	function goOn(){
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.method.value="seleccionarAsignableDestino";

		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
</script>


<tiles:definition id="infoUInstalacion" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.unidadInstalacionOrigen"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">

		<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<TR>
				<TD class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.ruta"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHueco.path}"/>
				</TD>
			</TR>

			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.signatura"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHueco.unidInstalacion.signaturaui}"/>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHueco.nombreformato}"/>
				</TD>
			</TR>
		</TABLE>
	</tiles:put>
</tiles:definition>

<tiles:definition id="listadoUdocs" template="/pages/tiles/PABlockLayout.jsp" >
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.seleccioneUdocsAReubicar"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">

<div class="separador8">&nbsp;</div>
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.relaciones.relacionEntrega"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
					<TR>
					<td>&nbsp;</td>
					</TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="visibleContent" direct="true">
					<TABLE class="w98m1" cellpadding=0 cellspacing=2>
						<TR>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.codigoTransferencia}"/>
								</span>
							</TD>
							<TD width="25%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.nombreestado}"/>
								</span>
							</TD>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
								</span>
							</TD>
							<TD width="35%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
								<c:set var="gestorEnOrganoRemitente" value="${vRelacion.gestorEnOrganoRemitente}" />
								<span class="etiquetaNegra11Normal">
									<c:out value="${gestorEnOrganoRemitente.nombreCompleto}"/>
								</span>
							</TD>
						</TR>
					</TABLE>
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<html:form action="/reubicacionUdocsAction">
	<input type="hidden" name="method" value="seleccionarAsignableDestino"/>

		<div class="separador8">&nbsp;</div>
						<c:set var="udocs" value="${caja.listaUDocs}"/>
						<display:table name="pageScope.udocs" id="unidadDocumental"
							style="width:99%;margin-left:auto;margin-right:auto">

							<display:setProperty name="basic.msg.empty_list">
								<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
									&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
								</div>
							</display:setProperty>

							<display:column title="" style="width:25px;text-align:right;" >

							<c:set var="idUnidad" value="${udoc.idunidaddoc}:${udoc.posudocenui}:${udoc.signaturaudoc}"/>
							<jsp:useBean id="idUnidad" class="java.lang.String"/>
								<c:set var="idUnidad" value="${unidadDocumental.idunidaddoc}:${unidadDocumental.posudocenui}:${unidadDocumental.signaturaudoc}"/>
								<input type="checkbox" name="udocsSeleccionadas" value="<c:out value='${idUnidad}'/>" <c:if test="${unidadDocumental.enRelacion== 'S'}">checked</c:if>/>
							</display:column>
							<display:column titleKey="archigest.archivo.valida" style="width:25px;text-align:right;" >
								<c:choose>
									<c:when test="${unidadDocumental.enRelacion== 'S'}">
										<html:img page="/pages/images/right.gif" border="0" styleClass="imgTextTop" />
									</c:when>
									<c:otherwise>
										<html:img page="/pages/images/wrong.gif" border="0" styleClass="imgTextTop" />
									</c:otherwise>
								</c:choose>
							</display:column>

							<display:column titleKey="archigest.archivo.posicion" style="width:10">
								<fmt:formatNumber value="${unidadDocumental_rowNum}" pattern="000"/>
							</display:column>

							<display:column titleKey="archigest.archivo.signatura" style="width:20">
								<c:out value="${unidadDocumental.signaturaudoc}" />
							</display:column>




							<display:column titleKey="archigest.archivo.transferencias.asunto">
								<c:out value="${unidadDocumental.titulo}" />
							</display:column>


						</display:table>
		<div class="separador8">&nbsp;</div>
	</html:form>
	</tiles:put>
</tiles:definition>

<%-- COMPOSICION DE PAGINA A MOSTRAR --%>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.reubicacionDeUDocumentales"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td width="10">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
				</tiles:insert>
			</td>
			<td width="10">&nbsp;</td>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img
					page="/pages/images/Next.gif"
					altKey="archigest.archivo.siguiente"
					titleKey="archigest.archivo.siguiente"
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<TD>
				<c:url var="cancelURL" value="/action/reubicacionUdocsAction">
					<c:param name="method" value="goReturnPoint" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
				<html:img page="/pages/images/close.gif"
					altKey="archigest.archivo.cerrar"
					titleKey="archigest.archivo.cerrar"
					styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
			</TD>

		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert beanName="infoUInstalacion" />
		<div class="separador8">&nbsp;</div>
		<tiles:insert beanName="listadoUdocs" />
	</tiles:put>
</tiles:insert>
