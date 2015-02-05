var ispac_needToConfirm = true;
var ispac_confirmMessage = 'Hay modificaciones en el formulario actual sin guardar.';

/*
function ispac_formobserver_install(windowobj)
{
 	windowobj.onbeforeunload = confirmExit;
}
*/

function ispac_formobserver_install(windowobj,confirmmessage)
{
 	windowobj.onbeforeunload = confirmExit;
 	if (confirmmessage != null)
	 	ispac_confirmMessage=confirmmessage;
}

function confirmExit()
{
    if (ispac_needToConfirm)
    {
      if (ispac_test_form())
	      return ispac_confirmMessage;
    }
}

function ispac_confirmexitobserver_install(windowobj,confirmmessage)
{
 	windowobj.onbeforeunload = confirmExitMessage;
 	if (confirmmessage != null)
	 	ispac_confirmMessage=confirmmessage;
}

function confirmExitMessage()
{
    if (ispac_needToConfirm)
    {
      return ispac_confirmMessage;
    }
}

var ispac_forms_values = new Array();

function ispac_load_formvalues()
{
    // Assign the default values to the items in the values array
    for (var i = 0; i < document.forms.length;i++)
    {
      for (var j=0; j<document.forms[i].elements.length;j++)
      {
		ispac_check_formelement((i+1)*j,document.forms[i].elements[j]);
      }
    }
}

function ispac_test_form()
{
    // Assign the default values to the items in the values array
    for (var i = 0; i < document.forms.length;i++)
    {
      for (var j=0; j<document.forms[i].elements.length;j++)
      {
		if (ispac_test_formelement((i+1)*j,document.forms[i].elements[j]))
		return true;
      }
    }
    return false;
}

function ispac_check_formelement(idx,element)
{
   if (element && element.type) {
      switch (element.type.toLowerCase()) {
        case 'checkbox':
          if ((element.name == 'allbox') || (element.name == 'multibox')) {
          	break;
          }
        case 'radio':
		  ispac_forms_values[idx]=element.checked;
          break;
        case 'password':
        case 'text':
        case 'textarea':
        case 'select-one':
        case 'select-multiple':
		  ispac_forms_values[idx]=element.value;
          break;
      }
    }
}

function ispac_test_formelement(idx,element)
{
   if (element && element.type) {
      switch (element.type.toLowerCase()) {
        case 'checkbox':
          if ((element.name == 'allbox') || (element.name == 'multibox')) {
          	break;
          }
        case 'radio':
        	//alert(ispac_forms_values[idx] + ' = ' + element.checked);
			return !(ispac_forms_values[idx]==element.checked);
          break;
        case 'password':
        case 'text':
        case 'textarea':
        case 'select-one':
        case 'select-multiple':
          //alert(ispac_forms_values[idx] + ' = ' + element.value);
		  return !(ispac_forms_values[idx]==element.value);
          break;
      }
    }
}
	
										

function confirmAction(action, msg,aviso, accept, cancel) {
	if(aviso==null){
	aviso="Pregunta";
	}
	if(accept==null){
	accept="Aceptar";
	}
	if(cancel==null){
	cancel="Cancelar";
	}
	jConfirm(msg, aviso,accept, cancel, function(resultado) {
		if(resultado){
			var element= document.getElementById("workframe");
			if (element != null) {
		    	element.style.visibility = "hidden";
		    	element.src = action;
			}
		}
		else {
		return false;
		}
							
	});

}
														


function setReadOnly(control) {

	control.className = "inputReadOnly";
	control.readOnly = "true";
}

function setNotReadOnly(control) {

	control.className = "input";
	control.readOnly = '';
}


function setReadOnlyIdentidad(control) {

	control.className = "textareaIdentidadRO";
	control.readOnly = "true";
}

function setNotReadOnlyIdentidad(control) {

	control.className = "textareaIdentidad";
	control.readOnly = '';
}

function setReadOnlyDir(control) {

	control.className = "textareaDirRO";
	control.readOnly = "true";
}

function setNotReadOnlyDir(control) {

	control.className = "textareaDir";
	control.readOnly = '';
}


function checkRadioById(id) {

    control = document.getElementById(id);
    if (control != null) {
		control.checked = true;
	}
}

function uncheckRadioById(id) {

    control = document.getElementById(id);
    if (control != null) {
		control.checked = false;
	}
}

function disabledRadioById(id) {

    control = document.getElementById(id);
    if (control != null) {
		control.disabled = true;
	}
}
