var activebgcolor = "white";
var activetextcolor = "#666600";

var inactivebgcolor = "#C7CE4E";
var inactivetextcolor = "black";

var overbgcolor = "#E6E6E6";
var overtextcolor = "black";

//Contantes de Estados de Elementos
//Estados de Beans
var ESTADO_SIN_CAMBIOS = 0;
var ESTADO_NUEVO = 1;
var ESTADO_MODIFICADO = 2;
var ESTADO_ELIMINADO = 3;
var ESTADO_ELIMINADO_NUEVO = 4;



var tabs = new Array();

function llamadaActionIdEditarUsuarioLibro (action, idOficina, idLibro, tipoLibro, idEstado, nombre) {
	document.forms[1].action = action + "?idLibro="+idLibro+"&idOficina="+idOficina+"&tipoLibro="+tipoLibro+"&idEstado="+idEstado+"&nombre="+nombre;
	document.forms[1].submit();
}

function llamadaActionCancelarFiltros(action, idLibro, tipoLibro, idEstado, nombre) {

	if( tipoLibro == "1") action += "?idLibroE="+idLibro;
	else if(tipoLibro == "2") action += "?idLibroS="+idLibro;

	document.forms[0].action = action + "&tipoLibro="+tipoLibro+"&idEstado="+idEstado+"&nombre="+nombre;
}

function llamadaActionCancelarFiltros(action, idLibro) {
	document.forms[0].action = action + "?idLibro="+idLibro;
	document.forms[0].submit();
}

function llamadaActionAsociar(action, idLibro, accion) {
	document.forms[0].action = action + "?idLibro="+idLibro+"&accion="+accion;
	document.forms[0].submit();
}

function llamadaActionIdOficinaEliminar(action, idOficina, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		llamadaActionIdOficina(action, idOficina);
	}
}

function llamadaActionOficinasTipoLibro(action, idLibro, tipoLibro, idEstado, nombre) {
	document.forms[0].action = action + "?idLibro="+idLibro+"&tipoLibro="+tipoLibro+"&idEstado="+idEstado+"&nombre="+nombre;
	document.forms[0].submit();
}

function llamadaActionOficinasLibro(action, idLibro) {
	document.forms[0].action = action + "?idLibro="+idLibro;
	document.forms[0].submit();
}

function llamadaActionUsuariosLibro(action, idLibro, accion) {
	document.forms[0].action = action + "?idLibro="+idLibro+"&accion="+accion;
	document.forms[0].submit();
}

function llamadaAction(action) {
	document.forms[0].action = action;
	document.forms[0].submit();
}

function llamadaActionIdPadre(action, idPadre) {
	document.forms[0].action = action + "?idPadre=" + idPadre;
	document.forms[0].submit();
}

function llamadaActionIdPadreRoot(action, idPadre) {
	document.forms[0].action = action + "?idPadre=" + idPadre + "&nodoRaiz=true";
	document.forms[0].submit();
}

function llamadaActionGuardarNumeracion(action, tipoLibro) {
	document.forms[0].action = action + "?tipoLibro=" + tipoLibro;
	document.forms[0].submit();
}

function llamadaActionForm1(action) {
	document.forms[1].action = action;
	document.forms[1].submit();
}

function llamadaActionForm(action) {
	document.permisosSicresForm.action = action;
	document.permisosSicresForm.submit();
}


function llamaActionIFrame(action, idUnidad, idTipo) {
	document.getElementById("iframeUnidades").src = action + "?idUnidad="+idUnidad+"&idTipo="+idTipo;
}


function llamaActionIFrameRedirect(action) {
	document.getElementById("iframeUnidades").src = action;
}

function llamadaActionIdUsuarioEliminar(action, idUsuario, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?")) {
		llamadaActionIdUsuario(action, idUsuario);
	}
}

function llamadaActionIdTransporte(action, idTransporte) {
	document.forms[0].action = action+"?idTransporte="+idTransporte;
	document.forms[0].submit();
}

function llamadaActionIdTransporteEliminar(action, idTransporte, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		llamadaActionIdTransporte(action, idTransporte);
	}
}

function llamadaActionIdUnidadEliminar(action, idUnidad, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		llamadaActionIdUnidad(action, idUnidad);
	}
}

function llamadaActionIdUnidadRootEliminar(action, idUnidad, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		document.forms[0].action = action+"?idUnidad="+idUnidad+"&nodoRaiz=true";
		document.forms[0].submit();
	}
}

function llamadaActionIdDistribucionEliminar(action, idDistribucion, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		llamadaActionIdDistribucion(action, idDistribucion);
	}
}

function llamadaActionIdDistribucion(action, idDistribucion) {
	document.forms[0].action = action+"?idDistribucion="+idDistribucion;
	document.forms[0].submit();
}

function llamadaActionAsociarUsuarioIdOficina(action, idUsuario) {
	document.forms[0].action = action+"?idUsuario="+idUsuario;
	document.forms[0].submit();
}

function llamadaActionAsociarOficPrefUsuario(action, idOficina, idUsuario) {
	document.forms[0].action = action+"?idOficina="+idOficina + "&idUsuario="+idUsuario;
	document.forms[0].submit();
}


function llamadaActionDesasociarOficinaIdUsuario(action, idOficina, idUsuario, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		llamadaActionDesasociarIdOficina(action, idOficina, idUsuario);
	}
}

function llamadaActionDesasociarIdOficina(action, idOficina, idUsuario) {
	document.forms[0].action = action+"?idUsuario="+idUsuario+"&idOficina="+idOficina+"&accion=DESASOCIAR";
	document.forms[0].submit();
}

function llamadaActionAsociarUsuarios(action, idOficina, nombreOficina) {
	document.forms[0].action = action+"?idOficina="+idOficina+"&nombreOficina="+nombreOficina;
	document.forms[0].submit();
}

function llamadaActionAsociarUsuariosOficina(action, idOficina, msg) {
	if(getNumSelectedChecked(document.forms[0], "idsUser") > 0){
		document.forms[0].action = action+"?idOficina="+idOficina+"&accion=ASOCIAR_USUARIOS";
		document.forms[0].submit();
	}else
		alert(msg);
}

function objectIsArray(object){
	if (object != null && object.length) return true;
	return false;
}

function getNumSelectedChecked(form,namecheck){
	totalSeleccionados=0;
	if (objectIsArray(form.elements[namecheck])){
		for(var i=0;i<form.elements[namecheck].length;i++){
			if(form.elements[namecheck][i].checked){
				totalSeleccionados++;
			}
		}
	}else{
		if(form.elements[namecheck]){
			if (form.elements[namecheck].checked){
				totalSeleccionados++;
			}
		}
	}

	return totalSeleccionados;
}

/*function llamadaActionIdEliminarAsociacion(action, idLibro, idOficina, nombre, msg) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		document.forms[0].action = action + "?idOficina="+idOficina+"&idLibro="+idLibro;
		document.forms[0].submit();
	}
}*/

function llamadaUpdateEstadoLibro(action, idLibro, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		document.forms[0].action = action + "&idLibro="+idLibro;
		document.forms[0].submit();
	}
}

function llamadaDeleteLibro(action, idLibro, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		document.forms[0].action = action + "?idLibro="+idLibro;
		document.forms[0].submit();
	}
}


function llamadaActionIdUsuario(action, idUsuario) {
	document.forms[0].action = action+"?idUsuario="+idUsuario;
	document.forms[0].submit();
}

function llamadaActionIdOficina(action, idOficina) {
	document.forms[0].action = action+"?idOficina="+idOficina;
	document.forms[0].submit();
}

function llamadaActionListas(action, idLista) {
	document.forms[0].action = action+"?idLista="+idLista;
	document.forms[0].submit();
}

function llamadaActionUsersOficina(action, idOficina) {
	document.forms[0].action = action+"?idOfic="+idOficina+"&usuarios=true"+"&agregados=true";
	document.forms[0].submit();
}

function llamadaActionUsuariosOficina() {
	document.forms[0].submit();
}

function elementSelected(buttonSet) {
	if(!buttonSet) return false;

	if (!buttonSet.length)
		return buttonSet.checked;
	else {
		for(var i=0;i<buttonSet.length;i++)
			if (buttonSet[i].checked) return true;
		return false;
	}
}

function llamadaActionIdUnidad(action, idUnidad) {
	document.forms[0].action = action+"?idUnidad="+idUnidad;
	document.forms[0].submit();
}

function llamadaActionIdUnidadRoot(action, idUnidad) {
	document.forms[0].action = action+"?idUnidad="+idUnidad+"&nodoRaiz=true";
	document.forms[0].submit();
}

function llamadaActionId(action, id) {
	document.forms[0].action = action+"?id="+id;
	document.forms[0].submit();
}

function llamadaActionEliminarId(action,parametros, msg, nombre) {
	if( confirm( msg + " '" + nombre + "' ?" )) {
		document.forms[0].action = action + "?" + parametros;
		document.forms[0].submit();
	}
}

function llamadaActionNameId(action, nombreId, id) {
	document.forms[0].action = action+"?"+ nombreId +"="+id;
	document.forms[0].submit();
}

/*function llamadaFiltrosOficina(action, idLibro, idObjeto, nombreObjeto){
	document.forms[0].action = action + "?idLibro="+idLibro+"&idObjeto="+idObjeto+"&nombreObjeto="+nombreObjeto+"&tipoFiltro=2";
	document.forms[0].submit();
}*/

function llamadaFiltrosUsuario(action, idObjeto, idLibro, nombreObjeto){
	document.forms[0].action = action + "?idLibro="+idLibro+"&idObjeto="+idObjeto+"&nombreObjeto="+nombreObjeto+"&tipoFiltro=1";
	document.forms[0].submit();
}

function llamadaActionGuardarFiltros(action, idLibro, tipoFiltro, idObjeto, nombreObjeto){
	document.forms[0].action = action + "?idLibro="+idLibro+"&tipoFiltro="+tipoFiltro+"&idObjeto="+idObjeto+"&nombreObjeto="+nombreObjeto;
	document.forms[0].submit();
}

function abreUsuariosOficina(url, usuariosDisponibles, msg) {

	if(usuariosDisponibles){
		var alturaPopup=530;
		var H = (screen.height - alturaPopup) / 2;
		var anchuraPopup=590;
		var L = (screen.width - anchuraPopup) / 2;

		var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
			features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

		window.open(url, "ventanaDestino", features);
	}else
		alert(msg);
}

function abreListaDistribucion(url) {

	var alturaPopup=530;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=590;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreListaDistribucionLdap(url) {

	var alturaPopup=390;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=490;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreUsuarios(url) {

	var alturaPopup=280;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=590;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreUsuariosLdap(url) {

	var alturaPopup=390;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=490;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreListas(url) {

	var alturaPopup=280;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=350;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";
	window.open(url, "ventanaDestino", features);
}

function abreListaOficinas(url) {

	var alturaPopup=350;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=400;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=1,status=no,titlebar=no,toolbar=no";
	var ventana = window.open(url, "ventanaDestino", features);
	if(ventana != null)
		ventana.focus();
}

function abreDepartamentos(url) {

	var alturaPopup=290;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=390;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreDepartamentosLdap(url) {

	var alturaPopup=390;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=490;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreLdap(url) {

	var alturaPopup=390;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=490;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreOficinas(url) {

	var alturaPopup=300;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=450;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreTiposAsunto(url) {
	var alturaPopup=300;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=450;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}

function abreUnidades(url) {

	var alturaPopup=500;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=400;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaDestino", features);
}


function abreBusquedaEntidadesReg(url){
	var alturaPopup=420;
	var H = (screen.height - alturaPopup) / 2;
	var anchuraPopup=800;
	var L = (screen.width - anchuraPopup) / 2;

	var features = "directories=0,top="+H+"px,left="+L+"px,height="+alturaPopup+"px,width="+anchuraPopup+"px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

	window.open(url, "ventanaIntercambioReg", features);
}

function nuevaUnidadClick() {
	ocultarDivsUnidad();
	visibleDiv('nuevaUnidad');
}

function direccionUnidadClick() {
	ocultarDivsUnidad();
	visibleDiv('direccionUnidad');
}

function intercambioRegUnidad(){
	ocultarDivsUnidad();
	visibleDiv('intercambioRegistralUnidad');
}

function permisosUsuarioClick() {
	ocultarDivsUsuario();
	visibleDiv('permisosUsuario');
}

function permisosAdministracionClick() {
	ocultarDivsUsuario();
	visibleDiv('permisosAdministracion');
}


function identificacionUsuarioClick() {
	ocultarDivsUsuario();
	visibleDiv('identificacionUsuario');
}

function localizacionUsuarioClick() {
	ocultarDivsUsuario();
	visibleDiv('localizacionUsuario');
}

function nuevoUsuarioClick() {
	ocultarDivsUsuario();
	visibleDiv('nuevoUsuario');
}

function nuevaOficinaClick() {
	ocultarDivsOficina();
	visibleDiv('nuevaOficina');
}

function direccionOficinaClick() {
	ocultarDivsOficina();
	visibleDiv('direccionOficina');
}

function intercambioRegistralOficinaClick(){
	ocultarDivsOficina();
	visibleDiv('intercambioRegistralOficina');
}

function intercambioRegistralUnidadClick(){
	ocultarDivsUnidad();
	visibleDiv('intercambioRegistralUnidad');
}

function ocultarDivsOficina() {
	document.getElementById('nuevaOficina').style.display = 'none';
	document.getElementById('direccionOficina').style.display = 'none';
	document.getElementById('intercambioRegistralOficina').style.display = 'none';
}

function ocultarDivsUnidad() {
	document.getElementById('nuevaUnidad').style.display = 'none';
	document.getElementById('direccionUnidad').style.display = 'none';
	document.getElementById('intercambioRegistralUnidad').style.display = 'none';
}


function ocultarDivsUsuario() {
	document.getElementById('nuevoUsuario').style.display = 'none';
	document.getElementById('permisosUsuario').style.display = 'none';
	document.getElementById('permisosAdministracion').style.display = 'none';
	document.getElementById('identificacionUsuario').style.display = 'none';
	document.getElementById('localizacionUsuario').style.display = 'none';
}


//GENERICO PARA TABS
function ocultarDivs() {
	for ( var i = 0; i < tabs.length; i++) {
		var nombreDiv = tabs[i];
		document.getElementById(nombreDiv).style.display = 'none';
	}
}

function tabClick(indice) {
	ocultarDivs();
	var nombreDiv = tabs[indice-1];
	if(document.getElementById("activeTab")) document.getElementById("activeTab").value = indice;
	visibleDiv(nombreDiv);
}

function visibleDiv(capa) {
	document.getElementById(capa).style.display = 'block';
}

function visibleInLineDiv(capa) {
	document.getElementById(capa).style.display = 'inline';
}

function ocultaDiv(capa) {
	document.getElementById(capa).style.display = 'none';
}


var checkBox = new Array(20);
checkBox[0] = 'altaPersonasCheck';
checkBox[1] = 'adaptacionRegistrosCheck';
checkBox[2] = 'modificaPersonasCheck';
checkBox[3] = 'modificaPersonasCheck';
checkBox[4] = 'rechazoRegistrosCheck';
checkBox[5] = 'introduccionFechaCheck';
checkBox[6] = 'archivoRegistrosCheck';
checkBox[7] = 'modificacionFechaCheck';
checkBox[8] = 'cambioDestinoDistribuidosCheck';
checkBox[9] = 'modificacionCamposCheck';
checkBox[10] = 'cambioDestinoRechazadosCheck';
checkBox[11] = 'accesoOperacionesCheck';
checkBox[12] = 'distribucionManualCheck';
checkBox[13] = 'verDocumentosCheck';
checkBox[14] = 'deleteDocumentosCheck';
checkBox[15] = 'gestionUnidadesAdministrativasCheck';
checkBox[16] = 'gestionUsuariosCheck';
checkBox[17] = 'gestionInformesCheck';
checkBox[18] = 'gestionTiposAsuntoCheck';
checkBox[19] = 'gestionTiposTransporteCheck';

var permSelected = new Array(20);

function checkSuperUser( itemPerfil, operacion, init ) {
	if( itemPerfil == 3) {
		for( i = 0; i < checkBox.length; i++) {
			disabledTrueCheckPermisos(checkBox[i]);
			checkPermisos(checkBox[i], true);
		}
	} else {
		for( i = 0; i < checkBox.length; i++) {
			disabledFalseCheckPermisos(checkBox[i]);
			if(operacion != 'EDICION')
				checkPermisos(checkBox[i], false);
			else if(operacion == 'EDICION' && !init)
				recargarChecksSelected(i, checkBox[i]);
		}
	}
}

function cargarChecksSelected(itemPerfil){
	if( itemPerfil == 1) {
		for(i = 0; i < checkBox.length; i++) {
			permSelected[i] = document.getElementById(checkBox[i]).checked;
		}
	} else if (itemPerfil == 3) {
		for(i = 0; i < checkBox.length; i++) {
			disabledTrueCheckPermisos(checkBox[i]);
		}
	}
}

function recargarChecksSelected(idSelect, id){
	if(permSelected[idSelect])
		checkPermisos(id, true);
	else
		checkPermisos(id, false);
}

function disabledFalseCheckPermisos(id) {
	document.getElementById(id).disabled = false;
}

function disabledTrueCheckPermisos(id) {
	document.getElementById(id).disabled = true;
}

function checkPermisos(id, value) {
	document.getElementById(id).checked = value;
}

function chequearSession( baseUrl )
{
	if(window.XMLHttpRequest) {
		try {
			req = new XMLHttpRequest();
		}
		catch(e) {
			req = false;
		}
	}
	else if(window.ActiveXObject) {
		try {
			req = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch(e) {
			try {
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch(e) {
				req = false;
			}
		}
	}
	if(req) {
		var d = new Date();
        var t = d.getTime();
		req.open("GET", baseUrl + "?" + 'timestamp=' + t, false);
		req.send("");
		xmlDoc = unescape( req.responseText);
		return xmlDoc;
	}
}

function checkModificar( check, fila ) {
	chequearValues("modificar", fila, check.checked);
	chequearValues("crear", fila, check.checked);
	chequearValues("consultar", fila, check.checked);
}

function checkCrear( check, fila ) {
	chequearValues("crear", fila, check.checked);
	chequearValues("consultar", fila, check.checked);
}

function chequearValues( typeCheck, fila, valueBoolean ) {
	document.getElementById("permisos["+fila+"]." + typeCheck).value= valueBoolean;
	document.getElementById(typeCheck + "Check_" + fila).checked = valueBoolean;
}


function esDigito(sChr){
  var sCod = sChr.charCodeAt(0);
  return ((sCod > 47) && (sCod < 58));
}

function valSep(oTxt){
	var bOk = false;
	bOk = bOk || ((oTxt.charAt(2) == "-") && (oTxt.charAt(5) == "-"));
	bOk = bOk || ((oTxt.charAt(2) == "/") && (oTxt.charAt(5) == "/"));
	return bOk;
}

function finMes(oTxt){
	var nMes = parseInt(oTxt.substr(3, 2), 10);
	var nRes = 0;
	switch (nMes){
		case 1: nRes = 31; break;
		case 2: nRes = 29; break;
		case 3: nRes = 31; break;
		case 4: nRes = 30; break;
		case 5: nRes = 31; break;
		case 6: nRes = 30; break;
		case 7: nRes = 31; break;
		case 8: nRes = 31; break;
		case 9: nRes = 30; break;
		case 10: nRes = 31; break;
		case 11: nRes = 30; break;
		case 12: nRes = 31; break;
	}
	return nRes;
}

function valDia(oTxt){
	var bOk = false;
	var nDia = parseInt(oTxt.substr(0, 2), 10);
	bOk = bOk || ((nDia >= 1) && (nDia <= finMes(oTxt)));
	return bOk;
}

function valMes(oTxt){
	var bOk = false;
	var nMes = parseInt(oTxt.substr(3, 2), 10);
	bOk = bOk || ((nMes >= 1) && (nMes <= 12));
	return bOk;
}

function valAno(oTxt){
	var bOk = true;
	var nAno = oTxt.substr(6);
	bOk = bOk && ((nAno.length == 2) || (nAno.length == 4));
	if (bOk){
		for (var i = 0; i < nAno.length; i++){
			bOk = bOk && esDigito(nAno.charAt(i));
		}
	}
	return bOk;
}

function validarFecha(oTxt){
	var bOk = true;
	if (oTxt != ""){
		bOk = bOk && (valAno(oTxt));
		bOk = bOk && (valMes(oTxt));
		bOk = bOk && (valDia(oTxt));
		bOk = bOk && (valSep(oTxt));
		if (!bOk){
			return false;
		} else {
			return true;
		}
	}
	return false;
}


// Metodo que modifica los check de permisos y usuarios asociados a un libro. Si se asigna permiso
// crear o modificar el permiso consultar se asigna automaticamente,
// si se deselecciona el permiso consultar, los permisos crear y modificar se deselecionan de forma
// automatica
function changePermisoConsultar(ele){
	var id = (ele.id).split("_");
	if(!(ele.checked)){
		var consultar = document.getElementById("consultarCheck_"+id[1]);
		var crear = document.getElementById("crearCheck_"+id[1]);
		var modificar = document.getElementById("modificarCheck_"+id[1]);
		consultar.checked = false;
		crear.checked = false;
		modificar.checked = false;

		getFirstElementByName("permisos["+id[1]+"].consultar").value=false;
		getFirstElementByName("permisos["+id[1]+"].crear").value=false;
		getFirstElementByName("permisos["+id[1]+"].modificar").value=false;

	} else {
		var consultar = document.getElementById("consultarCheck_"+id[1]);
		consultar.checked = true;

		getFirstElementByName("permisos["+id[1]+"].consultar").value=true;
	}

}

function changePermisoCrear(ele){
	var id = (ele.id).split("_");
	if(ele.checked){
		var consultar = document.getElementById("consultarCheck_"+id[1]);
		var crear = document.getElementById("crearCheck_"+id[1]);
		consultar.checked = true;
		crear.checked = true;

		getFirstElementByName("permisos["+id[1]+"].consultar").value=true;
		getFirstElementByName("permisos["+id[1]+"].crear").value=true;
	} else {
		var crear = document.getElementById("crearCheck_"+id[1]);
		crear.checked = false;

		getFirstElementByName("permisos["+id[1]+"].crear").value=false;
	}
}

function changePermisoModificar(ele){
	var id = (ele.id).split("_");
	if(ele.checked){
		var consultar = document.getElementById("consultarCheck_"+id[1]);
		var modificar = document.getElementById("modificarCheck_"+id[1]);
		consultar.checked = true;
		modificar.checked = true;

		getFirstElementByName("permisos["+id[1]+"].consultar").value=true;
		getFirstElementByName("permisos["+id[1]+"].modificar").value=true;
	} else {
		var modificar = document.getElementById("modificarCheck_"+id[1]);
		modificar.checked = false;

		getFirstElementByName("permisos["+id[1]+"].modificar").value=false;
	}
}

function chequearSessionBusquedaDC( url, urlSessionExpired) {
	var check = chequearSession('./chequearSession.do');
	if( check == "false") {
		abreBusquedaEntidadesReg(url);
	}else{
		window.document.location.href = urlSessionExpired;
	}
}

/**
 * Metodo que valida los datos de la entidad Registral desde la pantalla de OFICINAS
 * @param menssageError - Mensaje de error a mostrar en caso de que falle la validacion de campos
 * @returns {Boolean} TRUE - la validacion es correcta
 * 					  FALSE - alguno de los campos comprobados no es correcto
 */
function validateEntidadRegistralOficina(menssageError){
	var result = true;
	//comprobamos los datos de la Entidad Registral
	var campoError = validateEntidadRegistral();

	//si algun campo es erroneo
	if(campoError){
		//seleccionamos la pestaña de Intercambio Registral
		intercambioRegistralOficinaClick();
		//posicionamos el cursor en el campo erroneo y generamos el alert
		changeStylesPestanaWithError(menssageError, campoError);
		result = false;
	}

	return result;
}

/**
 * Metodo que valida los datos de Unidad de Tramitacion y la Entidad Registral
 * para las UNIDADES ADMINISTRATIVAS
 *
 * @param menssageError - Mensaje de error en caso de que no pase la validacion de campos
 * @returns {Boolean}  TRUE - la validacion es correcta
 * 					  FALSE - alguno de los campos comprobados no es correcto
 */
function validateFormularioUnidadesAdm(menssageError){

	var result = true;
	//comprobamos los datos de la Entidad Registral
	var campoError = validateEntidadRegistral();

	//si la validacion de la Entidad Registral es correcta seguimos comprobando mas datos
	if(!campoError){
		var codeUnidadTramit = window.document.forms[0].codeUnidadTramit.value;
		var nameUnidadTramit = window.document.forms[0].nameUnidadTramit.value;
		//validamos la unidad de tramitacion
		if((!isEmpty(codeUnidadTramit) || !isEmpty(nameUnidadTramit))){
			if(isEmpty(codeUnidadTramit) || isEmpty(nameUnidadTramit)){
				//uno de los campos obligatorio no esta relleno
				choosebox(3,2);

				//comprobamos que campo es el erroneo
				if(isEmpty(codeUnidadTramit)){
					//seleccionamos el campo erroneo
					campoError = document.getElementById('codeUnidadTramit');
				}else{
					//seleccionamos el campo erroneo
					campoError = document.getElementById('nameUnidadTramit');
				}
			}
		}
	}
	//si algun campo es erroneo
	if(campoError){
		//seleccionamos la pestaña de Intercambio Registral
		intercambioRegistralUnidadClick();
		//posicionamos el cursor en el campo erroneo y generamos el alert
		changeStylesPestanaWithError(menssageError, campoError);
		result = false;
	}

	return result;
}

/**
 * Metodo que valida los datos de una ENTIDAD REGISTRAL (Codigo y Nombre)
 * @returns campo erroneo en caso de que alguno de los campos no pase la validacion
 */
function validateEntidadRegistral(){
	var codEntidadReg = window.document.forms[0].codEntidadReg.value;
	var nameEntidadReg = window.document.forms[0].nameEntidadReg.value;

	if(!isEmpty(codEntidadReg) || !isEmpty(nameEntidadReg)){
		if(isEmpty(codEntidadReg) || isEmpty(nameEntidadReg)){
			//uno de los campos obligatorio no esta relleno
			//comprobamos que campo es el erroneo
			if(isEmpty(codEntidadReg)){
				//seleccionamos el campo erroneo
				return document.getElementById('codEntidadReg');
			}else{
				//seleccionamos el campo erroneo
				return document.getElementById('nameEntidadReg');
			}
		}
	}
	return;
}

/**
 * Funcion que cambia el estilo a la pestaña que contiene los campos erroneos,
 * posiciona el cursor en el campo erroneo y genera el alert con el mensaje
 *
 * @param menssageError
 * @param campoError
 */
function changeStylesPestanaWithError(menssageError, campoError){
	//cambiamos el estilo a la pestaña
	choosebox(3,2);
	//mostramos mensaje de error
	alert(menssageError);
	//posicionamos el cursor en el campo errorneo
	campoError.focus();
	campoError.select();
	//retornamos false porque no pasa la validacion
}

function isBlank(x)
{
	return (x==undefined || x=='' || x==null);
}

function isEmpty(x)
{
	return isBlank(x) || x.length<1;
}


function cancelarBusqueda(){
	window.parent.close();
}

function getFirstElementByName(element_name) {
    var elements = document.getElementsByName(element_name);
    if (elements.length) {
        return elements[0];
    } else {
        return undefined;
    }
}
