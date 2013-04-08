<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<c:set var="conPermisos" value="${false}"/>

<security:permissions action="${appConstants.serviciosActions.AUTORIZAR_PRORROGA_ACTION}">
	<c:set var="conPermisos" value="${true}"/>
</security:permissions>

<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>
<c:set var="bProrroga" value="${bPrestamo.prorrogaSolicitada}"/>
<c:set var="procesoAutorizacion" value="${requestScope[appConstants.prestamos.PROCESO_AUTORIZACON_PRESTAMO_KEY]}"/>

<c:out value="${procesoAutorizacion}"/>


<bean:struts id="actionMapping" mapping="/gestionProrroga" />
<c:set var="prorrogaBean" value="${requestScope[actionMapping.name]}"/>

<html:form action="/gestionProrroga" styleId="formularioProrroga">
<input type="text" id="methodProrroga" name="method" />
<input type="hidden" name="idPrestamo" value='<c:out value="${bPrestamo.id}"/>'/>
<input type="hidden" name="id" value='<c:out value="${bProrroga.id}"/>'/>


<script>
	function denegarProrroga2(idprestamo,identificadorPrestamo)
	{
		<c:url var="envioURL" value="/action/gestionPrestamos">
			<c:param name="method" value="denegarprorrogadesdevista"/>
			<c:param name="idMotivo" value=""/>
		</c:url>

		window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+
			document.getElementById('motivorechazoprorroga').value+'&idprestamo='+idprestamo;

	}

	function ocultarDenegarProrroga(){
			document.getElementById("denegarProrroga").style.display = 'none';
	}


	function ocultarAutorizarProrroga(){
			document.getElementById("autorizarprorroga").style.display = 'none';
	}

	function aceptarAutorizarProrroga(){
		document.getElementById("autorizar").value="1";
		document.getElementById("methodProrroga").value = "autorizarprorrogadesdevista";
		document.getElementById("formularioProrroga").submit();
	}

</script>
	<div class="bloque hidden" id="denegarProrroga" >
		<TABLE class="w100" cellpadding="4" cellspacing="4">
		<TR>
				   	<TD width="100%" style="text-align:right;">
						<a class="etiquetaAzul12Bold" href="javascript:denegarProrroga2('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>');" >
							<html:img
								page="/pages/images/Ok_Si.gif" border="0"
								altKey="archigest.archivo.denegar"
								titleKey="archigest.archivo.denegar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.denegar"/>
						</a>&nbsp;&nbsp;&nbsp;
				   		<a class="etiquetaAzul12Bold" href="javascript:ocultarDenegarProrroga();">
							<html:img
								page="/pages/images/Ok_No.gif" border="0"
								altKey="archigest.archivo.cancelar"
								titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
				   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				   		</a>&nbsp;
				   	</TD>
		</TR>
	  	<TR>
			<TD class="etiquetaAzul12Bold" style="text-align:center;">
				<bean:message key="archigest.archivo.prestamos.anadirMotivoRechazo" />&nbsp;&nbsp;
				<select id="motivorechazoprorroga" name="idmotivorechazoprorroga">
							<c:forEach items="${sessionScope[appConstants.prestamos.LISTA_MOTIVO_RECHAZO_PRORROGA_KEY]}" var="motivo" varStatus="status">
								<option  value="<c:out value="${motivo.id}"/>">
									<c:out value="${motivo.motivo}"/>
								</option>
							</c:forEach>
				</select>
			</TD>
		</TR>
	  	<TR><TD height="10px">&nbsp;</TD>
	  	</TR>
		</TABLE>
	</div>

	<div class="bloque hidden" id="autorizarprorroga">
		<table class="w100" cellpadding="4" cellspacing="4">
			<tr>
		   	<td width="100%" style="text-align:right;">
			<a class="etiquetaAzul12Bold" href="javascript:aceptarAutorizarProrroga('<c:out value="${bPrestamo.id}" />');" >
				<html:img
					page="/pages/images/Ok_Si.gif" border="0"
					altKey="archigest.archivo.autorizar"
					titleKey="archigest.archivo.autorizar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.autorizar"/>
				</a>&nbsp;&nbsp;&nbsp;
		   		<a class="etiquetaAzul12Bold" href="javascript:ocultarAutorizarProrroga();">
				<html:img
					page="/pages/images/Ok_No.gif" border="0"
					altKey="archigest.archivo.cancelar"
					titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>&nbsp;
			  </td>
			</tr>
		</table>
		<table class="formulario">
			<tr>
				<td width="200px" class="tdTitulo" nowrap="nowrap">
					<bean:message key="archigest.archivo.prestamo.fecha.fin.prorroga"/>:&nbsp;
				</td>
				<td class="tdDatos">
				<c:choose>
					<c:when test="${bProrroga.solicitada && conPermisos == true}">
						<input type="text" size="14" maxlength="10" name="fechaFinProrroga"  class="input" id="fechaFinProrroga" value="<c:out value="${bProrroga.fechaFinProrrogaAsString}"/>"/>
						<archigest:calendar
						image="../images/calendar.gif"
						formId="prorrogaForm"
						componentId="fechaFinProrroga"
						format="dd/mm/yyyy"
						enablePast="false" />
					</c:when>
					<c:otherwise>
						<input type="text" name="fechaFinProrroga" value="<c:out value="${bProrroga.fechaFinProrrogaAsString}"/>"/>
							<c:out value="${bProrroga.fechaFinProrrogaAsString}"/>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo"  nowrap="nowrap">
					<bean:message key="archigest.archivo.prestamo.prorroga.motivo"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<c:out value="${bProrroga.motivoProrroga}"/>
				</td>
			</tr>
		</table>
	</div>
</html:form>