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

<c:set var="conPermisos" value="${false}"/>

<security:permissions action="${appConstants.serviciosActions.AUTORIZAR_PRORROGA_ACTION}">
	<c:set var="conPermisos" value="${true}"/>
</security:permissions>

<security:permissions action="${appConstants.serviciosActions.AUTORIZAR_PRORROGA_ACTION}">
<script type="text/javascript">
function autorizar(){
	document.getElementById("method").value = "autorizarprorrogadesdevista";
	document.getElementById("formulario").submit();
}
</script>
</security:permissions>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.prestamo.prorroga.datos"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<security:permissions action="${appConstants.serviciosActions.AUTORIZAR_PRORROGA_ACTION}">
				<c:if test="${formBean.solicitada}">
					<td>
						<a class="etiquetaAzul12Bold" href="javascript:autorizar()" >
							<html:img
								page="/pages/images/pro_acep.gif"
								altKey="archigest.archivo.solicitudes.autorizarProrroga"
								titleKey="archigest.archivo.solicitudes.autorizarProrroga" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.autorizarProrroga"/>
							</a>
					</td>
					<td width="10px">&nbsp;</td>
				</c:if>
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
			<html:form action="/gestionProrroga" styleId="formulario">
				<html:hidden property="id" />
				<html:hidden property="idPrestamo" />
				<html:hidden property="estado" />
				<input type="hidden" name="method" value="solicitar" id="method">

				<c:out value=""/>
				<table class="formulario">
					<tr>
						<td width="200px" class="tdTitulo" nowrap="nowrap">
							<bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:&nbsp;
						</td>
						<td class="tdDatos">
						<c:choose>
							<c:when test="${formBean.solicitada && conPermisos == true}">
								<html:text  size="14" maxlength="10" property="fechaFinProrroga"  styleClass="input" styleId="fechaFinProrroga"/>
									<archigest:calendar
										image="../images/calendar.gif"
										formId="prorrogaForm"
										componentId="fechaFinProrroga"
										format="dd/mm/yyyy"
								enablePast="true" />
							</c:when>
							<c:otherwise>
								<html:hidden property="fechaFinProrroga"/>
								<c:out value="${formBean.fechaFinProrroga}"/>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>

					<tr>
						<td width="200px" class="tdTitulo" nowrap="nowrap">
							<bean:message key="archigest.archivo.estado"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<fmt:message key="archigest.archivo.prestamo.estadoSolictudProrroga.${formBean.estado}"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo"  nowrap="nowrap">
							<bean:message key="archigest.archivo.prestamo.prorroga.motivo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${formBean.motivoProrroga}"/>
						</td>
					</tr>
				</table>
			</html:form>
		</div>
		<div class="separador5">&nbsp;</div>

	</tiles:put>
</tiles:insert>
