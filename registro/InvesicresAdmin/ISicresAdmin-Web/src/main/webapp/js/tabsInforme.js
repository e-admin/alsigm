var active = null;

var tabs = new Array("informe","oficinasRegistro","usuarios","libros");

var tabArray = new Array(5);
tabArray[0] = null;
tabArray[1] = getStyleObj('tab1');
tabArray[2] = getStyleObj('tab2');
tabArray[3] = getStyleObj('tab3');
tabArray[4] = getStyleObj('tab4');


// Set up array of tabmiddle td element style property strings

var tabTextArray = new Array(5);
tabTextArray[0] = null;
tabTextArray[1] = getStyleObj('tabmiddle1');
tabTextArray[2] = getStyleObj('tabmiddle2');
tabTextArray[3] = getStyleObj('tabmiddle3');
tabTextArray[4] = getStyleObj('tabmiddle4');

function init(tabActive) {
	tabout(1);
	tabout(2);
	tabout(3);
	tabout(4);
	tabClick(tabActive);
	choosebox(tabActive,2);
	//verificarHabilitado();
	addControlCambios();
	actualizaEstado();
}

function tabover(tabnum) {
	if (tabnum != active) {
		tabcolor(tabnum, overbgcolor, overtextcolor);
	}
}

function tabout(tabnum) {

	if (tabnum != active) {
		tabcolor(tabnum, inactivebgcolor, inactivetextcolor);
	}
}

function tabcolor(tabnum, color1, color2) {
	tab = eval(tabArray[tabnum]);
	tabtext = eval(tabTextArray[tabnum]);
	tab.backgroundColor = color1;
	tabtext.color = color2;

	if (document.all) {
		tabtext.cursor = 'hand';
	} else {
		tabtext.cursor = 'pointer';
	}
}

function choosebox(num, perfiles ) {
if (perfiles == null )
	perfiles = 1;
if (document.all || document.getElementById) {
		if (active) {
			activetablayer = eval(tabArray[active]);
			activetabtext = eval(tabTextArray[active]);
			activetablayer.zIndex = 0;
			tabcolor(active,inactivebgcolor, inactivetextcolor);
	    }

	tablayer = eval(tabArray[num]);
	tabtext = eval(tabTextArray[num]);
	tablayer.zIndex = 11;
	tabcolor(num,activebgcolor, activetextcolor);

	active = num;

  }
} // fin choosebox

function getStyleObj(elem,parent) {
	if (document.layers) {
	    if (parent) {
	     return "document."+parent+".document."+elem;
	      }
	    else {
		return "document."+elem + ".style";
		     }

	  }
	    else if (document.all) {
		return "document.all."+elem + ".style";
	  }
	    else if (document.getElementById) {
		return "document.getElementById('"+elem+"').style";

	}
}


//FUNCIONES DE LA JSP
var filaActiva = 0;
var valorFilaActiva = "";


function verificarHabilitado(){
	if(document.forms[0].deshabilitado.checked){
		visibleInLineDiv("divFechaBaja");
	}
	else{
		ocultaDiv("divFechaBaja");
	}
}

function asignarUnidadAdministrativa(id ,codigo, nombre){
	document.forms[0].idUnidadAdministrativa.value = id;
	document.forms[0].codigoUnidadAdministrativa.value= codigo;
	document.forms[0].nombreUnidadAdministrativa.value= nombre;
	cambio();
}

//Funciones Busqueda en Popups

function chequearSessionBuscar(url, urlCheck, urlSessionExpired) {
	var check = chequearSession(urlCheck);
	if( check == "false") {
		abreListaOficinas( url );
	} else {
		window.document.location.href = urlSessionExpired;
	}
}

function chequearSessionBuscarOficinas(url, urlCheck, urlSessionExpired) {
	var check = chequearSession(urlCheck);
	if( check == "false") {
		abreListaOficinas( url );
	} else {
		window.document.location.href = urlSessionExpired;
	}
}

//Funciones de Perfiles
function addPerfil(id,codigo,nombre){
	document.forms[0].idPerfil.value= id;
	document.forms[0].nombrePerfil.value= nombre;
	var url= document.forms[0].urlAddPerfil.value;
	llamadaAction(url);
}

function eliminarPerfil(url,fila,msg,nombre,estado){
	if( confirm( msg + " '" + nombre + "' ?")) {
		document.forms[0].posPerfil.value= fila;
		document.forms[0].estadoPerfil.value= estado;
		llamadaAction(url);
	}
}

//Funciones de Libros
function addLibro(id,codigo,nombre){
	document.forms[0].idLibro.value= id;
	document.forms[0].nombreLibro.value= nombre;
	var url= document.forms[0].urlAddLibro.value;
	llamadaAction(url);
}

function eliminarLibro(url,fila,msg,nombre,estado){
	if( confirm( msg + " '" + nombre + "' ?")) {
		document.forms[0].posLibro.value= fila;
		document.forms[0].estadoLibro.value= estado;
		llamadaAction(url);
	}
}


//Funciones de Oficinas
function addOficina(id,codigo,nombre){
	document.forms[0].idOficina.value= id;
	document.forms[0].codigoOficina.value= codigo;
	document.forms[0].nombreOficina.value = nombre;
	var url= document.forms[0].urlAddOficina.value;
	llamadaAction(url);
}

function eliminarOficina(url,fila,msg,nombre,estado){
	if( confirm( msg + " '" + nombre + "' ?")) {
		document.forms[0].posOficina.value= fila;
		document.forms[0].estadoOficina.value= estado;
		llamadaAction(url);
	}
}

//Funciones de Documentos
function addDocumento(url, msgObligatorio){
	if(document.forms[0].nombreDocumento.value != ""){
		llamadaAction(url);
	}
	else{
		alert(msgObligatorio);
		document.forms[0].nombreDocumento.focus();
	}
}

function editarDocumento(fila, valor){
	if(filaActiva != 0)	cancelarEdicionDocumento();
	filaActiva = fila;
	valorFilaActiva = valor;

	visibleInLineDiv("txtDocumento_" + String(fila));
	visibleInLineDiv("btnAceptar_" + String(fila));
	visibleInLineDiv("btnCancelar_" + String(fila));

	ocultaDiv("labelDocumento_" + String(fila));
	ocultaDiv("btnEliminar_" + String(fila));
	ocultaDiv("btnEditar_" + String(fila));
	ocultaDiv("colNuevoDocumento");
}

function aceptarEdicionDocumento(url,msgObligatorio,estado){

	if(document.getElementById("editarNombreDocumento_" + String(filaActiva)).value != ""){
		document.forms[0].posDocumento.value= filaActiva;
		document.forms[0].estadoDocumento.value= estado;
		document.forms[0].nombreDocumento.value= document.getElementById("editarNombreDocumento_" + String(filaActiva)).value;
		llamadaAction(url);
	}
	else{
		alert(msgObligatorio);
		document.getElementById("editarNombreDocumento_" + String(filaActiva)).focus();
	}
}

function cancelarEdicionDocumento(ocultarColNuevo){
	ocultaDiv("txtDocumento_" + String(filaActiva));
	ocultaDiv("btnAceptar_" + String(filaActiva));
	ocultaDiv("btnCancelar_" + String(filaActiva));

	visibleInLineDiv("labelDocumento_" + String(filaActiva));
	visibleInLineDiv("btnEliminar_" + String(filaActiva));
	visibleInLineDiv("btnEditar_" + String(filaActiva));
	visibleInLineDiv("colNuevoDocumento");

	document.getElementById("editarNombreDocumento_" + String(filaActiva)).value = valorFilaActiva;
	filaActiva = 0;
	valorFilaActiva = "";

}

function eliminarDocumento(url,fila,msg,nombre,estado){
	if( confirm( msg + " '" + nombre + "' ?")) {
		document.forms[0].posDocumento.value= fila;
		document.forms[0].estadoDocumento.value= estado;
		llamadaAction(url);
	}
}
