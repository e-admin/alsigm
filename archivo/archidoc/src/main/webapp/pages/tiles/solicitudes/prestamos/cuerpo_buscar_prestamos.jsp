<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mapping" mapping="/buscarPrestamos" />

<c:set var="estados" value="${requestScope[appConstants.prestamos.LISTA_ESTADOS_KEY]}"/>
<c:set var="notas" value="${requestScope[appConstants.prestamos.LISTA_NOTAS_KEY]}"/>

<div id="contenedor_ficha">
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function clean()
	{
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.codigo.value ="";
		form.organo.value = "";
		form.solicitante.value = "";

		form.fechaCompEntrega.value = "=";
		form.fechaFormatoEntrega.value = "AAAA";
		form.fechaDEntrega.value = "";
		form.fechaMEntrega.value = "";
		form.fechaAEntrega.value = "";
		form.fechaSEntrega.value = "";
		checkFechaComp(form);

		deseleccionarCheckboxSet(form.estados);
		deseleccionarCheckboxSet(form.notas);

		form.fechaIniFormatoEntrega.value = "AAAA";
		form.fechaIniDEntrega.value = "";
		form.fechaIniMEntrega.value = "";
		form.fechaIniAEntrega.value = "";
		form.fechaIniSEntrega.value = "";

		form.fechaFinFormatoEntrega.value = "AAAA";
		form.fechaFinDEntrega.value = "";
		form.fechaFinMEntrega.value = "";
		form.fechaFinAEntrega.value = "";
		form.fechaFinSEntrega.value = "";
		form.datosautorizado.value="";
		form.tipoentrega.value = "";
	}

	function buscar(){

		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
			var message = '<bean:message key="archigest.archivo.buscando.msgPrestamos"/>';
			var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
			window.top.showWorkingDiv(title, message, message2);
		}
		document.forms[0].submit();
	}
</script>
	<html:form action="/buscarPrestamos">
	<input type="hidden" name="method" value="buscar"/>

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px"><bean:message key="archigest.archivo.prestamos.buscar"/></td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<a class="etiquetaAzul12Bold" href="javascript:buscar();">
										<html:img page="/pages/images/buscar.gif"
										        altKey="archigest.archivo.buscar"
										        titleKey="archigest.archivo.buscar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold" href="javascript:clean()">
										<html:img page="/pages/images/clear0.gif"
										        altKey="archigest.archivo.limpiar"
										        titleKey="archigest.archivo.limpiar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
									</td>
								   	<c:if test="${appConstants.configConstants.mostrarAyuda}">
										<td width="10">&nbsp;</td>
										<td>
											<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
											<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
											<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/prestamos/BusquedaPrestamo.htm');">
											<html:img page="/pages/images/help.gif"
												altKey="archigest.archivo.ayuda"
												titleKey="archigest.archivo.ayuda"
												styleClass="imgTextMiddle"/>
												&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
										</td>
										<td width="10">&nbsp;</td>
									</c:if>
									<TD>
										<c:url var="cancelURL" value="/action/buscarPrestamos">
											<c:param name="method" value="goBack" />
										</c:url>
										<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
											<html:img
												page="/pages/images/close.gif"
												altKey="archigest.archivo.cerrar"
												titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
											&nbsp;<bean:message key="archigest.archivo.cerrar"/>
										</a>
								   	</TD>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</span></h1>

		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">

			<div id="barra_errores"><archivo:errors/></div>

			<div class="bloque">
				<table class="formulario">

					<%--Filtro por código --%>
					<tr>
						<td class="tdTituloFicha" width="150">
							<bean:message key="archigest.archivo.prestamos.busqueda.codigo"/>:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text property="codigo" />
						</td>
					</tr>

					<security:permissions permission="${appConstants.permissions.GESTION_PRESTAMOS_ARCHIVO}|${appConstants.permissions.GESTION_PRESTAMOS_ORGANO_SOLICITANTE}">
					<%--Filtro por organos --%>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.prestamos.busqueda.organo"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:text property="organo" styleClass="input60"/>
						</td>
					</tr>

					<%--Filtro por solicitante del prestamo--%>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.prestamos.busqueda.solicitante"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:text property="solicitante"
								styleClass="input60"/>
						</td>
					</tr>
					</security:permissions>

					<%-- Filtro por número de expediente --%>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.prestamos.busqueda.numeroExpediente"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<html:text property="expedienteudoc" styleClass="input60"/>
						</td>
					</tr>

					<%--Filtro por estado--%>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.prestamos.busqueda.estado"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<table class="formulario">
									<tr>
										<td align="right">
											<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms[0].estados);"
								 			><html:img page="/pages/images/checked.gif"
												    border="0"
												    altKey="archigest.archivo.selTodos"
												    titleKey="archigest.archivo.selTodos"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
											&nbsp;
											<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms[0].estados);"
								 			><html:img page="/pages/images/check.gif"
												    border="0"
												    altKey="archigest.archivo.quitarSel"
												    titleKey="archigest.archivo.quitarSel"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
											&nbsp;&nbsp;
									   </td>
									</tr>
							</table>
							<table cellpadding="0" cellspacing="0">
								<c:forEach var="estado" items="${estados}" varStatus="row">
									<c:if test="${(row.index % 3) == 0}"><tr></c:if>
									<td>
										<html:multibox style="border:0" property="estados">
											<bean:write name="estado" property="id"/>
										</html:multibox>
									</td>
									<td class="tdDatos">
										<bean:write name="estado" property="nombre"/>
									</td>
									<td width="10">&nbsp;</td>
									<c:if test="${(row.index % 3) == 2}"></tr></c:if>
								</c:forEach>
							</table>
						</td>
					</tr>

					<%--Filtro por notas del prestamos--%>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.prestamos.busqueda.notas"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<table class="formulario">
									<tr>
										<td align="right">
											<a class="etiquetaAzul12Bold" href="javascript:seleccionarCheckboxSet(document.forms[0].notas);"
								 			><html:img page="/pages/images/checked.gif"
												    border="0"
												    altKey="archigest.archivo.selTodos"
												    titleKey="archigest.archivo.selTodos"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.selTodos"/></a>
											&nbsp;
											<a class="etiquetaAzul12Bold" href="javascript:deseleccionarCheckboxSet(document.forms[0].notas);"
								 			><html:img page="/pages/images/check.gif"
												    border="0"
												    altKey="archigest.archivo.quitarSel"
												    titleKey="archigest.archivo.quitarSel"
												    styleClass="imgTextBottom" />&nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
											&nbsp;&nbsp;
									   </td>
									</tr>
							</table>
							<table cellpadding="0" cellspacing="0">
								<c:forEach var="nota" items="${notas}" varStatus="row">
									<c:if test="${(row.index % 3) == 0}"><tr></c:if>
									<td>
										<html:multibox style="border:0" property="notas">
											<bean:write name="nota" property="id"/>
										</html:multibox>
									</td>
									<td class="tdDatos">
										<bean:write name="nota" property="nombre"/>
									</td>
									<td width="10">&nbsp;</td>
									<c:if test="${(row.index % 3) == 2}"></tr></c:if>
								</c:forEach>
							</table>
						</td>
					</tr>

					<%--Filtro por fechas de entrega--%>
					<tr>
						<td class="tdTituloFicha" width="150"><bean:message key="archigest.archivo.prestamos.busqueda.fechaEntrega"/>:&nbsp;</td>
						<td class="tdDatosFicha">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<script>
										function checkFechaComp(){
											if (window.document.forms[0].fechaCompEntrega.value == "[..]")
											{
												document.getElementById("idFechaEntrega").style.display = 'none';
												document.getElementById("idRangoFechasEntrega").style.display = 'block';
												if(!document.all){
													document.getElementById("idRangoFechasEntrega").style.display = 'table-cell';
												}

												checkFechaFormato(window.document.forms[0].fechaIniFormatoEntrega, "idFechaIni");
												checkFechaFormato(window.document.forms[0].fechaFinFormatoEntrega, "idFechaFin");
											}
											else
											{
												document.getElementById("idRangoFechasEntrega").style.display = 'none';
												document.getElementById("idFechaEntrega").style.display = 'block';
												if(!document.all){
													document.getElementById("idFechaEntrega").style.display = 'table-cell';
												}

												checkFechaFormato(window.document.forms[0].fechaFormatoEntrega, "idFecha");
											}
										}

										function checkFechaFormato(formato, prefix){
											checkFechaFormatoSuffix(formato,prefix,"Entrega");
										}
									</script>
									<td>
										<html:select styleId="fechaCompEntrega" property="fechaCompEntrega" styleClass="input" onchange="javascript:checkFechaComp();">
											<html:option key="archigest.archivo.simbolo.igual" value="="/>
											<html:option key="archigest.archivo.simbolo.mayor" value="&gt;"/>
											<html:option key="archigest.archivo.simbolo.menor" value="&lt;"/>
											<html:option key="archigest.archivo.simbolo.mayoroigual" value="&gt;="/>
											<html:option key="archigest.archivo.simbolo.menoroigual" value="&lt;="/>
											<html:option key="archigest.archivo.simbolo.rango" value="[..]"/>
									    </html:select>
									</td>
								    <td width="10">&nbsp;</td>
									<td id="idFechaEntrega" style="display:none;">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<html:select property="fechaFormatoEntrega" styleId="fechaFormatoEntrega" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFecha')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaDValorEntrega" style="display:none;" nowrap="true">
											    	<html:text property="fechaDEntrega" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaMValorEntrega" style="display:none;" nowrap="true">
											    	<html:text property="fechaMEntrega" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaAValorEntrega" style="display:none;">
											    	<html:text property="fechaAEntrega" size="4" maxlength="4" styleClass="input"/>
											    </td>
											    <td id="idFechaSValorEntrega" style="display:none;">
											    	<html:text property="fechaSEntrega" size="5" maxlength="5" styleClass="input"/>
											    </td>
										    </tr>
									    </table>
									</td>
									<td id="idRangoFechasEntrega" style="display:none;">
										<table cellpadding="0" cellspacing="0">
											<tr>
											    <td class="user_data">Desde</td>
											    <td width="5">&nbsp;</td>
												<td>
													<html:select property="fechaIniFormatoEntrega" styleId="fechaIniFormatoEntrega" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFechaIni')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaIniDValorEntrega" style="display:none;" nowrap="true">
											    	<html:text property="fechaIniDEntrega" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaIniMValorEntrega" style="display:none;" nowrap="true">
											    	<html:text property="fechaIniMEntrega" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaIniAValorEntrega" style="display:none;">
											    	<html:text property="fechaIniAEntrega" size="4" maxlength="4" styleClass="input"/>
											    </td>
											    <td id="idFechaIniSValorEntrega" style="display:none;">
											    	<html:text property="fechaIniSEntrega" size="5" maxlength="5" styleClass="input"/>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td class="user_data">Hasta</td>
											    <td width="5">&nbsp;</td>
												<td>
													<html:select property="fechaFinFormatoEntrega" styleId="fechaFinFormatoEntrega" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFechaFin')">
														<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
														<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
														<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
														<html:option key="archigest.archivo.formato.S" value="S"/>
												    </html:select>
											    </td>
											    <td width="10">&nbsp;</td>
											    <td id="idFechaFinDValorEntrega" style="display:none;" nowrap="true">
											    	<html:text property="fechaFinDEntrega" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaFinMValorEntrega" style="display:none;" nowrap="true">
											    	<html:text property="fechaFinMEntrega" size="2" maxlength="2" styleClass="input"/>-
											    </td>
											    <td id="idFechaFinAValorEntrega" style="display:none;">
											    	<html:text property="fechaFinAEntrega" size="4" maxlength="4" styleClass="input"/>
											    </td>
											    <td id="idFechaFinSValorEntrega" style="display:none;">
											    	<html:text property="fechaFinSEntrega" size="5" maxlength="5" styleClass="input"/>
											    </td>
										    </tr>
									    </table>
									</td>
								</tr>
							</table>
							<script>checkFechaComp();</script>
						</td>
					</tr>
					<tr>
						<td class="tdTituloFicha" width="200px">
							<bean:message key="archigest.archivo.prestamos.autorizado" />:&nbsp;
						</td>
						<td class="tdDatosFicha">
							<html:text styleId="datosautorizado" size="100" maxlength="100" property="datosautorizado" styleClass="input99" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.prestamos.tipoEntrega" />:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text styleId="tipoentrega" size="100" maxlength="100" property="tipoentrega" styleClass="input99" />
						</td>
					</tr>
				</table>
				<div class="separador5">&nbsp;</div>
			</div>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
	</html:form>
</div> <%--contenedor_ficha --%>


