var CON_CAMBIOS = "1";

function cambiosSinGuardar(){
	if(document.forms[0].cambios && document.forms[0].cambios.value == CON_CAMBIOS) return true;
	else return false;
}

function actualizaEstado(){
	if(cambiosSinGuardar()) window.status ="Cambios Pendientes de Grabar";
}

function cambio(){
	if(document.forms[0].cambios) document.forms[0].cambios.value = CON_CAMBIOS;
}

function llamadaActionComprobarCambios(action,comprobarCambios,msgCambios) {
	if(comprobarCambios && cambiosSinGuardar()){
		if(window.confirm(msgCambios)){
			llamadaAction(action);
		}
	}
	else llamadaAction(action);

}


function addControlCambios(){
	if(!cambiosSinGuardar()){
		var formulario = document.forms[0];

		if(formulario){
			  var campos = formulario.getElementsByTagName("input");
			  if(campos){
				  for(var i=0; i<campos.length; i++) {
					  campo = campos[i];
					  if(campo){
						 if(campo.type == "text" || campo.type== "checkbox" || campo.type=="radio"){
							 addEvento(campo, "change", function(){cambio();});
						 }
					  }
				  }
			  }

			  var textAreas = formulario.getElementsByTagName("textarea");
			  if(textAreas){
				  for(var j=0; j<textAreas.length; j++) {
					  textArea = textAreas[j];
					  if(textArea){
						  addEvento(textArea, "change", function(){cambio();});
					  }
				  }
			  }

			  var selects = formulario.getElementsByTagName("select");
			  if(selects){
				  for(var k=0; k<selects.length; k++) {
					  select = selects[k];
					  if(select){
						  addEvento(select, "change", function(){cambio();});
					  }
				  }
			  }
		}
	}
}

function addEvento(elemento, evento, funcion) {
	if (elemento.addEventListener) {
		elemento.addEventListener(evento, funcion, false);
    } else {
	elemento.attachEvent("on"+evento, funcion);
    }
}