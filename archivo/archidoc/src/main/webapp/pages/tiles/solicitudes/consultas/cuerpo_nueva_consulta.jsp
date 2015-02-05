<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<bean:struts id="actionMapping" mapping="/gestionConsultas" />
<c:set var="formBean" value="${sessionScope[actionMapping.name]}"/>
<c:set var="bConsulta" value="${sessionScope[appConstants.consultas.CONSULTA_KEY]}"/>


<c:set var="archivos" value="${sessionScope[appConstants.prestamos.LISTA_ARCHIVOS]}"/>
<c:set var="tiposEntrega" value="${sessionScope[appConstants.consultas.LISTA_TIPOS_ENTREGA]}" />
<c:set var="motivos" value="${sessionScope[appConstants.consultas.LISTA_MOTIVO_KEY]}"/>
<c:set var="temas" value="${sessionScope[appConstants.consultas.LISTA_TEMA_KEY]}"/>
<c:set var="unidadesDocumentales" value="${sessionScope[appConstants.solicitudes.LISTA_DETALLES_UDOCS_SOLICITUD_KEY]}" />

<c:set var="usuariosConsulta" value="${sessionScope[appConstants.consultas.LISTA_USUARIOS_CONSULTAS_KEY]}"/>

<c:set var="view" value="${requestScope[appConstants.consultas.CONSULTA_VIEW_OBJECT_KEY]}"/>

<c:set var="archivo" value="${sessionScope[appConstants.solicitudes.ARCHIVO_SOLICITUD_KEY]}" />
<c:if test="${archivo != null}">
	<c:set var="idArchivo" value="${archivo.id}" />
	<c:set var="nombreArchivo" value="${archivo.nombre}" />
</c:if>


<script>

	var tipoEntidad = '<c:out value="${formBean.tipoentconsultora}"/>';

	function aceptar() {

		if(esChecked("tienereserva")){
			if(esVacio("finicialreserva")){
				alert("<bean:message key='errors.solicitudes.prestamos.fechaReservaNoIndicada'/>");
				return;
			}
		}

		<c:if test="${view.esInvestigador}">
		if(getValor("idusrsolicitante") == ""){
			alert("<bean:message key='errors.solicitudes.consultas.noUsuarioConsultaSelected'/>");
			return;
		}
		</c:if>


		<c:choose>
			<c:when test="${view.esEdicion}">
				enviarFormulario("formulario","guardaredicion");
			</c:when>
			<c:otherwise>
				enviarFormulario("formulario","crear");
			</c:otherwise>
		</c:choose>

	}

	function actualizarTipoEntidad(radio){
		if(radio.value != tipoEntidad){
			enviarFormulario("formulario","actualizarTipoEntidad");
		}
	}

	function actualizarDatosUsuario(){
		enviarFormulario("formulario","actualizarDatosUsuario");
	}

	function actualizarArchivo(){
		enviarFormulario("formulario","actualizarArchivo");
	}


	function cancelNuevoTema(){
		hide();
	}

	function filtarUsuarios(){
		enviarFormulario("formulario","filtrarUsuarios");
	}

	function nuevoTema() {
		enviarFormulario("formulario", "addTema");
	}

	function addNuevoTema() {
		setVisible("TemaNew", true);
		setVisible("Tema", false);
	}

	function cancelAddNuevoTema() {
		setValor("temaNuevo","");
		setVisible("TemaNew", false);
		setVisible("Tema", true);
	}


	function actualizarConsultaEnSala(elemento){
		enviarFormulario("formulario", "actualizarConsultaEnSala");
	}

	function actualizarVerUsuariosConsultaEnSala(elemento){
		enviarFormulario("formulario", "actualizarVerUsuariosConsultaEnSala");
	}

	<c:if test="${not view.mostrarCheckUsuariosSala}">
	function actualizarArchivo(){
		enviarFormulario("formulario","actualizarArchivo");
	}
	</c:if>

	function tratarReserva(){
		if (!esChecked("tienereserva")) {
			setValor("finicialreserva", "");
			setVisible("tr_reserva", false);
		}
		else {
			setVisible("tr_reserva", true);
			if(!document.all)
				document.getElementById("tr_reserva").style.display = 'table-row';
		}
	}

</script>
<div id="contenedor_ficha">
<div class="ficha">
<html:form action="/gestionConsultas" styleId="formulario">
<input type="hidden" name="method" id="method" value="crear" />

<html:hidden property="id"/>


<h1><span>
	<div class="w100">
		<TABLE class="w98" cellpadding=0 cellspacing=0>
		<TR>
		    <TD class="etiquetaAzul12Bold" height="25px">
				<bean:message key="archigest.archivo.consultas.creacion"/>
			</TD>
		    	<TD align="right">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
				   	<TD>
						<a class="etiquetaAzul12Bold" href="javascript:aceptar();" >
							<html:img
								page="/pages/images/Ok_Si.gif" border="0"
								altKey="archigest.archivo.aceptar"
								titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>
					</TD>
				   	<TD width="10">&nbsp;</TD>
				   	<TD>
						<tiles:insert definition="button.closeButton" flush="true">
							<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
							<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
						</tiles:insert>
				   	</TD>
				   	<c:if test="${appConstants.configConstants.mostrarAyuda}">
					<td width="10">&nbsp;</td>
						<td>
							<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
							<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
							<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/consultas/CrearNuevaConsulta.htm');">
							<html:img page="/pages/images/help.gif"
							        altKey="archigest.archivo.ayuda"
							        titleKey="archigest.archivo.ayuda"
							        styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
						</td>
					</c:if>
				</TR>
				</TABLE>
			</TD>
		</TR>
	</TABLE>
	</div>
</span></h1>


<div class="cuerpo_drcha">
<div class="cuerpo_izda">

	<DIV id="barra_errores"><archivo:errors/></DIV>

	<div class="bloque"> <%--primer bloque de datos --%>

		<c:choose>
		<c:when test="${view.mostrarRadioTipoUsuario}">
		<table class="formulario">
		<tr id="tr_usuario">
			<td class="tdTitulo" width="200px">
				<bean:message key="archigest.archivo.consultas.tipoSolicitante"/>:&nbsp;
			</td>
			<td class="tdTitulo" colspan="3">
				<html:radio property="tipoentconsultora" value="2" styleId="opcionCiudadano" styleClass="radio"
					onclick="javascript:actualizarTipoEntidad(this);"
				/><bean:message key="archigest.archivo.consultas.ciudadano"/>
				<html:radio property="tipoentconsultora" value="3" styleId="opcionOrgano" styleClass="radio"
					onclick="javascript:actualizarTipoEntidad(this);"
				/><bean:message key="archigest.archivo.organo"/>
				<html:radio property="tipoentconsultora" value="1" styleId="opcionInvestigador" styleClass="radio"
					onclick="javascript:actualizarTipoEntidad(this);"
				/><bean:message key="archigest.archivo.consultas.investigador"/>
			</td>
		</tr>
		</table>
		</c:when>
		<c:otherwise>
			<html:hidden property="tipoentconsultora" styleId="tipoentconsultora"/>
		</c:otherwise>
		</c:choose>

		<c:if test="${view.mostrarCheckVerUsuarioSala}">
			<table class="formulario">
				<TR>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.salas.consulta.ver.usuarios.sala"/>:
					</TD>
					<TD class="tdDatos">
						<html:checkbox property="consultaEnSala" styleId="consultaEnSala" styleClass="checkbox" onclick="javascript:actualizarVerUsuariosConsultaEnSala(this);"/>
					</TD>
				</TR>
			</table>
			</c:if>

		<c:if test="${view.mostrarCheckUsuariosSala}">
		<table class="formulario">
			<TR>
				<TD class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.consultas.consulta.en.sala"/>:
				</TD>
				<TD class="tdDatos">
					<html:checkbox property="consultaEnSala" styleId="consultaEnSala" styleClass="checkbox" onclick="javascript:actualizarConsultaEnSala(this);"/>
				</TD>
			</TR>
		</table>

		</c:if>

		<table class="formulario">
			<c:choose>
				<c:when test="${empty archivos}">
					<input type="hidden" name="idarchivo" value="<c:out value="${user.idarchivo}"/>"/>
	 			</c:when>
				<c:otherwise>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.prestamos.archivo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${not empty unidadesDocumentales }">
									<input type="hidden" name="fromBusqueda" value='<c:out value="${bPrestamo.fromBusqueda}"/>'/>
									<c:out value="${nombreArchivo}"/>
									<input type="hidden" name="idarchivo" value="<c:out value="${idArchivo}"/>"/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${view.mostrarCheckUsuariosSala}">
											<html:select property="idarchivo" styleClass="input">
												<html:options collection="archivos" property="key" labelProperty="value"/>
						           		    </html:select>
										</c:when>
										<c:otherwise>
											<html:select property="idarchivo" styleClass="input" onchange="javascript:actualizarArchivo();">
												<html:options collection="archivos" property="key" labelProperty="value"/>
						           		    </html:select>
										</c:otherwise>
									</c:choose>

								</c:otherwise>
							</c:choose>
	            		</td>
	                </tr>
				</c:otherwise>
			</c:choose>
		</table>

		<table class="formulario">
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.solicitudes.datosSolicitante" />
			</td>
		</tr>
	</table>

	<table class="formulario">
		<c:if test="${view.mostrarCampoOrgano}">
			<tr>
				<td width="50px">
					<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
				</td>
				<td width="150px" class="tdTitulo">
					<bean:message key="archigest.archivo.prestamos.norgsolicitante"/>:&nbsp;
				</td>
				<td class="tdDatos" colspan="3">
					<c:choose>
						<c:when test="${view.organoEditable}">
							<html:text property="norgconsultor" styleClass="input99" maxlength="254" styleId="norgconsultor"/>
						</c:when>
						<c:otherwise>
							<c:out value="${formBean.norgconsultor}"></c:out>
							<html:hidden property="norgconsultor" styleId="norgconsultor"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:if>

		<TR>
			<td width="50px">
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<TD width="150px" class="tdTitulo">
				<fmt:message key="${view.keySolicitante}"></fmt:message>
			</TD>
			<td class="tdDatos" colspan="3">
				<div id="solicitanteInvestigador">

						<c:choose>
							<c:when test="${view.mostrarSelectSolicitante}">
								<c:choose>
									<c:when test="${view.selectSolicitanteEditable}">
										<html:select property="idusrsolicitante" styleId="idusrsolicitante" onchange="javascript:actualizarDatosUsuario();">
											<html:options collection="usuariosConsulta" property="key" labelProperty="value" />
										</html:select>&nbsp;&nbsp;&nbsp;&nbsp;

										<c:if test="${view.mostrarFiltroUsuarios}">
											<span class="td2Titulo">
												<bean:message key="archigest.archivo.solicitudes.filtro" />:&nbsp;
											</span>
											<html:text name="ConsultaForm" styleId="filtroUsuario" maxlength="60" property="filtroUsuario" size="40"/>
											<a class="etiquetaAzul12Bold" href="javascript:filtarUsuarios();">
												<html:img page="/pages/images/searchDoc.gif" border="0" altKey="archigest.archivo.solicitudes.filtrar" titleKey="archigest.archivo.solicitudes.filtrar" styleClass="imgTextBottom" />
												&nbsp;<bean:message key="archigest.archivo.solicitudes.filtrar" />
											</a>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:out value="${formBean.nusrconsultor}"/><br/>
										<html:hidden property="idusrsolicitante" styleId="idusrsolicitante" />

									</c:otherwise>
								</c:choose>
								<html:hidden name="ConsultaForm" property="nusrconsultor" styleId="nusrconsultor" />
							</c:when>
							<c:when test="${view.mostrarCampoSolicitante}">
								<html:text name="ConsultaForm" property="nusrconsultor" maxlength="254" styleClass="input99" styleId="nusrconsultor"/>
							</c:when>
						</c:choose>
				</div>

			</td>
		</TR>
		<tr>
			<td width="50px">
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<td class="tdTitulo"  width="150px">
				<bean:message key="archigest.archivo.prestamos.telefonoSolicitante" />:&nbsp;
			</td>
			<td class="tdDatos" colspan="3">
				<html:text size="15" maxlength="15" property="telefonosolicitante" styleId="telefonosolicitante"/>
			</td>
		</tr>
		<tr>
			<td>
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.prestamos.faxSolicitante" />:&nbsp;
			</td>
			<td class="tdDatos" colspan="3">
				<html:text size="15" maxlength="15" property="faxsolicitante" styleId="faxsolicitante"/>
			</td>
		</tr>
		<tr>
			<td>
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.prestamos.emailSolicitante" />:&nbsp;
			</td>
			<td class="tdDatos" colspan="3">
				<html:text size="50" maxlength="50" property="emailsolicitante" styleId="emailsolicitante"/>
			</td>
		</tr>
		</table>
		<div class="separador5">&nbsp;</div>

		<table class="formulario">
		<TR>
			<TD class="tdTitulo" width="200px">
				<bean:message key="archigest.archivo.consultas.tema"/>:&nbsp;
			</TD>
			<TD id="Tema" class="tdDatos"
					<c:if test="${view.permitidoAddTemas && !view.mostrarBotonAddTema}">
						style="display:none"
					</c:if>
			 >
				<table cellpadding=0 cellspacing=0>
				<tr>
					<td>
						<html:select property="idTema">
							<html:option value=""></html:option>
							<html:options collection="temas" property="tema" labelProperty="tema" />
						</html:select>
					</td>
					<td width="5"></td>
					<td>
						<c:if test="${view.permitidoAddTemas}">
							<c:if test="${view.mostrarBotonAddTema}">
								<a class=etiquetaAzul12Bold href="javascript:addNuevoTema();">
									<html:img page="/pages/images/addDoc_small.gif" border="0" altKey="archigest.archivo.consultas.nuevoTema" titleKey="archigest.archivo.consultas.nuevoTema" styleClass="imgTextBottom" />
								</a>
							</c:if>
						</c:if>
					</td>
				</tr>
				</table>
			</TD>

			<c:if test="${view.permitidoAddTemas}">
				<TD id="TemaNew" class="tdDatos"
					<c:if test="${!view.mostrarCampoAddTema}">
						style="display:none"
					</c:if>


				>
					<table cellpadding=0 cellspacing=0 class="w100">
					<tr>
					<td width="90%">
						<html:text name="ConsultaForm" styleClass="input99" property="temaNuevo" styleId="temaNuevo" maxlength="254" />
					</td>
					<td width="22px">
						<a class=etiquetaAzul12Bold href="javascript:nuevoTema();">
							<html:img page="/pages/images/right.gif" border="0" altKey="archigest.archivo.consultas.anadirNuevoTema" titleKey="archigest.archivo.consultas.anadirNuevoTema" styleClass="imgTextBottom"/>
						</a>
					</td>
					<td>
						<a class=etiquetaAzul12Bold href="javascript:cancelAddNuevoTema();">
							<html:img page="/pages/images/wrong.gif" border="0" altKey="archigest.archivo.consultas.cancelarNuevoTema" titleKey="archigest.archivo.consultas.cancelarNuevoTema" styleClass="imgTextBottom" />
						</a>
					</td>
					</tr>
					</table>
				</TD>

			</c:if>
		</TR>
		<tr>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.consultas.motivo"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<html:select property="idMotivo" styleId="idMotivo">
					<html:option value=""></html:option>
					<html:options collection="motivos" property="id" labelProperty="motivo" />
				</html:select>
			</TD>
		</TR>
		</TABLE>

		<table class="formulario">
			<tr>
				<td class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.prestamos.autorizado" />:&nbsp;
				</td>
				<td class="tdDatos">
					<html:text name="ConsultaForm" styleId="datosautorizado" maxlength="254" property="datosautorizado" styleClass="input99" />
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.prestamos.tipoEntrega" />:&nbsp;
				</td>
				<td class="tdDatos">
					<html:select property="tipoentrega">
						<option value=""></option>
						<html:options collection="tiposEntrega" property="valor" labelProperty="valor" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.solicitudes.observaciones" />:&nbsp;
				</td>
				<td class="tdDatos">
					<html:text property="observaciones" styleClass="input99"  maxlength="254"/>
				</td>
			</tr>
		</table>

	<TABLE class="formulario">
		<TR>
			<TD class="tdTitulo" width="200px">
				<bean:message key="archigest.archivo.solicitudes.tienereserva"/>:
			</TD>
			<TD class="tdDatos">
				<html:checkbox property="tienereserva" styleId="tienereserva" styleClass="checkbox" onclick="javascript:tratarReserva();"/>
			</TD>
		</TR>
	</table>


	<table class="formulario"
	>
		<TR id="tr_reserva"
			<c:if test="${!view.mostrarDatosReserva}">
				style="display:none"
			</c:if>
		>
				<td width="200px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
				<td class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.consultas.finicialreserva"/>:&nbsp;&nbsp;&nbsp;
				</td>
				<td class="tdDatos">
					<html:text  size="12" maxlength="10" property="finicialreserva" styleId="finicialreserva"/>
					<archigest:calendar
						image="../images/calendar.gif"
						formId="ConsultaForm"
						componentId="finicialreserva"
						format="dd/mm/yyyy"
						enablePast="false" />
				</TD>
		</TR>

	</TABLE>

	</div> <%--bloque --%>
	<tiles:insert page="/pages/tiles/solicitudes/consultas/detalles_consultas_udoc_seleccionadas_busqueda.jsp" />
</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>


<h2><span>&nbsp;
</span></h2>
</html:form>
</div> <%--ficha --%>

</div> <%--contenedor_ficha --%>


