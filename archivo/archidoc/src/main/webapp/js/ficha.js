function ElementDateFormatInfo(type, separator, label)
{
  this.type = type;
  this.separator = separator;
  this.label = label;
}

function ElementInfo()
{
  this.id = "";
  this.type = 0;
  this.style = "";
  this.mandatory = 'N';
  this.initialValue = "";
  this.defaultOptionValue = "";
  this.options = new Array();
  this.defaultFormatValue = "";
  this.formats = new Array();
  this.nestedElements = new Array();
  this.refType = "";
  this.refIdsLists = new Array();
  this.defaultTipoMedida = "";
  this.defaultUnidadMedida = "";
  this.mostrarTipoMedida = 'S';
  this.mostrarUnidadMedida = 'S';
  this.optionsTextId = false;
  this.usarMismoIdYNombre = false;
  this.tieneCheckRelacionado = false;
  this.textoCheck="";

  this.getOptions = function()
  {
    var currentOptions = new Array();
    for (var i = 0; i < this.options.length; i++)
      currentOptions[currentOptions.length] = new Option(this.options[i], this.options[i]);
    return currentOptions;
  }

  this.getFormats = function()
  {
    var currentFormats = new Array();
    for (var i = 0; i < this.formats.length; i++)
      currentFormats[currentFormats.length] = new Option(this.formats[i].label, this.formats[i].type);
    return currentFormats;
  }

  this.getRefIdsLists = function(separator)
  {
    var list = "";

    for (var i = 0; i < this.refIdsLists.length; i++)
    {
        if (i > 0)
            list += separator;
        list += this.refIdsLists[i];
    }

    return list;
  }
}

function isEmpty(id){
	if(id==null || id=='')
		return true;
	else
		return false;
}

function isTableEmplazamiento(idTable){
	return idTable==2;
}

function isTableInteresados(idTable){
	return idTable==3;
}


function isFormatoTabla(tipo){
	return tipo==3;
}

function isFormatoTablaTextual(tipo){
	return tipo=4;
}

function existSistemaExternoInTable(idTable){

	 var sistemaExterno=document.getElementById("tabla_"+idTable+"_sistemaExterno");
	 return (sistemaExterno!=null && sistemaExterno.value!=null && sistemaExterno.value!='');
}

function getValueSistemaExterno(idTable){
	if(existSistemaExternoInTable(idTable))
		return document.getElementById("tabla_"+idTable+"_sistemaExterno").value;
	else
		return null;
}

function ElementsInfo()
{
  this.fieldElements = new Array();
  this.tableElements = new Array();

  this.addFieldElementInfo = function(element)
  {
    if (element && element.id && (element.id != "") )
      this.fieldElements[this.fieldElements.length] = element;
  };

  this.addTableElementInfo = function(element)
  {
    if (element && element.id && (element.id != "") )
      this.tableElements[this.tableElements.length] = element;
  };

  this.getFieldElementInfo = function(id)
  {
    for (var i = 0; i < this.fieldElements.length; i++)
    {
      if (this.fieldElements[i].id == id)
        return this.fieldElements[i];
    }
  };

  this.getTableElementInfo = function(id)
  {
    for (var i = 0; i < this.tableElements.length; i++)
    {
      if (this.tableElements[i].id == id)
        return this.tableElements[i];
    }
  };
}

var elementsInfo = new ElementsInfo();

function showHideDiv(NDiv)
{
  var divN="div"+NDiv;
  var imgN="img"+NDiv;

  if ( (document.getElementById(divN).style.display == 'none') || (document.getElementById(divN).style.display == '') )
  {
    document.getElementById(divN).style.display = 'block';
    document.getElementById(imgN).src = '../images/up.gif';
    document.getElementById(imgN).title = 'Replegar';
    document.getElementById(imgN).alt = 'Replegar';
  }
  else
  {
    document.getElementById(divN).style.display = 'none';
    document.getElementById(imgN).src = '../images/down.gif';
    document.getElementById(imgN).title = 'Desplegar';
    document.getElementById(imgN).alt = 'Desplegar';
  }
}

/*
 Función que permite restaurar los campos de fecha año y día tras haberlos cambiado antes para el formato AAAAMMDD
 */
function restaurarFormatoFechaAAAAMMDD(id){

	var campoFechaTraspuesta = document.getElementById('idFechaTras_'+id);
	// Si existe el campo de fecha traspuesta restaurar año y día a su posición original
	if (campoFechaTraspuesta!=null){
		campoFechaTraspuesta.parentNode.removeChild(campoFechaTraspuesta);


		var campoAnio = document.getElementById('idFechaAValor_'+id);
		var campoDia = document.getElementById('idFechaDValor_'+id);

		// Crear un sustituto del año para reemplazar por el día
		var campoAnioSus = document.createElement("td");
		campoAnioSus.id = 'idFechaAValor_'+id+'_s';
		campoAnioSus.style.display='none';
		campoAnio.parentNode.insertBefore(campoAnioSus,campoAnio);

		// Crear un sustituto del día para reemplazar por el año
		var campoDiaSus = document.createElement("td");
		campoDiaSus.id = 'idFechaDValor_'+id+'_s';
		campoDiaSus.style.display='none';
		campoDia.parentNode.insertBefore(campoDiaSus,campoDia);

		// Eliminar los campos año y día originales
		campoAnio.parentNode.removeChild(campoAnio);
		campoDia.parentNode.removeChild(campoDia);

		// Reemplazar los sustitutos por los originales
		campoDiaSus.parentNode.replaceChild(campoAnio,campoDiaSus);
		campoAnioSus.parentNode.replaceChild(campoDia,campoAnioSus);
	}
}

/*
 Función que permite intercambiar la posición de los campos de fecha año y día para mostrar el formato AAAAMMDD
*/
function establecerFormatoFechaAAAAMMDD(id){

	// Si no existe el campo de fecha traspuesta intercambiar año y día
	if (document.getElementById('idFechaTras_'+id)==null){
		var campoAnio = document.getElementById('idFechaAValor_'+id);
		var campoDia = document.getElementById('idFechaDValor_'+id);

		// Crear un campo de fecha traspuesta para saber que se ha intercambiado el año y el día
		var campoFechaTraspuesta = document.createElement("td");
		campoFechaTraspuesta.id = 'idFechaTras_'+id;
		campoFechaTraspuesta.style.display='none';
		campoAnio.parentNode.insertBefore(campoFechaTraspuesta,campoAnio);

		// Crear un sustituto del año para reemplazar por el día
		var campoAnioSus = document.createElement("td");
		campoAnioSus.id = 'idFechaAValor_'+id+'_s';
		campoAnioSus.style.display='none';
		campoAnio.parentNode.insertBefore(campoAnioSus,campoAnio);

		// Crear un sustituto del día para reemplazar por el año
		var campoDiaSus = document.createElement("td");
		campoDiaSus.id = 'idFechaDValor_'+id+'_s';
		campoDiaSus.style.display='none';
		campoDia.parentNode.insertBefore(campoDiaSus,campoDia);

		// Eliminar los campos año y día originales
		campoAnio.parentNode.removeChild(campoAnio);
		campoDia.parentNode.removeChild(campoDia);

		// Reemplazar los sustitutos por los originales
		campoDiaSus.parentNode.replaceChild(campoAnio,campoDiaSus);
		campoAnioSus.parentNode.replaceChild(campoDia,campoAnioSus);
	}
}

function checkFechaFormato(element)
{
  if (element)
  {
    var id = element.id.substring(6);
  	if (element.value == 'AAAA')
  	{
  		restaurarFormatoFechaAAAAMMDD(id);


  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'none';
  		document.getElementById("idFechaDValor_" + id).style.display = 'none';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';
  		document.getElementById("idFechaG1_" + id).style.display = 'none';
  		document.getElementById("idFechaG2_" + id).style.display = 'none';

  		if(!document.all){
  			document.getElementById("idFechaAValor_" + id).style.display = 'table-cell';
  		}
  	}else if (element.value == 'MMAAAA') {
		restaurarFormatoFechaAAAAMMDD(id);

		document.getElementById("idFechaG1_" + id).style.display = 'none';
  		document.getElementById("idFechaG2_" + id).style.display = 'block';
  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'block';
  		document.getElementById("idFechaDValor_" + id).style.display = 'none';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';

  		if(!document.all){
			document.getElementById("idFechaG2_" + id).style.display = 'table-cell';
			document.getElementById("idFechaAValor_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaMValor_" + id).style.display = 'table-cell';
  		}
  	}else if (element.value == 'AAAAMM') {
  		establecerFormatoFechaAAAAMMDD(id);

  		document.getElementById("idFechaG1_" + id).style.display = 'block';
	  	document.getElementById("idFechaG2_" + id).style.display = 'none';
  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'block';
  		document.getElementById("idFechaDValor_" + id).style.display = 'none';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';

  		if(!document.all){
			document.getElementById("idFechaG1_" + id).style.display = 'table-cell';
			document.getElementById("idFechaAValor_" + id).style.display = 'table-cell';
			document.getElementById("idFechaMValor_" + id).style.display = 'table-cell';
  		}
	}else if (element.value == 'DDMMAAAA' || element.value == 'DDMMAAAAHHMMSS') {
		restaurarFormatoFechaAAAAMMDD(id);

  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'block';
  		document.getElementById("idFechaDValor_" + id).style.display = 'block';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';
  		document.getElementById("idFechaG1_" + id).style.display = 'block';
  		document.getElementById("idFechaG2_" + id).style.display = 'block';
  		if(!document.all){
  			document.getElementById("idFechaAValor_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaMValor_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaDValor_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaG1_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaG2_" + id).style.display = 'table-cell';
  		}
	}else if (element.value == 'AAAAMMDD' || element.value == 'AAAAMMDDHHMMSS'){
		establecerFormatoFechaAAAAMMDD(id);

  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'block';
  		document.getElementById("idFechaDValor_" + id).style.display = 'block';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';
  		document.getElementById("idFechaG1_" + id).style.display = 'block';
  		document.getElementById("idFechaG2_" + id).style.display = 'block';
  		if(!document.all){
  			document.getElementById("idFechaAValor_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaMValor_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaDValor_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaG1_" + id).style.display = 'table-cell';
  			document.getElementById("idFechaG2_" + id).style.display = 'table-cell';
  		}
  	}else /*if (element.value == 'S')*/ {
  		restaurarFormatoFechaAAAAMMDD(id);

  		document.getElementById("idFechaAValor_" + id).style.display = 'none';
  		document.getElementById("idFechaMValor_" + id).style.display = 'none';
  		document.getElementById("idFechaDValor_" + id).style.display = 'none';
  		document.getElementById("idFechaSValor_" + id).style.display = 'block';
  		if(document.getElementById("idFechaG1_" + id))
  			document.getElementById("idFechaG1_" + id).style.display = 'none';
  		if(document.getElementById("idFechaG2_" + id))
  			document.getElementById("idFechaG2_" + id).style.display = 'none';
  		if(!document.all){
  			document.getElementById("idFechaSValor_" + id).style.display = 'table-cell';
  		}
  	}


	var estilo = 'none';

	if(element.value == 'AAAAMMDDHHMMSS' || element.value == 'DDMMAAAAHHMMSS'){

		if(!document.all){
			estilo = 'table-cell';
		}
		else{
			estilo = 'block';
		}
	}

	document.getElementById("idFechaHHValor_" + id).style.display = estilo;
	document.getElementById("idFechaMMValor_" + id).style.display = estilo;
	document.getElementById("idFechaSSValor_" + id).style.display = estilo;

	document.getElementById("idHoraEspacio_" + id).style.display = estilo;
	document.getElementById("idHoraSeparador1_" + id).style.display = estilo;
	document.getElementById("idHoraSeparador2_" + id).style.display = estilo;
  }
}

function makeFieldsList(fieldElementInfo,listaFieldsIds,rownum){

     if(listaFieldsIds!='' && fieldElementInfo.id!='')
     	listaFieldsIds+=",";

     if(fieldElementInfo.id!=''){
     	if(fieldElementInfo.type==5){
     		listaFieldsIds+=fieldElementInfo.id+":"+"ref_"+fieldElementInfo.id+"_"+rownum;
     		listaFieldsIds+=":"+fieldElementInfo.refType;
     	}
     	if(fieldElementInfo.type==1){
     		listaFieldsIds+=fieldElementInfo.id+":"+"campo_"+fieldElementInfo.id+"_"+rownum;
     	}
     }



     var listaListIds=fieldElementInfo.getRefIdsLists(";");
     if(listaListIds!=''){
     	listaFieldsIds+=":"+listaListIds;
     }

     return listaFieldsIds;
}

function addTableRow(id, tableType)
{

  if (document.getElementById("tabla" + id))
  {
    var table = document.getElementById("tabla" + id);
    var thead = table.getElementsByTagName("thead").item(0);
    var theadRow = thead.getElementsByTagName("tr").item(0);
    var theadRowElements = theadRow.getElementsByTagName("th");
    var tbody = table.getElementsByTagName("tbody").item(0);
    var rownum = table.rows.length;
    var row = document.createElement("tr");
    var listaFieldsIds="";

	if (tableType == 'TABLA')
    {
      if ((rownum % 2) == 0)
        row.className = "even";
      else
        row.className = "odd";
    }

    row.id = "tr_" + id + "_" + rownum;

    for (var i = 0; i < theadRowElements.length; i++)
    {
      var theadRowElement = theadRowElements.item(i);
      var td = document.createElement("td");

      if (i == 0)
      {
        var checkbox = creaElemento("<input type='checkbox' name='eliminar_tabla_"
            + id + "' class='checkbox' style='border:0' value='" + rownum + "' onclick='javascript:checkIfSelectedToBeRemoved(this, \"tr_" + id + "_" + rownum + "\");'/>");

        td.appendChild(checkbox);
      }
      else
      {
        var tableElementInfo = elementsInfo.getTableElementInfo(id);
        var fieldElementInfo = elementsInfo.getFieldElementInfo(tableElementInfo.nestedElements[i-1]);

        // Para los enlaces en las tablas
        if(fieldElementInfo != null) {
        	listaFieldsIds=makeFieldsList(fieldElementInfo,listaFieldsIds,rownum);

        	td.appendChild(createElement(fieldElementInfo, rownum, id));

	        if (fieldElementInfo.tieneCheckRelacionado) {
	            var saltoLinea = creaElemento("br");
	            td.appendChild(saltoLinea);

	        	var checkboxDelElemento = creaElemento("<input type='checkbox' name='campo_"+fieldElementInfo.id+"_check_"+rownum
	        	//	+ "' class='checkbox' style='border:0' value='"+rownum+"' id='campo_"+fieldElementInfo.id+"_check' checked='checked' />");
	            	+ "' class='checkbox' style='border:0' value='"+rownum+"' id='campo_"+fieldElementInfo.id+"_check_"+rownum+"' checked='checked' />");

	            td.appendChild(checkboxDelElemento);
	            var elementoLabel = creaElemento("label");
	            var textoLabel = document.createTextNode(fieldElementInfo.textoCheck);
	            elementoLabel.appendChild(textoLabel);
	            td.appendChild(elementoLabel);
	        }
        }
      }
	  row.appendChild(td);
    }

    tbody.appendChild(row);

    if(existSistemaExternoInTable(id))
    	popupExternalReferencePage(listaFieldsIds,getValueSistemaExterno(id),row.id);
   }

}

function removeTableRow(idRow){
	items=idRow.split("_");
	idTable=items[1];
	var table = document.getElementById("tabla" + idTable);
	var tbody = table.getElementsByTagName("tbody").item(0);
	var row=document.getElementById(idRow);
	tbody.removeChild(row);

}

function removeTableRows(id)
{
  var table = document.getElementById("tabla" + id);
  var checkboxes = document.getElementsByName("eliminar_tabla_" + id);
  var deleteRows="";
  if (table && checkboxes)
  {
    for (var i = checkboxes.length - 1; i >= 0; i--){
        if (checkboxes[i].checked){
            table.deleteRow(checkboxes[i].parentNode.parentNode.rowIndex);
            var pos=i+1;
            if(deleteRows==""){
            	deleteRows=pos;
            }
            else{
            	deleteRows+=","+pos;
            }
        }
    }
    var deletedRowsTable="deletedRowsTable"+id;
    var deletedRowsTableElement = document.getElementById(deletedRowsTable);
    if (deletedRowsTableElement)
    	deletedRowsTableElement.value = deleteRows;

    //document.getElementById(deletedRowsTable).value=deleteRows;
  }
}


function createElement(elementInfo, rownum, idTable)
{
  var element;

  if (elementInfo.type == 1)
    element = createShortTextElement(elementInfo, rownum, idTable);
  else if (elementInfo.type == 2)
    element = createLongTextElement(elementInfo, idTable);
  else if (elementInfo.type == 3)
    element = createDateElement(elementInfo, rownum, idTable);
  else if (elementInfo.type == 4)
    element = createNumberElement(elementInfo, idTable);
  else if (elementInfo.type == 5)
    element = createReferenceElement(elementInfo, rownum, idTable);

  return element;
}

function createShortTextElement(elementInfo, rownum, idTable)
{
  var element;
  var suffix;

  // Si queremos usar el mismo valor para el id y el nombre, no añadimos sufijo con el rownum
  if (elementInfo.usarMismoIdYNombre)
   	suffix = "";
  else
  	suffix = "_" + rownum;

  if (elementInfo.options.length > 0){
    var translatedOpts =  getTranslatedOptions(elementInfo.options, elementInfo.optionsTextId);

	if (elementInfo.tieneCheckRelacionado) {
		var checkName = "campo_" + elementInfo.id + "_check" +"_" + rownum;

		element = createComboBoxElement(
    		  "campo_" + elementInfo.id + suffix,
	    	  "campo_" + elementInfo.id,
	    	  translatedOpts,
		      (!elementInfo.mandatory || (elementInfo.mandatory == 'N')),
	    	  elementInfo.defaultOptionValue,idTable,elementInfo.tipoPadre
	    	  // ,"javascript:enableCheck(this)");
	    	  ,"javascript:enableCheck("+checkName+")");
	}
	else {
	element = createComboBoxElement(
    	  "campo_" + elementInfo.id + suffix,
	      "campo_" + elementInfo.id,
    	  translatedOpts,
	      (!elementInfo.mandatory || (elementInfo.mandatory == 'N')),
	      elementInfo.defaultOptionValue,idTable,elementInfo.tipoPadre,
	      	elementInfo.onChange?elementInfo.onChange+"("+rownum+")":"");
	}
  }
  else
  {
    element = document.createElement("input");
    element.name = "campo_" + elementInfo.id ;
    element.id = "campo_" + elementInfo.id + suffix;

    /*
    if((isTableEmplazamiento(idTable) || isTableInteresados(idTable)) && elementInfo.editable=='N')
    	element.type="hidden";
    else
    */
    	element.type = "text";

    /*
    if(!isEmpty(idTable)){
    	element.style.width ="100%";
    }
    else{
    	element.size ="50";
    }
    */
    element.size = (!isEmpty(idTable) ? 20 : 50);
    element.value = "";
  }

  return element;
}

function createLongTextElement(elementInfo, idTable)
{

  var element= document.createElement("textarea");

  element.name = "campo_" + elementInfo.id;
  element.id = "campo_" + elementInfo.id;
  element.value = "";
  element.rows = (!isEmpty(idTable) ? 3 : 6);

  return element;
}

function checkCurrentFechaFormato()
{
  checkFechaFormato(this);
}

function createDateElement(elementInfo, rownum, idTable)
{
  var element = document.createElement("table");
  var tbody = document.createElement("tbody");
  var tr = document.createElement("tr");
  var td, combo, input, innerTableTr;
  var defaultFormat = elementInfo.defaultFormatValue ? elementInfo.defaultFormatValue : elementInfo.formats[0].type;

  // Tabla
  element.cellPadding = 0;
  element.cellSpacing = 0;

  // TD con el formato de fechas
  td = document.createElement("td");
  td.style.border = 0;
  var combo = createComboBoxElement(
    "campo_" + elementInfo.id + "_" + rownum,
    "campo_" + elementInfo.id + "_formato",
    elementInfo.getFormats(),
    true,
    defaultFormat,
    idTable,
    elementInfo.tipoPadre,
    "javascript:checkFechaFormato(this);");
  td.appendChild(combo);
  tr.appendChild(td);

  if (!isEmpty(idTable))
  {
      tbody.appendChild(tr);
      tr = document.createElement("tr");

      td = document.createElement("td");
      td.style.border = 0;
      tr.appendChild(td);

      var innerTable = document.createElement("table");
      innerTable.cellPadding = 0;
      innerTable.cellSpacing = 0;
      td.appendChild(innerTable);

      var innerTableTbody = document.createElement("tbody");
      innerTable.appendChild(innerTableTbody);

      innerTableTr = document.createElement("tr");
      innerTableTbody.appendChild(innerTableTr);
  }
  else
  {
      // TD separador
      td = document.createElement("td");
      td.width = 10;
      td.style.border = 0;
      td.innerHTML = "&nbsp;";
      tr.appendChild(td);
  }

  // TD Día
  td = document.createElement("td");
  td.id = "idFechaDValor_" + elementInfo.id + "_" + rownum;
  td.width = 10;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("DD") != -1) ? (document.all?"block":"table-cell") : "none");
  td._nowrap = true;
  td.style.whiteSpace="nowrap"; //el estandar w3c
  td.style.paddingLeft=0; td.style.paddingRight=0;

  input = creaElemento("<input type='text' size='2' maxlength='2' id='campo_" + elementInfo.id + "_fechaD' name='campo_" + elementInfo.id + "_fechaD'/>");
  td.appendChild(input);

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Guión 1
  td = document.createElement("td");
  td.id = "idFechaG1_" + elementInfo.id + "_" + rownum;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("MM") != -1) ? (document.all?"block":"table-cell") : "none");
  td._nowrap = true;
  td.style.whiteSpace="nowrap";	//el estandar w3c
  td.appendChild(document.createTextNode(" - "));

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Mes
  td = document.createElement("td");
  td.id = "idFechaMValor_" + elementInfo.id + "_" + rownum;
  td.width = 10;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("MM") != -1) ? (document.all?"block":"table-cell") : "none");
  td._nowrap = true;
  td.style.whiteSpace="nowrap";	//el estandar w3c
  td.style.paddingLeft=0; td.style.paddingRight=0;

  input = creaElemento("<input type='text' size='2' maxlength='2' id='campo_" + elementInfo.id + "_fechaM' name='campo_" + elementInfo.id + "_fechaM'/>");
  td.appendChild(input);

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Guión 2
  td = document.createElement("td");
  td.id = "idFechaG2_" + elementInfo.id + "_" + rownum;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("MM") != -1) ? (document.all?"block":"table-cell") : "none");
  td._nowrap = true;
  td.style.whiteSpace="nowrap";	//el estandar w3c
  td.appendChild(document.createTextNode(" - "));

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Año
  td = document.createElement("td");
  td.id = "idFechaAValor_" + elementInfo.id + "_" + rownum;
  td.width = 10;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("AAAA") != -1) ? (document.all?"block":"table-cell") : "none");
  td.nowrap = true;
  td.style.paddingLeft=0; td.style.paddingRight=0;

  input = creaElemento("<input type='text' size='4' maxlength='4' id='campo_" + elementInfo.id + "_fechaA' name='campo_" + elementInfo.id + "_fechaA'/>");

  td.appendChild(input);

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Separador Fecha Hora
  td = document.createElement("td");
  td.id = "idHoraEspacio_" + elementInfo.id + "_" + rownum;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("HHMMSS") != -1) ? (document.all?"block":"table-cell") : "none");
  td._nowrap = true;
  td.style.whiteSpace="nowrap";	//el estandar w3c
  //td.appendChild(document.createTextNode(" "));
  td.innerHTML = "&nbsp;&nbsp;&nbsp;";

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  //TD Horas
  td = document.createElement("td");
  td.id = "idFechaHHValor_" + elementInfo.id + "_" + rownum;
  td.width = 2;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("HHMMSS") != -1) ? (document.all?"block":"table-cell") : "none");
  td.nowrap = true;
  td.style.paddingLeft=0; td.style.paddingRight=0;

  input = creaElemento("<input type='text' value='00' size='2' maxlength='2' id='campo_" + elementInfo.id + "_fechaHH' name='campo_" + elementInfo.id + "_fechaHH'/>");

  td.appendChild(input);

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Separador Fecha Hora
  td = document.createElement("td");
  td.id = "idHoraSeparador1_" + elementInfo.id + "_" + rownum;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("HHMMSS") != -1) ? (document.all?"block":"table-cell") : "none");
  td._nowrap = true;
  td.style.whiteSpace="nowrap";	//el estandar w3c
  td.appendChild(document.createTextNode(":"));

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  //TD Minutos
  td = document.createElement("td");
  td.id = "idFechaMMValor_" + elementInfo.id + "_" + rownum;
  td.width = 2;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("HHMMSS") != -1) ? (document.all?"block":"table-cell") : "none");
  td.nowrap = true;
  td.style.paddingLeft=0; td.style.paddingRight=0;

  input = creaElemento("<input type='text' value='00'  size='2' maxlength='2' id='campo_" + elementInfo.id + "_fechaMM' name='campo_" + elementInfo.id + "_fechaMM'/>");

  td.appendChild(input);

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);


  // TD Separador Fecha Hora
  td = document.createElement("td");
  td.id = "idHoraSeparador2_" + elementInfo.id + "_" + rownum;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("HHMMSS") != -1) ? (document.all?"block":"table-cell") : "none");
  td._nowrap = true;
  td.style.whiteSpace="nowrap";	//el estandar w3c
  td.appendChild(document.createTextNode(":"));

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  //TD Segundos
  td = document.createElement("td");
  td.id = "idFechaSSValor_" + elementInfo.id + "_" + rownum;
  td.width = 2;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("HHMMSS") != -1) ? (document.all?"block":"table-cell") : "none");
  td.nowrap = true;
  td.style.paddingLeft=0; td.style.paddingRight=0;

  input = creaElemento("<input type='text' value='00' size='2' maxlength='2' id='campo_" + elementInfo.id + "_fechaSS' name='campo_" + elementInfo.id + "_fechaSS'  />");

  td.appendChild(input);

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);



  // TD Siglo
  td = document.createElement("td");
  td.id = "idFechaSValor_" + elementInfo.id + "_" + rownum;
  td.width = 10;
  td.style.border = 0;
  td.style.display = ( (defaultFormat == "S") ? "block" : "none");
  td.nowrap = true;

  input = creaElemento("<input type='text' size='5' maxlength='5' id='campo_" + elementInfo.id + "_fechaS' name='campo_" + elementInfo.id + "_fechaS'/>");

  td.appendChild(input);

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  if (!isEmpty(idTable))
  {
      tbody.appendChild(tr);
      tr = document.createElement("tr");

      td = document.createElement("td");
      td.style.border = 0;
      tr.appendChild(td);

      var innerTable = document.createElement("table");
      innerTable.cellPadding = 0;
      innerTable.cellSpacing = 0;
      td.appendChild(innerTable);

      var innerTableTbody = document.createElement("tbody");
      innerTable.appendChild(innerTableTbody);

      innerTableTr = document.createElement("tr");
      innerTableTbody.appendChild(innerTableTr);
  }
  else
  {
      // TD separador
      td = document.createElement("td");
      td.width = 10;
      td.style.border = 0;
      td.innerHTML = "&nbsp;";
      tr.appendChild(td);
  }

  // Etiqueta "Calificador:"
  td = document.createElement("td");
  if (isEmpty(idTable)) td.className = "tdDatosFicha";
  td.style.border = 0;
  td.appendChild(document.createTextNode("Calificador:"));

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD separador
  td = document.createElement("td");
  td.width = 10;
  td.style.border = 0;
  td.innerHTML = "&nbsp;";

  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // Calificador
  td = document.createElement("td");
  td.style.border = 0;
  td.appendChild(createComboBoxElement("campo_" + elementInfo.id + "_calificador", "campo_" + elementInfo.id + "_calificador", getQualifierOptions(!isEmpty(idTable) ? 0 : 1),idTable,elementInfo.tipoPadre,true));
  if (!isEmpty(idTable))
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  tbody.appendChild(tr);
  element.appendChild(tbody);

  return element;
}

function createNumberElement(elementInfo, idTable)
{

  var element = document.createElement("table");
  var tbody = document.createElement("tbody");
  var tr = document.createElement("tr");
  var td;

  // Tabla
  element.cellPadding = 0;
  element.cellSpacing = 0;


  // TD número
  td = document.createElement("td");
  if (isEmpty(idTable)) td.className = "tdDatosFicha";

  td.style.border = 0;
  var htmlText="<input type='text' size='20' id='campo_" + elementInfo.id + "_numero' name='campo_" + elementInfo.id + "_numero' style='" + elementInfo.style + "'/>";

  // Tipo
  if(elementInfo.defaultTipoMedida != ''){
  	htmlText+="<input type='hidden' id='campo_" + elementInfo.id + "_tipoMedida' name='campo_" + elementInfo.id + "_tipoMedida' value='" + elementInfo.defaultTipoMedida + "'/>";
  }

   //Unidad
  if(elementInfo.validationTable != '')
  {
	  if (isEmpty(idTable)) td.className = "tdDatosFicha";
	  td.style.border = 0;
	  htmlText+=" Unidad: ";

	  htmlText+="<select id='campo_" + elementInfo.id + "_unidadMedida' name='campo_" + elementInfo.id + "_unidadMedida' style='" + elementInfo.style + "'>";
      htmlText+="<option value=' '>&nbsp;</option>";

      for(i=1;i<=elementInfo.options.length;i++){
      	var option=elementInfo.options[i-1];
      	htmlText+="<option value='"+option+"'>"+option+"</option>";
      }
      htmlText+="</select>";
  }

  td.innerHTML=htmlText;
  tr.appendChild(td);
  tbody.appendChild(tr);

  element.appendChild(tbody);

  return element;
}

function createReferenceElement(elementInfo, rownum, idTable)
{


  var element = document.createElement("table");
  var tbody = document.createElement("tbody");
  var tr = document.createElement("tr");
  var td;

  // Tabla
  element.cellPadding = 0;
  element.cellSpacing = 0;

  // TD con el input text
  td = document.createElement("td");
  td.style.border = 0;

  /*
  td.appendChild(document.createElement("<input type='text' id='ref_" + elementInfo.id + "_" + rownum + "' name='campo_" + elementInfo.id + "' size='" + (inTable ? 20 : 50) + "'/>"));
  td.appendChild(document.createElement("<input type='hidden' id='ref_" + elementInfo.id + "_" + rownum + "_tiporef' name='campo_" + elementInfo.id + "_tiporef'/>"));
  td.appendChild(document.createElement("<input type='hidden' id='ref_" + elementInfo.id + "_" + rownum + "_idref' name='campo_" + elementInfo.id + "_idref'/>"));
  */

  var inputType="";
  /*
  if((isTableEmplazamiento(idTable) || isTableInteresados(idTable)) && elementInfo.editable=='N')
  	inputType="hidden";

  else*/
  	inputType="text";

  if(!isEmpty(idTable)){
      td.style.width = "99%";
      td.appendChild(creaElemento("<input type='"+inputType+"' id='ref_" + elementInfo.id + "_" + rownum + "' name='campo_" + elementInfo.id + "' style='width:100%'/>"));
  }
  else{
	  td.appendChild(creaElemento("<input type='"+inputType+"' id='ref_" + elementInfo.id + "_" + rownum + "' name='campo_" + elementInfo.id + "' size='50'/>"));
  }
  td.appendChild(creaElemento("<input type='hidden' id='ref_" + elementInfo.id + "_" + rownum + "_tiporef' name='campo_" + elementInfo.id + "_tiporef'/>"));
  td.appendChild(creaElemento("<input type='hidden' id='ref_" + elementInfo.id + "_" + rownum + "_idref' name='campo_" + elementInfo.id + "_idref'/>"));

  tr.appendChild(td);

  // TD con separador
  if (isEmpty(idTable))
  {
    td = document.createElement("td");
    td.style.border = 0;
    td.innerHTML= "&nbsp;";
    tr.appendChild(td);
  }


  // TD enlace a buscar
  td = document.createElement("td");
  td.width = 10;
  td.style.border = 0;
  var enlace = document.createElement("a");
  enlace.href = "javascript:popupReferencePage('ref_" + elementInfo.id + "_" + rownum + "','" + elementInfo.refType + "','" + elementInfo.getRefIdsLists(",") + "');";
  var imagen = document.createElement("img");
  imagen.src="../images/buscar.gif";
  imagen.style.border = 0;
  imagen.alt = "Buscar";
  imagen.title = "Buscar";
  imagen.className = "imgTextTop";
  enlace.appendChild(imagen);
  //if(!existSistemaExternoInTable(idTable) && elementInfo.editable=="S"){
  td.appendChild(enlace);
  //}
  tr.appendChild(td);

  tbody.appendChild(tr);
  element.appendChild(tbody);

  return element;
}

function createComboBoxElement(id, name, options, emptyValue, defaultValue, idTable, tipoPadre, js)
{

  var elementTag = "<select";
  if (js) elementTag += " onchange='" + js + "'";
  elementTag += ">";


  var element = creaElemento(elementTag);
  element.id = id;
  element.name = name;

  if(!isEmpty(idTable) && isFormatoTabla(tipoPadre)){
  	element.style.width="100%";
  }

  if (emptyValue)
    element.options[0] = new Option(" ","");

  if (options && (options.length > 0) )
  {
    for (var i=0; i<options.length; i++)
      element.options[element.options.length] = options[i];
  }

  if (defaultValue)
    element.value = defaultValue;


  return element;
}

function addField(id)
{
  var td = document.getElementById("TD_" + id);
  if (td)
  {
    var table = td.getElementsByTagName("table").item(0);
	var tbody = table.getElementsByTagName("tbody").item(0);
    var pos = table.rows.length + 1;

    var tr = document.createElement("tr");
    tr.vAlign = "top";

    var trId = "tr_" + id + "_" + pos;

    while(document.getElementById(trId)){
    	pos++;
    	trId = "tr_" + id + "_" + pos;
    }

    tr.id = trId;

    var td1 = document.createElement("td");
    td1.width = 30;

    //metodo que detecta el uso de firefox/mozilla e inyecta javascript compatible con esos navegadores
    var checkbox = creaElemento("<input type='checkbox' name='eliminar_"
        + id + "' style='border:0' value='" + pos
        + "' onclick='javascript:checkIfSelectedToBeRemoved(this, \"tr_" + id + "_"
        + pos + "\");'/>");

	//original de IE
 	/*var checkbox = document.createElement("<input type='checkbox' name='eliminar_"
        + id + "' style='border:0' value='" + pos
        + "' onclick='javascript:checkIfSelectedToBeRemoved(this, \"tr_" + id + "_"
        + pos + "\");'/>");
 	*/

	//codigo para firefox/mozilla
	/*
	var checkbox = document.createElement("input");
    checkbox.setAttribute('type', 'checkbox');
    //checkbox.setAttribute('name', 'eliminar_' + id);
    //checkbox.setAttribute('style', 'border:0');
    checkbox.setAttribute('value', pos);
    checkbox.setAttribute('onclick',
    					  'javascript:checkIfSelectedToBeRemoved(this,"tr_"'+ id + "_" + pos + '");');
    */

    td1.appendChild(checkbox);
    tr.appendChild(td1);

    var td2 = document.createElement("td");
    td2.appendChild(createElement(elementsInfo.getFieldElementInfo(id), pos, null));
    tr.appendChild(td2);

	tbody.appendChild(tr);


  }
}

function removeFields(id)
{
  var table = document.getElementById("tabla_campo_" + id);
  var checkboxes = document.getElementsByName("eliminar_" + id);
  if (table && checkboxes)
  {
    for (var i = checkboxes.length - 1; i >= 0; i--)
        if (checkboxes[i].checked)
            table.deleteRow(checkboxes[i].parentNode.parentNode.rowIndex);
  }
}

function getQualifierOptions(mode)
{
  var qops = new Array();
  var qualifiers = [
      "ant", "Anterior a",
      "pos", "Posterior a",
      "apr", "Aproximada",
      "con", "Conocida",
      "sup", "Supuesta",
      "sic", "Tal y como se ha escrito",
      "a.C.", "Antes de Cristo",
      "p.m.", "Primera mitad",
      "s.m.", "Segunda mitad",
      "p.t.", "Primer tercio",
      "s.t.", "Segundo tercio",
      "u.t.", "Ultimo tercio",
      "c.", "Cerca",
      "p.", "Principios",
      "m.", "Mediados",
      "f.", "Finales",
      "sf", "Sin fecha"
  ];

  for (var i = 0; i < qualifiers.length; i+=2)
    qops[qops.length] = new Option(mode == 0 ? qualifiers[i] : qualifiers[i+1], qualifiers[i]);

  return qops;
}

function getTranslatedOptions(options, textId)
{
  var ret = new Array();
  for (var i = 0; i < options.length; i+=2){
  	if (textId) {
	    ret[ret.length] = new Option(options[i+1], options[i]);
	} else {
		ret[ret.length] = new Option(options[i]);
		if ((i+1)<options.length)
			ret[ret.length] = new Option(options[i+1]);
	}
  }

  return ret;
}

function popupReferencePage(ref, refType, refIdsLists)
{
	var contextPath = document.getElementById("contextPath").value;
    if (refType == 1)
        popup(contextPath + "/action/descriptores?method=form&ref=" + ref + "&refType=" + refType + "&refIdsLists=" + refIdsLists, "_blank");
    else if (refType == 2)
        popup(contextPath + "/action/buscarElementos?method=formBusquedaElementos&ref=" + ref, "_blank");
    else if (refType == 3)
        popup(contextPath + "/action/descriptores?method=form&ref=" + ref + "&refType=" + refType, "_blank");
}

function popupExternalReferencePage(ref, type, idRow)
{
	var contextPath = document.getElementById("contextPath").value;
	if (type == 1)
		popup(contextPath + "/action/gestionInteresados?method=nuevoInteresadoDescripcion&ref=" + ref + "&type=" + type + "&idRow=" + idRow, "_blank",800,600);
    else if (type == 2)
		popup(contextPath + "/action/composicionEmplazamiento?method=nuevoEmplazamientoDescripcion&ref=" + ref + "&type=" + type + "&idRow=" + idRow, "_blank",800,500);
}

/*function showFichaDescriptor(id)
{
	var contextPath = document.getElementById("contextPath").value;
	window.location.href = contextPath+"/action/isaar?method=retrieve&id="+id+"&currentInvocation=DESCRIPTOR";
}*/

function showFicha (id, tipoFicha)
{
	var contextPath = document.getElementById("contextPath").value;
	if (tipoFicha == '1')
		window.location.href = contextPath+"/action/isaar?method=retrieve&id="+id+"&currentInvocation=DESCRIPTOR";
	else
		if (tipoFicha == '2')
			window.location.href = contextPath+"/action/isadg?method=retrieve&id="+id+"&currentInvocation=ELEMENTO";
}

function changeReferenceInfo(ref)
{
    if (document.getElementById(ref))
    {
        document.getElementById(ref + "_tiporef").value = "";
        document.getElementById(ref + "_idref").value = "";
    }
}

function checkIfSelectedToBeRemoved(checkbox, trId)
{
    var tr = document.getElementById(trId);
    if (tr)
    {
        if (checkbox.checked)
        {
            tr.lastClassName = tr.className;
            tr.className = "checked";
        }
        else
        {
            tr.className = tr.lastClassName;
            tr.lastClassName = "";
        }
    }
}

function enableCheck (checkId)
{
	//var check = document.getElementById(element.id+"_check");
	//var check = document.getElementById(checkId);
	if (checkId) {
		checkId.disabled = false;
	}
}

function enableChecks ()
{
	var elementsLVol = document.getElementsByName('campo_12');
	if (elementsLVol) {
		for(var i = 0; i < elementsLVol.length; i++) {
			var nombreCheck = 'campo_12_check_'+(i+1);
	       	var checkToEnable = document.getElementById(nombreCheck);
			enableCheck(checkToEnable);
        }
    }
}
