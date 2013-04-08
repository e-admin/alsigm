<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/gestionTiposDocumentos" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
		<c:when test="${!empty tipoDocumentoVitalForm.id}">
			<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.edicion.caption"/>
		</c:when>
		<c:otherwise>
			<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.creacion.caption"/>
		</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.documentosVitalesActions.EDICION_DOCUMENTOS_VITALES_ACTION}">
				<td nowrap>
					<script>
						function save()
						{
							var form = document.forms["<c:out value="${actionMapping.name}" />"];
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:save()">
						<html:img page="/pages/images/Ok_Si.gif" 
						titleKey="archigest.archivo.aceptar" 
						altKey="archigest.archivo.aceptar"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				</security:permissions>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">		
			<table class="formulario">
				<html:form action="/gestionTiposDocumentos">
				<input type="hidden" name="method" value="save">
				<html:hidden property="id"/>

				<tr>
					<td class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:text property="nombre" size="32" maxlength="32"/>
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.docvitales.tipoDocumentoVital.descripcion"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:textarea property="descripcion" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
					</td>
				</tr>
				</html:form>
			</table>
		</div>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
</tiles:insert>
