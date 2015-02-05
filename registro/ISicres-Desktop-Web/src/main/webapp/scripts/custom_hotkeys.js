function initiate(){
	//Busqueda de registros
	jQuery(document).bind('keydown', 'Ctrl+b',function (evt){invocarBuscarRegistro(evt);return false;});

	//Nuevo Registro (Pantalla Inicial: NewFolderBtn / Pantalla de registros: NewBtn)
	jQuery(document).bind('keydown', 'Ctrl+u',function (evt){invocarNuevoRegistro(evt);return false;});

	//Copiar Registro (activo desde resultado de busqueda de registros)
	jQuery(document).bind('keydown', 'Ctrl+c',function (evt){copiarRegistro(evt);return false;});

	//Propiedades del Registro (activo desde Bandeja de Distribucion)
	jQuery(document).bind('keydown', 'Ctrl+Return',function (evt){visualizarPropiedadesRegistro(evt);return false;});

	//Sello de validación
	jQuery(document).bind('keydown', 'Ctrl+s',function (evt){actionButtonFormRegistro(evt,'FolderBar','#selloReg'); return false; });

	//Guardar Registro
	jQuery(document).bind('keydown', 'Shift+f8',function (evt){searchElement(evt,'FolderBar','#SaveMenuBtn'); return false; });
	jQuery(document).bind('keydown', 'Shift+f11',function (evt){searchElement(evt,'FolderBar','#SaveMenuBtn'); return false; });

	//Capturar documentos
	jQuery(document).bind('keydown', 'Shift+f12',function (evt){mostrarMenuDocumentos(evt); return false; });

	//Origen
	jQuery(document).bind('keydown', 'Ctrl+Shift+1',function (evt){invocarValidacionCamposRegistro(evt,'FolderFormData','1014'); return false; });

	//Destino
	jQuery(document).bind('keydown', 'Ctrl+Shift+2',function (evt){invocarValidacionCamposRegistro(evt,'FolderFormData','1016'); return false; });

	//Destinatarios
	jQuery(document).bind('keydown', 'Ctrl+Shift+3',function (evt){invocarBuscarInteresados(evt,'Interesados','imgHelp'); return false; });

	//Tipo de asunto
	jQuery(document).bind('keydown', 'Ctrl+Shift+4',function (evt){invocarValidacionCamposRegistro(evt,'FolderFormData','1053'); return false; });

	//Tipo de transporte
	jQuery(document).bind('keydown', 'Ctrl+Shift+7',function (evt){invocarValidacionCamposRegistro(evt,'FolderFormData','1063'); return false; });

	//Obtenemos todos los frames de la pagina y los añadimos a una tabla hash global
	getAllFrames(window);

}

//Metodo que invoca al evento de PROPIEDADES DEL REGISTRO
function visualizarPropiedadesRegistro(evt){
	if ((top.Main.document.getElementById("Distr")) && (top.Main.document.getElementById("Distr").tabIndex == 1)){
		//obtenemos el boton de propiedades del registro
		var obj = top.Main.Distr.document.getElementById("Propiedades");
		if(obj != null){
			//ejecutamos su accion
			obj.click();
		}
	}
}

//Metodo que invoca al evento de COPIAR REGISTRO
function copiarRegistro(evt){
	//comprobamos si el frame esta activo
	if((top.Main.document.getElementById("Table")) && (top.Main.document.getElementById("Table").tabIndex == 1)){
		//obtenemos el boton de copiar registro
		var obj = top.Main.Table.TableData.document.getElementById("opCopy");
		if(obj != null){
			//ejecutamos su accion
			obj.click();
		}
	}
}

//Metodo que invoca al ESCANER()
function mostrarMenuDocumentos(evt){
	var frame = top.Main.Folder.FolderData.FolderFormTree;

	//situamos el foco
	if(frame.document.getElementById("lbAnexos")){
	frame.document.getElementById("lbAnexos").focus();
	}

	frame.ShowMenu(evt, 0, "R0");
	frame.PromptName(5);
}

//Metodo que invoca a la BUSQUEDA DE INTERESADOS
function invocarBuscarInteresados(evt, frame, element){
	//obtenemos el iframe de los Interesados
	var iframe = top.Main.Folder.FolderData.FolderFormData.document.getElementById(frame);
	//obtenemos el objeto con el que trabajamos imgHelp
	var obj = iframe.contentWindow.document.getElementById(element);
	//lanzamos el evento
	obj.click();

}

//Metodo que sirve para invocar a los CAMPOS VALIDADOS DEL FORMULARIO DE REGISTRO
function invocarValidacionCamposRegistro(evt, frame, element){
	if(navigator.appName == "Microsoft Internet Explorer"){
		//lanzamos el evento onblur del elemento que tiene el foco
		executeOnBlurElmentoActivo(evt);
		//obtenemos el iframe de los Interesados
		var iframe = top.Main.Folder.FolderData.document.getElementById(frame);
		//obtenemos el objeto con el que trabajamos imgHelp
		var obj = iframe.contentWindow.document.getElementById(element);
		VldHelp(evt, obj);
	}else{
		searchElement(evt, frame, "#" + element);
	}

}

//evento para invocar a NUEVO REGISTRO
function invocarNuevoRegistro(evt){
	//pantalla inicial - Pasamos a comprobar que frame esta activo
	var frame = "Workspace";
	if(top._frames[frame] != undefined){
		//obtenemos el frame activo
		frame = obtenerFrameActivoNuevoRegistro();
		//si existe el frame activo
		if(frame != ""){
			//lanzamos la operativa
			searchElement(evt,frame,'#NewFolderBtn');
		}
	}else{
		//pantalla crear registros en cadena - Formulario de Registro
		frame = "FolderBar";
		actionButtonFormRegistro(evt, frame, '#NewBtn');
	}
}

//funcion que nos indica que frame esta activo
function obtenerFrameActivoNuevoRegistro(){
	var frame ="";

	if((top.Main.document.getElementById("Table")) && (top.Main.document.getElementById("Table").tabIndex == 1)){
		//Esta activo el resultado de busqueda en formato TABLA
		frame = "TableData";
	}else{
		if ((top.Main.document.getElementById("Folder")) && (top.Main.document.getElementById("Folder").tabIndex == 1)){
			//Esta activo el resultado de busqueda en formato FORMULARIO
			frame = "ToolBarFrm";
		}else{
			if((top.Main.document.getElementById("Workspace")) && (top.Main.document.getElementById("Workspace").tabIndex != -1)){
				//Esta activo la busqueda de registros
				frame = "Workspace";
			}
		}
	}

	return frame;
}


//evento para invocar a la BUSQUEDA DE REGISTROS
function invocarBuscarRegistro(evt){
	var frame = "Workspace";
	var element ="";

	if (top._frames[frame] == undefined){
		return;
	}

	//comprobamos si el frame esta activo
	if((top.Main.document.getElementById(frame)) && (top.Main.document.getElementById(frame).tabIndex != -1)){
		//comprobamos la visibilidad del panel de botones de la busqueda para distinguir entre busqueda simple o avanzada
		if($(top._frames[frame].window.document).find("#seccion_tab").css("display") != 'none'){
			//busqueda simple
			element = "#buscar";
			obj = $(top._frames[frame].window.document).find(element);
		}else{
			//busqueda avanzada
			element = "#buscar_av";
			obj = $(top._frames[frame].window.document).find(element);
		}
		$(obj).click();
	}
}

//Metodo generico que invoca a las acciones de un elemento
function searchElement(evt, frame, element){
	if (top._frames[frame] == undefined){
		return;
	}

	//Buscamos el elemento en los frames
	obj = $(top._frames[frame].window.document).find(element);
	//Ejecutamos accion
	ejecutarAccion(evt, obj);
}

//Ejecutamos el evento onblur del elemento que esta activo en el momento del lanzamiento del evento
function executeOnBlurElmentoActivo(evt){
	//Obtenemos el elemento que tiene el foco
	var activeElement;
	if(!evt){//Para IE
		activeElement = evt.srcElement;
	}else{//Para el resto de navegadores
		activeElement = evt.target;
	}
	//Ejecutamos el evento onblur del objeto activo
	$(activeElement).blur();
}

//funcion que se utiliza para invocar a los botones del formulario del registro
function actionButtonFormRegistro(evt, frame, element){

	//Se fuerza la perdida del foco del campo desde el que se invoca las teclas
	executeOnBlurElmentoActivo(evt);

	//validamos la existencia del frame
	if (top._frames[frame] == undefined){
		return;
	}

	//Buscamos el elemento en los frames
	obj = $(top._frames[frame].window.document).find(element);
	//validamos que el boton este activo
	if((obj).attr("class") == "SubOptions"){
		// comprobamos si se debe activar la tecla de guardado, ya que se esta
		// habilitando
		comprobarCambiosRegistro();
		//ejecutamos accion
		ejecutarAccion(evt, obj);
	}
}

//funcion que comprueba si un registro ha sido modificado
function comprobarCambiosRegistro(){
	//comprobamos si ha habido cambios en el registro
	if(!top.g_changeDataRegistro){
		// desactivamos el boton de guardar ya que siempre se activa al
		// pulsar una tecla sobre un campo editable
		top.Main.Folder.FolderBar.DesactivateSave();
	}
}

function ejecutarAccion(evt, obj){
	//lanzamos el evento onblur del elemento que tiene el foco
	executeOnBlurElmentoActivo(evt);

	//Lanzamos el evento onclic del elemento que recibimos como parametro
	$(obj).click();
}

//Metodo que almacena los frames de las pantallas
function getAllFrames(win){
	 count = win.frames.length;
	 for(i=0 ; i<count ; i++){
		top._frames[win.frames[i].name] = win.frames[i];
	 }
}
$(document).ready(initiate);