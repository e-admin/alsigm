<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<script>
	function mostrarBuscadorSeries(formName, id) {
		var buscadorSeries = xGetElementById('buscadorSerie'+id);
		if (!buscadorSeries || buscadorSeries.style.display == 'none') {
			var form = document.forms[formName];
			document.getElementById("idDescriptorProductor").value = "";
			document.getElementById("nombreProductor").value = "";
			form.elements['method'].value="verBuscadorSeries"+id;
			form.submit();
		} else
			xDisplay(buscadorSeries, 'none');
	}

	function aceptar(formName){
		var form = document.forms[formName];
		form.elements['method'].value="guardar";

		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		form.submit();
	}
</script>

<bean:struts id="mappingGestionIngresoDirecto" mapping="/gestionIngresoDirecto" />
<c:set var="ingresoDirectoFormName" value="${mappingGestionIngresoDirecto.name}" />
<c:set var="ingresoDirectoForm" value="${requestScope[ingresoDirectoFormName]}" />

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<%--Variable con la relación de ingreso directo --%>
<c:set var="bRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<html:form action="/gestionIngresoDirecto">
<div id="contenedor_ficha">
		<input type="hidden" name="method"/>
		<html:hidden property="id"/>
		<html:hidden property="serie" styleId="serie"/>
		<html:hidden property="permitidoModificarFormato"/>
		<html:hidden property="idRevDoc"/>

		<div class="ficha">

			<h1><span>
				<div class="w100">
					<TABLE class="w98" cellpadding=0 cellspacing=0>
						<TR>
						    <TD class="etiquetaAzul12Bold" height="25px">
								<bean:message key="archigest.archivo.transferencias.edicion"/><bean:message key="archigest.archivo.transferencias.ele"/>
								<bean:message key="archigest.archivo.transferencias.contenido"/>
							</TD>
						    <TD align="right">
								<TABLE cellpadding=0 cellspacing=0>
									<TR>
										<TD>
											<a class="etiquetaAzul12Bold" href="javascript:aceptar('<c:out value="${ingresoDirectoFormName}" />');" >
												<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
												&nbsp;<bean:message key="archigest.archivo.aceptar"/>
											</a>
										</TD>
										<TD width="10">&nbsp;</TD>
										<TD>
											<c:url var="cancelURI" value="/action/gestionIngresoDirecto">
												<c:param name="method" value="goBack"  />
											</c:url>
											<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
												<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
												&nbsp;<bean:message key="archigest.archivo.cancelar"/>
											</a>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
				</div>
			</span></h1>

		<div class="cuerpo_drcha">
			<div class="cuerpo_izda">

			<div id="barra_errores">
					<archivo:errors />
			</div>

			<div class="bloque"> <%--primer bloque de datos --%>

	 			<table class="formulario">
					<TR>
						<TD class="tdTitulo" width="170px">
							<bean:message key="archigest.archivo.archivoCustodia"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="bListaCodigosArchivoKey" value="${appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY}"/>
							<jsp:useBean id="bListaCodigosArchivoKey" type="java.lang.String"/>
							<c:set var="listaArchivos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY]}" />
							<c:choose>
								<c:when test="${bRelacion == null}">

									<c:choose>
									<c:when test="${!empty listaArchivos}">
										<html:select property='idArchivo' styleId="idArchivo" size="1" styleClass="input">
											<html:option value=""></html:option>
											<html:optionsCollection name="<%=bListaCodigosArchivoKey%>" label="nombre" value="id"/>
										</html:select>
									</c:when>
									<c:otherwise>
											<html:text property="nombreArchivo" readonly="readonly" styleClass="inputRO95"/>
											<html:hidden property="idArchivo"/>
									</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
										<input type="text" value="<c:out value="${bRelacion.nombreArchivoReceptor}"/>" readonly="readonly" class="inputRO95"/>
										<html:hidden property="idArchivo"/>
								</c:otherwise>
							</c:choose>

						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.cf.fondoDestino"/>:&nbsp;
						</TD>
						<TD class="tdDatos">

						<script>
								function cambioListaFondo(){
									document.getElementById("nombreSerie").value = "";
									document.getElementById("serie").value = "";
									document.getElementById("idDescriptorProductor").value = "";
									document.getElementById("nombreProductor").value = "";
								}
							</script>

							<c:set var="bListaCodigosFondoKey" value="${appConstants.transferencias.LISTA_CODIGOSFONDO_KEY}"/>
							<jsp:useBean id="bListaCodigosFondoKey" type="java.lang.String"/>
							<c:set var="listaFondos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSFONDO_KEY]}" />
							<c:if test="${!empty listaFondos}">
								<html:select property='idFondo' styleId="idFondo" size="1" styleClass="input" onchange="cambioListaFondo()" >
									<html:option value=""></html:option>
									<html:optionsCollection name="<%=bListaCodigosFondoKey%>" label="label" value="id"/>
								</html:select>
							</c:if>
						</TD>
					</TR>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.serieDestino"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:set var="canSelectSerie" value="${true}" />
							<c:set var="inputClass" value="inputRO95" />
							<c:if test="${!canSelectSerie}"><c:set var="inputClass" value="inputRO99" /></c:if>
							<input name="nombreSerie" id="nombreSerie" type="text" class="<c:out value="${inputClass}" />"
							value="<c:out value="${ingresoDirectoForm.nombreSerie}" />" readOnly="readonly">
							<c:if test="${canSelectSerie}">
								<a href="javascript:mostrarBuscadorSeries('<c:out value="${mappingGestionIngresoDirecto.name}" />','')">
									<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.series" altKey="archigest.archivo.series" styleClass="imgTextMiddleHand" />
								</a>
								<bean:define id="idBuscadorSerie" value="" toScope="request"/>
								<bean:define id="formName" name="mappingGestionIngresoDirecto" property="name" toScope="request"/>
								<c:if test="${param.method == 'verBuscadorSeries' || param.method == 'buscarSerie'}">
									<jsp:include page="buscador_serie_generico.jsp" flush="true" />
								</c:if>
							</c:if>
						</td>
					</tr>

					<TR>
						<TD class="tdTitulo" >
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:choose>
								<c:when test="${ingresoDirectoForm.permitidoModificarFormato}">
									<c:set var="listaFormatos" value="${sessionScope[appConstants.transferencias.LISTA_FORMATOS_KEY]}"/>

									<html:select property='idFormato' size="1">
										<html:options collection="listaFormatos" labelProperty="nombre" property="id"/>
									</html:select>
								</c:when>
								<c:otherwise>
									<input type="text" value="<c:out value="${bRelacion.formato.nombre}"/>" readonly="readonly" class="inputRO95"/>
									<html:hidden property="idFormato"/>
								</c:otherwise>
							</c:choose>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo" >
							<bean:message key="archigest.archivo.transferencias.soporteDocumental"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="listaFormasDocumentales" value="${sessionScope[appConstants.transferencias.LISTA_FORMAS_DOCUMENTALES_KEY]}"/>
							<html:select property='formaDocumental' size="1">
								<html:options collection="listaFormasDocumentales" labelProperty="valor" property="valor"/>
							</html:select>
						</TD>
					</TR>
					<c:set var="nivelesDocumentales" value="${sessionScope[appConstants.transferencias.LISTA_NIVELES_DOCUMENTALES_KEY]}" />
					<%--Se podrá elegir el nivel documental para las relaciones NO ORDINARIAS --%>
					<script>
						function cargarFichas() {
							var form = document.forms['<c:out value="${mappingGestionIngresoDirecto.name}" />'];
							var idFicha = document.getElementById("idFicha");
							var idNivelDocumental = document.getElementById("idNivelDocumental");

							if ((idFicha != null) && (String(idFicha) != "undefined")
								&& ((idNivelDocumental == null)
									|| ((idNivelDocumental != null) && (String(idNivelDocumental) != "") && (String(idNivelDocumental) != "undefined")))) {

								form.method.value = "cargarFichas";
								form.submit();
							}
						}
					</script>
					<%--Se podrá elegir el nivel documental siempre que se haya establecido, por defecto será unidad documental --%>
					<c:if test="${!empty nivelesDocumentales}">
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.transferencias.nivelDocumental"/>:&nbsp;
							</td>
							<td class="tdDatos">
							<c:choose>
								<c:when test="${bRelacion == null}">
								<c:choose>
									<c:when test="${appConstants.configConstants.permitirFichaAltaRelacion}">
										<html:select property="idNivelDocumental" onchange="javascript:cargarFichas();" styleId="idNivelDocumental">
											<html:option value=""></html:option>
											<html:options collection="nivelesDocumentales" labelProperty="nombre" property="id" />
										</html:select>
									</c:when>
									<c:otherwise>
										<html:select property="idNivelDocumental" styleId="idNivelDocumental">
											<html:option value=""></html:option>
											<html:options collection="nivelesDocumentales" labelProperty="nombre" property="id" />
										</html:select>
									</c:otherwise>
								</c:choose>
								</c:when>
								<c:otherwise>
									<input type="text" value="<c:out value="${bRelacion.nivelDocumental.nombre}"/>" readonly="readonly" class="inputRO95"/>
									<html:hidden property="idNivelDocumental"/>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:if>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<TR>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.orgProd"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:hidden property="idDescriptorProductor" styleId="idDescriptorProductor"/>
							<html:text property="nombreProductor" styleId="nombreProductor" styleClass="inputRO95" readonly="true" />
							<a href="javascript:buscarOrganoProductor('<c:out value="${ingresoDirectoFormName}" />')" class="tdlink" >
								<html:img page="/pages/images/expand.gif" border="0" styleClass="imgTextMiddle"/>
							</a>
							<script>
								function hideProductores() {
									xDisplay('seleccionProductor', 'none')

								}
								function buscarOrganoProductor(formName) {
									var form = document.forms[formName];
									if (form != null) {
										var serie = document.getElementById("serie");
										if ((serie != null)&&(String(serie) != "") && (String(serie) != "undefined")){
											var serie = serie.value;
											if ((serie != null)&&(String(serie) != "") && (String(serie) != "undefined")){
												form.method.value = 'verPosiblesProductores';
												if (window.top.showWorkingDiv) {
													var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
													var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
													window.top.showWorkingDiv(title, message);
												}
												form.submit();
											}
										}
									}
								}
								function seleccionarProductor(idProductor, nombreProductor) {
									document.getElementById("idDescriptorProductor").value = idProductor;
									document.getElementById("nombreProductor").value = nombreProductor;
									hideProductores();
								}
							</script>
							<c:set var="posiblesProductores" value="${requestScope[appConstants.transferencias.LISTA_PRODUCTORES]}" />
							<c:if test="${!empty posiblesProductores}">
								<div id="seleccionProductor" class="bloque_busquedas">
									<div style="padding-top:8px;padding-bottom:8px">
									<display:table name="pageScope.posiblesProductores" id="productor"
										style="width:99%; margin-top:5px;margin-left:auto;margin-right:auto">
										<display:setProperty name="basic.msg.empty_list">
											<bean:message key="archigest.archivo.fondos.noProductoresIngreso"/>
										</display:setProperty>
										<display:column titleKey="archigest.archivo.transferencias.organismosProductores">
											<div onclick="seleccionarProductor('<c:out value="${productor.id}" />','<c:out value="${productor.nombre}" />')" style="cursor:default"><c:out value="${productor.nombre}" /></div>
										</display:column>
										<display:column titleKey="archigest.archivo.fechaInicial">
											<fmt:formatDate value="${productor.fechaInicial}" pattern="${FORMATO_FECHA}"/>
										</display:column>
										<display:column titleKey="archigest.archivo.fechaFinal">
											<fmt:formatDate value="${productor.fechaFinal}" pattern="${FORMATO_FECHA}"/>
										</display:column>
									</display:table>
									</div>
									<script>
										function hideSeleccionProductor() {
											xDisplay('seleccionProductor','none');
										}
									</script>
									<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6" style="border-top:1px solid #7B7B7B;">
									<a class=etiquetaAzul12Bold href="javascript:hideSeleccionProductor()">
										<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.cerrar"/>
									</a>&nbsp;
									</td></tr></table>
								</div> <%--bloque --%>
							</c:if>
						</td>
					</TR>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
					<c:if test="${appConstants.configConstants.permitirFichaAltaRelacion}">
						<security:permissions action="${appConstants.descripcionActions.USO_FICHA_ALTA_TRANSFERENCIA_ACTION}">
							<c:set var="fichas" value="${sessionScope[appConstants.transferencias.LISTA_FICHAS_KEY]}" />
							<tr>
								<td class="tdTitulo">
									<bean:message key="archigest.archivo.transferencias.ficha"/>:&nbsp;
								</td>
								<td class="tdDatos">
								<c:choose>
									<c:when test="${bRelacion == null}">
										<c:choose>
											<c:when test="${empty fichas}">
												<html:select property="idFicha" onclick="javascript:cargarFichas();" styleId="idFicha">
													<html:option value=""></html:option>
												</html:select>
											</c:when>
											<c:otherwise>
												<html:select property="idFicha" styleId="idFicha">
													<html:option value=""></html:option>
													<html:options collection="fichas" labelProperty="nombre" property="id" />
												</html:select>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<input type="text" value="<c:out value="${bRelacion.nombreFicha}"/>" readonly="readonly" class="inputRO95"/>
										<html:hidden property="idFicha"/>
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
						</security:permissions>
					</c:if>
					<tr><td class="separador5" colspan="2">&nbsp;</td></tr>
						<tr>
						<td class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.transferencias.observaciones"/>:
						</td>
						<td class="tdDatos">
							<html:textarea property="observaciones" rows="3" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
						</td>
					</tr>
				</table>
			</div> <%--bloque --%>

			</div> <%--cuerpo_drcha --%>
		</div> <%--cuerpo_izda --%>

		<h2><span>&nbsp;
		</span></h2>

		</div> <%--ficha --%>

</div> <%--contenedor_ficha --%>

</html:form>

