<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>

<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>


<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>

<bean:struts id="mappingGestionSeries" mapping="/gestionSeries" />
	
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.solicitarPasoAHistorica"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionSeries.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="cancelURI" value="/action/gestionSeries">
					<c:param name="method" value="goBack"  />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<html:form action="/gestionSeries">
		<input type="hidden" name="method" value="solicitarPasoAHistorica">
		<html:hidden property="idSerie" />

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockContent" direct="true">

		<table class="formulario">
			<tr>
				<td width="150px" class="tdTitulo" style="vertical-align:top;">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> - <c:out value="${serie.titulo}" />
				</td>
			</tr>
		</table>
		</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.datosSolicitud"/></tiles:put>
		<tiles:put name="blockContent" direct="true">

		<security:permissions permission="${appConstants.permissions.GESTION_SOLICITUDES_SERIE}">

			<div class="separador8">&nbsp;</div>

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tdTitulo" width="230" nowrap><bean:message key="archigest.archivo.cf.autorizarAutomaticamente"/>:</td>
					<td class="tdDatos">
						<html:checkbox property="autorizacionAutomatica" styleClass="checkbox" value="true"
							onclick="xDisplay('motivoSolicitud', this.checked?'none':'block')" />
					</td>
				</tr>
			</TABLE>
		</security:permissions>
		
		<c:set var="formBean" value="${requestScope[mappingGestionSeries.name]}" />
		<div id="motivoSolicitud" <c:if test="${formBean.autorizacionAutomatica}">style="display:none"</c:if>>

		<TABLE class="formulario" cellpadding="0" cellspacing="0">
			<tr>
				<td class="tdTitulo" width="210" style="vertical-align:top">
					<bean:message key="archigest.archivo.cf.motivoSolicitud"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<html:textarea property="motivo" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)" />
				</td>
			</tr>
		</TABLE>

		</div>
		</tiles:put>
		</tiles:insert>
		
		</html:form>

	</tiles:put>
</tiles:insert>
