<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="valoracion" value="${sessionScope[appConstants.valoracion.VALORACION_KEY]}" />
<c:set var="seriesRelacionadasConFondo" value="${valoracion.listaSeriesRelacionadasConFondo}" />
<c:set var="seriesRelacionadasSinFondo" value="${valoracion.listaSeriesRelacionadasSinFondo}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">Valoración</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<c:if test="${valoracion.puedeImprimirse}">
				<security:permissions action="${appConstants.fondosActions.IMPRIMIR_VALORACION_ACTION}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="URL" value="/action/informeValoracion">
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" 
						href="<c:out value="${URL}" escapeXml="false"/>"
					><html:img page="/pages/images/documentos/doc_pdf.gif" 
						border="0" 
						altKey="archigest.archivo.informe"
						titleKey="archigest.archivo.informe"
						styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
				</td>
				</security:permissions>
				</c:if>
				
				<c:if test="${valoracion.puedeSerDictaminada}">
				<security:permissions action="${appConstants.fondosActions.DICTAMINAR_ACTION}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="dictaminarValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="recogerDatosDictamen" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${dictaminarValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.valoracion.dictaminar" titleKey="archigest.archivo.valoracion.dictaminar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.dictaminar"/></a>
				</td>
				</security:permissions>
				</c:if>

				<c:if test="${valoracion.dictamenPuedeSerCerrado}">
				<security:permissions action="${appConstants.fondosActions.DICTAMINAR_ACTION}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="dictaminarValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="cerrarDictamenFavorable" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<script>
						function cerrarDictamen()
						{
							if (confirm("<bean:message key="archigest.archivo.valoracion.cerrarDictamen.confirm"/>"))
								window.location.href = "<c:out value="${dictaminarValoracionURL}" escapeXml="false" />";
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:cerrarDictamen()" >
					<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.valoracion.cerrarDictamen" titleKey="archigest.archivo.valoracion.cerrarDictamen" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.cerrarDictamen"/></a>
				</td>
				</security:permissions>
				</c:if>
				
				<c:if test="${valoracion.puedeSerEvaluada}">
				<security:permissions action="${appConstants.fondosActions.AUTORIZAR_EVALUACION_ACTION}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="aceptarEvaluacionValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="recogerDatosAutorizacionEvaluacion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${aceptarEvaluacionValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.valoracion.aceptarEvaluacion" titleKey="archigest.archivo.valoracion.aceptarEvaluacion" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.aceptarEvaluacion"/></a>
				</td>
				</security:permissions>
				<security:permissions action="${appConstants.fondosActions.RECHAZAR_EVALUACION_ACTION}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="rechazarEvaluacionValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="recogerDatosRechazoEvaluacion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${rechazarEvaluacionValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/rechazar.gif" altKey="archigest.archivo.valoracion.rechazarEvaluacion" titleKey="archigest.archivo.valoracion.rechazarEvaluacion" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.rechazarEvaluacion"/></a>
				</td>
				</security:permissions>
				</c:if>
				<c:if test="${valoracion.puedeSerValidada}">
				<security:permissions action="${appConstants.fondosActions.AUTORIZAR_VALIDACION_ACTION}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="aceptarValidacionValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="autorizarValidacion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${aceptarValidacionValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/aceptar.gif" altKey="archigest.archivo.valoracion.aceptarValidacion" titleKey="archigest.archivo.valoracion.aceptarValidacion" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.aceptarValidacion"/></a>
				</td>
				</security:permissions>
				<security:permissions action="${appConstants.fondosActions.RECHAZAR_VALIDACION_ACTION}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="rechazarValidacionValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="recogerDatosRechazoValidacion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${rechazarValidacionValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/rechazar.gif" altKey="archigest.archivo.valoracion.rechazarValidacion" titleKey="archigest.archivo.valoracion.rechazarValidacion" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.valoracion.rechazarValidacion"/></a>
				</td>
				</security:permissions>
				</c:if>
				<c:if test="${valoracion.permitidaSolicitudValidacion}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="solicitarValidacionValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="solicitarValidacion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${solicitarValidacionValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/enviar.gif" altKey="archigest.archivo.valoracion.solicitarValidacion" titleKey="archigest.archivo.valoracion.solicitarValidacion" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.valoracion.solicitarValidacion"/></a>
				</td>
				</c:if>
				<c:if test="${valoracion.puedeSerEliminada}">
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="eliminarValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="eliminarValoracion" />
						<c:param name="id" value="${valoracion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${eliminarValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
				</td>
				</c:if>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<c:url var="cerrarURL" value="/action/gestionValoracion">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cerrarURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.valoracion.serieValorada"/></tiles:put>

			<tiles:put name="blockContent" direct="true">
				<c:set var="serieValorada" value="${valoracion.serie}" />
				<table class="formulario">
					<tr>
						<td  width="220px" class="tdTitulo">
							<bean:message key="archigest.archivo.cf.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:url var="vistaSerieURL" value="/action/gestionSeries">
								<c:param name="method" value="verEnFondos" />
								<c:param name="id" value="${serieValorada.id}" />
							</c:url>
							<a class="tdlink" href="<c:out value="${vistaSerieURL}" escapeXml="false"/>" target="_self"><c:out value="${serieValorada.codigo}" /></a>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.cf.denominacion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${serieValorada.denominacion}" />
						</td>
					</tr>
					<TR><TD>&nbsp;</TD></TR>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.valoracion.titulo"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${valoracion.titulo}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.valoracion.estadoValoracion"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<fmt:message key="archigest.archivo.valoracion.estado${valoracion.estado}" />
						</td>
					</tr>
					
					<c:if test="${!empty valoracion.fechaValidacion}">
						<TR><TD>&nbsp;</TD></TR>
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
						<TR><TD>&nbsp;</TD></TR>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.fechaEvaluacion"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<fmt:formatDate value="${valoracion.fechaEvaluacion}" pattern="${FORMATO_FECHA}"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${!empty valoracion.fechaDictamen}">
						<TR><TD>&nbsp;</TD></TR>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.fechaDictamen"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<fmt:formatDate value="${valoracion.fechaDictamen}" pattern="${FORMATO_FECHA}"/>
							</td>
						</tr>
						<c:if test="${!valoracion.dictamenRechazado}">
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.fechaResolucionDictamen"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<fmt:formatDate value="${valoracion.fechaResolucionDictamen}" pattern="${FORMATO_FECHA}"/>
							</td>
						</tr>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.boletinDictamen"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<c:out value="${valoracion.boletinDictamen}" />
							</td>
						</tr>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.fechaBoletinDictamen"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<fmt:formatDate value="${valoracion.fechaBoletinDictamen}" pattern="${FORMATO_FECHA}"/>
							</td>
						</tr>
						</c:if>
					</c:if>

					<c:if test="${valoracion.validacionRechazada || valoracion.evaluacionRechazada || valoracion.dictamenRechazado}">
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.valoracion.motivoRechazo"/>:&nbsp;
							</td>
							<td class="tdDatosBold">
								<c:out value="${valoracion.motivoRechazo}">-</c:out>
							</td>
						</tr>
					</c:if>

				</table>			
			</tiles:put>
		</tiles:insert>

		<html:form action="/gestionValoracion">
		<input type="hidden" name="method" id="method">
		<html:hidden property="id"/>

		<script>
			var tabPanel = new TabPanel("tabSeleccionDatos");
			tabPanel.addTab(new Tab("seriesPrecedentes", "seriesPrecedentes"));
			//tabPanel.addTab(new Tab("seriesDescendientes", "seriesDescendientes"));
			tabPanel.addTab(new Tab("seriesRelacionadas", "seriesRelacionadas"));
			tabPanel.addTab(new Tab("datosValoracion", "datosValoracion"));

			function selectTab(tab) {
				tabPanel.showTab(tab);
			}
		</script>
		<div class="separador5"></div>
		<div class="cabecero_tabs">
			<table cellspacing="0" cellpadding=0>
				<tr>
				<td class="tabActual" id="pseriesPrecedentes">
						<a href="javascript:selectTab('seriesPrecedentes')" id="tseriesPrecedentes" class="textoPestana">
							<bean:message key="archigest.archivo.valoracion.seriesPrecedentes"/>
						</a>
				</td>
				<td width="5px">&nbsp;</td>
				<td class="tabSel" id="pseriesRelacionadas">
					<a href="javascript:selectTab('seriesRelacionadas')" id="tseriesRelacionadas" class="textoPestanaSel">
						<bean:message key="archigest.archivo.valoracion.seriesRelacionadas"/>
					</a>
				</td>
				<td width="5px">&nbsp;</td>
				<td class="tabSel" id="pdatosValoracion">
					<a href="javascript:selectTab('datosValoracion')" id="tdatosValoracion" class="textoPestanaSel">
						<bean:message key="archigest.archivo.valoracion.datosValoracion"/>
					</a>
				</td>
				</tr>
			</table>
		</div>


		<tiles:insert template="/pages/tiles/PATabBlockLayout.jsp">
			<tiles:put name="visible" direct="true">true</tiles:put>
			<tiles:put name="tabName" direct="true">seriesPrecedentes</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<c:if test="${valoracion.puedeSerEditada}">
				<script>
					function eliminarSeriesPrecedentes() {
						document.getElementById("method").value = "eliminarSerie";
						document.forms[0].submit();
					}
					function anadirSeriesPrecedentes() {
						document.getElementById("method").value = "seleccionarSeriesPrecedentes";
						document.forms[0].submit();
					}
				</script>
				<table cellpadding="0" cellpadding="0">
					<tr>
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:anadirSeriesPrecedentes()">
							<html:img page="/pages/images/addDoc.gif" altKey="archigest.archivo.valoracion.anadirSerie" titleKey="archigest.archivo.valoracion.anadirSerie" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.valoracion.anadirSerie"/>
						</a>
					</td>
					<TD width="10">&nbsp;</TD>
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:eliminarSeriesPrecedentes()" >
							<html:img page="/pages/images/delDoc.gif" altKey="archigest.archivo.valoracion.eliminarSerie" titleKey="archigest.archivo.valoracion.eliminarSerie" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.valoracion.eliminarSerie"/>&nbsp;
						</a>
					</td>
					</tr>
				</table>
				</c:if>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<c:set var="seriesPrecedentes" value="${valoracion.listaSeriesPrecedentes}" />
				<div style="padding-top:4px;padding-bottom:4px">
				<display:table name="pageScope.seriesPrecedentes"
					id="seriePrecedente" 
					style="width:99%;margin-left:auto;margin-right:auto">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.valoracion.msgNoSeriesPrecedentes"/>
					</display:setProperty>
					<c:if test="${valoracion.puedeSerEditada}">
					<display:column style="width:15px">
						<input type="checkbox" name="serieSeleccionadaPrecedenteEliminar" value="<c:out value="${seriePrecedente.id}" />" >
					</display:column>
					</c:if>
					<display:column titleKey="archigest.archivo.codigo">
						<c:url var="vistaSerieURL" value="/action/gestionSeries">
							<%-- Se sustituye verEnFondos por verDesdeFondos --%>
							<c:param name="method" value="verDesdeFondos" />
							<c:param name="id" value="${seriePrecedente.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${vistaSerieURL}" escapeXml="false"/>"><c:out value="${seriePrecedente.codigo}" /></a>
					</display:column>
					<display:column titleKey="archigest.archivo.titulo">
						<c:out value="${seriePrecedente.titulo}" />
					</display:column>
				</display:table>
				</div>
			</tiles:put>
		</tiles:insert>
		<tiles:insert template="/pages/tiles/PATabBlockLayout.jsp">
			<tiles:put name="tabName" direct="true">seriesRelacionadas</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<c:if test="${valoracion.puedeSerEditada}">
				<script>
					function eliminarSeriesRelacionadas() {
						document.getElementById("method").value = "eliminarSerie";
						document.forms[0].submit();
					}
					function anadirSeriesRelacionadas() {
						document.getElementById("method").value = "seleccionarSeriesRelacionadas";
						document.forms[0].submit();
					}
				</script>
				<table cellpadding="0" cellpadding="0">
					<tr>
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:anadirSeriesRelacionadas()">
							<html:img page="/pages/images/addDoc.gif" altKey="archigest.archivo.valoracion.anadirSerie" titleKey="archigest.archivo.valoracion.anadirSerie" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.valoracion.anadirSerie"/>
						</a>
					</td>
					<td width="10px"></td>
					<td nowrap>
						<a class="etiquetaAzul12Bold" href="javascript:eliminarSeriesRelacionadas()" >
							<html:img page="/pages/images/delDoc.gif" altKey="archigest.archivo.valoracion.eliminarSerie" titleKey="archigest.archivo.valoracion.eliminarSerie" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.valoracion.eliminarSerie"/>&nbsp;
						</a>
					</td>
					</tr>
				</table>
				</c:if>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div style="padding-top:4px;padding-bottom:4px;">
					
					<c:if test="${!empty seriesRelacionadasSinFondo}">
					<div class="titulo_left_bloque">
						&nbsp;&nbsp;
						<bean:message key="archigest.archivo.valoracion.seriesRelacionadas.con_fondo"/>:
					</div>

					<div class="separador1"></div>
					</c:if>

					<display:table name="pageScope.seriesRelacionadasConFondo"
						id="serieRelacionada" 
						style="width:99%;margin-left:auto;margin-right:auto">
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.valoracion.msgNoSeriesRelacionadas"/>
						</display:setProperty>
						<c:if test="${valoracion.puedeSerEditada}">
						<display:column style="width:15px">
							<input type="checkbox" name="serieSeleccionadaRelacionadaEliminar" value="<c:out value="${serieRelacionada.id}" />" >
						</display:column>
						</c:if>
						<display:column titleKey="archigest.archivo.codigo">
							<c:url var="vistaSerieURL" value="/action/gestionSeries">
								<%-- Se sustituye verEnFondos por verDesdeFondos --%>
								<c:param name="method" value="verDesdeFondos" />
								<c:param name="id" value="${serieRelacionada.id}" />
							</c:url>
							<a class="tdlink" href="<c:out value="${vistaSerieURL}" escapeXml="false"/>"><c:out value="${serieRelacionada.codigo}" /></a>
						</display:column>
						<display:column titleKey="archigest.archivo.titulo">
							<c:out value="${serieRelacionada.titulo}" />
						</display:column>
					</display:table>
				</div>
				
				<c:if test="${!empty seriesRelacionadasSinFondo}">
					<div class="separador8"></div>
					
					<div style="padding-top:4px;padding-bottom:4px;">
						<div class="titulo_left_bloque">
							&nbsp;&nbsp;
							<bean:message key="archigest.archivo.valoracion.seriesRelacionadas.sin_fondo"/>:
						</div>
						
						<div class="separador1"></div>
					
						<display:table name="pageScope.seriesRelacionadasSinFondo"
							id="serieRelacionadaSinFondo" 
							style="width:99%;margin-left:auto;margin-right:auto">
							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.valoracion.msgNoSeriesRelacionadas"/>
							</display:setProperty>
							<c:if test="${valoracion.puedeSerEditada}">
							<display:column style="width:15px">
								<input type="checkbox" name="serieSinFondoSeleccionadaRelacionadaEliminar" value="<c:out value="${serieRelacionadaSinFondo_rowNum}" />" >
							</display:column>
							</c:if>
							<display:column titleKey="archigest.archivo.titulo">
								<c:out value="${serieRelacionadaSinFondo.titulo}" />
							</display:column>
						</display:table>
					</div>
				</c:if>
			</tiles:put>
		</tiles:insert>
		<tiles:insert template="/pages/tiles/PATabBlockLayout.jsp">
			<tiles:put name="buttonBar" direct="true">
				<c:if test="${valoracion.puedeSerEditada || valoracion.dictamenPuedeSerCerrado}">
				<table><tr>
				<td nowrap>
					<c:url var="edicionValoracionURL" value="/action/gestionValoracion">
						<c:param name="method" value="edicionValoracion" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${edicionValoracionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.editar"/></a>
				</td>
				</tr></table>
				</c:if>
			</tiles:put>
			<tiles:put name="tabName" direct="true">datosValoracion</tiles:put>
			<tiles:put name="blockContent" value="valoracion.datosValoracion" type="definition" />
		</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>

<script>
	tabPanel.showTab(getCookie("tabSeleccionDatos"));
</script>