<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="vSerie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.solicitarAutorizacion"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0"><tr>
			<td>
				<script>
					<bean:struts id="form" mapping="/gestionSeries" />
					function goOn() {
						var solicitudForm = document.forms['<c:out value="${form.name}" />'];
						solicitudForm.submit();
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
	        <td>
				<c:url var="closeURL" value="/action/navigateAction">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="<c:out value="${closeURL}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
		    </td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

<html:form action="/gestionSeries">

	<div class="bloque"> <%-- primer bloque de datos --%>
		<table class="formulario">

			<input type="hidden" name="method" value="procesarSolicitudAutorizacionCambios">
			<html:hidden property="idSerie" />
			<tr>
				<td width="100px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
				</td>
				<td class="tdDatosBold"><c:out value="${vSerie.codigo}"/>
				</td>
			</tr>
			<tr>
				<td width="100px" class="tdTitulo">
					<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
				</td>
				<td class="tdDatosBold"><c:out value="${vSerie.denominacion}"/>
				</td>
			</tr>
		</table>
	</div>

	<div class="separador5">&nbsp;</div>

	<tiles:insert template="/pages/tiles/PADockableBlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.cf.identificacionSerie"/>
		</tiles:put>
		<tiles:put name="blockName" direct="true">infoIdentificacion</tiles:put>

		<bean:define id="fechaEditable" value="false" toScope="request"/>
		<tiles:put name="dockableContent" direct="true">
			<jsp:include page="identificacion.jsp" flush="true" />
		</tiles:put>
	</tiles:insert>

	<div class="separador5">&nbsp;</div>




	<div class="bloque"> <%-- primer bloque de datos --%>

			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.cf.motivoSolicitud"/>:&nbsp;
					</td>
				</tr>
				<tr>
					<td class="tdDatos">
						<html:textarea property="motivo" rows="2" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>
					</td>
				</tr>
	<security:permissions action="${appConstants.fondosActions.SOLICITUD_Y_AUTORIZACION_AUTOMATICA_CAMBIOS_ACTION}">
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.cf.autorizarAutomaticamente"/>:&nbsp;
						<html:checkbox property="autorizacionAutomatica" styleClass="checkbox" />
					</td>
				</tr>
	</security:permissions>
			</TABLE>

	</div>



	</html:form>

	</tiles:put>
</tiles:insert>