
function removeFicha(form,msgConfirm,msgNoFichas){


	if (form.fichasABorrar && elementSelected(form.fichasABorrar)) {
		if (confirm(msgConfirm))
		{
			form.action += "?method=delete";
			form.submit();
		}
	} else
		alert(msgNoFichas);
}

function removeFormato(form,msgConfirm,msgNoFormatos){


	if (form.formatosABorrar && elementSelected(form.formatosABorrar)) {
		if (confirm(msgConfirm))
		{
			form.action += "?method=delete";
			form.submit();
		}
	} else
		alert(msgNoFormatos);
}

function changeTipoNorma(form){

	form.action += "?method=changeTipoNorma";
	form.submit();
}

function create(form,msgNombre,msgDefinicion){
	if(form.id.value==null || form.id.value=="")
		form.action += "?method=create";
	else
		form.action += "?method=update";

	form.submit();
}

function call(form, method)
{

	form.action += "?method="+method;
	form.submit();
}


function acceptImport(form,method,msg)
{

	if(form.fichero.value==null || trim(form.fichero.value)=="")
		{
			alert(msg);
			return;
		}

	else
		{
		call(form,method);
	}
}
function acceptExport(form,method,msg)
{

	if(form.pathFileName.value==null || trim(form.pathFileName.value)=="")
		{
			alert(msg);
			return;
		}

	else call(form,method);
}