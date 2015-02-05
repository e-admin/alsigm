function removeArea(form,msgConfirm,msgNoFichas){
		
	
	if (form.areasABorrar && elementSelected(form.areasABorrar)) {
		if (confirm(msgConfirm))
		{
			form.action += "?method=delete";
			form.submit();
		}
	} else
		alert(msgNoFichas);
}

function changeTipoNorma(form){

	form.action += "?method=changeTipoNorma";
	form.submit();
}
	
function create(form,msgNombre){

	form.nombre.value=trim(form.nombre.value);
	if(form.nombre.value=="")
		{
			alert(msgNombre);
		}
	else
		{
			if(form.id.value==null || form.id.value=="")
					form.action += "?method=create";
			else
					form.action += "?method=update";
			
			form.submit();
		}
}
	
function call(form, method)
{
	form.action += "?method="+method;
	form.submit();
}