<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="estadoPendiente"><c:out value="${appConstants.transferencias.estadoCotejo.PENDIENTE.identificador}"/></c:set>
<c:set var="estadoRevisada"><c:out value="${appConstants.transferencias.estadoCotejo.REVISADA.identificador}"/></c:set>
<c:set var="estadoErrores"><c:out value="${appConstants.transferencias.estadoCotejo.ERRORES.identificador}"/></c:set>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
<c:set var="infoUdocReeacr" value="${sessionScope[appConstants.transferencias.INFO_UDOCREEACR]}" />


<c:set var="VER_BOTON_SIGNATURAR" value="${requestScope[appConstants.transferencias.VER_BOTON_SIGNATURAR_KEY]}" />
<c:set var="VER_BOTON_ACEPTAR_COTEJO" value="${requestScope[appConstants.transferencias.VER_BOTON_ACEPTAR_COTEJO_KEY]}" />
<c:set var="VER_BOTON_FINALIZAR_COTEJO" value="${requestScope[appConstants.transferencias.VER_BOTON_FINALIZAR_COTEJO_KEY]}" />
<c:set var="VER_BOTON_GENERAR_CARTELAS" value="${requestScope[appConstants.transferencias.VER_BOTON_GENERAR_CARTELAS_KEY]}" />
<c:set var="PUEDE_SER_SIGNATURADA" value="${requestScope[appConstants.transferencias.PUEDE_SER_SIGNATURADA_KEY]}" />


<bean:struts id="actionMapping" mapping="/cotejoysignaturizacionAction" />

<html:form action="/cotejoysignaturizacionAction" >
<html:hidden property="method" value="guardarestadocajas"/>
<html:hidden property="codigoseleccionada"/>
<html:hidden property="idCaja" />
<html:hidden property="estadoCotejo" />
<html:hidden property="ordenCaja" styleId="ordenCaja"/>
<html:hidden property="idRelacion"/>

<script>
var sinGuardar = false;

function activateFlagSinGuardar()
{
	sinGuardar = true;
}

function avisoSinGuardar(urlRedirect)
{
	if (sinGuardar)
	{
		if (confirm("<bean:message key="archigest.warning.formularioModificado"/>"))
			window.location = urlRedirect;
	}
	else{
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		window.location = urlRedirect;
	}
}


function finalizarCotejo(corregir)
{
	if(corregir == null || corregir == "undefined") corregir = "N";

	<c:url var="finalizarCotejoURL" value="/action/cotejoysignaturizacionAction">
		<c:param name="method" value="finalizarCotejo" />
		<c:param name="codigoseleccionada" value="${vRelacion.id}"/>
	</c:url>
	var direccion = '<c:out value="${finalizarCotejoURL}" escapeXml="false"/>';

	if(corregir == 'S'){
		direccion += "&correccionenarchivo=S";
	}

	avisoSinGuardar(direccion);
}



function save()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "guardarInfoCajaEntreArchivosCR";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	form.submit();
}

function marcarConErroresCotejo()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "marcarCajasConErroresCotejo";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	form.submit();
}

function marcarUDocElectronicaConErroresCotejo()
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "marcarUDocsElectronicaConErroresCotejo";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	form.submit();
}

function verCaja(idCaja, orden)
{
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "verCaja";
	form.idCaja.value = idCaja;
	form.ordenCaja.value = orden;

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	form.submit();
}

function verUDocElectronicas(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "verUDocsElectronicas";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	form.submit();
}


function checkChange(idCaja, estado)
{
	activateFlagSinGuardar();
	if (estado == 1)
	{
		setVisibilityObservaciones(idCaja, false);
	}
	else if (estado == 2)
	{
		setVisibilityObservaciones(idCaja, false);
	}
	else if (estado == 3)
	{
		setVisibilityObservaciones(idCaja, true);
	}

}

function setVisibilityObservaciones(idCaja, mostrar)
{

	var textarea = document.getElementById("observacioneserror"+idCaja);
	//var iddevolver = document.getElementById("iddevolver"+idCaja);
	var fila = document.getElementById("TDdivObservacioneserror"+idCaja);

	if(mostrar){
		fila.style.display= '';
	}
	else {
		fila.style.display='none';
	}

}


</script>





<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
		<c:when test="${vRelacion.puedeSerSignaturada}">
			<bean:message key="archigest.archivo.transferencias.cotejoSignat"/>
		</c:when>
		<c:otherwise>
			<bean:message key="archigest.archivo.transferencias.cotejar"/>
		</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table>
	<tr>
	<td>
		<c:url var="URL" value="/action/informeCotejo">
			<c:param name="idRelacion" value="${vRelacion.id}" />
   		</c:url>
		<a class="etiquetaAzul12Bold" href="<c:out value="${URL}" escapeXml="false"/>">
			<html:img page="/pages/images/documentos/doc_pdf.gif"
				        border="0"
				        altKey="archigest.archivo.informe"
				        titleKey="archigest.archivo.informe"
				        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.cotejo"/></a>
	</td>
	<td width="10">&nbsp;</td>
	<c:if test="${VER_BOTON_GENERAR_CARTELAS}">
		<td>
			<c:url var="urlImprimir" value="/action/gestionRelaciones">
				<c:param name="method" value="selCartelas"/>
			</c:url>
			<a class="etiquetaAzul12Bold" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
				<html:img page="/pages/images/cartela.gif"
					titleKey="archigest.archivo.transferencias.generarCartelas"
					altKey="archigest.archivo.transferencias.generarCartelas"
					styleClass="imgTextMiddle" />
				 &nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/>
			</a>
		</td>
		<td width="10">&nbsp;</td>
	</c:if>

	<td>
		<a class="etiquetaAzul12Bold"
			href="javascript:finalizarCotejo();" >
				<c:if test="${VER_BOTON_FINALIZAR_COTEJO}">
				 <html:img page="/pages/images/caja_cotejoError.gif"
				 	titleKey="archigest.archivo.transferencias.finalizarCotejo"
				 	altKey="archigest.archivo.transferencias.finalizarCotejo"
				 	styleClass="imgTextMiddle"/>
				 <bean:message key="archigest.archivo.transferencias.finalizarCotejo"/>
				</c:if>
				<c:if test="${VER_BOTON_SIGNATURAR}">
				 <html:img page="/pages/images/finCotejo.gif"
				 	titleKey="archigest.archivo.transferencias.signaturar"
				 	altKey="archigest.archivo.transferencias.signaturar"
				 	styleClass="imgTextMiddle"/>
				 <c:choose>
				 	<c:when test="${PUEDE_SER_SIGNATURADA}">
					 	<bean:message key="archigest.archivo.transferencias.signaturar"/>
				 	</c:when>
				 	<c:otherwise>
					 	<bean:message key="archigest.archivo.transferencias.finalizarCotejo"/>
				 	</c:otherwise>
				 </c:choose>
				</c:if>
				<c:if test="${VER_BOTON_ACEPTAR_COTEJO}">
				 	<html:img page="/pages/images/caja_ok.gif"
				 		titleKey="archigest.archivo.transferencias.aceptarCotejo"
				 		altKey="archigest.archivo.transferencias.aceptarCotejo"
				 		styleClass="imgTextMiddle"/>
					<bean:message key="archigest.archivo.transferencias.aceptarCotejo"/>
				</c:if>
			</a>
	</td>

	<td width="10">&nbsp;</td>
		<c:if test="${VER_BOTON_FINALIZAR_COTEJO}">
		<security:permissions action="${appConstants.transferenciaActions.CORRECCION_ERRORES_EN_ARCHIVO}">
		<td>
			<a class="etiquetaAzul12Bold"
				href="javascript:finalizarCotejo('S');" >
				<html:img page="/pages/images/caja_cotejoError.gif"
				titleKey="archigest.archivo.transferencias.finalizarCotejo.corregir.tooltip"
				altKey="archigest.archivo.transferencias.finalizarCotejo.y.corregir.tooltip"
				styleClass="imgTextMiddle"/>
				 <bean:message key="archigest.archivo.transferencias.finalizarCotejo.y.corregir"/>
			</a>
		</td>
		<td width="10">&nbsp;</td>
	</security:permissions>
	</c:if>
		<td>
			<a class="etiquetaAzul12Bold"
			   href="javascript:save()"><html:img page="/pages/images/save.gif"
	           altKey="archigest.archivo.guardar"
		       titleKey="archigest.archivo.guardar"
		       styleClass="imgTextMiddle"/>
		       &nbsp;<bean:message key="archigest.archivo.guardar"/></a>
		</td>
	<td width="10">&nbsp;</td>
	<td>

		<c:url var="cancelURI" value="/action/cotejoysignaturizacionAction">
			<c:param name="method" value="goBack" />
		</c:url>
		<a class="etiquetaAzul12Bold"
			href="javascript:avisoSinGuardar('<c:out value="${cancelURI}" escapeXml="false"/>');" >
			<html:img page="/pages/images/close.gif" border="0"
			altKey="archigest.archivo.cerrar"
			titleKey="archigest.archivo.cerrar"
			styleClass="imgTextTop" />
			&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		</a>
	</td>
	</tr>
	</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.relaciones.relacionEntrega"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
			</tiles:put>
			<tiles:put name="visibleContent" direct="true">
					<TABLE class="w98m1" cellpadding=0 cellspacing=2>
						<TR>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.codigoTransferencia}"/>
								</span>
							</TD>
							<TD width="25%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<c:out value="${vRelacion.nombreestado}"/>
								</span>
							</TD>
							<TD width="20%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
								<span class="etiquetaNegra11Normal">
									<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
								</span>
							</TD>
							<TD width="35%" class="etiquetaAzul11Bold">
								<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
								<c:set var="gestorEnOrganoRemitente" value="${vRelacion.gestorEnOrganoRemitente}" />
								<span class="etiquetaNegra11Normal">
									<c:out value="${gestorEnOrganoRemitente.nombreCompleto}"/>
								</span>
							</TD>
						</TR>
					</TABLE>

			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>

		</tiles:insert>

		<span class="separador1">&nbsp;</span>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
					<html:img page="/pages/images/caja_closed.gif" altKey="archigest.archivo.transferencias.UndInstal" titleKey="archigest.archivo.transferencias.UndInstal" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.transferencias.UndInstal" />
			</tiles:put>

			<tiles:put name="buttonBar" direct="true">

			</tiles:put>


				<tiles:put name="blockContent" direct="true">
				<%--Expedientes asignados: Cajas  --%>
					<c:set var="cajasRelacion" value="${infoUdocReeacr.uisWithUDocs}" />
					<div style="margin:8px">
						<div class="titulo_gris_bloque">
							<table class="w98" cellpadding="0" cellspacing="0">
								<tr>
									<td colspan="4">&nbsp;</td>
									<td width="14px" align="center"><img src="<c:url value="/pages/images/cajaPendiente.gif"/>" class="imgTextMiddle" /></td>
									<td width="14px" align="center"><img src="<c:url value="/pages/images/cajaRevisada.gif"/>" class="imgTextMiddle" /></td>
									<td width="14px" align="center"><img src="<c:url value="/pages/images/cajaError.gif"/>" class="imgTextMiddle" /></td>
								</tr>

							<c:forEach items="${cajasRelacion}" var="unidadInstalacion" varStatus="nCajas" >
								<tr>

								<c:set var="cajaPanelName" value="CajaReencajado${nCajas.count}" />
								<jsp:useBean id="cajaPanelName" type="java.lang.String" />


									<c:set var="panelControlImg" value="img${cajaPanelName}" />
									<jsp:useBean id="panelControlImg" type="java.lang.String" />
									<c:set var="panelVisibilityCommand" value="switchDivVisibility('${cajaPanelName}')" />
									<jsp:useBean id="panelVisibilityCommand" type="java.lang.String" />

									<td>
										<c:choose>
											<c:when test="${unidadInstalacion.key.revisada}">
												<c:set var="imageEstado">/pages/images/cajaRevisada.gif</c:set>
											</c:when>
											<c:when test="${unidadInstalacion.key.conErrores}">
												<c:set var="imageEstado">/pages/images/cajaError.gif</c:set>
											</c:when>
											<c:otherwise>
												<c:set var="imageEstado">/pages/images/cajaPendiente.gif</c:set>
											</c:otherwise>
										</c:choose>
										<img src="<c:url value="${imageEstado}"/>" class="imgTextMiddle" />
									</td>

									<td class="etiquetaAzul12Normal" width="150px">
										<html:img page="/pages/images/down.gif" styleId="<%=panelControlImg%>" onclick="<%=panelVisibilityCommand%>" styleClass="imgTextMiddle"/>
										&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.caja" />&nbsp;<c:out value="${nCajas.count}" />
										<c:choose>
										<c:when test="${unidadInstalacion.key.devolucion}">
											&nbsp;&nbsp<html:img page="/pages/images/cajaDevuelta.gif" border="0" altKey="archigest.archivo.transferencias.devolver" titleKey="archigest.archivo.transferencias.devolver" styleClass="imgTextMiddle"/>
										</c:when>
										</c:choose>
									</td>
									<td class="etiquetaAzul12Normal">
										<bean-el:message key="archigest.archivo.caja.contiene.unidades.documentales" arg0="${unidadInstalacion.key.numUdocsUI}"/>
									</td>
									<td class="etiquetaAzul11Normal">
										<c:if test="${!empty unidadInstalacion.key.signaturaUI}">
											<b><bean:message key="archigest.archivo.transferencias.signatura" />:</b>
											&nbsp;<c:out value="${unidadInstalacion.key.signaturaUI}" />
										</c:if>
										&nbsp;
									</td>

									<td>
										<input id='pendiente<c:out value="${unidadInstalacion_rowNum}"/>'
											type="radio" name='<c:out value="estadocotejounidaddocumental(${unidadInstalacion.key.id})"/>'
											value='<c:out value="${estadoPendiente}"/>'
											<c:if test="${unidadInstalacion.key.pendiente}">
												<c:out value="checked"/>
											</c:if>
											onClick="javascript:checkChange('<c:out value="${unidadInstalacion.key.id}"/>',1);"/>
									</td>
									<td>
											<input id='revisada<c:out value="${unidadInstalacion_rowNum}"/>'
											type="radio" name='<c:out value="estadocotejounidaddocumental(${unidadInstalacion.key.id})"/>'
											value='<c:out value="${estadoRevisada}"/>'
											<c:if test="${unidadInstalacion.key.revisada}">
												<c:out value="checked"/>
											</c:if>
											onClick="javascript:checkChange('<c:out value="${unidadInstalacion.key.id}"/>',2);"/>
									</td>
									<td>
										<input id='error<c:out value="${unidadInstalacion_rowNum}"/>'
											type="radio" name='<c:out value="estadocotejounidaddocumental(${unidadInstalacion.key.id})"/>'
											value='<c:out value="${estadoErrores}"/>'
											<c:if test="${unidadInstalacion.key.conErrores}">
												<c:out value="checked"/>
											</c:if>
											onClick="javascript:checkChange('<c:out value="${unidadInstalacion.key.id}"/>',3);" />
									</td>
									</tr>
									<tr id="TDdivObservacioneserror<c:out value="${unidadInstalacion.key.id}"/>">
										<td>&nbsp;</td>
										<td  class="etiquetaAzul11Bold" valign="top">
											<span class="separador3">&nbsp;</span>
											<div>
											<bean:message key="archigest.archivo.transferencias.notasCotejo"/>:&nbsp;
											</div>
										</td>
										<td colspan="6" >
											<span class="separador3">&nbsp;</span>
											<div id='divObservacioneserror<c:out value="${unidadInstalacion.key.id}"/>'>
											<textarea id='observacioneserror<c:out value="${unidadInstalacion.key.id}"/>'
													name='<c:out value="observacioneserror(${unidadInstalacion.key.id})"/>'
													rows="2" class="textarea99" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" ><c:out value="${unidadInstalacion.key.notasCotejo}" /></textarea>
											</div>
											<c:choose>
												<c:when test="${unidadInstalacion.key.conErrores}">
													<SCRIPT>setVisibilityObservaciones('<c:out value="${unidadInstalacion.key.id}"/>',true)</SCRIPT>
												</c:when>
												<c:otherwise>
													<SCRIPT>setVisibilityObservaciones('<c:out value="${unidadInstalacion.key.id}"/>',false)</SCRIPT>
												</c:otherwise>
											</c:choose>
											<span class="separador3">&nbsp;</span>
										</td>
									</tr>
									<tr id="div<%=cajaPanelName%>"  style="display:none;" isOpen="false">

									<td colspan="8">
									<div  id="div1<%=cajaPanelName%>" >
									<c:set var="udocs" value="${unidadInstalacion.value}" />

									<display:table name="pageScope.udocs" id="udoc" style="width:99%;margin-left:auto;margin-right:auto">

										<display:setProperty name="basic.show.header" value="false" />

										<display:setProperty name="basic.msg.empty_list">
											<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
												&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
											</div>
										</display:setProperty>

										<display:column title="" style="width:30px;text-align:right;">
											<c:out value="${udoc_rowNum}" />
										</display:column>
										<c:if test="${!empty unidadInstalacion.key.estadoCotejo}">
											<display:column titleKey="archigest.archivo.transferencias.cotejoUDoc" style="width:2%">
												<c:choose>
													<c:when test="${unidadInstalacion.key.pendiente}">
														<html:img page="/pages/images/cajaPendiente.gif" altKey="archigest.archivo.transferencias.pendienteCotejo" titleKey="archigest.archivo.transferencias.pendienteCotejo"/>
													</c:when>
													<c:when test="${unidadInstalacion.key.revisada}">
														<html:img page="/pages/images/cajaRevisada.gif" altKey="archigest.archivo.transferencias.cotejoCorrecto" titleKey="archigest.archivo.transferencias.cotejoCorrecto"/>
													</c:when>
													<c:when test="${unidadInstalacion.key.conErrores}">
														<html:img page="/pages/images/cajaError.gif" altKey="archigest.archivo.transferencias.cotejoConErrores" titleKey="archigest.archivo.transferencias.cotejoConErrores"/>
													</c:when>
												</c:choose>
											</display:column>

										</c:if>
										<display:column titleKey="archigest.archivo.transferencias.nExp" style="width:13%">
											<c:out value="${udoc.numExp}" /><c:out value="${udoc.parteExp}"/>
										</display:column>
										<display:column titleKey="archigest.archivo.transferencias.asunto">
											<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
												<c:param name="method" value="verEnFondos" />
												<c:param name="unidadDocumental" value="${udoc.idUnidadDoc}" />
											</c:url>
											<a href="javascript:avisoSinGuardar('<c:out value="${infoUdocURL}" escapeXml="false"/>');" class="tdlink">
												<c:out value="${udoc.asunto}" />
											</a>
										</display:column>
										<display:column style="width:20%">
											<c:out value="${udoc.signaturaUdoc}" />
										</display:column>
									</display:table>
								</div> <%--divcajaPanelNameN--%>



								<span class="separador3">&nbsp;</span>

										</td>
									</tr>
							</c:forEach>
							</table>
						</div>
					</div>
							<span class="separador5">&nbsp;</span>
				</tiles:put>
		</tiles:insert>


	</tiles:put>
</tiles:insert>
</html:form>