<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>
<c:set var="archivos" value="${sessionScope[appConstants.prestamos.LISTA_ARCHIVOS]}"/>
<c:set var="motivos" value="${sessionScope[appConstants.solicitudes.LISTA_MOTIVOS_KEY]}" />

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<div id="contenedor_ficha">

<html:form action="/gestionPrestamos">
<input type="hidden" name="method" value="guardaredicion"/>
<div class="ficha">

<h1><span>
	<div class="w100">
	<script>
		function aceptar() {
			if (document.forms[0].tienereserva.checked) {
				if (document.forms[0].finicialreserva.value == "") {
							alert("<bean:message key='errors.solicitudes.prestamos.fechaReservaNoIndicada'/>");
							return;
				}
			}
			document.forms[0].norgsolicitante.disabled = false;
			document.forms[0].nusrsolicitante.disabled = false;
			document.forms[0].submit();
		}

		function establecerSolicitanteInterno(){

			setValor("norgsolicitante",'<c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].norgsolicitante}"/>');

			if(!esVacio("norgsolicitante")){
				setHabilitado("norgsolicitante",false);
			}


			setValor("nusrsolicitante",'<c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].nusrsolicitante}"/>');

			if(!esVacio("nusrsolicitante")){
				setHabilitado("nusrsolicitante",false);
			}
		}

		function tratarReserva(){
			if (!document.forms[0].tienereserva.checked) {
				document.forms[0].finicialreserva.value = "";
				document.getElementById("tr_reserva").style.display = 'none';
			}else{
				document.getElementById("tr_reserva").style.display = 'block';
				if(!document.all){
					document.getElementById("tr_reserva").style.display='table-row';
				}
			}
		}

		function tratarSolicitante()
		{
			if (document.getElementById('opcionInterno').checked){
				document.getElementById("solicitanteInterno").style.display = 'block';
				if(!document.all){
						document.getElementById("solicitanteInterno").style.display='table-cell';
				}
				document.getElementById("textoSolicitanteInterno").style.display = 'none';
				document.forms[0].norgsolicitante.value = '';
				document.forms[0].nusrsolicitante.value = '';
				document.forms[0].tipoSolicitante.value = 'interno';
				deshabilitarSolicitante(true);
			}
			else
			{
				document.getElementById("solicitanteInterno").style.display  = 'none';
				document.getElementById("textoSolicitanteInterno").style.display = 'block';
				if(!document.all){
					document.getElementById("textoSolicitanteInterno").style.display='table-cell';
				}
				document.forms[0].norgsolicitante.value = '';
				document.forms[0].nusrsolicitante.value = '';

				//recoger datos de prstamo
				document.forms[0].norgsolicitante.value ='<c:out value="${bPrestamo.norgsolicitante}" />';
				document.forms[0].nusrsolicitante.value = '<c:out value="${bPrestamo.nusrsolicitante}" />';
				document.forms[0].tipoSolicitante.value = 'externo';
				document.forms[0].iduser.value = "";
				document.forms[0].idorg.value = "";

				deshabilitarSolicitante(false);
			}
		}


		function establecerUsuarioXDefecto() {
			<c:set var="usuarioSeleccionado" value="${sessionScope[appConstants.prestamos.ID_USUARIO_KEY]}"/>
			<c:set var="idOrganoUsuarioSeleccionado" value="${sessionScope[appConstants.prestamos.ORGANO_USUARIO_SELECCIONADO_KEY]}"/>
			<c:set var="nombreOrganoUsuarioSeleccionado" value="${sessionScope[appConstants.prestamos.NOMBRE_ORGANO_USUARIO_SELECCIONADO_KEY]}"/>
			<c:if test="${!empty usuarioSeleccionado}">
				document.forms[0].norgsolicitante.value='<c:out value="${nombreOrganoUsuarioSeleccionado}"/>';
			</c:if>
			<c:if test="${!empty usuarioSeleccionado}">
				document.forms[0].nusrsolicitante.value='<c:out value="${usuarioSeleccionado.nombreCompleto}"/>';
			</c:if>
			<c:if test="${!empty usuarioSeleccionado}">
				document.forms[0].iduser.value='<c:out value="${usuarioSeleccionado.id}"/>';
			</c:if>
			<c:if test="${!empty usuarioSeleccionado}">
				document.forms[0].idorg.value='<c:out value="${idOrganoUsuarioSeleccionado}"/>';
			</c:if>
		}

		function actualizaUsuario() {
			var cadena = document.forms[0].usuarioInterno.value;

			if (cadena.length>3)
			{
				var posicionPrimera=cadena.indexOf("#",0);
				var idUsr = cadena.substring(0,posicionPrimera);
				document.forms[0].iduser.value=idUsr;
			}
			else{
				document.forms[0].norgsolicitante.value="";
				document.forms[0].nusrsolicitante.value="";
				document.forms[0].iduser.value="";
				document.forms[0].idorg.value="";
			}

			document.forms[0].method.value="recargaEdicion";
			document.forms[0].submit();
		}

		function deshabilitarSolicitante(estado)
		{
			document.forms[0].norgsolicitante.disabled = estado;
			document.forms[0].nusrsolicitante.disabled = estado;
			if (estado=='true') {
				document.getElementById("textoSolicitanteInterno").style.display = 'none';
			}
		}

		function habilitarSolicitante()
		{
			document.forms[0].norgsolicitante.disabled = false;
			document.forms[0].nusrsolicitante.disabled = false;
			document.forms[0].norgsolicitante.enabled = true;
			document.forms[0].nusrsolicitante.enabled = true;
		}

		function cargarDatosUsuario(){
			document.forms[0].norgsolicitante.value="";
			document.forms[0].nusrsolicitante.value="";
			document.forms[0].iduser.value="";
			document.forms[0].method.value="recargaEdicion";
			document.forms[0].submit();
		}

	</script>
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	  <TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.prestamos.datosPrestamos"/>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
						<html:img page="/pages/images/Ok_Si.gif" border="0"
						altKey="archigest.archivo.aceptar"
						titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
			   <TD>
					<c:url var="cancelURL" value="/action/gestionPrestamos">
						<c:param name="method" value="goBack" />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false"/>'">
						<html:img page="/pages/images/Ok_No.gif" border="0"
						altKey="archigest.archivo.cancelar"
						titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
			   </TD>
			 </TR>
			</TABLE>
		</TR>
	</TABLE>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores"><archivo:errors/></DIV>

<DIV class="separador1">&nbsp;</DIV>

<DIV class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
	<jsp:include page="cuerpo_cabeceracte_prestamo.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="bloque"> <%--primer bloque de datos --%>

	<table class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
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
							<c:when test="${bPrestamo.tieneUnidadesDocumentales}">
								<c:out value="${bPrestamo.nombrearchivo}"/>
							</c:when>
							<c:otherwise>
								<html:select property="idarchivo" styleClass="input">
									<html:options collection="archivos" property="id" labelProperty="nombre" />
			           		    </html:select>
							</c:otherwise>
						</c:choose>
	           		</td>
	           </tr>
			</c:otherwise>
		</c:choose>
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
				<html:radio property="tipoSolicitante" styleId="opcionInterno" value="interno" styleClass="radio" onclick="javascript:tratarSolicitante(); establecerUsuarioXDefecto();" />
				<bean:message key="archigest.archivo.prestamos.interno" />
				<input type="hidden" name="iduser" value="">
				<input type="hidden" name="idorg" value="">
				<input type="hidden" name="tipoSolicitante" value="interno">
			</td>
		</tr>
		<TR>
			<td width="200px">
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<td  class="tdTitulo" width="150px">
				<bean:message key="archigest.archivo.prestamos.norgsolicitante"/>:&nbsp;
			</TD>
			<TD class="tdDatos" colspan="2">
				<html:text name="PrestamoForm" maxlength="254" property="norgsolicitante" styleClass="input99"/>
			</TD>
		</TR>
		<TR>
			<td>
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.prestamos.solicitante" />:&nbsp;
			</td>
			<td class="tdDatos" colspan="2">
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
		</TR>
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
		<tr>
			<td class="tdTitulo"  width="200px">
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

	<div class="separador5" >&nbsp;</div>

	<table class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
		<TR>
			<TD class="tdTitulo" width="200px" >
			<bean:message key="archigest.archivo.solicitudes.tienereserva"/>:

			</TD>
				<TD class="tdDatos">
					<%--hay que comprobar si hay reserva para checkear el control --%>
					<input type="checkbox" class="checkbox" name="tienereserva" id="tienereserva" onclick="javascript:tratarReserva()">
				</TD>
			</TR>
			<TR id="tr_reserva">
				<TD></TD>
				<TD  class="tdTitulo">
					<IMG src="../images/pixel.gif" class="imgTextMiddle" width="20" height="1"/>
					<bean:message key="archigest.archivo.prestamos.finicialreserva"/>:&nbsp;&nbsp;&nbsp;
					<html:text  size="14" maxlength="10" property="finicialreserva"  styleClass="input"/>
					<archigest:calendar
						image="../images/calendar.gif"
						formId="PrestamoForm"
						componentId="finicialreserva"
						format="dd/mm/yyyy"
						enablePast="false" />
				</TD>
		</TR>
	</table>

		<c:if test="${requestScope[appConstants.prestamos.VER_FECHA_INICIO_RESERVA]}">
			<script>
				document.getElementById('tienereserva').checked=true;
			</script>
		</c:if>

		<script>
			tratarReserva();
			<c:choose>
			<c:when test="${requestScope[appConstants.prestamos.ES_EXTERNO]}">
				habilitarSolicitante();
				document.getElementById('opcionExterno').checked=true;
				document.getElementById("solicitanteInterno").style.display  = 'none';
			</c:when>
			<c:otherwise>
				deshabilitarSolicitante('true');
			</c:otherwise>
			</c:choose>
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
					document.forms[0].idarchivo.disabled = true;
					xHide('filtroUsuarios');
				</script>
    	    </c:otherwise>
   	    </c:choose>


</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
<script>
var tipoSol = '<bean:write name="PrestamoForm" property="tipoSolicitante"/>';
if(tipoSol == 'interno')
	establecerUsuarioXDefecto();
</script>
</html:form>

</div> <%--contenedor_ficha --%>
