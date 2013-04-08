<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="tipoDocumentoVital" value="${sessionScope[appConstants.documentosVitales.TIPO_DOCUMENTO_VITAL_KEY]}"/>

<bean:struts id="actionMapping" mapping="/gestionTiposDocumentos" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.documentosVitalesActions.EDICION_DOCUMENTOS_VITALES_ACTION}">
				<td nowrap>
					<script>
						function remove()
						{
							<c:url var="eliminarURL" value="/action/gestionTiposDocumentos">
								<c:param name="method" value="remove" />
								<c:param name="id" value="${tipoDocumentoVital.id}" />
							</c:url>
							if (confirm("<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.delete.confirm.msg"/>"))
								window.location = "<c:out value="${eliminarURL}" escapeXml="false"/>";
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:remove()">
						<html:img page="/pages/images/deleteDoc.gif" 
						titleKey="archigest.archivo.eliminar" 
						altKey="archigest.archivo.eliminar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.eliminar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true" />
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
				<security:permissions action="${appConstants.documentosVitalesActions.EDICION_DOCUMENTOS_VITALES_ACTION}">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td nowrap>
								<c:url var="edicionURL" value="/action/gestionTiposDocumentos">
									<c:param name="method" value="form" />
									<c:param name="id" value="${tipoDocumentoVital.id}" />
								</c:url>
								<a class="etiquetaAzul12Bold" 
									href="<c:out value="${edicionURL}" escapeXml="false"/>">
									<html:img page="/pages/images/editDoc.gif" 
									titleKey="archigest.archivo.editar" 
									altKey="archigest.archivo.editar"
									styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.editar"/>
								</a>
							</td>
						</tr>
					</table>
				</security:permissions>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos"><c:out value="${tipoDocumentoVital.nombre}" /></td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.descripcion"/>:&nbsp;				
						</td>
						<td class="tdDatos"><c:out value="${tipoDocumentoVital.descripcion}" /></td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
