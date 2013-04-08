function validarFecha(input){
	var validformat=/^\d{2}\/\d{2}\/\d{4}$/ //Basic check for format validity
	var returnval=false
	if (!validformat.test(input.value))
		alert("Formato de fecha incorrecto. El formato correcto es 12/12/2000.")
	else{ //Detailed check for valid date ranges
		var monthfield=input.value.split("/")[1]
		var dayfield=input.value.split("/")[0]
		var yearfield=input.value.split("/")[2]
		var dayobj = new Date(yearfield, monthfield-1, dayfield)
		if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
			alert("Fecha incorrecta.")
		else
			returnval=true
	}
	if (returnval==false) input.select()
		return returnval
		
}

function validarImporte(input){
	var validformat = /^\d*(\,\d{1,2})?$/

	if (!validformat.test(input.value)){
		alert("Formato de importe incorrecto. El formato correcto es 1500,90")
		return false;
	}
	return true;
}

function validarNumerico(input, nombrecampo){
	var validformat = /^\d+$/
	if (!validformat.test(input.value)){
		alert("Formato incorrecto. " + nombrecampo + " debe ser un valor númerico")
		return false;
	}
	return true;
}

function validarLongitud(input, longitud){
	if(input.value != ""){
		if(input.value.length <= longitud){
			return true;
		}
	}
	return false;
}