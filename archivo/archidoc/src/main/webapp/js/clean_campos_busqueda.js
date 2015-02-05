function cleanForm(form) {
	cleanCodigoReferencia(form);

	cleanTitulo(form);

	cleanSignatura(form);

	cleanRango(form);

	cleanNumeroExpediente(form);

	cleanTexto(form);

	cleanFechaInicial(form);

	cleanFechaFinal(form);

	cleanProductor(form);

	cleanAllProductors(form);

	cleanFondo(form);

	cleanNivelesDescripcion(form);

	cleanAllDescriptors(form);

	cleanFecha(form);

	cleanAmbito(form);

	cleanDatoNumerico(form);

	cleanEstado(form);

	cleanCodigoRelacion(form);

	cleanCodigo(form);

	cleanCondicionesAvanzadas(form);

	cleanProcedimiento(form);

	cleanContenidoFichero(form);

	cleanCamposConfigurables();

	cleanBloqueo(form);
}

function cleanFormField(form,field){
	var fieldToReset = form.elements[field];
	if ((fieldToReset != null) && (fieldToReset != '') && (fieldToReset != 'undefined')){
		if(fieldToReset.type=="select-one"){
			//if(!document.all)
				fieldToReset.value=fieldToReset.options[0].value;
			//else fieldToReset.selectIndex=0;
		}
		else if(fieldToReset.type=="checkbox"){
			fieldToReset.checked = false;
		}
		else fieldToReset.value = "";
	}
}

function cleanFormFieldIdArchivo(form){
	var fieldToReset = form.elements['idArchivo'];
	if ((fieldToReset != null) && (fieldToReset != '') && (fieldToReset != 'undefined')){
		if(fieldToReset.type=="select-one"){
			fieldToReset.value=fieldToReset.options[0].value;
		}
	}
}


function cleanFormCheckbox(form,field,checked){
	var fieldToReset = form.elements[field];
	if ((fieldToReset != null) && (fieldToReset != '') && (fieldToReset != 'undefined')){
		if(fieldToReset.type=="checkbox"){
			fieldToReset.checked = checked;
		}
	}
}

function cleanCodigoReferencia(form)
{
	var codigoReferencia = document.getElementById('codigoReferencia');
	if ((codigoReferencia != null) && (codigoReferencia != '') && (codigoReferencia != 'undefined'))
		codigoReferencia.value = "";
}

function cleanSignatura(form)
{
	var signatura = document.getElementById('signatura');
	if ((signatura != null) && (signatura != '') && (signatura != 'undefined'))
		signatura.value = "";

	var signatura_like = document.getElementById('signatura_like');
	if ((signatura_like != null) && (signatura_like != '') && (signatura_like != 'undefined')){
		signatura_like.value = "igual";
	}
}

function cleanRango(form)
{
	var rango = document.getElementById('rango');

	if ((rango != null) && (rango != '') && (rango != 'undefined'))
		rango.value = "";
}

function cleanCodigoRelacion(form)
{
	var codigoRelacion = document.getElementById('codigoRelacion');
	if ((codigoRelacion != null) && (codigoRelacion != '') && (codigoRelacion != 'undefined'))
		codigoRelacion.value = "";
}

function cleanCodigo(form)
{
	var codigo = document.getElementById('codigo');
	if ((codigo != null) && (codigo != '') && (codigo != 'undefined'))
		codigo.value = "";
}

function cleanCodigoOrganizacion(form)
{
	var codigo = document.getElementById('codigo');
	if ((codigo != null) && (codigo != '') && (codigo != 'undefined'))
		codigo.value = "";
}

function cleanNombreOrganizacion(form)
{
	var nombre = document.getElementById('nombre');
	if ((nombre != null) && (nombre != '') && (nombre != 'undefined'))
		nombre.value = "";
}

function cleanTipoOrganizacion(form)
{
	var tipo = document.getElementById('tipo');
	if ((tipo != null) && (tipo != '') && (tipo != 'undefined'))
		tipo.value = "";
}

function cleanEstadoOrganizacion(form)
{
	var estado = document.getElementById('estado');
	if ((estado != null) && (estado != '') && (estado != 'undefined'))
		estado.value = "";
}

function cleanUsuarioOrganizacion(form)
{
	var nombreUsuario = document.getElementById('nombreUsuario');
	if ((nombreUsuario != null) && (nombreUsuario != '') && (nombreUsuario != 'undefined'))
		nombreUsuario.value = "";
}

function cleanAmbito(form)
{
	var td = document.getElementById("tdAmbitos");
 	if ((td!=null) && (td != '') && (td != 'undefined')) {
		var divs = td.getElementsByTagName("div");
		if (divs)
		{
			for (var countDivs = divs.length-1; countDivs >= 0; countDivs--)
			{
				if (countDivs == 0)
				{
					var inputs = divs[countDivs].getElementsByTagName("input");
					for (var i = 0; i < inputs.length; i++)
					{
						if (inputs[i].name == "idObjetoAmbito")
							inputs[i].value = "";
						else if (inputs[i].name == "nombreObjetoAmbito")
							inputs[i].value = "";
					}
				}
				else if (divs[countDivs].id.search(/^divAmbito/) == 0)
					td.removeChild(divs[countDivs]);
			}
		}
	}
}

function cleanCondicionesAvanzadas(form)
{
	var tblCondiciones = document.getElementById("tblCondiciones");
	if (tblCondiciones) {
		var tbody = tblCondiciones.getElementsByTagName("tbody").item(0);
		for (var i = tbody.rows.length; i > 0; i--) {
            tblCondiciones.deleteRow(i);
        }
        addTableRow();
    }
}

function cleanDatoNumerico(form)
{
	var numeroComp = document.getElementById('numeroComp');
	if ((numeroComp != null) && (numeroComp != '') && (numeroComp != 'undefined'))
		numeroComp.value = "=";

	var numero = document.getElementById('numero');
	if ((numero != null) && (numero != '') && (numero != 'undefined'))
		numero.value = "";

	var tipoMedida = document.getElementById('tipoMedida');
	if ((tipoMedida != null) && (tipoMedida != '') && (tipoMedida != 'undefined'))
		tipoMedida.value = "";

	var unidadMedida = document.getElementById('unidadMedida');
	if ((unidadMedida != null) && (unidadMedida != '') && (unidadMedida != 'undefined'))
		unidadMedida.value = "";
}

function cleanAllDescriptors(form)
{
	var selectElem = form.elements["selectDescriptores"];
	if ((selectElem != null) && (selectElem != '') && (selectElem != 'undefined')) {
		while (selectElem.options.length > 0)
			selectElem.remove(0);
	}
	var elem = form.descriptores;
	if (elem)
	{
		var value = "";
		selectElem = form.elements["selectDescriptores"];
		for (var i = 0; i < selectElem.options.length; i++)
		{
			if (i > 0)
				value += "#";
			value += selectElem.options[i].value + ":"+ selectElem.options[i].text;
		}
		elem.value = value;
	}
}

function cleanAllProductors(form)
{
	var selectElem = form.elements["selectProductores"];
	if ((selectElem != null) && (selectElem != '') && (selectElem != 'undefined')) {
		while (selectElem.options.length > 0)
			selectElem.remove(0);
	}
	var elem = form.productores;
	if (elem)
	{
		var value = "";
		selectElem = form.elements["selectProductores"];
		for (var i = 0; i < selectElem.options.length; i++)
		{
			if (i > 0)
				value += "#";
			value += selectElem.options[i].value + ":"+ selectElem.options[i].text;
		}
		elem.value = value;
	}
}

function cleanProductor(form)
{
	var selectElem = form.elements["selectProductores"];
	if ((selectElem != null) && (selectElem != '') && (selectElem != 'undefined')) {
		while (selectElem.options.length > 0)
			selectElem.remove(0);
	}
}

function cleanEstado(form)
{
	var estados = document.getElementById('estados');
	if ((estados != null) && (estados != '') && (estados != 'undefined'))
		deseleccionarCheckboxSet(form.estados);
}

function cleanFechaFinal(form)
{
	var fechaCompFin = document.getElementById('fechaCompFin');
	if ((fechaCompFin != null) && (fechaCompFin != '') && (fechaCompFin != 'undefined'))
		fechaCompFin.value = "=";

	var fechaFormatoFin = document.getElementById('fechaFormatoFin');
	if ((fechaFormatoFin != null) && (fechaFormatoFin != '') && (fechaFormatoFin != 'undefined'))
		fechaFormatoFin.value = "AAAA";

	var fechaDFin = document.getElementById('fechaDFin');
	if ((fechaDFin != null) && (fechaDFin != '') && (fechaDFin != 'undefined'))
		fechaDFin.value = "";

	var fechaMFin = document.getElementById('fechaMFin');
	if ((fechaMFin != null) && (fechaMFin != '') && (fechaMFin != 'undefined'))
		fechaMFin.value = "";

	var fechaAFin = document.getElementById('fechaAFin');
	if ((fechaAFin != null) && (fechaAFin != '') && (fechaAFin != 'undefined'))
		fechaAFin.value = "";

	var fechaSFin = document.getElementById('fechaSFin');
	if ((fechaSFin != null) && (fechaSFin != '') && (fechaSFin != 'undefined'))
		fechaSFin.value = "";

	checkFechaCompSuffix(form,'fechaCompFin','Fin');

	var calificadorFinal = document.getElementById('calificadorFinal');
	if ((calificadorFinal != null) && (calificadorFinal != '') && (calificadorFinal != 'undefined'))
		calificadorFinal.value = "";
}

function cleanFechaInicial(form)
{
	var fechaCompIni = document.getElementById('fechaCompIni');
	if ((fechaCompIni != null) && (fechaCompIni != '') && (fechaCompIni != 'undefined'))
		fechaCompIni.value = "=";

	var fechaFormatoIni = document.getElementById('fechaFormatoIni');
	if ((fechaFormatoIni != null) && (fechaFormatoIni != '') && (fechaFormatoIni != 'undefined'))
		fechaFormatoIni.value = "AAAA";

	var fechaDIni = document.getElementById('fechaDIni');
	if ((fechaDIni != null) && (fechaDIni != '') && (fechaDIni != 'undefined'))
		fechaDIni.value = "";

	var fechaMIni = document.getElementById('fechaMIni');
	if ((fechaMIni != null) && (fechaMIni != '') && (fechaMIni != 'undefined'))
		fechaMIni.value = "";

	var fechaAIni = document.getElementById('fechaAIni');
	if ((fechaAIni != null) && (fechaAIni != '') && (fechaAIni != 'undefined'))
		fechaAIni.value = "";

	var fechaSIni = document.getElementById('fechaSIni');
	if ((fechaSIni != null) && (fechaSIni != '') && (fechaSIni != 'undefined'))
		fechaSIni.value = "";

	checkFechaCompSuffix(form,'fechaCompIni','Ini');

	var calificadorInicial = document.getElementById('calificadorInicial');
	if ((calificadorInicial != null) && (calificadorInicial != '') && (calificadorInicial != 'undefined'))
		calificadorInicial.value = "";
}

function cleanFecha(form)
{
	var fechaComp = document.getElementById('fechaComp');
	if ((fechaComp != null) && (fechaComp != '') && (fechaComp != 'undefined'))
		fechaComp.value = "=";

	var fechaFormato = document.getElementById('fechaFormato');
	if ((fechaFormato != null) && (fechaFormato != '') && (fechaFormato != 'undefined'))
		fechaFormato.value = "AAAA";

	var fechaD = document.getElementById('fechaD');
	if ((fechaD != null) && (fechaD != '') && (fechaD != 'undefined'))
		fechaD.value = "";

	var fechaM = document.getElementById('fechaM');
	if ((fechaM != null) && (fechaM != '') && (fechaM != 'undefined'))
		fechaM.value = "";

	var fechaA = document.getElementById('fechaA');
	if ((fechaA != null) && (fechaA != '') && (fechaA != 'undefined'))
		fechaA.value = "";

	var fechaS = document.getElementById('fechaS');
	if ((fechaS != null) && (fechaS != '') && (fechaS != 'undefined'))
		fechaS.value = "";

	checkFechaComp(form);

	var calificador = document.getElementById('calificador');
	if ((calificador != null) && (calificador != '') && (calificador != 'undefined'))
		calificador.value = "";
}

function cleanFondo(form)
{
	var fondo = document.getElementById('fondo');
	if ((fondo != null) && (fondo != '') && (fondo != 'undefined'))
		fondo.value = "";
}

function cleanNivelesDescripcion(form)
{
	var niveles = document.getElementById('niveles');
	if ((niveles != null) && (niveles != '') && (niveles != 'undefined'))
		deseleccionarCheckboxSet(form.niveles);
}

function cleanNumeroExpediente(form)
{
	var numeroExpediente = document.getElementById('numeroExpediente');
	if ((numeroExpediente != null) && (numeroExpediente != '') && (numeroExpediente != 'undefined'))
		numeroExpediente.value = "";
}

function cleanTexto(form)
{
	var texto = document.getElementById('texto');
	if ((texto != null) && (texto != '') && (texto != 'undefined'))
		texto.value = "";
}

function cleanTitulo(form)
{
	var titulo = document.getElementById('titulo');
	if ((titulo != null) && (titulo != '') && (titulo != 'undefined'))
		titulo.value = "";
}

function cleanBloqueo(form)
{
	var bloqueos = document.getElementById('bloqueos');
	if ((bloqueos != null) && (bloqueos != '') && (bloqueos != 'undefined'))
		deseleccionarCheckboxSet(form.bloqueos);
}

function cleanProcedimiento(form)
{
	var procedimiento = document.getElementById('procedimiento');
	if ((procedimiento != null) && (procedimiento != '') && (procedimiento != 'undefined'))
		procedimiento.value = "";

	var listaProcedimientos = xGetElementById('listaProcedimientos');
	if ((listaProcedimientos != null) && (listaProcedimientos != '') && (listaProcedimientos != 'undefined'))
		xDisplay(listaProcedimientos, 'none');

	var buscadorProcedimiento = xGetElementById('seleccionProcedimiento');
	if ((buscadorProcedimiento != null) && (buscadorProcedimiento != '') && (buscadorProcedimiento != 'undefined'))
		xDisplay(buscadorProcedimiento, 'none');
}

function cleanContenidoFichero(form)
{
	var contenidoFichero = document.getElementById('contenidoFichero');
	if ((contenidoFichero != null) && (contenidoFichero != '') && (contenidoFichero != 'undefined'))
		contenidoFichero.value = "";
}

function cleanCampoCheckbox(form,namecheck,checked){
	object = form.elements[namecheck];

	if (object){
		if(object.length){
			for(i=0;i<form.elements[namecheck].length;i++){
				form.elements[namecheck][i].checked = checked;
			}
		}
		else{
			form.elements[namecheck].checked = checked;
		}
	}
}