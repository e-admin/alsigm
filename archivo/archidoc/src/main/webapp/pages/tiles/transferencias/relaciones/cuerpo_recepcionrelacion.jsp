<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<bean:struts id="mappingGestionRelaciones" mapping="/recepcionRelaciones" />
<c:set var="formName" value="${mappingGestionRelaciones.name}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="vNoExisteEspacioDisponible" value="${sessionScope[appConstants.transferencias.NO_EXISTE_ESPACIO_DISPONIBLE]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<SCRIPT>
isWorking = false;

function seleccionarUbicacion(idUbicacion) {
	var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
	document.getElementById("idubicacionseleccionada").value = idUbicacion;
	form.ubicacion.value = idUbicacion;

	if(form.solicitarreserva.checked){
		solicitarReserva(true)
	}
}

function solicitarReserva(value) {
	if(isWorking == false){
		var idUbicacion = document.getElementById("idubicacionseleccionada").value;

		var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
		if (value)
			if (idUbicacion != ""){
				form.ubicacion.value = idUbicacion;
				form.method.value = 'seleccionarUbicacion';
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
					var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
					window.top.showWorkingDiv(title, message);
				}
				form.submit();
				//xDisplay('depositosUbicacion','block');
			}else {
				form.solicitarreserva.checked = false;
				alert("<bean:message key='archigest.archivo.transferencias.elegirUbicacion'/>");
			}
		else
			xDisplay('depositosUbicacion','none');
	}
}

function recibirRelacion() {
	if(isWorking == false){
		var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
		if(form.idusuarioseleccionado.value=="")
		{
			alert("<bean:message key='archigest.archivo.transferencias.relaciones.gestorArchivoNoSeleccionado'/>");
			return;
		}
		<c:if test="${not vRelacion.sinDocsFisicos}">
		if(form.solicitarreserva && form.solicitarreserva.checked){

			var valorSeleccionado= window.frames['navegador'].getValorSeleccionado();


			if(valorSeleccionado == null || valorSeleccionado == "" ){
				alert("<bean:message key='errors.deposito.reserva.EsNecesarioSeleccionarUnaUbicacionParaLaReserva'/>");
				return;
			}

			var partes = valorSeleccionado.split(":");
			document.getElementById("idElementoSeleccionadoReserva").value = partes[0];
			document.getElementById("idTipoElementoSeleccionadoReserva").value = partes[1];
			document.getElementById("idubicacionseleccionada").value != valorSeleccionado;

		}


		<c:if test="${not vRelacion.signaturacionAsociadaHuecoYSignaturaSolicitable}">
			if (document.getElementById("idubicacionseleccionada").value == ""){
				alert("<bean:message key='archigest.archivo.transferencias.seleccioneUbicacion'/>");
				return;
			}
		</c:if>
		</c:if>
		workingDiv();
		form.submit();
	}
}

function workingDiv(){
	if(isWorking == false){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			isWorking = true;
			window.top.showWorkingDiv(title, message);
		}
	}
}


function setValorUbicacion(valor){
	document.getElementById("idubicacionseleccionada").value = valor;
}
</SCRIPT>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.recepcionRel"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		<c:if test="${!vNoExisteEspacioDisponible}">
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:recibirRelacion()" >
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		 </c:if>
		   <TD>
				<c:url var="cancelURI" value="/action/recepcionRelaciones">
					<c:param name="method" value="goBack"  />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
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

		<html:form action="/recepcionRelaciones" >
		<input type="hidden" name="method" value="guardarrecibirrelacion">
		<input type="hidden" name="ubicacion">

		<c:if test="${vRelacion.sinDocsFisicos}">
			<input type="hidden" name="idFormato" value="SIN_FORMATO"/>
		</c:if>



		<html:hidden property="codigo" />
		<html:hidden property="idDepositoReserva" styleId="idDepositoReserva"/>
		<html:hidden property="idTipoDepositoReserva" styleId="idTipoDepositoReserva"/>
		<html:hidden property="idElementoSeleccionadoReserva" styleId="idElementoSeleccionadoReserva"/>
		<html:hidden property="idTipoElementoSeleccionadoReserva" styleId="idTipoElementoSeleccionadoReserva"/>

		<html:hidden property="idubicacionseleccionada" styleId="idubicacionseleccionada" />
		<input type="hidden" name="relacionSinDocsFisicos" id="relacionSinDocsFisicos" value="<c:out value="${vRelacion.sindocsfisicos}"/>"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockContent" direct="true">

			<TABLE class="formulario" cellpadding=0 cellspacing=0>
					<c:if test="${not vRelacion.sinDocsFisicos}">
					<TR>
						<TD class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.transferencias.unidInstalacion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vRelacion.numUIs}"/>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo" width="250px" >
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<script>
								function seleccionarFormato() {
								<c:if test="${not vRelacion.sinDocsFisicos}">
									var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
									form.method.value = 'seleccionarFormato';
									document.getElementById("idubicacionseleccionada").value="";
									form.submit();
								 </c:if>
								}
							</script>
							<c:set var="listaFormatos" value="${sessionScope[appConstants.transferencias.LISTA_FORMATOS_KEY]}"/>

							<c:if test="${vRelacion.entreArchivos}">
								<c:out value="${vRelacion.formatoDestino.nombre}" />
								<html:hidden property="idFormato"/>
							</c:if>

							<c:if test="${!vRelacion.entreArchivos}">
								<html:select property='idFormato' size="1" onchange="seleccionarFormato()">
									<html:options collection="listaFormatos" labelProperty="nombre" property="id"/>
								</html:select>
							</c:if>

						</TD>
					</TR>
					</c:if>
					<TR>
						<TD class="tdTitulo"  width="250px" >
							<bean:message key="archigest.archivo.transferencias.soporteDocumental"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="listaFormasDocumentales" value="${sessionScope[appConstants.transferencias.LISTA_FORMAS_DOCUMENTALES_KEY]}"/>
							<html:select property='formaDocumental' size="1">
								<html:options collection="listaFormasDocumentales" labelProperty="valor" property="valor"/>
							</html:select>
						</TD>
					</TR>
			</TABLE>

			<div class="separador8">&nbsp;</div>

			<TABLE class="formulario" cellpadding=0 cellspacing=0>
					<TR>
						<TD class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.transferencias.gestorArchivo"/>:
						</TD>
						<TD class="tdDatos">
							<c:set var="listaUsuarios" value="${sessionScope[appConstants.transferencias.LISTADO_USUARIOS_KEY]}"/>
							<html:select property='idusuarioseleccionado' size="1">
								<html:option value="">&nbsp</html:option>
								<html:options collection="listaUsuarios" labelProperty="nombreYApellidos" property="id"/>
							</html:select>
						</TD>
					</TR>
			</TABLE>

			<c:if test="${not vRelacion.sinDocsFisicos}">

				<c:choose>
					<c:when test="${vRelacion.signaturacionAsociadaHuecoYSignaturaSolicitable}">

					</c:when>
					<c:otherwise>
						<div class="separador8">&nbsp;</div>

						<div id="listaEdificios">
						<c:set var="listaEdificios" value="${sessionScope[appConstants.transferencias.LISTA_EDIFICIOS_KEY]}" />
						<c:choose>
						<c:when test="${!empty listaEdificios}">

							<TABLE cellpadding=0 cellspacing=0 class="formulario">
								<TR>
									<TD class="tdTitulo" width="250px">
										<bean:message key="archigest.archivo.transferencias.ubicacionAsociada"/>:&nbsp;
									</TD>
									<c:if test="${vNoExisteEspacioDisponible}">
										<TD class="tdDatos">
											<bean:message key="archigest.archivo.transferencias.noUbicacionAsociada"/>
										</TD>
									</c:if>
								</TR>
							</TABLE>

							<div class="separador5">&nbsp;</div>

							<script>
								function verRelacionesPendientesDeUbicar(idUbicacion) {
									var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
									form.method.value = 'relacionesUbicacion';
									form.ubicacion.value = idUbicacion;
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
										var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
										window.top.showWorkingDiv(title, message);
									}
									form.submit();
								}
							</script>
							<TABLE cellpadding=4 cellspacing=0 class="formulario">
							<TR>
								<TD width="20px">&nbsp;</TD>
								<TD class="tdTitulo">
									<html:img page="/pages/images/pixel.gif" width="20px" styleClass="imgTextMiddle"/>
									<bean:message key="archigest.archivo.deposito.edificio" />&nbsp;
								</TD>
								<TD class="tdTitulo" width="15%" style="text-align:right">
									<bean:message key="archigest.archivo.deposito.huecos" />
									<bean:message key="archigest.archivo.deposito.libres" />&nbsp;
								</TD>
								<TD class="tdTitulo" width="15%" style="text-align:right">
									<bean:message key="archigest.archivo.deposito.huecos" />
									<bean:message key="archigest.archivo.deposito.disponibles" />&nbsp;
								</TD>
								<TD width="20px">&nbsp;</TD>
							</TR>
							<c:set var="relacionesPendientesUbicar" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}" />
							<c:forEach var="edificio" items="${sessionScope[appConstants.transferencias.LISTA_EDIFICIOS_KEY]}">
							<TR>
								<TD></TD>
								<TD class="tdDatos">
									<a href="javascript:verRelacionesPendientesDeUbicar('<c:out value="${edificio.id}" />')">
										<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.transferencias.relaciones.pendientesUbicar" altKey="archigest.archivo.transferencias.relaciones.pendientesUbicar" styleClass="imgTextMiddleHand" />
									</a>
									<c:choose>
										<c:when test="${vRelacion.numUIs <= edificio.numeroHuecosDisponibles}">
											<input type=radio name="idubicacion" value="<c:out value="${edificio.id}"/>"
											<c:if test="${form.idubicacionseleccionada == edificio.id}">checked</c:if> class="radio" onclick="seleccionarUbicacion(this.value)"/>
										</c:when>
										<c:otherwise>
											<html:img page="/pages/images/pixel.gif" width="20" styleClass="imgTextMiddle"/>
										</c:otherwise>
									</c:choose>

									<c:out value="${edificio.nombre}" />&nbsp;
								</TD>
								<TD class="tdDatosNumericos">
									 <c:out value="${edificio.huecosLibres}"/>
								</TD>
								<TD class="tdDatosNumericos">
									 <c:out value="${edificio.numeroHuecosDisponibles}"/>
								</TD>
								<TD></TD>
							</TR>
							<c:if test="${relacionesPendientesUbicar != null && param.ubicacion == edificio.id}">
							<tr>
								<TD>&nbsp;</TD>
								<td colspan="3">
									<div id="listaRelacionesUbicacion" class="bloque">
										<div class="separador5">&nbsp;</div>
										<display:table name="pageScope.relacionesPendientesUbicar"
											id="relacionPendienteUbicar"
											export="false"
											style="width:98%;margin-left:auto;margin-right:auto">

											<display:setProperty name="basic.msg.empty_list">
												<bean:message key="archigest.archivo.transferencias.msgNoRelacionesUbicacion"/>
											</display:setProperty>

											<display:column titleKey="archigest.archivo.relacion" style="white-space: nowrap;">
												<c:url var="verRelacionURL" value="/action/gestionRelaciones">
													<c:param name="method" value="verrelacion" />
													<c:param name="idrelacionseleccionada" value="${relacionPendienteUbicar.id}" />
												</c:url>
												<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" >
												<c:out value="${relacionPendienteUbicar.codigoTransferencia}"/>
												</a>
											</display:column>

											<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
												<fmt:message key="archigest.archivo.transferencias.tipooperacion${relacionPendienteUbicar.tipooperacion}" />
											</display:column>

											<display:column titleKey="archigest.archivo.transferencias.estado">
												<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${relacionPendienteUbicar.estado}" />
											</display:column>

											<display:column titleKey="archigest.archivo.transferencias.fEstado">
												<fmt:formatDate value="${relacionPendienteUbicar.fechaestado}" pattern="${FORMATO_FECHA}" />
											</display:column>

											<display:column titleKey="archigest.archivo.transferencias.orgRem">
												<c:out value="${relacionPendienteUbicar.organoRemitente.nombre}"  />
											</display:column>

											<display:column titleKey="archigest.archivo.transferencias.UndInstal" property="numeroUnidadesInstalacion"  style="width:20%" />

										</display:table>
										<div class="separador5">&nbsp;</div>
										<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6"  style="border-top:1px solid #000000">
											<a class=etiquetaAzul12Bold href="javascript:hideListaRelaciones()">
												<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
												&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;&nbsp;
											</a>
										</td></tr></table>
										<script>
											function hideListaRelaciones() {
												xGetElementById('listaRelacionesUbicacion').style.display='none';
											}
										</script>
									</div>
								</td>
								<TD>&nbsp;</TD>
							</tr>
							</c:if>
							</c:forEach>
							</TABLE>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.deposito.noUbicacion"/>
						</c:otherwise>
						</c:choose>
						</div>

						<c:choose>
							<c:when test="${!vNoExisteEspacioDisponible}">
								<c:set var="listaDepositos" value="${sessionScope[appConstants.transferencias.LISTA_DEPOSITOS_KEY]}" />
								<TABLE cellpadding=4 cellspacing=0 class="formulario">
									<TR>
									<TD class="tdTitulo">
									<bean:message key="archigest.archivo.transferencias.solicitarReserva"/>:&nbsp;
									<html:checkbox styleId="idSolicitarreserva" styleClass="checkbox"
										property="solicitarreserva" value="true" onclick="solicitarReserva(this.checked)"/>
									</TD>
									</TR>
								</TABLE>

								<div id="depositosUbicacion" <c:if test="${!param.solicitarreserva}">style="display:none"</c:if>>

								 	<c:set var="idSeleccionado" value="${sessionScope[appConstants.transferencias.ID_SELECCIONADO]}" />
								 	<c:if test="${idSeleccionado != null}">

									<c:url var="urlNavegador" value="/action/navegadorReservaDepositoAction">
										<c:param name="method" value="initial"/>
										<c:param name="root" value="${idSeleccionado}"/>
										<c:param name="filterByIdformato" value="${form.idFormato}"/>
										<c:param name="allowSelectAnyElement" value="true" />
										<c:param name="numHuecosNecesarios" value ="${vRelacion.numUIs}"/>
										<c:param name="recorrerDepositos" value="S" />
									</c:url>

									<script>
											function resizeNavegador() {
												var frameNavegador = document.getElementById("navegador");
												if (frameNavegador.contentDocument && frameNavegador.contentDocument.body.offsetHeight) //ns6 syntax
													frameNavegador.height = frameNavegador.contentDocument.body.offsetHeight;
												else if (frameNavegador.Document && frameNavegador.Document.body.scrollHeight) //ie5+ syntax
													frameNavegador.height = frameNavegador.Document.body.scrollHeight;
											}

											//mientras carga el iframe mostrar el cuadro de pantalla cargando.
											if (window.top.showWorkingDiv) {
												var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
												var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
												window.top.showWorkingDiv(title, message);
											}
									</script>
									<iframe id="navegador" name="navegador" src='<c:out value="${urlNavegador}" escapeXml="false"/>' frameborder="0" height="100%" width="100%"></iframe>

										<script>
											var frameNavegador = document.getElementById("navegador");
											if (frameNavegador.addEventListener)
												frameNavegador.addEventListener("load", resizeNavegador, false);
											else if (frameNavegador.attachEvent) {
												frameNavegador.detachEvent("onload", resizeNavegador); // Bug fix line
												frameNavegador.attachEvent("onload", resizeNavegador);
											}
										</script>

								</c:if>
								</div>
							</c:when>
							<c:otherwise>
								<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left">
									<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
									<bean:message key="archigest.archivo.transferencias.noUbicacionAsociada"/>
								</div>
								<div class="separador5">&nbsp;</div>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:if>
		</tiles:put>
		</tiles:insert>
	</html:form>
	</tiles:put>
</tiles:insert>