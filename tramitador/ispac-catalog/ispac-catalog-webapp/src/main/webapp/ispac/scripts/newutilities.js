//Envia el formulario a la url parametrizada
function submit(url)
{
	document.forms[0].action = url;
  	document.forms[0].submit();
}

function submit_confirm(url, msg, aviso, aceptar, cancelar)
{
	if(aviso==null){
		aviso="Pregunta";
	}
	jConfirm(msg, aviso,aceptar, cancelar, function(resultado) {
		if(resultado){
		document.forms[0].action = url;
	  	document.forms[0].submit();
		}
		
							
	});

}

function query(url, msg, aviso, aceptar, cancelar)
{
	if(msg==null){
	msg="Se va a realizar la eliminación. ¿Desea continuar?";
	}
	if(aviso==null){
	aviso="Pregunta";
	}
	
	jConfirm(msg, aviso,aceptar, cancelar, function(resultado) {
		if(resultado){
		document.forms[0].action = url;
	  	document.forms[0].submit();
		}
		
							
	});

}

//Limita el numero de caracteres, util en textarea
function maxlength(id, max)
{
	if (document.getElementById(id).value.length > (max - 1))
	{
		document.getElementById(id).value = document.getElementById(id).value.substring(0, max - 1);
	}
}