//**********************

function ClassFormsToolKit(){
	//sin hacer


	this.fillSelect = function(objectId, args){

	}

	this.clearSelect = function(objectID){
		object = document.getElementById(objectID);
		var noptions = object.options.length;
		for(i=0;i<noptions;i++){
			object.options[object.options.length-1] = null;//revisar esto en mozilla
		}
	}

	//TODO esto creo q solo es valido para explorer
	this.setVisible = function(objectID, visible){

		object = document.getElementById(objectID);
		if(visible){
			//object.style.visibility='visible';
			object.style.display='block';
		}
		else {
			//object.style.visibility='hidden';
			object.style.display='none';
		}
	}

	this.setValue = function (objectID,value){
		object = document.getElementById(objectID);
		object.value=value;
	}

	this.setChecked = function(objectID,value){
		object = document.getElementById(objectID);
		object.checked = value;
	}

	this.getNumSelectedChecked=function(form,namecheck){
		totalSeleccionados=0;
		if (objectIsArray(form.elements[namecheck])){
			for(i=0;i<form.elements[namecheck].length;i++){
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

	this.getSelectedChecked = function(form,namecheck){
		if (objectIsArray(form.elements[namecheck])){
			for(i=0;i<form.elements[namecheck].length;i++){
				if(form.elements[namecheck][i].checked){
					return form.elements[namecheck][i].value;
				}
			}
		}else if (form.elements[namecheck].checked){
			return form.elements[namecheck].value;
		}
	}

	this.getObjectSelected = function(form, nameoptions){
		if (objectIsArray(form.elements[nameoptions])){
			for(i=0;i<form.elements[nameoptions].length;i++){
				if(form.elements[nameoptions][i].checked){
					return form.elements[nameoptions][i];
				}
			}
		}else if (form.elements[nameoptions].checked){
			return form.elements[nameoptions];
		}
	}

	this.unselectOptions = function(form, nameoptions){
		if (objectIsArray(form.elements[nameoptions])){
			for(i=0;i<form.elements[nameoptions].length;i++){
				form.elements[nameoptions][i].checked=false;
			}
		}else if (form.elements[nameoptions].checked){
			form.elements[nameoptions].checked=false;
		}
	}

	this.isNSelectedChecked=function(form,namecheck,N){
		var numeroSeleccionados = this.getNumSelectedChecked(form,namecheck,N);
		return (numeroSeleccionados==N);
	}


	this.execClick = function(objectID){
		object = document.getElementById(objectID);
		if (object.click) {
			object.click();
		}
	}

	this.sinGuardar=false;

	this.activateFlagSinGuardar= function(){
		this.sinGuardar = true;
	}

	this.avisoSinGuardar= function(urlRedirect){
	if (this.sinGuardar){
		if (confirm("Se han realizado cambios y no han sido guardados. Si continua los cambios se perderán. ¿Desea continuar?")){
			window.location = urlRedirect;
		}
	}else
		window.location = urlRedirect;
	}

	function objectIsArray(object){
		if (object != null && object.length) return true;
		return false;
	}

}

var FormsToolKit = new ClassFormsToolKit();


////esta clase pensada para añadir objetos y trabajar sobre todos ellos de forma simultanea
//function FormObjectContainer(){
//	var objectsIDs = new Array();
//	var nElements = 0;
//
//	this.addObject() = new function(objectID){
//		objectsIDs[nElements++] = objectID;
//	}
//
//	this.setVisible = new function(visible){
//		for(i=0;i<nElements;i++){
//			objectId = objectsIDs[i];
//			PropertysToolKit.setVisible(visible, objectId);
//		}
//	}
//	this.setEnabled = new function(enabled){
//		for(i=0;i<nElements;i++){
//			objectId = objectsIDs[i];
//			PropertysToolKit.setVisible(visible, objectId);
//		}
//	}
//}

