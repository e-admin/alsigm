<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="mapping" mapping="/reubicacionUdocsAction" />

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vListaUdocs" value="${sessionScope[appConstants.deposito.DEPOSITO_UDOCS_A_REUBICAR]}"/>
<c:set var="vListaUdocsReubicadas" value="${sessionScope[appConstants.deposito.DEPOSITO_UDOCS_REUBICADAS]}"/>
<c:set var="reubicacionFinalizada" value="${sessionScope[appConstants.deposito.REUBICACION_FINALIZADA]}"/>
<c:set var="vHuecoOrigen" value="${sessionScope[appConstants.deposito.HUECO_ORIGEN_COMPACTAR_KEY]}"/>
<c:set var="vHuecoDestino" value="${sessionScope[appConstants.deposito.HUECO_DESTINO_SELECCIONADO_KEY]}"/>

<script language="JavaScript1.2" type="text/JavaScript">
	function goOn(){
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.method.value="aceptarMover";

		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
</script>


<tiles:definition id="listadoUdocs" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.unidadesDocumentales"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
<html:form action="/reubicacionUdocsAction">
	<input type="hidden" name="method" value="seleccionarAsignableDestino"/>
		<div class="separador8">&nbsp;</div>
		<display:table name="pageScope.vListaUdocs"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="udoc"
			sort="list"
			export="false">
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado.4" style="width:10">
				<c:choose>
					<c:when test="${udoc.validada}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
					<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.posicion" style="width:10">
				<fmt:formatNumber value="${udoc.posudocenui}" pattern="000"/>
			</display:column>

			<display:column titleKey="archigest.archivo.signatura" >
				<c:url var="verUDocURL" value="/action/verHuecoAction">
					<c:param name="method" value="verUdocEnUI" />
					<c:param name="idUInstalacion" value="${vHuecoOrigen.iduinstalacion}" />
					<c:param name="posUDoc" value="${udoc.posudocenui}" />
				</c:url>

					<c:out value="${udoc.signaturaudoc}"/>

			</display:column>
			<display:column titleKey="archigest.archivo.titulo" property="titulo" />
		</display:table>
<div class="separador8">&nbsp;</div>
</html:form>
	</tiles:put>
</tiles:definition>

<tiles:definition id="listadoUdocsReubicadas" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.unidadesDocumentales"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
		<div class="separador8">&nbsp;</div>
		<display:table name="pageScope.vListaUdocsReubicadas"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="udoc"
			sort="list"
			export="false">
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado.4" style="width:10">
				<c:choose>
					<c:when test="${udoc.validada}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
					<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.posicion" style="width:10">
				<fmt:formatNumber value="${udoc.posudocenui}" pattern="000"/>
			</display:column>

			<display:column titleKey="archigest.archivo.signatura" >
				<c:url var="verUDocURL" value="/action/verHuecoAction">
					<c:param name="method" value="verUdocEnUI" />
					<c:param name="idUInstalacion" value="${vHuecoOrigen.iduinstalacion}" />
					<c:param name="posUDoc" value="${udoc.posudocenui}" />
				</c:url>

					<c:out value="${udoc.signaturaudoc}"/>

			</display:column>
			<display:column titleKey="archigest.archivo.titulo" property="titulo" />
		</display:table>
<div class="separador8">&nbsp;</div>
	</tiles:put>
</tiles:definition>


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
					<c:out value="${vHuecoOrigen.path}"/>
				</TD>
			</TR>

			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.signatura"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHuecoOrigen.unidInstalacion.signaturaui}"/>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHuecoOrigen.nombreformato}"/>
				</TD>
			</TR>
			<tr>
				<td colspan="2">
					<div class="separador8">&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">

					<tiles:insert beanName="listadoUdocs" />
				</td>
			</tr>
		</TABLE>

	</tiles:put>
</tiles:definition>

<tiles:definition id="infoUInstalacionDestino" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.unidadInstalacionDestino"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">

		<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<TR>
				<TD class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.ruta"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHuecoDestino.path}"/>
				</TD>
			</TR>
			<c:if test="${vHuecoDestino.unidInstalacion.signaturaui!=null}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.signatura"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHuecoDestino.unidInstalacion.signaturaui}"/>
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHuecoDestino.nombreformato}"/>
				</TD>
			</TR>
		</TABLE>
		<c:if test="${reubicacionFinalizada == true}">
			<div class="separador8">&nbsp;</div>
			<tiles:insert beanName="listadoUdocsReubicadas" />
		</c:if>
	</tiles:put>
</tiles:definition>



<%-- COMPOSICION DE PAGINA A MOSTRAR --%>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.reubicacionDeUDocumentales"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding="0" cellspacing="0">
		<TR>
			<c:choose>
				<c:when test="${reubicacionFinalizada == true}">
					<td>
						<c:url var="informeURL" value="/action/informeReubicacion"/>

						<a class="etiquetaAzul12Bold" href="<c:out value="${informeURL}" escapeXml="false"/>">
						<html:img page="/pages/images/documentos/doc_pdf.gif"
						        altKey="archigest.archivo.informe"
						        titleKey="archigest.archivo.informe"
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.informe"/></a>
					</td>
					<td width="10">&nbsp;</td>
					<TD>
					<c:url var="huecoOrigenURL" value="/action/verHuecoAction">
						<c:param name="method" value="listadoudocsDesdeReubicacion"/>
						<c:param name="idHueco" value="${vHuecoOrigen.idElemAPadre}:${vHuecoOrigen.numorden}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${huecoOrigenURL}" escapeXml="false"/>">
					<html:img page="/pages/images/huecoBlanco.gif"
						altKey="archigest.archivo.deposito.ir.a.hueco.origen"
						titleKey="archigest.archivo.deposito.ir.a.hueco.origen"
						styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.deposito.ir.a.hueco.origen"/></a>
			   		</TD>
					<td width="10">&nbsp;</td>
					<TD>
					<c:url var="huecoDestinoURL" value="/action/verHuecoAction">
						<c:param name="method" value="listadoudocsDesdeReubicacion"/>
						<c:param name="idHueco" value="${vHuecoDestino.idElemAPadre}:${vHuecoDestino.numorden}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${huecoDestinoURL}" escapeXml="false"/>">
					<html:img page="/pages/images/huecoAzul.gif"
						altKey="archigest.archivo.deposito.ir.a.hueco.destino"
						titleKey="archigest.archivo.deposito.ir.a.hueco.destino"
						styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.deposito.ir.a.hueco.destino"/></a>
			   		</TD>
					<td width="10">&nbsp;</td>
					<TD>
					<c:url var="closeURL" value="/action/reubicacionUdocsAction">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class=etiquetaAzul12Bold href="<c:out value="${closeURL}" escapeXml="false"/>">
					<html:img page="/pages/images/close.gif"
						altKey="archigest.archivo.cerrar"
						titleKey="archigest.archivo.cerrar"
						styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
			   		</TD>
				</c:when>
				<c:otherwise>
					<td>
						<tiles:insert definition="button.closeButton" flush="true">
							<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
							<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
						</tiles:insert>
				   	</td>

					<td width="10">&nbsp;</td>
					<td>
						<a class="etiquetaAzul12Bold" href="javascript:goOn()">
							<html:img
								page="/pages/images/Ok_Si.gif"
								altKey="archigest.archivo.aceptar"
								titleKey="archigest.archivo.aceptar"
								styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>
					</td>
					<td width="10">&nbsp;</td>
					<td>
						<c:url var="cancelURL" value="/action/reubicacionUdocsAction">
							<c:param name="method" value="goReturnPoint" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>">
						<html:img page="/pages/images/Ok_No.gif"
							altKey="archigest.archivo.cancelar"
							titleKey="archigest.archivo.cancelar"
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
				   	</td>
				</c:otherwise>
			</c:choose>


		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert beanName="infoUInstalacion" />
		<div class="separador8">&nbsp;</div>
		<div class="separador8">&nbsp;</div>
		<tiles:insert beanName="infoUInstalacionDestino"/>
	</tiles:put>
</tiles:insert>
