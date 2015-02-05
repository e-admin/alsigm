/***************************************************************/


/***************************************************************/
/******* Comprobar cambios en las fechas	************/
function checkGenericoFechaComp(suffix)
{
	var idCampoGenericoFechaComp = "genericoFechaComp" + suffix;
	var idDivGenericoFecha = "idFecha" + suffix;
	var idDivGenericoRangoFechas = "idRangoFechas" + suffix;
	var idDivGenericoFechaIni = "idFechaIni" + suffix;
	var idDivGenericoFechaFin = "idFechaFin" + suffix;
	var idCampoGenericoFechaIniFormato = "genericoFechaIniFormato" + suffix;
	var idCampoGenericoFechaFinFormato = "genericoFechaFinFormato" + suffix;
	var idCampoGenericoFechaFormato = "genericoFechaFormato" + suffix;

	var campoFechaGenericaComp = document.getElementById(idCampoGenericoFechaComp);


 	if ((campoFechaGenericaComp!=null) && (campoFechaGenericaComp != '') && (campoFechaGenericaComp != 'undefined')) {
 		if (campoFechaGenericaComp.value == "[..]")
		{
			document.getElementById(idDivGenericoFecha).style.display = 'none';
			document.getElementById(idDivGenericoRangoFechas).style.display = 'block';
			if(!document.all){
				document.getElementById(idDivGenericoRangoFechas).style.display = 'table-cell';
			}

			checkGenericoFechaFormato(document.getElementById(idCampoGenericoFechaIniFormato), 'idFechaIni' ,suffix);
			checkGenericoFechaFormato(document.getElementById(idCampoGenericoFechaFinFormato), 'idFechaFin' ,suffix);
		}
		else
		{
			document.getElementById(idDivGenericoRangoFechas).style.display = 'none';
			document.getElementById(idDivGenericoFecha).style.display = 'block';
			if(!document.all){
				document.getElementById(idDivGenericoFecha).style.display = 'table-cell';
			}

			checkGenericoFechaFormato(document.getElementById(idCampoGenericoFechaFormato),'idFecha',suffix);
		}
	}
}

function checkGenericoFechaFormato(formato, prefix, suffix)
{

	var campoA = document.getElementById(prefix + "AValor" + suffix);
	var campoM = document.getElementById(prefix + "MValor" + suffix);
	var campoD = document.getElementById(prefix + "DValor" + suffix);
	var campoS = document.getElementById(prefix + "SValor" + suffix);


	if(formato.value == ""){
	    if (campoA) campoA.style.display = 'none';
		if (campoM) campoM.style.display = 'none';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'none';
	}
	else{

		if (formato.value == 'AAAA')
		{
		    if (campoA) campoA.style.display = 'block';
			if (campoM) campoM.style.display = 'none';
			if (campoD) campoD.style.display = 'none';
			if (campoS) campoS.style.display = 'none';
			if(!document.all){
	  			if (campoA) campoA.style.display = 'table-cell';
	  		}
		}
		else if (formato.value == 'MMAAAA')
		{
			if (campoA) campoA.style.display = 'block';
			if (campoM) campoM.style.display = 'block';
			if (campoD) campoD.style.display = 'none';
			if (campoS) campoS.style.display = 'none';
			if(!document.all){
	  			if (campoA) campoA.style.display = 'table-cell';
	  			if (campoM) campoM.style.display = 'table-cell';
	  		}
		}
		else if (formato.value == 'DDMMAAAA')
		{
			if (campoA) campoA.style.display = 'block';
			if (campoM) campoM.style.display = 'block';
			if (campoD) campoD.style.display = 'block';
			if (campoS) campoS.style.display = 'none';
			if(!document.all){
	  			if (campoA) campoA.style.display = 'table-cell';
	  			if (campoM) campoM.style.display = 'table-cell';
	  			if (campoD) campoD.style.display = 'table-cell';
	  		}
	    }
		else /*if (formato.value == 'S')*/
		{
			if (campoA) campoA.style.display = 'none';
			if (campoM) campoM.style.display = 'none';
			if (campoD) campoD.style.display = 'none';
			if (campoS) campoS.style.display = 'block';
			if(!document.all){
	  			if (campoS) campoS.style.display = 'table-cell';
	  		}
		}
	}
}

function checkGenericoOperadorNumerico(suffix){

	var campoOperador = document.getElementById("genericoOperadorCampoNumerico" + suffix);
	var campoValorFin = document.getElementById("genericoCampoNumericoFin" + suffix);

	if(campoOperador.value == "[..]"){
		campoValorFin.style.visibility="visible";
	}
	else{
		campoValorFin.style.visibility="hidden";
	}
}


var camposConfigurablesTextoCorto 	= new Array();
var camposConfigurablesTextoLargo 	= new Array();
var camposConfigurablesFecha	  	= new Array();
var camposConfigurablesNumericos 	= new Array();

/**
 * Resetea todos los campos configurables
 */
function cleanCamposConfigurables(){
	if(camposConfigurablesTextoCorto.length > 0){
		cleanCamposConfigurablesTextoCorto();
	}

	if(camposConfigurablesTextoLargo.length > 0){
		cleanCamposConfigurablesTextoLargo();
	}

	if(camposConfigurablesFecha.length > 0){
		cleanCamposConfigurablesFecha();
	}

	if(camposConfigurablesNumericos.length > 0){
		cleanCamposConfigurablesNumericos();
	}
}

/**
 * Resetea todos los campos configurables de Tipo Texto Corto
 */
function cleanCamposConfigurablesTextoCorto(){
	for(var i=0;i<camposConfigurablesTextoCorto.length;i++){
		var suffix = camposConfigurablesTextoCorto[i];
		document.getElementById("genericoTextoCorto" + suffix).value = "";
		document.getElementById("genericoOperadorTextoCorto" + suffix).value="contiene";
	}
}

/**
 * Resetea todos los campos configurables de Tipo Texto Largo
 */
function cleanCamposConfigurablesTextoLargo(){
	for(var i=0;i<camposConfigurablesTextoLargo.length;i++){
		var suffix = camposConfigurablesTextoLargo[i];
		document.getElementById("genericoTextoLargo" + suffix).value = "";
	}
}

/**
 * Resetea todos los campos configurables de Tipo Numérico
 */

function cleanCamposConfigurablesNumericos(){
	for(var i=0;i<camposConfigurablesNumericos.length;i++){
		var suffix = camposConfigurablesNumericos[i];
		document.getElementById("genericoOperadorCampoNumerico" + suffix).value = "=";
		document.getElementById("genericoCampoNumerico" + suffix).value="";
		document.getElementById("genericoCampoNumericoFin" + suffix).value="";

		checkGenericoOperadorNumerico(suffix);
	}
}

/**
 * Resetea todos los campos configurables de Tipo Texto Fecha
 */

function cleanCamposConfigurablesFecha(){
	for(var i=0;i<camposConfigurablesFecha.length;i++){
		var suffix = camposConfigurablesFecha[i];
		document.getElementById("genericoFechaComp" + suffix).selectedIndex = 0;

		document.getElementById("genericoFechaFormato" + suffix).selectedIndex = 0;
		document.getElementById("genericoFechaD" + suffix).value = "";
		document.getElementById("genericoFechaM" + suffix).value = "";
		document.getElementById("genericoFechaA" + suffix).value = "";
		document.getElementById("genericoFechaS" + suffix).value = "";

		document.getElementById("genericoFechaIniFormato" + suffix).selectedIndex = 0;
		document.getElementById("genericoFechaIniD" + suffix).value = "";
		document.getElementById("genericoFechaIniM" + suffix).value = "";
		document.getElementById("genericoFechaIniA" + suffix).value = "";
		document.getElementById("genericoFechaIniS" + suffix).value = "";

		document.getElementById("genericoFechaFinFormato" + suffix).selectedIndex = 0;
		document.getElementById("genericoFechaFinD" + suffix).value = "";
		document.getElementById("genericoFechaFinM" + suffix).value = "";
		document.getElementById("genericoFechaFinA" + suffix).value = "";
		document.getElementById("genericoFechaFinS" + suffix).value = "";

		document.getElementById("genericoFechaCalificador" + suffix).selectedIndex = 0;

		checkGenericoFechaComp(suffix);
	}
}


/***************************************************************/