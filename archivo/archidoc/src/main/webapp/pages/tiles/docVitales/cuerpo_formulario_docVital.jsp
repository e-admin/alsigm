<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<c:set var="tercero" value="${sessionScope[appConstants.documentosVitales.TERCERO_DE_DOCUMENTO_VITAL]}"/>
<c:set var="tiposDocumentosVitales" value="${sessionScope[appConstants.documentosVitales.TIPOS_DOCUMENTOS_VITALES_KEY]}"/>

<bean:struts id="actionMapping" mapping="/gestionDocumentosVitales" />

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.docvitales.docVital.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
					</tiles:insert>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<script>
						function aceptar()
						{
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:aceptar()">
						<html:img page="/pages/images/Ok_Si.gif" 
						titleKey="archigest.archivo.aceptar" 
						altKey="archigest.archivo.aceptar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.informacion"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<html:form action="/gestionDocumentosVitales" enctype="multipart/form-data">
					<input type="hidden" name="method" value="guardarDocumento">
					<html:hidden property="idBdTerceros"/>

					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${tercero.nombre}"/>
							<c:out value="${tercero.primerApellido}"/>
							<c:out value="${tercero.segundoApellido}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.identificacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${tercero.identificacion}"/>
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.tipoDocumento"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:select property="idTipoDocVit">
								<html:options collection="tiposDocumentosVitales" property="id" labelProperty="nombre" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.documentos.documento.form.documentoElectronico"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:file property="file" size="60"/>
						</td>
					</tr>					
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.fechaCaducidad"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<input type="text" size="14" maxlength="10" name="fechaCad" value="<fmt:formatDate value="${documentoVitalForm.fechaCad}" pattern="${appConstants.common.FORMATO_FECHA}" />"/>
							<archigest:calendar 
								image="../images/calendar.gif"
								formId="documentoVitalForm" 
								componentId="fechaCad" 
								format="dd/mm/yyyy" 
								enablePast="false" />
						</td>
					</tr>
					<tr><td class="separador8" colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.docvitales.docVital.observaciones"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:textarea property="observaciones"/>
						</td>
					</tr>
					</html:form>
				</table>
			</tiles:put>
		</tiles:insert>

	</tiles:put>
</tiles:insert>
