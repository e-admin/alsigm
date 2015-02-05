<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="identificacionSerie" value="${sessionScope[appConstants.fondos.IDENTIFICACION_SERIE_KEY]}" />

<c:set var="productoresHistoricos" value="${identificacionSerie.productoresHistoricos}" />
<c:set var="productoresADarDeAlta" value="${sessionScope[appConstants.fondos.PRODUCTORES_A_DAR_DE_ALTA_KEY]}" />
<c:set var="listaProductoresVigentesOriginalesAElegir" value="${sessionScope[appConstants.fondos.PRODUCTORES_ORIGINALES_VIGENTES_A_ELEGIR_KEY]}" />

<c:set var="productoresVigentes" value="${identificacionSerie.productoresVigentes}" />



<script language="JavaScript1.2">
	function desplegarOrgano(posicion) {
		switchVisibility("organoNoDesplegado"+posicion);
		switchVisibility("organoDesplegado"+posicion);
	}
</script>

<bean:struts id="mappingIdentificacionSerie" mapping="/gestionIdentificacionAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.identificacionSerie"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		<TD>



			<script>
				function guardar() {
					var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
					form.method.value = "guardar";

					if (window.top.showWorkingDiv) {
						var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
						var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
						window.top.showWorkingDiv(title, message);
					}


					form.submit();
				}
			</script>

			<a class="etiquetaAzul12Bold" href="javascript:guardar()" >
				<html:img page="/pages/images/save.gif" altKey="archigest.archivo.guardar" titleKey="archigest.archivo.guardar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.guardar"/>
			</a>

		</TD>
		<TD width="10"></TD>
		<TD>
			<c:url var="cerrarURL" value="/action/gestionIdentificacionAction">
				<c:param name="method" value="goBack" />
			</c:url>
			<c:set var="dataForm" value="${sessionScope[mappingIdentificacionSerie.name]}" />

			<SCRIPT>

				var dataHasChanged = <c:out value="${dataForm.changed}" />
				function warning() {
					if (dataHasChanged) {
						if (confirm("<bean:message key='archigest.warning.formularioModificado'/>"))
							window.location = '<c:out value="${cerrarURL}" escapeXml="false"/>';
					} else
						window.location = '<c:out value="${cerrarURL}" escapeXml="false"/>';
				}
			</SCRIPT>


			<a class="etiquetaAzul12Bold" href="javascript:warning()" >
				<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/>
			</a>
		</TD>

		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.serie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
		<table class="formulario">
			<tr>
				<td class="tdTitulo" style="vertical-align:top;" width="230px">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> <c:out value="${serie.denominacion}" />
				</td>
			</tr>
		</table>
		</tiles:put>
		</tiles:insert>

		<html:form action="/gestionIdentificacionAction">
		<input type="hidden" name="method">


		<c:set var="vProcedimiento" value="${identificacionSerie.procedimiento}" />

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.procedimientosDeLaSerie"/></tiles:put>
		<c:choose>
		<c:when test="${identificacionSerie.permitidoVincularProcedimiento}">
			<c:if test="${identificacionSerie.permitidoEditarProcedimientoAsociado}">
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
				<TD>
					<c:url var="listadoProcedimientoURL" value="/action/gestionIdentificacionAction">
						<c:param name="method" value="verBuscadorProcedimiento" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${listadoProcedimientoURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.cf.asociarProc" titleKey="archigest.archivo.cf.asociarProc" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.asociarProc"/>
					</a>
				</TD>
				<c:if test="${!empty vProcedimiento}">
				<TD width="10"></TD>
				<TD>
					<c:url var="devincularDeProcedimientoURL" value="/action/gestionIdentificacionAction">
						<c:param name="method" value="eliminarprocedimiento" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${devincularDeProcedimientoURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.cf.desvincularProc" titleKey="archigest.archivo.cf.desvincularProc" styleClass="imgTextMiddle" />
						<bean:message key="archigest.archivo.cf.desvincularProc"/>
					</a>
				</TD>
				</c:if>
				</TR>
				</TABLE>
			</tiles:put>
			</c:if>

			<tiles:put name="blockContent" direct="true">
				<div class="separador5">&nbsp;</div>
				<c:choose>
					<c:when test="${!empty vProcedimiento}">
						<table class="w98"><tr>
							<td class="etiquetaNegra11Normal">
								<c:out value="${vProcedimiento.codigo}" />&nbsp;
								<c:out value="${vProcedimiento.nombre}" />
							</td>
						</tr></table>
					</c:when>
					<c:otherwise>
						&nbsp;<bean:message key="archigest.archivo.cf.noProcedimientos"/>
					</c:otherwise>
				</c:choose>
				<div class="separador5">&nbsp;</div>
			</tiles:put>
		</c:when>
		<c:otherwise>
			<tiles:put name="blockContent" direct="true">
				<div class="aviso">
					<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
					<bean:message key="archigest.archivo.cf.msgSerieNoIdentificadaProc"/>
				</div>
			</tiles:put>
		</c:otherwise>
		</c:choose>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>


		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.identificacion"/></tiles:put>

		<tiles:put name="blockContent" direct="true">
			<c:url var="urlImages" value="/pages/images/controles/"/>
			<TABLE class="formulario" cellpadding="4"> <%-- para aspecto de formulario con lineas bottom de celda --%>
				<TR>
					<TD class="tdTitulo" width="260px">
						<bean:message key="archigest.archivo.identificacion.denominacion"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:text styleClass="input99" property="denominacion" onchange="dataHasChanged = true"/>
					</TD>
				</TR>
				<TR><td colspan="2"><div class="separador1">&nbsp;</div></td></TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.identificacion.definicion"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:textarea rows="20" property="definicion" onchange="dataHasChanged = true" styleId="definicion"/>
						<tiles:insert page="/pages/tiles/controls/textoAmpliable.jsp">
							<tiles:put name="property" direct="true">definicion</tiles:put>
							<tiles:put name="urlImages" direct="true"><c:out value="${urlImages}"/></tiles:put>
						</tiles:insert>
					</TD>
				</TR>
				<TR><td colspan="2"><div class="separador1">&nbsp;</div></td></TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.identificacion.tramites"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:textarea rows="20" property="tramites" styleId="tramites" onchange="dataHasChanged = true"/>
						<tiles:insert page="/pages/tiles/controls/textoAmpliable.jsp">
							<tiles:put name="property" direct="true">tramites</tiles:put>
							<tiles:put name="urlImages" direct="true"><c:out value="${urlImages}"/></tiles:put>
						</tiles:insert>

					</TD>
				</TR>
				<TR><td colspan="2"><div class="separador1">&nbsp;</div></td></TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.identificacion.normativa"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:textarea rows="20" property="normativa" styleId="normativa" onchange="dataHasChanged = true"/>
						<tiles:insert page="/pages/tiles/controls/textoAmpliable.jsp">
							<tiles:put name="property" direct="true">normativa</tiles:put>
							<tiles:put name="urlImages" direct="true"><c:out value="${urlImages}"/></tiles:put>
						</tiles:insert>
					</TD>
				</TR>
				<TR><td colspan="2"><div class="separador1">&nbsp;</div></td></TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.identificacion.documentosbasicos"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:textarea rows="7"  property="documentosbasicos" onchange="dataHasChanged = true"/>
					</TD>
				</TR>
				<c:if test="${appConstants.configConstants.mostrarInformacionIdentificacionExtendia}">
				<TR><td colspan="2"><div class="separador5">&nbsp;</div></td></TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.series.tipo.documentacion"/>
					</TD>
					<TD class="tdDatos">
						<c:set var="listaTiposDocumentacion" value="${sessionScope[appConstants.fondos.LISTA_TIPOS_DOCUMENTACION_KEY]}"/>
						<c:if test="${not empty listaTiposDocumentacion}">
						<html:select property='tipoDocumentacion' size="1">
							<html:option value=""></html:option>
							<html:options collection="listaTiposDocumentacion" labelProperty="valor" property="valor"/>
						</html:select>
						</c:if>
					</TD>
				</TR>
				<TR><td colspan="2"><div class="separador5">&nbsp;</div></td></TR>
				<TR>
					<TD class="tdTitulo" nowrap="nowrap">
						<bean:message key="archigest.archivo.series.prevision.anual.volumen"/>
					</TD>
					<TD class="tdDatos">
						<html:text property="volumenPrevisionAnual" styleId="volumenPrevisionAnual"/>
					</TD>
				</TR>
				<TR><td colspan="2"><div class="separador5">&nbsp;</div></td></TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.series.prevision.anual.soporte"/>
					</TD>
					<TD class="tdDatos">
						<html:text property="soportePrevisionAnual" styleId="soportePrevisionAnual"/>
					</TD>
				</TR>
				</c:if>
			</TABLE>
		</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<%-- ORGANOS PRODUCTORES --%>




		<c:if test="${empty identificacionSerie.procedimiento}" >


			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresVigentes"/></tiles:put>

			<tiles:put name="buttonBar" direct="true">

				<c:if test="${identificacionSerie.permitidaSeleccionProductores}">
					<script>
						function verBuscadorProductores() {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							form.method.value = "verBuscadorProductores";
							form.submit();
						}

						function verBuscadorProductoresHistoricos() {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							form.method.value = "verBuscadorProductoresHistoricos";
							form.submit();
						}


						function eliminarProductores() {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							if (form.guidsProductor && elementSelected(form.guidsProductor)) {
								form.method.value = "eliminarProductores";
								form.submit();
							} else
								alert("<bean:message key='archigest.archivo.cacceso.msgNoProductoresEliminar'/>");
						}
						function eliminarProductorEnEstudio() {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							if (form.guidProductor && elementSelected(form.guidProductor)) {
								if(window.confirm('<fmt:message key="archigest.archivo.series.msg.eliminar.productor"/>')){
									form.method.value = "eliminarProductorEnEstudio";
									form.submit();
								}
							} else
								alert("<bean:message key='archigest.archivo.cacceso.msgNoProductoresEliminar'/>");
						}
						function sustituirProductor() {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							form.method.value = "sustituirProductor";
							form.submit();
						}

						function reemplazarProductor(idProductor) {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							document.getElementById("guidProductor"+idProductor).checked = true;
							form.method.value = "sustituirProductor";
							form.submit();
						}

						//Pasar un productor existente a histórico
						function pasarAHistorico(){
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							if (form.guidProductor && elementSelected(form.guidProductor)) {
//								if(window.confirm('<fmt:message key="archigest.archivo.series.msg.a.historico.productor"/>')){
									form.method.value = "pasarAHistorico";
									form.submit();
//								}
							} else
								alert("<fmt:message key='archigest.archivo.series.msg.a.historico.productor.no.seleccionado'/>");

						}

					</script>
					<TABLE cellpadding=0 cellspacing=0>
						<tr>
						<TD>
							<a class="etiquetaAzul12Bold" href="javascript:verBuscadorProductores()" >
								<html:img page="/pages/images/table_add.gif" altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.anadir"/>&nbsp;
							</a>
						</TD>


						<c:if test="${serie.estadoserie == appConstants.estadosSerie.EN_ESTUDIO && not empty productoresVigentes}">
						<TD width="10px"></TD>
						<TD>
							<a class="etiquetaAzul12Bold" href="javascript:eliminarProductorEnEstudio()" >
								<html:img page="/pages/images/table_delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.eliminar"/>&nbsp;
							</a>
						</TD>
						</c:if>

						<c:if test="${serie.estadoserie != appConstants.estadosSerie.EN_ESTUDIO && not empty productoresVigentes}">
						<TD width="10px"></TD>
						<TD>
							<a class="etiquetaAzul12Bold" href="javascript:eliminarProductores()" >
								<html:img page="/pages/images/table_delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.eliminar"/>&nbsp;
							</a>
						</TD>
						</c:if>
						<c:if test="${serie.enEstudio && not empty productoresVigentes}">
						<TD width="10px"></TD>
						<TD id="tdPasarAHistorico" class="hidden">
									<a class="etiquetaAzul12Bold" href="javascript:pasarAHistorico()" >
									<html:img page="/pages/images/treeMover.gif" altKey="archigest.archivo.series.productor.pasar.historico" titleKey="archigest.archivo.series.productor.pasar.historico" styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.series.productor.pasar.historico"/>&nbsp;
								</a>
							</TD>
						<TD width="10px"></TD>
						<TD id="tdReemplazaA" class="hidden">
							<a class="etiquetaAzul12Bold" href="javascript:sustituirProductor()" >
								<html:img page="/pages/images/table_refresh.gif" altKey="archigest.archivo.reemplazaA" titleKey=" archigest.archivo.reemplazaA" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.reemplazaA"/>&nbsp;
							</a>
						</TD>

						</c:if>
					  </TR>
					</TABLE>
				</c:if>
			</tiles:put>


			<tiles:put name="blockContent" direct="true">
				<div class="separador5">&nbsp;</div>



				<display:table name="pageScope.productoresVigentes" id="productor" style="width:99%">
						<display:setProperty name="basic.msg.empty_list">
							&nbsp;<bean:message key="archigest.archivo.cf.noProductoresVigentes"/>
						</display:setProperty>
					<c:if test="${identificacionSerie.permitidaSeleccionProductores}">
						<display:column style="width:10px" >
							<c:choose>
								<c:when test="${serie.enEstudio}">
									<input type="radio" name="guidProductor" id="guidProductor<c:out value="${productor.guid}"/>" value="<c:out value="${productor.guid}"/>"/>


									<c:if test='${productor.permitidoPasarAHistorico}'>
										<script>
											if(document.getElementById("tdPasarAHistorico")!=null){
												document.getElementById("tdPasarAHistorico").className="visible_inline";
											}
										</script>
									</c:if>

									<c:if test='${productor.permitidoReemplazar && productor.sinGuardar}'>
										<script>
											if(document.getElementById("tdReemplazaA")!=null){
												document.getElementById("tdReemplazaA").className="visible_inline";
											}
										</script>
									</c:if>


								</c:when>
								<c:otherwise>
									<input type="checkbox" name="guidsProductor" value="<c:out value="${productor.guid}"/>"/>
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:if>
					<display:column titleKey="archigest.archivo.cf.nombre" property="nombre" />
					<display:column style="width:10px">
									<c:if test='${serie.estadoserie == appConstants.estadosSerie.EN_ESTUDIO
								                && !empty productor.sustituyeAHistorico
								                && productor.sinGuardar
								                }'>

									<c:url var="URL" value="/action/gestionIdentificacionAction">
										<c:param name="method" value="sustituirProductor" />
										<c:param name="guidProductor" value="${productor.guid}" />
									</c:url>
									<a class="etiquetaAzul12Bold" href="javascript:reemplazarProductor('<c:out value="${productor.guid}" escapeXml="false"/>')" >
										<html:img page="/pages/images/table_relationship.gif" altKey="archigest.archivo.reemplazaA" titleKey="archigest.archivo.reemplazaA" styleClass="imgTextMiddle" />
									</a>

								</c:if>
					</display:column>
									</display:table>

				<div class="separador5">&nbsp;</div>


			</tiles:put>

			</tiles:insert>


			<div class="separador5">&nbsp;</div>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresHistoricos"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">

				<c:if test="${identificacionSerie.permitidaSeleccionProductores}">
					<script>
						function pasarAVigente() {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							form.method.value = "pasarAVigente";
							form.submit();
						}

						function eliminarProductorHistorico(){

							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							if (form.guidProductorHistorico && elementSelected(form.guidProductorHistorico)) {
								if(window.confirm('<fmt:message key="archigest.archivo.series.msg.eliminar.productor"/>')){
									form.method.value = "eliminarProductorHistorico";
									form.submit();
								}
							} else
								alert("<bean:message key='archigest.archivo.cacceso.msgNoProductoresEliminar'/>");
						}

						function verProductorSustituido(idProductor) {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							document.getElementById("guidProductorHistorico"+idProductor).checked = true;
							form.method.value = "productorSustituido";
							form.submit();
						}
					</script>
					<TABLE cellpadding=0 cellspacing=0>

						<tr>
						<c:if test="${serie.estadoserie == appConstants.estadosSerie.EN_ESTUDIO}">
						<TD>
							<a class="etiquetaAzul12Bold" href="javascript:verBuscadorProductoresHistoricos()" >
								<html:img page="/pages/images/table_add.gif" altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.anadir"/>&nbsp;
							</a>
						</TD>
						<TD width="5px"></TD>
						<TD id="tdEliminarHistorico" class="hidden">
							<a class="etiquetaAzul12Bold" href="javascript:eliminarProductorHistorico()" >
								<html:img page="/pages/images/table_delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.eliminar"/>&nbsp;
							</a>
						</TD>

						<TD width="5px"></TD>
						<TD id="tdPasarAVigente" class="hidden">
							<a class="etiquetaAzul12Bold" href="javascript:pasarAVigente()" >
								<html:img page="/pages/images/go.gif" altKey="archigest.archivo.transferencias.pasarAVigente" titleKey=" archigest.archivo.transferencias.pasarAVigente" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.transferencias.pasarAVigente"/>&nbsp;
							</a>
						</TD>

						</c:if>
					  </TR>
					</TABLE>
				</c:if>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">

			<div class="separador5">&nbsp;</div>
				<display:table name="pageScope.productoresHistoricos" id="productorHistorico" style="width:99%">

					<display:setProperty name="basic.msg.empty_list">
						&nbsp;<bean:message key="archigest.archivo.cf.noProductoresHistoricos"/>
					</display:setProperty>



					<display:column style="width:10px" >
						<c:if test="${productorHistorico.mostrarRadio}">
							<input type="radio" name="guidProductorHistorico" id="guidProductorHistorico<c:out value="${productorHistorico.guid}"/>" value="<c:out value="${productorHistorico.guid}"/>"/>

							<c:if test="${productorHistorico.permitidoPasarAVigente}">
							<script>
								if(document.getElementById("tdPasarAVigente")!=null){
									tdPasarAVigente.className="visible_inline";
								}
							</script>
							</c:if>
							<c:if test="${productorHistorico.permitidoEliminar}">
							<script>
								var tdEliminarHistorico=document.getElementById("tdEliminarHistorico");
								if(tdEliminarHistorico!=null){
									tdEliminarHistorico.className="visible_inline";
								}
							</script>
							</c:if>
						</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.nombre" property="nombre" />
					<display:column titleKey="archigest.archivo.transferencias.reemplazado">
								<c:if test="${serie.estadoserie == appConstants.estadosSerie.EN_ESTUDIO
								                && productorHistorico.sustituidoPorVigente
								                && not productorHistorico.sinGuardar
								                }">

									<c:url var="URL" value="/action/gestionIdentificacionAction">
										<c:param name="method" value="productorSustituido" />
										<c:param name="guidProductorHistorico" value="${productorHistorico.guid}" />
									</c:url>
									<a class="etiquetaAzul12Bold" href="<c:out value="${URL}" escapeXml="false"/>" >
										<html:img page="/pages/images/table_relationship.gif" altKey="archigest.archivo.reemplazadoPor" titleKey="archigest.archivo.reemplazadoPor" styleClass="imgTextMiddle" />
									</a>
								</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.fechaInicial">
						<fmt:formatDate value="${productorHistorico.fechaInicial}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
					<display:column titleKey="archigest.archivo.fechaFinal">
						<fmt:formatDate value="${productorHistorico.fechaFinal}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
					</display:table>
				<div class="separador5">&nbsp;</div>
			</tiles:put>
			</tiles:insert>

		</c:if>

		<c:if test="${!empty identificacionSerie.procedimiento && dataForm.guardado=='false'}" >
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresDeLaSerie"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

				<div class="separador5">&nbsp;</div>

				<display:table name="pageScope.productoresVigentes" id="productor" style="width:99%">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.noProductores"/>
					</display:setProperty>
					<c:if test="${identificacionSerie.permitidaSeleccionProductores}">
						<display:column style="width:10px" >
							<input type="checkbox" name="guidsProductor" value="<c:out value="${productor.guid}"/>">
						</display:column>
					</c:if>
					<display:column titleKey="archigest.archivo.cf.nombre" property="nombre" />
				</display:table>
				<div class="separador5">&nbsp;</div>
			</tiles:put>
			</tiles:insert>
		</c:if>

		<c:if test="${!empty identificacionSerie.procedimiento && dataForm.guardado=='true'}" >

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresVigentes"/></tiles:put>
			<tiles:put name="blockContent" direct="true">


				<div class="separador5">&nbsp;</div>
					<display:table name="pageScope.productoresVigentes" id="productorVigente" style="width:99%">
						<display:setProperty name="basic.msg.empty_list">
							&nbsp;<bean:message key="archigest.archivo.cf.noProductoresVigentes"/>
						</display:setProperty>
						<display:column titleKey="archigest.archivo.nombre" property="nombre" />
						<display:column titleKey="archigest.archivo.fechaInicial">
							<fmt:formatDate value="${productorVigente.fechaInicio}" pattern="${appConstants.common.FORMATO_FECHA}" />
						</display:column>
					</display:table>
				<div class="separador5">&nbsp;</div>
			</tiles:put>
			</tiles:insert>

			<div class="separador5">&nbsp;</div>

			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresHistoricos"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

			<div class="separador5">&nbsp;</div>

				<display:table name="pageScope.productoresHistoricos" id="productorHistorico" style="width:99%">
					<display:setProperty name="basic.msg.empty_list">
						&nbsp;<bean:message key="archigest.archivo.cf.noProductoresHistoricos"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.nombre" property="nombre" />
					<display:column titleKey="archigest.archivo.fechaInicial">
						<fmt:formatDate value="${productorHistorico.fechaInicial}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
					<display:column titleKey="archigest.archivo.fechaFinal">
						<fmt:formatDate value="${productorHistorico.fechaFinal}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
				</display:table>
			</tiles:put>
			</tiles:insert>
		</c:if>

		<div class="separador5">&nbsp;</div>
		<c:if test="${!empty productoresADarDeAlta}">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresADarDeAlta"/></tiles:put>
			<tiles:put name="blockContent" direct="true">

				<div class="separador5">&nbsp;</div>
				<%-- Productores a dar de alta --%>

					<c:set var="archivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />
					<input type="hidden" name="isOrganoDesplegado"/>
					<div id="divTbl">
					<display:table name="pageScope.productoresADarDeAlta" id="productorADarDeAlta" style="width:99%">
						<display:column titleKey="archigest.archivo.organo">
							<c:set var="organoNoDesplegadoName" value="organoNoDesplegado" />
							<c:set var="organoDesplegadoName" value="organoDesplegado" />
							<c:set var="position" value="${productorADarDeAlta_rowNum - 1}" />
							<c:set var="organoNoDesplegado" value="${organoNoDesplegadoName}${position}" />
							<c:set var="organoDesplegado" value="${organoDesplegadoName}${position}" />

							<div id="<c:out value="${organoNoDesplegado}"/>">
								<a href="javascript:desplegarOrgano(<c:out value="${position}"/>)">
									<html:img page="/pages/images/plus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${productorADarDeAlta.nombreCorto}" />
							</div>

							<div id="<c:out value="${organoDesplegado}"/>" style="display:none">
								<a href="javascript:desplegarOrgano(<c:out value="${position}"/>)">
									<html:img page="/pages/images/minus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${productorADarDeAlta.nombre}" />
							</div>
						</display:column>
						<display:column titleKey="archigest.archivo.nombre">
							<c:set var="position" value="${productorADarDeAlta_rowNum - 1}" />
							<html-el:text property="nombreProductor[${productorADarDeAlta_rowNum - 1}]" styleClass="input" />
						</display:column>

						<display:column titleKey="archigest.archivo.descripcion">
							<html-el:text property="descripcionProductor[${productorADarDeAlta_rowNum - 1}]" styleClass="input" />
						</display:column>

						<display:column titleKey="archigest.archivo.archivo">
							<c:choose>
							<c:when test="${!empty archivos[1]}">
								<html:select property="archivoPorProductor">
									<html:options collection="archivos" property="id" labelProperty="nombre" />
								</html:select>
							</c:when>
							<c:otherwise>
								<html-el:hidden property="archivoPorProductor" value="${archivos[0].id}"/>
								<c:set var="archivoPorProductor" value="${archivos[0]}" />
								<c:out value="${archivoPorProductor.nombre}" />
							</c:otherwise>
							</c:choose>
						</display:column>
					</display:table>
					</div>
					<div class="separador5">&nbsp;</div>


			</tiles:put>
		</tiles:insert>
		</c:if>

		</html:form>
	</tiles:put>
</tiles:insert>