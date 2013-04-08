<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="valoracion" value="${sessionScope[appConstants.valoracion.VALORACION_KEY]}" />

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<bean:struts id="mappingGestionValoracion" mapping="/gestionValoracion" />

<%--
		Esta pagina se reutiliza para la recogida de datos necesarios en todos los cambios
		de estado. Por ello lo primero que se hace es segun el cambio de estada que haya sido
		solicitado presenta los campos para que el usuario introduzca la información necesaria
		para llevar a cabo dicho cambio de estado--%>
<tiles:definition id="cambioEstadoForm" template="/pages/tiles/PADataBlockLayout.jsp">
<tiles:put name="blockContent" direct="true">
	<script>
		function submitCambioEstadoForm() {
			var form = document.forms['<c:out value="${mappingGestionValoracion.name}" />'];
			form.submit();
		}	
	</script>
	<html:form action="/gestionValoracion">
	<input type="hidden" name="id" value="<c:out value="${valoracion.id}" />">
	<c:choose>
		<c:when test="${param.method == 'solicitarValidacion'}">
			<%--Se define el titulo para el bloque en el que se presentara el formulario de
					recogida de datos para el cambio de estado que ha sido solicitado		--%>
			<c:set var="blockTitle" value="Solicitar validación" />
			<%--Se define igualmente el href para el boton de aceptar el cambio de estado	--%>
			<c:set var="actionAceptar" value="javascript:submitCambioEstadoForm()" />
			<input type="hidden" name="method" value="procesarSolicitudValidacion">
			<table class="formulario">
			<tr>
				<td class="tdTitulo" width="300px">
					<bean:message key="archigest.archivo.cf.autorizarAutomaticamente"/>:&nbsp;
				</td>
				<td class="tdDatos"><input type="checkbox" name="autorizarSolicitudValidacionAutomaticamente" value="true" class="checkbox"></td>
			</tr>
			</table>
		</c:when>
		<c:when test="${param.method == 'recogerDatosRechazoValidacion' || param.method == 'rechazarValidacion'}">
			<%--Se define el titulo para el bloque en el que se presentara el formulario de
					recogida de datos para el cambio de estado que ha sido solicitado		--%>
			<c:set var="blockTitle" value="Rechazar Validación" />
			<%--Se define igualmente el href para el boton de aceptar el cambio de estado	--%>
			<c:set var="actionAceptar" value="javascript:submitCambioEstadoForm()" />
			<input type="hidden" name="method" value="rechazarValidacion">
			<table class="formulario">
			<tr>
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.motivoRechazo"/>:&nbsp;</td>
				<td class="tdDatos"><html:textarea property="motivoRechazo" rows="2" /></td>
			</tr>
			</table>
		</c:when>
		<c:when test="${param.method == 'recogerDatosRechazoEvaluacion' || param.method == 'rechazarEvaluacion'}">
			<%--Se define el titulo para el bloque en el que se presentara el formulario de
					recogida de datos para el cambio de estado que ha sido solicitado		--%>
			<c:set var="blockTitle" value="Evaluación rechazada" />
			<%--Se define igualmente el href para el boton de aceptar el cambio de estado	--%>
			<c:set var="actionAceptar" value="javascript:submitCambioEstadoForm()" />
			<input type="hidden" name="method" value="rechazarEvaluacion">

			<table class="formulario">
			<tr>
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.fechaEvaluacion"/>:&nbsp;</td>
				<td class="tdDatos">
					<html:text property="fechaEvaluacion" styleClass="input" size="12" maxlength="10"/>&nbsp;
					<c:set var="rechazoEvaluacionFormName" value="${mappingGestionValoracion.name}" />
					<jsp:useBean id="rechazoEvaluacionFormName" type="java.lang.String" />
					<archigest:calendar 
							image="../images/calendar.gif"
                            formId="<%=rechazoEvaluacionFormName%>" 
                            componentId="fechaEvaluacion" 
                            format="dd/mm/yyyy"
                            enablePast="true" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.valoracion.motivoRechazo"/>:&nbsp;</td>
				<td class="tdDatos"><html:textarea property="motivoRechazo" rows="2" /></td>
			</tr>
			</table>
		</c:when>
		<c:when test="${param.method == 'recogerDatosAutorizacionEvaluacion' || param.method == 'autorizarEvaluacion'}">
			<%--Se define el titulo para el bloque en el que se presentara el formulario de
					recogida de datos para el cambio de estado que ha sido solicitado		--%>
			<c:set var="blockTitle" value="Evaluación autorizada" />
			<%--Se define igualmente el href para el boton de aceptar el cambio de estado	--%>
			<c:set var="actionAceptar" value="javascript:submitCambioEstadoForm()" />
			<input type="hidden" name="method" value="autorizarEvaluacion">
			<table class="formulario">
			<tr>
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.fechaEvaluacion"/>:&nbsp;</td></td>
				<td class="tdDatos">
					<html:text property="fechaEvaluacion" styleClass="input" size="12"/>&nbsp;
					<c:set var="autirizacionEvaluacionFormName" value="${mappingGestionValoracion.name}" />
					<jsp:useBean id="autirizacionEvaluacionFormName" type="java.lang.String" />
					<archigest:calendar 
							image="../images/calendar.gif"
                            formId="<%=autirizacionEvaluacionFormName%>" 
                            componentId="fechaEvaluacion" 
                            format="dd/mm/yyyy"
                            enablePast="true" />
				</td>
			</tr>
			</table>
		</c:when>
		<c:when test="${param.method == 'recogerDatosDictamen' || param.method == 'rechazarDictamen' || param.method == 'autorizarDictamen'}">
			<%--Se define el titulo para el bloque en el que se presentara el formulario de
					recogida de datos para el cambio de estado que ha sido solicitado		--%>
			<c:set var="blockTitle" value="Dictamen" />
			
			<%--Se define igualmente el href para el boton de aceptar el cambio de estado	--%>
			<c:set var="actionAceptar" value="javascript:submitCambioEstadoForm()" />
			<input type="hidden" name="method" value="autorizarDictamen">

			<c:set var="dictamenFormName" value="${mappingGestionValoracion.name}" />
			<jsp:useBean id="dictamenFormName" type="java.lang.String" />
			
			<%--Boletines Oficiales --%>
			<c:set var="boletines" value="${sessionScope[appConstants.valoracion.LISTA_BOLETINES_OFICIALES_KEY]}" />
			
			<table class="formulario">
			<tr>
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.tipoDictamen"/>:&nbsp;</td>
				<td class="tdDatos">
					<script>
						function checkTipoDictamen()
						{
							var combo = document.forms["<c:out value="${dictamenFormName}" />"].tipoDictamen;
							var value = combo[combo.selectedIndex].value;
							if (value == 1) {
								xDisplay('trMotivoRechazo', 'none');
								xDisplay('trFechaResolucionDictamen', 'block');
								xDisplay('trBoletinDictamen', 'block');
								xDisplay('trFechaBoletinDictamen', 'block');
								if(!document.all){
									xDisplay('trFechaResolucionDictamen', 'table-row');
									xDisplay('trBoletinDictamen', 'table-row');
									xDisplay('trFechaBoletinDictamen', 'table-row');
								}
								document.forms["<c:out value="${dictamenFormName}" />"].method.value = "autorizarDictamen";
							} else {
								xDisplay('trMotivoRechazo', 'block');
								if(!document.all){
									xDisplay('trMotivoRechazo', 'table-row');
								}
								xDisplay('trFechaResolucionDictamen', 'none');
								xDisplay('trBoletinDictamen', 'none');
								xDisplay('trFechaBoletinDictamen', 'none');
								document.forms["<c:out value="${dictamenFormName}" />"].method.value = "rechazarDictamen";
							}
						}
					</script>
					<html:select property="tipoDictamen" styleClass="input" onchange="javascript:checkTipoDictamen()">
						<html:option key="archigest.archivo.valoracion.tipoDictamen.favorable" value="1"/>
						<html:option key="archigest.archivo.valoracion.tipoDictamen.desfavorable" value="2"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.fechaDictamen"/>:&nbsp;</td>
				<td class="tdDatos">
					<html:text property="fechaDictamen" styleClass="input" size="12"/>&nbsp;
					<archigest:calendar 
							image="../images/calendar.gif"
                            formId="<%=dictamenFormName%>" 
                            componentId="fechaDictamen" 
                            format="dd/mm/yyyy"
                            enablePast="true" />
				</td>
			</tr>
			<tr id="trMotivoRechazo">
				<td class="tdTitulo" ><bean:message key="archigest.archivo.valoracion.motivoRechazo"/>:&nbsp;</td></td>
				<td class="tdDatos"><html:textarea property="motivoRechazo" rows="2"/></td>
			</tr>
			<tr id="trFechaResolucionDictamen">
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.fechaResolucionDictamen"/>:&nbsp;</td>
				<td class="tdDatos">
					<html:text property="fechaResolucionDictamen" styleClass="input" size="12"/>&nbsp;
					<archigest:calendar 
							image="../images/calendar.gif"
                            formId="<%=dictamenFormName%>" 
                            componentId="fechaResolucionDictamen" 
                            format="dd/mm/yyyy"
                            enablePast="true" />
				</td>
			</tr>
			<tr id="trBoletinDictamen">
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.boletinDictamen"/>:&nbsp;</td>
				<td class="tdDatos">
					<html:select property="boletinDictamen" styleClass="input">
						<html:options collection="boletines" property="valor" labelProperty="valor" />
					</html:select>
				</td>
			</tr>
			<tr id="trFechaBoletinDictamen">
				<td class="tdTitulo" width="300px"><bean:message key="archigest.archivo.valoracion.fechaBoletinDictamen"/>:&nbsp;</td>
				<td class="tdDatos">
					<html:text property="fechaBoletinDictamen" styleClass="input" size="12"/>&nbsp;
					<archigest:calendar 
							image="../images/calendar.gif"
                            formId="<%=dictamenFormName%>" 
                            componentId="fechaBoletinDictamen" 
                            format="dd/mm/yyyy"
                            enablePast="true" />
				</td>
			</tr>
			<script>checkTipoDictamen();</script>
			</table>
		</c:when>
	</c:choose>
	</html:form>
</tiles:put>
</tiles:definition>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.valoracion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td nowrap>
				<a class="etiquetaAzul12Bold" href="<c:out value="${actionAceptar}" escapeXml="false"/>" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.aceptar"/>&nbsp;
				</a>
				</td>
				<TD width="10">&nbsp;</TD>
				<TD>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
						<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
					</tiles:insert>
				<TD>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><c:out value="${blockTitle}" /></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<tiles:insert beanName="cambioEstadoForm" />
			</tiles:put>			
		</tiles:insert>
		<div class="separador5"></div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.valoracion.serieValorada"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<c:set var="serieValorada" value="${valoracion.serie}" />
				<table class="formulario">
					<tr>
						<td  width="300px" class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${serieValorada.codigo}" />
						</td>
					</tr>
					<tr>
						<td width="300px" class="tdTitulo">
							<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${serieValorada.denominacion}" />
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.valoracion.estadoValoracion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<fmt:message key="archigest.archivo.valoracion.estado${valoracion.estado}" />
						</td>
					</tr>
					<c:if test="${!empty valoracion.motivoRechazo}">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.motivoRechazo"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${valoracion.motivoRechazo}" />
						</td>
					</tr>
					</c:if>
					<c:if test="${!empty valoracion.fechaValidacion}">
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.fechaValidacion"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<fmt:formatDate value="${valoracion.fechaValidacion}" pattern="${FORMATO_FECHA}"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${!empty valoracion.fechaEvaluacion}">
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.fechaEvaluacion"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<fmt:formatDate value="${valoracion.fechaEvaluacion}" pattern="${FORMATO_FECHA}"/>
							</td>
						</tr>
					</c:if>
				</table>			
			</tiles:put>	
		</tiles:insert>

		<tiles:insert template="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">datosValoracion</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.datosValoracion"/></tiles:put>
			<tiles:put name="dockableContentVisible" direct="true">true</tiles:put>
			<tiles:put name="dockableContent" value="valoracion.datosValoracion" type="definition" />
		</tiles:insert>

		<tiles:insert template="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.seriesPrecedentes"/></tiles:put>
			<tiles:put name="blockName" direct="true">seriesPrecedentes</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:set var="seriesPrecedentes" value="${valoracion.listaSeriesPrecedentes}" />
				<div style="padding-top:4px;padding-bottom:4px">
				<display:table name="pageScope.seriesPrecedentes"
					id="seriePrecedente" 
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.valoracion.msgNoSeriesPrecedentes"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.codigo">
						<c:url var="vistaSerieURL" value="/action/gestionSeries">
							<%--Se sustituye verserie por verDesdeFondos --%>
							<c:param name="method" value="verDesdeFondos" />
							<c:param name="id" value="${seriePrecedente.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${vistaSerieURL}" escapeXml="false"/>"><c:out value="${seriePrecedente.codigo}" /></a>
					</display:column>
					<display:column titleKey="archigest.archivo.denominacion">
						<c:out value="${seriePrecedente.titulo}" />
					</display:column>
				</display:table>
				</div>
			</tiles:put>
		</tiles:insert>
		<tiles:insert template="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.seriesRelacionadas"/></tiles:put>
			<tiles:put name="blockName" direct="true">seriesRelacionadas</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:set var="seriesRelacionadas" value="${valoracion.listaSeriesRelacionadas}" />
				<div style="padding-top:4px;padding-bottom:4px">
				<display:table name="pageScope.seriesRelacionadas"
					id="serieRelacionada" 
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.valoracion.msgNoSeriesRelacionadas"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.codigo">
						<c:url var="vistaSerieURL" value="/action/gestionSeries">
							<%--Se sustituye verserie por verDesdeFondos --%>
							<c:param name="method" value="verDesdeFondos" />
							<c:param name="id" value="${serieRelacionada.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${vistaSerieURL}" escapeXml="false"/>"><c:out value="${serieRelacionada.codigo}" /></a>
					</display:column>
					<display:column titleKey="archigest.archivo.denominacion">
						<c:out value="${serieRelacionada.titulo}" />
					</display:column>
				</display:table>
				</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>