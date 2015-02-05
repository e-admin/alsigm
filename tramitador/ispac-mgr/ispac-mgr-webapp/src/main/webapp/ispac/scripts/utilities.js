var utilitiesDefaultLanguage = "es";

var utilitiesConfirmSalirString = {
	es : '¿Est\u00e1 seguro que quiere salir de la aplicaci\u00f3n?',
	gl : 'Est\u00e1 seguro que quere sa\u00edr da aplicaci\u00f3n?',
	eu : 'Aplikaziotik atera nahi duzu?',
	ca : 'Est\u00e0 segur que vol sortir de l\\u0027aplicaci\u00f3?'
};

var utilitiesConfirmOperacionString = {
	es : 'Se requiere confirmaci\u00f3n para la operaci\u00f3n a realizar.\\nAceptar para Continuar, Cancelar para anular la operaci\u00f3n.',
	gl : 'Requ\u00edrese confirmaci\u00f3n para a operaci\u00f3n a realizar.\\nAceptar para continuar, Cancelar para anular a operaci\u00f3n.',
	eu : 'Baieztapena eskatzen du operazioarentzat egitera.\\nAdos jarraitzeko, Utzi eragiketa baliogabetzeko.',
	ca : 'Es requereix confirmaci\u00f3 per a l\\u0027operaci\u00f3 a realitzar.\\nAccepta per continuar, Cancel\u00b7la per anul\u00b7lar l\\u0027operaci\u00f3.'
};

var utilitiesAvisoString = {
	es : 'Aviso',
	gl : 'Aviso',
	eu : 'Abisu',
	ca : 'Av\u00eds'
};

var utilitiesPreguntaString = {
	es : 'Pregunta',
	gl : 'Pregunta',
	eu : 'Galdera',
	ca : 'Pregunta'
};

var utilitiesConfirmacionString = {
	es : 'Confirmaci\u00f3n',
	gl : 'Confirmaci\u00f3n',
	eu : 'Berrespena',
	ca : 'Confirmaci\u00f3'
};

var utilitiesAceptarString = {
	es : 'Aceptar',
	gl : 'Aceptar',
	eu : 'Ados',
	ca : 'Accepta'
};

var utilitiesCancelarString = {
	es : 'Cancelar',
	gl : 'Cancelar',
	eu : 'Utzi',
	ca : 'Cancel\u00b7la'
};


function getUtilitiesString(map) {
	var res = null;
	if (map != null) {
		if (typeof ispacLanguage != 'undefined') {
			res = map[ispacLanguage];
		}
		if (res == null) {
			res = map[utilitiesDefaultLanguage];
		}
	}
	return res;
}


//Envia el formulario a la url parametrizada
function submit(url)
{
	document.forms[0].action = url;
  	document.forms[0].submit();
}


function confirm_jquery(msg, aviso,accept,cancel){
	if(aviso==null){
		aviso=getUtilitiesString(utilitiesPreguntaString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}
	jConfirm(msg, aviso,accept, cancel, function(resultado) {
		if(resultado){
			return true;
		}
		else {
			return false;
		}

	});
	return false;
	}

	function messageConfirm(url, msg, aviso,accept, cancel) {
	if(aviso==null){
		aviso=getUtilitiesString(utilitiesPreguntaString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}
	jConfirm(msg, aviso,accept, cancel, function(resultado) {
		if(resultado){
		window.location.href=url;
		}
		else {
		return false;}

	});
	return false;


}
//	Calcula el contexto de la aplicacion
/*
function getContext() {
  return "/" + ((location.pathname).split("/")[1]);
}
*/

//	Abre una ventana
function openWin(url) {
  open(url,"popup","status=yes,scrollbars=yes,location=yes,toolbar=yes,screenX=200,screenY=100,width=500,height=400");
}



//Muestra el diseñador
function showDesigner(target, action, difwidth, difheight) {

  var width = 0, height = 0;
 width=window.document.body.clientWidth-difwidth;
 height=window.document.body.clientHeight;

  showFrame(target, action,  '');
}

//	Concatena los identificadores segun si se delegan fases o tramites
function delegate(action) {

	var form = document.forms[0];
	var url = replaceActionForm(form.action, action);
	var simbolo = "&";

	if (url.indexOf("?") == -1)
		simbolo = "?";

	if (form.idsStage.value != "") {
		url = url + simbolo + "idsStage=" + form.idsStage.value;
	}
	else if (form.idsTask.value != "") {
		url = url + simbolo + "idsTask=" + form.idsTask.value;
	}

	document.location.href = url;
}

//	Confirma la salida de la aplicacion
function exit() {
  var dato = confirm(getUtilitiesString(utilitiesConfirmSalirString))
  return dato;
}

// Comprueba si el objeto está oculto
function isHidden(obj) {
    var hidden = false;
    if ((obj != null) && (obj.style != null)) {
        hidden = (obj.style.display == 'none');
    }
    return hidden;
}

//	Oculta o muestra el listado de los procedimientos y tramites
function showHidden(i) {
	var img = document.getElementById('img'+i);
	var block = document.getElementById('block'+i);

	if (block != null) {
		if ((block.style.display == 'block') || (block.style.display == '')) {
			block.style.display='none';
			if (img != null) {
				img.src = img.src.replace('img/arrow_up.gif','img/arrow_down.gif');
			}
		} else {
			block.style.display='block';
			if (img != null) {
				img.src = img.src.replace('img/arrow_down.gif','img/arrow_up.gif');
			}
		}
	}
}

//	Oculta o muestra el listado de las entidades en el esquema
function hide_expand(i, imgOn, imgOff) {

	restoreNeedToConfirm();
	if(document.getElementById('imgsch'+i)!=null){
	img = document.getElementById('imgsch'+i).src;
	if(img!=null){
	if (document.getElementById('blocksch'+i).style.display == 'block') {
		document.getElementById('blocksch'+i).style.display='none';
		document.getElementById('imgsch'+i).src=img.replace(imgOn,imgOff);
	}	else {
		document.getElementById('blocksch'+ i).style.display='block';
		document.getElementById('imgsch'+i).src=img.replace(imgOff,imgOn);
	}
	}
}
}

// 	Proporciona los elementos seleccionados del checkbox
function checkboxElement(form) {
    var element = "" ;
    if (form) {
	    if (typeof form.length == 'undefined') { // cuando tenemos un checkbox
	      if ( (form.type == 'checkbox' && form.checked)
              || (form.type == 'hidden') ){
	        element = form.value;
	      }
	    } else {
	      for (var i=0; i < form.length; i++) {
	        if ((form[i].type == 'checkbox' && form[i].checked)
              		|| (form[i].type == 'hidden') ) {
	            if (element == "") {
	              element = form[i].value;
	            } else {
	              element = element + "-" + form[i].value;
	            }
	        }
	      }
	    }
    }
    return element;
}

/*	Coge los identificadores seleccionados en los checkbox
	y si el atributo "variable" es distinto del vacio
	se concatena con la url
*/
function takeElement(action, variable, message, aviso,accept, cancel,messageConfirm) {

	var formulario = document.forms[0].multibox;
	if (formulario == 'undefined') {
	   jAlert(message, aviso,accept,cancel);
	    return;
	}

	var simbolo = "&";
	var data = checkboxElement(formulario);
	if (data == "") {
		 jAlert(message, aviso,accept, cancel);
	}
	else {

		//El usuario debe confirmar la operación
		if(messageConfirm==null){
			messageConfirm=getUtilitiesString(utilitiesConfirmOperacionString);
		}

		jConfirm(messageConfirm, aviso,accept, cancel, function(r) {
			if(r){

				var url = replaceActionForm(document.forms[0].action, action);

				if (variable == "") {
					document.forms[0].action = url;
					document.forms[0].submit();
				}
				else {
					if (url.indexOf("?") == -1) {
						simbolo = "?";
					}
					document.forms[0].action = url + simbolo + variable + "=" + data ;
					document.location.href = document.forms[0].action;
				}

				// Se muestra la capa de operacion en progreso
				// y si exite, se oculta la capa del contenido movible
				elementContenido = document.getElementById('contenido');
				if ((elementContenido != null) && (elementContenido.className.indexOf('move') != -1)) {
					elementContenido.style.visibility = "hidden";
				}
				document.body.scrollTop = 0;
				showLayer("waitOperationInProgress");
			}
		});

	}
}

function takeElementAndShowFrame(action, variable, message, aviso,accept,cancel) {

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}
	var formulario = document.forms[0].multibox;
	if (formulario == undefined) {
	      jAlert(message, aviso,accept, cancel);
	    return;

	}

	var simbolo = "&";
	var data = checkboxElement(formulario);
	if (data == "") {
	   jAlert(message, aviso,accept, cancel);
	}
	else {

		var url = replaceActionForm(document.forms[0].action, action);

		if (variable == "") {
			showFrame( 'workframe', url );
		}
		else {
			if (url.indexOf("?") == -1) {
				simbolo = "?";
		    }
			showFrame( 'workframe', url + simbolo + variable + "=" + data );
		}
	}
}



function takeElementInForm(action, variable, nameForm, message, aviso,accept, cancel) {

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	var formulario = document.forms[nameForm].multibox;
	if (formulario == 'undefined') {
	   jAlert(message, aviso,accept, cancel);
	    return;
	}

	var simbolo = "&";
	var data = checkboxElement(formulario);
	if (data == "") {
	   jAlert(message, aviso,accept, cancel);
	}
	else {
		var url = replaceActionForm(document.forms[nameForm].action, action);

		if (variable == "") {
			document.forms[nameForm].action = url;
			document.forms[nameForm].submit();
		}
		else {
			if (url.indexOf("?") == -1)
				simbolo = "?";
			document.forms[nameForm].action = url + simbolo + variable + "=" + data ;
			document.location.href = document.forms[nameForm].action;
		}
	}
}

function takeElementInWorkFrame(action, variable, message, aviso,accept, cancel) {
	takeElementInFormWorkFrame(action, variable, null, message, aviso,accept, cancel);
}

function takeElementInFormWorkFrame(action, variable, nameForm, message, aviso,accept, cancel) {

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	var formulario = document.forms[0];
	if (nameForm != null)
		formulario = document.forms[nameForm];
	if (formulario.multibox == 'undefined'){
	   jAlert(message, aviso,accept, cancel);
	    return;
	}

	var simbolo = "&";
	var data = checkboxElement(formulario.multibox);
	if (data == "") {
	   jAlert(message, aviso,accept, cancel);
	}
	else {

		var url = replaceActionForm(formulario.action, action);

		if (variable == "") {
			showFrame( 'workframe', url );
		}
		else {
			if (url.indexOf("?") == -1)
				simbolo = "?";
			showFrame( 'workframe', url + simbolo + variable + "=" + data );
		}
	}
}

// Comprueba que se sube un fichero
function uploadValidate(msg, aviso,accept, cancel) {

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	if (document.uploadForm.theFile.value == '') {
		//alert(msg);
		jAlert(msg, aviso,accept, cancel);
		return false;
	}
	return true;
}

function executeFrame(id, action, msgConfirm) {

  	if ((msgConfirm != null) && (msgConfirm != ''))
  	  	if (confirm(msgConfirm) == false)
  	      	return;

  	var element;
  	eval('element = document.getElementById("' + id + '")');

  	if (element != null && document.forms.length == 1) {

    	eval('document.forms[0].target = "' + id + '"');

    	if (action.substring(0,4) == 'http')
        	document.forms[0].action = action;
	    else if (action.substring(0,1) != '/')
			document.forms[0].action = replaceActionForm(document.forms[0].action, action);
		else
			document.forms[0].action = action;

		document.forms[0].submit();
  	}
}

function getWidthWindow(){
	var width= 630;
	if (parseInt(navigator.appVersion)>3) {
		 if (navigator.appName=="Netscape") {
		  width = window.innerWidth;
		 }
		 if (navigator.appName.indexOf("Microsoft")!=-1) {
		  width = document.body.offsetWidth;

		 }
		}
	return width;
}

function getHeightWindow(){

 var height = 460;

		if (parseInt(navigator.appVersion)>3) {
		 if (navigator.appName=="Netscape") {
		  height = window.innerHeight;
		 }
		 if (navigator.appName.indexOf("Microsoft")!=-1) {
		  height = document.body.offsetHeight;
		 }
		}
	return height;

}


function positionMiddleScreen(target, height, width,element){

		if(element==null){
		eval('element = document.getElementById("' + target + '")');
		}
		if(height==null){
			height="450";
		}
		if(width==null){
			width="700";
		}
		if(element!=null){
		element.style.left=getWidthWindow()/2-(width/2);
		element.style.top=getHeightWindow()/2-(height/2);
		}


}

function showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) {

	if (needToConfirm != null) {

		if (typeof ispac_needToConfirm != 'undefined') {
			ispac_needToConfirm = needToConfirm;
		}
	}

  	if ((msgConfirm != null) && (msgConfirm != ''))
  	  	if (confirm(msgConfirm) == false)
  	      	return;

  	if ((form == null) && (document.forms.length > 0)) {
  		form = document.forms[0];
  	}

  	var element;
  	/*if (width == null)
      	width = 640;
  	if (height == null)
      	height = 480;*/

		width=getWidthWindow();
		height=getHeightWindow();

  	eval('element = document.getElementById("' + target + '")');


	if (element != null) {

	   	showLayer();

    	var x = (document.body.clientWidth - width) / 2;
    	//var y = (document.body.clientHeight - height) / 2;
    	var y = document.body.scrollTop + (document.body.clientHeight - height) / 2;
    	if (y < 10) {
    		y = 10;
    	}
		element.style.height = height;
		element.style.width = width;
		element.style.position = "absolute";
		element.style.left = x;
		element.style.top = y;
		element.style.visibility = "visible";

		if (document.forms.length > 0) {

			var oldTarget = form.target;
			var oldAction = form.action;

	    	eval('form.target = "' + target + '"');

	    	if (action.substring(0,4) == 'http')
	        	form.action = action;
		    else if (action.substring(0,1) != '/')
				form.action = replaceActionForm(form.action, action);
			else
				form.action = action;

			// Hacer un submit al workframe o abrir una url
			if ((doSubmit != null) && (!doSubmit)) {
				window.frames[target].location = form.action;;
			}
			else {
				form.submit();
			}

			form.target = oldTarget;
			form.action = oldAction;
		}
		else {

			eval("frames['" + target + "'].location.href='" + action + "'");

		}
  	}

}

function showParentFrame(target, width, height) {

  	var element = parent.document.getElementById(target);
	if (element) {

    	showLayer();

   	  /*	if (width == null) {
	      	width = 640;
   	  	}
  		if (height == null) {
      		height = 480;
  		}*/

  		height=getHeightWindow();
  		width=getWidthWindow();

    	var x = (parent.document.body.clientWidth - width) / 2;
    	var y = parent.document.body.scrollTop + (parent.document.body.clientHeight - height) / 2;
    	if (y < 10) {
    		y = 10;
    	}
		element.style.height = height;
		element.style.width = width;
		element.style.position = "absolute";
		element.style.left = x;
		element.style.top = y;
		element.style.visibility = "visible";
  	}
}



function hideFrame(target, src) {

  	var element;
  	eval('element = document.getElementById("' + target + '")');

  	if (element != null) {
    	element.style.visibility = "hidden";
    	if (src != null) {
	    	element.src = src;
		}
  	}

  	hideLayer();
}

function showLayer(id)
{
  var element;
  var elements;
  var i;

  if (id == null) {
	id = "layer";
  }

  element = document.getElementById(id);

  if (element != null)
  {
  	// Deshabilitar el scroll
	document.body.style.overflow = "hidden";

	element.style.position = "absolute";
	//element.style.height = document.body.clientHeight;
	element.style.height = document.body.scrollHeight + 1200;
	element.style.width = document.body.clientWidth + 1200;
	element.style.left = -600;
	element.style.top = -600;

	element.style.display = "block";

	if (isIE())
	{
	  elements = document.getElementsByTagName("SELECT");

	  for (i = 0; i < elements.length; i++)
	  {
		elements[i].style.visibility = "hidden";
	  }
	}
  }
}

function hideLayer(id)
{
  var element;
  var elements;
  var i;

  if (id == null) {
	id = "layer";
  }

  element = document.getElementById(id);

  if (element != null)
  {
   	// Habilitar el scroll
	document.body.style.overflow = "auto";

    element.style.display = "none";

	if (isIE())
	{
	  elements = document.getElementsByTagName("SELECT");
	  for (i = 0; i< elements.length; i++)
	  {
		elements[i].style.visibility = "visible";
	  }
	}
  }
}

function attachFile( url)
{
	showFrame( "workframe", url);
}

function attachTemplate( url)
{
	executeFrame( "workframe", url);
}

function generateDocuments( url, message, aviso,accept, cancel)
{

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	var data;
	var elements;

	elements = document.forms[0].multibox;

	if (elements != null)
	{
		data = checkboxElement(elements);

		if (data == "")
		{
			  jAlert(message, aviso,accept, cancel);
			return;
		}
	}

	showFrame( "workframe", url);
}

function generateStageDocument( url)
{
	showFrame( "workframe", url);
}

function isIE()
{
  var agent = navigator.userAgent.toLowerCase();

  return ((agent.indexOf("msie") != -1) && (agent.indexOf("opera") == -1));
}

function executeAction(action, aviso,accept, cancel) {

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

  var request;

  try
  {
    if (window.XMLHttpRequest) // Non-IE
    {
      request = new XMLHttpRequest();
      request.open("GET", action, false);
      request.send(null);
    }
    else if (window.ActiveXObject) // IE
    {
      request = new ActiveXObject("Microsoft.XMLHTTP");
      request.open("GET", action, false);
      request.send();
    }

    if (request.status == 200) // OK
	{
      return request.responseText;
    }
	else
	{
	  jAlert(request.statusText, aviso,accept, cancel);
     // alert(request.statusText);
    }
  }
  catch (e)
  {
  	jAlert(e, aviso,accept, cancel);
    //alert(e);
  }

  return null;
}

function executeHideFrame(action, id, sure) {

	if (sure != null)
  	  	if (confirm(sure) == false)
  	      	return;

  	// Mostramos una capa semiOpaca
  	showLayer();
  	var element;
  	eval('element = document.getElementById("' + id + '")');

  	if (element != null && document.forms.length == 1) {

    	eval('document.forms[0].target = "' + id + '"');
		document.forms[0].action = replaceActionForm(document.forms[0].action, action);
		document.forms[0].submit();
  	}
}

function _checkAll(field, obj){
	if (field) {
		if (!field.length) {
			if (!field.disabled) {
			   field.checked = obj.checked;
			}
		}
		else {
			for (i = 0; i < field.length; i++) {
				if (!field[i].disabled) {
					field[i].checked = obj.checked;
				}
			}
		}
	}
}

function sure(action, msg,aviso,accept, cancel){

    var _msg = msg;
	if (_msg == null)
	   _msg = getUtilitiesString(utilitiesConfirmOperacionString);

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesConfirmacionString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	jConfirm(_msg, aviso,accept, cancel, function(r) {
		if(r){

			// Se muestra la capa de operacion en progreso
			// y si exite, se oculta la capa del contenido movible
			elementContenido = document.getElementById('contenido');
			if ((elementContenido != null) && (elementContenido.className.indexOf('move') != -1)) {
				elementContenido.style.visibility = "hidden";
			}
			document.body.scrollTop = 0;
			showLayer("waitOperationInProgress");

			ispac_needToConfirm = false;
	    	document.location.href = action;
		}

	});

}

function selectOnChecked(_objSrc, _objDes){
    if (_objSrc.checked == true)
        _objDes.value = 'SI';
    else
        _objDes.value = 'NO';
}

function attachFileMsg( url, msg,numMaxCaracteres, noSelect, aviso,accept, cancel)
{
	/*if (confirm(msg) == true)
	    showFrame( "workframe", url);*/

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesConfirmacionString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	jConfirm(msg, aviso,accept, cancel, function(r) {
		if(r){
			 showFrame( "workframe", url);
		}

	});
}

//comprueba que en el campo 'field' no se hayan introducido mas de 'maxlen' caracteres
function validateLength(field, maxlen, numMaxCaracteres, aviso,accept, cancel){

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesConfirmacionString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

    if (field.value.length > maxlen + 1)
		jAlert(numMaxCaracteres, aviso,accept, cancel);
	if (field.value.length > maxlen)
		field.value = field.value.substring(0, maxlen);
}
/*
function takeElement(action, variable, msg, aviso,accept, cancel) {

	var formulario = document.forms[0].multibox;

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	if (formulario == 'undefined'){
	   jAlert(msg, aviso,accept,cancel);
	    return;
	}

	var simbolo = "&";
	var data = checkboxElement(formulario);

	if (data == "") {
		jAlert(msg, aviso,accept,cancel);
	}
	else {
		var url = replaceActionForm(document.forms[0].action, action);

		if (variable == "") {
			document.forms[0].action = url;
			document.forms[0].submit();
		}
		else {
			if (url.indexOf("?") == -1)
				simbolo = "?";
			document.forms[0].action = url + simbolo + variable + "=" + data ;
			sure(document.forms[0].action,msg,aviso,accept,cancel);

		}
	}
}
*/
function _confirm(action, msg, needToConfirm, aviso,accept,cancel){
    var _msg = msg;
	if (_msg == null) {
	   _msg = getUtilitiesString(utilitiesConfirmOperacionString);
	}
	/*if (confirm(_msg)) {
	    document.location.href = action;
	}*/

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesConfirmacionString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	jConfirm(msg,aviso, accept, cancel, function(r) {
		if(r){
		document.location.href = action;
		}});
	if (typeof needToConfirm != 'undefined') {
		ispac_needToConfirm = needToConfirm;
	}
}

//Parametros:
//e: evento
//f: funcion a evaluar si la tecla pulsada en el ENTER
function handleEnter(e, f){
	var characterCode;
	if(e && e.which){
		//if which property of event object is supported (NN4)
		e = e;
		//character code is contained in NN4's which property
		characterCode = e.which;
	}
	else{
		e = event;
		//character code is contained in IE's keyCode property
		characterCode = e.keyCode;
	}
	if(characterCode == 13){
		eval(f);
		return false;
	}
	else{
		return true;
	}
}

/*
function isIEWord() {
	var ieword = false;

	try {
		if (window.ActiveXObject) {
			var app = new ActiveXObject('Word.Application');
			app.Application.Quit();
			ieword = true;
		}
	} catch(e) {
		ieword = false;
	}

	return ieword;
}
*/
function isIEWord() {
	var ieword = false;

	try {
		if ( navigator.userAgent.indexOf("Windows") != -1){
			if (window.ActiveXObject) {
				var app = new ActiveXObject('WScript.Shell');
				app.RegRead('HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\Winword.exe\\');
				app.Quit;
				ieword = true;
			}
		}
	} catch(e) {
		ieword = false;
	}
	return ieword;
}

function replaceActionForm(actionForm, newAction) {

	var actionFormSplit = actionForm.split("/");
	var action = actionFormSplit[actionFormSplit.length - 1];

	if (newAction.indexOf("/") == 0) {
		action = "/" + action;
	}

	return actionForm.replace(action, newAction);
}

// Desactivar el ispac_needToConfirm para un enlace:
// 1 - deactivateNeedToConfirm() en el evento onclick
// 2 - restoreNeedToConfirm() en el href
// En 1 se desactiva el needToConfirm para que el href no lance el mensaje de aviso
function deactivateNeedToConfirm() {

	if (typeof ispac_needToConfirm != 'undefined') {
		// Variable global a la página (sin var) que se utiliza en restoreNeedToConfirm
		ispac_needToConfirm_set = ispac_needToConfirm; ispac_needToConfirm = false;
	}
}

function restoreNeedToConfirm() {

	if ((typeof ispac_needToConfirm != 'undefined') &&
		(typeof ispac_needToConfirm_set != 'undefined')) {
		ispac_needToConfirm = ispac_needToConfirm_set;
	}
}

function help() {

	restoreNeedToConfirm();
}
function leftPad(str, len) {
    str = str.toString();
    var pd = '';
    if (len > str.length) {
        for (var i = 0; i < (len-str.length); i++) {
            pd += '0';
        }
    }
    return pd + str;
}

//---------------------------------------------------------------------------------------------------------------------
//Funciones para insertar controles html de forma dinamica para los campos Multivalue

//Elimina los campos multivalor seleccionados
function deleteElements(name,alert, aviso,accept,cancel){
	obj = document.forms[0];
	for (i=obj.length-1;i>0;i--){
	  ele=document.forms[0].elements[i];
	  if (ele != undefined && ele.type=='checkbox' && ele.name=='checkbox_'+name){
		if (ele.checked){
		  deleteElement('div_'+name+'_'+ele.value,alert, aviso,accept,cancel);
		}
	  }
	}
}

//Elimina un elemento html segun su id
function deleteElement(id, alert, aviso,accept,cancel){
	obj = document.getElementById(id);

	if(aviso==null){
		aviso=getUtilitiesString(utilitiesAvisoString);
	}
	if(accept==null){
		accept=getUtilitiesString(utilitiesAceptarString);
	}
	if(cancel==null){
		cancel=getUtilitiesString(utilitiesCancelarString);
	}

	if (!obj){
		//alert(alert);
		jAlert(alert, aviso,accept,cancel);
	} else {
		padre = obj.parentNode;
		padre.removeChild(obj);
	}
}


function insertCommonMultivalueElement(name, propertyName, size, maxlength, inputClass, blockHTML){
   var newdiv = document.createElement("div");
   id = eval('max'+name);
   newdiv.setAttribute('id','div_'+name+'_'+id);
   styleId = propertyName.replace(')', '_'+id+')');
   newdiv.innerHTML = '<input type="checkbox" value="' + id + '" name="checkbox_'+name+'"/><input id="'+styleId+'" type="text" class="'+inputClass+'" size="'+size+'" maxlength="'+maxlength+'" name="' + propertyName + '"/>'
   +blockHTML;
   obj = document.getElementById("div_"+name);
   obj.appendChild(newdiv);
   eval('max'+name +'= id + 1' );
}
function insertMultivalueElement(name, propertyName, size, maxlength, inputClass){
	insertCommonMultivalueElement(name, propertyName, size, maxlength, inputClass, '');
}

function insertMultivalueCalendarElement(name, propertyName, size, maxlength, inputClass, pathImgCalendar){
    id = eval('max'+name);
	styleId = propertyName.replace(')', '_'+id+')');
	blockHTML = '<span id=imgCalendar_'+name+'_'+id+' style="margin: 0px;"><img onclick=\'showCalendar(this, document.getElementById("'+styleId+'"), "dd/mm/yyyy","es",1,-1,-1)\'  src="' +pathImgCalendar+ '" valign="middle" id="calgif"/></span>';
	insertCommonMultivalueElement(name, propertyName, size, maxlength, inputClass,blockHTML);
}




function insertMultivalueImageFrameSubstituteElement(substitute, name, propertyName, propertySubstituteName, urlSubstitute, pathSearchImg, size, maxlength, inputClass, showDelete, pathDeleteImg, messageSelect, messageDeleteSelection){
    id = eval('max'+name);
	field = propertyName.replace('propertyMultivalue(', '');
	field = field.replace(':', '_');
	field = field.replace(')', '');

	if (substitute == 'true'){
		blockHTML = '<input type=\"hidden\" name=\"' + propertyName + '\" id=\"' + propertyName.replace(')', '_'+id + ')') +'\" value=""/>'
	}else{
		blockHTML = '';
	}

	blockHTML +=
	'<span id=\"imgSearch_SEARCH_'+
	field+'_'+id+
	'\" style=\"margin: 0px;\"><img border=\"0\" title=\"' + messageSelect + '\" onclick=\"javascript: ispac_needToConfirm = false; showFrame(\'workframe\', \''+
	urlSubstitute+
	'&amp;parameters=SEARCH_'+
	field+
	'&amp;multivalueId='+id+
	'\',\'\'); ispac_needToConfirm = true;\" style=\"cursor: pointer;\" src=\"'+
	pathSearchImg+
	'\"/></span>';


	if (showDelete == 'true'){
		blockHTML +=
		'<span id=\"imgDelete_SEARCH_'+
		field+'_'+id+
		'\" style=\"margin: 0px;\"> <a onclick=\"javascript: ispac_needToConfirm = false;\" class=\"tdlink\" href=\"javascript: delete_SEARCH_'+
		field+'('+id+')'+
		';\"><img border=\"0\" title=\"'+
		messageDeleteSelection+
		'\" src=\"'+
		pathDeleteImg+
		'\"/></a></span>';
	}


	insertCommonMultivalueElement(name, propertySubstituteName, size, maxlength, inputClass,blockHTML);
}

function insertMultivalueTextareaElement(name, propertyName, size, maxlength, inputClass){
   var newdiv = document.createElement("div");
   id = eval('max'+name);
   newdiv.setAttribute('id','div_'+name+'_'+id);
   styleId = propertyName.replace(')', '_'+id+')');
   newdiv.innerHTML = '<input type="checkbox" value="' + id + '" name="checkbox_'+name+'"/>'
   +'<textarea id="'+styleId+'" class="'+inputClass+'" rows="2" cols="80" name="' + propertyName + '"/>';
   obj = document.getElementById("div_"+name);
   obj.appendChild(newdiv);
   eval('max'+name +'= id + 1' );
}

function formatDate(date) {
	var mymonth = date.getMonth()+1;

	if (mymonth < 10)
		mymonth = '0'+ mymonth;

	var myweekday = date.getDate();
	if (myweekday < 10)
		myweekday = '0'+ myweekday;

	var myyear = date.getUTCFullYear();
	return (myweekday + "/" + mymonth + "/" + myyear);
}

function daysInMonth(intMonth, intYear){

	var dteMonth = new Date(intYear,intMonth);//
	var intDaysInMonth = 28;//the fewest number of days in a month
	var blnDateFound = false;//Set a variable to check on the while loop

	while (!blnDateFound){
		dteMonth.setDate(intDaysInMonth+1);//create the next possible day
		var intNewMonth = dteMonth.getMonth();//new month date

		if (intNewMonth != intMonth)//if the month has changed
		  blnDateFound = true;
		else
		  intDaysInMonth++;
	}
	return intDaysInMonth;
}

function printWeek(obj) {
	var now = new Date();
	//var nowDayOfWeek = now.getDay() - 1;
	var nowDayOfWeek = now.getDay();
	var nowDay = now.getDate();
	var nowMonth = now.getMonth();
	var nowYear = now.getUTCFullYear();
	nowYear += (nowYear < 2000) ? 1900 : 0;


	var dayStartOfWeek = nowDay;
	if (nowDayOfWeek == 0){
		dayStartOfWeek -= 6;
	}else{
		dayStartOfWeek -=  (nowDayOfWeek - 1);
	}

	var dayEndOfWeek = nowDay;
	if (nowDayOfWeek != 0){
		dayEndOfWeek += (7- nowDayOfWeek)
	}

	var weekStartDate = new Date(nowYear, nowMonth, dayStartOfWeek);

	var weekEndDate = new Date(nowYear, nowMonth, dayEndOfWeek);
	document.getElementById(obj).value = formatDate(weekStartDate) + ";" + formatDate(weekEndDate);
}

function printYear(obj){
	var now = new Date();
	var nowYear = now.getUTCFullYear();
	document.getElementById(obj).value = "01/01/"+nowYear+";"+"31/12/"+nowYear;
}

function printMonth(obj){
	var now = new Date();
	var nowMonth = now.getMonth();
	var nowYear = now.getUTCFullYear();
	var daysMonth = daysInMonth(nowMonth, nowYear);
	nowMonth += 1;
	if ( (nowMonth) < 10)
		nowMonth = '0' + nowMonth;
	document.getElementById(obj).value = "01/"+nowMonth+"/"+nowYear+";"+ daysMonth +"/"+nowMonth+"/"+nowYear;
}

function setObjDirectory(fieldUID, fieldName, objDirectoryUID, objDirectoryName){
	document.getElementById(fieldUID).value = objDirectoryUID;
	document.getElementById(fieldName).value = objDirectoryName;
}




//---------------------------------------------------------------------------------------------------------------------
