<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="bConsulta" value="${sessionScope[appConstants.consultas.CONSULTA_KEY]}"/>
<c:set var="archivos" value="${sessionScope[appConstants.consultas.LISTA_ARCHIVOS]}"/>
<c:set var="usuarioSeleccionado" value="${sessionScope[appConstants.consultas.ID_INVESTIGADOR]}"/>
<c:set var="organoUsuarioSeleccionado" value="${sessionScope[appConstants.consultas.ID_ORGANO_USUARIO_SELECCIONADO_KEY]}"/>
<c:set var="idUsuarioSeleccionado" value="${sessionScope[appConstants.consultas.ID_INVESTIGADOR]}"/>
<c:set var="esCiudadano" value="${requestScope[appConstants.consultas.CHECK_CIUDADANO]}"/>
<c:set var="esOrgano" value="${requestScope[appConstants.consultas.CHECK_ORGEXTERNO]}"/>
<c:set var="esInvestigador" value="${requestScope[appConstants.consultas.CHECK_INVESTIGADOR]}"/>
<c:set var="esInterno" value="${requestScope[appConstants.consultas.CHECK_INTERNO]}"/>
<c:set var="motivos" value="${sessionScope[appConstants.consultas.LISTA_MOTIVO_KEY]}"/>
<c:set var="temas" value="${sessionScope[appConstants.consultas.LISTA_TEMA_KEY]}"/>
<c:set var="tiposEntrega" value="${sessionScope[appConstants.consultas.LISTA_TIPOS_ENTREGA]}" />

<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<div id="contenedor_ficha">

<html:form action="/gestionConsultas">
<html:hidden property="id"/>
<input type="hidden" name="method" value="guardaredicion"/>

<div class="ficha">
<h1><span>
	<div class="w100">
	<script>	
		function aceptar() {
			if (document.forms[0].tienereserva.checked) {
				if (document.forms[0].finicialreserva.value == "") {
					alert("<bean:message key='archigest.archivo.solicitudes.msgIndicarFechaReserva'/>");
					return;
				}
			}
			setHabilitado("norgconsultor", true);
			setHabilitado("nusrconsultor", true);
			document.forms[0].submit();
		}
		
		function actualizarTemas(id) {
			document.forms[0].method.value = "loadTemasEdicion";
			document.forms[0].submit();
		}

		function actualizarUsuario() {
			var cadena = "";
			if (esChecked("opcionInvestigador"))      
				cadena = getValor("usuarioInvestigador");
					
			if (cadena.length>4) {
				var posicionPrimera=cadena.indexOf("#",0);
				var posicionSegunda=cadena.indexOf("#",posicionPrimera+1);
				var posicionTercera=cadena.indexOf("#",posicionSegunda+1);
				var posicionCuarta = cadena.indexOf("#",posicionTercera+1);
		
				var idUsr = cadena.substring(0,posicionPrimera);
				var idUsrsExtGestor = cadena.substring(posicionPrimera+1,posicionSegunda);
				var idUsrSistOrg = cadena.substring(posicionSegunda+1,posicionTercera);
				var nombre = cadena.substring(posicionTercera+1,posicionCuarta);
				var apellidos = cadena.substring(posicionCuarta+1,cadena.length);

				setValor("iduser", idUsr);
				setValor("idUsrsExtGestor", idUsrsExtGestor);
				setValor("idUsrSistOrg", idUsrSistOrg);
			}else{
				setValor("iduser","");
				setValor("idUsrsExtGestor","");
				setValor("idUsrSistOrg","");						
			}
		}
					
		function establecerUsuario() {			
			var cadena = "";
			if (esChecked("opcionInvestigador"))      
				cadena = getValor("usuarioInvestigador");					
			if (cadena.length>4) {
				document.forms[0].norgconsultor.value='<c:out value="${organoUsuarioSeleccionado}"/>';
				document.forms[0].nusrconsultor.value='<c:out value="${usuarioSeleccionado.nombreCompleto}"/>';
				document.forms[0].iduser.value='<c:out value="${usuarioSeleccionado.id}"/>';
				document.forms[0].idUsrsExtGestor.value='<c:out value="${usuarioSeleccionado.idUsrsExtGestor}"/>';
				document.forms[0].idUsrSistOrg.value='<c:out value="${usuarioSeleccionado.idUsrSistOrg}"/>';								
			}
		}
		
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
		
		function resetDatosSolicitante(){
			setVisible("solicitanteInvestigador", false);					
			setHabilitado("norgconsultor", true);
			setHabilitado("nusrconsultor", true);					
		}
					
		function establecerUsuarioXDefecto() {					
			var usuarioSel="<c:out value="${idUsuarioSeleccionado.id}#${idUsuarioSeleccionado.idUsrsExtGestor}#${idUsuarioSeleccionado.idUsrSistOrg}#${idUsuarioSeleccionado.nombre}#${idUsuarioSeleccionado.apellidos}"/>";

			if (esChecked("opcionInvestigador"))
				setValor("usuarioInvestigador", usuarioSel);					
		}
		
		function nuevoTema() {
			var opt = new Option(getValor("temaNuevo"));
			document.forms[0].idTema.options[document.forms[0].idTema.options.length] = opt;
			document.forms[0].idTema.options[document.forms[0].idTema.options.length-1].selected = true;
			show();					
		}
					
		function hide() {
			setVisible("TemaNew", true);
			setVisible("Tema", false);
		}
		
		function show() {
			setVisible("TemaNew", false);
			setVisible("Tema", true);
		}

		function cargarInvestigadores(){
			setValor("norgconsultor","");
			setValor("nusrconsultor","");
			setValor("iduser","");					
			document.forms[0].method.value = "cargarInvestigadores";
			document.forms[0].submit();
		}	
	</script>

	<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
    	<TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.consultas.datosconsultas"/>
		</TD>
		<TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			   	<TD>
					<a class="etiquetaAzul12Bold" href="javascript:javascript:aceptar();">
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
			   	<TD width="10">&nbsp;</TD>
			   	<TD>
					<c:url var="cancelURL" value="/action/gestionConsultas">
						<c:param name="method" value="goBack" />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false"/>'">
						<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
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

<DIV id="barra_errores"><archivo:errors/></DIV>

<DIV class="separador1">&nbsp;</DIV>

<DIV class="cabecero_bloque"> <%--primer bloque de datos (resumen siempre visible) --%>
	<jsp:include page="cuerpo_cabeceracte_consulta.jsp" flush="true" />
</DIV> 

<div class="bloque"> <%--primer bloque de datos --%>

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
							<c:when test="${bConsulta.tieneUnidadesDocumentales}">
								<c:out value="${bConsulta.nombrearchivo}"/>							
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
			<td width="200px">
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<td class="tdTitulo" width="150px">
				<bean:message key="archigest.archivo.consultas.tipoSolicitante"/>:&nbsp;
			</td>
			<td class="tdTitulo" colspan="3">
				<html:radio property="tipoentconsultora" value="2" styleId="opcionCiudadano" styleClass="radio" onclick="javascript:actualizarTemas();" />
				<bean:message key="archigest.archivo.consultas.ciudadano"/>
				<html:radio property="tipoentconsultora" value="3" styleId="opcionOrgano" styleClass="radio" onclick="javascript:actualizarTemas();" />
				<bean:message key="archigest.archivo.organo"/>
				<html:radio property="tipoentconsultora" value="1" styleId="opcionInvestigador" styleClass="radio" onclick="javascript:actualizarTemas();" />
				<bean:message key="archigest.archivo.consultas.investigador"/>
				
				<html:hidden property="iduser" value="" styleId="iduser"/>
				<html:hidden property="idorg" value="" styleId="idorg"/>
				<html:hidden property="tipo" value="" styleId="tipo"/>
				<html:hidden property="idUsrsExtGestor" value="" styleId="idUsrsExtGestor"/>
				<html:hidden property="idUsrSistOrg" value="" styleId="idUsrSistOrg"/>				
			</td>
		</tr>					
		  
		<c:if test="${!esCiudadano}"> 					
			<TR>
				<td width="200px">
					<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
				</td>
				<td width="150px" class="tdTitulo">
					<bean:message key="archigest.archivo.prestamos.norgsolicitante"/>:&nbsp;
				</td>
				<td class="tdDatos" colspan="3">
					<html:text property="norgconsultor" styleClass="input99" maxlength="254" styleId="norgconsultor"/>
				</td>
			</TR>			
		</c:if>
		
 		<c:if test="${esCiudadano}"> 					
			<html:hidden property="norgconsultor" styleId="norgconsultor"/>
		</c:if>

		<TR>
			<td width="200px">
				<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
			</td>
			<TD width="150px" class="tdTitulo">
				<c:if test="${esCiudadano}"> 					
					<bean:message key="archigest.archivo.prestamos.solicitante"/>:&nbsp;
				</c:if>
				<c:if test="${esOrgano}"> 					
					<bean:message key="archigest.archivo.consultas.representante"/>:&nbsp;
				</c:if>				
				<c:if test="${esInvestigador}"> 					
					<bean:message key="archigest.archivo.prestamos.solicitante"/>:&nbsp;
				</c:if>
			</TD>
			<td class="tdDatos" colspan="3">
					<div id="solicitanteInvestigador">
						<select id="usuarioInvestigador" name="usuarioInvestigador" onChange="javascript:resetDatosSolicitante(); actualizarUsuario(); actualizarTemas(2);">
							<c:forEach items="${sessionScope[appConstants.consultas.LISTA_USUARIOS_INVESTIGADORES_KEY]}" var="usuario" varStatus="status">
								<option  value="<c:out value="${usuario.id}#${usuario.idUsrsExtGestor}#${usuario.idUsrSistOrg}#${usuario.nombre}#${usuario.apellidos}"/>"
									<c:if test="${sessionScope[appConstants.consultas.ID_INVESTIGADOR].id==usuario.id}"> SELECTED </c:if>
									>
									<c:out value="${usuario.nombreCompleto}"/>
								</option>
							</c:forEach>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
						<span class="td2Titulo">
							<bean:message key="archigest.archivo.solicitudes.filtro" />:&nbsp;
						</span>
						<html:text name="ConsultaForm" styleId="filtroUsuario" maxlength="60" property="filtroUsuario" size="40"/>
						<a class="etiquetaAzul12Bold" href="javascript:cargarInvestigadores();">
							<html:img page="/pages/images/searchDoc.gif" border="0" altKey="archigest.archivo.solicitudes.filtrar" titleKey="archigest.archivo.solicitudes.filtrar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.solicitudes.filtrar" /> 
						</a>
					</div>
					<div id="textoSolicitanteInvestigador">
						<html:text name="ConsultaForm" property="nusrconsultor" styleClass="input99" maxlength="254" styleId="nusrconsultor"/>
					</div>
			</td>
		</TR>
		<tr>
				<td width="200px">
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
			<TD id="Tema" class="tdDatos">
				<table cellpadding=0 cellspacing=0>
				<tr>
				<td>
					<html:select property="idTema" styleId="idTema">
						<html:option value=""></html:option>
						<html:options collection="temas" property="tema" labelProperty="tema" />
					</html:select>
				</td>
				<td width="5"></td>
				<td>
					<c:set var="altKeyNuevoTema" value="archigest.archivo.consultas.nuevoTema"/>
					<img id="imgTema" src="<%=request.getContextPath()%>/pages/images/addDoc_small.gif" 
					onClick="javascript:hide();" class="imgTextBottom"
					alt="<fmt:message key='${altKeyNuevoTema}'/>" 
					title="<fmt:message key='${altKeyNuevoTema}'/>" >
				</td>
				</tr>
				</table>								
			</TD>	
			<TD id="TemaNew">
				<table cellpadding=0 cellspacing=0 class="w100">
				<tr>
				<td width="90%">
					<html:text name="ConsultaForm" styleClass="input99" styleId="temaNuevo" property="temaNuevo" maxlength="254"/>				
				</td>
				<td width="22px">
					<c:set var="altKeyAnadirNuevoTema" value="archigest.archivo.consultas.anadirNuevoTema"/>
					<img id="imgTemaAA" src="<%=request.getContextPath()%>/pages/images/right.gif" 
					onClick="javascript:nuevoTema();" 
					alt="<fmt:message key='${altKeyAnadirNuevoTema}'/>" 
					title="<fmt:message key='${altKeyAnadirNuevoTema}'/>" >
				</td>
				<td>
					<c:set var="altKeyCancelarNuevoTema" value="archigest.archivo.consultas.cancelarNuevoTema"/>
					<img id="imgTemaCancel" src="<%=request.getContextPath()%>/pages/images/wrong.gif" 
					onClick="javascript:show();" 
					alt="<fmt:message key='${altKeyCancelarNuevoTema}'/>" 
					title="<fmt:message key='${altKeyCancelarNuevoTema}'/>" >
				</td>
				</tr>
				</table>
			</TD>			
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.consultas.motivo"/>:&nbsp;
			</TD>
			<TD class="tdDatos">				
				<html:select property="idMotivo">
					<html:option value=""></html:option>
					<html:options collection="motivos" property="id" labelProperty="motivo" />
				</html:select>				
			</TD>
		</TR>
	</table>
	
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
				<html:text property="observaciones" styleClass="input99"  maxlength="254" styleId="observaciones"/>
			</td>
		</tr> 
	</table>	
	<div class="separador5" >&nbsp;</div>
	
	<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
		
		<TR>
			<TD class="tdTitulo" width="150px" >
				<bean:message key="archigest.archivo.solicitudes.tienereserva"/>:
			</TD>
			<TD class="tdDatos">
				<html:checkbox property="tienereserva" styleId="tienereserva" styleClass="checkbox" onclick="javascript:tratarReserva();"/>
			</TD>
			</TR>
				<TR id="tr_reserva">
				<TD></TD>
				<TD class="tdTitulo">
					<IMG src="../images/pixel.gif" class="imgTextMiddle" width="20" height="1"/>
					<bean:message key="archigest.archivo.consultas.finicialreserva"/>:&nbsp;&nbsp;&nbsp;
						<html:text  size="14" maxlength="10" property="finicialreserva"  styleClass="input" styleId="finicialreserva"/>
						<archigest:calendar 
							image="../images/calendar.gif"
							formId="ConsultaForm" 
							componentId="finicialreserva" 
							format="dd/mm/yyyy" 
							enablePast="false" />
					</TD>
				</TR>
		
	</TABLE>

	<c:if test="${requestScope[appConstants.consultas.VER_FECHA_INICIO_RESERVA]}">
		<script> 
			document.getElementById('tienereserva').checked=true;
		</script>
	</c:if>
	<script>

		tratarReserva();
		
		resetDatosSolicitante();
		
		setVisible("imgTema", false);
		show();
		if(document.getElementById('finicialreserva').value != '')
			document.getElementById('tienereserva').checked=true;
		
		<c:if test="${esInvestigador}">
			setVisible("solicitanteInvestigador", true);
			setVisible("textoSolicitanteInvestigador", false);
			setVisible("imgTema", true);	
			setHabilitado("norgconsultor", false);
			establecerUsuario();								
		</c:if>
		<c:if test="${esInterno}">	
			document.getElementById("tr_usuario").style.display = 'none';			
			document.forms[0].norgconsultor.value = '<c:out value="${sessionScope[appConstants.consultas.ID_ORGANO_USUARIO_SELECCIONADO_KEY]}"/>';
			document.forms[0].norgconsultor.disabled = true;
			document.forms[0].usuarioInvestigador.disabled = true;	
			document.forms[0].nusrconsultor.value = '<c:out value="${sessionScope[appConstants.consultas.ID_INVESTIGADOR].nombreCompleto}"/>';				
			document.forms[0].iduser.value = '<c:out value="${sessionScope[appConstants.consultas.CONSULTA_KEY].idusrsolicitante}"/>';		
		</c:if>		
	</script> 
</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>