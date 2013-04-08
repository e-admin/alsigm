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
<c:set var="vListaReubicacion" value="${sessionScope[appConstants.deposito.LISTA_REUBICACION_KEY]}"/>

<c:set var="reubicacionFinalizada" value="${sessionScope[appConstants.deposito.REUBICACION_FINALIZADA]}"/>





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
			<tr>
				<td colspan="2">
					<div class="separador8">&nbsp;</div>
				</td>
			</tr>
		</TABLE>

	</tiles:put>
</tiles:definition>

<tiles:definition id="infoReubicacion1" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.infoReubicar"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
		<div class="separador8">&nbsp;</div>
		<display:table name="pageScope.vListaReubicacion"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="reubicacion" 
			sort="list"
			export="false">
			<display:column style="width:10px;valign=top">
				<bean:message key="archigest.archivo.deposito.rutaOrigen"/>
				<br>
				<bean:message key="archigest.archivo.deposito.rutaDestino"/>
			</display:column>
			<display:column titleKey="archigest.archivo.ruta">
				<c:out value="${reubicacion.pathOrigen}"/><br>
				<c:out value="${reubicacion.pathDestino}"/>
			</display:column>			
			<display:column titleKey="archigest.archivo.signatura" style="width:10px;valign=top">
				<c:out value="${reubicacion.signaturaOrigen}"/><br>
				<c:if test="${reubicacion.signaturaOrigen != reubicacion.signaturaDestino}">
					<c:out value="${reubicacion.signaturaDestino}"/>
				</c:if>
				&nbsp;
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.formato" >
				<c:out value="${reubicacion.nombreFormatoOrigen}"/><br>
				<c:if test="${reubicacion.nombreFormatoOrigen != reubicacion.nombreFormatoDestino}">
					<c:out value="${reubicacion.nombreFormatoDestino}"/>
				</c:if>
				&nbsp;
			</display:column>
		</display:table>
		<div class="separador8">&nbsp;</div>
	</tiles:put>
</tiles:definition>

<tiles:definition id="infoReubicacion" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.infoReubicar"/>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
	<div class="separador8">&nbsp;</div>
		<display:table name="pageScope.vListaReubicacion"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="reubicacion" 
			sort="list"
			export="false">
			<display:column titleKey="archigest.archivo.signatura" style="width:10px;valign=top">
				<c:out value="${reubicacion.signaturaOrigen}"/><br>
			</display:column>
			<display:column titleKey="archigest.archivo.transferencias.formato">
				<c:out value="${reubicacion.nombreFormatoOrigen}"/><br>
			</display:column>
			<display:column titleKey="archigest.archivo.deposito.rutaOrigen">
				<c:out value="${reubicacion.pathOrigen}"/>
			</display:column>	
			<display:column titleKey="archigest.archivo.deposito.rutaDestino">
				<c:out value="${reubicacion.pathDestino}"/>
			</display:column>		
		</display:table>
<div class="separador8">&nbsp;</div>
	</tiles:put>
</tiles:definition>

<%-- COMPOSICION DE PAGINA A MOSTRAR --%>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.deposito.informe.reubicacion.uinst"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		
		<TABLE cellpadding="0" cellspacing="0">
		<TR>
				<td>
						<c:url var="informeURL" value="/action/informeReubicacionUinst"/>
					 
						<a class="etiquetaAzul12Bold" href="<c:out value="${informeURL}" escapeXml="false"/>">
						<html:img page="/pages/images/documentos/doc_pdf.gif" 
						        altKey="archigest.archivo.informe" 
						        titleKey="archigest.archivo.informe" 
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.informe"/></a>
					</td>	
					<td width="10">&nbsp;</td>

				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>									
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		
		<tiles:insert beanName="infoReubicacion" />
		<div class="separador8">&nbsp;</div>
	</tiles:put>
</tiles:insert>
