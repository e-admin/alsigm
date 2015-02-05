<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>


<bean:struts id="actionMapping" mapping="/gestionProrroga" />
<c:set var="formBean" value="${requestScope[actionMapping.name]}"/>
<c:set var="isUsuarioArchivoPrestamo" value="${requestScope[appConstants.prestamos.PRESTAMO_USUARIO_ARCHIVO_GESTOR_KEY]}"/>

<script type="text/javascript">
function aceptar(){
	document.getElementById("method").value = "solicitar";
	document.getElementById("formulario").submit();
}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
			<bean:message key="archigest.archivo.prestamo.prorroga.solicitud"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
				</td>
				<td width="10px">&nbsp;</td>
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
			<html:form action="/gestionProrroga" styleId="formulario">
				<html:hidden property="id" />
				<html:hidden property="idPrestamo" />
				<html:hidden property="estado" />
				<input type="hidden" name="method" value="solicitar" id="method">

				<c:out value=""/>
				<table class="formulario">
					<tr>
						<td width="200px" class="tdTitulo" nowrap="nowrap">
							<bean:message key="archigest.archivo.prestamo.fecha.fin.prorroga"/>:&nbsp;
						</td>
						<td class="tdDatos">
						<html:text  size="14" maxlength="10" property="fechaFinProrroga"  styleClass="input" styleId="fechaFinProrroga"/>
							<archigest:calendar
								image="../images/calendar.gif"
								formId="prorrogaForm"
								componentId="fechaFinProrroga"
								format="dd/mm/yyyy"
								enablePast="true" />
						</td>
					</tr>

					<tr>
						<td class="tdTitulo"  nowrap="nowrap">
							<bean:message key="archigest.archivo.prestamo.prorroga.motivo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:textarea rows="4" property="motivoProrroga" onkeypress="maxlength(this,512,false)" onchange="maxlength(this,512,true)" />
						</td>
					</tr>
					<security:permissions action="${appConstants.serviciosActions.AUTORIZAR_PRORROGA_ACTION}">
					<c:if test="${isUsuarioArchivoPrestamo == true}">
					<tr>
						<td width="200px" class="tdTitulo" nowrap="nowrap">
							<bean:message key="archigest.archivo.cf.autorizarAutomaticamente"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:checkbox property="autorizar" styleClass="checkbox"></html:checkbox>
						</td>
					</tr>
					</c:if>
					</security:permissions>
				</table>
			</html:form>
		</div>
		<div class="separador5">&nbsp;</div>

	</tiles:put>
</tiles:insert>
