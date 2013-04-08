/***************************************************************/
/*******	Búsqueda avanzada		****************************/

var valorTipoFecha1;
var valorTipoFecha2;

var SIGLO = "S";
var ANIO = "AAAA";
var MES_ANIO = "MMAAAA";
var DIA_MES_ANIO = "DDMMAAAA";

var CAMPO_VALOR_TIPO_FECHA = "formatoFechaSel";
var CAMPO_TIPO_FECHA = "formatoFecha";
var CAMPO_TIPO_OPERADOR = "operador";
var SEPARADOR = "_";
var SEPARADOR_FECHA = "-";

var NUMERO_FILA;

function getTableRow(rowIndex)
{
    
    NUMERO_FILA = getNumeroFila();
    
    var row = creaElemento("<tr valign='top'>");
    var td = document.createElement("td");
    //var tdContent = creaElemento("<input type='checkbox' name='check' id='check_"+ rowIndex +"' style='border:0' />");
	
	td.innerHTML = "<input type='checkbox' name='check' id='check_"+ NUMERO_FILA +"' style='border:0' /><input type='hidden' size='2' name='" + CAMPO_VALOR_TIPO_FECHA + "1' id='" + CAMPO_VALOR_TIPO_FECHA + "1_"+ NUMERO_FILA +"' value='0'/><input type='hidden' size='2' name='" + CAMPO_VALOR_TIPO_FECHA + "2' id='" + CAMPO_VALOR_TIPO_FECHA + "2_"+ NUMERO_FILA +"' value='0'/>";
    row.appendChild(td);

    td = document.createElement("td");
    
    tdContent = creaElemento("<select name='booleano' id='booleano_"+ NUMERO_FILA +"'>");
    addOptionInSelect(tdContent,new Option("   ", ""));
    for (var i = 0; i < BOOLEANOS.length; i++)
    {
        addOptionInSelect(tdContent,new Option(BOOLEANOS[i][1], BOOLEANOS[i][0]));
        if (rowIndex > 1 && BOOLEANOS[i][2])
            tdContent.options.selectedIndex = i+1;
    }
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = creaElemento("<select name='abrirpar' id='abrirpar_"+ NUMERO_FILA+"'>");
    addOptionInSelect(tdContent,new Option("", ""));
    addOptionInSelect(tdContent,new Option("(", "("));
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = creaElemento("<select name='campo' id='campo_"+ NUMERO_FILA +"' onchange='javascript:cambioEnCampo(this,"+ NUMERO_FILA +")'>");
    addOptionInSelect(tdContent,new Option("", ""));
    for (var i = 0; i < CAMPOS.length; i++)
    	addOptionInSelect(tdContent,new Option(CAMPOS[i][1], CAMPOS[i][0]));
    td.appendChild(tdContent);
    tdContent = creaElemento("<input type='hidden' name='tipoCampo' id='tipoCampo_"+ NUMERO_FILA +"'/>");
    td.appendChild(tdContent);
    row.appendChild(td);
    tdContent = creaElemento("<input type='hidden' name='tiposReferencia' id='tipoReferencia_"+ NUMERO_FILA +"'/>");
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = creaElemento("<select name='operador' id='operador_"+ NUMERO_FILA +"' onchange='javascript:cambioEnOperador(this,"+ NUMERO_FILA +")'>");
    addOptionInSelect(tdContent,new Option("", ""));
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    td.noWrap = true;
    
	//td.innerHTML = "<input type='hidden' name='valor1' id='valor1_"+ NUMERO_FILA +"' /><input type='hidden' name='valor1D' id='valor1D_"+ NUMERO_FILA +"'/><input type='hidden' name='valor1M' id='valor1M_"+ NUMERO_FILA +"'/><input type='hidden' name='valor1A' id='valor1A_"+ NUMERO_FILA +"'/><input type='hidden' name='nombreDesc'  id='nombreDesc_"+ NUMERO_FILA +"'/>";
     td.innerHTML = getCampos(1,NUMERO_FILA,false,false,false,false,false,false,false);

    row.appendChild(td);

    td = document.createElement("td");
    td.noWrap = true;
    
//	td.innerHTML = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/>";
// 	td.innerHTML = "<input type='hidden' name='valor2' id='valor2_"+ NUMERO_FILA +"' /><input type='hidden' name='valor2D' id='valor2D_"+ NUMERO_FILA +"'/><input type='hidden' name='valor2M' id='valor2M_"+ NUMERO_FILA +"'/><input type='hidden' name='valor2A' id='valor2A_"+ NUMERO_FILA +"'/>";
     td.innerHTML = getCampos(2,NUMERO_FILA,false,false,false,false,false,false,false);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = creaElemento("<select name='cerrarpar'  id='cerrarpar_"+ NUMERO_FILA +"'>");
    addOptionInSelect(tdContent,new Option("", ""));
    addOptionInSelect(tdContent,new Option(")", ")"));
    td.appendChild(tdContent);
    row.appendChild(td);
    
    return row;
}
function insertTableRow()
{
	NUMERO_FILA = getNumeroFila();
	NUMERO_FILA++;
	setNumeroFila(NUMERO_FILA);
	
	var tabla = document.getElementById("tblCondiciones");
	var checkboxes = document.getElementsByName("check");
	for (var i = 0; i < checkboxes.length; i++)
	{
        if (checkboxes[i].checked)
        {
		    var tbody = tabla.getElementsByTagName("tbody").item(0);
            tbody.insertBefore(getTableRow(checkboxes[i].parentNode.parentNode.rowIndex), checkboxes[i].parentNode.parentNode);
            break;
        }
    }
}
function addTableRow()
{
	NUMERO_FILA = getNumeroFila();
	NUMERO_FILA++;
	setNumeroFila(NUMERO_FILA);
	var tabla = document.getElementById("tblCondiciones");
    var tbody = tabla.getElementsByTagName("tbody").item(0);
    tbody.appendChild(getTableRow(tbody.rows.length+1));
}

function removeTableRows()
{
	var tabla = document.getElementById("tblCondiciones");
	var checkboxes = document.getElementsByName("check");
	if (tabla && checkboxes) {
		for (var i = checkboxes.length - 1; i >= 0; i--) {
	        if (checkboxes[i].checked) {
	            tabla.deleteRow(checkboxes[i].parentNode.parentNode.rowIndex);
            }
        }
    }
}

function cambioEnCampo(select,fila){
	var tabla = document.getElementById("tblCondiciones");
	var tbody = tabla.getElementsByTagName("tbody").item(0);
	var tr = tbody.rows[select.parentNode.parentNode.rowIndex-1];
	var tdOperador = tr.cells[4];
	var selectOperador = getNoTextChildNode(tdOperador,0);
	var inputTipoCampo = select.parentNode.getElementsByTagName("input").item(0);
	removeAllOptions(selectOperador.options);
	


	var idCampo = select.options[select.selectedIndex].value;
	if (idCampo == "")
	{
		inputTipoCampo.value = "";
		addOptionInSelect(selectOperador,new Option("   ", ""));
    	var tdValor1 = tr.cells[5];
    	var tdValor2 = tr.cells[6];
		//tdValor1.innerHTML = "<input type='hidden' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc'/>";
		tdValor1.innerHTML = getCampos(1,fila,false,false,false,false,false,false,false);
		//tdValor2.innerHTML = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/><input type='hidden' name='valor2S'/>";
		tdValor2.innerHTML = getCampos(2,fila,false,false,false,false,false,false,false);
	}
	else
	{
		var infoCampo = getInfoCampo(idCampo);
		inputTipoCampo.value = infoCampo[2];
		
		if (infoCampo[2] == 5) //Enlace
		{
			addOptionInSelect(selectOperador,new Option(OPERADORES[0][1], OPERADORES[0][0]));
		}
		else if (infoCampo[2] == 3) //Fecha
		{
			addOptionInSelect(selectOperador,new Option(OPERADORES[0][1], OPERADORES[0][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[1][1], OPERADORES[1][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[2][1], OPERADORES[2][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[3][1], OPERADORES[3][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[4][1], OPERADORES[4][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[5][1], OPERADORES[5][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[8][1], OPERADORES[8][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[9][1], OPERADORES[9][0]));
			

		}
		else if (infoCampo[2] == 4) //Número
		{
			addOptionInSelect(selectOperador,new Option(OPERADORES[0][1], OPERADORES[0][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[1][1], OPERADORES[1][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[2][1], OPERADORES[2][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[3][1], OPERADORES[3][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[4][1], OPERADORES[4][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[5][1], OPERADORES[5][0]));
		}
		else if (infoCampo[2] == 2) //Texto largo
		{
			addOptionInSelect(selectOperador,new Option(OPERADORES[6][1], OPERADORES[6][0]));
		}
		else if (infoCampo[2] == 1) //Texto corto
		{
			addOptionInSelect(selectOperador,new Option(OPERADORES[0][1], OPERADORES[0][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[1][1], OPERADORES[1][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[2][1], OPERADORES[2][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[3][1], OPERADORES[3][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[4][1], OPERADORES[4][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[7][1], OPERADORES[7][0]));
			addOptionInSelect(selectOperador,new Option(OPERADORES[6][1], OPERADORES[6][0]));
		}

		cambioEnOperador(selectOperador, fila);
	}
}

function cambioEnOperador(select,fila)
{
	var operador = select.options[select.selectedIndex].value;
	var tabla = document.getElementById("tblCondiciones");
	var tbody = tabla.getElementsByTagName("tbody").item(0);
	var tr = tbody.rows[select.parentNode.parentNode.rowIndex-1];
	var tdCampo = tr.cells[3];
	var selectCampo = getNoTextChildNode(tdCampo,0);
	var idCampo = selectCampo.options[selectCampo.selectedIndex].value;
	var infoCampo = getInfoCampo(idCampo);
	var tdValor1 = tr.cells[5];
	var tdValor2 = tr.cells[6];
	
	var contentValor1 = "";
	var contentValor2 = "";
	var idRelacionesDesc = "";

	document.getElementById("tipoReferencia_"+fila).value=infoCampo[3];
	if (operador == "QC" || operador == "QCC")
	{
		//contentValor1 = "<input type='text'   name='valor1'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc'/>";
		contentValor1 = getCampos(1,fila, false, true, false, false, false, false, false);
		//contentValor2 = "<input type='hidden' name='valor2'/><input type='hidden' name='formatoFecha2'><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/><input type='hidden' name='valor2S'/>";
		contentValor2 = getCampos(2,fila, false, false, false, false, false, false, false);
		
	}
	else if (operador == "=" || operador == "<" || operador == "<="
		|| operador == ">" || operador == ">=")
	{
		if (infoCampo[2] == 5)
		{
		    tdValor1.style.width = "200px";
		    
	        idRelacionesDesc = "" + new Date().getTime();
		    
		    
		    
		   // contentValor1 = "<input type='text' name='nombreDesc' class='inputRO90' readonly='true'/>"
		    //    + "<input type='hidden' name='valor1'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/>";
		     contentValor1 = getCampos(1,fila, false, false, false, false, false, false, true);
		       
		       
		     contentValor1 += "<a href='javascript:showSearchObjectForm(" 
		        + select.parentNode.parentNode.rowIndex
		        + ")'><img src='../../pages/images/pixel.gif' width='2px' class='imgTextMiddle'/>"
		        + "<img src='../../pages/images/expand.gif' class='imgTextMiddle'/></a>";
		}
		else if (infoCampo[2] == 4 || infoCampo[2] == 1)
			contentValor1 = "<input type='text' name='valor1'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc'/>";
		else{
			contentValor1 = getCamposByTipoFecha(1,fila);
			//contentValor1 = "<input type='hidden' name='valor1'/><input type='text' name='valor1D' size='2' maxlength='2'/>-<input type='text' name='valor1M' size='2' maxlength='2'/>-<input type='text' name='valor1A' size='4' maxlength='4'/><input type='hidden' name='nombreDesc'/>";
		}
        
		//contentValor2 = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/><input type='hidden' name='valor2S'/>";
		contentValor2 = getCampos(2,fila, false, false, false, false, false, false, false);
	}
	else if (operador == "[..]")
	{
		if (infoCampo[2] == 4 || infoCampo[2] == 1)
		{
			//contentValor1 = "<input type='text' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc'/>";
			contentValor1 = getCampos(1,fila, false, true, false, false, false, false, false);
			//contentValor2 = "<input type='text' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/><input type='hidden' name='valor2S'/>";
			contentValor2 = getCampos(2,fila, false, true, false, false, false, false, false);
		}
		else
		{
			contentValor1 = getCamposByTipoFecha(1,fila);
			contentValor2 = getCamposByTipoFecha(2,fila);
			//contentValor1 = "<input type='hidden' name='valor1'/><input type='text' name='valor1D' size='2' maxlength='2'/>-<input type='text' name='valor1M' size='2' maxlength='2'/>-<input type='text' name='valor1A' size='4' maxlength='4'/><input type='hidden' name='nombreDesc'/>";
			//contentValor2 = "<input type='hidden' name='valor2'/><input type='text' name='valor2D' size='2' maxlength='2'/>-<input type='text' name='valor2M' size='2' maxlength='2'/>-<input type='text' name='valor2A' size='4' maxlength='4'/>";
		}	
	}
	else if (operador == "QCN" || operador == "EX")
	{
		
		//contentValor1 = "<input type='hidden' name='valor1'/><input type='text' name='valor1D' size='2' maxlength='2'/>-<input type='text' name='valor1M' size='2' maxlength='2'/>-<input type='text' name='valor1A' size='4' maxlength='4'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc'/>";
		contentValor1 = getCamposByTipoFecha(1,fila);
		//contentValor2 = "<input type='hidden' name='valor2'/><input type='text' name='valor2D' size='2' maxlength='2'/>-<input type='text' name='valor2M' size='2' maxlength='2'/>-<input type='text' name='valor2A' size='4' maxlength='4'/><input type='hidden' name='valor2S'/>";
		contentValor2 = getCamposByTipoFecha(2,fila);
	}
	else
	{
		//contentValor1 = "<input type='hidden' name='valor1'/><input type='hidden' name='formatoFecha1'><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='valor1S'/><input type='hidden' name='nombreDesc'/>";
		contentValor1 = getCampos(1,fila, false, false, false, false, false, false, false);
		//contentValor2 = "<input type='hidden' name='valor2'/><input type='hidden' name='formatoFecha2'><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/><input type='hidden' name='valor2S'/>";
		contentValor2 = getCampos(2,fila, false, false, false, false, false, false, false);
	}

    contentValor1 = contentValor1 + "<input type='hidden' name='idRelacionesDesc' value='" + idRelacionesDesc + "'/>";
	
	
	tdValor1.innerHTML = contentValor1;
	tdValor2.innerHTML = contentValor2;
}
function getInfoCampo(idCampo)
{
	for (var i = 0; i < CAMPOS.length; i++)
		if (CAMPOS[i][0] == idCampo)
			return CAMPOS[i];
}

function cambioTipoFecha(select,orden,fila){
	var tabla = document.getElementById("tblCondiciones");
	var tbody = tabla.getElementsByTagName("tbody").item(0);
	var tr = tbody.rows[select.parentNode.parentNode.rowIndex-1];

	document.getElementById(CAMPO_VALOR_TIPO_FECHA  + orden + SEPARADOR + fila).value = select.selectedIndex;

	selectOperador = document.getElementById(CAMPO_TIPO_OPERADOR + SEPARADOR + fila);
	if(orden == 1 && selectOperador.value != "[..]" && selectOperador.value != "EX" && selectOperador.value != "QCN"){
		document.getElementById(CAMPO_VALOR_TIPO_FECHA  + "2" + SEPARADOR + fila).value = select.selectedIndex;
	}
	
	if(orden == 1){
		tdValor = tr.cells[5];
	}
	else{
		tdValor = tr.cells[6];
	}
	
	var contenido = getCamposByTipoFecha(orden,fila);
	contenido += "<input type='hidden' name='idRelacionesDesc' value=''/>";
	tdValor.innerHTML = contenido;
}




function getCamposByTipoFecha(orden,fila){
	var posicion = parseInt(document.getElementById(CAMPO_VALOR_TIPO_FECHA + orden + SEPARADOR + fila).value,10);
	

	var valor = TIPOSFECHA[posicion][0];

	var str;


	switch(valor){
		case SIGLO:
			str = getCampos(orden,fila,true, false, false, false, false, true, false);
			//str = "<input type='text' size='5' maxlength='5' name='valor"+ orden +"S'/><input type='hidden' name='valor"+ orden +"D' size='2' maxlength='2'/><input type='hidden' name='valor"+ orden +"M' size='2' maxlength='2'/><input type='hidden' name='valor"+ orden +"A' size='4' maxlength='4'/>";
			str
			break;
	case ANIO:
			str = getCampos(orden,fila,true, false, false, false, true, false, false);
			//str = "<input type='hidden' name='valor"+ orden +"D' size='2' maxlength='2'/><input type='hidden' name='valor"+ orden +"M' size='2' maxlength='2'/><input type='text' name='valor"+ orden +"A' size='4' maxlength='4'/><input type='hidden' name='valor"+ orden +"S'/>";
			break;
	case MES_ANIO:
			str = getCampos(orden,fila,true, false, false, true, true, false, false);
			//str = "<input type='hidden' name='valor"+ orden +"D' size='2' maxlength='2'/><input type='text' name='valor"+ orden +"M' size='2' maxlength='2'/>-<input type='text' name='valor"+ orden +"A' size='4' maxlength='4'/><input type='hidden' name='valor"+ orden +"S'/>";
			break;
	case DIA_MES_ANIO:
			str = getCampos(orden,fila,true, false, true, true, true, false, false);
			//str = "<input type='text' name='valor"+ orden +"D' size='2' maxlength='2'/>-<input type='text' name='valor"+ orden +"M' size='2' maxlength='2'/>-<input type='text' name='valor"+ orden +"A' size='4' maxlength='4'/><input type='hidden' name='valor"+ orden +"S'/>";
			break;
	}
	
	//str += "<input type='hidden' name='valor"+ orden +"'/><input type='hidden' name='nombreDesc'/>";

	return str;
}

function getCampos(orden,fila,mostrarFormato, mostrarValor, mostrarValorD, mostrarValorM, mostrarValorA, mostrarValorS, mostrarDesc){

	
	var CAMPO_FORMATOFECHA = CAMPO_TIPO_FECHA + String(orden) ;
	var CAMPO_VALOR 	= "valor"  + String(orden);
	var CAMPO_VALOR_D	= "valor"  + String(orden) + "D";
	var CAMPO_VALOR_M 	= "valor"  + String(orden) + "M";
	var CAMPO_VALOR_A	= "valor"  + String(orden) + "A";
	var CAMPO_VALOR_S	= "valor"  + String(orden) + "S";
	var CAMPO_NOMBREDESC= "nombreDesc";

	if(mostrarFormato){
		CAMPO_FORMATOFECHA = getSelectTipoFecha(orden,fila) + "&nbsp;";
	}else{
		CAMPO_FORMATOFECHA = getCampoHidden(CAMPO_FORMATOFECHA,fila);
	}
	
	if(mostrarValor){
		CAMPO_VALOR = getCampoTextoSimple(CAMPO_VALOR,fila);
	}else{
		CAMPO_VALOR = getCampoHidden(CAMPO_VALOR,fila);
	}	
	
	if(mostrarValorD){
		CAMPO_VALOR_D = getCampoTexto(CAMPO_VALOR_D,fila,2,2);
	}else{
		CAMPO_VALOR_D = getCampoHidden(CAMPO_VALOR_D,fila);
	}

	if(mostrarValorM){
		CAMPO_VALOR_M = getCampoTexto(CAMPO_VALOR_M,fila,2,2);
	}else{
		CAMPO_VALOR_M = getCampoHidden(CAMPO_VALOR_M,fila);
	}
	
	if(mostrarValorA){
		CAMPO_VALOR_A = getCampoTexto(CAMPO_VALOR_A,fila,4,4);
	}
	else{
		CAMPO_VALOR_A = getCampoHidden(CAMPO_VALOR_A,fila);
	}	
	
	if(mostrarValorS){
		CAMPO_VALOR_S = getCampoTexto(CAMPO_VALOR_S,fila,5,5);
	}else{
		CAMPO_VALOR_S = getCampoHidden(CAMPO_VALOR_S,fila);
	}
	
	
	if(orden == 2){	
		CAMPO_NOMBREDESC = "";
	}
	else{

		if(mostrarDesc){
			CAMPO_NOMBREDESC = getCampoSoloLectura(CAMPO_NOMBREDESC,fila);
		}
		else{
			CAMPO_NOMBREDESC = getCampoHidden(CAMPO_NOMBREDESC,fila);
		}
	}
	
	str = 	CAMPO_FORMATOFECHA +
			CAMPO_VALOR +
			CAMPO_VALOR_D;

	if(mostrarValorD && mostrarValorM){
		 str += SEPARADOR_FECHA;
	}

	str +=	CAMPO_VALOR_M;

	if(mostrarValorM && mostrarValorA) {
		str+= SEPARADOR_FECHA;
	}

	str += 	CAMPO_VALOR_A +
			CAMPO_VALOR_S +
			CAMPO_NOMBREDESC;
	
	//alert(str);		
	return str;
}

function getCampoHidden(nombre,fila){
	return "<input type='hidden' name='"+ nombre +"' id='"+ nombre + SEPARADOR + fila +"'>";
}

function getCampoTexto(nombre,fila, tamanio, longitudMax){
	return "<input type='text' name='"+ nombre +"' id='"+ nombre + SEPARADOR + fila +"' size='"+ tamanio + "' maxlength='"+ longitudMax +"'>";
}

function getCampoTextoSimple(nombre,fila){
	return "<input type='text' name='"+ nombre +"' id='"+ nombre + SEPARADOR + fila +"'>";
}

function getCampoSoloLectura(nombre,fila){
	return "<input type='text' name='"+ nombre +"' id='"+ nombre + SEPARADOR + fila +"' class='inputRO90' readonly='true'>";
}

function getSelectTipoFecha(orden,fila){
	var nombre = CAMPO_TIPO_FECHA + String(orden);

	var nombreCampo = CAMPO_VALOR_TIPO_FECHA + String(orden) + SEPARADOR + String(fila);
	var posicionSeleccionada = parseInt(document.getElementById(nombreCampo).value,10);
	

	var str = "<select name='" + nombre +"' id='"+ nombre +"' onchange='cambioTipoFecha(this,"+ orden +","+ fila + ")'>";
		for(var i=0;i<TIPOSFECHA.length;i++){
			var valor = TIPOSFECHA[i][0];
			var texto = TIPOSFECHA[i][1];
			str += "<option value='"+ valor +"'";
			
			if(i == posicionSeleccionada){
				str += " SELECTED";
			}
			str += ">"+ texto +"</option>";
		}
	str += "</select>";

	return str;
}

function getNumeroFila(){
	return parseInt(document.getElementById("contadorDetallesAvanzados").value,10)
}

function setNumeroFila(valor){
	document.getElementById("contadorDetallesAvanzados").value = valor;
}
