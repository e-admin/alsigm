<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>



<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.creacion"/>
		<bean:message key="archigest.archivo.transferencias.prevision"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD>
				<bean:struts id="mappingGestionPrevisiones" mapping="/gestionPrevisiones" />
				<script>
					function seleccionarTipoTransferencia() {
						var form = document.forms['<c:out value="${mappingGestionPrevisiones.name}" />'];
						if (!elementSelected(form.tipooperacion))
							alert("<bean:message key='archigest.archivo.transferecnias.previsiones.selTipoCrear'/>");
						else {
							form.submit();
						}
					}
				</script>

				<a class="etiquetaAzul12Bold" href="javascript:seleccionarTipoTransferencia()" >
					<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="urlCancelacion" value="/action/gestionPrevisiones" >
					<c:param name="method" value="goBack"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${urlCancelacion}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
		   </TD>
	   		<c:if test="${appConstants.configConstants.mostrarAyuda}">
				<td width="10">&nbsp;</td>
				<td>
					<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
					<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
					<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/transferencias/creacionPrevision.html');">
					<html:img page="/pages/images/help.gif" 
					        altKey="archigest.archivo.ayuda" 
					        titleKey="archigest.archivo.ayuda" 
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
				</td>
			</c:if>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.selTipoPrevision" /></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<html:form action="/gestionPrevisiones" method="POST">
				<input type="hidden" name="method" value="nueva">
				<div class="separador12">&nbsp;</div>
				<TABLE cellpadding=0 cellspacing=0 align="center">
				<security:permissions action="${appConstants.transferenciaActions.ALTA_PREVISION_ORDINARIA}">
					<TR>
						<TD class="etiquetaAzul11Bold" width="80">
							&nbsp;<bean:message key="archigest.archivo.transferencias.ord"/>:
						</TD>
						<TD class="etiquetaAzul11Bold">
							<html:img page="/pages/images/pixel.gif" width="15" styleClass="imgTextTop"/>
							<input type="radio" name="tipooperacion" value="1" <c:if test="${param.tipooperacion == 1}">checked</c:if>>
						</TD>
					<TR>
				</security:permissions>
				<security:permissions action="${appConstants.transferenciaActions.ALTA_PREVISION_EXTRAORDINARIA}">
				<TR>
					<TD colspan="2"><html:img page="/pages/images/pixel.gif" height="5"/></TD>
				</TR>
					<TR>
						<TD class="etiquetaAzul11Bold" valign="top">
							&nbsp;<bean:message key="archigest.archivo.transferencias.ext"/>:
						</TD>
						<TD class="etiquetaAzul11Bold">
							<TABLE cellpadding=0 cellspacing=0>
								<TR>
									<TD class="etiquetaAzul11Bold">
										<html:img page="/pages/images/pixel.gif" width="15" />
										<input type="radio" name ="tipooperacion" value="2" <c:if test="${param.tipooperacion == 2}">checked</c:if>>
									</TD>
									<TD class="etiquetaAzul11Bold">
										&nbsp;<bean:message key="archigest.archivo.transferencias.sinSigConDet"/>
									<TD>
								</TR>
	
								<security:permissions action="${appConstants.transferenciaActions.ALTA_PREVISION_EXTRAORDINARIA_ARCHIVO}">
								<TR>
									<TD class="etiquetaAzul11Bold">
										<html:img page="/pages/images/pixel.gif" width="15"/>
										<input type="radio" name ="tipooperacion" value="3" <c:if test="${param.tipooperacion == 3}">checked</c:if>>
									</TD>
									<TD class="etiquetaAzul11Bold">
										&nbsp;<bean:message key="archigest.archivo.transferencias.sinSigSinDet"/>
									<TD>
								</TR>
								<TR>
									<TD class="etiquetaAzul11Bold">
										<html:img page="/pages/images/pixel.gif" width="15" styleClass="imgTextTop"/>
										<input type="radio" name ="tipooperacion" value="4" <c:if test="${param.tipooperacion == 4}">checked</c:if>>
									</TD>
									<TD class="etiquetaAzul11Bold">
										&nbsp;<bean:message key="archigest.archivo.transferencias.conSig"/>
									</TD>
								<TR>
								</security:permissions>
							</TABLE>
						<TD>
					</TR>
				</security:permissions>
				<c:if test="${appConstants.configConstants.permitirTransferenciasEntreArchivos}">
					<security:permissions action="${appConstants.transferenciaActions.ALTA_PREVISION_ENTRE_ARCHIVOS}">
						<TR>
							<TD colspan="2"><html:img page="/pages/images/pixel.gif" height="5"/></TD>
						</TR>
						<TR>
							<TD class="etiquetaAzul11Bold" valign="top">
								&nbsp;<bean:message key="archigest.archivo.transferencias.entre.archivos"/>:
							</TD>
							<TD class="etiquetaAzul11Bold">
								<TABLE cellpadding=0 cellspacing=0>
									<TR>
										<TD class="etiquetaAzul11Bold">
											<html:img page="/pages/images/pixel.gif" width="15" />
											<input type="radio" name ="tipooperacion" value="5" <c:if test="${param.tipooperacion == 5}">checked</c:if>>
										</TD>
									</TR>
								</TABLE>
							<TD>
						</TR>
					</security:permissions>
				</c:if>	
				</TABLE>
				</html:form>
			</tiles:put>
		</tiles:insert>	

	</tiles:put>
</tiles:insert>	