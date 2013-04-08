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

function ispac_submit(url)
{
	if (typeof document.forms[0] != "undefined") {
		ispac_needToConfirm=false;
		document.forms[0].action = url;
	  	document.forms[0].submit();
		ispac_needToConfirm=true;
	}
	else {
		document.location = document.location;
	}
}


var ispac_forms_values = new Array();



function ispac_load_formvalues()
{
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
        case 'radio':
        	//alert(ispac_forms_values[idx] + ' = ' + element.checked);
			return (ispac_forms_values[idx]!=element.checked);
          break;
        case 'password':
        case 'text':
        case 'textarea':
        case 'select-one':
        case 'select-multiple':
          //alert(ispac_forms_values[idx] + ' = ' + element.value);
		  return (ispac_forms_values[idx]!=element.value);
          break;
      }
    }
}