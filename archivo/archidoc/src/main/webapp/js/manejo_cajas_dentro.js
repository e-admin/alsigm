
var cajaSeleccionada = null;

function UdocPart (partID,caja, pos)  {
	this.partID = partID;
	this.caja = caja;
	this.pos = pos;

	this.assign = function(caja, pos) {
		this.caja = caja;
		this.pos = pos;
	}
	this.toString = function() {
		var asString = this.partID;
		if (this.caja)
			asString += '_'+this.caja+'_'+this.pos;
		return asString;
	}
}

function ChangeManager() {
	this.udocsRelacion = new Array();
	this.changes = new Array();


	this.getChanges = function () {
		var createdParts = new Array();
		var asignedParts = new Array();
		var removedParts = new Array();
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
					else
						createdParts[createdParts.length] = changedUdocPart.toString();
				}
			}
		}
		return {'createdParts':createdParts,'asignedParts':asignedParts,'removedParts':removedParts};
	}

	this.registerUdocChanged = function (partID) {
		var udocPart = this.changes[partID];
		if (udocPart == null) {
			udocPart = this.udocsRelacion[partID];
			this.changes[partID] = new UdocPart(udocPart.partID,udocPart.caja,udocPart.pos);
		}
	}
	this.udocAsignment = function (partID, caja, pos) {
		//alert(partID);
		this.registerUdocChanged(partID);
		this.changes[partID].assign(caja.substring(4),pos);
	}
	this.udocUnasignment = function (partID, caja) {
		this.registerUdocChanged(partID);
		this.changes[partID].assign(null,-1);
	}
	this.udocPositionUpdated = function (partID, pos) {
		//alert(partID + ' :: '+pos);
		this.registerUdocChanged(partID);
		this.changes[partID].pos = pos;
		//alert(this.changes[partID].toString());
	}
	this.udocPartCreation = function (partID) {
		//alert(partID);
		this.changes[partID] = new UdocPart(partID, null, -1);
	}
	this.udocPartDeletion = function (partID) {
		//alert(partID);
		this.registerUdocChanged(partID);
		this.changes[partID] = null;
	}

	this.addUdocRelacion = function (partID, caja, pos)  {
		var cajaUdoc = caja!=null?caja.substring(4):null;
		var udoc = new UdocPart(partID.substring(4),cajaUdoc , pos);
		this.udocsRelacion[partID.substring(4)] = udoc;
		return udoc
	}
}

var changeManager = new ChangeManager();

function activateSeleccionCaja(value) {
	for (var i=0;i<numeroCajas;i++) {
		var capaSeleccionCaja = xGetElementById('seleccion'+udocsXCaja.keys[i]);
		if (capaSeleccionCaja) {
			xDisplay(capaSeleccionCaja, value?'inline':'none');
			xWidth(capaSeleccionCaja, xWidth(udocsXCaja.keys[i]));
		}
	}
}


var selectedRows = new HashMap();

function SelectableItem(object) {
	this.DOMObject = object;
	this.originalBackgroundColor = object.style.backgroundColor;
	this.numPartes = 1;
}

function ArrayList() {
	this.length = 0;
	this.values = new Array();
	this.indexOf = function(value) {
		var pos = -1;
		for(var i=0;i<this.length;i++)
			if (this.values[i] == value) {pos = i; break; }
		return pos;
	}
	this.add = function(value) {
		this.values[this.length++] = value;
	}
	this.remove = function(posToRemove) {
		var valueToRemove = null;
		if (! (posToRemove < 0)) {
			valueToRemove = this.values[posToRemove];
			for (var i=posToRemove;i<this.length-1;i++)
				this.values[i] = this.values[i+1];
			this.values.pop();
			this.length--;
		}
		return valueToRemove;
	}
	this.toString = function (separator) {
		return this.values.join(separator);
	}
}

function HashMap() {
	this.length = 0;
	this.keys = new Array();
	this.values = new Array();
	this.indexOf = function(key) {
		var pos = -1;
		for(var i=0;i<this.length;i++)
			if (this.keys[i] == key) {pos = i; break; }
		//alert('index of '+key+' is: '+pos);
		return pos;
	}
	this.put = function(key, value) {
	//alert('put: '+key);
		var pos = this.indexOf(key);
		if (pos < 0)
			pos = this.length++;
		this.keys[pos] = key;
		this.values[pos] = value;
		//alert(pos+'-'+this.keys[pos]+'-'+this.values[pos]);
	}
	this.get = function(key) {
		var i = this.indexOf(key);
		//alert('get from  pos: '+i);
		return i<0?null:this.values[i];
	}
	this.getValue = function(pos) {
		return this.values[pos];
	}
	this.containsKey = function(key) {
		return !(this.indexOf(key)<0);
	}
	this.remove = function(key) {
	//alert('removed: '+key);
		var posToRemove = this.indexOf(key);
		var valueToRemove = null;
		if (! (posToRemove < 0)) {
			valueToRemove = this.values[posToRemove];
			for (var i=posToRemove;i<this.length-1;i++) {
				this.keys[i] = this.keys[i+1];
				this.values[i] = this.values[i+1];
			}
			this.keys.pop();
			this.values.pop();
			this.length--;
		}
		return valueToRemove;
	}
}


var udocsXCaja = new HashMap();
function addCaja(idCaja) {
	udocsXCaja.put(idCaja, new ArrayList());
}
function addUdoc(idCaja, idUdoc) {
	//alert("adding "+idCaja+"-"+idUdoc);
	var udocsEnCaja = udocsXCaja.get(idCaja);
	if (udocsEnCaja == null) 
		udocsEnCaja = new ArrayList();
	udocsEnCaja.add(idUdoc.substr(4));
	udocsXCaja.put(idCaja, udocsEnCaja);
	document.all[idUdoc].caja = idCaja;
}
function removeUdoc(idCaja, idUdoc) {
	var udocsEnCaja = udocsXCaja.get(idCaja)
	udocsEnCaja.remove(udocsEnCaja.indexOf(idUdoc.substr(4)));
	if (udocsEnCaja.length == 0)
		xDisplay('empty'+idCaja, 'block')

}

var udocsNoAsignadas = new ArrayList();
function addUdocNoAsignada(idUdoc) {
	udocsNoAsignadas.add(idUdoc);
}

function removeUdocNoAsignada(idUdoc) {
	udocsNoAsignadas.remove(udocsNoAsignadas.indexOf(idUdoc))
}

/*function xdOnMouseDown(e) {
	var evt = new xEvent(e);
	var dragIcon = xGetElementById('dragSymbol');
	xVisibility(dragIcon, false);
	dragIcon.style.display = 'inline';
	dragIcon.xDragX = evt.pageX;
	dragIcon.xDragY = evt.pageY; 
	xMoveTo(dragIcon,dragIcon.xDragX - xWidth(dragIcon)/2,dragIcon.xDragY - xHeight(dragIcon)/2);
	xVisibility(dragIcon, true);
	alert(xHeight(dragIcon));
	xDragMgr.mm = true;
	xDragMgr.ele = dragIcon;
	xAddEventListener(document,'mouseup',xdOnMouseUp,false);
	xAddEventListener(document,'mousemove',xdOnMouseMove,false);
	//activateSeleccionCaja(true);
}
function xdOnMouseMove(e) {
	var evt = new xEvent(e);
	if (xDragMgr.ele) {
		if (e && e.preventDefault) e.preventDefault();
		else if (window.event) window.event.returnValue = false;
		var ele = xDragMgr.ele;
		var dx = evt.pageX - ele.xDragX;
		var dy = evt.pageY - ele.xDragY;
		ele.xDragX = evt.pageX;
		ele.xDragY = evt.pageY;
		xMoveTo(ele,xLeft(ele)+dx,xTop(ele)+dy);
	}
}*/

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

/*function xdOnMouseUp(e) {
	if (xDragMgr.ele) {
		if (e && e.preventDefault) e.preventDefault();
		else if (window.event) window.event.returnValue = false;
		xRemoveEventListener(document,'mousemove',xdOnMouseMove,false);
		xRemoveEventListener(document,'mouseup',xdOnMouseUp,false);
		xDragMgr.ele.style.display = 'none';
		if (cajaSeleccionada != null) {
			var cajaDestino = cajaSeleccionada;
			xDisplay('empty'+cajaDestino, 'none')

			var udocsCajaDestino = document.all['udocs'+cajaDestino];
			//alert(cajaDestino);
			for (var i=0;i<selectedRows.length;i++) {
				//var nUdocsEnCaja = udocsXCaja.get(cajaDestino);
				//if (nUdocsEnCaja == null) nUdocsEnCaja = 0;
				var rowToRemove = selectedRows.getValue(i).DOMObject;
				var udocLayerId = rowToRemove.id;

				var udocsEnCaja = udocsXCaja.get(cajaDestino);
				changeManager.udocAsignment(udocLayerId.substring(4),cajaDestino,udocsEnCaja.length);

				//alert(udocLayerId);
				var udocLayerContent = rowToRemove.innerHTML;
				//alert("-- "+udocLayerId);
				var newRow = document.createElement("<div class='Udocs' id=\""+udocLayerId+"\">");
				var udocNumPartes = rowToRemove.getAttribute("numPartes");
				if (udocNumPartes != null)
					newRow.setAttribute("numPartes",udocNumPartes);
				newRow.setAttribute("udocPos", udocsCajaDestino.length);
				rowToRemove.outerHTML = "";				
				//newRow.innerHTML = udocRowContent(udocLayerId, udocLayerContent);
				newRow.innerHTML = udocLayerContent;
				//alert(newRow.innerHTML);
				udocsCajaDestino.appendChild(newRow);
				//newRow.innerHTML = newRow.innerHTML + controlForUdoc;
				if (!xGetElementById('udocs').hasChildNodes())
					xDisplay('emptyUdocsSinCaja', 'inline');
				addUdoc(cajaDestino, udocLayerId);
				removeUdocNoAsignada(udocLayerId.substring(4));
				//alert(SelectionGroups[cajaDestino]);
				SelectionGroups[cajaDestino].addItem(udocLayerId);
			}
		}
		//activateSeleccionCaja(false);
		xDragMgr.ele = null;
	}
}*/


function findRow(rowID) {
	var clickedRow = null;
	if (document.all) clickedRow = document.all[rowID];
	else if (document.getElementById) clickedRow = document.getElementById(rowID);
	return clickedRow;
}


function clearSelection() {
	var aSelectedItem;
	while (selectedRows.length > 0 ) {
		aSelectedItem = selectedRows.remove(selectedRows.keys[selectedRows.length - 1]);
		aSelectedItem.DOMObject.style.backgroundColor = aSelectedItem.originalBackgroundColor;
		xRemoveEventListener(aSelectedItem.DOMObject,'mousedown',xdOnMouseDown,false);
	}
}
function selectRow(selectedRow,e) {
	//var selectedRow = findRow(rowID);
	var selectionEvent;
	if (document.all) selectionEvent = window.event;
	else if (document.getElementById) selectionEvent = e;
	if (!selectionEvent.ctrlKey) {
		clearSelection();
	}
	var rowID = selectedRow.id;
	if (selectedRows.get(rowID) == null) {		
		selectedRows.put(rowID, new SelectableItem(selectedRow));		
	} else
		alert('Esta ya estaba seleccionada');
	selectedRow.style.backgroundColor = "#a0a0a0";
	xAddEventListener(rowID,'mousedown',xdOnMouseDown,false);
}

var xDragMgr = {ele:null, mm:false};

// implementar en clase generica Box que puede contener cosas y puede abrirse y cerrarse manteniendose las que se encuentran abiertas
var cajasAbiertas = new ArrayList();
function clickOn(idCaja, numCaja) {
	var posCaja = cajasAbiertas.indexOf(numCaja);
	var udocsCajaLayerName = "udocs"+idCaja;
	var udocsCajaLayer = document.all[udocsCajaLayerName];
	if (posCaja < 0) {
		//cajasAbiertas.add(idCaja.substr(4));
		udocsCajaLayer.style.display = 'block';
		cajaAbierta(idCaja, numCaja);
	} else {
		cajasAbiertas.remove(posCaja);
		udocsCajaLayer.style.display = 'none';
		xGetElementById('icono'+idCaja).src = imagesSrcs[0];
	}
}
function cajaAbierta(idCaja, numCaja) {
	cajasAbiertas.add(numCaja);
	xGetElementById('icono'+idCaja).src = imagesSrcs[1];
}

function selectCajaDestino(idCaja, value) {
	cajaSeleccionada = value?idCaja:null;
	document.all[idCaja].style.backgroundColor = value?'#a0a0a0':'#F2F2F2';
}

/*
function subirUdoc(udocGroup) {
	var udocToMove = SelectionGroups[udocGroup].selectedItem;
	if (udocToMove) {
		var previousUdoc = udocToMove.previousSibling;
		if (previousUdoc) {
			var udocPos = parseInt(udocToMove.getAttribute("udocPos"),10);
			changeManager.udocPositionUpdated(udocToMove.id.substring(4), (udocPos - 1));
			var udocToMoveContainer = udocToMove.parentNode;
			var removedNode = udocToMoveContainer.removeChild(udocToMove);
			removedNode.setAttribute("udocPos", (udocPos - 1));
			udocToMoveContainer.insertBefore(removedNode,previousUdoc);
			previousUdoc.setAttribute("udocPos", udocPos);
			changeManager.udocPositionUpdated(previousUdoc.id.substring(4), udocPos);
		}
	}
}

function bajarUdoc(udocGroup) {
	var udocToMove = SelectionGroups[udocGroup].selectedItem;
	if (udocToMove) {
		var nextUdoc = udocToMove.nextSibling;
		if (nextUdoc) {
			var udocPos = parseInt(udocToMove.getAttribute("udocPos"),10);
			var udocToMoveContainer = udocToMove.parentNode;
			var removedNode = udocToMoveContainer.removeChild(udocToMove);
			removedNode.setAttribute("udocPos", (udocPos + 1));
			changeManager.udocPositionUpdated(udocToMove.id.substring(4), (udocPos + 1));
			if (nextUdoc.nextSibling)
				udocToMoveContainer.insertBefore(removedNode,nextUdoc.nextSibling);
			else
				udocToMoveContainer.appendChild(removedNode);
			nextUdoc.setAttribute("udocPos", udocPos);
			changeManager.udocPositionUpdated(nextUdoc.id.substring(4), udocPos);
		}
	}
}

function quitarUdoc(udocGroup) {
	var udocToMove = SelectionGroups[udocGroup].selectedItem;
	if (udocToMove) {
		removeUdoc(udocToMove.caja, udocToMove.id);
		addUdocNoAsignada(udocToMove.id.substring(4));
		changeManager.udocUnasignment(udocToMove.id.substring(4),udocToMove.caja);
		var udocLayerContent = xGetElementById("titulo"+udocToMove.id).outerHTML;
		var newRow = document.createElement("<div class='Udocs' id=\""+udocToMove.id+"\" onClick=\"selectRow(this)\" style=\"cursor:default;width:100%\" unselectable=\"on\">");
		var udocNumPartes = udocToMove.getAttribute("numPartes");
		if (udocNumPartes != null)
			newRow.setAttribute("numPartes",udocNumPartes);
		newRow.innerHTML = xGetElementById("parte"+udocToMove.id).outerHTML + udocLayerContent;
		udocToMove.outerHTML = "";
		xDisplay('emptyUdocsSinCaja', 'none')
		document.all['udocs'].appendChild(newRow);
		SelectionGroups[udocGroup].clearSelection();
	}
}
*/
/*
function guardarCambios(form) {
	form.elements["cajasAbiertas"].value = cajasAbiertas.toString(",");
	form.elements["udocsNoAsignadas"].value = udocsNoAsignadas.toString(",");
	var changes = changeManager.getChanges();
	//alert('partes creadas '+changes.createdParts.length);
	dumpArray(changes.createdParts);
	//alert('partes asignadas '+changes.asignedParts.length + ' - '+changes.asignedParts.join(','));
	dumpArray(changes.asignedParts);
	//alert('partes eliminadas '+changes.removedParts.length);
	dumpArray(changes.removedParts);
	form.elements['createdParts'].value = changes.createdParts.join(',');
	form.elements['asignedParts'].value = changes.asignedParts.join(',');
	form.elements['removedParts'].value = changes.removedParts.join(',');
	form.submit();
}

function dumpArray(array) {
	//alert("join "+array.join('|'));
	if (array.length > 0) {
		var arrayAsString = array[0];
		for (var i=1; i<array.length; i++) {
			arrayAsString += array[i]; 
			//alert(arrayAsString);
		}
	}
	return arrayAsString;
}
function eliminarCaja(idCaja) {
	var form = document.forms[0];
	form.method.value = "eliminarCaja";
	form.idCaja.value = idCaja.substr(4);
	guardarCambios(form);
}
*/



/*
function crearCaja() {
	var form = document.forms[0];
	form.method.value = "crearCaja";
	guardarCambios(form);
}
*/

/*
function dividirUdoc() {
	if (selectedRows.length != 1)
		alert('Para hacer uso de la division de expedientes debe tener seleccionado un expediente');
	else {
		var selectedUdoc =  selectedRows.getValue(0).DOMObject;
		var numParteUdoc = selectedUdoc.getAttribute("numPartes");
		//alert(numParteUdoc);
		var numParteAsInt = 2;			
		if (numParteUdoc != null)
			numParteAsInt = parseInt(numParteUdoc,10)+1;
		//alert(numParteAsInt);
		var udocID = selectedUdoc.id.split("_")[0];

		var newRowID = udocID+'_'+(numParteAsInt-1);
		//alert(newRowID);
		addUdocNoAsignada(newRowID.substring(4));
		changeManager.udocPartCreation(newRowID.substring(4));
		var newRow = document.createElement("<div class='Udocs' id=\""+newRowID+"\" onClick=\"selectRow(this)\" style=\"cursor:default;width:100%\" unselectable=\"on\">");
		newRow.innerHTML = "<span class=\"tituloUdoc\" id=\"parte"+newRowID+"\"></span> <span id=\"titulo"+newRowID+"\" class='tituloUdoc' unselectable='on'>"+xGetElementById('titulo'+selectedUdoc.id).innerHTML+"</span>";
		var insertPlace = selectedUdoc.nextSibling;
		if (insertPlace != null)
			selectedUdoc.parentNode.insertBefore(newRow,insertPlace);
		else
			selectedUdoc.parentNode.appendChild(newRow);
		for (var i=0;i<numParteAsInt;i++) {
			xGetElementById(udocID+'_'+i).setAttribute("numPartes",""+numParteAsInt);
			xGetElementById("parte"+udocID+'_'+i).innerHTML = "("+(i+1)+"/"+numParteAsInt+")";
		}
	}
}

function eliminarParteUdoc() {
	if (selectedRows.length != 1)
		alert('Para hacer uso de la division de expedientes debe tener seleccionado un expediente');
	else {
		var selectedUdoc =  selectedRows.getValue(0).DOMObject;
		if (selectedUdoc.getAttribute("numPartes") == null)
			alert('El expediente seleccionado no ha sido dividido en partes');
		else {
			var splitedUdocID = selectedUdoc.id.split("_");
			var udocID = splitedUdocID[0];
			numeroParte =  parseInt(splitedUdocID[1],10);
			var numPartesUdoc = parseInt(selectedUdoc.getAttribute("numPartes"),10) - 1;
			if (numeroParte < numPartesUdoc) {
				alert("Solo es posible eliminar la ultima parte del expediente creada");
			}else {
				removeUdocNoAsignada(selectedUdoc.id.substring(4));
				changeManager.udocPartDeletion(selectedUdoc.id.substring(4));
				//alert(numPartesUdoc + '-' + numeroParte);
				//selectedUdoc.outerHTML = "";
				selectedUdoc.parentNode.removeChild(selectedUdoc);
				for (var i=numeroParte +1;i<=numPartesUdoc;i++) {
					//xGetElementById(udocID+'_'+i).setAttribute("numPartes",""+numParteAsInt);
					xGetElementById(udocID+'_'+i).setAttribute("id",udocID+"_"+(i-1));
					//alert(xGetElementById("parte"+udocID+'_'+i).innerHTML);
					//parteUdocLabel = xGetElementById("parte"+udocID+'_'+i);
					xGetElementById("parte"+udocID+'_'+i).id = "parte"+udocID+'_'+(i - 1);
					xGetElementById("titulo"+udocID+'_'+i).id = "titulo"+udocID+'_'+(i - 1);
					//alert(i+' new id '+parteUdocLabel.id);
				}
				if (numPartesUdoc == 1) {
					xGetElementById(udocID+'_0').removeAttribute("numPartes");
					xGetElementById("parte"+udocID+'_'+0).innerHTML = "";
				} else
					for (var i=0;i<numPartesUdoc;i++) {
						xGetElementById(udocID+'_'+i).setAttribute("numPartes",""+numPartesUdoc);
						xGetElementById("parte"+udocID+'_'+i).innerHTML = "("+(i+1)+"/"+numPartesUdoc+")";
					}
			}
		}
	}
}
*/
function selectableItemSelected(e) {
	var evt = new xEvent(e);
	var selectedItem = evt.target;
	var itemGroup = selectedItem.getAttribute('itemGroup');
	if (itemGroup != null)
		SelectionGroups[itemGroup].selectItem(selectedItem);
	/*else
		alert("no tiene grupo");*/
}

var SelectionGroups = new Array()
function SelectableItemsGroup(groupID) {
	this.id = groupID;
	this.items = new Array();
	this.selectedItem = null;
	this.selectionColor = '#a0a0a0';
	SelectionGroups[groupID] = this;

	this.addItem = function (itemID) {
		//alert('Adding to selectionGroup: '+this.id+' item: '+itemID);
		var item = xGetElementById(itemID);
		if (item != null) {
			item.setAttribute('itemGroup',this.id);
			this.items[itemID] = item;
			xAddEventListener(item,'click',selectableItemSelected,false);
		}
	}
	this.selectItem = function (item) {
		if (this.selectedItem)
			this.selectedItem.style.backgroundColor = this.selectedItem.originalColor;
		item.originalColor = item.style.backgroundColor
		item.style.backgroundColor = "#a0a0a0";
		this.selectedItem = item;
	}
	this.clearSelection = function() {
		this.selectedItem.style.backgroundColor = this.selectedItem.originalColor;
		this.selectedItem = null;
	}
}
