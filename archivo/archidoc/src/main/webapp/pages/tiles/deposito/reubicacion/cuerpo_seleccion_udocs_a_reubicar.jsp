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
<c:set var="vListaUdocs" value="${sessionScope[appConstants.deposito.LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY]}"/>
<c:set var="vHueco" value="${sessionScope[appConstants.deposito.HUECO_ORIGEN_COMPACTAR_KEY]}"/>


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

	<html:form action="/reubicacionUdocsAction">
	<input type="hidden" name="method" value="seleccionarAsignableDestino"/>

		<div class="separador8">&nbsp;</div>
		<display:table name="pageScope.vListaUdocs"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="udoc"
			sort="list"
			export="false">
			<display:column style="width:10">
				<c:set var="idUnidad" value="${udoc.idunidaddoc}:${udoc.posudocenui}:${udoc.signaturaudoc}"/>
				<jsp:useBean id="idUnidad" class="java.lang.String"/>
				<html:multibox style="border:0" value="<%=idUnidad%>" property="udocsSeleccionadas"/>
			</display:column>
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado.4" style="width:10">
				<c:choose>
					<c:when test="${udoc.validada}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
					<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.posicion" style="width:10">
				<fmt:formatNumber value="${udoc_rowNum}" pattern="000"/>
			</display:column>

			<display:column titleKey="archigest.archivo.signatura" style="width:20">
				<c:url var="verUDocURL" value="/action/verHuecoAction">
					<c:param name="method" value="verUdocEnUI" />
					<c:param name="idUInstalacion" value="${vHueco.iduinstalacion}" />
					<c:param name="posUDoc" value="${udoc.posudocenui}" />
				</c:url>
				<c:out value="${udoc.signaturaudoc}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.titulo" property="titulo"/>
			<display:column titleKey="archigest.archivo.solicitudes.numExp" property="numExp"/>
			<display:column titleKey="archigest.archivo.descripcion" property="descripcion"/>
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


