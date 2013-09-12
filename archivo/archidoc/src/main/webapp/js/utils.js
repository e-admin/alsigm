//var aceleradores = new Array();

function showHideDivSimple(NDiv)
{
  var divN="div"+NDiv;

  if ( (document.getElementById(divN).style.display == 'none')
  		|| (document.getElementById(divN).style.display == '') )
    document.getElementById(divN).style.display = 'block';
  else
    document.getElementById(divN).style.display = 'none';
}

function joinArray(array, token) {
	var result = "";
	for (var i in array) {
		//alert(i);
		if (result.length == 0)
			result += array[i];
		else
			result += token + array[i] ;
	}
	//alert(result);
	return result;
}

function getUniqueId()
{
    return Math.floor(Math.random() * new Date().getTime());
}

function showSelects()
{
    var selectElements =document.getElementsByTagName("SELECT");
    for (var i=0; i<selectElements.length; i++)
        xShow(selectElements[i]);
}

function hideSelects()
{
    var selectElements =document.getElementsByTagName("SELECT");
    for (var i=0; i<selectElements.length; i++)
        xHide(selectElements[i]);
}

/***************************************************************/
/*******	Gestion de cookies		****************************/

function setCookie(nombre, valor) {
  document.cookie = nombre + "=" + escape(valor);
}

function getCookie(nombre) {
  var buscamos = nombre + "=";
  if (document.cookie.length > 0) {
    i = document.cookie.indexOf(buscamos);
    if (i != -1) {
      i += buscamos.length;
      j = document.cookie.indexOf(";", i);
      if (j == -1)
		j = document.cookie.length;
      return unescape(document.cookie.substring(i,j));
    }
  }
  return null;
}

function removeCookie(nombre) {
	var exp = new Date();
	exp.setTime (exp.getTime() - 1);
	var valor = getCookie (nombre);
	document.cookie = nombre + "=" + valor + "; expires=" + exp.toGMTString();
}

/***************************************************************/


/***************************************************************/
/*******	Contenido dividido en pestañas		****************/

// elem debe ser el id del div que contiene lo que
// se debe mostrar en la pestaña
function Tab(name, elem) {
	this.name = name;
	this.tabContentElem = elem;

	this.setVisible = function(visible) {
		var tabObj = document.getElementById(this.tabContentElem);
		if (tabObj)
			tabObj.style.display = visible?'block':'none';
		tabObj = document.getElementById('p'+this.name);
		if (tabObj != null)
			tabObj.className= visible?'tabActual':'tabSel';
		tabObj = document.getElementById('t'+this.name);
		if (tabObj != null)
			tabObj.className= visible?'textoPestana':'textoPestanaSel';
	};
}


function TabPanel(name) {
	this.name = name;
	this.tabs = new Array();
	this.selectedTab = null;
	this.addTab = function (tab) {
		this.tabs[tab.name] = tab;
	};
	this.saveSelectedTab = function(tabName) {
		setCookie(this.name, tabName);
	};
	this.showTab = function(tabName) {
		if (this.selectedTab && tabName == this.selectedTab.name) return;
		var tab = this.tabs[tabName];
		if (tab) {
			var tabContentObj = document.getElementById(tab.tabContentElem);
			if (tabContentObj)
				for (var aTab in this.tabs) {
					var isVisible = (aTab == tabName)?true:false;
					this.tabs[aTab].setVisible(isVisible);
					if (isVisible) this.saveSelectedTab(tabName);
				}
			this.selectedTab = tab;
		}
	};
	this.getSelectedTab = function () {
		return this.selectedTab.name;
	};
}
/***************************************************************/


function switchDivVisibility(NDiv) {
	var divN="div"+NDiv;
	var imgN="img"+NDiv;
	var divObj = document.getElementById(divN);
	if (divObj != null) {
		var isOpen = false;
		var openAttribute = divObj.getAttribute("isOpen");
		if (openAttribute && openAttribute == "true")
			isOpen = true;
		isOpen = !isOpen;
		divObj.style.display = isOpen?'block':'none';
		var imgObj = document.getElementById(imgN);
		if (imgObj != null)
			imgObj.src = imgObj.src.substring(0,imgObj.src.lastIndexOf("/")) + (isOpen?'/up.gif':'/down.gif');
		divObj.setAttribute("isOpen",""+isOpen);
	}
}

function switchVisibility(nDiv) {
	var divObj = document.getElementById(nDiv);

	if (divObj != null) {
		if (divObj.style.display == 'none') {
			divObj.style.display = 'block';
		}
		 else {
			divObj.style.display = 'none';
		}
	}
}

/***************************************************************/
/*******	Seleccionar/Deseleccionar checkboxes	************/

function seleccionarCheckboxSet(checkboxSet)
{
    if (checkboxSet)
    {
    	if (!checkboxSet.length )
    		checkboxSet.checked = true;
    	else
    		for (var i=0;i<checkboxSet.length;i++)
    			checkboxSet[i].checked = true;
	}
}

function deseleccionarCheckboxSet(checkboxSet)
{
    if (checkboxSet)
    {
    	if (!checkboxSet.length)
    		checkboxSet.checked = false;
    	else
    		for (var i=0;i<checkboxSet.length;i++)
    			checkboxSet[i].checked = false;
	}
}

/***************************************************************/
/*******	Comprobación checkbox/radio seleccionado	********/

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

function numElementsSelected(buttonSet) {
	if (!buttonSet.length)
		return buttonSet.checked ? 1 : 0;
	else {
		var numSelected = 0;
		for(var i=0;i<buttonSet.length;i++)
			if (buttonSet[i].checked) numSelected ++;
		return numSelected;
	}
}

function valueElementsSelected(buttonSet) {
	if (!buttonSet.length)
		return buttonSet.checked ? buttonSet.value : null;
	else {
		var selected = new Array();
		for(var i=0;i<buttonSet.length;i++){
			if (buttonSet[i].checked)
				selected[i] = buttonSet[i].value;
		}
		return selected;
	}
}

/***************************************************************/

/***************************************************************/
/*******	Mostrar capa de trabajo en progreso		************/

/** Método para cambiar dinámicamente el texto de un campo **/
function dynamicTextChange(elementId, newText)
{
	var element=document.getElementById(elementId);

	if (element){

		if (document.all)
		{
			// IE 4 o superior
			element.innerText=newText;
		}

		if (document.layers)
		{
			// Netscape 4 o anterior
			element.innerText=newText;
		}

		if (document.getElementById && !document.all && element.firstChild)
		{
			// Mozilla
			element.firstChild.nodeValue=newText;
		}
	}
}

function showWorkingDiv(title,message,message2) {
	    var id = "workInProgress";
		var workInProgress = xGetElementById(id);
		var clientWidth = xClientWidth();
		var clientHeight = xClientHeight();
		xHide(workInProgress);
		xDisplay(workInProgress, 'block');
		xHeight('bg'+id,clientHeight);
		xWidth('bg'+id,clientWidth);
		var top = (clientHeight - xHeight(workInProgress))/2;
		var left = (clientWidth - xWidth(workInProgress))/2;
		xMoveTo(workInProgress, left, top);
		var selectElements =document.getElementsByTagName("SELECT");
		for (var i=0; i<selectElements.length; i++)
				selectElements[i].style.visibility = "hidden";

		/* Ocultar los select del frame de contenido*/
		var frameBasefrm = document.getElementById("basefrm");
		if (frameBasefrm){
			var doc = getDocumentFromIframe(frameBasefrm);
			if (!doc) return;

			var baseFrmSelectElements = doc.getElementsByTagName("SELECT");
			if (baseFrmSelectElements){
				for (var i=0; i<baseFrmSelectElements.length; i++) {
					baseFrmSelectElements[i].style.visibility = "hidden";
				}
			}
		}

		if (title) {
			dynamicTextChange('wipTitle',title);
		}

		if (message) {
			dynamicTextChange('wipContent',message);
		}

		if (message2) {
			dynamicTextChange('wipContent2',message2);
		} else {
			dynamicTextChange('wipContent2','');
		}

		xShow('bg'+id);
		xShow(workInProgress);
	}

function showWorkingDivIFrame(title,message,message2) {
    var id = "workInProgressIFrame";
	var workInProgress = xGetElementById(id);
	var clientWidth = xClientWidth();
	var clientHeight = xClientHeight();
	xHide(workInProgress);
	xDisplay(workInProgress, 'block');
	xHeight('bg'+id,clientHeight);
	xWidth('bg'+id,clientWidth);
	var top = (clientHeight - xHeight(workInProgress))/2;
	var left = (clientWidth - xWidth(workInProgress))/2;
	xMoveTo(workInProgress, left, top);
	var selectElements =document.getElementsByTagName("SELECT");
	for (var i=0; i<selectElements.length; i++)
			selectElements[i].style.visibility = "hidden";

	/* Ocultar los select del frame de contenido*/
	var frameBasefrm = document.getElementById("basefrm");
	if (frameBasefrm){
		var doc = getDocumentFromIframe(frameBasefrm);
		if(!doc) return;
		var baseFrmSelectElements = doc.getElementsByTagName("SELECT");
		if (baseFrmSelectElements){
			for (var j=0; j<baseFrmSelectElements.length; j++) {
				baseFrmSelectElements[j].style.visibility = "hidden";
			}
		}
	}

	if (title) {
		dynamicTextChange('wipTitleIFrame',title);
	}

	if (message) {
		dynamicTextChange('wipContentIFrame',message);
	}

	if (message2) {
		dynamicTextChange('wipContent2IFrame',message2);
	} else {
		dynamicTextChange('wipContent2IFrame','');
	}

	xShow('bg'+id);
	xShow(workInProgress);

	var frame=parent.document.getElementById("stateWipFrame");

	var doc;
	if(frame.contentDocument) doc=frame.contentDocument;
	else if(frame.contentWindow.document) doc=frame.contentWindow.document;
	else return;

	if(doc.getElementById("progressBar")){
		doc.getElementById("progressBar").style.width=0;
	}
}

function getDocumentFromIframe(objIframe){
	if(objIframe.contentDocument){
		return objIframe.contentDocument;
	}
	else if(objIframe.contentWindow && objIframe.contentWindow.document){
		return objIframe.contentWindow.document;
	}
	else if(objIframe.document){
		return objIframe.document;
	}
	return null;
}


function hideWorkingDiv() {
  	var id = "workInProgress";
  	var selectElements =document.getElementsByTagName("SELECT");
	for (var i=0; i<selectElements.length; i++)
			selectElements[i].style.visibility = "visible";

	/* Mostrar los select del frame de contenido*/
	var frameBasefrm = document.getElementById("basefrm");
	if (frameBasefrm){
		var doc = getDocumentFromIframe(frameBasefrm);
		if(!doc) return;
		var baseFrmSelectElements = doc.getElementsByTagName("SELECT");
		if (baseFrmSelectElements){
			for (var j=0; j<baseFrmSelectElements.length; j++) {
				baseFrmSelectElements[j].style.visibility = "visible";
			}
		}
	}

	xHide(id);
	xHide('bg'+id);
}

function hideWorkingDivIFrame() {
  	var id = "workInProgressIFrame";
  	var selectElements =document.getElementsByTagName("SELECT");
	for (var i=0; i<selectElements.length; i++)
			selectElements[i].style.visibility = "visible";

	/* Mostrar los select del frame de contenido*/
	var frameBasefrm = document.getElementById("basefrm");
	if (frameBasefrm){
		var doc = getDocumentFromIframe(frameBasefrm);
		if(!doc) return;
		var baseFrmSelectElements = doc.getElementsByTagName("SELECT");
		if (baseFrmSelectElements){
			for (var j=0; j<baseFrmSelectElements.length; j++) {
				baseFrmSelectElements[j].style.visibility = "visible";
			}
		}
	}

	xHide(id);
	xHide('bg'+id);
}

window.anchoPorcentajeAnterior=0;
window.iteracionesSinVariar=0;

function initProgressBar(resetProgressBarURL){
	//reset porcentaje de progreesBar
	var frame=parent.document.getElementById("stateWipFrame");
	frame.style.visibility="visible";
	frame.src=resetProgressBarURL;

	var doc=getDocumentFromIframe(frame);
	if(doc && doc.getElementById("progressBar")){
		doc.getElementById("progressBar").style.width="0px";
	}

	window.top.document.getElementById("cancelReportButton").style.display="block";
	deleteErrors();

}

function endGeneracion(){
	var frame=parent.document.getElementById("stateWipFrame");
	if (frame && frame.src && frame.src!="javascript:''"){
		frame.style.visibility="hidden";
		frame.src="javascript:''";
		window.top.document.getElementById("cancelReportButton").style.display="none";

		var selectElements =document.getElementsByTagName("SELECT");
		for (var i=0; i<selectElements.length; i++)
			selectElements[i].style.visibility = "visible";
		if (window.top.hideWorkingDivIFrame) {
			window.top.hideWorkingDivIFrame();
		}
	}
}

function deleteErrors(){
	var iframe=parent.document.getElementById("basefrm");
	var doc2;
	if(iframe.contentDocument) doc2=iframe.contentDocument;
	else if(iframe.contentWindow.document) doc2=iframe.contentWindow.document;
	else return;

	var errores_div=doc2.getElementById("barra_errores");
	if(errores_div){
		for(var i=0;i<errores_div.childNodes.length;i++){
			if(i<errores_div.childNodes.length){
				errores_div.removeChild(errores_div.childNodes[i]);
				i--;
			}
		}
	}
}

//function muestraBotonExportarSiProcede(){
//	var iframe=parent.document.getElementById("basefrm");
//	var doc;
//	if(iframe.contentDocument) doc=iframe.contentDocument;
//	else if(iframe.contentWindow.document) doc=iframe.contentWindow.document;
//	else return;
//	var errores_div=doc.getElementById("barra_errores");
//	if(errores_div){
//		var errores_ul=errores_div.getElementsByTagName("ul");
//		if(errores_ul.length>0){
//			var elem_ul=errores_ul[0];
//			var listaLi=elem_ul.getElementsByTagName("li")
//			for(var i=0;i<listaLi.length;i++){
//				var elem=listaLi[i];
//				var texto=elem.innerText;
//				if(!texto) texto=elem.textContent;
//				if(texto.indexOf("a")!=-1){
//					doc.getElementById("botonBusquedaToInforme").style.display="";
//					doc.getElementById("sepBotonBusquedaToInforme").style.display="";
//				}
//			}
//		}
//	}
//}

function printProperties(obj){
	var cad="";
	for(var prop in obj){

		if(prop.substring(0,2)=="on") continue;
		cad+=prop+"="+obj[prop]+"\n";
	}
	alert(cad);
}

/***************************************************************/


/***************************************************************/
/******* Comprobar cambios en las fechas	************/

function checkFechaComp(form)
{
	var fechaComp = document.getElementById('fechaComp');
 	if ((fechaComp!=null) && (fechaComp != '') && (fechaComp != 'undefined')) {
		if (form.fechaComp.value == "[..]")
		{
			document.getElementById("idFecha").style.display = 'none';
			document.getElementById("idRangoFechas").style.display = 'block';
			if(!document.all){
				document.getElementById("idRangoFechas").style.display = 'table-cell';
			}

			checkFechaFormato(document.getElementById('fechaIniFormato'), "idFechaIni");
			checkFechaFormato(document.getElementById('fechaFinFormato'), "idFechaFin");
		}
		else
		{
			document.getElementById("idRangoFechas").style.display = 'none';
			document.getElementById("idFecha").style.display = 'block';
			if(!document.all){
				document.getElementById("idFecha").style.display = 'table-cell';
			}

			checkFechaFormato(document.getElementById("fechaFormato"), "idFecha");
			//checkFechaFormato(document.getElementById("fechaIniFormato"), "idFecha");
		}
	}
}

function checkFechaCompSuffix(form, field, suffix)
{
	var fechaComp = document.getElementById(field);
 	if ((fechaComp!=null) && (fechaComp != '') && (fechaComp != 'undefined')) {
		if (fechaComp.value == "[..]")
		{
			document.getElementById("idFecha" + suffix).style.display = 'none';
			document.getElementById("idRangoFechas"+ suffix).style.display = 'block';
			if(!document.all){
				document.getElementById("idRangoFechas"+ suffix).style.display = 'table-cell';
			}

			checkFechaFormatoSuffix(document.getElementById("fechaIniFormato" + suffix), "idFechaIni", suffix);
			checkFechaFormatoSuffix(document.getElementById("fechaFinFormato" + suffix), "idFechaFin", suffix);
		}
		else
		{
			document.getElementById("idRangoFechas"+ suffix).style.display = 'none';
			document.getElementById("idFecha"+ suffix).style.display = 'block';
			if(!document.all){
				document.getElementById("idFecha" + suffix).style.display = 'table-cell';
			}

			var fechaFormato = document.getElementById("fechaFormato" + suffix);

			checkFechaFormatoSuffix(fechaFormato, "idFecha", suffix);
		}
	}
}

function checkFechaFormatoSuffix(formato, prefix, suffix)
{
	var campoA = document.getElementById(prefix + "AValor" + suffix);
	var campoM = document.getElementById(prefix + "MValor" + suffix);
	var campoD = document.getElementById(prefix + "DValor" + suffix);
	var campoS = document.getElementById(prefix + "SValor" + suffix);

	if (formato.value == 'AAAA')
	{
	    if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'none';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'none';
		if(!document.all){
			 if (campoA) campoA.style.display = 'table-cell';
		}
	}
	else if (formato.value == 'MMAAAA')
	{
		if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'block';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'none';
		if(!document.all){
			 if (campoA) campoA.style.display = 'table-cell';
			 if (campoM) campoM.style.display = 'table-cell';
		}
	}
	else if (formato.value == 'DDMMAAAA')
	{
		if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'block';
		if (campoD) campoD.style.display = 'block';
		if (campoS) campoS.style.display = 'none';
		if(!document.all){
			 if (campoA) campoA.style.display = 'table-cell';
			 if (campoM) campoM.style.display = 'table-cell';
			 if (campoD) campoD.style.display = 'table-cell';
		}
    }
	else
	{
		if (campoA) campoA.style.display = 'none';
		if (campoM) campoM.style.display = 'none';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'block';
		if(!document.all){
			 if (campoS) campoS.style.display = 'table-cell';
		}
	}
}

function checkFechaFormato(formato, prefix)
{
	var campoA = document.getElementById(prefix + "AValor");
	var campoM = document.getElementById(prefix + "MValor");
	var campoD = document.getElementById(prefix + "DValor");
	var campoS = document.getElementById(prefix + "SValor");

	if (formato.value == 'AAAA')
	{
	    if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'none';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'none';
		if(!document.all){
  			if (campoA) campoA.style.display = 'table-cell';
  		}
	}
	else if ((formato.value == 'MMAAAA') || (formato.value == 'AAAAMM'))
	{
		if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'block';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'none';
		if(!document.all){
  			if (campoA) campoA.style.display = 'table-cell';
  			if (campoM) campoM.style.display = 'table-cell';
  		}
	}
	else if ((formato.value == 'DDMMAAAA') || (formato.value == 'AAAAMMDD'))
	{
		if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'block';
		if (campoD) campoD.style.display = 'block';
		if (campoS) campoS.style.display = 'none';
		if(!document.all){
  			if (campoA) campoA.style.display = 'table-cell';
  			if (campoM) campoM.style.display = 'table-cell';
  			if (campoD) campoD.style.display = 'table-cell';
  		}
//    }else if (formato.value == 'AAAAMMDD'){
//		if (campoA) campoA.style.display = 'block';
//		if (campoM) campoM.style.display = 'block';
//		if (campoD) campoD.style.display = 'block';
//		if (campoS) campoS.style.display = 'none';
//		if(!document.all){
//  			if (campoA) campoA.style.display = 'table-cell';
//  			if (campoM) campoM.style.display = 'table-cell';
//  			if (campoD) campoD.style.display = 'table-cell';
//  		}
    }else /*if (formato.value == 'S')*/{
		if (campoA) campoA.style.display = 'none';
		if (campoM) campoM.style.display = 'none';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'block';
		if(!document.all){
  			if (campoS) campoS.style.display = 'table-cell';
  		}
	}
}
/***************************************************************/

function popup(url, name, width, height)
{
	if (width == undefined) width = 700;
	if (height == undefined) height = 400;

	var left = (screen.width-width)/2;
	if (left < 0) left = 0;

	var top = (screen.height-height)/2;
	if (top < 0) top = 0;

	return window.open(url, name,
	    "height=" + height
	    + ",width=" + width
	    + ",top=" + top
	    + ",left=" + left
	    + ",location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=yes,status=yes");
}

function popupHelp(url)
{
	popup(url, "_blank",1000,600);
}

function removeAllOptions(options)
{
    while (options.length > 0){
        options[0].parentNode.removeChild(options[options.length-1]);
    }

}

function unescapeHTML(text)
{
    if (text)
    {
        var div = document.createElement('div');
        div.innerHTML = text.replace(/<\/?[^>]+>/gi, '');
        return div.childNodes[0] ? div.childNodes[0].nodeValue : '';
    }
    else
        return text;

}

function trim(cadena)
{
	if (cadena=="") return cadena;
	for(var i=0; i<cadena.length; )
	{
		if(cadena.charAt(i)==" ")
			cadena=cadena.substring(i+1, cadena.length);
		else
			break;
	}

	for(var i=cadena.length-1; i>=0; i=cadena.length-1)
	{
		if(cadena.charAt(i)==" ")
			cadena=cadena.substring(0,i);
		else
			break;
	}
	return cadena;

}

function validarEntero(valor){

     if (isNaN(valor)) {
           return "";
     }else{
           return valor;
     }
}

// Funcion que devuelve el control cuyo identificador
// coincide con idControl
function getControl(idControl) {
	if (typeof(idControl) == "string") {

		var control = document.getElementById(idControl);

		if (typeof(control) == "undefined" || control == null)
			if (document.forms.length != 0)
				control = document.forms[0][idControl];
		if (typeof(control) == "undefined" || control == null) {
			return null;
		}
		else {
			return control;
		}
	}
	return idControl;
}


// Funcion que permite agregar un acelerador Javascript
function nuevoAcelerador(aceleradorId, controlId, metodoId, aceleradorTitle) {

	alert('nuevoaceledaro');
	var aceleradores2 = new Array();
	aceleradores2['A']='A';
	alert('aceleradores2.length:'+aceleradores2.length);

	alert('nuevoacelerador');
	// Se crea un nuevo objeto acelerador con el id del control y el metodo a ejecutar
	var acelerador = new Object();
	acelerador.controlId = controlId;
	acelerador.metodoId = metodoId;
	acelerador.tab = -1;

	alert(aceleradorId);

	alert(aceleradorId.charCodeAt(0));
	alert(Number(String.fromCharCode(aceleradorId)));
	if ((aceleradores[aceleradorId] == null) || (aceleradores[aceleradorId] == "undefined")) {
		alert("no lo encuentra");
		aceleradores[aceleradorId.charCodeAt(0)] = acelerador;
		alert(aceleradores);
	} else {
		alert("lo encuentra");
	}
	//aceleradores[aceleradorId].push(acelerador);
	alert(aceleradores.length);

	// Aniadir tecla aceleradora al tooltip
/*	var control = getControl(controlId);
	if (control != null) {
		if (aceleradorTitle!=null)
			control.title += ("  [ Ctrl + Mayus + " + aceleradorTitle + " ]");
		else
			control.title += ("  [ Ctrl + Mayus + " + aceleradorId + " ]");
	}*/
}

// Funcion que detecta la pulsacion de una tecla
function onKeypress(evt)
{
	var e = new xEvent(evt);
	return onKeyEvent(evt, e);
}

// Funcion que detecta la pulsacion de una tecla (keyup)
function onKeyup(evt)
{
	var e = new xEvent(evt);
	return onKeyEvent(evt, e);
}

// Funcion que detecta la pulsacion de una tecla (keydown)
function onKeydown(evt)
{
 	var e = new xEvent(evt);
	return onKeyEvent(evt, e);
}

// Funcion que maneja los eventos de aceleradores
function onKeyEvent(evt, xe)
{
	//alert('keyevent');
	// No permitir la propagacion ni que se ejecute la funcion por defecto
	xStopPropagation(evt);
    xPreventDefault(evt);

    // Obtener el codigo de la tecla
    var charCode =	(evt.charCode) ? evt.charCode :
						((evt.keyCode) ? evt.keyCode :
						((evt.which) ? evt.which : 0));

	// Comprobar que esta presionada solo la tecla CTRL
	if ((evt.ctrlKey==true)&&(evt.shiftKey==true)&&(evt.altKey==false))
	{
		//alert ("entra");
		//alert(aceleradores);
		//alert(aceleradores.length);
		// Obtener el acelerador, si existe
		//alert(charCode);
	 	var acelerador = aceleradores[charCode];
	 	//alert('acelerador.control' + acelerador.controlId);

	 	if ((acelerador != null) && (acelerador != "undefined"))
	 	{

 			// Obtener el objeto, si existe
	 		var objeto = document.getElementById(acelerador.controlId);
	 		//alert(objeto);
	 		if ((objeto!=null) && (objeto!="undefined") && (objeto!="")) {


	 				alert("objeto." + acelerador.metodoId + "();");
	 				eval("objeto." + acelerador.metodoId + "();");
/*	 				eval(acelerador.metodoId);
	 				alert('después del eval');
objeto.onClick();*/
	 		}
	 	}
	}
    return true;
}

// Funcion generica de inicio, todas las pantallas deberan
// implementar un inicializarPantalla
function inicializarAceleradores() {
	//alert('inicio aceleradores');
	xAddEventListener(document, 'keypress', onKeypress, false);
	xAddEventListener(document, 'keyup', onKeyup, false);
	//alert('fin aceleradores');
}


/**
 *	Limita cualquier campo al número de caracteres especificado.
 *  Esta función debería ser llamada desde el evento onkeypress y onkeychange
 *  @param Objecto campo Referencia al Campo (se le suele pasar con this)
 *  @param Entero numCaracteres Número máximo de caracteres del campo.<b>
 *  @param Booleano mostarMensaje Si se le pasa true, muestra un alert.
 *  La variable mostrar mensaje solo debería recibir true si se llama desde
 *  el evento onchange.
 */
function maxlength(campo, numCaracteres, mostrarMensaje) {
	if (campo != null){
		if (campo.value.length > numCaracteres){
			campo.value = campo.value.substr(0, numCaracteres);
			if(mostrarMensaje){
				alert("La longitud del campo excede el tamaño permitido (" + String(numCaracteres) + " caracteres). El texto se truncará");
			}
		}
	}
}


/**
 * Establece la propiedad 'display' a 'block' o 'none', según el parámetro visible
 * @param String idCampo Identificador del Campo
 * @param boolean visible Si true display  block, si false display:none
 */
function setVisible(idCampo, visible){
	if(visible){
		document.getElementById(idCampo).style.display = 'block';
	}
	else{
		document.getElementById(idCampo).style.display = 'none';
	}
}

/**
 * Establece la propiedad 'value' del campo con el valor especificado
 * @param String idCampo Identificador del Campo
 * @param String valor valor a Establecer
 */
function setValor(idCampo, valor){
	document.getElementById(idCampo).value = valor;
}

/**
 * Obtiene la propiedad 'value' del campo  especificado
 * @param String idCampo Identificador del Campo
 */
function getValor(idCampo){
	var elemento = document.getElementById(idCampo);
	if(elemento){
		window.status = "";
		return elemento.value;

	}
	else{
		window.status = "Objeto " + idCampo +" No Encontrado";
		return "";
	}
}

/**
 * Establece la propiedad 'disabled' a true o false, según el parámetro habilitado
 * @param String idCampo Identificador del Campo
 * @param boolean habilitado Si true disabled=false, si false disabled=true
 */
function setHabilitado(idCampo, habilitado){
	document.getElementById(idCampo).disabled = !habilitado;
}

/**
 * Comprueba la propieda disabled
 * @param String idCampo Identificador del Campo
 */
function isHabilitado(idCampo){
	return !document.getElementById(idCampo).disabled;
}

/**
 * Establece la propiedad 'readonly' a true o false, según el parámetro soloLectura
 * @param String idCampo Identificador del Campo
 * @param boolean soloLectura Si true readOnly=false, si false readOnly=true
 */
function setSoloLectura(idCampo,soloLectura){
	document.getElementById(idCampo).readOnly = soloLectura;
}

/**
 * Establece el método del formulario
 * @param String valor Nombre del método
 */
function setMetodo(nombreMetodo){
	setValor("method",nombreMetodo);
}


/**
 * Envía el formulario especificado
 * @param String idFormulario Identificador del formulario
 */
function enviarFormulario(idFormulario){
	document.getElementById(idFormulario).submit();
}

/**
 * Envía el formulario especificado
 * @param String idFormulario Identificador del formulario
 */
function enviarFormulario(idFormulario, accion){
	document.getElementById("method").value = accion;
	document.getElementById(idFormulario).submit();
}

/**
 * Envía el formulario especificado
 * @param String idFormulario Identificador del formulario
 */
function enviarFormulario(idFormulario, accion, title, message){
	document.getElementById("method").value = accion;

	if (window.top.showWorkingDiv) {
		window.top.showWorkingDiv(title, message);
	}
	document.getElementById(idFormulario).submit();
}


/**
 * Comprueba si el campo está checkeado.
 * Solo válido para campos de tipo radio y tipo checkbox
 * @param String idCampo Identificador del Campo
 * @return boolean true si está checkeado, en caso contrario false.
 */
function esChecked(idCampo){
	return document.getElementById(idCampo).checked;
}

/**
 * Comprueba si el campo está vacío, o es nulo.
 * @param Strin idCampo Identificador del Campo.
 */
function esVacio(idCampo){
	var valor = getValor(idCampo);
	if(valor == null || valor == "" || valor == " ") return true;

	return false;
}

/**
 * Se encarga de transforma un codigo HTML pasado al createElement
 * en codigo javascript valido para firefox/mozilla
 */
 function creaElemento(datosElemento){
 	//si el elemento contiene "<" -> el parametro es codigo HTML
 	//sino se crea y se devuelve el elemento creado
 	datosElemento=trim(datosElemento);

 	var pos=datosElemento.indexOf("<");
 	if(pos==-1 || document.all){ return document.createElement(datosElemento); }

 	//es codigo html y no estamos usando IExplorer

 	//obtener el tipo de parametro a crear (finaliza con el caracter espacio o con "/")
 	var posSeparador=datosElemento.indexOf(" ");
 	if(posSeparador==-1){
 		posSeparador=datosElemento.indexOf("/");
 		if(posSeparador==-1){
 			posSeparador=datosElemento.indexOf(">");
 			if(posSeparador==-1){ return null; }
 		}
 	}
 	var tipoElemento=datosElemento.substring(1,posSeparador);
 	var elemento=document.createElement(tipoElemento);

 	//trim de la cadena restante
 	var cadenaAtributos=trim(datosElemento.substring(posSeparador+1));

 	//recorrer la cadena restante y extraer los atributos nombreAtributo="'valorAtributo'"
 	//Se detecta el final de un atributo, por las comilla simple.
 	//para obtener la cadena completa de un atributo hay que buscar las dos comillas
 	//tener en cuenta que esta no puede estar precedida de una "\" (caracter de escape)

 	do{
 		var posComilla1=getPosComillaSimple(0,cadenaAtributos);
 		var posComilla2=0;
	 	var posSeparador=0;

	 	if(posComilla1!=-1){
	 		posComilla2=getPosComillaSimple(posComilla1+1,cadenaAtributos);
	 		if(posComilla2==-1){
	 			return elemento;
	 		}
	 		posComilla2=posComilla2+posComilla1+1;
	 		posSeparador=posComilla2+1;
 		}else{
 			var posSeparador=cadenaAtributos.indexOf(" ");
	 		if(posSeparador==-1){
	 			posSeparador=cadenaAtributos.indexOf("/");
		 		if(posSeparador==-1){
		 			posSeparador=cadenaAtributos.indexOf(">");
		 		}
	 		}
	 	}

	 	if(posSeparador!=-1){
		 	var cadenaAtrib=trim(cadenaAtributos.substring(0,posSeparador));
		 	cadenaAtributos=trim(cadenaAtributos.substring(posSeparador));

		 	//valor de atributo solo puede estar separado por "="
		 	//para evitar el problema con los espacios extras se utiliza trim
		 	var posIgual=cadenaAtrib.indexOf("=");
		 	if(posIgual==-1){ return elemento; }

		 	var nombreAtributo=trim(cadenaAtrib.substring(0,posIgual));
		 	var valorAtributo="";
		 	if(posComilla1==-1){
		 		valorAtributo=trim(cadenaAtrib.substring(posIgual+1));
		 		valorAtributo=valorAtributo.replace(/'/g,"");
		 	}else{
		 		valorAtributo=trim(cadenaAtrib.substring(posComilla1+1,posComilla2));
		 	}
		 	//alert(nombreAtributo+'='+valorAtributo);
		 	elemento.setAttribute(nombreAtributo,valorAtributo);
		 }
 	}while(posSeparador!=-1);

 	return elemento;
 }

  /**
  * Obtiene la posicion de la primera comilla simple valida de la cadena.
  * Es valida si no va precedida del caracter de escape "\"
  */
 function getPosComillaSimple(inicio,cadena){
 	var cadenaAux=cadena.substring(inicio);
 	var posComilla=0;
 	do{
 		posComilla=cadenaAux.indexOf("'");
 		if(posComilla==-1){
 			return -1;
 		}
 		var carAnterior=cadenaAux.charAt(posComilla-1);
 		if(carAnterior=="\\"){
 			cadenaAux=cadenaAux.substring(posComilla+1);
 		}
	 	}while(carAnterior=="\\");
	 	return posComilla;
 }

 function addOptionInSelect(select,option){
 	try{
 		select.add(option);
 	}catch(e){
 		select.add(option,null);
 	}
}

function getNoTextChildNode(contenedor,pos){
	var numNoTextNodes=0;
	var nodo=null;
	for(var i=0;i<contenedor.childNodes.length;i++){
		nodo=contenedor.childNodes[i];
		if(nodo.nodeType!=3){
			numNoTextNodes++;
		}
		if(numNoTextNodes==pos+1){
			break;
		}
	}
	return nodo;

}

function goTo(url){
	window.location=url;
}

function eliminarEspacios(texto){
	var res;
	if(texto)
		res = texto.replace(/^\s*|\s*$/g,"");
	return res;
}

function mostrarRecalcular(){
	var elemento = document.getElementById('textoNumeracion');
	var texto;
	if(elemento){
		texto = elemento.value;
		texto = eliminarEspacios(texto);
	}
	var mostrar = document.getElementById('mostrarRecalcular');
	if (!/^([0-9])*$/.test(texto)){
		document.getElementById('recalcular').checked = false;
		mostrar.style.display='none';
	}else
		mostrar.style.display='inline';
}


function editarCampo(idDivTexto, idDivTextbox, posicion){
	var divTexto;
	var divTextbox;

	//Ocultar todos los elementos que se estén editando
	for(var i=1;;i++)
	{
		divTexto= idDivTexto + i;
		divTextbox= idDivTextbox + i;
		elemento=document.getElementById(divTextbox);

		if(elemento!=null){
			elemento.className="hidden";
			document.getElementById(divTexto).className="visible";
		}
		else{
			break;
		}
	}

	divTexto= idDivTexto + posicion;
	divTextbox= idDivTextbox + posicion;

	document.getElementById(divTextbox).className="visible";
	document.getElementById(divTexto).className="hidden";

}

function cancelarEdicionCampo(idDivTexto, idDivTextbox, posicion, valorInicial,idCampo){
	divTexto= idDivTexto + posicion;
	divTextbox= idDivTextbox + posicion;

	document.getElementById(divTextbox).className="hidden";
	document.getElementById(divTexto).className="visible";

	//Establecer el valor inicial
	document.getElementById(idCampo).value = valorInicial;
}


function aceptarEdicionCampo(idDivTexto, idDivTextbox, posicion, tipo,valorInicial,idCampo,nombreFuncion){
	var valor = document.getElementById(idCampo).value;
	//Si es el mismo valor
	if(valor == valorInicial){
		cancelarEdicionCampo(idDivTexto, idDivTextbox, posicion, valorInicial,idCampo);
	}
	else{
		eval(nombreFuncion + "('"+ valor + "','"+ posicion +"','"+ tipo +"')");
	}
}
