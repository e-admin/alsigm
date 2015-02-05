<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.solicitudes.devolucion.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<script>
						function printReport()
						{
							var form = document.forms["formDevs"];
							if (form.fechaIni.value == "" || form.fechaFin.value == "")
							{
								alert("<bean:message key="archigest.archivo.solicitudes.devolucion.error.empty"/>");
							}
							else
								form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" 
						href="javascript:printReport()">
						<html:img page="/pages/images/print.gif" 
						titleKey="archigest.archivo.imprimir" 
						altKey="archigest.archivo.imprimir"
						styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.imprimir"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<div class="bloque">		
			<table class="formulario">
				<form name="formDevs" method="post" action="../../action/informeDevolucionFechas">
					<tr>
						<td class="tdTituloFicha" width="100px">
							<bean:message key="archigest.archivo.desde"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<input type="text" maxlength="10" name="fechaIni" size="11" readonly="readonly"/>
							<archigest:calendar 
								image="../images/calendar.gif"
								formId="formDevs" 
								componentId="fechaIni" 
								format="dd/mm/yyyy" 
								enablePast="true" />
						</td>
					</tr>
					<tr>
						<td class="tdTituloFicha">
							<bean:message key="archigest.archivo.hasta"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<input type="text" name="fechaFin" size="11" readonly="readonly"/>
							<archigest:calendar 
								image="../images/calendar.gif"
								formId="formDevs" 
								componentId="fechaFin" 
								format="dd/mm/yyyy" 
								enablePast="true" />
						</td>
					</tr>
				</form>
			</table>
		</div>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
</tiles:insert>