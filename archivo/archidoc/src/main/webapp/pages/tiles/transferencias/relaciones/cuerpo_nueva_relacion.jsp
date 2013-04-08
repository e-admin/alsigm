<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMAT_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>

<c:set var="vPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="vDetallePrevision" value="sessionScope[appConstants.transferencias.DETALLEPREVISION_KEY]"/>
<c:set var="vListaSeries" value="${sessionScope[appConstants.transferencias.LISTA_SERIES_KEY]}"/>

<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />
<c:set var="gestionRelacionesFormName" value="${mappingGestionRelaciones.name}" />
<c:set var="gestionRelacionesForm" value="${requestScope[gestionRelacionesFormName]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.crearRel" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0 >
		 <TR>
			<TD>
				<c:url var="atrasURL" value="/action/gestionRelaciones">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${atrasURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/Previous.gif" altKey="wizard.atras" titleKey="wizard.atras" styleClass="imgTextTop" />
					&nbsp;<bean:message key="wizard.atras"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>

			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionRelaciones.name}" />'].submit();" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextTop" />
					&nbsp;<bean:message key="archigest.archivo.aceptar" />
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
	       <TD>
				<c:url var="urlCancelar" value="/action/gestionRelaciones">
					<c:param name="method" value="goReturnPoint"/>
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${urlCancelar}" escapeXml="false"/>'" >
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextTop" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
		   </TD>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoPrevision</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.previsionDeTransferencia"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>

				<TABLE class="w98m1" cellpadding=0 cellspacing=2>
					<TR>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.prevision"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${bPrevision.codigoTransferencia}"/>
							</span>
						</TD>
						<TD width="25%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${bPrevision.nombreestado}"/>
							</span>
						</TD>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<fmt:formatDate value="${bPrevision.fechaestado}" pattern="${FORMATO_FECHA}" />
							</span>
						</TD>
						<TD width="35%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
							<span class="etiquetaNegra12Normal">
								<c:out value="${bPrevision.gestor.nombreCompleto}"/>
							</span>
						</TD>
					</TR>
				</TABLE>
				</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/datos_prevision.jsp" />
			</tiles:put>
		</tiles:insert>

		<html:form action="/gestionRelaciones">

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<c:choose>
			<c:when test="${vPrevision.detallada}">
				<bean:message key="archigest.archivo.transferencias.relacion.selLineaDetalle"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.transferencias.relacion.selSerie"/>
			</c:otherwise>
			</c:choose>
		</tiles:put>
		<c:if test="${!vPrevision.detallada}">
		<tiles:put name="buttonBar" direct="true">
			<table><tr>
				<td align="right">
					<script>
						function buscarSeries() {
							var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
							form.method.value = "buscarSerie";
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Normal" href="javascript:buscarSeries()">
						<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
						<bean:message key="archigest.archivo.buscar"/>
					</a>
				</td>
			</tr></table>
		</tiles:put>
		</c:if>
		<tiles:put name="blockContent" direct="true">
		<div class="separador5"></div>
		<html:hidden property="idseriemostrarproductores" styleId="idseriemostrarproductores" />
		<c:choose>
		<c:when test="${vPrevision.detallada}">

				<c:url var="urlPaginacion" value="/action/gestionRelaciones">
					<c:param name="method" value="nueva"/>
					<c:param name="idprevisionseleccionada" value="${vPrevision.id}"/>
				</c:url>

				<c:set var="detallesPrevision" value="${sessionScope[appConstants.transferencias.DETALLEPREVISION_KEY]}" />
				<display:table name="pageScope.detallesPrevision"
					style="width:99%;margin-top:2px;margin-left:auto;margin-right:auto"
					id="listaRegistros"
					export="false">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.msgNoDetCrearRel" />:
					</display:setProperty>

					<display:column title="&nbsp;" style="width:10px">
					<script>
						function detalleSeleccionado(idDetalle, formato, serie) {
							var form = document.forms['<c:out value="${gestionRelacionesFormName}" />'];
							var selectFormato = form.idformatoseleccionado;
							var nOptions = selectFormato.options.length;
							for (var i=0;i<nOptions; i++) {
								if (selectFormato.options[i].value == formato) {
									selectFormato.selectedIndex = i;
									break;
								}
							}

							<c:if test="${vPrevision.ordinaria == false}">
							document.getElementById("iddescriptorproductor").value = '';

							document.getElementById("nombreproductor").value = '';

							document.getElementById("idseriemostrarproductores").value=serie;

							hideProductores();

							</c:if>
						}
					</script>

					<input type="radio" name="iddetalleseleccionado" value="<c:out value="${listaRegistros.id}" />"
						onclick="detalleSeleccionado('<c:out value="${listaRegistros.id}" />', '<c:out value="${listaRegistros.idFormatoUI}" />', '<c:out value="${listaRegistros.serieDestino.id}" />')" <c:if test="${RelacionForm.iddetalleseleccionado == listaRegistros.id}">CHECKED</c:if> >
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.procedimiento">
						<c:out value="${listaRegistros.procedimiento.codigo}" />&nbsp;
						<c:out value="${listaRegistros.procedimiento.nombre}" />
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.rangoYears">
						<c:out value="${listaRegistros.anoIniUdoc}" />&nbsp;-&nbsp;
						<c:out value="${listaRegistros.anoFinUdoc}" />
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.serie">
						<c:out value="${listaRegistros.serieDestino.codigo}" />
						<c:out value="${listaRegistros.serieDestino.denominacion}" />
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.funcion" >
						<c:out value="${listaRegistros.funcion.codReferencia}" />
						<c:out value="${listaRegistros.funcion.titulo}" />
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.nUndInst" property="numUInstalacion"/>

					<display:column titleKey="archigest.archivo.transferencias.formato" >
						<c:out value="${listaRegistros.formato.nombre}" />
					</display:column>

				</display:table>
				<div class="separador5">&nbsp;</div>

		</c:when>

		<c:otherwise>

		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" width="180px"><bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;</td>
				<td class="tdDatos">
					<c:out value="${vPrevision.fondo.titulo}" />
					<input type="hidden" name="fondo" value="<c:out value="${vPrevision.fondo.id}" />">
				</td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.cf.codigoSerie"/>:&nbsp;</td>
				<td class="tdDatos"><input type="text" size="30" name="codigo" value="<c:out value="${param.codigo}" />"></td>
			</tr>
			<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;</td>
				<td class="tdDatos"><input type="text" class="input80" name="titulo" value="<c:out value="${param.titulo}" />"></td>
			</tr>
		</table>

		<c:if test="${vListaSeries != null}">
			<div class="separador5"></div>

			<div class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
				<TABLE class="w100m1" cellpadding=0 cellspacing=0  height="100%">
				  <TR>
					<TD class="etiquetaAzul12Bold"><bean:message key="archigest.archivo.resultadosBusqueda"/></TD>
				</TR></TABLE>
			</div>

			<c:url var="paginacionSeriesURI" value="/action/gestionRelaciones" />
			<jsp:useBean id="paginacionSeriesURI" type="java.lang.String" />

			<script>
				function serieSeleccionada(serie) {
					document.getElementById("iddescriptorproductor").value = '';
					document.getElementById("nombreproductor").value = '';
					document.getElementById("idseriemostrarproductores").value=serie;
					hideProductores();
					cargarFichas();
				}
			</script>
			<div id="resultados" class="bloque_m5" style="padding-top:5px;padding-bottom:5px;width:99%;margin-left:auto;margin-right:auto">

				<div <c:if test="${vListaSeries!=null && vListaSeries[3]!=null}">id="bloqueConScroll175" class="bloqueConScroll175"</c:if>>
					<div class="separador5"></div>
					<display:table name="pageScope.vListaSeries"
						id="serie"
						requestURI="<%=paginacionSeriesURI%>"
						export="false"
						style="width:98%;margin-left:auto;margin-right:auto">

						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.transferencias.msgNoSerieSel"/>
						</display:setProperty>

						<display:column  style="width:10px">
	 				 		<input
	 				 			type="radio"
	 				 			id="idserieseleccionada"
	 				 			name="idserieseleccionada"
	 				 			value="<c:out value="${serie.id}" />"
	 				 			onclick="serieSeleccionada('<c:out value="${serie.id}" />')"
								<c:if test="${RelacionForm.idserieseleccionada == serie.id}">CHECKED</c:if>
	 				 			/>
						</display:column>
						<display:column titleKey="archigest.archivo.codigoReferencia" property="codReferencia" sortable="true"/>
						<display:column titleKey="archigest.archivo.denominacion" property="titulo" sortable="true"/>
						<display:column titleKey="archigest.archivo.transferencias.estado">
							<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
						</display:column>
					</display:table>
					<div class="separador5"></div>
				 </div>
			</div>
			<div class="separador5"></div>
		</c:if>
		</c:otherwise>
		</c:choose>

		</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">Datos Relación</tiles:put>
		<tiles:put name="blockContent" direct="true">
		<input type="hidden" name="method" value="crearrelacion"/>
		<html:hidden property="idprevisionseleccionada" />

			<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
				<TR>
					<TD class="tdTitulo" width="210px">
						<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipooperacion<c:out value="${sessionScope[appConstants.transferencias.PREVISION_KEY].tipooperacion}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vPrevision.fondo.codReferencia}"/>
						<c:out value="${vPrevision.fondo.titulo}"/>
					</TD>
				</TR>
				<c:if test="${vPrevision.ordinaria == false}">
					<TR>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.productor.relacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:hidden property="iddescriptorproductor" styleId="iddescriptorproductor"/>
							<html:text property="nombreproductor" styleId="nombreproductor" styleClass="inputRO95" readonly="true" />
							<a href="javascript:buscarOrganoProductor('<c:out value="${gestionRelacionesFormName}" />')" class="tdlink" >
								<html:img page="/pages/images/expand.gif" border="0" styleClass="imgTextMiddle"/>
							</a>
							<script>
								function hideProductores() {
									xDisplay('seleccionProductor', 'none')

								}
								function buscarOrganoProductor(formName) {
									var form = document.forms[formName];
									if (form != null) {
										var serie = document.getElementById("idseriemostrarproductores");
										if ((serie != null)&&(String(serie) != "") && (String(serie) != "undefined")){
											var serie = serie.value;
											if ((serie != null)&&(String(serie) != "") && (String(serie) != "undefined")){
												form.method.value = 'verPosiblesProductores';
												form.submit();
											}
										}
									}
								}
								function seleccionarProductor(idProductor, nombreProductor) {
									document.getElementById("iddescriptorproductor").value = idProductor;
									document.getElementById("nombreproductor").value = nombreProductor;
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
											<bean:message key="archigest.archivo.transferencias.noProductoresSerie"/>
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
				</c:if>
				<c:if test="${!vPrevision.detallada}">
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vPrevision.numuinstalacion}"/>
					</TD>
				</TR>
				</c:if>
				<TR>
					<TD class="tdTitulo" >
						<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="listaFormatos" value="${sessionScope[appConstants.transferencias.LISTA_FORMATOS_KEY]}"/>
						<html:select property='idformatoseleccionado' size="1">
							<html:options collection="listaFormatos" labelProperty="nombre" property="id"/>
						</html:select>
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
						var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
						var idFicha = document.getElementById("idFicha");
						var idNivelDocumental = document.getElementById("idNivelDocumental");

						/*if ((idNivelDocumental != null) && ((String(idNivelDocumental) == "") || (String(idNivelDocumental) == "undefined"))) {
							alert('Es necesario seleccionar un nivel documental para cargar la lista de fichas disponibles.');
						}
						else
						{*/
							if ((idFicha != null) && (String(idFicha) != "undefined")
								&& ((idNivelDocumental == null)
									|| ((idNivelDocumental != null) && (String(idNivelDocumental) != "") && (String(idNivelDocumental) != "undefined")))) {

								form.method.value = "cargarFichas";
								form.submit();
							}
						/*}*/
					}
				</script>
				<c:if test="${!empty nivelesDocumentales && vPrevision.ordinaria == false}">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.nivelDocumental"/>:&nbsp;
						</td>
						<td class="tdDatos">
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
						</td>
					</tr>
				</c:if>
				<c:if test="${appConstants.configConstants.permitirFichaAltaRelacion && vPrevision.ordinaria == false}">
					<security:permissions action="${appConstants.descripcionActions.USO_FICHA_ALTA_TRANSFERENCIA_ACTION}">
						<c:set var="fichas" value="${sessionScope[appConstants.transferencias.LISTA_FICHAS_KEY]}" />
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.transferencias.ficha"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:choose>
									<c:when test="${empty fichas}">
										<html:select property="idFicha" styleId="idFicha">
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
							</td>
						</tr>
					</security:permissions>
				</c:if>
				<TR>
					<TD class="tdTitulo" style="vertical-align:top">
						<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:textarea property="observaciones" rows="2" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>
					</TD>
				</TR>
			</TABLE>
			</tiles:put>
		</tiles:insert>
		</html:form>

	</tiles:put>
</tiles:insert>