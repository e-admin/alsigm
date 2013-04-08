
EVEN_BG_COLOR = '#dadada';
ODD_BG_COLOR = '#ffffff';

var cajaSeleccionada = null;
var asignacionUdocsDivididas = new Array();

function UdocPart (partID,caja, pos)  {
	this.partID = partID;
	this.caja = caja;
	this.pos = pos;

	this.assign = function(caja, pos) {
		//alert('parte: '+this.partID+'-caja:'+caja+'-pos:'+pos);
		this.caja = caja;
		this.pos = pos;
	}
	this.toString = function() {
		var asString = this.partID;
		if (this.caja != null)
			asString += '_'+this.caja+'_'+this.pos;
		return asString;
	}
}

function show_props(obj, obj_name) {
   var result = "";
   for (var i in obj)
      result += obj_name + "." + i + " = " + obj[i] + "\n";
   return result
} 

function ChangeManager() {
	this.udocsRelacion = new Array();
	this.changes = new Array();
	this.descripciones = new Array();
	this.changedUIs = 0;

	this.getChanges = function () {
		var createdParts = new Array();
		var removedParts = new Array();
		var asignedParts = new Array();
		for (var i in this.changes) {
			//alert("udocchanged "+i);
			originalUdocPart = this.udocsRelacion[i];
			changedUdocPart = this.changes[i];
			if (originalUdocPart) {
				//alert(originalUdocPart.toString());
				if (changedUdocPart) {
					//alert(changedUdocPart.caja+'-'+originalUdocPart.caja+'::'+changedUdocPart.pos+'-'+originalUdocPart.pos);
					if (changedUdocPart.caja != originalUdocPart.caja || changedUdocPart.pos != originalUdocPart.pos)
						asignedParts[asignedParts.length] = changedUdocPart.toString();
				} else
					removedParts[removedParts.length] = originalUdocPart.toString();
			} else {
				//alert("Es una parte creada");
				if (changedUdocPart) {
					if (changedUdocPart.caja)
						asignedParts[asignedParts.length] = changedUdocPart.toString();
					//else
						createdParts[createdParts.length] = changedUdocPart.toString();
				}
			}
		}
		//return asignedParts;
		return {'asignedParts':asignedParts,'createdParts':createdParts,'removedParts':removedParts};

	}


	this.registerUdocChanged = function (idPart) {
		//alert("registrando: "+idPart);
		var udocPart = this.changes[idPart];
		//alert(udocPart);
		if (udocPart == null) {
			//alert(show_props(this.udocsRelacion,'udocsRelacion'));
			udocPart = this.udocsRelacion[idPart];
			//alert(udocPart.partID);
			this.changes[idPart] = new UdocPart(idPart,udocPart.caja,udocPart.pos);
		}
	}
	this.udocAsignment = function (partID, pos) {
		//alert("aa");
		//alert(partID+'-'+pos);
		var idPart = partID.substring(4);
		this.registerUdocChanged(idPart);
		//alert(cajaSeleccionada.getAttribute('numeroCajaSeleccionada'));
		this.changes[idPart].assign(cajaSeleccionada.getAttribute('numeroCajaSeleccionada'),pos);
	}
	this.udocUnasignment = function (partID, caja) {
		//alert(partID+'-'+caja);
		var idPart = partID.substring(4);
		this.registerUdocChanged(idPart);
		this.changes[idPart].assign(null,-1);
	}
	this.udocPositionUpdated = function (partID, pos) {
		//alert(partID + ' :: '+pos);
		var idPart = partID.substring(4);
		this.registerUdocChanged(idPart);
		this.changes[idPart].pos = pos;
		//alert(this.changes[partID].toString());
	}
	this.udocPartCreation = function (partID) {
		//alert(partID);
		var idPart = partID.substring(4);
		this.changes[idPart] = new UdocPart(idPart, null, -1);
	}
	this.udocPartDeletion = function (partID) {
		//alert(partID);
		var idPart = partID.substring(4);
		this.registerUdocChanged(idPart);
		this.changes[idPart] = null;
		delete this.descripciones[idPart];
	}

	this.addUdoc = function (udocID, numeroParte, numCaja, pos)  {
		var partID = udocID.substring(4)+'_'+numeroParte;
		//alert("adding "+partID+'/'+numCaja+'/'+pos);
		var udoc = new UdocPart(partID,numCaja , pos);
		this.udocsRelacion[partID] = udoc;
		if (! (numCaja < 0)) {
			var infoAsignacionUdoc = asignacionUdocsDivididas[udocID];
			if (!infoAsignacionUdoc )
				asignacionUdocsDivididas[udocID] = {'ultimaParteAsignada':numeroParte, 'cajaUltimaParte':numCaja}
			else
				if (infoAsignacionUdoc.ultimaParteAsignada < numeroParte) {
					infoAsignacionUdoc.ultimaParteAsignada = numeroParte;
					infoAsignacionUdoc.cajaUltimaParte = numCaja;
				}
		}
		//alert(show_props(this.udocsRelacion,'udocsRelacion'));
		return udoc;
	}
	
	this.uiCreated = function() {
		this.changedUIs ++;
	}
	this.uiRemoved = function() {
		this.changedUIs --;
	}
	this.descriptionSetted = function(partID, descripcion) {
		var idPart = partID.substring(4);
		this.descripciones[idPart] = descripcion;
	}
}

var changeManager = new ChangeManager();


function SelectableItem(object) {
	this.DOMObject = object;
	this.originalBackgroundColor = object.style.backgroundColor;
	this.numPartes = 1;
}


var numeroCajasEnRelacion = 0;
var minNumCajas = 0
function addCaja(canBeDeleted) {
	numeroCajasEnRelacion ++;
	//alert(numeroCajasEnRelacion);
	if (canBeDeleted)
		minNumCajas ++
}


function xdOnMouseDown(e) {
	var evt = new xEvent(e);
	var dragIcon = xGetElementById('dragSymbol');
	dragIcon.xDragX = evt.pageX;
	dragIcon.xDragY = evt.pageY; 
	xMoveTo(dragIcon,dragIcon.xDragX - xWidth(dragIcon)/2,dragIcon.xDragY - xHeight(dragIcon)/2);
	xVisibility(dragIcon, true);
	xDragMgr.mm = true;
	xDragMgr.ele = dragIcon;
	xAddEventListener(document,'mouseup',xdOnMouseUp,false);
	xAddEventListener(document,'mousemove',xdOnMouseMove,false);
}
function xdOnMouseMove(e) {
	var evt = new xEvent(e);
	if (xDragMgr.ele) {
		if (e && e.preventDefault) e.preventDefault();
		else if (window.event) window.event.returnValue = false;
		var ele = xDragMgr.ele;
		var dx = evt.pageX - ele.xDragX;
		var dy = evt.pageY - ele.xDragY;
		var newX = xLeft(ele)+dx;
		var newY = xTop(ele)+dy;
		ele.xDragX = evt.pageX;
		ele.xDragY = evt.pageY;
		xMoveTo(ele,newX,newY);
	}
}

function udocRowContent(idUdoc, titleUdoc) {
	var code = "<table><tr>";
	code += "<td>"+titleUdoc+"</td>";
	code +=	"<td><a class=\"Acaja\" href=\"javascript:subirUdoc('"+idUdoc+"')\">";
	code += "<img src=\""+imagesSrcs[2]+"\" border=\"0\" alt=\"Subir Unidad Documental\" title=\"Subir Unidad Documental\">";
	code +=	" <bean:message key='archigest.archivo.subir' /></a> </td>";
	code +=	"<td><a class=\"Acaja\" href=\"javascript:bajarUdoc('"+idUdoc+"')\">";
	code += "<img src=\""+imagesSrcs[3]+"\" border=\"0\" alt=\"Bajar Unidad Documental\" title=\"Bajar Unidad Documental\">";
	code +=	" <bean:message key='archigest.archivo.bajar' /></a> </td>";
	code +=	"<td><a class=\"Acaja\" href=\"javascript:quitarUdoc('"+idUdoc+"')\">";
	code += "<img src=\""+imagesSrcs[4]+"\" border=\"0\" alt=\"Quitar Unidad Documental\" title=\"Quitar Unidad Documental\">";
	code +=	" <bean:message key='archigest.archivo.quitar' /></a> </td>";
	code +=	"</tr></table>";
	//alert(code);
	return code;
}

function xdOnMouseUp(e) {
	if (xDragMgr.ele) {
		var evt = new xEvent(e);
		if (e && e.preventDefault) e.preventDefault();
		else if (window.event) window.event.returnValue = false;
		xRemoveEventListener(document,'mousemove',xdOnMouseMove,false);
		xRemoveEventListener(document,'mouseup',xdOnMouseUp,false);
		xVisibility('dragSymbol',false);
		//xDragMgr.ele.style.display = 'none';
		if (cajaSeleccionada != null) {
			var dragEleXPos = evt.pageX;
			var dragEleYPos = evt.pageY;
			//alert(dragEleXPos+':'+xPageX(cajaSeleccionada)+'/'+(xPageX(cajaSeleccionada)+xWidth(cajaSeleccionada)));
			//alert(dragEleYPos+':'+xPageY(cajaSeleccionada)+'/'+(xPageY(cajaSeleccionada)+xHeight(cajaSeleccionada)));
			//alert(xPageX(cajaSeleccionada) <  dragEleXPos);
			//alert((xPageX(cajaSeleccionada)+xWidth(cajaSeleccionada)) > dragEleXPos);
			//alert(xPageY(cajaSeleccionada) < dragEleYPos);
			//alert((xPageY(cajaSeleccionada) + xHeight(cajaSeleccionada)) > dragEleYPos);
			if (xPageX(cajaSeleccionada) <  dragEleXPos && (xPageX(cajaSeleccionada)+xWidth(cajaSeleccionada)) > dragEleXPos && xPageY(cajaSeleccionada) < dragEleYPos && (xPageY(cajaSeleccionada) + xHeight(cajaSeleccionada)) > dragEleYPos) {

			var udocsCajaDestino = xGetElementById('udocsCajaSeleccionada');
			var nUdocsEnCajaSeleccionada = parseInt(udocsCajaDestino.getAttribute("nudocsEnCaja"), 10);
			var numCajaSeleccionada = parseInt(cajaSeleccionada.getAttribute("numeroCajaSeleccionada",10));
			//alert(nUdocsEnCajaSeleccionada);
			//alert(selectedRows.length);
			var count = 0;
			for (var i in selectedRows) {
				//var nUdocsEnCaja = udocsXCaja.get(cajaDestino);
				//if (nUdocsEnCaja == null) nUdocsEnCaja = 0;
				var rowToMove = selectedRows[i].DOMObject;
				var udocLayerId = rowToMove.id;

		var splitedUdocID = udocLayerId.split("_");
		var udocID = splitedUdocID[0];
		var numeroParte =  parseInt(splitedUdocID[1],10);
		var numPartesUdoc = parseInt(rowToMove.getAttribute("numPartes"),10);
		if (numPartesUdoc > 1) {
			if (udocID in asignacionUdocsDivididas) {
				var infoAsignacion = asignacionUdocsDivididas[udocID];
				//alert(infoAsignacion.ultimaParteAsignada+'-'+numeroParte+' , '+infoAsignacion.cajaUltimaParte+'-'+numCajaSeleccionada);
				if (infoAsignacion.ultimaParteAsignada != numeroParte - 1 || infoAsignacion.cajaUltimaParte != numCajaSeleccionada - 1)
					alert('No se pueden asignar dos partes de una Unidad a la misma Caja.');
				else {
					putUdocEnCaja(rowToMove, udocsCajaDestino, nUdocsEnCajaSeleccionada + count);
					count ++;
					infoAsignacion.ultimaParteAsignada ++;
					infoAsignacion.cajaUltimaParte ++
				}
			} else {
				//alert('asignacion no registrada '+numeroParte);
				if (numeroParte != 1)
					alert('Se debe asignar primero la primera parte.');
				else {
					putUdocEnCaja(rowToMove, udocsCajaDestino, nUdocsEnCajaSeleccionada + count);
					count ++;
					asignacionUdocsDivididas[udocID] = {'ultimaParteAsignada':1, 'cajaUltimaParte':numCajaSeleccionada};
				}
			}
		} else {
			putUdocEnCaja(rowToMove, udocsCajaDestino, nUdocsEnCajaSeleccionada + count);
			count ++;
		}




			}
			udocsCajaDestino.setAttribute("nudocsEnCaja", nUdocsEnCajaSeleccionada + count);
			var listaUdocs = xGetElementById('udocsNoAsignadas').getElementsByTagName('div');
			var nUdocs = listaUdocs.length;
			for(var i=0; i<nUdocs; i++)
				xBackground(listaUdocs[i],i%2?EVEN_BG_COLOR:ODD_BG_COLOR);
			clearSelection();
			SelectionGroups['udocsCajaSeleccionada'].clearSelection();
			listaUdocs = udocsCajaDestino.getElementsByTagName('div');
			var nUdocs = listaUdocs.length;
			for(var i=0; i<nUdocs; i++)
				xBackground(listaUdocs[i],i%2?EVEN_BG_COLOR:ODD_BG_COLOR);
			}
		}
		xDragMgr.ele = null;
	}
}

function putUdocEnCaja(udocDOMNode, caja, pos) {
	var udocID = udocDOMNode.id;
	changeManager.udocAsignment(udocID, pos);
	udocDOMNode.parentNode.removeChild(udocDOMNode);
	caja.appendChild(udocDOMNode);
	udocDOMNode.setAttribute("udocPos", pos);
	removeUdocNoAsignada(udocID);
	SelectionGroups['udocsCajaSeleccionada'].addItem(udocID);
}


function findRow(rowID) {
	var clickedRow = null;
	if (document.all) clickedRow = document.all[rowID];
	else if (document.getElementById) clickedRow = document.getElementById(rowID);
	return clickedRow;
}

var selectedRows = new Array();

function selectRow(e) {
	xStopPropagation(e);
	var selectionEvent = new xEvent(e);
	var evt = e || window.event;
	if (!evt.ctrlKey) {
		clearSelection();
	}
	var selectedElement = selectionEvent.target;
	while (selectedElement.tagName.toLowerCase() != "div") {
		selectedElement = selectedElement.parentNode;
	}
	var rowID = selectedElement.id;
	selectedRows[rowID] = new SelectableItem(selectedElement);		
	selectedElement.style.backgroundColor = "#a0a0a0";
	xAddEventListener(rowID,'mousedown',xdOnMouseDown,false);
}

function clearSelection() {
	var aSelectedItem;
	for (var aRow in selectedRows) {
		aSelectedItem = selectedRows[aRow];
		aSelectedItem.DOMObject.style.backgroundColor = aSelectedItem.originalBackgroundColor;
		xRemoveEventListener(aSelectedItem.DOMObject,'mousedown',xdOnMouseDown,false);
		delete selectedRows[aRow];
	}
}

var xDragMgr = {ele:null, mm:false};

// implementar en clase generica Box que puede contener cosas y puede abrirse y cerrarse manteniendose las que se encuentran abiertas
var cajasAbiertas = new Array();
function clickOn(numCaja) {
	var udocsCajaLayerName = "udocscaja"+numCaja;
	var udocsCajaLayer = xGetElementById(udocsCajaLayerName);
	//alert(numCaja+': '+cajasAbiertas[numCaja]);
	if (!cajasAbiertas[numCaja]) {
		//cajasAbiertas.add(idCaja.substr(4));
		udocsCajaLayer.style.display = 'block';
		cajaAbierta(numCaja);
	} else {
		cajasAbiertas[numCaja] = false;
		udocsCajaLayer.style.display = 'none';
		xGetElementById('iconocaja'+numCaja).src = imagesSrcs[0];
	}
}
function cajaAbierta(numCaja) {
	cajasAbiertas[numCaja] = true;
	//alert(numCaja+': '+cajasAbiertas[numCaja]);
	xGetElementById('iconocaja'+numCaja).src = imagesSrcs[1];
}

function repaintCajaSeleccionada() {
	var listaUdocs = xGetElementById('udocsCajaSeleccionada').getElementsByTagName('div');
	var nItems = listaUdocs.length
	for(var i=0; i<nItems; i++) {
		if (listaUdocs[i].id == SelectionGroups['udocsCajaSeleccionada'].selectedItemID) 
			listaUdocs[i].setAttribute("originalColor",i%2?EVEN_BG_COLOR:ODD_BG_COLOR);
		else
			xBackground(listaUdocs[i],i%2?EVEN_BG_COLOR:ODD_BG_COLOR);
	}
}

function subirUdoc(evt) {
	//alert("AAAAA");
	stopPropagation(evt);
	var udocToMove = xGetElementById(SelectionGroups['udocsCajaSeleccionada'].selectedItemID);
	//alert(udocToMove);
	if (udocToMove) {
		var previousUdoc = udocToMove.previousSibling;
		if (previousUdoc && previousUdoc.tagName) {
			var udocPos = parseInt(udocToMove.getAttribute("udocPos"),10);
			//alert(udocPos);
			changeManager.udocPositionUpdated(udocToMove.id, (udocPos - 1));
			//alert('tomove id'+udocToMove.id);
			var udocToMoveContainer = udocToMove.parentNode;
			udocToMoveContainer.replaceChild(previousUdoc, udocToMove);
			//var removedNode = udocToMoveContainer.removeChild(udocToMove);
			udocToMoveContainer.insertBefore(udocToMove, previousUdoc);
			udocToMove.setAttribute("udocPos", (udocPos - 1));
			//udocToMoveContainer.insertBefore(removedNode,previousUdoc);
			previousUdoc.setAttribute("udocPos", udocPos);
			//alert('previous id'+previousUdoc.id);
			changeManager.udocPositionUpdated(previousUdoc.id, udocPos);
			repaintCajaSeleccionada();
		}
	}
	return false;
}

function bajarUdoc(evt) {
	stopPropagation(evt);
	var udocToMove = xGetElementById(SelectionGroups['udocsCajaSeleccionada'].selectedItemID);
	if (udocToMove) {
		var nextUdoc = udocToMove.nextSibling;
		if (nextUdoc && nextUdoc.tagName) {
			var udocPos = parseInt(udocToMove.getAttribute("udocPos"),10);
			var udocToMoveContainer = udocToMove.parentNode;
			udocToMoveContainer.replaceChild(udocToMove, nextUdoc);
			udocToMoveContainer.insertBefore(nextUdoc, udocToMove);
			//var removedNode = udocToMoveContainer.removeChild(udocToMove);
			udocToMove.setAttribute("udocPos", (udocPos + 1));
			changeManager.udocPositionUpdated(udocToMove.id, (udocPos + 1));
			/*if (nextUdoc.nextSibling)
				udocToMoveContainer.insertBefore(removedNode,nextUdoc.nextSibling);
			else
				udocToMoveContainer.appendChild(removedNode);*/
			nextUdoc.setAttribute("udocPos", udocPos);
			changeManager.udocPositionUpdated(nextUdoc.id, udocPos);
			repaintCajaSeleccionada();
		}
	}
}

function quitarUdoc(evt) {
	stopPropagation(evt);
	var udocToMove = xGetElementById(SelectionGroups['udocsCajaSeleccionada'].selectedItemID);
	if (udocToMove) {
		var numPartesUdoc = parseInt(udocToMove.getAttribute("numPartes"),10);
//		if (numPartesUdoc > 1) {
			var splitedUdocID = udocToMove.id.split("_");
			var udocID = splitedUdocID[0];
			var numeroParte =  parseInt(splitedUdocID[1],10);

			if (asignacionUdocsDivididas[udocID] && asignacionUdocsDivididas[udocID].ultimaParteAsignada != numeroParte) {
				alert("Hay partes de la misma Unidad asignadas a Cajas posteriores.");
				return;
			}
			if (numeroParte > 1) {
				asignacionUdocsDivididas[udocID].ultimaParteAsignada--;
				asignacionUdocsDivididas[udocID].cajaUltimaParte--;
			} else
				delete asignacionUdocsDivididas[udocID];
//		}
		SelectionGroups['udocsCajaSeleccionada'].removeItem(udocToMove.id);
		SelectionGroups['udocsCajaSeleccionada'].clearSelection();
		changeManager.udocUnasignment(udocToMove.id,parseInt(cajaSeleccionada.getAttribute('numeroCajaSeleccionada'), 10));
		var nextUdoc = udocToMove.nextSibling;
		while(nextUdoc && nextUdoc.tagName) {
			var nextUdocPos = nextUdoc.getAttribute("udocPos");
			if (nextUdocPos) {
				var nexUdocPosIntValue = parseInt(nextUdocPos, 10) - 1;
				nextUdoc.setAttribute("udocPos", nexUdocPosIntValue);
				changeManager.udocPositionUpdated(nextUdoc.id, nexUdocPosIntValue);
			}
			nextUdoc = nextUdoc.nextSibling;
		}
		var udocsCajaSeleccionada = udocToMove.parentNode;
		var nUdocsEnCajaSeleccionada = parseInt(udocsCajaSeleccionada.getAttribute("nudocsEnCaja"), 10);
		udocsCajaSeleccionada.setAttribute("nudocsEnCaja", nUdocsEnCajaSeleccionada - 1);
		udocsCajaSeleccionada.removeChild(udocToMove);
		var udocsNoAsignadas = xGetElementById("udocsNoAsignadas");
		udocsNoAsignadas.appendChild(udocToMove);
		xBackground(udocToMove,(udocsNoAsignadas.getElementsByTagName('div').length+1)%2?EVEN_BG_COLOR:ODD_BG_COLOR);
		xDisplay('emptyUdocsSinCaja', 'none')
		addUdocNoAsignada(udocToMove.id);
		repaintCajaSeleccionada();
	}
}

function closeCajaSeleccionada(evt) {
	stopPropagation(evt);
	if (!cajaSeleccionada) return;
	SelectionGroups['udocsCajaSeleccionada'].clearSelection();
	var numCajaSeleccionada = parseInt(cajaSeleccionada.getAttribute("numeroCajaSeleccionada",10));
	var containerUdocsCajaSeleccionada =  xGetElementById("udocsCajaSeleccionada");
	var cajaEnLista = xGetElementById("udocscaja"+numCajaSeleccionada);
	var udocsEnCajaSeleccionada = containerUdocsCajaSeleccionada.getElementsByTagName("div");
	var nUdocsEnCajaSeleccionada = udocsEnCajaSeleccionada.length;
	//alert(containerUdocsCajaSeleccionada.innerHTML + ':: '+nUdocsEnCajaSeleccionada);
	cajaEnLista.innerHTML = "";
	if (nUdocsEnCajaSeleccionada == 0) {
		var cajaVacia = xGetElementById("cajaVacia").cloneNode(true);
		cajaVacia.setAttribute("id", 'emptycaja'+numCajaSeleccionada);
		cajaEnLista.appendChild(cajaVacia);
		if (cajasAbiertas[numCajaSeleccionada])
			xDisplay(cajaVacia, 'block');
	}
	for (var i=0; i<nUdocsEnCajaSeleccionada; i++) {
		var aUdoc = udocsEnCajaSeleccionada[i].cloneNode(true);
		aUdoc.removeAttribute("id");
		cajaEnLista.appendChild(aUdoc);
	}
	for (var i=nUdocsEnCajaSeleccionada; i>0; i--)
		containerUdocsCajaSeleccionada.removeChild(udocsEnCajaSeleccionada[i-1]);

	xDisplay("msgNoCajaSeleccionada", "inline");
	xGetElementById("labelCajaSeleccionada").innerHTML = "";
	var msgSeleccionCaja = xGetElementById("msgSeleccionCaja");
	containerUdocsCajaSeleccionada.appendChild(msgSeleccionCaja);
	xGetElementById("linkAbrirCaja"+numCajaSeleccionada).style.color= "#003399";
	xDisplay(msgSeleccionCaja, "inline");
	cajaSeleccionada = null;
}

function introDescripcionContenido(evt) {
	stopPropagation(evt);
	if (!SelectionGroups['udocsCajaSeleccionada'].selectedItemID)
		alert("Seleccione una Unidad de la Caja");
	else {
		var descripcionContenido = xGetElementById('descripcionContenido');
		var inputDescripcionContenido = xGetElementById('inputDescripcionContenido');
		var currentContentDesc = xGetElementById('descripcion'+SelectionGroups['udocsCajaSeleccionada'].selectedItemID);
		inputDescripcionContenido.value = currentContentDesc?currentContentDesc.innerHTML:'';
		var udocSeleccionada = xGetElementById(SelectionGroups['udocsCajaSeleccionada'].selectedItemID);
		xDisplay(descripcionContenido, 'block');
		//alert(xHeight(descripcionContenido));
		xMoveTo(descripcionContenido, -50, xOffsetTop(udocSeleccionada)-xHeight(descripcionContenido)+5);
		SelectionGroups['udocsCajaSeleccionada'].setSelectionEnabled(false);
	}
}

function guardarDescripcionContenido(evt) {
	stopPropagation(evt);
	var udocSeleccionada = xGetElementById(SelectionGroups['udocsCajaSeleccionada'].selectedItemID);
	var currentContentDesc = xGetElementById('descripcion'+SelectionGroups['udocsCajaSeleccionada'].selectedItemID);
	var textoDescripcion = xGetElementById('inputDescripcionContenido').value;
	if (!currentContentDesc) {
		udocSeleccionada.appendChild(document.createElement("br"));
		currentContentDesc = document.createElement("span");
		currentContentDesc.setAttribute("id" ,'descripcion'+udocSeleccionada.id);
		currentContentDesc.setAttribute(xMoz?"class":"className", "descripcionContenido");
		udocSeleccionada.appendChild(currentContentDesc);
	}
	currentContentDesc.innerHTML = textoDescripcion;
	changeManager.descriptionSetted(udocSeleccionada.id, textoDescripcion);
	xDisplay('descripcionContenido', 'none');
	SelectionGroups['udocsCajaSeleccionada'].setSelectionEnabled(true);
}

function hideDescripcionContenido(evt) {
	stopPropagation(evt); 
	xDisplay('descripcionContenido', 'none');
	SelectionGroups['udocsCajaSeleccionada'].setSelectionEnabled(true);
}

function guardarCambios(form) {
	form.elements["cajasAbiertas"].value = cajasAbiertas.toString(",");
	form.elements["udocsNoAsignadas"].value = udocsNoAsignadas.toString(",");
	//alert(form.target);
	var changes = changeManager.getChanges();
	//alert('partes creadas '+ changes.createdParts.join(','));
	//dumpArray(changes.createdParts);
	//alert('partes asignadas '+changes.asignedParts.length + ' - '+changes.asignedParts.join(','));
	//dumpArray(changes.asignedParts);
	//alert('partes eliminadas '+changes.removedParts.length + '-'+changes.removedParts.join(','));
	//dumpArray(changes.removedParts);
	form.elements['createdParts'].value = changes.createdParts.join(',');
	form.elements['asignedParts'].value = changes.asignedParts.join(',');
	//form.elements['changedNumParts'].value = changes.changedNumParts.join(',');
	form.elements['removedParts'].value = changes.removedParts.join(',');
	form.elements['changedUIs'].value = changeManager.changedUIs;
	for (var i in changeManager.descripciones) {
		var contentDescInput = document.createElement("input");
		contentDescInput.type = 'hidden';
		contentDescInput.name = 'descripcion('+i+')';
		contentDescInput.value = changeManager.descripciones[i];
		form.appendChild(contentDescInput);
	}
	for (var i in changeManager.changedNumParts) {
		if (changeManager.changedNumParts[i] > 0) {
			var inputField = document.createElement("input");
			inputField.type = 'hidden';
			inputField.name = 'numPartsChange('+i+')';
			inputField.value = changeManager.changedNumParts[i];
			form.appendChild(inputField);
		}
	}
	form.submit();
}

function dumpArray(array) {
	alert("Número de elementos: "+array.length);
	if (array.length > 0) {
		var joinedContent = array.join('|');
		if (! joinedContent.length) {
			var arrayAsString = "";
			for (var i in array)
			{
				arrayAsString += i+' -- '+array[i];
			}
			alert(arrayAsString);
		} else
			alert(joinedContent);
	}
}

function getCodeCabeceraCaja(numeroCaja, icon, label, includeInput) {
	var cabeceraCaja = "<table cellpadding=0 cellspacing=0 class='w100' style='border-bottom:1px dotted #999999;'>";
	cabeceraCaja += "<tbody><tr>";
	cabeceraCaja += "<td width='40%' height='18px'>";
	cabeceraCaja += "<a href=\"javascript:clickOn("+numeroCaja+")\" unselectable='on'>"; 
	cabeceraCaja += "<img src='"+icon+"' class='imgTextMiddle' id='iconocaja"+numeroCaja+"'>";
	cabeceraCaja += "</a>";
	cabeceraCaja += "\n<a id='linkAbrirCaja"+numeroCaja+"' href=\"javascript:abrirCaja("+numeroCaja+")\" class='numeroCaja'  unselectable='on'>"; 
	cabeceraCaja += label + " " + (numeroCaja+1);
	cabeceraCaja += "</a>";
	if (includeInput) {
		cabeceraCaja += "\n&nbsp;&nbsp;<input numeroCaja=\""+numeroCaja+"\" type=\"text\" size=\"6\" onchange=\"signaturaModificada(event)\">";
	}
	cabeceraCaja += "</td></tr></table>";
	return cabeceraCaja;
}

function crearCaja(icon, label, manageSignaturas) {
	var numeroCaja = numeroCajasEnRelacion++;
	var nuevaCaja = document.createElement("div");
	nuevaCaja.innerHTML = getCodeCabeceraCaja(numeroCaja, icon, label, manageSignaturas);
	nuevaCaja.setAttribute("id", "caja"+numeroCaja);
	var udocsCaja = document.createElement("div");
	udocsCaja.setAttribute(xMoz?"class":"className", "contenidoCaja");
	udocsCaja.setAttribute("id", "udocscaja"+numeroCaja);
	udocsCaja.innerHTML ="<div class='cuerpoCaja' style='padding-left:30px' id='msgCajaVacia"+numeroCaja+"'>Caja Vacía</div>";
	var listaCajas = xGetElementById("cajasRelacion");
	listaCajas.appendChild(nuevaCaja);
	listaCajas.appendChild(udocsCaja);

	cajaAbierta(numeroCaja);
	changeManager.uiCreated();
}

function signaturaModificada(evt) {
	//alert(evt);
	var event = new xEvent(evt);
	//alert('target: '+event.target);
	//alert('srcElement: '+event.srcElement);
	var numeroCaja = event.target.getAttribute('numeroCaja');
	//alert(numeroCaja);
	var nuevaSignatura = event.target.value;
	//alert(nuevaSignatura);
	var inputSignatura = xGetElementById('signatura'+numeroCaja);
	if (! inputSignatura) {
		inputSignatura = document.createElement("input");
		inputSignatura.setAttribute("id", 'signatura'+numeroCaja);
		inputSignatura.setAttribute("type", "hidden");
		inputSignatura.setAttribute("name", 'signatura('+numeroCaja+')');
		inputSignatura.setAttribute("value", '+'+numeroCaja);
		xGetElementById('formulario').appendChild(inputSignatura);
	}
	inputSignatura.value = nuevaSignatura;
}


function eliminarCaja() {
	var listaCajas = xGetElementById("cajasRelacion");
	//alert('minimo: '+minNumCajas+' -numeroCajas: '+numeroCajasEnRelacion);
	if (minNumCajas < numeroCajasEnRelacion) {
		numeroCajasEnRelacion--;
		var idUltimaCaja = 'udocscaja'+numeroCajasEnRelacion;
		var docsUltimaCaja = xGetElementById(idUltimaCaja);
		if (docsUltimaCaja) {
			var udocsNoAsignadas = xGetElementById("udocsNoAsignadas");
			var udocsEnCaja = docsUltimaCaja.getElementsByTagName("div");
			var nDIVs = udocsEnCaja.length;
			var unasignedUdocs = false;
			for (var i=nDIVs -1;i>0; i--) {
				var udocToMove = docsUltimaCaja.removeChild(udocsEnCaja[i]);
				if (udocToMove.getAttribute("idUdoc")) {
					udocsNoAsignadas.appendChild(udocToMove);
					xBackground(udocToMove,(udocsNoAsignadas.getElementsByTagName('div').length+1)%2?EVEN_BG_COLOR:ODD_BG_COLOR);
					addUdocNoAsignada(udocToMove.id);
					changeManager.udocUnasignment(udocToMove.id, numeroCajasEnRelacion);
					unasignedUdocs = true;
				}
			}
			if (unasignedUdocs) xDisplay('emptyUdocsSinCaja', 'none');
			listaCajas.removeChild(docsUltimaCaja);
			listaCajas.removeChild(xGetElementById('caja'+numeroCajasEnRelacion));
		}
		changeManager.uiRemoved();
	}

}

function abrirCaja(numeroCaja) {
	//alert(numeroCaja);
	SelectionGroups['udocsCajaSeleccionada'].clearSelection();	
	SelectionGroups['udocsCajaSeleccionada'].reset();	
	if (cajaSeleccionada == null)
		cajaSeleccionada = xGetElementById("cajaSeleccionada");
	else {
		var numCajaSeleccionada = parseInt(cajaSeleccionada.getAttribute("numeroCajaSeleccionada",10));
		xGetElementById("linkAbrirCaja"+numCajaSeleccionada).style.color = '#003399';
		var containerUdocsCajaSeleccionada =  xGetElementById("udocsCajaSeleccionada");
		var cajaEnLista = xGetElementById("udocscaja"+numCajaSeleccionada);
		//alert(cajaEnLista);
		var udocsEnCajaSeleccionada = containerUdocsCajaSeleccionada.getElementsByTagName("div");
		var nUdocsEnCajaSeleccionada = udocsEnCajaSeleccionada.length;
		//alert(containerUdocsCajaSeleccionada.innerHTML + ':: '+nUdocsEnCajaSeleccionada);
		cajaEnLista.innerHTML = "";
		if (nUdocsEnCajaSeleccionada > 0) {
			var listaUdocsCajaSeleccionada = new Array();
			//alert("udocsEnCajaSeleccionada: "+udocsEnCajaSeleccionada+' - '+nUdocsEnCajaSeleccionada);
			for (var i=0; i<nUdocsEnCajaSeleccionada; i++) {
				var aUdoc = udocsEnCajaSeleccionada[i];
				xRemoveEventListener(aUdoc, 'click', selectableItemSelected, false);
				listaUdocsCajaSeleccionada.push(aUdoc);
			}
			for (var i=0; i<listaUdocsCajaSeleccionada.length; i++) {
				var aUdoc = containerUdocsCajaSeleccionada.removeChild(listaUdocsCajaSeleccionada[i]);
				cajaEnLista.appendChild(aUdoc);
			}
		} else {
			var cajaVacia = xGetElementById("cajaVacia").cloneNode(true);
			cajaVacia.setAttribute("id", 'emptycaja'+numCajaSeleccionada);
			cajaEnLista.appendChild(cajaVacia);
			if (cajasAbiertas[numCajaSeleccionada])
				xDisplay(cajaVacia, 'block');
		}
	}

	cajaSeleccionada.setAttribute("numeroCajaSeleccionada", numeroCaja);
	var udocsCajaSeleccionada = xGetElementById("udocsCajaSeleccionada");

	var msgSeleccionCaja = xGetElementById("msgSeleccionCaja");
	if (msgSeleccionCaja.parentNode == udocsCajaSeleccionada) {
		udocsCajaSeleccionada.removeChild(msgSeleccionCaja);
		cajaSeleccionada.appendChild(msgSeleccionCaja);
		xDisplay(msgSeleccionCaja, "none");	
	}
	var udocsCaja = xGetElementById("udocscaja"+numeroCaja);
	var udocsEnCajaDOMs = udocsCaja.getElementsByTagName("div");
	var nChilds = udocsEnCajaDOMs.length;
	//alert("nchilds "+nChilds);
	var count = 0;
	for (var i=0; i<nChilds; i++) {
		var aUdoc = udocsEnCajaDOMs[i];
		var idUdoc = aUdoc.getAttribute("idUdoc");
		//alert(idUdoc);
		if (idUdoc) {
			var nodeCopy = aUdoc.cloneNode(true);
			//nodeCopy.removeAttribute("id");
			removeIDs(nodeCopy);
			udocsCaja.insertBefore(nodeCopy, aUdoc);
			udocsCaja.removeChild(aUdoc);
			udocsCajaSeleccionada.appendChild(aUdoc);
			aUdoc.setAttribute("id", idUdoc);
			SelectionGroups['udocsCajaSeleccionada'].addItem(idUdoc);
			count ++;
		}
	}
	udocsCajaSeleccionada.setAttribute("nudocsEnCaja", count);

	xDisplay("msgNoCajaSeleccionada", "none");
	xGetElementById("labelCajaSeleccionada").style.color = '#D6062C';
	xGetElementById("labelCajaSeleccionada").innerHTML = "Caja "+(numeroCaja+1);
	xGetElementById("linkAbrirCaja"+numeroCaja).style.color = '#D6062C';

	if (!cajasAbiertas[numeroCaja]) {
		xGetElementById("udocscaja"+numeroCaja).style.display = 'block';
		cajaAbierta(numeroCaja);
	}
}

function removeIDs(element) {
	var childs = element.childNodes;
	if (childs)
		for (var i in childs) 
			removeIDs(childs[i]);
	if (element.id)
		element.removeAttribute("id");
}

function dividirUdoc(evt) {
	stopPropagation(evt);
	var selectedUdoc =  null;
	for (var i in selectedRows)
		selectedUdoc = selectedRows[i].DOMObject;
	if (selectedUdoc == null)
		alert('Seleccione una Unidad.');
	else {
		var splitedUdocID = selectedUdoc.id.split("_");
		var udocID = splitedUdocID[0];
		numeroParte =  parseInt(splitedUdocID[1],10);
		var numPartesUdoc = parseInt(selectedUdoc.getAttribute("numPartes"),10);
		if (numeroParte < numPartesUdoc) {
			alert("Sólo es posible dividir la última parte creada de la Unidad.");
			return;
		}
		numPartesUdoc ++;
		var newRowID = udocID+'_'+numPartesUdoc;
		//alert(newRowID);
		changeManager.udocPartCreation(newRowID);
		var newRow = createUnasignedUdocElement(newRowID);
		var insertPlace = selectedUdoc.nextSibling;
		if (insertPlace != null)
			selectedUdoc.parentNode.insertBefore(newRow,insertPlace);
		else
			selectedUdoc.parentNode.appendChild(newRow);

		xGetElementById("titulo"+newRowID).innerHTML = xGetElementById('titulo'+selectedUdoc.id).innerHTML;
		addUdocNoAsignada(newRowID);
		for (var i=1;i<=numPartesUdoc;i++) {
			xGetElementById(udocID+'_'+i).setAttribute("numPartes",""+numPartesUdoc);
			xGetElementById("parte"+udocID+'_'+i).innerHTML = "("+i+"/"+numPartesUdoc+") ";
		}
	}
}

function createUnasignedUdocElement(id) {
	var element = document.createElement("div");
	element.setAttribute(xMoz?"class":"className","Udocs");
	element.setAttribute("id", id);
	element.setAttribute("idUdoc", id);

	//element.style.backgroundColor = "#ff0000";
	//element.onClick = selectRow;
	var numParte = document.createElement("span");
	numParte.setAttribute("id", "parte"+id);
	element.appendChild(numParte);
	numParte.setAttribute(xMoz?"class":"className","tituloUdoc");
	numParte.style.fontWeight = 'bold';

	var titulo = document.createElement("span");
	titulo.setAttribute("id","titulo"+id);
	element.appendChild(titulo);
	titulo.setAttribute(xMoz?"class":"className","tituloUdoc");
	return element;
}

function eliminarParteUdoc(evt) {
	stopPropagation(evt);
	var selectedUdoc =  null;
	for (var i in selectedRows)
		selectedUdoc = selectedRows[i].DOMObject;
	if (selectedUdoc == null)
		alert('Debe seleccionar la Unidad a dividir.');
	else {
		var numPartesUdoc = parseInt(selectedUdoc.getAttribute("numPartes"),10);
		if (numPartesUdoc == 1)
			alert('La Unidad seleccionada no ha sido dividida en partes');
		else {
			var splitedUdocID = selectedUdoc.id.split("_");
			var udocID = splitedUdocID[0];
			numeroParte =  parseInt(splitedUdocID[1],10);
			if (numeroParte != numPartesUdoc)
				alert("Sólo es posible eliminar la última parte creada de la Unidad.");
			else {
				removeUdocNoAsignada(selectedUdoc.id);
				changeManager.udocPartDeletion(selectedUdoc.id);
				//alert(numPartesUdoc + '-' + numeroParte);
				selectedUdoc.parentNode.removeChild(selectedUdoc);
				numPartesUdoc --;
				for (var i=1;i<=numPartesUdoc;i++) {
					xGetElementById("parte"+udocID+'_'+i).innerHTML = numPartesUdoc > 1?"("+i+"/"+numPartesUdoc+")":"";
					xGetElementById(udocID+'_'+i).setAttribute("numPartes",""+numPartesUdoc);
				}
			}
		}
	}
}

function selectableItemSelected(e) {
	//alert("item selected");
	var evt = new xEvent(e);
	var selectedItem = evt.target;
	xStopPropagation(e);
	var itemGroup = selectedItem.getAttribute('itemGroup');
	while (!itemGroup && selectedItem.parentNode) {
		selectedItem = selectedItem.parentNode;
		itemGroup = selectedItem.getAttribute('itemGroup');
	}
	//alert("itemGroup: "+itemGroup);
	if (itemGroup != null)
		SelectionGroups[itemGroup].selectItem(selectedItem.id);
}

var SelectionGroups = new Array()
function SelectableItemsGroup(groupID) {
	this.id = groupID;
	this.items = new Array();
	this.selectedItemID = null;
	this.selectionEnabled = true;
	this.selectionColor = '#a0a0a0';
	SelectionGroups[groupID] = this;

	this.addItem = function (itemID) {
		//alert('Adding to selectionGroup: '+this.id+' item: '+itemID);
		var item = xGetElementById(itemID);
		//alert(item);
		if (item != null) {
			item.setAttribute('itemGroup',this.id);
			this.items.push(itemID);
			xAddEventListener(itemID,'click',selectableItemSelected,false);
			//alert(item.onclick);
		}
	}
	this.removeItem = function (itemID) {
		xRemoveEventListener(itemID,'click',selectableItemSelected,false);
	}
	this.selectItem = function (itemID) {
		//alert(itemID);
		if (!this.selectionEnabled) return;
		if (this.selectedItemID) {
			//alert(this.selectedItemID);
			var selectedItem = xGetElementById(this.selectedItemID);
			//alert(selectedItem.getAttribute("originalColor"));
			selectedItem.style.backgroundColor = selectedItem.getAttribute("originalColor");
		}
		var item = xGetElementById(itemID)
		//alert(item.style.backgroundColor);
		item.setAttribute("originalColor", item.style.backgroundColor);
		item.style.backgroundColor = "#a0a0a0";
		this.selectedItemID = itemID;
	}
	this.clearSelection = function() {
		if (this.selectedItemID) {
			var selectedItem = xGetElementById(this.selectedItemID);
			selectedItem.style.backgroundColor = selectedItem.getAttribute("originalColor");
			this.selectedItemID = null;
		}
	}
	this.reset = function() {
		this.selectedItemID = null;
		this.items.splice(0, this.items.length);
	}
	this.setSelectionEnabled = function(enable) {
		this.selectionEnabled = enable;
	}

}

function removeSelections(e) {
	//if (e) alert(e.eventPhase);
	//alert("removing selections");
	if (SelectionGroups)
		for (var group in SelectionGroups)
			SelectionGroups[group].clearSelection();
	clearSelection();
	hideDescripcionContenido();
	//xDisplay('descripcionContenido', 'none');
}

function stopPropagation(evt) {
	//alert("stop propagation");
	xStopPropagation(evt);
	xPreventDefault(evt);
	//return false;
}

var udocsNoAsignadas = new Array();
function addUdocNoAsignada(idUdoc) {
	//alert(idUdoc);
	udocsNoAsignadas[idUdoc] = xGetElementById(idUdoc);
	//alert(udocsNoAsignadas[idUdoc]);
	xAddEventListener(udocsNoAsignadas[idUdoc], 'click', selectRow);
}

function removeUdocNoAsignada(idUdoc) {
	delete udocsNoAsignadas.idUdoc;
	xRemoveEventListener(idUdoc, 'click', selectRow);
}