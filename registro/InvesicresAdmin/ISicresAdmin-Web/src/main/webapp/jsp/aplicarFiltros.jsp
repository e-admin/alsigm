<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.beans.Filtro" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<link href="css/calendar-win2k-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script>

	var urlSessionExpired = '<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>';
	var idLibro = <bean:write name="libro" property="id"/>;
	var tipoFiltro = <bean:write name="tipoFiltro" />;
	var idObjeto = <bean:write name="idObjeto"/>;
	var nombreObjeto = "<bean:write name="nombreObjeto" filter="false"/>";

	function dameFiltro(fila) {

		var action = '<html:rewrite page="/aplicarFiltros.do"/>';
		action += '?idLibro=' + idLibro;
		action += "&tipoFiltro=" + tipoFiltro;
		action += "&idObjeto=" + idObjeto;
		action += "&nombreObjeto=" + nombreObjeto;
		action += "&idCampo=" + document.getElementById('filtros_campo_' + fila).value;
		action += "&fila=" + fila;
		document.forms[0].action = action;
		document.forms[0].submit();
	}

	function anadirFiltro(fila) {

		if( document.getElementById('filtros_campo_' + fila).value == "") {
			var msg_campo = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validar.masFiltro.seleccione.campo"/>";
			alert(msg_campo);
			document.getElementById('filtros_campo_' + fila).focus();
			return null;
		}
		else if( document.getElementById('filtros_nexo_' + fila).value == "") {
			var msg_nexo = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validar.masFiltro.seleccione.nexo"/>";
			alert(msg_nexo);
			document.getElementById('filtros_nexo_' + fila).focus();
			return null;
		}
		var action = '<html:rewrite page="/aplicarFiltros.do"/>';
		action += '?idLibro=' + idLibro;
		action += "&tipoFiltro=" + tipoFiltro;
		action += "&idObjeto=" + idObjeto;
		action += "&nombreObjeto=" + nombreObjeto;
		action += "&filaAnadir=" + fila;
		document.forms[0].action = action;
		document.forms[0].submit();
	}

	function eliminarFiltro(fila) {
		var size = '<bean:write name="filtrosSize"/>';
		if( fila != 0 || size > 0) {
			var action = '<html:rewrite page="/aplicarFiltros.do"/>';
			action += '?idLibro=' + idLibro;
			action += "&tipoFiltro=" + tipoFiltro;
			action += "&idObjeto=" + idObjeto;
			action += "&nombreObjeto=" + nombreObjeto;
			action += "&filaEliminar=" + fila;
			document.forms[0].action = action;
			document.forms[0].submit();
		}
	}

	function checquearSessionBuscarOficinas(url) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false")
			abreOficinas(url);
		else
			window.document.location.href = urlSessionExpired;
	}

	function checquearSessionBuscarTipoAsunto(url) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false")
			abreTiposAsunto(url);
		else
			window.document.location.href = urlSessionExpired;
	}

	function checquearSessionBuscarUnidades(url) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false")
			abreUnidades(url);
		else
			window.document.location.href = urlSessionExpired;
	}

	function borrarTodos() {
		var msg = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.message.advertencia.borrar" />";
		if(confirm(msg)) {
			var action = '<html:rewrite page="/aplicarFiltros.do"/>';
			action += '?idLibro=' + idLibro;
			action += "&tipoFiltro=" + tipoFiltro;
			action += "&idObjeto=" + idObjeto;
			action += "&nombreObjeto=" + nombreObjeto;
			action += "&accion=BORRAR";
			document.forms[0].action = action;
			document.forms[0].submit();
		}
	}
	function validarFiltros(action) {
		var size = '<bean:write name="filtrosSize"/>';
		var valor;
		if( document.getElementById('filtros_nexo_' + parseInt(size-1)).value != "") {
			var msg_validation_nexo = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.message.validacion.nexos"/>";
			alert(msg_validation_nexo);
			document.getElementById('filtros_nexo_' + parseInt(size-1)).focus();
			return null;
		}
		for(var i = 0; i < parseInt(size-1); i++) {
			if( document.getElementById('filtros_nexo_' + i).value == "") {
				var msg_nexo_vacio = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.message.validacion.nexo.vacio" />";
				alert(msg_nexo_vacio + (i+1));
				document.getElementById('filtros_nexo_' + i).focus();
				return null;
			}
			if( document.getElementById('filtros_campo_' + i).value == 2 ||
				document.getElementById('filtros_campo_' + i).value == 4 ||
				document.getElementById('filtros_campo_' + i).value == 12) {
				valor = document.getElementById('filtros_valor_' + i).value;
				if( !validarFecha(valor)) {
					alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.message.validacion.valor.fecha" />");
					document.getElementById('filtros_valor_' + i).focus();
					document.getElementById('filtros_valor_' + i).select();
					return null;
				}
			}
		}
		llamadaActionGuardarFiltros(action, idLibro, tipoFiltro, idObjeto, nombreObjeto);
	}

	function cancelarFiltros(){
		if(tipoFiltro == 1)
			llamadaAction('<html:rewrite page="/asociarUsuarios.do"/>' + '?idLibro=' + idLibro + "&idOficina="+idObjeto);
		else if(tipoFiltro == 2)
			llamadaAction('<html:rewrite page="/editarLibro.do"/>' + '?idLibro=' + idLibro);
	}

</script>

<script type="text/javascript">
    var oldLink = null;

	function setActiveStyleSheet(link, title) {
  		var i, a, main;
		  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
			if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
			  a.disabled = true;
			  if(a.getAttribute("title") == title)
				  a.disabled = false;
			}
		  }
		  if (oldLink)
			  oldLink.style.fontWeight = 'normal';
		  oldLink = link;
		  link.style.fontWeight = 'bold';
		  return false;
	}

	function selected(cal, date) {
	  	cal.sel.value = date;
	  	if (cal.dateClicked && (cal.sel.id == "sel1" || cal.sel.id == "sel3"))
			cal.callCloseHandler();
	}

	function closeHandler(cal) {
  		cal.hide();
	    _dynarch_popupCalendar = null;
	}


	function showCalendar(id, format, showsTime, showsOtherMonths) {
	  var el = document.getElementById(id);
	  if (_dynarch_popupCalendar != null)
		_dynarch_popupCalendar.hide();
	  else {
		var cal = new Calendar(1, null, selected, closeHandler);
		 cal.weekNumbers = false;
		if (typeof showsTime == "string") {
		  cal.showsTime = true;
		  cal.time24 = (showsTime == "24");
		}
		if (showsOtherMonths)
		  cal.showsOtherMonths = true;

		_dynarch_popupCalendar = cal;
		cal.setRange(1900, 2070);
		cal.create();
	  }
	  _dynarch_popupCalendar.setDateFormat(format);
	  _dynarch_popupCalendar.parseDate(el.value);
	  _dynarch_popupCalendar.sel = el;
	  _dynarch_popupCalendar.showAtElement(el.nextSibling, "Br");

	  return false;
	}

	var MINUTE = 60 * 1000;
	var HOUR = 60 * MINUTE;
	var DAY = 24 * HOUR;
	var WEEK = 7 * DAY;

	function isDisabled(date) {
	  var today = new Date();
	  return (Math.abs(date.getTime() - today.getTime()) / DAY) > 10;
	}

	function flatSelected(cal, date) {
	  var el = document.getElementById("preview");
	  el.innerHTML = date;
	}

	function showFlatCalendar() {
	  var parent = document.getElementById("display");
	  var cal = new Calendar(0, null, flatSelected);
	  cal.weekNumbers = false;
	  cal.setDisabledHandler(isDisabled);
	  cal.setDateFormat("%A, %B %e");
	  cal.create(parent);
	  cal.show();
	}

	function PincharCalendario(IDEnlace){
		if (document.getElementById(IDEnlace)){
			if (document.createEventObject)
				document.getElementById(IDEnlace).fireEvent("onclick");
			else if (document.createEvent){
				var evObj = document.createEvent('MouseEvents');
				evObj.initEvent('click', true, false);
				document.getElementById("imgPestanaBuscador").dispatchEvent(evObj);
			}
		}
	}
</script>

</head>

<body>
<html:form action="/aplicarFiltros.do">
<div id="contenedora">
  <!-- Inicio Cabecera -->
  <jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="libros"/>
		  </jsp:include>

          <div class="cuadro">
			<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros"/>
			</div>
			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" nowrap="nowrap" class="col_eliminar" onclick="borrarTodos()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.borrar.todos"/></td>
					<td align="right" class="col_nuevo" onclick="validarFiltros('<html:rewrite page="/guardarFiltros.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td align="right" class="col_eliminar" onclick="cancelarFiltros()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table>
			</div>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr><td height="10"></td></tr>
				<logic:equal name="libro" property="tipo" value="1">
					<tr><td><font color="#950101" ><b> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.entrada"/></b>:</font>&nbsp;&nbsp;<bean:write name="libro" property="nombre"/></td></tr>
					<logic:equal name="tipoFiltro" value="2">
						<tr><td><font color="#950101" ><b> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.oficina.asociada" /></b>:</font>&nbsp;&nbsp;<bean:write name="nombreObjeto" filter="true"/></td></tr>
					</logic:equal>
					<logic:notEqual name="tipoFiltro" value="2">
						<tr><td><font color="#950101" ><b> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.usuario.asociada" /></b>:</font>&nbsp;&nbsp;<bean:write name="nombreObjeto" /></td></tr>
					</logic:notEqual>
				</logic:equal>
				<logic:notEqual name="libro" property="tipo" value="1">
					<tr><td><font color="#006699" ><b> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.salida"/></b>:</font>&nbsp;&nbsp;<bean:write name="libro" property="nombre"/></td></tr>
					<logic:equal name="tipoFiltro" value="2">
						<tr><td><font color="#006699" ><b> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.oficina.asociada" /></b>:</font>&nbsp;&nbsp;<bean:write name="nombreObjeto" filter="true"/></td></tr>
					</logic:equal>
					<logic:notEqual name="tipoFiltro" value="2">
						<tr><td><font color="#006699" ><b> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.usuario.asociada" /></b>:</font>&nbsp;&nbsp;<bean:write name="nombreObjeto" /></td></tr>
					</logic:notEqual>
				</logic:notEqual>
				<tr><td height="10"></td></tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width="100%" valign="top">
						<display:table name="filtros.lista" uid="fila"
							requestURI="/aplicarFiltros.do" sort="page" class="tablaListado" style="width:100%">
								<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.filtro.consulta.campo"
									 sortable="false" style="width: 30%;">
									 <table cellpadding="0" cellspacing="0" border="0">
									 	<tr>
									 		<td>
									 			<html-el:select property="filtros[${fila_rowNum-1}].campo" styleClass="textInput" style="width:222px" onchange="dameFiltro(${fila_rowNum-1})" styleId="filtros_campo_${fila_rowNum-1}">
													<html:option value=""><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.filtros.seleccione.campo" /></html:option>
													<html:optionsCollection name="campos" property="lista" value="id" label="descripcion"/>
												</html-el:select>
									 		</td>
									 	</tr>
									 </table>
								</display:column>
								<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.filtro.consulta.operador"
									 sortable="false" style="width: 30%;">
									 <table cellpadding="0" cellspacing="0" border="0">
									 	<tr>
									 		<td>
									 			<html-el:select property="filtros[${fila_rowNum-1}].operador" styleClass="textInput" style="width:222px" styleId="filtros_operador_${fila_rowNum-1}">
													<html-el:optionsCollection name="operadores[${fila_rowNum-1}]" property="lista" value="codigo" label="descripcion"/>
												</html-el:select>
									 		</td>
									 	</tr>
									 </table>
								</display:column>
								<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.filtro.consulta.valor"
									 sortable="false" style="width: 25%;">
									 <table cellpadding="0" cellspacing="0" border="0" >
									 	<tr>
									 		<td>
												<c:set var="_tipoCampo" value="tipoCampo_${fila_rowNum-1}"/>
												<jsp:useBean id='_tipoCampo' type="java.lang.String"/>

												<logic:empty name='<%=_tipoCampo%>'>
													<html-el:text property="filtros[${fila_rowNum-1}].valor" styleClass="textInput" style="width:150px" styleId="filtros_valor_${fila_rowNum-1}"/>
												</logic:empty>
										 		<logic:equal name='<%=_tipoCampo%>' value="A">
										 			<html-el:text property="filtros[${fila_rowNum-1}].valor" styleClass="textInput" style="width:150px" styleId="filtros_valor_${fila_rowNum-1}"/>
										 		</logic:equal>
										 		<logic:equal name='<%=_tipoCampo%>' value="B">
										 			<html-el:text property="filtros[${fila_rowNum-1}].valor" styleClass="textInput" style="width:128px" maxlength="10" styleId="filtros_valor_${fila_rowNum-1}" readonly="true"/><img src='<html:rewrite page="/img/img_calendar.gif" />' border="0" onclick="return showCalendar('filtros_valor_<c:out value="${fila_rowNum-1}"/>', '%d/%m/%Y', true);" style="cursor:hand" hspace="3" />
										 		</logic:equal>
										 		<logic:equal name='<%=_tipoCampo%>' value="C">
										 				<html-el:select property="filtros[${fila_rowNum-1}].valor" styleClass="textInput" style="width:150px" styleId="filtros_valor_${fila_rowNum-1}" >
											 				<html-el:optionsCollection name="valores[${fila_rowNum-1}]" property="lista" value="codigo" label="descripcion"/>
											 			</html-el:select>
										 		</logic:equal>
										 		<logic:equal name='<%=_tipoCampo%>' value="D">
										 			<table cellpadding="0" cellspacing="0" border="0" >
										 				<tr>
										 					<td><html-el:text property="filtros[${fila_rowNum-1}].valor" styleClass="textInput" style="width:130px" styleId="filtros_valor_${fila_rowNum-1}"/></td>
										 					<td><a href="#" onclick="checquearSessionBuscarOficinas('<html-el:rewrite page="/listadoOficinas.do?fila=${fila_rowNum-1}"/>')"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a></td>
										 				</tr>
										 			</table>
										 		</logic:equal>
										 		<logic:equal name='<%=_tipoCampo%>' value="E">
										 			<tr>
										 					<td><html-el:text property="filtros[${fila_rowNum-1}].valor" styleClass="textInput" style="width:130px" styleId="filtros_valor_${fila_rowNum-1}"/></td>
										 					<td><a href="#" onclick="checquearSessionBuscarUnidades('<html-el:rewrite page="/jsp/buscarUnidadesPopup.jsp?fila=${fila_rowNum-1}"/>')"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a></td>
										 				</tr>
										 		</logic:equal>
										 		<logic:equal name='<%=_tipoCampo%>' value="F">
										 			<table cellpadding="0" cellspacing="0" border="0" >
										 				<tr>
										 					<td><html-el:text property="filtros[${fila_rowNum-1}].valor" styleClass="textInput" style="width:130px" styleId="filtros_valor_${fila_rowNum-1}"/></td>
										 					<td><a href="#" onclick="checquearSessionBuscarTipoAsunto('<html-el:rewrite page="/listadoAsuntos.do?fila=${fila_rowNum-1}"/>')"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a></td>
										 				</tr>
										 			</table>
										 		</logic:equal>
									 		</td>
									 	</tr>
									 </table>
								</display:column>
								<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.filtro.consulta.nexo"
									 sortable="false" style="width: 10%;">
									 <table cellpadding="0" cellspacing="0" border="0" style="width: 10%;">
									 	<tr>
									 		<td align="center">
										 		<html-el:select property="filtros[${fila_rowNum-1}].nexo" styleClass="textInput" style="width:70px" styleId="filtros_nexo_${fila_rowNum-1}">
													<html-el:optionsCollection name="nexos[${fila_rowNum-1}]" property="lista" value="codigo" label="descripcion"/>
												</html-el:select>
									 		</td>
									 	</tr>
									 </table>
								</display:column>
								<display:column sortable="false" style="width: 5%;">
									<table cellpadding="0" cellspacing="0" border="0">
										<tr>
											<c:set var="_rowNum">
												<c:out value="${fila_rowNum}"/>
											</c:set>
											<jsp:useBean id='_rowNum' type="java.lang.String"/>

											<logic:equal name="filtrosSize" value='<%=_rowNum%>'>
												<logic:equal name="filtrosSize" value="1">
													<td onclick="javascript:anadirFiltro(<c:out value="${fila_rowNum-1}"/>)" style="cursor:hand"><img src="<html:rewrite page="/img/masFiltro.gif"/>" hspace="3" alt='<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.anadir.filtro" />'/></td>
													<td width="4">&nbsp;</td>
												</logic:equal>
												<logic:notEqual name="filtrosSize" value="1">
													<td onclick="javascript:anadirFiltro(<c:out value="${fila_rowNum-1}"/>)" style="cursor:hand"><img src="<html:rewrite page="/img/masFiltro.gif"/>" hspace="3" alt='<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.anadir.filtro" />'/></td>
													<td width="4">&nbsp;</td>
													<td onclick="javascript:eliminarFiltro(<c:out value="${fila_rowNum-1}"/>)" style="cursor:hand"><img src="<html:rewrite page="/img/menosFiltro.gif"/>" hspace="3" alt='<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.eliminar.filtro" />'/></td>
												</logic:notEqual>
											</logic:equal>
											<logic:notEqual name="filtrosSize" value='<%=_rowNum%>'>
												<td onclick="javascript:eliminarFiltro(<c:out value="${fila_rowNum-1}"/>)" style="cursor:hand"><img src="<html:rewrite page="/img/menosFiltro.gif"/>" hspace="3" alt='<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.eliminar.filtro" />'/></td>
											</logic:notEqual>
										</tr>
									</table>
								</display:column>
						</display:table>
					</td>
				</tr>
			</table>
          </div>
        </div>
      </div>
    </div>
    <div class="cuerpobt">
      <div class="cuerporightbt">
        <div class="cuerpomidbt"></div>
      </div>
    </div>
  </div>
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>
</html:form>
</body>
</html>
