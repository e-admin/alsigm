<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js" />

<div id="contenedor_ficha">
<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}" />
<c:set var="user" value="${sessionScope[appConstants.prestamos.ID_USUARIO_KEY]}" />
<c:set var="archivos" value="${sessionScope[appConstants.prestamos.LISTA_ARCHIVOS]}" />
<c:set var="motivos" value="${sessionScope[appConstants.solicitudes.LISTA_MOTIVOS_KEY]}" />
<c:set var="unidadesDocumentales" value="${sessionScope[appConstants.solicitudes.LISTA_DETALLES_UDOCS_SOLICITUD_KEY]}" />
<c:set var="archivo" value="${sessionScope[appConstants.solicitudes.ARCHIVO_SOLICITUD_KEY]}" />

<c:if test="${archivo != null}">
	<c:set var="idArchivo" value="${archivo.id}" />
	<c:set var="nombreArchivo" value="${archivo.nombre}" />
</c:if>

<script>

		function aceptar() {
			if(esChecked("tienereserva")){
				if(esVacio("finicialreserva")){
					alert("<bean:message key='errors.solicitudes.prestamos.fechaReservaNoIndicada'/>");
					return;
				}
			}
			setHabilitado("norgsolicitante",true);
			setHabilitado("nusrsolicitante",true);
			enviarFormulario("formulario", "crear" );

		}

		function establecerSolicitanteInterno(){
			setValor("norgsolicitante",'<c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].norgsolicitante}"/>');
			if(!esVacio("norgsolicitante"))
				setHabilitado("norgsolicitante",false);

			setValor("nusrsolicitante",'<c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].nusrsolicitante}"/>');

			if(!esVacio("nusrsolicitante"))
				setHabilitado("nusrsolicitante",false);
		}

		function tratarReserva(){
			if (!document.forms[0].tienereserva.checked) {
				document.forms[0].finicialreserva.value = "";
				document.getElementById("tr_reserva").style.display = 'none';
			}else{
				document.getElementById("tr_reserva").style.display = 'block';
				if(!document.all)
					document.getElementById("tr_reserva").style.display='table-row';
			}
		}
		function tratarUsuario(){
			if(esVacio("norgsolicitante") && esChecked("opcionInterno"))
				setVisible("tr_usuario",false);
			else{
				document.getElementById("tr_usuario").style.display = 'block';
				if(!document.all)
					document.getElementById("tr_usuario").style.display='table-row';
			}
		}
		function tratarSolicitante(){
			if(esChecked("opcionInterno")){
				setVisible("solicitanteInterno",true);
				setVisible("textoSolicitanteInterno",false);
				setValor("norgsolicitante","");
				setValor("nusrsolicitante","");
				deshabilitarSolicitante(true);
				establecerUsuarioXDefecto();
			}
			else{
				setVisible("solicitanteInterno",false);
				setVisible("textoSolicitanteInterno",true);
				setValor("norgsolicitante","");
				setValor("nusrsolicitante","");
				setValor("iduser","");
				setValor("idorg","");
				deshabilitarSolicitante(false);
			}
			document.forms[0].method.value = "loadMotivos";
			document.forms[0].submit();
		}

		function establecerUsuarioXDefecto() {
			<c:set var="usuarioSeleccionado" value="${sessionScope[appConstants.prestamos.ID_USUARIO_KEY]}"/>
			<c:set var="idOrganoUsuarioSeleccionado" value="${sessionScope[appConstants.prestamos.ORGANO_USUARIO_SELECCIONADO_KEY]}"/>
			<c:set var="nombreOrganoUsuarioSeleccionado" value="${sessionScope[appConstants.prestamos.NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY]}"/>

			setValor("norgsolicitante",'<c:out value="${nombreOrganoUsuarioSeleccionado}"/>');
			setValor("nusrsolicitante",'<c:out value="${usuarioSeleccionado.nombreCompleto}"/>');
			setValor("iduser",'<c:out value="${usuarioSeleccionado.id}"/>');
			setValor("idorg",'<c:out value="${idOrganoUsuarioSeleccionado}"/>');
		}

		//creo q solo hace falta guardar el id el resto se pone al recargar la pagina
		function actualizaUsuario() {
			var cadena = getValor("usuarioInterno");
			var partes = cadena.split('#');
			var codigo="";

			if(partes.length > 1){
				codigo = partes[0];
				setValor("iduser",codigo);
				enviarFormulario("formulario","cargarDatosUsuario");
			}
			enviarFormulario("formulario","cargarDatosUsuario");
		}

		function deshabilitarSolicitante(estado){
			setHabilitado("norgsolicitante",!estado);
			setHabilitado("nusrsolicitante",!estado);
			if(estado)
				setVisible("textoSolicitanteInterno",false);
		}

		function cargarDatosUsuario(){
			setValor("norgsolicitante","");
			setValor("nusrsolicitante","");
			setValor("iduser","");
			enviarFormulario("formulario","cargarDatosUsuario");
		}

	</script>

<html:form action="/gestionPrestamos" styleId="formulario">
	<input type="hidden" name="method" id="method" value="crear" />
	<input type="hidden" name="iduser" id="iduser" value="">
	<input type="hidden" name="idorg" id="idorg" value="">

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
				<tr>
					<td class="etiquetaAzul12Bold" height="25px">
						<bean:message key="archigest.archivo.prestamos.creacion" />
					</td>
				<td align="right">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td><a class="etiquetaAzul12Bold" href="javascript:aceptar()">
									<html:img page="/pages/images/Ok_Si.gif" border="0"	altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
									&nbsp;<bean:message key="archigest.archivo.aceptar" />
								</a>
							</td>
							<td width="10">&nbsp;</td>
							<td>
								<c:url var="cancelURL" value="/action/gestionPrestamos">
								<c:param name="method" value="goBack" />
								</c:url>
									<a class="etiquetaAzul12Bold" href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false"/>'">
										<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
										&nbsp;<bean:message key="archigest.archivo.cancelar" />
									</a>
							</td>

							<c:if test="${appConstants.configConstants.mostrarAyuda}">

								<td width="10">&nbsp;</td>
								<td>
									<c:set var="requestURI"	value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
									<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
									<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/prestamos/CrearPrestamo.htm');">
										<html:img page="/pages/images/help.gif" altKey="archigest.archivo.ayuda" titleKey="archigest.archivo.ayuda" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.ayuda" />
									</a>
								</td>
							</c:if>
						</tr>
					</table>
				</td>
			</tr>
			</table>
		</div>
	</span>
	</h1>

	<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
			<div id="barra_errores">
				<archivo:errors />
			</div>
			<div class="bloque"><%--primer bloque de datos --%>
				<table class="formulario">
					<%--para aspecto de formulario con lineas bottom de celda --%>
					<tr>
						<td class="tdTitulo"  width="200px">
							<bean:message key="archigest.archivo.prestamos.archivo" />:&nbsp;
						</td>
						<td class="tdDatos">

						<c:choose>
							<c:when test="${not empty unidadesDocumentales }">
								<input type="hidden" name="fromBusqueda" value='<c:out value="${bPrestamo.fromBusqueda}"/>'/>
								<c:out value="${nombreArchivo}"/>
								<input type="hidden" name="idarchivo" value="<c:out value="${idArchivo}"/>"/>
							</c:when>
							<c:otherwise>
								<html:select property="idarchivo" styleClass="input">
									<html:options collection="archivos" property="id" labelProperty="nombre" />
			           		    </html:select>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
			   </table>
			   <div class="separador5">&nbsp;</div>
 			   <table class="formulario">
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.solicitudes.datosSolicitante" />
						</td>
					</tr>
				</table>

				<table class="formulario">
					<tr id="tr_usuario">
						<td width="50px">
							<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
						</td>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.prestamos.tipoSolicitante" />:&nbsp;
						</td>
						<td class="tdTitulo" colspan="2">
							<html:radio property="tipoSolicitante" value="externo" styleId="opcionExterno" styleClass="radio" onclick="javascript:tratarSolicitante()" />
							<bean:message key="archigest.archivo.prestamos.externo" />
							<html:radio property="tipoSolicitante" styleId="opcionInterno" value="interno" styleClass="radio" onclick="javascript:tratarSolicitante()" />
							<bean:message key="archigest.archivo.prestamos.interno" />
						</td>
					</tr>
					<tr>
						<td width="200px">
							<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
						</td>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.prestamos.norgsolicitante" />:&nbsp;
						</td>
						<td class="tdDatos" colspan="2">
							<html:text name="PrestamoForm" maxlength="254" styleId="norgsolicitante" property="norgsolicitante" styleClass="input99"  />
						</td>
					</tr>
				<tr>
					<td>
						<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
					</td>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.prestamos.solicitante" />:&nbsp;
					</td>
					<td class="tdDatos" colspan="3">
						<div id="solicitanteInterno">
							<select id="usuarioInterno" name="usuarioInterno" onChange="javascript:actualizaUsuario();" class="input">
							<c:forEach	items="${sessionScope[appConstants.prestamos.LISTA_USUARIOS_KEY]}"	var="usuario" varStatus="status">
								<option value="<c:out value="${usuario.id}#${usuario.nombre}#${usuario.apellidos}"/>"
								<c:if test="${sessionScope[appConstants.prestamos.ID_USUARIO_KEY].id==usuario.id}"> SELECTED </c:if>
								>
								<c:out value="${usuario.nombreCompleto}" /></option>
							</c:forEach>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;
							<span id="filtroUsuarios">
							<span class="td2Titulo">
								<bean:message key="archigest.archivo.solicitudes.filtro" />:&nbsp;
							</span>
							<html:text name="PrestamoForm" styleId="filtroUsuario" maxlength="60" property="filtroUsuario" size="40"/>
							<a class="etiquetaAzul12Bold" href="javascript:cargarDatosUsuario();">
								<html:img page="/pages/images/searchDoc.gif" border="0" altKey="archigest.archivo.solicitudes.filtrar" titleKey="archigest.archivo.solicitudes.filtrar" styleClass="imgTextBottom" />
								&nbsp;<bean:message key="archigest.archivo.solicitudes.filtrar" />
							</a>
							</span>
						</div>
						<div id="textoSolicitanteInterno">
							<html:text name="PrestamoForm" styleId="nusrsolicitante" maxlength="254" property="nusrsolicitante" styleClass="input99" />
						</div>
					</td>
				</tr>

				<tr>
					<td>
						<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
					</td>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.prestamos.telefonoSolicitante" />:&nbsp;
					</td>
					<td class="tdDatos" colspan="2">
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
					<td class="tdDatos" colspan="2">
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
					<td class="tdDatos" colspan="2">
						<html:text size="50" maxlength="50" property="emailsolicitante" styleId="emailsolicitante"/>
					</td>
				</tr>
			</table>
			<div class="separador5">&nbsp;</div>
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.consultas.motivo"/>:&nbsp;
					</td>
					<TD class="tdDatos">
						<html:select property="idmotivo">
							<html:option value=""></html:option>
							<html:options collection="motivos" property="id" labelProperty="motivo" />
						</html:select>
					</TD>
				</tr>
			</table>
			<div class="separador5">&nbsp;</div>
			<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.prestamos.autorizado" />:&nbsp;
					</td>
					<td class="tdDatos">
						<html:text name="PrestamoForm" styleId="datosautorizado" maxlength="254" property="datosautorizado" styleClass="input99" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.prestamos.tipoEntrega" />:&nbsp;
					</td>
					<td class="tdDatos">
						<c:set var="tiposEntrega" value="${sessionScope[appConstants.prestamos.LISTA_TIPOS_ENTREGA]}" />
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

				<div class="separador5">&nbsp;</div>
				<table class="formulario">
					<%--para aspecto de formulario con lineas bottom de celda --%>
					<tr>
						<td class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.solicitudes.tienereserva" />:
						</td>
						<td class="tdDatos">
							<html:checkbox styleClass="checkbox" property="tienereserva" styleId="tienereserva" onclick="javascript:tratarReserva()" />
						</td>
					</tr>
				</table>
				<table class="formulario">
					<tr id="tr_reserva">
						<td width="200px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.prestamos.finicialreserva" />:&nbsp;&nbsp;&nbsp;
						</td>
						<td class="tdDatos">
								<html:text size="14" maxlength="10" property="finicialreserva" styleId="finicialreserva" />
								<archigest:calendar	image="../images/calendar.gif" formId="PrestamoForm" componentId="finicialreserva" format="dd/mm/yyyy" enablePast="false" />
						</td>
					</tr>
				</table>
			</div><!--bloque -->
			<tiles:insert page="/pages/tiles/solicitudes/prestamos/detalle_prestamos_udocs_seleccionadas_busqueda.jsp" />
		</div><%--cuerpo_drcha --%>
	</div><%--cuerpo_izda --%>
	<h2><span>&nbsp; </span></h2>
</div><%--ficha --%>
</html:form>
<script>
	var tipoSol = '<bean:write name="PrestamoForm" property="tipoSolicitante"/>';
	if(tipoSol == 'interno'){
		establecerSolicitanteInterno();
		//tratarUsuario();
		tratarReserva();
		deshabilitarSolicitante(true);
		establecerUsuarioXDefecto();
	}
	else{
		setVisible("solicitanteInterno",false);
		setVisible("textoSolicitanteInterno",true);
		tratarReserva();
		deshabilitarSolicitante(false);
	}
</script>
	<c:choose>
		<c:when test="${requestScope[appConstants.prestamos.VER_SELECT]}">
			<script>
				if(document.all) document.getElementById("tr_usuario").style.display  = 'inline';
				else document.getElementById("tr_usuario").style.display  = 'table-row';
			</script>
		</c:when>
		<c:otherwise>
			<script>
				document.getElementById("tr_usuario").style.display  = 'none';
				document.forms[0].usuarioInterno.disabled = true;
				xHide('filtroUsuarios');
			</script>
    	</c:otherwise>
	</c:choose>
</div><%--contenedor_ficha --%>