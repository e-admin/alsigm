function removeCampo(form,msgConfirm,msgNoCampos){
		
	
	if (form.camposABorrar && elementSelected(form.camposABorrar)) {
		if (confirm(msgConfirm))
		{
			form.action += "?method=delete";
			form.submit();
		}
	} else
		alert(msgNoCampos);
}


function changeTipoNorma(form){

	form.action += "?method=changeTipoNorma";
	form.submit();
}

function changeTipo(form){
	form.action += "?method=changeTipo";
	form.submit();
	
}

function call(form, method)
{
	form.action += "?method="+method;
	form.submit();
}

function create(form,msgNombre,msgEtiquetaXml){

	form.nombre.value=trim(form.nombre.value);
	if(form.nombre.value=="")
		{
			alert(msgNombre);
		}
	else
		{
			form.etiquetaXml.value=trim(form.etiquetaXml.value);
			if(form.etiquetaXml.value=="")
				alert(msgEtiquetaXml);
			
			else
				{
					if(form.id.value==null || form.id.value=="")
							form.action += "?method=create";
					else
							form.action += "?method=update";
					
					form.submit();
				}
		}
}

function create2(form,msgNombre,msgEtiquetaXml,etiqXmlFila){

	form.nombre.value=trim(form.nombre.value);
	if(form.nombre.value=="")
		{
			alert(msgNombre);
		}
	else
		{
			form.etiquetaXml.value=trim(form.etiquetaXml.value);
			if(form.etiquetaXml.value=="")
				alert(msgEtiquetaXml);
			else
				{
					form.etiqXmlFila.value=trim(form.etiqXmlFila.value);
					if(form.etiqXmlFila.value=="")
						alert(etiqXmlFila);
					else
						{	
					
							if(form.id.value==null || form.id.value=="")
									form.action += "?method=create";
							else
									form.action += "?method=update";
							
							form.submit();
						}
				}
		}
}