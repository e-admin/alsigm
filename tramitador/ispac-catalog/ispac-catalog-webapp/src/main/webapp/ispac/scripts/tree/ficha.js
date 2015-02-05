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
  
  this.getRefIdsLists = function()
  {
    var list = "";
    
    for (var i = 0; i < this.refIdsLists.length; i++)
    {
        if (i > 0)
            list += ",";
        list += this.refIdsLists[i];
    }
    
    return list;
  }
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

function checkFechaFormato(element)
{
  if (element)
  {
    var id = element.id.substring(6);
  	if (element.value == 'AAAA')
  	{
  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'none';
  		document.getElementById("idFechaDValor_" + id).style.display = 'none';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';
  	}
  	else if (element.value == 'MMAAAA')
  	{
  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'block';
  		document.getElementById("idFechaDValor_" + id).style.display = 'none';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';
  	}
  	else if (element.value == 'DDMMAAAA')
  	{
  		document.getElementById("idFechaAValor_" + id).style.display = 'block';
  		document.getElementById("idFechaMValor_" + id).style.display = 'block';
  		document.getElementById("idFechaDValor_" + id).style.display = 'block';
  		document.getElementById("idFechaSValor_" + id).style.display = 'none';
  	}
  	else /*if (element.value == 'S')*/
  	{
  		document.getElementById("idFechaAValor_" + id).style.display = 'none';
  		document.getElementById("idFechaMValor_" + id).style.display = 'none';
  		document.getElementById("idFechaDValor_" + id).style.display = 'none';
  		document.getElementById("idFechaSValor_" + id).style.display = 'block';
  	}
  }
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
        var checkbox = document.createElement("<input type='checkbox' name='eliminar_tabla_" 
            + id + "' class='checkbox' style='border:0' value='" + rownum + "' onclick='javascript:checkIfSelectedToBeRemoved(this, \"tr_" + id + "_" + rownum + "\");'/>");
            
        td.appendChild(checkbox);
      }
      else
      {
        var tableElementInfo = elementsInfo.getTableElementInfo(id);
        var fieldElementInfo = elementsInfo.getFieldElementInfo(tableElementInfo.nestedElements[i-1]);
        td.appendChild(createElement(fieldElementInfo, rownum, true));
      }

      row.appendChild(td);
    }
    
    tbody.appendChild(row);
  }
}

function removeTableRows(id)
{
  var table = document.getElementById("tabla" + id);
  var checkboxes = document.getElementsByName("eliminar_tabla_" + id);
  if (table && checkboxes)
  {
    for (var i = checkboxes.length - 1; i >= 0; i--)
        if (checkboxes[i].checked)
            table.deleteRow(checkboxes[i].parentNode.parentNode.rowIndex);
  }
}


function createElement(elementInfo, rownum, inTable)
{
  var element;

  if (elementInfo.type == 1)
    element = createShortTextElement(elementInfo, inTable);
  else if (elementInfo.type == 2)
    element = createLongTextElement(elementInfo, inTable);
  else if (elementInfo.type == 3)
    element = createDateElement(elementInfo, rownum, inTable);
  else if (elementInfo.type == 4)
    element = createNumberElement(elementInfo, inTable);
  else if (elementInfo.type == 5)
    element = createReferenceElement(elementInfo, rownum, inTable);

  return element;
}

function createShortTextElement(elementInfo, inTable)
{
  var element;
  
  if (elementInfo.options.length > 0)
    element = createComboBoxElement(
      "campo_" + elementInfo.id, 
      "campo_" + elementInfo.id, 
      elementInfo.getOptions(), 
      (!elementInfo.mandatory || (elementInfo.mandatory == 'N')), 
      elementInfo.defaultOptionValue);
  else
  {
    element = document.createElement("input");
    element.name = "campo_" + elementInfo.id;
    element.type = "text";
    element.size = (inTable ? 20 : 50);
    element.value = "";
  }
  
  return element;
}

function createLongTextElement(elementInfo, inTable)
{
  var element = document.createElement("textarea");
  
  element.name = "campo_" + elementInfo.id;
  element.value = "";
  element.rows = (inTable ? 3 : 6);
  
  return element;
}

function checkCurrentFechaFormato()
{
  checkFechaFormato(this);
}

function createDateElement(elementInfo, rownum, inTable)
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
    false, 
    defaultFormat,
    "javascript:checkFechaFormato(this);");
  td.appendChild(combo);
  tr.appendChild(td);

  if (inTable)
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
  td.style.display = ( (defaultFormat.indexOf("DD") != -1) ? "block" : "none");
  td.nowrap = true;
  
  input = document.createElement("<input type='text' size='2' maxlength='2' name='campo_" + elementInfo.id + "_fechaD'/>");

  td.appendChild(input);
  td.appendChild(document.createTextNode("-"));

  if (inTable)
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Mes
  td = document.createElement("td");
  td.id = "idFechaMValor_" + elementInfo.id + "_" + rownum;
  td.width = 10;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("MM") != -1) ? "block" : "none");
  td.nowrap = true;
  
  input = document.createElement("<input type='text' size='2' maxlength='2' name='campo_" + elementInfo.id + "_fechaM'/>");
  
  td.appendChild(input);
  td.appendChild(document.createTextNode("-"));

  if (inTable)
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // TD Año
  td = document.createElement("td");
  td.id = "idFechaAValor_" + elementInfo.id + "_" + rownum;
  td.width = 10;
  td.style.border = 0;
  td.style.display = ( (defaultFormat.indexOf("AAAA") != -1) ? "block" : "none");
  td.nowrap = true;

  input = document.createElement("<input type='text' size='4' maxlength='4' name='campo_" + elementInfo.id + "_fechaA'/>");
  
  td.appendChild(input);

  if (inTable)
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
  
  input = document.createElement("<input type='text' size='5' maxlength='5' name='campo_" + elementInfo.id + "_fechaS'/>");
  
  td.appendChild(input);

  if (inTable)
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  if (inTable)
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
  if (!inTable) td.className = "data divisoria";
  td.style.border = 0;
  td.appendChild(document.createTextNode("Calificador:"));

  if (inTable)
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);
  
  // TD separador
  td = document.createElement("td");
  td.width = 10;
  td.style.border = 0;
  td.innerHTML = "&nbsp;";

  if (inTable)
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  // Calificador
  td = document.createElement("td");
  td.style.border = 0;
  td.appendChild(createComboBoxElement("campo_" + elementInfo.id + "_calificador", "campo_" + elementInfo.id + "_calificador", getQualifierOptions(inTable ? 0 : 1),true));
  if (inTable)
      innerTableTr.appendChild(td);
  else
      tr.appendChild(td);

  tbody.appendChild(tr);
  element.appendChild(tbody);

  return element;
}

function createNumberElement(elementInfo, inTable)
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
  if (!inTable) td.className = "data divisoria";
  td.style.border = 0;
  td.appendChild(document.createElement("<input type='text' size='20' name='campo_" + elementInfo.id + "_numero' style='" + elementInfo.style + "'/>"));
  tr.appendChild(td);

  if (inTable)
  {
      tbody.appendChild(tr);
      tr = document.createElement("tr");
  }
  
  // TD tipo
  td = document.createElement("td");
  if (!inTable) td.className = "data divisoria";
  td.style.border = 0;
  td.appendChild(document.createTextNode("Tipo: "));
  if (elementInfo.defaultTipoMedida != '')
  {
      td.appendChild(document.createElement("<input type='hidden' name='campo_" + elementInfo.id + "_tipoMedida' value='" + elementInfo.defaultTipoMedida + "'/>"));
      if (elementInfo.defaultTipoMedida == '1')
          td.appendChild(document.createTextNode("Longitud"));
      else if (elementInfo.defaultTipoMedida == '2')
          td.appendChild(document.createTextNode("Peso"));
      else if (elementInfo.defaultTipoMedida == '3')
          td.appendChild(document.createTextNode("Volumen"));
  }
  else
  {
      var select = document.createElement("<select name='campo_" + elementInfo.id + "_tipoMedida' style='" + elementInfo.style + "'/>");
      select.options[0] = new Option(" ", "0");
      select.options[1] = new Option("Longitud", "1");
      select.options[2] = new Option("Peso", "2");
      select.options[3] = new Option("Volumen", "3");
      
      td.appendChild(select);
  }
      
  tr.appendChild(td);

  if (inTable)
  {
      tbody.appendChild(tr);
      tr = document.createElement("tr");
  }

  // TD unidad
  td = document.createElement("td");
  if (!inTable) td.className = "data divisoria";
  td.style.border = 0;
  td.appendChild(document.createTextNode("Unidad: "));
  if (elementInfo.defaultUnidadMedida != '')
  {
      td.appendChild(document.createElement("<input type='hidden' name='campo_" + elementInfo.id + "_unidadMedida' value='" + elementInfo.defaultUnidadMedida + "'/>"));
      td.appendChild(document.createTextNode(elementInfo.defaultUnidadMedida));
  }
  else
    td.appendChild(document.createElement("<input type='text' size='5' name='campo_" + elementInfo.id + "_unidadMedida' style='" + elementInfo.style + "'/>"));
  
  tr.appendChild(td);
  
  tbody.appendChild(tr);
  element.appendChild(tbody);
  
  return element;
}

function createReferenceElement(elementInfo, rownum, inTable)
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
  td.appendChild(document.createElement("<input type='text' id='ref_" + elementInfo.id + "_" + rownum + "' name='campo_" + elementInfo.id + "' size='" + (inTable ? 20 : 50) + "'/>"));
  td.appendChild(document.createElement("<input type='hidden' id='ref_" + elementInfo.id + "_" + rownum + "_tiporef' name='campo_" + elementInfo.id + "_tiporef'/>"));
  td.appendChild(document.createElement("<input type='hidden' id='ref_" + elementInfo.id + "_" + rownum + "_idref' name='campo_" + elementInfo.id + "_idref'/>"));

  tr.appendChild(td);

  // TD con separador
  if (!inTable)
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
  enlace.href = "javascript:popupReferencePage('ref_" + elementInfo.id + "_" + rownum + "','" + elementInfo.refType + "','" + elementInfo.getRefIdsLists() + "');";
  var imagen = document.createElement("image");
  imagen.src="../images/buscar.gif";
  imagen.style.border = 0;
  imagen.alt = "Buscar";
  imagen.title = "Buscar";
  imagen.className = "imgTextTop";
  enlace.appendChild(imagen);
  td.appendChild(enlace);
  tr.appendChild(td);
  
  tbody.appendChild(tr);
  element.appendChild(tbody);

  return element;
}

function createComboBoxElement(id, name, options, emptyValue, defaultValue, js)
{
  var elementTag = "<select";
  if (js) elementTag += " onchange='" + js + "'";
  elementTag += ">";
  
  var element = document.createElement(elementTag);
  element.id = id;
  element.name = name;
 
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
    var table = td.getElementsByTagName("table")[0];
    var tbody = table.getElementsByTagName("tbody")[0];
    var pos = table.rows.length + 1;
    
    var tr = document.createElement("tr");
    tr.vAlign = "top";
    tr.id = "tr_" + id + "_" + pos;

    var td1 = document.createElement("td");
    td1.width = 30;
    
    var checkbox = document.createElement("<input type='checkbox' name='eliminar_" 
        + id + "' style='border:0' value='" + pos
        + "' onclick='javascript:checkIfSelectedToBeRemoved(this, \"tr_" + id + "_" 
        + pos + "\");'/>");


    td1.appendChild(checkbox);
    tr.appendChild(td1);

    var td2 = document.createElement("td");
    td2.appendChild(createElement(elementsInfo.getFieldElementInfo(id), pos, false));
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

function popupReferencePage(ref, refType, refIdsLists)
{
    if (refType == 1)
        popup("/archivo/action/descriptores?method=form&ref=" + ref + "&refType=" + refType + "&refIdsLists=" + refIdsLists, "_blank");
    else if (refType == 2) 
        popup("/archivo/action/elementos?method=form&ref=" + ref, "_blank");
    else if (refType == 3) 
        popup("/archivo/action/descriptores?method=form&ref=" + ref + "&refType=" + refType, "_blank");
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
