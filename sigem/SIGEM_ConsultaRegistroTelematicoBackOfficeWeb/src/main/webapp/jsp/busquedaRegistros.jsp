<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.core.services.telematico.Registro"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ieci.tecdoc.sgm.consulta_telematico.form.BusquedaRegistrosForm"%>
<%@page import="ieci.tecdoc.sgm.consulta_telematico.utils.Defs"%>
<%@page import="ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta"%>
<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>



<html:html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title><bean:message key="titulo.aplicacion"/></title>
		<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
		<![endif]-->

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
		<![endif]-->

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
		<![endif]-->

		<link rel="stylesheet" type="text/css" media="all" href="calendario/skins/aqua/theme.css" title="Aqua" />


		<!-- import the calendar script -->
		<script type="text/javascript" src="calendario/calendar.js"></script>
		<script type="text/javascript" src="calendario/lang/calendar-es.js"></script>
		<script type="text/javascript" src="calendario/calendario.js"></script>
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
		<script type="text/javascript" language="javascript" src="js/utilsTabla.js"></script>

		<script language="javascript">

			var oldLink = null;

			function limpiarFormulario() {

				var fd = document.getElementById('fechaDesde');
				var fhs = document.getElementById('fechaHasta');
				var fh = document.getElementById('fechaH');
				var op = document.getElementById('operadorFechas');

				var fde = document.getElementById('fechaEfectivaDesde');
				var fhse = document.getElementById('fechaEfectivaHasta');
				var fhe = document.getElementById('fechaHEfectiva');
				var ope = document.getElementById('operadorFechasEfectivas');

				var numReg = document.getElementById('numeroRegistro');
				var opr = document.getElementById('operadorNumeroRegistro');

				var asunto = document.getElementById('asunto');

				var cnifUsuario = document.getElementById('cnifUsuario');

		  		fd.value="";
		  		fhs.value="";
				fh.style.visibility = "hidden";
				op.selectedIndex=0;

				fde.value="";
		  		fhse.value="";
				fhe.style.visibility = "hidden";
				ope.selectedIndex=0;
				numReg.value="";
				opr.selectedIndex=0;

				asunto.selectedIndex=0;

				cnifUsuario.value="";
			}

			function mostrarHasta() {

		  		var seleccion = document.getElementById('operadorFechas');
				var indice = seleccion.selectedIndex ;

		  		var fh = document.getElementById('fechaH');

			  	if (seleccion.options[indice].value == 'entre'){
		  			fh.style.visibility = "visible";
		  		} else {
		  			var fhs = document.getElementById('fechaHasta');
		  			fhs.value = "";
		  			fh.style.visibility= "hidden";

		  			if (seleccion.options[indice].value == '') {
		  				var fd = document.getElementById('fechaDesde');
		  				fd.value = "";
		  			}
		  		}
	  		}

			function mostrarHastaEfectiva() {

		  		var seleccion = document.getElementById('operadorFechasEfectivas');
				var indice = seleccion.selectedIndex ;

		  		var fh = document.getElementById('fechaHEfectiva');

			  	if (seleccion.options[indice].value == 'entre'){
		  			fh.style.visibility = "visible";
		  		} else {
		  			var fhse = document.getElementById('fechaEfectivaHasta');
		  			fhse.value = "";
		  			fh.style.visibility= "hidden";

		  			if (seleccion.options[indice].value == '') {
		  				var fde = document.getElementById('fechaEfectivaDesde');
		  				fde.value = "";
		  			}
		  		}
	  		}

			function validarFormulario() {

				var campoFechaDesde = document.forms[0].fechaDesde;
				var campoFechaHasta = document.forms[0].fechaHasta;
				var campoOperadorFechas = document.forms[0].operadorFechas;
				var campoFechaEfectivaDesde = document.forms[0].fechaEfectivaDesde;
				var campoFechaEfectivaHasta = document.forms[0].fechaEfectivaHasta;
				var campoOperadorFechasEfectivas = document.forms[0].operadorFechasEfectivas;
				var campoNumeroRegistro = document.forms[0].numeroRegistro;
				var campoOperadorNumeroRegistro = document.forms[0].operadorNumeroRegistro;
				var campoAsunto = document.forms[0].asunto;
				var campoCnifUsuario = document.forms[0].cnifUsuario;

				if (campoOperadorFechas.value == "" && (campoFechaHasta.value != "" || campoFechaDesde.value != "")) {
					alert("<bean:message key="mensaje.error.busqueda.tipoFecha"/>");
					return false;
				}  else if (campoOperadorFechas.value == "entre" && campoFechaHasta.value == "") {
					alert("<bean:message key="mensaje.error.busqueda.campoFechaHasta"/>");
					return false;
				} else if(campoOperadorFechas.value != "" && campoFechaDesde.value == "") {
					alert("<bean:message key="mensaje.error.busqueda.formatoFechaIncorrecto"/>");
					return false;
				} else if(campoOperadorFechas.value == "entre" && fechaAnterior(campoFechaDesde.value, campoFechaHasta.value) == false){
					alert("<bean:message key="mensaje.error.busqueda.finalMenor"/>");
					return false;
				}
				else if (campoOperadorFechasEfectivas.value == "" && (campoFechaEfectivaHasta.value != "" || campoFechaEfectivaDesde.value != "")) {
					alert("<bean:message key="mensaje.error.busqueda.tipoFecha"/>");
					return false;
				}  else if (campoOperadorFechasEfectivas.value == "entre" && campoFechaEfectivaHasta.value == "") {
					alert("<bean:message key="mensaje.error.busqueda.campoFechaHasta"/>");
					return false;
				} else if(campoOperadorFechasEfectivas.value != "" && campoFechaEfectivaDesde.value == "") {
					alert("<bean:message key="mensaje.error.busqueda.formatoFechaIncorrecto"/>");
					return false;
				} else if(campoOperadorFechasEfectivas.value == "entre" && fechaAnterior(campoFechaEfectivaDesde.value, campoFechaEfectivaHasta.value) == false){
					alert("<bean:message key="mensaje.error.busqueda.finalMenor"/>");
					return false;
				}
				else if(campoOperadorNumeroRegistro.value != "" && campoNumeroRegistro.value == ""){
					alert("<bean:message key="mensaje.error.busqueda.numeroRegistro"/>");
					return false;
				} else if(campoOperadorNumeroRegistro.value == "" && campoNumeroRegistro.value != ""){
					alert("<bean:message key="mensaje.error.busqueda.operadorNumeroRegistro"/>");
					return false;
				} else if (campoCnifUsuario.value == "" && campoAsunto.value == "" && campoOperadorNumeroRegistro.value == "" && (campoOperadorFechas.value == "" || (campoOperadorFechas.value != "" && campoFechaDesde.value == "")) && (campoOperadorFechasEfectivas.value == "" || (campoOperadorFechasEfectivas.value != "" && campoFechaEfectivaDesde.value == ""))){
					alert("<bean:message key="mensaje.error.busqueda.rellenarCampos"/>");
					return false;
				}
			}

			function fechaAnterior(fechaInicio, fechaFin) {

				if (fechaInicio == "" || fechaFin == "")
					return false;

				var iDia = fechaInicio.substr(0,2);
				var iMes = fechaInicio.substr(3,2);
				var iAnio = fechaInicio.substr(6,4);
				var fDia = fechaFin.substr(0,2);
				var fMes = fechaFin.substr(3,2);
				var fAnio = fechaFin.substr(6,4);

				var iFecha = iAnio + iMes + iDia;
				var fFecha = fAnio + fMes + fDia;

				if (iFecha > fFecha)
					return false;
				return true;
			}

			function mostrarRegistro(numeroRegistro, asunto, fecha, fechaEfectiva, destino,descripcion) {

				var iframe = document.getElementById("detalleRegistro");
				iframe.style.height=size() - 180 + "px";
				iframe.style.width="880px";
				iframe.style.left="50%";
				iframe.style.top="130px";
				iframe.style.marginLeft="-440px";
				iframe.style.visibility="visible";
					iframe.src="detalleRegistro.do?<%=Defs.NUMERO_REGISTRO%>=" + numeroRegistro + "&<%=Defs.DESCRIPCION%>=" + descripcion + "&<%=Defs.FECHA_REGISTRO%>=" + fecha + "&<%=Defs.FECHA_EFECTIVA%>=" + fechaEfectiva+ "&<%=Defs.ADDREESSE%>=" + destino+ "&<%=Defs.ASUNTO%>=" + asunto;
				//window.scroll();
			}

			function mostrarListado() {

				var iframe = document.getElementById("detalleRegistro");
				iframe.style.height="0px";
				iframe.style.width="0px";
				iframe.style.left="0px";
				iframe.style.top="0px";
				iframe.style.marginLeft="0px";
				iframe.style.marginTop="0px";
				iframe.style.visibility="hidden";
				iframe.src="jsp/blank.html";
			}

			function size() {

	 			var tamanyo = 0;
				if (typeof window.innerWidth != 'undefined') {
					tamanyo = window.innerHeight;
				} else if (typeof document.documentElement != 'undefined' && typeof document.documentElement.clientWidth != 'undefined' && document.documentElement.clientWidth != 0) {
					tamanyo = document.documentElement.clientHeight;
				} else {
					tamanyo = document.getElementsByTagName('body')[0].clientHeight;
				}
				return tamanyo;
			}

		</script>
	</head>

	<body>

	<div id="contenedora">
		<jsp:include flush="true" page="cabecera.jsp"></jsp:include>

		<div class="centered">
		<div class="contenedor_centrado">


			<div class="cuerpo">
      			<div class="cuerporight">
        			<div class="cuerpomid">

				<h1><bean:message key="listado.titulo"/></h1>

				<div class="tabs_submenus">
					<ul>
						<li class="subOn">
							<h3><a><bean:message key="menu.busquedaRegistros"/></a></h3>
						</li>
					</ul>
				</div>

				<div class="cuerpo_subs clearfix" >

					<div class="seccion">
						<html:messages id="error">
							 <label class="error_rojo"><bean:write name="error"/></label>
						</html:messages>

						<%if(request.getAttribute("MENSAJE_ERROR") != null){%>
							<label class="error_rojo"><%=request.getAttribute("MENSAJE_ERROR")%></label>
						<%}%>

						<html:form styleId="busquedaRegistrosBean" action="/busquedaRegistros.do" method="post" styleClass="busquedaRegistrosBean">


						<div class="cuerpo_sec fila_sub clearfix">
							<ul>
								<li>
									<input type="button" value='<bean:message key="boton.limpiar"/>' id="limpiar_busqueda" class="ok" onclick="javascript:limpiarFormulario();"/>
								</li>
								<li>
									<input type="submit" value='<bean:message key="boton.buscar"/>' onClick="javascript: return validarFormulario();" id="busqueda_expedientes" class="ok" />
								</li>
							</ul>
						</div>

						<p class="fila_sub">
			                		<label class="sml"><bean:message key="busqueda.cnif"/>: </label>
							<html:text property="cnifUsuario" styleClass="sml"></html:text>

						</p>
						<p class="fila_sub">
				             		<%String op = ((BusquedaRegistrosForm)request.getAttribute("busquedaRegistrosBean")).getOperadorFechas(); %>
				                	<label for="fecha" class="sml"><bean:message key="busqueda.fechaRegistro"/>: </label>
							<select name="operadorFechas" id="operadorFechas" class="single" onChange="javascript:mostrarHasta()">
								<option value="" selected></option>
								<option value="=" <%=("=".equals(op))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.igual"/></option>
								<option value="entre" <%=("entre".equals(op))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.entre"/></option>
								<option value=">" <%=(">".equals(op))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.mayor_que"/></option>
								<option value="<" <%=("<".equals(op))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.menor_que"/></option>
							</select>

							<%String fd = (String)session.getAttribute("fechaDesdeBusqueda"); %>
							<html:text property="fechaDesde" styleId="fechaDesde" styleClass="sml disabled" maxlength="10" readonly="true"></html:text><img src="img/<%=rutaImagenes%>calendario.bmp" onclick="return showCalendar('fechaDesde', '%d-%m-%Y');" style="vertical-align: bottom; cursor: hand;" /><span name="Desde" id="f"></span>

							<%String fh = (String)session.getAttribute("fechaHastaBusqueda"); %>
							<span id="fechaH" style="visibility:hidden">
								<label class="frase xsml"><bean:message key="busqueda.fechaRegistro.hasta"/>:</label>
								<html:text property="fechaHasta" styleId="fechaHasta" styleClass="sml disabled" maxlength="10" readonly="true"></html:text><img id="botonFechaHasta" src="img/<%=rutaImagenes%>calendario.bmp" onclick="return showCalendar('fechaHasta', '%d-%m-%Y');" style="vertical-align: bottom; cursor: hand;" /><span name="Desde" id="f"></span>
							</span>
										<!-- name de los html:text??-->
						</p>



						<p class="fila_sub">
				             		<%String ope = ((BusquedaRegistrosForm)request.getAttribute("busquedaRegistrosBean")).getOperadorFechasEfectivas(); %>
				           <label for="fecha" class="sml"><bean:message key="busqueda.fechaEfectiva"/>: </label>
							<select name="operadorFechasEfectivas" id="operadorFechasEfectivas" class="single" onChange="javascript:mostrarHastaEfectiva()">
								<option value="" selected></option>
								<option value="=" <%=("=".equals(ope))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.igual"/></option>
								<option value="entre" <%=("entre".equals(ope))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.entre"/></option>
								<option value=">" <%=(">".equals(ope))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.mayor_que"/></option>
								<option value="<" <%=("<".equals(ope))?"selected":""%>><bean:message key="busqueda.fechaRegistro.operador.menor_que"/></option>
							</select>


							<html:text property="fechaEfectivaDesde" styleId="fechaEfectivaDesde" styleClass="sml disabled" maxlength="10" readonly="true"></html:text><img src="img/<%=rutaImagenes%>calendario.bmp" onclick="return showCalendar('fechaEfectivaDesde', '%d-%m-%Y');" style="vertical-align: bottom; cursor: hand;" /><span name="Desde" id="fe"></span>


							<span id="fechaHEfectiva" style="visibility:hidden">
								<label class="frase xsml"><bean:message key="busqueda.fechaRegistro.hasta"/>:</label>
								<html:text property="fechaEfectivaHasta" styleId="fechaEfectivaHasta" styleClass="sml disabled" maxlength="10" readonly="true"></html:text><img id="botonFechaHasta" src="img/<%=rutaImagenes%>calendario.bmp" onclick="return showCalendar('fechaEfectivaHasta', '%d-%m-%Y');" style="vertical-align: bottom; cursor: hand;" /><span name="Desde" id="fe"></span>
							</span>
										<!-- name de los html:text??-->
						</p>




						<p class="fila_sub">
					             	<%String opr = ((BusquedaRegistrosForm)request.getAttribute("busquedaRegistrosBean")).getOperadorNumeroRegistro(); %>
		                			<label class="sml"><bean:message key="busqueda.numeroRegistro"/>: </label>
			                		<html:select styleId="operadorNumeroRegistro" property="operadorNumeroRegistro" styleClass="single">
								<option value="" selected></option>
								<option value="=" <%=("=".equals(opr))?"selected":""%>><bean:message key="busqueda.numeroRegistro.operador.igual"/></option>
								<option value=">" <%=(">".equals(opr))?"selected":""%>><bean:message key="busqueda.numeroRegistro.operador.mayor_que"/></option>
								<option value="<" <%=("<".equals(opr))?"selected":""%>><bean:message key="busqueda.numeroRegistro.operador.menor_que"/></option>
								<option value="<%=RegistroConsulta.CONTIENE%>" <%=(RegistroConsulta.CONTIENE.equals(opr))?"selected":""%>><bean:message key="busqueda.numeroRegistro.operador.contiene"/></option>
								<option value="<%=RegistroConsulta.EMPIEZA%>" <%=(RegistroConsulta.EMPIEZA.equals(opr))?"selected":""%>><bean:message key="busqueda.numeroRegistro.operador.empieza"/></option>
								<option value="<%=RegistroConsulta.TERMINA%>" <%=(RegistroConsulta.TERMINA.equals(opr))?"selected":""%>><bean:message key="busqueda.numeroRegistro.operador.termina"/></option>
							</html:select>
							<html:text property="numeroRegistro" styleId="numeroRegistro" styleClass="sml"></html:text>
						</p>
						<p class="fila_sub">
		                			<label class="sml"><bean:message key="busqueda.asunto"/>: </label>
							<html:select styleId="asunto" property="asunto" styleClass="single">
								<html:optionsCollection name="tramites" value="idTramite" label="descripcionTramite"/>
							</html:select>
						</p>
						</html:form>
					</div>

					<logic:equal name="busquedaRegistrosBean" property="buscado" value="S">

						<div class="seccion">

								<logic:empty name="<%=Defs.RESULTADO_BUSQUEDA%>">
									<label class="error"><bean:message key="mensaje.error.busqueda.noEncontrado"/></label>
								</logic:empty>
								<logic:notEmpty name="<%=Defs.RESULTADO_BUSQUEDA%>">
									<% int i = 0; %>
									<display:table name="<%=Defs.RESULTADO_BUSQUEDA%>" class="table-display-tag" uid="registro"
										pagesize="10"
										requestURI="busquedaRegistros.do"
										export="false"
										sort="list">

										<%
										Registro registry = (Registro) registro;
	  									String mostrarRegistro = "javascript:mostrarRegistro('"
	  														   + registry.getRegistryNumber() + "', '"
	  														   + registry.getTopic().replaceAll("'", "\\\\'") + "', '"
	  														 + new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(registry.getRegistryDate()) + "', '"
	  														+ new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(registry.getEffectiveDate()) + "', '"
	  														+ registry.getAddressee() + "', '"
	  														   + registry.getName().replaceAll("'", "\\\\'") + "');";
		  								%>

										<display:column  media="html" titleKey="tabla.cabeceraDescargarJustificante" sortable="true" headerClass="cabeceraTabla anchoImg">
											<acronym title='<bean:message key="tabla.descargarJustificante"/>'>
												<table id="tabla0<%=i%>" width="100%" height="100%" style="cursor: pointer"
													onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
													<tr style="height: 100%" valign="middle">
														<td width="100%" height="100%">
															<a target="_blank" href="mostrarJustificante.do?<%=Defs.JUSTIFICANTE%>=<bean:write name="registro" property="registryNumber"/>"><img src="img/pdf.gif" border=0px"/></a>
														</td>
													</tr>
												</table>
											</acronym>
										</display:column>
										<display:column  media="html" titleKey="tabla.numeroRegistro" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="registryNumber">
											<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
												<table id="tabla1<%=i%>" width="100%" height="100%" style="cursor: pointer"
														onclick="<%=mostrarRegistro%>"
													onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
													<tr style="height: 100%">
														<td width="100%" height="100%">
															<bean:write name="registro" property="registryNumber"/>
														</td>
													</tr>
												</table>
											</acronym>
										</display:column>
										<display:column  media="html" titleKey="tabla.cnif" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="senderId">
											<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
												<table id="tabla2<%=i%>" width="100%" height="100%" style="cursor: pointer"
														onclick="<%=mostrarRegistro%>"
													onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
													<tr style="height: 100%">
														<td width="100%" height="100%">
															<bean:write name="registro" property="senderId"/>
														</td>
													</tr>
												</table>
											</acronym>
										</display:column>
										<display:column  media="html" titleKey="tabla.organoDestinatario" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="addressee">
											<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
												<table id="tabla3<%=i%>" width="100%" height="100%" style="cursor: pointer"
														onclick="<%=mostrarRegistro%>"
													onmouseover="javascript:changeColor(<%=i%>, 'true');"
													onmouseout="javascript:changeColor(<%=i%>, 'false');">
													<tr style="height: 100%">
														<td width="100%" height="100%">
															<bean:write name="registro" property="addressee"/>
														</td>
													</tr>
												</table>
											</acronym>
										</display:column>
										<display:column  media="csv excel xml pdf" titleKey="tabla.numeroRegistroExport" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="registryNumber" decorator="ieci.tecdoc.sgm.consulta_telematico.web.decorators.NumRegistroExcelDecorator" property="registryNumber">
										</display:column>
										<display:column  media="csv excel xml pdf" titleKey="tabla.organoDestinatario" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="addressee">
											<bean:write name="registro" property="addressee"/>
										</display:column>
										<display:column  media="html" titleKey="tabla.asuntoRegistro" sortable="true" headerClass="cabeceraTabla anchoSbjt" sortProperty="name">
											<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
												<table id="tabla4<%=i%>" width="100%" height="100%" style="cursor: pointer"
														onclick="<%=mostrarRegistro%>"
													onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
													<tr style="height: 100%">
														<td width="100%" height="100%">
															<bean:write name="registro" property="name" />
														</td>
													</tr>
												</table>
											</acronym>
										</display:column>
										<display:column  media="csv excel xml pdf" titleKey="tabla.asuntoRegistro" sortable="true" headerClass="cabeceraTabla anchoSbjt" sortProperty="name">
											<bean:write name="registro" property="name" />
										</display:column>
										<display:column  media="html" titleKey="tabla.fechaRegistro" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="registryDate" format="{0,date,dd-MM-yyyy}">
											<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
												<table id="tabla5<%=i%>" width="100%" height="100%" style="cursor: pointer"
														onclick="<%=mostrarRegistro%>"
													onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
													<tr style="height: 100%">
														<td width="100%" height="100%">
															<bean:write name="registro" property="registryDate" format="dd/MM/yyyy, HH:mm:ss" />
														</td>
													</tr>
												</table>
											</acronym>
										</display:column>
										<display:column  media="csv excel xml pdf" titleKey="tabla.fechaRegistroExport" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="registryDate" format="{0,date,dd-MM-yyyy}">
											<bean:write name="registro" property="registryDate" format="dd/MM/yyyy, HH:mm:ss" />
										</display:column>
										<display:column  media="html" titleKey="tabla.fechaEfectiva" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="effectiveDate" format="{0,date,dd-MM-yyyy}">
											<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
												<table id="tabla6<%=i%>" width="100%" height="100%" style="cursor: pointer"
													onclick="<%=mostrarRegistro%>"
													onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
													<tr style="height: 100%">
														<td width="100%" height="100%">
															<bean:write name="registro" property="effectiveDate" format="dd/MM/yyyy, HH:mm:ss" />
														</td>
													</tr>
												</table>
											</acronym>
										</display:column>
										<display:column  media="csv excel xml pdf" titleKey="tabla.fechaEfectiva" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="effectiveDate" format="{0,date,dd-MM-yyyy}">
											<bean:write name="registro" property="effectiveDate" format="dd/MM/yyyy, HH:mm:ss" />
										</display:column>
										<% i++; %>
									</display:table>
								</logic:notEmpty>

						</div>

					</logic:equal>

				</div>
			</div>
      			</div>
    		</div>
    		<div class="cuerpobt">
      			<div class="cuerporightbt">
        			<div class="cuerpomidbt">&nbsp;</div>
      			</div>
		</div>


	    	</div>
    		</div>
    	</div>


	<iframe id="detalleRegistro" name="detalleRegistro" src="jsp/blank.html" class="frame_barra" style="visibility: hidden; position: absolute; height: 0px; width: 0px;" frameborder=0></iframe>

  	<script language="javascript">
  		mostrarHasta();
  		mostrarHastaEfectiva();
  	</script>

	</body>

</html:html>