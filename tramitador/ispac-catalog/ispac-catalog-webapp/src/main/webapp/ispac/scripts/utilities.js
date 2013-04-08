

function confirm_remake(method , msg, action ,width, height, aviso,accept, cancel){
	
	if(aviso==null){
		aviso="Pregunta";
	}
	if(accept==null){
		accept="Aceptar";
	}
	if(cancel==null){
		cancel="Cancelar";
	}
	jConfirm(msg, aviso,accept, cancel,function(resultado) {
		if(resultado){
		showFrame('workframemsg',action+'?method='+method,width,height);
		}						
	});

	
}

function isIExplorer() {
  var agent = navigator.userAgent.toLowerCase();
  return
  (
  	(agent.indexOf("msie") != -1) &&
  	(agent.indexOf("opera") == -1)
  );
}

function confirm_jquery(msg, aviso,accept, cancel){
	if(aviso==null)
	{
	aviso="Pregunta";
	}
	if(accept==null){
	accept="Aceptar";
	}
	if(cancelar==null){
	cancelar="Cancelar";
	}
	
	jConfirm(msg, aviso,aceptar, cancelar,function(resultado) {
		if(resultado){
		return true;
		}
		else{
		return false;}
		
							
	});
	
	
	}

//	Calcula el contexto de la aplicación
/*
function getContext() {
  return "/" + ((location.pathname).split("/")[1]);
}
*/

//	Abre una ventana
function openWin(url) {
  open(url,"popup","status=yes,scrollbars=yes,location=yes,toolbar=yes,screenX=200,screenY=100,width=500,height=400");
}

// 	Proporciona los elementos seleccionado del checkbox
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

// Comprueba que se sube un fichero
function validate(msg, aviso, accept, cancel) {

	if(msg==null){
		msg="Debe elegir un fichero a subir";
	}
	if (document.uploadForm.theFile.value == "") {
		jAlert(msg, aviso, accept, cancel);
		return false;
	}
	return true;
}

function executeFrame(id, action, msgConfirm, aviso,accept, cancel) {

	if(aviso==null){
		aviso="Pregunta";
		
	}
	if(accept==null){
		accept="Aceptar";
	}
	if(cancel==null){
		cancel="Cancelar";
	}
  	if ((msgConfirm != null) && (msgConfirm != '')){
  		jConfirm(msgConfirm, aviso,accept, cancel, function(resultado) {
			if(resultado==false){
				return
			}
			else{
		
				_executeFrame(id,action);
			}					
	});
  	}
	else{
	_executeFrame(id,action);
	}
}



function _executeFrame( id, action) {

  	var element;
  	eval('element = document.getElementById("' + id + '")');

  	if (element != null && document.forms.length == 1) {
  
    	var targetorig = document.forms[0].target;
    	eval('document.forms[0].target = "' + id + '"');
    
       	if (action.substring(0,4) == 'http')
        	document.forms[0].action = action;
	    else if (action.substring(0,1) != '/')
			document.forms[0].action = replaceActionForm(document.forms[0].action, action);
		else
			document.forms[0].action = action;

		document.forms[0].submit();
    	eval('document.forms[0].target = "' + targetorig + '"');
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

function showFrame(target, action, width, height) {

	var element;
	width=getWidthWindow();
	height=getHeightWindow();
  	eval('element = document.getElementById("dialog' + target + '")');

  	if (element != null ) {
  	
    	showLayer();

    	var x = (document.body.clientWidth - width) / 2;
    	//var y = (document.body.clientHeight - height) / 2;
    	var y = document.body.scrollTop + (document.body.clientHeight - height) / 2;
    	if (y < 10) {
    		y = 10;
    	}
   		element.style.position = "absolute";
		element.style.height = height;
		element.style.width = width;
		element.style.left = x;
		element.style.top = y;
		//Cambiado para que el elemento no ocupe espacio
		element.style.display = "block";

	if( document.forms.length > 0) {
	
		var targetorig = document.forms[0].target;
	    eval('document.forms[0].target = "' + target + '"');
	    
    	if (action.substring(0,4) == 'http')
        	document.forms[0].action = action;
	    else if (action.substring(0,1) != '/')
			document.forms[0].action = replaceActionForm(document.forms[0].action, action);
		else
			document.forms[0].action = action;
			
		document.forms[0].submit();
	    eval('document.forms[0].target = "' + targetorig + '"');
	}
	else {
		//eval("frames['" + target + "'].location.href='" + getContext() + "/" + action + "'");
		eval("frames['" + target + "'].location.href='" + action + "'");
	}
  }
}
function showDesigner(target, action, difwidth, difheight) {

			
   var width = 0, height = 0;
  

  
  width=window.document.body.clientWidth-difwidth;
  height=window.document.body.clientHeight;
  
	var element;
  	eval('element = document.getElementById("dialog' + target + '")');

  	if (element != null ) {
  	
    	showLayer();

    	var x = (document.body.clientWidth - width) / 2;
    	//var y = (document.body.clientHeight - height) / 2;
    	var y = document.body.scrollTop + (document.body.clientHeight - height) / 2;
    	if (y < 10) {
    		y = 10;
    	}
   		element.style.position = "absolute";
		element.style.height = height;
		element.style.width = width;
		element.style.left = x;
		element.style.top = y;
		//Cambiado para que el elemento no ocupe espacio
		element.style.display = "block";

	if( document.forms.length > 0) {
	
		var targetorig = document.forms[0].target;
	    eval('document.forms[0].target = "' + target + '"');
	    
    	if (action.substring(0,4) == 'http')
        	document.forms[0].action = action;
	    else if (action.substring(0,1) != '/')
			document.forms[0].action = replaceActionForm(document.forms[0].action, action);
		else
			document.forms[0].action = action;
			
		document.forms[0].submit();
	    eval('document.forms[0].target = "' + targetorig + '"');
	}
	else {
		//eval("frames['" + target + "'].location.href='" + getContext() + "/" + action + "'");
		eval("frames['" + target + "'].location.href='" + action + "'");
	}
	
  }
}


function resizeFrame(target)
{
  var dialog,frame;
  eval('dialog = document.getElementById("dialog' + target + '")');
  eval('frame = document.getElementById("' + target + '")');

  if (dialog == null || frame == null )
		return;

  var dlg_height=dialog.style.height;
  var frmcnt_height=frame.contentWindow.document.body.scrollHeight;
  var frm_height=frame.style.height;

  var caption_height=dlg_height-frm_height;

	jAlert('dlg_height: '+dlg_height+'\nfrmcnt_height: '+frmcnt_height+
		'\nfrm_height: '+frm_height+'\ncaption_height: '+caption_height);

   dialog.style.height=frmcnt_height;
  if (frmcnt_height < frm_height)
  {
		frame.style.height=caption_height+frmcnt_height;
  }

}

function _showFrame(target, action, width, height) {

  	var element;
  	eval('element = document.getElementById("' + target + '")');

  	if (element != null && document.forms.length == 1) {
  	
    	showLayer();

    	var x = (document.body.clientWidth - width) / 2;
    	//var y = (document.body.clientHeight - height) / 2;
    	var y = document.body.scrollTop + (document.body.clientHeight - height) / 2;
    	if (y < 10) {
    		y = 10;
    	}
   		element.style.position = "absolute";
		element.style.height = height;
		element.style.width = width;
		element.style.left = x;
		element.style.top = y;
		//Cambiado para que el elemento no ocupe espacio
		element.style.display = "block";

    	eval('document.forms[0].target = "' + target + '"');

    	if (action.substring(0,4) == 'http')
        	document.forms[0].action = action;
	    else if (action.substring(0,1) != '/')
			document.forms[0].action = replaceActionForm(document.forms[0].action, action);
		else
			document.forms[0].action = action;
			
		document.forms[0].submit();
  	}
}

function ispac_refresh()
{
	top.window.location.href = top.window.location.href;
}

function ispac_hideFrame(target, refresh, src)
{
	hideFrame(target, src);
	if (refresh)
		ispac_refresh();
}

function hideFrame(target, src)
{
  var window;
  var element;

  eval('window = document.getElementById("dialog' + target + '")');
  if (window != null)
  {
    window.style.display = "none";
    eval('element = document.getElementById("' + target + '")');
    if ((element != null) &&
        (src != null)) {
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

	if (isIExplorer())
	{
	  elements = document.getElementsByTagName("SELECT");

	  for (i = 0; i < elements.length; i++)
	  {
	    elements[i].style.display = "none";
	  }
	}
  }

}

function hideLayer()
{
  var element;
  var elements;
  var i;

  element = document.getElementById("layer");

  if (element != null)
  {
    // Habilitar el scroll
	document.body.style.overflow = "auto";
	
	element.style.display = "none";

	if (isIExplorer())
	{
	  elements = document.getElementsByTagName("SELECT");

	  for (i = 0; i< elements; i++)
	  {
		elements[i].style.display = "visible";
	  }
	}
  }
}

function isIE()
{
  var agent = navigator.userAgent.toLowerCase();
  
  return ((agent.indexOf("msie") != -1) && (agent.indexOf("opera") == -1));
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
	showFrame( "workframe", url, 320, 240);
}

function generateDocuments( url, msg,aviso, accept,cancel)
{
	var data;
	var elements;
	if(msg==null){
		msg="Debe seleccionar algún elemento del listado";
	}
	if(aviso==null){
		aviso="Aviso";
	}
	if(accept==null){
		accept="Aceptar";
	}

	if(cancel==null){
		cancel="Cancelar";
	}
	elements = document.forms[0].multibox;

	if (elements != null)
	{
		data = checkboxElement(elements);

		if (data == "")
		{
			jAlert(msg,aviso, accept,cancel);
			return;
		}
	}

	showFrame( "workframe", url, 640, 480);
}

function checkAll(field, obj){
	if (field != undefined) {
		if (field.length == undefined)
		   field.checked = obj.checked;
		for (i = 0; i < field.length; i++)
			field[i].checked = obj.checked;
	}
}

function isCheckSelected(check) {
	var selected = false;
	if (check) {
		if (check.length == undefined) {
			selected = check.checked;
		} else {
			for (i = 0; !selected && (i < check.length); i++) {
				selected = check[i].checked;
			}
		}
	}
	return selected;
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

function messageConfirm(url, msg, aviso,accept,cancel, target) {
	
	if(aviso==null){
		aviso="Pregunta";
	}
	
	jConfirm(msg, aviso,accept, cancel,function(resultado) {
		if(resultado){
			if (target==null) {
				window.location.href=url;
			} else {
				target.location.href=url;
			}
		}
	});
}